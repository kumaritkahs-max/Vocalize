package com.yourapp.vocalize.manager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.yourapp.vocalize.ReminderWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NotificationScheduler @Inject constructor(@ApplicationContext private val context: Context) {

    private val workManager = WorkManager.getInstance(context)

    fun scheduleReminder(memoId: String, triggerTimeMs: Long) {
        val delay = triggerTimeMs - System.currentTimeMillis()
        if (delay > 0) {
            val workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .setInputData(workDataOf("memoId" to memoId))
                .build()
            workManager.enqueue(workRequest)
        }
    }

    fun cancelReminder(memoId: String) {
        workManager.cancelAllWorkByTag(memoId)
    }
}
