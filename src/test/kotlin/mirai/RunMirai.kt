package mirai

import kotlinx.coroutines.runBlocking
import net.mamoe.mirai.console.command.CommandManager
import net.mamoe.mirai.console.pure.MiraiConsolePureLoader
import top.wsure.warframe.WorldState
import top.wsure.warframe.enums.BeginWithKeyword
import top.wsure.warframe.enums.WorldStateKey
import top.wsure.warframe.utils.MessageUtils
import top.wsure.warframe.utils.OkHttpUtils

object RunMirai {

    // 执行 gradle task: runMiraiConsole 来自动编译, shadow, 复制, 并启动 pure console.

    @JvmStatic
    fun main(args: Array<String>) {
        // 默认在 /test 目录下运行
//
//        MiraiConsolePureLoader.load(args[0], args[1]) // 启动 console
//
//        runBlocking { CommandManager.join() } // 阻止主线程退出
//        print(OkHttpUtils.doGet(WorldState.NYMPH_HOST +"fissures"))
//        println()
//        print(WorldStateKey.values().joinToString("\n") { e -> e.keyWord })
//        println()
        val messageContent = " wm  x "
        val host = MessageUtils.getUrlByEnum(messageContent)
        if (host!=null)
        print(OkHttpUtils.doGet(host))
    }
}