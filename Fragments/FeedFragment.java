package com.example.ritamartiniano.earthcleanser.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ritamartiniano.earthcleanser.MainActivity;
import com.example.ritamartiniano.earthcleanser.MySingleton;
import com.example.ritamartiniano.earthcleanser.R;

import org.json.JSONObject;


public class FeedFragment extends Fragment {

    TextView txtOrigin, txtDestination;
    Button calculateDist;

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
        txtOrigin = v.findViewById(R.id.txtOrigin);
        txtDestination = v.findViewById(R.id.txtDestination);
        calculateDist = v.findViewById(R.id.calcDist);
        calculateDist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  calculateDist(txtOrigin.getText().toString(),txtDestination.getText().toString());
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
               Log.d("FeedFragment", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                   Log.d("FeedFragment",error.toString());
            }
        });

    MySingleton.getInstance(getActivity()).addToRequestQueue(request);


    }
}
