package com.example.demo2.presenter;

import android.text.TextUtils;

import com.example.demo2.inter.AllUrl;
import com.example.demo2.utils.InternetUtils;
import com.example.demo2.utils.UtilInterface;

import java.util.Map;

public class MyPresenter {
    public boolean IsNotNull(String num,String pwd){
        if(TextUtils.isEmpty(num) || TextUtils.isEmpty(pwd)){
            return false;
        }
        return true;
    }
    public boolean IsNotNullChongfu(String num,String num1){
        if(!num.equals(num1)){
            return false;
        }
        return true;
    }

    public void Login(Map<String,String> map, final PersneterInterface persneterInterface){
        new InternetUtils().getInstance().doLogin(AllUrl.Login, map, new UtilInterface() {
            @Override
            public void onResponse(String json) {
                persneterInterface.onResponse(json);
            }

            @Override
            public void onFailure() {
                persneterInterface.onFailure();
            }
        });

    }
    public void Zhuce(Map<String,String> map, final PersneterInterface persneterInterface){
        new InternetUtils().getInstance().doZhuCe(AllUrl.ZHUCE, map, new UtilInterface() {
            @Override
            public void onResponse(String json) {
                persneterInterface.onResponse(json);
            }

            @Override
            public void onFailure() {
                persneterInterface.onFailure();
            }
        });
    }
    public void GeRen(Map<String,String> map, final PersneterInterface persneterInterface){
        new InternetUtils().getInstance().doGeRen(AllUrl.GEREN, map, new UtilInterface() {
            @Override
            public void onResponse(String json) {
                persneterInterface.onResponse(json);
            }

            @Override
            public void onFailure() {
                persneterInterface.onFailure();
            }
        });
    }

    public void Show(Map<String,String> map, final PersneterInterface persneterInterface){
        new InternetUtils().getInstance().Goods(AllUrl.GOODS, map, new UtilInterface() {
            @Override
            public void onResponse(String json) {
                persneterInterface.onResponse(json);
            }

            @Override
            public void onFailure() {
                persneterInterface.onFailure();
            }
        });
    }

    public void Xiang(Map<String,String> map, final PersneterInterface persneterInterface){
        new InternetUtils().getInstance().XiangQing(AllUrl.XIANGQING, map, new UtilInterface() {
            @Override
            public void onResponse(String json) {
                persneterInterface.onResponse(json);
            }

            @Override
            public void onFailure() {
                persneterInterface.onFailure();
            }
        });
    }
}
