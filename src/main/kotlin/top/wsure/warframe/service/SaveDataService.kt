package top.wsure.warframe.service

import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.contact.User
import top.wsure.warframe.dao.GroupDao
import top.wsure.warframe.dao.SearchRecordDao
import top.wsure.warframe.dao.UserDao
import top.wsure.warframe.utils.MessageUtils

/**
 * FileName: SaveDataService
 * Author:   wsure
 * Date:     2021/2/5 5:43 下午
 * Description:
 */
object SaveDataService {

    fun storage(user: User, instruction: MessageUtils.Instruction) {
        UserDao.insertUser(user)
        val record = SearchRecordDao.getSearchRecordEntity(user, instruction)
        if (user is Member) {
            val group: Group = user.group
            record.groupId = group.id
            GroupDao.insertGroup(group)
        }
        SearchRecordDao.insertRecord(record)
    }
}