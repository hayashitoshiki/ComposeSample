package com.myapp.composesample.ui.left

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapp.composesample.R
import com.myapp.composesample.util.component.CustomSpinner

/**
 * テキスト関連画面
 *
 */
@Composable
fun ButtonGroupScreen(buttonGroupViewModel: ButtonGroupViewModel) {

    // state
    val state = buttonGroupViewModel.state.value

    // event
    val changeRadioButton: (Int) ->  Unit = {
        buttonGroupViewModel.setEvent(ButtonContract.Event.ChangeRadioButton(it))
    }
    val changeSwitch: (Boolean) ->  Unit = {
        buttonGroupViewModel.setEvent(ButtonContract.Event.ChangeSwitch(it))
    }
    val changeSlider: (Float) ->  Unit = {
        buttonGroupViewModel.setEvent(ButtonContract.Event.ChangeSlider(it))
    }

    Scaffold(backgroundColor = Color(0xfff5f5f5)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            ButtonScreenMainContent(
                state.checkedRadioButton,
                state.sliderValue,
                state.switchValue,
                changeRadioButton,
                changeSlider,
                changeSwitch
            )
        }
    }
}


/**
 * コンテンツ
 *
 */
@Composable
private fun ButtonScreenMainContent(
    radioButtonValue: Int,
    slideValue: Float,
    switchValue: Boolean,
    changeRadioButton: (Int) -> Unit,
    changeSlide: (Float) -> Unit,
    changeSwitch: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
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
            val tint by animateColorAsState(
                if (checked.value) Color(0xFFEC407A) else Color(
                    0xFFB0BEC5
                )
            )
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
        Row(Modifier.selectableGroup()) {
            RadioButton(
                selected = radioButtonValue == 1,
                onClick = { changeRadioButton(1) }
            )
            RadioButton(
                selected = radioButtonValue == 2,
                onClick = { changeRadioButton(2) }
            )
            RadioButton(
                selected = radioButtonValue == 3,
                onClick = { changeRadioButton(3) }
            )
            RadioButton(
                selected = radioButtonValue == 4,
                onClick = { changeRadioButton(4) }
            )
            RadioButton(
                selected = radioButtonValue == 5,
                onClick = { changeRadioButton(5) }
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
                tint = Color.Black
            )
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
            text = { Text(stringResource(id = R.string.btn_txt_floating_extend)) },
            onClick = { /*TODO*/ }
        )

        // Floating Button
        ButtonScreenSubTitle("Floating Button")
        FloatingActionButton(onClick = { /*do something*/ }) {
            Icon(Icons.Filled.Add, contentDescription = "追加")
        }

        // Slider
        ButtonScreenSubTitle("Slider")
        Slider(
            value = slideValue,
            onValueChange = { changeSlide(it) }
        )

        // Switch
        ButtonScreenSubTitle("Switch")
        Switch(
            checked = switchValue,
            onCheckedChange = { changeSwitch(it) }
        )

        // Spinner
        ButtonScreenSubTitle("Spiner")
        var selectedIndex by remember { mutableStateOf(0) }
        val items = listOf(
            "一番上は洗濯しても反映されません",
            "２番目",
            "３番目",
            "４番目"
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            CustomSpinner(
                menuItems = items,
                selectedIndex = selectedIndex,
                onMenuItemClick = { selectedIndex = it }
            )
        }
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
fun ButtonGroupScreenDemo() {
    val viewModel = ButtonGroupViewModel()
    Scaffold(backgroundColor = Color(0xfff5f5f5)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            ButtonGroupScreen(viewModel)
        }
    }
}
