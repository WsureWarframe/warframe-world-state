package top.wsure.warframe.enums

/**
 * FileName: BeginWithKeyword
 * Author:   wsure
 * Date:     2020/9/25 9:29 上午
 * Description:
 */
enum class BeginWithKeyword(val path:String) {
    wm("wm"),
    rm("rm"),
    wiki("wiki"),
    tran("dict/tran"),
    ;
    companion object {
        fun getByStartWith(message:String):BeginWithKeyword ?{
            return values().firstOrNull { keyword -> message.startsWith(keyword.name) }
        }
        fun getHelpMenu():String {
            return values().joinToString("\n") { e -> "${e.name} 搜索词" }
        }
    }
}