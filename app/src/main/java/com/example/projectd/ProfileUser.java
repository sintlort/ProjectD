package com.example.projectd;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectd.Preference.shared_preference_class;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProfileUser extends AppCompatActivity {
    TextView bigNama, smallemail;
    EditText bottom_area_nama, bottom_area_alamat, bottom_area_email, bottom_area_phone;
    Button update_profile, update_password;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        bottom_area_nama = findViewById(R.id.bottom_area_name);
        bottom_area_alamat = findViewById(R.id.bottom_area_address);
        bottom_area_email = findViewById(R.id.bottom_area_email);
        bottom_area_phone = findViewById(R.id.bottom_area_phone);
        bigNama = findViewById(R.id.full_name);
        smallemail = findViewById(R.id.small_email);
        mContext = this;
        initProfile(mContext);
        update_profile = findViewById(R.id.update_profile);
        update_password = findViewById(R.id.update_password);

        update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileUser.this, EditProfile.class);
                startActivity(intent);
            }
        });

        update_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentz = new Intent(ProfileUser.this, UpdatePassword.class);
                startActivity(intentz);
            }
        });
    }

    private void initProfile(Context mContext) {
        bigNama.setText(shared_preference_class.getLoggedInName(mContext));
        smallemail.setText(shared_preference_class.getLoggedInEmail(mContext));
        bottom_area_nama.setText(shared_preference_class.getLoggedInName(mContext));
        bottom_area_alamat.setText(shared_preference_class.getLoggedInAlamat(mContext));
        bottom_area_email.setText(shared_preference_class.getLoggedInEmail(mContext));
        bottom_area_phone.setText(shared_preference_class.getLoggedInTelp(mContext));
    }
}