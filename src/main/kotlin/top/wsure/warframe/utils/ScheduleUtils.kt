package top.wsure.warframe.utils

import java.util.*

/**

 * FileName: Timer
 * Author:   wsure
 * Date:     2021/1/21 3:32 下午
 * Description:
 */

class ScheduleUtils {
    companion object{
        fun loopEvent(process:()->Unit,start:Date,period:Long){
            Timer().schedule(object:TimerTask(){
                override fun run() {
                    process()
                }
            }, start, period)
        }
    }
}