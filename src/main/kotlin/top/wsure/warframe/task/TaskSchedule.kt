package top.wsure.warframe.task

import net.mamoe.mirai.console.MiraiConsole
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.utils.MiraiLogger
import top.wsure.warframe.cache.ConstantObject
import top.wsure.warframe.data.WorldStateData
import top.wsure.warframe.utils.CommandUtils

/**
 * FileName: TaskSchedule
 * Author:   wsure
 * Date:     2021/3/31 8:02 下午
 * Description:
 */
object TaskSchedule {
    @ConsoleExperimentalApi
    private val logger: MiraiLogger = MiraiConsole.createLogger(this::class.java.name)

    @ConsoleExperimentalApi
    val process : suspend () ->Unit = suspend {
        val tasks = CommandUtils.getRemoteTask(WorldStateData.host)
        val add = tasks.filter { task -> !WorldStateData.taskList.map { it.name }.contains(task.name) }
        CommandUtils.executeTask(add)
        ConstantObject.taskMap.keys.filter { key -> ! tasks.map { it.name }.contains(key) }.forEach {
            logger.info("取消 task - $it")
            val timer = ConstantObject.taskMap[it]
            timer?.second?.cancel()
            timer?.first?.purge()
            timer?.first?.cancel()
            ConstantObject.taskMap.remove(it)
        }
        WorldStateData.taskList = tasks
    }
}