package com.example.projectd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectd.Preference.shared_preference_class;
import com.example.projectd.retrofitClient.BaseAPIService;
import com.example.projectd.retrofitClient.UtilsApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePassword extends AppCompatActivity {
    EditText passwordLama, passwordBaru, passwordBaru2;
    TextView fullname, smallemail;
    Button submitUpdatePassword;
    BaseAPIService mApiService;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        mContext = this;
        passwordLama = findViewById(R.id.password_lama);
        passwordBaru = findViewById(R.id.password_baru);
        passwordBaru2 = findViewById(R.id._ulangi_password_baru);
        fullname = findViewById(R.id.full_name);
        smallemail = findViewById(R.id.small_email);
        fullname.setText(shared_preference_class.getLoggedInName(mContext));
        smallemail.setText(shared_preference_class.getLoggedInEmail(mContext));
        mApiService = UtilsApi.getAPIService();
        submitUpdatePassword = findViewById(R.id._update_password);
        submitUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passwordLama.getText().toString().equals("")){
                    Toast.makeText(mContext, "Harap password lama diisi", Toast.LENGTH_SHORT).show();
                } else {
                    if(passwordBaru.getText().toString().matches(passwordBaru2.getText().toString())){
                        updatePassword();
                    } else {
                        Toast.makeText(mContext, "Kedua password baru tidak cocok", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void updatePassword() {
        mApiService.updatePassword(shared_preference_class.getUsername(mContext),passwordLama.getText().toString(), passwordBaru.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(mContext, "Password berhasil diupdate", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UpdatePassword.this, ProfileUser.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(mContext, "Password lama salah!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(mContext, "HARAP PERIKSA KONEKSI ANDA!!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}