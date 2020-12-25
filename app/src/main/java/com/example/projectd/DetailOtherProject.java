package com.example.projectd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectd.Preference.shared_preference_class;

import com.example.projectd.Model.project;
import com.example.projectd.retrofitClient.BaseAPIService;
import com.example.projectd.retrofitClient.UtilsApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailOtherProject extends AppCompatActivity {
    TextView nama_project, start_Theproject, deadline_project, description_project;
    String id_project, status_project;
    Button submitProject_registration;
    project projectData;
    Context mContext;
    BaseAPIService mApiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_other_project);
        nama_project = findViewById(R.id.nama_other_project);
        start_Theproject = findViewById(R.id.start_other_project);
        deadline_project = findViewById(R.id.deadline_other_project);
        description_project = findViewById(R.id.description_other_project);
        mApiService = UtilsApi.getAPIService();
        mContext = this;
        showProject();
        submitProject_registration = findViewById(R.id.button_join_project);
        if(status_project.matches("0")){
            submitProject_registration.setVisibility(View.GONE);
        }
        submitProject_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                joinProject();
            }
        });
    }

    private void joinProject() {
        mApiService.joinProject(id_project, shared_preference_class.getLoggedInId(mContext))
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(mContext, "Berhasil Join", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(mContext, JoinedProject.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(mContext, "Request tidak berhasil", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(mContext, "HARAP PERIKSA KONEKSI ANDA!!", Toast.LENGTH_SHORT).show();
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
            this.status_project = projectData.getStatus_project();
            nama_project.setText(nameOfProject);
            start_Theproject.setText(startOfProject);
            deadline_project.setText(deadlineOfProject);
            description_project.setText(descriptionOfProject);
        }
    }
}