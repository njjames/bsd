package com.example.administrator.boshide2.Modular.Fragment.LiShiJieDan;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.view.pullview.AbPullToRefreshView;
import com.alibaba.fastjson.JSON;
import com.example.administrator.boshide2.Conts;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Activity.MainActivity;
import com.example.administrator.boshide2.Modular.Entity.WorkPzGz_Entity;
import com.example.administrator.boshide2.Modular.Fragment.BaseFragment;
import com.example.administrator.boshide2.Modular.Fragment.LiShiJieDan.Adapter.BSD_lsjd_adp;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_Entity;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.QuanQuan.WeiboDialogUtils;
import com.example.administrator.boshide2.Tools.Show;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * @历史接单 Created by Administrator on 2017-4-13.
 */

public class BSD_lishijiedan_Fragment extends BaseFragment implements AbPullToRefreshView.OnFooterLoadListener, AbPullToRefreshView.OnHeaderRefreshListener {
    private LinearLayout bsd_lsbj_fanhui;
    private ListView bsd_lsbj_lv;
    private List<WorkPzGz_Entity> data = new ArrayList<>();
    private BSD_lsjd_adp adapter;
    private AbPullToRefreshView abPullToRefreshView = null;
    private int page = 1;
    private URLS url;
    private EditText bsd_lsjd_cp;
    private EditText bsd_lsjd_cz;
    private TextView iv_search;
    private TextView title;
    private TextView footerText;
    private String cheNo = "";
    private String kehuMc = "";
    private Dialog mWeiboDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.bsd_lsjd_fragment;
    }

    @Override
    public void initView() {
        bsd_lsjd_cp = (EditText) view.findViewById(R.id.bsd_lsjd_cp);
        bsd_lsjd_cz = (EditText) view.findViewById(R.id.bsd_lsjd_cz);
        bsd_lsbj_lv = (ListView) view.findViewById(R.id.bsd_lsbj_lv);
        adapter = new BSD_lsjd_adp(getActivity(), data);
        adapter.setPhoto(new BSD_lsjd_adp.Photo() {
            @Override
            public void Ddh(String dianhua) {
                TelephonyManager manager = (TelephonyManager) getActivity()
                        .getSystemService(TELEPHONY_SERVICE);// 取得相关系统服务
                String imsi = manager.getSubscriberId(); // 取出IMSI
                if (imsi == null || imsi.length() <= 0) {
                    Toast.makeText(getContext(), "没有检测到SIM卡", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + dianhua));
                    startActivity(intent);
                }
            }
        });
        bsd_lsbj_lv.setAdapter(adapter);
        bsd_lsbj_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ((MainActivity) getActivity()).showWxjdXqFragment(JSON.toJSON(data.get(i)).toString());
            }
        });
        // 返回
        bsd_lsbj_fanhui = (LinearLayout) view.findViewById(R.id.bsd_lsbj_fanhui);
        bsd_lsbj_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).upBSD_WXJD_log();
            }
        });
        abPullToRefreshView = (AbPullToRefreshView) view.findViewById(R.id.lsfreshview);
        // 设置监听器
        abPullToRefreshView.setOnHeaderRefreshListener(this);
        abPullToRefreshView.setOnFooterLoadListener(this);
        // 设置进度条的样式
        // 设置进度条的样式
        abPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
                getResources().getDrawable(R.drawable.progress_circular));
        abPullToRefreshView.getFooterView().setFooterProgressBarDrawable(
                getResources().getDrawable(R.drawable.progress_circular));
        // 查询
        iv_search = (TextView) view.findViewById(R.id.iv_search);
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.clear();
                cheNo = bsd_lsjd_cp.getText().toString();
                kehuMc = bsd_lsjd_cz.getText().toString();
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
        title.setText("历史接单");
        footerText.setText("公司名称 :   " + MyApplication.shared.getString("GongSiMc", "") +
                "                  公司电话 :   " + MyApplication.shared.getString("danw_dh", ""));
        data.clear();
        page = 1;
        getData();
    }

    /**
     * 获取历史接单
     */
    public void getData() {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        data.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("pageNumber", page);
        params.put("che_no", cheNo);
        params.put("kehu_mc", kehuMc);
        params.put("gongsino", MyApplication.shared.getString("GongSiNo", ""));
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_LSJD, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int statusCode, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        JSONArray array = jsonObject.getJSONArray("data");
                        List<WorkPzGz_Entity> _list = JSON.parseArray(array.toString(), WorkPzGz_Entity.class);
                        data.addAll(_list);
                    } else {
                        Show.showTime(getActivity(), jsonObject.get("message").toString());
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
            public void onFailure(int statusCode, String s, Throwable throwable) {
                Show.showTime(getActivity(), "网络连接超时");
                abPullToRefreshView.onFooterLoadFinish();
                abPullToRefreshView.onHeaderRefreshFinish();
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });


    }

    @Override
    public void onFooterLoad(AbPullToRefreshView abPullToRefreshView) {
        page++;
        getData();
    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView abPullToRefreshView) {
        data.clear();
        page = 1;
        getData();
        adapter.notifyDataSetChanged();
    }


}
