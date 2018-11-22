package com.example.administrator.boshide2.UpdataManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UpdateManager {
    private String downloadUrl;
    private String curVersion;
    private String newVersion;
    private int curVersionCode;
    private String newVersionCode;
    private String updateInfo;
    private UpdateCallback callback;
    private Context ctx;
    private int progress;
    private Boolean hasNewVersion;
    private Boolean canceled;
    // 存放更新APK文件的路径
    public static String UPDATE_DOWNURL = "http://wdpbjd.bsd268.com:8888/app-releaseV1.0.apk";
    // 存放更新APK文件相应的版本说明路径
    public static final String UPDATE_CHECKURL = "http://wdpbjd.bsd268.com:8888/app-releaseV1.0.apk";
    private static final int UPDATE_CHECKCOMPLETED = 1;
    private static final int UPDATE_DOWNLOADING = 2;
    private static final int UPDATE_DOWNLOAD_ERROR = 3;
    private static final int UPDATE_DOWNLOAD_COMPLETED = 4;
    private static final int UPDATE_DOWNLOAD_CANCELED = 5;

    // 从服务器上下载apk存放文件夹
    private String savefolder;
    ;

    // private String savefolder = "/sdcard/";
    // public static final String SAVE_FOLDER =Storage. // "/mnt/innerDisk";
    public UpdateManager(Context context, String url, UpdateCallback updateCallback) {
        ctx = context;
        UPDATE_DOWNURL = url;
        downloadUrl = url;
        callback = updateCallback;
        canceled = false;
        savefolder = Environment.getExternalStorageDirectory().getPath();
    }

    public void checkUpdate() {
        hasNewVersion = true;
        new Thread() {
            @Override
            public void run() {
                try {
                    String verjson = NetHelper.httpStringGet(UPDATE_CHECKURL);
                    JSONObject array = new JSONObject(verjson);
                    try {
                        newVersionCode = array
                                .getString("version");
                        newVersion = array.getString("version");
                        updateInfo = "";
                        if (!curVersion.equals(newVersionCode)) {
                            hasNewVersion = true;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        newVersionCode = "-1";
                        newVersion = "";
                        updateInfo = "";

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                updateHandler.sendEmptyMessage(UPDATE_CHECKCOMPLETED);
            }
        }.start();

    }

    public void update() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(
                Uri.fromFile(new File(savefolder, "app-release.apk")),
                "application/vnd.android.package-archive");
        ctx.startActivity(intent);
    }

    public void downloadPackage() {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(downloadUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    int length = conn.getContentLength();
                    InputStream is = conn.getInputStream();
                    File ApkFile = new File(savefolder, "app-release.apk");
                    if (ApkFile.exists()) {
                        ApkFile.delete();
                    }
                    FileOutputStream fos = new FileOutputStream(ApkFile);
                    int count = 0;
                    byte buf[] = new byte[512];
                    do {
                        int numread = is.read(buf);
                        count += numread;
                        progress = (int) (((float) count / length) * 100);
                        updateHandler.sendMessage(updateHandler.obtainMessage(UPDATE_DOWNLOADING));
                        if (numread <= 0) {
                            updateHandler.sendEmptyMessage(UPDATE_DOWNLOAD_COMPLETED);
                            break;
                        }
                        fos.write(buf, 0, numread);
                    } while (!canceled);
                    if (canceled) {
                        updateHandler.sendEmptyMessage(UPDATE_DOWNLOAD_CANCELED);
                    }
                    fos.close();
                    is.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    updateHandler.sendMessage(updateHandler.obtainMessage(UPDATE_DOWNLOAD_ERROR, e.getMessage()));
                } catch (IOException e) {
                    e.printStackTrace();
                    updateHandler.sendMessage(updateHandler.obtainMessage(UPDATE_DOWNLOAD_ERROR, e.getMessage()));
                }
            }
        }.start();
    }

    public void cancelDownload() {
        canceled = true;
    }

    @SuppressLint("HandlerLeak")
    Handler updateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_CHECKCOMPLETED:
                    callback.checkUpdateCompleted(hasNewVersion, newVersion);
                    break;
                case UPDATE_DOWNLOADING:
                    callback.downloadProgressChanged(progress);
                    break;
                case UPDATE_DOWNLOAD_ERROR:
                    callback.downloadCompleted(false, msg.obj.toString());
                    break;
                case UPDATE_DOWNLOAD_COMPLETED:
                    callback.downloadCompleted(true, "");
                    break;
                case UPDATE_DOWNLOAD_CANCELED:
                    callback.downloadCanceled();
                default:
                    break;
            }
        }
    };

    public interface UpdateCallback {
        void checkUpdateCompleted(Boolean hasUpdate, CharSequence updateInfo);

        void downloadProgressChanged(int progress);

        void downloadCanceled();

        void downloadCompleted(Boolean sucess, CharSequence errorMsg);
    }

}
