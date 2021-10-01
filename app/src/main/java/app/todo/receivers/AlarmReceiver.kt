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

    override fun onReceive(context: Context, intent: Intent) {
        val alarmNotificationManager =
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
        alarmNotificationManager.notify(1, alarmNotificationBuilder.build())

//        val notificationIntent = Intent(context, HomeActivity::class.java)
//        notificationIntent.putExtra("clicked", "Notification Clicked")
//        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
//        val mBuilder = NotificationCompat.Builder(context)
//        val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//        mBuilder.setContentTitle("Reminder")
//        mBuilder.setContentText("You have new Reminders.")
//        mBuilder.setTicker("New Reminder Alert!")
//        mBuilder.setSmallIcon(R.drawable.ic_alarm)
//        mBuilder.setSound(uri)
//        mBuilder.setAutoCancel(true)
//        val inboxStyle = NotificationCompat.InboxStyle()
//        inboxStyle.setBigContentTitle("You have Reminders:")
//        mBuilder.setStyle(inboxStyle)
//        val resultIntent = Intent(context, HomeActivity::class.java)
//
//        val stackBuilder: TaskStackBuilder = TaskStackBuilder.create(context)
//        stackBuilder.addParentStack(HomeActivity::class.java)
//        stackBuilder.addNextIntent(resultIntent)
//        val resultPendingIntent: PendingIntent = stackBuilder
//            .getPendingIntent(0, PendingIntent.FLAG_CANCEL_CURRENT)
//
//        mBuilder.setContentIntent(resultPendingIntent)
//        val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        mNotificationManager.notify(999, mBuilder.build())

        Toast.makeText(
            context,
            "Todo notify that alarm is started.",
            Toast.LENGTH_LONG
        ).show()
    }
}