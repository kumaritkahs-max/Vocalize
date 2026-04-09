package com.yourapp.vocalize.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourapp.vocalize.manager.BackupManager
import com.yourapp.vocalize.manager.VoskTranscriber
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val backupManager: BackupManager,
    private val voskTranscriber: VoskTranscriber
) : ViewModel() {

    private val _offlineTranscription = MutableStateFlow(true)
    val offlineTranscription: StateFlow<Boolean> = _offlineTranscription

    private val _darkMode = MutableStateFlow(false)
    val darkMode: StateFlow<Boolean> = _darkMode

    private val _backupStatus = MutableStateFlow("Never")
    val backupStatus: StateFlow<String> = _backupStatus

    fun setOfflineTranscription(enabled: Boolean) {
        _offlineTranscription.value = enabled
    }

    fun setDarkMode(enabled: Boolean) {
        _darkMode.value = enabled
    }

    suspend fun signInToGoogle() {
        // TODO: Implement Google Sign-In
    }

    suspend fun backupNow() {
        backupManager.backupNow()
        _backupStatus.value = "Just now"
    }

    suspend fun restoreLatest() {
        backupManager.restoreLatest()
    }

    suspend fun downloadVoskModel() {
        voskTranscriber.downloadModel()
    }

    fun getUsedSpace(): String {
        // TODO: Calculate used space
        return "10 MB"
    }

    suspend fun clearCache() {
        // TODO: Clear cache
    }
}
