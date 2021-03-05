package top.wsure.warframe.command

import net.mamoe.mirai.console.command.CommandOwner
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.console.command.descriptor.ExperimentalCommandDescriptors
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import top.wsure.warframe.WorldState
import top.wsure.warframe.data.WorldStateData
import top.wsure.warframe.utils.CommandUtils

/**
 * FileName: WFHelp
 * Author:   wsure
 * Date:     2021/3/5 12:48 下午
 * Description:help
 */
@ConsoleExperimentalApi
class WFHelp(
    plugin: CommandOwner,
    helpKey:String,
) : SimpleCommand(plugin, primaryName = helpKey, description = "插件帮助指令"){

    @ExperimentalCommandDescriptors
    override val prefixOptional = true

    @Handler
    suspend fun CommandSender.handle() { // 函数名随意, 但参数需要按顺序放置.

        sendMessage(CommandUtils.getRemoteCommandHelp(WorldStateData.commandList))

    }
}