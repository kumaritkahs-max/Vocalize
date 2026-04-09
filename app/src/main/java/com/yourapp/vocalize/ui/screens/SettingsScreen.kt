package com.yourapp.vocalize.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yourapp.vocalize.viewmodel.SettingsViewModel
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onCategoryManage: () -> Unit,
    onBackupRestore: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val offlineTranscription = viewModel.offlineTranscription.collectAsState().value
    val darkMode = viewModel.darkMode.collectAsState().value
    val backupStatus = viewModel.backupStatus.collectAsState().value

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.Top) {
            Text(text = "Settings", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Account", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { scope.launch { viewModel.signInToGoogle() } }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Sign in with Google")
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Backup", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onBackupRestore, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Backup & Restore")
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Categories", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onCategoryManage, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Manage Categories")
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Voice-to-Text", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Offline transcription")
                Switch(checked = offlineTranscription, onCheckedChange = { viewModel.setOfflineTranscription(it) })
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { scope.launch { viewModel.downloadVoskModel() } }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Download English model")
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Appearance", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Dark theme")
                Switch(checked = darkMode, onCheckedChange = { viewModel.setDarkMode(it) })
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Storage", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Used space: ${viewModel.getUsedSpace()}")
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { scope.launch { viewModel.clearCache() } }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Clear cache")
            }

            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Back")
            }
        }
    }
}
