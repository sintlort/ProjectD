package com.example.projectd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projectd.Model.project;
import com.example.projectd.Preference.shared_preference_class;
import com.example.projectd.SQLite.DBCRUDHelper;
import com.example.projectd.retrofitClient.UtilsApi;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class getAllProject extends AppCompatActivity implements ProjectAdapter.ClickedItem {
    ImageView addButton;
    RecyclerView recyclerView;
    ProjectAdapter projectAdapter;
    Context mContext;
    List<project> projectList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_project);
        recyclerView = findViewById(R.id.recyclerProject);
        BottomNavigationView botNav = findViewById(R.id.botnav);
        botNav.setOnNavigationItemSelectedListener(navListener);
        botNav.setSelectedItemId(R.id.other_project);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mContext = this;
        projectAdapter = new ProjectAdapter(this::ClickedProject);

        getProject();

        addButton = findViewById(R.id.add_project_button);
    }



    private void getProject(){
        Call<List<project>> call = (Call<List<project>>) UtilsApi.getAPIService().getOtherProject(shared_preference_class.getLoggedInId(mContext));
        call.enqueue(new Callback<List<project>>() {
            @Override
            public void onResponse(Call<List<project>> call, Response<List<project>> response) {
                if(response.isSuccessful()){
                    Log.e("success", response.body().toString());
                    projectList = response.body();
                    projectAdapter.setData(projectList);
                    recyclerView.setAdapter(projectAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<project>> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
                Toast.makeText(mContext, "HARAP PERIKSA KONEKSI ANDA!!", Toast.LENGTH_SHORT).show();
                final DBCRUDHelper dbcrudHelper = new DBCRUDHelper(mContext);
                dbcrudHelper.open();
                projectList = dbcrudHelper.myProjectQuery();
                dbcrudHelper.close();
                projectAdapter.setData(projectList);
                recyclerView.setAdapter(projectAdapter);
            }
        });
    }

    @Override
    public void ClickedProject(project projectData) {
        Log.e("Clicked",projectData.toString());
        Intent intent = new Intent(this, DetailOtherProject.class).putExtra("data",projectData);
        startActivity(intent);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch(menuItem.getItemId()){
                        case R.id.dashboard:
                            Intent intent = new Intent(getAllProject.this, activity_dashboard.class);
                            startActivity(intent);
                            break;
                        case R.id.project:
                            Intent intent1 = new Intent(getAllProject.this, getProject.class);
                            startActivity(intent1);
                            break;
                        case R.id.other_project:
                            break;
                        case R.id.profile:
                            Intent profile = new Intent(getAllProject.this, ProfileUser.class);
                            startActivity(profile);
                            break;
                    }
                    return true;
                }
            };

}