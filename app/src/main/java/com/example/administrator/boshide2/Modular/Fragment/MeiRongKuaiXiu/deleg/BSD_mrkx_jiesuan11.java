package com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.deleg;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.R;

/***
 * 快速报价----选择品牌 ----diaglog
 */
public class BSD_mrkx_jiesuan11 extends Dialog {
    private View view;
    TextView bsd_wzzl_tv_qx;
    URLS url;
    JieSuan jieSuan;

    EditText bsd_mrkx_xmyh, bsd_mrkx_clyh, bsd_mrkx_shishou;
    TextView bsd_mrkx_yuer, bsd_mrkx_hejijiner, bsd_mrkx_yingshou;
    TextView bsd_wzzl_tv_queren, bsd_wzzl_tijiao;
    String zongjia;
    double xm;
    double cl;
    double zh;

    public void setZongjia(String zongjia) {
        this.zongjia = zongjia;
    }

    public String getZongjia() {
        return zongjia;
    }



    public void setJieSuan(JieSuan jieSuan) {
        this.jieSuan = jieSuan;
    }

    public BSD_mrkx_jiesuan11(final Context context, String kh, double yue, final double zongjia) {
        super(context, R.style.mydialog);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        // TODO Auto-generated constructor stub
        url = new URLS();
        view = getLayoutInflater().inflate(R.layout.bsd_mrkx_jiesuan111, null, false);
        bsd_mrkx_xmyh = (EditText) view.findViewById(R.id.et_xmyhje);
        bsd_mrkx_clyh = (EditText) view.findViewById(R.id.et_clyhje);
        bsd_mrkx_shishou = (EditText) view.findViewById(R.id.et_xche_ssje);
        bsd_mrkx_yuer = (TextView) view.findViewById(R.id.tv_cardleftje);
        bsd_mrkx_hejijiner = (TextView) view.findViewById(R.id.tv_xche_hjje);
        bsd_mrkx_yingshou = (TextView) view.findViewById(R.id.tv_xche_ysje);
        bsd_wzzl_tv_queren = (TextView) view.findViewById(R.id.tv_confirm);
        bsd_wzzl_tijiao = (TextView) view.findViewById(R.id.tv_cancel);


        bsd_wzzl_tijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dissm();
            }
        });


        bsd_mrkx_hejijiner.setText(zongjia + "");
        if (bsd_mrkx_xmyh.getText().toString().equals("")) {
            xm = 0;
        } else {
            xm = Double.parseDouble(bsd_mrkx_xmyh.getText().toString());
        }
        if (bsd_mrkx_clyh.getText().toString().equals("")) {
            cl = 0;
        } else {
            cl = Double.parseDouble(bsd_mrkx_clyh.getText().toString());
        }
        bsd_mrkx_xmyh.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (bsd_mrkx_xmyh.getText().toString().equals("")) {
                    xm = 0;
                } else {
                    xm = Double.parseDouble(bsd_mrkx_xmyh.getText().toString());
                }
                if (bsd_mrkx_clyh.getText().toString().equals("")) {
                    cl = 0;
                } else {
                    cl = Double.parseDouble(bsd_mrkx_clyh.getText().toString());
                }
                if (editable.length() > 0) {
                    xm = Double.parseDouble(editable.toString());
                    zh = zongjia - xm - cl;
                    bsd_mrkx_yingshou.setText("" + zh);
                    bsd_mrkx_shishou.setText("" + zh);
                } else if (editable.length() == 0) {
                    zh = zongjia - cl;
                    bsd_mrkx_yingshou.setText("" + zh);
                    bsd_mrkx_shishou.setText("" + zh);
                }
            }
        });


        bsd_mrkx_clyh.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (bsd_mrkx_xmyh.getText().toString().equals("")) {
                    xm = 0;
                } else {
                    xm = Double.parseDouble(bsd_mrkx_xmyh.getText().toString());
                }
                if (bsd_mrkx_clyh.getText().toString().equals("")) {
                    cl = 0;
                } else {
                    cl = Double.parseDouble(bsd_mrkx_clyh.getText().toString());
                }
                if (editable.length() > 0) {
                    cl = Double.parseDouble(editable.toString());
                    zh = zongjia - xm - cl;
                    bsd_mrkx_yingshou.setText("" + zh);
                    bsd_mrkx_shishou.setText("" + zh);
                } else if (editable.length() == 0) {
                    zh = zongjia - xm;
                    bsd_mrkx_yingshou.setText("" + zh);
                    bsd_mrkx_shishou.setText("" + zh);
                }
            }
        });
        zh = zongjia - xm - cl;
        bsd_mrkx_yingshou.setText("" + zh);
        bsd_mrkx_shishou.setText("" + zh);
        bsd_wzzl_tv_queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jieSuan.onyes(Double.parseDouble(bsd_mrkx_hejijiner.getText().toString()),
                        Double.parseDouble(bsd_mrkx_shishou.getText().toString()),
                        xm,
                        cl,
                        Double.parseDouble(bsd_mrkx_yingshou.getText().toString())



                );
                dissm();
            }
        });

        setContentView(view);
        setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams params =
                this.getWindow().getAttributes();
        params.width = 600;
        params.x = 0;
        params.y = 0;
        params.height = 600;
        this.getWindow().setAttributes(params);

    }

    /**
     * 初始化页面内容
     */
    public void init(View view) {

    }

    public void dissm() {
        this.dismiss();
    }


    public void diss() {
        this.dissm();
    }

    public interface JieSuan {
        public void onyes(double xche_hjje, double xche_ssje, double xche_wxxm_yhje,
                          double xche_peij_yhje, double xche_ysje);
    }
}
