package com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.dialogFragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.example.administrator.boshide2.Conts;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.Entity.BSD_KuaiSuBaoJia_ety;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.Entity.WXLS_Bean;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.Entity.WXLS_XM_Bean;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.Entity.WXLS_YL_Bean;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.adapter.WXLS_Adapter;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.adapter.WXLS_WXXM_Adapter;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.adapter.WXLS_WXYL_Adapter;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_Entity;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.Entity.BSD_WeiXiuYueYue_entiy;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.QuanQuan.WeiboDialogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017-11-14.
 */

public class BSD_LishiWeiXiu_DialogFragment  extends DialogFragment implements View.OnClickListener {
    private static final String PARAMS_KEY = "params_key";
    private Context context;
    private URLS url;
    private Dialog mWeiboDialog,xmWeiboDialog,ylWeiboDialog;
    private WXLS_Adapter wxls_adapter;//维修历史
    private List<WXLS_Bean> wxlsBeanList;
    private Button but_yes;
    private ListView lv_lishi;
    private BSD_WeiXiuJieDan_Entity entityWxjd;   //美容快修、维修接单、维修业务调度都用的这个bean；
    private BSD_KuaiSuBaoJia_ety entityKsbj;
    private BSD_WeiXiuYueYue_entiy entityWxyy;
    private String type;  //用于区分是从哪类单据跳转过来的
    private String chePai;
    private WXLS_WXXM_Adapter wxxm_adapter;//维修项目
    private List<WXLS_XM_Bean> wxlsXmBeanList;
    private WXLS_WXYL_Adapter wxyl_adapter;//维修用料
    private List<WXLS_YL_Bean> wxlsYlBeanList;
    private HashMap<Integer, Boolean> isSelected;//项目选中
    private List<String> wxxms;//存维修项目标识
    private HashMap<Integer, Boolean> isChecked;//用料选中
    private List<String> wxyls;//存维修用料标识
    private String param;
    private int currentPosition = 0; // 维修历史默认的位置

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
        url = new URLS();
        View view = inflater.from(context).inflate(R.layout.dialogfragment_lishi_weixiu, null);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        type = Conts.danju_type;
        initView(view);
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
//        getDialog().getWindow().setLayout(getContext().getResources().getDimensionPixelSize(R.dimen.x600), getContext().getResources().getDimensionPixelSize(R.dimen.y700));
        //隐藏输入法
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
//        params.width = (int) getActivity().getResources().getDimension(R.dimen.x480);
//        params.height = (int) getActivity().getResources().getDimension(R.dimen.y820);
//        params.width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        WindowManager manager = getActivity().getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
//        WindowManager windowManager =
//                (WindowManager) getActivity().getApplication().getSystemService(Context.
//                        WINDOW_SERVICE);
//        Display display = windowManager.getDefaultDisplay();
//        Point outPoint = new Point();
//        display.getRealSize(outPoint);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = height;
        getDialog().getWindow().setAttributes(params);
    }


    public void initView(View view) {
        but_yes = (Button) view.findViewById(R.id.btn_yes);
        but_yes.setOnClickListener(this);

        //历史维修主表
        lv_lishi = (ListView) view.findViewById(R.id.lv_lishi_weixiiu);
        wxlsBeanList = new ArrayList<>();
        wxls_adapter = new WXLS_Adapter(getActivity(), wxlsBeanList);
        lv_lishi.setAdapter(wxls_adapter);

        // 都不要了，就按传进来的参数查询
//        if ("mrkx".equals(type) | "wxjd".equals(type) | "wxywdd".equals(type)) {    //美容快修、维修接单、业务调度用的是同一个bean；
//            entityWxjd = new BSD_WeiXiuJieDan_Entity();
//            entityWxjd = ((MainActivity) getActivity()).getWxjdentity();
//            Log.i("wxls", "entityWxjd  null???= " + entityWxjd);
//            chePai = entityWxjd.getChe_no();
//            Log.i("wxls", "initView:111里的type= " + type);
//        } else if ("wxyy".equals(type)) {
//            entityWxyy = new BSD_WeiXiuYueYue_entiy();
//            entityWxyy = ((MainActivity) getActivity()).getEntiy();
//            chePai = entityWxyy.getChe_no();
//            Log.i("wxls", "initView:222里的type= " + type);
//        } else if ("ksbj".equals(type)) {
//            entityKsbj = new BSD_KuaiSuBaoJia_ety();
//            entityKsbj = ((MainActivity) getActivity()).getKsbjenity();
//            chePai = entityKsbj.getChe_no();
//            Log.i("wxls", "initView:333里的type= " + type);
//        }

        //项目
        ListView lv_danhao = (ListView) view.findViewById(R.id.lv_danhao);
        wxlsXmBeanList = new ArrayList<>();
        isSelected = new HashMap<>();
        wxxm_adapter = new WXLS_WXXM_Adapter(getActivity(), wxlsXmBeanList, isSelected);
        lv_danhao.setAdapter(wxxm_adapter);
        //用料
        ListView lv_yongliao = (ListView) view.findViewById(R.id.lv_yongliao);
        wxlsYlBeanList = new ArrayList<>();
        isChecked = new HashMap<>();
        wxyl_adapter = new WXLS_WXYL_Adapter(getActivity(), wxlsYlBeanList, isChecked);
        lv_yongliao.setAdapter(wxyl_adapter);

        lv_lishi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                wxlsXmBeanList.clear();
                wxlsYlBeanList.clear();
                currentPosition = position;
                int firstVisiblePosition = lv_lishi.getFirstVisiblePosition();
                wxls_adapter.setCurrentPositon(position);
                wxls_adapter.notifyDataSetChanged();
                lv_lishi.setSelection(firstVisiblePosition);
                setWXYLData(wxlsBeanList.get(position).getDanhao(), wxlsBeanList.get(position).getRiqi());//用料
                setWXLSXMData(wxlsBeanList.get(position).getDanhao(), wxlsBeanList.get(position).getRiqi());//项目
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
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxls_zbxx, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("wxls", "onSuccess: 查询主表成功==" + s);
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("message").equals("查询失败")) {
//                        Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();
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
                    }
                    wxls_adapter.notifyDataSetChanged();
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                    if (wxlsBeanList.size() > 0) {
                        setWXLSXMData(wxlsBeanList.get(0).getDanhao(), wxlsBeanList.get(0).getRiqi());//项目
                        setWXYLData(wxlsBeanList.get(0).getDanhao(), wxlsBeanList.get(0).getRiqi());//用料
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
                Log.i("wxls", "onFailure:请求主表失败了" + s.toString());
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });
    }



    /**
     * 维修历史项目数据接口
     */
    private void setWXLSXMData(final String work_no, final String riqi) {
        xmWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载项目中...");
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", work_no);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxls_xmmx ,params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                try {
                    Log.i("wxls", "查项目成功" + s);
                    JSONObject object = new JSONObject(s);
                    if (object.getString("message").equals("查询成功")) {
                        JSONArray array = new JSONArray(object.getString("data"));
                        for (int j = 0; j < array.length(); j++) {
                            JSONObject json = array.getJSONObject(j);
                            WXLS_XM_Bean bean = new WXLS_XM_Bean();
                            bean.setWxriqi(riqi);
                            bean.setWxxm_no(json.getString("wxxm_no"));
                            bean.setJine(json.getString("wxxm_je"));
                            bean.setWxxiangmu(json.getString("wxxm_mc"));
                            isSelected.put(wxlsXmBeanList.size(), false);
                            wxlsXmBeanList.add(bean);
                        }
                    }
                    wxxm_adapter.notifyDataSetChanged();
                    WeiboDialogUtils.closeDialog(xmWeiboDialog);
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
                Log.i("wxls", "onFailure: 网络请求失败了");
                WeiboDialogUtils.closeDialog(xmWeiboDialog);
            }
        });
    }



    /**
     * 维修用料数据接口
     */
    private void setWXYLData(String work_no, final String riqi) {
        ylWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载材料中...");
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", work_no);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxls_ylmx,params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                try {
                    Log.i("wxls", "查用料成功" + s);
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
                            bean.setWxriqi(riqi);
                            bean.setShuliang(json.getString("peij_sl"));
                            isChecked.put(wxlsYlBeanList.size(), false);
                            wxlsYlBeanList.add(bean);
                        }
                    }
                    wxyl_adapter.notifyDataSetChanged();
                    WeiboDialogUtils.closeDialog(ylWeiboDialog);
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
                WeiboDialogUtils.closeDialog(ylWeiboDialog);
            }
        });
    }





    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case   R.id.btn_yes:
                getDialog().dismiss();
                break;

        }
    }

}



