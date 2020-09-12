package top.wsure.warframe

import com.google.auto.service.AutoService
import net.mamoe.mirai.console.plugin.jvm.JvmPlugin
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.console.plugin.jvm.SimpleJvmPluginDescription
import net.mamoe.mirai.event.events.FriendInputStatusChangedEvent
import net.mamoe.mirai.event.events.MessagePreSendEvent
import net.mamoe.mirai.event.events.MessageRecallEvent
import net.mamoe.mirai.event.subscribeAlways
import net.mamoe.mirai.event.subscribeFriendMessages
import net.mamoe.mirai.event.subscribeMessages
import net.mamoe.mirai.message.FriendMessageEvent
import net.mamoe.mirai.message.data.content
import net.mamoe.mirai.utils.info

@AutoService(JvmPlugin::class)
object WorldState : KotlinPlugin(
    SimpleJvmPluginDescription("top.wsure.warframe" ,
            "0.0.1-alpha")
) {
    override fun onLoad() {
        super.onLoad()
        print("芜湖，起飞🛫️")

    }

    override fun onEnable() {
        super.onEnable()

        logger.info("Plugin loaded!")

        subscribeMessages {
            "greeting" reply { "Hello ${sender.nick}" }
        }

        subscribeAlways<FriendMessageEvent> { event -> reply("你说了 :${event.message.content}") }
    }
}