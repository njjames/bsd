package com.example.administrator.boshide2.Modular.Fragment.LiShiJieDan;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.example.administrator.boshide2.Conts;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Activity.MainActivity;
import com.example.administrator.boshide2.Modular.Adapter.AbstractSpinerAdapter;
import com.example.administrator.boshide2.Modular.Adapter.CustemSpinerAdapter;
import com.example.administrator.boshide2.Modular.Entity.CustemObject;
import com.example.administrator.boshide2.Modular.Fragment.BaseFragment;
import com.example.administrator.boshide2.Modular.Fragment.LiShiJieDan.Adapter.BSD_WXJD_CL_xiangqing_adp;
import com.example.administrator.boshide2.Modular.Fragment.LiShiJieDan.Adapter.BSD_WXJD_XM_xiangqingadp;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_CL_Entity;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_Entity;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_XM_Entity;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.PopWindow.BSD_WXJD_CL_POP;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.PopWindow.BSD_WXJD_XM_POP;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.BSD_WXYY_XM_POP;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.BSD_XiuGaiGongShi;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.Pop_Entity.BSD_wxyy_cl_pop_entity;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.Pop_Entity.BSD_wxyy_xm_pop_entiy;
import com.example.administrator.boshide2.Modular.View.SpinerPopWindow;
import com.example.administrator.boshide2.Modular.View.diaog.QueRen;
import com.example.administrator.boshide2.Modular.View.timepicker.TimePickerShow;
import com.example.administrator.boshide2.R;
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
 * @维修接单碎片页 Created by Administrator on 2017-4-13.
 */

public class BSD_WeiXiuJieDan_xiangqing_Fragment extends BaseFragment{
    private static final String PARAM_KEY = "param_key";
    private TextView bsd_wxjd_chepai;//车牌
    private TextView bsd_wxjd_jinchanglicheng;//进厂里程
    private TextView bsd_wxjd_name;//车主
    private TextView bsd_wxjd_shouji;//手机
    private TextView bsd_wxjd_vin;//VIN码
    private TextView bsd_wxjd_pinpai;//品牌
    private TextView bsd_wxjd_chexi;//车系
    private TextView bsd_wxjd_chezu;//车组
    private TextView bsd_wxjd_chexing;//车型
    private TextView bsd_wxjd_huiyuan;//会员卡号
    private TextView tv_wxjd_clhj;
    private TextView tv_wxjd_xmhj;
    private TextView tv_wxjd_hj;
    private TextView bsd_wxjd_gsfl;
    private TextView bsd_wxjd_jdrq;
    private ListView listxm;//维修项目
    private ListView listcl;//维修材料
    private BSD_WXJD_XM_xiangqingadp adp_xm;
    private List<BSD_WeiXiuJieDan_XM_Entity> list_XM = new ArrayList<>();
    private BSD_WXJD_CL_xiangqing_adp adp_cl;
    private List<BSD_WeiXiuJieDan_CL_Entity> list_CL = new ArrayList<>();
    private LinearLayout bsd_lsbj_fanhui;
    private URLS url;
    private TextView title;
    private TextView footerText;
    private TextView billNo;
    private BSD_WeiXiuJieDan_Entity billEntiy;
    private String params;
    private TextView tv_wxclCount;
    private TextView tv_wxxmCount;

    public static BSD_WeiXiuJieDan_xiangqing_Fragment newInstance(String params) {
        BSD_WeiXiuJieDan_xiangqing_Fragment fragment = new BSD_WeiXiuJieDan_xiangqing_Fragment();
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
        return R.layout.bsd_wxjd_xiangqing_fragment;
    }

    @Override
    public void initView() {
        // 返回
        bsd_lsbj_fanhui = (LinearLayout) view.findViewById(R.id.bsd_lsbj_fanhui);
        bsd_lsbj_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).uplishijiedan(view);
            }
        });
        bsd_wxjd_chepai = (TextView) view.findViewById(R.id.bsd_wxjd_chepai);
        bsd_wxjd_jinchanglicheng = (TextView) view.findViewById(R.id.bsd_wxjd_jinchanglicheng);
        bsd_wxjd_name = (TextView) view.findViewById(R.id.bsd_wxjd_name);
        bsd_wxjd_shouji = (TextView) view.findViewById(R.id.bsd_wxjd_shouji);
        bsd_wxjd_vin = (TextView) view.findViewById(R.id.bsd_wxjd_vin);
        bsd_wxjd_pinpai = (TextView) view.findViewById(R.id.bsd_wxjd_pinpai);
        bsd_wxjd_chexi = (TextView) view.findViewById(R.id.bsd_wxjd_chexi);
        bsd_wxjd_chezu = (TextView) view.findViewById(R.id.bsd_wxjd_chezu);
        bsd_wxjd_chexing = (TextView) view.findViewById(R.id.bsd_wxjd_chexing);
        bsd_wxjd_huiyuan = (TextView) view.findViewById(R.id.bsd_wxjd_huiyuan);
        tv_wxjd_clhj = (TextView) view.findViewById(R.id.tv_wxjd_clhj);
        tv_wxjd_xmhj = (TextView) view.findViewById(R.id.tv_wxjd_xmhj);
        tv_wxjd_hj = (TextView) view.findViewById(R.id.tv_wxjd_hj);
        bsd_wxjd_jdrq = (TextView) view.findViewById(R.id.bsd_wxjd_jdrq);
        bsd_wxjd_gsfl = (TextView) view.findViewById(R.id.bsd_wxjd_gsfl);
        listxm = (ListView) view.findViewById(R.id.lv_wxxm);
        adp_xm = new BSD_WXJD_XM_xiangqingadp(getActivity(), list_XM);
        listxm.setAdapter(adp_xm);
        listcl = (ListView) view.findViewById(R.id.lv_wxcl);
        adp_cl = new BSD_WXJD_CL_xiangqing_adp(getContext(), list_CL);
        listcl.setAdapter(adp_cl);
        title = (TextView) view.findViewById(R.id.tv_title);
        footerText = (TextView) view.findViewById(R.id.tv_footertext);
        billNo = (TextView) view.findViewById(R.id.tv_billNo);
        tv_wxxmCount = (TextView) view.findViewById(R.id.tv_wxxm_count);
        tv_wxclCount = (TextView) view.findViewById(R.id.tv_wxcl_count);
    }

    @Override
    public void initData() {
        url = new URLS();
        title.setText("历史接单详情");
        footerText.setText("公司名称 :   " + MyApplication.shared.getString("GongSiMc", "") +
                "                  公司电话 :   " + MyApplication.shared.getString("danw_dh", ""));
        getBillInfoFromParam();
        updateBillInfoUI();
        xmdata();
        cldata();
    }

    private void updateBillInfoUI() {
        String cheCx = billEntiy.getChe_cx();
        String[] cheCxs = cheCx.split("\\|");
        if (cheCxs.length >= 4) {
            bsd_wxjd_pinpai.setText(cheCxs[0]);
            bsd_wxjd_chexi.setText(cheCxs[1]);
            bsd_wxjd_chezu.setText(cheCxs[2]);
            bsd_wxjd_chexing.setText(cheCxs[3]);
        }
        bsd_wxjd_chepai.setText(billEntiy.getChe_no());
        bsd_wxjd_vin.setText(billEntiy.getChe_vin());
        bsd_wxjd_jinchanglicheng.setText("" + billEntiy.getXche_lc());
        bsd_wxjd_name.setText(billEntiy.getKehu_mc());
        bsd_wxjd_shouji.setText(billEntiy.getKehu_dh());
        bsd_wxjd_huiyuan.setText(billEntiy.getCard_no());
        bsd_wxjd_gsfl.setText(billEntiy.getXche_sfbz());
        bsd_wxjd_jdrq.setText(billEntiy.getXche_jdrq());
        billNo.setText(billEntiy.getWork_no());
        tv_wxjd_hj.setText(billEntiy.getXche_hjje() + "");
    }

    private void getBillInfoFromParam() {   // 注意这个里面取值的字段都需要小写
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(params);
            billEntiy = new BSD_WeiXiuJieDan_Entity();
            billEntiy.setWork_no(jsonObject.getString("work_no"));
            billEntiy.setChe_no(jsonObject.getString("che_no"));
            billEntiy.setChe_vin(jsonObject.getString("che_vin"));
            billEntiy.setChe_cx(jsonObject.getString("che_cx"));
            billEntiy.setKehu_no(jsonObject.getString("kehu_no"));
            billEntiy.setKehu_mc(jsonObject.getString("kehu_mc"));
            billEntiy.setKehu_dh(jsonObject.getString("kehu_dh"));
            billEntiy.setCard_no(jsonObject.getString("card_no"));
            billEntiy.setXche_lc(jsonObject.getInt("xche_lc"));
            billEntiy.setXche_sfbz(jsonObject.getString("xche_sfbz"));
            billEntiy.setXche_jdrq(jsonObject.getString("xche_jdrq"));
            billEntiy.setXche_hjje(jsonObject.getDouble("xche_hjje"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

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
                        wxclPrice();
                        adp_cl.notifyDataSetChanged();
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
                Log.e("返回值", "查询材料00000失败了"+s);
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
                        wxxmPrice();
                        adp_xm.notifyDataSetChanged();
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
                Log.e("返回值", "查询项目失败了"+s);
            }
        });
    }

    public void wxclPrice() {
        double jg = 0;
        for (int i = 0; i < list_CL.size(); i++) {
            jg = jg + (list_CL.get(i).getPeij_dj() * list_CL.get(i).getPeij_sl());
        }
        tv_wxjd_clhj.setText(jg + "元");
        tv_wxclCount.setText("(共" + list_CL.size() + "条记录)");
    }

    public void wxxmPrice() {
        double bb = 0;
        for (int i = 0; i < list_XM.size(); i++) {
            bb = bb + (list_XM.get(i).getWxxm_dj() * list_XM.get(i).getWxxm_gs());
        }
        tv_wxjd_xmhj.setText(bb + "元");
        tv_wxxmCount.setText("(共" + list_XM.size() + "条记录)");
    }
}