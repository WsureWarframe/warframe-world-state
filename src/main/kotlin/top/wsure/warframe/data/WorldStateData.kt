package top.wsure.warframe.data

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.value
import top.wsure.warframe.cache.CacheValue
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import kotlin.collections.HashMap

/**
 * FileName: WorldStateData
 * Author:   wsure
 * Date:     2021/3/4 8:15 下午
 * Description:
 */
object WorldStateData : AutoSavePluginConfig("WorldStateData") {
    val token :String by value(UUID.randomUUID().toString())

    var masters: MutableSet<Long> by value(mutableSetOf())

    var useCM: Boolean by value(true)
    //api host 默认使用自身的，可自己改
    var host: String by value("http://nymph.rbq.life:3000")

    //help key
    var helpKey: String by value("菜单")

    var commandList: List<RemoteCommand> by value(emptyList())

    var taskList: List<RemoteTask> by value(emptyList())

    var groupTaskSetting: MutableMap<Long,MutableMap<String,Boolean>> by value(HashMap())

    var cache: MutableMap<String, CacheValue<String>> by value(HashMap())
}