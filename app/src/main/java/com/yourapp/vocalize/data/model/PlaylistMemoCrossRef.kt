package com.yourapp.vocalize.data.model

import androidx.room.Entity

@Entity(primaryKeys = ["playlistId", "memoId"])
data class PlaylistMemoCrossRef(
    val playlistId: String,
    val memoId: String
)
