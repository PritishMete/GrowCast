package com.example.growcast;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class login extends AppCompatActivity {
    /* access modifiers changed from: private */
    public FirebaseAuth auth;
    private Button forgot;
    private Button login;
    private EditText pass;
    private DatabaseReference reference;
    /* access modifiers changed from: private */
    public CheckBox rememberCheckBox;
    /* access modifiers changed from: private */
    public SharedPreferences sharedPreferences;
    private Button signup;
    private EditText user;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        FirebaseUser currentUser;
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.login);
        ImageView passEye = findViewById(R.id.pass_eye);
        ImageView repassEye = findViewById(R.id.repass_eye);
        passEye.setOnClickListener(new View.OnClickListener() {
            boolean isPasswordVisible = false;

            @Override
            public void onClick(View v) {
                isPasswordVisible = !isPasswordVisible;
                if (isPasswordVisible) {
                    // Show password
                    pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passEye.setImageResource(R.drawable.eye2);
                } else {
                    // Hide password
                    pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passEye.setImageResource(R.drawable.eye1);
                }
                // Move cursor to the end of the text
                pass.setSelection(pass.getText().length());
            }
        });
        init();
        if (this.sharedPreferences.getBoolean("rememberDevice", false) && (currentUser = this.auth.getCurrentUser()) != null) {
            Intent intent = new Intent(this, page1.class);
            intent.putExtra("user", currentUser.getUid());
            startActivity(intent);
            finish();
        }
    }

    private void init() {
        this.user = (EditText) findViewById(R.id.user);
        this.pass = (EditText) findViewById(R.id.pass);
        this.forgot = (Button) findViewById(R.id.forgot);
        this.signup = (Button) findViewById(R.id.signup);
        this.login = (Button) findViewById(R.id.login);
        this.rememberCheckBox = (CheckBox) findViewById(R.id.rememberCheckBox);
        this.reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://growcast-678b1-default-rtdb.firebaseio.com/").child("Users");
        this.auth = FirebaseAuth.getInstance();
        this.sharedPreferences = getSharedPreferences("loginPreferences", 0);
    }

    public void performRegistration(View v) {
        startActivity(new Intent(this, signup.class));
    }

    public void forgot(View v) {
        startActivity(new Intent(this, forgot.class));
    }

    public void login(View v) {
        String u = this.user.getText().toString().trim();
        String p = this.pass.getText().toString().trim();
        findViewById(R.id.a).startAnimation(AnimationUtils.loadAnimation(this, R.anim.vanish));
        loginUserAccount(u, p);
    }

    private void loginUserAccount(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email!!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!!", Toast.LENGTH_SHORT).show();
        } else {
            this.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                public void onComplete(Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        String uid = login.this.auth.getCurrentUser().getUid();
                        if (login.this.rememberCheckBox.isChecked()) {
                            SharedPreferences.Editor editor = login.this.sharedPreferences.edit();
                            editor.putBoolean("rememberDevice", true);
                            editor.apply();
                        } else {
                            SharedPreferences.Editor editor2 = login.this.sharedPreferences.edit();
                            editor2.putBoolean("rememberDevice", false);
                            editor2.clear();
                            editor2.apply();
                        }
                        Toast.makeText(login.this.getApplicationContext(), "Login successful!!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(login.this, page1.class);
                        intent.putExtra("user", uid);
                        login.this.startActivity(intent);
                        login.this.finish();
                        return;
                    }
                    Toast.makeText(login.this.getApplicationContext(), "Login failed!!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
