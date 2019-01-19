package com.example.demo2;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo2.bean.GeRenBeans;
import com.example.demo2.presenter.MyPresenter;
import com.example.demo2.presenter.PersneterInterface;
import com.google.gson.Gson;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GeRenActivity extends AppCompatActivity {

    @BindView(R.id.tou)
    ImageView tou;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.clicks)
    Button clicks;
    @BindView(R.id.tuichu)
    TextView tuichu;
    private SharedPreferences mLogin;
    private String path = Environment.getExternalStorageState() + "/iamge";
    private MyPresenter mMyPsersenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ge_ren);
        ButterKnife.bind(this,this);
        initDatas();//初始化数据  进行网络请求的到数据
    }

    private void initDatas() {
        //p层
        mMyPsersenter = new MyPresenter();
        mLogin = getSharedPreferences("Login", MODE_PRIVATE);
        String uid = mLogin.getString("uid", "");
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid);
        mMyPsersenter.GeRen(map, new PersneterInterface() {
            //判断数据的正确性
            @Override
            public void onResponse(String json) {
                //吐司
                Toast.makeText(GeRenActivity.this, "" + json, Toast.LENGTH_SHORT).show();
                if (json.equals("{\"msg\":\"没有此用户信息\",\"code\":\"1\"}")) {
                    Toast.makeText(GeRenActivity.this, "没有此用户信息", Toast.LENGTH_SHORT).show();
                    return;
                }
                Gson gson = new Gson();
                final GeRenBeans geRenBeans = gson.fromJson(json, GeRenBeans.class);
                final String nickname = geRenBeans.getData().getNickname();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //显示用户昵称
                        name.setText(nickname);

                    }
                });
            }

            //失败
            @Override
            public void onFailure() {
                Toast.makeText(GeRenActivity.this, "请求服务器失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick({R.id.tou, R.id.clicks})
    public void onViewClicked(View view) {
        //更换头像操作
        switch (view.getId()) {
            case R.id.tou:

                new AlertDialog.Builder(GeRenActivity.this)
                        .setTitle("更换头像")
                        .setIcon(R.mipmap.ic_launcher)
                        .setMessage("请选择")
                        .setNegativeButton("相册", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(intent, 1000);
                            }
                        })
                        .setPositiveButton("相机", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));
                                startActivityForResult(intent, 1500);
                            }
                        })
                        .create()
                        .show();


                break;
            case R.id.clicks:
                //进入到商品展示页面
                startActivity(new Intent(GeRenActivity.this, ShowGoodsActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000 && resultCode == RESULT_OK) {//相片的截取操作
            Uri uri = data.getData();
            Intent intent = new Intent("com.android.camera.action.CROP");

            intent.setDataAndType(uri, "image/*");

            intent.putExtra("crop", true);

            intent.putExtra("aspectX", 1);
            intent.putExtra("outputX", 250);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputY", 250);

            intent.putExtra("return-data", true);


            startActivityForResult(intent, 2000);
        }
        if (requestCode == 1500 && resultCode == RESULT_OK) {
            Intent intent = new Intent("com.android.camera.action.CROP");

            intent.setDataAndType(Uri.fromFile(new File(path)), "image/*");

            intent.putExtra("crop", true);

            intent.putExtra("aspectX", 1);
            intent.putExtra("outputX", 250);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputY", 250);

            intent.putExtra("return-data", true);
        }
        if (requestCode == 2000 && resultCode == RESULT_OK) {
            Bitmap bitmap = data.getParcelableExtra("data");
            tou.setImageBitmap(bitmap);
            Toast.makeText(GeRenActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mMyPsersenter != null){
            mMyPsersenter = null;
        }
    }

    @OnClick(R.id.tuichu)
    public void onViewClicked() {

        SharedPreferences.Editor edit = mLogin.edit();
        edit.putString("mobile","");
        edit.putString("password","");
        edit.putString("token","");
        edit.putString("uid","");
        edit.putString("username","");
        edit.commit();
        startActivity(new Intent(GeRenActivity.this,MainActivity.class));
        finish();
    }
}
