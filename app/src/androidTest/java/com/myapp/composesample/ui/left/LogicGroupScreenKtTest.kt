package com.myapp.composesample.ui.left

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.myapp.composesample.ui.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * ロジック関連画面 画面仕様
 *
 */
@RunWith(AndroidJUnit4::class)
class LogicGroupScreenKtTest{

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            PreviewLogicScreen()
        }
    }

    /**
     * 初期表示
     *
     * 条件：なし
     * 期待結果：各項目が期待通り表示されていること
     */
    @Test
    fun initTest() {
        composeTestRule.onNodeWithTag(LogicGroupScreenTag.TextInitCounterTitle.value)
            .assert(hasText("画面描画時非同期取得"))
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(LogicGroupScreenTag.TextInitCounter.value)
            .assert(hasText("10000000"))
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(LogicGroupScreenTag.TextCounterTitle.value)
            .assert(hasText("カウントアップ"))
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(LogicGroupScreenTag.ButtonMinus.value)
            .assert(hasText("-"))
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(LogicGroupScreenTag.ButtonPlus.value)
            .assert(hasText("+"))
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(LogicGroupScreenTag.TextCounter.value)
            .assert(hasText("0"))
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(LogicGroupScreenTag.TextCopyTextTitle.value)
            .assert(hasText("テキスト変更"))
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(LogicGroupScreenTag.EditText.value)
            .assert(hasText(""))
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(LogicGroupScreenTag.TextCopyText.value)
            .assert(hasText(""))
            .assertIsNotDisplayed()
    }

    /**
     * イベント処理
     *
     * ＜ケース１＞
     * 動作　　：＋ボタン押下
     * 期待結果：カウンターが１増加
     * ＜ケース２＞
     * 期待結果：ーボタン押下
     * 動作　　：カウンターが１減少
     * ＜ケース３＞
     * 動作　　：テキスト変更
     * 期待結果：反映用のTextViewに入力した文字列が反映される
     *
     */
    @Test
    fun actionTest() {
        // +-ボタン
        composeTestRule.onNodeWithTag(LogicGroupScreenTag.ButtonPlus.value).performClick()
        composeTestRule.onNodeWithTag(LogicGroupScreenTag.TextCounter.value).assert(hasText("1"))
        composeTestRule.onNodeWithTag(LogicGroupScreenTag.ButtonMinus.value).performClick()
        composeTestRule.onNodeWithTag(LogicGroupScreenTag.TextCounter.value).assert(hasText("0"))
        // テキスト変更
        composeTestRule.onNodeWithTag(LogicGroupScreenTag.EditText.value).performTextInput("android Test")
        composeTestRule.onNodeWithTag(LogicGroupScreenTag.TextCopyText.value).assert(hasText("android Test"))
    }
}