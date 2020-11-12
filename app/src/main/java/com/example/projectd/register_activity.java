package com.example.projectd;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectd.Preference.shared_preference_class;
import com.example.projectd.retrofitClient.BaseAPIService;
import com.example.projectd.retrofitClient.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class register_activity extends AppCompatActivity {
    Context mContext;
    EditText usernamer, passwordr, namar, tanggalr, emailr, phoner, addressr;
    Button submit;
    RadioGroup mRadioGroup;
    String dob , mGender = "Laki laki";
    BaseAPIService mApiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity);
        getWindow().setBackgroundDrawableResource(R.drawable.background);
        usernamer = findViewById(R.id.usernamer);
        passwordr = findViewById(R.id.passwordr);
        namar = findViewById(R.id.namar);
        tanggalr = findViewById(R.id.tanggalr);
        emailr = findViewById(R.id.emailr);
        addressr = findViewById(R.id.addressr);
        phoner = findViewById(R.id.telpr);
        submit = findViewById(R.id.submit_register);
        mContext = this;
        mApiService = UtilsApi.getAPIService();

        tanggalr.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "YYYYMMDD";
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
                        clean = clean + ddmmyyyy.substring(clean.length());
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

                    clean = String.format("%s/%s/%s", clean.substring(0, 4),
                            clean.substring(4, 6),
                            clean.substring(6, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    tanggalr.setText(current);
                    tanggalr.setSelection(sel < current.length() ? sel : current.length());
                    dob = current;
                    Log.d("MAINDATE",dob);
                }
            }


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request_register();
            }
        });

        mRadioGroup = findViewById(R.id.radiogroupr);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.rad1:
                        mGender = "Laki laki";
                        Log.d("SEX",mGender);
                        break;
                    case R.id.rad2:
                        mGender = "Perempuan";
                        Log.d("SEX",mGender);
                        break;
                }
            }
        });

    }
    private void request_register() {
        mApiService.registerRequest(usernamer.getText().toString(), passwordr.getText().toString(), namar.getText().toString(), dob,mGender, addressr.getText().toString(), emailr.getText().toString(), phoner.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("status").matches("200")){
                                    Toast.makeText(mContext, "BERHASIL REGISTRASI", Toast.LENGTH_SHORT).show();
                                    String nama = jsonRESULTS.getJSONObject("user").getString("nama_user");
                                    Toast.makeText(mContext, nama, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(register_activity.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    String error_message = jsonRESULTS.getString("message");
                                    if (error_message.equals("username has been used")){
                                        Toast.makeText(mContext, "Username telah digunakanw!!", Toast.LENGTH_SHORT).show();
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


}