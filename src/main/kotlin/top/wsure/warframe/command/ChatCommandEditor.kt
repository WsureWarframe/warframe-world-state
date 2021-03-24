package top.wsure.warframe.command

import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.ConsoleCommandSender.sendMessage
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.console.command.descriptor.ExperimentalCommandDescriptors
import net.mamoe.mirai.console.command.isConsole
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.message.data.PlainText
import top.wsure.warframe.WorldState
import top.wsure.warframe.data.WorldStateData

object ChatCommandEditor: SimpleCommand(
    WorldState,
    "chat-command",
    "聊天命令","cm","chatCommand",
    description = "聊天命令转发设置"
) {
    @ExperimentalCommandDescriptors
    @ConsoleExperimentalApi
    override val prefixOptional = true

    @Handler
    suspend fun CommandSender.handle(useCM:Boolean) { // 函数名随意, 但参数需要按顺序放置.
        if (isConsole()) {
            WorldStateData.useCM = useCM
            sendMessage(PlainText("设置使用聊天命令${if (WorldStateData.useCM) "开启,可以在聊天中使用命令" else "关闭,再次开启需要在控制台执行"}"))
        }
    }
}