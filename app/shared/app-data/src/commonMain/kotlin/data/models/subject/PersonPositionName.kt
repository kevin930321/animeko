/*
 * Copyright (C) 2026 OpenAni and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license, which can be found at the following link.
 *
 * https://github.com/open-ani/ani/blob/main/LICENSE
 */

package me.him188.ani.app.data.models.subject

import me.him188.ani.app.ui.lang.Lang
import me.him188.ani.app.ui.lang.subject_staff_position_1
import me.him188.ani.app.ui.lang.subject_staff_position_2
import me.him188.ani.app.ui.lang.subject_staff_position_3
import me.him188.ani.app.ui.lang.subject_staff_position_4
import me.him188.ani.app.ui.lang.subject_staff_position_5
import me.him188.ani.app.ui.lang.subject_staff_position_6
import me.him188.ani.app.ui.lang.subject_staff_position_7
import me.him188.ani.app.ui.lang.subject_staff_position_8
import me.him188.ani.app.ui.lang.subject_staff_position_9
import me.him188.ani.app.ui.lang.subject_staff_position_10
import me.him188.ani.app.ui.lang.subject_staff_position_11
import me.him188.ani.app.ui.lang.subject_staff_position_13
import me.him188.ani.app.ui.lang.subject_staff_position_14
import me.him188.ani.app.ui.lang.subject_staff_position_15
import me.him188.ani.app.ui.lang.subject_staff_position_16
import me.him188.ani.app.ui.lang.subject_staff_position_17
import me.him188.ani.app.ui.lang.subject_staff_position_18
import me.him188.ani.app.ui.lang.subject_staff_position_19
import me.him188.ani.app.ui.lang.subject_staff_position_20
import me.him188.ani.app.ui.lang.subject_staff_position_21
import me.him188.ani.app.ui.lang.subject_staff_position_22
import me.him188.ani.app.ui.lang.subject_staff_position_23
import me.him188.ani.app.ui.lang.subject_staff_position_24
import me.him188.ani.app.ui.lang.subject_staff_position_25
import me.him188.ani.app.ui.lang.subject_staff_position_26
import me.him188.ani.app.ui.lang.subject_staff_position_27
import me.him188.ani.app.ui.lang.subject_staff_position_28
import me.him188.ani.app.ui.lang.subject_staff_position_29
import me.him188.ani.app.ui.lang.subject_staff_position_30
import me.him188.ani.app.ui.lang.subject_staff_position_31
import me.him188.ani.app.ui.lang.subject_staff_position_32
import me.him188.ani.app.ui.lang.subject_staff_position_33
import me.him188.ani.app.ui.lang.subject_staff_position_34
import me.him188.ani.app.ui.lang.subject_staff_position_35
import me.him188.ani.app.ui.lang.subject_staff_position_36
import me.him188.ani.app.ui.lang.subject_staff_position_37
import me.him188.ani.app.ui.lang.subject_staff_position_38
import me.him188.ani.app.ui.lang.subject_staff_position_39
import me.him188.ani.app.ui.lang.subject_staff_position_40
import me.him188.ani.app.ui.lang.subject_staff_position_41
import me.him188.ani.app.ui.lang.subject_staff_position_42
import me.him188.ani.app.ui.lang.subject_staff_position_43
import me.him188.ani.app.ui.lang.subject_staff_position_44
import me.him188.ani.app.ui.lang.subject_staff_position_45
import me.him188.ani.app.ui.lang.subject_staff_position_46
import me.him188.ani.app.ui.lang.subject_staff_position_47
import me.him188.ani.app.ui.lang.subject_staff_position_48
import me.him188.ani.app.ui.lang.subject_staff_position_49
import me.him188.ani.app.ui.lang.subject_staff_position_50
import me.him188.ani.app.ui.lang.subject_staff_position_51
import me.him188.ani.app.ui.lang.subject_staff_position_52
import me.him188.ani.app.ui.lang.subject_staff_position_56
import me.him188.ani.app.ui.lang.subject_staff_position_57
import me.him188.ani.app.ui.lang.subject_staff_position_58
import me.him188.ani.app.ui.lang.subject_staff_position_59
import me.him188.ani.app.ui.lang.subject_staff_position_60
import me.him188.ani.app.ui.lang.subject_staff_position_61
import me.him188.ani.app.ui.lang.subject_staff_position_62
import me.him188.ani.app.ui.lang.subject_staff_position_64
import me.him188.ani.app.ui.lang.subject_staff_position_65
import me.him188.ani.app.ui.lang.subject_staff_position_66
import me.him188.ani.app.ui.lang.subject_staff_position_67
import me.him188.ani.app.ui.lang.subject_staff_position_69
import me.him188.ani.app.ui.lang.subject_staff_position_70
import me.him188.ani.app.ui.lang.subject_staff_position_71
import me.him188.ani.app.ui.lang.subject_staff_position_72
import me.him188.ani.app.ui.lang.subject_staff_position_74
import me.him188.ani.app.ui.lang.subject_staff_position_75
import me.him188.ani.app.ui.lang.subject_staff_position_76
import me.him188.ani.app.ui.lang.subject_staff_position_77
import me.him188.ani.app.ui.lang.subject_staff_position_80
import me.him188.ani.app.ui.lang.subject_staff_position_81
import me.him188.ani.app.ui.lang.subject_staff_position_82
import me.him188.ani.app.ui.lang.subject_staff_position_83
import me.him188.ani.app.ui.lang.subject_staff_position_84
import me.him188.ani.app.ui.lang.subject_staff_position_85
import me.him188.ani.app.ui.lang.subject_staff_position_86
import me.him188.ani.app.ui.lang.subject_staff_position_87
import me.him188.ani.app.ui.lang.subject_staff_position_88
import me.him188.ani.app.ui.lang.subject_staff_position_89
import me.him188.ani.app.ui.lang.subject_staff_position_90
import me.him188.ani.app.ui.lang.subject_staff_position_91
import me.him188.ani.app.ui.lang.subject_staff_position_92
import org.jetbrains.compose.resources.StringResource

/**
 * 职位的本地化显示名, 随应用语言切换 (简体中文/繁体中文/粤语/英语).
 *
 * 未知职位返回 `null`, 调用方回退为空字符串.
 */
fun PersonPosition.nameResource(): StringResource? = when (id) {
    1 -> Lang.subject_staff_position_1
    2 -> Lang.subject_staff_position_2
    3 -> Lang.subject_staff_position_3
    4 -> Lang.subject_staff_position_4
    5 -> Lang.subject_staff_position_5
    6 -> Lang.subject_staff_position_6
    7 -> Lang.subject_staff_position_7
    8 -> Lang.subject_staff_position_8
    9 -> Lang.subject_staff_position_9
    10 -> Lang.subject_staff_position_10
    11 -> Lang.subject_staff_position_11
    13 -> Lang.subject_staff_position_13
    14 -> Lang.subject_staff_position_14
    15 -> Lang.subject_staff_position_15
    16 -> Lang.subject_staff_position_16
    17 -> Lang.subject_staff_position_17
    18 -> Lang.subject_staff_position_18
    19 -> Lang.subject_staff_position_19
    20 -> Lang.subject_staff_position_20
    21 -> Lang.subject_staff_position_21
    22 -> Lang.subject_staff_position_22
    23 -> Lang.subject_staff_position_23
    24 -> Lang.subject_staff_position_24
    25 -> Lang.subject_staff_position_25
    26 -> Lang.subject_staff_position_26
    27 -> Lang.subject_staff_position_27
    28 -> Lang.subject_staff_position_28
    29 -> Lang.subject_staff_position_29
    30 -> Lang.subject_staff_position_30
    31 -> Lang.subject_staff_position_31
    32 -> Lang.subject_staff_position_32
    33 -> Lang.subject_staff_position_33
    34 -> Lang.subject_staff_position_34
    35 -> Lang.subject_staff_position_35
    36 -> Lang.subject_staff_position_36
    37 -> Lang.subject_staff_position_37
    38 -> Lang.subject_staff_position_38
    39 -> Lang.subject_staff_position_39
    40 -> Lang.subject_staff_position_40
    41 -> Lang.subject_staff_position_41
    42 -> Lang.subject_staff_position_42
    43 -> Lang.subject_staff_position_43
    44 -> Lang.subject_staff_position_44
    45 -> Lang.subject_staff_position_45
    46 -> Lang.subject_staff_position_46
    47 -> Lang.subject_staff_position_47
    48 -> Lang.subject_staff_position_48
    49 -> Lang.subject_staff_position_49
    50 -> Lang.subject_staff_position_50
    51 -> Lang.subject_staff_position_51
    52 -> Lang.subject_staff_position_52
    56 -> Lang.subject_staff_position_56
    57 -> Lang.subject_staff_position_57
    58 -> Lang.subject_staff_position_58
    59 -> Lang.subject_staff_position_59
    60 -> Lang.subject_staff_position_60
    61 -> Lang.subject_staff_position_61
    62 -> Lang.subject_staff_position_62
    64 -> Lang.subject_staff_position_64
    65 -> Lang.subject_staff_position_65
    66 -> Lang.subject_staff_position_66
    67 -> Lang.subject_staff_position_67
    69 -> Lang.subject_staff_position_69
    70 -> Lang.subject_staff_position_70
    71 -> Lang.subject_staff_position_71
    72 -> Lang.subject_staff_position_72
    74 -> Lang.subject_staff_position_74
    75 -> Lang.subject_staff_position_75
    76 -> Lang.subject_staff_position_76
    77 -> Lang.subject_staff_position_77
    80 -> Lang.subject_staff_position_80
    81 -> Lang.subject_staff_position_81
    82 -> Lang.subject_staff_position_82
    83 -> Lang.subject_staff_position_83
    84 -> Lang.subject_staff_position_84
    85 -> Lang.subject_staff_position_85
    86 -> Lang.subject_staff_position_86
    87 -> Lang.subject_staff_position_87
    88 -> Lang.subject_staff_position_88
    89 -> Lang.subject_staff_position_89
    90 -> Lang.subject_staff_position_90
    91 -> Lang.subject_staff_position_91
    92 -> Lang.subject_staff_position_92
    else -> null
}
