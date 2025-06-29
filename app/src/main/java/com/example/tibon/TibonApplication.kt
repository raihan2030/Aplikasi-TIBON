package com.example.tibon

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.example.tibon.service.NotificationHelper
import com.example.tibon.service.NotificationWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
// [DIPERBAIKI] Kembalikan implementasi Configuration.Provider
class TibonApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    // [DIPERBAIKI] Sediakan konfigurasi melalui override ini, jangan panggil initialize() manual
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        // Panggil fungsi lain seperti biasa
        NotificationHelper.createNotificationChannel(this)
        scheduleDailyNotificationWorker()
    }

    private fun scheduleDailyNotificationWorker() {
        // Logika penjadwalan ini sudah benar
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()

        val periodicWorkRequest = PeriodicWorkRequestBuilder<NotificationWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "daily_notification_worker",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )
    }
}