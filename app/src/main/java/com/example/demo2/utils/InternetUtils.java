package com.example.demo2.utils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InternetUtils {
    private static InternetUtils mInternetUtil;
    private final OkHttpClient.Builder mBuilder;

    public InternetUtils(){
        mBuilder = new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS);
    }

    public static InternetUtils getInstance(){
        if(mInternetUtil == null){
            synchronized (InternetUtils.class){
                if(mInternetUtil == null){
                    return mInternetUtil = new InternetUtils();
                }
            }
        }
        return mInternetUtil;
    }

    public void doLogin(String url, Map<String,String> map, final UtilInterface utilInterface){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(mBuilder.build())
                .build();
        RetrofitLogin retrofitLogin = retrofit.create(RetrofitLogin.class);
        Call<ResponseBody> login = retrofitLogin.Login(map);
        login.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    utilInterface.onResponse(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                utilInterface.onFailure();
            }
        });
    }

    public void doZhuCe(String url, Map<String,String> map, final UtilInterface utilInterface){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(mBuilder.build())
                .build();
        RetrofitLogin retrofitLogin = retrofit.create(RetrofitLogin.class);
        Call<ResponseBody> login = retrofitLogin.ZhuCe(map);
        login.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    utilInterface.onResponse(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                utilInterface.onFailure();
            }
        });
    }

    public void doGeRen(String url, Map<String,String> map, final UtilInterface utilInterface){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(mBuilder.build())
                .build();
        RetrofitLogin retrofitLogin = retrofit.create(RetrofitLogin.class);
        Call<ResponseBody> login = retrofitLogin.GeRen(map);
        login.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    utilInterface.onResponse(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                utilInterface.onFailure();
            }
        });
    }

    public void Goods(String url, Map<String,String> map, final UtilInterface utilInterface){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(mBuilder.build())
                .build();
        RetrofitLogin retrofitLogin = retrofit.create(RetrofitLogin.class);
        Call<ResponseBody> login = retrofitLogin.Goods(map);
        login.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    utilInterface.onResponse(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                utilInterface.onFailure();
            }
        });
    }

    public void XiangQing(String url, Map<String,String> map, final UtilInterface utilInterface){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(mBuilder.build())
                .build();
        RetrofitLogin retrofitLogin = retrofit.create(RetrofitLogin.class);
        Call<ResponseBody> login = retrofitLogin.XiangQing(map);
        login.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    utilInterface.onResponse(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                utilInterface.onFailure();
            }
        });
    }
}
