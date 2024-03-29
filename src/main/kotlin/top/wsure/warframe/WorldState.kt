package top.wsure.warframe

import kotlinx.coroutines.launch
import net.mamoe.mirai.console.command.CommandManager
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.command.CommandSender.Companion.toCommandSender
import net.mamoe.mirai.console.command.descriptor.ExperimentalCommandDescriptors
import net.mamoe.mirai.console.permission.AbstractPermitteeId
import net.mamoe.mirai.console.permission.PermissionService.Companion.permit
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.event.events.MessageEvent
import net.mamoe.mirai.event.globalEventChannel
import net.mamoe.mirai.utils.info
import top.wsure.warframe.cache.ConstantObject
import top.wsure.warframe.command.*
import top.wsure.warframe.data.WorldStateData
import top.wsure.warframe.task.TaskSchedule
import top.wsure.warframe.utils.CommandUtils
import top.wsure.warframe.utils.ScheduleUtils
import java.util.*

object WorldState : KotlinPlugin(
//        @OptIn(ConsoleExperimentalApi::class)
//        JvmPluginDescription.loadFromResource()
    JvmPluginDescription(
        version = "0.0.5",
        id = "top.wsure.warframe.WorldState",
        name = "WarframeWorldState",
    )
) {

    @OptIn(ExperimentalCommandDescriptors::class, ConsoleExperimentalApi::class)
    override fun onEnable() {
        logger.info{"os.name:${System.getProperty("os.name")}"}
        logger.info{"os.arch:${System.getProperty("os.arch")}"}
        logger.info{"os.version:${System.getProperty("os.version")}"}

        launch {
            WorldStateData.reload()

            WorldStateData.commandList = CommandUtils.getRemoteCommand(WorldStateData.host)

            WorldStateData.taskList = CommandUtils.getRemoteTask(WorldStateData.host)

            CommandUtils.initTaskQueue(WorldStateData.taskList)

            CommandUtils.registerAll(WorldStateData.commandList)

            CommandUtils.executeTask(WorldStateData.taskList)

            WFHelp(WorldState,WorldStateData.helpKey).register()

            EditMaster.register()

            EditTask.register()

            ChatCommandEditor.register()

            SendToAllGroup.register()

            AbstractPermitteeId.AnyContact.permit(WorldState.parentPermission)

            ScheduleUtils.loopEvent(TaskSchedule.process, Date(),30*60*1000L,ConstantObject.TASK_HOLDER)

        }

        globalEventChannel().subscribeAlways<MessageEvent> {
            if(WorldStateData.useCM){
                val sender = kotlin.runCatching {
                    this.toCommandSender()
                }.getOrNull() ?: return@subscribeAlways

                launch { // Async
                    runCatching {
                        CommandManager.executeCommand(sender, message)
                    }
                }
            }
        }
    }

    /**
     * Will be invoked when the plugin is disabled
     */
    override fun onDisable() {
        logger.info { "Plugin unloaded" }
    }
}