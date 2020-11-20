package com.example.projectd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.projectd.Preference.shared_preference_class;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class activity_dashboard extends AppCompatActivity {
    CardView addProject, removeAccount;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        removeAccount = findViewById(R.id.card_about);
        mContext = this;
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
    }
}