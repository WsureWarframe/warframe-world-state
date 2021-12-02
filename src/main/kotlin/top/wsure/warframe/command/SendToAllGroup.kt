package top.wsure.warframe.command

import net.mamoe.mirai.console.MiraiConsole
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.RawCommand
import net.mamoe.mirai.console.command.descriptor.ExperimentalCommandDescriptors
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.utils.MiraiLogger
import top.wsure.warframe.WorldState
import top.wsure.warframe.cache.ConstantObject.INITIATIVE_NOTIFY
import top.wsure.warframe.utils.NotifyUtils.Companion.notifyAllGroup
import top.wsure.warframe.utils.PromiseUtils.Companion.isMaster

@ConsoleExperimentalApi
object SendToAllGroup : RawCommand(
    WorldState,
    "发送全部群",
    "sendToAllGroup"
) {
    private val logger: MiraiLogger = MiraiConsole.createLogger(this.javaClass.canonicalName)

    @OptIn(ExperimentalCommandDescriptors::class)
    override val prefixOptional = true

    override suspend fun CommandSender.onCommand(args: MessageChain) {
        if (isMaster(this.user)) {
            val msg = args.joinToString(" ") { it.contentToString() }
            logger.info("发送全部群 :${msg}")
            sendMessage("发送全部群 完成，耗时:${notifyAllGroup(this.bot, args,INITIATIVE_NOTIFY)}ms")
            logger.info("发送全部群 :完成")
        }
    }

}