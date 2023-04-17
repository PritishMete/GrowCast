package com.example.growcast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


public class page1 extends AppCompatActivity {
    private Toolbar account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page1);
        account= (Toolbar) findViewById(R.id.account);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.item_file,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public void account(View v){


        Intent intent=new Intent(page1.this,user.class);
        startActivity(intent);
    }
}
