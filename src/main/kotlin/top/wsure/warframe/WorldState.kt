package top.wsure.warframe

import net.mamoe.mirai.console.plugins.PluginBase
import net.mamoe.mirai.event.events.MessageRecallEvent
import net.mamoe.mirai.event.subscribeAlways
import net.mamoe.mirai.message.MessageEvent
import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.utils.info
import top.wsure.warframe.enums.BeginWithKeyword
import top.wsure.warframe.enums.WorldStateKey
import top.wsure.warframe.utils.MessageUtils
import top.wsure.warframe.utils.OkHttpUtils

object WorldState : PluginBase() {

    private const val HELP_KEY = "help"
    override fun onLoad() {
        super.onLoad()
    }

    override fun onEnable() {
        super.onEnable()

        logger.info("Plugin loaded!")

        subscribeAlways<MessageEvent> { event ->
            val messageContent = event.message.contentToString()
            val host = MessageUtils.getUrlByEnum(messageContent)
            if (host != null) {
                val response = OkHttpUtils.doGet(host)
                logger.info { "${event.senderName} 查询 $host" }
                if(response != null){
                    event.reply(PlainText(response))
                }
            }

            if(messageContent == HELP_KEY){
                val messageChain =  MessageChainBuilder()
                    .append(PlainText("warframe-world-state插件功能如下\n"))
                    .append(PlainText(BeginWithKeyword.getHelpMenu()))
                    .append(PlainText(WorldStateKey.getHelpMenu()))
                    .build()
                event.reply(messageChain)
            }

        }

        subscribeAlways<MessageRecallEvent> { event ->
            logger.info { "${event.authorId} 的消息被撤回了" }
        }
    }
}