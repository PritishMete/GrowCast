package com.example.growcast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Calendar;


public class user extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    /* access modifiers changed from: private */
    public TextView UserName;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private Button back;

    /* renamed from: dp */
    private ImageView f0dp;
    private Button logout;
    private Uri mImageUri;
    private TextView mail;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://growcast-678b1-default-rtdb.firebaseio.com/").child("Users");
    private ViewStub signup;
    private String uName;
    private Button upload;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.user);
        this.uName = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.upload = (Button) findViewById(R.id.upload);
        this.f0dp = (ImageView) findViewById(R.id.dp);
        this.back = (Button) findViewById(R.id.back);
        this.logout = (Button) findViewById(R.id.logout);
        this.UserName = (TextView) findViewById(R.id.userName);
        this.reference.child(this.uName).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                String name = (String) snapshot.child("userName").getValue(String.class);
                user.this.UserName.setText(name);
            }

            public void onCancelled(DatabaseError error) {
            }
        });
        this.upload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                user.this.openFileChooser();
            }
        });

        this.logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                SharedPreferences.Editor editor = user.this.getSharedPreferences("loginPreferences", 0).edit();
                editor.remove("rememberDevice");
                editor.apply();
                user.this.startActivity(new Intent(user.this, login.class));
                user.this.finish();
            }
        });

    }
    public void onAddReminderButtonClick(View view) {
        // Set the reminder details
        String reminderTitle = "Reminder Title";

        // Create an intent to open the calendar app
        Intent calendarIntent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, System.currentTimeMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, System.currentTimeMillis() + 30000) // Set the duration (1 hour in this case)
                .putExtra(CalendarContract.Events.TITLE, reminderTitle)
                .putExtra(CalendarContract.Events.DESCRIPTION, "Reminder Description")
                .putExtra(CalendarContract.Events.EVENT_LOCATION, "Event Location");

        // Start the calendar app
        startActivity(calendarIntent);

        // Schedule the alarm
        scheduleAlarm(this, reminderTitle);
    }

    private void scheduleAlarm(user context, String alarmTitle) {
        // Set the alarm time (1 minute from the current time for demonstration purposes)
        Calendar alarmTime = Calendar.getInstance();
        alarmTime.add(Calendar.MINUTE, 1);

        // Create an intent for the alarm receiver
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("alarmTitle", alarmTitle);

        // Create a pending intent to be triggered when the alarm fires
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get the AlarmManager service
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Set the alarm using the AlarmManager
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(), pendingIntent);
        }

        // Display a toast message indicating that the alarm has been scheduled
        Toast.makeText(context, "Alarm scheduled: " + alarmTitle, Toast.LENGTH_SHORT).show();
    }

    private void shareApkFile() {
        try {
            // Get the path of the APK file
            String apkFilePath = getApplicationInfo().sourceDir;
            File apkFile = new File(apkFilePath);

            // Get the URI using FileProvider
            Uri apkUri = FileProvider.getUriForFile(this,
                    BuildConfig.APPLICATION_ID + ".fileprovider", apkFile);

            // Create a new intent with ACTION_SEND
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("application/vnd.android.package-archive");
            intent.putExtra(Intent.EXTRA_STREAM, apkUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            // Start the activity with the share intent
            startActivity(Intent.createChooser(intent, "Share APK"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /* access modifiers changed from: private */
    public void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.GET_CONTENT");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == -1 && data != null && data.getData() != null) {
            this.mImageUri = data.getData();
            Picasso.get().load(this.mImageUri).into(this.f0dp);
        }
    }
}
