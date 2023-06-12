package com.example.growcast;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class user extends AppCompatActivity {
    private Button back, logout, upload;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView dp;
    private Uri mImageUri;
    private TextView UserName;
    private ViewStub signup;
    private TextView mail;
    private String uName;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://growcast-678b1-default-rtdb.firebaseio.com/").child("Users");
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);
        uName = FirebaseAuth.getInstance().getCurrentUser().getUid();

        upload = findViewById(R.id.upload);
        dp = findViewById(R.id.dp);
        back = findViewById(R.id.back);
        logout = findViewById(R.id.logout);
        UserName = findViewById(R.id.userName);

        reference.child(uName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String name = snapshot.child("userName").getValue(String.class);
                Toast.makeText(user.this, "fuck"+" "+name, Toast.LENGTH_SHORT).show();
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

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                // Clear the login preferences when logging out
                SharedPreferences sharedPreferences = getSharedPreferences("loginPreferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("rememberDevice");
                editor.apply();

                Intent intent = new Intent(user.this, login.class);
                startActivity(intent);
                finish();
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
}
