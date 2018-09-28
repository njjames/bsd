package com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue;

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
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.dialogFragment.BSD_MeiRongKuaiXiu_cheliangxinxi_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.Entity.BSD_Car_Entity;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.Entity.BSD_KeHu_Entity;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.Entity.BSD_WeiXiuYueYue_entiy;
import com.example.administrator.boshide2.Modular.View.diaog.Car_Shi_Bie;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.OcrUtil;
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
 * @维修预约扫描输入页面 Created by Administrator on 2017-4-13.
 */

public class BSD_weixiuyuyue_Log_Fragment extends BaseFragment {
    private BSD_WeiXiuYueYue_entiy entiy = new BSD_WeiXiuYueYue_entiy();
    private List<HashMap<String, String>> listche = new ArrayList<>();
    private BSD_ksbj_chexins bsd_ksbj_chexins;
    private ImageView bsd_im_zdsb, bsd_im_sdsr, bsd_im_xcbsb;
    private String filename;
    private String chepai;
    private int tiaozhuan;
    private String chepaihao;
    private Dialog mWeiboDialog;
    private List<BSD_WeiXiuYueYue_entiy> list = new ArrayList<BSD_WeiXiuYueYue_entiy>();
    private LinearLayout bsd_lsbj;
    private URLS url;
    private String name = new DateFormat().format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
    private TextView title;
    private TextView titleLishi;
    private TextView footerText;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_choose_car_layout;
    }

    @Override
    public void initView() {
        bsd_im_zdsb = (ImageView) view.findViewById(R.id.bsd_im_zdsb);
        bsd_im_sdsr = (ImageView) view.findViewById(R.id.bsd_im_sdsr);
        bsd_lsbj = (LinearLayout) view.findViewById(R.id.ll_lishi);
        bsd_im_xcbsb = (ImageView) view.findViewById(R.id.bsd_im_xcbsb);
        title = (TextView) view.findViewById(R.id.tv_title);
        titleLishi = (TextView) view.findViewById(R.id.tv_title_lishi);
        footerText = (TextView) view.findViewById(R.id.tv_footertext);
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

                        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
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
        bsd_lsbj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).upjiedan(view);

            }
        });
        bsd_im_sdsr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getHostActicity()).showManualInputFragment();
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
    }

    @Override
    public void initData() {
        url = new URLS();
        title.setText("维修预约");
        titleLishi.setText("历史预约");
        footerText.setText("公司名称 :   " + MyApplication.shared.getString("GongSiMc", "") +
                "                  公司电话 :   " + MyApplication.shared.getString("danw_dh", ""));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
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
//
//                        ((MainActivity) getActivity()).upBSD_WXYY_srjp();
//                        car_shi_bie.dismiss();
                    }
                });


                car_shi_bie.setSetYes(new Car_Shi_Bie.SetYes() {
                    @Override
                    public void onyes(String  chepai) {

//                        data(chepaihao);
                        chepaihao=chepai;
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

    /**
     * 请求数据一共有几条数据
     *
     * @param carp
     */
    public void data(final String carp) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        AbRequestParams params = new AbRequestParams();
        params.put("pai", carp);
        params.put("gongsiNo", MyApplication.shared.getString("GongSiNo", ""));
        params.put("caozuoyuan_xm", MyApplication.shared.getString("name", ""));
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxyy_LieBiao, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int a, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("status").toString().equals("1")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject item = jsonarray.getJSONObject(i);
                            BSD_WeiXiuYueYue_entiy entity = new BSD_WeiXiuYueYue_entiy();
                            entity.setYuyue_no(item.getString("yuyue_no"));
                            entity.setKehu_mc(item.getString("kehu_mc"));
                            entity.setKehu_dh(item.getString("kehu_dh"));
                            entity.setChe_no(item.getString("che_no"));
                            entity.setChe_vin(item.getString("che_vin"));
                            entity.setChe_cx(item.getString("che_cx"));
                            entity.setYuyue_yjjcrq(item.getString("yuyue_yjjcrq"));
                            entity.setYuyue_yjjclc(item.getInt("yuyue_yjjclc"));
                            entity.setYuyue_jlrq(item.getString("yuyue_jlrq"));
                            entity.setYuyue_sffl(item.getDouble("yuyue_sffl"));
                            entity.setYuyue_sfbz(item.getString("yuyue_sfbz"));
                            entity.setGcsj(item.getString("gcsj"));

                            list.add(entity);
                        }

                        WeiboDialogUtils.closeDialog(mWeiboDialog);
                    } else {
                        Show.showTime(getActivity(), jsonObject.get("message").toString());
                    }

                    if (jsonObject.get("total").toString().equals("1")) {
                        Log.i("cjn", "这个是一条数据");
                        entiy = list.get(0);
                        ((MainActivity) getActivity()).setEntiy(entiy);//传了个实体
//

                        Conts.zt = 1;
                        Conts.cp = carp;
                        chexing();//车行

                    } else if (jsonObject.get("total").toString().equals("0")) {

                        ((MainActivity) getActivity()).upyuyue();
                        //请求

                        Conts.cp = carp;
                        Conts.zt = 0;

                        Log.i("cjn", "这里是没有数据操作");
                    } else {

                        BSD_WeiXiuYueYue_entiy entiy = new BSD_WeiXiuYueYue_entiy();
                        entiy = list.get(0);
                        ((MainActivity) getActivity()).setEntiy(entiy);//传了个实体
//
                        ((MainActivity) getActivity()).upyuyue();
                        Log.i("cjn", "这里是一条数据操作+" + entiy.getYuyue_no().toString());
                        Conts.zt = 1;
                        Conts.cp = carp;
                        Log.i("cjn", "这里是两条数据以上操作");
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
                            if (null==entiy.getYuyue_no()||entiy.getYuyue_no().equals("") || entiy.getYuyue_no().equals("null")) {
                                Toast.makeText(getActivity(), "网络超时请重试", Toast.LENGTH_SHORT).show();
                            } else {
                                ((MainActivity) getActivity()).upyuyue();


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
                            if (null==entiy.getYuyue_no()||entiy.getYuyue_no().equals("") || entiy.getYuyue_no().equals("null")) {
                                Toast.makeText(getActivity(), "网络超时请重试", Toast.LENGTH_SHORT).show();
                            } else {
                                ((MainActivity) getActivity()).upyuyue();
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

//    public void getCarInfo(String license) {
//
//
//        AbRequestParams params = new AbRequestParams();
//        params.put("che_no", license);
//        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxyy_kehubianhao, params, new AbStringHttpResponseListener() {
//            @Override
//            public void onSuccess(int a, String s) {
//                try {
//                    JSONObject jsonObject = new JSONObject(s);
//                    if (jsonObject.get("status").toString().equals("1")) {
//                        JSONObject jsonarray = jsonObject.getJSONObject("data");
//
//
//                        Conts.kehu_no = jsonarray.getString("kehu_no");
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
//
//            }
//        });
//
//
//    }


    /*
    *根据车牌返回车辆信息和客户信息
    */
    public void cheoruser(String license) {
        Log.e("mr", "获取用户no方法" );
        AbRequestParams params = new AbRequestParams();
        params.put("che_no", license);
        Request.Post(MyApplication.shared.getString("ip", "")+url.BSD_wxjd_clandkh, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int a, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    Log.e("mr", "onSuccess查询车辆客户返回值: "+s );
                    Toast.makeText(getActivity(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                    if (jsonObject.get("status").toString().equals("1")) {
                        JSONObject jsonarray = jsonObject.getJSONObject("data");
                        //客户实体
                        JSONObject   kehuObject=jsonarray.getJSONObject("kehu");
                        BSD_KeHu_Entity kehuentiy = new BSD_KeHu_Entity();
                        Conts.kehu_no = kehuObject.getString("kehu_no");

                        kehuentiy.setKehu_mc(kehuObject.getString("kehu_mc"));
                        kehuentiy.setKehu_lxr(kehuObject.getString("kehu_xm"));
                        kehuentiy.setKehu_shouji(kehuObject.getString("kehu_sj"));
                        kehuentiy.setKehu_dianhua(kehuObject.getString("kehu_dh"));

                        //车辆实体
                        JSONObject   carObject=jsonarray.getJSONObject("cheliang");
                        BSD_Car_Entity carentity=new BSD_Car_Entity();
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
                        Log.e("mr", "...... " );
                        //跳转到编辑车辆、客户信息界面
                        Conts.danju_type="wxyy";
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