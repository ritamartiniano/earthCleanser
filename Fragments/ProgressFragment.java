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
import com.example.ritamartiniano.earthcleanser.R;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
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


public class ProgressFragment extends Fragment {
    HorizontalBarChart barChart;
    DatabaseReference database;
    FirebaseUser mAuth;
    HashMap<String,Integer> sectors;
    public static ProgressFragment newInstance()
    {
        ProgressFragment progFg = new ProgressFragment();
        return progFg;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance)
    {
        View v = inflater.inflate(R.layout.fragment_progress,container, false);
        barChart = v.findViewById(R.id.barChart);
        setData(3,50);
        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        String user = mAuth.getUid();
        database = FirebaseDatabase.getInstance().getReference("Points");
        database.child(user);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren())
                {    int food = Integer.parseInt(String.valueOf(ds.child("Food").child("total").getValue()));
                     int transportation = Integer.parseInt(String.valueOf(ds.child("Transportation").child("total").getValue()));
                     int energy = Integer.parseInt(String.valueOf(ds.child("Energy").child("total").getValue()));
                    sectors.put("Food",food);
                    sectors.put("transportation",transportation);
                    sectors.put("energy",energy);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
       return v;
    }
    private void setData(int count, int range)
    {
        ArrayList<BarEntry> yVals = new ArrayList<>();
        float barWidth = 3f;
        float spaceforBar = 10f;
        for(int i=0;i<count;i++)
        {   //set points for actions
            float val = (float)(Math.random()*range);
            for(int j =0;j<sectors.size();j++)
            {
                yVals.add(new BarEntry(i+spaceforBar,j));
            }
           // yVals.add(new BarEntry(i+spaceforBar,val));
        }
        BarDataSet set1;
        set1 = new BarDataSet(yVals,"Data Set 1");
        BarData data = new BarData(set1);
        data.setBarWidth(barWidth);
        barChart.setData(data);
    }
}
