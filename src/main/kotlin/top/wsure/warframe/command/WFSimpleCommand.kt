package top.wsure.warframe.command

import net.mamoe.mirai.console.MiraiConsole
import net.mamoe.mirai.console.command.CommandOwner
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.console.command.descriptor.ExperimentalCommandDescriptors
import net.mamoe.mirai.console.command.isConsole
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.message.data.buildForwardMessage
import net.mamoe.mirai.utils.MiraiLogger
import top.wsure.warframe.data.RemoteCommand
import top.wsure.warframe.data.WorldStateData
import top.wsure.warframe.utils.CommandUtils

/**
 * 一个简单的简单参数注册类
 *
 * FileName: Warframe
 * Author:   wsure
 * Date:     2021/3/4 8:11 下午
 * Description:
 */
@ConsoleExperimentalApi
class WFSimpleCommand(
    plugin: CommandOwner,
    private val command: RemoteCommand,
) : SimpleCommand(plugin, primaryName = command.name, description = command.desc) {

    private val logger: MiraiLogger = MiraiConsole.createLogger(command.name)

    @OptIn(ExperimentalCommandDescriptors::class)
    override val prefixOptional = true

    @Handler
    suspend fun CommandSender.handle() { // 函数名随意, 但参数需要按顺序放置.
        val remoteUrl = WorldStateData.host + command.path
        logger.info("${this.user?.nick} 查询 $remoteUrl")
        val responseMsg = CommandUtils.getRemoteResponse(remoteUrl,this.bot,this.user)
        if(isConsole()){
            sendMessage(responseMsg)
        } else {
            sendMessage(buildForwardMessage(this.subject!!) {
                val bot = this@handle.bot!!
                add(bot,responseMsg)
            })
        }
    }

}