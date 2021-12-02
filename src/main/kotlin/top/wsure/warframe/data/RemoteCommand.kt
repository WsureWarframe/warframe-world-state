package top.wsure.warframe.data

import kotlinx.serialization.Serializable

/**
 * 远程接口对应指令参数类
 *
 * 如果你想在本接口上改造，打造自己的远程指令，参考
 *
 * http://nymph.rbq.life:3000/robot/commands
 *
 * FileName: WFCommand
 * Author:   wsure
 * Date:     2021/3/4 8:21 下午
 * Description:
 */
@Serializable
data class RemoteCommand(
    val name: String,
    val type: CommandType,
    val path: String,
    val alia: String,
    val desc: String,
)

@Serializable
enum class CommandType{
    PARAM,
    SIMPLE
}