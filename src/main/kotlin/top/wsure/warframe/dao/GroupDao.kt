package top.wsure.warframe.dao

import net.mamoe.mirai.contact.Group
import org.joda.time.DateTime
import top.wsure.warframe.entity.GroupEntity

/**
 * FileName: GroupService
 * Author:   wsure
 * Date:     2021/2/5 4:17 下午
 * Description:
 */
object GroupDao {
    fun saveGroup(group:Group){
        val exist = GroupEntity.findById(group.id)
        if(exist == null){
            insert(group)
        } else {
            updateGroupEntity(group, exist)
        }
    }

    private fun updateGroupEntity(group: Group, exist: GroupEntity): GroupEntity {
        exist.name = group.name
        exist.remark = group.owner.remark
        exist.avatarUrl = group.avatarUrl
        exist.updateDate = DateTime.now()
        return exist
    }

    private fun insert(group: Group): GroupEntity {
        return GroupEntity.new(group.id) {
            name = group.name
            remark = group.owner.remark
            avatarUrl = group.avatarUrl
            createDate = DateTime.now()
            updateDate = DateTime.now()
        }
    }
}