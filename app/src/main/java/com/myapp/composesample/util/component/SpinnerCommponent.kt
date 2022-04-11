package com.myapp.composesample.util.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.myapp.composesample.R

/**
 * Spinner用コンポーネント
 *
 * @param menuItems メニューのアイテム
 * @param selectedIndex 洗濯している項目
 * @param onMenuItemClick 項目を洗濯したときのアクション
 */
@Composable
fun CustomSpinner(
    menuItems: List<String>,
    selectedIndex : Int,
    onMenuItemClick : (Int) -> Unit,
) {

    var menuExpandedState by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopStart)
            .padding(top = 10.dp)
            .border(0.5.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.5f))
            .clickable(onClick = { menuExpandedState = true }),
    ) {

        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {

            val (label, iconView, list) = createRefs()

            // 選択したテキスト
            Text(
                text = menuItems[selectedIndex],
                color = Color.Green,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(label) {
                        top.linkTo(parent.top, margin = 16.dp)
                        bottom.linkTo(parent.bottom, margin = 16.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                        end.linkTo(iconView.start)
                        width = Dimension.fillToConstraints
                    }
            )

            // トグルアイコン
            val displayIcon: Painter = painterResource(id = R.drawable.ic_phone_android_black_48)
            Icon(
                painter = displayIcon,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp, 20.dp)
                    .constrainAs(iconView) {
                        end.linkTo(parent.end, margin = 16.dp)
                        top.linkTo(parent.top, margin = 16.dp)
                        bottom.linkTo(parent.bottom, margin = 16.dp)
                    },
                tint = MaterialTheme.colors.onSurface
            )

            // セレクトメニュー
            DropdownMenu(
                expanded = menuExpandedState,
                onDismissRequest = { menuExpandedState = false },
                modifier = Modifier
                    .background(MaterialTheme.colors.surface)
                    .constrainAs(list) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
            ) {
                menuItems.forEachIndexed { index, title ->
                    DropdownMenuItem(
                        onClick = {
                            // 先頭を選択させたくない場合の制御
                            if (index != 0) {
                                menuExpandedState = false
                                onMenuItemClick(index)
                            }
                        }
                    ) {
                        Text(text = title)
                    }
                }
            }
        }
    }
}