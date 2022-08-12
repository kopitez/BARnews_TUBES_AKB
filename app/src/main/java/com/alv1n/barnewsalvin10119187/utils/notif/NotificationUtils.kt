package com.alv1n.barnewsalvin10119187.utils.notif

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.alv1n.barnewsalvin10119187.R

private const val NOTIFICATION_ID = 0

fun NotificationManager.sendNotification(context: Context) {
    val builder = NotificationCompat.Builder(
        context,
        context.getString(R.string.notif_channel_id)
    )
        .setSmallIcon(R.drawable.logo_barnews)
        .setContentTitle(context.getString(R.string.notif_title))
        .setContentText(context.getString(R.string.notif_message))
    createChannel(context)
    notify(NOTIFICATION_ID, builder.build())
}

private fun createChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel = NotificationChannel(
            context.getString(R.string.notif_channel_id),
            context.getString(R.string.notif_channel_name),
            NotificationManager.IMPORTANCE_HIGH
        )
        with(notificationChannel) {
            // Kita hanya menggunakan 1 notifikasi, jadi tidak perlu badge
            setShowBadge(false)
            enableLights(true)
            lightColor = Color.RED
            enableVibration(true)
            description = context.getString(R.string.notif_channel_desc)
        }
        val notificationManager = context.getSystemService(
            NotificationManager::class.java)
        notificationManager?.createNotificationChannel(notificationChannel)
    }
}