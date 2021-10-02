package app.todo.ui

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import app.todo.R
import app.todo.ui.home.HomeActivity

class ShowNotificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val alarmNotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val contentIntent = PendingIntent.getActivity(
            this, 0,
            Intent(this, HomeActivity::class.java), 0
        )

        val alarmNotificationBuilder = NotificationCompat
            .Builder(this, "channel_id")
            .setContentTitle("Alarm")
            .setSmallIcon(R.drawable.ic_alarm)
            .setStyle(NotificationCompat.BigTextStyle().bigText("msg"))
            .setContentText("msg")

        alarmNotificationBuilder.setContentIntent(contentIntent)
        alarmNotificationManager.notify(1, alarmNotificationBuilder.build())
    }
}