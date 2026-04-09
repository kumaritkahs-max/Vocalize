package com.yourapp.vocalize.manager

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.google.api.services.drive.model.File as DriveFile
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import javax.inject.Inject

class BackupManager @Inject constructor(@ApplicationContext private val context: Context) {

    private val driveService: Drive? by lazy {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        account?.let {
            val credential = GoogleAccountCredential.usingOAuth2(context, listOf(DriveScopes.DRIVE_APPDATA))
            credential.selectedAccount = account.account
            Drive.Builder(NetHttpTransport(), GsonFactory(), credential).setApplicationName("Vocalize").build()
        }
    }

    suspend fun backupNow() = withContext(Dispatchers.IO) {
        driveService?.let { drive ->
            // Create backup folder
            val folder = DriveFile().apply {
                name = "Vocalize Backup ${System.currentTimeMillis()}"
                mimeType = "application/vnd.google-apps.folder"
                parents = listOf("appDataFolder")
            }
            val folderId = drive.files().create(folder).setFields("id").execute().id

            // Backup database
            val dbFile = context.getDatabasePath("vocalize.db")
            uploadFile(drive, dbFile, folderId, "database.db")

            // Backup recordings (zip them)
            // TODO: zip recordings and upload
        }
    }

    suspend fun restoreLatest() = withContext(Dispatchers.IO) {
        driveService?.let { drive ->
            val files = drive.files().list()
                .setSpaces("appDataFolder")
                .setFields("files(id, name, modifiedTime)")
                .execute().files
            // Find latest backup and download
            // TODO: implement restore logic
        }
    }

    private fun uploadFile(drive: Drive, file: File, folderId: String, name: String) {
        val driveFile = DriveFile().apply {
            this.name = name
            parents = listOf(folderId)
        }
        FileInputStream(file).use { input ->
            drive.files().create(driveFile, com.google.api.client.http.FileContent("application/octet-stream", file)).execute()
        }
    }
}
