package com.yourapp.vocalize.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yourapp.vocalize.data.model.Memo

@Dao
interface MemoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemo(memo: Memo)

    @Query("SELECT * FROM memo_table ORDER BY dateCreated DESC")
    suspend fun getAllMemos(): List<Memo>
}
