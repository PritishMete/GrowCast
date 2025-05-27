package com.example.growcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.io.IOException;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Play the custom alarm tone
        playCustomAlarmTone(context);

        // Show a notification or perform any other actions as needed
        showNotification(context);
    }

    private void playCustomAlarmTone(Context context) {
        try {
            // Open the sound file in assets folder
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor assetFileDescriptor = assetManager.openFd("alarm_sound.mp3");

            // Create a MediaPlayer to play the sound
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showNotification(Context context) {
        // Show a notification to indicate that the alarm has triggered
        // Customize the notification as per your requirements
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "alarm_channel_id")
                .setContentTitle("Alarm")
                .setContentText("Alarm triggered")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        // Show the notification
        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Request the missing permission or handle the case where the permission is not granted
            return;
        }
        notificationManager.notify(123, builder.build());

    }
}
