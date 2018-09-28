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
                searchCar();
            }
        });
        adapter = new BSD_clxx_adp(getActivity(), datas);
        bsd_lsbj_lv.setAdapter(adapter);
        bsd_lsbj_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BSD_CLXX_ety cx_ety = new BSD_CLXX_ety();//拿到数据set到activity
                cx_ety = datas.get(i);
                ((MainActivity) getHostActicity()).setClxx_ety(cx_ety);
                ((MainActivity) getHostActicity()).upclxq(view);
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
        searchCar();
    }

    /**
     * 车辆信息
     */
    public void searchCar() {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        String chepai = et_cl_chepai.getText().toString().trim();
        String chezhu = et_cl_chezhu.getText().toString().trim();
        AbRequestParams params = new AbRequestParams();
        params.put("pageNumber", page);
        params.put("che_no", chepai);
        params.put("kehu_mc", chezhu);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_CAR, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject json = jsonarray.getJSONObject(i);
                            BSD_CLXX_ety clxx_ety = new BSD_CLXX_ety();
                            clxx_ety.setKehu_xm(json.optString("kehu_mc"));
                            clxx_ety.setKehu_mc(json.optString("kehu_xm"));
                            clxx_ety.setChe_djrq(json.optString("che_djrq"));
                            clxx_ety.setChe_no(json.optString("che_no"));
                            clxx_ety.setChe_cx(json.optString("che_cx"));
                            clxx_ety.setChe_xingzhi(json.optString("che_xingzhi"));
                            clxx_ety.setKehu_mc(json.optString("kehu_mc"));
                            clxx_ety.setChe_vin(json.optString("che_vin"));
                            clxx_ety.setChe_xingzhi(json.optString("che_xingzhi"));
                            clxx_ety.setChe_gcrq(json.optString("che_gcrq"));
                            clxx_ety.setKehu_xm(json.getString("kehu_xm"));
                            clxx_ety.setKehu_dh(json.getString("kehu_dh"));
                            clxx_ety.setChe_prior_byrq(json.optString("che_prior_byrq"));
                            clxx_ety.setChe_prior_licheng(Double.parseDouble(json.optString("che_prior_licheng")));
                            clxx_ety.setChe_next_byrq(json.optString("che_next_byrq"));
                            clxx_ety.setChe_next_byrq(json.optString("che_next_byrq"));
                            clxx_ety.setChe_next_licheng(json.optString("che_next_licheng"));
                            clxx_ety.setChe_zjno(json.optString("che_zjno"));
                            datas.add(clxx_ety);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Show.showTime(getActivity(), jsonObject.get("message").toString());
                    }
                    refreshView.onFooterLoadFinish();
                    refreshView.onHeaderRefreshFinish();
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                } catch (JSONException e) {
                    e.printStackTrace();
                    adapter.notifyDataSetChanged();
                    refreshView.onFooterLoadFinish();
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                }
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int statusCode, String s, Throwable throwable) {
                Show.showTime(getActivity(), "网络连接超时");
                refreshView.onFooterLoadFinish();
                refreshView.onHeaderRefreshFinish();
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });
    }

    @Override
    public void onFooterLoad(AbPullToRefreshView abPullToRefreshView) {
        page++;
        searchCar();
    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView abPullToRefreshView) {
        datas.clear();
        page = 1;
        searchCar();
    }

}
