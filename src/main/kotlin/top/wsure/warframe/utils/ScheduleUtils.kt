package top.wsure.warframe.utils

import kotlinx.coroutines.launch
import net.mamoe.mirai.console.MiraiConsole
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.utils.MiraiLogger
import top.wsure.warframe.WorldState
import java.util.*
import kotlin.concurrent.schedule

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
        fun loopEvent(process:suspend ()->Unit, start:Date, period:Long, name:String):Timer{
            val t = Timer()
            t.schedule(start,period){
                logger.info("[task - ${name}] - start - period:${period}ms")
                WorldState.launch{
                    process()
                }
                logger.info("[task - ${name}] - end")
            }
            return t
        }
    }
}