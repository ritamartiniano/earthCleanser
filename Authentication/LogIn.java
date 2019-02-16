package com.example.ritamartiniano.earthcleanser.Authentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ritamartiniano.earthcleanser.MainActivity;
import com.example.ritamartiniano.earthcleanser.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity {
    TextView txtEmail, txtPassword;
    Button btnLogin;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        txtEmail = findViewById(R.id.email);
        txtPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.signin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();

               if(validateInfo(email,password))
               {
                   SignIn(email,password);
               }
            }
        });
    }
    private void SignIn(String email, String password)
    {
        mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(LogIn.this, "Login Successfull",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LogIn.this,MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(LogIn.this,"Login Unsuccessfull", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean validateInfo(String email, String password)
    {   Boolean validated;
         validated = true;
        if(TextUtils.isEmpty(email))
        {
          txtEmail.setError("Required");
          validated = false;
        }
        else if (TextUtils.isEmpty(password))
        {
            txtPassword.setError("Required");
            validated = false;
        }
        return validated;

    }
}
