package com.myapp.composesample.ui.center

import androidx.lifecycle.viewModelScope
import com.myapp.composesample.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

/**
 * リスト関連画面用 ViewModel
 *
 */
@HiltViewModel
class ListViewModel @Inject constructor() :
    BaseViewModel<ListContract.State, ListContract.Effect, ListContract.Event>() {
    override fun initState(): ListContract.State {
        return ListContract.State()
    }

    override fun handleEvents(event: ListContract.Event) {
        updateList()
    }

    private fun updateList() {
        setState { copy(isRefreshing = true) }
        viewModelScope.launch {
            delay(500)
            val list = mutableListOf<String>()
            for(i in 1..20) {
                list.add( LocalTime.now().nano.toString())
            }
            setState {
                copy(
                    isRefreshing = false,
                    sampleContent = list
                )
            }
        }
    }
}