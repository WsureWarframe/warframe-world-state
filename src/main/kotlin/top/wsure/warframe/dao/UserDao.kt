package top.wsure.warframe.dao

import net.mamoe.mirai.console.command.CommandSender.Companion.asMemberCommandSender
import net.mamoe.mirai.contact.Contact.Companion.uploadImage
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.contact.User
import net.mamoe.mirai.event.events.MessageEvent
import net.mamoe.mirai.message.data.Image
import net.mamoe.mirai.message.data.MessageChainBuilder
import net.mamoe.mirai.message.data.PlainText
import org.ktorm.dsl.*
import org.ktorm.entity.add
import org.ktorm.entity.filter
import org.ktorm.entity.find
import org.ktorm.entity.toList
import top.wsure.warframe.WorldState
import top.wsure.warframe.WorldState.globalDatabase
import top.wsure.warframe.WorldState.logger
import top.wsure.warframe.entity.UserEntity
import top.wsure.warframe.entity.user
import top.wsure.warframe.utils.OkHttpUtils
import java.time.LocalDateTime

/**
 * FileName: UserService
 * Author:   wsure
 * Date:     2021/2/2 2:47 下午
 * Description:
 */
object UserDao {
    fun insertUser(user: User) {
        try {
            val exist = globalDatabase.user.find { it.id eq user.id }
            if (exist == null) {
                globalDatabase.user.add(getUserEntity(user))
            } else {
                updateUserEntity(user, exist).flushChanges()
            }
        } catch (e: Exception) {
            WorldState.logger.error(e.stackTraceToString())
        }
    }

    suspend fun userList(event: MessageEvent) {
        val user = event.sender
        event.subject.sendMessage(MessageChainBuilder()
            .append(PlainText("用户列表\n"))
            .append(PlainText(globalDatabase.user
                .filter { it ->
                    if (user is Member)
                        it.id.inList(user.group.members.map { it.id })
                    else
                        it.id.isNotNull()
                }
                .toList()
                .joinToString("\n") { "[${it.id}]: ${it.nick}" }
            ))
            .build()
        )
        /*
            .forEach {
                logger.info(it.toString())
                messageChain.append(PlainText("[${it.id}]:${it.nick}\n"))
                    .append(Image(event.subject.uploadImage(OkHttpUtils.getImage(it.avatarUrl)).imageId))
                    .append(PlainText("\n"))
            }
         */
    }


    private fun getUserEntity(user: User): UserEntity {
        return UserEntity {
            id = user.id
            nick = user.nick
            remark = user.remark
            avatarUrl = user.avatarUrl
            createDate = LocalDateTime.now()
            updateDate = LocalDateTime.now()
        }
    }

    private fun updateUserEntity(user: User, exist: UserEntity): UserEntity {
        exist.nick = user.nick
        exist.remark = user.remark
        exist.avatarUrl = user.avatarUrl
        exist.updateDate = LocalDateTime.now()
        return exist
    }

}