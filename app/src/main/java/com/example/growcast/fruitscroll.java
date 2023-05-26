package com.example.growcast;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class fruitscroll extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fruitscroll);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.item_file,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public void back(View v){


        Intent intent=new Intent(this,page1.class);
        startActivity(intent);
    }
    public void shop(View v){


        Intent intent=new Intent(this,shop.class);
        startActivity(intent);
    }
    public void account(View v){


        Intent intent=new Intent(this,user.class);
        startActivity(intent);
    }
    public void weather(View v){


        Intent intent=new Intent(this,weather.class);
        startActivity(intent);
    }
}
