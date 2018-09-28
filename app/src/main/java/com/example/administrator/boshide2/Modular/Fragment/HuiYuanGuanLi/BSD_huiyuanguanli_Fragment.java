package com.example.administrator.boshide2.Modular.Fragment.HuiYuanGuanLi;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.view.pullview.AbPullToRefreshView;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Adapter.AbstractSpinerAdapter;
import com.example.administrator.boshide2.Modular.Adapter.CustemSpinerAdapter;
import com.example.administrator.boshide2.Modular.Entity.CustemObject;
import com.example.administrator.boshide2.Modular.Fragment.BaseFragment;
import com.example.administrator.boshide2.Modular.Fragment.HuiYuanGuanLi.Adapter.BSD_hygl_adp;
import com.example.administrator.boshide2.Modular.Fragment.HuiYuanGuanLi.Entity.BSD_HYGL_ety;
import com.example.administrator.boshide2.Modular.Fragment.HuiYuanGuanLi.PopWindow.BSD_HYGL_TianJia_delo;
import com.example.administrator.boshide2.Modular.View.SpinerPopWindow;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.QuanQuan.WeiboDialogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @会员管理碎片页 Created by Administrator on 2017-4-13.
 */

public class BSD_huiyuanguanli_Fragment extends BaseFragment implements View.OnClickListener, AbPullToRefreshView.OnHeaderRefreshListener, AbPullToRefreshView.OnFooterLoadListener {
    ListView bsd_lv1;
    BSD_hygl_adp adapter;
    List<BSD_HYGL_ety> data = new ArrayList<BSD_HYGL_ety>();
    LinearLayout bsd_hybl;
    //项目维修
    BSD_HYGL_TianJia_delo bsd_wxxm;
    View view22;
    private TextView[] arr_tv;// 图标的数组
    TextView bsd_hygl_yfk, bsd_hygl_dfk;
    //转圈
    private Dialog mWeiboDialog;
    URLS url;
    EditText bsd_huiyuanguanli_sskh;
    TextView bsd_kleixin;
    RelativeLayout relativeLayout4444, bsd_hygl_cx;
    List<Map<String, String>> listjbcz = new ArrayList<Map<String, String>>();
    private List<CustemObject> nameList1 = new ArrayList<CustemObject>();
    private AbstractSpinerAdapter mAdapter1;
    int type = 0;
    private SpinerPopWindow mSpinerPopWindow1;

    AbPullToRefreshView abPullToRefreshView = null;

    int page=1;
    String klx;
    private TextView title;
    private TextView footerText;
    private TextView titleAdd;

    @Override
    protected int getLayoutId() {
        return R.layout.bsd_hygl_fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        bsd_huiyuanguanli_sskh.setText("");
    }

    @Override
    public void initView() {
        abPullToRefreshView= (AbPullToRefreshView) view.findViewById(R.id.lsfreshview);

        abPullToRefreshView.setOnHeaderRefreshListener(this);
        abPullToRefreshView.setOnFooterLoadListener(this);

        // 设置进度条的样式
        // 设置进度条的样式
        abPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
                getResources().getDrawable(R.drawable.progress_circular));
        abPullToRefreshView.getFooterView().setFooterProgressBarDrawable(
                getResources().getDrawable(R.drawable.progress_circular));

        bsd_hygl_cx = (RelativeLayout) view.findViewById(R.id.bsd_hygl_cx);
        bsd_hygl_cx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type == 0) {
                    Log.i("cjn","这里是已收款查询");
                    data.clear();
                    hygl_yf();
                }
                if (type == 1) {
                    data.clear();
                    hygl_df();
                    Log.i("cjn","这里是未收款查询");
                }
            }
        });

        bsd_kleixin = (TextView) view.findViewById(R.id.bsd_hygl_keixing);
        relativeLayout4444 = (RelativeLayout) view.findViewById(R.id.relativeLayout4444);
        bsd_huiyuanguanli_sskh = (EditText) view.findViewById(R.id.bsd_huiyuanguanli_sskh);
        relativeLayout4444.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGongSi1();
            }
        });
        view22 = view.findViewById(R.id.view22);
        bsd_hygl_yfk = (TextView) view.findViewById(R.id.bsd_hygl_yfk);
        bsd_hygl_dfk = (TextView) view.findViewById(R.id.bsd_hygl_dfk);
        arr_tv = new TextView[2];
        arr_tv[0] = bsd_hygl_yfk;
        arr_tv[1] = bsd_hygl_dfk;
        bsd_hygl_yfk.setOnClickListener(this);
        bsd_hygl_dfk.setOnClickListener(this);
        //会员办理
        bsd_hybl = (LinearLayout) view.findViewById(R.id.ll_lishi);
        //
        bsd_lv1 = (ListView) view.findViewById(R.id.bsd_lv1);
        title = (TextView) view.findViewById(R.id.tv_title);
        titleAdd = (TextView) view.findViewById(R.id.tv_title_lishi);
        footerText = (TextView) view.findViewById(R.id.tv_footertext);
    }

    @Override
    public void initData() {
        title.setText("会员管理");
        titleAdd.setText("办理");
        footerText.setText("公司名称 :   " + MyApplication.shared.getString("GongSiMc", "") +
                "                  公司电话 :   " + MyApplication.shared.getString("danw_dh", ""));
        url = new URLS();
        adapter = new BSD_hygl_adp(getActivity());
        checkHighLight(0);
        popup();
        hygl_yf();
        kaxiala();
    }

    public void popup() {
        //维修项目
        bsd_hybl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bsd_wxxm = new BSD_HYGL_TianJia_delo(getActivity(), getActivity());
                bsd_wxxm.show();
            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bsd_hygl_yfk:
                data.clear();
                type = 0;
                checkHighLight(0);
                hygl_yf();
                adapter.notifyDataSetChanged();
                break;
            case R.id.bsd_hygl_dfk:
                mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
                data.clear();
                type = 1;
                checkHighLight(1);
                hygl_df();
                adapter.notifyDataSetChanged();
                break;
        }
    }

    /**
     * 已付款
     */
    String kh=null;
    String lx=null;
    private void hygl_yf() {
        if (bsd_huiyuanguanli_sskh.getText().toString().length()<0){
            kh=null;
        }else {
            kh=bsd_huiyuanguanli_sskh.getText().toString();
        }
        if (bsd_kleixin.getText().toString().length()<0){
            lx=null;
        }else {
            lx=bsd_kleixin.getText().toString();
        }

        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        AbRequestParams params = new AbRequestParams();
        params.put("card_no", bsd_huiyuanguanli_sskh.getText().toString());
        if (bsd_kleixin.getText().toString().equals("全部")){
            klx="";
        }else {
            klx=bsd_kleixin.getText().toString();
        }
        params.put("cardkind", klx);
        params.put("pageNumber",page);
        Log.i("cjn","card_no=="+kh+"cardkind=="+lx);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_HYGL_YFK, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int stud, String s) {
                Log.i("jjj","这里是已收款查询1111111"+s);
                try {
                    JSONObject jsonObject = new JSONObject(s);

                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        JSONArray json = jsonObject.getJSONArray("data");
                        for (int i = 0; i < json.length(); i++) {
                            JSONObject item = json.getJSONObject(i);
                            BSD_HYGL_ety hygl_ety = new BSD_HYGL_ety();
                            hygl_ety.setKehu_mc(item.optString("kehu_mc"));
                            hygl_ety.setKehu_dh(item.optString("kehu_dh"));
                            hygl_ety.setCard_no(item.optString("card_no"));
                            hygl_ety.setCard_kind(item.optString("card_kind"));
                            hygl_ety.setCard_jlrq(item.optString("card_jlrq"));
                            hygl_ety.setCard_lx(item.optString("card_lx"));
                            hygl_ety.setCard_jb(item.optString("card_jb"));
                            hygl_ety.setCard_jifen(item.optString("card_leftjf"));
                            hygl_ety.setCard_leftje(item.optString("card_leftje"));
                            Log.i("jjj","这里是已收款查询AAAAAAA"+item.getString("id"));
                            data.add(hygl_ety);
                        }
                        Log.i("jjj","这里是已收款查询BBBBB"+data.size());
                        adapter.setList(data);
                        bsd_lv1.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        abPullToRefreshView.onFooterLoadFinish();
                        abPullToRefreshView.onHeaderRefreshFinish();
                    } else {
                        adapter.setList(data);
                        bsd_lv1.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        WeiboDialogUtils.closeDialog(mWeiboDialog);
                        adapter.notifyDataSetChanged();
                        abPullToRefreshView.onFooterLoadFinish();
                        abPullToRefreshView.onHeaderRefreshFinish();
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
                WeiboDialogUtils.closeDialog(mWeiboDialog);
                abPullToRefreshView.onFooterLoadFinish();
                abPullToRefreshView.onHeaderRefreshFinish();

            }
        });

    }

    /**
     * 代付款
     */
    private void hygl_df() {
        AbRequestParams params = new AbRequestParams();
        params.put("card_no", bsd_huiyuanguanli_sskh.getText().toString());
        if (bsd_kleixin.getText().toString().equals("全部")){
            klx="";
        }else {
            klx=bsd_kleixin.getText().toString();
        }

        params.put("cardkind", klx);
        params.put("pageNumber",page);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_HYGL_DFK, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int stud, String s) {
                Log.i("jjj","这里是未收款款查询22222222"+s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        JSONArray json = jsonObject.getJSONArray("data");
                        for (int i = 0; i < json.length(); i++) {
                            JSONObject item = json.getJSONObject(i);
                            BSD_HYGL_ety hygl_ety = new BSD_HYGL_ety();
                            hygl_ety.setKehu_mc(item.optString("kehu_mc"));
                            hygl_ety.setKehu_dh(item.optString("kehu_dh"));
                            hygl_ety.setCard_no(item.optString("card_no"));
                            hygl_ety.setCard_kind(item.optString("card_kind"));
                            hygl_ety.setCard_jlrq(item.optString("card_jlrq"));
                            hygl_ety.setCard_lx(item.optString("card_lx"));
                            hygl_ety.setCard_jb(item.optString("card_jb"));
                            hygl_ety.setCard_jifen(item.optString("card_jifen"));
                            hygl_ety.setCard_leftje(item.optString("card_leftje"));
                            data.add(hygl_ety);
                        }
                        adapter.setList(data);
                        adapter.notifyDataSetChanged();
                        abPullToRefreshView.onFooterLoadFinish();
                        abPullToRefreshView.onHeaderRefreshFinish();
                    }
                    adapter.setList(data);
                    adapter.notifyDataSetChanged();
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                    abPullToRefreshView.onFooterLoadFinish();
                    abPullToRefreshView.onHeaderRefreshFinish();
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
                WeiboDialogUtils.closeDialog(mWeiboDialog);
                adapter.notifyDataSetChanged();
                abPullToRefreshView.onFooterLoadFinish();
                abPullToRefreshView.onHeaderRefreshFinish();
            }
        });

    }

    public void kaxiala() {
        listjbcz.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("type", "ITCardKind");
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_HYGL_ADD_TYPE, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aa, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        Map<String, String> map1 = new HashMap<String, String>();

                        map1.put("id","");
                        map1.put("CardKind","全部");
                        listjbcz.add(map1);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject item = jsonArray.getJSONObject(i);
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("id", item.getString("id"));
                            map.put("CardKind", item.getString("CardKind"));
                            listjbcz.add(map);
                        }
                    }
                    Log.i("cjn","listjbcz========================="+listjbcz.toString());
                    bumen3();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Log.i("cjn", "查看是否请求成功会员管理" + s);
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

    private void showGongSi1() {
        mSpinerPopWindow1.setWidth(relativeLayout4444.getWidth());
        mSpinerPopWindow1.showAsDropDown(relativeLayout4444);
    }

    public void bumen3() {

        nameList1.clear();

        for (int i = 0; i < listjbcz.size(); i++) {
            CustemObject object = new CustemObject();
            object.data = listjbcz.get(i).get("CardKind");
            nameList1.add(object);
        }
        mAdapter1 = new CustemSpinerAdapter(getActivity());
        mAdapter1.refreshData(nameList1, 0);

        mSpinerPopWindow1 = new SpinerPopWindow(getActivity());
        mSpinerPopWindow1.setAdatper(mAdapter1, 310);
        mSpinerPopWindow1.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
            @Override
            public void onItemClick(int pos) {
                String value = nameList1.get(pos).toString();

                Log.i("cjn", "看看是不是这里的问题" + value);
                if (!bsd_kleixin.getText().toString().equals(value)) {
                    bsd_kleixin.setText(value);
                }
            }
        });


    }

    //选中项的变色
    private void checkHighLight(int index) {
        if (index == 0) {
            bsd_hygl_yfk.setTextColor(this.getResources().getColor(R.color.bai));
            bsd_hygl_yfk.setBackground(getResources().getDrawable(R.mipmap.bsd_shixin));

            bsd_hygl_dfk.setTextColor(this.getResources().getColor(R.color.hong));
            bsd_hygl_dfk.setBackground(getResources().getDrawable(R.mipmap.bsd_yuankuang));
        }
        if (index == 1) {
            bsd_hygl_yfk.setTextColor(this.getResources().getColor(R.color.hong));
            bsd_hygl_yfk.setBackground(getResources().getDrawable(R.mipmap.bsd_yuankuang));

            bsd_hygl_dfk.setTextColor(this.getResources().getColor(R.color.bai));
            bsd_hygl_dfk.setBackground(getResources().getDrawable(R.mipmap.bsd_shixin));
        }

    }


    @Override
    public void onHeaderRefresh(AbPullToRefreshView abPullToRefreshView) {
        data.clear();
        page = 1;
        if (  type == 0){
            hygl_yf();
        }else {
            hygl_df();
        }
    }


    @Override
    public void onFooterLoad(AbPullToRefreshView abPullToRefreshView) {
        page++;
        if (  type == 0){
            hygl_yf();
        }else {
            hygl_df();
        }
    }
}
