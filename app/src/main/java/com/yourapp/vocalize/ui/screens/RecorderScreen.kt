package com.yourapp.vocalize.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import android.app.Activity
import com.yourapp.vocalize.manager.PermissionsHelper
import com.yourapp.vocalize.ui.components.WaveformView
import com.yourapp.vocalize.viewmodel.RecorderViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RecorderScreen(
    onCancel: () -> Unit,
    onSaved: () -> Unit,
    viewModel: RecorderViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val isRecording = remember { mutableStateOf(false) }
    val title = remember { mutableStateOf("") }
    val note = remember { mutableStateOf("") }
    val amplitude = remember { mutableStateOf(0) }

    val buttonColor = animateColorAsState(targetValue = if (isRecording.value) Color.Red else MaterialTheme.colorScheme.primary)

    LaunchedEffect(isRecording.value) {
        while (isRecording.value) {
            amplitude.value = viewModel.getAmplitude()
            delay(100)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.releaseRecorder()
        }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                IconButton(onClick = onCancel) {
                    Icon(Icons.Default.Close, contentDescription = "Cancel")
                }
                IconButton(onClick = {
                    scope.launch {
                        viewModel.saveRecording(title.value, note.value)
                        onSaved()
                    }
                }) {
                    Icon(Icons.Default.Check, contentDescription = "Save")
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                WaveformView(isRecording = isRecording.value, amplitude = amplitude.value)
            }

            Spacer(modifier = Modifier.height(28.dp))

            Box(
                modifier = Modifier
                    .size(92.dp)
                    .clip(CircleShape)
                    .background(buttonColor.value)
                    .clickable {
                        if (!isRecording.value) {
                            if (PermissionsHelper().hasRecordingPermission(context)) {
                                viewModel.startRecording()
                                isRecording.value = true
                            } else {
                                PermissionsHelper().requestRecordingPermission(context as Activity)
                            }
                        } else {
                            viewModel.stopRecording()
                            isRecording.value = false
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Record",
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Memo title", fontWeight = FontWeight.Bold)
            TextField(value = title.value, onValueChange = { title.value = it }, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Notes", fontWeight = FontWeight.Bold)
            TextField(value = note.value, onValueChange = { note.value = it }, modifier = Modifier.fillMaxWidth(), placeholder = { Text("Add a quick note") })
        }
    }
}
