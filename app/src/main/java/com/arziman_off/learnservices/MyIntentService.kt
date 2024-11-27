package com.arziman_off.learnservices

import android.app.IntentService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat


class MyIntentService : IntentService(NAME) {


    override fun onCreate() {
        super.onCreate()
        log("onCreate()")
        setIntentRedelivery(true)
        showNotification()
    }

    private fun showNotification() {
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, createNotification())
    }

    private fun createNotificationChannel() {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun createNotification() = NotificationCompat.Builder(this, CHANNEL_ID)
        .setContentTitle("Таймер")
        .setContentText("Тик-Так")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .build()

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy()")
    }

    override fun onHandleIntent(p0: Intent?) {
        log("onHandleIntent()")

        for (i in 0..5) {
            Thread.sleep(1000)
            log("Timer $i")
        }
    }

    private fun log(msg: String) {
        Log.d("SERVICE_TAG", "MyForegroundService $msg")
    }

    companion object {
        private const val CHANNEL_ID = "channel_id"
        private const val CHANNEL_NAME = "Таймер"
        private const val NAME = "MyIntentService"
        private const val NOTIFICATION_ID = 1
        fun newIntent(context: Context): Intent {
            return Intent(context, MyIntentService::class.java)
        }
    }
}
