package com.seiztheday.test.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.seiztheday.test.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final Button register=findViewById(R.id.register_register);
        final Button back=findViewById(R.id.register_back);
        EditText username=findViewById(R.id.register_username);
        EditText password=findViewById(R.id.register_password);
        EditText phone=findViewById(R.id.register_phone);
        EditText email=findViewById(R.id.register_email);
        EditText real_name=findViewById(R.id.register_real_name);
        EditText student_id=findViewById(R.id.register_student_id);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLoginActivity();
            }
        });






    }


    private void goLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        this.startActivity(intent);
        this.finish();
    }




}
