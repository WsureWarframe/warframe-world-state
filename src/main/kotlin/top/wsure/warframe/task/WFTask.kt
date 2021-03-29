package top.wsure.warframe.task

import net.mamoe.mirai.Bot
import top.wsure.warframe.data.RemoteTask
import top.wsure.warframe.data.TaskEnum
import top.wsure.warframe.data.WorldStateData
import java.util.stream.Collectors

/**
 * FileName: UpdatePlugin
 * Author:   wsure
 * Date:     2021/3/29 3:30 下午
 * Description:
 */
class WFTask(val remoteTask: RemoteTask){
    val process:() -> Unit = {

        when(remoteTask.type){
            TaskEnum.NOTIFY -> {
                WorldStateData.host
            }
            TaskEnum.UPDATE -> {

            }
            TaskEnum.REFRESH -> {

            }
            TaskEnum.PULL -> {

            }
        }

        Bot.instances.map { it.groups.stream() }.stream().flatMap { it }.collect(Collectors.toSet())
    }
}