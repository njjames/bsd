package com.example.administrator.boshide2.Main;

import android.app.Application;
import android.content.SharedPreferences;

import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Modular.View.ImageLoaderHelper;

/**
 * Created by Administrator on 2017-4-12.
 */

public class MyApplication extends Application {
    public static SharedPreferences shared;
    public static SharedPreferences.Editor editor;
    @Override
    public void onCreate() {
        super.onCreate();


        Request.intoRequest(getApplicationContext());
        shared = getSharedPreferences("BSD_APP", MODE_PRIVATE);
        editor = shared.edit();
        editor.putString("banben","你好");
        editor.commit();
        ImageLoaderHelper.init(getApplicationContext());

    }




}
