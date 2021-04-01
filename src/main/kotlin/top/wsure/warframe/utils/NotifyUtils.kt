package top.wsure.warframe.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import net.mamoe.mirai.Bot
import net.mamoe.mirai.console.MiraiConsole
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.message.data.Message
import net.mamoe.mirai.utils.MiraiLogger
import top.wsure.warframe.cache.ConstantObject
import top.wsure.warframe.data.WorldStateData
import kotlin.system.measureTimeMillis

class NotifyUtils {
    companion object{
        @ConsoleExperimentalApi
        private val logger: MiraiLogger = MiraiConsole.createLogger(this::class.java.name)

        @ConsoleExperimentalApi
        suspend fun notifyAllGroup(bot:Bot?, message:Message,name:String):Long{
            val useTime = measureTimeMillis {
                withContext(Dispatchers.Default) {
                    if (bot != null) {
                        notifyGroup(bot, message,name)
                    } else {
                        Bot.instances.forEach { notifyGroup(it, message,name) }
                    }
                }
            }
            logger.info("notifyAllGroup 用时:$useTime ms")
            return useTime
        }

        private suspend fun notifyGroup(bot: Bot, message:Message,name:String){
            bot.groups.forEach {
                val enable:Boolean = WorldStateData.groupTaskSetting.getOrDefault(it.id,HashMap()).getOrDefault(name,false)
                if (enable || ConstantObject.INITIATIVE_NOTIFY == name){
                    kotlin.runCatching {
                        it.sendMessage(message)
                    }
                    delay(8000L+(1000 .. 3000).random())
                }
            }
        }

        @ConsoleExperimentalApi
        suspend fun notifyAllMaster(bot:Bot?, message: Message):Long{
            val useTime = measureTimeMillis {
                withContext(Dispatchers.Default) {
                    if (bot != null) {
                        notifyMaster(bot, message)
                    } else {
                        Bot.instances.slice(0..1).forEach { notifyMaster(it, message) }
                    }
                }
            }
            logger.info("notifyAllGroup 用时:$useTime ms")
            return useTime
        }

        private suspend fun notifyMaster(bot: Bot, message:Message){
            WorldStateData.masters.forEach {
                kotlin.runCatching {
                    bot.getStranger(it)?.sendMessage(message)
                }
                delay(3000L+(0 .. 1000).random())
            }
        }
    }
}