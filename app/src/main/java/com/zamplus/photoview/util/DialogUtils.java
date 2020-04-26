package com.zamplus.photoview.util;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Alex Yin
 * 日期: 2020/4/26
 * 时间: 13:54
 * 邮箱：yindechao2015@163.com
 * 该类作用：
 */
public class DialogUtils {
    private static String[] items = new String[]{"保存图片"};
//    private static Uri uri1;
    private static Context context1;

    public static Dialog onCreateDialog(final Context context, final String uri) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        context1 = context;
        builder.setItems(items, new DialogInterface.OnClickListener() {

            private InputStream inputStream;

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //保存照片

                //根据uri获取InputStream

                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        InputStream inputStream = MyUtils.getImageStream(uri);
                        String displayName = System.currentTimeMillis()+".jpg";
                        String mimeType = "image/jpeg";
                        Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;
                        String uri1 = MyUtils.addInputStreamToAlbum(context, inputStream, displayName, mimeType, compressFormat);
                        //handler将uri1传出去，然后在主线程弹出toast
                        Message message = Message.obtain();
                        message.what = 1;
                        message.obj = uri1;
                        handler.sendMessage(message);
                    }
                }.start();





            }
        });

        return builder.create();
    }


    public static Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    String uri = (String) msg.obj;
                    Toast.makeText(context1, "已成功将图片保存至："+uri, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };


}
