//import android.app.AlarmManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.provider.CalendarContract;
//import android.widget.Toast;
//
//import java.util.Calendar;
//
//public void onAddReminderButtonClick(View view) {
//        // Set the reminder details
//        String reminderTitle = "Reminder Title";
//
//        // Create an intent to open the calendar app
//        Intent calendarIntent = new Intent(Intent.ACTION_INSERT)
//        .setData(CalendarContract.Events.CONTENT_URI)
//        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, System.currentTimeMillis())
//        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, System.currentTimeMillis() + 3600000) // Set the duration (1 hour in this case)
//        .putExtra(CalendarContract.Events.TITLE, reminderTitle)
//        .putExtra(CalendarContract.Events.DESCRIPTION, "Reminder Description")
//        .putExtra(CalendarContract.Events.EVENT_LOCATION, "Event Location");
//
//        // Start the calendar app
//        startActivity(calendarIntent);
//
//        // Schedule the alarm
//        scheduleAlarm(this, reminderTitle);
//        }
//
//private void scheduleAlarm(Context context, String alarmTitle) {
//        // Set the alarm time (1 minute from the current time for demonstration purposes)
//        Calendar alarmTime = Calendar.getInstance();
//        alarmTime.add(Calendar.MINUTE, 1);
//
//        // Create an intent for the alarm receiver
//        Intent intent = new Intent(context, AlarmReceiver.class);
//        intent.putExtra("alarmTitle", alarmTitle);
//
//        // Create a pending intent to be triggered when the alarm fires
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        // Get the AlarmManager service
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//
//        // Set the alarm using the AlarmManager
//        if (alarmManager != null) {
//        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(), pendingIntent);
//        }
//
//        // Display a toast message indicating that the alarm has been scheduled
//        Toast.makeText(context, "Alarm scheduled: " + alarmTitle, Toast.LENGTH_SHORT).show();
//        }
