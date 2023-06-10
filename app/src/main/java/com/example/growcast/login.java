package com.example.growcast;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class login extends AppCompatActivity {

    private EditText user, pass;
    private Button login, signup, forgot;
    private CheckBox rememberCheckBox;

    private DatabaseReference reference;
    private FirebaseAuth auth;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        init();

        boolean rememberDevice = sharedPreferences.getBoolean("rememberDevice", false);

        if (rememberDevice) {
            // User has chosen to remember the device, directly open page1
            FirebaseUser currentUser = auth.getCurrentUser();
            if (currentUser != null) {
                Intent intent = new Intent(login.this, page1.class);
                intent.putExtra("user", currentUser.getUid());
                startActivity(intent);
                finish();
            }
        }
    }

    private void init() {
        user = findViewById(R.id.user);
        pass = findViewById(R.id.pass);
        forgot = findViewById(R.id.forgot);
        signup = findViewById(R.id.signup);
        login = findViewById(R.id.login);
        rememberCheckBox = findViewById(R.id.rememberCheckBox);
        reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://growcast-678b1-default-rtdb.firebaseio.com/").child("Users");
        auth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("loginPreferences", MODE_PRIVATE);
    }

    public void performRegistration(View v) {
        Intent intent = new Intent(this, signup.class);
        startActivity(intent);
    }

    public void forgot(View v) {
        Intent intent = new Intent(this, forgot.class);
        startActivity(intent);
    }

    public void login(View v) {
        final String u = user.getText().toString().trim();
        final String p = pass.getText().toString().trim();

        View view = findViewById(R.id.a);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.vanish);
        view.startAnimation(animation);
        loginUserAccount(u, p);
    }

    private void loginUserAccount(final String email, final String password) {
        // Validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter email!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter password!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String uid = auth.getCurrentUser().getUid();

                            if (rememberCheckBox.isChecked()) {
                                // Save the login preferences if Remember Device is checked
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("rememberDevice", true);
                                editor.apply();
                            } else {
                                // Clear the login preferences if Remember Device is unchecked
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("rememberDevice", false);
                                editor.clear();
                                editor.apply();
                            }

                            Toast.makeText(getApplicationContext(),
                                            "Login successful!!",
                                            Toast.LENGTH_LONG)
                                    .show();

                            // If sign-in is successful, intent to home activity
                            Intent intent = new Intent(login.this, page1.class);
                            intent.putExtra("user", uid);
                            startActivity(intent);
                            finish();
                        } else {
                            // Sign-in failed
                            Toast.makeText(getApplicationContext(),
                                            "Login failed!!",
                                            Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
    }
}
