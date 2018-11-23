package com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.dialogFragment;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.alibaba.fastjson.JSON;
import com.example.administrator.boshide2.Conts;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Activity.MainActivity;
import com.example.administrator.boshide2.Modular.Adapter.AbstractSpinerAdapter;
import com.example.administrator.boshide2.Modular.Adapter.CustemSpinerAdapter;
import com.example.administrator.boshide2.Modular.Entity.CustemObject;
import com.example.administrator.boshide2.Modular.Entity.Kehu_Entity;
import com.example.administrator.boshide2.Modular.Entity.WorkCheliangSm_Entity;
import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.Entity.BSD_KuaiSuBaoJia_ety;
import com.example.administrator.boshide2.Modular.Fragment.PinpaiInfoDialog;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.Entity.BSD_Car_Entity;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.Entity.BSD_KeHu_Entity;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_Entity;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.Entity.BSD_WeiXiuYueYue_entiy;
import com.example.administrator.boshide2.Modular.View.SpinerPopWindow;
import com.example.administrator.boshide2.Modular.View.Time.TimeDialog;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.BsdUtil;
import com.example.administrator.boshide2.Tools.QuanQuan.WeiboDialogUtils;
import com.example.administrator.boshide2.Tools.Show;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @编辑车辆、客户信息页面 Created by 李赛 on 2017-09-06.
 */

public class BSD_MeiRongKuaiXiu_cheliangxinxi_Fragment extends DialogFragment {
    private static final String PARAM_BILLTYPE = "param_billtype";
    private static final String PARAM_BILLNO = "param_billno";
    private static final String PARAM_CHENO = "param_cheno";
    private MainActivity mainActivity;
    private TextView confirm;
    private TextView cancel;
    private EditText cl_wxys, cl_nf, cl_vin,et_cl_cp;
    private EditText kh_mc, kh_lxr, kh_sj, kh_dh;
    private Dialog mWeiboDialog;
    private List<BSD_WeiXiuJieDan_Entity> list = new ArrayList<BSD_WeiXiuJieDan_Entity>();
    private List<BSD_KuaiSuBaoJia_ety> list_ksbj = new ArrayList<BSD_KuaiSuBaoJia_ety>();
    private List<BSD_WeiXiuYueYue_entiy> list_wxyy = new ArrayList<BSD_WeiXiuYueYue_entiy>();
    private List<BSD_WeiXiuJieDan_Entity> list_wxjd = new ArrayList<BSD_WeiXiuJieDan_Entity>();
    private WorkCheliangSm_Entity carEntity;
    private Kehu_Entity keHuEntity;
    private TextView tv_pinpai;
    private TextView tv_chexi;
    private TextView tv_chezu;
    private TextView tv_chexing;
    private String pinpai;
    private String chexi;
    private String chezu;
    private String chexing;
    private String che_cx;
    private PinpaiInfoDialog pinpaiInfoDialog;
    private LinearLayout ll_pinpai;
    private LinearLayout ll_chexi;
    private LinearLayout ll_chezu;
    private LinearLayout ll_chexing;
    private String cxbianhao;
    private String pinpaiming;
    //这是车系
    private List<Map<String, String>> listjbcx = new ArrayList<Map<String, String>>();
    private List<CustemObject> nameList = new ArrayList<CustemObject>();
    private AbstractSpinerAdapter mAdapter;
    private SpinerPopWindow mSpinerPopWindow;
    private String chexiid;
    //车组
    private List<Map<String, String>> listjbcz = new ArrayList<Map<String, String>>();
    private String chezuid;
    private List<CustemObject> nameList1 = new ArrayList<CustemObject>();
    private AbstractSpinerAdapter mAdapter1;
    private SpinerPopWindow mSpinerPopWindow1;
    //车型
    private List<Map<String, String>> listjbchexing = new ArrayList<Map<String, String>>();
    private String chexingid;
    private List<CustemObject> nameList2 = new ArrayList<CustemObject>();
    private AbstractSpinerAdapter mAdapter2;
    private SpinerPopWindow mSpinerPopWindow2;
    //时间日期
    private TimeDialog timeShow;
    private LinearLayout bsd_mrkx_rl_gcrq, bsd_mrkx_rl_cxbyrq, bsd_mrkx_rl_cxjcrq, bsd_mrkx_rl_jqxdq, bsd_mrkx_rl_syxdq;
    private TextView readData;
    TextView mv_bsd_cl_gcrq, mv_bsd_cl_xcbyrq, mv_bsd_cl_xcjcrq, mv_bsd_cl_jqxdq, mv_bsd_cl_syxdq;
    //根据VIN返回车型信息
    private List<Map<String, String>> listvincx = new ArrayList<>();
    private String cxnm;      //车型内码
    private URLS url;
    private View view;
    private ImageView isNewCar;
    private boolean isnew;
    private String paramBillType;
    private String paramBillNo;
    private String paramCheNo;

    /**
     * 初始化
     * @param cheNo    车牌号
     * @param billType  单据类型
     * @param billNo  维修单号
     * @return
     */
    public static BSD_MeiRongKuaiXiu_cheliangxinxi_Fragment newInstance(String cheNo, String billType, String billNo) {
        BSD_MeiRongKuaiXiu_cheliangxinxi_Fragment dialogFragment = new BSD_MeiRongKuaiXiu_cheliangxinxi_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_CHENO, cheNo);
        bundle.putString(PARAM_BILLTYPE, billType);
        bundle.putString(PARAM_BILLNO, billNo);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        paramBillType = getArguments().getString(PARAM_BILLTYPE);
        paramBillNo = getArguments().getString(PARAM_BILLNO);
        paramCheNo = getArguments().getString(PARAM_CHENO);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //去掉title栏
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.bsd_mrkx_clxx_fragment, null);
        timeShow = new TimeDialog(getActivity());
        initView();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //隐藏输入法
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = (int) getActivity().getResources().getDimension(R.dimen.qb_px_600);
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes(params);
    }

    public void initView() {
        url = new URLS();
        confirm = (TextView) view.findViewById(R.id.tv_confirm);
        mainActivity = (MainActivity) getActivity();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //调用保存车辆客户信息接口
                saveCarInfo();
            }
        });

        cancel = (TextView) view.findViewById(R.id.tv_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        et_cl_cp= (EditText) view.findViewById(R.id.et_cl_cp);
        cl_wxys = (EditText) view.findViewById(R.id.et_cl_wxys);
        cl_nf = (EditText) view.findViewById(R.id.et_cl_nf);
        cl_vin = (EditText) view.findViewById(R.id.et_cl_vin);
        kh_mc = (EditText) view.findViewById(R.id.et_kh_mc);
        kh_lxr = (EditText) view.findViewById(R.id.et_kh_lxr);
        kh_sj = (EditText) view.findViewById(R.id.et_kh_sj);
        kh_dh = (EditText) view.findViewById(R.id.et_kh_dh);
        //读取vin码
        readData = (TextView) view.findViewById(R.id.tv_readvin);
        readData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vin = cl_vin.getText().toString();
                if ("".equals(vin)) {
                    Toast.makeText(getActivity(), "请输入vin码", Toast.LENGTH_LONG).show();
                } else {
                    duVin();
                }
            }
        });
        //时间日期
        mv_bsd_cl_gcrq = (TextView) view.findViewById(R.id.mv_bsd_cl_gcrq);
        mv_bsd_cl_xcbyrq = (TextView) view.findViewById(R.id.mv_bsd_cl_xcbyrq);
        mv_bsd_cl_xcjcrq = (TextView) view.findViewById(R.id.mv_bsd_cl_xcjcrq);
        mv_bsd_cl_jqxdq = (TextView) view.findViewById(R.id.mv_bsd_cl_jqxdq);
        mv_bsd_cl_syxdq = (TextView) view.findViewById(R.id.mv_bsd_cl_syxdq);
        bsd_mrkx_rl_gcrq = (LinearLayout) view.findViewById(R.id.bsd_mrkx_rl_gcrq);
        bsd_mrkx_rl_cxbyrq = (LinearLayout) view.findViewById(R.id.bsd_mrkx_rl_cxbyrq);
        bsd_mrkx_rl_cxjcrq = (LinearLayout) view.findViewById(R.id.bsd_mrkx_rl_cxjcrq);
        bsd_mrkx_rl_jqxdq = (LinearLayout) view.findViewById(R.id.bsd_mrkx_rl_jqxdq);
        bsd_mrkx_rl_syxdq = (LinearLayout) view.findViewById(R.id.bsd_mrkx_rl_syxdq);
        bsd_mrkx_rl_gcrq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeShow.timePickerAlertDialog(mv_bsd_cl_gcrq);
            }
        });
        bsd_mrkx_rl_cxbyrq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeShow.timePickerAlertDialog(mv_bsd_cl_xcbyrq);
            }
        });
        bsd_mrkx_rl_cxjcrq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeShow.timePickerAlertDialog(mv_bsd_cl_xcjcrq);
            }
        });
        bsd_mrkx_rl_jqxdq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeShow.timePickerAlertDialog(mv_bsd_cl_jqxdq);
            }
        });
        bsd_mrkx_rl_syxdq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeShow.timePickerAlertDialog(mv_bsd_cl_syxdq);
            }
        });
        tv_pinpai = (TextView) view.findViewById(R.id.bsd_wxyy_top_pinpai);
        tv_chexi = (TextView) view.findViewById(R.id.bsd_wxyy_top_chexi);
        tv_chezu = (TextView) view.findViewById(R.id.bsd_wxyy_top_chezu);
        tv_chexing = (TextView) view.findViewById(R.id.bsd_wxyy_top_chexing);
        ll_chexi = (LinearLayout) view.findViewById(R.id.bsd_wxyy_rl_chexi);
        ll_chezu = (LinearLayout) view.findViewById(R.id.bsd_wxyy_rl_chezu);
        ll_chexing = (LinearLayout) view.findViewById(R.id.bsd_wxyy_cl_chexing);
        ll_pinpai = (LinearLayout) view.findViewById(R.id.bsd_wxyy_pp);
        //品牌弹框
        ll_pinpai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pinpaiInfoDialog = new PinpaiInfoDialog(getActivity());
                pinpaiInfoDialog.setToopromtOnClickListener(new PinpaiInfoDialog.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick(String chepainame, String bianhao) {
                        cxbianhao = bianhao;//车牌编号
                        pinpaiming = chepainame;//车牌名称
                        tv_pinpai.setText(chepainame);
                        tv_chexi.setText("");
                        tv_chezu.setText("");
                        tv_chexing.setText("");
                        pinpaiInfoDialog.dismiss();
                    }
                });
                pinpaiInfoDialog.show();
            }
        });
        // 车系
        ll_chexi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_pinpai.getText().toString().equals("")) {
                    Show.showTime(getActivity(), "请选择品牌");
                } else {
                    getCheXiInfo(tv_pinpai.getText().toString());
                }
            }
        });
        //车组
        ll_chezu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_chexi.getText().toString().equals("")) {
                    Show.showTime(getActivity(), "请选择车系");
                } else {
                    getCheZuInfo(tv_chexi.getText().toString());
                }
            }
        });
        //车型
        ll_chexing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_chezu.getText().toString().equals("")) {
                    Show.showTime(getActivity(), "请选择车组");
                } else {
                    getCheXingInfo(tv_chezu.getText().toString());
                }


            }
        });
        isNewCar = (ImageView) view.findViewById(R.id.iv_new);
        getCarInfo();
    }

    public void updateUI() {
        if (isnew) {
            isNewCar.setVisibility(View.VISIBLE);
        } else {
            isNewCar.setVisibility(View.INVISIBLE);
        }
        // 初始化车辆信息
        String cheCxs = carEntity.getChe_cx();
        String[] carInfos = cheCxs.split("\\|");
        if (carInfos.length >= 4) {
            tv_pinpai.setText(carInfos[0]);
            tv_chexi.setText(carInfos[1]);
            tv_chezu.setText(carInfos[2]);
            tv_chexing.setText(carInfos[3]);
        }
        et_cl_cp.setText(carEntity.getChe_no());
        cl_wxys.setText(carEntity.getChe_wxys());
        cl_nf.setText(carEntity.getChe_nf());
        cl_vin.setText(carEntity.getChe_vin());
        mv_bsd_cl_gcrq.setText(BsdUtil.dateToStr(carEntity.getChe_gcrq()));
        mv_bsd_cl_xcbyrq.setText(BsdUtil.dateToStr(carEntity.getChe_next_byrq()));
        mv_bsd_cl_xcjcrq.setText(BsdUtil.dateToStr(carEntity.getChe_jianche_dqrq()));
        mv_bsd_cl_jqxdq.setText(BsdUtil.dateToStr(carEntity.getChe_jiaoqx_dqrq()));
        mv_bsd_cl_syxdq.setText(BsdUtil.dateToStr(carEntity.getChe_shangyex_dqrq()));
        //初始化客户信息
        kh_mc.setText(keHuEntity.getKehu_mc());
        kh_lxr.setText(keHuEntity.getKehu_xm());
        kh_sj.setText(keHuEntity.getKehu_sj());
        kh_dh.setText(keHuEntity.getKehu_dh());
    }

    /**
     * 根据车牌返回车辆信息和客户信息
     */
    public void getCarInfo() {
        AbRequestParams params = new AbRequestParams();
        params.put("che_no", paramCheNo);
        Request.Post(MyApplication.shared.getString("ip", "")+url.BSD_wxjd_clandkh, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    if (jsonObject.get("status").toString().equals("1")) {
                        JSONObject object = jsonObject.getJSONObject("data");
                        //客户实体
                        JSONObject kehuObject = object.getJSONObject("kehu");
                        keHuEntity = JSON.parseObject(kehuObject.toString(), Kehu_Entity.class);
                        //车辆实体
                        JSONObject carObject = object.getJSONObject("cheliang");
                        carEntity = JSON.parseObject(carObject.toString(), WorkCheliangSm_Entity.class);
                        isnew = object.getBoolean("isnew");
                        updateUI();
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
                Log.e("mr", "onFailure方法");
            }
        });
    }

    /*
     * 保存车辆、客户信息
     */
    private void saveCarInfo() {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        pinpai = tv_pinpai.getText().toString();
        chexi = tv_chexi.getText().toString();
        chezu = tv_chezu.getText().toString();
        chexing = tv_chexing.getText().toString();
        if (TextUtils.isEmpty(pinpai) || TextUtils.isEmpty(chexi) ||
                TextUtils.isEmpty(chezu) || TextUtils.isEmpty(chexing)) {
            che_cx = "";
        } else {
            che_cx = pinpai + "|" + chexi + "|" + chezu + "|" + chexing;
        }
        final AbRequestParams params = new AbRequestParams();
        //车辆信息
        params.put("che_no", paramCheNo);
        params.put("che_wxys", cl_wxys.getText().toString()); //颜色
        params.put("che_cx", che_cx);//车型、车组、车系、车牌
        params.put("che_nf", cl_nf.getText().toString());//年份
        params.put("che_gcrq", mv_bsd_cl_gcrq.getText().toString());//购车日期
        params.put("che_next_byrq", mv_bsd_cl_xcbyrq.getText().toString());//保养到期
        params.put("che_jianche_dqrq", mv_bsd_cl_xcjcrq.getText().toString());//检车到期
        params.put("che_jiaoqx_dqrq", mv_bsd_cl_jqxdq.getText().toString());//交强险到期
        params.put("che_shangyex_dqrq", mv_bsd_cl_syxdq.getText().toString());//商业险到期
        params.put("che_vin", cl_vin.getText().toString());
        //客户信息
        params.put("kehu_mc", kh_mc.getText().toString());//客户名称
        params.put("kehu_xm", kh_lxr.getText().toString()); //客户联系人
        params.put("kehu_sj", kh_sj.getText().toString());  //手机
        params.put("kehu_dh", kh_dh.getText().toString());  //电话
        params.put("kehu_no", keHuEntity.getKehu_no()); // 把kehu_no也传过去，省的后台在查一次
        //公司编码
        params.put("GongSiNo",MyApplication.shared.getString("bsd_gs_id", ""));
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_MRKX_SAVE_cheKehu, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                WeiboDialogUtils.closeDialog(mWeiboDialog);
                showDraftBill();
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
//                Show.showTime(getActivity(), "网络请求超时");
                WeiboDialogUtils.closeDialog(mWeiboDialog);

            }
        });
    }

    /**
     * 显示草稿单据
     */
    private void showDraftBill() {
        switch (paramBillType) {
            case Conts.BILLTYPE_MRKX:
                data_mrkx(paramCheNo, paramBillNo);
                break;
            case Conts.BILLTYPE_KSBJ:
                data_ksbj(paramCheNo, paramBillNo);
                break;
            case Conts.BILLTYPE_WXDD:
                ((MainActivity) getActivity()).showWxddFragment(paramBillNo);
                break;
            case Conts.BILLTYPE_WXJD:
                data_wxjd(paramCheNo, paramBillNo);
                break;
            case Conts.BILLTYPE_WXYY:
                data_wxyy(paramCheNo, paramBillNo);
                break;
        }
        dismiss();
//        if ("mrkx".equals(paramBillType)) {
//            // 去获取草稿单据
//            data_mrkx(Conts.cp, billNo);
////            //打开美容快修单据
////            mainActivity.upBSD_mrkx();
//        }
//        if ("ksbj".equals(type)) {
//            //打开快速单据
//            data_ksbj(Conts.cp, view);
//        }
//        if ("wxyy".equals(type)) {
//            //打开维修预约单据
//            data_wxyy(Conts.cp, view);
//        }
//        if ("wxjd".equals(type)) {
//            //打开维修接待单据
//            data_wxjd(Conts.cp, view);
//        }
//        if ("wxywdd".equals(type)) {
//            Log.i("wxywdd", "走wxywdd了");
//            //打开维修业务调度单据
//            ((MainActivity) getActivity()).upwxywdd(view);
//        }
    }

    /*
     *根据车牌获取数据，打开美容快修单据
     */
    public void data_mrkx(final String cheNo, String billNo) {
        list.clear();
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        AbRequestParams params = new AbRequestParams();
        params.put("che_no", cheNo);
        params.put("gongsiNo", MyApplication.shared.getString("GongSiNo", ""));
        params.put("caozuoyuan_xm", MyApplication.shared.getString("name", ""));
        params.put("work_no", billNo);
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_MRKX_IN, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    // 如果查询成功就启动美容快修的fragment
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        mainActivity.showMrkxFragment(jsonObject.getJSONObject("data").toString());
                    } else {
                        Show.showTime(getActivity(), jsonObject.get("message").toString());
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
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });
    }

    /*
    *根据车牌获取数据，打开快速报价
    */
    public void data_ksbj(final String cardNo, String billNo) {
        list_ksbj.clear();
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        AbRequestParams params = new AbRequestParams();
        params.put("pai", cardNo);
        params.put("gongsiNo", MyApplication.shared.getString("GongSiNo", ""));
        params.put("caozuoyuan_xm", MyApplication.shared.getString("name", ""));
        params.put("list_no", billNo);
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_ksbj_jbxx, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        mainActivity.showKSBJFragment(data);
                    } else {
                        Show.showTime(getActivity(), jsonObject.get("message").toString());
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
                WeiboDialogUtils.closeDialog(mWeiboDialog);
                Show.showTime(getActivity(), "网路链接超时");
            }
        });

    }

    /*
    *根据车牌获取数据，打开维修预约
    */
    public void data_wxyy(final String cardNo, String billNo) {
        list_wxyy.clear();
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        AbRequestParams params = new AbRequestParams();
        params.put("che_no", cardNo);
        params.put("gongsiNo", MyApplication.shared.getString("GongSiNo", ""));
        params.put("caozuoyuan_xm", MyApplication.shared.getString("name", ""));
        params.put("yuyue_no", billNo);
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_wxyy_LieBiao, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int a, String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        mainActivity.showWxyyFragment(jsonObject.getString("data"));
                    } else {
                        Show.showTime(getActivity(), jsonObject.get("message").toString());
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
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });

    }

    /*
    *根据车牌获取数据，打开维修接待
    */
    public void data_wxjd(final String cheNo, String billNo) {
        list_wxjd.clear();
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        AbRequestParams params = new AbRequestParams();
        params.put("che_no", cheNo);
        params.put("gongsiNo", MyApplication.shared.getString("GongSiNo", ""));
        params.put("caozuoyuan_xm", MyApplication.shared.getString("name", ""));
        params.put("work_no", billNo);
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_wxjd_jbxx, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        mainActivity.showWxjdFragment(jsonObject.getJSONObject("data").toString());
                    } else {
                        Show.showTime(getActivity(), jsonObject.get("message").toString());
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
                Log.i("cjn", "失败");
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });
    }

    /*
     *根据vin码获取车辆名称、代码、内部名称；
     */
    public void duVin() {
        listvincx.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("vinCode", cl_vin.getText().toString());
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_getcxnm_byvin, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aa, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        Map<String, String> map;
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject item = jsonarray.getJSONObject(i);
                            map = new HashMap<>();
                            map.put("cxMcStd", item.getString("chex_mc_std"));   //车系
                            map.put("cxDm", item.getString("chex_dm"));     //车系代码
                            map.put("cxMc", item.getString("chex_mc"));   //车系名称
                            listvincx.add(map);
                        }
                        if (listvincx.size() == 1) {
                            //如果只查到一条记录，通过chex_dm查询相应的品牌、车系、车组、车型信息；
                            cxnm = listvincx.get(0).get("cxDm");      //车型内码
//                            cxmc=listvincx.get(0).get("cxMc");      //车型名称
//                            cxmcstd=listvincx.get(0).get("cxMcStd");   //车型内部名称；
                            getcx_by_cxdm();
                        } else if (listvincx.size() > 1) {
                            //如果查到多条记录，弹出对话框，显示chex_mc和chex_mc_std；
                            showDialogSelectCx();
                        }
                    }
                    if (jsonObject.get("message").toString().equals("查询失败")) {
                           Toast.makeText(getActivity(),jsonObject.getString("data").toString(),Toast.LENGTH_LONG).show();
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
                Log.e("vin", "onfailure失败了");
            }
        });


    }


    /*
    *根据vin码、车辆代码获取车牌车系车组车型信息
    */
    public void getcx_by_cxdm() {
        AbRequestParams params = new AbRequestParams();
        Log.e("sss", "cxnm是："+cxnm );
        params.put("chex_dm", cxnm);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_getcx_byvindm, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aa, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        String data = jsonObject.getString("data");
                        String[] cheXs = data.split("\\|");
                        if (cheXs.length >= 4) {
                            tv_pinpai.setText(cheXs[0]);
                            tv_chexi.setText(cheXs[1]);
                            tv_chezu.setText(cheXs[2]);
                            tv_chexing.setText(cheXs[3]);
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

    /*
     *vin对应多条车辆信息时，弹出选择对话框；
     */
    public void  showDialogSelectCx(){
        final View view=LayoutInflater.from(getActivity()).inflate(R.layout.bsd_clxx_dialog_for_select_cx,null);
        final Dialog dialog = new Dialog(getActivity());
        dialog.setTitle("请选择");
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = 900;
        params.height = 500 ;
        dialog.getWindow().setAttributes(params);
        ListView  lv= (ListView) window.findViewById(R.id.bsd_clxx_lv_for_select_cx);
        lv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return listvincx.size();
            }

            @Override
            public Object getItem(int position) {
                return listvincx.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
             View   layout= LayoutInflater.from(getActivity()).inflate(R.layout.select_cx_dialog_item,null);
             TextView   tv_mc= (TextView) layout.findViewById(R.id.tv_cxmc);
             TextView  tv_nbmc= (TextView) layout.findViewById(R.id.tv_cx_nbmc);
             tv_mc.setText(listvincx.get(position).get("cxMc"));     //车辆名称
             tv_nbmc.setText(listvincx.get(position).get("cxMcStd"));  //车辆内部名称
             layout.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     cxnm=listvincx.get(position).get("cxDm");    //车辆代码
                     dialog.dismiss();
                     getcx_by_cxdm();
                 }
             });
              return layout;
            }
        });
        dialog.show();
    }

    /**
     * 获取车型信息
     * @param chezuid
     */
    public void getCheXingInfo(String chezuid) {
        listjbchexing.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("dm", chezuid);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_Chexing, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aa, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject item = jsonarray.getJSONObject(i);
                            Map<String, String> map = new HashMap<String, String>();
                            if (item.getString("chex_mc_std").length() > 1) {
                                String a = item.getString("chex_mc") + "【" + item.getString("chex_mc_std") + "】";
                                map.put("chex_dm", a);
                                map.put("chex_mc", a);
                                map.put("chex_bz", item.getString("chex_bz"));
                                map.put("chex_mc_std", item.getString("chex_mc_std"));
                            } else {
                                map.put("chex_dm", item.getString("chex_dm"));
                                map.put("chex_mc", item.getString("chex_mc"));
                                map.put("chex_bz", item.getString("chex_bz"));
                                map.put("chex_mc_std", item.getString("chex_mc_std"));
                            }
                            listjbchexing.add(map);
                        }
                    }
                    updateCheXingData();
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
     * 更新车型的数据
     */
    public void updateCheXingData() {
        nameList2.clear();
        for (int i = 0; i < listjbchexing.size(); i++) {
            CustemObject object = new CustemObject();
            object.data = listjbchexing.get(i).get("chex_mc");
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
                if (!tv_chexing.getText().toString().equals(value)) {
                    tv_chexing.setText(value);
                    chexingid = listjbchexing.get(pos).get("chex_bz");
                    if (tv_pinpai.getText().toString().equals("") ||
                            tv_chexi.getText().toString().equals("") ||
                            tv_chezu.getText().toString().equals("") ||
                            tv_chexing.getText().toString().equals("")) {
                        che_cx = "";
                    } else {
                        che_cx = pinpai + "|" + chexi + "|" + chezu + "|" + chexing;
                        Conts.chexing = che_cx;
                    }
                }
            }
        });
        mSpinerPopWindow2.setWidth(ll_chexing.getWidth());
        mSpinerPopWindow2.showAsDropDown(ll_chexing);
    }

    /**
     * 更新车系的数据
     */
    public void updateCheXiData() {
        nameList.clear();
        for (int i = 0; i < listjbcx.size(); i++) {
            CustemObject object = new CustemObject();
            object.data = listjbcx.get(i).get("chex_mc");
            nameList.add(object);
        }
        mAdapter = new CustemSpinerAdapter(getActivity());
        mAdapter.refreshData(nameList, 0);
        mSpinerPopWindow = new SpinerPopWindow(getActivity());
        mSpinerPopWindow.setAdatper(mAdapter, 310);
        mSpinerPopWindow.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
            @Override
            public void onItemClick(int pos) {
                String value = nameList.get(pos).toString();
                if (!tv_chexi.getText().toString().equals(value)) {
                    tv_chexi.setText(value);
                    chexiid = listjbcx.get(pos).get("chex_dm");
                    tv_chezu.setText("");
                    tv_chexing.setText("");
                }
            }
        });
        // 显示出来
        mSpinerPopWindow.setWidth(ll_chexi.getWidth());
        mSpinerPopWindow.showAsDropDown(ll_chexi);
    }

    /**
     * 基本信息车组接口
     */
    public void getCheZuInfo(String chexiid) {
        listjbcz.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("dm", chexiid);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_CZ, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aa, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject item = jsonarray.getJSONObject(i);
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("chex_dm", item.getString("chex_dm"));
                            map.put("chex_mc", item.getString("chex_mc"));
                            listjbcz.add(map);
                        }
                    }
                    updateCheZuData();
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
     * 更新车组的数据
     */
    public void updateCheZuData() {
        nameList1.clear();
        for (int i = 0; i < listjbcz.size(); i++) {
            CustemObject object = new CustemObject();
            object.data = listjbcz.get(i).get("chex_mc");
            nameList1.add(object);
        }
        mAdapter1 = new CustemSpinerAdapter(getActivity());
        mAdapter1.refreshData(nameList1, 0);
        mSpinerPopWindow1 = new SpinerPopWindow(getActivity());
        mSpinerPopWindow1.setAdatper(mAdapter1, 310);
        mSpinerPopWindow1.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
            @Override
            public void onItemClick(int pos) {
                String value = nameList1.get(pos).toString();
                if (!tv_chezu.getText().toString().equals(value)) {
                    chezuid = listjbcz.get(pos).get("chex_dm");
                    tv_chezu.setText(value);
                    tv_chexing.setText("");
                }
            }
        });
        mSpinerPopWindow1.setWidth(ll_chezu.getWidth());
        mSpinerPopWindow1.showAsDropDown(ll_chezu);
    }

    /**
     * 基本信息车系接口
     */
    public void getCheXiInfo(String cxbianhao) {
        listjbcx.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("dm", cxbianhao);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_CX, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aa, String s) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(s);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject item = jsonarray.getJSONObject(i);
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("chex_dm", item.getString("chex_dm"));
                            map.put("chex_mc", item.getString("chex_mc"));
                            listjbcx.add(map);
                        }
                    }
                    updateCheXiData();
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