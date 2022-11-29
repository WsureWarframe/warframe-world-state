package top.wsure.warframe.command

import net.mamoe.mirai.console.command.CommandOwner
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.ConsoleCommandSender.sendMessage
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.console.command.descriptor.ExperimentalCommandDescriptors
import net.mamoe.mirai.console.command.isConsole
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.message.data.buildForwardMessage
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
    @OptIn(ExperimentalCommandDescriptors::class)
    override val prefixOptional = true

    @Handler
    suspend fun CommandSender.handle() { // 函数名随意, 但参数需要按顺序放置.

        val responseMsg = CommandUtils.getRemoteCommandHelp(WorldStateData.commandList)

        if(isConsole()){
            sendMessage(responseMsg)
        } else {
//            sendMessage(buildForwardMessage(this.subject!!) {
//                val bot = this@handle.bot!!
//                add(bot,responseMsg)
//            })
            sendMessage(responseMsg)
        }

    }
}