package com.yourapp.vocalize.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourapp.vocalize.data.AudioPlayerManager
import com.yourapp.vocalize.data.MemoRepository
import com.yourapp.vocalize.data.model.Memo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemoDetailViewModel @Inject constructor(
    private val audioPlayerManager: AudioPlayerManager,
    private val memoRepository: MemoRepository
) : ViewModel() {

    private val _memo = MutableStateFlow<Memo?>(null)
    val memo: StateFlow<Memo?> = _memo

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private val _currentPosition = MutableStateFlow(0)
    val currentPosition: StateFlow<Int> = _currentPosition

    fun loadMemo(memoId: String) {
        viewModelScope.launch {
            // For now, assume we have the memo, but in real app, fetch by ID
        }
    }

    fun playPause(filePath: String) {
        if (_isPlaying.value) {
            audioPlayerManager.pause()
            _isPlaying.value = false
        } else {
            audioPlayerManager.play(filePath)
            _isPlaying.value = true
        }
    }

    fun seekTo(position: Int) {
        audioPlayerManager.seekTo(position)
        _currentPosition.value = position
    }

    fun updateMemo(updatedMemo: Memo) {
        viewModelScope.launch {
            memoRepository.updateMemo(updatedMemo)
            _memo.value = updatedMemo
        }
    }

    fun stopPlayback() {
        audioPlayerManager.stop()
    }
}
