package com.example.projectd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.projectd.Model.project;

public class detail_joined_project extends AppCompatActivity {

    TextView nama_project, start_Theproject, deadline_project, description_project;
    String id_project, status_project;
    Button submitProject_registration;
    project projectData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_joined_project);
        nama_project = findViewById(R.id.detail_nama_project);
        start_Theproject = findViewById(R.id.detail_tanggalstart);
        deadline_project = findViewById(R.id.detail_tanggalend);
        description_project = findViewById(R.id.detail_description_project);
    }


    private void showProject() {
        Intent intent = getIntent();
        if(intent.getExtras() !=null){
            projectData = (project) intent.getSerializableExtra("data");
            String nameOfProject = projectData.getNama_project();
            String startOfProject = projectData.getStart_project();
            String deadlineOfProject = projectData.getEnd_project();
            String descriptionOfProject = projectData.getDesc_project();
            this.id_project = projectData.getId();
            this.status_project = projectData.getStatus_project();
            nama_project.setText(nameOfProject);
            start_Theproject.setText(startOfProject);
            deadline_project.setText(deadlineOfProject);
            description_project.setText(descriptionOfProject);
        }
    }
}