package com.myapp.composesample.ui.left

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapp.composesample.R

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
fun ButtonGroupScreen() {
    Scaffold(backgroundColor = Color(0xfff5f5f5)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            ButtonScreenMainContent()
        }
    }
}


/**
 * コンテンツ
 *
 */
@Preview(showBackground = true)
@Composable
private fun ButtonScreenMainContent() {
    Column( modifier = Modifier
        .padding(start = 8.dp, bottom = 56.dp)
        .verticalScroll(rememberScrollState())
    ) {

        // タイトル
        Text(
            text = "Button Sample Screen",
            fontSize = 24.sp,
            modifier = Modifier.padding(top = 8.dp, start = 32.dp),
        )

        // デフォルトButton
        ButtonScreenSubTitle("default Button")
        Button(
            onClick = {},
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(stringResource(id = R.string.btn_txt_default))
        }

        // 角丸Button
        ButtonScreenSubTitle("sharp Button")
        Button(
            onClick = {},
            modifier = Modifier.padding(top = 8.dp),
            shape = MaterialTheme.shapes.large
        ) {
            Text(stringResource(id = R.string.btn_txt_default))
        }

        // IconToggleButton
        ButtonScreenSubTitle("IconToggleButton")
        val checked = remember { mutableStateOf(false) }
        IconToggleButton(checked = checked.value, onCheckedChange = { checked.value = it }) {
            val tint by animateColorAsState(if (checked.value) Color(0xFFEC407A) else Color(0xFFB0BEC5))
            Icon(Icons.Filled.Favorite, contentDescription = "Localized description", tint = tint)
        }

        // 非活性Button
        ButtonScreenSubTitle("非活性 Button")
        Button(
            onClick = {},
            modifier = Modifier.padding(top = 8.dp),
            enabled = false
        ) {
            Text(stringResource(id = R.string.btn_txt_enable))
        }

        // Radio Button
        ButtonScreenSubTitle("Radio Button")
        val state = remember { mutableStateOf(1) }
        Row(Modifier.selectableGroup()) {
            RadioButton(
                selected = state.value == 1,
                onClick = { state.value = 1 }
            )
            RadioButton(
                selected = state.value == 2,
                onClick = { state.value = 2 }
            )
            RadioButton(
                selected = state.value == 3,
                onClick = { state.value = 3 }
            )
            RadioButton(
                selected = state.value == 4,
                onClick = { state.value = 4 }
            )
            RadioButton(
                selected = state.value == 5,
                onClick = { state.value = 5 }
            )
        }

        // ImageButton
        ButtonScreenSubTitle("Icon Button")
        IconButton(
            modifier = Modifier.then(Modifier.size(48.dp)),
            onClick = { }
        ) {
            Icon(
                Icons.Filled.Home,
                "contentDescription",
                tint = Color.Black)
        }

        // TextButton
        ButtonScreenSubTitle("Text Button")
        TextButton(
            onClick = {},
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(stringResource(id = R.string.btn_txt_enable))
        }

        // OutlineButton
        ButtonScreenSubTitle("Outlined Button")
        OutlinedButton(
            onClick = {},
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(stringResource(id = R.string.btn_txt_outline))
        }

        // ExtendedFloatingActionButton
        ButtonScreenSubTitle("ExtendedFloatingActionButton")
        ExtendedFloatingActionButton(
            icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
            text = { Text(stringResource(id = R.string.btn_txt_floating_extend))},
            onClick = { /*TODO*/ }
        )

        // Floating Button
        ButtonScreenSubTitle("Floating Button")
        FloatingActionButton(onClick = { /*do something*/ }) {
            Icon(Icons.Filled.Add, contentDescription = "追加")
        }

        // Slider
        ButtonScreenSubTitle("Slider")
        val sliderPosition = remember { mutableStateOf(0f) }
        Slider(
            value = sliderPosition.value,
            onValueChange = { sliderPosition.value = it }
        )

        // Switch
        ButtonScreenSubTitle("Switch")
        val checkedState = remember { mutableStateOf(true) }
        Switch(
            checked = checkedState.value,
            onCheckedChange = { checkedState.value = it }
        )
    }

}

/**
 * サブタイトル
 *
 * @param title サブタイトルテキスト
 */
@Composable
private fun ButtonScreenSubTitle(title: String) {
    Text(
        title,
        fontStyle = FontStyle.Italic,
        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
    )
}