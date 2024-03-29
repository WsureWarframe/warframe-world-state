package top.wsure.warframe.utils

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import net.mamoe.mirai.utils.MiraiLogger
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object JsonUtils {
    val logger: MiraiLogger = MiraiLogger.Factory.create(this::class)
    @OptIn(ExperimentalSerializationApi::class)
    val formatter = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        // 等mirai 更新json依赖
//        explicitNulls = false
    }

    @OptIn(ExperimentalSerializationApi::class)
    inline fun <reified T : Any> T.objectToJson(): String {
        return formatter.encodeToString(this)
    }

    @OptIn(ExperimentalSerializationApi::class)
    inline fun <reified T> String.jsonToObject(): T {
        return formatter.decodeFromString(this)
    }

    @OptIn(ExperimentalSerializationApi::class)
    inline fun <reified T> String.jsonToObjectOrNull(failureReason:Boolean = true): T? {
        return kotlin.runCatching { formatter.decodeFromString<T>(this) }
            .onFailure {
                if(failureReason) {
                    logger.warning("format string to json failed !! string :$this", it)
                }
            }.getOrNull()
    }

    fun String.toJsonElement(): JsonElement {
        return formatter.parseToJsonElement(this)
    }

    inline fun <reified T> JsonElement.jsonToObject(): T {
        return formatter.decodeFromJsonElement(this)
    }

    @OptIn(ExperimentalSerializationApi::class)
    inline fun <reified T> JsonElement.jsonToObjectOrNull(failureReason:Boolean = true): T? {
        return kotlin.runCatching { formatter.decodeFromJsonElement<T>(this) }
            .onFailure {
                if(failureReason) {
                    logger.warning("format string to json failed !! string :$this", it)
                }
            }.getOrNull()
    }
}