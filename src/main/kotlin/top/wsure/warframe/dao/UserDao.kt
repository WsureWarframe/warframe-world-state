package top.wsure.warframe.dao

import net.mamoe.mirai.contact.Contact
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.contact.User
import net.mamoe.mirai.event.events.MessageEvent
import net.mamoe.mirai.message.data.Message
import net.mamoe.mirai.message.data.MessageChainBuilder
import net.mamoe.mirai.message.data.PlainText
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import top.wsure.warframe.WorldState
import top.wsure.warframe.entity.UserEntity
import top.wsure.warframe.entity.UserTable
import top.wsure.warframe.utils.DBUtils

/**
 * FileName: UserService
 * Author:   wsure
 * Date:     2021/2/2 2:47 下午
 * Description:
 */
object UserDao {
    fun saveUser(user: User) {
        val exist = UserEntity.findById(user.id)
        if (exist == null) {
            insert(user)
        } else {
            updateUserEntity(user, exist)
        }
    }

    fun userList(user: User?) :Message{
            return MessageChainBuilder()
                .append(PlainText("用户列表\n"))
                .append(PlainText(users(user).joinToString("\n")))
                .build()
    }

    private fun users(user: User?): List<String> {
        return transaction(DBUtils.getDatabase(WorldState.DB_FILE)) {
            addLogger(StdOutSqlLogger)
            UserTable.select {
                if (user is Member)
                    UserTable.id.inList(user.group.members.map { it.id })
                else
                    UserTable.id.isNotNull()
            }.map { "[${it[UserTable.id]}]: ${it[UserTable.nick]}" }
        }
    }

    private fun insert(user: User): UserEntity {
        return UserEntity.new(user.id) {
            nick = user.nick
            remark = user.remark
            avatarUrl = user.avatarUrl
            createDate = DateTime.now()
            updateDate = DateTime.now()
        }
    }

    private fun updateUserEntity(user: User, exist: UserEntity): UserEntity {

        exist.nick = user.nick
        exist.remark = user.remark
        exist.avatarUrl = user.avatarUrl
        exist.updateDate = DateTime.now()
        return exist
    }

}