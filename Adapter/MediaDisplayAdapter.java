package com.example.ritamartiniano.earthcleanser.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ritamartiniano.earthcleanser.Model.Action;
import com.example.ritamartiniano.earthcleanser.Model.MediaActions;
import com.example.ritamartiniano.earthcleanser.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
//import com.squareup.picasso.PicassoProvider;

import java.util.ArrayList;

public class MediaDisplayAdapter extends RecyclerView.Adapter<MediaDisplayAdapter.MyViewHolder>
{   public Context context;
    public ArrayList<MediaActions> actions;
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView action_Name, action_Description;
        ImageView action_Img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            action_Name = itemView.findViewById(R.id.itemTitle);
            action_Description = itemView.findViewById(R.id.ItemDescription);
            action_Img = itemView.findViewById(R.id.itemImg);
        }
    }
    public MediaDisplayAdapter(Context c, ArrayList<MediaActions> ac)
    {
        context = c;
        actions = ac;
    }
    @NonNull
    @Override
    public MediaDisplayAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.media_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MediaDisplayAdapter.MyViewHolder myViewHolder, int i) {

        final String finalTitle = actions.get(i).getTitle();
        final String finalDesc = actions.get(i).getDescription();
        final String finalSector = actions.get(i).getSector();
        myViewHolder.action_Name.setText(finalTitle);
        myViewHolder.action_Description.setText(finalDesc);
        //myViewHolder.action_Img.setImageBitmap(finalImage);
        Glide.with(context).load(actions.get(i).getImage()).into(myViewHolder.action_Img);
        
        /**Picasso.get().load(actions.get(i).getImage()).placeholder(R.drawable.common_google_signin_btn_icon_dark).into(myViewHolder.action_Img, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
                Log.d("MediaDisplay",e.toString());
            }
        });
        Log.d("Url",actions.get(i).getImage());
        //Picasso.with(context).load(actions.get(i).getImage()).resize(300,300).centerCrop().into(myViewHolder.action_Img);
         **/
    }
    @Override
    public int getItemCount() {
        return actions.size();
    }
}