package com.example.ritamartiniano.earthcleanser.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ritamartiniano.earthcleanser.R;



public class ProgressFragment extends Fragment {
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
        return inflater.inflate(R.layout.fragment_progress,container, false);
    }
}
