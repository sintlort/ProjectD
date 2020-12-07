package com.example.projectd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectd.Model.project;
import com.example.projectd.Preference.shared_preference_class;
import com.example.projectd.retrofitClient.BaseAPIService;
import com.example.projectd.retrofitClient.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailProject extends AppCompatActivity {
    TextView nama, start, end, descP;
    project Project;
    Button editproject, stopproject;
    BaseAPIService mApiService;
    String id_project,status_project;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_project);
        nama = findViewById(R.id.nama);
        start = findViewById(R.id.tanggalstart);
        end = findViewById(R.id.tanggalend);
        descP = findViewById(R.id.description_project);
        editproject = findViewById(R.id.edit_project);
        stopproject = findViewById(R.id.stop_project);
        mApiService = UtilsApi.getAPIService();
        mContext = this;

        Intent intent = getIntent();
        if(intent.getExtras() !=null){
            Project = (project) intent.getSerializableExtra("data");
            String namaproject = Project.getNama_project();
            String startdate = Project.getStart_project();
            String enddate = Project.getEnd_project();
            String desc = Project.getDesc_project();
            this.id_project = Project.getId();
            this.status_project = Project.getStatus_project();
            nama.setText(namaproject);
            start.setText(startdate);
            end.setText(enddate);
            descP.setText(desc);

        }

        if(this.status_project.matches("0")){
            stopproject.setVisibility(View.GONE);

        }

        editproject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailProject.this, UpdateProject.class).putExtra("data",Project);
                startActivity(intent);
            }
        });

        stopproject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMyProject();
            }
        });
    }

    private void stopMyProject() {
        mApiService.stopMyProject(id_project).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        if (jsonRESULTS.getString("status").matches("200")){
                            String message = jsonRESULTS.getString("message");
                            Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
                        } else {
                            String error_message = jsonRESULTS.getString("message");
                            if (error_message.equals("data not found")){
                                Toast.makeText(mContext, "Login Gagal, Username atau Password salah!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}