package com.example.growcast;

import static android.text.TextUtils.isEmpty;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class login extends AppCompatActivity {

    private EditText user, pass;

    private Button login,signup,forgot;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        user= (EditText) findViewById(R.id.user);
        pass= (EditText) findViewById(R.id.pass);
        forgot= (Button) findViewById(R.id.forgot);
        signup= (Button) findViewById(R.id.signup);
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

