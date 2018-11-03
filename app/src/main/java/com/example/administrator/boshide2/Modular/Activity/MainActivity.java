package com.example.administrator.boshide2.Modular.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ab.http.AbStringHttpResponseListener;
import com.example.administrator.boshide2.Conts;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Fragment.CheLiangXinXi.BSD_cheliangxinxi_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.CheLiangXinXi.Entity.BSD_CLXX_ety;
import com.example.administrator.boshide2.Modular.Fragment.ChooseCarFragment;
import com.example.administrator.boshide2.Modular.Fragment.HuiYuanGuanLi.BSD_huiyuanguanli_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.BSD_kuaisubaojia_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.Entity.BSD_KuaiSuBaoJia_ety;
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
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.ManualInputFragment;
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
import com.example.administrator.boshide2.Modular.View.diaog.Queding_Quxiao;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.DensityUtil;
import com.example.administrator.boshide2.Tools.DownJianPan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @主页面
 * @陈佳宁
 * @2017-4-13
 */

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    //碎片数组
    private Fragment[] fragments;
    ImageView bsd_im_9, bsd_im_1, bsd_im_2, bsd_im_3, bsd_im_4, bsd_im_5, bsd_im_6, bsd_im_7, bsd_im_8;
    //图标的数组
    private ImageView[] arr_iv;// 图标的数组

    RelativeLayout rl_mrkx;
    RelativeLayout rl_ksbj;
    RelativeLayout rl_wxyy;
    RelativeLayout rl_wxjd;
    RelativeLayout rl_zcdd;
    RelativeLayout rl_hygl;
    RelativeLayout rl_lswx;
    RelativeLayout rl_clxx;
    RelativeLayout rl_my;

    private RelativeLayout[] arr_rl;//点击布局
    //点击布局的id
    private int[] arr_id_box = {R.id.rl_mrkx, R.id.rl_ksbj, R.id.rl_wxyy, R.id.rl_wxjd, R.id.rl_zcdd,
            R.id.rl_hygl, R.id.rl_lswx, R.id.rl_clxx, R.id.rl_my};

    private String[] arr_name_box = {"美容快修", "快速报价", "维修预约", "维修接待", "在厂调度",
            "会员管理", "历史维修", "车辆信息", "我的管理"};
    //碎片
    private Fragment BSD_mrkx_xq, BSD_lskx, BSD_mrkx, BSD_mrkx_clxx,BSD_mrkx_jpsr, BSD_mrby_Log, BSD_wxjd_xiangqing, BSD_wxyy_xiangqing, BSD_ksbj_xiangqing, BSD_bycx, BSD_wxjd_log, BSD_wxjd_srjp, BSD_wxyy_srjp, BSD_wxyy_log, BSD_KSBJ_Log, BSD_srjp, BSD_ksbj_smsr, BSD_lxwm, BSD_cxq, BSD_wxxq, BSD_ksbj, BSD_wxyy, BSD_wxjd, BSD_zcdd, BSD_hygl, BSD_lswx, BSD_clxx, BSD_my, BSD_lsbj, BSD_lsyy, BSD_lsjd;
    private BSD_WeiXiuYeWuDiaoDu_Fragment BSD_wxywdd;
    //图片数组
    private int[] imgyes = new int[]{R.drawable.bsd_mrkx_1, R.drawable.bsd_kuaisubaojia_1, R.drawable.bsd_weixiuyuyue_1,
            R.drawable.bsd_weixiujiedan_1, R.drawable.bsd_zaichangdiaodu_1, R.drawable.bsd_huiyuanguanli_1,
            R.drawable.bsd_lishiweixiu_1, R.drawable.bsd_cheliangxinxi_1, R.drawable.bsd_my_1};
    //图片数组
    private int[] imgno = new int[]{R.drawable.bsd_mrkx_2, R.drawable.bsd_kuaisubaojia_2, R.drawable.bsd_weixiuyuyue_2,
            R.drawable.bsd_weixiujiedan_2, R.drawable.bsd_zaichangdiaodu_2, R.drawable.bsd_huiyuanguanli_2,
            R.drawable.bsd_lishiweixiu_2, R.drawable.bsd_cheliangxinxi_2, R.drawable.bsd_my_2};
    public RelativeLayout relativeLayout;
    public BSD_CLXX_ety clxx_ety;
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

    public BSD_WeiXiuYueYue_entiy getEntiy() {
        return entiy;
    }
    public void setEntiy(BSD_WeiXiuYueYue_entiy entiy) {
        this.entiy = entiy;
    }
    public BSD_WeiXiuYueYue_entiy entiy;

    public BSD_KuaiSuBaoJia_ety ksbjenity;


    public BSD_KuaiSuBaoJia_ety getKsbjenity() {
        return ksbjenity;
    }

    public void setKsbjenity(BSD_KuaiSuBaoJia_ety ksbjenity) {
        this.ksbjenity = ksbjenity;
    }
    public BSD_WeiXiuJieDan_Entity wxjdentity;

    public BSD_WeiXiuJieDan_Entity getWxjdentity() {
        return wxjdentity;
    }
    public void setWxjdentity(BSD_WeiXiuJieDan_Entity wxjdentity) {
        this.wxjdentity = wxjdentity;
    }
    //客户信息实体
    public BSD_KeHu_Entity khentity;
    public BSD_KeHu_Entity getKhentity() {
        return khentity;
    }
    public void setKhentity(BSD_KeHu_Entity khentity) {
        this.khentity = khentity;
    }
    //车辆信息实体
    public BSD_Car_Entity carentity;
    public BSD_Car_Entity getCarentity() {
        return carentity;
    }
    public void setCarentity(BSD_Car_Entity carentity) {
        this.carentity = carentity;
    }
    RelativeLayout bsd_top;
    LinearLayout bsd_main_loginAgain,bsd_main_tuichu;
    TextView bsd_main_guanliyuan;

    public BSD_LSWX_ety getData() {
        return data;
    }

    public void setData(BSD_LSWX_ety data) {
        this.data = data;
    }

    BSD_LSWX_ety data;
    ScrollView scrollView;

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
        scrollView= (ScrollView) findViewById(R.id.scrollView);
        urls = new URLS();
        Bundle b = this.getIntent().getExtras();
        lAuthorization = b.getStringArray("QD");
        initFragment(firstPageIndex);
        init();
        jinyoushuju();
    }

    /**
     * 精友数据
     */
    public void jinyoushuju() {
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
                        String a = item.getString("sys_shuju_url") + "?operatorCode=" +
                                item.getString("sys_shuju_user") + "&operatorPwd=" +
                                item.getString("sys_shuju_pwd") + "&requestCode=700102";
                        String b = item.getString("sys_shuju_url") + "?operatorCode=" +
                                item.getString("sys_shuju_user") + "&operatorPwd=" +
                                item.getString("sys_shuju_pwd") + "&requestCode=700101";
                        MyApplication.editor.putString("ocrUrl", a);
                        MyApplication.editor.putString("apiUrl", item.getString("sys_shuju_url"));
                        MyApplication.editor.putString("username", item.getString("sys_shuju_user"));
                        MyApplication.editor.putString("userpassword", item.getString("sys_shuju_pwd"));

                        MyApplication.editor.putString("ocrUrlxcb", b);
                        MyApplication.editor.commit();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("cjn", "精友数据=" + data);
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
        caozuoyuanInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog.Builder builder = new CustomDialog.Builder(MainActivity.this);
                CustomDialog dialog = builder.style(R.style.mydialog)
                        .view(R.layout.dialog_caozuoyuaninfo_layout)
                        .widthDimenRes(R.dimen.qb_px_280)
//                        .heightDimenRes(R.dimen.qb_px_220)
                        .heightdp(LinearLayout.LayoutParams.WRAP_CONTENT)
                        .cancelTouchout(true)
                        .addViewOnclick(R.id.tv_confirm, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                CustomDialog.dismissDialog();
                            }
                        })
                        .build();
                TextView gongsiGndm = (TextView) dialog.getView().findViewById(R.id.tv_gongsi_gndm);
                TextView cangkuGndm = (TextView) dialog.getView().findViewById(R.id.cangku_gndm);
                TextView cangkuduGndm = (TextView) dialog.getView().findViewById(R.id.cangkudo_gndm);
                gongsiGndm.setText("可查询公司：" + MyApplication.shared.getString("gongsi_gndm", ""));
                cangkuGndm.setText("可查询仓库：" + MyApplication.shared.getString("cangku_gndm", ""));
                cangkuduGndm.setText("可操作仓库：" + MyApplication.shared.getString("cangkudo_gndm", ""));
                dialog.show();
            }
        });
        bsd_main_loginAgain = (LinearLayout) findViewById(R.id.bsd_main_loginAgain);
        bsd_main_loginAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queding_quxiao = new Queding_Quxiao(MainActivity.this, "您确定要重新登录");
                queding_quxiao.show();
                queding_quxiao.setOnResultClickListener(new Queding_Quxiao.OnResultClickListener() {
                    @Override
                    public void onConfirm() {
                        MyApplication.editor.putString("psd", "");
                        MyApplication.editor.commit();
                        queding_quxiao.dismiss();
                        finish();
                        System.gc();
                    }

                    @Override
                    public void onCancel() {
                        queding_quxiao.dismiss();
                    }
                });

            }
        });
        bsd_main_guanliyuan = (TextView) findViewById(R.id.bsd_main_guanliyuan);
        bsd_main_guanliyuan.setText(MyApplication.shared.getString("name", "没有用户"));

        bsd_main_tuichu= (LinearLayout) findViewById(R.id.bsd_main_tuichu);
        bsd_main_tuichu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queding_quxiao = new Queding_Quxiao(MainActivity.this, "您确定要退出系统");
                queding_quxiao.show();
                queding_quxiao.setOnResultClickListener(new Queding_Quxiao.OnResultClickListener() {
                    @Override
                    public void onConfirm() {
                        MyApplication.editor.putString("psd", "");
                        MyApplication.editor.commit();
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }

                    @Override
                    public void onCancel() {
                        queding_quxiao.dismiss();
                    }
                });
            }
        });

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        // 获取图片，并且吧图片放到图片数组里
        bsd_im_1 = (ImageView) findViewById(R.id.bsd_im_1);
        bsd_im_2 = (ImageView) findViewById(R.id.bsd_im_2);
        bsd_im_3 = (ImageView) findViewById(R.id.bsd_im_3);
        bsd_im_4 = (ImageView) findViewById(R.id.bsd_im_4);
        bsd_im_5 = (ImageView) findViewById(R.id.bsd_im_5);
        bsd_im_6 = (ImageView) findViewById(R.id.bsd_im_6);
        bsd_im_7 = (ImageView) findViewById(R.id.bsd_im_7);
        bsd_im_8 = (ImageView) findViewById(R.id.bsd_im_8);
        bsd_im_9 = (ImageView) findViewById(R.id.bsd_im_9);
        arr_iv = new ImageView[9];
        arr_iv[0] = bsd_im_9;
        arr_iv[1] = bsd_im_1;
        arr_iv[2] = bsd_im_2;
        arr_iv[3] = bsd_im_3;
        arr_iv[4] = bsd_im_4;
        arr_iv[5] = bsd_im_5;
        arr_iv[6] = bsd_im_6;
        arr_iv[7] = bsd_im_7;
        arr_iv[8] = bsd_im_8;
        //获取点击的相对布局，为以后点击没想更换颜色做准备
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

        for (int i = (lAuthorization.length - 1); i >= 0; i--) {
            if (lAuthorization[i].equals("0")) {
                arr_rl[i].setVisibility(View.GONE);
            } else {
                firstPageIndex = i; // 用来获取左侧第一个显示的box的位置
            }
        }
        checkHighLight(firstPageIndex);
        currentPageIndex = firstPageIndex;
//        change(fragments[firstPageIndex]);
        initFragment(firstPageIndex);
    }


    public BSD_CLXX_ety getClxx_ety() {
        return clxx_ety;
    }

    public void setClxx_ety(BSD_CLXX_ety clxx_ety) {
        this.clxx_ety = clxx_ety;
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
     * 初始化碎片
     */
    int zaichangtiaozhuan = 0;

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
//        //快速报价
//        BSD_ksbj = new BSD_kuaisubaojia_Fragment();
//        //维修预约
//        BSD_wxyy = new BSD_weixiuyuyue_Fragment();
//        //维修接单
//        BSD_wxjd = new BSD_WeiXiuJieDan_Fragment();
//        //在厂调度
//        BSD_zcdd = new BSD_zaichangdiaodu_Fragment();
//        //会员管理
//        BSD_hygl = new BSD_huiyuanguanli_Fragment();
//        //历史维修
//        BSD_lswx = new BSD_lishiweixiu_Fragment();
//        //车辆信息
//        BSD_clxx = new BSD_cheliangxinxi_Fragment();
//        //我的管理
//        BSD_my = new BSD_my_Fragment();
//        //历史报价
//        BSD_lsbj = new BSD_lishibaojia_Fragment();
//        //预约历史
//        BSD_lsyy = new BSD_YuYueLiShi_Fragment();
//        //历史接单
//        BSD_lsjd = new BSD_lishijiedan_Fragment();
//        //维修详情
//        BSD_wxxq = new BSD_WeiXiuXiangQing_Fragment();
//        //车辆详情
//        BSD_cxq = new BSD_CheXiangQing_Fragment();
//        //联系我们
//        BSD_lxwm = new BSD_LianXiWoMen_Fragment();
//        //快速扫描的入口
//        BSD_ksbj_smsr = new BSD_ksbj_smsr_Fragment();
//
//        //键盘输入
//        BSD_srjp = new BSD_KSBJ_JianPanShuRu_Fragment();
//        //快速报价
//        BSD_KSBJ_Log = new BSD_KSBJ_Log_Fragment();
//        //维修预约
//        BSD_wxyy_log = new BSD_weixiuyuyue_Log_Fragment();
//        //维修预约键盘输入
//        BSD_wxyy_srjp = new BSD_weixiuyuyue_JianPanShuRu_Fragment();
//        //维修接单
//        BSD_wxjd_log = new BSD_WeiXiuJieDan_Log_Fragment();
//        //维修接单键盘输入
//        BSD_wxjd_srjp = new BSD_WeiXiuJieDan_JianPanShuRu_Fragment();
//        //保养查询
//        BSD_bycx = new BSD_BaoYangChaXun_Fragment();
//        //快速报价详情
//        BSD_ksbj_xiangqing = new BSD_kuaisubaojia_xiangqing_Fragment();
//        //维修预约详情
//        BSD_wxyy_xiangqing = new BSD_weixiuyuyue_xiangqing_Fragment();
//        //维修接单详情
//        BSD_wxjd_xiangqing = new BSD_WeiXiuJieDan_xiangqing_Fragment();
//        // 美容快修
//        BSD_mrby_Log = new BSD_MeiRongKuaiXiu_Log_Fragment();
//        // 手动输入页面
//        BSD_mrkx_jpsr = new ManualInputFragment();
//        //美容快修主页面
//        BSD_mrkx = new BSD_MeiRongKuaiXiu_Fragment();
//        //美容快修编辑车辆、客户信息界面
//        BSD_mrkx_clxx = new BSD_MeiRongKuaiXiu_cheliangxinxi_Fragment();
//        //历史快修
//        BSD_lskx = new BSD_LiShiKuaiXiu_Fragment();
//
//        BSD_mrkx_xq = new BSD_MeiRongKuaiXiuWeiXiuXiangQing_Fragment();
//
//        fragments = new Fragment[29];
//        fragments[0] = BSD_mrby_Log;
//        fragments[1] = BSD_KSBJ_Log;
//        fragments[2] = BSD_wxyy_log;
//        fragments[3] = BSD_wxjd_log;
//        fragments[4] = BSD_zcdd;
//        fragments[5] = BSD_hygl;
//        fragments[6] = BSD_lswx;
//        fragments[7] = BSD_clxx;
//        fragments[8] = BSD_my;
//        fragments[9] = BSD_lsbj;
//        fragments[10] = BSD_lsyy;
//        fragments[11] = BSD_lsjd;
//        fragments[12] = BSD_wxxq;
//        fragments[13] = BSD_cxq;
//        fragments[14] = BSD_lxwm;
//        fragments[15] = BSD_ksbj_smsr;
//        fragments[16] = BSD_wxywdd;
//        fragments[17] = BSD_srjp;
//        fragments[18] = BSD_ksbj;
//        fragments[19] = BSD_wxyy;
//        fragments[20] = BSD_wxyy_srjp;
//        fragments[21] = BSD_wxjd;
//        fragments[22] = BSD_wxjd_srjp;
//        fragments[23] = BSD_bycx;
//        fragments[24] = BSD_ksbj_xiangqing;
//        fragments[25] = BSD_wxyy_xiangqing;
//        fragments[26] = BSD_wxjd_xiangqing;
//        fragments[27] = BSD_mrkx_jpsr;
//        fragments[28] = BSD_mrkx;
    }


    /**
     * 跳转快速报价
     */
    public void upksbj() {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_ksbj);
    }

    /**
     * 跳转预约历史详情
     */
    public void wxyyxiangqing() {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_wxyy_xiangqing);
    }

    /**
     * 跳转维修预约
     */
    public void upyuyue() {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_wxyy);
    }

    /**
     * 跳转维修接单
     */
    public void upwxjd() {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_wxjd);
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
     * 跳转车辆详情
     * @param view
     */
    public void upclxq(View view) {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_cxq);
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

    /**
     * 保养查询
     */
    public void upBSD_bycx() {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_bycx);
    }

    //切换碎片事务的方法
    private void change(Fragment fragment) {
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
        }
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
}
