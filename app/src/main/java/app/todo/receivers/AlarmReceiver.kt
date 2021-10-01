package app.todo.receivers

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationCompat
import app.todo.R
import app.todo.ui.home.HomeActivity

class AlarmReceiver : BroadcastReceiver() {

    private var alarmNotificationManager: NotificationManager? = null

    override fun onReceive(context: Context, intent: Intent) {
            alarmNotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val contentIntent = PendingIntent.getActivity(
                context, 0,
                Intent(context, HomeActivity::class.java), 0
            )

            val alarmNotificationBuilder = NotificationCompat
                .Builder(context, "channel_id")
                .setContentTitle("Alarm")
                .setSmallIcon(R.drawable.ic_alarm)
                .setStyle(NotificationCompat.BigTextStyle().bigText("msg"))
                .setContentText("msg")

            alarmNotificationBuilder.setContentIntent(contentIntent)
            alarmNotificationManager!!.notify(1, alarmNotificationBuilder.build())

        Toast.makeText(
            context,
            "If App in background then Just notify that alarm is started.",
            Toast.LENGTH_LONG
        ).show()
    }
}