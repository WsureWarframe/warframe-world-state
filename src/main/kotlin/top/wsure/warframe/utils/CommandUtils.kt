package top.wsure.warframe.utils

import net.mamoe.mirai.Bot
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.contact.User
import net.mamoe.mirai.message.data.Message
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.content
import net.mamoe.mirai.utils.MiraiLogger
import top.wsure.warframe.WorldState
import top.wsure.warframe.cache.TaskCache
import top.wsure.warframe.command.WFParamCommand
import top.wsure.warframe.command.WFSimpleCommand
import top.wsure.warframe.data.*
import top.wsure.warframe.task.WFTask
import java.util.*

/**
 * FileName: CommandUtils
 * Author:   wsure
 * Date:     2021/3/4 9:48 下午
 * Description:
 */
class CommandUtils {
    companion object {
        private val logger: MiraiLogger = MiraiLogger.create(this::class.java.name)
        suspend fun getRemoteCommand(host:String):List<RemoteCommand> {
            logger.info("加载远程指令列表-开始,host:${host}")
            var res = emptyList<RemoteCommand>()
            try {
                res =  OkHttpUtils.doGetObject("${host}/robot/commands")
                logger.info("加载远程指令列表-成功,指令列表:${getRemoteCommandHelp(res).content}")
            }catch (e:Exception){
                logger.error("加载远程指令列表-失败,请检查网络是否通畅,host:${host}",e)
            }
            return res
        }

        suspend fun getRemoteTask(host:String):List<RemoteTask> {
            logger.info("加载远程task列表-开始,host:${host}")
            var res = emptyList<RemoteTask>()
            try {
                res =  OkHttpUtils.doGetObject("${host}/robot/tasks")
                logger.info("加载远程task列表-成功,任务列表:${res.joinToString(",") { it.name }}")
            }catch (e:Exception){
                logger.error("加载远程task列表-失败,请检查网络是否通畅,host:${host}",e)
            }
            return res
        }

        suspend fun getRemoteQueue(host:String,path:String,name:String):List<RemoteQueue> {
            logger.info("加载远程${name}queue列表-开始,host:${host}")
            var res = emptyList<RemoteQueue>()
            try {
                res =  OkHttpUtils.doGetObject("${host}${path}")
                logger.info("加载远程${name}queue列表-成功")
            }catch (e:Exception){
                logger.error("加载远程${name}queue列表-失败,请检查网络是否通畅,host:${host}",e)
            }
            return res
        }

        suspend fun getRemoteResponse(url:String,bot:Bot?,user:User?):Message{
            val query = "?bots=${
                bot?.id ?: Bot.instances.map { it.id }.joinToString(",")
            }&users=${
                user?.id ?: Bot.instances.map { it.id }.joinToString(",")
            }"
            var response:String? = null
            try {
                response = OkHttpUtils.asyncDoGet(url+query)
            }catch (e:Exception){
                WorldState.logger.error(e)
            }

            return if (response != null) {
                PlainText(response)
            } else {
                PlainText("暂时无法查询，请访问\n$url")
            }
        }

        @ConsoleExperimentalApi
        fun registerAll(list:List<RemoteCommand>){
            logger.info("注册远程指令列表-开始,commandsList:${list.joinToString(","){it.name}}")
            list.map {
                when(it.type) {
                CommandType.PARAM -> WFParamCommand(WorldState,it)
                CommandType.SIMPLE -> WFSimpleCommand(WorldState,it)
            } }.forEach { it.register() }
        }

        fun getRemoteCommandHelp(commandList: List<RemoteCommand>): Message {
            return PlainText("warframe-world-state插件功能如下\n${
                commandList.joinToString("\n") { 
                    "${it.name}${if (it.type == CommandType.PARAM) " <搜索词>" else ""}" 
                }
            }")
        }

        @ConsoleExperimentalApi
        fun executeTask(taskList: List<RemoteTask>) {
            taskList.forEach {
                val timer = ScheduleUtils.loopEvent(WFTask(it).process, Date(),it.period,it.name)
                TaskCache.taskMap[it.name] = timer
            }
        }

        @ConsoleExperimentalApi
        suspend fun initTaskQueue(taskList: List<RemoteTask>) {
            taskList.forEach {
                WFTask(it).init()
            }
        }
    }
}