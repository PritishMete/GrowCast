package com.example.growcast;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgot extends AppCompatActivity {
    private EditText mail;
    private String email;
    private Button reset;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot);

        auth=FirebaseAuth.getInstance();
        mail= (EditText) findViewById(R.id.mail);
        reset= (Button) findViewById(R.id.reset);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=mail.getText().toString();
                if (email.isEmpty()){
                    mail.setError("Required");
                }
                else {
                    auth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(forgot.this, new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(forgot.this, "check your email", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(),login.class));
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(forgot.this, "something went wrong", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });

    }



}
