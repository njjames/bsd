package com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
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
    private static final String PARAM_KEY = "param_key";
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
    private String billType; // 单据类型

    public static ManualInputFragment newInstance(String type) {
        ManualInputFragment fragment = new ManualInputFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_KEY, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        billType = getArguments().getString(PARAM_KEY);
    }

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
                back();
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
            public void onYesClick(String cheNo) {
                Conts.cp = cheNo;
                checkCarCanused(cheNo);
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

    private void back() {
        switch (billType) {
            case Conts.BILLTYPE_MRKX:
                ((MainActivity) getActivity()).upBSD_mrkx_log();
                break;
            case Conts.BILLTYPE_KSBJ:
                ((MainActivity) getActivity()).upBSD_KSBJ_Log();
                break;
            case Conts.BILLTYPE_WXJD:
                ((MainActivity) getActivity()).upBSD_WXJD_log();
                break;
            case Conts.BILLTYPE_WXYY:
                ((MainActivity) getActivity()).upBSD_WXyy_log();
                break;
        }
    }

    private void checkCarCanused(final String cheNo) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        AbRequestParams params = new AbRequestParams();
        params.put("che_no", cheNo);
        params.put("caozuoyuanID", MyApplication.shared.getString("id", ""));
        Request.Post(MyApplication.shared.getString("ip", "")+URLS.CHECK_CAR_CANUSED, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    boolean canUsedType = jsonObject.getBoolean("data");
                    if (canUsedType) {
                        //跳转到编辑车辆、客户信息对话框
                        BSD_MeiRongKuaiXiu_cheliangxinxi_Fragment.newInstance(cheNo, billType,"")
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
    }

}