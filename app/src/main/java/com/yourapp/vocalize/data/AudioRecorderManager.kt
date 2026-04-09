package com.yourapp.vocalize.data

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import java.io.File

class AudioRecorderManager(private val context: Context) {
    private var mediaRecorder: MediaRecorder? = null
    private var outputFile: File? = null
    var isRecording = false
        private set

    fun startRecording(outputPath: String) {
        if (isRecording) return

        outputFile = File(outputPath)
        mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else {
            @Suppress("DEPRECATION")
            MediaRecorder()
        }.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(outputPath)
            prepare()
            start()
        }
        isRecording = true
    }

    fun stopRecording(): String? {
        if (!isRecording) return null

        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null
        isRecording = false
        return outputFile?.absolutePath
    }

    fun getAmplitude(): Int {
        return mediaRecorder?.maxAmplitude ?: 0
    }

    fun release() {
        mediaRecorder?.release()
        mediaRecorder = null
        isRecording = false
    }
}
