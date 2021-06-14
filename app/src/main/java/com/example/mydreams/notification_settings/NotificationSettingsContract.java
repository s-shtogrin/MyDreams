package com.example.mydreams.notification_settings;

import android.app.Activity;
import android.content.Context;

public interface NotificationSettingsContract {

    interface View {
        void notificationOn();
        void notificationOff();
    }

    interface Presenter {
        void checkNotificationStatus();
        void setAlarm(Context context, long millis);
        void removeNotification(Context context);
    }

    interface onSettingsListener {
        void onSuccess(String message);
        void onFailure(String message);
    }
}
