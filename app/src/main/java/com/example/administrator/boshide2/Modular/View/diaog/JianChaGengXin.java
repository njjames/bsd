package com.example.administrator.boshide2.Modular.View.diaog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.http.AbStringHttpResponseListener;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.AppUtils;
import com.example.administrator.boshide2.Tools.QuanQuan.WeiboDialogUtils;
import com.example.administrator.boshide2.UpdataManager.DialogHelper;
import com.example.administrator.boshide2.UpdataManager.UpdateManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class JianChaGengXin extends Dialog implements View.OnClickListener {
    private View view;
    TextView bt_toopromptdiaog_quedding;
    TextView bt_toopromptdiaog_quxiao;
    TextView tv_currentVersion;
    TextView tv_newVersion;
    private ToopromtOnClickListener toopromtOnClickListener;
    private UpdateManager updateMan;
    private ProgressDialog updateProgressDialog;
    String version;//最新版本号
    String url;//下载地址
    String currentVersion;//当前版本号
    Context context;
    URLS urls;
    String json;
    private Dialog mWeiboDialog;

    public JianChaGengXin(Context context) {
        super(context, R.style.mydialog);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        view = getLayoutInflater().inflate(R.layout.jianchagengxin, null, false);
        setContentView(view);
        setCanceledOnTouchOutside(false);
        this.context = context;
        urls = new URLS();
        bt_toopromptdiaog_quedding = (TextView) view.findViewById(R.id.tv_confirm);
        bt_toopromptdiaog_quedding.setOnClickListener(this);
        bt_toopromptdiaog_quxiao = (TextView) view.findViewById(R.id.tv_cancel);
        bt_toopromptdiaog_quxiao.setOnClickListener(this);
        tv_currentVersion = (TextView) view.findViewById(R.id.tv_currentVersion);
        tv_newVersion = (TextView) view.findViewById(R.id.tv_newVersion);
        currentVersion = AppUtils.getVersionName(getContext());
        if (currentVersion != null) {
            tv_currentVersion.setText("当前版本号:" + currentVersion);
        } else {
            Toast.makeText(getContext(), "获取当前版本信息失败", Toast.LENGTH_SHORT).show();
        }
        new Thread() {
            @Override
            public void run() {
                json= sendGet();
                handler.sendMessage(handler.obtainMessage(10));
            }
        }.start();
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = getContext().getResources().getDimensionPixelSize(R.dimen.qb_px_300);
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(params);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            if (msg.what == 10) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    JSONObject jsonarray = jsonObject.getJSONObject("data");
                    version = jsonarray.getString("version");
                    tv_newVersion.setText("最新版本号:" + version);
                    url = jsonarray.getString("url");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }

    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_confirm:
                if (currentVersion.equals(version)) {
                    Toast.makeText(getContext(), "当前已是最新版本", Toast.LENGTH_SHORT).show();
                } else {
                    mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getContext(), "下载中...");
                    updateMan = new UpdateManager(context, url, appUpdateCb);
                    updateMan.checkUpdate();
                }
                this.dismiss();
                break;
            case R.id.tv_cancel:
                this.dismiss();
                break;
        }
    }

    public void setToopromtOnClickListener(ToopromtOnClickListener toopromtOnClickListener) {
        this.toopromtOnClickListener = toopromtOnClickListener;
    }


    public interface ToopromtOnClickListener {
        public void onYesClick();
    }

    /**
     * 版本更新
     */
    public void banben() {
        Request.Post("http://update.bossed.com.cn:1999/yanjiuyuan/bsdDown/Files/wd2185/getAppVersion.html", null, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONObject jsonarray = jsonObject.getJSONObject("data");
                    version = jsonarray.getString("version");
                    tv_newVersion.setText("最新版本号:" + version);
                    url = jsonarray.getString("url");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                Log.i("cjn", "查看这个s" + s);
            }
        });
    }

    UpdateManager.UpdateCallback appUpdateCb = new UpdateManager.UpdateCallback() {
        @Override
        public void checkUpdateCompleted(Boolean hasUpdate, CharSequence updateInfo) {
            if (hasUpdate) {
                WeiboDialogUtils.closeDialog(mWeiboDialog);
                updateProgressDialog = new ProgressDialog(context);
                updateProgressDialog.setMessage("正在更新");
                updateProgressDialog.setIndeterminate(false);
                updateProgressDialog.setCanceledOnTouchOutside(false);
                updateProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                updateProgressDialog.setMax(100);
                updateProgressDialog.setProgress(0);
                updateProgressDialog.show();

                updateMan.downloadPackage();


            }
        }


        @Override
        public void downloadProgressChanged(int progress) {
            if (updateProgressDialog != null && updateProgressDialog.isShowing()) {
                updateProgressDialog.setProgress(progress);
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        }

        @Override
        public void downloadCanceled() {

        }

        @Override
        public void downloadCompleted(Boolean sucess, CharSequence errorMsg) {
            if (updateProgressDialog != null && updateProgressDialog.isShowing()) {
                updateProgressDialog.dismiss();
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
            if (sucess) {
                WeiboDialogUtils.closeDialog(mWeiboDialog);
                updateMan.update();

            } else {
                WeiboDialogUtils.closeDialog(mWeiboDialog);
                DialogHelper.Confirm(context, R.string.dialog_error_title, R.string.dialog_downfailed_msg,
                        R.string.dialog_downfailed_btnnext, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                updateMan.downloadPackage();

                            }
                        }, R.string.dialog_downfailed_btnnext, null);
            }
        }
    };


////// 自动更新回调函数
//
//    UpdateManager.UpdateCallback appUpdateCb = new UpdateManager.UpdateCallback() {
//
//		@Override
//		public void checkUpdateCompleted(Boolean hasUpdate, CharSequence updateInfo) {
//
//		}
//
//		public void downloadProgressChanged(int progress) {
//			if (updateProgressDialog != null && updateProgressDialog.isShowing()) {
//				updateProgressDialog.setProgress(progress);
//			}
//
//		}
//
//		@Override
//		public void downloadCanceled() {
//			if (updateProgressDialog != null && updateProgressDialog.isShowing()) {
//				updateProgressDialog.dismiss();
//			}
//
//		}
//
//		@Override
//		public void downloadCompleted(Boolean sucess, CharSequence errorMsg) {
//
//		}
//	};
//
//		/**
//		 * 下载
//		 */
//
//		public void downloadCompleted(Boolean sucess, CharSequence errorMsg) {
//			if (updateProgressDialog != null && updateProgressDialog.isShowing()) {
//				updateProgressDialog.dismiss();
//			}
//			if (sucess) {
//				updateMan.update();
//			} else {
//				updateMan.downloadPackage();
//			}
//		}
//
//		public void downloadCanceled() {
//			// TODO Auto-generated method stub
//
//		}
//
//
//		/**
////		 * 检查更新
////		 */
////        public void checkUpdateCompleted(Boolean hasUpdate, final CharSequence updateInfo) {
////            if (hasUpdate) {
////                DialogHelper.Confirm(getContext(), "更新",
////                        "发现新版本，版本号：" + updateInfo
////                                + "是否立即更新",
////                        "更新", new DialogInterface.OnClickListener() {
////
////                            public void onClick(DialogInterface dialog, int which) {
////
////                                updateProgressDialog = new ProgressDialog(getContext());
////                                updateProgressDialog.setMessage("正在下载更新。。。");
////                                updateProgressDialog.setIndeterminate(false);
////                                updateProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
////                                updateProgressDialog.setMax(100);
////                                updateProgressDialog.setProgress(0);
////                                updateProgressDialog.show();
////
////                                updateMan.downloadPackage();
////                            }
////                        }, "下次再说", null);
////            }
////
////        }
////	};

    /**
     * 向指定URL发送GET方法的请求
     * <p>
     * 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     *
     * @return URL 所代表远程资源的响应结果
     */

    public static String sendGet() {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = "http://update.bossed.com.cn:1999/yanjiuyuan/bsdDown/Files/wd2185/getAppVersion.html";
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            /*
             * Map<String, List<String>> map = connection.getHeaderFields(); //
             * 遍历所有的响应头字段 for (String key : map.keySet()) {
             * System.out.println(key + "--->" + map.get(key)); }
             */
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

}

