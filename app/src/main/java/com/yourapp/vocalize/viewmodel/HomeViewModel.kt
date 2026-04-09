package com.yourapp.vocalize.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourapp.vocalize.data.MemoRepository
import com.yourapp.vocalize.data.model.Memo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val memoRepository: MemoRepository) : ViewModel() {

    private val _memos = MutableStateFlow<List<Memo>>(emptyList())
    val memos: StateFlow<List<Memo>> = _memos

    init {
        loadMemos()
    }

    private fun loadMemos() {
        viewModelScope.launch {
            _memos.value = memoRepository.getAllMemos()
        }
    }
}
