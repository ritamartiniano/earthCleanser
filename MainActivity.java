package com.example.ritamartiniano.earthcleanser;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.example.ritamartiniano.earthcleanser.Fragments.ActionsFragment;
import com.example.ritamartiniano.earthcleanser.Fragments.FeedFragment;
import com.example.ritamartiniano.earthcleanser.Fragments.ProgressFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView btmNavigationView = findViewById(R.id.bottomNavigationView);
        btmNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fg = null;
                switch(menuItem.getItemId())
                {
                    case R.id.feed:
                        fg = FeedFragment.newInstance();
                        break;
                    case R.id.actions:
                        fg= ActionsFragment.newInstance();
                        break;
                    case R.id.progress:
                        fg= ProgressFragment.newInstance();
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment,fg);
                transaction.commit();
                return true;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.add:
                Intent addAction = new Intent(MainActivity.this,CreateAction.class);
                MainActivity.this.startActivity(addAction);
        }
        return true;
    }
}
