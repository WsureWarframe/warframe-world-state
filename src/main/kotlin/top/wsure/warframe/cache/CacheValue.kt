package top.wsure.warframe.cache

import kotlinx.serialization.Serializable

/**
 * FileName: CacheValue
 * Author:   wsure
 * Date:     2021/3/29 5:56 下午
 * Description:
 */
@Serializable
data class CacheValue<K,V :java.io.Serializable>(val key:K,val value:V,val timeout:Long)
