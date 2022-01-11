package com.myapp.composesample.ui.left

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.RelocationRequester
import androidx.compose.ui.layout.relocationRequester


/**
 * テキスト関連発展画面
 *
 */
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun TextScrollerScreen() {
   val text = remember { mutableStateOf("") }
    Scaffold(backgroundColor = Color(0xfff5f5f5)) {
        MainContent(text.value){
            text.value = it
        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun MainContent(
    inputOne: String,
    inputOneChange: (String) -> Unit,
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val bringIntoViewRequester = remember { RelocationRequester() }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        repeat(6) { PlaceholderCard() }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .relocationRequester(bringIntoViewRequester),
            elevation = 8.dp
        ) {
            Column {
                Text(modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp), text = "Please fill something in")
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .onFocusEvent { focusState ->
                            if (focusState.isFocused) {
                                coroutineScope.launch {
                                    bringIntoViewRequester.bringIntoView()
                                }
                            }
                        },
                    placeholder = { Text("Some input") },
                    value = inputOne,
                    singleLine = true,
                    onValueChange = inputOneChange,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, bottom = 16.dp, end = 16.dp),
                    onClick = { /*Nothing*/ }) {
                    Text("Click")
                }
            }
        }

        repeat(10) { PlaceholderCard() }
    }
}

@Composable
private fun PlaceholderCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = 8.dp
    ) {
        Text(modifier = Modifier.padding(32.dp), text = "Placeholder")
    }
}