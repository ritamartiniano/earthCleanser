package com.example.ritamartiniano.earthcleanser.Fragments;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.FileInputStream;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ritamartiniano.earthcleanser.Adapter.MediaDisplayAdapter;
import com.example.ritamartiniano.earthcleanser.Model.Action;
import com.example.ritamartiniano.earthcleanser.Model.MediaActions;
import com.example.ritamartiniano.earthcleanser.Model.User;
import com.example.ritamartiniano.earthcleanser.MySingleton;
import com.example.ritamartiniano.earthcleanser.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class FeedFragment extends Fragment {

    TextView txtOrigin, txtDestination,advisedSpeed;
    Button calculateDist;
    private RecyclerView actions;
    private RecyclerView.Adapter actionsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<MediaActions> items;
    private DatabaseReference getActions,getEmission,getCarDetails;
    private static  String REQUEST1 = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=";
    private static  String REQUEST2 = "&destinations=";
    private static  String Key = "&key=AIzaSyAdd9CXUNNmvLZEKXcL8hTiabZQT9xzBPk";
    public static FeedFragment newInstance()
    {
        FeedFragment feedFragment = new FeedFragment();
        return feedFragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance)
    {
        View v = inflater.inflate(R.layout.fragment_feed,container, false);
        actions = v.findViewById(R.id.actions);
        txtOrigin = v.findViewById(R.id.txtOrigin);
        txtDestination = v.findViewById(R.id.txtDestination);
        advisedSpeed = v.findViewById(R.id.advisedSpeed);
        calculateDist = v.findViewById(R.id.calcDist);
        calculateDist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  calculateDist(txtOrigin.getText().toString(),txtDestination.getText().toString());
            }
        });
        actions = v.findViewById(R.id.actions);
        layoutManager = new LinearLayoutManager(getActivity());
        actions.setLayoutManager(layoutManager);
        items = new ArrayList<MediaActions>();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        getActions = FirebaseDatabase.getInstance().getReference().child("Actions").child(userId);
        getEmission = FirebaseDatabase.getInstance().getReference().child("Emissions").child(userId);
        getCarDetails = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        getActions.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    MediaActions a = ds.getValue(MediaActions.class);
                    items.add(a);
                    actionsAdapter = new MediaDisplayAdapter(getActivity(),items);
                    actions.setAdapter(actionsAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return v;
    }
    private void calculateDist(String origin, String destination)
    {
        final String url = REQUEST1 + origin + REQUEST2 + destination + Key;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                        JSONArray rows = response.getJSONArray("rows");
                        JSONObject elements = rows.getJSONObject(0);
                        JSONArray strings = elements.getJSONArray("elements");
                        JSONObject data = strings.getJSONObject(0);
                        JSONObject values = data.getJSONObject("distance");
                        JSONObject secs = data.getJSONObject("duration");
                        int s = secs.getInt("value");
                        int f = values.getInt("value");
                        final double kilometers = f*0.001;
                        final int hours = s/3600;
                    getCarDetails.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren())
                            {
                                User user = ds.getValue(User.class);
                                double emission1 = calculateEmission(user.mpg,kilometers,user.gasType);
                                int r = calculateOptimalSpeed(kilometers,hours);
                                advisedSpeed.setText(String.valueOf(r));
                                double emission2 = calculateEmissionfromOptimalSpeed(user.mpg,r,user.gasType);
                                double value = calculateDifference(emission1,emission2);
                                saveEmission(value);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                   // calculateSpeed(miles,hours);
                    Log.d("FeedFragment", String.valueOf(kilometers));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                   Log.d("FeedFragment",error.toString());
            }
        });

    MySingleton.getInstance(getActivity()).addToRequestQueue(request);

    }
    public double calculateSpeed(double distance, int time)
    {
        Double speed = distance/time;
        return speed;

    }

    public double calculateEmission(double mpg,double distance,String fuel)
    {   //check the emission factors for each fuel for litres
        //convert miles to kilometers
        double kilometers = mpg * 1.61;
        //calculate litres used from mpg
        double litres = (kilometers * 100)/4.5461;
        //calculate litres used from distance
        double flitres = (distance * litres)/100;

        double emission = 0;

        if(fuel == "diesel")
        {
             emission = flitres * 2.62694;
        }
        else if(fuel == "petrol")
        {
            emission = flitres * 2.20307;
        }
        return emission;
    }

    public void saveEmission(Double saving)
    {
        double time = System.currentTimeMillis();
        getEmission.setValue(time,saving);
    }


    public int calculateOptimalSpeed(Double distance, int time)
    {
        Double speed = calculateSpeed(distance, time);
        int optimalSpeed = 0;

        if(speed>=55 && speed<=70)
        {
            if(speed>55&& speed<=60)
            {
                optimalSpeed = 55;
            }
            else if(speed>60&&speed<=65)
            {
                optimalSpeed = 60;
            }
            else if(speed>65&&speed<=70)
            {
                optimalSpeed = 65;
            }
        }
        if(speed<55)
        {
        }
        return optimalSpeed;
    }
    public double calculateEmissionfromOptimalSpeed(double mpg,int optimalSpeed,String fuel,int distance)
    {
        Double emission = 0.0;
                if(optimalSpeed == 60)
                {
                    double value = (0.03*mpg)/100;
                    mpg = mpg + value;
                    double p = ((mpg*1.61) * 100)/4.5461;
                    //do modifications to the switch statement
                    double litres = p * distance/100;
                    switch(fuel)
                    {
                      case"diesel":
                         emission = litres * 2.62694;
                      break;

                        case"petrol":
                            emission = litres * 2.20307;
                            break;
                    }
                }
                else if(optimalSpeed == 65)
                {
                    double value = (0.08*mpg)/100;
                    mpg = mpg + value;
                    double p = ((mpg*1.61)*100)/4.5461;
                    double litres = p * distance/100;
                    switch(fuel)
                    {
                        case "diesel":
                            emission = litres * 2.62694;
                            break;
                        case "petrol":
                            emission = litres * 2.20307;
                        break;
                    }
                }
                else if(optimalSpeed == 70)
                {
                    double value = (0.17*mpg)/100;
                    mpg = mpg + value;
                    double p = ((mpg*1.61)*100)/4.5461;
                    double litres = p * distance/100;
                    switch(fuel)
                    {
                        case "diesel":
                            emission = litres * 2.62694;
                            break;
                        case"petrol":
                            emission = litres * 2.20307;
                    }
                }

        return emission;
    }
    public double calculateDifference(double emission1, double emission2)
    {
        Double saving = emission1-emission2;
        return saving;
    }

}
