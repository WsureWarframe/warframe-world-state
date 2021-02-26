package top.wsure.warframe.entity

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.jodatime.datetime

/**

 * FileName: User
 * Author:   wsure
 * Date:     2021/1/22 12:10 上午
 * Description:user
 */

class UserEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<UserEntity>(UserTable)

    var nick by UserTable.nick
    var remark by UserTable.remark
    var avatarUrl by UserTable.avatarUrl
    var createDate by UserTable.createDate
    var updateDate by UserTable.updateDate
}

object UserTable : LongIdTable("USER") {
    var nick = text("nick").nullable()
    var remark = text("remark").nullable()
    var avatarUrl = text("avatar_url").nullable()
    val createDate = datetime("create_date")
    val updateDate = datetime("update_date")
}
