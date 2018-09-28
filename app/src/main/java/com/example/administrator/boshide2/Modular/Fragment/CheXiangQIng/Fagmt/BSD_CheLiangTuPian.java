package com.example.administrator.boshide2.Modular.Fragment.CheXiangQIng.Fagmt;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.example.administrator.boshide2.Conts;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Activity.ActivityPhoto;
import com.example.administrator.boshide2.Modular.Fragment.BaseFragment;
import com.example.administrator.boshide2.Modular.View.diaog.CustomDialog;
import com.example.administrator.boshide2.R;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017-4-24.
 */

public class BSD_CheLiangTuPian extends BaseFragment {
    private WebView bsd_cxq_wbweb;
    private URLS url;
    private Button but_upload;
    private Button but_delete;
    private Context context;
    private String jsonUrl;
    private String urlStr;

    private CheckBox cb1, cb2, cb3, cb4;
    private Button but_queding;
    private String picnames, picname;

    //选中上传图片对话框
    private Button  but1,but2,but3,but4;
    private String  name;

    @Override
    protected int getLayoutId() {
        return R.layout.bsd_clxq_cltp;
    }

    @Override
    public void initView() {
        bsd_cxq_wbweb = (WebView) view.findViewById(R.id.bsd_cxq_wbweb);
        but_upload = (Button) view.findViewById(R.id.but_upload);
        but_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUploadDialog();
            }
        });

        but_delete = (Button) view.findViewById(R.id.but_delete);
        but_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeletImgDialog();
            }
        });
    }

    @Override
    public void initData() {
        context = getHostActicity();
        url = new URLS();
        getImages();
    }

    /**
     * 测试用
     */
    public void upload() {
        BitmapDrawable draw = (BitmapDrawable) this.getResources().getDrawable(R.drawable.arrow);
        Bitmap bitmap = draw.getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //将bitmap一字节流输出 Bitmap.CompressFormat.PNG 压缩格式，100：压缩率，baos：字节流
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] buffer = baos.toByteArray();
        //将图片的字节流数据加密成base64字符输出
        String    sss=Base64.encodeToString(buffer, 0, buffer.length, Base64.DEFAULT);
        AbRequestParams params = new AbRequestParams();
        params.put("file", sss);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_upload_pic, null, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("cjn", "查看这个请求的数据" + s);
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
     * 弹出选择上传图片话框
     */
    public void showUploadDialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(getHostActicity());
        final CustomDialog dialog = builder.style(R.style.mydialog)
                .view(R.layout.dialog_upload_layout)
                .cancelTouchout(true)
                .widthDimenRes(R.dimen.qb_px_260)
                .heightDimenRes(R.dimen.qb_px_300)
                .addViewOnclick(R.id.but_1, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clickUploadBtn("1");
                        CustomDialog.dismissDialog();
                    }
                })
                .addViewOnclick(R.id.but_2, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clickUploadBtn("2");
                        CustomDialog.dismissDialog();
                    }
                })
                .addViewOnclick(R.id.but_3, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clickUploadBtn("3");
                        CustomDialog.dismissDialog();
                    }
                })
                .addViewOnclick(R.id.but_4, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clickUploadBtn("4");
                        CustomDialog.dismissDialog();
                    }
                })
                .build();
        dialog.show();
    }

    private void clickUploadBtn(String num) {
        name= Conts.cp + num;
        Intent intent = new Intent(context,ActivityPhoto.class);
        Bundle bundle=new Bundle();
        bundle.putString("picName",name);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /*
    *弹出选择删除对话框；
    */
    public void showDeletImgDialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(getHostActicity());
        final CustomDialog dialog = builder.style(R.style.mydialog)
                .view(R.layout.dialog_deleteimg_layout)
                .cancelTouchout(true)
                .widthDimenRes(R.dimen.qb_px_260)
                .heightDimenRes(R.dimen.qb_px_300)
                .addViewOnclick(R.id.but_confirm, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteImg();
                    }
                })
                .addViewOnclick(R.id.but_cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CustomDialog.dismissDialog();
                    }
                })
                .build();
        View view = dialog.getView();
        cb1 = (CheckBox) view.findViewById(R.id.cb_tp1);
        cb2 = (CheckBox) view.findViewById(R.id.cb_tp2);
        cb3 = (CheckBox) view.findViewById(R.id.cb_tp3);
        cb4 = (CheckBox) view.findViewById(R.id.cb_tp4);
        cb1.setText("图一："+Conts.cp+"_1");
        cb2.setText("图二："+Conts.cp+"_2");
        cb3.setText("图三："+Conts.cp+"_3");
        cb4.setText("图四："+Conts.cp+"_4");
        dialog.show();
    }

    private void deleteImg() {
        picnames="";
        if (cb1.isChecked()) {
            picnames += Conts.cp + "1" + ",";
        }
        if (cb2.isChecked()) {
            picnames += Conts.cp + "2".toString() + ",";
        }
        if (cb3.isChecked()) {
            picnames += Conts.cp + "3".toString() + ",";
        }
        if (cb4.isChecked()) {
            picnames += Conts.cp + "4".toString() + ",";
        }
        if (picnames.equals("")){
            Toast.makeText(context,"请选择要删除的照片",Toast.LENGTH_LONG).show();
        }else {
            picname = picnames.substring(0, picnames.length() - 1);  //去掉最后的逗号
            deletepic();
        }
    }

    //请求删除图片接口
    public void deletepic() {
        AbRequestParams params = new AbRequestParams();
        Log.e("pic", "图片名字----" + picname);
        params.put("photo", picname);                   //选中的照片名字，多个用逗号隔开；
        Log.i("pic", "deletepic删除的图片: "+picname);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_delete_image, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("pic", "查看这个请求的数据" + s);
                getImages();   //刷新网页
                CustomDialog.dismissDialog();
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                Log.i("pic", "查看失败==" + s);
                CustomDialog.dismissDialog();
            }
        });
    }

    /*
   *获取显示图片的网页地址
   */
    public void getImages() {
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_getImgUrl, null, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    jsonUrl = jsonObject.getString("data").toString().trim();
                    urlStr = java.net.URLEncoder.encode(Conts.cp, "GB2312");
                    jsonUrl = jsonUrl + "/ShowImageChe.aspx?che_no=" + urlStr;
//                    bsd_cxq_wbweb.loadUrl("http://css85.bsd126.com:8/BSDImage/ShowImageChe.aspx?che_no=%BB%A6%BD%F2Q023456");
                    bsd_cxq_wbweb.loadUrl(jsonUrl);
                } catch (Exception e) {
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


}