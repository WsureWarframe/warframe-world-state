package top.wsure.warframe.entity

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.jodatime.datetime
import org.joda.time.DateTime

/**
 * FileName: SearchRecordEntity
 * Author:   wsure
 * Date:     2021/2/5 5:51 下午
 * Description:
 */
class SearchRecordEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<SearchRecordEntity>(SearchRecordTable)

    var userId by SearchRecordTable.userId
    var groupId by SearchRecordTable.groupId
    var nick by SearchRecordTable.nick
    var keyWord by SearchRecordTable.keyWord
    var param by SearchRecordTable.param
    var url by SearchRecordTable.url
    var createDate by SearchRecordTable.createDate
    var updateDate by SearchRecordTable.updateDate
}

object SearchRecordTable : LongIdTable("SEARCH_RECORD") {

    var userId = long("user_id")
    var groupId = long("group_id").nullable()
    var nick = text("nick").nullable()
    var keyWord = text("key_word").nullable()
    var param = text("param").nullable()
    var url = text("url").nullable()
    val createDate = datetime("create_date").default(DateTime.now())
    val updateDate = datetime("update_date").default(DateTime.now())
}