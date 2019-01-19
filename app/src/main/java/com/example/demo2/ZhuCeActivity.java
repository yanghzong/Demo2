package com.example.demo2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.demo2.presenter.MyPresenter;
import com.example.demo2.presenter.PersneterInterface;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ZhuCeActivity extends AppCompatActivity {

    @BindView(R.id.zhuce_name)
    EditText zhuceName;
    @BindView(R.id.zhuce_pwd)
    EditText zhucePwd;
    @BindView(R.id.zhuce_pwd_1)
    EditText zhucePwd1;
    @BindView(R.id.zhuce_you)
    EditText zhuceYou;
    @BindView(R.id.zhuce_zhuce)
    Button zhuceZhuce;
    private MyPresenter mMyPsersenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhu_ce);
        ButterKnife.bind(this);//绑定View
        mMyPsersenter = new MyPresenter();

    }

    @OnClick(R.id.zhuce_zhuce)
    public void onViewClicked() {//数据的上传
        final String name = zhuceName.getText().toString().trim();
        final String pwd = zhucePwd.getText().toString().trim();
        String pwd1 = zhucePwd1.getText().toString().trim();
        String you = zhuceYou.getText().toString().trim();
        if (!mMyPsersenter.IsNotNull(name, pwd)) {
            Toast.makeText(this, "请输入正确格式的手机号和密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!mMyPsersenter.IsNotNullChongfu(pwd, pwd1)) {
            Toast.makeText(this, "密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("mobile", name);
        map.put("password", pwd);

        mMyPsersenter.Zhuce(map, new PersneterInterface() {
            @Override
            public void onResponse(String json) {
                if (json.equals("{\"msg\":\"密码格式有问题，不能少于6位数\",\"code\":\"1\",\"data\":\"{}\"}")) {
                    Toast.makeText(ZhuCeActivity.this, "密码格式有问题，不能少于6位数", Toast.LENGTH_SHORT).show();
                } else if (json.equals("{\"msg\":\"请输入正确的手机号码\",\"code\":\"1\",\"data\":\"{}\"}")) {
                    Toast.makeText(ZhuCeActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                } else if (json.equals("{\"msg\":\"天呢！用户已注册\",\"code\":\"1\",\"data\":\"{}\"}")) {
                    Toast.makeText(ZhuCeActivity.this, "该用户已注册", Toast.LENGTH_SHORT).show();
                } else if(json.equals("{\"msg\":\"注册成功\",\"code\":\"0\"}")){
                    SharedPreferences login = getSharedPreferences("Login", MODE_PRIVATE);
                    SharedPreferences.Editor edit = login.edit();
                    edit.putString("mobile",name);
                    edit.putString("password",pwd);
                    edit.commit();
                    startActivity(new Intent(ZhuCeActivity.this, MainActivity.class));

                    finish();
                }
            }

            @Override
            public void onFailure() {
                Toast.makeText(ZhuCeActivity.this, "请求服务器失败", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mMyPsersenter != null){
            mMyPsersenter=null;
        }

    }
}
