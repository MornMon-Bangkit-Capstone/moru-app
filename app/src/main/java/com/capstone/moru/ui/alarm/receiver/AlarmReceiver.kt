package com.capstone.moru.ui.alarm.receiver

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.capstone.moru.R
import com.capstone.moru.ui.MainActivity
import com.capstone.moru.ui.alarm.AlarmActivity
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    private val channelId = "Channel_1"
    private val channelName = "Alarm Manager Channel"

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action

        if (action == ACTION_SKIP_ALARM || action == ACTION_START_ALARM) {
            cancelAlarm(context)
            val notificationManagerCompat =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManagerCompat.cancel(ID_ONETIME)
        } else {
            val routineName = intent.getStringExtra(ROUTINE_NAME)
            val routineDetail = intent.getStringExtra(ROUTINE_DETAIL)
            showNotification(context, routineName!!, routineDetail!!)
        }
    }

    fun createNotificationChannel(context: Context) {
        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        )

        channel.description = "This is default channel used for all other notifications"
        channel.enableVibration(true)
        channel.enableLights(true)
        channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)

        val notificationManagerCompat =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManagerCompat.createNotificationChannel(channel)
    }

    private fun showNotification(context: Context, routineName: String, routineDetail: String) {

        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)

        val startRoutineIntent = Intent(context, AlarmActivity::class.java)
        startRoutineIntent.action = ACTION_START_ALARM
        val startPendingIntent = PendingIntent.getActivity(
            context,
            REQUEST_CODE_START,
            startRoutineIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder =
            NotificationCompat.Builder(context, channelId).setSmallIcon(R.drawable.ic_run_24)
                .setContentTitle(routineName).setContentText(routineDetail)
                .setColor(ContextCompat.getColor(context, android.R.color.white))
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                .setSound(alarmSound)
                .addAction(R.drawable.ic_calendar_month_24, "START", startPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        builder.setChannelId(channelId)
        builder.setAutoCancel(true)
        builder.setContentIntent(startPendingIntent)

        val notification = builder.build()
        notification.flags = NotificationCompat.FLAG_AUTO_CANCEL

        val notificationManagerCompat =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManagerCompat.notify(ID_ONETIME, notification)
    }

    fun setOneTimeAlarm(
        context: Context,
        date: String,
        routineName: String,
        routineDetail: String,
    ) {
        Log.e("ALARM", routineDetail)

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)

        intent.putExtra(ROUTINE_NAME, routineName)
        intent.putExtra(ROUTINE_DETAIL, routineDetail)

        val dateArray = date.split("-").toTypedArray()
        val timeArray = routineDetail.split(":").toTypedArray()

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, Integer.parseInt(dateArray[0]))
        calendar.set(Calendar.MONTH, Integer.parseInt(dateArray[1]) - 1)
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateArray[2]))
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent =
            PendingIntent.getBroadcast(context, ID_ONETIME, intent, PendingIntent.FLAG_IMMUTABLE)

        Log.e("CHECK ALARM", calendar.toString())
        Log.e("CHECK ALARM2", calendar.timeInMillis.toString())

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }

    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            ID_ONETIME,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
    }

    companion object {
        private const val ROUTINE_NAME = "routine_name"
        private const val ROUTINE_DETAIL = "routine_detail"
        private const val ID_ONETIME = 100
        private const val REQUEST_CODE_START = 1
        private const val ACTION_SKIP_ALARM = "com.capstone.moru.action.SKIP_ALARM"
        private const val ACTION_START_ALARM = "com.capstone.moru.action.START_ALARM"
    }
}