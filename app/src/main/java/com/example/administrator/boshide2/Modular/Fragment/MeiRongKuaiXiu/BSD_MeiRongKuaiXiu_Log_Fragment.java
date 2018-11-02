package com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
import com.example.administrator.boshide2.Modular.Fragment.BaseFragment;
import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.PopWinow.BSD_ksbj_chexins;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.Entity.BSD_Car_Entity;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.Entity.BSD_KeHu_Entity;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.dialogFragment.BSD_MeiRongKuaiXiu_cheliangxinxi_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_Entity;
import com.example.administrator.boshide2.Modular.View.diaog.OCRInfoDialog;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.OcrUtil;
import com.example.administrator.boshide2.Tools.PermissionUtils;
import com.example.administrator.boshide2.Tools.PermissionsManager;
import com.example.administrator.boshide2.Tools.Show;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * @美容保修三块 Created by Administrator on 2017-4-13.
 */

public class BSD_MeiRongKuaiXiu_Log_Fragment extends BaseFragment {
    private BSD_WeiXiuJieDan_Entity entiy = new BSD_WeiXiuJieDan_Entity();
    private List<HashMap<String, String>> listche = new ArrayList<>();
    private BSD_ksbj_chexins bsd_ksbj_chexins;
    private ImageView bsd_im_zdsb, bsd_im_sdsr, bsd_im_xcbsb;
    private String filename;
    private String chepai;
    private String chepaihao;
    private Dialog mWeiboDialog;
    private LinearLayout bsd_mrkx_lishikuaiuxi;
    private List<BSD_WeiXiuJieDan_Entity> list = new ArrayList<BSD_WeiXiuJieDan_Entity>();
    private String name = "";
    private URLS url;
    private TextView title;
    private TextView titleLishi;
    private TextView footerText;
    private OCRInfoDialog mOCRInfoDialog;

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
        url = new URLS();
        bsd_im_zdsb = (ImageView) view.findViewById(R.id.bsd_im_zdsb);
        bsd_im_sdsr = (ImageView) view.findViewById(R.id.bsd_im_sdsr);
        bsd_im_xcbsb = (ImageView) view.findViewById(R.id.bsd_im_xcbsb);
        title = (TextView) view.findViewById(R.id.tv_title);
        titleLishi = (TextView) view.findViewById(R.id.tv_title_lishi);
        footerText = (TextView) view.findViewById(R.id.tv_footertext);
        bsd_mrkx_lishikuaiuxi = (LinearLayout) view.findViewById(R.id.ll_lishi);
        bsd_mrkx_lishikuaiuxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).upLSKX();
            }
        });
        bsd_im_sdsr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).showManualInputFragment(Conts.BILLTYPE_MRKX);
            }
        });
        bsd_im_zdsb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, Conts.REQUESTCODE_OCR);
            }
        });
        bsd_im_xcbsb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String status = Environment.getExternalStorageState();
                if (status.equals(Environment.MEDIA_MOUNTED)) {
                    try {
                        File dir = new File("/sdcard/myImage/");
                        if (!dir.exists())
                            dir.mkdirs();
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        name = new DateFormat().format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
                        File f = new File(dir, name);
                        Uri u = Uri.fromFile(f);
                        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
                        startActivityForResult(intent, Conts.REQUESTCODE_XCB);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getHostActicity(), "SD卡不可用", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void initData() {
        title.setText("美容快修");
        titleLishi.setText("历史快修");
        footerText.setText("公司名称 :   " + MyApplication.shared.getString("GongSiMc", "") +
                "                  公司电话 :   " + MyApplication.shared.getString("danw_dh", ""));
    }

    //打开相机返回
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Conts.REQUESTCODE_OCR) {
                getCardNoByOCR(data);
            } else if (requestCode == Conts.REQUESTCODE_XCB) {
                File f = new File("/sdcard/myImage/" + name);
                filename = f.toString();
                transImage(filename, "/sdcard/myImage/" + name);
                new Thread() {
                    @Override
                    public void run() {
                        chepai = OcrUtil.getOcrData(filename, MyApplication.shared.getString("ocrUrlxcb", ""));
                        try {
                            JSONObject jsonObject = new JSONObject(chepai);
                            if (jsonObject.get("error_code").toString().equals("000000")) {
                                JSONObject json = (JSONObject) jsonObject.get("result");
                                chepaihao = json.getString("PlateNo");
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

    private void getCardNoByOCR(Intent data) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        String name = new DateFormat().format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
        Bundle bundle = data.getExtras();
        Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
        FileOutputStream b = null;
        File file = new File("/sdcard/myImage/");
        file.mkdirs();// 创建文件夹
        String fileName = "/sdcard/myImage/" + name;
        filename = fileName;
        try {
            b = new FileOutputStream(fileName);
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
        new Thread() {
            @Override
            public void run() {
                //把网络访问的代码放在这里
                chepai = OcrUtil.getOcrData(filename, MyApplication.shared.getString("ocrUrl", ""));
                try {
                    JSONObject jsonObject = new JSONObject(chepai);
                    if (jsonObject.get("error_code").toString().equals("000000")) {
                        JSONObject json = (JSONObject) jsonObject.get("result");
                        chepaihao = json.getString("PlateNo");
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
                mOCRInfoDialog = new OCRInfoDialog(getActivity(), chepaihao);
                mOCRInfoDialog.show();
                mOCRInfoDialog.setOnBackListener(new OCRInfoDialog.OnBackListener() {
                    @Override
                    public void onConfirm(String chepai) {
                        chepaihao = chepai;
                        cheoruser(chepaihao);
                        mOCRInfoDialog.dismiss();
                    }
                });
            }
            if (msg.what == 11) {
                Show.showTime(getActivity(), "车牌识别失败,请重新拍照！");
            }
        }
    };

    /*
    *根据车牌返回车辆信息和客户信息
    */
    public void cheoruser(String license) {
        Log.e("mr", "获取用户no方法");
        AbRequestParams params = new AbRequestParams();
        params.put("che_no", license);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxjd_clandkh, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int a, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    Log.e("mr", "onSuccess查询车辆客户返回值: " + s);
//                    Log.e("cjn", "谁空22: "+ getActivity());
                    Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    if (jsonObject.get("status").toString().equals("1")) {
                        JSONObject jsonarray = jsonObject.getJSONObject("data");
                        //客户实体
                        JSONObject kehuObject = jsonarray.getJSONObject("kehu");
                        BSD_KeHu_Entity kehuentiy = new BSD_KeHu_Entity();
                        Conts.kehu_no = kehuObject.getString("kehu_no");

                        kehuentiy.setKehu_mc(kehuObject.getString("kehu_mc"));
                        kehuentiy.setKehu_lxr(kehuObject.getString("kehu_xm"));
                        kehuentiy.setKehu_shouji(kehuObject.getString("kehu_sj"));
                        kehuentiy.setKehu_dianhua(kehuObject.getString("kehu_dh"));

                        //车辆实体
                        JSONObject carObject = jsonarray.getJSONObject("cheliang");
                        BSD_Car_Entity carentity = new BSD_Car_Entity();
                        Conts.cp = carObject.getString("che_no");
                        carentity.setChe_no(carObject.getString("che_no"));
                        carentity.setChe_vin(carObject.getString("che_vin"));
                        carentity.setChe_color(carObject.getString("che_wxys"));
                        carentity.setChe_gcrq(carObject.getString("che_gcrq"));
                        carentity.setChe_nf(carObject.getString("che_nf"));
                        carentity.setChe_jqxrq(carObject.getString("che_jiaoqx_dqrq"));
                        carentity.setChe_syxdq(carObject.getString("che_shangyex_dqrq"));
                        carentity.setChe_xcbyrq(carObject.getString("che_next_byrq"));
                        carentity.setChe_xcjcrq(carObject.getString("che_jianche_dqrq"));
                        carentity.setChe_pinpai(carObject.getString("che_cx"));  //车系、车型、品牌、车组是一个字段；
                        //传了个客户实体
                        ((MainActivity) getActivity()).setKhentity(kehuentiy);
                        //传了个车辆实体
                        ((MainActivity) getActivity()).setCarentity(carentity);
                        Log.e("mr", "...... ");
                        //跳转到编辑车辆、客户信息界面
                        Conts.danju_type = "mrkx";
//                        ((MainActivity) getActivity()).upBSD_mrkx_clxx();

                        //跳转到编辑车辆、客户信息对话框
                        //隐藏输入法
                        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

                        new BSD_MeiRongKuaiXiu_cheliangxinxi_Fragment()
                                .show(getFragmentManager(), "dialog_fragment");


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
                Log.e("mr", "onFailure方法");
            }
        });
    }

    /**
     * 图片压缩
     *
     * @param fromFile
     * @param toFile
     */
    public void transImage(String fromFile, String toFile) {
        try {
            Bitmap bitmap = BitmapFactory.decodeFile(fromFile);
            int bitmapWidth = bitmap.getWidth();
            int bitmapHeight = bitmap.getHeight();
            // 缩放图片的尺寸
            float scaleWidth = (float) 800 / bitmapWidth;
            float scaleHeight = (float) 600 / bitmapHeight;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            // 产生缩放后的Bitmap对象
            Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, false);
            // save file
            File myCaptureFile = new File(toFile);
            FileOutputStream out = new FileOutputStream(myCaptureFile);
            if (resizeBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
                out.flush();
                out.close();
            }
            if (!bitmap.isRecycled()) {
                bitmap.recycle();//记得释放资源，否则会内存溢出
            }
            if (!resizeBitmap.isRecycled()) {
                resizeBitmap.recycle();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}