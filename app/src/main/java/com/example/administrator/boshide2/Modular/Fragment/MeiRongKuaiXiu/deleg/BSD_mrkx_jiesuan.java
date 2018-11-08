package com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.deleg;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.example.administrator.boshide2.Conts;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.View.diaog.QueRen;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.DownJianPan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/***
 * 快速报价----选择品牌 ----diaglog
 */
public class BSD_mrkx_jiesuan extends Dialog {
    private View view;
    TextView bsd_wzzl_tv_qx;
    URLS url;
    JieSuan jieSuan;

    EditText bsd_mrkx_huiyuankahao, bsd_mrkx_mima, bsd_mrkx_xmyh, bsd_mrkx_clyh, bsd_mrkx_shishou;
    TextView bsd_mrkx_yuer, bsd_mrkx_hejijiner, bsd_mrkx_yingshou;
    CheckBox checkBox;
    TextView bsd_wzzl_tv_queren, bsd_wzzl_tijiao, bsd_mrkx_buxianjin;
    String zongjia;
    double wxxmYh;
    double wxclYh;
    double zh;
    double shishou;

    public void setZongjia(String zongjia) {
        this.zongjia = zongjia;
    }

    double shishoujine = 0.0;

    public String getZongjia() {
        return zongjia;
    }

    private int isUseCard = 0;

    private LinearLayout ll_buxianjin;

    TextView bsd_wzzl_js_duqu;

    QueRen queRen;

    public void setJieSuan(JieSuan jieSuan) {
        this.jieSuan = jieSuan;
    }

    Guanbi gb;

    public Guanbi getGb() {
        return gb;
    }

    public void setGb(Guanbi gb) {
        this.gb = gb;
    }

    public BSD_mrkx_jiesuan(final Context context, String kh, double yue, final double zongjia) {
        super(context, R.style.mydialog);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        url = new URLS();
        view = getLayoutInflater().inflate(R.layout.bsd_mrkx_jiesuan, null, false);
        bsd_mrkx_huiyuankahao = (EditText) view.findViewById(R.id.bsd_mrkx_huiyuankahao);
        bsd_mrkx_mima = (EditText) view.findViewById(R.id.bsd_mrkx_mima);
        bsd_mrkx_xmyh = (EditText) view.findViewById(R.id.bsd_mrkx_xmyh);
        bsd_mrkx_clyh = (EditText) view.findViewById(R.id.bsd_mrkx_clyh);
        bsd_mrkx_shishou = (EditText) view.findViewById(R.id.bsd_mrkx_shishou);
        bsd_mrkx_yuer = (TextView) view.findViewById(R.id.bsd_mrkx_yuer);
        bsd_mrkx_hejijiner = (TextView) view.findViewById(R.id.bsd_mrkx_hejijiner);
        bsd_mrkx_yingshou = (TextView) view.findViewById(R.id.bsd_mrkx_yingshou);
        checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        bsd_wzzl_tv_queren = (TextView) view.findViewById(R.id.bsd_wzzl_tv_queren);
        bsd_wzzl_tijiao = (TextView) view.findViewById(R.id.bsd_wzzl_tijiao);
        bsd_mrkx_buxianjin = (TextView) view.findViewById(R.id.bsd_mrkx_buxianjin);
        ll_buxianjin = (LinearLayout) view.findViewById(R.id.ll_buxianjin);
        bsd_wzzl_js_duqu = (TextView) view.findViewById(R.id.bsd_wzzl_js_duqu);
        bsd_wzzl_js_duqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownJianPan.hide(getContext(), bsd_mrkx_huiyuankahao);
                huiyuankachauxn();
            }
        });
        bsd_wzzl_tijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gb.guanbi();
                dissm();
            }
        });
        bsd_mrkx_huiyuankahao.setText(kh);
        bsd_mrkx_yuer.setText("" + yue);
        zh = zongjia - wxxmYh - wxclYh;
        double v = (Math.round(zh * 100) / 100.0);
        bsd_mrkx_yingshou.setText("" + v);
//        应收实收逻辑
        if ((Double.parseDouble(bsd_mrkx_yingshou.getText().toString()) - Double.parseDouble(bsd_mrkx_yuer.getText().toString())) > 0) {
            double v1 = (Math.round(Double.parseDouble(bsd_mrkx_yuer.getText().toString()) * 100) / 100.0);

            bsd_mrkx_shishou.setText(v1 + "");
            double v2 = (Math.round((Double.parseDouble(bsd_mrkx_yingshou.getText().toString())
                    - Double.parseDouble(bsd_mrkx_shishou.getText().toString())) * 100) / 100.0);
            bsd_mrkx_buxianjin.setText(v2 + "");
        } else if ((Double.parseDouble(bsd_mrkx_yingshou.getText().toString()) - Double.parseDouble(bsd_mrkx_yuer.getText().toString())) < 0) {
            double v2 = (Math.round(Double.parseDouble(bsd_mrkx_yingshou.getText().toString()) * 100) / 100.0);
            bsd_mrkx_shishou.setText(v2 + "");
            bsd_mrkx_buxianjin.setText("0");
        }

        bsd_mrkx_hejijiner.setText(zongjia + "");
        if (bsd_mrkx_xmyh.getText().toString().equals("")) {
            wxxmYh = 0;
        } else {
            wxxmYh = Double.parseDouble(bsd_mrkx_xmyh.getText().toString());
        }
        if (bsd_mrkx_clyh.getText().toString().equals("")) {
            wxclYh = 0;
        } else {
            wxclYh = Double.parseDouble(bsd_mrkx_clyh.getText().toString());
        }
        if (isUseCard == 0) {
            double v3 = (Math.round(Double.parseDouble(bsd_mrkx_yingshou.getText().toString()) * 100) / 100.0);
            bsd_mrkx_shishou.setText(v3 + "");
            bsd_mrkx_buxianjin.setText("0");
        }
        //项目改变
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
                    wxxmYh = 0;
                } else {
                    wxxmYh = Double.parseDouble(bsd_mrkx_xmyh.getText().toString());
                }
                if (bsd_mrkx_clyh.getText().toString().equals("")) {
                    wxclYh = 0;
                } else {
                    wxclYh = Double.parseDouble(bsd_mrkx_clyh.getText().toString());
                }
                if (editable.length() > 0) {
                    wxxmYh = Double.parseDouble(editable.toString());
                    zh = zongjia - wxxmYh - wxclYh;
                    double v = (Math.round(zh * 100) / 100.0);
                    bsd_mrkx_yingshou.setText("" + v);
                    bsd_mrkx_shishou.setText("" + v);
                    Log.i("cjn", "项目1111111");
                } else if (editable.length() == 0) {
                    Log.i("cjn", "项目0000000");
                    zh = zongjia - wxclYh;
                    double v = (Math.round(zh * 100) / 100.0);
                    bsd_mrkx_yingshou.setText("" + v);
                    bsd_mrkx_shishou.setText("" + v);
                }
            }
        });
        //材料改变
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
                    wxxmYh = 0;
                } else {
                    wxxmYh = Double.parseDouble(bsd_mrkx_xmyh.getText().toString());
                }
                if (bsd_mrkx_clyh.getText().toString().equals("")) {
                    wxclYh = 0;
                } else {
                    wxclYh = Double.parseDouble(bsd_mrkx_clyh.getText().toString());
                }
                if (editable.length() > 0) {
                    Log.i("cjn", "材料1111111");
                    wxclYh = Double.parseDouble(editable.toString());
                    zh = zongjia - wxxmYh - wxclYh;
                    double v = (Math.round(zh * 100) / 100.0);
                    bsd_mrkx_yingshou.setText("" + v);
                    bsd_mrkx_shishou.setText("" + v);
                } else if (editable.length() == 0) {
                    Log.i("cjn", "材料0000000");
                    zh = zongjia - wxxmYh;
                    double v = (Math.round(zh * 100) / 100.0);
                    bsd_mrkx_yingshou.setText("" + v);
                    bsd_mrkx_shishou.setText("" + v);
                }
            }
        });
        //实收改变
        bsd_mrkx_shishou.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (bsd_mrkx_shishou.getText().toString().equals("")) {
                    shishou = 0;
                } else {
                    try {
                        shishou = Double.parseDouble(bsd_mrkx_shishou.getText().toString());
                    } catch (NumberFormatException e) {
                        Log.i("cjn", "捕获异常" + e);
                    }
                }
                if (editable.length() > 0) {
                    Log.i("cjn", "实收111111");
                    try {
                        shishou = Double.parseDouble(editable.toString());
                        double v = (Math.round((Double.parseDouble(bsd_mrkx_yingshou.getText().toString()) - Double.parseDouble(bsd_mrkx_shishou.getText().toString())) * 100) / 100.0);
                        bsd_mrkx_buxianjin.setText("" + v);

                    } catch (NumberFormatException e) {
                        Log.i("cjn", "捕获异常" + e);
                    }
                } else if (editable.length() == 0) {
                    Log.i("cjn", "实收0000000");
                    double v = (Math.round(Double.parseDouble(bsd_mrkx_yingshou.getText().toString()) * 100) / 100.0);
                    bsd_mrkx_buxianjin.setText(v + "");

                }
            }
        });
        //是否使用储值卡监听改变
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    //获得checkBox的文本内容
                    isUseCard = 1;
                    ll_buxianjin.setVisibility(View.VISIBLE);
                    if ((Double.parseDouble(bsd_mrkx_yingshou.getText().toString()) - Double.parseDouble(bsd_mrkx_yuer.getText().toString())) > 0) {
                        double v = (Math.round(Double.parseDouble(bsd_mrkx_yuer.getText().toString()) * 100) / 100.0);
                        bsd_mrkx_shishou.setText(v + "");
                        bsd_mrkx_buxianjin.setText((Double.parseDouble(bsd_mrkx_yingshou.getText().toString()) - Double.parseDouble(bsd_mrkx_shishou.getText().toString())) + "");
                    } else if ((Double.parseDouble(bsd_mrkx_yingshou.getText().toString()) - Double.parseDouble(bsd_mrkx_yuer.getText().toString())) < 0) {
                        double v = (Math.round(Double.parseDouble(bsd_mrkx_yingshou.getText().toString()) * 100) / 100.0);
                        bsd_mrkx_shishou.setText(v + "");
                        bsd_mrkx_buxianjin.setText("0");
                    }
                } else {
                    isUseCard = 0;
                    ll_buxianjin.setVisibility(View.GONE);
                    double v = (Math.round(Double.parseDouble(bsd_mrkx_yingshou.getText().toString()) * 100) / 100.0);
                    bsd_mrkx_shishou.setText(v + "");
                    bsd_mrkx_buxianjin.setText("0");

                }
            }
        });


//        bsd_mrkx_shishou.setText("" + zh);
//        if ((Double.parseDouble(bsd_mrkx_shishou.getText().toString())-Double.parseDouble(bsd_mrkx_yuer.getText().toString())) < 0) {
//            bsd_mrkx_buxianjin.setText((Double.parseDouble(bsd_mrkx_yuer.getText().toString())-Double.parseDouble(bsd_mrkx_shishou.getText().toString()))+"");
//        }else {
//            bsd_mrkx_buxianjin.setText("0");
//        }

        bsd_wzzl_tv_queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gb.guanbi();
                if (isUseCard == 1) {
                    if (TextUtils.isEmpty(bsd_mrkx_huiyuankahao.getText().toString())) {
                        Toast.makeText(getContext(), "使用储值卡结算时，会员卡号不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (TextUtils.isEmpty(bsd_mrkx_xmyh.getText().toString())) {
                    wxxmYh = 0;
                } else {
                    wxxmYh = Double.parseDouble(bsd_mrkx_xmyh.getText().toString());
                }
                if (TextUtils.isEmpty(bsd_mrkx_clyh.getText().toString())) {
                    wxclYh = 0;
                } else {
                    wxclYh = Double.parseDouble(bsd_mrkx_clyh.getText().toString());
                }
                if (TextUtils.isEmpty(bsd_mrkx_shishou.getText().toString())) {
                    shishoujine = 0.0;
                } else {
                    shishoujine = Double.parseDouble(bsd_mrkx_shishou.getText().toString());
                }
                if (Double.parseDouble(bsd_mrkx_hejijiner.getText().toString()) >= (wxxmYh + wxclYh) &&
                        Double.parseDouble(bsd_mrkx_yingshou.getText().toString()) >= shishoujine) {
                    jieSuan.onyes(Double.parseDouble(bsd_mrkx_hejijiner.getText().toString()),
                            shishoujine,
                            wxxmYh,
                            wxclYh,
                            Double.parseDouble(bsd_mrkx_yingshou.getText().toString()),
                            bsd_mrkx_huiyuankahao.getText().toString(),
                            bsd_mrkx_mima.getText().toString(),
                            isUseCard,
                            Double.parseDouble(bsd_mrkx_buxianjin.getText().toString()),
                            shishoujine,
                            Double.parseDouble(bsd_mrkx_buxianjin.getText().toString()));
                } else if (Double.parseDouble(bsd_mrkx_hejijiner.getText().toString()) < (wxxmYh + wxclYh)) {
                    Toast.makeText(getContext(), "优惠金额不能超过合计金额", Toast.LENGTH_SHORT).show();
                } else if (Double.parseDouble(bsd_mrkx_yingshou.getText().toString()) < Double.parseDouble(bsd_mrkx_shishou.getText().toString())) {
                    Toast.makeText(getContext(), "实收金额不能超过应收金额", Toast.LENGTH_SHORT).show();
                }


            }
        });

        setContentView(view);
        setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams params =
                this.getWindow().getAttributes();
        params.width = (int) getContext().getResources().getDimension(R.dimen.qb_px_620);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(params);

    }

    /**
     * 会员卡查询接口
     */
    public void huiyuankachauxn() {
        AbRequestParams params = new AbRequestParams();
        params.put("card_no", bsd_mrkx_huiyuankahao.getText().toString());
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_MRKX_HYK, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aa, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getString("message").equals("查询成功")) {
                        JSONObject object = jsonObject.getJSONObject("data");
                        Conts.MRKX_shengYu_jinQian = Double.parseDouble(object.getString("card_leftje"));
                        bsd_mrkx_yuer.setText(Double.parseDouble(object.getString("card_leftje")) + "");
                        queRen = new QueRen(getContext(), "读取成功，卡内余额" + object.getString("card_leftje"));
                        queRen.show();
                        queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                            @Override
                            public void onYesClick() {
                                queRen.dismiss();
                            }
                        });

                    } else {
                        queRen = new QueRen(getContext(), jsonObject.getString("message"));
                        queRen.show();
                        bsd_mrkx_yuer.setText("");
                        queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                            @Override
                            public void onYesClick() {
                                queRen.dismiss();
                            }
                        });
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
                Log.i("cjn", "会员卡号" + s);
            }
        });

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
                          double xche_peij_yhje, double xche_ysje, String card_no, String mima, int iscard, double bxj, double zhifu_card_je, double zhifu_card_xj);
    }

    public interface OnAccountListener {
        void onAccount(double xche_hjje, double xche_ssje, double xche_wxxm_yhje,
                       double xche_peij_yhje, double xche_ysje, String card_no, String mima, int iscard, double bxj, double zhifu_card_je, double zhifu_card_xj);

    }

    public interface Guanbi {
        public void guanbi();


    }
}
