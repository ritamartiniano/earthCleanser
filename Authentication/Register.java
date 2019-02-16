package com.example.ritamartiniano.earthcleanser.Authentication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ritamartiniano.earthcleanser.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends AppCompatActivity {
    Button register;
    TextView username, email,password,password2;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register = findViewById(R.id.btnRegister);
        username = findViewById(R.id.textUsername);
        email = findViewById(R.id.textEmail);
        password = findViewById(R.id.textPassword);
        password2 = findViewById(R.id.textPassword2);
        mAuth = FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Email = email.getText().toString();
                String ps = password.getText().toString();
                String ps2 = password2.getText().toString();
                createUser(Email,ps,ps2);
            }
        });
    }
    private void createUser(String email, String password, String password2)
    {
        if(validateUserInformation(email,password,password2))
        {
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(Register.this,"User Successfuly Registered",Toast.LENGTH_SHORT).show();
                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
                        HashMap user_info = new HashMap();
                        user_info.put("username",username.getText().toString());
                        dbref.setValue(user_info);
                    }
                    else
                    {
                        Toast.makeText(Register.this,"User Unsuccessfuly Registered",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private boolean validateUserInformation(String Email, String password1, String password2)
    {
        boolean validated = true;
        String upperCaseChars = "(.*[A-Z].*)";
        String lowerCaseChars = "(.*[a-z].*)";
        String numbers = "(.*[0-9].*)";
        if (TextUtils.isEmpty(Email)) {
            email.setError("Required");
            validated = false;
        } else {
            email.setError(null);
        }

        if (TextUtils.isEmpty(password1)) {
            password.setError("Required");
            validated = false;
        } else {
            password.setError(null);
        }
        if(password1.length()<6)
        {
            Toast.makeText(Register.this, "Password should contain at least 6 characters",Toast.LENGTH_LONG).show();
            validated = false;
        }
        if(password1.equals(password2))
        {
            if(!(password1.contains(upperCaseChars) && password1.contains(lowerCaseChars) && password1.contains(numbers)))
            {
                validated = true;
            }
            else
            {
                validated = false;
                Toast.makeText(Register.this,"Password should contain Upper Case characters and numbers", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(Register.this, "Passwords don't match",Toast.LENGTH_SHORT).show();
            validated = false;
        }

        return validated;
    }
}
