package top.wsure.warframe.command

import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.console.command.descriptor.ExperimentalCommandDescriptors
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import top.wsure.warframe.WorldState
import top.wsure.warframe.dao.UserDao
import top.wsure.warframe.service.StatisticalService

object DatabaseCommand : CompositeCommand(
    WorldState,"db","统计",description = "数据库统计"
){
    @ExperimentalCommandDescriptors
    @ConsoleExperimentalApi
    override val prefixOptional = true

    @SubCommand("热门查询")
    suspend fun CommandSender.top(){
        sendMessage(StatisticalService.queryKeyTop())
    }

    @SubCommand("用户列表")
    suspend fun CommandSender.userList(){
        sendMessage(UserDao.userList(this.user))
    }

}