package com.example.growcast;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

public class fruitscroll extends AppCompatActivity {
    private Toolbar account;
    private String uName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fruitscroll);
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
    public void banana(View v){


        Intent intent=new Intent(this,banana.class);
        startActivity(intent);
    }




    public void carambola(View v){


        Intent intent=new Intent(this,carambola.class);
        startActivity(intent);
    }
    public void blackberry(View v){


        Intent intent=new Intent(this,blackberry.class);
        startActivity(intent);
    }
    public void cherry(View v){


        Intent intent=new Intent(this,cherry.class);
        startActivity(intent);
    }
    public void coconut(View v){


        Intent intent=new Intent(this,coconut.class);
        startActivity(intent);
    }
    public void cucumber(View v){


        Intent intent=new Intent(this,cucumber.class);
        startActivity(intent);
    }
    public void grape(View v){


        Intent intent=new Intent(this,grape.class);
        startActivity(intent);
    }
    public void jackfruit(View v){


        Intent intent=new Intent(this,jackfruit.class);
        startActivity(intent);
    }
    public void lemon(View v){


        Intent intent=new Intent(this,lemon.class);
        startActivity(intent);
    }
    public void mango(View v){


        Intent intent=new Intent(this,mango.class);
        startActivity(intent);
    }
    public void orange(View v){


        Intent intent=new Intent(this,orange.class);
        startActivity(intent);
    }
    public void pineapple(View v){


        Intent intent=new Intent(this,pineapple.class);
        startActivity(intent);
    }
    public void papaya(View v){


        Intent intent=new Intent(this,papaya.class);
        startActivity(intent);
    }
    public void roseapple(View v){


        Intent intent=new Intent(this,roseapple.class);
        startActivity(intent);
    }
    public void pomegrante(View v){


        Intent intent=new Intent(this,pomegrante.class);
        startActivity(intent);
    }
    public void strawberry(View v){


        Intent intent=new Intent(this,strawberry.class);
        startActivity(intent);
    }



}
