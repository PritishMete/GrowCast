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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.regex.Pattern;

public class signup extends AppCompatActivity {
    private EditText name,mail,pass,repass;
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

                final String userName=name.getText().toString(); //todo ekhne username ta acche eta user page username er jaygay show korate hbe
                final String email=mail.getText().toString();
                final String password=pass.getText().toString();
                final String repeatPass=repass.getText().toString();
                if(userName.isEmpty() || email.isEmpty() ||  password.isEmpty() || repeatPass.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Fill up the page", Toast.LENGTH_SHORT).show();
                    return ;
                }
                if(!password.equals(repeatPass)){
                    Toast.makeText(getApplicationContext(), "password meleni khankir chele", Toast.LENGTH_SHORT).show();
                    return ;
                }
                reference.push().setValue(new RegUserDet(userName,email.replace(".",""),password));
                registerNewUser(email,password);
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
    private void init(){
        name= (EditText) findViewById(R.id.name);
        mail= (EditText) findViewById(R.id.mail);
        pass= (EditText) findViewById(R.id.pass);
        repass= (EditText) findViewById(R.id.repass);
        sign= (Button) findViewById(R.id.sgn);
        reference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://growcast-36424-default-rtdb.firebaseio.com/")
                .child("Users");
        auth=FirebaseAuth.getInstance();
    }
    private void registerNewUser(final String email,final String password)
    {
        if (!isPasswordValid(password)) {
            // Password does not meet the required criteria
            // Show an appropriate message to the user
            // For example, display a toast message
            Toast.makeText(getApplicationContext(), "Invalid password."+"Password must be at least 8 characters long"+"contain at least one uppercase letter"+" one lowercase letter.", Toast.LENGTH_SHORT).show();
        }
        // create new user or register new user
        auth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            if (user != null && user.getEmail().endsWith("@gmail.com")){
                                Toast.makeText(getApplicationContext(),
                                                "Registration successful!",
                                                Toast.LENGTH_LONG)
                                        .show();
                            }
                            else {
                                // Invalid email or not a Gmail account
                                // Show an appropriate message to the user
                                // For example, display a toast message
                                Toast.makeText(getApplicationContext(), "Invalid or non-Gmail email", Toast.LENGTH_SHORT).show();
                            }


                        }
                        else {
                            // An error occurred during signup
                            // Check if the email is already registered
                            Exception exception = task.getException();
                            if (exception instanceof FirebaseAuthUserCollisionException) {
                                // Email already registered
                                // Show an appropriate message to the user
                                // For example, display a toast message
                                Toast.makeText(getApplicationContext(), "Email already registered", Toast.LENGTH_LONG).show();
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

                    }
                });
    }

    private boolean isPasswordValid(String password) {
        // Password must be at least 8 characters long and contain at least one uppercase letter and one lowercase letter
        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z]).{8,}$");
        return pattern.matcher(password).matches();
    }
}
