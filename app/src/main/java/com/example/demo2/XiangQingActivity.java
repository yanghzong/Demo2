package com.example.demo2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo2.bean.XiangQing;
import com.example.demo2.presenter.MyPresenter;
import com.example.demo2.presenter.PersneterInterface;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class XiangQingActivity extends Activity {


    @BindView(R.id.showVideo)
    JCVideoPlayerStandard showVideo;
    @BindView(R.id.show_image)
    SimpleDraweeView showImage;
    @BindView(R.id.show_name)
    TextView showName;
    @BindView(R.id.show_price)
    TextView showPrice;
    String urlString = "http://ips.ifeng.com/video19.ifeng.com/video09/2014/06/16/1989823-102-086-0009.mp4";
    @BindView(R.id.jia)
    Button jia;
    @BindView(R.id.pay)
    Button pay;
    private String pid;
    private MyPresenter mMyPsersenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiang_qing);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        pid = intent.getStringExtra("pid");
        showVideo.setUp(urlString, showVideo.SCREEN_LAYOUT_NORMAL, "商品视频");
        initData();
    }

    private void initData() {
        mMyPsersenter = new MyPresenter();
        Map<String, String> map = new HashMap<>();
        map.put("pid", pid);
        mMyPsersenter.Xiang(map, new PersneterInterface() {
            @Override
            public void onResponse(String json) {
                if (json.equals("{}")) {
                    Toast.makeText(XiangQingActivity.this, "没有此商品", Toast.LENGTH_SHORT).show();
                    return;
                }
                Gson gson = new Gson();
                XiangQing xiangQing = gson.fromJson(json, XiangQing.class);
                XiangQing.DataBean data = xiangQing.getData();
                String[] split = data.getImages().trim().split("!");
                showImage.setImageURI(split[0]);
                showName.setText(data.getTitle());
                showPrice.setText(data.getPrice() + "");

            }

            @Override
            public void onFailure() {
                Toast.makeText(XiangQingActivity.this, "连接服务失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMyPsersenter = null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @OnClick({R.id.jia, R.id.pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.jia:
                Toast.makeText(XiangQingActivity.this, "已加入购物车", Toast.LENGTH_SHORT).show();
                break;
            case R.id.pay:
                Toast.makeText(XiangQingActivity.this, "支付完成", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
