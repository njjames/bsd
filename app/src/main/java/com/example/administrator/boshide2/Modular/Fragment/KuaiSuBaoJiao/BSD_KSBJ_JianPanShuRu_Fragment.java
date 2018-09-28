package com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.boshide2.Conts;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Activity.MainActivity;
import com.example.administrator.boshide2.Modular.Fragment.BaseFragment;
import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.Entity.BSD_KuaiSuBaoJia_ety;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.dialogFragment.BSD_MeiRongKuaiXiu_cheliangxinxi_Fragment;
import com.example.administrator.boshide2.Modular.View.KeyBoard.KeyboardUtil;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.Show;

import java.util.ArrayList;
import java.util.List;

/**
 * @会员管理碎片页 Created by Administrator on 2017-4-13.
 */

public class BSD_KSBJ_JianPanShuRu_Fragment extends BaseFragment {
    private Dialog mWeiboDialog;
    public static final String INPUT_LICENSE_COMPLETE = "me.kevingo.licensekeyboard.input.comp";
    public static final String INPUT_LICENSE_KEY = "LICENSE";

    private EditText inputbox1, inputbox2,
            inputbox3, inputbox4,
            inputbox5, inputbox6, inputbox7, inputbox8, inputbox9;
    private LinearLayout boxesContainer;
    private KeyboardUtil keyboardUtil;
    private LinearLayout ll_back;
    private List<BSD_KuaiSuBaoJia_ety> list = new ArrayList<BSD_KuaiSuBaoJia_ety>();

    URLS url;
    //车牌号前缀
    private String  cpPrefix;
    private int currentEditText=0;//当前光标
    private EditText[]  inputboxs;
    private TextView title;
    private TextView footerText;

    @Override
    protected int getLayoutId() {
        return R.layout.bsd_wxyy_jpsr_fragment;
    }

    @Override
    public void initView() {
        //车牌号前缀
        cpPrefix= MyApplication.shared.getString("cpPrefix","");
        ll_back = (LinearLayout) view.findViewById(R.id.bsd_lsbj_fanhui);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).upBSD_KSBJ_Log(view);
            }
        });
        inputbox1 = (EditText) view.findViewById(R.id.et_car_license_inputbox1);
        inputbox2 = (EditText) view.findViewById(R.id.et_car_license_inputbox2);
        inputbox3 = (EditText) view.findViewById(R.id.et_car_license_inputbox3);
        inputbox4 = (EditText) view.findViewById(R.id.et_car_license_inputbox4);
        inputbox5 = (EditText) view.findViewById(R.id.et_car_license_inputbox5);
        inputbox6 = (EditText) view.findViewById(R.id.et_car_license_inputbox6);
        inputbox7 = (EditText) view.findViewById(R.id.et_car_license_inputbox7);
        inputbox8 = (EditText) view.findViewById(R.id.et_car_license_inputbox8);
        inputbox9 = (EditText) view.findViewById(R.id.et_car_license_inputbox9);
        boxesContainer = (LinearLayout) view.findViewById(R.id.ll_car_license_inputbox_content);
        inputboxs=  new EditText[]{inputbox1, inputbox2, inputbox3,
                inputbox4, inputbox5, inputbox6, inputbox7, inputbox8, inputbox9 };
        //输入车牌完成后的intent过滤器
        IntentFilter finishFilter = new IntentFilter(INPUT_LICENSE_COMPLETE);
        if(cpPrefix.length()>0){
            currentEditText=cpPrefix.length();
        }else {
            currentEditText=0;
        }
        keyboardUtil = new KeyboardUtil(getActivity(), new EditText[]{inputbox1, inputbox2, inputbox3,
                inputbox4, inputbox5, inputbox6, inputbox7, inputbox8, inputbox9 }, view ,currentEditText);
        keyboardUtil.showKeyboard();
        keyboardUtil.setOnClickYes(new KeyboardUtil.OnClickYes() {
            @Override
            public void onYesClick(String license) {
//                data(license, view);
//                cheoruser(license);
                Conts.cp = license;
                //跳转到编辑车辆、客户信息界面
                Conts.danju_type="ksbj";

                //跳转到编辑车辆、客户信息对话框
                new BSD_MeiRongKuaiXiu_cheliangxinxi_Fragment()
                        .show(getFragmentManager(), "dialog_fragment");
            }
        });
        final BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String license = intent.getStringExtra(INPUT_LICENSE_KEY);
                if (license != null && license.length() > 0) {
                    boxesContainer.setVisibility(View.GONE);
                    if (keyboardUtil != null) {
                        keyboardUtil.hideKeyboard();
                    }

                    Show.showTime(getActivity(), "车牌号为" + license);
                }
                getActivity().unregisterReceiver(this);
            }
        };
        getActivity().registerReceiver(receiver, finishFilter);
        //设置edittext不能获取焦点
        for(int  i=0;i<inputboxs.length;i++){
            final int finalI = i;
            inputboxs[i].setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    inputboxs[finalI].setFocusable(false);
                    return true;
                }
            });
        }
        title = (TextView) view.findViewById(R.id.tv_title);
        footerText = (TextView) view.findViewById(R.id.tv_footertext);
    }

    @Override
    public void initData() {
        url=new URLS();
        title.setText("输入车牌");
        footerText.setText("公司名称 :   " + MyApplication.shared.getString("GongSiMc", "") +
                "                  公司电话 :   " + MyApplication.shared.getString("danw_dh", ""));
    }

    @Override
    public void onResume() {
        super.onResume();
        for(int  i=0;i<inputboxs.length;i++){
            inputboxs[i].setText("");
        }

        if(cpPrefix.length()>0){
            Log.i("cpqz", "onResume:=-===cpPrefix== "+cpPrefix.charAt(0));
            for (int  j=0;j<cpPrefix.length();j++){
                inputboxs[j].setText(cpPrefix.charAt(j)+"");
            }
        }
    }



    /*
  *根据车牌返回车辆信息和客户信息
  */
//    public void cheoruser(String license) {
//        Log.e("mr", "获取用户no方法" );
//        AbRequestParams params = new AbRequestParams();
//        params.put("che_no", license);
//        Request.Post(MyApplication.shared.getString("ip", "")+url.BSD_wxjd_clandkh, params, new AbStringHttpResponseListener() {
//            @Override
//            public void onSuccess(int a, String s) {
//                try {
//                    JSONObject jsonObject = new JSONObject(s);
//                    Log.e("mr", "onSuccess查询车辆客户返回值: "+s );
//                    Toast.makeText(getActivity(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
//                    if (jsonObject.get("status").toString().equals("1")) {
//                        JSONObject jsonarray = jsonObject.getJSONObject("data");
//                        //客户实体
//                        JSONObject   kehuObject=jsonarray.getJSONObject("kehu");
//                        BSD_KeHu_Entity kehuentiy = new BSD_KeHu_Entity();
//                        Conts.kehu_no = kehuObject.getString("kehu_no");
//
//                        kehuentiy.setKehu_mc(kehuObject.getString("kehu_mc"));
//                        kehuentiy.setKehu_lxr(kehuObject.getString("kehu_xm"));
//                        kehuentiy.setKehu_shouji(kehuObject.getString("kehu_sj"));
//                        kehuentiy.setKehu_dianhua(kehuObject.getString("kehu_dh"));
//
//                        //车辆实体
//                        JSONObject   carObject=jsonarray.getJSONObject("cheliang");
//                        BSD_Car_Entity carentity=new BSD_Car_Entity();
//                        Conts.cp = carObject.getString("che_no");
//                        carentity.setChe_no(carObject.getString("che_no"));
//                        carentity.setChe_vin(carObject.getString("che_vin"));
//                        carentity.setChe_color(carObject.getString("che_wxys"));
//                        carentity.setChe_gcrq(carObject.getString("che_gcrq"));
//                        carentity.setChe_nf(carObject.getString("che_nf"));
//                        carentity.setChe_jqxrq(carObject.getString("che_jiaoqx_dqrq"));
//                        carentity.setChe_syxdq(carObject.getString("che_shangyex_dqrq"));
//                        carentity.setChe_xcbyrq(carObject.getString("che_next_byrq"));
//                        carentity.setChe_xcjcrq(carObject.getString("che_jianche_dqrq"));
//                        carentity.setChe_pinpai(carObject.getString("che_cx"));  //车系、车型、品牌、车组是一个字段；
//                        //传了个客户实体
//                        ((MainActivity) getActivity()).setKhentity(kehuentiy);
//                        //传了个车辆实体
//                        ((MainActivity) getActivity()).setCarentity(carentity);
//                        Log.e("mr", "...... " );
//                        //跳转到编辑车辆、客户信息界面
//                        Conts.danju_type="ksbj";
////                        ((MainActivity) getActivity()).upBSD_mrkx_clxx();
//
//                        //跳转到编辑车辆、客户信息对话框
//                        new BSD_MeiRongKuaiXiu_cheliangxinxi_Fragment()
//                                .show(getFragmentManager(), "dialog_fragment");
//
//
//
//                    }
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onStart() {
//
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//
//            @Override
//            public void onFailure(int i, String s, Throwable throwable) {
//                Log.e("mr", "onFailure方法");
//            }
//        });
//
//
//    }
//
//
//
//
//    /**
//     * 请求数据一共有几条数据
//     *
//     * @param carp
//     */
//    public void data(final String carp, final View view) {
//        list.clear();
//        mWeiboDialog = WeiboDialogUtils.createLoadingDialog
//                (getActivity(), "加载中...");
//
//        AbRequestParams params = new AbRequestParams();
//        params.put("pai", carp);
//        params.put("gongsiNo", MyApplication.shared.getString("GongSiNo", ""));
//        params.put("caozuoyuan_xm", MyApplication.shared.getString("name", ""));
//        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_ksbj_jbxx, params, new AbStringHttpResponseListener() {
//            @Override
//            public void onSuccess(int a, String s) {
//                Log.i("cjn", "查看返回的json=" + s.toString());
//                try {
//                    JSONObject jsonObject = new JSONObject(s.toString());
//                    if (jsonObject.get("status").toString().equals("1")) {
//                        JSONArray jsonarray = jsonObject.getJSONArray("data");
//                        for (int i = 0; i < jsonarray.length(); i++) {
//                            JSONObject item = jsonarray.getJSONObject(i);
//                            BSD_KuaiSuBaoJia_ety entity = new BSD_KuaiSuBaoJia_ety();
//                            entity.setReco_no(item.getDouble("reco_no"));
//                            entity.setList_no(item.getString("list_no"));
//                            entity.setList_sfbz(item.getString("List_sfbz"));
//                            entity.setList_sffl(item.getDouble("List_sffl"));
//                            entity.setKehu_no(item.getString("kehu_no"));
//                            entity.setKehu_mc(item.getString("kehu_mc"));
//                            entity.setKehu_xm(item.getString("kehu_xm"));
//                            entity.setKehu_dh(item.getString("kehu_dh"));
//                            entity.setChe_no(item.getString("che_no"));
//                            entity.setChe_vin(item.getString("che_vin"));
//                            entity.setChe_cx(item.getString("che_cx"));
//                            entity.setList_czy(item.getString("List_czy"));
//                            entity.setGongSiNo(item.getString("GongSiNo"));
//                            entity.setGongSiMc(item.getString("GongSiMc"));
//                            entity.setWork_no(item.getString("work_no"));
//                            entity.setList_jlrq(item.getString("List_jlrq"));
//                            entity.setList_yjjclc(item.getInt("List_yjjclc"));
//                            entity.setList_hjje(item.getDouble("List_hjje"));
//                            entity.setGcsj(item.getString("che_gcrq"));
//                            entity.setList_lc(Double.parseDouble(item.getString("List_lc")));
//
//
//
//                            list.add(entity);
//                            Log.i("cjn", "查看这个的编号" + item.getString("list_no"));
//                        }
//                        WeiboDialogUtils.closeDialog(mWeiboDialog);
//                    } else {
//                        Show.showTime(getActivity(), jsonObject.get("message").toString());
//                    }
//                    if (jsonObject.get("total").toString().equals("1")) {
//
//                        BSD_KuaiSuBaoJia_ety entiy = new BSD_KuaiSuBaoJia_ety();
//                        entiy = list.get(0);
//                        ((MainActivity) getActivity()).setKsbjenity(entiy);//传了个实体
////
//                        if (null==entiy.getList_no()||entiy.getList_no().equals("")||entiy.getList_no().equals("null")){
//                            Toast.makeText(getActivity(),"",Toast.LENGTH_SHORT).show();
//                        }else {
//                            ((MainActivity) getActivity()).upksbj(view);
//                        }
//                        Conts.zt = 1;
//                        Conts.cp = carp;
//
//                    } else if (jsonObject.get("total").toString().equals("0")) {
//                        ((MainActivity) getActivity()).upksbj(view);
//                        Conts.cp = carp;
//                        Conts.zt = 0;
//
//                    } else {
//                        BSD_KuaiSuBaoJia_ety entiy = new BSD_KuaiSuBaoJia_ety();
//                        entiy = list.get(0);
//                        ((MainActivity) getActivity()).setKsbjenity(entiy);//传了个实体
////
//                        ((MainActivity) getActivity()).upksbj(view);
//                        Conts.zt = 1;
//                        Conts.cp = carp;
//                    }
//                    //在这里请求
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onStart() {
//
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//
//            @Override
//            public void onFailure(int i, String s, Throwable throwable) {
//                WeiboDialogUtils.closeDialog(mWeiboDialog);
//                Show.showTime(getActivity(), "网路链接超时");
//
//            }
//        });
//
//    }


}