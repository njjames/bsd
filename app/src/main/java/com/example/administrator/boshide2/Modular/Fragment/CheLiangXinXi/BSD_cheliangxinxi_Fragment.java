package com.example.administrator.boshide2.Modular.Fragment.CheLiangXinXi;

import android.app.Dialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
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
import com.example.administrator.boshide2.Modular.Fragment.CheLiangXinXi.Adapter.BSD_clxx_adp;
import com.example.administrator.boshide2.Modular.Fragment.CheLiangXinXi.Entity.BSD_CLXX_ety;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.QuanQuan.WeiboDialogUtils;
import com.example.administrator.boshide2.Tools.Show;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @车量信息碎片页 Created by Administrator on 2017-4-13.
 */

public class BSD_cheliangxinxi_Fragment extends BaseFragment
        implements AbPullToRefreshView.OnHeaderRefreshListener, AbPullToRefreshView.OnFooterLoadListener {
    private ListView bsd_lsbj_lv;
    private List<BSD_CLXX_ety> datas = new ArrayList<BSD_CLXX_ety>();
    private BSD_clxx_adp adapter;
    private EditText et_cl_chepai, et_cl_chezhu, et_cl_kehu, et_cl_time;
    private TextView tv_cl_select;
    private int page = 1;
    private AbPullToRefreshView refreshView = null;
    private Dialog mWeiboDialog;
    private URLS url;
    private TextView title;
    private TextView footerText;
    private String chepai = "";
    private String chezhu = "";

    @Override
    protected int getLayoutId() {
        return R.layout.bsd_clxx_fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        et_cl_chepai.setText("");
        et_cl_chezhu.setText("");
    }

    @Override
    public void initView() {
        bsd_lsbj_lv = (ListView) view.findViewById(R.id.bsd_lsbj_lv);
        bsd_lsbj_lv.setVisibility(View.VISIBLE);
        et_cl_chepai = (EditText) view.findViewById(R.id.et_cl_chepai);
        et_cl_chezhu = (EditText) view.findViewById(R.id.et_cl_chezhu);
        tv_cl_select = (TextView) view.findViewById(R.id.tv_cl_select);
        et_cl_chepai.setText("");
        et_cl_chezhu.setText("");
        tv_cl_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datas.clear();
                page = 1;
                chepai = et_cl_chepai.getText().toString().trim();
                chezhu = et_cl_chezhu.getText().toString().trim();
                searchCar();
            }
        });
        adapter = new BSD_clxx_adp(getActivity(), datas);
        bsd_lsbj_lv.setAdapter(adapter);
        bsd_lsbj_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ((MainActivity) getHostActicity()).showCLXXXQFragment(JSON.toJSONString(datas.get(i)));
            }
        });
        refreshView = (AbPullToRefreshView) view.findViewById(R.id.refreshview);
        refreshView.getHeaderView().setStateTextSize(24);
        refreshView.getHeaderView().setTimeTextSize(20);
        refreshView.getFooterView().setTextSize(16);
        // 设置监听器
        refreshView.setOnHeaderRefreshListener(this);
        refreshView.setOnFooterLoadListener(this);
        // 设置进度条的样式
        // 设置进度条的样式
        refreshView.getHeaderView().
                setHeaderProgressBarDrawable(getResources().getDrawable(R.drawable.progress_circular));
        refreshView.getFooterView().
                setFooterProgressBarDrawable(getResources().getDrawable(R.drawable.progress_circular));
        title = (TextView) view.findViewById(R.id.tv_title);
        footerText = (TextView) view.findViewById(R.id.tv_footertext);
    }

    @Override
    public void initData() {
        url = new URLS();
        title.setText("车辆信息");
        footerText.setText("公司名称 :   " + MyApplication.shared.getString("GongSiMc", "") +
                "                  公司电话 :   " + MyApplication.shared.getString("danw_dh", ""));
        datas.clear();
        page = 1;
        searchCar();
    }

    /**
     * 车辆信息
     */
    public void searchCar() {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        AbRequestParams params = new AbRequestParams();
        params.put("pageNumber", page);
        params.put("che_no", chepai);
        params.put("kehu_mc", chezhu);
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_CAR, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    if (jsonObject.getString("message").equals("查询成功")) {
                        JSONArray array = jsonObject.getJSONArray("data");
                        List<BSD_CLXX_ety> _list = JSON.parseArray(array.toString(), BSD_CLXX_ety.class);
                        datas.addAll(_list);
                    } else {
                        if (page > 1) {
                            Show.showTime(getActivity(), "没有更多车辆信息了");
                        } else {
                            Show.showTime(getActivity(), "没有车辆信息");
                        }
                        subPage();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    subPage();
                }
                adapter.notifyDataSetChanged();
                refreshView.onFooterLoadFinish();
                refreshView.onHeaderRefreshFinish();
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int statusCode, String s, Throwable throwable) {
                Show.showTime(getActivity(), "查询失败");
                refreshView.onFooterLoadFinish();
                refreshView.onHeaderRefreshFinish();
                WeiboDialogUtils.closeDialog(mWeiboDialog);
                subPage();
            }
        });
    }

    private void subPage() {
        if (page > 1) {
            page--;
        }
    }

    @Override
    public void onFooterLoad(AbPullToRefreshView abPullToRefreshView) {
        page++;
        chepai = et_cl_chepai.getText().toString().trim();
        chezhu = et_cl_chezhu.getText().toString().trim();
        searchCar();
    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView abPullToRefreshView) {
        datas.clear();
        page = 1;
        chepai = et_cl_chepai.getText().toString().trim();
        chezhu = et_cl_chezhu.getText().toString().trim();
        searchCar();
    }

}
