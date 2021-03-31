package top.wsure.warframe.data

import kotlinx.serialization.Serializable

/**
 * FileName: RemoteTask
 * Author:   wsure
 * Date:     2021/3/29 4:16 下午
 * Description:
 */
@Serializable
data class RemoteTask(
    val name:String,
    val period: Long,
    val queuePath:String,
    val infoPath:String,
    val type:TaskEnum
    )

enum class TaskEnum{
    NOTIFY_GROUP,
    NOTIFY_MASTER,
    REFRESH,
    PULL,
    RELOAD,
    CANCEL,
}