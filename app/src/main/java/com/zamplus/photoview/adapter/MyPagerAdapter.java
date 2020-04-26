package com.zamplus.photoview.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.zamplus.photoview.util.DialogUtils;

import java.util.List;

public class MyPagerAdapter extends PagerAdapter {
    private static final String TAG = "MyPagerAdapter";
    private Context context;
    private List<String> data;
    private onCallBack callBack;

    public MyPagerAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    public void setCallBack(onCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        PhotoView photoView = new PhotoView(context);
        //data里是uri
        Glide.with(context).load(data.get(position)).into(photoView);
        container.addView(photoView);

        photoView.setOnClickListener(new View.OnClickListener() { // 给每个ViewPager加载页添加点击事件,点击消失
            @Override
            public void onClick(View view) {
                if (callBack != null) {
                    callBack.onItemClick();
                }
            }
        });

        photoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DialogUtils.onCreateDialog(context,data.get(position)).show();
                return false;
            }
        });

        return photoView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public interface onCallBack {
        void onItemClick();
    }
}
