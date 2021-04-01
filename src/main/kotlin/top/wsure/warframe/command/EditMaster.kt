package top.wsure.warframe.command

import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.console.command.descriptor.ExperimentalCommandDescriptors
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.contact.User
import net.mamoe.mirai.message.data.PlainText
import top.wsure.warframe.WorldState
import top.wsure.warframe.data.WorldStateData
import top.wsure.warframe.utils.PromiseUtils

object EditMaster : CompositeCommand(
    WorldState,
    "master",
    "主人", "骂死他",
    description = "bot-master-tools插件设置"
) {
    @ExperimentalCommandDescriptors
    @ConsoleExperimentalApi
    override val prefixOptional = true

    @SubCommand("list", "show", "列表")
    @Description("master列表")
    suspend fun CommandSender.list() {
        sendMessage(PlainText(WorldStateData.masters.joinToString("\n")))
    }

    @SubCommand("add", "加", "plus","增加","添加")
    @Description("+ master")
    suspend fun CommandSender.add(user: User) {
        if (PromiseUtils.isMaster(this.user)) {
            val isSuccess = WorldStateData.masters.add(user.id)
            sendMessage(PlainText("添加${user.id}${if (isSuccess) "成功" else "失败"}"))
        }
    }

    @SubCommand("del", "remove", "rm","delete","删","减","删除","去除","移除")
    @Description("- master")
    suspend fun CommandSender.del(user: User) {
        if (PromiseUtils.isMaster(this.user)) {
            val isSuccess = WorldStateData.masters.remove(user.id)
            sendMessage(PlainText("删除${user.id}${if (isSuccess) "成功" else "失败"}"))
        }
    }
}