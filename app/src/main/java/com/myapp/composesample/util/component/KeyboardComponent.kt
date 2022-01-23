package com.myapp.composesample.util.component

import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.myapp.composesample.util.converterPxToTo
/**
 * キーボードの表示判定
 *
 */
sealed class Keyboard {
    /**
     * キーボードの高さ
     */
    abstract val height: Dp

    /**
     * 表示
     *
     * @property height キーボードの高さ
     */
    data class Opened(override val height: Dp): Keyboard()

    /**
     * 非表示
     */
    object Closed : Keyboard() {
        override val height: Dp = 0.dp
    }
}

@Composable
fun keyboardAsState(): State<Keyboard> {

    val view = LocalView.current
    val context = LocalContext.current
    val keyboardState = remember { mutableStateOf<Keyboard>(Keyboard.Closed) }
    DisposableEffect(view) {
        val onGlobalListener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = Rect()
            view.getWindowVisibleDisplayFrame(rect)
            val screenHeight = view.rootView.height
            val keypadHeight = screenHeight - rect.bottom
            keyboardState.value = if (keypadHeight > screenHeight * 0.15) {
                Keyboard.Opened(converterPxToTo(context, keypadHeight.toFloat()).dp )
            } else {
                Keyboard.Closed
            }
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(onGlobalListener)

        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalListener)
        }
    }

    return keyboardState
}