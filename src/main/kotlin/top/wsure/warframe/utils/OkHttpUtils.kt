package top.wsure.warframe.utils

import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

/**
 * Copyright (C), 上海维跃信息科技有限公司
 * FileName: OkHttpUtils
 * Author:   wsure
 * Date:     2020/9/24 4:44 下午
 * Description:
 */
class OkHttpUtils {
    companion object {
        private val client = OkHttpClient().newBuilder().readTimeout(60,TimeUnit.SECONDS).build()

        fun doGet(url:String):String?{
            val request = Request.Builder()
                .url(url)
                .get()
                .build()
            val call = client.newCall(request)
            val response = call.execute()
            return response.body?.string();
        }
    }



}