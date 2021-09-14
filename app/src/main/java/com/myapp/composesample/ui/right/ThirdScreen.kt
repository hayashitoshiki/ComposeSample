package com.myapp.composesample.ui.right

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

/**
 * 右画面
 *
 */
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
            Text(text = "Third")
        }
    }
}