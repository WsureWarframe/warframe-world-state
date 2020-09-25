package top.wsure.warframe.utils

import net.mamoe.mirai.message.data.MessageContent
import top.wsure.warframe.enums.BeginWithKeyword
import top.wsure.warframe.enums.WorldStateKey

/**
 * FileName: MessageUtils
 * Author:   wsure
 * Date:     2020/9/25 9:59 上午
 * Description:
 */
class MessageUtils {
    companion object {
        private const val NYMPH_HOST = "http://nymph.rbq.life:3000"
        fun getUrlByEnum(messageContent: String):String?{
            val message = messageContent.trim()
            val worldStateKey = WorldStateKey.getByKeyWord(message)
            val beginWithKeyword = BeginWithKeyword.getByStartWith(message)
            return when {
                worldStateKey != null -> "${NYMPH_HOST}/wf/robot/${worldStateKey.name}"

                (beginWithKeyword != null )  -> {
                    val param = getParam(beginWithKeyword,message)
                    if(param != null)
                        "${NYMPH_HOST}/${beginWithKeyword.name}/robot/${param}"
                    else
                        null
                }

                else -> null
            }
        }

        private fun getParam(beginWithKeyword: BeginWithKeyword, message:String):String?{
            val param = message.removePrefix(beginWithKeyword.name).trim()
            return when {
                param.isBlank() -> null
                else -> param
            }
        }
    }
}