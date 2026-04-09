package com.yourapp.vocalize.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MemoDetailScreen(
    memo: com.yourapp.vocalize.data.model.Memo,
    onBack: () -> Unit,
    viewModel: MemoDetailViewModel = hiltViewModel()
) {
    val memo = viewModel.memo.collectAsState().value ?: memo // Use passed memo if not loaded
    val isPlaying = viewModel.isPlaying.collectAsState().value
    val currentPosition = viewModel.currentPosition.collectAsState().value
    val duration = remember { mutableStateOf(memo.duration.toInt()) }
    val title = remember { mutableStateOf(memo.title) }
    val note = remember { mutableStateOf(memo.textNote) }
    val scope = rememberCoroutineScope()

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Row {
                    IconButton(onClick = { isEditing.value = !isEditing.value }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Share, contentDescription = "Share")
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (isEditing.value) {
                OutlinedTextField(
                    value = title.value,
                    onValueChange = { title.value = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = note.value,
                    onValueChange = { note.value = it },
                    label = { Text("Notes") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    scope.launch {
                        viewModel.updateMemo(memo.copy(title = title.value, textNote = note.value))
                        isEditing.value = false
                    }
                }) {
                    Text("Save")
                }
            } else {
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp)) {
                    Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = title.value, style = MaterialTheme.typography.titleLarge)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Waveform thumbnail here")
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = { viewModel.playPause(memo.filePath) }) {
                                Icon(if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow, contentDescription = "Play/Pause")
                            }
                            Slider(
                                value = currentPosition.toFloat(),
                                onValueChange = { viewModel.seekTo(it.toInt()) },
                                valueRange = 0f..duration.value.toFloat(),
                                modifier = Modifier.weight(1f)
                            )
                            Text(text = Utils.formatDuration(duration.value.toLong()))
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { /* Open reminder setup */ }) {
                            Text("Set Reminder")
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Transcription: ${memo.transcription}")
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Notes: ${note.value}")
                    }
                }
            }
        }
    }
}
