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
import java.io.FileInputStream;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ritamartiniano.earthcleanser.Adapter.MediaDisplayAdapter;
import com.example.ritamartiniano.earthcleanser.Model.Action;
import com.example.ritamartiniano.earthcleanser.Model.MediaActions;
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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
public class FeedFragment extends Fragment {

    TextView txtOrigin, txtDestination,advisedSpeed;
    Button calculateDist;
    private RecyclerView actions;
    private RecyclerView.Adapter actionsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<MediaActions> items;

    private DatabaseReference getActions;
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
        ArrayList<String> details = null;
        try {
            FileInputStream inputStream = getContext().openFileInput("carDetails");
            ObjectInputStream in = new ObjectInputStream(inputStream);
            Object d  = in.readObject();
            in.close();
            inputStream.close();
            Log.d("Object",d.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
                        double converter = 1609.344;
                        int seconds = 3600;
                        double miles = f/converter;
                        int hours = s/seconds;
                        //calculateSpeed(miles,hours);
                        Log.d("FeedFragment", String.valueOf(s));

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
    public void double emissionfromNormalSpeed(double mpg,double distance,String fuel)
    {   //check the emission factors for each fuel for litres
        Double gallons = distance / mpg;
        if(fuel =="diesel")
        {
            Double emission = gallons * 2.13;
        }
        else if(fuel == "petrol")
        {
            Double emission = gallons * 1.45;
        }

    }
    /**
    public double calculateOptimalSpeed()
    {
        calculateSpeed();
        //if speed is below 55 do this
        //if speed is between 55 and < 60 do this
        //if speed is between > 60 and < 65 do this
        //
    }


    public double calculateEmissionfromOptimalSpeed(double mpg,double )
    {
        //

    }

**/
}
