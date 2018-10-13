package com.example.administrator.boshide2.Modular.Fragment.HuiYuanGuanLi;

import android.app.Dialog;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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

public class BSD_huiyuanguanli_Fragment extends BaseFragment implements AbPullToRefreshView.OnHeaderRefreshListener, AbPullToRefreshView.OnFooterLoadListener {
    ListView bsd_lv1;
    BSD_hygl_adp adapter;
    List<BSD_HYGL_ety> data = new ArrayList<BSD_HYGL_ety>();
    LinearLayout bsd_hybl;
    //项目维修
    BSD_HYGL_TianJia_delo bsd_wxxm;
    //转圈
    private Dialog mWeiboDialog;
    private URLS url;
    private EditText et_cardno;
    private TextView tv_cardkind;
    private LinearLayout ll_cardkind;
    private TextView tv_search;
    List<Map<String, String>> listCradKind = new ArrayList<Map<String, String>>();
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
    private SwitchCompat sw_shoukuan;

    @Override
    protected int getLayoutId() {
        return R.layout.bsd_hygl_fragment;
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
        sw_shoukuan = (SwitchCompat) view.findViewById(R.id.sw_shoukuan);
        sw_shoukuan.setChecked(true);
        sw_shoukuan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                data.clear();
                page = 1;
                if (b) {
                    hygl_yf();
                } else {
                    hygl_df();
                }
            }
        });
        tv_search = (TextView) view.findViewById(R.id.tv_search);
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                page = 1;
                data.clear();
                if (sw_shoukuan.isChecked()) {
                    hygl_yf();
                } else  {
                    hygl_df();
                }
            }
        });

        tv_cardkind = (TextView) view.findViewById(R.id.tv_cardkind);
        ll_cardkind = (LinearLayout) view.findViewById(R.id.ll_cardkind);
        et_cardno = (EditText) view.findViewById(R.id.et_cardno);
        ll_cardkind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCardKindData();
            }
        });
        //会员办理
        bsd_hybl = (LinearLayout) view.findViewById(R.id.ll_lishi);
        bsd_hybl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bsd_wxxm = new BSD_HYGL_TianJia_delo(getActivity());
                bsd_wxxm.show();
            }
        });
        //
        bsd_lv1 = (ListView) view.findViewById(R.id.bsd_lv1);
        adapter = new BSD_hygl_adp(getActivity(), data);
        bsd_lv1.setAdapter(adapter);
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
        getCardKind();
        data.clear();
        hygl_yf();
    }

    /**
     * 已付款
     */
    private void hygl_yf() {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        AbRequestParams params = new AbRequestParams();
        params.put("card_no", et_cardno.getText().toString());
        if (tv_cardkind.getText().toString().equals("全部")){
            klx = "";
        }else {
            klx = tv_cardkind.getText().toString();
        }
        params.put("cardkind", klx);
        params.put("pageNumber", page);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_HYGL_YFK, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int stud, String s) {
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
                            data.add(hygl_ety);
                        }
                    } else {
                        subPage();
                    }
                } catch (JSONException e) {
                    subPage();
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
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

    /**
     * 代付款
     */
    private void hygl_df() {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        AbRequestParams params = new AbRequestParams();
        params.put("card_no", et_cardno.getText().toString());
        if (tv_cardkind.getText().toString().equals("全部")){
            klx = "";
        }else {
            klx = tv_cardkind.getText().toString();
        }
        params.put("cardkind", klx);
        params.put("pageNumber", page);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_HYGL_DFK, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int stud, String s) {
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
                    } else {
                        subPage();
                    }
                } catch (JSONException e) {
                    subPage();
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
                WeiboDialogUtils.closeDialog(mWeiboDialog);
                abPullToRefreshView.onFooterLoadFinish();
                abPullToRefreshView.onHeaderRefreshFinish();
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
                adapter.notifyDataSetChanged();
                abPullToRefreshView.onFooterLoadFinish();
                abPullToRefreshView.onHeaderRefreshFinish();
            }
        });

    }

    public void getCardKind() {
        listCradKind.clear();
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
                        listCradKind.add(map1);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject item = jsonArray.getJSONObject(i);
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("id", item.getString("id"));
                            map.put("CardKind", item.getString("CardKind"));
                            listCradKind.add(map);
                        }
                    }
                    updateCardKindData();
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

    private void showCardKindData() {
        mSpinerPopWindow1.setWidth(ll_cardkind.getWidth());
        mSpinerPopWindow1.showAsDropDown(ll_cardkind);
    }

    public void updateCardKindData() {
        nameList1.clear();
        for (int i = 0; i < listCradKind.size(); i++) {
            CustemObject object = new CustemObject();
            object.data = listCradKind.get(i).get("CardKind");
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
                if (!tv_cardkind.getText().toString().equals(value)) {
                    tv_cardkind.setText(value);
                }
            }
        });
    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView abPullToRefreshView) {
        data.clear();
        page = 1;
        if (sw_shoukuan.isChecked()){
            hygl_yf();
        }else {
            hygl_df();
        }
    }


    @Override
    public void onFooterLoad(AbPullToRefreshView abPullToRefreshView) {
        page++;
        if (sw_shoukuan.isChecked()){
            hygl_yf();
        }else {
            hygl_df();
        }
    }

    public void subPage() {
        if (page > 1) {
            page--;
        }
    }
}
