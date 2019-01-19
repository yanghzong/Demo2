package com.example.demo2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.example.demo2.adapter.RecAdapter;
import com.example.demo2.bean.GoodShowBean;
import com.example.demo2.presenter.MyPresenter;
import com.example.demo2.presenter.PersneterInterface;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowGoodsActivity extends AppCompatActivity {
    @BindView(R.id.xrec_show)
    XRecyclerView xrecShow;
    private RecAdapter adapter;
    private MyPresenter mMyPsersenter;
    int page =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_goods);
        ButterKnife.bind(this);
        adapter = new RecAdapter(ShowGoodsActivity.this);
        xrecShow.setLayoutManager(new LinearLayoutManager(ShowGoodsActivity.this));
        xrecShow.setAdapter(adapter);
        initData();
        //设置Xrec可滑动刷新和加载
        xrecShow.setPullRefreshEnabled(true);
        xrecShow.setLoadingMoreEnabled(true);
        xrecShow.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                initData();
            }

            @Override
            public void onLoadMore() {
                page++;
                initData();
            }
        });
        //条目点击进入详情页
        adapter.setOnitemClickLis(new RecAdapter.OnitemClickLis() {
            @Override
            public void itemclick(int pid) {
                Intent intent = new Intent(ShowGoodsActivity.this, XiangQingActivity.class);
                intent.putExtra("pid",pid+"");
                startActivity(intent);
            }
        });
    }

    public void initData(){//初始化数据
        mMyPsersenter = new MyPresenter();
        Map<String,String> map = new HashMap<>();
        map.put("pscid","1");
        map.put("page",""+page);
        mMyPsersenter.Show(map, new PersneterInterface() {
            @Override
            public void onResponse(String json) {
                //Toast.makeText(ShowGoodsActivity.this,""+json,Toast.LENGTH_SHORT).show();
                Gson gson = new Gson();
                GoodShowBean goodShowBean = gson.fromJson(json, GoodShowBean.class);
                adapter.setList(goodShowBean.getData(),page);
                xrecShow.refreshComplete();
            }

            @Override
            public void onFailure() {
                Toast.makeText(ShowGoodsActivity.this,"服务器连接失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mMyPsersenter != null){
            mMyPsersenter = null;
        }
    }
}
