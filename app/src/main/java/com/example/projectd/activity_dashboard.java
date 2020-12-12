package com.example.projectd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.projectd.Preference.shared_preference_class;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class activity_dashboard extends AppCompatActivity {
    CardView addProject, removeAccount, otherProject;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        removeAccount = findViewById(R.id.card_about);
        otherProject = findViewById(R.id.card_register);
        mContext = this;
        BottomNavigationView botNav = findViewById(R.id.botnav);
        botNav.setOnNavigationItemSelectedListener(navListener);
        removeAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shared_preference_class.clearLoggedInUser(mContext);
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
            }
        });
        addProject = findViewById(R.id.card_dashboard);
        addProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_dashboard.this, getProject.class);
                startActivity(intent);
            }
        });

        otherProject = findViewById(R.id.card_register);
        otherProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_dashboard.this, getAllProject.class);
                startActivity(intent);
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch(menuItem.getItemId()){
                        case R.id.dashboard:
                            break;
                        case R.id.project:
                            Intent intent = new Intent(activity_dashboard.this, getProject.class);
                            startActivity(intent);
                            break;
                        case R.id.other_project:
                            Intent intent1 = new Intent(activity_dashboard.this, getAllProject.class);
                            startActivity(intent1);
                            break;
                        case R.id.profile:
                            break;
                    }
                    return true;
                }
            };
}