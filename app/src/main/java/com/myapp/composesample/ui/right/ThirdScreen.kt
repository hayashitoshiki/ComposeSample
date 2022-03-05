package com.myapp.composesample.ui.right

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.myapp.composesample.util.component.Sample1TabComponent

/**
 * 右画面
 *
 */
@ExperimentalPagerApi
@Preview(showBackground = true)
@Composable
fun ThirdScreen() {
    Scaffold(
        backgroundColor = Color(0xfff5f5f5)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Box(modifier = Modifier.padding(top = 16.dp)){
                Sample1TabComponent()
            }
        }
    }
}