package top.wsure.warframe.dao

import net.mamoe.mirai.contact.User
import org.joda.time.DateTime
import top.wsure.warframe.entity.SearchRecordEntity
import top.wsure.warframe.utils.MessageUtils

/**
 * FileName: SearchRecordDao
 * Author:   wsure
 * Date:     2021/2/5 6:00 下午
 * Description:
 */
object SearchRecordDao {

    fun insert(user: User, instruction: MessageUtils.Instruction):SearchRecordEntity{
        return SearchRecordEntity.new {
            userId = user.id
            nick = user.nick
            keyWord = instruction.keyWord
            param = instruction.param
            url = instruction.url
            createDate = DateTime.now()
            updateDate = DateTime.now()
        }
    }
}