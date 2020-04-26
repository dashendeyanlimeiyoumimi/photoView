package com.zamplus.photoview.util;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.zamplus.photoview.MainActivity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;

/**
 * Created by Alex Yin
 * 日期: 2020/4/26
 * 时间: 14:30
 * 邮箱：yindechao2015@163.com
 * 该类作用：
 */
public class MyUtils {
    public static String addInputStreamToAlbum(Context context, InputStream inputStream, String displayName, String mimeType, Bitmap.CompressFormat compressFormat) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, displayName);
        values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM);
        } else {
            values.put(MediaStore.MediaColumns.DATA, Environment.getExternalStorageDirectory().getPath() + "/" + Environment.DIRECTORY_DCIM + "/" + displayName);
        }
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        if (uri != null) {
            try {
                OutputStream outputStream = context.getContentResolver().openOutputStream(uri);
                if (outputStream != null) {
                    BufferedOutputStream bos = new BufferedOutputStream(outputStream);
                    byte[] buffer = new byte[1024];
                    int bytes = bis.read(buffer);
                    while (bytes >= 0) {
                        bos.write(buffer, 0, bytes);
                        bos.flush();
                        bytes = bis.read(buffer);
                    }
                    bos.close();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //通知更新相册
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(uri);
        context.sendBroadcast(intent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return values.get(MediaStore.MediaColumns.RELATIVE_PATH)+"/"+values.get(MediaStore.MediaColumns.DISPLAY_NAME);
        } else {
            return String.valueOf(values.get(MediaStore.MediaColumns.DATA));
        }
    }

    /**
     * 获取网络图片流
     *
     * @param url
     * @return
     */
    public static InputStream getImageStream(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");


            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                return inputStream;
            }
        } catch (IOException e) {
            System.out.println("获取网络图片出现异常，图片路径为：" + url);
            e.printStackTrace();
        }
        return null;
    }


}
