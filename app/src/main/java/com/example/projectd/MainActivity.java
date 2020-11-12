package com.example.projectd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectd.Preference.shared_preference_class;
import com.example.projectd.SQLite.DBProject;
import com.example.projectd.retrofitClient.BaseAPIService;
import com.example.projectd.retrofitClient.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText username, password;
    TextView register;
    Button login_button;
    Context mContext;
    BaseAPIService mApiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setBackgroundDrawableResource(R.drawable.background);
        login_button = (Button) findViewById(R.id.login_button);
        username = findViewById(R.id.inputusername);
        password = findViewById(R.id.inputPassword);
        mContext = this;
        mApiService = UtilsApi.getAPIService();
        DBProject dbProject = new DBProject(getBaseContext());
        Toast.makeText(getApplication(), dbProject.getDatabaseName(), Toast.LENGTH_SHORT).show();
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request_login();
            }
        });


        register = (TextView) findViewById(R.id.register_text);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register_intent = new Intent(MainActivity.this, register_activity.class);
                startActivity(register_intent);
            }
        });

        boolean status = shared_preference_class.getLoggedInStatus(mContext);
        if(status){
            Intent dashboard = new Intent(MainActivity.this, activity_dashboard.class);
            startActivity(dashboard);
        }

    }

    private void request_login() {
        mApiService.loginRequest(username.getText().toString(), password.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("status").matches("200")){
                                    Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                                    String user = jsonRESULTS.getJSONObject("user").getString("username");
                                    String nama = jsonRESULTS.getJSONObject("user").getString("nama_user");
                                    String dob = jsonRESULTS.getJSONObject("user").getString("tgl_lahir");
                                    String gender = jsonRESULTS.getJSONObject("user").getString("gender");
                                    String address = jsonRESULTS.getJSONObject("user").getString("alamat");
                                    String email = jsonRESULTS.getJSONObject("user").getString("email");
                                    String phone = jsonRESULTS.getJSONObject("user").getString("telp");
                                    shared_preference_class.setLoggedInUser(mContext,user,nama,dob,address,email,phone,gender);
                                    Toast.makeText(mContext, nama, Toast.LENGTH_SHORT).show();
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
}