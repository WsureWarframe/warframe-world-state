package top.wsure.warframe.task

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.mamoe.mirai.console.MiraiConsole
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.message.data.content
import net.mamoe.mirai.utils.MiraiLogger
import top.wsure.warframe.cache.CacheValue
import top.wsure.warframe.cache.ExpirableCache
import top.wsure.warframe.cache.TaskCache
import top.wsure.warframe.data.RemoteQueue
import top.wsure.warframe.data.RemoteTask
import top.wsure.warframe.data.TaskEnum
import top.wsure.warframe.data.WorldStateData
import top.wsure.warframe.utils.CommandUtils
import top.wsure.warframe.utils.NotifyUtils

/**
 * FileName: UpdatePlugin
 * Author:   wsure
 * Date:     2021/3/29 3:30 下午
 * Description:
 */
class WFTask(val remoteTask: RemoteTask){

    @ConsoleExperimentalApi
    private val logger: MiraiLogger = MiraiConsole.createLogger(this::class.java.name)

    @ConsoleExperimentalApi
    val process: suspend () -> Unit =  {
        val queues:List<RemoteQueue> = requestQueue(remoteTask)
        if(queues.isNotEmpty()){
            val oldQueues = getCatchQueue(remoteTask)
            val now = System.currentTimeMillis()
            val difference = queues.filter { queue -> !oldQueues.map { it.key }.contains(queue.key) && queue.timeout > now }
            when(remoteTask.type){
                TaskEnum.NOTIFY_GROUP -> {
                    difference.forEach {
                        val msg = CommandUtils.getRemoteResponse(WorldStateData.host+remoteTask.infoPath+it.key,null,null)
                        logger.info("[notifyAllGroup - ${remoteTask.name}] - message:${msg.content}")
                        NotifyUtils.notifyAllGroup(null,msg)
                    }
                }
                TaskEnum.NOTIFY_MASTER -> {
                    difference.forEach {
                        val msg = CommandUtils.getRemoteResponse(WorldStateData.host+remoteTask.infoPath+it.key,null,null)
                        logger.info("[notifyAllMaster - ${remoteTask.name}] - message:${msg.content}")
                        NotifyUtils.notifyAllMaster(null,msg)
                    }
                }
                TaskEnum.PULL -> suspend {
                    val res = Json.encodeToString(difference.map { ExpirableCache.CACHE_MAP[it.key] })
                    //todo post message
                }
                TaskEnum.CANCEL -> suspend {
                    queues.forEach {
                        TaskCache.taskMap[it.key]?.cancel()
                    }
                }
                TaskEnum.RELOAD -> suspend {
                    queues.forEach {
                        TaskCache.taskMap[it.key]?.cancel()
                    }
                    var newRemoteTasks = CommandUtils.getRemoteTask(WorldStateData.host)
                    if(newRemoteTasks.isNotEmpty()){
                        newRemoteTasks =  newRemoteTasks.filter { nt -> queues.map { it.key }.contains(nt.name) || nt.type == TaskEnum.RELOAD }
                        CommandUtils.executeTask(newRemoteTasks)
                    }
                }
                TaskEnum.REFRESH -> {}
                else -> {}
            }
            storeQueue(remoteTask,queues)
        }

    }

    suspend fun init(){
        val queues = requestQueue(remoteTask)
        if(queues.isNotEmpty())
            storeQueue(remoteTask,queues)
    }

    suspend fun requestQueue(remoteTask: RemoteTask):List<RemoteQueue>{
        return CommandUtils.getRemoteQueue(WorldStateData.host,remoteTask.queuePath,remoteTask.name)
    }

    fun getCatchQueue(remoteTask: RemoteTask):List<RemoteQueue>{
        val cacheKey = cacheQueueName(remoteTask)
        val oldValues = ExpirableCache.CACHE_MAP.getData<List<RemoteQueue>>(cacheKey)
        return if(oldValues != null && oldValues.isNotEmpty()) oldValues.map { it } else emptyList()
    }

    fun cacheQueueName(remoteTask: RemoteTask):String{
        return "task::queue::${remoteTask.name}"
    }

    fun storeQueue(remoteTask: RemoteTask,queues:List<RemoteQueue>){
        val cacheKey = cacheQueueName(remoteTask)
        ExpirableCache.CACHE_MAP[cacheKey] = CacheValue(cacheKey,Json.encodeToString(queues),queues.maxOf { it.timeout })
    }
}