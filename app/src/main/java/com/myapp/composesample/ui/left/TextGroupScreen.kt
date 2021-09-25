package com.myapp.composesample.ui.left

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myapp.composesample.util.base.LayoutTag

/**
 * テキスト関連画面
 *
 */
@Composable
fun TextGroupScreen(textGroupViewModel: TextGroupViewModel) {

    // state
    val state = textGroupViewModel.state.value

    // event
    val changeText1: (String) -> Unit = {
        textGroupViewModel.setEvent(TextContract.Event.ChangeTxt1(it))
    }
    val changeText2: (String) -> Unit = {
        textGroupViewModel.setEvent(TextContract.Event.ChangeTxt2(it))
    }
    val changeText3: (String) -> Unit = {
        textGroupViewModel.setEvent(TextContract.Event.ChangeTxt3(it))
    }

    // 画面描画
    Scaffold(backgroundColor = Color(0xfff5f5f5)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            TextScreenMainContent(
                state.text1,
                state.text2,
                state.text3,
                state.textResult,
                changeText1,
                changeText2,
                changeText3
            )
        }
    }
}


/**
 * 画面要素定義
 */
sealed class TextGroupScreenTag(value: String) : LayoutTag(value) {
    // Text
    object TextTitle : LogicGroupScreenTag("TEXT_SCREEN_TEXT_TITLE")
    object TextTitleNotChange : LogicGroupScreenTag("TEXT_SCREEN_TEXT_TITLE_NOT_CHANGE")
    object TextTitleNotChangeMaterial : LogicGroupScreenTag("TEXT_SCREEN_TEXT_TITLE_NOT_CHANGE_MATERIAL")
    object TextTitleChange : LogicGroupScreenTag("TEXT_SCREEN_TEXT_TITLE_CHANGE")
    object TextTitleChangeMaterial1 : LogicGroupScreenTag("TEXT_SCREEN_TEXT_TITLE_CHANGE_MATERIAL1")
    object TextTitleChangeMaterial2 : LogicGroupScreenTag("TEXT_SCREEN_TEXT_TITLE_CHANGE_MATERIAL2")

    // TextField
    object EditNotChange : LogicGroupScreenTag("TEXT_SCREEN_EDIT_NOT_CHANGE")
    object EditNotChangeMaterial : LogicGroupScreenTag("TEXT_SCREEN_EDIT_NOT_CHANGE_MATERIAL")
    object EditChange : LogicGroupScreenTag("TEXT_SCREEN_EDIT_CHANGE")
    object EditChangeMaterial1 : LogicGroupScreenTag("TEXT_SCREEN_EDIT_CHANGE_MATERIAL1")
    object EditChangeMaterial2 : LogicGroupScreenTag("TEXT_SCREEN_EDIT_CHANGE_MATERIAL2")
}

/**
 * コンテンツ
 *
 */
@Composable
private fun TextScreenMainContent(
    text1: String,
    text2: String,
    text3: String,
    textResult: String,
    changeText1: (String) -> Unit,
    changeText2: (String) -> Unit,
    changeText3: (String) -> Unit
) {
    Column {

        // テキスト
        Text(
            "Text 一覧",
            fontStyle = FontStyle.Italic,
            modifier = Modifier
                .padding(top = 8.dp)
                .testTag(TextGroupScreenTag.TextTitle.value)
        )

        // テキストフィールド
        TextScreenSubTitle("変更不可能なテキストフィールド", TextGroupScreenTag.TextTitleNotChange)
        TextField(
            value = "キョン",
            onValueChange = {},
            modifier = Modifier
                .padding(top = 8.dp)
                .testTag(TextGroupScreenTag.EditNotChange.value)
        )

        // マテリアルテキストフィールド
        TextScreenSubTitle("変更不可能なマテリアルフィールド", TextGroupScreenTag.TextTitleNotChangeMaterial)
        OutlinedTextField(
            value = "text",
            onValueChange = { },
            label = { Text("Label") },
            modifier = Modifier
                .padding(top = 8.dp)
                .testTag(TextGroupScreenTag.EditNotChangeMaterial.value),
        )

        // 変更可能なテキストフィールド
        TextScreenSubTitle("変更可能なテキストフィールド", TextGroupScreenTag.TextTitleChange)
        TextField(
            value = text1,
            onValueChange = { changeText1(it) },
            modifier = Modifier
                .padding(top = 8.dp)
                .testTag(TextGroupScreenTag.EditChange.value)
        )

        // 変更可能なマテリアルフィールド
        TextScreenSubTitle("変更可能なマテリアルフィールド", TextGroupScreenTag.TextTitleChangeMaterial1)
        OutlinedTextField(
            value = text2,
            onValueChange = { changeText2(it) },
            label = { Text("Label") },
            modifier = Modifier
                .padding(top = 8.dp)
                .testTag(TextGroupScreenTag.EditChangeMaterial1.value)
        )

        // 変更可能なマテリアルフィールド
        TextScreenSubTitle("変更を動的反映", TextGroupScreenTag.TextTitleChangeMaterial2)
        OutlinedTextField(
            value = text3,
            onValueChange = { changeText3(it) },
            label = { Text("動的変更") },
            modifier = Modifier
                .padding(top = 8.dp)
                .testTag(TextGroupScreenTag.EditChangeMaterial2.value)
        )

        // テキスト
        Text(
            textResult,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.padding(top = 8.dp)
        )

    }

}

@Composable
fun TextScreenSubTitle(title: String, tag: LayoutTag) {
    // テキスト
    Text(
        title,
        fontStyle = FontStyle.Italic,
        modifier = Modifier
            .padding(top = 8.dp)
            .testTag(tag.value)
    )
}

/**
 * プレビュー表示
 *
 */
@Preview(
    showBackground = true,
    name = "Light Mode"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PreviewTextGroupScreen() {
    val texGroupViewModel = TextGroupViewModel()
    Scaffold(backgroundColor = Color(0xfff5f5f5)) {
        TextGroupScreen(texGroupViewModel)
    }
}
