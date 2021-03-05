package top.wsure.warframe.command

import net.mamoe.mirai.console.command.CommandOwner
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.RawCommand
import net.mamoe.mirai.console.command.descriptor.ExperimentalCommandDescriptors
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.content
import top.wsure.warframe.data.RemoteCommand
import top.wsure.warframe.data.WorldStateData
import top.wsure.warframe.utils.CommandUtils

/**
 * 这是一个包含参数的指令
 * 目前由于没有多参数的场景，暂时只有单参数
 * 但是，args的chain支持切分参数，以后再说吧
 *
 * FileName: Warframe
 * Author:   wsure
 * Date:     2021/3/4 8:11 下午
 * Description:
 */
@ConsoleExperimentalApi
class WFParamCommand(
    plugin: CommandOwner,
    private val command:RemoteCommand,
) : RawCommand(plugin, primaryName = command.name, description = command.desc){

    @ExperimentalCommandDescriptors
    override val prefixOptional = true

    override suspend fun CommandSender.onCommand(args: MessageChain) {
        val msg = args.joinToString(" ") { it.content }
        sendMessage(CommandUtils.getRemoteResponse(WorldStateData.host + command.path + msg))
    }

}