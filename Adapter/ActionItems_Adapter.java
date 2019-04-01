package com.example.ritamartiniano.earthcleanser.Adapter;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import com.example.ritamartiniano.earthcleanser.CreateAction;
import com.example.ritamartiniano.earthcleanser.MainActivity;
import com.example.ritamartiniano.earthcleanser.Model.Action;
import com.example.ritamartiniano.earthcleanser.Model.Energy;
import com.example.ritamartiniano.earthcleanser.Model.Food;
import com.example.ritamartiniano.earthcleanser.Model.Transportation;
import com.example.ritamartiniano.earthcleanser.MyNotificationPublisher;
import com.example.ritamartiniano.earthcleanser.R;
import com.example.ritamartiniano.earthcleanser.StartActivity;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class ActionItems_Adapter extends RecyclerView.Adapter<ActionItems_Adapter.MyViewHolder> {
    Context c;
    Dialog d;
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

            viewholder.actionTitle.setText(actionsList.get(i).getTitle());
            viewholder.actionDescription.setText(actionsList.get(i).getDescription());
            viewholder.actionPoints.setText(String.valueOf(actionsList.get(i).getPoints()));
            viewholder.icon.setImageBitmap(actionsList.get(i).getIcon());
            viewholder.sector.setText(actionsList.get(i).getClass().getSimpleName());

    }
    @Override
    public int getItemCount() {
        return actionsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView actionTitle, actionDescription, actionPoints,sector;
        Switch btn;
        ImageView icon;
        TimePickerDialog tmd;
        String sec;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            actionTitle = itemView.findViewById(R.id.txtTitle);
            actionDescription = itemView.findViewById(R.id.txtdescription);
            actionPoints = itemView.findViewById(R.id.txtpoints);
            btn = itemView.findViewById(R.id.activatebtn);
            sector = itemView.findViewById(R.id.txtSector);

            icon = itemView.findViewById(R.id.action_Icon);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
            this.btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    if(checked)
                    {
                        String points = actionPoints.getText().toString();
                        Log.d("ActionAdapter", points);
                        final PopupMenu popup = new PopupMenu(c.getApplicationContext(), btn);
                        popup.getMenuInflater().inflate(R.menu.pop_up, popup.getMenu());
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                switch (menuItem.getItemId()) {
                                    case R.id.take_Now:
                                        c.startActivity(new Intent(c.getApplicationContext(), CreateAction.class).putExtra("Points",actionPoints.getText().toString()).putExtra("Sector",sector.getText()));
                                        break;
                                    case R.id.remind_Later:
                                        getTimePicker();
                                        break;
                                }
                                return true;
                            }
                        });
                        popup.show();
                    }
                }
            });
        }
    }

    public void getTimePicker()
    {
         final  Calendar mCurrentTime = Calendar.getInstance();
         int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
         int min = mCurrentTime.get(Calendar.MINUTE);
        final TimePickerDialog timePickerDialog;
        timePickerDialog = new TimePickerDialog(c,new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                //calculate delay from current time since boot and selected time = delay
                 long chosenTime = mCurrentTime.getTimeInMillis();

                 Log.d("TimePicker",Integer.toString(i) + "" + Integer.toString(i1));
                 Log.d("Current Time",Long.toString(System.currentTimeMillis()));
                //scheduleNotification(c,delay,1);
            }
        },hour,min,true);
        timePickerDialog.show();
    }

    public void scheduleNotification(Context c, long delay, int notificationId)
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(c.getApplicationContext()).
                setContentTitle("Earth Cleanser").
                setContentText("It's time to take an action!!");

        Intent intent = new Intent(c,ActionItems_Adapter.class);
        PendingIntent activity = PendingIntent.getActivity(c,notificationId,intent,PendingIntent.FLAG_CANCEL_CURRENT );
        builder.setContentIntent(activity);
        Notification notification = builder.build();
        Intent notificationIntent = new Intent(c, MyNotificationPublisher.class);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID,notificationId);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION,notification);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(c,notificationId,notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        long futureInMills = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) c.getSystemService(c.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,futureInMills,pendingIntent);
        }

}
