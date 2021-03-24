package top.wsure.warframe.data

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.value

/**
 * FileName: WorldStateData
 * Author:   wsure
 * Date:     2021/3/4 8:15 下午
 * Description:
 */
object WorldStateData : AutoSavePluginConfig("WorldStateData") {

    var useCM: Boolean by value(true)
    //api host 默认使用自身的，可自己改
    var host: String by value("http://nymph.rbq.life:3000")

    //help key
    var helpKey: String by value("菜单")

    var commandList: List<RemoteCommand> by value(emptyList())
}