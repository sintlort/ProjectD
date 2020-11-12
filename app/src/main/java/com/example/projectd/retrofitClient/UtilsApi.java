package com.example.projectd.retrofitClient;

public class UtilsApi {
    // 10.0.2.2 ini adalah localhost.
    public static final String BASE_URL_API = "https://000projectd.000webhostapp.com/api/";

    // Mendeklarasikan Interface BaseApiService
    public static BaseAPIService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseAPIService.class);
    }
}
