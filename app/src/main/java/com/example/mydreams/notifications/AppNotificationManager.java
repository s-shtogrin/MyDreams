package com.example.mydreams.notifications;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;

import com.example.mydreams.DateConverter;
import com.example.mydreams.R;
import com.example.mydreams.notifications.receivers.AppAlarmReceiver;
import com.example.mydreams.notifications.receivers.AppNotificationReceiver;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static android.app.PendingIntent.FLAG_CANCEL_CURRENT;
import static android.content.Context.ALARM_SERVICE;

public class AppNotificationManager {

    private static AppNotificationManager instance;
    private NotificationContract.onNotificationListener onNotificationListener;

    public static final String CHANNEL_ID_DREAMS = "mydreams";
    public static final String EXTRA_TEXT_REPLY = "dream_content";
    public static final String ACTION_REPLY = "reply";

    public static final int notificationId = 1;

    private Boolean notificationStatus = false;

    private AppNotificationManager() {
        this.onNotificationListener = null;
    }

    public static AppNotificationManager getInstance() {
        if (instance == null) {
            instance = new AppNotificationManager();
        }
        return instance;
    }

    // todo: detach
    public void setListener(NotificationContract.onNotificationListener onNotificationListener) {
        this.onNotificationListener = onNotificationListener;
    }

    public Boolean getNotificationStatus() {
        return notificationStatus;
    }

    public void setUpNotificationChannels(Context context) {
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID_DREAMS, "MyDreams notifications", NotificationManager.IMPORTANCE_DEFAULT);

        channel.enableLights(false);
        manager.createNotificationChannel(channel);
    }

    public void showNotification(Context context) {
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);

        Intent intent = new Intent(context, AppNotificationReceiver.class);
        intent.setAction(ACTION_REPLY);

        PendingIntent replyPendingIntent = PendingIntent
                .getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteInput remoteInput = new RemoteInput
                .Builder(EXTRA_TEXT_REPLY)
                .build();

        NotificationCompat.Action action = new NotificationCompat
                .Action
                .Builder(android.R.drawable.ic_menu_save, context.getString(R.string.notification_question), replyPendingIntent)
                .addRemoteInput(remoteInput)
                .build();

        NotificationCompat.Builder builder =  new NotificationCompat.Builder(context, CHANNEL_ID_DREAMS)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentIntent(replyPendingIntent)
                .addAction(action)
                .setAutoCancel(false); // setAutoCancel() automatically removes the notification
                                       // when the user taps it

        Notification notification = builder.build();
        manager.notify(notificationId, notification);
    }

    public void removeNotification(Context context) {
        Intent intent = new Intent(context, AppAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Objects.requireNonNull(alarmManager).cancel(pendingIntent);

        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.cancel(notificationId);

        notificationStatus = false;
        onNotificationListener.onStatusChanged();

        Toast.makeText(context, "Уведомление отменено", Toast.LENGTH_SHORT).show();
    }

    public void setAlarm(Context context, long millis) {
        Intent intent = new Intent(context, AppAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Objects.requireNonNull(alarmManager)
                .setRepeating(AlarmManager.RTC_WAKEUP, millis, AlarmManager.INTERVAL_DAY, pendingIntent);

        notificationStatus = true;
        onNotificationListener.onStatusChanged();

        //Toast.makeText(context, "Уведомление установлено", Toast.LENGTH_SHORT).show();
        String notificationTime = "Время уведомления: " + DateConverter.getTimeFromMillis(millis);
        Toast.makeText(context, notificationTime, Toast.LENGTH_SHORT).show();
    }
}
