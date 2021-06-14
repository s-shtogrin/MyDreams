package com.example.mydreams.notifications.receivers;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;

import com.example.mydreams.R;
import com.example.mydreams.create_dream.CreateDreamActivity;

import static com.example.mydreams.notifications.AppNotificationManager.ACTION_REPLY;
import static com.example.mydreams.notifications.AppNotificationManager.CHANNEL_ID_DREAMS;
import static com.example.mydreams.notifications.AppNotificationManager.EXTRA_TEXT_REPLY;

public class AppNotificationReceiver extends BroadcastReceiver {

    public static final int notificationId = 1;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (ACTION_REPLY.equals(intent.getAction())) {
            CharSequence replyText = null;
            Bundle results = RemoteInput.getResultsFromIntent(intent);
            if (results != null) {
                replyText = results.getCharSequence(EXTRA_TEXT_REPLY);

                Toast.makeText(context, replyText, Toast.LENGTH_SHORT).show();

                //Intent newIntent = new Intent(context, MainActivity.class);
                Intent newIntent = new Intent(context, CreateDreamActivity.class);
                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                newIntent.putExtra("mode", "create_from_notification");
                newIntent.putExtra("replyText", replyText);

                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, newIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationManagerCompat manager = NotificationManagerCompat.from(context);

                NotificationCompat.Builder builder =  new NotificationCompat.Builder(context, CHANNEL_ID_DREAMS)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(pendingIntent)
                        //.setSmallIcon(R.drawable.ic_notifications_active)
                        .setContentIntent(pendingIntent)
                        .setContentText("Replied");

                Notification notification = builder.build();
                manager.notify(notificationId, notification);
            }
        }
    }
}
