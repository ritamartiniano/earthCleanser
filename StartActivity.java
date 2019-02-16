package com.example.ritamartiniano.earthcleanser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {
    Button LogIn, Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        LogIn = findViewById(R.id.btnSignin);
        Register = findViewById(R.id.btnSignup);
        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(StartActivity.this, com.example.ritamartiniano.earthcleanser.Authentication.LogIn.class);
                startActivity(login);
            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(StartActivity.this, com.example.ritamartiniano.earthcleanser.Authentication.Register.class);
                startActivity(register);
            }
        });
    }
}
