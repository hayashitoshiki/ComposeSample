package com.myapp.composesample.ui.left

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
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

        // Slider
        ButtonScreenSubTitle("ボタン活性制御アニメーション")
        val button0Enable = remember { mutableStateOf(true) }
        val button25Enable = remember { mutableStateOf(true) }
        val button50Enable = remember { mutableStateOf(true) }
        val button1Enable = remember { mutableStateOf(true) }
        val button150Enable = remember { mutableStateOf(true) }
        val button2Enable = remember { mutableStateOf(true) }
        val button3Enable = remember { mutableStateOf(true) }
        val button4Enable = remember { mutableStateOf(true) }
        val button5Enable = remember { mutableStateOf(true) }
        val alphaButton0: Color by animateColorAsState(
            targetValue = if (button0Enable.value) MaterialTheme.colors.primary else Color.Gray, // 活性時は青、非活性時は灰色
            animationSpec = tween(
                durationMillis = 0,  // アニメーションの持続時間
                easing = FastOutSlowInEasing
            )
        )
        val alphaButton25: Color by animateColorAsState(
            targetValue = if (button25Enable.value) MaterialTheme.colors.primary else Color.Gray, // 活性時は青、非活性時は灰色
            animationSpec = tween(
                durationMillis = 20,  // アニメーションの持続時間
                easing = FastOutSlowInEasing
            )
        )
        val alphaButton50: Color by animateColorAsState(
            targetValue = if (button50Enable.value) MaterialTheme.colors.primary else Color.Gray, // 活性時は青、非活性時は灰色
            animationSpec = tween(
                durationMillis = 50,  // アニメーションの持続時間
                easing = FastOutSlowInEasing
            )
        )
        val alphaButton1: Color by animateColorAsState(
            targetValue = if (button1Enable.value) MaterialTheme.colors.primary else Color.Gray, // 活性時は青、非活性時は灰色
            animationSpec = tween(
                durationMillis = 100,  // アニメーションの持続時間
                easing = FastOutSlowInEasing
            )
        )
        val alphaButton150: Color by animateColorAsState(
            targetValue = if (button150Enable.value) MaterialTheme.colors.primary else Color.Gray, // 活性時は青、非活性時は灰色
            animationSpec = tween(
                durationMillis = 150,  // アニメーションの持続時間
                easing = FastOutSlowInEasing
            )
        )
        val alphaButton2: Color by animateColorAsState(
            targetValue = if (button2Enable.value) MaterialTheme.colors.primary else Color.Gray, // 活性時は青、非活性時は灰色
            animationSpec = tween(
                durationMillis = 200,  // アニメーションの持続時間
                easing = FastOutSlowInEasing
            )
        )
        val alphaButton3: Color by animateColorAsState(
            targetValue = if (button3Enable.value) MaterialTheme.colors.primary else Color.Gray, // 活性時は青、非活性時は灰色
            animationSpec = tween(
                durationMillis =300,  // アニメーションの持続時間
                easing = FastOutSlowInEasing
            )
        )
        val alphaButton4: Color by animateColorAsState(
            targetValue = if (button4Enable.value) MaterialTheme.colors.primary else Color.Gray, // 活性時は青、非活性時は灰色
            animationSpec = tween(
                durationMillis =400,  // アニメーションの持続時間
                easing = FastOutSlowInEasing
            )
        )
        val alphaButton5: Color by animateColorAsState(
            targetValue = if (button5Enable.value) MaterialTheme.colors.primary else Color.Gray, // 活性時は青、非活性時は灰色
            animationSpec = tween(
                durationMillis =500,  // アニメーションの持続時間
                easing = FastOutSlowInEasing
            )
        )
        Button(
            modifier = Modifier,
            onClick = {button25Enable.value = !button25Enable.value},
            colors = ButtonDefaults.buttonColors(backgroundColor = alphaButton0),
            enabled = button0Enable.value
        ) {
            Text("アニメーションスピード0")
        }
        Spacer(modifier = Modifier.size(16.dp))
        Button(
            modifier = Modifier,
            onClick = {button50Enable.value = !button50Enable.value},
            colors = ButtonDefaults.buttonColors(backgroundColor = alphaButton25),
            enabled = button25Enable.value
        ) {
            Text("アニメーションスピード25")
        }
        Spacer(modifier = Modifier.size(16.dp))
        Button(
            modifier = Modifier,
            colors = ButtonDefaults.buttonColors(backgroundColor = alphaButton50),
            onClick = {button1Enable.value = !button1Enable.value},
            enabled = button50Enable.value
        ) {
            Text("アニメーションスピード50")
        }
        Spacer(modifier = Modifier.size(16.dp))
        Button(
            modifier = Modifier,
            colors = ButtonDefaults.buttonColors(backgroundColor = alphaButton1),
            onClick = {button150Enable.value = !button150Enable.value},
            enabled = button1Enable.value
        ) {
            Text("アニメーションスピード100")
        }
        Spacer(modifier = Modifier.size(16.dp))
        Button(
            modifier = Modifier,
            colors = ButtonDefaults.buttonColors(backgroundColor = alphaButton150),
            onClick = {button2Enable.value = !button2Enable.value},
            enabled = button150Enable.value
        ) {
            Text("アニメーションスピード150")
        }
        Spacer(modifier = Modifier.size(16.dp))
        Button(
            modifier = Modifier,
            colors = ButtonDefaults.buttonColors(backgroundColor = alphaButton2),
            onClick = {button3Enable.value = !button3Enable.value},
            enabled = button2Enable.value
        ) {
            Text("アニメーションスピード200")
        }
        Spacer(modifier = Modifier.size(16.dp))
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = alphaButton3),
            onClick = {button4Enable.value = !button4Enable.value},
            modifier = Modifier,
            enabled = button3Enable.value
        ) {
            Text("アニメーションスピード300")
        }
        Spacer(modifier = Modifier.size(16.dp))
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = alphaButton4),
            onClick = {button5Enable.value = !button5Enable.value},
            modifier = Modifier,
            enabled = button4Enable.value
        ) {
            Text("アニメーションスピード400")
        }
        Spacer(modifier = Modifier.size(16.dp))
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = alphaButton5),
            onClick = {button0Enable.value = !button0Enable.value},
            modifier = Modifier,
            enabled = button5Enable.value
        ) {
            Text("アニメーションスピード500")
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
