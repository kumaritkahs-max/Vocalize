package com.yourapp.vocalize.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourapp.vocalize.data.AudioFileManager
import com.yourapp.vocalize.data.AudioRecorderManager
import com.yourapp.vocalize.data.db.AppDatabase
import com.yourapp.vocalize.data.model.Memo
import com.yourapp.vocalize.manager.VoskTranscriber
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RecorderViewModel @Inject constructor(
    private val audioRecorderManager: AudioRecorderManager,
    private val audioFileManager: AudioFileManager,
    private val voskTranscriber: VoskTranscriber,
    private val appDatabase: AppDatabase
) : ViewModel() {

    fun startRecording() {
        val outputFile = audioFileManager.createRecordingFile()
        audioRecorderManager.startRecording(outputFile.absolutePath)
    }

    fun stopRecording(): String? {
        return audioRecorderManager.stopRecording()
    }

    fun getAmplitude(): Int {
        return audioRecorderManager.getAmplitude()
    }

    fun releaseRecorder() {
        audioRecorderManager.release()
    }

    suspend fun saveRecording(title: String, note: String) {
        val filePath = stopRecording() ?: return
        val duration = calculateDuration(filePath)
        val memo = Memo(
            title = title.ifBlank { "Voice Memo" },
            filePath = filePath,
            duration = duration,
            dateCreated = System.currentTimeMillis(),
            dateModified = System.currentTimeMillis(),
            textNote = note
        )
        appDatabase.memoDao().insertMemo(memo)

        // Start transcription in background
        viewModelScope.launch {
            val transcription = voskTranscriber.transcribe(filePath)
            // Update memo with transcription
            val updatedMemo = memo.copy(transcription = transcription)
            appDatabase.memoDao().insertMemo(updatedMemo)
        }
    }

    private fun calculateDuration(filePath: String): Long {
        val mediaPlayer = android.media.MediaPlayer()
        mediaPlayer.setDataSource(filePath)
        mediaPlayer.prepare()
        val duration = mediaPlayer.duration.toLong()
        mediaPlayer.release()
        return duration
    }
}
