package top.wsure.warframe.data

import kotlinx.serialization.Serializable

/**
 * FileName: RemoteQueue
 * Author:   wsure
 * Date:     2021/3/30 10:23 上午
 * Description:
 */
@Serializable
data class RemoteQueue(val key:String,val timeout:Long) : java.io.Serializable
