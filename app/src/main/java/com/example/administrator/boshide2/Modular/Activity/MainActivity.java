package com.example.administrator.boshide2.Modular.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.example.administrator.boshide2.Conts;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Fragment.CheLiangXinXi.BSD_cheliangxinxi_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.CheLiangXinXi.Entity.BSD_CLXX_ety;
import com.example.administrator.boshide2.Modular.Fragment.CheXiangQIng.BSD_CheXiangQing_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.ChooseCarFragment;
import com.example.administrator.boshide2.Modular.Fragment.HuiYuanGuanLi.BSD_huiyuanguanli_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.BSD_kuaisubaojia_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.LiShiBaoJia.BSD_kuaisubaojia_xiangqing_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.LiShiBaoJia.BSD_lishibaojia_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.LiShiJieDan.BSD_WeiXiuJieDan_xiangqing_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.LiShiJieDan.BSD_lishijiedan_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.LiShiKuaiXiu.BSD_LiShiKuaiXiu_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.LiShiKuaiXiu.BSD_MeiRongKuaiXiuWeiXiuXiangQing_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.LiShiWeiXiu.BSD_lishiweixiu_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.LiShiWeiXiu.Entity.BSD_LSWX_ety;
import com.example.administrator.boshide2.Modular.Fragment.LianXiWoMen.BSD_LianXiWoMen_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.BSD_MeiRongKuaiXiu_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.ManualInputFragment;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.Entity.BSD_Car_Entity;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.Entity.BSD_KeHu_Entity;
import com.example.administrator.boshide2.Modular.Fragment.My.BSD_my_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.BSD_WeiXiuJieDan_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_Entity;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuXiangQing.BSD_WeiXiuXiangQing_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.BSD_WeiXiuYeWuDiaoDu_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.BSD_weixiuyuyue_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.Entity.BSD_WeiXiuYueYue_entiy;
import com.example.administrator.boshide2.Modular.Fragment.YuyueLiSHi.BSD_YuYueLiShi_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.ZaiChangDiaoDU.BSD_zaichangdiaodu_Fragment;
import com.example.administrator.boshide2.Modular.View.diaog.ChangePageDialog;
import com.example.administrator.boshide2.Modular.View.diaog.CustomDialog;
import com.example.administrator.boshide2.Modular.View.diaog.QueRen;
import com.example.administrator.boshide2.Modular.View.diaog.Queding_Quxiao;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.BsdUtil;
import com.example.administrator.boshide2.Tools.DensityUtil;
import com.example.administrator.boshide2.Tools.DownJianPan;
import com.example.administrator.boshide2.Tools.MyTimeTask;
import com.example.administrator.boshide2.Tools.QuanQuan.WeiboDialogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.TimerTask;

/**
 * @主页面
 * @2017-4-13
 */

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private static final int TIMER = 1;
    //图标的数组
    private ImageView[] arr_iv;// 图标的数组
    private RelativeLayout rl_mrkx;
    private RelativeLayout rl_ksbj;
    private RelativeLayout rl_wxyy;
    private RelativeLayout rl_wxjd;
    private RelativeLayout rl_zcdd;
    private RelativeLayout rl_hygl;
    private RelativeLayout rl_lswx;
    private RelativeLayout rl_clxx;
    private RelativeLayout rl_my;

    private RelativeLayout[] arr_rl;//点击布局

    private String[] arr_name_box = {"美容快修", "快速报价", "维修预约", "维修接待", "在厂调度",
            "会员管理", "历史维修", "车辆信息", "我的管理"};
    //碎片
    private Fragment BSD_wxyy_xiangqing;
    //图片数组
    private int[] imgyes = new int[]{R.drawable.bsd_mrkx_1, R.drawable.bsd_kuaisubaojia_1, R.drawable.bsd_weixiuyuyue_1,
            R.drawable.bsd_weixiujiedan_1, R.drawable.bsd_zaichangdiaodu_1, R.drawable.bsd_huiyuanguanli_1,
            R.drawable.bsd_lishiweixiu_1, R.drawable.bsd_cheliangxinxi_1, R.drawable.bsd_my_1};
    //图片数组
    private int[] imgno = new int[]{R.drawable.bsd_mrkx_2, R.drawable.bsd_kuaisubaojia_2, R.drawable.bsd_weixiuyuyue_2,
            R.drawable.bsd_weixiujiedan_2, R.drawable.bsd_zaichangdiaodu_2, R.drawable.bsd_huiyuanguanli_2,
            R.drawable.bsd_lishiweixiu_2, R.drawable.bsd_cheliangxinxi_2, R.drawable.bsd_my_2};
    String work_no;//历史维修单号
    private int firstPageIndex = 0;
    private int currentPageIndex = 0;
    private Queding_Quxiao queding_quxiao;
    private String[] lAuthorization = null; //左侧box的权限
    private URLS urls;
    private ChangePageDialog changePageDialog;
    private LinearLayout caozuoyuanInfo;
    private ChooseCarFragment mPageWXJDFragment;
    private ChooseCarFragment mPageWXYYFragment;
    private ChooseCarFragment mPageKSBJFragment;
    private ChooseCarFragment mPageMRKXFragment;
    private BSD_zaichangdiaodu_Fragment mPageZCDDFragment;
    private BSD_huiyuanguanli_Fragment mPageHYGLFragment;
    private BSD_lishiweixiu_Fragment mPageLSWXFragment;
    private BSD_cheliangxinxi_Fragment mPageCLXXFragment;
    private BSD_my_Fragment mPageMYFragment;
    private LinearLayout bsd_main_loginAgain;
    private LinearLayout bsd_main_tuichu;
    private TextView bsd_main_guanliyuan;
    private QueRen queRen;
    private Dialog mWeiboDialog;
    private MyTimeTask task;
    private BSD_LSWX_ety data;
    public BSD_WeiXiuYueYue_entiy entiy;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case TIMER:
                    // 更新在线状态，不考虑是否更新成功
                    updateOnline();
                    break;
                default:
                    break;
            }
        }
    };

    public BSD_WeiXiuYueYue_entiy getEntiy() {
        return entiy;
    }

    public void setEntiy(BSD_WeiXiuYueYue_entiy entiy) {
        this.entiy = entiy;
    }


    public BSD_LSWX_ety getData() {
        return data;
    }

    public void setData(BSD_LSWX_ety data) {
        this.data = data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        //透明状态栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        super.onCreate(savedInstanceState);
        DensityUtil.setCustomDensity(this, getApplication());
        setContentView(R.layout.activity_main);
        setTimer();
        init();
        initJYParams();
    }

    private void setTimer() {
        // 1分钟更新一次在线状态
        task = new MyTimeTask(60000, new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(TIMER);
            }
        });
        task.start();
    }

    public void stopTimer() {
        task.stop();
    }

    private void updateOnline() {
        AbRequestParams params = new AbRequestParams();
        params.put("name", MyApplication.shared.getString("name", ""));
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_UPDATEONLINE, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
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

    @Override
    protected void onResume() {
        super.onResume();
        DensityUtil.setCustomDensity(this, getApplication());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
    }

    /**
     * 精友数据
     */
    public void initJYParams() {
        Request.Post(MyApplication.shared.getString("ip", "") + urls.BSD_JY, null, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        item.getString("sys_shuju_url");
                        item.getString("sys_shuju_user");
                        item.getString("sys_shuju_pwd");
                        String ocrUrl = item.getString("sys_shuju_url") + "?operatorCode=" +
                                item.getString("sys_shuju_user") + "&operatorPwd=" +
                                item.getString("sys_shuju_pwd") + "&requestCode=700102";
                        String ocrUrlxcb = item.getString("sys_shuju_url") + "?operatorCode=" +
                                item.getString("sys_shuju_user") + "&operatorPwd=" +
                                item.getString("sys_shuju_pwd") + "&requestCode=700101";
                        MyApplication.editor.putString("ocrUrl", ocrUrl);
                        MyApplication.editor.putString("apiUrl", item.getString("sys_shuju_url"));
                        MyApplication.editor.putString("username", item.getString("sys_shuju_user"));
                        MyApplication.editor.putString("userpassword", item.getString("sys_shuju_pwd"));
                        MyApplication.editor.putString("ocrUrlxcb", ocrUrlxcb);
                        MyApplication.editor.commit();
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

    public void init() {
        caozuoyuanInfo = (LinearLayout) findViewById(R.id.ll_caozuoyuan);
        caozuoyuanInfo.setOnClickListener(this);
        bsd_main_loginAgain = (LinearLayout) findViewById(R.id.bsd_main_loginAgain);
        bsd_main_loginAgain.setOnClickListener(this);
        bsd_main_guanliyuan = (TextView) findViewById(R.id.bsd_main_guanliyuan);
        bsd_main_guanliyuan.setText(MyApplication.shared.getString("name", "没有用户"));
        bsd_main_tuichu= (LinearLayout) findViewById(R.id.bsd_main_tuichu);
        bsd_main_tuichu.setOnClickListener(this);
        // 获取点击的相对布局
        rl_mrkx = (RelativeLayout) findViewById(R.id.rl_mrkx);
        rl_ksbj = (RelativeLayout) findViewById(R.id.rl_ksbj);
        rl_wxyy = (RelativeLayout) findViewById(R.id.rl_wxyy);
        rl_wxjd = (RelativeLayout) findViewById(R.id.rl_wxjd);
        rl_zcdd = (RelativeLayout) findViewById(R.id.rl_zcdd);
        rl_hygl = (RelativeLayout) findViewById(R.id.rl_hygl);
        rl_lswx = (RelativeLayout) findViewById(R.id.rl_lswx);
        rl_clxx = (RelativeLayout) findViewById(R.id.rl_clxx);
        rl_my = (RelativeLayout) findViewById(R.id.rl_my);
        rl_mrkx.setOnClickListener(this);
        rl_ksbj.setOnClickListener(this);
        rl_wxyy.setOnClickListener(this);
        rl_wxjd.setOnClickListener(this);
        rl_zcdd.setOnClickListener(this);
        rl_hygl.setOnClickListener(this);
        rl_lswx.setOnClickListener(this);
        rl_clxx.setOnClickListener(this);
        rl_my.setOnClickListener(this);
        arr_rl = new RelativeLayout[9];
        arr_rl[0] = rl_mrkx;
        arr_rl[1] = rl_ksbj;
        arr_rl[2] = rl_wxyy;
        arr_rl[3] = rl_wxjd;
        arr_rl[4] = rl_zcdd;
        arr_rl[5] = rl_hygl;
        arr_rl[6] = rl_lswx;
        arr_rl[7] = rl_clxx;
        arr_rl[8] = rl_my;
        // 获取左侧图片控件
        arr_iv = new ImageView[9];
        arr_iv[0] = (ImageView) findViewById(R.id.bsd_im_0);
        arr_iv[1] = (ImageView) findViewById(R.id.bsd_im_1);
        arr_iv[2] = (ImageView) findViewById(R.id.bsd_im_2);
        arr_iv[3] = (ImageView) findViewById(R.id.bsd_im_3);
        arr_iv[4] = (ImageView) findViewById(R.id.bsd_im_4);
        arr_iv[5] = (ImageView) findViewById(R.id.bsd_im_5);
        arr_iv[6] = (ImageView) findViewById(R.id.bsd_im_6);
        arr_iv[7] = (ImageView) findViewById(R.id.bsd_im_7);
        arr_iv[8] = (ImageView) findViewById(R.id.bsd_im_8);
        // 初始化权限，并按照权限显示
        urls = new URLS();
        Bundle bundle = this.getIntent().getExtras();
        lAuthorization = bundle.getStringArray("QD");
        for (int i = (lAuthorization.length - 1); i >= 0; i--) {
            if (lAuthorization[i].equals("0")) {
                arr_rl[i].setVisibility(View.GONE);
            } else {
                firstPageIndex = i; // 用来获取左侧第一个显示的box的位置
            }
        }
        currentPageIndex = firstPageIndex;
        checkHighLight(firstPageIndex);
        initFragment(firstPageIndex);
    }

    public String getWork_no() {
        return work_no;
    }

    public void setWork_no(String work_no) {
        this.work_no = work_no;
    }

    private String getBoxNameByIndex(int index) {
        return arr_name_box[index];
    }

    /**
     * 初始化第一个碎片
     * @param firstPageIndex
     */
    private void initFragment(int firstPageIndex) {
        switch (firstPageIndex) {
            case 0:
                upBSD_MRKX_log();
                break;
            case 1:
                upBSD_KSBJ_Log();
                break;
            case 2:
                upBSD_WXYY_log();
                break;
            case 3:
                upBSD_WXJD_log();
                break;
            case 4:
                upBSD_ZCDD_page();
                break;
            case 5:
                upBSD_HYGL_page();
                break;
            case 6:
                upBSD_LSWX_page();
                break;
            case 7:
                upBSD_CLXX_page();
                break;
            case 8:
                upBSD_MY_page();
                break;
        }
    }

    /**
     * 跳转预约历史详情
     */
    public void wxyyxiangqing() {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_wxyy_xiangqing);
    }

    /**
     * 跳转历史快修
     */
    public void upLSKX() {
        DownJianPan.DJP(MainActivity.this);
        change(new BSD_LiShiKuaiXiu_Fragment());
    }

    /**
     * 跳转历史报价
     */
    public void upLSBJ() {
        DownJianPan.DJP(MainActivity.this);
        change(new BSD_lishibaojia_Fragment());
    }

    /**
     * 跳转预约历史
     */
    public void upLSYY() {
        DownJianPan.DJP(MainActivity.this);
        change(new BSD_YuYueLiShi_Fragment());
    }

    /**
     * 跳转历史接单
     */
    public void upLSJD() {
        DownJianPan.DJP(MainActivity.this);
        change(new BSD_lishijiedan_Fragment());
    }

    /**
     * 跳转联系我们
     */
    public void uplxwm() {
        DownJianPan.DJP(MainActivity.this);
        change(new BSD_LianXiWoMen_Fragment());
    }

    /**
     * 显示手动输入的Fragment
     * @param billType
     */
    public void showManualInputFragment(String billType) {
        DownJianPan.DJP(this);
        change(ManualInputFragment.newInstance(billType));
    }

    //切换碎片事务的方法
    private void change(Fragment fragment) {
        DensityUtil.setCustomDensity(this, getApplication());
        this.getSupportFragmentManager()//碎片管理者
                //开启事务
                .beginTransaction()
                //替换方法
                .replace(R.id.login_content, fragment)
                //提交事务
                .commit();
    }

    //选中项的变色
    private void checkHighLight(int index) {
        for (int i = 0; i < arr_rl.length; i++) {
            arr_rl[i].setBackgroundColor(getResources().getColor(R.color.bsd_xz_no));
            arr_iv[i].setImageResource(imgno[i]);
        }
        arr_rl[index].setBackgroundColor(getResources().getColor(R.color.bsd_xz_yes));
        arr_iv[index].setImageResource(imgyes[index]);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            return true;//不执行父类点击事件
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
    }

    /**
     * 启动美容快修主页（草稿单）
     * @param params  参数是草稿单的json字符串
     */
    public void showMrkxFragment(String params) {
        DownJianPan.DJP(this);
        change(BSD_MeiRongKuaiXiu_Fragment.newInstance(params));
    }

    public void showKSBJFragment(String params) {
        DownJianPan.DJP(this);
        change(BSD_kuaisubaojia_Fragment.newInstance(params));
    }

    public void showWXXQFragment(String params) {
        DownJianPan.DJP(this);
        change(BSD_WeiXiuXiangQing_Fragment.newInstance(params));
    }

    public void showWxddFragment(String params) {
        DownJianPan.DJP(this);
        if (currentPageIndex != 4) {
            checkHighLight(4);
            currentPageIndex = 4;
        }
        change(BSD_WeiXiuYeWuDiaoDu_Fragment.newInstance(params));
    }

    public void showWxjdFragment(String params) {
        DownJianPan.DJP(this);
        change(BSD_WeiXiuJieDan_Fragment.newInstance(params));
    }

    public void showWxyyFragment(String params) {
        DownJianPan.DJP(this);
        change(BSD_weixiuyuyue_Fragment.newInstance(params));
    }

    /**
     * 显示美容快修单历史详情
     * @param params
     */
    public void showMrkxXqFragment(String params) {
        DownJianPan.DJP(this);
        change(BSD_MeiRongKuaiXiuWeiXiuXiangQing_Fragment.newInstance(params));
    }

    public void showKsbjXqFragment(String params) {
        DownJianPan.DJP(this);
        change(BSD_kuaisubaojia_xiangqing_Fragment.newInstance(params));
    }

    public void showWxjdXqFragment(String params) {
        DownJianPan.DJP(this);
        change(BSD_WeiXiuJieDan_xiangqing_Fragment.newInstance(params));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_mrkx:
                switchPage(0);
                break;
            case R.id.rl_ksbj:
                switchPage(1);
                break;
            case R.id.rl_wxyy:
                switchPage(2);
                break;
            case R.id.rl_wxjd:
                switchPage(3);
                break;
            case R.id.rl_zcdd:
                switchPage(4);
                break;
            case R.id.rl_hygl:
                switchPage(5);
                break;
            case R.id.rl_lswx:
                switchPage(6);
                break;
            case R.id.rl_clxx:
                switchPage(7);
                break;
            case R.id.rl_my:
                switchPage(8);
                break;
            case R.id.bsd_main_tuichu:
                showLogoutDialog();
                break;
            case R.id.bsd_main_loginAgain:
                showLogagainDialog();
                break;
            case R.id.ll_caozuoyuan:
                showCaoZuoYuanDialog();
                break;
        }
    }

    /**
     * 显示操作员信息对话框
     */
    private void showCaoZuoYuanDialog() {
        String tip = "可查询公司：" + MyApplication.shared.getString("gongsi_gndm", "") +
                "\n可查询仓库：" + MyApplication.shared.getString("cangku_gndm", "") +
                "\n可操作仓库：" + MyApplication.shared.getString("cangkudo_gndm", "");
        queRen = new QueRen(this, tip);
        queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
            @Override
            public void onYesClick() {
                queRen.dismiss();
            }
        });
        queRen.show();
    }

    /**
     * 显示重新登录对话框
     */
    private void showLogagainDialog() {
        queding_quxiao = new Queding_Quxiao(MainActivity.this, "您确定要重新登录吗？");
        queding_quxiao.show();
        queding_quxiao.setOnResultClickListener(new Queding_Quxiao.OnResultClickListener() {
            @Override
            public void onConfirm() {
                queding_quxiao.dismiss();
                removeOnline(false);
            }

            @Override
            public void onCancel() {
                queding_quxiao.dismiss();
            }
        });
    }

    /**
     * 显示退出系统对话框
     */
    private void showLogoutDialog() {
        queding_quxiao = new Queding_Quxiao(MainActivity.this, "您确定要退出系统吗？");
        queding_quxiao.setOnResultClickListener(new Queding_Quxiao.OnResultClickListener() {
            @Override
            public void onConfirm() {
                queding_quxiao.dismiss();
                removeOnline(true);
            }

            @Override
            public void onCancel() {
                queding_quxiao.dismiss();
            }
        });
        queding_quxiao.show();
    }

    private void removeOnline(final boolean isExit) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "正在退出...");
        AbRequestParams params = new AbRequestParams();
        params.put("name", MyApplication.shared.getString("name", ""));
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_REMOVEONLINE, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                WeiboDialogUtils.closeDialog(mWeiboDialog);
                if (data.equals("success")) {
                    if (isExit) {
                        MyApplication.editor.putString("psd", "");
                        MyApplication.editor.commit();
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        android.os.Process.killProcess(android.os.Process.myPid());
                    } else {
                        MyApplication.editor.putString("psd", "");
                        MyApplication.editor.commit();
                        finish();
                        System.gc();
                    }
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
                Toast.makeText(MainActivity.this, "更新操作员在线状态失败", Toast.LENGTH_SHORT).show();
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });
    }

    private void switchPage(final int pageIndex) {
        if (pageIndex != currentPageIndex) {
            String clickname = getBoxNameByIndex(pageIndex);
            String currentname = getBoxNameByIndex(currentPageIndex);
            changePageDialog = new ChangePageDialog(this, currentname, clickname);
            changePageDialog.setOnResultClickListener(new ChangePageDialog.OnResultClickListener() {
                @Override
                public void onConfirm() {
                    currentPageIndex = pageIndex;
                    switch (pageIndex) {
                        case 0:
                            upBSD_MRKX_log();
                            break;
                        case 1:
                            upBSD_KSBJ_Log();
                            break;
                        case 2:
                            upBSD_WXYY_log();
                            break;
                        case 3:
                            upBSD_WXJD_log();
                            break;
                        case 4:
                            upBSD_ZCDD_page();
                            break;
                        case 5:
                            upBSD_HYGL_page();
                            break;
                        case 6:
                            upBSD_LSWX_page();
                            break;
                        case 7:
                            upBSD_CLXX_page();
                            break;
                        case 8:
                            upBSD_MY_page();
                            break;
                    }
                    checkHighLight(pageIndex);
                    changePageDialog.dismiss();
                }

                @Override
                public void onCancel() {
                    changePageDialog.dismiss();
                }
            });
            changePageDialog.show();
        }
    }

    /**
     * 显示我的页面
     */
    public void upBSD_MY_page() {
        DownJianPan.DJP(this);
        if (mPageMYFragment == null) {
            mPageMYFragment = new BSD_my_Fragment();
        }
        change(mPageMYFragment);
    }

    /**
     * 显示车辆信息页面
     */
    public void upBSD_CLXX_page() {
        DownJianPan.DJP(this);
        if (mPageCLXXFragment == null) {
            mPageCLXXFragment = new BSD_cheliangxinxi_Fragment();
        }
        change(mPageCLXXFragment);
    }

    /**
     * 显示历史维修页面
     */
    public void upBSD_LSWX_page() {
        DownJianPan.DJP(this);
        if (mPageLSWXFragment == null) {
            mPageLSWXFragment = new BSD_lishiweixiu_Fragment();
        }
        change(mPageLSWXFragment);
    }

    /**
     * 显示会员管理页面
     */
    public void upBSD_HYGL_page() {
        DownJianPan.DJP(this);
        if (mPageHYGLFragment == null) {
            mPageHYGLFragment = new BSD_huiyuanguanli_Fragment();
        }
        change(mPageHYGLFragment);
    }

    /**
     * 显示在场调度页面
     */
    public void upBSD_ZCDD_page() {
        DownJianPan.DJP(this);
        if (mPageZCDDFragment == null) {
            mPageZCDDFragment = new BSD_zaichangdiaodu_Fragment();
        }
        change(mPageZCDDFragment);
    }

    /**
     * 跳转美容快修选择车辆界面
     */
    public void upBSD_MRKX_log() {
        DownJianPan.DJP(this);
        if (mPageMRKXFragment == null) {
            mPageMRKXFragment = ChooseCarFragment.newInstance(Conts.BILLTYPE_MRKX);
        }
        change(mPageMRKXFragment);
    }

    /**
     * 跳转快速报价选择车辆界面
     */
    public void upBSD_KSBJ_Log() {
        DownJianPan.DJP(this);
        if (mPageKSBJFragment == null) {
            mPageKSBJFragment = ChooseCarFragment.newInstance(Conts.BILLTYPE_KSBJ);
        }
        change(mPageKSBJFragment);
    }

    /**
     * 跳转维修接单选择车辆界面
     */
    public void upBSD_WXYY_log() {
        DownJianPan.DJP(this);
        if (mPageWXYYFragment == null) {
            mPageWXYYFragment = ChooseCarFragment.newInstance(Conts.BILLTYPE_WXYY);
        }
        change(mPageWXYYFragment);
    }

    /**
     * 跳转维修预约选择车辆界面
     */
    public void upBSD_WXJD_log() {
        DownJianPan.DJP(this);
        if (mPageWXJDFragment == null) {
            mPageWXJDFragment = ChooseCarFragment.newInstance(Conts.BILLTYPE_WXJD);
        }
        change(mPageWXJDFragment);
    }

    public void showCLXXXQFragment(String params) {
        DownJianPan.DJP(this);
        change(BSD_CheXiangQing_Fragment.newInstance(params));
    }
}
