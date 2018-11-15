package com.example.administrator.boshide2.Modular.Fragment.LiShiBaoJia;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Activity.MainActivity;
import com.example.administrator.boshide2.Modular.Fragment.BaseFragment;
import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.Entity.BSD_KuaiSuBaoJia_CL_entity;
import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.Entity.BSD_KuaiSuBaoJia_XM_entity;
import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.Entity.BSD_KuaiSuBaoJia_ety;
import com.example.administrator.boshide2.Modular.Fragment.LiShiBaoJia.Adapter.BSD_wxxm_xiangqiang_adp;
import com.example.administrator.boshide2.Modular.Fragment.LiShiBaoJia.Adapter.BSD_xzcl_xiangqing_adp;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.Show;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @快速报价碎片页 Created by Administrator on 2017-4-13.
 */

public class BSD_kuaisubaojia_xiangqing_Fragment extends BaseFragment {
    private static final String PARAM_KEY = "param_key";
    private TextView tv_wxxm_money, tv_wxcl_money;
    private TextView bsd_ksbj_cp;
    private TextView bsd_ksbj_lc;
    private TextView bsd_ksbj_cezhu;
    private TextView bsd_ksbj_dh;
    private TextView bsd_ksbj_vin;
    private TextView bsd_ksbj_pp;
    private TextView bsd_ksbj_cx;
    private TextView bsd_ksbj_cz;
    private TextView bsd_ksbj_cxing;
    private TextView tv_zong_money;
    private ListView listViewXM;//维修项目
    private List<BSD_KuaiSuBaoJia_XM_entity> listXM = new ArrayList<BSD_KuaiSuBaoJia_XM_entity>();
    private BSD_wxxm_xiangqiang_adp adpxm;
    private List<BSD_KuaiSuBaoJia_CL_entity> listCL = new ArrayList<BSD_KuaiSuBaoJia_CL_entity>();
    private BSD_xzcl_xiangqing_adp adpcl;//车系适配器
    private ListView listViewCL;//维修材料
    private TextView bsd_ksbj_tv_gsfl;
    private LinearLayout ll_back;
    private TextView billNo;
    private TextView title;
    private TextView footerText;
    private String params;
    private BSD_KuaiSuBaoJia_ety billEntiy;
    private TextView tv_wxxmCount;
    private TextView tv_wxclVount;

    public static Fragment newInstance(String params) {
        BSD_kuaisubaojia_xiangqing_Fragment fragment = new BSD_kuaisubaojia_xiangqing_Fragment();
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
        return R.layout.bsd_ksbj_xiangqing_fragment;
    }

    @Override
    public void initView() {
        ll_back = (LinearLayout) view.findViewById(R.id.bsd_lsbj_fanhui);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).upLSBJ();
            }
        });

        bsd_ksbj_tv_gsfl = (TextView) view.findViewById(R.id.bsd_ksbj_tv_gsfl);
        tv_zong_money = (TextView) view.findViewById(R.id.tv_total_je);
        //维修项目
        listViewXM = (ListView) view.findViewById(R.id.bsd_lv);
        adpxm = new BSD_wxxm_xiangqiang_adp(getActivity(), listXM);
        listViewXM.setAdapter(adpxm);
        //维修材料
        listViewCL = (ListView) view.findViewById(R.id.bsd_lv1);
        adpcl = new BSD_xzcl_xiangqing_adp(getActivity(), listCL);
        listViewCL.setAdapter(adpcl);
        bsd_ksbj_cp = (TextView) view.findViewById(R.id.bsd_ksbj_cp);
        bsd_ksbj_lc = (TextView) view.findViewById(R.id.bsd_ksbj_lc);
        bsd_ksbj_cezhu = (TextView) view.findViewById(R.id.bsd_ksbj_cezhu);
        bsd_ksbj_dh = (TextView) view.findViewById(R.id.bsd_ksbj_dh);
        bsd_ksbj_vin = (TextView) view.findViewById(R.id.bsd_ksbj_vin);
        bsd_ksbj_pp = (TextView) view.findViewById(R.id.bsd_ksbj_pp);
        bsd_ksbj_cx = (TextView) view.findViewById(R.id.bsd_ksbj_cx);
        bsd_ksbj_cz = (TextView) view.findViewById(R.id.bsd_ksbj_cz);
        bsd_ksbj_cxing = (TextView) view.findViewById(R.id.bsd_ksbj_cxing);
        tv_wxxm_money = (TextView) view.findViewById(R.id.tv_wxxm_money);
        tv_wxcl_money = (TextView) view.findViewById(R.id.tv_wxcl_money);
        title = (TextView) view.findViewById(R.id.tv_title);
        footerText = (TextView) view.findViewById(R.id.tv_footertext);
        billNo = (TextView) view.findViewById(R.id.tv_billNo);
        tv_wxxmCount = (TextView) view.findViewById(R.id.tv_wxxm_count);
        tv_wxclVount = (TextView) view.findViewById(R.id.tv_wxcl_count);
    }

    @Override
    public void initData() {
        title.setText("历史报价详情");
        footerText.setText("公司名称 :   " + MyApplication.shared.getString("GongSiMc", "") +
                "                  公司电话 :   " + MyApplication.shared.getString("danw_dh", ""));
        getBillInfoFromParam();
        updateBillInfoUI();
        //维修项目查询
        xmdata();
        //维修单号查询
        cldata();
    }

    private void updateBillInfoUI() {
        billNo.setText(billEntiy.getList_no());
        bsd_ksbj_cp.setText(billEntiy.getChe_no());
        String che = billEntiy.getChe_cx();
        String[] cheCxs = che.split("\\|");
        if (cheCxs.length >= 4) {
            bsd_ksbj_pp.setText(cheCxs[0]);
            bsd_ksbj_cx.setText(cheCxs[1]);
            bsd_ksbj_cz.setText(cheCxs[2]);
            bsd_ksbj_cxing.setText(cheCxs[3]);
        }
        bsd_ksbj_vin.setText(billEntiy.getChe_vin());
        bsd_ksbj_lc.setText("" + billEntiy.getList_yjjclc());
        bsd_ksbj_cezhu.setText(billEntiy.getKehu_mc());
        bsd_ksbj_dh.setText(billEntiy.getKehu_dh());
        bsd_ksbj_tv_gsfl.setText(billEntiy.getList_sfbz());
        tv_zong_money.setText("" + billEntiy.getList_hjje());
    }

    private void getBillInfoFromParam() {   // 注意这个里面取值的字段都需要小写
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(params);
            billEntiy = new BSD_KuaiSuBaoJia_ety();
            billEntiy.setList_no(jsonObject.getString("list_no"));
            billEntiy.setChe_no(jsonObject.getString("che_no"));
            billEntiy.setKehu_mc(jsonObject.getString("kehu_mc"));
            billEntiy.setKehu_no(jsonObject.getString("kehu_no"));
            billEntiy.setKehu_dh(jsonObject.getString("kehu_dh"));
            billEntiy.setKehu_xm(jsonObject.getString("kehu_xm"));
            billEntiy.setChe_vin(jsonObject.getString("che_vin"));
            billEntiy.setChe_cx(jsonObject.getString("che_cx"));
            billEntiy.setList_hjje(jsonObject.getDouble("list_hjje"));
            billEntiy.setList_yjjclc(jsonObject.getInt("list_yjjclc"));
            billEntiy.setList_sfbz(jsonObject.getString("list_sfbz"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 计算维修项目的总价格
     */
    double b = 0;

    public void wxxmPrice() {
        double bb = 0;
        for (int i = 0; i < listXM.size(); i++) {
            bb = bb + (listXM.get(i).getWxxm_dj() * listXM.get(i).getWxxm_gs());
        }
        tv_wxxm_money.setText(bb + "元");
        tv_wxxmCount.setText("(共" + listXM.size() + "条记录)");
    }

    /**
     * 计算维修材料的总价
     */
    double a = 0;

    double c = 0;
    public void wxclPrice() {
        double jg = 0;
        for (int i = 0; i < listCL.size(); i++) {
            jg = jg + (listCL.get(i).getPeij_dj() * listCL.get(i).getPeij_sl());
        }
        tv_wxcl_money.setText(jg + "元");
        tv_wxclVount.setText("(共" + listCL.size() + "条记录)");
    }

    /**
     * 材料拉数据
     */
    public void cldata() {
        AbRequestParams params = new AbRequestParams();
        params.put("list_no", billEntiy.getList_no());
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_ksbj_wxcl, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aa, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("status").toString().equals("1")) {
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
                        wxclPrice();
                        adpcl.notifyDataSetChanged();
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
     * 项目拉数据
     */
    public void xmdata() {
        AbRequestParams params = new AbRequestParams();
        params.put("list_no", billEntiy.getList_no());
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_ksbj_wxxm, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aa, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("status").toString().equals("1")) {
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
                        adpxm.notifyDataSetChanged();
                        wxxmPrice();
                    } else {
                        Show.showTime(getActivity(), jsonObject.get("message").toString());
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
                Show.showTime(getActivity(), s);
            }
        });
    }
}
