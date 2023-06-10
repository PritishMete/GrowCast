package com.example.growcast;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

public class flowerscroll extends AppCompatActivity {
    private Toolbar account;
    private String uName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flowerscroll);
        account= (Toolbar) findViewById(R.id.account);
        uName= FirebaseAuth.getInstance().getCurrentUser().getUid();

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
        intent.putExtra("user",uName);
        startActivity(intent);
    }
    public void weather(View v){


        Intent intent=new Intent(this,weather.class);
        startActivity(intent);
    }
    public void ashoka(View v){


        Intent intent=new Intent(this,ashoka.class);
        startActivity(intent);
    }
    public void bluebell(View v){


        Intent intent=new Intent(this,bluebell.class);
        startActivity(intent);
    }public void cactus(View v){


        Intent intent=new Intent(this,cactus.class);
        startActivity(intent);
    }public void celendula(View v){


        Intent intent=new Intent(this,calendula.class);
        startActivity(intent);
    }public void dahlia(View v){


        Intent intent=new Intent(this,dahlia.class);
        startActivity(intent);
    }public void frangipani(View v){


        Intent intent=new Intent(this,frangipani.class);
        startActivity(intent);
    }public void hibiscus(View v){


        Intent intent=new Intent(this,hibiscus.class);
        startActivity(intent);
    }public void jasmine(View v){


        Intent intent=new Intent(this,jasmine.class);
        startActivity(intent);
    }public void lily(View v){


        Intent intent=new Intent(this,lily.class);
        startActivity(intent);
    }public void mangolia(View v){


        Intent intent=new Intent(this,mangolia.class);
        startActivity(intent);
    }public void marigold(View v){


        Intent intent=new Intent(this,marigold.class);
        startActivity(intent);
    }public void orchid(View v){


        Intent intent=new Intent(this,orchid.class);
        startActivity(intent);
    }public void rose(View v){


        Intent intent=new Intent(this,rose.class);
        startActivity(intent);
    }public void sunflower(View v){


        Intent intent=new Intent(this,sunflower.class);
        startActivity(intent);
    }public void tulip(View v){


        Intent intent=new Intent(this,tulip.class);
        startActivity(intent);
    }
}
