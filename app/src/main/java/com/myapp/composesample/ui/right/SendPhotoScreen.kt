package com.myapp.composesample.ui.right

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.myapp.composesample.util.component.SubTitleGroup
import com.myapp.composesample.util.theme.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach


/**
 * 写真送信TOP画面
 *
 */
@Preview(showBackground = true)
@Composable
fun SendPhotoScreen() {
    SendPhotoContent(SendPhotoViewModel())
}

/**
 * コンテンツ
 *
 */
@Composable
private fun SendPhotoContent(viewModel: SendPhotoViewModel) {

    // state
    val firstName: String by viewModel.firstNameText.observeAsState("")
    val lastName: String by viewModel.lastNameText.observeAsState("")
    val comment: String by viewModel.commentText.observeAsState("")
    val enableAddPhotoButton: Boolean by viewModel.enabledAddPhotoButton.observeAsState(true)
    val enableSendButton: Boolean by viewModel.enabledSendButton.observeAsState(true)
    val list by viewModel.photoList.observeAsState(listOf())
    val listChunked = list.chunked(3)
    val deleteIndex by viewModel.deleteIndex.observeAsState(-1)
    val isDeleteDialog by viewModel.isDeleteDialog.observeAsState(false)

    // effect
    val focusManager = LocalFocusManager.current
    LaunchedEffect(true) {
        viewModel.effect.onEach { effect ->
            when (effect) {
                 SendPhotoViewModel.Effect.FocusChangeFirstNameToLastName -> {
                    focusManager.moveFocus(FocusDirection.Right)
                }
                SendPhotoViewModel.Effect.FocusChangeLastNameToComment -> {
                    focusManager.moveFocus(FocusDirection.Down)
                }
                SendPhotoViewModel.Effect.NavigateToSendPhotoStart -> {
                    // TODO : 画面遷移
                }
            }
        }.collect()
    }

    Scaffold(backgroundColor = Color(0xfff5f5f5)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(start = 15.dp, end = 15.dp, bottom = 68.dp)
        ) {

            // 基本情報
            SubTitleGroup(
                text = "基本情報",
                modifier = Modifier.padding(top = 84.dp)
            )
            LabelText(
                text = "お客様氏名",
                modifier = Modifier.padding(top = 30.dp)
            )
            Row(
                modifier = Modifier.padding(top = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                LabelText(
                    text = "姓"
                )
                NameInputField(
                    value = firstName,
                    onValueChange = { viewModel.changeFirstName(it) },
                    modifier = Modifier.padding(start = 10.dp)
                )
                LabelText(
                    text = "名",
                    modifier = Modifier.padding(start = 30.dp)
                )
                NameInputField(
                    value = lastName,
                    onValueChange = { viewModel.changeLastName(it) },
                    modifier = Modifier.padding(start = 10.dp)
                )
                LabelText(
                    text = "様",
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
            LabelText(
                text = "コメント",
                modifier = Modifier.padding(top = 10.dp)
            )
            OutlinedTextField(
                value = comment,
                maxLines = 5,
                onValueChange = { viewModel.changeComment(it) },
                modifier = Modifier
                    .height(144.dp)
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )

            // 写真
            SubTitleGroup(
                text = "写真",
                modifier = Modifier.padding(top = 40.dp)
            )
            Column(modifier = Modifier.padding(top = 20.dp)) {
                for (textList in listChunked) {
                    Row {
                        for (text in textList) {
                            TextListItem(
                                text = text,
                                onClickAction = { viewModel.openDeleteDialog(list.indexOf(text)) }
                            )
                        }
                    }
                }
            }
            if (enableAddPhotoButton) {
                CommonRedFrameButton(
                    text = "写真を追加する",
                    onClickAction = {
                        // TODO : カメラ処理
                        viewModel.add() },
                    modifier = Modifier.padding(top = 30.dp, start = 15.dp, end = 15.dp)
                )
            }
            if (enableSendButton) {
                CommonRedButton(
                    text = "送信する",
                    onClickAction = { viewModel.onClickSend() },
                    modifier = Modifier.padding(top = 30.dp, start = 15.dp, end = 15.dp)
                )
            }
            if (isDeleteDialog) {
                DeleteConfirmDialog(
                    text = list[deleteIndex],
                    onNegativeClickAction = { viewModel.clearDeleteDialog() },
                    onPositiveClickAction = { viewModel.delete(deleteIndex) }
                )
            }
        }
////            Button(
////                onClick = { },
////                enabled = false,
////                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.5.dp),
////                colors = ButtonDefaults.textButtonColors(
////                    backgroundColor = IconCheckedColor,
////                    contentColor = IconCheckedColor,
////                    disabledContentColor = IconCheckedColor
////                ),
////                shape = CircleShape,
////                modifier = Modifier
////                    .height(48.dp)
////                    .fillMaxWidth()
////                    .constrainAs(btnSendPhoto) {
////                        top.linkTo(btnAddPhoto.bottom, margin = 30.dp)
////                        start.linkTo(btnAddPhoto.start)
////                        end.linkTo(btnAddPhoto.end)
////                    }
////            ) {
////                Text(
////                    modifier = Modifier,
////                    text = "送信する",
////                    fontSize = 16.sp,
////                    color = TextColorLight
////                )
////            }
//        }
    }
}

@Composable
fun NameInputField(value: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        modifier = modifier.width(100.dp)
    )
}

/**
 * 氏名ラベル
 *
 * @param text
 * @param modifier
 */
@Composable
private fun LabelText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontSize = 18.sp,
        color = TextColor,
        modifier = modifier
    )
}


@Composable
fun CommonRedButton(text: String, onClickAction: () -> Unit, modifier: Modifier) {
    Box(modifier = modifier) {
        Button(
            onClick = onClickAction,
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .height(48.dp)
                .fillMaxWidth()
                .background(ButtonPrimaryColor)
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Composable
fun DeleteConfirmDialog(
    text: String,
    onNegativeClickAction: () -> Unit,
    onPositiveClickAction: () -> Unit
) {
    Dialog(onDismissRequest = { /*TODO*/ }) {
        Text(text = text)
        Button(
            onClick = onNegativeClickAction
        ) {
            Text(text = "キャンセル")
        }
        Button(
            onClick = onPositiveClickAction,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = BackGroundColor,
                contentColor = ButtonPrimaryColor
            ),
        ) {
            Text(text = "はい")
        }
    }
}

@Composable
fun CommonRedFrameButton(text: String, onClickAction: () -> Unit, modifier: Modifier) {
    Box(modifier = modifier) {
        Button(
            onClick = onClickAction,
            border = BorderStroke(2.dp, ButtonPrimaryColor),
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = BackGroundColor,
                contentColor = ButtonPrimaryColor
            ),
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = ButtonPrimaryColor
            )
        }
    }
}

/**
 * テキスリスト表示用のItem
 *
 */
@Composable
fun TextListItem(text: String, onClickAction: () -> Unit) {
    Button(
        onClick = onClickAction,
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .size(124.dp)
            .fillMaxWidth()
            .background(ButtonPrimaryColor)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
//    Card(
//        backgroundColor = Color.Red,
//        modifier = Modifier
//            .size(124.dp)
//            .padding(4.dp),
//        elevation = 8.dp,
//    ) {
//        Text(
//            text = text,
//            fontWeight = FontWeight.Bold,
//            fontSize = 30.sp,
//            color = Color(0xFFFFFFFF),
//            textAlign = TextAlign.Center,
//            modifier = Modifier.padding(16.dp)
//        )
//    }
}

@Composable
fun WhiteRedFrameButton(text: String, onClickAction: () -> Unit, modifier: Modifier) {
    Box(modifier = modifier) {
        Button(
            onClick = onClickAction,
            border = BorderStroke(2.dp, ButtonPrimaryColor),
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White,
                contentColor = ButtonPrimaryColor
            ),
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = ButtonPrimaryColor
            )
        }
    }
}
