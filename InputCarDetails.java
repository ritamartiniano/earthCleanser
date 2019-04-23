package com.example.ritamartiniano.earthcleanser;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Map;

public class InputCarDetails extends AppCompatActivity {
    TextView gasType, mpg;
    Button saveDetails;
    private static final String carDetails = "carDetails";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_car_details);
        gasType = findViewById(R.id.gasType);
        mpg = findViewById(R.id.mpg);
        saveDetails = findViewById(R.id.detailsButton);
        saveDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gasType!= null && mpg != null)
                {
                    ArrayList details = new ArrayList();
                    details.add(gasType.getText().toString().toLowerCase().getBytes());
                    details.add(mpg.getText().toString().toLowerCase().getBytes());
                    try {
                        FileOutputStream fileOutputStream = getApplicationContext().openFileOutput(carDetails,MODE_PRIVATE);
                        ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
                        out.writeObject(details);
                        out.close();
                        fileOutputStream.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


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
