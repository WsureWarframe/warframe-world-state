package top.wsure.warframe.utils

import top.wsure.warframe.enums.BeginWithKeyword
import top.wsure.warframe.enums.DatabaseKey
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

        private const val DB_KEY = "db"

        fun getUrlByEnum(messageContent: String):Instruction?{
            val message = messageContent.trim()
            val worldStateKey = WorldStateKey.getByKeyWord(message)
            val beginWithKeyword = BeginWithKeyword.getByStartWith(message)
            return when {
                worldStateKey != null -> Instruction("wf",worldStateKey.name,"${NYMPH_HOST}/wf/robot/${worldStateKey.name}")

                beginWithKeyword != null -> {
                    val param = getParam(beginWithKeyword,message)
                    if(param != null)
                        Instruction(beginWithKeyword.name,param,"${NYMPH_HOST}/${beginWithKeyword.path}/robot/${param}")
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

        fun getDatabaseEnum(message:String):DatabaseKey?{
            var dbKey:DatabaseKey? = null
            if (message.startsWith("$DB_KEY ")){
                val keyword = message.removePrefix("$DB_KEY ").trim()
                dbKey = DatabaseKey.getByKeyWord(keyword)
            }
            return dbKey
        }
    }

    data class Instruction(
        val keyWord:String,
        val param:String,
        val url:String,
    )
}