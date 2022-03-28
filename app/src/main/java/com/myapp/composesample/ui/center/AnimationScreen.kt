package com.myapp.composesample.ui.center

import androidx.compose.animation.*
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.navigation.NavHostController

/**
 * アニメーションサンプル画面
 *
 */
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun AnimationScreen(navController: NavHostController) {
    Scaffold(
        backgroundColor = Color(0xfff5f5f5)
    ) {
        Column {
            val count = remember{ mutableStateOf(0) }
            // fadeアニメーション
            AnimatedContent(targetState = count.value) { targetCount ->
                Text(
                    text = "$targetCount",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h2,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(onClick = { count.value-- }) {
                    Text("MINUS")
                }
                Button(onClick = { count.value++ }) {
                    Text("PLUS")
                }
            }

            // スクロールアニメーション
            val x = remember { mutableStateOf(0) }
            val y = remember { mutableStateOf(0) }
            val xyList = listOf(x.value,y.value)
            AnimatedContent(
                targetState = xyList,
                transitionSpec = {
                    val isXPlus = targetState[0] > initialState[0]
                    val isYPlus = targetState[1] > initialState[1]
                    if (targetState[1] != initialState[1]) {
                        // 横スライド
                        if (isYPlus) {
                            slideInVertically { height -> -height } + fadeIn() with slideOutVertically { height -> height } + fadeOut()
                        } else {
                            slideInVertically { height -> height } + fadeIn() with slideOutVertically { height -> -height } + fadeOut()
                        }.using(
                            SizeTransform(clip = false)
                        )
                    } else {
                        // 縦スライド
                        if (isXPlus) {
                            slideInHorizontally { width -> width } + fadeIn() with slideOutHorizontally { width -> -width } + fadeOut()
                        } else {
                            slideInHorizontally { width -> -width } + fadeIn() with slideOutHorizontally { width -> width } + fadeOut()
                        }.using(
                            SizeTransform(clip = false)
                        )
                    }
                }
            ) { targetCount ->
                Text(
                    text = "$targetCount",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h2,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(onClick = { x.value-- }) {
                    Text("X MINUS")
                }
                Button(onClick = { x.value++ }) {
                    Text("X PLUS")
                }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(onClick = { y.value-- }) {
                    Text("Y MINUS")
                }
                Button(onClick = { y.value++ }) {
                    Text("Y PLUS")
                }
            }

            // 拡大縮小アニメーション
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                val expanded = remember { mutableStateOf(false) }
                AnimatedContent(
                    targetState = expanded.value,
                    transitionSpec = {
                        fadeIn() with fadeOut() using SizeTransform { initialSize, targetSize ->
                            keyframes {
                                IntSize(initialSize.width, initialSize.height) at 250
                                durationMillis = 500
                            }
                        }
                    }
                ) { targetExpanded ->
                    if (targetExpanded) {
                        Button(
                            onClick = { expanded.value = !expanded.value},
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(text = "Expanded")
                        }
                    } else {
                        Button(
                            onClick = { expanded.value = !expanded.value },
                        ) {
                            Text(text = "Expanded")
                        }
                    }
                }
            }
        }
    }
}