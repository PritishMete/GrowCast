package com.example.growcast;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class login extends AppCompatActivity {

    private EditText user, pass;
    private Button login,signup,forgot;

    private FirebaseDatabase database;
    private DatabaseReference reference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        user= (EditText) findViewById(R.id.user);
        pass= (EditText) findViewById(R.id.pass);
        forgot= (Button) findViewById(R.id.forgot);
        signup= (Button) findViewById(R.id.signup);
        login= (Button) findViewById(R.id.login);


    }

    public void fun(View v){
        String u=user.getText().toString();
        String p=pass.getText().toString();

            Intent intent=new Intent(login.this,signup.class);
            startActivity(intent);


    }
    public void forgot(View v){

        Intent intent=new Intent(this,forgot.class);
        startActivity(intent);


    }
    public void login(View v){
        String u=user.getText().toString();
        String p=pass.getText().toString();
        View view = findViewById(R.id.a);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.vanish);
        view.startAnimation(animation);

        Intent intent=new Intent(login.this,page1.class);
        startActivity(intent);
    }
}

