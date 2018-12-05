package com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.example.administrator.boshide2.Modular.Fragment.BaoYangChaXun.BSD_BaoYangChaXun_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.BaseFragment;
import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.Adapter.BSD_wxxm_adp;
import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.Adapter.BSD_xzcl_adp;
import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.Entity.BSD_KuaiSuBaoJia_CL_entity;
import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.Entity.BSD_KuaiSuBaoJia_XM_entity;
import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.Entity.BSD_KuaiSuBaoJia_ety;
import com.example.administrator.boshide2.Modular.Fragment.PinpaiInfoDialog;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.dialogFragment.BSD_LiShiWeiXiuJianYi_DialogFragment;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.dialogFragment.BSD_LishiWeiXiu_DialogFragment;
import com.example.administrator.boshide2.Modular.Fragment.KuCunDialogFragment;
import com.example.administrator.boshide2.Modular.Fragment.CheliangxinxiDialogFragment;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.fagmt.BSD_ZCDUXQ_CL_POP;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.fagmt.BSD_ZCDUXQ_XM_POP;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.Pop_Entity.BSD_wxyy_cl_pop_entity;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.Pop_Entity.BSD_wxyy_xm_pop_entiy;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.UpdateItemInfoDialog;
import com.example.administrator.boshide2.Modular.View.SpinerPopWindow;
import com.example.administrator.boshide2.Modular.View.Time.TimeDialog;
import com.example.administrator.boshide2.Modular.View.diaog.QueRen;
import com.example.administrator.boshide2.Modular.View.diaog.Queding_Quxiao;
import com.example.administrator.boshide2.Modular.View.diaog.TooPromptdiaog;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.DownJianPan;
import com.example.administrator.boshide2.Tools.QuanQuan.WeiboDialogUtils;
import com.example.administrator.boshide2.Tools.Show;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @快速报价碎片页 Created by Administrator on 2017-4-13.
 */
public class BSD_kuaisubaojia_Fragment extends BaseFragment {
    private static final String PARAM_KEY = "param_key";
    private RelativeLayout beijing;
    private TextView tv_wxxm_money, tv_wxcl_money;//维修项目金额，维修材料金额
    private LinearLayout rea_pinpai;
    private Dialog mWeiboDialog;
    private TextView bsd_ksbj_cp;
    private TextView bsd_ksbj_lc, bsd_ksbj_cezhu, bsd_ksbj_dh, bsd_ksbj_vin, tv_pinpai, tv_chexi,
            tv_chezu, tv_chexing, tv_zong_money;
    private List<BSD_KuaiSuBaoJia_XM_entity> listXM = new ArrayList<BSD_KuaiSuBaoJia_XM_entity>();
    private BSD_wxxm_adp adpxm;
    private ListView listViewXM;//维修项目
    private List<BSD_KuaiSuBaoJia_CL_entity> listCL = new ArrayList<BSD_KuaiSuBaoJia_CL_entity>();
    private BSD_xzcl_adp adpcl;//车系适配器
    private ListView listViewCL;//维修材料
    private TextView bsd_ksbj_cd;
    private TextView bsd_ksbj_jc;
    private QueRen queRen;
    private PinpaiInfoDialog pinpaiInfoDialog;
    private String cxbianhao;
    private String pinpaiming;
    //这是车系
    private LinearLayout bsd_ksbj_rl_cx;
    private List<Map<String, String>> listjbcx = new ArrayList<Map<String, String>>();
    private List<CustemObject> nameList = new ArrayList<CustemObject>();
    private AbstractSpinerAdapter mAdapter;
    private SpinerPopWindow mSpinerPopWindow;
    String chexiid;
    //车组
    private LinearLayout bsd_ksbj_rl_cz;
    private List<Map<String, String>> listjbcz = new ArrayList<Map<String, String>>();
    private String chezuid;
    private List<CustemObject> nameList1 = new ArrayList<CustemObject>();
    private AbstractSpinerAdapter mAdapter1;
    private SpinerPopWindow mSpinerPopWindow1;
    //车型
    private LinearLayout bsd_ksbj_chexing;
    private List<Map<String, String>> listjbchexing = new ArrayList<Map<String, String>>();
    private String chexingid;
    private List<CustemObject> nameList2 = new ArrayList<CustemObject>();
    private AbstractSpinerAdapter mAdapter2;
    private SpinerPopWindow mSpinerPopWindow2;
    //车辆信息、历史维修、历史维修建议
    private TextView bsd_ksbj_clxx;
    private TextView bsd_ksbj_lswxjy;
    private TextView bsd_ksbj_lswx;
    //工时费率
    private LinearLayout bsd_ksbj_rl_gsfl;
    private TextView bsd_ksbj_tv_gsfl;
    private List<Map<String, String>> listgslv = new ArrayList<Map<String, String>>();
    private List<CustemObject> nameList3 = new ArrayList<CustemObject>();
    private AbstractSpinerAdapter mAdapter3;
    private SpinerPopWindow mSpinerPopWindow3;
    private String gongshifeili_name;
    private String gongshifeili_id;
    private LinearLayout bsd_ksbj_rl_gcsj;//日期选择
    private TextView bsd_ksbj_tv_gcsj;
    private TextView bsd_ksbj_rl_bycx;//保养查询
    private Queding_Quxiao queRen_quxiao;
    private TimeDialog timeShow;
    //根据VIN返回车型信息
    private List<Map<String, String>> listvincx = new ArrayList<>();
    private String cxnm;      //车型内码
    private TextView rl_duqu;
    private TextView title;
    private TextView footerText;
    private String param;
    private BSD_KuaiSuBaoJia_ety billEntiy;
    private TextView tv_billNo;
    private LinearLayout ll_back;
    private TextView addWxxm;
    private BSD_ZCDUXQ_XM_POP bsd_zcduxq_xm_pop;
    private TextView addWxcl;
    private BSD_ZCDUXQ_CL_POP bsd_zcduxq_cl_pop;
    private TooPromptdiaog promptdiaog;
    private double xmZje;
    private double clZje;
    private double totalJe;
    private UpdateItemInfoDialog updateItemInfoDialog;
    private TextView tv_wxcl_count;
    private TextView tv_wxxm_count;

    public static BSD_kuaisubaojia_Fragment newInstance(String params) {
        BSD_kuaisubaojia_Fragment fragment = new BSD_kuaisubaojia_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_KEY, params);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        param = getArguments().getString(PARAM_KEY);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.bsd_ksbj_fragment;
    }

    @Override
    public void initView() {
        beijing = (RelativeLayout) getActivity().findViewById(R.id.beijing);
        timeShow = new TimeDialog(getActivity());
        bsd_ksbj_tv_gcsj = (TextView) view.findViewById(R.id.bsd_ksbj_tv_gcsj);
        bsd_ksbj_rl_gcsj = (LinearLayout) view.findViewById(R.id.bsd_ksbj_rl_gcsj);
        bsd_ksbj_rl_gcsj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeShow.timePickerAlertDialog(bsd_ksbj_tv_gcsj);
            }
        });
        //工时费率
        bsd_ksbj_rl_gsfl = (LinearLayout) view.findViewById(R.id.bsd_ksbj_rl_gsfl);
        bsd_ksbj_rl_gsfl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getGSFLdata();
            }
        });
        bsd_ksbj_tv_gsfl = (TextView) view.findViewById(R.id.bsd_ksbj_tv_gsfl);
        tv_zong_money = (TextView) view.findViewById(R.id.tv_zong_money);
        // 存档
        bsd_ksbj_cd = (TextView) view.findViewById(R.id.bsd_ksbj_cd);
        bsd_ksbj_cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveBillInfo(true, false);
            }
        });
        // 进厂
        bsd_ksbj_jc = (TextView) view.findViewById(R.id.bsd_ksbj_jc);
        bsd_ksbj_jc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveBillInfo(false, false);
            }
        });

        //维修项目
        listViewXM = (ListView) view.findViewById(R.id.bsd_lv);
        adpxm = new BSD_wxxm_adp(getActivity(), listXM);
        adpxm.setOnOperateItemListener(new BSD_wxxm_adp.OnOperateItemListener() {
            @Override
            public void onDelete(final String wxxmNo, final int position) {
                promptdiaog = new TooPromptdiaog(getContext(), "是否删除");
                promptdiaog.setToopromtOnClickListener(new TooPromptdiaog.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick() {
                        deleteWxxm(wxxmNo, position);
                    }
                });
                promptdiaog.show();
            }

            @Override
            public void onUpdateGS(final String wxxmNo, final double wxxmGs, final String wxxmMc, final int position) {
                updateItemInfoDialog = new UpdateItemInfoDialog(getActivity(), UpdateItemInfoDialog.CHANGE_WXXMBZGS, wxxmGs, wxxmMc);
                updateItemInfoDialog.show();
                updateItemInfoDialog.setToopromtOnClickListener(new UpdateItemInfoDialog.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick(double gongshif) {
                        updateWxxmGs(wxxmNo, gongshif, position);
                    }
                });
            }

            @Override
            public void onUpdateJe(final String wxxmNo, double wxxmje, String wxxmMc, final int position) {
                updateItemInfoDialog = new UpdateItemInfoDialog(getActivity(), UpdateItemInfoDialog.CHANGE_WXXMGS, wxxmje, wxxmMc);
                updateItemInfoDialog.show();
                updateItemInfoDialog.setToopromtOnClickListener(new UpdateItemInfoDialog.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick(double gongshif) {
                        updateWxxmJE(wxxmNo, gongshif, position);
                    }
                });
            }
        });
        listViewXM.setAdapter(adpxm);
        //维修材料
        listViewCL = (ListView) view.findViewById(R.id.bsd_lv1);
        adpcl = new BSD_xzcl_adp(getActivity(), listCL);
        adpcl.setOnOperateItemListener(new BSD_xzcl_adp.OnOperateItemListener() {
            @Override
            public void onDelete(final String peijNo, final int position) {
                promptdiaog = new TooPromptdiaog(getContext(), "是否删除");
                promptdiaog.setToopromtOnClickListener(new TooPromptdiaog.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick() {
                        deleteWxcl(peijNo, position);
                    }
                });
                promptdiaog.show();
            }

            @Override
            public void onUpdateSL(final String peijNo, double peijSL, String peijMc, final int position) {
                updateItemInfoDialog = new UpdateItemInfoDialog(getActivity(), UpdateItemInfoDialog.CHANGE_PEIJSL, peijSL, peijMc);
                updateItemInfoDialog.show();
                updateItemInfoDialog.setToopromtOnClickListener(new UpdateItemInfoDialog.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick(double gongshif) {
                        updateWxclSL(peijNo, gongshif, position);
                    }
                });
            }

            @Override
            public void onUpdateDj(final String peijNo, double peijDj, String peijMc, final int position) {
                updateItemInfoDialog = new UpdateItemInfoDialog(getActivity(), UpdateItemInfoDialog.CHANGE_PEIJDJ, peijDj, peijMc);
                updateItemInfoDialog.show();
                updateItemInfoDialog.setToopromtOnClickListener(new UpdateItemInfoDialog.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick(double gongshif) {
                        updateWxclDJ(peijNo, gongshif, position);
                    }
                });
            }

            @Override
            public void onSearchStock(String peij_no) {
                KuCunDialogFragment kcDialog = KuCunDialogFragment.newInstance(peij_no);
                kcDialog.show(getFragmentManager(), "kcDialog");
            }
        });
        listViewCL.setAdapter(adpcl);
        bsd_ksbj_cp = (TextView) view.findViewById(R.id.bsd_ksbj_cp);
        bsd_ksbj_lc = (TextView) view.findViewById(R.id.bsd_ksbj_lc);
        bsd_ksbj_cezhu = (TextView) view.findViewById(R.id.bsd_ksbj_cezhu);
        bsd_ksbj_dh = (TextView) view.findViewById(R.id.bsd_ksbj_dh);
        bsd_ksbj_vin = (TextView) view.findViewById(R.id.bsd_ksbj_vin);
        //读取vin码
        rl_duqu = (TextView) view.findViewById(R.id.tv_readvin);
        rl_duqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(bsd_ksbj_vin.getText().toString())) {
                    Toast.makeText(getActivity(), "请输入vin码", Toast.LENGTH_LONG).show();
                } else {
                    duVin();
                }
            }
        });

        tv_pinpai = (TextView) view.findViewById(R.id.bsd_ksbj_pp);
        tv_chexi = (TextView) view.findViewById(R.id.bsd_ksbj_cx);
        tv_chezu = (TextView) view.findViewById(R.id.bsd_ksbj_cz);
        tv_chexing = (TextView) view.findViewById(R.id.bsd_ksbj_cxing);
        tv_wxxm_money = (TextView) view.findViewById(R.id.tv_wxxm_money);
        tv_wxcl_money = (TextView) view.findViewById(R.id.tv_wxcl_money);
        rea_pinpai = (LinearLayout) view.findViewById(R.id.rea_pinpai);
        bsd_ksbj_rl_cx = (LinearLayout) view.findViewById(R.id.bsd_ksbj_rl_cx);
        bsd_ksbj_rl_cz = (LinearLayout) view.findViewById(R.id.bsd_ksbj_rl_cz);
        bsd_ksbj_chexing = (LinearLayout) view.findViewById(R.id.bsd_ksbj_chexing);
        //品牌监听，弹出框
        rea_pinpai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pinpaiInfoDialog = new PinpaiInfoDialog(getActivity());
                pinpaiInfoDialog.setToopromtOnClickListener(new PinpaiInfoDialog.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick(String aa, String bianhao) {
                        cxbianhao = bianhao;//车牌编号
                        pinpaiming = aa;//车牌名称
                        tv_pinpai.setText(pinpaiming);
                        tv_chexi.setText("");
                        tv_chezu.setText("");
                        tv_chexing.setText("");
                        pinpaiInfoDialog.dismiss();
                    }
                });
                pinpaiInfoDialog.show();
            }
        });
        //车系
        bsd_ksbj_rl_cx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_pinpai.getText().toString().equals("")) {
                    Show.showTime(getActivity(), "请选择品牌");
                } else {
                    getCheXiData(tv_pinpai.getText().toString());
                }
            }
        });
        //车组
        bsd_ksbj_rl_cz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_chexi.getText().toString().equals("")) {
                    Show.showTime(getActivity(), "请选择车系");
                } else {
                    getCheZuData(tv_chexi.getText().toString());
                }
            }
        });
        //车行
        bsd_ksbj_chexing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_chezu.getText().toString().equals("")) {
                    Show.showTime(getActivity(), "请选择车组");
                } else {
                    getCheXingData(tv_chezu.getText().toString());
                }
            }
        });

        bsd_ksbj_rl_bycx = (TextView) view.findViewById(R.id.bsd_ksbj_tv_bycx);
        bsd_ksbj_rl_bycx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_chexing.getText().toString().equals("") ||
                        bsd_ksbj_lc.getText().toString().equals("") ||
                        bsd_ksbj_tv_gcsj.getText().toString().equals("")) {
                    Show.showTime(getActivity(), "请输入完整信息");
                } else {
                    getBYCXData();
                }
            }
        });

        //车辆信息、历史维修、历史维修建议
        bsd_ksbj_clxx = (TextView) view.findViewById(R.id.bsd_ksbj_clxx);
        bsd_ksbj_clxx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheliangxinxiDialogFragment.newInstance(billEntiy.getChe_no(), Conts.BILLTYPE_KSBJ, billEntiy.getList_no())
                        .show(getFragmentManager(), "dialog_fragment");

            }
        });
        bsd_ksbj_lswx = (TextView) view.findViewById(R.id.bsd_ksbj_lswx);
        bsd_ksbj_lswx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BSD_LishiWeiXiu_DialogFragment.newInstance(billEntiy.getChe_no()).show(getFragmentManager(), "mrkx_lswx");
            }
        });
        bsd_ksbj_lswxjy = (TextView) view.findViewById(R.id.bsd_ksbj_lswxjy);
        bsd_ksbj_lswxjy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BSD_LiShiWeiXiuJianYi_DialogFragment().
                        show(getFragmentManager(), "mrkx_lswxjy");
            }
        });
        title = (TextView) view.findViewById(R.id.tv_title);
        footerText = (TextView) view.findViewById(R.id.tv_footertext);
        tv_billNo = (TextView) view.findViewById(R.id.tv_billNo);
        // 返回
        ll_back = (LinearLayout) view.findViewById(R.id.bsd_lsbj_fanhui);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveBillInfo(true, true);
            }
        });
        // 添加项目
        addWxxm = (TextView) view.findViewById(R.id.tv_add_wxxm);
        addWxxm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWxxm();
            }
        });
        // 添加用料
        addWxcl = (TextView) view.findViewById(R.id.tv_add_wxcl);
        addWxcl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWxcl();
            }
        });
        tv_wxxm_count = (TextView) view.findViewById(R.id.tv_wxxm_count);
        tv_wxcl_count = (TextView) view.findViewById(R.id.tv_wxcl_count);
        bsd_ksbj_vin.setFocusable(true);
        bsd_ksbj_vin.setFocusableInTouchMode(true);
        bsd_ksbj_vin.requestFocus();
    }

    private void updateWxclDJ(String peijNo, final double gongshif, final int position) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "更新中...");
        AbRequestParams params = new AbRequestParams();
        params.put("list_no", billEntiy.getList_no());
        params.put("peij_no", peijNo);
        params.put("dj", gongshif + "");
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_ksbj_upcldj, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                listCL.get(position).setPeij_dj(gongshif);
                listCL.get(position).setPeij_je(gongshif * listCL.get(position).getPeij_sl());
                int firstVisiblePosition = listViewCL.getFirstVisiblePosition();
                adpcl.notifyDataSetChanged();
                listViewCL.setSelection(firstVisiblePosition);
                wxclPrice();
                updateItemInfoDialog.dismiss();
                DownJianPan.DJP(getContext());
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
                Toast.makeText(getContext(), "修改失败", Toast.LENGTH_SHORT).show();
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });
    }

    private void updateWxclSL(String peijNo, final double gongshif, final int position) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "更新中...");
        AbRequestParams params = new AbRequestParams();
        params.put("list_no", billEntiy.getList_no());
        params.put("peij_no", peijNo);
        params.put("sl", gongshif + "");
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_ksbj_upclsl, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                listCL.get(position).setPeij_sl(gongshif);
                listCL.get(position).setPeij_je(gongshif * listCL.get(position).getPeij_dj());
                int firstVisiblePosition = listViewCL.getFirstVisiblePosition();
                adpcl.notifyDataSetChanged();
                listViewCL.setSelection(firstVisiblePosition);
                wxclPrice();
                updateItemInfoDialog.dismiss();
                DownJianPan.DJP(getContext());
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
                Toast.makeText(getContext(), "修改失败", Toast.LENGTH_SHORT).show();
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });
    }

    private void deleteWxcl(String peijNo, final int position) {
        AbRequestParams params = new AbRequestParams();
        params.put("list_no", billEntiy.getList_no());
        params.put("peij_no", peijNo);
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_ksbj_deletCL, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                listCL.remove(position);
                adpxm.notifyDataSetChanged();
                wxclPrice();
                promptdiaog.dismiss();
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                Toast.makeText(getContext(), "删除失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateWxxmJE(String wxxmNo, final double gongshif, final int position) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "更新中...");
        AbRequestParams params = new AbRequestParams();
        params.put("list_no", billEntiy.getList_no());
        params.put("wxxm_no", wxxmNo);
        params.put("je", gongshif + "");
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_ksbj_upxmje, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                listXM.get(position).setWxxm_je(gongshif);
                if (listXM.get(position).getWxxm_gs() == 0) {
                    listXM.get(position).setWxxm_dj(gongshif);
                } else {
                    listXM.get(position).setWxxm_dj(gongshif / listXM.get(position).getWxxm_gs());
                }
                int firstVisiblePosition = listViewXM.getFirstVisiblePosition();
                adpxm.notifyDataSetChanged();
                listViewXM.setSelection(firstVisiblePosition);
                wxxmPrice();
                updateItemInfoDialog.dismiss();
                DownJianPan.DJP(getContext());
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
                Toast.makeText(getContext(), "修改失败", Toast.LENGTH_SHORT).show();
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });
    }

    private void updateWxxmGs(String wxxmNo, final double gongshi, final int position) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "更新中...");
        AbRequestParams params = new AbRequestParams();
        params.put("list_no", billEntiy.getList_no());
        params.put("wxxm_no", wxxmNo);
        params.put("gs", gongshi + "");
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_ksbj_upxmgs, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                listXM.get(position).setWxxm_gs(gongshi);
                listXM.get(position).setWxxm_je(gongshi * listXM.get(position).getWxxm_dj());
                int firstVisiblePosition = listViewXM.getFirstVisiblePosition();
                adpxm.notifyDataSetChanged();
                listViewXM.setSelection(firstVisiblePosition);
                wxxmPrice();
                updateItemInfoDialog.dismiss();
                DownJianPan.DJP(getContext());
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
                Toast.makeText(getContext(), "修改失败", Toast.LENGTH_SHORT).show();
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });
    }

    private void deleteWxxm(String wxxmNo, final int position) {
        AbRequestParams params = new AbRequestParams();
        params.put("list_no", billEntiy.getList_no());
        params.put("wxxm_no", wxxmNo);
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_ksbj_deletXM, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                listXM.remove(position);
                adpxm.notifyDataSetChanged();
                wxxmPrice();
                promptdiaog.dismiss();
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                Toast.makeText(getContext(), "删除失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void wxxmPrice() {
        double jg = 0;
        for (int i = 0; i < listXM.size(); i++) {
            jg = jg + listXM.get(i).getWxxm_je();
        }
        xmZje = jg;
        tv_wxxm_money.setText(jg + "元");
        tv_wxxm_count.setText("(共" + listXM.size() + "条记录)");
        zoongjia();
    }

    private void showWxcl() {
        bsd_zcduxq_cl_pop = new BSD_ZCDUXQ_CL_POP(getActivity());
        bsd_zcduxq_cl_pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        List<BSD_wxyy_cl_pop_entity> tempLists = new ArrayList<>();
        if (listCL != null) {
            for (BSD_KuaiSuBaoJia_CL_entity item : listCL) {
                BSD_wxyy_cl_pop_entity tempItem = new BSD_wxyy_cl_pop_entity();
                tempItem.setPeij_no(item.getPeij_no());
                tempItem.setPeij_sl(item.getPeij_sl());
                tempItem.setPeij_ydj(item.getPeij_dj());
                tempLists.add(tempItem);
            }
        }
        bsd_zcduxq_cl_pop.setTempLists(tempLists);
        bsd_zcduxq_cl_pop.setKehuNo(billEntiy.getKehu_no());
        bsd_zcduxq_cl_pop.setCheNo(billEntiy.getChe_no());
        bsd_zcduxq_cl_pop.gb(new BSD_ZCDUXQ_CL_POP.Guanbi() {
            @Override
            public void onGuanBi(List<BSD_wxyy_cl_pop_entity> tempList) {
                List<BSD_KuaiSuBaoJia_CL_entity> needAddList = new ArrayList<>();
                // 循环返回的数据，找不需要新添加的
                for (BSD_wxyy_cl_pop_entity item : tempList) {
                    boolean hasAdd = false;
                    double addPeijSl = item.getPeij_sl();
                    for (BSD_KuaiSuBaoJia_CL_entity listCLItem : listCL) {
                        if (listCLItem.getPeij_no().equals(item.getPeij_no())) {
                            if (listCLItem.getPeij_sl() == item.getPeij_sl()) {
                                // 如果相等，则表示存在，直接退出，否则继续循环
                                hasAdd = true;
                            } else {
                                addPeijSl = item.getPeij_sl() - listCLItem.getPeij_sl();
                            }
                            break;
                        }
                    }
                    // 如果没有找到，则是需要添加的集合
                    if (!hasAdd) {
                        BSD_KuaiSuBaoJia_CL_entity wxclItem = new BSD_KuaiSuBaoJia_CL_entity();
                        wxclItem.setList_no(billEntiy.getList_no());
                        wxclItem.setPeij_no(item.getPeij_no());
                        wxclItem.setPeij_mc(item.getPeij_mc());
                        wxclItem.setPeij_sl(addPeijSl);
                        wxclItem.setPeij_dj(item.getPeij_ydj());
                        wxclItem.setPeij_je(addPeijSl * item.getPeij_ydj());
                        wxclItem.setPeij_dw(item.getPeij_dw());
                        wxclItem.setPeij_th(item.getPeij_th());
                        wxclItem.setPeij_zt("正常");
                        needAddList.add(wxclItem);
                    }
                }
                // 请求接口，把数据添加到数据库中
                if (needAddList.size() > 0) {
                    saveNewWxclToDB(needAddList);
                }
            }
        });
        bsd_zcduxq_cl_pop.showAtLocation(beijing, Gravity.TOP, 0, 0);
    }

    private void saveNewWxclToDB(final List<BSD_KuaiSuBaoJia_CL_entity> needAddList) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "保存中...");
        AbRequestParams params = new AbRequestParams();
        Object json = JSON.toJSON(needAddList);
        params.put("addLists", json.toString());
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_ksbj_newcl, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                // 保存成功
                if (data.equals("success")) {
                    boolean isAdd = true;
                    for (BSD_KuaiSuBaoJia_CL_entity item : needAddList) {
                        for (BSD_KuaiSuBaoJia_CL_entity listClItem : listCL) {
                            if (listClItem.getPeij_no().equals(item.getPeij_no())) {
                                isAdd = false;
                                listClItem.setPeij_sl(listClItem.getPeij_sl() + item.getPeij_sl());
                                listClItem.setPeij_je(listClItem.getPeij_je() + item.getPeij_je());
                                break;
                            }
                        }
                        if (isAdd) {
                            listCL.add(item);
                        }
                    }
                    // 循环遍历一下集合，删除数量为0的
                    Iterator<BSD_KuaiSuBaoJia_CL_entity> iterator = listCL.iterator();
                    while (iterator.hasNext()) {
                        BSD_KuaiSuBaoJia_CL_entity next = iterator.next();
                        if (next.getPeij_sl() == 0) {
                            iterator.remove();
                        }
                    }
                    adpcl.notifyDataSetChanged();
                    wxclPrice();
                }
                bsd_zcduxq_cl_pop.dismiss();
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

    private void showWxxm() {
        bsd_zcduxq_xm_pop = new BSD_ZCDUXQ_XM_POP(getActivity());
        bsd_zcduxq_xm_pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        // 将当前已经添加的维修项目，放到popwindow的临时表中，表示这些项目已经添加
        List<BSD_wxyy_xm_pop_entiy> tempLists = new ArrayList<>();
        if (listXM != null) {
            for (BSD_KuaiSuBaoJia_XM_entity item : listXM) {
                BSD_wxyy_xm_pop_entiy tempItem = new BSD_wxyy_xm_pop_entiy();
                tempItem.setWxxm_no(item.getWxxm_no());
                tempLists.add(tempItem);
            }
        }
        bsd_zcduxq_xm_pop.setTempLists(tempLists);
        bsd_zcduxq_xm_pop.setCheCx(billEntiy.getChe_cx());
        bsd_zcduxq_xm_pop.setCheNo(billEntiy.getChe_no());
        bsd_zcduxq_xm_pop.setCheFl(billEntiy.getList_sfbz());
        bsd_zcduxq_xm_pop.setWorkNo(billEntiy.getWork_no());
        // 设置点击返回的回调
        bsd_zcduxq_xm_pop.setOnGuanbiListener(new BSD_ZCDUXQ_XM_POP.OnGuanbiListener() {
            @Override
            public void onGuanBi(List<BSD_wxyy_xm_pop_entiy> tempList) {
                List<BSD_KuaiSuBaoJia_XM_entity> needAddList = new ArrayList<>();
                // 循环返回的数据，找不需要新添加的
                for (BSD_wxyy_xm_pop_entiy item : tempList) {
                    boolean hasAdd = false;
                    for (BSD_KuaiSuBaoJia_XM_entity listXMItem : listXM) {
                        if (listXMItem.getWxxm_no().equals(item.getWxxm_no())) {
                            // 如果相等，则表示存在，直接退出，否则继续循环
                            hasAdd = true;
                            break;
                        }
                    }
                    // 如果没有找到，则是需要添加的集合
                    if (!hasAdd) {
                        BSD_KuaiSuBaoJia_XM_entity wxxmItem = new BSD_KuaiSuBaoJia_XM_entity();
                        wxxmItem.setList_no(billEntiy.getList_no());
                        wxxmItem.setWxxm_no(item.getWxxm_no());
                        wxxmItem.setWxxm_mc(item.getWxxm_mc());
                        wxxmItem.setWxxm_gs(item.getWxxm_gs());
                        wxxmItem.setWxxm_dj(item.getWxxm_dj());
                        wxxmItem.setWxxm_cb(item.getWxxm_cb());
                        wxxmItem.setWxxm_je(item.getWxxm_zddj());
                        wxxmItem.setWxxm_zt("正常");
                        needAddList.add(wxxmItem);
                    }
                }
                // 请求接口，把数据添加到数据库中
                if (needAddList.size() > 0) {
                    saveNewWxxmToDB(needAddList);
                }
            }
        });
        bsd_zcduxq_xm_pop.showAtLocation(beijing, Gravity.TOP, 0, 0);
    }

    private void saveNewWxxmToDB(final List<BSD_KuaiSuBaoJia_XM_entity> needAddList) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "保存中...");
        AbRequestParams params = new AbRequestParams();
        Object json = JSON.toJSON(needAddList);
        params.put("addLists", json.toString());
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_ksbj_newxm, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                // 保存成功
                if (data.equals("success")) {
                    // 1.将新维修项目添加到work_ll_gz的集合中;
                    listXM.addAll(needAddList);
                    adpxm.notifyDataSetChanged();
                    wxxmPrice();
                }
                bsd_zcduxq_xm_pop.dismiss();
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

    @Override
    public void initData() {
        title.setText("快速报价");
        footerText.setText("公司名称 :   " + MyApplication.shared.getString("GongSiMc", "") +
                "                  公司电话 :   " + MyApplication.shared.getString("danw_dh", ""));
        getBillInfoFromParam();
        updateBillInfo();
        getWxxmData();
        getWxclData();
    }

    private void updateBillInfo() {
        tv_billNo.setText(billEntiy.getList_no());
        bsd_ksbj_cp.setText(billEntiy.getChe_no());
        String che = billEntiy.getChe_cx();
        String[] cheCxs = che.split("\\|");
        if (cheCxs.length >= 4) {
            tv_pinpai.setText(cheCxs[0]);
            tv_chexi.setText(cheCxs[1]);
            tv_chezu.setText(cheCxs[2]);
            tv_chexing.setText(cheCxs[3]);
        }
        if (TextUtils.isEmpty(billEntiy.getChe_vin()) || billEntiy.getChe_vin().length() < 2) {
            bsd_ksbj_vin.setText(Conts.VIN);
        } else {
            bsd_ksbj_vin.setText(billEntiy.getChe_vin());
        }
        bsd_ksbj_lc.setText("" + billEntiy.getList_lc());
        bsd_ksbj_cezhu.setText(billEntiy.getKehu_mc());
        bsd_ksbj_dh.setText(billEntiy.getKehu_dh());
        bsd_ksbj_tv_gsfl.setText(billEntiy.getList_sfbz());
        bsd_ksbj_tv_gcsj.setText(billEntiy.getGcsj());
        gongshifeili_name = billEntiy.getList_sfbz();
        gongshifeili_id = billEntiy.getList_gdfl();
    }

    private void getBillInfoFromParam() {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(param);
            JSONObject item = jsonObject.getJSONObject("data");
            billEntiy = new BSD_KuaiSuBaoJia_ety();
            billEntiy.setList_no(item.getString("list_no"));
            billEntiy.setList_sfbz(item.getString("List_sfbz"));
            billEntiy.setList_sffl(item.getDouble("List_sffl"));
            billEntiy.setKehu_no(item.getString("kehu_no"));
            billEntiy.setKehu_mc(item.getString("kehu_mc"));
            billEntiy.setKehu_xm(item.getString("kehu_xm"));
            billEntiy.setKehu_dh(item.getString("kehu_dh"));
            billEntiy.setChe_no(item.getString("che_no"));
            billEntiy.setChe_vin(item.getString("che_vin"));
            billEntiy.setChe_cx(item.getString("che_cx"));
            billEntiy.setList_czy(item.getString("List_czy"));
            billEntiy.setGongSiNo(item.getString("GongSiNo"));
            billEntiy.setGongSiMc(item.getString("GongSiMc"));
            billEntiy.setWork_no(item.getString("work_no"));
            billEntiy.setList_jlrq(item.getString("List_jlrq"));
            billEntiy.setList_yjjclc(item.getInt("List_yjjclc"));
            billEntiy.setList_hjje(item.getDouble("List_hjje"));
            billEntiy.setGcsj(item.getString("che_gcrq"));
            billEntiy.setList_lc(Double.parseDouble(item.getString("List_lc")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 材料拉数据
     */
    public void getWxclData() {
        listCL.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("list_no", billEntiy.getList_no());
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_ksbj_wxcl, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aa, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject item = jsonarray.getJSONObject(i);
                            BSD_KuaiSuBaoJia_CL_entity entity = new BSD_KuaiSuBaoJia_CL_entity();
                            entity.setReco_no(item.getDouble("reco_no"));
                            entity.setList_no(item.getString("list_no"));
                            entity.setPeij_no(item.getString("peij_no"));
                            entity.setPeij_th(item.getString("peij_th"));
                            entity.setPeij_mc(item.getString("peij_mc"));
                            entity.setPeij_dw(item.getString("peij_dw"));
                            entity.setPeij_sl(item.getDouble("peij_sl"));
                            entity.setPeij_dj(item.getDouble("peij_dj"));
                            entity.setPeij_je(item.getDouble("peij_je"));
                            entity.setPeij_jk(item.getString("peij_jk"));
                            entity.setPeij_zt(item.getString("peij_zt"));
                            listCL.add(entity);
                        }
                    }
                    wxclPrice();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adpcl.notifyDataSetChanged();
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
     * 工时费率数据
     */
    public void updateGSFLData() {
        nameList3.clear();
        for (int i = 0; i < listgslv.size(); i++) {
            CustemObject object = new CustemObject();
            object.data = listgslv.get(i).get("feil_mc");
            nameList3.add(object);
        }
        mAdapter3 = new CustemSpinerAdapter(getActivity());
        mAdapter3.refreshData(nameList3, 0);
        mSpinerPopWindow3 = new SpinerPopWindow(getActivity());
        mSpinerPopWindow3.setAdatper(mAdapter3, 310);
        mSpinerPopWindow3.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
            @Override
            public void onItemClick(int pos) {
                String clickValue = nameList3.get(pos).toString();
                if (!bsd_ksbj_tv_gsfl.getText().toString().equals(clickValue)) {
                    bsd_ksbj_tv_gsfl.setText(clickValue);
                    gongshifeili_name = listgslv.get(pos).get("feil_mc");
                    gongshifeili_id = listgslv.get(pos).get("feil_fl");
                }
            }
        });
        mSpinerPopWindow3.setWidth(bsd_ksbj_rl_gsfl.getWidth());
        mSpinerPopWindow3.showAsDropDown(bsd_ksbj_rl_gsfl);
    }

    public void getGSFLdata() {
        listgslv.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("type", "ITGongShi");
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_HYGL_ADD_TYPE, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aa, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("status").toString().equals("1")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject item = jsonarray.getJSONObject(i);
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("id", item.getString("id"));
                            map.put("feil_mc", item.getString("feil_mc"));
                            map.put("feil_fl", item.getString("feil_fl"));
                            map.put("feil_sy", item.getString("feil_sy"));
                            listgslv.add(map);
                        }
                    }
                    updateGSFLData();
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
     * 项目拉数据
     */
    public void getWxxmData() {
        listXM.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("list_no", billEntiy.getList_no());
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_ksbj_wxxm, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aa, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject item = jsonarray.getJSONObject(i);
                            BSD_KuaiSuBaoJia_XM_entity entity = new BSD_KuaiSuBaoJia_XM_entity();
                            entity.setReco_no(item.getDouble("reco_no"));
                            entity.setList_no(item.getString("list_no"));
                            entity.setWxxm_no(item.getString("wxxm_no"));
                            entity.setWxxm_mc(item.getString("wxxm_mc"));
                            entity.setWxxm_gs(item.getDouble("wxxm_gs"));
                            entity.setWxxm_khgs(item.getDouble("wxxm_khgs"));
                            entity.setWxxm_cb(item.getDouble("wxxm_cb"));
                            entity.setWxxm_je(item.getDouble("wxxm_je"));
                            entity.setWxxm_yje(item.getDouble("wxxm_yje"));
                            entity.setWxxm_mxcx(item.getString("wxxm_mxcx"));
                            entity.setWxxm_zt(item.getString("wxxm_zt"));
                            entity.setWxxm_bz(item.getString("wxxm_bz"));
                            entity.setWxxm_dj(item.getDouble("wxxm_dj"));
                            listXM.add(entity);
                        }
                    }
                    wxxmPrice();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adpxm.notifyDataSetChanged();
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                Show.showTime(getActivity(), "网络连接超时");
            }
        });
    }


    /*
     *根据vin码获取车辆名称、代码、内部名称；
     */
    public void duVin() {
        listvincx.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("vinCode", bsd_ksbj_vin.getText().toString());
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_getcxnm_byvin, params, new AbStringHttpResponseListener() {
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
                            Log.e("vins", "dm:" + item.getString("chex_dm"));
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
                        Toast.makeText(getActivity(), jsonObject.getString("data").toString(), Toast.LENGTH_LONG).show();
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
        params.put("chex_dm", cxnm);
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_getcx_byvindm, params, new AbStringHttpResponseListener() {
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
    public void showDialogSelectCx() {
        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.bsd_clxx_dialog_for_select_cx, null);
        final Dialog dialog = new Dialog(getActivity());
        dialog.setTitle("请选择");
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params =
                dialog.getWindow().getAttributes();
        params.width = 900;
        params.height = 500;
        dialog.getWindow().setAttributes(params);
        ListView lv = (ListView) window.findViewById(R.id.bsd_clxx_lv_for_select_cx);
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
                View layout = LayoutInflater.from(getActivity()).inflate(R.layout.select_cx_dialog_item, null);
                TextView tv_mc = (TextView) layout.findViewById(R.id.tv_cxmc);
                TextView tv_nbmc = (TextView) layout.findViewById(R.id.tv_cx_nbmc);
                tv_mc.setText(listvincx.get(position).get("cxMc"));     //车辆名称
                tv_nbmc.setText(listvincx.get(position).get("cxMcStd"));  //车辆内部名称
                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        cxnm = listvincx.get(position).get("cxDm");    //车辆代码
                        getcx_by_cxdm();
                    }
                });
                return layout;
            }
        });
        dialog.show();
    }

    //车型
    public void getCheXingData(String chezuid) {
        listjbchexing.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("dm", chezuid);
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_Chexing, params, new AbStringHttpResponseListener() {
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
                            } else {
                                map.put("chex_dm", item.getString("chex_dm"));
                                map.put("chex_mc", item.getString("chex_mc"));
                                map.put("chex_bz", item.getString("chex_bz"));
                            }
                            listjbchexing.add(map);
                        }
                        updateCheXingData();
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

    /**
     * 基本信息车型
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
                }
            }
        });
        mSpinerPopWindow2.setWidth(bsd_ksbj_chexing.getWidth());
        mSpinerPopWindow2.showAsDropDown(bsd_ksbj_chexing);
    }

    /**
     * 基本信息车组接口
     */
    public void getCheZuData(String chexiid) {
        listjbcz.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("dm", chexiid);
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_CZ, params, new AbStringHttpResponseListener() {
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
                        updateCheZuData();
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

    /**
     * 基本信息车组接口
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
                    tv_chezu.setText(value);
                    tv_chexing.setText("");
                    chezuid = listjbcz.get(pos).get("chex_dm");
                }
            }
        });
        mSpinerPopWindow1.setWidth(bsd_ksbj_rl_cz.getWidth());
        mSpinerPopWindow1.showAsDropDown(bsd_ksbj_rl_cz);
    }

    /**
     * 基本信息车系接口
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
        mSpinerPopWindow.setWidth(bsd_ksbj_rl_cx.getWidth());
        mSpinerPopWindow.showAsDropDown(bsd_ksbj_rl_cx);
    }

    /**
     * 基本信息车系接口
     */
    public void getCheXiData(String cxbianhao) {
        listjbcx.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("dm", cxbianhao);
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_CX, params, new AbStringHttpResponseListener() {
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
                        updateCheXiData();
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

    /**
     * 计算维修材料的总价
     */
    public void wxclPrice() {
        double jg = 0;
        for (int i = 0; i < listCL.size(); i++) {
            jg = jg + (listCL.get(i).getPeij_dj() * listCL.get(i).getPeij_sl());
        }
        clZje = jg;
        tv_wxcl_money.setText(jg + "元");
        tv_wxcl_count.setText("(共" + listCL.size() + "条记录)");
        zoongjia();
    }

    public void zoongjia() {
        totalJe = xmZje + clZje;
        tv_zong_money.setText(totalJe + "");
    }

    /**
     * 保存单据信息
     */
    private void saveBillInfo(final boolean onlySave, final boolean isBack) {
        if (onlySave) {
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "正在保存...");
        } else {
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "正在进厂...");
        }
        String che_cx = "";
        if (!TextUtils.isEmpty(tv_pinpai.getText().toString()) &&
                !TextUtils.isEmpty(tv_chexi.getText().toString()) &&
                !TextUtils.isEmpty(tv_chezu.getText().toString()) &&
                !TextUtils.isEmpty(tv_chexing.getText().toString())) {
            che_cx = tv_pinpai.getText().toString() + "|" +
                    tv_chexi.getText().toString() + "|" +
                    tv_chezu.getText().toString() + "|" +
                    tv_chexing.getText().toString();
        }
        AbRequestParams params = new AbRequestParams();
        params.put("che_no", billEntiy.getChe_no());
        params.put("list_no", billEntiy.getList_no());//预约单号
        params.put("che_cx", che_cx);//品牌，车系，车组，车行，che_cx
        params.put("che_vin", bsd_ksbj_vin.getText().toString());//VIN码
        params.put("List_lc", bsd_ksbj_lc.getText().toString());//进厂里程
        params.put("List_yjjclc", bsd_ksbj_lc.getText().toString());//进厂里程
        params.put("kehu_mc", bsd_ksbj_cezhu.getText().toString());//客户司机
        params.put("kehu_dh", bsd_ksbj_dh.getText().toString());//手机号
        params.put("List_sfbz", gongshifeili_name);
        params.put("List_sffl", gongshifeili_id);
        params.put("kehu_no", billEntiy.getKehu_no());//客户名称
        params.put("List_hjje", tv_zong_money.getText().toString());
        params.put("gcsj", bsd_ksbj_tv_gcsj.getText().toString());
        params.put("List_state", "1");
        params.put("List_progress", "未进店");
        params.put("List_gj_wx", xmZje + "");
        params.put("List_gj_ll", clZje + "");
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_ksbj_bcjbxx, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        //点击存档操作的时候走的步骤
                        if (onlySave) {
                            if (!isBack) {  // 如果不是返回，则显示提示信息
                                queRen = new QueRen(getHostActicity(), "存档成功");
                                queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                                    @Override
                                    public void onYesClick() {
                                        queRen.dismiss();
                                    }
                                });
                                queRen.show();
                            } else {
                                ((MainActivity) getActivity()).upBSD_KSBJ_Log();
                            }

                        } else {
                            jinChang();
                        }
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
                Show.showTime(getActivity(), "网络请求超时");
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });
    }

    //进厂操作
    public void jinChang() {
        AbRequestParams params = new AbRequestParams();
        params.put("list_no", billEntiy.getList_no());
        params.put("gongsiNo", MyApplication.shared.getString("bsd_gs_id", ""));
        params.put("caozuoyuan_xm", MyApplication.shared.getString("bsd_user_name", ""));
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_ksbj_jc, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                WeiboDialogUtils.closeDialog(mWeiboDialog);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(data);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        final String work_no = jsonObject.getString("data");
                        queRen_quxiao = new Queding_Quxiao(getActivity(), "进厂成功，是否进入调度页面");
                        queRen_quxiao.show();
                        queRen_quxiao.setOnResultClickListener(new Queding_Quxiao.OnResultClickListener() {
                            @Override
                            public void onConfirm() {
                                queRen_quxiao.dismiss();
                                ((MainActivity) getActivity()).showWxddFragment(work_no);
                            }

                            @Override
                            public void onCancel() {
                                queRen_quxiao.dismiss();
                                ((MainActivity) getActivity()).upBSD_KSBJ_Log();
                            }
                        });
                    } else {
                        queRen = new QueRen(getHostActicity(), jsonObject.getString("data"));
                        queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                            @Override
                            public void onYesClick() {
                                queRen.dismiss();
                            }
                        });
                        queRen.show();
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
                WeiboDialogUtils.closeDialog(mWeiboDialog);
                Toast.makeText(getHostActicity(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getBYCXData() {
        AbRequestParams params = new AbRequestParams();
        params.put("quanMing", tv_chexing.getText().toString());
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_chexing_fansuan, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String getCar = jsonObject.getString("data");
                    int licheng = (int) Double.parseDouble(bsd_ksbj_lc.getText().toString().trim());
                    String time = bsd_ksbj_tv_gcsj.getText().toString().trim();
                    BSD_BaoYangChaXun_Fragment.newInstance(getCar, "" + licheng, time)
                            .show(getFragmentManager(), "bycxdialog");
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
