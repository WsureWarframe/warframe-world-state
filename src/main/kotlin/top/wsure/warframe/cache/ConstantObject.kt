package top.wsure.warframe.cache

import java.util.*
import kotlin.collections.HashMap

/**
 * FileName: TaskCache
 * Author:   wsure
 * Date:     2021/3/31 10:31 上午
 * Description:
 */
object ConstantObject {
    val taskMap: MutableMap<String, Pair<Timer,TimerTask>> = HashMap()

    const val TASK_HOLDER = "updateTaskLoop"

    const val INITIATIVE_NOTIFY = "INITIATIVE_NOTIFY_ALL_GROUP"
}