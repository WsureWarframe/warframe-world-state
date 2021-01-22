package top.wsure.warframe

import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.events.MessageEvent
import net.mamoe.mirai.event.events.MessageRecallEvent
import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.utils.info
import net.mamoe.mirai.event.globalEventChannel
import org.apache.ibatis.session.SqlSession
import top.wsure.warframe.dao.UserMapper
import top.wsure.warframe.entity.User
import top.wsure.warframe.enums.BeginWithKeyword
import top.wsure.warframe.enums.WorldStateKey
import top.wsure.warframe.utils.MessageUtils
import top.wsure.warframe.utils.OkHttpUtils
import top.wsure.warframe.utils.SqliteUtils
import java.io.File

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



    override fun onEnable() {
        super.onEnable()

        logger.info("Plugin loaded!")
        globalEventChannel().subscribeAlways<MessageEvent> { event ->
            val messageContent = event.message.contentToString()
            val host = MessageUtils.getUrlByEnum(messageContent)
            if (host != null) {
                val response = OkHttpUtils.doGet(host)
                logger.info { "${event.senderName} 查询 $host" }
                val u = event.sender
                u.nick
                u.remark
                u.avatarUrl
                u.id
                if (response != null) {
                    event.subject.sendMessage(PlainText(response))
                }
            }

            if (messageContent == HELP_KEY) {
                val messageChain = MessageChainBuilder()
                    .append(PlainText("warframe-world-state插件功能如下\n"))
                    .append(PlainText(BeginWithKeyword.getHelpMenu()))
                    .append(PlainText("\n"))
                    .append(PlainText(WorldStateKey.getHelpMenu()))
                    .build()
                event.subject.sendMessage(messageChain)
            }

        }

        globalEventChannel().subscribeAlways<MessageRecallEvent> { event ->
            logger.info { "${event.authorId} 的消息被撤回了" }
        }
        val file:File = resolveDataFile("test.db")

        val session: SqlSession  = SqliteUtils.sqlSessionFactory(file).openSession(true)

        val mapper:UserMapper = session.getMapper(UserMapper::class.java)
        mapper.insert(User(id= 844157922,nick ="test"))

        session.close()
    }

    /**
     * Will be invoked when the plugin is disabled
     */
    override fun onDisable() {
        logger.info { "Plugin unloaded" }

    }
}