package top.wsure.warframe.utils

import kotlinx.coroutines.launch
import net.mamoe.mirai.console.MiraiConsole
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.utils.MiraiLogger
import top.wsure.warframe.WorldState
import top.wsure.warframe.cache.ConstantObject
import top.wsure.warframe.data.WorldStateData
import java.util.*

/**

 * FileName: Timer
 * Author:   wsure
 * Date:     2021/1/21 3:32 下午
 * Description:
 */

class ScheduleUtils {
    companion object{
        @ConsoleExperimentalApi
        private val logger: MiraiLogger = MiraiConsole.createLogger(this::class.java.name)

        @ConsoleExperimentalApi
        fun loopEvent(process:suspend ()->Unit, start:Date, period:Long, name:String):Pair<Timer,TimerTask>{
            val t = Timer()
            val enable = WorldStateData.taskList.any { it.name == name } || name == ConstantObject.TASK_HOLDER
            val tt = object :TimerTask(){
                override fun run(){
                    logger.info("[task - ${name}] - start - period:${period}ms")
                    if(!enable) {
                        logger.info("取消 TimerTask - $name")
                        this.cancel()
                    }
                    WorldState.launch{
                        process()
                    }
                    logger.info("[task - ${name}] - end")
                }
            }
            t.schedule(tt,start,period)
            return Pair(t,tt)
        }
    }
}