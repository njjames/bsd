package com.example.administrator.boshide2.Modular.Activity;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.View.diaog.QueRen;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.Show;

/***
 * IP设置
 */
public class WzzlDialog extends Dialog {
    private View view;
    //	WZZL
    EditText bsd_wzzl_et_ip, bsd_wzzl_et_user, bsd_wzzl_et_password, bsd_wzzl_et_dk;
    CheckBox checkBox;
    TextView bsd_wzzl_tv_qx, bsd_wzzl_tijiao;
    String ip;
    int a = 1;
    String dizhi;
    URLS url;
    String duankoug;
    QueRen queRen;

    public WzzlDialog(final Context context) {
        super(context, R.style.mydialog);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        url = new URLS();
        view = getLayoutInflater().inflate(R.layout.bsd_wzzl, null, false);
        //ip输入
        bsd_wzzl_et_ip = (EditText) view.findViewById(R.id.bsd_wzzl_et_ip);
        checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        bsd_wzzl_et_user = (EditText) view.findViewById(R.id.bsd_wzzl_et_user);
        bsd_wzzl_et_password = (EditText) view.findViewById(R.id.bsd_wzzl_et_password);
        bsd_wzzl_et_dk = (EditText) view.findViewById(R.id.bsd_wzzl_et_dk);
        //取消
        bsd_wzzl_tv_qx = (TextView) view.findViewById(R.id.bsd_wzzl_tv_qx);
        bsd_wzzl_tv_qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dissm();
            }
        });
        bsd_wzzl_tijiao = (TextView) view.findViewById(R.id.bsd_wzzl_tijiao);
        bsd_wzzl_tijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked()) {
                    //点击确认提交如果选中走下面
                    wzngzhezhilu(context);
                } else {
                    duankoug = bsd_wzzl_et_dk.getText().toString();
                    dizhi = bsd_wzzl_et_ip.getText().toString() + ":" + duankoug + "/";
                    MyApplication.editor.putString("ip", dizhi);
                    MyApplication.editor.putString("ip1", bsd_wzzl_et_ip.getText().toString());
                    MyApplication.editor.putString("username1", "");
                    MyApplication.editor.putString("password", "");
                    MyApplication.editor.putString("dk", duankoug);
                    MyApplication.editor.putInt("xz", 0);
                    MyApplication.editor.commit();
                    queRen = new QueRen(context, "IP地址保存成功");
                    queRen.show();
                    queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                        @Override
                        public void onYesClick() {
                            queRen.dismiss();
                            dissm();
                        }
                    });
                }
            }
        });

        setContentView(view);
        setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
//        params.width = WindowManager.LayoutParams.WRAP_CONTENT;//宽高可设置具体大小
//        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = getContext().getResources().getDimensionPixelOffset(R.dimen.qb_px_500);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(params);
        initData();//初始化页面数据
    }

    /**
     * 初始化页面内容
     */
    public void initData() {
        String ip1 = MyApplication.shared.getString("ip1", "");
        if (TextUtils.isEmpty(ip1)) {
            bsd_wzzl_et_ip.setText("http://");
        } else {
            bsd_wzzl_et_ip.setText(ip1);
        }
        bsd_wzzl_et_ip.setSelection(bsd_wzzl_et_ip.getText().length());
        bsd_wzzl_et_user.setText(MyApplication.shared.getString("username1", ""));
        bsd_wzzl_et_password.setText(MyApplication.shared.getString("password", ""));
        bsd_wzzl_et_dk.setText(MyApplication.shared.getString("dk", ""));
        if (MyApplication.shared.getInt("xz", 0) > 0) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }
    }

    public void dissm() {
        this.dismiss();
    }

    public void wzngzhezhilu(final Context context) {
        final String username = bsd_wzzl_et_user.getText().toString();
        final String password = bsd_wzzl_et_password.getText().toString();
        AbRequestParams params = new AbRequestParams();
        params.put("UserName", username);
        params.put("UserPwd", password);
        Request.Post("http://bsdip.bsd102.com/ReturnIpM.aspx", params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                duankoug = bsd_wzzl_et_dk.getText().toString();
                ip = "http://" + data + ":" + duankoug + "/";
                bsd_wzzl_et_ip.setText(ip);
                MyApplication.editor.putInt("xz", 1);//记录选中状态
                MyApplication.editor.putString("ip", ip);
                MyApplication.editor.putString("ip1", bsd_wzzl_et_ip.getText().toString());
                MyApplication.editor.putString("username1", username);
                MyApplication.editor.putString("password", password);
                MyApplication.editor.putString("dk", duankoug);
                MyApplication.editor.commit();
                queRen = new QueRen(context, "王道地址保存成功");
                queRen.show();
                queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick() {
                        queRen.dismiss();
                        dissm();
                    }
                });
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                Log.i("cjn", "这是请求失败");
                Show.showTime((LoginActivity) context, "网络连接超时");
            }
        });

    }

}
