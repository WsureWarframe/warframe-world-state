package top.wsure.warframe.utils

import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import org.ktorm.logging.Logger

/**
 * FileName: MiraiLogger
 * Author:   wsure
 * Date:     2021/2/6 7:26 下午
 * Description:
 */
class MiraiLogger(private val plugin: KotlinPlugin) : Logger {
    override fun debug(msg: String, e: Throwable?) {
        plugin.logger.debug(msg,e)
    }

    override fun error(msg: String, e: Throwable?) {
        plugin.logger.error(msg,e)
    }

    override fun info(msg: String, e: Throwable?) {
        plugin.logger.info(msg,e)
    }

    override fun isDebugEnabled(): Boolean {
        return plugin.logger.isEnabled
    }

    override fun isErrorEnabled(): Boolean {
        return plugin.logger.isEnabled
    }

    override fun isInfoEnabled(): Boolean {
        return plugin.logger.isEnabled
    }

    override fun isTraceEnabled(): Boolean {
        return plugin.logger.isEnabled
    }

    override fun isWarnEnabled(): Boolean {
        return plugin.logger.isEnabled
    }

    override fun trace(msg: String, e: Throwable?) {
        plugin.logger.debug("[trace] - $msg",e)
    }

    override fun warn(msg: String, e: Throwable?) {
        plugin.logger.warning(msg,e)
    }
}