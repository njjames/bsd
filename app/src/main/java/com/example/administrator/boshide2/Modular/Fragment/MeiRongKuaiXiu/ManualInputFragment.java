package com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.example.administrator.boshide2.Conts;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Activity.MainActivity;
import com.example.administrator.boshide2.Modular.Fragment.BaseFragment;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.Entity.BSD_Car_Entity;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.Entity.BSD_KeHu_Entity;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.dialogFragment.BSD_MeiRongKuaiXiu_cheliangxinxi_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_Entity;
import com.example.administrator.boshide2.Modular.View.KeyBoard.KeyboardUtil;
import com.example.administrator.boshide2.Modular.View.diaog.CustomDialog;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.QuanQuan.WeiboDialogUtils;
import com.example.administrator.boshide2.Tools.Show;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @维修预约键盘输入跳转列表页面 Created by Administrator on 2017-4-13.
 */

public class ManualInputFragment extends BaseFragment {
    public static final String INPUT_LICENSE_COMPLETE = "me.kevingo.licensekeyboard.input.comp";
    public static final String INPUT_LICENSE_KEY = "LICENSE";

    private EditText inputbox1;
    private EditText inputbox2;
    private EditText inputbox3;
    private EditText inputbox4;
    private EditText inputbox5;
    private EditText inputbox6;
    private EditText inputbox7;
    private EditText inputbox8;
    private EditText inputbox9;
    private EditText[]  inputboxs;
    private LinearLayout boxesContainer;
    private KeyboardUtil keyboardUtil;
    private LinearLayout ll_back;
    private Dialog mWeiboDialog;
    private List<BSD_WeiXiuJieDan_Entity>list=new ArrayList<BSD_WeiXiuJieDan_Entity>();
    private URLS url;
    //车牌号前缀
    private String  cpPrefix;
    private int currentEditText;//当前光标
    private TextView footerText;
    private TextView title;

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
                ((MainActivity) getActivity()).upBSD_mrkx_log();
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
                inputbox4, inputbox5, inputbox6, inputbox7, inputbox8, inputbox9};
        //输入车牌完成后的intent过滤器
        IntentFilter finishFilter = new IntentFilter(INPUT_LICENSE_COMPLETE);
        if (cpPrefix.length() > 0) {
            currentEditText = cpPrefix.length();
        } else {
            currentEditText = 0;
        }
        keyboardUtil = new KeyboardUtil(getActivity(), new EditText[]{inputbox1, inputbox2, inputbox3,
                inputbox4, inputbox5, inputbox6, inputbox7, inputbox8, inputbox9}, view, currentEditText);
        keyboardUtil.showKeyboard();
        keyboardUtil.setOnClickYes(new KeyboardUtil.OnClickYes() {
            @Override
            public void onYesClick(String license) {
                Conts.cp = license;
                checkCarNoCanused(license);
                Conts.danju_type="mrkx";
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
        for (int i = 0; i < inputboxs.length; i++) {
            final int finalI = i;
            inputboxs[i].setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    inputboxs[finalI].setFocusable(false);
//                  int inType =   inputboxs[finalI].getInputType(); // backup the input type
//                  inputboxs[finalI].setInputType(InputType.TYPE_NULL); // disable soft input
//                  inputboxs[finalI].onTouchEvent(event); // call native handler
//                  inputboxs[finalI].setInputType(inType); // restore input type
//                  inputboxs[finalI].setSelection(  inputboxs[finalI].getText().length());
                    return true;
                }
            });
        }
        title = (TextView) view.findViewById(R.id.tv_title);
        footerText = (TextView) view.findViewById(R.id.tv_footertext);
    }

    private void checkCarNoCanused(String license) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        AbRequestParams params = new AbRequestParams();
        params.put("che_no", license);
        params.put("caozuoyuanID", MyApplication.shared.getString("id", ""));
        Request.Post(MyApplication.shared.getString("ip", "")+url.CHECK_CAR_CANUSED, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    boolean canUsedType = jsonObject.getBoolean("data");
                    if (canUsedType) {
                        //跳转到编辑车辆、客户信息对话框
                        BSD_MeiRongKuaiXiu_cheliangxinxi_Fragment.newInstance("")
                                .show(getFragmentManager(), "dialog_fragment");
                    } else {   // 这种情况是有这个车，但是没有权限
                        showTipsDialog("此车已存在，但是您没有查询权限");
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
            }
        });
    }

    private void showTipsDialog(String tips) {
        CustomDialog.Builder builder = new CustomDialog.Builder(getHostActicity());
        CustomDialog dialog = builder.style(R.style.mydialog)
                .view(R.layout.queren)
                .widthDimenRes(R.dimen.qb_px_300)
                .heightDimenRes(R.dimen.qb_px_174)
                .addViewOnclick(R.id.bsd_queren_queren, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CustomDialog.dismissDialog();
                    }
                })
                .build();
        TextView tv_tips = (TextView) dialog.getView().findViewById(R.id.bsd_queren_neirong);
        tv_tips.setText(tips);
        dialog.show();
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
        for (int i = 0; i < inputboxs.length; i++) {
            inputboxs[i].setText("");
        }

        if (cpPrefix.length() > 0) {
            Log.i("cpqz", "onResume:=-===cpPrefix== " + cpPrefix.charAt(0));
            for (int j = 0; j < cpPrefix.length(); j++) {
                inputboxs[j].setText(cpPrefix.charAt(j) + "");
            }
        }
        //设置光标到指定位置；
//        inputboxs[cpPrefix.length()].setFocusable(true);
//        inputboxs[cpPrefix.length()].setFocusableInTouchMode(true);
//        inputboxs[cpPrefix.length()].requestFocus();
//        inputboxs[cpPrefix.length()].findFocus();


    }

//    /*
//    *根据车牌返回车辆信息和客户信息
//    */
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
//                      //客户实体
//                        JSONObject   kehuObject=jsonarray.getJSONObject("kehu");
//                        BSD_KeHu_Entity  kehuentiy = new BSD_KeHu_Entity();
//                        Conts.kehu_no = kehuObject.getString("kehu_no");
//
//                        kehuentiy.setKehu_mc(kehuObject.getString("kehu_mc"));
//                        kehuentiy.setKehu_lxr(kehuObject.getString("kehu_xm"));
//                        kehuentiy.setKehu_shouji(kehuObject.getString("kehu_sj"));
//                        kehuentiy.setKehu_dianhua(kehuObject.getString("kehu_dh"));
//
//                       //车辆实体
//                         JSONObject   carObject=jsonarray.getJSONObject("cheliang");
//                         BSD_Car_Entity   carentity=new BSD_Car_Entity();
//                         Conts.cp = carObject.getString("che_no");
//                         carentity.setChe_no(carObject.getString("che_no"));
//                         carentity.setChe_vin(carObject.getString("che_vin"));
//                         carentity.setChe_color(carObject.getString("che_wxys"));
//                         carentity.setChe_gcrq(carObject.getString("che_gcrq"));
//                         carentity.setChe_nf(carObject.getString("che_nf"));
//                         Log.e("mr", "年份sss："+carObject.getString("che_nf") );
//                         carentity.setChe_jqxrq(carObject.getString("che_jiaoqx_dqrq"));
//                         carentity.setChe_syxdq(carObject.getString("che_shangyex_dqrq"));
//                         carentity.setChe_xcbyrq(carObject.getString("che_next_byrq"));
//                         carentity.setChe_xcjcrq(carObject.getString("che_jianche_dqrq"));
//                         carentity.setChe_pinpai(carObject.getString("che_cx"));  //车系、车型、品牌、车组是一个字段；
//                        //传了个客户实体
//                        ((MainActivity) getActivity()).setKhentity(kehuentiy);
//                        //传了个车辆实体
//                        ((MainActivity) getActivity()).setCarentity(carentity);
//                        Log.e("mr", "...... " );
//                        //跳转到编辑车辆、客户信息界面
//                        Conts.danju_type="mrkx";
//
//
////                        ((MainActivity) getActivity()).upBSD_mrkx_clxx();
//                        //跳转到编辑车辆、客户信息对话框
//                        //隐藏输入法
//                        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
//                                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//
//                        new BSD_MeiRongKuaiXiu_cheliangxinxi_Fragment()
//                                .show(getFragmentManager(), "dialog_fragment");
//
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
//    public void data(final String cp, final View view){
//        list.clear();
//        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
//        AbRequestParams params = new AbRequestParams();
//        params.put("che_no",cp);
//        params.put("gongsiNo", MyApplication.shared.getString("GongSiNo", ""));
//        params.put("caozuoyuan_xm",MyApplication.shared.getString("name", ""));
//        Log.i("cjn","维修接单车牌是"+cp+"gongsiNO"+MyApplication.shared.getString("GongSiNo", "")
//        +"------caozuoyuan_xm"+MyApplication.shared.getString("name", ""));
//
//        Request.Post(MyApplication.shared.getString("ip", "")+url.BSD_MRKX_IN, params, new AbStringHttpResponseListener() {
//            @Override
//            public void onSuccess(int aa, String s) {
//
//                Log.i("jjj","成功1"+s);
//
//                try {
//                    JSONObject jsonObject = new JSONObject(s);
//                    if (jsonObject.get("message").toString().equals("查询成功")) {
//                        JSONArray jsonarray = jsonObject.getJSONArray("data");
//                        for (int i = 0; i < jsonarray.length(); i++) {
//                            //这块拿到的是维系接单的详细表
//                            JSONObject item = jsonarray.getJSONObject(i);
//                            BSD_WeiXiuJieDan_Entity entiy = new BSD_WeiXiuJieDan_Entity();
//                            entiy.setWork_no(item.getString("work_no"));
//                            entiy.setKehu_no(item.getString("kehu_no"));
//                            entiy.setKehu_mc(item.getString("kehu_mc"));
//                            entiy.setKehu_xm(item.getString("kehu_xm"));
//                            entiy.setKehu_dz(item.getString("kehu_dz"));
//                            entiy.setKehu_yb(item.getString("kehu_yb"));
//                            entiy.setKehu_dh(item.getString("kehu_dh"));
//                            entiy.setChe_no(item.getString("che_no"));
//                            entiy.setChe_cx(item.getString("che_cx"));
//                            entiy.setChe_vin(item.getString("che_vin"));
//                            entiy.setXche_lc(item.getInt("xche_lc"));
//                            entiy.setXche_jdrq(item.getString("xche_jdrq"));
//                            entiy.setXche_sfbz(item.getString("xche_sfbz"));
//                            entiy.setXche_sffl(item.getDouble("xche_sffl"));
//                            entiy.setCangk_dm(item.getString("cangk_dm"));
//                            entiy.setCangk_mc(item.getString("cangk_mc"));
//                            entiy.setGcsj(item.getString("gcsj"));
//
//                            list.add(entiy);
//
//                            Log.i("jjj","1111111111111"+list.get(0).getKehu_mc());
//                        }
//                        WeiboDialogUtils.closeDialog(mWeiboDialog);
//                        if (jsonObject.get("total").toString().equals("1")) {
//                            BSD_WeiXiuJieDan_Entity entiy = new BSD_WeiXiuJieDan_Entity();
//                            entiy = list.get(0);
//                            Conts.work_no=entiy.getWork_no();
//                            Log.i("jjj","成功asaaaaaaaaaaaaaaaa"+list.get(0).getKehu_mc());
//                            ((MainActivity) getActivity()).setWxjdentity(list.get(0));//传了个实体
////
//                            if (null==entiy.getWork_no()||entiy.getWork_no().equals("")||entiy.getWork_no().equals("null")){
//                                Toast.makeText(getActivity(),"网络超时请重试",Toast.LENGTH_SHORT).show();
//                            }   else {
//                                ((MainActivity) getActivity()).upBSD_mrkx();
//                            }
//
//                            Conts.zt = 1;
//                            Conts.cp = cp;
//
//                        } else if (jsonObject.get("total").toString().equals("0")) {
//                            ((MainActivity) getActivity()).upBSD_mrkx();
//                            //请求
//
//                            Conts.cp = cp;
//                            Conts.zt = 0;
//
//
//                        } else {
//
//                            BSD_WeiXiuJieDan_Entity entiy = new BSD_WeiXiuJieDan_Entity();
//                            entiy = list.get(0);
//                            ((MainActivity) getActivity()).setWxjdentity(entiy);//传了个实体
////
//                            ((MainActivity) getActivity()).upBSD_mrkx();
//                            Conts.zt = 1;
//                            Conts.cp = cp;
//                        }
//                    }else {
//                        Show.showTime(getActivity(), jsonObject.get("message").toString());
//                    }
//
//                    //在这里请求
//
//
//
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
//                Log.i("cjn","失败"+s);
//                WeiboDialogUtils.closeDialog(mWeiboDialog);
//            }
//        });
//
//
//    }
//

}