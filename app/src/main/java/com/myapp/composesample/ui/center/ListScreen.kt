package com.myapp.composesample.ui.center

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

/**
 * リスト用画面
 *
 */
@Composable
fun ListScreen(viewModel: ListViewModel) {

    // state
    val state = viewModel.state.value

    // event
    val refresh: () -> Unit = {
        viewModel.setEvent(ListContract.Event.Refresh)
    }

    // content
    ListContent(
        state,
        refresh
    )

}
/**
 * コンテンツ
 *
 */
@Composable
private fun ListContent(
    state: ListContract.State,
    refresh: () -> Unit
) {
    Column {
        // こいつがpull to Refresh を行っている
        SwipeRefresh(
            state = rememberSwipeRefreshState(state.isRefreshing),
            onRefresh = { refresh() },
        ) {
            LazyColumn {
                state.sampleContent.forEach{
                    item {
                        ListContentCard(it)
                    }
                }
            }
        }
    }
}

@Composable
private fun ListContentCard(message: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = 8.dp
    ) {
        Text(
            modifier = Modifier.padding(32.dp),
            text = message
        )
    }
}