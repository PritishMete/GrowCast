package com.example.growcast;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;



public class page1 extends AppCompatActivity {
    private Toolbar account;
    private String uName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page1);
        account= (Toolbar) findViewById(R.id.account);
        uName=getIntent().getStringExtra("user");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.item_file,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public void account(View v){


        Intent intent=new Intent(page1.this,user.class);
        intent.putExtra("user",uName);
        startActivity(intent);
    }
    public void shop(View v){


        Intent intent=new Intent(page1.this,shop.class);
        startActivity(intent);
    }
    public void flower(View v){


        Intent intent=new Intent(page1.this,flowerscroll.class);
        startActivity(intent);
    }
    public void vegetable(View v){


        Intent intent=new Intent(page1.this, vegscroll.class);
        startActivity(intent);
    }
    public void fruit(View v){


        Intent intent=new Intent(page1.this, fruitscroll.class);
        startActivity(intent);
    }

    public void plant(View v){


        Intent intent=new Intent(page1.this, weather.class);
        startActivity(intent);
    }
}
