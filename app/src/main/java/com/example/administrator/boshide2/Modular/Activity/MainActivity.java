package com.example.administrator.boshide2.Modular.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ab.http.AbStringHttpResponseListener;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Fragment.BaoYangChaXun.BSD_BaoYangChaXun_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.CheLiangXinXi.BSD_cheliangxinxi_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.CheLiangXinXi.Entity.BSD_CLXX_ety;
import com.example.administrator.boshide2.Modular.Fragment.CheXiangQIng.BSD_CheXiangQing_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.HuiYuanGuanLi.BSD_huiyuanguanli_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.BSD_KSBJ_JianPanShuRu_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.BSD_KSBJ_Log_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.BSD_ksbj_smsr_Fragment;
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
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.BSD_MeiRongKuaiXiu_Log_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.dialogFragment.BSD_MeiRongKuaiXiu_cheliangxinxi_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.Entity.BSD_Car_Entity;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.Entity.BSD_KeHu_Entity;
import com.example.administrator.boshide2.Modular.Fragment.My.BSD_my_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.BSD_WeiXiuJieDan_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.BSD_WeiXiuJieDan_JianPanShuRu_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.BSD_WeiXiuJieDan_Log_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_Entity;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuXiangQing.BSD_WeiXiuXiangQing_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.BSD_WeiXiuYeWuDiaoDu_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.BSD_weixiuyuyue_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.BSD_weixiuyuyue_JianPanShuRu_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.BSD_weixiuyuyue_Log_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.Entity.BSD_WeiXiuYueYue_entiy;
import com.example.administrator.boshide2.Modular.Fragment.YuyueLiSHi.BSD_YuYueLiShi_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.YuyueLiSHi.BSD_weixiuyuyue_xiangqing_Fragment;
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

public class MainActivity extends FragmentActivity {
    //碎片数组
    private Fragment[] fragments;
    ImageView bsd_im_9, bsd_im_1, bsd_im_2, bsd_im_3, bsd_im_4, bsd_im_5, bsd_im_6, bsd_im_7, bsd_im_8;
    //图标的数组
    private ImageView[] arr_iv;// 图标的数组

    RelativeLayout bsd_rl_9, bsd_rl_1, bsd_rl_2, bsd_rl_3, bsd_rl_4, bsd_rl_5, bsd_rl_6, bsd_rl_7, bsd_rl_8;

    private RelativeLayout[] arr_rl;//点击布局
    //点击布局的id
    private int[] arr_id_box = {R.id.bsd_rl_9, R.id.bsd_rl_1, R.id.bsd_rl_2, R.id.bsd_rl_3, R.id.bsd_rl_4,
            R.id.bsd_rl_5, R.id.bsd_rl_6, R.id.bsd_rl_7, R.id.bsd_rl_8};

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
    private int firstBoxIndex = 0;
    private int currentBoxIndex = 0;
    private Queding_Quxiao queding_quxiao;
    private String[] lAuthorization = null; //左侧box的权限
    private URLS urls;
    private ChangePageDialog changePageDialog;
    private LinearLayout caozuoyuanInfo;

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
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
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
        initFragment();
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
                        .heightDimenRes(R.dimen.qb_px_220)
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
        bsd_rl_1 = (RelativeLayout) findViewById(R.id.bsd_rl_1);
        bsd_rl_2 = (RelativeLayout) findViewById(R.id.bsd_rl_2);
        bsd_rl_3 = (RelativeLayout) findViewById(R.id.bsd_rl_3);
        bsd_rl_4 = (RelativeLayout) findViewById(R.id.bsd_rl_4);
        bsd_rl_5 = (RelativeLayout) findViewById(R.id.bsd_rl_5);
        bsd_rl_6 = (RelativeLayout) findViewById(R.id.bsd_rl_6);
        bsd_rl_7 = (RelativeLayout) findViewById(R.id.bsd_rl_7);
        bsd_rl_8 = (RelativeLayout) findViewById(R.id.bsd_rl_8);
        bsd_rl_9 = (RelativeLayout) findViewById(R.id.bsd_rl_9);
        arr_rl = new RelativeLayout[9];
        arr_rl[0] = bsd_rl_9;
        arr_rl[1] = bsd_rl_1;
        arr_rl[2] = bsd_rl_2;
        arr_rl[3] = bsd_rl_3;
        arr_rl[4] = bsd_rl_4;
        arr_rl[5] = bsd_rl_5;
        arr_rl[6] = bsd_rl_6;
        arr_rl[7] = bsd_rl_7;
        arr_rl[8] = bsd_rl_8;

        for (int i = (lAuthorization.length - 1); i >= 0; i--) {
            if (lAuthorization[i].equals("0")) {
                arr_rl[i].setVisibility(View.GONE);
            } else {
                firstBoxIndex = i; // 用来获取左侧第一个显示的box的位置
            }
        }
        checkHighLight(firstBoxIndex);
        change(fragments[firstBoxIndex]);
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

    /**
     * 切换碎片的点击方法
     * @param view
     */
    public void clickbox(View view) {
        // 循环 存储着id的的数组，根据点击的id获取到位置
        for (int i = 0; i < arr_id_box.length; i++) {
            if (view.getId() == arr_id_box[i]) {
                final int clickBoxIndex = i;
                DownJianPan.DJP(MainActivity.this);
                if (zaichangtiaozhuan == 1) {
                    currentBoxIndex = 4;
                    zaichangtiaozhuan = 0;
                    Log.i("njc", "跳转问题111：clickBoxIndex==" + clickBoxIndex + "===currentBoxIndex===" + currentBoxIndex);
                } else {
                    // 如果点击的不是当前的box，则弹出提示框，并退出循环
                    if (clickBoxIndex != currentBoxIndex) {
                        String clickname = getBoxNameByIndex(clickBoxIndex);
                        String currentname = getBoxNameByIndex(currentBoxIndex);
                        changePageDialog = new ChangePageDialog(MainActivity.this, currentname, clickname);
                        changePageDialog.setOnResultClickListener(new ChangePageDialog.OnResultClickListener() {
                            @Override
                            public void onConfirm() {
                                change(fragments[clickBoxIndex]);
                                checkHighLight(clickBoxIndex);
                                changePageDialog.dismiss();
                                currentBoxIndex = clickBoxIndex;
                            }

                            @Override
                            public void onCancel() {
                                changePageDialog.dismiss();
                            }
                        });
                        changePageDialog.show();
                        break;
                    }
                }
            }
        }
    }

    private String getBoxNameByIndex(int index) {
        return arr_name_box[index];
    }

    /**
     * 初始化碎片
     */
    int zaichangtiaozhuan = 0;

    private void initFragment() {
        //快速报价
        BSD_ksbj = new BSD_kuaisubaojia_Fragment();
        //维修预约
        BSD_wxyy = new BSD_weixiuyuyue_Fragment();
        //维修接单
        BSD_wxjd = new BSD_WeiXiuJieDan_Fragment();
        //在厂调度
        BSD_zcdd = new BSD_zaichangdiaodu_Fragment();
        //会员管理
        BSD_hygl = new BSD_huiyuanguanli_Fragment();
        //历史维修
        BSD_lswx = new BSD_lishiweixiu_Fragment();
        //车辆信息
        BSD_clxx = new BSD_cheliangxinxi_Fragment();
        //我的管理
        BSD_my = new BSD_my_Fragment();
        //历史报价
        BSD_lsbj = new BSD_lishibaojia_Fragment();
        //预约历史
        BSD_lsyy = new BSD_YuYueLiShi_Fragment();
        //历史接单
        BSD_lsjd = new BSD_lishijiedan_Fragment();
        //维修详情
        BSD_wxxq = new BSD_WeiXiuXiangQing_Fragment();
        //车辆详情
        BSD_cxq = new BSD_CheXiangQing_Fragment();
        //联系我们
        BSD_lxwm = new BSD_LianXiWoMen_Fragment();
        //快速扫描的入口
        BSD_ksbj_smsr = new BSD_ksbj_smsr_Fragment();

        //键盘输入
        BSD_srjp = new BSD_KSBJ_JianPanShuRu_Fragment();
        //快速报价
        BSD_KSBJ_Log = new BSD_KSBJ_Log_Fragment();
        //维修预约
        BSD_wxyy_log = new BSD_weixiuyuyue_Log_Fragment();
        //维修预约键盘输入
        BSD_wxyy_srjp = new BSD_weixiuyuyue_JianPanShuRu_Fragment();
        //维修接单
        BSD_wxjd_log = new BSD_WeiXiuJieDan_Log_Fragment();
        //维修接单键盘输入
        BSD_wxjd_srjp = new BSD_WeiXiuJieDan_JianPanShuRu_Fragment();
        //保养查询
        BSD_bycx = new BSD_BaoYangChaXun_Fragment();
        //快速报价详情
        BSD_ksbj_xiangqing = new BSD_kuaisubaojia_xiangqing_Fragment();
        //维修预约详情
        BSD_wxyy_xiangqing = new BSD_weixiuyuyue_xiangqing_Fragment();
        //维修接单详情
        BSD_wxjd_xiangqing = new BSD_WeiXiuJieDan_xiangqing_Fragment();
        // 美容快修
        BSD_mrby_Log = new BSD_MeiRongKuaiXiu_Log_Fragment();
        // 手动输入页面
        BSD_mrkx_jpsr = new ManualInputFragment();
        //美容快修主页面
        BSD_mrkx = new BSD_MeiRongKuaiXiu_Fragment();
        //美容快修编辑车辆、客户信息界面
        BSD_mrkx_clxx = new BSD_MeiRongKuaiXiu_cheliangxinxi_Fragment();
        //历史快修
        BSD_lskx = new BSD_LiShiKuaiXiu_Fragment();

        BSD_mrkx_xq = new BSD_MeiRongKuaiXiuWeiXiuXiangQing_Fragment();

        fragments = new Fragment[29];
        fragments[0] = BSD_mrby_Log;
        fragments[1] = BSD_KSBJ_Log;
        fragments[2] = BSD_wxyy_log;
        fragments[3] = BSD_wxjd_log;
        fragments[4] = BSD_zcdd;
        fragments[5] = BSD_hygl;
        fragments[6] = BSD_lswx;
        fragments[7] = BSD_clxx;
        fragments[8] = BSD_my;
        fragments[9] = BSD_lsbj;
        fragments[10] = BSD_lsyy;
        fragments[11] = BSD_lsjd;
        fragments[12] = BSD_wxxq;
        fragments[13] = BSD_cxq;
        fragments[14] = BSD_lxwm;
        fragments[15] = BSD_ksbj_smsr;
        fragments[16] = BSD_wxywdd;
        fragments[17] = BSD_srjp;
        fragments[18] = BSD_ksbj;
        fragments[19] = BSD_wxyy;
        fragments[20] = BSD_wxyy_srjp;
        fragments[21] = BSD_wxjd;
        fragments[22] = BSD_wxjd_srjp;
        fragments[23] = BSD_bycx;
        fragments[24] = BSD_ksbj_xiangqing;
        fragments[25] = BSD_wxyy_xiangqing;
        fragments[26] = BSD_wxjd_xiangqing;
        fragments[27] = BSD_mrkx_jpsr;
        fragments[28] = BSD_mrkx;
    }


    /**
     * 跳转历史快修
     */
    public void upmrkx_xq() {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_mrkx_xq);
    }

    /**
     * 跳转历史快修
     */
    public void uplskx() {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_lskx);
    }


    /**
     * 跳转快速报价
     *
     * @param view
     */
    public void upksbj(View view) {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_ksbj);
    }

    /**
     * 跳转快速报价
     */
    public void upksbj() {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_ksbj);
    }

    /**
     * 跳转快速报价lgo
     */
    public void upksbjlog() {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_KSBJ_Log);
    }

    /**
     * 跳转快速报价详情
     */
    public void upksbjxiangqing() {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_ksbj_xiangqing);
    }

    /**
     * 跳转历史报价
     *
     * @param view
     */
    public void upqhf(View view) {

        DownJianPan.DJP(MainActivity.this);
        change(BSD_lsbj);
    }

    /**
     * 跳转预约历史
     *
     * @param view
     */
    public void upjiedan(View view) {

        DownJianPan.DJP(MainActivity.this);
        change(BSD_lsyy);
    }

    /**
     * 跳转历史报价log
     *
     * @param view
     */
    public void upqhflog(View view) {

        DownJianPan.DJP(MainActivity.this);
        change(BSD_wxyy_log);
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
     *
     * @param view
     */
    public void upyuyue(View view) {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_wxyy);
    }

    /**
     * 跳转维修预约
     */
    public void upyuyue() {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_wxyy);
    }

    /**
     * 跳转历史接单
     *
     * @param view
     */
    public void uplishijiedan(View view) {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_lsjd);
    }

    /**
     * 跳转历史接单log
     *
     * @param view
     */
    public void uplishijiedanlog(View view) {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_wxjd_log);
    }

    /**
     * 跳转历史接单详情
     */
    public void uowxjdxiangqing() {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_wxjd_xiangqing);
    }

    /**
     * 跳转维修接单
     *
     * @param view
     */
    public void upwxjd(View view) {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_wxjd);
    }

    /**
     * 跳转维修接单
     */
    public void upwxjd() {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_wxjd);
    }

    /**
     * 跳转维修详情
     *
     * @param view
     */
    public void upwxxq(View view) {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_wxxq);
    }

    /**
     * 跳转历史维修
     *
     * @param view
     */
    public void uplswx(View view) {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_lswx);
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
     * 跳转车辆信息列表
     *
     * @param view
     */
    public void upclxx(View view) {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_clxx);
    }


    /**
     * 跳转联系我们
     *
     * @param view
     */
    public void uplxwm(View view) {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_lxwm);
    }

    /**
     * 跳转我的guanli
     *
     * @param view
     */
    public void upmy(View view) {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_my);
    }

    /**
     * 跳转维修业务调度页面
     */
    public void upwxywdd(View view) {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_wxywdd);
    }

    /**
     * 跳转维
     */
    public void upwxywdd() {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_wxywdd);
    }

    /**
     * 跳转在场调度页面
     */
    public void upzcdd(View view) {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_zcdd);
    }

    /**
     * 跳转在场调度页面
     */
    public void upzcdd() {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_zcdd);
    }

    /**
     * 跳转输入界面
     */
    public void upsrjm(View view) {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_srjp);
    }

    /**
     * 跳转输入界面
     */
    public void upsrjm() {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_srjp);
    }

    /**
     * 跳转输入界面
     */
    public void upBSD_KSBJ_Log(View view) {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_KSBJ_Log);
    }

    /**
     * 跳转输入界面
     */
    public void upBSD_KSBJ_Log() {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_KSBJ_Log);
    }

    /**
     * 微信预约手动输入
     */
    public void upBSD_WXYY_srjp(View view) {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_wxyy_srjp);
    }

    /**
     * 微信预约手动输入
     */
    public void upBSD_WXYY_srjp() {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_wxyy_srjp);
    }

    /**
     * 维修预约跳转车牌识别和手动输入
     */
    public void upBSD_WXyy_log(View view) {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_wxyy_log);
    }

    /**
     * 维修预约跳转车牌识别和手动输入
     */
    public void upBSD_WXyy_log() {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_wxyy_log);
    }

    /**
     * 维修接单跳转车牌识别和手动输入
     */
    public void upBSD_WXJD_log(View view) {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_wxjd_log);
    }

    /**
     * 维修接单跳转车牌识别和手动输入
     */
    public void upBSD_WXJD_log() {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_wxjd_log);
    }

    /**
     * 维修接单手动输入
     */
    public void upBSD_WXJD_srjp(View view) {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_wxjd_srjp);
    }

    /**
     * 显示手动输入的Fragment
     */
    public void showManualInputFragment() {
        DownJianPan.DJP(this);
        change(new ManualInputFragment());
    }

    /**
     * 维修接单手动输入
     */
    public void upBSD_WXJD_srjp() {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_wxjd_srjp);
    }

    /**
     * 保养查询
     */
    public void upBSD_bycx() {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_bycx);
    }

    /**
     * 美容快修键盘输入
     */
    public void upBSD_mrkx_jpsr() {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_mrkx_jpsr);
    }

    public void upBSD_mrkx_log() {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_mrby_Log);
    }






    /**
     * 编辑车辆、客户信息界面
     */
    public void upBSD_mrkx_clxx() {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_mrkx_clxx);
    }






    /**
     * 美容快修主页
     */
    public void upBSD_mrkx() {
        DownJianPan.DJP(MainActivity.this);
        change(BSD_mrkx);
    }

    /**
     * 启动美容快修主页（草稿单）
     * @param params  参数是草稿单的json字符串
     */
    public void showMrkxFragment(String params) {
        DownJianPan.DJP(this);
        change(BSD_MeiRongKuaiXiu_Fragment.newInstance(params));
    }


//    //切换碎片事务的方法
//    private void changedata(Fragment f,BSD_WeiXiuYueYue_entiy entiy) {
//        this.getSupportFragmentManager()//碎片管理者
//                //开启事务
//                .beginTransaction()
//                //替换方法
//                .replace(R.id.login_content, f)
//                //提交事务
//                .commit();
//    }

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
}
