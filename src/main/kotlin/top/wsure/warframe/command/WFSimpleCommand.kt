package top.wsure.warframe.command

import net.mamoe.mirai.console.MiraiConsole
import net.mamoe.mirai.console.command.CommandOwner
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.console.command.descriptor.ExperimentalCommandDescriptors
import net.mamoe.mirai.console.command.isNotConsole
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.contact.User
import net.mamoe.mirai.utils.MiraiLogger
import top.wsure.warframe.data.RemoteCommand
import top.wsure.warframe.data.WorldStateData
import top.wsure.warframe.service.SaveDataService
import top.wsure.warframe.utils.CommandUtils
import top.wsure.warframe.utils.MessageUtils

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

    @ExperimentalCommandDescriptors
    override val prefixOptional = true

    @Handler
    suspend fun CommandSender.handle() { // 函数名随意, 但参数需要按顺序放置.
        val remoteUrl = WorldStateData.host + command.path
        logger.info("${this.user?.nick} 查询 $remoteUrl")
        if(isNotConsole()){
            SaveDataService.storage(
                this.user ?: this.bot as User, MessageUtils.Instruction(
                    command.alia,
                    command.name,
                    remoteUrl
                )
            )
        }
        sendMessage(CommandUtils.getRemoteResponse(remoteUrl))
    }

}