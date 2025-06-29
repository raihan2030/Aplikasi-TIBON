package com.example.tibon.service

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.tibon.data.local.AppDao
import com.example.tibon.data.local.NotificationHistory
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import java.text.SimpleDateFormat
import java.util.*

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val dao: AppDao
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        try {
            val accounts = dao.getAllAccounts().first()
            val calendar = Calendar.getInstance()
            val todayName = SimpleDateFormat("EEEE", Locale.ENGLISH).format(calendar.time).uppercase()
            val currentHour = calendar.get(Calendar.HOUR_OF_DAY)

            accounts.forEach { account ->
                val schedule = account.notificationSchedule
                val notificationHour = account.notificationHour

                val shouldNotifyToday = (schedule == "DAILY" || schedule.contains(todayName))

                if (shouldNotifyToday && currentHour == notificationHour) {
                    val title = "Pengingat Keuangan"
                    val message = "Jangan lupa catat transaksi untuk rekening '${account.name}' hari ini!"

                    NotificationHelper.showNotification(applicationContext, title, message)

                    dao.insertNotificationHistory(
                        NotificationHistory(title = title, message = message, timestamp = Date())
                    )
                }
            }
            return Result.success()
        } catch (e: Exception) {
            Firebase.crashlytics.recordException(e)
            return Result.failure()
        }
    }
}