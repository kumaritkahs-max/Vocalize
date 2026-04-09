package com.yourapp.vocalize.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "memo_table")
data class Memo(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val title: String,
    val filePath: String,
    val duration: Long,
    val dateCreated: Long,
    val dateModified: Long,
    val hasReminder: Boolean = false,
    val reminderTime: Long? = null,
    val repeatType: String = "NONE",
    val customDays: String? = null,
    val categoryId: String? = null,
    val textNote: String = "",
    val transcription: String = ""
)
