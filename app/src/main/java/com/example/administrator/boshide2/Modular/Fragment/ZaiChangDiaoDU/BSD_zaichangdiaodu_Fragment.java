package com.example.administrator.boshide2.Modular.Fragment.ZaiChangDiaoDU;

import android.app.Dialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.view.pullview.AbPullToRefreshView;
import com.alibaba.fastjson.JSON;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Activity.MainActivity;
import com.example.administrator.boshide2.Modular.Entity.WorkPzGz_Entity;
import com.example.administrator.boshide2.Modular.Fragment.BaseFragment;
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
    private List<WorkPzGz_Entity> list = new ArrayList<>();
    private Dialog mWeiboDialog;
    private TextView bsd_zcdd_name, bsd_zcdd_dh, bsd_zcdd_cp;
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
                ((MainActivity) getActivity()).showWxddFragment(JSON.toJSONString(list.get(i)));
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
        params.put("gongsino", MyApplication.shared.getString("GongSiNo", ""));
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_zcdu_list, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getString("message").equals("查询成功")) {
                        JSONArray array = jsonObject.getJSONArray("data");
                        List<WorkPzGz_Entity> _list = JSON.parseArray(array.toString(), WorkPzGz_Entity.class);
                        list.addAll(_list);
                    } else {
                        if (page > 1) {
                            Toast.makeText(getHostActicity(), "没有更多单据了", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getHostActicity(), "当前没有单据", Toast.LENGTH_SHORT).show();
                        }
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
