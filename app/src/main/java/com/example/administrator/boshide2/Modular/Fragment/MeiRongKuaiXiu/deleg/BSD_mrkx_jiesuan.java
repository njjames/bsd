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

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;

/***
 * 快速报价----选择品牌 ----diaglog
 */
public class BSD_mrkx_jiesuan extends Dialog implements View.OnClickListener {
    private String workNo;
    private View view;
    TextView bsd_wzzl_tv_qx;
    JieSuan jieSuan;

    EditText et_cardno, et_cardpass, et_xmyhje, et_clyhje, et_xche_ssje;
    TextView tv_cardleftje, tv_xche_hjje, tv_xche_ysje;
    CheckBox checkBox;
    TextView tv_confirm, tv_cancel, tv_buxianjin;
    String zongjia;
    double wxxmYh;
    double wxclYh;
    double zh;
    double shishou;
    private double cardLeftje;
    private String cardNo;
    private BigDecimal xche_wxxm_yhje;
    private BigDecimal xche_peij_yhje;
    private BigDecimal card_leftje;
    private BigDecimal xche_hjje;
    private BigDecimal xche_ysje;
    private BigDecimal buXianJin;
    private BigDecimal xche_ssje;

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

    public BSD_mrkx_jiesuan(final Context context, final String cardNo, String workNo) {
        super(context, R.style.mydialog);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        view = getLayoutInflater().inflate(R.layout.bsd_mrkx_jiesuan, null, false);
        setContentView(view);
        setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = (int) getContext().getResources().getDimension(R.dimen.qb_px_620);
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(params);
        this.cardNo = cardNo;
        this.workNo = workNo;
        initView();
        initData();
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gb.guanbi();
                if (checkBox.isChecked()) {
                    if (TextUtils.isEmpty(cardNo)) {
                        Toast.makeText(getContext(), "使用储值卡结算时，会员卡号不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                if (TextUtils.isEmpty(et_xche_ssje.getText().toString())) {
                    shishoujine = 0.0;
                } else {
                    shishoujine = Double.parseDouble(et_xche_ssje.getText().toString());
                }
                if (Double.parseDouble(tv_xche_hjje.getText().toString()) >= (wxxmYh + wxclYh) &&
                        Double.parseDouble(tv_xche_ysje.getText().toString()) >= shishoujine) {

                } else if (Double.parseDouble(tv_xche_hjje.getText().toString()) < (wxxmYh + wxclYh)) {
                    Toast.makeText(getContext(), "优惠金额不能超过合计金额", Toast.LENGTH_SHORT).show();
                } else if (Double.parseDouble(tv_xche_ysje.getText().toString()) < Double.parseDouble(et_xche_ssje.getText().toString())) {
                    Toast.makeText(getContext(), "实收金额不能超过应收金额", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void initView() {
        tv_xche_hjje = (TextView) view.findViewById(R.id.tv_xche_hjje);
        et_xmyhje = (EditText) view.findViewById(R.id.et_xmyhje);
        et_clyhje = (EditText) view.findViewById(R.id.et_clyhje);
        tv_xche_ysje = (TextView) view.findViewById(R.id.tv_xche_ysje);
        et_xche_ssje = (EditText) view.findViewById(R.id.et_xche_ssje);
        et_cardno = (EditText) view.findViewById(R.id.et_cardno);
        et_cardpass = (EditText) view.findViewById(R.id.et_cardpass);
        tv_cardleftje = (TextView) view.findViewById(R.id.tv_cardleftje);
        checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        tv_buxianjin = (TextView) view.findViewById(R.id.tv_buxianjin);
        ll_buxianjin = (LinearLayout) view.findViewById(R.id.ll_buxianjin);
        tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        bsd_wzzl_js_duqu = (TextView) view.findViewById(R.id.bsd_wzzl_js_duqu);
        bsd_wzzl_js_duqu.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        //是否使用储值卡监听改变
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                if (ischecked) {
                    isUseCard = 1;
                    ll_buxianjin.setVisibility(View.VISIBLE);
                    // 如果应收金额大于等于会员卡剩余金额
                    if (xche_ysje.subtract(card_leftje).compareTo(BigDecimal.ZERO) >= 0) {
                        buXianJin = xche_ysje.subtract(card_leftje);
                        et_xche_ssje.setText(card_leftje.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                        tv_buxianjin.setText(buXianJin.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                    } else { // 如果应收金额小于会员卡剩余金额
                        buXianJin = new BigDecimal(0);
                        et_xche_ssje.setText(xche_ysje.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                        tv_buxianjin.setText(buXianJin.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                    }
                } else {
                    isUseCard = 0;
                    ll_buxianjin.setVisibility(View.GONE);
                    et_xche_ssje.setText(xche_ysje.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                    tv_buxianjin.setText("0.00");
                }
            }
        });
        //项目改变
        et_xmyhje.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(et_xmyhje.getText().toString())) {
                    xche_wxxm_yhje = new BigDecimal(0);
                } else {
                    try {
                        xche_wxxm_yhje = new BigDecimal(et_xmyhje.getText().toString());
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "输入的数值格式不正确", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                xche_ysje = xche_hjje.subtract(xche_wxxm_yhje).subtract(xche_peij_yhje);
                tv_xche_ysje.setText(xche_ysje.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                if (checkBox.isChecked()) {
                    if (xche_ysje.subtract(card_leftje).compareTo(BigDecimal.ZERO) >= 0) {
                        buXianJin = xche_ysje.subtract(card_leftje);
                        xche_ssje = card_leftje;
                        et_xche_ssje.setText(xche_ssje.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                        tv_buxianjin.setText(buXianJin.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                    } else { // 如果应收金额小于会员卡剩余金额
                        buXianJin = new BigDecimal(0);
                        xche_ssje = xche_ysje;
                        et_xche_ssje.setText(xche_ssje.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                        tv_buxianjin.setText(buXianJin.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                    }
                } else {
                    xche_ssje = xche_ysje;
                    et_xche_ssje.setText(xche_ssje.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                }
            }
        });
        //材料改变
        et_clyhje.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(et_clyhje.getText().toString())) {
                    xche_peij_yhje = new BigDecimal(0);
                } else {
                    try {
                        xche_peij_yhje = new BigDecimal(et_clyhje.getText().toString());
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "输入的数值格式不正确", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                xche_ysje = xche_hjje.subtract(xche_wxxm_yhje).subtract(xche_peij_yhje);
                tv_xche_ysje.setText(xche_ysje.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                if (checkBox.isChecked()) {
                    if (xche_ysje.subtract(card_leftje).compareTo(BigDecimal.ZERO) >= 0) {
                        buXianJin = xche_ysje.subtract(card_leftje);
                        xche_ssje = card_leftje;
                        et_xche_ssje.setText(xche_ssje.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                        tv_buxianjin.setText(buXianJin.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                    } else { // 如果应收金额小于会员卡剩余金额
                        buXianJin = new BigDecimal(0);
                        xche_ssje = xche_ysje;
                        et_xche_ssje.setText(xche_ssje.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                        tv_buxianjin.setText(buXianJin.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                    }
                } else {
                    xche_ssje = xche_ysje;
                    et_xche_ssje.setText(xche_ssje.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                }
            }
        });
        //实收改变
        et_xche_ssje.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(et_xche_ssje.getText().toString())) {
                    xche_ssje = new BigDecimal(0);
                } else {
                    try {
                        xche_ssje = new BigDecimal(et_xche_ssje.getText().toString());
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "输入的数值格式不正确", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });
    }

    private void initData() {
        getJieSuanInfo();
    }

    /**
     * 获取结算的一些信息
     * 会员卡剩余金额，项目优惠金额，材料优惠金额
     */
    private void getJieSuanInfo() {
        AbRequestParams params = new AbRequestParams();
        params.put("card_no", this.cardNo);
        params.put("work_no", this.workNo);
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_GETBILLJIESUANINFO, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    if (jsonObject.getString("message").equals("查询成功")) {
                        JSONObject object = jsonObject.getJSONObject("data");
                        xche_wxxm_yhje = new BigDecimal(object.getString("xche_wxxm_yhje"));
                        xche_peij_yhje = new BigDecimal(object.getString("xche_peij_yhje"));
                        card_leftje = new BigDecimal(object.getString("card_leftje"));
                        xche_hjje = new BigDecimal(object.getString("xche_hjje"));
                        et_cardno.setText(cardNo);
                        tv_cardleftje.setText(card_leftje.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                        et_xmyhje.setText(xche_wxxm_yhje.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                        et_clyhje.setText(xche_peij_yhje.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                        tv_xche_hjje.setText(xche_hjje.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                        xche_ysje = xche_hjje.subtract(xche_wxxm_yhje).subtract(xche_peij_yhje);
                        xche_ssje = xche_ysje;
                        tv_xche_ysje.setText(xche_ysje.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                        et_xche_ssje.setText(xche_ssje.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString()); // 实收金额默认显示为应收金额
                        buXianJin = new BigDecimal(0);
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
     * 会员卡查询接口
     */
    public void getCardInfo() {
        AbRequestParams params = new AbRequestParams();
        params.put("card_no", et_cardno.getText().toString());
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_MRKX_HYK, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aa, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getString("message").equals("查询成功")) {
                        JSONObject object = jsonObject.getJSONObject("data");
                        card_leftje = new BigDecimal(object.getString("card_leftje"));
                        cardNo = et_cardno.getText().toString();
                        tv_cardleftje.setText(card_leftje.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                        queRen = new QueRen(getContext(), "读取成功，卡内余额" + card_leftje.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
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
                        tv_cardleftje.setText("");
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

    public void dissm() {
        this.dismiss();
    }


    public void diss() {
        this.dissm();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bsd_wzzl_js_duqu:
                readCardInfo();
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_confirm:
                jiesuan();
                break;
        }
    }

    private void jiesuan() {
        if (xche_ysje.compareTo(BigDecimal.ZERO) < 0) {
            Toast.makeText(getContext(), "优惠金额不能大于合计金额", Toast.LENGTH_SHORT).show();
            return;
        }
        if (xche_ysje.subtract(xche_ssje).subtract(buXianJin).compareTo(BigDecimal.ZERO) < 0) {
            Toast.makeText(getContext(), "收款金额不能大于应收金额", Toast.LENGTH_SHORT).show();
            return;
        }
        jieSuan.onyes(Double.parseDouble(tv_xche_hjje.getText().toString()),
                shishoujine,
                wxxmYh,
                wxclYh,
                Double.parseDouble(tv_xche_ysje.getText().toString()),
                cardNo,
                et_cardpass.getText().toString(),
                isUseCard,
                Double.parseDouble(tv_buxianjin.getText().toString()),
                shishoujine,
                Double.parseDouble(tv_buxianjin.getText().toString()));
    }

    private void readCardInfo() {
        DownJianPan.hide(getContext(), et_cardno);
        if (TextUtils.isEmpty(et_cardno.getText().toString())) {
            Toast.makeText(getContext(), "会员卡卡号不能为空", Toast.LENGTH_SHORT).show();
        } else {
            getCardInfo();
        }
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
