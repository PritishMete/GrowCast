package com.example.growcast;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;



public class user extends AppCompatActivity {
    private Button back,logout,upload;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView dp;
    private Uri mImageUri;
    private TextView UserName;
    private ViewStub signup;
    private TextView mail;
    private String uName;
    private DatabaseReference reference=FirebaseDatabase.getInstance().getReferenceFromUrl("https://growcast-36424-default-rtdb.firebaseio.com/");


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);
        uName=getIntent().getStringExtra("user");

        upload = findViewById(R.id.upload);
        dp = findViewById(R.id.dp);
        back= (Button) findViewById(R.id.back);
        logout= (Button) findViewById(R.id.logout);
        UserName= (TextView) findViewById(R.id.userName);


        reference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name=snapshot.child(uName).child("userName").getValue(String.class);
                UserName.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();

            }
        });
    }
    private String extractUsernameFromEmail(String email) {
        int atIndex = email.indexOf('@');
        if (atIndex != -1) {
            return email.substring(0, atIndex);
        } else {
            return null;
        }
    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(dp); // or you can use any other image loading library to display the image in the ImageView
        }
    }


    public void logout(View v){


        Intent intent=new Intent(this,login.class);
        startActivity(intent);


    }
}


