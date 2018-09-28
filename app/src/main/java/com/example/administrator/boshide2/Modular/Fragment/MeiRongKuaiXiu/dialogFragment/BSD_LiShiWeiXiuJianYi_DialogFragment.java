package com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.dialogFragment;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.example.administrator.boshide2.Conts;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Activity.MainActivity;
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
 * Created by Administrator on 2017-11-16.
 */

public class BSD_LiShiWeiXiuJianYi_DialogFragment extends DialogFragment implements View.OnClickListener{
    private Context context;
    private URLS url;
    private Dialog mWeiboDialog,xmWeiboDialog,ylWeiboDialog;
    WXLS_Adapter wxls_adapter;//维修历史
    List<WXLS_Bean> wxls_been;
    //    WXDH_Adapter wxdh_adapter;//单号
    Button but_yes;
    ListView lv_lishi;
    BSD_WeiXiuJieDan_Entity entityWxjd;   //美容快修、维修接单、维修业务调度都用的这个bean；
    BSD_KuaiSuBaoJia_ety entityKsbj;
    BSD_WeiXiuYueYue_entiy entityWxyy;
    String   type;  //用于区分是从哪类单据跳转过来的
    String   chePai;

    WXLS_WXXM_Adapter wxxm_adapter;//维修项目
    List<WXLS_XM_Bean> wxxm_beans;
    WXLS_WXYL_Adapter wxyl_adapter;//维修用料
    List<WXLS_YL_Bean> wxyl_beans;
    HashMap<Integer, Boolean> isSelected;//项目选中
    List<String> wxxms;//存维修项目标识
    HashMap<Integer, Boolean> isChecked;//用料选中
    List<String> wxyls;//存维修用料标识


    private  TextView  tv_title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        context=getActivity();
        url=new URLS();

        Log.i("wxls", "onCreateView: ");
        View view = inflater.from(context).inflate(R.layout.dialogfragment_lishi_weixiu, null);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        chePai= Conts.cp;
        initView(view);
        return  view;
    }



    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        //隐藏输入法
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }





    public  void   initView(View  view){

        tv_title= (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText("历史维修建议");

        but_yes= (Button) view.findViewById(R.id.btn_yes);
        but_yes.setOnClickListener(this);

        //历史维修主表
        lv_lishi = (ListView) view.findViewById(R.id.lv_lishi_weixiiu);
        wxls_been = new ArrayList<>();
        wxls_adapter = new WXLS_Adapter(getActivity(), wxls_been);
        lv_lishi.setAdapter(wxls_adapter);

        setWXLSJYData();

        //项目
        ListView lv_danhao = (ListView) view.findViewById(R.id.lv_danhao);
        wxxm_beans = new ArrayList<>();
        isSelected = new HashMap<>();
        wxxms = new ArrayList<>();
        wxxm_adapter = new WXLS_WXXM_Adapter(getActivity(), wxxm_beans, isSelected);
        lv_danhao.setAdapter(wxxm_adapter);
        //用料数据
        ListView lv_yongliao = (ListView) view.findViewById(R.id.lv_yongliao);
        wxyl_beans = new ArrayList<>();
        isChecked = new HashMap<>();
        wxyls = new ArrayList<>();
        wxyl_adapter = new WXLS_WXYL_Adapter(getActivity(), wxyl_beans, isChecked);
        lv_yongliao.setAdapter(wxyl_adapter);
        lv_lishi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                wxxm_beans.clear();
                wxyl_beans.clear();
//                tv_work_no.setText(wxls_been.get(position).getDanhao());
                Log.i("wxlsjydh", "onItemClick: 单号="+wxls_been.get(position).getDanhao());
                setWXJYdhData(wxls_been.get(position).getDanhao());
                setWXJYylData(wxls_been.get(position).getDanhao());
            }
        });

    }




    /**
     * 维修历史主表数据接口
     */
    private void setWXLSJYData() {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        AbRequestParams params = new AbRequestParams();
        params.put("che_no", chePai);
        Log.i("wxlsjy", "查询主表che_no=="+chePai);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxlsjy_zbxx, params,new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("wxlsjy", "onSuccess: 查询主表成功=="+s);
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("message").equals("查询失败")) {
                        Toast.makeText(getActivity(), "未查到维修建议信息", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    } else {
                        JSONArray array = new JSONArray(object.getString("data"));
                        for (int j = 0; j < array.length(); j++) {
                            JSONObject obj = array.getJSONObject(j);
                            WXLS_Bean bean = new WXLS_Bean();
                            bean.setDanhao(obj.getString("work_no"));
                            bean.setRiqi(obj.getString("rq"));
                            bean.setChepai(chePai);
                            bean.setChezhu(obj.getString("kehu_mc"));
                            bean.setDianhua(obj.getString("kehu_sj"));
                            bean.setHuiyuan(obj.getString("card_no"));
                            bean.setJine(obj.getString("xche_ysje"));
                            bean.setLicheng(obj.getString("xche_cclc"));
                            wxls_been.add(bean);
                        }
                    }
                    wxls_adapter.notifyDataSetChanged();
                    WeiboDialogUtils.closeDialog(mWeiboDialog);

                    if(wxls_been.size()>0){
                        setWXJYdhData(wxls_been.get(0).getDanhao());
                        setWXJYylData(wxls_been.get(0).getDanhao());
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
                Log.i("wxlsjy", "onFailure:请求主表失败了"+s.toString());
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });
    }


    /**
     * 维修建议用料数据接口
     */
    private void setWXJYylData(String work_no) {
        ylWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", work_no);
        Log.i("wxls", "查询用料work_no=" + work_no);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxjy_ylmx, params,new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.e("维修建议用料", s);
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
                            bean.setWxriqi(json.getString("jianyi_rq"));
                            bean.setShuliang(json.getString("peij_sl"));
                            isChecked.put(wxyl_beans.size(), false);
                            wxyl_beans.add(bean);
                        }
                    } else {
//                        Toast.makeText(getActivity(), "无用料", Toast.LENGTH_SHORT).show();
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

    /**
     * 维修建议项目数据接口
     */
    private void setWXJYdhData(final String work_no) {
        xmWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", work_no);
        Log.i("wxls", "查询项目work_no=" + work_no);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxjy_xmmx, params,new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("message").equals("查询成功")) {
                        JSONArray array = new JSONArray(object.getString("data"));
                        for (int j = 0; j < array.length(); j++) {
                            JSONObject json = array.getJSONObject(j);
                            WXLS_XM_Bean bean = new WXLS_XM_Bean();
                            bean.setWxriqi(json.getString("jianyi_rq"));
                            bean.setJine(json.getString("wxxm_je"));
                            bean.setWxxiangmu(json.getString("wxxm_mc"));
                            bean.setWxxm_no(json.getString("wxxm_no"));
                            isSelected.put(wxxm_beans.size(), false);
                            wxxm_beans.add(bean);
                        }
                    } else {
//                        Toast.makeText(getActivity(), "无项目", Toast.LENGTH_SHORT).show();
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
                WeiboDialogUtils.closeDialog(xmWeiboDialog);
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
