package com.yourapp.vocalize.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlist_table")
data class Playlist(
    @PrimaryKey val id: String,
    val name: String,
    val createdAt: Long
)
