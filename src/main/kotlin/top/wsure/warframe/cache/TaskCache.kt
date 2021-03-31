package top.wsure.warframe.cache

import java.util.*
import kotlin.collections.HashMap

/**
 * FileName: TaskCache
 * Author:   wsure
 * Date:     2021/3/31 10:31 上午
 * Description:
 */
object TaskCache {
    val taskMap: MutableMap<String, Timer>
        get() = HashMap()
}