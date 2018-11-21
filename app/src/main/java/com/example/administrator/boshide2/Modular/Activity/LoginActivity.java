package com.example.administrator.boshide2.Modular.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.fragment.AbLoadDialogFragment;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.example.administrator.boshide2.Conts;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Adapter.AbstractSpinerAdapter;
import com.example.administrator.boshide2.Modular.Adapter.CustemSpinerAdapter;
import com.example.administrator.boshide2.Modular.Entity.BSD_dlUser;
import com.example.administrator.boshide2.Modular.Entity.BSD_dlgongsi;
import com.example.administrator.boshide2.Modular.Entity.CustemObject;
import com.example.administrator.boshide2.Modular.View.SpinerPopWindow;
import com.example.administrator.boshide2.Modular.View.diaog.QueRen;
import com.example.administrator.boshide2.Modular.View.diaog.TooPromptdiaog;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.DensityUtil;
import com.example.administrator.boshide2.Tools.PermissionUtils;
import com.example.administrator.boshide2.Tools.PermissionsManager;
import com.example.administrator.boshide2.Tools.QuanQuan.WeiboDialogUtils;
import com.example.administrator.boshide2.Tools.Show;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @登陆页
 * @陈佳宁
 * @2017-4-11
 */


public class LoginActivity extends Activity {
    //弹出框
    private List<CustemObject> nameList = new ArrayList<CustemObject>();
    private AbstractSpinerAdapter mAdapter;
    private SpinerPopWindow mSpinerPopWindow;
    private List<CustemObject> nameList1 = new ArrayList<CustemObject>();
    private AbstractSpinerAdapter mAdapter1;
    private SpinerPopWindow mSpinerPopWindow1;
    TextView bst_text_gs, bsd_tv_user;
    RelativeLayout bsd_rl_bm, bsd_rl_user;
    //公司数据集合
    List<BSD_dlgongsi> listitem = new ArrayList<BSD_dlgongsi>();
    //公司下用户的集合
    List<BSD_dlUser> listuser = new ArrayList<BSD_dlUser>();
    String GongSiNo;
    //博士德公司id
    String bsd_gs_id;
    //博士德公司名字
    String bsd_gs_name;
    //博士德操作员id
    String bsd_user_id;
    //博士德操作员名字
    String bsd_user_name;
    //博士德密码
    String bsd_password;
    //输入密码
    EditText bsd_et_password;
    //记住密码
    ImageView bsd_im_jizhumima;
    //记住切换状态
    String yes_no = "0";
    //转圈
    private AbLoadDialogFragment fragment;
    private Dialog mWeiboDialog;
    String ip;//请求ip

    TextView bsd_log_wangzhezhilu;
    WzzlDialog wzzlDialog;

    String quanxian;
    String[] QXS;

    boolean hasLoginQX = false;
    QueRen queRen;
    List<Map<String, Integer>> list = new ArrayList<>();
    String[] st = new String[8];
    URLS url;
    TextView bsd_sbid;

    @Override
    protected void onResume() {
        super.onResume();
        bsd_et_password.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PermissionUtils.camera(LoginActivity.this, new PermissionUtils.OnPermissionResult() {
            @Override
            public void onGranted() {

            }
        });
//
        PermissionUtils.quanxian(LoginActivity.this, new PermissionUtils.OnPermissionResult() {
            @Override
            public void onGranted() {
            }
        });
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //透明状态栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        DensityUtil.setCustomDensity(this, getApplication());
        setContentView(R.layout.loginactivity_main);
        init();
        Conts.SBID = getDiviceUuid();
        bsd_sbid.setText("设备码:" + Conts.SBID);
        bsd_log_wangzhezhilu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //弹出王者之路
                wzzlDialog = new WzzlDialog(LoginActivity.this);
                wzzlDialog.show();
            }
        });
        url = new URLS();
        url.BSD = MyApplication.shared.getString("ip", "");
        Log.i("cjn", "url==" + url.BSD);
        if (TextUtils.isEmpty(url.BSD)) {
            queRen = new QueRen(this, "请输入IP地址");
            queRen.show();
            queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                @Override
                public void onYesClick() {
                    wzzlDialog = new WzzlDialog(LoginActivity.this);
                    wzzlDialog.show();
                    queRen.dismiss();
                }
            });
        }
        // 读取是否记住登录名
        yes_no = MyApplication.shared.getString("yes_no", "0");
        if (yes_no.equals("1")) {
            bsd_im_jizhumima.setImageResource(R.drawable.bsd_ok);
            bsd_gs_id = MyApplication.shared.getString("bsd_gs_id", "");
            bsd_gs_name = MyApplication.shared.getString("bsd_gs_name", "");
            GongSiNo = MyApplication.shared.getString("bsd_gs_no", "");
            bst_text_gs.setText(bsd_gs_name);
            bsd_user_name = MyApplication.shared.getString("bsd_user_name", "");
            bsd_tv_user.setText(bsd_user_name);
            bsd_et_password.setText("");
        } else {
            bsd_im_jizhumima.setImageResource(R.drawable.bsd_no);
        }

//        bsd_jizhumima();

        /**
         * 判断选没选中王者之路，1代表选中，0代表没选中
         */
//        if (MyApplication.shared.getInt("xz", 0) > 0) {
//            wzngzhezhilu();
//        } else {
//            url.BSD = MyApplication.shared.getString("ip", "");
//            Show.showTime(LoginActivity.this, url.BSD);
//        }
    }

    /**
     * 初始化UI布局
     */
    public void init() {
        bsd_log_wangzhezhilu = (TextView) findViewById(R.id.bsd_log_wzzl);
        //公司
        bst_text_gs = (TextView) findViewById(R.id.bst_text_gs);
        bsd_rl_bm = (RelativeLayout) findViewById(R.id.bsd_rl_bm);
        //用户
        bsd_tv_user = (TextView) findViewById(R.id.bsd_tv_user);
        bsd_rl_user = (RelativeLayout) findViewById(R.id.bsd_rl_user);
        //密码
        bsd_et_password = (EditText) findViewById(R.id.bsd_et_password);
        //记住密码
        bsd_im_jizhumima = (ImageView) findViewById(R.id.bsd_im_jizhumima);
        bsd_sbid = (TextView) findViewById(R.id.bsd_sbid);
    }

    // 获取设备号
    private String getDiviceUuid() {
        TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        String tmDevice, tmSerial, tmPhone, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        return deviceUuid.toString();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

    /**
     * 请求ip
     */
    public void wzngzhezhilu() {
        AbRequestParams params = new AbRequestParams();
        params.put("UserName", MyApplication.shared.getString("username", ""));
        params.put("UserPwd", MyApplication.shared.getString("password", ""));
        Request.Post("http://bsdip.bsd102.com/ReturnIpM.aspx?UserPwd=" + MyApplication.shared.getString("password", "") + "&UserName=" + MyApplication.shared.getString("username", "") + "", null, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                ip = "http://" + s + ":" + MyApplication.shared.getString("dk", "") + "/";

                MyApplication.editor.putString("ip", ip);
                Log.i("dddd", "ip是多少--" + ip);
                MyApplication.editor.commit();
                Show.showTime(LoginActivity.this, url.BSD);
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                Log.i("cjn", "这是请求失败");
                Log.e("lll", "s:" + s.toString() + "throwable:" + throwable.toString());
                Show.showTime(LoginActivity.this, "网络连接超时");
            }
        });
    }

    /**
     * 记住密码的判断查询
     */
    public void bsd_jizhumima() {
        Log.i("cjn", "记住密码的判断查询");
        if (MyApplication.shared.getString("yes_no", "").equals("1")) {
            bsd_gs_id = MyApplication.shared.getString("bsd_gs_id", "");
            bst_text_gs.setText(MyApplication.shared.getString("bsd_gs_name", ""));
            bsd_tv_user.setText(MyApplication.shared.getString("bsd_user_name", ""));
            bsd_et_password.setText("");
            bsd_im_jizhumima.setImageResource(R.mipmap.bsd_ok);
            bsd_user_name = MyApplication.shared.getString("bsd_user_name", "");
            bsd_password = MyApplication.shared.getString("bsd_password", "");
//            sbd_user(MyApplication.shared.getString("bsd_gs_id", ""));

            Log.i("cjn", "记住密码判断");

        } else if (MyApplication.shared.getString("yes_no", "").equals("0")) {
            Log.i("cjn", "不记住");

            sbd_gongsi();
        }
    }

    /**
     * 选择公司
     * @param view
     */
    public void clk_bumen(View view) {
        url.BSD = MyApplication.shared.getString("ip", "");
        Log.i("cjn", "url==" + url.BSD);
        if (TextUtils.isEmpty(url.BSD)) {
            queRen = new QueRen(this, "请输入IP地址");
            queRen.show();
            queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                @Override
                public void onYesClick() {
                    wzzlDialog = new WzzlDialog(LoginActivity.this);
                    wzzlDialog.show();
                    queRen.dismiss();
                }
            });
        } else {
            sbd_gongsi();
        }
    }

    /**
     * 选择公司下拉
     */
    public void bumen() {
        Log.i("cjn", "选择公司下拉");
        nameList.clear();
        for (int i = 0; i < listitem.size(); i++) {
            CustemObject object = new CustemObject();
            object.data = listitem.get(i).getGongSiMc();
            nameList.add(object);
        }
        mAdapter = new CustemSpinerAdapter(this);
        mAdapter.refreshData(nameList, 0);
        mSpinerPopWindow = new SpinerPopWindow(this);
        mSpinerPopWindow.setAdatper(mAdapter, 310);
        mSpinerPopWindow.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
            @Override
            public void onItemClick(int pos) {
                bsd_gs_name = nameList.get(pos).toString();
                GongSiNo = listitem.get(pos).getGongSiNo().toString();
                bsd_gs_id = listitem.get(pos).getGongSiNo();
                if (!bst_text_gs.getText().toString().equals(bsd_gs_name)) {
                    bst_text_gs.setText(bsd_gs_name);
                    bsd_tv_user.setText("");
                    bsd_et_password.setText("");
                }
            }
        });
        // 显示下拉选择的界面
        mSpinerPopWindow.setWidth(bsd_rl_bm.getWidth());
        mSpinerPopWindow.showAsDropDown(bsd_rl_bm);
    }

    /**
     * 登陆
     * @param view
     */
    public void bsd_login(View view) {
        if (bst_text_gs.getText().toString().equals("")) {
            Show.showTime(LoginActivity.this, "请先选择公司");
        } else if (bsd_tv_user.getText().toString().equals("")) {
            Show.showTime(LoginActivity.this, "请先选择用户");
        } else {
            //登陆
            bsd_landing();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWeiboDialog = null;
    }

    /**
     * 获取首页公司
     */
    public void sbd_gongsi() {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(LoginActivity.this, "加载中...");
        Log.i("cjn", "获取公司转圈");
        listitem.clear();
        Log.i("cjn", "查看公司网址" + MyApplication.shared.getString("ip", ""));
        AbRequestParams params = new AbRequestParams();
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_gongsi, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int statusCode, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("status").toString().equals("1")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject item = jsonarray.getJSONObject(i);
                            BSD_dlgongsi bsd_dlgongsi = new BSD_dlgongsi();
                            bsd_dlgongsi.setGongSiMc(item.optString("GongSiMc"));
                            bsd_dlgongsi.setGongSiNo(item.optString("GongSiNo"));
                            listitem.add(bsd_dlgongsi);
                        }
                    } else {
                        WeiboDialogUtils.closeDialog(mWeiboDialog);
                        Show.showTime(LoginActivity.this, "查询失败");
                    }
                    bumen();
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
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
                WeiboDialogUtils.closeDialog(mWeiboDialog);
                Show.showTime(LoginActivity.this, "网络连接超时");
            }
        });
    }

    /**
     * 获取用户选择
     */
    public void sbd_user() {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(LoginActivity.this, "加载中...");
        listuser.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("GongSiNo", GongSiNo);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_user, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                Log.i("cjn", "获取用户成功" + data);
                try {
                    JSONObject json = new JSONObject(data);
                    if (json.get("status").toString().equals("1")) {
                        JSONArray jsonarray = json.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject item = jsonarray.getJSONObject(i);
                            BSD_dlUser bsd_user = new BSD_dlUser();
                            bsd_user.setId(item.optString("id"));
                            bsd_user.setName(item.optString("name"));
                            listuser.add(bsd_user);
                        }
                    } else {
                        WeiboDialogUtils.closeDialog(mWeiboDialog);
                        Show.showTime(LoginActivity.this, "查询用户失败");
                    }
                    //选择用户
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                    bsd_dl_user();
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
                Log.i("cjn", "获取用户失败" + s);
                WeiboDialogUtils.closeDialog(mWeiboDialog);
                Show.showTime(LoginActivity.this, "获取用户选择网络请求超时");

            }
        });


    }

    /**
     * 点击选择用户
     *
     * @param view
     */
    public void bsd_dluser(View view) {
        if (TextUtils.isEmpty(bst_text_gs.getText().toString())) {
            Show.showTime(LoginActivity.this, "请先选择公司");
        } else {
            sbd_user();
        }
    }

    /**
     * 选择用户判断
     */
    public void bsd_dl_user() {
        Log.i("cjn", "选择用户判断");
        nameList1.clear();
        for (int i = 0; i < listuser.size(); i++) {
            CustemObject object = new CustemObject();
            object.data = listuser.get(i).getName();
            nameList1.add(object);
        }
        mAdapter1 = new CustemSpinerAdapter(this);
        mAdapter1.refreshData(nameList1, 0);

        mSpinerPopWindow1 = new SpinerPopWindow(this);
        mSpinerPopWindow1.setAdatper(mAdapter1, 300);
        mSpinerPopWindow1.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
            @Override
            public void onItemClick(int pos) {
                bsd_user_name = nameList1.get(pos).toString();
                bsd_user_id = listuser.get(pos).getId();
                bsd_tv_user.setText(bsd_user_name);
            }
        });
        mSpinerPopWindow1.setWidth(bsd_rl_user.getWidth());
        mSpinerPopWindow1.showAsDropDown(bsd_rl_user);
    }

    /**
     * 登陆方法
     */
    public void bsd_landing() {
        Conts.QX_baoyangxinxii = 0;
        Conts.QX_baoyangxinxi_JQ = 0;
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(LoginActivity.this, "加载中...");
        bsd_password = bsd_et_password.getText().toString();
        AbRequestParams params = new AbRequestParams();
        params.put("name", bsd_user_name);
        params.put("psd", bsd_password);
        params.put("sid", Conts.SBID);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_login, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                try {
                    JSONObject json = new JSONObject(data);
                    if (json.get("message").toString().equals("查询成功")) {
                        JSONObject item = json.getJSONObject("data");
                        quanxian = item.getString("danw_dz");
                        //2086--车辆信息维护
                        //2401--美容快修
                        //2402--美容、维修查询
                        //2422--预约单
                        //2427--快速报价
                        //2428--快速报价单查询
                        //2429--查看保养数据
                        //2430--使用平板接待APP大维修
                        //2431--使用平板接待APP快修
                        //2433--查看保养数据价格信息
                        //2503--会员信息维护
                        //2801--接待登记
                        //2803--维修调度
                        //2826--预约维修停工业务查询
                        if (quanxian.contains("2430")) {   //先判断有没有大修登录的权限，如果有则找下面的权限
                            hasLoginQX = true;
                            if (quanxian.contains("2401")) {
                                st[0] = "1";
                            }
                            if (quanxian.contains("2427")) {
                                st[1] = "1";
                            }
                            if (quanxian.contains("2428")) {
                            }
                            if (quanxian.contains("2422")) {
                                st[2] = "1";
                                Conts.QX_weixiuyuyue = 0;
                            }
                            if (quanxian.contains("2826")) {
                                Conts.QX_weixiuyuyue_lishi = 1;
                            }
                            if (quanxian.contains("2801")) {
                                st[3] = "1";
                                Conts.QX_weixiujiedan = 1;
                            }
                            if (quanxian.contains("2503")) {
                                st[5] = "1";
                                Conts.BSD_zhongxiaoweixiu = 1;
                                Conts.QX_huiyuanguanli = 1;
                            }
                            if (quanxian.contains("2402")) {
                                st[6] = "1";
                                Conts.QX_lishiweixiu = 1;
                            }
                            if (quanxian.contains("2086")) {
                                st[7] = "1";
                                Conts.QX_cheliangxinxi = 1;
                            }
                            if (quanxian.contains("2803")) {
                                st[4] = "1";
                                Conts.QX_zaichangdiaodu = 1;
                            }
                            if (quanxian.contains("2429")) {
                                Conts.QX_baoyangxinxii = 1;
                            }
                            if (quanxian.contains("2433")) {
                                Conts.QX_baoyangxinxi_JQ = 1;
                            }
                        } else if (quanxian.contains("2431")) {  // 如果没有大修登录的权限，看是否有快修登录的权限
                            hasLoginQX = true;
                            if (quanxian.contains("2401")) {
                                st[0] = "1";
                            }
                            if (quanxian.contains("2503")) {
                                st[5] = "1";
                                Conts.BSD_zhongxiaoweixiu = 1;
                                Conts.QX_huiyuanguanli = 1;
                            }
                            if (quanxian.contains("2086")) {
                                st[7] = "1";
                                Conts.QX_cheliangxinxi = 1;
                            }
                            if (quanxian.contains("2429")) {
                                Conts.QX_baoyangxinxii = 1;
                            }
                        } else {
                            hasLoginQX = false;
                        }
                        if (hasLoginQX) {
                            MyApplication.editor.putString("name", item.getString("name"));
                            MyApplication.editor.putString("id", item.getString("id"));
                            MyApplication.editor.putString("psd", bsd_password);
                            MyApplication.editor.putString("userid", item.getString("id"));
                            MyApplication.editor.putString("GongSiNo", item.getString("GongSiNo"));
                            MyApplication.editor.putString("GongSiMc", item.getString("GongSiMc"));
                            MyApplication.editor.putString("danw_dh", item.getString("danw_dh"));
                            MyApplication.editor.putString("danw_shouji", item.getString("danw_shouji"));
                            MyApplication.editor.putString("danw_dz", item.getString("danw_dz"));
                            MyApplication.editor.putString("gongsi_gndm", item.getString("gongsi_gndm"));
                            MyApplication.editor.putString("cangku_gndm", item.getString("cangku_gndm"));
                            MyApplication.editor.putString("cangkudo_gndm", item.getString("cangkudo_gndm"));
                            // 真正的登录成功之后，才根据是否记住用户名，来保存数据
                            if (yes_no.equals("1")) {
                                MyApplication.editor.putString("bsd_gs_id", bsd_gs_id);
                                MyApplication.editor.putString("bsd_gs_name", bst_text_gs.getText().toString().trim());
                                MyApplication.editor.putString("bsd_gs_no", item.getString("GongSiNo"));
                                MyApplication.editor.putString("bsd_user_id", bsd_user_id);
                                MyApplication.editor.putString("bsd_user_name", bsd_tv_user.getText().toString().trim());
                                MyApplication.editor.putString("yes_no", "1");
                            }
                            MyApplication.editor.putString("yes_no", yes_no);
                            MyApplication.editor.commit();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            Bundle mBundle = new Bundle();
                            mBundle.putStringArray("QD", st);
                            intent.putExtras(mBundle);
                            startActivity(intent);
                        } else {
                            Show.showTime(LoginActivity.this, "没有登录权限");
                        }
                    } else if (json.get("message").toString().equals("查询失败")) {

                        Toast.makeText(LoginActivity.this, json.getString("data"), Toast.LENGTH_SHORT).show();
                    } else {
                        Show.showTime(LoginActivity.this, "网络连接超时");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                Show.showTime(LoginActivity.this, "服务器连接失败，稍后重试");
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });
    }

    /**
     * 切换是否记住用户的图片
     * @param view
     */
    public void bsd_qiehuan(View view) {
        if (yes_no.equals("0")) {
            yes_no = "1";
            bsd_im_jizhumima.setImageResource(R.drawable.bsd_ok);
        } else {
            yes_no = "0";
            bsd_im_jizhumima.setImageResource(R.drawable.bsd_no);
        }

    }

    /**
     * 返回
     */
    public void showTips() {
        TooPromptdiaog promptdiaog = new TooPromptdiaog(this, "是否退出程序");
        promptdiaog.setToopromtOnClickListener(new TooPromptdiaog.ToopromtOnClickListener() {
            @Override
            public void onYesClick() {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        promptdiaog.show();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            showTips();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}