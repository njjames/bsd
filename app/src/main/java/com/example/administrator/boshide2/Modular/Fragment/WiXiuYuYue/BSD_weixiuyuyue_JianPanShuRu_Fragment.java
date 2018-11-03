package com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue;

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
import android.widget.RelativeLayout;

import com.example.administrator.boshide2.Conts;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Activity.MainActivity;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.dialogFragment.BSD_MeiRongKuaiXiu_cheliangxinxi_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.Entity.BSD_WeiXiuYueYue_entiy;
import com.example.administrator.boshide2.Modular.View.KeyBoard.KeyboardUtil;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.Show;

import java.util.ArrayList;
import java.util.List;

/**
 * @维修预约键盘输入跳转列表页面 Created by Administrator on 2017-4-13.
 */

public class BSD_weixiuyuyue_JianPanShuRu_Fragment extends Fragment {

    public static final String INPUT_LICENSE_COMPLETE = "me.kevingo.licensekeyboard.input.comp";
    public static final String INPUT_LICENSE_KEY = "LICENSE";

    private EditText inputbox1, inputbox2,
            inputbox3, inputbox4,
            inputbox5, inputbox6, inputbox7, inputbox8, inputbox9,inputbox10;
    private LinearLayout boxesContainer;
    private KeyboardUtil keyboardUtil;
    private RelativeLayout relativeLayout22;
    private List<BSD_WeiXiuYueYue_entiy> list = new ArrayList<BSD_WeiXiuYueYue_entiy>();
    private Dialog mWeiboDialog;
    URLS url;
    //车牌号前缀
    private String  cpPrefix;
    private int currentEditText=0;//当前光标
    private EditText[]  inputboxs;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bsd_wxjd_jpsr_fragment, null);
        url=new URLS();
        init(view);
        return view;
    }


    public void init(final View view) {
        //车牌号前缀
        cpPrefix= MyApplication.shared.getString("cpPrefix","");
        relativeLayout22 = (RelativeLayout) view.findViewById(R.id.relativeLayout22);

        relativeLayout22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((MainActivity) getActivity()).upBSD_WXYY_log();
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
        inputbox10 = (EditText) view.findViewById(R.id.et_car_license_inputbox10);
        boxesContainer = (LinearLayout) view.findViewById(R.id.ll_car_license_inputbox_content);
        inputboxs=  new EditText[]{inputbox1, inputbox2, inputbox3,
                inputbox4, inputbox5, inputbox6, inputbox7, inputbox8, inputbox9, inputbox10};
        //输入车牌完成后的intent过滤器
        IntentFilter finishFilter = new IntentFilter(INPUT_LICENSE_COMPLETE);
        if(cpPrefix.length()>0){
            currentEditText=cpPrefix.length();
        }else {
            currentEditText=0;
        }
        Log.i("kkkk", "initView: cpPrefix的长度---"+cpPrefix.length());
        keyboardUtil = new KeyboardUtil(getActivity(), new EditText[]{inputbox1, inputbox2, inputbox3,
                inputbox4, inputbox5, inputbox6, inputbox7, inputbox8, inputbox9, inputbox10}, view ,currentEditText);
        keyboardUtil.showKeyboard();
        keyboardUtil.setOnClickYes(new KeyboardUtil.OnClickYes() {
            @Override
            public void onYesClick(String license) {
//                cheoruser(license);
//                data(license, view);
                Conts.cp = license;
                Conts.danju_type="wxyy";
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
//                        Conts.danju_type="wxyy";
////                        ((MainActivity) getActivity()).upBSD_mrkx_clxx();
//
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
//    /**
//     * 请求数据一共有几条数据
//     *
//     * @param carp
//     */
//    public void data(final String carp, final View view) {
//        list.clear();
//        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
//        AbRequestParams params = new AbRequestParams();
//        params.put("pai", carp);
//        params.put("gongsiNo", MyApplication.shared.getString("GongSiNo", ""));
//        params.put("caozuoyuan_xm",MyApplication.shared.getString("name", ""));
//        Request.Post(MyApplication.shared.getString("ip", "")+url.BSD_wxyy_LieBiao, params, new AbStringHttpResponseListener() {
//            @Override
//            public void onSuccess(int a, String s) {
//                Log.i("cjn","查看数据！！！！！！！！！"+s);
//                try {
//                    JSONObject jsonObject = new JSONObject(s);
//                    if (jsonObject.get("message").toString().equals("查询成功")) {
//                        JSONArray jsonarray = jsonObject.getJSONArray("data");
//                        for (int i = 0; i < jsonarray.length(); i++) {
//                            JSONObject item = jsonarray.getJSONObject(i);
//                            BSD_WeiXiuYueYue_entiy entity = new BSD_WeiXiuYueYue_entiy();
//                            entity.setYuyue_no(item.getString("yuyue_no"));
//                            entity.setKehu_mc(item.getString("kehu_mc"));
//                            entity.setKehu_dh(item.getString("kehu_dh"));
//                            entity.setChe_no(item.getString("che_no"));
//                            entity.setChe_vin(item.getString("che_vin"));
//                            entity.setChe_cx(item.getString("che_cx"));
//                            entity.setYuyue_yjjcrq(item.getString("yuyue_yjjcrq"));
//                            entity.setYuyue_yjjclc(item.getInt("yuyue_yjjclc"));
//                            entity.setYuyue_jlrq(item.getString("yuyue_jlrq"));
//                            entity.setYuyue_sffl(item.getDouble("yuyue_sffl"));
//                            entity.setYuyue_sfbz(item.getString("yuyue_sfbz"));
//                            entity.setGcsj(item.getString("gcsj"));
//                            list.add(entity);
//                        }
//
//                        WeiboDialogUtils.closeDialog(mWeiboDialog);
//                    } else {
//                        WeiboDialogUtils.closeDialog(mWeiboDialog);
//                        Show.showTime(getActivity(), jsonObject.get("message").toString());
//                    }
//
//                    if (jsonObject.get("total").toString().equals("1")) {
//                        BSD_WeiXiuYueYue_entiy entiy = new BSD_WeiXiuYueYue_entiy();
//                        entiy = list.get(0);
//                        ((MainActivity) getActivity()).setEntiy(entiy);//传了个实体
//                        if (null==entiy.getYuyue_no()||entiy.getYuyue_no().equals("")||entiy.getYuyue_no().equals("null")){
//                            Toast.makeText(getActivity(),"网络超时请重试",Toast.LENGTH_SHORT).show();
//                        }else {
//                            ((MainActivity) getActivity()).upyuyue(view);
//                        }
//
//
//
//                        Conts.zt = 1;
//                        Conts.cp = carp;
//
//                    } else if (jsonObject.get("total").toString().equals("0")) {
//
//                        ((MainActivity) getActivity()).upyuyue(view);
//                        //请求
//
//                        Conts.cp = carp;
//                        Conts.zt = 0;
//
//                        Log.i("cjn", "这里是没有数据操作");
//                    } else {
//
//                        BSD_WeiXiuYueYue_entiy entiy = new BSD_WeiXiuYueYue_entiy();
//                        entiy = list.get(0);
//                        entiy = list.get(0);
//                        ((MainActivity) getActivity()).setEntiy(entiy);//传了个实体
////
//                        ((MainActivity) getActivity()).upyuyue(view);
//                        Log.i("cjn", "这里是一条数据操作+" + entiy.getYuyue_no().toString());
//                        Conts.zt = 1;
//                        Conts.cp = carp;
//                        Log.i("cjn", "这里是两条数据以上操作");
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
//            }
//        });
//
//    }




}