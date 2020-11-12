package com.example.projectd.retrofitClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BaseAPIService {
    // Fungsi ini untuk memanggil API http://10.0.2.2/mahasiswa/login.php
    @FormUrlEncoded
    @POST("login-api")
    Call<ResponseBody> loginRequest(@Field("username") String username,
                                    @Field("password") String password);

    // Fungsi ini untuk memanggil API http://10.0.2.2/mahasiswa/register.php
    @FormUrlEncoded
    @POST("register-api")
    Call<ResponseBody> registerRequest(@Field("username") String username,
                                       @Field("password") String password,
                                       @Field("nama_user") String name,
                                       @Field("tgl_lahir") String dob,
                                       @Field("gender") String gender,
                                       @Field("alamat") String address,
                                       @Field("email") String email,
                                       @Field("telp") String phone);

    @FormUrlEncoded
    @POST("add-project-api")
    Call<ResponseBody> addProjectRequest(@Field("username") String username,
                                       @Field("nama_project") String nama_project,
                                       @Field("start_project") String start_project,
                                       @Field("end_project") String end_project,
                                       @Field("desc_project") String desc_project,
                                       @Field("status_project") int status_project,
                                       @Field("no_hp") String no_hp,
                                       @Field("max_orang") String max_orang);

}
