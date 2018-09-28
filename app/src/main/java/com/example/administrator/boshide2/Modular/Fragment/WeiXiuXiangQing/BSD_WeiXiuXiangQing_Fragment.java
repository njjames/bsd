package com.example.administrator.boshide2.Modular.Fragment.WeiXiuXiangQing;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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

public class BSD_WeiXiuXiangQing_Fragment extends Fragment {
    ListView listxm;//维修项目
    ListView listcl;//维修材料
    ScrollView scrollview;
    RelativeLayout beijing;
    RelativeLayout bsd_lsbj_fanhui;
    //维修项目
    BSD_WXXQ_XM_adp adp_xm;

    List<BSD_LSWX_WXMU_Entity> data = new ArrayList<BSD_LSWX_WXMU_Entity>();
    //选择材料
    BSD_WXXQ_CL_adp adp_cl;
    List<BSD_LSWX_WXCL_Entity> data1 = new ArrayList<BSD_LSWX_WXCL_Entity>();

    TextView tv_wxxq_wxcl_jine, tv_xq_xm, tv_xq_zj;//维修材料合几
    String danhao;
    int page = 1;
    URLS url;
    BSD_LSWX_ety entatydata;

    TextView bsd_lswx_danhao;//维修单号
    TextView bsd_lswx_chepaihao;//车牌号
    TextView bsd_lswx_kehu;//客户
    TextView bsd_lswx_riqi;//日期
    TextView bsd_lswx_dianhua;//电话
    TextView bsd_lswx_huiyuankahao;//会员卡号
    TextView bsd_lswx_huiyuankazhifujine;//会员卡支付金额
    TextView bsd_lswx_chuzhikahao;//储值卡号
    CheckBox bsd_lswx_jiesuanbiaozhi;//储值卡结算标志
    TextView bsd_lswx_buxianjin;//补现金


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bsd_wxxq_fragment, null);
        url = new URLS();
        // data();
        danhao = ((MainActivity) getActivity()).getWork_no();

        bsdtext(view);
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
        Log.i("cjn","22222222222222222222222222"+entatydata.isFlag_cardjs());
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
        listxm = (ListView) view.findViewById(R.id.bsd_wxxq_lv);
        //维修材料
        listcl = (ListView) view.findViewById(R.id.bsd_wxxq_cl_lv1);
        bsd_lsbj_fanhui = (RelativeLayout) view.findViewById(R.id.bsd_lsbj_fanhui);
        bsd_lsbj_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).uplswx(view);
            }
        });
        tv_wxxq_wxcl_jine = (TextView) view.findViewById(R.id.tv_wxxq_wxcl_jine);
        tv_xq_xm = (TextView) view.findViewById(R.id.tv_xq_xm);
        tv_xq_zj = (TextView) view.findViewById(R.id.tv_xq_zj);
        beijing = (RelativeLayout) getActivity().findViewById(R.id.beijing);


        //滑动
        scrollview = (ScrollView) view.findViewById(R.id.scrollview);
        //解决滑动问题
        huadong();


    }

    /**
     * 维修项目
     */
    private void lswx_wxxm() {
        data.clear();
        Log.i("cjn", "项目查询" + danhao);
        AbRequestParams params = new AbRequestParams();

        params.put("work_no", danhao);
        params.put("pageNumber", page);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_lswx_wxxm, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int sss, String s) {
                try {

                    JSONObject jsonObject = new JSONObject(s);
                    Log.i("cjn", "项目查询成功" + s);
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

                            data.add(wxmu_entity);

                        }

                        //数据atap
                        adp_xm = new BSD_WXXQ_XM_adp(getContext(), data);
                        listxm.setAdapter(adp_xm);
                    }
                    xq_xm();
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
                Log.i("cjn", "项目查询失败了" + s);
            }
        });
    }

    /**
     * 项目的金额
     */
    double b = 0;

    public void xq_xm() {
        double bb = 0;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getWxxm_je().equals("")) {
                Log.i("cjn","没有数据");
            } else {
                bb = bb +Double.parseDouble( data.get(i).getWxxm_je());
            }
        }
        tv_xq_xm.setText(bb + "");
        Log.i("cjn111", "XM的总价：" + bb);
        b = bb;
        zongjia();
    }

    /**
     * 材料的金额
     */
    double a = 0;

    public void xq_cl() {
        double aa = 0;
        for (int i = 0; i < data1.size(); i++) {
            aa = aa + (data1.get(i).getPeij_dj() * data1.get(i).getPeij_sl());
        }
        tv_wxxq_wxcl_jine.setText(aa + "");
        Log.i("cjn111", "XM的总价：" + aa);
        a = aa;
        zongjia();
    }

    public void zongjia() {
        tv_xq_zj.setText(a + b + "");
    }



    /**
     * 材料
     */
    private void lswx_wxcl() {
        data1.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", danhao);
        params.put("pageNumber", page);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_lswx_wxcl, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int sss, String s) {
                Log.i("cjn","材料查询成功"+s);
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
                            data1.add(lswx_wxcl_entity);
                            Log.i("cjn","data1.get(i).getPeij_dj()+data1.get(i).getPeij_sl()===="
                            +data1.get(i).getPeij_dj()+"=========="+data1.get(i).getPeij_sl());

                        }
                        adp_cl = new BSD_WXXQ_CL_adp(getContext(), data1);
                        listcl.setAdapter(adp_cl);
                        xq_cl();
                        // wxclzongjia();

                    }else {
                        BSD_LSWX_WXCL_Entity lswx_wxcl_entity = new BSD_LSWX_WXCL_Entity();
                        lswx_wxcl_entity.setPeij_sl(0);
                        lswx_wxcl_entity.setPeij_dj(0);
                        data1.add(lswx_wxcl_entity);
                    }
                    xq_cl();
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
                Log.i("cjn","材料查询失败了"+s);
            }
        });
    }


//    private void data() {
//        for (int i = 0; i < 20; i++) {
//            HashMap<String, String> map = new HashMap<>();
//            map.put("name", "洗车");
//            map.put("shuliang", "50");
//            map.put("danjia", "150");
//            map.put("tuhao", "100");
//            data.add(map);
//        }
//        for (int i = 0; i < 20; i++) {
//            HashMap<String, String> map = new HashMap<>();
//            map.put("name", "洗车" + i + "个");
//            map.put("shuliang", "" + i);
//            map.put("danjia", "" + i + 100);
//            map.put("tuhao", "50");
//            map.put("pinpai", "150");
//            map.put("caozuo", "操作");
//            data1.add(map);
//        }
//
//    }


    /**
     * 解决滑动问题
     */
    public void huadong() {
        //解决滑动问题
        listcl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                try {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            scrollview.requestDisallowInterceptTouchEvent(true); //禁止scrollview拦截事件，让listview可滑动
                            break;
                        case MotionEvent.ACTION_UP:
                            scrollview.requestDisallowInterceptTouchEvent(false);
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });


        //解决滑动问题。
        listxm.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                try {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            scrollview.requestDisallowInterceptTouchEvent(true); //禁止scrollview拦截事件，让listview可滑动
                            break;
                        case MotionEvent.ACTION_UP:
                            scrollview.requestDisallowInterceptTouchEvent(false);
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

    }

    TextView bsd_01_text;

    public void bsdtext(View view) {
        bsd_01_text = (TextView) view.findViewById(R.id.bsd_13_text);
        bsd_01_text.setText("公司名称 :   " + MyApplication.shared.getString("GongSiMc", "") +
                "                  公司电话 :   " + MyApplication.shared.getString("danw_dh", ""));
    }
}
