package com.example.mydreams.notifications.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.mydreams.notifications.AppNotificationManager;

public class AppAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        AppNotificationManager appNotificationManager = AppNotificationManager.getInstance();
        appNotificationManager.showNotification(context);
        Toast.makeText(context, "Alarm", Toast.LENGTH_SHORT).show();
    }
}