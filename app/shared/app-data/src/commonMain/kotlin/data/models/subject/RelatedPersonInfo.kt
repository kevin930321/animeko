/*
 * Copyright (C) 2024 OpenAni and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license, which can be found at the following link.
 *
 * https://github.com/open-ani/ani/blob/main/LICENSE
 */

package me.him188.ani.app.data.models.subject

import androidx.collection.mutableIntObjectMapOf
import androidx.compose.runtime.Stable
import kotlin.jvm.JvmInline

data class RelatedPersonInfo(
    val index: Int,
    val personInfo: PersonInfo,
    val position: PersonPosition,
) {
    companion object {
        private val SORT_ORDER by lazy {
            listOf(
                PersonPosition.AnimationWork,
                PersonPosition.OriginalWork,
                PersonPosition.Director,
                PersonPosition.Script,
                PersonPosition.Music,
                PersonPosition.CharacterDesign,
                PersonPosition.SeriesComposition,
                PersonPosition.ArtDirection,
                PersonPosition.ActionAnimationDirection,
            )
        }

        val ImportanceOrder = compareBy<RelatedPersonInfo> { info ->
            val index = SORT_ORDER.indexOfFirst { position ->
                info.position == position
            }
            if (index == -1) {
                Int.MAX_VALUE
            } else {
                index
            }
        }
    }
}

// https://github.com/bangumi/server/blob/d4c6bf268ab02e3c69cfe59aee22d4bd120376c4/pkg/vars/staff.go.json#L498
@JvmInline // 节省内存，避免创建过多的子类。
value class PersonPosition(val id: Int) {
    companion object {
        val Invalid = PersonPosition(-1)
        
        /**
         * 原作
         */
        val OriginalWork = PersonPosition(1)

        /**
         * 导演
         */
        val Director = PersonPosition(2)

        /**
         * 脚本
         */
        val Script = PersonPosition(3)

        /**
         * 分镜
         */
        val Storyboard = PersonPosition(4)

        /**
         * 演出
         */
        val EpisodeDirector = PersonPosition(5)

        /**
         * 音乐
         */
        val Music = PersonPosition(6)

        /**
         * 人物原案
         */
        val OriginalCharacterDesign = PersonPosition(7)

        /**
         * 人物设定
         */
        val CharacterDesign = PersonPosition(8)

        /**
         * 分镜构图
         */
        val Layout = PersonPosition(9)

        /**
         * 系列构成
         */
        val SeriesComposition = PersonPosition(10)

        /**
         * 美术监督
         */
        val ArtDirection = PersonPosition(11)

        /**
         * 色彩设计
         */
        val ColorDesign = PersonPosition(13)

        /**
         * 总作画监督
         */
        val ChiefAnimationDirector = PersonPosition(14)

        /**
         * 作画监督
         */
        val AnimationDirection = PersonPosition(15)

        /**
         * 机械设定
         */
        val MechanicalDesign = PersonPosition(16)

        /**
         * 摄影监督
         */
        val DirectorOfPhotography = PersonPosition(17)

        /**
         * 监修
         */
        val Supervision = PersonPosition(18)

        /**
         * 道具设计
         */
        val PropDesign = PersonPosition(19)

        /**
         * 原画
         */
        val KeyAnimation = PersonPosition(20)

        /**
         * 第二原画
         */
        val SecondKeyAnimation = PersonPosition(21)

        /**
         * 动画检查
         */
        val AnimationCheck = PersonPosition(22)

        /**
         * 助理制片人
         */
        val AssistantProducer = PersonPosition(23)

        /**
         * 协同制片人
         */
        val AssociateProducer = PersonPosition(24)

        /**
         * 背景美术
         */
        val BackgroundArt = PersonPosition(25)

        /**
         * 色彩指定
         */
        val ColorSetting = PersonPosition(26)

        /**
         * 数码绘图
         */
        val DigitalPaint = PersonPosition(27)

        /**
         * 剪辑
         */
        val Editing = PersonPosition(28)

        /**
         * 原案
         */
        val OriginalPlan = PersonPosition(29)

        /**
         * 主题歌编曲
         */
        val ThemeSongArrangement = PersonPosition(30)

        /**
         * 主题歌作曲
         */
        val ThemeSongComposition = PersonPosition(31)

        /**
         * 主题歌作词
         */
        val ThemeSongLyrics = PersonPosition(32)

        /**
         * 主题歌演出
         */
        val ThemeSongPerformance = PersonPosition(33)

        /**
         * 插入歌演出
         */
        val InsertedSongPerformance = PersonPosition(34)

        /**
         * 企划
         */
        val Planning = PersonPosition(35)

        /**
         * 企划制片人
         */
        val PlanningProducer = PersonPosition(36)

        /**
         * 制作管理
         */
        val ProductionManager = PersonPosition(37)

        /**
         * 宣传
         */
        val Publicity = PersonPosition(38)

        /**
         * 录音
         */
        val Recording = PersonPosition(39)

        /**
         * 录音助理
         */
        val RecordingAssistant = PersonPosition(40)

        /**
         * 系列监督
         */
        val SeriesProductionDirector = PersonPosition(41)

        /**
         * 制作
         */
        val Production = PersonPosition(42)

        /**
         * 设定
         */
        val Setting = PersonPosition(43)

        /**
         * 音响监督
         */
        val SoundDirector = PersonPosition(44)

        /**
         * 音响
         */
        val Sound = PersonPosition(45)

        /**
         * 音效
         */
        val SoundEffects = PersonPosition(46)

        /**
         * 特效
         */
        val SpecialEffects = PersonPosition(47)

        /**
         * 配音监督
         */
        val ADRDirector = PersonPosition(48)

        /**
         * 联合导演
         */
        val CoDirector = PersonPosition(49)

        /**
         * 背景设定
         */
        val BackgroundSetting = PersonPosition(50)

        /**
         * 补间动画
         */
        val InBetweenAnimation = PersonPosition(51)

        /**
         * 执行制片人
         */
        val ExecutiveProducer = PersonPosition(52)

        /**
         * 助理制片协调
         */
        val AssistantProductionCoordination = PersonPosition(56)

        /**
         * 演员监督
         */
        val CastingDirector = PersonPosition(57)

        /**
         * 总制片
         */
        val ChiefProducer = PersonPosition(58)

        /**
         * 联合制片人
         */
        val CoProducer = PersonPosition(59)

        /**
         * 台词编辑
         */
        val DialogueEditing = PersonPosition(60)

        /**
         * 后期制片协调
         */
        val PostProductionAssistant = PersonPosition(61)

        /**
         * 制作助手
         */
        val ProductionAssistant = PersonPosition(62)

        /**
         * 制作协调
         */
        val ProductionCoordination = PersonPosition(64)

        /**
         * 音乐制作
         */
        val MusicWork = PersonPosition(65)

        /**
         * 友情协力
         */
        val SpecialThanks = PersonPosition(66)

        /**
         * 动画制作
         */
        val AnimationWork = PersonPosition(67)

        /**
         * CG 导演
         */
        val CGDirector = PersonPosition(69)

        /**
         * 机械作画监督
         */
        val MechanicalAnimationDirection = PersonPosition(70)

        /**
         * 美术设计
         */
        val ArtDesign = PersonPosition(71)

        /**
         * 副导演
         */
        val AssistantDirector = PersonPosition(72)

        /**
         * 总导演
         */
        val ChiefDirector = PersonPosition(74)

        /**
         * 3DCG
         */
        val ThreeDCG = PersonPosition(75)

        /**
         * 制作协力
         */
        val WorkAssistance = PersonPosition(76)

        /**
         * 动作作画监督
         */
        val ActionAnimationDirection = PersonPosition(77)

        /**
         * 监制
         */
        val SupervisingProducer = PersonPosition(80)

        /**
         * 协力
         */
        val Assistance = PersonPosition(81)

        /**
         * 摄影
         */
        val Photography = PersonPosition(82)

        /**
         * 制作进行协力
         */
        val AssistantProductionManagerAssistance = PersonPosition(83)

        /**
         * 设定制作
         */
        val DesignManager = PersonPosition(84)

        /**
         * 音乐制作人
         */
        val MusicProducer = PersonPosition(85)

        /**
         * 3DCG 导演
         */
        val ThreeDCGDirector = PersonPosition(86)

        /**
         * 动画制片人
         */
        val AnimationProducer = PersonPosition(87)

        /**
         * 特效作画监督
         */
        val SpecialEffectsAnimationDirection = PersonPosition(88)

        /**
         * 主演出
         */
        val ChiefEpisodeDirection = PersonPosition(89)

        /**
         * 作画监督助理
         */
        val AssistantAnimationDirection = PersonPosition(90)

        /**
         * 演出助理
         */
        val AssistantEpisodeDirection = PersonPosition(91)

        /**
         * 主动画师
         */
        val MainAnimator = PersonPosition(92)

        val entryRange = 1..92

        /**
         * 根据中文名查找职位
         *
         * @return maybe [Invalid]
         */
        fun findByName(name: String): PersonPosition {
            for (i in entryRange) {
                if (PersonPosition(i).nameCn == name) {
                    return PersonPosition(i)
                }
            }
            return Invalid
        }
    }
}

private val nameCnMap by lazy(LazyThreadSafetyMode.PUBLICATION) {
    mutableIntObjectMapOf<String>().apply {
        put(PersonPosition.OriginalWork.id, "原作")
        put(PersonPosition.Director.id, "導演")
        put(PersonPosition.Script.id, "腳本")
        put(PersonPosition.Storyboard.id, "分鏡")
        put(PersonPosition.EpisodeDirector.id, "演出")
        put(PersonPosition.Music.id, "音樂")
        put(PersonPosition.OriginalCharacterDesign.id, "人物原案")
        put(PersonPosition.CharacterDesign.id, "人物設定")
        put(PersonPosition.Layout.id, "分鏡構圖")
        put(PersonPosition.SeriesComposition.id, "系列構成")
        put(PersonPosition.ArtDirection.id, "美術監督")
        put(PersonPosition.ColorDesign.id, "色彩設計")
        put(PersonPosition.ChiefAnimationDirector.id, "總作畫監督")
        put(PersonPosition.AnimationDirection.id, "作畫監督")
        put(PersonPosition.MechanicalDesign.id, "機械設定")
        put(PersonPosition.DirectorOfPhotography.id, "攝影監督")
        put(PersonPosition.Supervision.id, "監修")
        put(PersonPosition.PropDesign.id, "道具設計")
        put(PersonPosition.KeyAnimation.id, "原畫")
        put(PersonPosition.SecondKeyAnimation.id, "第二原畫")
        put(PersonPosition.AnimationCheck.id, "動畫檢查")
        put(PersonPosition.AssistantProducer.id, "助理製片人")
        put(PersonPosition.AssociateProducer.id, "協同製片人")
        put(PersonPosition.BackgroundArt.id, "背景美術")
        put(PersonPosition.ColorSetting.id, "色彩指定")
        put(PersonPosition.DigitalPaint.id, "數位繪圖")
        put(PersonPosition.Editing.id, "剪輯")
        put(PersonPosition.OriginalPlan.id, "原案")
        put(PersonPosition.ThemeSongArrangement.id, "主題曲編曲")
        put(PersonPosition.ThemeSongComposition.id, "主題曲作曲")
        put(PersonPosition.ThemeSongLyrics.id, "主題曲作詞")
        put(PersonPosition.ThemeSongPerformance.id, "主題曲演出")
        put(PersonPosition.InsertedSongPerformance.id, "插入歌演出")
        put(PersonPosition.Planning.id, "企劃")
        put(PersonPosition.PlanningProducer.id, "企劃製片人")
        put(PersonPosition.ProductionManager.id, "製作管理")
        put(PersonPosition.Publicity.id, "宣傳")
        put(PersonPosition.Recording.id, "錄音")
        put(PersonPosition.RecordingAssistant.id, "錄音助理")
        put(PersonPosition.SeriesProductionDirector.id, "系列監督")
        put(PersonPosition.Production.id, "製作")
        put(PersonPosition.Setting.id, "設定")
        put(PersonPosition.SoundDirector.id, "音響監督")
        put(PersonPosition.Sound.id, "音響")
        put(PersonPosition.SoundEffects.id, "音效")
        put(PersonPosition.SpecialEffects.id, "特效")
        put(PersonPosition.ADRDirector.id, "配音監督")
        put(PersonPosition.CoDirector.id, "聯合導演")
        put(PersonPosition.BackgroundSetting.id, "背景設定")
        put(PersonPosition.InBetweenAnimation.id, "補間動畫")
        put(PersonPosition.ExecutiveProducer.id, "執行製片人")
        put(PersonPosition.AssistantProductionCoordination.id, "助理製片協調")
        put(PersonPosition.CastingDirector.id, "演員監督")
        put(PersonPosition.ChiefProducer.id, "總製片")
        put(PersonPosition.CoProducer.id, "聯合製片人")
        put(PersonPosition.DialogueEditing.id, "台詞編輯")
        put(PersonPosition.PostProductionAssistant.id, "後期製片協調")
        put(PersonPosition.ProductionAssistant.id, "製作助手")
        put(PersonPosition.ProductionCoordination.id, "製作協調")
        put(PersonPosition.MusicWork.id, "音樂製作")
        put(PersonPosition.SpecialThanks.id, "友情協力")
        put(PersonPosition.AnimationWork.id, "動畫製作")
        put(PersonPosition.CGDirector.id, "CG 導演")
        put(PersonPosition.MechanicalAnimationDirection.id, "機械作畫監督")
        put(PersonPosition.ArtDesign.id, "美術設計")
        put(PersonPosition.AssistantDirector.id, "副導演")
        put(PersonPosition.ChiefDirector.id, "總導演")
        put(PersonPosition.ThreeDCG.id, "3DCG")
        put(PersonPosition.WorkAssistance.id, "製作協力")
        put(PersonPosition.ActionAnimationDirection.id, "動作作畫監督")
        put(PersonPosition.SupervisingProducer.id, "監製")
        put(PersonPosition.Assistance.id, "協力")
        put(PersonPosition.Photography.id, "攝影")
        put(PersonPosition.AssistantProductionManagerAssistance.id, "製作進行協力")
        put(PersonPosition.DesignManager.id, "設定製作")
        put(PersonPosition.MusicProducer.id, "音樂製作人")
        put(PersonPosition.ThreeDCGDirector.id, "3DCG 導演")
        put(PersonPosition.AnimationProducer.id, "動畫製片人")
        put(PersonPosition.SpecialEffectsAnimationDirection.id, "特效作畫監督")
        put(PersonPosition.ChiefEpisodeDirection.id, "主演出")
        put(PersonPosition.AssistantAnimationDirection.id, "作畫監督助理")
        put(PersonPosition.AssistantEpisodeDirection.id, "演出助理")
        put(PersonPosition.MainAnimator.id, "主動畫師")
    }
}

@Stable
val PersonPosition.nameCn: String?
    get() = nameCnMap[id]
