package com.example.administrator.boshide2.UpdataManager;

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
	public static  String UPDATE_DOWNURL = "http://wdpbjd.bsd268.com:8888/app-releaseV1.0.apk";
	// 存放更新APK文件相应的版本说明路径
	public static final String UPDATE_CHECKURL = "http://wdpbjd.bsd268.com:8888/app-releaseV1.0.apk";
	// public static final String UPDATE_VERJSON = "ver.txt";
	public static final String UPDATE_SAVENAME = "http://wdpbjd.bsd268.com:8888/app-releaseV1.0.apk";
	private static final int UPDATE_CHECKCOMPLETED = 1;
	private static final int UPDATE_DOWNLOADING = 2;
	private static final int UPDATE_DOWNLOAD_ERROR = 3;
	private static final int UPDATE_DOWNLOAD_COMPLETED = 4;
	private static final int UPDATE_DOWNLOAD_CANCELED = 5;

	// 从服务器上下载apk存放文件夹

	private String savefolder = Environment.getExternalStorageDirectory().getPath();;

	// private String savefolder = "/sdcard/";
	// public static final String SAVE_FOLDER =Storage. // "/mnt/innerDisk";
	public UpdateManager(Context context,String url, UpdateCallback updateCallback) {
		ctx = context;
		UPDATE_DOWNURL=url;
		callback = updateCallback;
		// savefolder = context.getFilesDir();
		canceled = false;
		//getCurVersion();
	}



	public String getNewVersionName() {
		return newVersion;
	}

	public String getUpdateInfo() {
		return updateInfo;
	}

//	private void getCurVersion() {
//		try {
//			PackageInfo pInfo = ctx.getPackageManager().getPackageInfo(
//					ctx.getPackageName(), 0);
//			curVersion =pInfo.versionName;
//			curVersionCode = pInfo.versionCode;
//			System.out.println("=================获取的版本号==="+curVersionCode+"====="+curVersion);
//		} catch (NameNotFoundException e) {
//			Log.e("update", e.getMessage());
//			curVersion =" 1.100000";
//			curVersionCode = 111000;
//		}

	//}

	public void checkUpdate() {
		hasNewVersion = true;
		new Thread() {
			// ***************************************************************
			/**
			 * @by wainiwann add
			 *
			 */
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
					}
				 catch (Exception e) {
//					Log.e("update", e.getMessage());
				}
				updateHandler.sendEmptyMessage(UPDATE_CHECKCOMPLETED);
			};
			// ***************************************************************
		}.start();

	}

	public void update() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(
				Uri.fromFile(new File(savefolder, "app-release.apk")),
				"application/vnd.android.package-archive");
		ctx.startActivity(intent);




		//+++++++++++++++++++

	}



	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	public void downloadPackage() {
		Toast.makeText(ctx,"查看这个接口+++"+savefolder,Toast.LENGTH_LONG).show();

		new Thread() {
			@Override
			public void run() {
				try {
					URL url = new URL(UPDATE_DOWNURL);

					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.connect();
					int length = conn.getContentLength();
					InputStream is = conn.getInputStream();
					System.out.println(UPDATE_SAVENAME +".apk");

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

						updateHandler.sendMessage(updateHandler
								.obtainMessage(UPDATE_DOWNLOADING));
						if (numread <= 0) {

							updateHandler
									.sendEmptyMessage(UPDATE_DOWNLOAD_COMPLETED);
							break;
						}
						fos.write(buf, 0, numread);
					} while (!canceled);
					if (canceled) {
						updateHandler
								.sendEmptyMessage(UPDATE_DOWNLOAD_CANCELED);
					}
					fos.close();
					is.close();
				} catch (MalformedURLException e) {
					e.printStackTrace();

					updateHandler.sendMessage(updateHandler.obtainMessage(
							UPDATE_DOWNLOAD_ERROR, e.getMessage()));
				} catch (IOException e) {
					e.printStackTrace();

					updateHandler.sendMessage(updateHandler.obtainMessage(
							UPDATE_DOWNLOAD_ERROR, e.getMessage()));
				}

			}
		}.start();
	}

	public void cancelDownload() {
		canceled = true;
	}

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
		public void checkUpdateCompleted(Boolean hasUpdate,
                                         CharSequence updateInfo);

		public void downloadProgressChanged(int progress);

		public void downloadCanceled();

		public void downloadCompleted(Boolean sucess, CharSequence errorMsg);
	}

}
