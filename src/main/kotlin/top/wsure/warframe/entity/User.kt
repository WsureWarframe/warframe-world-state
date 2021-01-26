package top.wsure.warframe.entity

import org.ktorm.schema.Table
import org.ktorm.schema.date
import org.ktorm.schema.long
import org.ktorm.schema.varchar

/**

 * FileName: User
 * Author:   wsure
 * Date:     2021/1/22 12:10 上午
 * Description:user
 */

object User :Table<Nothing>("USER") {

    var id = long("id").primaryKey()
    var nick = varchar("nick")
    var remark = varchar("remark")
    var avatarUrl = varchar("avatar_url")
    val createDate = date("create_date")
    val updateDate = date("update_date")
}
