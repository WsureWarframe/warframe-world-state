package top.wsure.warframe.dao

import net.mamoe.mirai.contact.Group
import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.find
import top.wsure.warframe.WorldState
import top.wsure.warframe.entity.GroupEntity
import top.wsure.warframe.entity.group
import java.time.LocalDateTime

/**
 * FileName: GroupService
 * Author:   wsure
 * Date:     2021/2/5 4:17 下午
 * Description:
 */
object GroupDao {
    fun insertGroup(group:Group){
//        try {
            val exist = WorldState.globalDatabase.group.find { it.id eq group.id}
            if(exist == null){
                WorldState.globalDatabase.group.add(getGroupEntity(group))
            } else {
                updateGroupEntity(group, exist).flushChanges()
            }
//        }catch (e:Exception){
//            WorldState.logger.warning(e.message,e)
//        }
    }

    private fun updateGroupEntity(group: Group, exist: GroupEntity): GroupEntity {
        exist.name = group.name
        exist.remark = group.owner.remark
        exist.avatarUrl = group.avatarUrl
        exist.updateDate = LocalDateTime.now()
        return exist
    }

    private fun getGroupEntity(group: Group): GroupEntity {
        return GroupEntity {
            id = group.id
            name = group.name
            remark = group.owner.remark
            avatarUrl = group.avatarUrl
            createDate = LocalDateTime.now()
            updateDate = LocalDateTime.now()
        }
    }
}