package top.wsure.warframe.utils

import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.isConsole
import net.mamoe.mirai.console.command.isNotConsole
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.contact.MemberPermission
import net.mamoe.mirai.contact.User
import top.wsure.warframe.data.WorldStateData

/**
 * FileName: PromiseUtils
 * Author:   wsure
 * Date:     2021/4/1 8:49 下午
 * Description:
 */
class PromiseUtils {
    companion object{
        fun isMaster(user: User?): Boolean {
            return !(user != null && !WorldStateData.masters.contains(user.id))
        }

        fun isManage(sender: CommandSender): Boolean {

            return if (sender.isConsole() || isMaster(sender.user)) true
            else {
                val sub = sender.subject!!
                val user = sender.user!!
                if (sub.id != user.id ){
                    val member = sender.user!! as Member
                    member.permission == MemberPermission.ADMINISTRATOR || member.permission == MemberPermission.OWNER
                }
                else false
            }
        }

        fun getGroupId(sender: CommandSender): Long? {
            return if (sender.isNotConsole() && sender.subject!!.id != sender.user!!.id) sender.subject!!.id else null
        }

    }
}