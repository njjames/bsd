package com.example.administrator.boshide2.Modular.Fragment.YuyueLiSHi;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.view.pullview.AbPullToRefreshView;
import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
import com.example.administrator.boshide2.Conts;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Activity.MainActivity;
import com.example.administrator.boshide2.Modular.Fragment.BaseFragment;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.Entity.BSD_WeiXiuYueYue_entiy;
import com.example.administrator.boshide2.Modular.Fragment.YuyueLiSHi.Adapter.BSD_yyls_adp;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.QuanQuan.WeiboDialogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @预约历史碎片页 Created by Administrator on 2017-4-13.
 */

public class BSD_YuYueLiShi_Fragment extends BaseFragment implements OnFooterLoadListener, AbPullToRefreshView.OnHeaderRefreshListener {
    private LinearLayout bsd_lsbj_fanhui;
    private ListView bsd_lsbj_lv;
    private List<BSD_WeiXiuYueYue_entiy> data = new ArrayList<BSD_WeiXiuYueYue_entiy>();
    private BSD_yyls_adp adapter;
    private AbPullToRefreshView bsd_lsbj_av;
    private Dialog mWeiboDialog;
    private int page = 1;
    private URLS url;
    private EditText bsd_lsyy_cp;
    private EditText bsd_lsyy_cu;
    private TextView tv_search;
    private TextView title;
    private TextView footerText;
    private String cheNo = "";
    private String kehuMc = "";

    @Override
    protected int getLayoutId() {
        return R.layout.bsd_lsyy_fragment;
    }

    @Override
    public void initView() {
        bsd_lsyy_cp = (EditText) view.findViewById(R.id.bsd_lsyy_cp);
        bsd_lsyy_cu = (EditText) view.findViewById(R.id.bsd_lsyy_cu);
        tv_search = (TextView) view.findViewById(R.id.tv_search);
        bsd_lsbj_lv = (ListView) view.findViewById(R.id.bsd_lsbj_lv);
        adapter = new BSD_yyls_adp(getActivity(), data);
        bsd_lsbj_lv.setAdapter(adapter);
        bsd_lsbj_av = (AbPullToRefreshView) view.findViewById(R.id.bsd_lsbj_av);
        bsd_lsbj_av.setOnHeaderRefreshListener(this);
        bsd_lsbj_av.setOnFooterLoadListener(this);
        // 设置进度条的样式
        bsd_lsbj_av.getHeaderView().setHeaderProgressBarDrawable(
                getActivity().getResources().getDrawable(R.drawable.progress_circular));
        bsd_lsbj_av.getFooterView().setFooterProgressBarDrawable(
                getActivity().getResources().getDrawable(R.drawable.progress_circular));

        bsd_lsbj_fanhui = (LinearLayout) view.findViewById(R.id.bsd_lsbj_fanhui);
        bsd_lsbj_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).upqhflog(view);
            }
        });
        bsd_lsbj_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                BSD_WeiXiuYueYue_entiy entity = new BSD_WeiXiuYueYue_entiy();
                entity = data.get(i);

                ((MainActivity) getActivity()).setEntiy(entity);//传了个实体
                Conts.zt = 1;
                Conts.cp = entity.getChe_no();
                ((MainActivity) getActivity()).wxyyxiangqing();
            }
        });

        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.clear();
                cheNo = bsd_lsyy_cp.getText().toString();
                kehuMc = bsd_lsyy_cu.getText().toString();
                getData();
            }
        });
        title = (TextView) view.findViewById(R.id.tv_title);
        footerText = (TextView) view.findViewById(R.id.tv_footertext);
    }

    @Override
    public void initData() {
        url = new URLS();
        title.setText("历史预约");
        footerText.setText("公司名称 :   " + MyApplication.shared.getString("GongSiMc", "") +
                "                  公司电话 :   " + MyApplication.shared.getString("danw_dh", ""));
        data.clear();
        getData();
    }

    /**
     * 请求预约历史接口数据
     */
    public void getData() {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        AbRequestParams params = new AbRequestParams();
        params.put("pageNumber", page);
        params.put("che_no", cheNo);
        params.put("kehu_xm", kehuMc);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxyy_yuls, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                JSONObject result;
                try {
                    result = new JSONObject(s);
                    if (result.get("message").toString().equals("查询成功")) {
                        JSONArray json = result.getJSONArray("data");
                        for (int j = 0; j < json.length(); j++) {
                            JSONObject item = json.getJSONObject(j);
                            BSD_WeiXiuYueYue_entiy entity = new BSD_WeiXiuYueYue_entiy();
                            entity.setYuyue_no(item.getString("yuyue_no"));
                            entity.setKehu_mc(item.getString("kehu_mc"));
                            entity.setKehu_dh(item.getString("kehu_dh"));
                            entity.setChe_no(item.getString("che_no"));
                            entity.setChe_vin(item.getString("che_vin"));
                            entity.setChe_cx(item.getString("che_cx"));
                            entity.setYuyue_yjjcrq(item.getString("yuyue_yjjcrq"));
                            entity.setYuyue_yjjclc(item.getInt("yuyue_yjjclc"));
                            entity.setYuyue_jlrq(item.getString("yuyue_jlrq"));
                            entity.setYuyue_scjcrq(item.getString("yuyue_scjcrq"));
                            entity.setKehu_xm(item.getString("kehu_xm"));
                            entity.setYuyue_czy(item.getString("yuyue_czy"));
                            entity.setYuyue_progress(item.getString("yuyue_progress"));
                            entity.setYuyue_sffl(item.getDouble("yuyue_sffl"));
                            entity.setYuyue_sfbz(item.getString("yuyue_sfbz"));
                            data.add(entity);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                bsd_lsbj_av.onFooterLoadFinish();
                bsd_lsbj_av.onHeaderRefreshFinish();
                adapter.notifyDataSetChanged();
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
                WeiboDialogUtils.closeDialog(mWeiboDialog);
                bsd_lsbj_av.onFooterLoadFinish();
                bsd_lsbj_av.onHeaderRefreshFinish();
                adapter.notifyDataSetChanged();
            }
        });

    }


    @Override
    public void onFooterLoad(AbPullToRefreshView abPullToRefreshView) {
        page = page + 1;
        getData();
    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView abPullToRefreshView) {
        data.clear();
        page = 1;
        getData();
    }
}
