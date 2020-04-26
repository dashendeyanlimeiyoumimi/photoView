package com.zamplus.photoview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zamplus.photoview.R;
import com.zamplus.photoview.util.ButtonUtils;
import com.zamplus.photoview.util.CustomRoundAngleImageView;

import java.util.ArrayList;
import java.util.List;

public class ChildAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<String> data;

    private OnChildClick onChildClick;

    public ChildAdapter(Context context) {
        this.context = context;
        this.data = new ArrayList<>();
    }

    public void setData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setOnChildClick(OnChildClick onChildClick) {
        this.onChildClick = onChildClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_two, null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyHolder) {
            ((MyHolder) holder).buildHolder(position);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private class MyHolder extends RecyclerView.ViewHolder {
        CustomRoundAngleImageView imageView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.item_two_iv);
        }

        public void buildHolder(final int position) {
            RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.ic_launcher)//图片加载出来前，显示的图片
                    .fallback(R.mipmap.a) //url为空的时候,显示的图片
                    .error(R.mipmap.b);//图片加载失败后，显示的图片 图片加载失败


            Glide.with(context).load(data.get(position))
                    .apply(options)
                    .into(imageView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onChildClick != null) {
                        if (!ButtonUtils.isFastDoubleClick()) {
                            onChildClick.onItemClick(position);
                        }
                    }
                }
            });

        }
    }

    public interface OnChildClick {
        void onItemClick(int position);
    }
}
