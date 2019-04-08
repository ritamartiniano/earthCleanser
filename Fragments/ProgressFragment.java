package com.example.ritamartiniano.earthcleanser.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.example.ritamartiniano.earthcleanser.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
//import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class ProgressFragment extends Fragment {
    BarChart barChart;
    BarData barData;
    DatabaseReference database;
    FirebaseUser mAuth;

    public HashMap<String, Integer> sectors;
    float food;
    public static ProgressFragment newInstance() {
        ProgressFragment progFg = new ProgressFragment();
        return progFg;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sectors = new HashMap<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View v = inflater.inflate(R.layout.fragment_progress, container, false);
        barChart = v.findViewById(R.id.barChart);
        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        String user = mAuth.getUid();
        database = FirebaseDatabase.getInstance().getReference("Points");
        database.child(user);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                     food = Integer.parseInt(String.valueOf(ds.child("Food").child("total").getValue()));
                     //Log.d("Chart",String.valueOf(food));
                    //int transportation = Integer.parseInt(String.valueOf(ds.child("Transportation").child("total").getValue()));
                   // int energy = Integer.parseInt(String.valueOf(ds.child("Energy").child("total").getValue()));
                    //sectors.put("Food", food);

                   // sectors.put("transportation", transportation);
                  //  sectors.put("energy", energy);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        barData = new BarData(getXvalues(),getBarvalues());
        barChart.setData(barData);

        return v;
    }
    public ArrayList<String> getXvalues()
    {
      ArrayList<String> xValues = new ArrayList<>();
      xValues.add("Food");
      xValues.add("Transportation");
      xValues.add("Energy");
      return xValues;
    }
    public ArrayList<BarDataSet> getBarvalues()
    {
        Log.d("Progress Fragment",String.valueOf(food));
        ArrayList<BarDataSet> barDataSets = new ArrayList<>();
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        BarEntry one = new BarEntry(food,0);

        barEntries.add(one);
        BarDataSet barDataSet1 = new BarDataSet(barEntries,"Sectors");
        barDataSets.add(barDataSet1);
        return barDataSets;

    }
    /**public void setData(int count, float range)
    {
        float start = 1f;
        ArrayList<BarEntry> values = new ArrayList<>();
        for(int i = (int) start; i < start + count;i++) {
            float val = (float) Math.random() * (range + 1);

            if(Math.random() * 100< 25)
            {
                values.add(new BarEntry(i, val, ))
            }
        }
        }
    }
     **/

}
