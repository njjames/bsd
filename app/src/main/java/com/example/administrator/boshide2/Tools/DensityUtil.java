package com.example.administrator.boshide2.Tools;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public class DensityUtil {

    private static float sNoncompatDensity;
    private static float sNoncompatScaledDensity;

    public static int dip2px(Context context, int dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static void setCustomDensity(Activity activity, final Application application) {
        DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();
        if (sNoncompatDensity == 0) {
            sNoncompatDensity = displayMetrics.density;
            sNoncompatScaledDensity = displayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration configuration) {
                    if (configuration != null && configuration.fontScale > 0) {
                        sNoncompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }
//            WindowManager windowManager =
//                    (WindowManager) application.getSystemService(Context.
//                            WINDOW_SERVICE);
//            final Display display = windowManager.getDefaultDisplay();
//            Point outPoint = new Point();
//            if (Build.VERSION.SDK_INT >= 19) {
//                // 可能有虚拟按键的情况
//                display.getRealSize(outPoint);
//            } else {
//                // 不可能有虚拟按键
//                display.getSize(outPoint);
//            }
//        float targetDensity = displayMetrics.heightPixels * 1.0f / 752;
        float targetDensity = 1.5f;
//            float targetDensity = outPoint.y * 1.0f / 600;
        float targetScaledDensity = targetDensity * (sNoncompatScaledDensity / sNoncompatDensity);
        displayMetrics.scaledDensity = targetScaledDensity;
        DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.scaledDensity = targetScaledDensity;

//        Log.i("scaledDensity", "setCustomDensity: " + activity.getResources().getDisplayMetrics().scaledDensity);
//        Log.i("scaledDensity", "sNoncompatScaledDensity: " + sNoncompatScaledDensity);
//        Log.i("scaledDensity", "sNoncompatDensity: " + sNoncompatDensity);
    }

    public static int getScreenHeight(Activity activity) {
        WindowManager manager = activity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }
}
