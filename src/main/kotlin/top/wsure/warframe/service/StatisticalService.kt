package top.wsure.warframe.service

import net.mamoe.mirai.event.events.MessageEvent
import net.mamoe.mirai.message.data.MessageChainBuilder
import net.mamoe.mirai.message.data.PlainText
import org.ktorm.dsl.*
import top.wsure.warframe.WorldState
import top.wsure.warframe.entity.SearchRecordTable
import top.wsure.warframe.enums.DatabaseKey
import top.wsure.warframe.enums.WorldStateKey
import java.time.LocalDateTime

/**
 * FileName: StatisticalService
 * Author:   wsure
 * Date:     2021/2/6 5:11 下午
 * Description:
 */
object StatisticalService {

    private const val COUNT_COLUMN:String = "count_"

    suspend fun queryKeyTop(event: MessageEvent) {
        event.subject.sendMessage(
            MessageChainBuilder()
            .append(PlainText("${DatabaseKey.KEY_TOP.keyWord}(Top5)\n功能\t参数\t次数\n"))
            .append(PlainText(queryTopSearch(null, null, null).joinToString("\n") {
                    "${it.keyWord}\t${
                        if (it.keyWord.equals("wf")) WorldStateKey.valueOf(it.param!!).keyWord else it.param
                    }\t${it.count}"
                }))
            .build()
        )
    }

    fun queryTopSearch(groupId: Long?, start: LocalDateTime?, end: LocalDateTime?): List<TopSearchBo> {
        val query = WorldState.globalDatabase
            .from(SearchRecordTable)
            .select(SearchRecordTable.keyWord, SearchRecordTable.param, count().aliased(COUNT_COLUMN))
            .whereWithConditions {
                if (groupId != null) {
                    it += SearchRecordTable.groupId eq groupId
                }
                if (start != null && end != null) {
                    it += SearchRecordTable.createDate between start..end
                }
            }
            .groupBy(SearchRecordTable.keyWord, SearchRecordTable.param)
            .orderBy(count().desc())
            .limit(0,5)

        return query.map {
            TopSearchBo(
                it[SearchRecordTable.keyWord],
                it[SearchRecordTable.param],
                it.getInt(COUNT_COLUMN)
            )
        }
    }

    data class TopSearchBo(
        val keyWord: String?,
        val param: String?,
        val count: Int
    )
}