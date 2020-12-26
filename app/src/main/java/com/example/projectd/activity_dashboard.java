package com.example.projectd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.projectd.Model.project;
import com.example.projectd.Preference.shared_preference_class;
import com.example.projectd.SQLite.DBCRUDHelper;
import com.example.projectd.retrofitClient.BaseAPIService;
import com.example.projectd.retrofitClient.UtilsApi;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_dashboard extends AppCompatActivity {
    CardView addProject, joinedProject, otherProject, profile, logout;
    Context mContext;

    BaseAPIService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mContext = this;
        mApiService = UtilsApi.getAPIService();
        initBotNav();
        getProjectData();
        initCardView();
    }

    private void initBotNav() {
        BottomNavigationView botNav = findViewById(R.id.botnav);
        botNav.setOnNavigationItemSelectedListener(navListener);
    }

    private void initCardView() {
        joinedProject = findViewById(R.id.card_joined);
        joinedProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, JoinedProject.class);
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
        profile = findViewById(R.id.card_profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(activity_dashboard.this, ProfileUser.class);
                startActivity(profileIntent);
            }
        });
        logout = findViewById(R.id.card_exit);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shared_preference_class.clearLoggedInUser(mContext);
                Intent logout = new Intent(activity_dashboard.this, MainActivity.class);
                startActivity(logout);
            }
        });
    }

    private void getProjectData() {
        mApiService.getTheProjectD()
                .enqueue(new Callback<List<project>>() {
                    @Override
                    public void onResponse(Call<List<project>> call, Response<List<project>> response) {
                        if(response.isSuccessful()){
                            List<project> projects = response.body();
                            final DBCRUDHelper dbcrudHelper = new DBCRUDHelper(mContext);
                            dbcrudHelper.open();
                            dbcrudHelper.trunate();

                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    projects.forEach(new Consumer<project>() {
                                        @Override
                                        public void accept(project projecto) {
                                            long a = dbcrudHelper.insert(projecto);
                                        }
                                    });
                                    dbcrudHelper.close();
                                }
                            });
                        } else {
                            Toast.makeText(mContext, "Terjadi kesalahan, harap membuka aplikasi kembali!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<project>> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(mContext, "Periksa kembali koneksi anda!!", Toast.LENGTH_SHORT).show();
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
                            Intent profile = new Intent(activity_dashboard.this, ProfileUser.class);
                            startActivity(profile);
                            break;
                    }
                    return true;
                }
            };
}