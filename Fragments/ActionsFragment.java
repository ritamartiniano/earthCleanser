package com.example.ritamartiniano.earthcleanser.Fragments;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.ritamartiniano.earthcleanser.Adapter.ActionItems_Adapter;
import com.example.ritamartiniano.earthcleanser.Model.Action;
import com.example.ritamartiniano.earthcleanser.R;

import java.util.ArrayList;

public class ActionsFragment extends Fragment {
    BottomNavigationView navigationView;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter actionsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Action> actionsList = new ArrayList<>();
    public ActionsFragment() {

    }
    public static ActionsFragment newInstance()
    {
        ActionsFragment actionsFragment= new ActionsFragment();
        return actionsFragment;

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_actions, container, false);
        mRecyclerView = v.findViewById(R.id.actionItems);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
      
        actionsList.add(new Action("Eat a vegan meal", "Eating meat increases the emissions of greenhouse gases, if you cut down these meals, your emissions will significantly reduce.","10 points"));
        actionsList.add(new Action("Change light bulbs to energy saving.","These bulbs can cut down your energy consumption up to 10%.","5 points"));
        actionsList.add(new Action("Recycle once in a week","Example description","3 points"));
        actionsAdapter = new ActionItems_Adapter(getContext(),actionsList);
        mRecyclerView.setAdapter(actionsAdapter);
        return v;
    }

}
