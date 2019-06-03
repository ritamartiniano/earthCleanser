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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
//import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
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
    DatabaseReference database,getEmission;
    FirebaseUser mAuth;
    float food,transportation,energy,emission;
    int time;
    LineChart lineChart;
    public static ProgressFragment newInstance() {
        ProgressFragment progFg = new ProgressFragment();
        return progFg;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View v = inflater.inflate(R.layout.fragment_progress, container, false);
        barChart = v.findViewById(R.id.barChart);
        lineChart = v.findViewById(R.id.lineChart);
        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        String user = mAuth.getUid();
        database = FirebaseDatabase.getInstance().getReference("Points");
        database.child(user);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                     food = Integer.parseInt(String.valueOf(ds.child("Food").child("total").getValue()));
                     transportation = Integer.parseInt(String.valueOf(ds.child("Transportation").child("total").getValue()));
                     energy = Integer.parseInt(String.valueOf(ds.child("Energy").child("total").getValue()));

                     getBarChart(food,transportation,energy);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        getEmission = FirebaseDatabase.getInstance().getReference("Emission");
        getEmission.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                   emission = Float.parseFloat(String.valueOf(ds.child("total")));
                   time = Integer.parseInt(String.valueOf(ds.child("time")));
                   ArrayList<String> xValues = new ArrayList<>();
                   ArrayList<Float> yValues = new ArrayList<Float>();
                   xValues.add(String.valueOf(time));
                   yValues.add(emission);
                  //getLineChart(xValues, yValues);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return v;
    }
    public void getBarChart(Float food,Float transportation, Float energy)
    {
        ArrayList<String> xValues = new ArrayList<>();
        xValues.add("Food");
        xValues.add("Transportation");
        xValues.add("Energy");
        ArrayList<BarDataSet> barDataSets = new ArrayList<>();
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        BarEntry one = new BarEntry(food,0);
        BarEntry two = new BarEntry(transportation,1);
        BarEntry three = new BarEntry(energy,2);
        barEntries.add(one);
        barEntries.add(two);
        barEntries.add(three);
        BarDataSet barDataSet1 = new BarDataSet(barEntries,"Sectors");
        barDataSets.add(barDataSet1);
        barData = new BarData(xValues,barDataSets);
        barChart.setData(barData);
    }
/**
    public void getLineChart(ArrayList<String>, ArrayList<>)
    {
            ArrayList<String> xAxisvalues = new ArrayList<String>();
            xAxisvalues.add(String.valueOf(time));
            ArrayList<Entry> yAxisValues = new ArrayList<Entry>();
            LineDataSet set1 = new LineData(yAxisValues,"DataSet1")
            yAxisValues.add(set1);

            LineDataSet dataSet = new LineDataSet(entries, "Emissions");
            //LineData lineData = new LineData(dataSet);

    }
 **/
}
