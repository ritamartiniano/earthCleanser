package com.example.ritamartiniano.earthcleanser.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.ritamartiniano.earthcleanser.Model.Action;
import com.example.ritamartiniano.earthcleanser.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ActionItems_Adapter extends RecyclerView.Adapter<ActionItems_Adapter.MyViewHolder> {
    Context c;
    public ArrayList<Action> actionsList;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(c).inflate(R.layout.action_item,viewGroup,false));
    }

    public ActionItems_Adapter(Context ctx, ArrayList<Action> list)
    {
        c = ctx;
        actionsList = list;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder viewholder,int i) {

        for(Action a: actionsList) {

            final String title = a.getTitle();
            final String description = a.getDescription();
            final String points = a.getPoints();

            viewholder.actionTitle.setText(title);
            viewholder.actionDescription.setText(description);
            viewholder.actionPoints.setText(points);
        }
    }
    @Override
    public int getItemCount() {
        return actionsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView actionTitle, actionDescription, actionPoints,sector;
        Button btn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            actionTitle = itemView.findViewById(R.id.txtTitle);
            actionDescription = itemView.findViewById(R.id.txtdescription);
            actionPoints = itemView.findViewById(R.id.txtpoints);
            btn = itemView.findViewById(R.id.activatebtn);
            sector = itemView.findViewById(R.id.txtSector);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String points = actionPoints.getText().toString();
                    Log.d("Adapter",points);
                }
            });
        }
    }
}
