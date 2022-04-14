package com.myapp.composesample.ui.center

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.draggedItem
import org.burnoutcrew.reorderable.rememberReorderState
import org.burnoutcrew.reorderable.reorderable

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
    val moveList: (Int, Int) -> Unit = { from, to ->
        viewModel.setEvent(ListContract.Event.MoveList(from, to))
    }

    // content
    ListContent(
        state,
        refresh,
        moveList
    )

}
/**
 * コンテンツ
 *
 */
@Composable
private fun ListContent(
    state: ListContract.State,
    refresh: () -> Unit,
    moveList: (Int, Int) -> Unit
) {
    val reorderState = rememberReorderState()
    Column {
        // こいつがpull to Refresh を行っている
        SwipeRefresh(
            state = rememberSwipeRefreshState(state.isRefreshing),
            onRefresh = { refresh() },
        ) {
            LazyColumn(
                state = reorderState.listState,
                modifier = Modifier
                    .reorderable(
                        state = reorderState,
                        onMove = { from, to ->
                            moveList(from.index, to.index)
                        },
                    ),
            ) {
                state.sampleContent.forEachIndexed { index, item ->
                    item{
                        Box(
                            modifier = Modifier
                                .detectReorderAfterLongPress(reorderState)
                                .draggedItem(reorderState.offsetByIndex(index))
                        ){
                            ListContentCard(item)
                        }
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