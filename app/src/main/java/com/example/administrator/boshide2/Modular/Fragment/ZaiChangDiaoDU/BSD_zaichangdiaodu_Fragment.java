package com.example.administrator.boshide2.Modular.Fragment.ZaiChangDiaoDU;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.view.pullview.AbPullToRefreshView;
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
import java.util.HashMap;
import java.util.List;

/**
 * @在厂调度碎片页 Created by Administrator on 2017-4-13.
 */

public class BSD_zaichangdiaodu_Fragment extends BaseFragment implements AbPullToRefreshView.OnHeaderRefreshListener, AbPullToRefreshView.OnFooterLoadListener {

    ListView listxm;//维修项目
    //维修项目
    BSD_ZCDD_XM_adp adp_xm;
    List<HashMap<String, String>> data = new ArrayList<>();
    List<BSD_ZaiChangDiaoDu_Entity> list = new ArrayList<BSD_ZaiChangDiaoDu_Entity>();

    private Dialog mWeiboDialog;

    TextView bsd_zcdd_name, bsd_zcdd_dh, bsd_zcdd_cp;
    URLS url;
    RelativeLayout relativeLayout13;
    AbPullToRefreshView abPullToRefreshView = null;

    int page = 1;
    private TextView title;
    private TextView footerText;

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
        //数据atap
        adp_xm = new BSD_ZCDD_XM_adp(getActivity());
        //维修项目
        listxm = (ListView) view.findViewById(R.id.bsd_lv);
        //维修材料
        //滑动
        //解决滑动问题
        listxm.setAdapter(adp_xm);
        listxm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Conts.cp = list.get(i).getChe_no();
                Conts.work_no = list.get(i).getWork_no();

                ((MainActivity) getActivity()).upwxywdd(view);
            }
        });
        relativeLayout13 = (RelativeLayout) view.findViewById(R.id.relativeLayout13);
        relativeLayout13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.clear();
                data();
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
        data();
    }

    private void data() {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        AbRequestParams params = new AbRequestParams();
        params.put("che_no", bsd_zcdd_cp.getText().toString());
        params.put("kehu_mc", bsd_zcdd_name.getText().toString());
        params.put("work_no", bsd_zcdd_dh.getText().toString());
        params.put("pageNumber", page);
//        params.put("gongsino", MyApplication.shared.getString("bsd_gs_id", ""));
        params.put("gongsino", MyApplication.shared.getString("GongSiNo", ""));
        Log.i("zcddgsno", "GongSiNo进来了吗" +  MyApplication.shared.getString("GongSiNo", ""));

        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_zcdu_list, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aa, String s) {
                Log.i("cjn", "进来了吗" + s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    Log.i("cjn", "进来了吗" + jsonObject);
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
                        adp_xm.setList(list);
                        adp_xm.notifyDataSetChanged();
                        WeiboDialogUtils.closeDialog(mWeiboDialog);
                        abPullToRefreshView.onFooterLoadFinish();
                        abPullToRefreshView.onHeaderRefreshFinish();
                    }
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                    abPullToRefreshView.onFooterLoadFinish();
                    abPullToRefreshView.onHeaderRefreshFinish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                Log.i("cjn", "网络连接超时");
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
        data();
    }

    @Override
    public void onFooterLoad(AbPullToRefreshView abPullToRefreshView) {
        page++;
        data();
    }
}
