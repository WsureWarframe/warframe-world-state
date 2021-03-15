package top.wsure.warframe.utils

import net.mamoe.mirai.Bot
import net.mamoe.mirai.console.MiraiConsole
import net.mamoe.mirai.console.command.Command
import net.mamoe.mirai.console.command.CommandExecuteResult
import net.mamoe.mirai.console.command.CommandManager
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.descriptor.ExperimentalCommandDescriptors
import net.mamoe.mirai.console.plugin.jvm.JvmPlugin
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.contact.User
import net.mamoe.mirai.message.data.Message
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.content
import net.mamoe.mirai.utils.MiraiLogger
import top.wsure.warframe.WorldState
import top.wsure.warframe.command.WFParamCommand
import top.wsure.warframe.command.WFSimpleCommand
import top.wsure.warframe.data.CommandType
import top.wsure.warframe.data.RemoteCommand
import top.wsure.warframe.enums.DatabaseKey

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

        @ConsoleExperimentalApi
        @ExperimentalCommandDescriptors
        suspend fun handleCommand(sender: CommandSender, message: MessageChain) {

            fun isDebugging(command: Command?): Boolean {
                /*
                if (command?.prefixOptional == false || message.content.startsWith(CommandManager.commandPrefix)) {
                    if (MiraiConsoleImplementationBridge.loggerController.shouldLog("console.debug", SimpleLogger.LogPriority.DEBUG)) {
                        return true
                    }
                }*/
                return false
            }

            when (val result = CommandManager.executeCommand(sender, message)) {
                is CommandExecuteResult.PermissionDenied -> {
                    if (isDebugging(result.command)) {
                        sender.sendMessage("权限不足. ${CommandManager.commandPrefix}${result.command.primaryName} 需要权限 ${result.command.permission.id}.")
                        // intercept()
                    }
                }
                is CommandExecuteResult.IllegalArgument -> {
                    result.exception.message?.let { sender.sendMessage(it) }
                    // intercept()
                }
                is CommandExecuteResult.Success -> {
                    //  intercept()
                }
                is CommandExecuteResult.ExecutionFailed -> {
                    val owner = result.command.owner
                    val (logger, printOwner) = when (owner) {
                        is JvmPlugin -> owner.logger to false
                        else -> MiraiConsole.mainLogger to true
                    }
                    logger.warning(
                        "Exception in executing command `$message`" + if (printOwner) ", command owned by $owner" else "",
                        result.exception
                    )
                    // intercept()
                }
                is CommandExecuteResult.Intercepted -> {
                    if (isDebugging(result.command)) {
                        sender.sendMessage("指令执行被拦截, 原因: ${result.reason}")
                    }
                }
                is CommandExecuteResult.UnmatchedSignature,
                is CommandExecuteResult.UnresolvedCommand,
                -> {
                    // noop
                }
            }

        }

        fun getRemoteCommandHelp(commandList: List<RemoteCommand>): Message {
            return PlainText("warframe-world-state插件功能如下\n${
                commandList.joinToString("\n") { 
                    "${it.name}${if (it.type == CommandType.PARAM) " <搜索词>" else ""}" 
                }
            }\n${DatabaseKey.getHelpMenu()}")
        }

    }
}