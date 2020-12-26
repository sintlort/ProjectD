package com.example.projectd.retrofitClient;

import com.example.projectd.Model.DetailProjectModel;
import com.example.projectd.Model.progressImageModel;
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
                                         @Field("max_orang") String max_orang,
                                         @Field("encoded_image") String encoded_image);

    @FormUrlEncoded
    @POST("get-project")
    Call<List<project>> getAllProject(@Field("id") String id);

    @FormUrlEncoded
    @POST("get-other-project")
    Call<List<project>> getOtherProject(@Field("id") String id);

    @FormUrlEncoded
    @POST("read-project")
    Call<ResponseBody> readMyProject(@Field("id") String id);

    @FormUrlEncoded
    @POST("update-project")
    Call<ResponseBody> updateMyProject(@Field("id") String id,
                                       @Field("nama_project") String nama_project,
                                       @Field("start_project") String start_project,
                                       @Field("end_project") String end_project,
                                       @Field("desc_project") String desc_project,
                                       @Field("no_hp") String no_hp,
                                       @Field("max_orang") String max_orang);

    @FormUrlEncoded
    @POST("stop-my-project")
    Call<ResponseBody> stopMyProject(@Field("id") String id);

    @FormUrlEncoded
    @POST("post-fcm")
    Call<ResponseBody> postFCM(@Field("username") String username);

    @GET("send-fcm")
    Call<ResponseBody> sendFCM();

    @FormUrlEncoded
    @POST("fcm-topics")
    Call<ResponseBody> fcmTopics(@Field("fcm") String fcm,
                                 @Field("message") String message);

    @FormUrlEncoded
    @POST("detail-my-project")
    Call<List<project>> getMyDetailProject(@Field("id_user") String id_user);

    @FormUrlEncoded
    @POST("join-project")
    Call<ResponseBody> joinProject(@Field("id_project") String id_project,
                                   @Field("id_user") String id_user);

    @FormUrlEncoded
    @POST("read-joined-project")
    Call<DetailProjectModel> detailProject(@Field("id_user") String id_user);

    @FormUrlEncoded
    @POST("leave-project")
    Call<ResponseBody> leaveProject(@Field("id_project") String id_project,
                                    @Field("id_user") String id_user);

    @FormUrlEncoded
    @POST("post-progress")
    Call<ResponseBody> postProgressProject(@Field("id_project") String id_project,
                                           @Field("id_user") String id_user,
                                           @Field("encoded_image") String encoded_image);

    @FormUrlEncoded
    @POST("get-progress-image")
    Call<List<progressImageModel>> getProgressImageAPI(@Field("id_project") String id_project,
                                                       @Field("id_user") String id_user);

    @FormUrlEncoded
    @POST("get-the-f-out-of-project")
    Call<ResponseBody> GTFOP(@Field("id_project") String id_project,
                             @Field("id_user") String id_user);

    @FormUrlEncoded
    @POST("update-profile")
    Call<ResponseBody> updateProfile(@Field("id_user") String id_user,
                                     @Field("alamat") String alamat,
                                     @Field("email") String email,
                                     @Field("telp") String telp);

    @FormUrlEncoded
    @POST("update-password")
    Call<ResponseBody> updatePassword(@Field("username") String username,
                                      @Field("password_lama") String password_lama,
                                      @Field("password_baru") String password_baru);

    @GET("get-the-project-d")
    Call<List<project>> getTheProjectD();

    @FormUrlEncoded
    @POST("count-my-joined-project")
    Call<ResponseBody> countMyAndJoinedProject(@Field("id") String id);

    @FormUrlEncoded
    @POST("get-my-progress-image")
    Call<List<progressImageModel>> getMyProgressImage(@Field("id_project") String id_project);

}
