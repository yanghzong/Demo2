package com.example.demo2.utils;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface RetrofitLogin {
    @GET("login")
    Call<ResponseBody> Login(@QueryMap Map<String, String> map);

    @GET("reg")
    Call<ResponseBody> ZhuCe(@QueryMap Map<String, String> map);

    @GET("getUserInfo")
    Call<ResponseBody> GeRen(@QueryMap Map<String, String> map);

    @GET("getProducts")
    Call<ResponseBody> Goods(@QueryMap Map<String, String> map);

    @GET("getProductDetail")
    Call<ResponseBody> XiangQing(@QueryMap Map<String, String> map);
}
