package com.example.projectd.retrofitClient;

import com.example.projectd.Model.project;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BaseAPIService {

    @FormUrlEncoded
    @POST("login-api")
    Call<ResponseBody> loginRequest(@Field("username") String username,
                                    @Field("password") String password);


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

    @FormUrlEncoded
    @POST("get-project")
    Call<List<project>> getAllProject(@Field("id") String id);

    @FormUrlEncoded
    @POST("get-other-project")
    Call<List<project>> getOtherProject(@Field("id") String id);

}
