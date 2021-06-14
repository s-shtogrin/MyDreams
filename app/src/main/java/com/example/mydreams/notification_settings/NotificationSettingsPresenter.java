package com.example.mydreams.notification_settings;

import android.app.Activity;
import android.content.Context;

import com.example.mydreams.notifications.AppNotificationManager;
import com.example.mydreams.notifications.NotificationContract;

public class NotificationSettingsPresenter implements NotificationSettingsContract.Presenter, NotificationSettingsContract.onSettingsListener {

    private NotificationSettingsContract.View settingsView;

    private AppNotificationManager notificationManager;

    public NotificationSettingsPresenter(NotificationSettingsContract.View settingsView) {
        this.settingsView = settingsView;

        notificationManager = AppNotificationManager.getInstance();
        notificationManager.setListener(new NotificationContract.onNotificationListener() {
            @Override
            public void onStatusChanged() {
                checkNotificationStatus();
            }
        });
    }

    @Override
    public void checkNotificationStatus() {
        if (notificationManager.getNotificationStatus()) {
            settingsView.notificationOn();
        } else {
            settingsView.notificationOff();
        }
    }

    @Override
    public void setAlarm(Context context, long millis) {
        notificationManager.setAlarm(context, millis);
    }

    @Override
    public void removeNotification(Context context) {
        notificationManager.removeNotification(context);
    }

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void onFailure(String message) {

    }
}
