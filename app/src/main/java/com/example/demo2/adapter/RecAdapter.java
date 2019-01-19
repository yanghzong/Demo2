package com.example.demo2.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.demo2.R;
import com.example.demo2.bean.GoodShowBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<GoodShowBean.DataBean> list;

    public RecAdapter(Context context) {
        mContext = context;
        list = new ArrayList<>();
    }

    public void setList(ArrayList<GoodShowBean.DataBean> beans, int page) {
        if (page == 1) {
            this.list.clear();
        }

        if(beans != null){
            this.list.addAll(beans);
        }
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public SimpleDraweeView item_image;
        public TextView item_price;
        public TextView item_miao;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.item_image = (SimpleDraweeView) itemView.findViewById(R.id.item_image);
            this.item_price = (TextView) itemView.findViewById(R.id.item_price);
            this.item_miao = (TextView) itemView.findViewById(R.id.item_miao);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = View.inflate(mContext, R.layout.item, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        String[] img = list.get(position).getImages().trim().split("!");
        holder.item_image.setImageURI(img[0]);
        holder.item_miao.setText(list.get(position).getSubhead());
        holder.item_price.setText("$:"+list.get(position).getPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnitemClickLis.itemclick(list.get(position).getPid());
            }
        });
    }

    public void setOnitemClickLis(OnitemClickLis onitemClickLis) {
        mOnitemClickLis = onitemClickLis;
    }

    OnitemClickLis mOnitemClickLis;

    public interface OnitemClickLis{
        void itemclick(int pid);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
