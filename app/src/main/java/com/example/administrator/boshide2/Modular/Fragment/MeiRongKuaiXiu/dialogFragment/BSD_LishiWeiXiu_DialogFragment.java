package com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.dialogFragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.Entity.WXLS_Bean;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.Entity.WXLS_XM_Bean;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.Entity.WXLS_YL_Bean;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.adapter.WXLS_Adapter;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.adapter.WXLS_WXXM_Adapter;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.adapter.WXLS_WXYL_Adapter;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.QuanQuan.WeiboDialogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-11-14.
 */
public class BSD_LishiWeiXiu_DialogFragment  extends DialogFragment implements View.OnClickListener {
    private static final String PARAMS_KEY = "params_key";
    private Context context;
    private Dialog mWeiboDialog;
    private WXLS_Adapter wxls_adapter;//维修历史
    private List<WXLS_Bean> wxlsBeanList;
    private TextView tv_close;
    private ListView lv_lishi;
    private WXLS_WXXM_Adapter wxxm_adapter;//维修项目
    private List<WXLS_XM_Bean> wxlsXmBeanList;
    private WXLS_WXYL_Adapter wxyl_adapter;//维修用料
    private List<WXLS_YL_Bean> wxlsYlBeanList;
    private String param;
    private boolean xmLoaded = false;
    private boolean clLoaded = false;

    public static BSD_LishiWeiXiu_DialogFragment newInstance(String params) {
        BSD_LishiWeiXiu_DialogFragment dialogFragment = new BSD_LishiWeiXiu_DialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAMS_KEY, params);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        param = getArguments().getString(PARAMS_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.from(context).inflate(R.layout.dialogfragment_lishi_weixiu, null);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        initView(view);
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        //隐藏输入法
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        params.height = LinearLayout.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes(params);
    }

    public void initView(View view) {
        tv_close = (TextView) view.findViewById(R.id.tv_close);
        tv_close.setOnClickListener(this);
        //历史维修主表
        lv_lishi = (ListView) view.findViewById(R.id.lv_lishi_weixiiu);
        wxlsBeanList = new ArrayList<>();
        wxls_adapter = new WXLS_Adapter(getActivity(), wxlsBeanList);
        lv_lishi.setAdapter(wxls_adapter);
        //项目
        ListView lv_wxxmls = (ListView) view.findViewById(R.id.lv_wxxmls);
        wxlsXmBeanList = new ArrayList<>();
        wxxm_adapter = new WXLS_WXXM_Adapter(getActivity(), wxlsXmBeanList);
        lv_wxxmls.setAdapter(wxxm_adapter);
        //用料
        ListView lv_wxclls = (ListView) view.findViewById(R.id.lv_wxclls);
        wxlsYlBeanList = new ArrayList<>();
        wxyl_adapter = new WXLS_WXYL_Adapter(getActivity(), wxlsYlBeanList);
        lv_wxclls.setAdapter(wxyl_adapter);
        lv_lishi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                xmLoaded = false;
                clLoaded = false;
                mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
                wxlsXmBeanList.clear();
                wxlsYlBeanList.clear();
                int firstVisiblePosition = lv_lishi.getFirstVisiblePosition();
                wxls_adapter.setCurrentPositon(position);
                wxls_adapter.notifyDataSetChanged();
                lv_lishi.setSelection(firstVisiblePosition);
                getWXLSYLData(wxlsBeanList.get(position).getDanhao());//用料
                getWXLSXMData(wxlsBeanList.get(position).getDanhao());//项目
            }
        });
        //维修历史主表
        getWXLSData(param);
    }

    /**
     * 维修历史主表数据接口
     */
    private void getWXLSData(final String cheNo) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        AbRequestParams params = new AbRequestParams();
        params.put("che_no", cheNo);
        params.put("caozuoyuan_xm", MyApplication.shared.getString("name", ""));
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_wxls_zbxx, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("message").equals("查询失败")) {
                        Toast.makeText(getActivity(), "未查到历史维修信息", Toast.LENGTH_SHORT).show();
                    } else {
                        JSONArray array = new JSONArray(object.getString("data"));
                        for (int j = 0; j < array.length(); j++) {
                            JSONObject obj = array.getJSONObject(j);
                            WXLS_Bean bean = new WXLS_Bean();
                            bean.setChepai(cheNo);
                            bean.setDanhao(obj.getString("work_no"));
                            bean.setRiqi(obj.getString("xche_jsrq"));
                            bean.setChezhu(obj.getString("kehu_mc"));
                            bean.setDianhua(obj.getString("kehu_sj"));
                            bean.setHuiyuan(obj.getString("card_no"));
                            bean.setJine(obj.getString("xche_ysje"));
                            bean.setLicheng(obj.getString("xche_cclc"));
                            wxlsBeanList.add(bean);
                        }
                        wxls_adapter.notifyDataSetChanged();
                        if (wxlsBeanList.size() > 0) {
                            getWXLSXMData(wxlsBeanList.get(0).getDanhao());//项目
                            getWXLSYLData(wxlsBeanList.get(0).getDanhao());//用料
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
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });
    }



    /**
     * 维修历史项目数据接口
     */
    private void getWXLSXMData(final String work_no) {
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", work_no);
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_wxls_xmmx ,params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("message").equals("查询成功")) {
                        JSONArray array = new JSONArray(object.getString("data"));
                        for (int j = 0; j < array.length(); j++) {
                            JSONObject json = array.getJSONObject(j);
                            WXLS_XM_Bean bean = new WXLS_XM_Bean();
                            bean.setWxxm_no(json.getString("wxxm_no"));
                            bean.setJine(json.getString("wxxm_je"));
                            bean.setWxxiangmu(json.getString("wxxm_mc"));
                            wxlsXmBeanList.add(bean);
                        }
                    }
                    wxxm_adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                xmLoaded = true;
                if (clLoaded) {
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
            public void onFailure(int i, String s, Throwable throwable) {
                xmLoaded = true;
                if (clLoaded) {
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                }
            }
        });
    }

    /**
     * 维修用料数据接口
     */
    private void getWXLSYLData(String work_no) {
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", work_no);
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_wxls_ylmx,params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("message").equals("查询成功")) {
                        JSONArray array = new JSONArray(object.getString("data"));
                        for (int j = 0; j < array.length(); j++) {
                            JSONObject json = array.getJSONObject(j);
                            WXLS_YL_Bean bean = new WXLS_YL_Bean();
                            bean.setPrij_list(json.getString("peij_no"));
                            bean.setDanjia(json.getString("peij_dj"));
                            bean.setGuige(json.getString("peij_th"));
                            bean.setJine(json.getString("peij_je"));
                            bean.setPeijian(json.getString("peij_mc"));
                            bean.setShuliang(json.getString("peij_sl"));
                            wxlsYlBeanList.add(bean);
                        }
                    }
                    wxyl_adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                clLoaded = true;
                if (xmLoaded) {
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
            public void onFailure(int i, String s, Throwable throwable) {
                clLoaded = true;
                if (xmLoaded) {
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_close:
                getDialog().dismiss();
                break;
        }
    }

}



