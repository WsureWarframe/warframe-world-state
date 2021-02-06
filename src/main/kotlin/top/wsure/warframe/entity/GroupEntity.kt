package top.wsure.warframe.entity

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.*
import java.time.LocalDateTime

/**
 * FileName: GroupEntity
 * Author:   wsure
 * Date:     2021/1/29 11:30 上午
 * Description:
 */
interface GroupEntity : Entity<GroupEntity> {
    companion object : Entity.Factory<GroupEntity>()
    var id:Long
    var name:String
    var remark:String
    var avatarUrl:String
    var createDate: LocalDateTime
    var updateDate: LocalDateTime
}

object GroupTable :Table<GroupEntity>("GROUP") {

    var id = long("id").primaryKey().bindTo { it.id }
    var name = varchar("name").bindTo { it.name }
    var remark = varchar("remark").bindTo { it.remark }
    var avatarUrl = varchar("avatar_url").bindTo { it.avatarUrl }
    val createDate = datetime("create_date").bindTo { it.createDate }
    val updateDate = datetime("update_date").bindTo { it.updateDate }
}
val Database.group get() = this.sequenceOf(UserTable)