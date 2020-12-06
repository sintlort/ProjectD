package com.example.projectd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.projectd.Model.project;

public class DetailOtherProject extends AppCompatActivity {
    TextView nama_project, start_Theproject, deadline_project, description_project;
    String id_project;
    Button submitProject_registration;
    project projectData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_other_project);
        nama_project = findViewById(R.id.nama_other_project);
        start_Theproject = findViewById(R.id.start_other_project);
        deadline_project = findViewById(R.id.deadline_other_project);
        description_project = findViewById(R.id.description_other_project);
        showProject();
        submitProject_registration = findViewById(R.id.button_join_project);
        submitProject_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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

            nama_project.setText(nameOfProject);
            start_Theproject.setText(startOfProject);
            deadline_project.setText(deadlineOfProject);
            description_project.setText(descriptionOfProject);
        }
    }
}