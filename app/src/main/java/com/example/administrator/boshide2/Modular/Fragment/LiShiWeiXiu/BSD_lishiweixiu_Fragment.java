package com.example.administrator.boshide2.Modular.Fragment.LiShiWeiXiu;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.view.pullview.AbPullToRefreshView;
import com.alibaba.fastjson.JSON;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Activity.MainActivity;
import com.example.administrator.boshide2.Modular.Fragment.BaseFragment;
import com.example.administrator.boshide2.Modular.Fragment.LiShiWeiXiu.Adapter.BSD_lswx_adp;
import com.example.administrator.boshide2.Modular.Fragment.LiShiWeiXiu.Adapter.BSD_lswx_guwen_adp;
import com.example.administrator.boshide2.Modular.Fragment.LiShiWeiXiu.Entity.BSD_LSWX_ety;
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
 * @历史维修碎片页 Created by Administrator on 2017-4-13.
 */

public class BSD_lishiweixiu_Fragment extends BaseFragment implements AbPullToRefreshView.OnHeaderRefreshListener, AbPullToRefreshView.OnFooterLoadListener {
    private ListView bsd_lsbj_lv;
    private List<BSD_LSWX_ety> data = new ArrayList<BSD_LSWX_ety>();
    private BSD_lswx_adp adapter;
    private AbPullToRefreshView wx_rfview;//刷新
    private int page = 1;//分页
    private EditText et_wx_chepai;
    private TextView tv_wx_chaxun;
    private String wx_chepai;
    //转圈
    private Dialog mWeiboDialog;
    private BSD_lswx_guwen_adp lswx_guwen_adp;//服务顾问适配器
    private List<Map<String, String>> list_gu = new ArrayList<Map<String, String>>();
    private URLS url;
    private TextView title;
    private TextView footerText;

    @Override
    protected int getLayoutId() {
        return R.layout.bsd_lishiweixiu_fragment;
    }

    @Override
    public void initView() {
        wx_rfview = (AbPullToRefreshView) view.findViewById(R.id.wx_rfview);
        wx_rfview.setOnHeaderRefreshListener(this);
        wx_rfview.setOnFooterLoadListener(this);
        et_wx_chepai = (EditText) view.findViewById(R.id.et_wx_chepai);
        tv_wx_chaxun = (TextView) view.findViewById(R.id.tv_wx_chaxun);
        tv_wx_chaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.clear();
                wx_chepai = et_wx_chepai.getText().toString();
                LSWX();
            }
        });

        bsd_lsbj_lv = (ListView) view.findViewById(R.id.bsd_lsbj_lv);
        adapter = new BSD_lswx_adp(getActivity(), data);
        bsd_lsbj_lv.setAdapter(adapter);
        bsd_lsbj_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ((MainActivity) getActivity()).showWXXQFragment(JSON.toJSON(data.get(i)).toString());
            }
        });
        title = (TextView) view.findViewById(R.id.tv_title);
        footerText = (TextView) view.findViewById(R.id.tv_footertext);
    }

    @Override
    public void initData() {
        url = new URLS();
        title.setText("历史维修");
        footerText.setText("公司名称 :   " + MyApplication.shared.getString("GongSiMc", "") +
                "                  公司电话 :   " + MyApplication.shared.getString("danw_dh", ""));
        data.clear();
        LSWX();
    }

    public void LSWX() {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        AbRequestParams params = new AbRequestParams();
        params.put("pageNumber", page);
        params.put("che_no", wx_chepai);
        params.put("type", 0);
        params.put("caozuoyuanid", Integer.parseInt(MyApplication.shared.getString("id", "")));
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_CL_WX, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int sss, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject json = jsonarray.getJSONObject(i);
                            BSD_LSWX_ety lswx_ety = new BSD_LSWX_ety();
                            lswx_ety.setWork_no(json.getString("work_no"));
                            lswx_ety.setKehu_mc(json.getString("kehu_mc"));
                            lswx_ety.setChe_no(json.getString("che_no"));
                            lswx_ety.setXche_jsrq(json.getString("xche_jsrq"));
                            lswx_ety.setXche_jcr(json.getString("xche_jcr"));
                            lswx_ety.setXche_hjje(json.getString("xche_hjje"));
                            lswx_ety.setXche_jdrq(json.getString("xche_jdrq"));
                            lswx_ety.setKehu_mc(json.getString("kehu_mc"));
                            lswx_ety.setKehu_dh(json.getString("kehu_dh"));
                            lswx_ety.setXche_jsrq(json.getString("xche_jsrq"));
                            lswx_ety.setCard_no(json.getString("card_no"));
                            lswx_ety.setZhifu_card_je(json.getString("zhifu_card_je"));//会员卡支付金额
                            lswx_ety.setZhifu_card_no(json.getString("zhifu_card_no"));//储值卡号
                            lswx_ety.setFlag_cardjs(json.getBoolean("flag_cardjs"));//储值卡结算标志
                            lswx_ety.setZhifu_card_xj(json.getString("zhifu_card_xj"));//补现金
                            data.add(lswx_ety);
                        }
                    } else {
                        subPage();
                    }
                } catch (JSONException e) {
                    subPage();
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
                WeiboDialogUtils.closeDialog(mWeiboDialog);
                wx_rfview.onFooterLoadFinish();
                wx_rfview.onHeaderRefreshFinish();
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                subPage();
                WeiboDialogUtils.closeDialog(mWeiboDialog);
                wx_rfview.onFooterLoadFinish();
                wx_rfview.onHeaderRefreshFinish();
                Show.showTime(getActivity(), "网络连接超时");
            }
        });
    }

    /**
     * 服务顾问
     *
     * @param
     */
    private void guwen() {
        AbRequestParams params = new AbRequestParams();
        params.put("type", "ITJcr");
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_HYGL_ADD_TYPE, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int sssss, String s) {
                try {
                    JSONObject object = new JSONObject(s);

                    if (object.get("status").toString().equals("1")) {
                        Log.i("传走没有", "..." + s);
                        JSONArray jsonarray = object.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {

                            JSONObject json = jsonarray.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<>();

                            map.put("name",
                                    json.get("reny_xm").toString());
                            map.put("bianhao", json.get("reny_dm").toString());
                            list_gu.add(map);
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


    @Override
    public void onFooterLoad(AbPullToRefreshView abPullToRefreshView) {
        page++;
        wx_chepai = et_wx_chepai.getText().toString();
        LSWX();
    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView abPullToRefreshView) {
        data.clear();
        page = 1;
        wx_chepai = et_wx_chepai.getText().toString();
        LSWX();
    }

    public void subPage() {
        if (page > 1) {
            page--;
        }
    }
}
