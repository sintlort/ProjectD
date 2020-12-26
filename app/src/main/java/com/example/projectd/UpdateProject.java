package com.example.projectd;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projectd.Model.project;
import com.example.projectd.retrofitClient.BaseAPIService;
import com.example.projectd.retrofitClient.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProject extends AppCompatActivity {
    EditText namaProject, startProject, endProject, descProject, maxOrang, noHp;
    project Project;
    Button submit_update, selectStartDate, selectEndDate;
    String id_project;
    BaseAPIService baseAPIService;
    Context mContext;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_project);
        namaProject = findViewById(R.id.update_nama_project);
        startProject = findViewById(R.id.update_start_date);
        endProject = findViewById(R.id.update_end_date);
        descProject = findViewById(R.id.update_desc_date);
        maxOrang = findViewById(R.id.update_max_orang);
        noHp = findViewById(R.id.update_no_Hproject);
        submit_update = findViewById(R.id.submit_update_project);
        selectStartDate = findViewById(R.id.select_update_start_date);
        selectStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(startProject);
            }
        });

        selectEndDate = findViewById(R.id.select_update_end_date);
        selectEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(endProject);
            }
        });
        baseAPIService = UtilsApi.getAPIService();
        mContext = this;

        Intent intent = getIntent();
        if(intent.getExtras() !=null){
            Project = (project) intent.getSerializableExtra("data");
            String namaproject = Project.getNama_project();
            String startdate = Project.getStart_project();
            String enddate = Project.getEnd_project();
            String desc = Project.getDesc_project();
            Log.d("desc",namaproject);
            this.id_project = Project.getId();
            namaProject.setText(namaproject);
            startProject.setText(startdate);
            endProject.setText(enddate);
            descProject.setText(desc);
            maxOrang.setText(Project.getMax_orang());
            noHp.setText(Project.getNo_hp());
        }

        submit_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseAPIService.updateMyProject(id_project, namaProject.getText().toString(), startProject.getText().toString(), endProject.getText().toString(), descProject.getText().toString(), noHp.getText().toString(), maxOrang.getText().toString())
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if(response.isSuccessful()){
                                    try {
                                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                        if (jsonRESULTS.getString("status").matches("200")){
                                            String message = jsonRESULTS.getString("message");
                                            Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(mContext, getProject.class);
                                            startActivity(intent);
                                        } else {
                                            String error_message = jsonRESULTS.getString("message");
                                            if (error_message.equals("failed")){
                                                Toast.makeText(mContext, "Update failed", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Toast.makeText(mContext, "HARAP PERIKSA KONEKSI ANDA!!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                t.printStackTrace();
                                Toast.makeText(mContext, "HARAP PERIKSA KONEKSI ANDA!!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private void showDateDialog(EditText editText) {
        Calendar newCalendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                editText.setText(df.format(newDate.getTime()));

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
}