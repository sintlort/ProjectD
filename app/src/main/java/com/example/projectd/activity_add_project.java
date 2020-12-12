package com.example.projectd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projectd.Model.project;
import com.example.projectd.Preference.shared_preference_class;
import com.example.projectd.retrofitClient.BaseAPIService;
import com.example.projectd.retrofitClient.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

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
    String startProject, endProject, user;
    Button submitProject;
    Context mContext;
    BaseAPIService mApiService;

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
        mContext = this;
        mApiService = UtilsApi.getAPIService();
        user = shared_preference_class.getUsername(mContext);
        submitProject = findViewById(R.id.submitproject);
        submitProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request_addproject();

            }
        });

        sProject.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private String yyyymmdd = "YYYYMMDD";
            private Calendar cal = Calendar.getInstance();

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + yyyymmdd.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int year  = Integer.parseInt(clean.substring(0,4));
                        int mon  = Integer.parseInt(clean.substring(4,6));
                        int day = Integer.parseInt(clean.substring(6,8));

                        if(mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon-1);

                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",year, mon, day);
                    }

                    clean = String.format("%s-%s-%s", clean.substring(0, 4),
                            clean.substring(4, 6),
                            clean.substring(6, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    sProject.setText(current);
                    sProject.setSelection(sel < current.length() ? sel : current.length());
                    startProject = current;
                }
            }


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });

        eProject.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private String yyyymmdd = "YYYYMMDD";
            private Calendar cal = Calendar.getInstance();

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + yyyymmdd.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int year  = Integer.parseInt(clean.substring(0,4));
                        int mon  = Integer.parseInt(clean.substring(4,6));
                        int day = Integer.parseInt(clean.substring(6,8));

                        if(mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon-1);

                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",year, mon, day);
                    }

                    clean = String.format("%s-%s-%s", clean.substring(0, 4),
                            clean.substring(4, 6),
                            clean.substring(6, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    eProject.setText(current);
                    eProject.setSelection(sel < current.length() ? sel : current.length());
                    endProject = current;
                }
            }


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void request_addproject() {
        try {
            SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = curFormater.parse(startProject);
            Date date2 = curFormater.parse(endProject);
            Date now = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String formattedDate = df.format(now);
            Date datenow = curFormater.parse(formattedDate);
            if (datenow.compareTo(date1)>0){
                Toast.makeText(this, "Mulainya project tidak boleh kurang dari hari ini", Toast.LENGTH_SHORT).show();
            }else if(date1.compareTo(date2)>0){
                Toast.makeText(this, "date mulai lebih besar dariapda date selesai", Toast.LENGTH_SHORT).show();
            }else if(date1.compareTo(date2)<0){
                mApiService.addProjectRequest(user, nProject.getText().toString(),startProject, endProject, dProject.getText().toString(), 1, nHP.getText().toString(), xOrang.getText().toString())
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if(response.isSuccessful()){
                                    try {
                                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                        if (jsonRESULTS.getString("status").matches("200")){
                                            Toast.makeText(mContext, "BERHASIL Register Project", Toast.LENGTH_SHORT).show();
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
        } catch (ParseException e){
            e.printStackTrace();
        }

    }


}