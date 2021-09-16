package com.myapp.composesample.ui.left

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * テキスト関連画面
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
fun TextGroupScreen() {
    Scaffold(backgroundColor = Color(0xfff5f5f5)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            textScreenMainContent()
        }
    }
}

/**
 * コンテンツ
 *
 */
@Preview(showBackground = true)
@Composable
fun textScreenMainContent() {
    Column {

        // テキスト
        Text(
            "Text 一覧",
            fontStyle = FontStyle.Italic,
            modifier = Modifier.padding(top = 8.dp)
        )

        // テキストフィールド
        TextScreenSubTitle("変更不可能なテキストフィールド")
        TextField(
            value = "キョン",
            onValueChange = {},
            modifier = Modifier.padding(top = 8.dp)
        )

        // マテリアルテキストフィールド
        TextScreenSubTitle("変更不可能なマテリアルフィールド")
        OutlinedTextField(
            value = "text",
            onValueChange = {  },
            label = { Text("Label") },
            modifier = Modifier.padding(top = 8.dp),
        )

        // 変更可能なテキストフィールド
        TextScreenSubTitle("変更可能なテキストフィールド")
        val fieldValue = remember { mutableStateOf("キョン") }
        TextField(
            value = fieldValue.value,
            onValueChange = { newValue -> fieldValue.value = newValue },
            modifier = Modifier.padding(top = 8.dp)
        )

        // 変更可能なマテリアルフィールド
        TextScreenSubTitle("変更可能なマテリアルフィールド")
        val text  = remember { mutableStateOf("Hello") }
        OutlinedTextField(
            value = text.value,
            onValueChange = { text.value = it },
            label = { Text("Label") },
            modifier = Modifier.padding(top = 8.dp)
        )

        // 変更可能なマテリアルフィールド
        TextScreenSubTitle("変更を動的反映")
        val text2  = remember { mutableStateOf("Hello") }
        OutlinedTextField(
            value = text2.value,
            onValueChange = { text2.value = it },
            label = { Text("動的変更") },
            modifier = Modifier.padding(top = 8.dp)
        )

        // テキスト
        Text(
            text2.value,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.padding(top = 8.dp)
        )

    }

}

@Composable
fun TextScreenSubTitle(title: String) {
    // テキスト
    Text(
        title,
        fontStyle = FontStyle.Italic,
        modifier = Modifier.padding(top = 8.dp)
    )
}