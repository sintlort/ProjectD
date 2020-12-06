package com.example.projectd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.projectd.Model.project;
import com.example.projectd.Preference.shared_preference_class;
import com.example.projectd.retrofitClient.UtilsApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class getAllProject extends AppCompatActivity implements ProjectAdapter.ClickedItem {
    ImageView addButton;
    RecyclerView recyclerView;
    ProjectAdapter projectAdapter;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_project);
        recyclerView = findViewById(R.id.recyclerProject);

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
                    List<project> projectList = response.body();
                    projectAdapter.setData(projectList);
                    recyclerView.setAdapter(projectAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<project>> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void ClickedProject(project projectData) {
        Log.e("Clicked",projectData.toString());
        Intent intent = new Intent(this, DetailProject.class).putExtra("data",projectData);
        startActivity(intent);
    }
}