package com.myapp.composesample.ui.right

import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.myapp.composesample.ui.NavigationScreens
import com.myapp.composesample.util.component.BigWhiteImageButton
import com.myapp.composesample.util.component.SendPhotoHistoryListButton
import com.myapp.composesample.util.component.SendPhotoHistoryTopic
import com.myapp.composesample.util.theme.BackGroundColor
import com.myapp.composesample.util.theme.ButtonPrimaryColor
import com.myapp.composesample.util.theme.IconGreenColor
import com.myapp.composesample.util.theme.TextColor

/**
 * 右画面
 *
 */
@Composable
fun ThirdScreen(
    navController: NavHostController,
    viewModel: SendPhotoTopViewModel
) {
    LaunchedEffect(true) {
        viewModel.updateSendPhotoList()
    }
    SendPhotoTopContent(navController, viewModel)
}

/**
 * コンテンツ
 *
 */
@Composable
fun SendPhotoTopContent(
    navController: NavHostController,
    viewModel: SendPhotoTopViewModel) {
    // state
    val sendPhotoHistoryList: List<SendPhotoHistory> by viewModel.sendPhotoHistoryList.observeAsState(listOf())
    val openDialog = remember { mutableStateOf(false) }

    Scaffold(backgroundColor = Color(0xfff5f5f5)) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp)) {
            BigWhiteImageButton(
                resId = 0,
                text = "写真を撮影",
                onClickAction = { openDialog.value = true },
                modifier = Modifier.padding(top = 30.dp),
            )
            SendPhotoHistoryTopic(
                itemList = sendPhotoHistoryList.take(5),
                modifier = Modifier.padding(top = 30.dp),
                enabledMemo = false,
                enabledStatus = false
            )
            if (sendPhotoHistoryList.isNotEmpty()) {
                SendPhotoHistoryListButton(
                    onClickAction = { viewModel.changeData() },
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .align(alignment = Alignment.End)
                )
            }
            if (openDialog.value) {
                NetworkErrorDialog(
                    onClickNegativeAction = { openDialog.value = false },
                    onClickPositiveAction = {
                        openDialog.value = false
                        navController.navigate(NavigationScreens.SEND_PHOTO_SCREEN.route)
                        //    navController.navigate()
                    }
                )
            }
        }
    }
}


/**
 * プレビュー表示
 *
 */
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
fun PreviewPhotoTopScreen() {
    val remoteMock = RemotePhotoRepositoryImp()
    val localMock = LocalPhotoRepositoryImp()
    val useCase = PhotoUseCaseImp(remoteMock, localMock)
    val viewModel = SendPhotoTopViewModel(useCase)
    val navController = rememberNavController()
    Scaffold(backgroundColor = Color(0xfff5f5f5)) {
        ThirdScreen(navController, viewModel)
    }
}



@Composable
fun NetworkErrorDialog(onClickNegativeAction: () -> Unit, onClickPositiveAction: () -> Unit) {
    var test = ""
    for(i in 0..1000){
        test += i.toString() + ", "
    }
    Dialog(onDismissRequest = { },){
        Column (
            modifier = Modifier
                .clip(RoundedCornerShape(5))
                .background(BackGroundColor)
                .padding(10.dp),
            verticalArrangement= Arrangement.SpaceBetween) {
            TextButton(
                onClick = { onClickNegativeAction() },
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.size(30.dp).align(alignment = Alignment.End)
            ) {
                Text(
                    text = "×",
                    fontSize = 20.sp,
                    color = TextColor,
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = 20.dp, bottom = 10.dp)
                    .verticalScroll(rememberScrollState())
                    .weight(1f, false)
            ) {
                Text(text = test)
            }
            TextButton(
                onClick = { onClickPositiveAction() },
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "確定",
                    fontSize = 24.sp,
                    color = TextColor,
                    style = TextStyle(textDecoration = TextDecoration.Underline)
                )
            }
        }
    }
}