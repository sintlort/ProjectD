package com.example.projectd;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectd.Adapter.ProgressImageAdapter;
import com.example.projectd.Model.progressImageModel;
import com.example.projectd.Model.project;
import com.example.projectd.retrofitClient.BaseAPIService;
import com.example.projectd.retrofitClient.UtilsApi;
import com.example.projectd.Preference.shared_preference_class;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class detail_joined_project extends AppCompatActivity {

    TextView nama_project, start_Theproject, deadline_project, description_project;
    String id_project, status_project;
    Button pick_image, upload_image, leave_project;
    project projectData;
    RecyclerView recyclerView;
    int IMG_REQUEST = 21;
    BaseAPIService mApiService;
    Bitmap bitmap;
    Context mContext;
    ProgressImageAdapter progressImageAdapter, progressImageAdapterz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_detail_joined_project);
        nama_project = findViewById(R.id.detail_nama_project);
        start_Theproject = findViewById(R.id.detail_tanggalstart);
        deadline_project = findViewById(R.id.detail_tanggalend);
        description_project = findViewById(R.id.detail_description_project);
        recyclerView = findViewById(R.id.recycler_image_progress);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        BottomNavigationView botNav = findViewById(R.id.botnav);
        botNav.setOnNavigationItemSelectedListener(navListener);
        showProject();
        getProgressImage();
        pick_image = findViewById(R.id.pick_image);
        upload_image = findViewById(R.id.upload_Progress);
        leave_project = findViewById(R.id.leave_project);
        mApiService = UtilsApi.getAPIService();
        pick_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bitmap!=null){
                    String encoded_image = imageEncode(bitmap);
                    request_post_image(encoded_image);
                }else {
                    Toast.makeText(mContext, "Pilih foto terlebih dahulu", Toast.LENGTH_SHORT).show();
                }
            }
        });

        leave_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTheFOutOfProject();
            }
        });


    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMG_REQUEST && resultCode == RESULT_OK && data!=null){

            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                pick_image.setText("Selected");
            } catch (IOException e){
                e.printStackTrace();
            }

        }
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

    private String imageEncode(Bitmap imageData){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imageData.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        byte[] imageByte = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imageByte, Base64.DEFAULT);
    }

    private void getProgressImage() {
        Call<List<progressImageModel>> call = (Call<List<progressImageModel>>) UtilsApi.getAPIService().getProgressImageAPI(id_project, shared_preference_class.getLoggedInId(mContext));
        call.enqueue(new Callback<List<progressImageModel>>() {
            @Override
            public void onResponse(Call<List<progressImageModel>> call, Response<List<progressImageModel>> response) {
                if(response.isSuccessful()){
                    List<progressImageModel> progressImageModels = response.body();
                    progressImageAdapter = new ProgressImageAdapter(progressImageModels);
                    recyclerView.setAdapter(progressImageAdapter);
                } else{
                    Toast.makeText(mContext, "Gagal load image progress",Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<List<progressImageModel>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(mContext, "HARAP PERIKSA KONEKSI ANDA!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void request_post_image(String encoded_image) {
        mApiService.postProgressProject(id_project, shared_preference_class.getLoggedInId(mContext), encoded_image)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(mContext, "Sukses", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(getIntent());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(mContext, "HARAP PERIKSA KONEKSI ANDA!!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getTheFOutOfProject() {
        mApiService.GTFOP(id_project, shared_preference_class.getLoggedInId(mContext))
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(mContext, "Keluar Project Berhasil", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(mContext, JoinedProject.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(mContext, "Gagal keluar Project", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(mContext, "HARAP PERIKSA KONEKSI ANDA!!", Toast.LENGTH_SHORT).show();
                            t.printStackTrace();
                    }
                });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch(menuItem.getItemId()){
                        case R.id.dashboard:
                            Intent intent = new Intent(detail_joined_project.this, activity_dashboard.class);
                            startActivity(intent);
                            break;
                        case R.id.project:
                            Intent intent1 = new Intent(detail_joined_project.this, getProject.class);
                            startActivity(intent1);
                            break;
                        case R.id.other_project:
                            Intent intent2 = new Intent(detail_joined_project.this, getAllProject.class);
                            startActivity(intent2);
                            break;
                        case R.id.profile:
                            Intent profile = new Intent(detail_joined_project.this, ProfileUser.class);
                            startActivity(profile);
                            break;
                    }
                    return true;
                }
            };

}