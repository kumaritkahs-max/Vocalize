package com.yourapp.vocalize.data

import android.content.Context
import java.io.File

class AudioFileManager(private val context: Context) {
    fun getRecordingDirectory(): File {
        val folder = File(context.filesDir, "recordings")
        if (!folder.exists()) folder.mkdirs()
        return folder
    }

    fun createRecordingFile(): File {
        val timestamp = System.currentTimeMillis()
        return File(getRecordingDirectory(), "${timestamp}.m4a")
    }

    fun deleteRecording(path: String) {
        File(path).takeIf { it.exists() }?.delete()
    }
}
