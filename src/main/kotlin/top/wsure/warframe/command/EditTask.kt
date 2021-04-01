package top.wsure.warframe.command

import net.mamoe.mirai.Bot
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.console.command.ConsoleCommandSender
import net.mamoe.mirai.console.command.descriptor.ExperimentalCommandDescriptors
import net.mamoe.mirai.console.command.isConsole
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.message.data.PlainText
import top.wsure.warframe.WorldState
import top.wsure.warframe.data.RemoteTask
import top.wsure.warframe.data.TaskEnum
import top.wsure.warframe.data.WorldStateData
import top.wsure.warframe.utils.PromiseUtils

/**
 * FileName: EditTask
 * Author:   wsure
 * Date:     2021/4/1 8:39 下午
 * Description:
 */
object EditTask : CompositeCommand(
    WorldState,
    "task",
    "任务", "定时任务",
    description = "定时任务设置"
) {
    @ExperimentalCommandDescriptors
    @ConsoleExperimentalApi
    override val prefixOptional = true


    @SubCommand("list", "show", "列表")
    @Description("task list")
    suspend fun CommandSender.list() {
        if(isConsole()){
            sendMessage(PlainText(WorldStateData.taskList.filter { it.type == TaskEnum.NOTIFY_GROUP }.joinToString("\n") { task ->
                "${task.name} - 间隔${(task.period / 60 / 1000).toDouble()}分钟"
            }))
        } else {
            val groupId = PromiseUtils.getGroupId(this)
            if (groupId != null) {
                var groupSetting = WorldStateData.groupTaskSetting[groupId]
                if (groupSetting == null) {
                    groupSetting = HashMap()
                    WorldStateData.groupTaskSetting[groupId] = groupSetting
                }
                sendMessage(PlainText(getGroupTaskSetting(groupSetting)))
            }
        }
    }

    @SubCommand("add", "加", "plus", "增加", "添加", "enable", "open", "开", "启用", "使用")
    @Description("+task")
    suspend fun CommandSender.add(task: String) {
        if(isConsole()){
            setAllGroupTaskList(task,this,true)
        } else {
            setGroupTaskList(task, this,true)
        }
    }

    @SubCommand("del", "remove", "rm", "delete", "删", "减", "删除", "去除", "移除", "关", "关闭", "禁用", "停用", "disable", "close")
    @Description("-task")
    suspend fun CommandSender.del(task: String) {
        if(isConsole()){
            setAllGroupTaskList(task,this,false)
        } else {
            setGroupTaskList(task, this,false)
        }
    }

    suspend fun setGroupTaskList(
        task: String,
        sender: CommandSender,
        enabled:Boolean
    ) {
        val remoteTask = WorldStateData.taskList.find { it.name == task }
        val groupId = PromiseUtils.getGroupId(sender)
        if (remoteTask != null && groupId != null) {
            var groupSetting = WorldStateData.groupTaskSetting[groupId]
            if (groupSetting == null) {
                groupSetting = HashMap()
                WorldStateData.groupTaskSetting[groupId] = groupSetting
            }
            setOperator(sender,groupId, remoteTask,enabled)
        }
    }

    suspend fun setOperator(sender: CommandSender,groupId: Long, task: RemoteTask,enabled:Boolean){
        if (PromiseUtils.isManage(sender)) {
            WorldStateData.groupTaskSetting[groupId]?.set(task.name, enabled)
            sender.sendMessage(PlainText("群${groupId} ${if (enabled) "启用" else "禁用" } ${task.name} 成功"))
        }
    }

    suspend fun setAllGroupTaskList(
        task: String,
        sender: CommandSender,
        enabled:Boolean
    ) {
        val remoteTask = WorldStateData.taskList.find { it.name == task }
        if (remoteTask != null) {
            val groups = Bot.instances.map { it.groups }.flatten().distinct()
            groups.forEach {
                setOperator(sender,it.id , remoteTask,enabled)
            }
        }
    }

    fun getGroupTaskSetting(groupSetting: MutableMap<String, Boolean>): String {
        return WorldStateData.taskList.filter { it.type == TaskEnum.NOTIFY_GROUP }.joinToString("\n") { task ->
            "${task.name} - 间隔${(task.period / 60 / 1000).toDouble()}分钟 - 状态: ${
                if (groupSetting.getOrDefault(task.name, false)) "开启" else "关闭"
            } "
        }
    }
}