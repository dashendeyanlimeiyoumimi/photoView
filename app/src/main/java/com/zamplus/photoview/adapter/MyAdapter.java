package com.zamplus.photoview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zamplus.photoview.R;
import com.zamplus.photoview.bean.SimpleData;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<SimpleData> data;
    private OnMyAdapterCallBack callBack;

    public MyAdapter(Context context) {
        this.context = context;
        this.data = new ArrayList<>();
    }

    public void setData(List<SimpleData> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setCallBack(OnMyAdapterCallBack callBack) {
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_one, null);
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
        RecyclerView recyclerView;
        TextView textView;


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.item_one_rv);
        }

        public void buildHolder(final int position) {
            recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
            ChildAdapter childAdater = new ChildAdapter(context);
            recyclerView.setAdapter(childAdater);
            childAdater.setData(data.get(position).getImgs());

            childAdater.setOnChildClick(new ChildAdapter.OnChildClick() {
                @Override
                public void onItemClick(int currentPosition) {
                    if (callBack != null) {
                        callBack.onItemClick(currentPosition, data.get(position).getImgs());
                    }
                }
            });
        }
    }

    public interface OnMyAdapterCallBack {
        void onItemClick(int position, ArrayList<String> itemImgs);
    }
}
