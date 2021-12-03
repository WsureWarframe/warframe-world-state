package top.wsure.warframe.cache

import kotlinx.serialization.Serializable

/**
 * FileName: CacheValue
 * Author:   wsure
 * Date:     2021/3/29 5:56 下午
 * Description:
 */
@Serializable
data class CacheValue<K>(val key:K,var value:String, val timeout:Long)
