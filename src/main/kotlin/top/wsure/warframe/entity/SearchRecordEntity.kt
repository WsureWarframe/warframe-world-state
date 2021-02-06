package top.wsure.warframe.entity

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.datetime
import org.ktorm.schema.long
import org.ktorm.schema.varchar
import java.time.LocalDateTime

/**
 * FileName: SearchRecordEntity
 * Author:   wsure
 * Date:     2021/2/5 5:51 下午
 * Description:
 */
interface SearchRecordEntity : Entity<SearchRecordEntity> {
    companion object : Entity.Factory<SearchRecordEntity>()

    var userId: Long
    var groupId: Long
    var nick: String
    var keyWord: String
    var param: String
    var url: String
    var createDate: LocalDateTime
    var updateDate: LocalDateTime
}

object SearchRecordTable : Table<SearchRecordEntity>("SEARCH_RECORD") {

    var userId = long("user_id").bindTo { it.userId }
    var groupId = long("group_id").bindTo { it.groupId }
    var nick = varchar("nick").bindTo { it.nick }
    var keyWord = varchar("key_word").bindTo { it.keyWord }
    var param = varchar("param").bindTo { it.param }
    var url = varchar("url").bindTo { it.url }
    val createDate = datetime("create_date").bindTo { it.createDate }
    val updateDate = datetime("update_date").bindTo { it.updateDate }
}

val Database.searchRecord get() = this.sequenceOf(SearchRecordTable)