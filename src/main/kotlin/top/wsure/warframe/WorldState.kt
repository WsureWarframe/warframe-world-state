package top.wsure.warframe

import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.events.MessageEvent
import net.mamoe.mirai.event.events.MessageRecallEvent
import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.utils.info
import net.mamoe.mirai.event.globalEventChannel
import org.jetbrains.exposed.sql.Database
import top.wsure.warframe.enums.BeginWithKeyword
import top.wsure.warframe.enums.DatabaseKey
import top.wsure.warframe.enums.WorldStateKey
import top.wsure.warframe.dao.UserDao
import top.wsure.warframe.service.SaveDataService
import top.wsure.warframe.service.StatisticalService
import top.wsure.warframe.utils.MessageUtils
import top.wsure.warframe.utils.OkHttpUtils
import top.wsure.warframe.utils.DBUtils

object WorldState : KotlinPlugin(
//        @OptIn(ConsoleExperimentalApi::class)
//        JvmPluginDescription.loadFromResource()
    JvmPluginDescription(
        version = "0.0.4",
        id = "top.wsure.warframe.WorldState",
        name = "WarframeWorldState",
    )
) {

    private const val HELP_KEY = "help"
    private const val DB_NAME = "test"
    private val DB_FILE = resolveDataFile(DB_NAME)
    lateinit var globalDatabase: Database


    override fun onEnable() {
        val osName = System.getProperty("os.name").split(" ")[0]
        val osArch = System.getProperty("os.arch")
        logger.info{"os.name:${osName}"}
        logger.info{"os.arch:${osArch}"}
        logger.info{"os.version:${System.getProperty("os.version")}"}

        super.onEnable()
        //加载数据库
        Thread {
            initDatabase()

            logger.info("Plugin loaded!")
            globalEventChannel().subscribeAlways<MessageEvent> { event ->
                val messageContent = event.message.contentToString()
                val instruction = MessageUtils.getUrlByEnum(messageContent)
                val host = instruction?.url
                if (host != null) {
                    logger.info { "${event.senderName} 查询 $host" }
                    var response:String? = null
                    try {
                        response = OkHttpUtils.doGet(host)
                    }catch (e:Exception){
                        logger.error(e)
                    }

                    if (response != null) {
                        event.subject.sendMessage(PlainText(response))
                    } else {
                        event.subject.sendMessage(PlainText("暂时无法查询，请访问\n$host"))
                    }
                    SaveDataService.storage(event.sender,instruction)

                }

                if (messageContent == HELP_KEY) {
                    val messageChain = MessageChainBuilder()
                            .append(PlainText("warframe-world-state插件功能如下\n"))
                            .append(PlainText(BeginWithKeyword.getHelpMenu()))
                            .append(PlainText("\n"))
                            .append(PlainText(WorldStateKey.getHelpMenu()))
                            .append(PlainText("\n"))
                            .append(PlainText(DatabaseKey.getHelpMenu()))
                            .build()
                    event.subject.sendMessage(messageChain)
                }
                val dbKey = MessageUtils.getDatabaseEnum(messageContent)
                if (dbKey != null){
                    when(dbKey) {
                        DatabaseKey.USER_LIST -> UserDao.userList(event)
                        DatabaseKey.KEY_TOP -> StatisticalService.queryKeyTop(event)
                    }
                }

            }

            globalEventChannel().subscribeAlways<MessageRecallEvent> { event ->
                logger.info { "${event.authorId} 的消息被撤回了" }
            }
        }.start()

    }

    /**
     * Will be invoked when the plugin is disabled
     */
    override fun onDisable() {
        logger.info { "Plugin unloaded" }

    }

    private fun initDatabase(){
        globalDatabase = DBUtils.getDatabase(DB_FILE)
        DBUtils.initTableIfNotExist(globalDatabase)
    }
}