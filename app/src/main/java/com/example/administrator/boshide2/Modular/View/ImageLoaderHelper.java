package com.example.administrator.boshide2.Modular.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;

import com.example.administrator.boshide2.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * Created by Administrator on 2016/7/12.
 */
public class ImageLoaderHelper {
    public static void init(Context context) {
        //config   全局的配置
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)// 线程的优先级别
                .denyCacheImageMultipleSizesInMemory() // 拒绝不同的缓存大小
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())// 对临时文件名加密
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb SDCard上的缓存空间
                .tasksProcessingOrder(QueueProcessingType.LIFO)// 任务队列采取LIFO
                .writeDebugLogs() //调试日志-可在项目发布时删除
                .build();// 构建配置
        //单例模式   初始化ImageLoader
        ImageLoader.getInstance().init(config);
    }

    /**
     * 普通的配置
     * 配置图片的显示设置
     * @return
     */
    public static DisplayImageOptions getOptionlist(Context context) {
        // 图片的显示设置(选项)
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                //.showImageOnLoading(R.drawable.ic_launcher)// 加载时的图标
                .showImageForEmptyUri(R.drawable.image_empty)//地址错误时的图标
                .showImageOnFail(R.drawable.image_empty)//加载失败的图标
                        //.resetViewBeforeLoading(true)// 在加载前重置View
                .cacheInMemory(true)//内存缓存 -- oom（建议不打开）
                .cacheOnDisk(true)// 在SDCard上缓存
                .imageScaleType(ImageScaleType.EXACTLY)//拉伸类型
                .bitmapConfig(Bitmap.Config.RGB_565)//图片的解码格式
                .displayer(new CircleBitmapDisplayer(Color.WHITE, 5))
                .considerExifParams(true)// 考虑JPEG的旋转
                .build();

        return options;

    }

    /**
     * 普通的配置
     * 配置图片的显示设置
     * @return
     */
    public static DisplayImageOptions getOptiongrid(Context context) {
        // 图片的显示设置(选项)
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                //.showImageOnLoading(R.drawable.ic_launcher)// 加载时的图标
                .showImageForEmptyUri(R.drawable.image_empty)//地址错误时的图标
                .showImageOnFail(R.drawable.image_empty)//加载失败的图标
                        .resetViewBeforeLoading(true)// 在加载前重置View
                .cacheInMemory(true)//内存缓存 -- oom（建议不打开）
                .cacheOnDisk(true)// 在SDCard上缓存
                .imageScaleType(ImageScaleType.EXACTLY)//拉伸类型
                .bitmapConfig(Bitmap.Config.RGB_565)//图片的解码格式
                .considerExifParams(true)// 考虑JPEG的旋转
                .displayer(new RoundedBitmapDisplayer(2))//是否设置为圆角，弧度为多少
                .build();
        // 滑动时加载图片，快速滑动时不加载图片
        return options;

    }
    public static DisplayImageOptions getOptiongridyuan(Context context) {
        // 图片的显示设置(选项)
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                //.showImageOnLoading(R.drawable.ic_launcher)// 加载时的图标
                .showImageForEmptyUri(R.drawable.image_empty)//地址错误时的图标
                .showImageOnFail(R.drawable.image_empty)//加载失败的图标
                        .resetViewBeforeLoading(true)// 在加载前重置View
                .cacheInMemory(true)//内存缓存 -- oom（建议不打开）
                .cacheOnDisk(true)// 在SDCard上缓存
                .imageScaleType(ImageScaleType.EXACTLY)//拉伸类型
                .bitmapConfig(Bitmap.Config.RGB_565)//图片的解码格式
                .considerExifParams(true)// 考虑JPEG的旋转
                .displayer(new RoundedBitmapDisplayer(100))//是否设置为圆角，弧度为多少
                .build();
        // 滑动时加载图片，快速滑动时不加载图片
        return options;

    }
}
