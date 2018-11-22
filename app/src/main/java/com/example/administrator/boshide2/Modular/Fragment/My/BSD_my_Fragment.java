package com.example.administrator.boshide2.Modular.Fragment.My;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.alibaba.fastjson.JSON;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Activity.MainActivity;
import com.example.administrator.boshide2.Modular.Adapter.AbstractSpinerAdapter;
import com.example.administrator.boshide2.Modular.Adapter.CustemSpinerAdapter;
import com.example.administrator.boshide2.Modular.Entity.CustemObject;
import com.example.administrator.boshide2.Modular.Fragment.BaseFragment;
import com.example.administrator.boshide2.Modular.View.SpinerPopWindow;
import com.example.administrator.boshide2.Modular.View.diaog.ChangepswDialog;
import com.example.administrator.boshide2.Modular.View.diaog.CustomDialog;
import com.example.administrator.boshide2.Modular.View.diaog.GengXinWanCheng;
import com.example.administrator.boshide2.Modular.View.diaog.JianChaGengXin;
import com.example.administrator.boshide2.Modular.View.diaog.QueRen;
import com.example.administrator.boshide2.Modular.View.diaog.Queding_Quxiao;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.AppUtils;
import com.example.administrator.boshide2.Tools.QuanQuan.WeiboDialogUtils;
import com.example.administrator.boshide2.Tools.Show;
import com.example.administrator.boshide2.UpdataManager.DialogHelper;
import com.example.administrator.boshide2.UpdataManager.UpdateManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @我的管理碎片页 Created by Administrator on 2017-4-13.
 */

public class BSD_my_Fragment extends BaseFragment {
    private LinearLayout bsd_xgmm;
    private LinearLayout bsd_lxwm;
    private LinearLayout bsd_jcgx;
    private LinearLayout bsd_jiage;
    private List<Map<String, String>> list = new ArrayList<>();
    private List<CustemObject> nameList2 = new ArrayList<CustemObject>();
    private AbstractSpinerAdapter mAdapter2;
    private SpinerPopWindow mSpinerPopWindow2;
    private TextView bsd_my_tv_jiaqian;
    private CheckBox bsd_my_shifoudayin;
    private URLS url;
    private Dialog mWeiboDialog;
    //保存车牌号前缀
    private String cpPrefix;
    private EditText bsd_my_et_qianzhui;
    private TextView bsd_my_rl_save;
    private TextView title;
    private TextView footerText;
    private EditText oldPsw;
    private EditText newPsw;
    private EditText rePsw;
    private ChangepswDialog changepswDialog;
    private Queding_Quxiao quedingQuxiao;
    private UpdateManager updateMan;
    private ProgressDialog updateProgressDialog;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            WeiboDialogUtils.closeDialog(mWeiboDialog);
            int currentVersionCode = AppUtils.getVersionCode(getContext());
            String versionInfo = (String) msg.obj;
            try {
                JSONObject jsonObject = new JSONObject(versionInfo);
                int newVersionCode = jsonObject.getInt("versioncode");
                if (newVersionCode == currentVersionCode) {
                    Toast.makeText(getContext(), "当前已经是最新版本！", Toast.LENGTH_SHORT).show();
                } else {
                    String newVersionName = jsonObject.getString("versionname");
                    final String url = jsonObject.getString("url");
                    queRen = new QueRen(getContext(), "当前最新版本为：" + newVersionName + "\n请务必更新！");
                    queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                        @Override
                        public void onYesClick() {
                            queRen.dismiss();
                            updateMan = new UpdateManager(getContext(), url, appUpdateCb);
                            updateProgressDialog = new ProgressDialog(getContext());
                            updateProgressDialog.setMessage("正在更新");
                            updateProgressDialog.setIndeterminate(false);
                            updateProgressDialog.setCanceledOnTouchOutside(false);
                            updateProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                            updateProgressDialog.setMax(100);
                            updateProgressDialog.setProgress(0);
                            updateProgressDialog.show();
                            updateMan.downloadPackage();
                        }
                    });
                    queRen.show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    };

    UpdateManager.UpdateCallback appUpdateCb = new UpdateManager.UpdateCallback() {
        @Override
        public void checkUpdateCompleted(Boolean hasUpdate, CharSequence updateInfo) {

        }

        @Override
        public void downloadProgressChanged(int progress) {
            if (updateProgressDialog != null && updateProgressDialog.isShowing()) {
                updateProgressDialog.setProgress(progress);
            }
        }

        @Override
        public void downloadCanceled() {

        }

        @Override
        public void downloadCompleted(Boolean sucess, CharSequence errorMsg) {
            if (updateProgressDialog != null && updateProgressDialog.isShowing()) {
                updateProgressDialog.dismiss();
            }
            if (sucess) {
                updateMan.update();
            } else {
                DialogHelper.Confirm(getContext(), R.string.dialog_error_title, R.string.dialog_downfailed_msg,
                        R.string.dialog_downfailed_btnnext, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                updateMan.downloadPackage();
                            }
                        }, R.string.dialog_downfailed_btnnext, null);
            }
        }

    };
    private QueRen queRen;

    @Override
    protected int getLayoutId() {
        return R.layout.bsd_my_fragment;
    }

    @Override
    public void initView() {
        //车牌号前缀
        bsd_my_et_qianzhui = (EditText) view.findViewById(R.id.bsd_my_et_qianzhui);
        bsd_my_et_qianzhui.setText(MyApplication.shared.getString("cpPrefix", ""));
        bsd_my_rl_save = (TextView) view.findViewById(R.id.tv_save);
        bsd_my_rl_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cpPrefix = bsd_my_et_qianzhui.getText().toString();
                MyApplication.editor.putString("cpPrefix", cpPrefix);
                MyApplication.editor.commit();
                Toast.makeText(getActivity(), "保存成功", Toast.LENGTH_SHORT).show();
            }
        });

        bsd_my_shifoudayin = (CheckBox) view.findViewById(R.id.bsd_my_shifoudayin);
        if (MyApplication.shared.getString("shifoudayin", "").equals("1")) {
            bsd_my_shifoudayin.setChecked(true);
        } else {
            bsd_my_shifoudayin.setChecked(false);
        }

        bsd_my_shifoudayin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    //选中的时候
                    Log.i("cjn", "选中状态");
                    MyApplication.editor.putString("shifoudayin", "1");
                    MyApplication.editor.commit();
                } else {
                    //没选中的时候
                    Log.i("cjn", "未选中状态");
                    MyApplication.editor.putString("shifoudayin", "0");
                    MyApplication.editor.commit();
                }

            }
        });

        bsd_my_tv_jiaqian = (TextView) view.findViewById(R.id.bsd_my_tv_jiaqian);
        bsd_my_tv_jiaqian.setText(MyApplication.shared.getString("jiagedengji", ""));

        //修改密码
        bsd_xgmm = (LinearLayout) view.findViewById(R.id.bsd_xgmm);
        bsd_xgmm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangepswDialog();
            }
        });
        //联系我们
        bsd_lxwm = (LinearLayout) view.findViewById(R.id.bsd_lxwm);
        bsd_lxwm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).uplxwm();
            }
        });
        //检查更新
        bsd_jcgx = (LinearLayout) view.findViewById(R.id.bsd_jcgx);
        bsd_jcgx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNewVersion();
            }
        });
        bsd_jiage = (LinearLayout) view.findViewById(R.id.bsd_my_jiaqian);
        bsd_jiage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGongSi2();
            }
        });
        title = (TextView) view.findViewById(R.id.tv_title);
        footerText = (TextView) view.findViewById(R.id.tv_footertext);
    }

    private void getNewVersion() {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getContext(), "正在获取最新版本");
        new Thread(){
            @Override
            public void run() {
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
                    in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = in.readLine()) != null) {
                        result += line;
                    }
                    Log.i("version", "versioninfo: " + result);
                    Message msg = handler.obtainMessage();
                    msg.obj = result;
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (in != null) {
                            in.close();
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }.start();

    }

    /**
     * 修改密码的对话框
     */
    private void showChangepswDialog() {
        changepswDialog = new ChangepswDialog(getHostActicity());
        changepswDialog.show();
    }

    @Override
    public void initData() {
        url = new URLS();
        title.setText("我的管理");
        footerText.setText("当前版本：" + AppUtils.getVersionName(getContext()));
        jiaqiandata();
    }

    // 请求价格
    public void jiaqiandata() {
        list.clear();
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_JiaQian, null, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                try {
                    JSONObject json = new JSONObject(s);
                    if (json.get("status").toString().equals("1")) {
                        JSONObject item = json.getJSONObject("data");
                        Iterator<String> keys = item.keys();
                        String key = null;
                        String value = null;
                        while (keys.hasNext()) {
                            Map<String, String> map = new HashMap<String, String>();
                            key = keys.next();
                            value = item.getString(key);
                            map.put("name", key);
                            map.put("id", value);
                            list.add(map);
                        }
                    }
                    bumen2();
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

            }
        });
    }

    /**
     * 基本信息车型
     */
    public void bumen2() {
        nameList2.clear();
        for (int i = 0; i < list.size(); i++) {
            CustemObject object = new CustemObject();
            object.data = list.get(i).get("name");
            nameList2.add(object);
        }
        mAdapter2 = new CustemSpinerAdapter(getActivity());
        mAdapter2.refreshData(nameList2, 0);
        mSpinerPopWindow2 = new SpinerPopWindow(getActivity());
        mSpinerPopWindow2.setAdatper(mAdapter2, 310);
        mSpinerPopWindow2.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
            @Override
            public void onItemClick(int pos) {
                String value = nameList2.get(pos).toString();
                if (!bsd_my_tv_jiaqian.getText().toString().equals(value)) {
                    bsd_my_tv_jiaqian.setText(value);
                    MyApplication.editor.putString("jiagedengji", value);
                    jiaqianbaocundata(list.get(pos).get("id"), list.get(pos).get("name"));
                    Show.showTime(getActivity(), "您选择了," + value);
                }
            }
        });
    }


    private void showGongSi2() {
        mSpinerPopWindow2.setWidth(bsd_jiage.getWidth());
        mSpinerPopWindow2.showAsDropDown(bsd_jiage);
    }


    public void jiaqianbaocundata(final String id, final String name) {
        AbRequestParams params = new AbRequestParams();
        params.put("price_id", id);
        params.put("price_name", name);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_JiaQian_BaoCun, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int a, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("status").toString().equals("1")) {
                        Log.i("cjn", "查看手否存档成功" + jsonObject.getString("data"));
                        if (jsonObject.getString("data").toString().equals("保存价格成功")) {
                            MyApplication.editor.putString("BSD_jiaqian_id", id);
                            MyApplication.editor.putString("BSD_jiaqian_name", name);
                            MyApplication.editor.commit();
                        }
                    }
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

            }
        });
    }

}
