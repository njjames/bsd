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
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.example.administrator.boshide2.Modular.View.diaog.Car_Shi_Bie;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.OcrUtil;
import com.example.administrator.boshide2.Tools.PermissionUtils;
import com.example.administrator.boshide2.Tools.PermissionsManager;
import com.example.administrator.boshide2.Tools.QuanQuan.WeiboDialogUtils;
import com.example.administrator.boshide2.Tools.Show;

import org.json.JSONArray;
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
    int tiaozhuan;
    private Dialog mWeiboDialog;
    private LinearLayout bsd_mrkx_lishikuaiuxi;
    private List<BSD_WeiXiuJieDan_Entity> list = new ArrayList<BSD_WeiXiuJieDan_Entity>();
    private RelativeLayout bsd_lsbj;
    private String name = new DateFormat().format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
    private URLS url;
    private TextView title;
    private TextView titleLishi;
    private TextView footerText;

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
        bsd_lsbj = (RelativeLayout) view.findViewById(R.id.bsd_lsbj);
        title = (TextView) view.findViewById(R.id.tv_title);
        titleLishi = (TextView) view.findViewById(R.id.tv_title_lishi);
        footerText = (TextView) view.findViewById(R.id.tv_footertext);
        bsd_mrkx_lishikuaiuxi = (LinearLayout) view.findViewById(R.id.ll_lishi);
        bsd_mrkx_lishikuaiuxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).uplskx();
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
                tiaozhuan = 2;
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
            }
        });
        //  打开相机
        bsd_im_xcbsb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tiaozhuan = 1;
                String status = Environment.getExternalStorageState();
                if (status.equals(Environment.MEDIA_MOUNTED)) {
                    try {
                        File dir = new File("/sdcard/myImage/");
                        if (!dir.exists())
                            dir.mkdirs();

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(dir, name);//localTempImgDir
//                        localTempImageFileName
//                                是自己定义的名字
                        Uri u = Uri.fromFile(f);
                        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
                        startActivityForResult(intent, 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {

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
//            tiaozhuan==1行车本识别
            if (tiaozhuan == 1) {
                File f = new File("/sdcard/myImage/" + name);
                filename = f.toString();
                Uri uri = null;
                transImage(filename, "/sdcard/myImage/" + name);

                new Thread() {
                    @Override
                    public void run() {
                        //把网络访问的代码放在这里
                        chepai = OcrUtil.getOcrData(filename, MyApplication.shared.getString("ocrUrlxcb", ""));
                        Log.i("cjn", "行车本为" + chepai);
                        try {
                            JSONObject jsonObject = new JSONObject(chepai);
                            if (jsonObject.get("error_code").toString().equals("000000")) {
                                JSONObject json = (JSONObject) jsonObject.get("result");

                                chepaihao = json.getString("PlateNo");
                                Conts.VIN = json.getString("Vin");
                                String b = json.getString("VehicleModels");
                                b = b.replace("牌", "");
                                Conts.BSD_chexing = b;
                                Log.i("cjn", "车牌为" + chepaihao);
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

//            tiaozhuan==2车牌识别  OCR
            if (tiaozhuan == 2) {
                String sdStatus = Environment.getExternalStorageState();
                if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
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
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        b.flush();
                        b.close();
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
                                Log.i("cjn", "车牌为" + chepaihao);
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
            if (msg.what == 10) { // 更改选中商品的总价格
                final Car_Shi_Bie car_shi_bie = new Car_Shi_Bie(getActivity(), chepaihao);
                car_shi_bie.show();

                car_shi_bie.setSetUp(new Car_Shi_Bie.SetUp() {
                    @Override
                    public void onup() {
                        //跳转美容快修
//                        Log.i("cjn","不对跳转键盘输入");
//                        ((MainActivity) getActivity()).upBSD_mrkx_jpsr();
//                        car_shi_bie.dismiss();


                    }
                });


                car_shi_bie.setSetYes(new Car_Shi_Bie.SetYes() {
                    @Override
                    public void onyes(String chepai) {
                        Log.i("cjn", "正确走查询跳转美容快修页面");
//                        data(chepaihao);
                        chepaihao = chepai;
                        Log.i("cphm", "onyes: 车牌好是---" + chepaihao);
                        cheoruser(chepaihao);

                        car_shi_bie.dismiss();
                    }
                });

            }
            if (msg.what == 11) {
                Show.showTime(getActivity(), "车牌识别失败,请重新拍照！");
            }

        }

    };
//    //获取kehu_no
//    public void getCarInfo(String license) {
//        AbRequestParams params = new AbRequestParams();
//        params.put("che_no", license);
//        Request.Post(MyApplication.shared.getString("ip", "")+url.BSD_wxyy_kehubianhao, params, new AbStringHttpResponseListener() {
//            @Override
//            public void onSuccess(int a, String s) {
//                try {
//                    JSONObject jsonObject = new JSONObject(s);
//                    if (jsonObject.get("status").toString().equals("1")) {
//                        JSONObject jsonarray = jsonObject.getJSONObject("data");
//                        Conts.kehu_no = jsonarray.getString("kehu_no");
//
//
//                        //跳转到编辑车辆、客户信息界面---李赛；
//                        ((MainActivity) getActivity()).upBSD_mrkx_clxx();
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
//
//            }
//        });
//
//
//    }
//


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


    //根据车牌获取数据
    public void data(final String cp) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        AbRequestParams params = new AbRequestParams();
        params.put("che_no", cp);
        params.put("gongsiNo", MyApplication.shared.getString("GongSiNo", ""));
        params.put("caozuoyuan_xm", MyApplication.shared.getString("name", ""));
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_MRKX_IN, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aa, String s) {
                Log.i("cjn", "成功1");
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("status").toString().equals("1")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            //这块拿到的是维系接单的详细表
                            JSONObject item = jsonarray.getJSONObject(i);
                            BSD_WeiXiuJieDan_Entity entiy = new BSD_WeiXiuJieDan_Entity();
                            entiy.setWork_no(item.getString("work_no"));
                            entiy.setKehu_no(item.getString("kehu_no"));
                            entiy.setKehu_mc(item.getString("kehu_mc"));
                            entiy.setKehu_xm(item.getString("kehu_xm"));
                            entiy.setKehu_dz(item.getString("kehu_dz"));
                            entiy.setKehu_yb(item.getString("kehu_yb"));
                            entiy.setKehu_dh(item.getString("kehu_dh"));
                            entiy.setChe_no(item.getString("che_no"));
                            entiy.setChe_cx(item.getString("che_cx"));
                            entiy.setChe_vin(item.getString("che_vin"));
                            entiy.setXche_lc(item.getInt("xche_lc"));
                            entiy.setGcsj(item.getString("gcsj"));

                            list.add(entiy);

                        }
                        WeiboDialogUtils.closeDialog(mWeiboDialog);
                    } else {
//                        Show.showTime(getActivity(), jsonObject.get("message").toString());
                    }
                    if (jsonObject.get("total").toString().equals("1")) {
                        entiy = list.get(0);
                        Log.i("cjn", "键盘输入车牌存上了吗" + entiy.getChe_no());
                        ((MainActivity) getActivity()).setWxjdentity(entiy);//传了个实体
                        Conts.zt = 1;
                        Conts.cp = cp;
                        chexing();//车行

                    } else if (jsonObject.get("total").toString().equals("0")) {

                        ((MainActivity) getActivity()).upBSD_mrkx();
                        //请求

                        Conts.cp = cp;
                        Conts.zt = 0;
                    } else {
                        BSD_WeiXiuJieDan_Entity entiy = new BSD_WeiXiuJieDan_Entity();
                        entiy = list.get(0);
                        entiy = list.get(0);
                        ((MainActivity) getActivity()).setWxjdentity(entiy);//传了个实体
//
                        ((MainActivity) getActivity()).upBSD_mrkx();
                        Conts.zt = 1;
                        Conts.cp = cp;
                    }
                    //在这里请求


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
                Log.i("cjn", "失败");
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });


    }


    /**
     * 车行几条数据查询
     */

    public void chexing() {
        listche.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("chex_mc", Conts.BSD_chexing);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_public_getcheIDBycheXingMingCheng, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aa, String s) {
                Log.i("cjn", "成功");
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getString("message").equals("查询成功")) {
                        if (jsonObject.getInt("total") == 0) {
//                            Toast.makeText(getContext(), "没有查到车辆信息", Toast.LENGTH_SHORT).show();
                            if (null == entiy.getWork_no() || entiy.getWork_no().equals("") || entiy.getWork_no().equals("null")) {
                                Toast.makeText(getActivity(), "网络超时请重试", Toast.LENGTH_SHORT).show();
                            } else {
                                ((MainActivity) getActivity()).upBSD_mrkx();
                            }


                        }
                        if (jsonObject.getInt("total") == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            JSONObject g = jsonArray.getJSONObject(0);
                            Conts.BSD_chexingDM = g.getString("chex_bz");
                            //一条，数据，的情况下走的流程
                            huoquequancheng();

                        }
                        if (jsonObject.getInt("total") > 1) {
                            //多条，数据走的方法
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                HashMap<String, String> map = new HashMap<String, String>();
                                map.put("chex_dm", object.getString("chex_dm"));
                                map.put("chex_bz", object.getString("chex_bz"));
                                map.put("chex_mc_std", object.getString("chex_mc_std"));
                                map.put("name", object.getString("chex_dm") + "【" + object.getString("chex_mc_std") + "】");
                                listche.add(map);
                            }
                            bsd_ksbj_chexins = new BSD_ksbj_chexins(getContext(), listche);
                            bsd_ksbj_chexins.show();
                            bsd_ksbj_chexins.setXuanZECheXing(new BSD_ksbj_chexins.XuanZECheXing() {
                                @Override
                                public void onyes(String id) {
                                    Conts.BSD_chexingDM = id;
                                    huoquequancheng();

                                }
                            });

                        }


                    } else {
                        Log.i("cjn", "查询失败");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                handler.sendMessage(handler.obtainMessage(12));


            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                Log.i("cjn", "失败" + s);
            }
        });


    }

    /**
     * 获取全称
     */
    public void huoquequancheng() {
        AbRequestParams params = new AbRequestParams();
        params.put("chex_dm", Conts.BSD_chexingDM);
        Log.i("cjn", "获取全称阐述" + Conts.BSD_chexingDM);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_public_get4JIjiegouMingcheng, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("cjn", "获取全称" + s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getString("message").toString().equals("查询失败")) {
                        if (jsonObject.getString("data").length() > 1) {
                            entiy.setChe_cx(jsonObject.getString("data"));
                            Conts.BSD_chexingQC = jsonObject.getString("data");

                            if (null == entiy.getWork_no() || entiy.getWork_no().equals("") || entiy.getWork_no().equals("null")) {
                                Toast.makeText(getActivity(), "网络超时请重试", Toast.LENGTH_SHORT).show();
                            } else {
                                ((MainActivity) getActivity()).upBSD_mrkx();
                            }
                            bsd_ksbj_chexins.dismiss();
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