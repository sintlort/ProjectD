package com.example.projectd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectd.Preference.shared_preference_class;
import com.example.projectd.retrofitClient.BaseAPIService;
import com.example.projectd.retrofitClient.UtilsApi;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfile extends AppCompatActivity {
    TextView bigname, smollemail;
    EditText updateEmail, updateAlamat, updatePhone;
    Button submitUpdateProfile;
    BaseAPIService mApiService;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        bigname = findViewById(R.id.big_name);
        smollemail = findViewById(R.id.smoll_email);
        updateEmail = findViewById(R.id.email_baru);
        updateAlamat = findViewById(R.id.alamat_baru);
        updatePhone = findViewById(R.id.no_handphone_baru);
        submitUpdateProfile = findViewById(R.id.submit_update_profile);
        mApiService = UtilsApi.getAPIService();
        mContext = this;
        bigname.setText(shared_preference_class.getLoggedInName(mContext));
        smollemail.setText(shared_preference_class.getLoggedInEmail(mContext));
        submitUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });
    }

    private void updateProfile() {
        ArrayList<String> data = new ArrayList<>();
        if(updateEmail.getText().toString().equals("")){
            data.add(shared_preference_class.getLoggedInEmail(mContext));
        } else {
            data.add(updateEmail.getText().toString());
        }

        if(updatePhone.getText().toString().equals("")){
            data.add(shared_preference_class.getLoggedInTelp(mContext));
        } else {
            data.add(updatePhone.getText().toString());
        }

        if(updateAlamat.getText().toString().equals("")){
            data.add(shared_preference_class.getLoggedInAlamat(mContext));
        } else {
            data.add(updateAlamat.getText().toString());
        }

        mApiService.updateProfile(shared_preference_class.getLoggedInId(mContext),data.get(0), data.get(1), data.get(2))
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(mContext, "Profile berhasil diupdate",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EditProfile.this, ProfileUser.class);
                            shared_preference_class.setUpdatedProfileData(mContext, data.get(2), data.get(0), data.get(1));
                            startActivity(intent);
                        } else {
                            Toast.makeText(mContext, "Profile tidak berhasil diupdate", Toast.LENGTH_SHORT).show();
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