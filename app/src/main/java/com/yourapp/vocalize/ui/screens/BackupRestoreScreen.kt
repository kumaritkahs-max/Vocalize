package com.yourapp.vocalize.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
fun BackupRestoreScreen(
    onBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val backupStatus = viewModel.backupStatus.collectAsState().value

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.Top) {
            Text(text = "Backup & Restore", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Backup your memos to Google Drive or restore from backup.")
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Last backup: $backupStatus")
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = { scope.launch { viewModel.backupNow() } }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Backup Now")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { scope.launch { viewModel.restoreLatest() } }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Restore Latest")
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Back")
            }
        }
    }
}