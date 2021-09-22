package com.myapp.composesample.ui.left

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.myapp.composesample.ui.MainActivity
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * テキスト関連画面 画面仕様
 *
 */
@RunWith(AndroidJUnit4::class)
class TextGroupScreenKtTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            PreviewTextGroupScreen()
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
        composeTestRule.onNodeWithTag(TextGroupScreenTag.TextTitle.value)
            .assert(hasText("Text 一覧"))
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(TextGroupScreenTag.TextTitleNotChange.value)
            .assert(hasText("変更不可能なテキストフィールド"))
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(TextGroupScreenTag.TextTitleNotChangeMaterial.value)
            .assert(hasText("変更不可能なマテリアルフィールド"))
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(TextGroupScreenTag.TextTitleChange.value)
            .assert(hasText("変更可能なテキストフィールド"))
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(TextGroupScreenTag.TextTitleChangeMaterial1.value)
            .assert(hasText("変更可能なマテリアルフィールド"))
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(TextGroupScreenTag.TextTitleChangeMaterial2.value)
            .assert(hasText("変更を動的反映"))
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(TextGroupScreenTag.EditNotChange.value)
            .assert(hasText("キョン"))
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(TextGroupScreenTag.EditNotChangeMaterial.value)
            .assert(hasText("text"))
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(TextGroupScreenTag.EditChange.value)
            .assert(hasText("キョン"))
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(TextGroupScreenTag.EditChangeMaterial1.value)
            .assert(hasText("Hello"))
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(TextGroupScreenTag.EditChangeMaterial2.value)
            .assert(hasText("動的変更"))
            .assertIsDisplayed()
    }
}