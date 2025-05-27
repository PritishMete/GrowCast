package com.example.growcast;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {
    private FirebaseAuth auth;
    /* access modifiers changed from: private */
    public EditText mail;
    /* access modifiers changed from: private */
    public EditText name;
    /* access modifiers changed from: private */
    public EditText pass;
    /* access modifiers changed from: private */
    public DatabaseReference reference;
    /* access modifiers changed from: private */
    public EditText repass;
    private Button sign;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.signup);
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
        repassEye.setOnClickListener(new View.OnClickListener() {
            boolean isPasswordVisible = false;

            @Override
            public void onClick(View v) {
                isPasswordVisible = !isPasswordVisible;
                if (isPasswordVisible) {
                    // Show password
                    repass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    repassEye.setImageResource(R.drawable.eye2);
                } else {
                    // Hide password
                    repass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    repassEye.setImageResource(R.drawable.eye1);
                }
                // Move cursor to the end of the text
                repass.setSelection(repass.getText().length());
            }
        });
        init();
        this.sign.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String userName = signup.this.name.getText().toString();
                String email = signup.this.mail.getText().toString();
                String password = signup.this.pass.getText().toString();
                String repeatPass = signup.this.repass.getText().toString();
                if (userName.isEmpty() || email.isEmpty() || password.isEmpty() || repeatPass.isEmpty()) {
                    Toast.makeText(signup.this.getApplicationContext(), "Fill up the page", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(repeatPass)) {
                    Toast.makeText(signup.this.getApplicationContext(), "password doesn't match", Toast.LENGTH_SHORT).show();
                } else {
                    signup.this.registerNewUser(email, password, userName);
                    signup.this.reference.addChildEventListener(new ChildEventListener() {
                        public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                            signup.this.finish();
                        }

                        public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
                        }

                        public void onChildRemoved(DataSnapshot snapshot) {
                        }

                        public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
                        }

                        public void onCancelled(DatabaseError error) {
                        }
                    });
                }
            }
        });
    }

    private void init() {
        this.name = (EditText) findViewById(R.id.name);
        this.mail = (EditText) findViewById(R.id.mail);
        this.pass = (EditText) findViewById(R.id.pass);
        this.repass = (EditText) findViewById(R.id.repass);
        this.sign = (Button) findViewById(R.id.sgn);
        this.reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://growcast-678b1-default-rtdb.firebaseio.com/").child("Users");
        this.auth = FirebaseAuth.getInstance();
    }

    /* access modifiers changed from: private */
    public void registerNewUser(final String email, final String password, final String userName) {
        this.auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(signup.this.getApplicationContext(), "Registration successful!", Toast.LENGTH_SHORT).show();
                    signup.this.reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(new RegUserDet(userName, email.replace(".", ""), password));

                    return;
                }
                Toast.makeText(signup.this.getApplicationContext(), "Registration failed!! Please try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
