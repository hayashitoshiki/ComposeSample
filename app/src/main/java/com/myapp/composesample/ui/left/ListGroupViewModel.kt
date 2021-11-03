package com.myapp.composesample.ui.left

import com.myapp.composesample.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * テキスト関連発展画面 UIロジック
 *
 */
@HiltViewModel
class ListGroupViewModel @Inject constructor() :
    BaseViewModel<ListGroupContract.State, ListGroupContract.Effect, ListGroupContract.Event>() {

    /**
     * Stateの初期化
     *
     * @return 初期化されたState
     */
    override fun initState(): ListGroupContract.State {
        return ListGroupContract.State()
    }

    /**
     * アクションとUIロジクの紐付け
     *
     * @param event アクション
     */
    override fun handleEvents(event: ListGroupContract.Event) = when (event) {
        is ListGroupContract.Event.AddTextList -> addTextList()
        is ListGroupContract.Event.DeleteTextList -> deleteTextList()
    }

    /**
     * テキストリスト追加
     *
     */
    private fun addTextList() {
        val size = state.value.textList.size + 1
        val list = state.value.textList.toMutableList()
        list += size.toString() + "こ目"
        setState { copy(textList = list) }
    }

    /**
     * テキストリスト削除
     *
     */
    private fun deleteTextList() {
        if(state.value.textList.isNotEmpty()) {
            val list = state.value.textList.toMutableList()
            list.removeAt(state.value.textList.size - 1)
            setState { copy(textList = list) }
        }
    }


}
