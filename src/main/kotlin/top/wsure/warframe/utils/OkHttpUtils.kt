package top.wsure.warframe.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.mamoe.mirai.utils.MiraiLogger
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStream
import java.util.concurrent.TimeUnit

/**
 * FileName: OkHttpUtils
 * Author:   wsure
 * Date:     2020/9/24 4:44 下午
 * Description:
 */
class OkHttpUtils {
    companion object {
        private val logger: MiraiLogger = MiraiLogger.create(this::class.java.name)

        private val client = OkHttpClient().newBuilder().readTimeout(60,TimeUnit.SECONDS).build()

        fun doGet(url:String):String?{
            val request = Request.Builder()
                .url(url)
                .get()
                .build()
            val call = client.newCall(request)
            val response = call.execute()
            return response.body?.string()
        }

        fun getImage(url:String): InputStream {
            val request = Request.Builder()
                .url(url)
                .get()
                .build()
            val call = client.newCall(request)
            val response = call.execute()
            return response.body?.byteStream()!!
        }

        fun doPost(url: String,body:Any?):String?{
            val mediaType = "application/json; charset=utf-8".toMediaType()
            val reqBody: RequestBody = Json.encodeToString(body).toRequestBody(mediaType)
            val request = Request.Builder()
                .url(url)
                .post(reqBody)
                .build()
            val call = client.newCall(request)
            val response = call.execute()
            return response.body?.string()
        }

        suspend inline fun <reified T> doGetObject(url: String):T{
            return asyncDoGet(url)?.let { Json{ ignoreUnknownKeys = true }.decodeFromString<T>(it) }!!
        }

        suspend fun asyncDoGet(url: String):String?{
            return withContext(Dispatchers.Default){
                doGet(url)
            }
        }

        suspend inline fun <reified T> doPostObject(url: String,body:Any?):T{
            return asyncDoPost(url,body)?.let { Json{ ignoreUnknownKeys = true }.decodeFromString<T>(it) }!!
        }

        suspend fun asyncDoPost(url: String,body:Any?):String?{
            return withContext(Dispatchers.Default){
                doPost(url,body)
            }
        }

    }
}