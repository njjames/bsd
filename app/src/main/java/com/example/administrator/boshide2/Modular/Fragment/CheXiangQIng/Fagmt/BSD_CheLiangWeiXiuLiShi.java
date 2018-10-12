package com.example.administrator.boshide2.Modular.Fragment.CheXiangQIng.Fagmt;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Fragment.BaseFragment;
import com.example.administrator.boshide2.Modular.Fragment.CheXiangQIng.Fagmt.FagmtAdp.BSD_CheLiangLiShiWeiXiu_adp;
import com.example.administrator.boshide2.Modular.Fragment.LiShiBaoJia.BSD_kuaisubaojia_xiangqing_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.LiShiWeiXiu.Entity.BSD_LSWX_ety;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.QuanQuan.WeiboDialogUtils;
import com.example.administrator.boshide2.Tools.Show;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 博士德车辆信息维修历史
 * Created by Administrator on 2017-4-24.
 */

public class BSD_CheLiangWeiXiuLiShi extends BaseFragment {
    private static final String PARAM_KEY = "param_key";
    private ListView bsd_lsbj_lv;
    private BSD_CheLiangLiShiWeiXiu_adp adapter;
    private ScrollView scrollView;
    private List<BSD_LSWX_ety> datas = new ArrayList<BSD_LSWX_ety>();
    private String chepai;//传入的车牌参数
    //转圈
    private Dialog mWeiboDialog;
    private URLS url;
    private String param;

    public static Fragment newInstance(String params) {
        BSD_CheLiangWeiXiuLiShi fragment = new BSD_CheLiangWeiXiuLiShi();
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
        return R.layout.bsd_clxq_cllswx;
    }

    @Override
    public void initView() {
        scrollView = (android.widget.ScrollView) getActivity().findViewById(R.id.scrollview);
        bsd_lsbj_lv = (ListView) view.findViewById(R.id.bsd_lsbj_lv);
        adapter = new BSD_CheLiangLiShiWeiXiu_adp(getActivity(), datas);
        bsd_lsbj_lv.setAdapter(adapter);
        bsd_lsbj_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });
        //解决滑动问题。
        bsd_lsbj_lv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                try {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            scrollView.requestDisallowInterceptTouchEvent(true); //禁止scrollview拦截事件，让listview可滑动
                            break;
                        case MotionEvent.ACTION_UP:
                            scrollView.requestDisallowInterceptTouchEvent(false);
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

    @Override
    public void initData() {
        url = new URLS();
        chepai = MyApplication.shared.getString("che_no", "");//从全局变量里面取出来车牌
        datas.clear();
        WXLS();
    }

    public void WXLS() {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        AbRequestParams params = new AbRequestParams();
        params.put("pageNumber", 1);
        params.put("che_no", param);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_CL_WX, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject json = jsonarray.getJSONObject(i);
                            BSD_LSWX_ety lswx_ety = new BSD_LSWX_ety();
                            lswx_ety.setWork_no(json.optString("work_no"));
                            lswx_ety.setKehu_no(json.optString("kehu_no"));
                            lswx_ety.setKehu_bxno(json.optString("kehu_bxno"));
                            lswx_ety.setKehu_mc(json.optString("kehu_mc"));
                            lswx_ety.setChe_no(json.optString("che_no"));
                            lswx_ety.setXche_last_jdrq(json.optString("xche_last_jdrq"));
                            lswx_ety.setXche_hjje(json.optString("xche_hjje"));
                            lswx_ety.setXche_jcr(json.optString("xche_jcr"));
                            lswx_ety.setXche_jdrq(json.getString("xche_jdrq"));
                            datas.add(lswx_ety);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getActivity(), "没有数据", Toast.LENGTH_SHORT).show();
                        WeiboDialogUtils.closeDialog(mWeiboDialog);
                    }
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
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
                Show.showTime(getActivity(), "网络连接超时");
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });
    }
}
