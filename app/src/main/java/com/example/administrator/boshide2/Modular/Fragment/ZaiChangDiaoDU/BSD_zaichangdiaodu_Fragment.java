package com.example.administrator.boshide2.Modular.Fragment.ZaiChangDiaoDU;

import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.view.pullview.AbPullToRefreshView;
import com.alibaba.fastjson.JSON;
import com.example.administrator.boshide2.Conts;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Activity.MainActivity;
import com.example.administrator.boshide2.Modular.Fragment.BaseFragment;
import com.example.administrator.boshide2.Modular.Fragment.ZaiChangDiaoDU.Entity.BSD_ZaiChangDiaoDu_Entity;
import com.example.administrator.boshide2.Modular.Fragment.ZaiChangDiaoDU.PopWindow.Adapter.BSD_ZCDD_XM_adp;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.QuanQuan.WeiboDialogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @在厂调度碎片页 Created by Administrator on 2017-4-13.
 */

public class BSD_zaichangdiaodu_Fragment extends BaseFragment implements AbPullToRefreshView.OnHeaderRefreshListener, AbPullToRefreshView.OnFooterLoadListener {
    private ListView listview;
    private BSD_ZCDD_XM_adp adp_xm;
    private List<BSD_ZaiChangDiaoDu_Entity> list = new ArrayList<BSD_ZaiChangDiaoDu_Entity>();
    private Dialog mWeiboDialog;
    private TextView bsd_zcdd_name, bsd_zcdd_dh, bsd_zcdd_cp;
    private URLS url;
    private TextView tv_search;
    private AbPullToRefreshView abPullToRefreshView = null;
    private int page = 1;
    private TextView title;
    private TextView footerText;
    private String cheNo = "";
    private String kehuMc = "";
    private String workNo = "";

    @Override
    protected int getLayoutId() {
        return R.layout.bsd_zcdd_fragment;
    }

    @Override
    public void initView() {
        abPullToRefreshView = (AbPullToRefreshView) view.findViewById(R.id.lsfreshview);
        abPullToRefreshView.setOnHeaderRefreshListener(this);
        abPullToRefreshView.setOnFooterLoadListener(this);
        // 设置进度条的样式
        // 设置进度条的样式
        abPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
                getResources().getDrawable(R.drawable.progress_circular));
        abPullToRefreshView.getFooterView().setFooterProgressBarDrawable(
                getResources().getDrawable(R.drawable.progress_circular));

        bsd_zcdd_cp = (TextView) view.findViewById(R.id.bsd_zcdd_cp);
        bsd_zcdd_dh = (TextView) view.findViewById(R.id.bsd_zcdd_dh);
        bsd_zcdd_name = (TextView) view.findViewById(R.id.bsd_zcdd_name);
        //维修项目
        listview = (ListView) view.findViewById(R.id.bsd_lv);
        adp_xm = new BSD_ZCDD_XM_adp(getActivity(), list);
        listview.setAdapter(adp_xm);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ((MainActivity) getActivity()).showWxddFragment(list.get(i).getWork_no());
            }
        });
        // 查询
        tv_search = (TextView) view.findViewById(R.id.tv_search);
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.clear();
                cheNo = bsd_zcdd_cp.getText().toString();
                kehuMc = bsd_zcdd_name.getText().toString();
                workNo = bsd_zcdd_dh.getText().toString();
                page = 1;
                getData();
            }
        });
        title = (TextView) view.findViewById(R.id.tv_title);
        footerText = (TextView) view.findViewById(R.id.tv_footertext);
    }

    @Override
    public void initData() {
        url = new URLS();
        title.setText("在厂调度");
        footerText.setText("公司名称 :   " + MyApplication.shared.getString("GongSiMc", "") +
                "                  公司电话 :   " + MyApplication.shared.getString("danw_dh", ""));
        list.clear();
        page = 1;
        getData();
    }

    private void getData() {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        AbRequestParams params = new AbRequestParams();
        params.put("che_no", cheNo);
        params.put("kehu_mc", kehuMc);
        params.put("work_no", workNo);
        params.put("pageNumber", page);
//        params.put("gongsino", MyApplication.shared.getString("bsd_gs_id", ""));
        params.put("gongsino", MyApplication.shared.getString("GongSiNo", ""));
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_zcdu_list, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("status").toString().equals("1")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject item = jsonarray.getJSONObject(i);
                            BSD_ZaiChangDiaoDu_Entity entity = new BSD_ZaiChangDiaoDu_Entity();
                            entity.setWork_no(item.getString("work_no"));
                            entity.setKehu_no(item.getString("kehu_no"));
                            entity.setKehu_xm(item.getString("kehu_xm"));
                            entity.setKehu_mc(item.getString("kehu_mc"));
                            entity.setChe_no(item.getString("che_no"));
                            entity.setXche_jcr(item.getString("xche_jcr"));
                            entity.setXche_wxsj(item.getInt("xche_wxsj"));
                            entity.setKehu_dh(item.getString("kehu_dh"));
                            entity.setXche_cz(item.getString("xche_cz"));
                            entity.setXche_ywlx(item.getString("substate"));
                            entity.setXche_hjje(Double.parseDouble(item.getString("xche_hjje")));
                            entity.setXche_jdrq(item.getString("xche_jdrq"));
                            list.add(entity);
                        }
                    } else {
                        subPage();
                    }
                } catch (JSONException e) {
                    subPage();
                    e.printStackTrace();
                }
                adp_xm.notifyDataSetChanged();
                abPullToRefreshView.onFooterLoadFinish();
                abPullToRefreshView.onHeaderRefreshFinish();
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
                subPage();
                WeiboDialogUtils.closeDialog(mWeiboDialog);
                abPullToRefreshView.onFooterLoadFinish();
                abPullToRefreshView.onHeaderRefreshFinish();
            }
        });
    }


    @Override
    public void onHeaderRefresh(AbPullToRefreshView abPullToRefreshView) {
        list.clear();
        page = 1;
        getData();
    }

    @Override
    public void onFooterLoad(AbPullToRefreshView abPullToRefreshView) {
        page++;
        getData();
    }
    public void subPage() {
        if (page > 1) {
            page--;
        }
    }
}
