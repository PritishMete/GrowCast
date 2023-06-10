package com.example.growcast;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    private EditText name, mail, pass, repass;
    private Button sign;

    private DatabaseReference reference;
    private FirebaseAuth auth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        init();
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String userName = name.getText().toString(); //todo ekhne username ta acche eta user page username er jaygay show korate hbe
                final String email = mail.getText().toString();
                final String password = pass.getText().toString();
                final String repeatPass = repass.getText().toString();
                if (userName.isEmpty() || email.isEmpty() || password.isEmpty() || repeatPass.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fill up the page", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(repeatPass)) {
                    Toast.makeText(getApplicationContext(), "password meleni khankir chele", Toast.LENGTH_SHORT).show();
                    return;
                }

                registerNewUser(email, password, userName);
                reference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        //each email will be a new child in Users
                        //each email will be having several parametes i.e user name ,password, and other things
                        finish();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }


        });
    }

    private void init() {
        name = (EditText) findViewById(R.id.name);
        mail = (EditText) findViewById(R.id.mail);
        pass = (EditText) findViewById(R.id.pass);
        repass = (EditText) findViewById(R.id.repass);
        sign = (Button) findViewById(R.id.sgn);
        reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://growcast-678b1-default-rtdb.firebaseio.com/")
                .child("Users");
        auth = FirebaseAuth.getInstance();
    }

    private void registerNewUser(final String email, final String password, final String userName) {


        // create new user or register new user
        auth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                            "Registration successful!",
                                            Toast.LENGTH_LONG)
                                    .show();
                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(new RegUserDet(userName, email.replace(".", ""), password));
                        } else {

                            // Registration failed
                            Toast.makeText(
                                            getApplicationContext(),
                                            "Registration failed!!"
                                                    + " Please try again later",
                                            Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
    }
}
