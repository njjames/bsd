package com.example.administrator.boshide2.Tools;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 2017-4-11.
 */

public class Show {
    public static int a=1;
    public static int b=1;
    public static int c=1;

    public static void show(Context context, String mag){
        if (a==1){
            Log.i("AOTU",mag);
            Toast.makeText(context,mag, Toast.LENGTH_LONG).show();
        }
    }
    public static void log(String mag){
        if (b==1){
            Log.i("AOTU",mag);
        }


    }
    public static void Toast(Context context,String mag){
        if (c==1){
            Toast.makeText(context,mag, Toast.LENGTH_SHORT).show();
        }


    }
    /**
     *时间可以自传入
     * 显示toast，自己定义显示长短。
     * param1:activity  传入context
     * param2:word   我们需要显示的toast的内容
     * param3:time length  long类型，我们传入的时间长度（如500）
     */
    public static void showTime(final Activity activity, final String word){
        activity.runOnUiThread(new Runnable() {
            public void run() {
                final Toast toast = Toast.makeText(activity, word, Toast.LENGTH_LONG);
                toast.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        toast.cancel();
                    }
                }, 500);
            }
        });
    }



}
