package top.wsure.warframe.cache

import kotlinx.serialization.Serializable

/**
 * Copyright (C), 上海维跃信息科技有限公司
 * FileName: CacheValue
 * Author:   wsure
 * Date:     2021/3/29 5:56 下午
 * Description:
 */
@Serializable
data class CacheValue<K,V :java.io.Serializable>(val key:K,val value:V,val timeout:Long)
