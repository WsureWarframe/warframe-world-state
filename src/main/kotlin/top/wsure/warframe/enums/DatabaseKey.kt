package top.wsure.warframe.enums

/**
 * FileName: DatabaseKey
 * Author:   wsure
 * Date:     2021/2/2 7:33 下午
 * Description:
 */
enum class DatabaseKey(val keyWord: String) {
    USER_LIST("用户列表"),
//    GROUP_LIST("群列表"),
//    GROUP_TOP("本群排行"),
    KEY_TOP("热门查询"),
//    RECORD_STATISTICAL("插件统计"),
    ;
    companion object {

        fun getByKeyWord(key:String): DatabaseKey? {
            return values().firstOrNull { e -> e.keyWord == key }
        }

        fun getHelpMenu():String {
            return values().joinToString("\n") { e -> "db ${e.keyWord}" }
        }
    }
}