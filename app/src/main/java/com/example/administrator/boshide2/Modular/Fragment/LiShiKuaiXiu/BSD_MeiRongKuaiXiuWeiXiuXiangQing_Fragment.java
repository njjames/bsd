package com.example.administrator.boshide2.Modular.Fragment.LiShiKuaiXiu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Activity.MainActivity;
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

public class BSD_MeiRongKuaiXiuWeiXiuXiangQing_Fragment extends Fragment {
    private ListView listViewXM;//维修项目
    private ListView listViewCL;//维修材料
    private RelativeLayout beijing;
    private LinearLayout bsd_lsbj_fanhui;
    //维修项目
    private BSD_WXXQ_XM_adp adp_xm;
    private List<BSD_LSWX_WXMU_Entity> xmLists = new ArrayList<BSD_LSWX_WXMU_Entity>();
    //选择材料
    private BSD_WXXQ_CL_adp adp_cl;
    private List<BSD_LSWX_WXCL_Entity> clLists = new ArrayList<BSD_LSWX_WXCL_Entity>();

    private TextView tv_clZje;
    private TextView tv_xmZje;
    private String danhao;
    private int page = 1;
    private URLS url;
    private BSD_LSWX_ety entatydata;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bsd_wxxq_fragment, null);
        url = new URLS();
        // data();
        danhao = ((MainActivity) getActivity()).getWork_no();
        init(view);
        lswx_wxxm();
        lswx_wxcl();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        data111();
    }

    public void data111(){
        bsd_lswx_danhao.setText(entatydata.getWork_no());
        bsd_lswx_chepaihao.setText(entatydata.getChe_no());
        bsd_lswx_kehu.setText(entatydata.getKehu_mc());
        bsd_lswx_riqi.setText(entatydata.getXche_jsrq());
        bsd_lswx_dianhua.setText(entatydata.getKehu_dh());
        bsd_lswx_huiyuankahao.setText(entatydata.getCard_no());
        bsd_lswx_huiyuankazhifujine.setText(entatydata.getZhifu_card_je());
        bsd_lswx_chuzhikahao.setText(entatydata.getZhifu_card_no());
        bsd_lswx_jiesuanbiaozhi.setChecked(entatydata.isFlag_cardjs());
        bsd_lswx_buxianjin.setText(entatydata.getZhifu_card_xj());

    }
    private void init(View view) {
        entatydata = new BSD_LSWX_ety();
        entatydata = ((MainActivity) getActivity()).getData();
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
        listViewXM = (ListView) view.findViewById(R.id.bsd_wxxq_lv);
        adp_xm = new BSD_WXXQ_XM_adp(getContext(), xmLists);
        listViewXM.setAdapter(adp_xm);
        //维修材料
        listViewCL = (ListView) view.findViewById(R.id.bsd_wxxq_cl_lv1);
        adp_cl = new BSD_WXXQ_CL_adp(getContext(), clLists);
        listViewCL.setAdapter(adp_cl);
        // 返回
        bsd_lsbj_fanhui = (LinearLayout) view.findViewById(R.id.bsd_lsbj_fanhui);
        bsd_lsbj_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).uplskx();
            }
        });
        tv_clZje = (TextView) view.findViewById(R.id.tv_wxxq_wxcl_jine);
        tv_xmZje = (TextView) view.findViewById(R.id.tv_xq_xm);
        beijing = (RelativeLayout) getActivity().findViewById(R.id.beijing);

        //滑动
//        scrollview = (ScrollView) view.findViewById(R.id.scrollview);
        //解决滑动问题
//        huadong();
    }

    /**
     * 维修项目
     */
    private void lswx_wxxm() {
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", danhao);
        params.put("pageNumber", page);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_lswx_wxxm, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("status").toString().equals("1")) {
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
                            xmLists.add(wxmu_entity);
                        }
                        xq_xm();
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

            }
        });
    }

    /**
     * 项目的金额
     */
    double b = 0;

    public void xq_xm() {
        double xmZje = 0;
        for (int i = 0; i < xmLists.size(); i++) {
            xmZje = xmZje + Double.parseDouble((xmLists.get(i).getWxxm_je()));
        }
        tv_xmZje.setText(xmZje + "元");
        b = xmZje;
        zongjia();
    }

    /**
     * 材料的金额
     */
    double a = 0;

    public void xq_cl() {
        double clZje = 0;
        for (int i = 0; i < clLists.size(); i++) {
            clZje = clZje + (clLists.get(i).getPeij_dj() * clLists.get(i).getPeij_sl());
        }
        tv_clZje.setText(clZje + "元");
        a = clZje;
        zongjia();
    }

    public void zongjia() {
    }

    /**
     * 材料
     */
    private void lswx_wxcl() {
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", danhao);
        params.put("pageNumber", page);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_lswx_wxcl, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int sss, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("status").toString().equals("1")) {
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
                            clLists.add(lswx_wxcl_entity);
                        }
                        xq_cl();
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

            }
        });
    }

    /**
     * 解决滑动问题
     */
//    public void huadong() {
//        //解决滑动问题
//        listViewCL.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                try {
//                    switch (event.getAction()) {
//                        case MotionEvent.ACTION_DOWN:
//                            scrollview.requestDisallowInterceptTouchEvent(true); //禁止scrollview拦截事件，让listview可滑动
//                            break;
//                        case MotionEvent.ACTION_UP:
//                            scrollview.requestDisallowInterceptTouchEvent(false);
//                            break;
//                        default:
//                            break;
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return false;
//            }
//        });
//
//
//        //解决滑动问题。
//        listViewXM.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                try {
//                    switch (event.getAction()) {
//                        case MotionEvent.ACTION_DOWN:
//                            scrollview.requestDisallowInterceptTouchEvent(true); //禁止scrollview拦截事件，让listview可滑动
//                            break;
//                        case MotionEvent.ACTION_UP:
//                            scrollview.requestDisallowInterceptTouchEvent(false);
//                            break;
//                        default:
//                            break;
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return false;
//            }
//        });
//
//    }

}
