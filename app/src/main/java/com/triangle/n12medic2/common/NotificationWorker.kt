package com.triangle.n12medic2.common

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.triangle.n12medic2.R
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import kotlin.random.Random

// Класс для работы с уведомлениями
// Дата создания: 14.03.2023 14:12
// Автор: Triangle
class NotificationWorker(
    val context: Context? = null,
    val workerParams: WorkerParameters? = null
): Worker(context!!, workerParams!!) {

    override fun doWork(): Result {
        val channel = NotificationChannel("c1", "Channel", NotificationManager.IMPORTANCE_HIGH)
        val manager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(context, "c1")
            .setSmallIcon(R.drawable.ic_app_foreground)
            .setContentTitle("Напоминание о записи")
            .setContentText("Напоминание о записи")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(context)) {
            notify(Random.nextInt(), notification.build())
        }

        return Result.success()
    }
}

// Отправка уведомлений
// Дата создания: 14.03.2023 14:12
// Автор: Triangle
fun scheduleNotification(context: Context, date: LocalDateTime) {
    val currentDateTime = LocalDateTime.now()

    val delay = Duration.between(currentDateTime, date)
    Log.d(TAG, "scheduleNotification: ${delay.seconds}")

    Log.d(TAG, "scheduleNotification: ${currentDateTime}")
    Log.d(TAG, "scheduleNotification: ${date}")

    val work = OneTimeWorkRequestBuilder<NotificationWorker>()
        .setInitialDelay(delay.seconds, TimeUnit.SECONDS)
        .addTag("NOTIFICATION")
        .build()

    WorkManager.getInstance(context).enqueue(work)
}
