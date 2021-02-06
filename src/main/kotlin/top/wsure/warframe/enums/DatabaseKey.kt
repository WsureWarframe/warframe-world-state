package top.wsure.warframe.enums

/**
 * FileName: DatabaseKey
 * Author:   wsure
 * Date:     2021/2/2 7:33 下午
 * Description:
 */
enum class DatabaseKey(val keyWord: String) {
    USER_LIST("用户列表")
    ;
    companion object {

        fun getByKeyWord(key:String): DatabaseKey? {
            return values().firstOrNull { e -> e.keyWord == key }
        }

        fun getHelpMenu():String {
            return values().joinToString("\n") { e -> e.keyWord }
        }
    }
}