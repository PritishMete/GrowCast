package com.example.growcast;

import static android.text.TextUtils.isEmpty;

import static com.example.growcast.R.id.name;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class signup extends AppCompatActivity {
    private EditText name,mail,phone,pass,repass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        name= (EditText) findViewById(R.id.name);
        mail= (EditText) findViewById(R.id.mail);
        phone= (EditText) findViewById(R.id.phone);
        pass= (EditText) findViewById(R.id.pass);
        repass= (EditText) findViewById(R.id.repass);

    }

    public void signup(View view){
        String n=name.getText().toString();
        String m=mail.getText().toString();
        String ph=phone.getText().toString();
        String p=pass.getText().toString();
        String r=repass.getText().toString();
        if (isEmpty(n)){
            if (isEmpty(m)){
                if (isEmpty(ph)) {
                    if (isEmpty(p)){
                        if (isEmpty(r)){
                            Toast.makeText(signup.this, "fill the  page", Toast.LENGTH_SHORT).show();

                        }
                    }


                }

            }
        }
        else {
            Intent intent=new Intent(this,login.class);
            startActivity(intent);
        }

    }


}
