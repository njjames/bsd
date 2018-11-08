package com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan;

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
import android.widget.EditText;
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
import com.example.administrator.boshide2.Modular.Fragment.PinpaiInfoDialog;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.dialogFragment.BSD_LiShiWeiXiuJianYi_DialogFragment;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.dialogFragment.BSD_LishiWeiXiu_DialogFragment;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.dialogFragment.BSD_MeiRongKuaiXiu_KuCun_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.dialogFragment.BSD_MeiRongKuaiXiu_cheliangxinxi_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Adapter.BSD_WXJD_CL_adp;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Adapter.BSD_WXJD_XM_adp;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_CL_Entity;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_Entity;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_XM_Entity;
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
 * @维修接单碎片页 Created by Administrator on 2017-4-13.
 */
public class BSD_WeiXiuJieDan_Fragment extends BaseFragment implements View.OnClickListener {
    private static final String PARAM_KEY = "param_key";
    RelativeLayout beijing;
    private TextView bsd_wxjd_tv_chepai;//车牌
    private EditText et_jclc;//进厂里程
    private EditText et_kehumc;//车主
    private EditText et_shouji;//手机
    private EditText et_vin;//VIN码
    private TextView tv_pinpai;//品牌
    private TextView tv_chexi;//车系
    private TextView tv_chezu;//车组
    private TextView tv_chexing;//车型
    private EditText bsd_wxjd_et_qiankuan;//欠款
    private EditText et_cardno;//会员卡号
    private TextView tv_wxjd_clhj;
    private TextView tv_wxjd_xmhj;
    private TextView tv_wxjd_hj;//项目金额。材料金额，总金额
    private ListView listViewXM;//维修项目
    private ListView listViewCL;//维修材料
    private BSD_WXJD_XM_adp adp_xm;
    private BSD_WXJD_CL_adp adp_cl;
    private List<BSD_WeiXiuJieDan_XM_Entity> list_XM = new ArrayList<>();
    private List<BSD_WeiXiuJieDan_CL_Entity> list_CL = new ArrayList<>();
    private TextView bsd_wxjd_save;
    private TextView bsd_wxjd_jc;
    private Dialog mWeiboDialog;
    private QueRen queRen;
    private LinearLayout ll_pinpai;
    private LinearLayout ll_chexi;
    private LinearLayout ll_chezu;
    private LinearLayout ll_chexing;
    //品牌 车系车组 车行
    private PinpaiInfoDialog pinpaiInfoDialog;
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
    //工时费率
    private LinearLayout ll_gsfl;
    private TextView tv_gsfl;
    private List<Map<String, String>> listgslv = new ArrayList<Map<String, String>>();
    private List<CustemObject> nameList3 = new ArrayList<CustemObject>();
    private AbstractSpinerAdapter mAdapter3;
    private SpinerPopWindow mSpinerPopWindow3;
    private String gongshifeili_name;
    private String gongshifeili_id;
    private LinearLayout ll_gcsj;
    private TextView bsd_wxjd_rl_bycx;
    private TextView tv_gcsj;
    private EditText et_niankuan;
    private EditText et_cunyou;
    private EditText et_color;
    private EditText et_miaoshu;
    private TimeDialog timePickerShow;
    private Queding_Quxiao queding_quxiao;
    private URLS url;
    //根据VIN返回车型信息
    private List<Map<String, String>> listvincx = new ArrayList<>();
    private String cxnm;      //车型内码
    private TextView rl_duqu;
    //车辆信息、历史维修、历史维修建议
    private TextView bsd_wxjd_clxx;
    private TextView bsd_wxjd_lswxjy;
    private TextView bsd_wxjd_lswx;
    private String params;
    private BSD_WeiXiuJieDan_Entity billEntiy;
    private TextView title;
    private TextView footerText;
    private TextView billNo;
    private TextView tv_addWxxm;
    private TextView tv_addWxcl;
    private BSD_ZCDUXQ_XM_POP bsd_zcduxq_xm_pop;
    private double xmZje;
    private double clZje;
    private double totalJe;
    private TextView tv_wxxm_count;
    private TextView tv_wxcl_count;
    private TooPromptdiaog promptdiaog;
    private UpdateItemInfoDialog updateItemInfoDialog;
    private LinearLayout bsd_lsbj_fanhui;
    private BSD_ZCDUXQ_CL_POP bsd_zcduxq_cl_pop;

    public static BSD_WeiXiuJieDan_Fragment newInstance(String params) {
        BSD_WeiXiuJieDan_Fragment fragment = new BSD_WeiXiuJieDan_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_KEY, params);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        params = getArguments().getString(PARAM_KEY);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.bsd_wxjd_fragment;
    }

    @Override
    public void initView() {
        bsd_lsbj_fanhui = (LinearLayout) view.findViewById(R.id.bsd_lsbj_fanhui);
        bsd_lsbj_fanhui.setOnClickListener(this);
        tv_gcsj = (TextView) view.findViewById(R.id.tv_gcsj);
        ll_gcsj = (LinearLayout) view.findViewById(R.id.ll_gcsj);
        ll_gcsj.setOnClickListener(this);
        rl_duqu = (TextView) view.findViewById(R.id.tv_readvin);
        rl_duqu.setOnClickListener(this);
        bsd_wxjd_clxx = (TextView) view.findViewById(R.id.bsd_wxjd_clxx);
        bsd_wxjd_clxx.setOnClickListener(this);
        bsd_wxjd_lswx = (TextView) view.findViewById(R.id.bsd_wxjd_lswx);
        bsd_wxjd_lswx.setOnClickListener(this);
        bsd_wxjd_lswxjy = (TextView) view.findViewById(R.id.bsd_wxjd_lswxjy);
        bsd_wxjd_lswxjy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BSD_LiShiWeiXiuJianYi_DialogFragment().
                        show(getFragmentManager(), "mrkx_lswxjy");
            }
        });
        bsd_wxjd_jc = (TextView) view.findViewById(R.id.bsd_wxjd_jc);
        bsd_wxjd_jc.setOnClickListener(this);
        et_niankuan = (EditText) view.findViewById(R.id.et_niankuan);
        et_cunyou = (EditText) view.findViewById(R.id.et_cunyou);
        et_color = (EditText) view.findViewById(R.id.et_color);
        et_miaoshu = (EditText) view.findViewById(R.id.et_miaoshu);
        ll_gsfl = (LinearLayout) view.findViewById(R.id.ll_gsfl);
        tv_gsfl = (TextView) view.findViewById(R.id.tv_gsfl);
        ll_gsfl.setOnClickListener(this);
        bsd_wxjd_save = (TextView) view.findViewById(R.id.bsd_wxjd_save);
        bsd_wxjd_save.setOnClickListener(this);
        bsd_wxjd_tv_chepai = (TextView) view.findViewById(R.id.bsd_wxjd_tv_chepai);
        et_jclc = (EditText) view.findViewById(R.id.et_jinchanglicheng);
        et_kehumc = (EditText) view.findViewById(R.id.et_kehumc);
        et_shouji = (EditText) view.findViewById(R.id.et_shouji);
        et_vin = (EditText) view.findViewById(R.id.et_vin);
        tv_pinpai = (TextView) view.findViewById(R.id.tv_pinpai);
        tv_chexi = (TextView) view.findViewById(R.id.tv_chexi);
        tv_chezu = (TextView) view.findViewById(R.id.tv_chezu);
        tv_chexing = (TextView) view.findViewById(R.id.tv_chexing);
        et_cardno = (EditText) view.findViewById(R.id.et_cardno);
        tv_wxjd_clhj = (TextView) view.findViewById(R.id.tv_wxjd_clhj);
        tv_wxjd_hj = (TextView) view.findViewById(R.id.tv_wxjd_hj);
        tv_wxjd_xmhj = (TextView) view.findViewById(R.id.tv_wxjd_xmhj);
        ll_pinpai = (LinearLayout) view.findViewById(R.id.ll_pinpai);
        ll_chexi = (LinearLayout) view.findViewById(R.id.ll_chexi);
        ll_chezu = (LinearLayout) view.findViewById(R.id.ll_chezu);
        ll_chexing = (LinearLayout) view.findViewById(R.id.ll_chexing);
        tv_addWxxm = (TextView) view.findViewById(R.id.tv_add_wxxm);
        tv_addWxcl = (TextView) view.findViewById(R.id.tv_add_wxcl);
        tv_addWxxm.setOnClickListener(this);
        tv_addWxcl.setOnClickListener(this);
        tv_wxxm_count = (TextView) view.findViewById(R.id.tv_wxxm_count);
        tv_wxcl_count = (TextView) view.findViewById(R.id.tv_wxcl_count);

        listViewXM = (ListView) view.findViewById(R.id.lv_wxxm);
        listViewCL = (ListView) view.findViewById(R.id.lv_wxcl);
        adp_cl = new BSD_WXJD_CL_adp(getContext(), list_CL);
        adp_cl.setOnOperateItemListener(new BSD_WXJD_CL_adp.OnOperateItemListener() {
            @Override
            public void onDelete(final int position) {
                promptdiaog = new TooPromptdiaog(getContext(), "确定删除吗？");
                promptdiaog.setToopromtOnClickListener(new TooPromptdiaog.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick() {
                        deletWxcl(position);
                    }
                });
                promptdiaog.show();
            }

            @Override
            public void onUpdateSL(final int position) {
                updateItemInfoDialog = new UpdateItemInfoDialog(getActivity(), UpdateItemInfoDialog.CHANGE_PEIJSL,
                        list_CL.get(position).getPeij_sl(), list_CL.get(position).getPeij_mc());
                updateItemInfoDialog.show();
                updateItemInfoDialog.setToopromtOnClickListener(new UpdateItemInfoDialog.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick(double newPeijSl) {
                        updatePeijSl(newPeijSl, position);
                    }
                });
            }

            @Override
            public void onUpdateDj(final int position) {
                updateItemInfoDialog = new UpdateItemInfoDialog(getActivity(), UpdateItemInfoDialog.CHANGE_PEIJDJ,
                        list_CL.get(position).getPeij_ydj(), list_CL.get(position).getPeij_mc());
                updateItemInfoDialog.show();
                updateItemInfoDialog.setToopromtOnClickListener(new UpdateItemInfoDialog.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick(double newPeijYdj) {
                        updatePeijYdj(newPeijYdj, position);
                    }
                });
            }

            @Override
            public void onSearchStock(String peij_no) {
                BSD_MeiRongKuaiXiu_KuCun_Fragment.newInstance(peij_no).show(getFragmentManager(), "kcDialog");
            }
        });
        listViewCL.setAdapter(adp_cl);
        adp_xm = new BSD_WXJD_XM_adp(getActivity(), list_XM);
        adp_xm.setOnOperateItemListener(new BSD_WXJD_XM_adp.OnOperateItemListener() {
            @Override
            public void onPaiGong(String wxxmNo) {

            }

            @Override
            public void onDelete(final int position) {
                promptdiaog = new TooPromptdiaog(getContext(), "确定删除吗？");
                promptdiaog.setToopromtOnClickListener(new TooPromptdiaog.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick() {
                        deleteWxxm(position);
                    }
                });
                promptdiaog.show();
            }

            @Override
            public void onUpdateYgsf(final int position) {
                updateItemInfoDialog = new UpdateItemInfoDialog(getActivity(), UpdateItemInfoDialog.CHANGE_WXXMGS, list_XM.get(position).getWxxm_yje(), list_XM.get(position).getWxxm_mc());
                updateItemInfoDialog.show();
                updateItemInfoDialog.setToopromtOnClickListener(new UpdateItemInfoDialog.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick(double gongshif) {
                        updateWxxmGsf(gongshif, position);
                    }
                });
            }

            @Override
            public void onUpdateWxxmMc(final int position) {
                updateItemInfoDialog = new UpdateItemInfoDialog(getActivity(), UpdateItemInfoDialog.CHANGE_WXXMNAME, 0, list_XM.get(position).getWxxm_mc());
                updateItemInfoDialog.show();
                updateItemInfoDialog.setToopromtXmmc(new UpdateItemInfoDialog.ToopromtXmmc() {
                    @Override
                    public void onYesClick(String newWxxmMc) {
                        updateWxxmMC(newWxxmMc, position);
                    }
                });
            }
        });
        listViewXM.setAdapter(adp_xm);
        beijing = (RelativeLayout) getActivity().findViewById(R.id.beijing);
        ll_pinpai.setOnClickListener(this);
        ll_chexi.setOnClickListener(this);
        ll_chezu.setOnClickListener(this);
        ll_chexing.setOnClickListener(this);
        bsd_wxjd_rl_bycx = (TextView) view.findViewById(R.id.bsd_wxjd_rl_bycx);
        bsd_wxjd_rl_bycx.setOnClickListener(this);
        title = (TextView) view.findViewById(R.id.tv_title);
        footerText = (TextView) view.findViewById(R.id.tv_footertext);
        billNo = (TextView) view.findViewById(R.id.tv_billNo);
    }

    private void updateWxxmGsf(final double gongshif, final int position) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "更新中...");
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", list_XM.get(position).getWork_no());
        params.put("wxxm_no", list_XM.get(position).getWxxm_no());
        params.put("jg", gongshif + "");
        params.put("hyzk", "1"); // 这个参数目前也没有用
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_mrkx_upxm, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                list_XM.get(position).setWxxm_yje(gongshif);
                list_XM.get(position).setWxxm_je(gongshif);
                if (list_XM.get(position).getWxxm_gs() == 0) {
                    list_XM.get(position).setWxxm_dj(gongshif);
                } else {
                    list_XM.get(position).setWxxm_dj(gongshif / list_XM.get(position).getWxxm_gs());
                }
                int firstVisiblePosition = listViewXM.getFirstVisiblePosition();
                adp_xm.notifyDataSetChanged();
                listViewXM.setSelection(firstVisiblePosition);
                wxxmPrice();
                updateItemInfoDialog.dismiss();
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

    private void updateWxxmMC(final String newWxxmMc, final int position) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "更新中...");
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", list_XM.get(position).getWork_no());
        params.put("wxxm_no", list_XM.get(position).getWxxm_no());
        params.put("wxxm_mc", newWxxmMc);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_Update_WxxmMc, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                list_XM.get(position).setWxxm_mc(newWxxmMc);
                int firstVisiblePosition = listViewXM.getFirstVisiblePosition();
                adp_xm.notifyDataSetChanged();
                listViewXM.setSelection(firstVisiblePosition);
                updateItemInfoDialog.dismiss();
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

    private void updatePeijYdj(final double newPeijYdj, final int position) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "更新中...");
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", billEntiy.getWork_no());
        params.put("peij_no", list_CL.get(position).getPeij_no());
        params.put("ydj", newPeijYdj + "");
        params.put("zk", Conts.MRKX_CL_ZK + "");
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_update_peijydj, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                list_CL.get(position).setPeij_ydj(newPeijYdj);
                list_CL.get(position).setPeij_yje(newPeijYdj * list_CL.get(position).getPeij_sl());
                list_CL.get(position).setPeij_dj(newPeijYdj * Conts.MRKX_CL_ZK);
                list_CL.get(position).setPeij_je(newPeijYdj * Conts.MRKX_CL_ZK * list_CL.get(position).getPeij_sl());
                int firstVisiblePosition = listViewCL.getFirstVisiblePosition();
                adp_cl.notifyDataSetChanged();
                listViewCL.setSelection(firstVisiblePosition);
                wxclPrice();
                updateItemInfoDialog.dismiss();
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

    private void updatePeijSl(final double newPeijSl, final int position) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "更新中...");
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", billEntiy.getWork_no());
        params.put("peij_no", list_CL.get(position).getPeij_no());
        params.put("sl", newPeijSl + "");
        params.put("zk", 1 + "");
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_meirongxiugai, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                list_CL.get(position).setPeij_sl(newPeijSl);
                list_CL.get(position).setPeij_yje(newPeijSl * list_CL.get(position).getPeij_ydj());
                list_CL.get(position).setPeij_dj(Conts.MRKX_CL_ZK * list_CL.get(position).getPeij_ydj());
                list_CL.get(position).setPeij_je(newPeijSl * Conts.MRKX_CL_ZK * list_CL.get(position).getPeij_ydj());
                int firstVisiblePosition = listViewCL.getFirstVisiblePosition();
                adp_cl.notifyDataSetChanged();
                listViewCL.setSelection(firstVisiblePosition);
                wxclPrice();
                updateItemInfoDialog.dismiss();
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

    private void deletWxcl(final int position) {
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", billEntiy.getWork_no());
        params.put("peij_no", list_CL.get(position).getPeij_no());
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_deletCL, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int a, String s) {
                list_CL.remove(position);
                adp_cl.notifyDataSetChanged();
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
                Log.i("cjn", "失败" + s);
            }
        });
    }

    private void deleteWxxm(final int position) {
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", list_XM.get(position).getWork_no());
        params.put("wxxm_no", list_XM.get(position).getWxxm_no());
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_deletXM, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                list_XM.remove(position);
                adp_xm.notifyDataSetChanged();
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


    @Override
    public void initData() {
        url = new URLS();
        title.setText("接待登记");
        footerText.setText("公司名称 :   " + MyApplication.shared.getString("GongSiMc", "") +
                "                  公司电话 :   " + MyApplication.shared.getString("danw_dh", ""));
        timePickerShow = new TimeDialog(getActivity());
        getBillInfoFromParam();
        updateBillInfoUI();
        xmdata();
        cldata();
    }

    private void updateBillInfoUI() {
        billNo.setText(billEntiy.getWork_no());
        bsd_wxjd_tv_chepai.setText(billEntiy.getChe_no());
        String cheCx = billEntiy.getChe_cx();
        String[] cheCxs = cheCx.split("\\|");
        if (cheCxs.length >= 4) {
            tv_pinpai.setText(cheCxs[0]);
            tv_chexi.setText(cheCxs[1]);
            tv_chezu.setText(cheCxs[2]);
            tv_chexing.setText(cheCxs[3]);
        }
        if (TextUtils.isEmpty(billEntiy.getChe_vin()) || billEntiy.getChe_vin().length() < 2) {
            et_vin.setText(Conts.VIN);
        } else {
            et_vin.setText(billEntiy.getChe_vin());
        }
        et_jclc.setText("" + billEntiy.getXche_lc());
        et_kehumc.setText(billEntiy.getKehu_mc());
        et_shouji.setText(billEntiy.getKehu_dh());
        et_cardno.setText(billEntiy.getCard_no());
        gongshifeili_name = billEntiy.getXche_sfbz();
        gongshifeili_id = String.valueOf(billEntiy.getXche_sffl());
        tv_gcsj.setText(billEntiy.getGcsj());
        et_niankuan.setText(billEntiy.getChe_nf());
        et_color.setText(billEntiy.getChe_wxys());
        et_cunyou.setText(billEntiy.getXche_cy());
        et_miaoshu.setText(billEntiy.getXche_bz());
        tv_gsfl.setText(billEntiy.getXche_sfbz());
        gongshifeili_name = billEntiy.getXche_sfbz();
        gongshifeili_id = String.valueOf(billEntiy.getXche_sffl());
    }

    /**
     * 根据传入的参数，获取到单据中的信息
     */
    private void getBillInfoFromParam() {
        try {
            JSONObject item = new JSONObject(params);
            billEntiy = new BSD_WeiXiuJieDan_Entity();
            billEntiy.setWork_no(item.getString("work_no"));
            billEntiy.setKehu_no(item.getString("kehu_no"));
            billEntiy.setKehu_mc(item.getString("kehu_mc"));
            billEntiy.setKehu_xm(item.getString("kehu_xm"));
            billEntiy.setKehu_dz(item.getString("kehu_dz"));
            billEntiy.setKehu_yb(item.getString("kehu_yb"));
            billEntiy.setKehu_dh(item.getString("kehu_dh"));
            billEntiy.setChe_no(item.getString("che_no"));
            billEntiy.setChe_cx(item.getString("che_cx"));
            billEntiy.setChe_vin(item.getString("che_vin"));
            billEntiy.setChe_gzms(item.getString("xche_bz"));   //故障描述
            billEntiy.setXche_lc(item.getInt("xche_lc"));
            billEntiy.setXche_jdrq(item.getString("xche_jdrq"));
            billEntiy.setXche_sfbz(item.getString("xche_sfbz"));   //费率名称
            billEntiy.setXche_sffl(item.getDouble("xche_sffl"));
            billEntiy.setCangk_dm(item.getString("cangk_dm"));
            billEntiy.setCangk_mc(item.getString("cangk_mc"));
            billEntiy.setGcsj(item.getString("gcsj"));
            billEntiy.setCard_no(item.getString("card_no"));
            billEntiy.setXche_hjje(item.getDouble("xche_hjje"));
//            xm_zj = item.getDouble("xche_rgf");
//            cl_zj = item.getDouble("xche_clf");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*
     * 根据vin码获取车辆名称、代码、内部名称；
     */
    public void duVin() {
        listvincx.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("vinCode", et_vin.getText().toString());
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
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_getcx_byvindm, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aa, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        String data = jsonObject.getString("data");
                        //给车牌、车系、车组、车型赋值；
                        String[] s1 = data.split("\\|");
                        tv_pinpai.setText(s1[0]);
                        tv_chexi.setText(s1[1]);
                        tv_chezu.setText(s1[2]);
                        tv_chexing.setText(s1[3]);
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
                Log.e("vin", "名称： " + listvincx.get(position).get("cxMc"));
                Log.e("vin", "内部名称： " + listvincx.get(position).get("cxMcStd"));
                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        cxnm = listvincx.get(position).get("cxDm");    //车辆代码
                        Log.e("vins", "车辆代码。。。" + cxnm);
                        getcx_by_cxdm();
                    }
                });

                return layout;
            }
        });
        dialog.show();
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
                String value = nameList3.get(pos).toString();
                if (!tv_gsfl.getText().toString().equals(value)) {
                    tv_gsfl.setText(value);
                    gongshifeili_name = listgslv.get(pos).get("feil_mc");
                    gongshifeili_id = listgslv.get(pos).get("feil_fl");
                }
            }
        });
        mSpinerPopWindow3.setWidth(ll_gsfl.getWidth());
        mSpinerPopWindow3.showAsDropDown(ll_gsfl);
    }

    /**
     * 工时费率
     */
    public void getGSFLData() {
        listgslv.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("type", "ITGongShi");
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_HYGL_ADD_TYPE, params, new AbStringHttpResponseListener() {
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
     * 进厂时发微信
     */
    private void weixin() {
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", Conts.work_no);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxjd_weixin, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {

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


    //项目金额
    double j = 0;

    //材料金额
    double g = 0;

    /**
     * 维修材料列表
     */
    public void cldata() {
        list_CL.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", billEntiy.getWork_no());
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxjd_cllb, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int a, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject item = jsonarray.getJSONObject(i);
                            BSD_WeiXiuJieDan_CL_Entity entity = new BSD_WeiXiuJieDan_CL_Entity();
                            entity.setReco_no(item.getInt("reco_no"));
                            entity.setWork_no(item.getString("work_no"));
                            entity.setPeij_no(item.getString("peij_no"));
                            entity.setPeij_th(item.getString("peij_th"));
                            entity.setPeij_mc(item.getString("peij_mc"));
                            entity.setPeij_dw(item.getString("peij_dw"));
                            entity.setPeij_sl(item.getDouble("peij_sl"));
                            entity.setPeij_dj(item.getDouble("peij_dj"));
                            entity.setPeij_je(item.getDouble("peij_je"));
                            entity.setPeij_zt(item.getString("peij_zt"));
                            list_CL.add(entity);
                        }
                    }
                    adp_cl.notifyDataSetChanged();
                    wxclPrice();
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
     * 维修项目列表
     */
    public void xmdata() {
        list_XM.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", billEntiy.getWork_no());
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxjd_xmlb, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aaa, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject item = jsonarray.getJSONObject(i);
                            BSD_WeiXiuJieDan_XM_Entity entity = new BSD_WeiXiuJieDan_XM_Entity();
                            entity.setReco_no(item.getInt("reco_no"));
                            entity.setWork_no(item.getString("work_no"));
                            entity.setWxxm_no(item.getString("wxxm_no"));
                            entity.setWxxm_mc(item.getString("wxxm_mc"));
                            entity.setWxxm_gs(item.getDouble("wxxm_gs"));
                            entity.setWxxm_je(item.getDouble("wxxm_je"));
                            entity.setWxxm_zt(item.getString("wxxm_zt"));
                            entity.setWxxm_dj(item.getDouble("wxxm_dj"));
                            list_XM.add(entity);
                        }
                    }
                    adp_xm.notifyDataSetChanged();
                    wxxmPrice();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bsd_lsbj_fanhui:
                ((MainActivity) getActivity()).upBSD_WXJD_log();
                break;
            case R.id.ll_pinpai:
                showPinPaiDialog();
                break;
            case R.id.ll_chexi:
                showCheXiData();
                break;
            case R.id.ll_chezu:
                showCheZuData();
                break;
            case R.id.ll_chexing:
                showCHeXingData();
                break;
            case R.id.ll_gsfl:
                getGSFLData();
                break;
            case R.id.ll_gcsj:
                timePickerShow.timePickerAlertDialog(tv_gcsj);
                break;
            case R.id.tv_add_wxxm:
                showWxxm();
                break;
            case R.id.tv_add_wxcl:
                showWxcl();
                break;
            case R.id.tv_readvin:
                readVin();
                break;
            case R.id.bsd_wxjd_lswx:
                showLSWX();
                break;
            case R.id.bsd_wxjd_clxx:
                showCarInfo();
                break;
            case R.id.bsd_wxjd_rl_bycx:
                showBYCX();
                break;
            case R.id.bsd_wxjd_save:
                save();
                break;
            case R.id.bsd_wxjd_jc:
                enter();
                break;

        }
    }

    private void enter() {
        queding_quxiao = new Queding_Quxiao(getActivity(), "确认进厂吗？");
        queding_quxiao.show();
        queding_quxiao.setOnResultClickListener(new Queding_Quxiao.OnResultClickListener() {
            @Override
            public void onConfirm() {
                jinchang();
                queding_quxiao.dismiss();
            }

            @Override
            public void onCancel() {
                queding_quxiao.dismiss();
            }
        });
    }

    private void showBYCX() {
        if (tv_chexing.getText().toString().equals("") || et_jclc.getText().toString().equals("") || tv_gcsj.getText().toString().equals("")) {
            Show.showTime(getActivity(), "请输入完整信息");
        } else {
            getBYCXData();
        }
    }

    private void showCarInfo() {
        BSD_MeiRongKuaiXiu_cheliangxinxi_Fragment.newInstance(billEntiy.getChe_no(), Conts.BILLTYPE_WXJD, billEntiy.getWork_no())
                .show(getFragmentManager(), "dialog_fragment");
    }

    private void showLSWX() {
        BSD_LishiWeiXiu_DialogFragment.newInstance(billEntiy.getChe_no()).show(getFragmentManager(), "lswx");
    }

    private void readVin() {
        if (TextUtils.isEmpty(et_vin.getText().toString())) {
            Toast.makeText(getActivity(), "请输入vin码", Toast.LENGTH_SHORT).show();
        } else {
            duVin();
        }
    }

    private void showWxcl() {
        bsd_zcduxq_cl_pop = new BSD_ZCDUXQ_CL_POP(getActivity());
        bsd_zcduxq_cl_pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        List<BSD_wxyy_cl_pop_entity> tempLists = new ArrayList<>();
        if (list_CL != null) {
            for (BSD_WeiXiuJieDan_CL_Entity item : list_CL) {
                BSD_wxyy_cl_pop_entity tempItem = new BSD_wxyy_cl_pop_entity();
                tempItem.setPeij_no(item.getPeij_no());
                tempItem.setPeij_sl(item.getPeij_sl());
                tempItem.setPeij_ydj(item.getPeij_dj());
                tempLists.add(tempItem);
            }
        }
        bsd_zcduxq_cl_pop.setTempLists(tempLists);
        bsd_zcduxq_cl_pop.setCheNo(billEntiy.getChe_no());
        bsd_zcduxq_cl_pop.setKehuNo(billEntiy.getKehu_no());
        bsd_zcduxq_cl_pop.gb(new BSD_ZCDUXQ_CL_POP.Guanbi() {
            @Override
            public void onGuanBi(List<BSD_wxyy_cl_pop_entity> tempList) {
                List<BSD_WeiXiuJieDan_CL_Entity> needAddList = new ArrayList<>();
                // 循环返回的数据，找不需要新添加的
                for (BSD_wxyy_cl_pop_entity item : tempList) {
                    boolean hasAdd = false;
                    double addPeijSl = item.getPeij_sl();
                    for (BSD_WeiXiuJieDan_CL_Entity listCLItem : list_CL) {
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
                        BSD_WeiXiuJieDan_CL_Entity wxclItem = new BSD_WeiXiuJieDan_CL_Entity();
                        wxclItem.setWork_no(billEntiy.getWork_no());
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

    private void saveNewWxclToDB(final List<BSD_WeiXiuJieDan_CL_Entity> needAddList) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "保存中...");
        AbRequestParams params = new AbRequestParams();
        Object json = JSON.toJSON(needAddList);
        params.put("addLists", json.toString());
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxjd_addnewcl, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                if (data.equals("success")) {
                    boolean isAdd = true;
                    for (BSD_WeiXiuJieDan_CL_Entity item : needAddList) {
                        for (BSD_WeiXiuJieDan_CL_Entity listClItem : list_CL) {
                            if (listClItem.getPeij_no().equals(item.getPeij_no())) {
                                isAdd = false;
                                listClItem.setPeij_sl(listClItem.getPeij_sl() + item.getPeij_sl());
                                listClItem.setPeij_je(listClItem.getPeij_je() + item.getPeij_je());
                                break;
                            }
                        }
                        if (isAdd) {
                            list_CL.add(item);
                        }
                    }
                    Iterator<BSD_WeiXiuJieDan_CL_Entity> iterator = list_CL.iterator();
                    while (iterator.hasNext()) {
                        BSD_WeiXiuJieDan_CL_Entity next = iterator.next();
                        if (next.getPeij_sl() == 0) {
                            iterator.remove();
                        }
                    }
                    adp_cl.notifyDataSetChanged();
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

    private void wxclPrice() {
        double jg = 0;
        for (int i = 0; i < list_CL.size(); i++) {
            jg = jg + (list_CL.get(i).getPeij_dj() * list_CL.get(i).getPeij_sl());
        }
        clZje = jg;
        tv_wxjd_clhj.setText(jg + "元");
        tv_wxcl_count.setText("(共" + list_CL.size() + "条记录)");
        zoongjia();
    }

    private void showWxxm() {
        bsd_zcduxq_xm_pop = new BSD_ZCDUXQ_XM_POP(getActivity());
        bsd_zcduxq_xm_pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        // 将当前已经添加的维修项目，放到popwindow的临时表中，表示这些项目已经添加
        List<BSD_wxyy_xm_pop_entiy> tempLists = new ArrayList<>();
        if (list_XM != null) {
            for (BSD_WeiXiuJieDan_XM_Entity item : list_XM) {
                BSD_wxyy_xm_pop_entiy tempItem = new BSD_wxyy_xm_pop_entiy();
                tempItem.setWxxm_no(item.getWxxm_no());
                tempLists.add(tempItem);
            }
        }
        bsd_zcduxq_xm_pop.setTempLists(tempLists);
        bsd_zcduxq_xm_pop.setCheCx(billEntiy.getChe_cx());
        bsd_zcduxq_xm_pop.setCheNo(billEntiy.getChe_no());
        bsd_zcduxq_xm_pop.setCheFl(billEntiy.getXche_sfbz());
        bsd_zcduxq_xm_pop.setWorkNo(billEntiy.getWork_no());
        // 设置点击返回的回调
        bsd_zcduxq_xm_pop.setOnGuanbiListener(new BSD_ZCDUXQ_XM_POP.OnGuanbiListener() {
            @Override
            public void onGuanBi(List<BSD_wxyy_xm_pop_entiy> tempList) {
                List<BSD_WeiXiuJieDan_XM_Entity> needAddList = new ArrayList<>();
                // 循环返回的数据，找不需要新添加的
                for (BSD_wxyy_xm_pop_entiy item : tempList) {
                    boolean hasAdd = false;
                    for (BSD_WeiXiuJieDan_XM_Entity listXMItem : list_XM) {
                        if (listXMItem.getWxxm_no().equals(item.getWxxm_no())) {
                            // 如果相等，则表示存在，直接退出，否则继续循环
                            hasAdd = true;
                            break;
                        }
                    }
                    // 如果没有找到，则是需要添加的集合
                    if (!hasAdd) {
                        BSD_WeiXiuJieDan_XM_Entity wxxmItem = new BSD_WeiXiuJieDan_XM_Entity();
                        wxxmItem.setWork_no(billEntiy.getWork_no());
                        wxxmItem.setWxxm_no(item.getWxxm_no());
                        wxxmItem.setWxxm_mc(item.getWxxm_mc());
                        wxxmItem.setWxxm_gs(item.getWxxm_gs());
                        wxxmItem.setWxxm_dj(item.getWxxm_dj());
                        wxxmItem.setWxxm_cb(item.getWxxm_cb());
                        wxxmItem.setWxxm_je(item.getWxxm_zddj());
                        wxxmItem.setWxxm_zt("正常");
                        wxxmItem.setWxxm_tpye("正常");
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

    private void saveNewWxxmToDB(final List<BSD_WeiXiuJieDan_XM_Entity> needAddList) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "保存中...");
        AbRequestParams params = new AbRequestParams();
        Object json = JSON.toJSON(needAddList);
        params.put("addLists", json.toString());
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxjd_addnewxm, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                // 保存成功
                if (data.equals("success")) {
                    // 1.将新维修项目添加到work_ll_gz的集合中;
                    list_XM.addAll(needAddList);
                    adp_xm.notifyDataSetChanged();
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

    private void wxxmPrice() {
        double jg = 0;
        for (int i = 0; i < list_XM.size(); i++) {
            jg = jg + list_XM.get(i).getWxxm_je();
        }
        xmZje = jg;
        tv_wxjd_xmhj.setText(jg + "元");
        tv_wxxm_count.setText("(共" + list_XM.size() + "条记录)");
        zoongjia();
    }

    private void zoongjia() {
        totalJe = xmZje + clZje;
        tv_wxjd_hj.setText(totalJe + "");
    }

    private void showCHeXingData() {
        if (tv_chezu.getText().toString().equals("")) {
            Show.showTime(getActivity(), "请选择车组");
        } else {
            getCheXingData(tv_chezu.getText().toString());
        }
    }

    private void showCheZuData() {
        if (tv_chexi.getText().toString().equals("")) {
            Show.showTime(getActivity(), "请选择车系");
        } else {
            getCheZuData(tv_chexi.getText().toString());
        }
    }

    private void showCheXiData() {
        if (tv_pinpai.getText().toString().equals("")) {
            Show.showTime(getActivity(), "请选择品牌");
        } else {
            getCheXiData(tv_pinpai.getText().toString());
        }

    }

    private void showPinPaiDialog() {
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

    /**
     * 根据车组获取车型信息
     *
     * @param chezuid
     */
    public void getCheXingData(String chezuid) {
        listjbchexing.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("dm", chezuid);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_Chexing, params, new AbStringHttpResponseListener() {
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
                    Conts.chexing = tv_chexing.getText().toString();
                    chexingid = listjbchexing.get(pos).get("chex_bz");
                }
            }
        });
        mSpinerPopWindow2.setWidth(ll_chexing.getWidth());
        mSpinerPopWindow2.showAsDropDown(ll_chexing);
    }

    /**
     * 根据车系获取车组信息
     *
     * @param chexiid
     */
    public void getCheZuData(String chexiid) {
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
                    updateChexZuData();
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
    public void updateChexZuData() {
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
     * 更新车系的数据并显示
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
                chexiid = "";
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
     * 根据车牌号获取车系信息
     *
     * @param cxbianhao
     */
    public void getCheXiData(String cxbianhao) {
        listjbcx.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("dm", cxbianhao);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_CX, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aa, String s) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(s);
                    if (jsonObject.get("status").toString().equals("1")) {
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

    String chepai;
    String pinpai;
    String chexi;
    String chezu;
    String chexing;
    String che_cx;
    String VIN;
    String huiyuankahao;
    String jinchanglicheng;
    String chezhu;
    String dinahua;

    //存档操作
    private void save() {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        chepai = bsd_wxjd_tv_chepai.getText().toString();
        pinpai = tv_pinpai.getText().toString();
        chexi = tv_chexi.getText().toString();
        chezu = tv_chezu.getText().toString();
        chexing = tv_chexing.getText().toString();
        if (tv_pinpai.getText().toString().equals("") ||
                tv_chexi.getText().toString().equals("") ||
                tv_chezu.getText().toString().equals("") ||
                tv_chexing.getText().toString().equals("")) {
            che_cx = "";
        } else {
            che_cx = pinpai + "|" + chexi + "|" + chezu + "|" + chexing;
        }
        VIN = et_vin.getText().toString();
        jinchanglicheng = et_jclc.getText().toString();
        dinahua = et_shouji.getText().toString();
        chezhu = et_kehumc.getText().toString();
        huiyuankahao = et_cardno.getText().toString();
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", billEntiy.getWork_no());
        params.put("che_no", billEntiy.getChe_no());
        params.put("che_cx", che_cx);
        params.put("che_vin", VIN);
        params.put("xche_lc", jinchanglicheng);
        params.put("kehu_mc", chezhu);
        params.put("kehu_dh", dinahua);
        params.put("xche_sfbz", gongshifeili_name);
        params.put("xche_sffl", gongshifeili_id);
        params.put("kehu_no", billEntiy.getKehu_no());
        params.put("xche_hjje", tv_wxjd_hj.getText().toString());
        params.put("gcsj", tv_gcsj.getText().toString());
        params.put("card_no", et_cardno.getText().toString());
        params.put("che_nf", et_niankuan.getText().toString());
        params.put("che_wxys", et_color.getText().toString());
        params.put("xche_cy", et_cunyou.getText().toString());
        params.put("xche_bz", et_miaoshu.getText().toString());
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxjd_jbxxtj, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                if ("success".equals(data)) {
                    queRen = new QueRen(getActivity(), "存档成功");
                    queRen.show();
                    queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                        @Override
                        public void onYesClick() {
                            queRen.dismiss();
                        }
                    });
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
                Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 打印维修接待单
     */
    private void print() {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", billEntiy.getWork_no());
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxjd_print, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
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
                Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 进厂
     */
    public void jinchang() {
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", billEntiy.getWork_no());
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_WXJD_jc, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    // 进厂成功之后，发送微信，弹出提示框是否打印
                    if (jsonObject.get("data").toString().trim().equals("进厂成功")) {
                        //发送微信
                        weixin();
                        queRen = new QueRen(getActivity(), "进厂成功");
                        queRen.show();
                        queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                            @Override
                            public void onYesClick() {
                                queRen.dismiss();
                                queding_quxiao = new Queding_Quxiao(getActivity(), "是否打印单据？");
                                queding_quxiao.show();
                                queding_quxiao.setOnResultClickListener(new Queding_Quxiao.OnResultClickListener() {
                                    @Override
                                    public void onConfirm() {
                                        queding_quxiao.dismiss();
                                        print();
                                        showTiaoZhuanXQDialog();
                                    }

                                    @Override
                                    public void onCancel() {
                                        queding_quxiao.dismiss();
                                        showTiaoZhuanXQDialog();
                                    }
                                });
                            }
                        });

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

    private void showTiaoZhuanXQDialog() {
        queding_quxiao = new Queding_Quxiao(getActivity(), "是否跳转到单据详情");
        queding_quxiao.show();
        queding_quxiao.setOnResultClickListener(new Queding_Quxiao.OnResultClickListener() {
            @Override
            public void onConfirm() {
                queding_quxiao.dismiss();
                ((MainActivity) getHostActicity()).showWxddFragment(billEntiy.getWork_no());
            }

            @Override
            public void onCancel() {
                queding_quxiao.dismiss();
                ((MainActivity) getActivity()).upBSD_WXJD_log();
            }
        });
    }

    /**
     * 获取保养查询所需的数据，并显示保养查询窗口
     */
    public void getBYCXData() {
        AbRequestParams params = new AbRequestParams();
        params.put("quanMing", tv_chexing.getText().toString());
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_chexing_fansuan, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String getCar = jsonObject.getString("data");
                    chexingid = getCar;
                    int licheng = (int) Double.parseDouble(et_jclc.getText().toString().trim());
                    String time = tv_gcsj.getText().toString().trim();
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