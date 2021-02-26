package top.wsure.warframe.entity

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.jodatime.datetime

/**
 * FileName: GroupEntity
 * Author:   wsure
 * Date:     2021/1/29 11:30 上午
 * Description:
 */
class GroupEntity(id:EntityID<Long>) :LongEntity(id) {
    companion object : LongEntityClass<GroupEntity>(GroupTable)
    var name by GroupTable.name
    var remark by GroupTable.remark
    var avatarUrl by GroupTable.avatarUrl
    var createDate by GroupTable.createDate
    var updateDate by GroupTable.updateDate
}

object GroupTable :LongIdTable("QQ_GROUP") {
    var name = text("name").nullable()
    var remark = text("remark").nullable()
    var avatarUrl = text("avatar_url").nullable()
    val createDate = datetime("create_date")
    val updateDate = datetime("update_date")
}