package com.myapp.composesample.ui.right

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.net.toUri
import coil.compose.rememberImagePainter
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
    val isOpenCameraView: Boolean by viewModel.isOpenCameraView.observeAsState(false)
    var isGranted by remember { mutableStateOf(false) }
//    Log.d("内部ファイルリスト", "file list size :" + files.size)
//    Log.d("内部ファイルリスト", "file list :" + files)

//    // 全権取得
//    val context = LocalContext.current
//    var filesDir: Array<String> = context.fileList()
//    // １件削除
//    context.deleteFile(cacheFileName)
//    // 全件削除
//    context.fileList().forEach {
//        context.deleteFile(it)
//    }


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
    Box(modifier = Modifier.fillMaxSize()){
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
                for (imageUriList in listChunked) {
                    Row {
                        for (imageUri in imageUriList) {
                            TextListItem(
                                imageUri = imageUri,
                                onClickAction = { viewModel.openDeleteDialog(list.indexOf(imageUri)) }
                            )
                        }
                    }
                }
            }
            if (enableAddPhotoButton) {
                CommonRedFrameButton(
                    text = "写真を追加する",
                    onClickAction = { viewModel.openCameraView() },
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
    }
        // カメラ表示
        if (isOpenCameraView) {
            PermissionHandler { isGranted = it }
            if (isGranted) {
                CameraView(
                    onImageCaptured = { viewModel.add(it.toString()) },
                    onError = {}
                )
            }
        }
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
 * 画像リスト表示用のItem
 *
 */
@Composable
fun TextListItem(imageUri: String, onClickAction: () -> Unit) {
    IconButton(
        onClick = onClickAction,
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .size(124.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = rememberImagePainter(imageUri.toUri()),
            contentDescription = null,
        )
    }
}
