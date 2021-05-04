package ua.edu.onaft.wirelessdatatransmission_wdt.Configuration

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.WDTComponents.AlertInterfaces.ILoadAlert
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.SessionState
import ua.edu.onaft.wirelessdatatransmission_wdt.R

class LoadAlertConfiguration: ILoadAlert {

    private lateinit var notificationManager: NotificationManager
    private lateinit var notificationBuilder: NotificationCompat.Builder
    private lateinit var notificationChannel: NotificationChannel

    override fun closeAlert() {
        SessionState.activity.runOnUiThread {
            notificationManager.cancel(100)
        }
    }

    override fun setContentText(text: String) {
        SessionState.activity.runOnUiThread {
            notificationBuilder.setContentText(text)
            notificationManager.notify(100, notificationBuilder.build())
        }
    }

    override fun setPercentageOfDownload(percent: Double) {
        SessionState.activity.runOnUiThread {
            notificationBuilder.setProgress(100, (percent * 100).toInt(), false)
            notificationManager.notify(100, notificationBuilder.build())
        }
    }

    override fun setTitleAlert(title: String) {
        SessionState.activity.runOnUiThread {
            notificationBuilder.setContentTitle(title)
            notificationManager.notify(100, notificationBuilder.build())
        }
    }

    override fun showAlert() {
        SessionState.activity.runOnUiThread {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager = SessionState.activity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationChannel = NotificationChannel("LoadAlertChannel".intern(), "ch 1", NotificationManager.IMPORTANCE_LOW)

                notificationBuilder = NotificationCompat.Builder(SessionState.activity, "LoadAlertChannel".intern()).apply {
                    setSmallIcon(R.drawable.ic_logo)
                    setContentTitle("")
                    setContentText("")
                    setProgress(100, 0, false)
                }

                notificationManager.createNotificationChannel(notificationChannel)
                notificationManager.notify(100, notificationBuilder.build())
            }
        }
    }

}