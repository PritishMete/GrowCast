package com.example.growcast;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class forgot extends AppCompatActivity {
    private EditText fid,otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot);


        fid= (EditText) findViewById(R.id.fid);
        otp= (EditText) findViewById(R.id.otp);
    }

    public void forgot(View v){
        String f=fid.getText().toString();
        String o=otp.getText().toString();

        Intent intent=new Intent(this,login.class);
        startActivity(intent);


    }

}
