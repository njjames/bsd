package com.example.administrator.boshide2.Modular.Fragment.BaoYangChaXun;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.boshide2.Conts;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Activity.MainActivity;
import com.example.administrator.boshide2.Modular.Fragment.BaoYangChaXun.Adapter.BSD_bycx_adp;
import com.example.administrator.boshide2.Modular.View.diaog.BSD_Byaoyang_diaog;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.DensityUtil;
import com.example.administrator.boshide2.Tools.OcrbaoyangUtil;
import com.example.administrator.boshide2.Tools.QuanQuan.WeiboDialogUtils;
import com.example.administrator.boshide2.Tools.Show;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @我的管理碎片页 Created by Administrator on 2017-4-13.
 */

public class BSD_BaoYangChaXun_Fragment extends DialogFragment implements View.OnClickListener {
    private static final String PARAM_KEY_CHEXING = "param_key_chexing";
    private static final String PARAM_KEY_LICHENG = "param_key_licheng";
    private static final String PARAM_KEY_TIME = "param_key_time";
    private Dialog mWeiboDialog;
    String remark, byState;
    List<Map<String, String>> list = new ArrayList<>();
    BSD_bycx_adp adp;
    ListView bsd_bycx_lv;
    TextView bsd_bycx_top1_tv, bsd_bycx_top_tv;
    TextView bsd_bycx_youpin;
    List<Map<String, String>> list1 = new ArrayList<>();
    BSD_Byaoyang_diaog bsd_byaoyang_diaog;
    TextView tv_close;
    String cuowuxinxi;
    int shihoushibai = 0;
    private View view;
    private String param_cheXing;
    private String param_liCheng;
    private String param_time;

    public static BSD_BaoYangChaXun_Fragment newInstance(String cheXing, String liCheng, String time) {
        BSD_BaoYangChaXun_Fragment fragment = new BSD_BaoYangChaXun_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_KEY_CHEXING, cheXing);
        bundle.putString(PARAM_KEY_LICHENG, liCheng);
        bundle.putString(PARAM_KEY_TIME, time);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        param_cheXing = getArguments().getString(PARAM_KEY_CHEXING);
        param_liCheng = getArguments().getString(PARAM_KEY_CHEXING);
        param_time = getArguments().getString(PARAM_KEY_CHEXING);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bsd_baoyangchaxun_fragment, null);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        initView();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        params.height = LinearLayout.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes(params);
    }

    private void initView() {
        bsd_bycx_lv = (ListView) view.findViewById(R.id.bsd_bycx_lv);
        bsd_bycx_top1_tv = (TextView) view.findViewById(R.id.bsd_bycx_top1_tv);
        bsd_bycx_top_tv = (TextView) view.findViewById(R.id.bsd_bycx_top_tv);
        bsd_bycx_youpin = (TextView) view.findViewById(R.id.bsd_bycx_youpin);
        tv_close = (TextView) view.findViewById(R.id.tv_close);
        tv_close.setOnClickListener(this);
        bsd_bycx_youpin.setOnClickListener(this);
        adp = new BSD_bycx_adp(getActivity(), list);
        bsd_bycx_lv.setAdapter(adp);
    }

    public void initData() {
        list.clear();
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        new Thread() {
            @Override
            public void run() {
                //接口地址
                //http://www.jy-epc.com/api-show/NqAfterMarketDataServlet
                String apiUrl = MyApplication.shared.getString("apiUrl", "");
                //账号?requestCode=300101&operatorCode=&operatorPwd=
                String username = MyApplication.shared.getString("username", "");
                //密码
                String userpassword = MyApplication.shared.getString("userpassword", "");
                //请求参数
                Map map = new HashMap();
                map.put("vehicleCode", param_cheXing);
                map.put("currentMileage", param_liCheng);
                map.put("purchaseDate", param_time);
                map.put("maintainCount", "10");
                //调用接口
                String data = OcrbaoyangUtil.getDate(apiUrl, username, userpassword, "300101", map);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    if (jsonObject.get("error_code").toString().equals("000000")) {
                        remark = jsonObject.getString("remark");
                        byState = jsonObject.getString("byState");
                        JSONObject object = jsonObject.getJSONObject("result");
                        JSONArray jsonArray = object.getJSONArray("xmbylxList");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject item = jsonArray.getJSONObject(i);
                            Map<String, String> map1 = new HashMap<String, String>();
                            map1.put("bylc", item.getString("bylc"));
                            map1.put("cfzdj", item.getString("cfzdj"));
                            map1.put("yl", item.getString("yl"));
                            map1.put("ycljh", item.getString("ycljh"));
                            map1.put("byxmmc", item.getString("byxmmc"));
                            map1.put("byxmlx", item.getString("byxmlx"));
                            map1.put("dw", item.getString("dw"));
                            map1.put("gsf", item.getString("gsf"));
                            map1.put("ljid", item.getString("ljid"));
                            map1.put("subtotal", item.getString("subtotal"));
                            list.add(map1);
                        }
                        handler.sendMessage(handler.obtainMessage(10));
                    } else {
                        shihoushibai = 1;
                        cuowuxinxi = jsonObject.get("reason").toString();
                        handler.sendMessage(handler.obtainMessage(11));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 10) {
                bsd_bycx_top1_tv.setText(remark);
                bsd_bycx_top_tv.setText(byState);
                adp.notifyDataSetChanged();
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            } else if (msg.what == 11) {
                remark = "";
                byState = "";
                bsd_bycx_top1_tv.setText("");
                bsd_bycx_top_tv.setText("");
                Show.showTime(getActivity(), "查询保养数据失败");
                adp.notifyDataSetChanged();
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            } else if (msg.what == 12) {
                if (list1.get(0) != null || list1.get(0).get("vehicleName").toString().equals("")) {
                    bsd_byaoyang_diaog = new BSD_Byaoyang_diaog(getActivity(), list1.get(0).get("vehicleName"), list1.get(0).get("brandName")
                            , list1.get(0).get("familyName"), list1.get(0).get("jcycf"), list1.get(0).get("ypdj"), list1.get(0).get("ndjb"));
                    bsd_byaoyang_diaog.show();
                } else {
                    Show.showTime(getActivity(), "网络连接超时");
                }
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        }

    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_close:
                getDialog().dismiss();
                break;
            case R.id.bsd_bycx_youpin:
                getData();
                break;
        }
    }

    private void getData() {
        list1.clear();
        if (shihoushibai == 1) {
            Show.showTime(getActivity(), "查询保养数据失败");
        } else {
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
            new Thread() {
                @Override
                public void run() {
                    //接口地址
                    //http://www.jy-epc.com/api-show/NqAfterMarketDataServlet
                    String apiUrl = MyApplication.shared.getString("apiUrl", "");
                    //账号?requestCode=300101&operatorCode=&operatorPwd=
                    String username = MyApplication.shared.getString("username", "");
                    //密码
                    String userpassword = MyApplication.shared.getString("userpassword", "");
                    //请求参数
                    Map map = new HashMap();
                    map.put("vehicleCode", Conts.bycx_chexing);
//                map.put("currentMileage", Conts.bycx_licheng);
//                map.put("purchaseDate", Conts.bycx_time);
//                map.put("maintainCount", "10");
                    //调用接口
                    String data = OcrbaoyangUtil.getDate(apiUrl, username, userpassword, "300102", map);
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        if (jsonObject.get("error_code").toString().equals("000000")) {
                            JSONObject object = jsonObject.getJSONObject("result");
                            JSONArray jsonArray = object.getJSONArray("vehicleList");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject item = jsonArray.getJSONObject(i);
                                Map<String, String> map1 = new HashMap<String, String>();
                                map1.put("vehicleName", item.getString("vehicleName"));
                                map1.put("brandName", item.getString("brandName"));
                                map1.put("familyName", item.getString("familyName"));
                                map1.put("jcycf", item.getString("jcycf"));
                                map1.put("ypdj", item.getString("ypdj"));
                                map1.put("ndjb", item.getString("ndjb"));
                                list1.add(map1);
                            }
                            handler.sendMessage(handler.obtainMessage(12));
                        } else {
                            handler.sendMessage(handler.obtainMessage(11));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }
}