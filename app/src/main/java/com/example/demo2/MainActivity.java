package com.example.demo2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.demo2.bean.LoginBean;
import com.example.demo2.presenter.MyPresenter;
import com.example.demo2.presenter.PersneterInterface;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import com.example.demo2.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.main_phone)
    EditText mainPhone;
    @BindView(R.id.main_pwd)
    EditText mainPwd;
    @BindView(R.id.main_zhuce)
    TextView mainZhuce;
    @BindView(R.id.main_login)
    Button mainLogin;
    private MyPresenter mMyPsersenter;
    private SharedPreferences mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //保存密码  和获取密码
        mLogin = getSharedPreferences("Login", MODE_PRIVATE);
        initData();

        String mobile = mLogin.getString("mobile", "");
        String password = mLogin.getString("password", "");


        //点击登录按钮
        mainLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = mainPhone.getText().toString().trim();
                String pwd = mainPwd.getText().toString().trim();
                Map<String,String> map = new HashMap<>();//传值
                map.put("mobile",phone);
                map.put("password",pwd);
                if(!mMyPsersenter.IsNotNull(phone,pwd)){
                    Toast.makeText(MainActivity.this,"请输入正确格式的手机号和密码",Toast.LENGTH_SHORT).show();
                    return;
                }
                mMyPsersenter.Login(map, new PersneterInterface() {
                    @Override
                    public void onResponse(String json) {
                        //验证返回数据的正确性
                        if(json.equals("{\"msg\":\"密码格式有问题，不能少于6位数\",\"code\":\"1\"}")){
                            Toast.makeText(MainActivity.this,"密码格式有问题，不能少于6位数",Toast.LENGTH_SHORT).show();
                        }else if(json.equals("{\"msg\":\"请输入正确的手机号码\",\"code\":\"1\"}")){
                            Toast.makeText(MainActivity.this,"请输入正确的手机号码",Toast.LENGTH_SHORT).show();
                        }else if(json.equals("{\"msg\":\"天呢！用户不存在\",\"code\":\"1\"}")){
                            Toast.makeText(MainActivity.this,"请先注册",Toast.LENGTH_SHORT).show();
                        }else {
                            Gson gson = new Gson();
                            LoginBean loginBean = gson.fromJson(json, LoginBean.class);
                            //执行正确  跳转页面
                            SharedPreferences.Editor edit = mLogin.edit();
                            edit.putString("mobile",loginBean.getData().getMobile());
                            edit.putString("password",loginBean.getData().getPassword());
                            edit.putString("token",loginBean.getData().getToken());
                            edit.putString("uid",loginBean.getData().getUid()+"");
                            edit.putString("username",loginBean.getData().getUsername());
                            edit.commit();
                            startActivity(new Intent(MainActivity.this,GeRenActivity.class));
                            finish();
                        }


                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(MainActivity.this,"链接服务器失败",Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }


    @OnClick({R.id.main_zhuce})
    public void onViewClicked() {
        startActivity(new Intent(MainActivity.this,ZhuCeActivity.class));
    }

    public void initData(){
        mMyPsersenter = new MyPresenter();

    }

    @Override
    protected void onDestroy() {
        //释放内存
        super.onDestroy();
        if(mMyPsersenter !=null){
            mMyPsersenter = null;
        }
    }
}
