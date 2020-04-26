package com.zamplus.photoview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.zamplus.photoview.adapter.MyAdapter;
import com.zamplus.photoview.bean.SimpleData;
import com.zamplus.photoview.dialog.PhotoDialog;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private String img1 = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3933355596,669391437&fm=26&gp=0.jpg";
    private String img2 = "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2534506313,1688529724&fm=26&gp=0.jpg";
    private String img3 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1587730124660&di=a177f254bfe527404464445a217e6c16&imgtype=0&src=http%3A%2F%2Fa0.att.hudong.com%2F64%2F76%2F20300001349415131407760417677.jpg";
    private String img4 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1587730124659&di=ed8ce81d3cc7c6dc6a40063a00b53ca1&imgtype=0&src=http%3A%2F%2Fc.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2Fd009b3de9c82d1587e249850820a19d8bd3e42a9.jpg";
    private String img5 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1587730124658&di=3a42b6ed43b386575bdccc5fde7609a0&imgtype=0&src=http%3A%2F%2Fa4.att.hudong.com%2F21%2F09%2F01200000026352136359091694357.jpg";
    private String img6 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1587730124657&di=6ceaec668ecd86d49ea3c6379f9db0de&imgtype=0&src=http%3A%2F%2Fe.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2Fd62a6059252dd42a1c362a29033b5bb5c9eab870.jpg";
    private String img7 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1587730124656&di=730c7263c4715689709f3829755ef7ed&imgtype=0&src=http%3A%2F%2Fa0.att.hudong.com%2F27%2F10%2F01300000324235124757108108752.jpg";
    private String img8 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1587730124652&di=1fcd344029a7df90684e887840dcaee8&imgtype=0&src=http%3A%2F%2F46.s21i-2.faidns.com%2F2841046%2F2%2FABUIABACGAAg5Ou0mQUo1pmzjwMw6Ac4lAU.jpg";
    private String img9 = "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1669743261,233792530&fm=11&gp=0.jpg";


    private ArrayList<String> imageData = new ArrayList<>();
    private List<SimpleData> datas = new ArrayList<>();
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPermissions();

        initData();
        initView();
        initListener();
    }

    private void initPermissions() {
        ArrayList<String> permissionsToRequire = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequire.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            ActivityCompat.requestPermissions(this, (String[]) permissionsToRequire.toArray(new String[0]), 0);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequire.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            ActivityCompat.requestPermissions(this, (String[]) permissionsToRequire.toArray(new String[0]), 0);
        }
//        ActivityCompat.requestPermissions(this, (String[]) permissionsToRequire.toArray(new String[0]), 0);
    }

    private void initData() {
        SimpleData data = new SimpleData();
//        data.setTitle("秒速5厘米");
        imageData.add(img1);
        imageData.add(img2);
        imageData.add(img3);
        imageData.add(img4);
        imageData.add(img5);
        imageData.add(img6);
        imageData.add(img7);
        imageData.add(img8);
        imageData.add(img9);
        data.setImgs(imageData);
        datas.add(0, data);
    }


    private void initView() {
        recyclerView = findViewById(R.id.rv_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setData(datas);
    }

    private void initListener() {
        adapter.setCallBack(new MyAdapter.OnMyAdapterCallBack() {
            @Override
            public void onItemClick(int position, ArrayList<String> itemImgs) {
                Bundle bundle = new Bundle();
                ArrayList<String> data = new ArrayList<>();
                data.addAll(itemImgs);
                bundle.putInt("currentPostion", position);
                bundle.putStringArrayList("imageData", itemImgs);

                PhotoDialog photoDialog = new PhotoDialog();
                photoDialog.setArguments(bundle);
                photoDialog.show(getSupportFragmentManager(), "");
            }
        });
    }

}
