package mirai

import net.mamoe.mirai.console.MiraiConsole
import net.mamoe.mirai.console.plugin.PluginManager.INSTANCE.enable
import net.mamoe.mirai.console.plugin.PluginManager.INSTANCE.load
import net.mamoe.mirai.console.terminal.MiraiConsoleTerminalLoader
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import top.wsure.warframe.WorldState
import top.wsure.warframe.cache.CacheValue
import top.wsure.warframe.data.WorldStateData
import top.wsure.warframe.utils.ScheduleUtils
import java.util.*


@ConsoleExperimentalApi
suspend fun main() {

    MiraiConsoleTerminalLoader.startAsDaemon()
    WorldState.load()
    WorldState.enable()
    println("System.nanoTime(): ->"+System.nanoTime()+" .")
    MiraiConsole.job.join()
}