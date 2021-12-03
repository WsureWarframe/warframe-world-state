package top.wsure.warframe.task

import net.mamoe.mirai.console.MiraiConsole
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.message.data.content
import net.mamoe.mirai.utils.MiraiLogger
import top.wsure.warframe.cache.CacheValue
import top.wsure.warframe.cache.ExpirableCache
import top.wsure.warframe.data.RemoteQueue
import top.wsure.warframe.data.RemoteTask
import top.wsure.warframe.data.TaskEnum
import top.wsure.warframe.data.WorldStateData
import top.wsure.warframe.utils.CommandUtils
import top.wsure.warframe.utils.JsonUtils.objectToJson
import top.wsure.warframe.utils.NotifyUtils
import top.wsure.warframe.utils.OkHttpUtils

/**
 * FileName: UpdatePlugin
 * Author:   wsure
 * Date:     2021/3/29 3:30 下午
 * Description:
 */
class WFTask(private val remoteTask: RemoteTask) {

    @ConsoleExperimentalApi
    private val logger: MiraiLogger = MiraiConsole.createLogger(this::class.java.name)

    @ConsoleExperimentalApi
    val process: suspend () -> Unit = {
        val queues: List<RemoteQueue> = requestQueue(remoteTask)
        if (queues.isNotEmpty()) {
            val oldQueues = getCatchQueue(remoteTask)
            val now = System.currentTimeMillis()
            val difference =
                queues.filter { queue -> !oldQueues.map { it.key }.contains(queue.key) && queue.timeout > now }
            if (difference.isNotEmpty()) {
                when (remoteTask.type) {
                    TaskEnum.NOTIFY_GROUP -> {
                        val msg = CommandUtils.getRemoteResponse(WorldStateData.host + remoteTask.infoPath +
                                difference.joinToString(",") { it.key }, null, null
                        )
                        NotifyUtils.notifyAllGroup(null, msg, remoteTask.name)
                        logger.info("[notifyAllGroup - ${remoteTask.name}] - message:${msg.content}")

                    }
                    TaskEnum.NOTIFY_MASTER -> {
                        val msg = CommandUtils.getRemoteResponse(
                            WorldStateData.host + remoteTask.infoPath +
                                    difference.joinToString(",") { it.key }, null, null
                        )
                        logger.info("[notifyAllMaster - ${remoteTask.name}] - message:${msg.content}")
                        NotifyUtils.notifyAllMaster(null, msg)

                    }
                    TaskEnum.PULL -> suspend {
                        val res = difference.mapNotNull { ExpirableCache.CACHE_MAP[it.key] }
                        OkHttpUtils.asyncDoPost(
                            WorldStateData.host + remoteTask.infoPath + remoteTask.name + "?token=${WorldStateData.token}",
                            res
                        )
                    }
                    TaskEnum.REFRESH -> {
                    }
                }
            }
            storeQueue(remoteTask, queues)
        }

    }

    suspend fun init() {
        val queues = requestQueue(remoteTask)
        if (queues.isNotEmpty())
            storeQueue(remoteTask, queues)
    }

    private suspend fun requestQueue(remoteTask: RemoteTask): List<RemoteQueue> {
        return CommandUtils.getRemoteQueue(WorldStateData.host, remoteTask.queuePath, remoteTask.name)
    }

    private fun getCatchQueue(remoteTask: RemoteTask): List<RemoteQueue> {
        val cacheKey = cacheQueueName(remoteTask)
        val oldValues = ExpirableCache.CACHE_MAP.getData<List<RemoteQueue>>(cacheKey)
        return if (oldValues != null && oldValues.isNotEmpty()) oldValues.map { it } else emptyList()
    }

    private fun cacheQueueName(remoteTask: RemoteTask): String {
        return "task::queue::${remoteTask.name}"
    }

    private fun storeQueue(remoteTask: RemoteTask, queues: List<RemoteQueue>) {
        val cacheKey = cacheQueueName(remoteTask)
        ExpirableCache.CACHE_MAP[cacheKey] =
            CacheValue(cacheKey, queues.objectToJson(), queues.maxOf { it.timeout })
    }
}