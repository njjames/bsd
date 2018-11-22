package com.example.administrator.boshide2.Modular.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.example.administrator.boshide2.Conts;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Activity.MainActivity;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.dialogFragment.BSD_MeiRongKuaiXiu_cheliangxinxi_Fragment;
import com.example.administrator.boshide2.Modular.View.diaog.OCRInfoDialog;
import com.example.administrator.boshide2.Modular.View.diaog.QueRen;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.DensityUtil;
import com.example.administrator.boshide2.Tools.OcrUtil;
import com.example.administrator.boshide2.Tools.PermissionUtils;
import com.example.administrator.boshide2.Tools.PermissionsManager;
import com.example.administrator.boshide2.Tools.QuanQuan.WeiboDialogUtils;
import com.example.administrator.boshide2.Tools.Show;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

/**
 * 选择车的界面
 * 包括车牌识别，手动输入，行车本识别
 */
public class ChooseCarFragment extends BaseFragment implements View.OnClickListener {
    private static final String PARAM_KEY = "param_key";
    private ImageView iv_zdsb;
    private ImageView iv_sdsr;
    private ImageView iv_xcbsb;
    private String filename;
    private String carNo;
    private LinearLayout ll_historyInfo;
    private TextView title;
    private TextView historyTitle;
    private TextView footerText;
    private OCRInfoDialog mOCRInfoDialog;
    private String billType;
    private MainActivity mMainActivity;
    private Dialog mWeiboDialog;
    private QueRen mQueRen;

    public static ChooseCarFragment newInstance(String type) {
        ChooseCarFragment fragment = new ChooseCarFragment();
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
        return R.layout.fragment_choose_car_layout;
    }

    @Override
    public void initView() {
        PermissionUtils.camera(getActivity(), new PermissionUtils.OnPermissionResult() {
            @Override
            public void onGranted() {

            }
        });
        iv_zdsb = (ImageView) view.findViewById(R.id.bsd_im_zdsb);
        iv_sdsr = (ImageView) view.findViewById(R.id.bsd_im_sdsr);
        iv_xcbsb = (ImageView) view.findViewById(R.id.bsd_im_xcbsb);
        title = (TextView) view.findViewById(R.id.tv_title);
        historyTitle = (TextView) view.findViewById(R.id.tv_title_lishi);
        footerText = (TextView) view.findViewById(R.id.tv_footertext);
        ll_historyInfo = (LinearLayout) view.findViewById(R.id.ll_lishi);
        ll_historyInfo.setOnClickListener(this);
        iv_sdsr.setOnClickListener(this);
        iv_zdsb.setOnClickListener(this);
        iv_xcbsb.setOnClickListener(this);
    }

    @Override
    public void initData() {
        mMainActivity = (MainActivity) getHostActicity();
        initTitle();
    }

    private void initTitle() {
        switch (billType) {
            case Conts.BILLTYPE_MRKX:
                title.setText("美容快修");
                historyTitle.setText("历史快修");
                break;
            case Conts.BILLTYPE_KSBJ:
                title.setText("快速报价");
                historyTitle.setText("历史报价");
                break;
            case Conts.BILLTYPE_WXYY:
                title.setText("维修预约");
                historyTitle.setText("历史预约");
                break;
            case Conts.BILLTYPE_WXJD:
                title.setText("维修接单");
                historyTitle.setText("历史接单");
                break;
        }
        String gongSiMc = MyApplication.shared.getString("GongSiMc", "");
        String danw_dh = MyApplication.shared.getString("danw_dh", "");
        footerText.setText("公司名称：" + gongSiMc + " | 公司电话：" + danw_dh);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_lishi:
                showHistoryInfo();
                break;
            case R.id.bsd_im_zdsb:
                Intent intentOCR = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentOCR, Conts.REQUESTCODE_OCR);
                break;
            case R.id.bsd_im_sdsr:
                mMainActivity.showManualInputFragment(billType);
                break;
            case R.id.bsd_im_xcbsb:
                Intent intentXCB = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentXCB, Conts.REQUESTCODE_OCR);
                break;
        }
    }

    private void showHistoryInfo() {
        switch (billType) {
            case Conts.BILLTYPE_MRKX:  // 历史快修
                mMainActivity.upLSKX();
                break;
            case Conts.BILLTYPE_KSBJ:  // 历史报价
                mMainActivity.upLSBJ();
                break;
            case Conts.BILLTYPE_WXYY:  // 历史预约
                mMainActivity.upLSYY();
                break;
            case Conts.BILLTYPE_WXJD:  // 历史接单
                mMainActivity.upLSJD();
                break;
        }
    }

    //打开相机返回
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        DensityUtil.setCustomDensity(getHostActicity(), getHostActicity().getApplication());
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
                Toast.makeText(getHostActicity(), "SD卡不可用", Toast.LENGTH_SHORT).show();
                return;
            }
            String name = new DateFormat().format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
            FileOutputStream b = null;
            File file = new File("/sdcard/myImage/");
            file.mkdirs();// 创建文件夹
            filename = "/sdcard/myImage/" + name;
            try {
                b = new FileOutputStream(filename);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (b != null) {
                        b.flush();
                        b.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (requestCode == Conts.REQUESTCODE_OCR) {
                new Thread() {
                    @Override
                    public void run() {
                        //把网络访问的代码放在这里
                        String ocrData = OcrUtil.getOcrData(filename, MyApplication.shared.getString("ocrUrl", ""));
                        try {
                            JSONObject jsonObject = new JSONObject(ocrData);
                            if (jsonObject.get("error_code").toString().equals("000000")) {
                                JSONObject json = (JSONObject) jsonObject.get("result");
                                carNo = json.getString("PlateNo");
                                handler.sendMessage(handler.obtainMessage(10));
                            } else if (jsonObject.get("error_code").toString().equals("900501")) {
                                handler.sendMessage(handler.obtainMessage(11));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            } else if (requestCode == Conts.REQUESTCODE_XCB) {
                new Thread() {
                    @Override
                    public void run() {
                        String ocrData = OcrUtil.getOcrData(filename, MyApplication.shared.getString("ocrUrlxcb", ""));
                        try {
                            JSONObject jsonObject = new JSONObject(ocrData);
                            if (jsonObject.get("error_code").toString().equals("000000")) {
                                JSONObject json = (JSONObject) jsonObject.get("result");
                                carNo = json.getString("PlateNo");
                                Conts.VIN = json.getString("Vin");
                                String b = json.getString("VehicleModels");
                                b = b.replace("牌", "");
                                Conts.BSD_chexing = b;
                                handler.sendMessage(handler.obtainMessage(10));
                            } else if (jsonObject.get("error_code").toString().equals("900501")) {
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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 10) { // 表示获取车牌号成功
                mOCRInfoDialog = new OCRInfoDialog(getActivity(), carNo);
                mOCRInfoDialog.show();
                mOCRInfoDialog.setOnBackListener(new OCRInfoDialog.OnBackListener() {
                    @Override
                    public void onConfirm(String chepai) {
                        mOCRInfoDialog.dismiss();
                        checkCarCanused(chepai);
                    }
                });
            }
            if (msg.what == 11) {
                Show.showTime(getActivity(), "车牌识别失败,请重新拍照！");
            }
        }
    };

    /**
     * 检测车牌是否可用
     * 如果可以用，会显示出车辆信息窗口
     * 如果不可用，就是显示此车存在，但是没有权限
     * @param cheNo
     */
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
                        mQueRen = new QueRen(getContext(), "此车已存在，但是您没有查询权限");
                        mQueRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                            @Override
                            public void onYesClick() {
                                mQueRen.dismiss();
                            }
                        });
                        mQueRen.show();
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
}