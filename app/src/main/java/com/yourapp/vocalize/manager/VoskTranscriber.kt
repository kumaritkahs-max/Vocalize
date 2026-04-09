package com.yourapp.vocalize.manager

import android.content.Context
import org.vosk.Model
import org.vosk.Recognizer
import org.vosk.android.StorageService
import java.io.File
import java.io.FileInputStream
import javax.inject.Inject

class VoskTranscriber @Inject constructor(private val context: Context) {

    private var model: Model? = null

    init {
        // Load model if available
        val modelDir = File(context.filesDir, "models/vosk-en")
        if (modelDir.exists()) {
            model = Model(modelDir.absolutePath)
        }
    }

    suspend fun transcribe(filePath: String): String {
        model ?: return ""

        val recognizer = Recognizer(model, 16000f)
        val inputStream = FileInputStream(filePath)
        val buffer = ByteArray(4096)

        var result = ""
        var nbytes: Int
        while (inputStream.read(buffer).also { nbytes = it } >= 0) {
            if (recognizer.acceptWaveForm(buffer, nbytes)) {
                result += recognizer.result
            } else {
                recognizer.partialResult
            }
        }
        result += recognizer.finalResult
        recognizer.close()
        inputStream.close()

        return result
    }

    fun downloadModel() {
        // TODO: Download model from Vosk server
        StorageService.unpack(context, "model-en-us", "model", { modelPath ->
            model = Model(modelPath)
        }, { exception ->
            // Handle error
        })
    }
}
