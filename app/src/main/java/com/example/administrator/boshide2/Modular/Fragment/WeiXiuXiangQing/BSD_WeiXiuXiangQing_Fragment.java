package com.example.administrator.boshide2.Modular.Fragment.WeiXiuXiangQing;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Activity.MainActivity;
import com.example.administrator.boshide2.Modular.Fragment.BaseFragment;
import com.example.administrator.boshide2.Modular.Fragment.LiShiWeiXiu.Entity.BSD_LSWX_ety;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuXiangQing.Adapter.BSD_WXXQ_CL_adp;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuXiangQing.Adapter.BSD_WXXQ_XM_adp;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuXiangQing.Entity.BSD_LSWX_WXCL_Entity;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuXiangQing.Entity.BSD_LSWX_WXMU_Entity;
import com.example.administrator.boshide2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/***
 * 历史维修详情
 */

public class BSD_WeiXiuXiangQing_Fragment extends BaseFragment {
    private static final String PARAM_KEY = "param_key";
    private ListView listxm;//维修项目
    private ListView listcl;//维修材料
    private LinearLayout bsd_lsbj_fanhui;
    private BSD_WXXQ_XM_adp adp_xm;
    private List<BSD_LSWX_WXMU_Entity> listXM = new ArrayList<BSD_LSWX_WXMU_Entity>();
    private BSD_WXXQ_CL_adp adp_cl;
    private List<BSD_LSWX_WXCL_Entity> listCL = new ArrayList<BSD_LSWX_WXCL_Entity>();
    private TextView tv_wxclJe;
    private TextView tv_wxxmJe;//维修材料合几
    private int page = 1;
    private URLS url;

    private TextView bsd_lswx_danhao;//维修单号
    private TextView bsd_lswx_chepaihao;//车牌号
    private TextView bsd_lswx_kehu;//客户
    private TextView bsd_lswx_riqi;//日期
    private TextView bsd_lswx_dianhua;//电话
    private TextView bsd_lswx_huiyuankahao;//会员卡号
    private TextView bsd_lswx_huiyuankazhifujine;//会员卡支付金额
    private TextView bsd_lswx_chuzhikahao;//储值卡号
    private CheckBox bsd_lswx_jiesuanbiaozhi;//储值卡结算标志
    private TextView bsd_lswx_buxianjin;//补现金
    private TextView footerText;
    private TextView title;
    private String params;
    private BSD_LSWX_ety billEntiy;
    private TextView tv_totalJE;
    private TextView tv_wxxmCount;
    private TextView tv_wxclCount;
    private double xmZJe = 0;
    private double clZje = 0;

    public static BSD_WeiXiuXiangQing_Fragment newInstance(String params) {
        BSD_WeiXiuXiangQing_Fragment fragment = new BSD_WeiXiuXiangQing_Fragment();
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
        return R.layout.bsd_wxxq_fragment;
    }

    @Override
    public void initView() {
        bsd_lsbj_fanhui = (LinearLayout) view.findViewById(R.id.bsd_lsbj_fanhui);
        bsd_lsbj_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).upBSD_LSWX_page();
            }
        });
        bsd_lswx_danhao = (TextView) view.findViewById(R.id.bsd_lswx_danhao);
        bsd_lswx_chepaihao = (TextView) view.findViewById(R.id.bsd_lswx_chepaihao);
        bsd_lswx_kehu = (TextView) view.findViewById(R.id.bsd_lswx_kehu);
        bsd_lswx_riqi = (TextView) view.findViewById(R.id.bsd_lswx_riqi);
        bsd_lswx_dianhua = (TextView) view.findViewById(R.id.bsd_lswx_dianhua);
        bsd_lswx_huiyuankahao = (TextView) view.findViewById(R.id.bsd_lswx_huiyuankahao);
        bsd_lswx_huiyuankazhifujine = (TextView) view.findViewById(R.id.bsd_lswx_huiyuankazhifujine);
        bsd_lswx_chuzhikahao = (TextView) view.findViewById(R.id.bsd_lswx_chuzhikahao);
        bsd_lswx_jiesuanbiaozhi = (CheckBox) view.findViewById(R.id.bsd_lswx_jiesuanbiaozhi);
        bsd_lswx_buxianjin = (TextView) view.findViewById(R.id.bsd_lswx_buxianjin);
        //维修项目
        listxm = (ListView) view.findViewById(R.id.bsd_wxxq_lv);
        adp_xm = new BSD_WXXQ_XM_adp(getContext(), listXM);
        listxm.setAdapter(adp_xm);
        //维修材料
        listcl = (ListView) view.findViewById(R.id.bsd_wxxq_cl_lv1);
        adp_cl = new BSD_WXXQ_CL_adp(getContext(), listCL);
        listcl.setAdapter(adp_cl);
        tv_wxclJe = (TextView) view.findViewById(R.id.tv_wxxq_wxcl_jine);
        tv_wxxmJe = (TextView) view.findViewById(R.id.tv_xq_xm);
        title = (TextView) view.findViewById(R.id.tv_title);
        footerText = (TextView) view.findViewById(R.id.tv_footertext);
        tv_totalJE = (TextView) view.findViewById(R.id.tv_total_je);
        tv_wxxmCount = (TextView) view.findViewById(R.id.tv_wxxm_count);
        tv_wxclCount = (TextView) view.findViewById(R.id.tv_wxcl_count);
    }

    @Override
    public void initData() {
        url = new URLS();
        title.setText("历史维修详情");
        footerText.setText("公司名称 :   " + MyApplication.shared.getString("GongSiMc", "") +
                "                  公司电话 :   " + MyApplication.shared.getString("danw_dh", ""));
        getBillInfoFromParam();
        updateBillInfoUI();
        lswx_wxxm();
        lswx_wxcl();
    }

    private void updateBillInfoUI() {
        bsd_lswx_danhao.setText(billEntiy.getWork_no());
        bsd_lswx_chepaihao.setText(billEntiy.getChe_no());
        bsd_lswx_kehu.setText(billEntiy.getKehu_mc());
        bsd_lswx_riqi.setText(billEntiy.getXche_jsrq());
        bsd_lswx_dianhua.setText(billEntiy.getKehu_dh());
        bsd_lswx_huiyuankahao.setText(billEntiy.getCard_no());
        bsd_lswx_huiyuankazhifujine.setText(billEntiy.getZhifu_card_je());
        bsd_lswx_chuzhikahao.setText(billEntiy.getZhifu_card_no());
        bsd_lswx_jiesuanbiaozhi.setChecked(billEntiy.isFlag_cardjs());
        bsd_lswx_buxianjin.setText(billEntiy.getZhifu_card_xj());
    }

    private void getBillInfoFromParam() {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(params);
            billEntiy = new BSD_LSWX_ety();
            billEntiy.setWork_no(jsonObject.getString("work_no"));
            billEntiy.setChe_no(jsonObject.getString("che_no"));
            billEntiy.setKehu_mc(jsonObject.getString("kehu_mc"));
            billEntiy.setXche_jsrq(jsonObject.getString("xche_jsrq"));
            billEntiy.setKehu_dh(jsonObject.getString("kehu_dh"));
            billEntiy.setCard_no(jsonObject.getString("card_no"));
            billEntiy.setZhifu_card_je(jsonObject.getString("zhifu_card_je"));
            billEntiy.setFlag_IsCheck(jsonObject.getBoolean("flag_cardjs"));
            billEntiy.setZhifu_card_xj(jsonObject.getString("zhifu_card_xj"));
            billEntiy.setZhifu_card_no(jsonObject.getString("zhifu_card_no"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 维修项目
     */
    private void lswx_wxxm() {
        listXM.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", billEntiy.getWork_no());
        params.put("pageNumber", page);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_lswx_wxxm, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int sss, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject json = jsonarray.getJSONObject(i);
                            BSD_LSWX_WXMU_Entity wxmu_entity = new BSD_LSWX_WXMU_Entity();
                            wxmu_entity.setWxxm_mc(json.optString("wxxm_mc"));
                            wxmu_entity.setWxxm_gs(json.optDouble("wxxm_gs"));
                            wxmu_entity.setWxxm_dj(json.optDouble("wxxm_dj"));
                            wxmu_entity.setWxxm_je(json.optString("wxxm_je"));
                            wxmu_entity.setWxxm_yje(json.optString("wxxm_yje"));
                            wxmu_entity.setWxxm_zk(json.optString("wxxm_zk"));
                            listXM.add(wxmu_entity);
                        }
                    }
                    xq_xm();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adp_xm.notifyDataSetChanged();
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                Log.i("cjn", "项目查询失败了" + s);
            }
        });
    }

    /**
     * 项目的金额
     */
    public void xq_xm() {
        tv_wxxmCount.setText("(共" + listXM.size() + "条记录)");
        double bb = 0;
        for (int i = 0; i < listXM.size(); i++) {
            bb = bb +Double.parseDouble(listXM.get(i).getWxxm_je());
        }
        xmZJe = bb;
        tv_wxxmJe.setText(bb + "");
        zongjia();
    }

    /**
     * 材料的金额
     */
    public void xq_cl() {
        tv_wxclCount.setText("(共" + listCL.size() + "条记录)");
        double aa = 0;
        for (int i = 0; i < listCL.size(); i++) {
            aa = aa + (listCL.get(i).getPeij_dj() * listCL.get(i).getPeij_sl());
        }
        clZje = aa;
        tv_wxclJe.setText(aa + "");
        zongjia();
    }

    public void zongjia() {
        tv_totalJE.setText(clZje + xmZJe + "");
    }

    /**
     * 材料
     */
    private void lswx_wxcl() {
        listCL.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", billEntiy.getWork_no());
        params.put("pageNumber", page);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_lswx_wxcl, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int sss, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject json = jsonarray.getJSONObject(i);
                            BSD_LSWX_WXCL_Entity lswx_wxcl_entity = new BSD_LSWX_WXCL_Entity();
                            lswx_wxcl_entity.setPeij_mc(json.optString("peij_mc"));
                            lswx_wxcl_entity.setPeij_sl(json.optDouble("peij_sl"));
                            lswx_wxcl_entity.setPeij_dj(json.optDouble("peij_dj"));
                            lswx_wxcl_entity.setPeij_th(json.optString("peij_th"));
                            lswx_wxcl_entity.setPeij_pp(json.optString("peij_pp"));
                            lswx_wxcl_entity.setPeij_je(json.optString("peij_je"));
                            listCL.add(lswx_wxcl_entity);
                        }
                        xq_cl();
                    }
                    xq_cl();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adp_cl.notifyDataSetChanged();
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                Log.i("cjn","材料查询失败了"+s);
            }
        });
    }
}
