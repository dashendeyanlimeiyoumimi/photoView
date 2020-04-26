package com.zamplus.photoview.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;


import com.zamplus.photoview.R;
import com.zamplus.photoview.adapter.MyPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PhotoDialog extends DialogFragment {

    private static final String TAG = "PhotoDialog";

    private int currentPostion = -1;
    private List<String> imageData = new ArrayList<>();
    private ViewPager viewPager;
    private TextView textView;
    private MyPagerAdapter pagerAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            currentPostion = bundle.getInt("currentPostion");
            imageData = bundle.getStringArrayList("imageData");
        }

        View view = inflater.inflate(R.layout.dialog_photo, null);

        initView(view);
        initListener();
        return view;
    }


    private void initView(View view) {
        viewPager = view.findViewById(R.id.dialog_photo_vp);
        textView = view.findViewById(R.id.dialog_photo_tv);
        pagerAdapter = new MyPagerAdapter(getActivity(), imageData);
        viewPager.setAdapter(pagerAdapter);

        textView.setText(currentPostion + 1 + "/" + imageData.size());

        viewPager.setCurrentItem(currentPostion, false);

    }

    private void initListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                textView.setText(position + 1 + "/" + imageData.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        pagerAdapter.setCallBack(new MyPagerAdapter.onCallBack() {
            @Override
            public void onItemClick() {
                close();
            }
        });

    }

    private void close() {
        this.dismiss();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new Dialog(getActivity(), R.style.PhotoDialog);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // dialog进入和消失的动画
        getDialog().getWindow().getAttributes().windowAnimations = R.style.FragmentDialogAnimation;

    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                window.setLayout(width, height); // 使dialog充满真个屏幕
            }
        }
    }
}
