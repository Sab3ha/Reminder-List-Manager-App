package edu.qc.seclass.rlm;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.core.app.NotificationCompat;

import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.UUID;

public class Alarm extends BroadcastReceiver {


    public static void create(Context context, Reminder reminder) throws ParseException {

        Intent intent = new Intent(context, Alarm.class);

        intent.putExtra("id", reminder.getReminder_id());
        intent.putExtra("text", reminder.getReminderName());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, reminder.getReminder_id(), intent, PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        String dateTime = reminder.getDate() + " " + reminder.getTime();
        DateTimeFormatter dtFormatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("M/d/y h:mma")
                .toFormatter().withZone(ZoneOffset.systemDefault());
        ZonedDateTime dt = ZonedDateTime.parse(dateTime, dtFormatter);

        alarmManager.cancel(pendingIntent);
        alarmManager.set(AlarmManager.RTC_WAKEUP, dt.toInstant().toEpochMilli(), pendingIntent);

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("Alarm", "Attempting to trigger reminder alert...");

        int id = intent.getIntExtra("id", 0);
        String text = intent.getStringExtra("text");

        Intent notifIntent = new Intent(context, MainActivity.class);

        notifIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

        String channelId = UUID.randomUUID().toString();
        PendingIntent contentIntent = PendingIntent.getActivity(context, id, notifIntent, PendingIntent.FLAG_IMMUTABLE);
        NotificationManager notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(channelId, context.getResources().getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH);

        notifManager.createNotificationChannel(channel);

        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(text);

        notifBuilder.setContentIntent(contentIntent);
        notifBuilder.setAutoCancel(true);
        notifManager.notify(1, notifBuilder.build());

    }

}
