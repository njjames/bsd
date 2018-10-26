package com.example.administrator.boshide2.Modular.Fragment.LiShiJieDan;

import android.app.Dialog;
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
    private BSD_WeiXiuJieDan_Entity entity;
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
    }

    @Override
    public void initData() {
        url = new URLS();
        title.setText("历史接单详情");
        footerText.setText("公司名称 :   " + MyApplication.shared.getString("GongSiMc", "") +
                "                  公司电话 :   " + MyApplication.shared.getString("danw_dh", ""));
        entity = new BSD_WeiXiuJieDan_Entity();
        entity = ((MainActivity) getActivity()).getWxjdentity();
        Conts.work_no = entity.getWork_no();
        String cheCx = entity.getChe_cx();
        String[] cheCxs = cheCx.split("\\|");
        if (cheCxs.length >= 4) {
            bsd_wxjd_pinpai.setText(cheCxs[0]);
            bsd_wxjd_chexi.setText(cheCxs[1]);
            bsd_wxjd_chezu.setText(cheCxs[2]);
            bsd_wxjd_chexing.setText(cheCxs[3]);
        }
        bsd_wxjd_chepai.setText(entity.getChe_no());
        bsd_wxjd_vin.setText(entity.getChe_vin());
        bsd_wxjd_jinchanglicheng.setText("" + entity.getXche_lc());
        bsd_wxjd_name.setText(entity.getKehu_mc());
        bsd_wxjd_shouji.setText(entity.getKehu_dh());
        bsd_wxjd_huiyuan.setText(entity.getCard_no());
        bsd_wxjd_gsfl.setText(entity.getXche_sfbz());
        bsd_wxjd_jdrq.setText(entity.getXche_jdrq());
        xmdata();
        cldata();
    }

    /**
     * 维修材料列表
     */
    public void cldata() {
        list_CL.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", entity.getWork_no());
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxjd_lscl, params, new AbStringHttpResponseListener() {
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
        params.put("work_no", entity.getWork_no());
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxjd_lsxm, params, new AbStringHttpResponseListener() {
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
}