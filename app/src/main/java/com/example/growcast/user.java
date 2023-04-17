package com.example.growcast;

import android.annotation.SuppressLint;
import com.squareup.picasso.Picasso;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.CircularArray;

public class user extends AppCompatActivity {
    private Button back,logout,upload;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView dp;
    private Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);

        upload = findViewById(R.id.upload);
        dp = findViewById(R.id.dp);
        back= (Button) findViewById(R.id.back);
        logout= (Button) findViewById(R.id.logout);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
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


