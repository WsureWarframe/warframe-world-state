package top.wsure.warframe

import net.mamoe.mirai.console.plugins.PluginBase
import net.mamoe.mirai.event.events.MessageRecallEvent
import net.mamoe.mirai.event.subscribeAlways
import net.mamoe.mirai.event.subscribeMessages
import net.mamoe.mirai.message.MessageEvent
import net.mamoe.mirai.message.MessageReceipt
import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.utils.info
import top.wsure.warframe.enums.WorldStateKey
import top.wsure.warframe.utils.OkHttpUtils

object WorldState : PluginBase() {
    const val NYMPH_HOST = "http://nymph.rbq.life:3000/wf/robot/"
    override fun onLoad() {
        super.onLoad()
    }

    override fun onEnable() {
        super.onEnable()

        logger.info("Plugin loaded!")

        subscribeAlways<MessageEvent> { event ->
            val key = WorldStateKey.getByKeyWord(event.message.contentToString())
            if (key != null) {
                val response = OkHttpUtils.doGet(NYMPH_HOST+key.name)
                logger.info { "${event.senderName} 查询 ${key.keyWord}" }
                if(response != null){
                    event.reply(PlainText(response))
                }
            }
        }

        subscribeAlways<MessageRecallEvent> { event ->
            logger.info { "${event.authorId} 的消息被撤回了" }
        }
    }
}