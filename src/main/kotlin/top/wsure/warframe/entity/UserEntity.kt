package top.wsure.warframe.entity

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.*
import java.time.LocalDateTime

/**

 * FileName: User
 * Author:   wsure
 * Date:     2021/1/22 12:10 上午
 * Description:user
 */

interface UserEntity : Entity<UserEntity> {
    companion object : Entity.Factory<UserEntity>()

    var id: Long
    var nick: String
    var remark: String
    var avatarUrl: String
    var createDate: LocalDateTime
    var updateDate: LocalDateTime
}

object UserTable : Table<UserEntity>("USER") {

    var id = long("id").primaryKey().bindTo { it.id }
    var nick = varchar("nick").bindTo { it.nick }
    var remark = varchar("remark").bindTo { it.remark }
    var avatarUrl = varchar("avatar_url").bindTo { it.avatarUrl }
    val createDate = datetime("create_date").bindTo { it.createDate }
    val updateDate = datetime("update_date").bindTo { it.updateDate }
}

val Database.user get() = this.sequenceOf(UserTable)