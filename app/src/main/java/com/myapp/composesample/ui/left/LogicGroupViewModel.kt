package com.myapp.composesample.ui.left

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LogicGroupViewModel : ViewModel() {

    // カウント
    private val _initCounter : MutableLiveData<Int> = MutableLiveData<Int>(0)
    val initCounter : LiveData<Int> = _initCounter

    // カウント
    private val _buttonCounter : MutableLiveData<Int> = MutableLiveData<Int>(0)
    val buttonCounter : LiveData<Int> = _buttonCounter

    // テキスト
    private val _text : MutableLiveData<String> = MutableLiveData<String>("")
    val text : LiveData<String> =_text

    /**
     * 初期表示
     *
     */
    fun createView() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                for (i in 0..10000000) {
                    _initCounter.postValue(i)
                }
            }
        }
    }

    /**
     * カウントアップ
     *
     */
    fun countUp() {
        _buttonCounter.value = _buttonCounter.value?.plus(1)
    }

    /**
     * カウントダウン
     *
     */
    fun countDown() {
        _buttonCounter.value = _buttonCounter.value?.minus(1)
    }

    /**
     * テキスト変更
     *
     * @param text テキスト変更
     */
    fun onChangeText(text: String) {
        _text.value = text
    }
}