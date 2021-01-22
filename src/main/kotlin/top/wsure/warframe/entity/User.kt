package top.wsure.warframe.entity

import com.baomidou.mybatisplus.annotation.TableId
import java.util.*

/**
 * Copyright (C), 上海维跃信息科技有限公司
 * FileName: User
 * Author:   wsure
 * Date:     2021/1/22 12:10 上午
 * Description:user
 */

data class User (
    @TableId
    var id: Long? = null,
    var nick: String? = null,
    var remark: String? = null,
    var avatarUrl: String? = null,
    val createDate: Date? = null,
    val updateDate: Date? = null,
)
