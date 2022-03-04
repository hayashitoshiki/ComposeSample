package com.myapp.composesample.ui.right

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun Tab3Screen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Tab3Screen",
            fontSize= 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
