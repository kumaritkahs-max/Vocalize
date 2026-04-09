package com.yourapp.vocalize.data

import com.yourapp.vocalize.data.db.AppDatabase
import com.yourapp.vocalize.data.model.Memo
import javax.inject.Inject

class MemoRepository @Inject constructor(private val appDatabase: AppDatabase) {
    suspend fun insertMemo(memo: Memo) {
        appDatabase.memoDao().insertMemo(memo)
    }

    suspend fun updateMemo(memo: Memo) {
        appDatabase.memoDao().insertMemo(memo) // Since it's replace
    }
}