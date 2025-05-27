package com.example.growcast;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.firebase.auth.FirebaseAuth;

public class page1 extends AppCompatActivity {
    private Toolbar account;
    private String uName;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.page1);
        this.account = (Toolbar) findViewById(R.id.account);
        this.uName = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_file, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void account(View v) {
        Intent intent = new Intent(this, user.class);
        intent.putExtra("user", this.uName);
        startActivity(intent);
    }

    public void note(View v) {
        startActivity(new Intent(this, note.class));
    }

    public void flower(View v) {
        startActivity(new Intent(this, flowerscroll.class));
    }

    public void vegetable(View v) {
        startActivity(new Intent(this, vegscroll.class));
    }

    public void fruit(View v) {
        startActivity(new Intent(this, fruitscroll.class));
    }

    public void weather(View v) {
        startActivity(new Intent(this, weather.class));
    }
}
