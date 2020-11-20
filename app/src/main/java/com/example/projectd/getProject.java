package com.example.projectd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.projectd.Model.project;
import com.example.projectd.retrofitClient.UtilsApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class getProject extends AppCompatActivity {
    ImageView addButton;
    RecyclerView recyclerView;
    ProjectAdapter projectAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_project);
        recyclerView = findViewById(R.id.recyclerProject);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        projectAdapter = new ProjectAdapter();

        getProject();

        addButton = findViewById(R.id.add_project_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goAddProject();
            }
        });


    }

    private void goAddProject() {
        Intent intent = new Intent(getProject.this, activity_add_project.class);
        startActivity(intent);
    }

    private void getProject(){
        Call<List<project>> call = (Call<List<project>>) UtilsApi.getAPIService().getAllProject();
        call.enqueue(new Callback<List<project>>() {
            @Override
            public void onResponse(Call<List<project>> call, Response<List<project>> response) {
                if(response.isSuccessful()){
                    Log.e("success", response.body().toString());
                    List<project> projectList = response.body();
                    projectAdapter.setData(projectList);
                    recyclerView.setAdapter(projectAdapter);
                    addButton.bringToFront();
                }
            }

            @Override
            public void onFailure(Call<List<project>> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
            }
        });
    }
}