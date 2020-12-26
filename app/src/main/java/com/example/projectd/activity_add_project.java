package com.example.projectd;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projectd.Model.project;
import com.example.projectd.Preference.shared_preference_class;
import com.example.projectd.retrofitClient.BaseAPIService;
import com.example.projectd.retrofitClient.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_add_project extends AppCompatActivity {
    EditText nProject, sProject, eProject, dProject, stProject, xOrang, nHP;
    ImageView projectImage;
    String startProject, endProject, user;
    Button submitProject, selectStartDate, selectEndDate, selectImage;
    Context mContext;
    int IMG_REQUEST = 21;
    private Bitmap bitmap;
    BaseAPIService mApiService;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        nProject = findViewById(R.id.nama_project);
        sProject = findViewById(R.id.start_project);
        eProject = findViewById(R.id.end_project);
        dProject = findViewById(R.id.desc_project);
        nHP = findViewById(R.id.no_Hproject);
        xOrang = findViewById(R.id.max_orang);
        selectStartDate = findViewById(R.id.select_start_date);
        selectEndDate = findViewById(R.id.select_end_date);
        selectImage = findViewById(R.id.select_image);
        submitProject = findViewById(R.id.submitproject);
        projectImage = findViewById(R.id.project_image);
        mContext = this;
        mApiService = UtilsApi.getAPIService();
        user = shared_preference_class.getUsername(mContext);
        submitProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_calendar();

            }
        });

        selectStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(sProject);
            }
        });

        selectEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(eProject);
            }
        });

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentz = new Intent();
                intentz.setType("image/*");
                intentz.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intentz, IMG_REQUEST);
            }
        });
    }


    private void check_calendar() {
        try {
            SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = curFormater.parse(sProject.getText().toString());
            Date date2 = curFormater.parse(eProject.getText().toString());
            Date now = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String formattedDate = df.format(now);
            Date datenow = curFormater.parse(formattedDate);
            if (datenow.compareTo(date1)>0){
                Toast.makeText(this, "Mulainya project tidak boleh kurang dari hari ini", Toast.LENGTH_SHORT).show();
            }else if(date1.compareTo(date2)>0){
                Toast.makeText(this, "Date mulai lebih besar dariapda date selesai", Toast.LENGTH_SHORT).show();
            }else if(date1.compareTo(date2)<0 ){
                String encoded_image = imageEncode(bitmap);
                request_project(encoded_image);
            } else {
                Toast.makeText(this, "Isi Form sepenuhnya", Toast.LENGTH_SHORT).show();
            }
        } catch (ParseException e){
            e.printStackTrace();
        }

    }

    private void request_project(String encoded_image) {
        mApiService.addProjectRequest(user, nProject.getText().toString(),sProject.getText().toString(), eProject.getText().toString(), dProject.getText().toString(), 1, nHP.getText().toString(), xOrang.getText().toString(), encoded_image)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("status").matches("200")){
                                    Toast.makeText(mContext, "BERHASIL Register Project", Toast.LENGTH_SHORT).show();
                                    sendFCM();
                                    Intent intent = new Intent(activity_add_project.this, getProject.class);
                                    startActivity(intent);
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
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMG_REQUEST && resultCode == RESULT_OK && data!=null){

            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                projectImage.setImageBitmap(bitmap);
                selectImage.setText("Selected");
            } catch (IOException e){
                e.printStackTrace();
            }

        }
    }

    private String imageEncode(Bitmap imageData){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imageData.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        byte[] imageByte = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imageByte, Base64.DEFAULT);
    }

    private void sendFCM(){
        mApiService.sendFCM().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}

