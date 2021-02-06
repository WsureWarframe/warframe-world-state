package top.wsure.warframe.dao

import net.mamoe.mirai.contact.User
import org.ktorm.entity.add
import top.wsure.warframe.WorldState
import top.wsure.warframe.entity.SearchRecordEntity
import top.wsure.warframe.entity.searchRecord
import top.wsure.warframe.utils.MessageUtils
import java.time.LocalDateTime

/**
 * FileName: SearchRecordDao
 * Author:   wsure
 * Date:     2021/2/5 6:00 下午
 * Description:
 */
object SearchRecordDao {

    fun insertRecord(record:SearchRecordEntity){
        WorldState.globalDatabase.searchRecord.add(record)
    }

    fun getSearchRecordEntity(user: User, instruction: MessageUtils.Instruction):SearchRecordEntity{
        return SearchRecordEntity {
            userId = user.id
            nick = user.nick
            keyWord = instruction.keyWord
            param = instruction.param
            url = instruction.url
            createDate = LocalDateTime.now()
            updateDate = LocalDateTime.now()
        }
    }
}