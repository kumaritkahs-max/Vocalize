package com.yourapp.vocalize.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yourapp.vocalize.data.model.Category
import com.yourapp.vocalize.data.model.Memo
import com.yourapp.vocalize.data.model.Playlist
import com.yourapp.vocalize.data.model.PlaylistMemoCrossRef

@Database(entities = [Memo::class, Category::class, Playlist::class, PlaylistMemoCrossRef::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun memoDao(): MemoDao
    abstract fun categoryDao(): CategoryDao
    abstract fun playlistDao(): PlaylistDao
}
