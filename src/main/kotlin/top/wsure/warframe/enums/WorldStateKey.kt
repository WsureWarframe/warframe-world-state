package top.wsure.warframe.enums

import java.util.*
import java.util.stream.Stream

/**
 * Copyright (C), 上海维跃信息科技有限公司
 * FileName: WorldStateKey
 * Author:   wsure
 * Date:     2020/9/24 10:26 上午
 * Description:世界状态
 */

enum class WorldStateKey(val keyWord: String) {
    news("新闻"),
    events("事件"),
    
    alerts("警报"),
    
    sortie("突击"),

    Ostrons("地球赏金"),
    Solaris("金星赏金"),
    EntratiSyndicate("火卫二赏金"),

    fissures("裂缝"),
    
    flashSales("促销商品"),
    
    invasions("入侵"),
    
    voidTrader("奸商"),
    
    dailyDeals("特价"),

    persistentEnemies("小小黑"),

    earthCycle("地球"),

    cetusCycle("地球平原"),

    cambionCycle("火卫二平原"),

    vallisCycle("金星平原"),
    
    nightwave("电波"),
    
    arbitration("仲裁"),
    ;

    companion object {
        fun getByKeyWord(key:String): WorldStateKey? {
            return values().firstOrNull { e -> e.keyWord == key }
        }
    }

}