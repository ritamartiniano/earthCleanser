package com.example.ritamartiniano.earthcleanser;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InputCarDetails extends AppCompatActivity {
    TextView gasType, mpg;
    Button saveDetails;
    DatabaseReference databaseReference;
    FirebaseUser user;
    private static final String carDetails = "carDetails";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_car_details);
        gasType = findViewById(R.id.gasType);
        mpg = findViewById(R.id.mpg);
        saveDetails = findViewById(R.id.detailsButton);
        user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
        saveDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gasType!= null && mpg != null)
                {
                    HashMap<String,Object> carDetails = new HashMap<>();
                    carDetails.put("GasType",gasType);
                    carDetails.put("MPG",mpg);
                    databaseReference.setValue(carDetails);
                }
                else if(gasType==null)
                {
                    gasType.setError("This field cannot be empty");
                }
                else if(mpg == null)
                {
                    mpg.setError("This field cannon be empty");
                }
            }
        });
    }
}
