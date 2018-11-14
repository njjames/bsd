package com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.deleg;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.example.administrator.boshide2.Modular.Adapter.AbstractSpinerAdapter;
import com.example.administrator.boshide2.Modular.Adapter.CustemSpinerAdapter;
import com.example.administrator.boshide2.Modular.Entity.CustemObject;
import com.example.administrator.boshide2.Modular.View.SpinerPopWindow;
import com.example.administrator.boshide2.Modular.View.Time.TimeDialog;
import com.example.administrator.boshide2.Modular.View.diaog.QueRen;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.DownJianPan;
import com.example.administrator.boshide2.Tools.QuanQuan.WeiboDialogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 快速报价----选择品牌 ----diaglog
 */
public class BSD_mrkx_jiesuan extends Dialog implements View.OnClickListener {
    private String workNo;
    private View view;
    TextView bsd_wzzl_tv_qx;
    OnJieSuanListener jieSuan;

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
    private String cheNo;
    private BigDecimal xche_wxxm_yhje;
    private BigDecimal xche_peij_yhje;
    private BigDecimal card_leftje;
    private BigDecimal xche_hjje;
    private BigDecimal xche_ysje;
    private BigDecimal buXianJin;
    private BigDecimal xche_ssje;
    private TextView tv_xche_ssje;
    boolean isChangeSsje; // 是否触发实收金额改变的监听回调
    private QueRen queRen;
    private LinearLayout ll_jsfs;
    private TextView tv_jsfs;
    private LinearLayout ll_zhanghao;
    private TextView tv_zhanghao;
    private List<Map<String, String>> listJsfs = new ArrayList<>();
    private List<Map<String, String>> listZH = new ArrayList<>();
    private List<CustemObject> nameList1 = new ArrayList<>();
    private List<CustemObject> nameList2 = new ArrayList<>();
    private AbstractSpinerAdapter mAdapter1;
    private AbstractSpinerAdapter mAdapter2;
    private SpinerPopWindow mSpinerPopWindow1;
    private SpinerPopWindow mSpinerPopWindow2;
    private String currentJSFSId;
    private String currentJSFSName;
    private LinearLayout ll_nexttx;
    private CheckBox cb_nexttx;
    private EditText et_next_bylc;
    private LinearLayout ll_next_byrq;
    private TextView tv_next_byrq;
    private LinearLayout ll_next_info;
    private int sys_baoyang_che_fs;
    private TimeDialog timeShow;
    private BigDecimal che_baoyanglicheng;
    private BigDecimal che_rjlc;
    private BigDecimal che_next_licheng;
    private OnJieSuanListener onJieSuanListener;
    private Dialog mWeiboDialog;

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

    public void setJieSuan(OnJieSuanListener jieSuan) {
        this.jieSuan = jieSuan;
    }

    Guanbi gb;

    public Guanbi getGb() {
        return gb;
    }

    public void setGb(Guanbi gb) {
        this.gb = gb;
    }

    public BSD_mrkx_jiesuan(final Context context, final String cardNo, String workNo, String cheNo) {
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
        this.cheNo = cheNo;
        isChangeSsje = true;
        initView();
        initData();
    }

    private void initView() {
        tv_xche_hjje = (TextView) view.findViewById(R.id.tv_xche_hjje);
        et_xmyhje = (EditText) view.findViewById(R.id.et_xmyhje);
        et_clyhje = (EditText) view.findViewById(R.id.et_clyhje);
        tv_xche_ysje = (TextView) view.findViewById(R.id.tv_xche_ysje);
        et_xche_ssje = (EditText) view.findViewById(R.id.et_xche_ssje); // 实收金额，如果用储值卡支付，就是储值卡支付的金额，如果不是储值卡支付，就是收款金额
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
        tv_confirm.setOnClickListener(this);
        tv_xche_ssje = (TextView) view.findViewById(R.id.tv_xche_ssje);
        ll_jsfs = (LinearLayout) view.findViewById(R.id.ll_jsfs);
        tv_jsfs = (TextView) view.findViewById(R.id.tv_jsfs);
        ll_jsfs.setOnClickListener(this);
        ll_zhanghao = (LinearLayout) view.findViewById(R.id.ll_zhanghao);
        tv_zhanghao = (TextView) view.findViewById(R.id.tv_zhanghao);
        ll_zhanghao.setOnClickListener(this);
        ll_nexttx = (LinearLayout) view.findViewById(R.id.ll_nexttx);
        cb_nexttx = (CheckBox) view.findViewById(R.id.cb_nexttx);
        et_next_bylc = (EditText) view.findViewById(R.id.et_next_bylc);
        ll_next_byrq = (LinearLayout) view.findViewById(R.id.ll_next_byrq);
        tv_next_byrq = (TextView) view.findViewById(R.id.tv_next_byrq);
        ll_next_info = (LinearLayout) view.findViewById(R.id.ll_next_info);
        ll_next_byrq.setOnClickListener(this);
        cb_nexttx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar calendar = Calendar.getInstance();
                    if (che_baoyanglicheng.compareTo(BigDecimal.ZERO) != 0 && che_rjlc.compareTo(BigDecimal.ZERO) != 0) {
                        calendar.add(Calendar.DATE, che_baoyanglicheng.divide(che_rjlc, 2, BigDecimal.ROUND_HALF_UP).intValue());
                        tv_next_byrq.setText(format.format(calendar.getTime()));
                        et_next_bylc.setText(che_next_licheng.add(che_baoyanglicheng).toString());
                    } else {
                        calendar.add(Calendar.DATE, 90);
                        tv_next_byrq.setText(format.format(calendar.getTime()));
                    }
                    ll_next_info.setVisibility(View.VISIBLE);
                } else {
                    et_next_bylc.getText().clear();
                    tv_next_byrq.setText("");
                    ll_next_info.setVisibility(View.INVISIBLE);
                }
            }
        });
        // 是否使用储值卡监听改变
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                isChangeSsje = false;
                if (ischecked) {
                    isUseCard = 1;
                    tv_xche_ssje.setText("刷卡金额：");
                    // 如果应收金额大于等于会员卡剩余金额
                    if (xche_ysje.subtract(card_leftje).compareTo(BigDecimal.ZERO) >= 0) {
                        buXianJin = xche_ysje.subtract(card_leftje);
                        xche_ssje = card_leftje;
                    } else { // 如果应收金额小于会员卡剩余金额
                        buXianJin = new BigDecimal(0);
                        xche_ssje = xche_ysje;
                    }
                    et_xche_ssje.setText(xche_ssje.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                    tv_buxianjin.setText(buXianJin.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                } else {
                    isUseCard = 0;
                    tv_xche_ssje.setText("实收金额：");
                    xche_ssje = xche_ysje;
                    et_xche_ssje.setText(xche_ysje.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                    tv_buxianjin.setText("0.00");
                }
                et_xche_ssje.requestFocus();
                et_xche_ssje.setSelection(et_xche_ssje.getText().length());
                isChangeSsje = true;
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
                isChangeSsje = false;
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
                    } else { // 如果应收金额小于会员卡剩余金额
                        buXianJin = new BigDecimal(0);
                        xche_ssje = xche_ysje;
                    }
                    et_xche_ssje.setText(xche_ssje.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                    tv_buxianjin.setText(buXianJin.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                } else {
                    xche_ssje = xche_ysje;
                    et_xche_ssje.setText(xche_ssje.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                }
                isChangeSsje = true;
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
                isChangeSsje = false;
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
                    } else { // 如果应收金额小于会员卡剩余金额
                        buXianJin = new BigDecimal(0);
                        xche_ssje = xche_ysje;
                    }
                    et_xche_ssje.setText(xche_ssje.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                    tv_buxianjin.setText(buXianJin.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                } else {
                    xche_ssje = xche_ysje;
                    et_xche_ssje.setText(xche_ssje.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                }
                isChangeSsje = true;
            }
        });
        // 实收改变,实收改变（也就是刷卡金额改变），只会更改补现金
        et_xche_ssje.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (isChangeSsje) {
                    isChangeSsje = false;
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
                    if (checkBox.isChecked()) {
                        if (xche_ysje.subtract(card_leftje).compareTo(BigDecimal.ZERO) >= 0) {
                            // 如果刷卡金额大于剩余金额
                            if (xche_ssje.subtract(card_leftje).compareTo(BigDecimal.ZERO) > 0) {
                                Toast.makeText(getContext(), "刷卡金额不能大于剩余金额", Toast.LENGTH_SHORT).show();
                                xche_ssje = card_leftje;
                                et_xche_ssje.setText(xche_ssje.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                                et_xche_ssje.setSelection(xche_ssje.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString().length());
                            }
                            buXianJin = xche_ysje.subtract(xche_ssje);
                            tv_buxianjin.setText(buXianJin.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                        } else { // 如果应收金额小于会员卡剩余金额
                            if (xche_ssje.subtract(xche_ysje).compareTo(BigDecimal.ZERO) > 0) {
                                Toast.makeText(getContext(), "刷卡金额不能大于应收金额", Toast.LENGTH_SHORT).show();
                                xche_ssje = xche_ysje;
                                et_xche_ssje.setText(xche_ssje.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                                et_xche_ssje.setSelection(xche_ssje.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString().length());
                            }
                            buXianJin = xche_ysje.subtract(xche_ssje);
                            tv_buxianjin.setText(buXianJin.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                        }
                    }
                    isChangeSsje = true;
                }
            }
        });
        // 会员卡改变时，清空密码
        et_cardno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                et_cardpass.getText().clear();
            }
        });
        et_xche_ssje.requestFocus();
    }

    private void initData() {
        getJieSuanInfo();
        timeShow = new TimeDialog(getContext());
    }

    /**
     * 获取结算的一些信息
     * 会员卡剩余金额，项目优惠金额，材料优惠金额
     */
    private void getJieSuanInfo() {
        AbRequestParams params = new AbRequestParams();
        params.put("card_no", this.cardNo);
        params.put("work_no", this.workNo);
        params.put("che_no", this.cheNo);
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_GETBILLJIESUANINFO, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    if (jsonObject.getString("message").equals("查询成功")) {
                        isChangeSsje = false;
                        JSONObject object = jsonObject.getJSONObject("data");
                        sys_baoyang_che_fs = object.getInt("sys_baoyang_che_fs");
                        xche_wxxm_yhje = new BigDecimal(object.getString("xche_wxxm_yhje"));
                        xche_peij_yhje = new BigDecimal(object.getString("xche_peij_yhje"));
                        card_leftje = new BigDecimal(object.getString("card_leftje"));
                        xche_hjje = new BigDecimal(object.getString("xche_hjje"));
                        che_next_licheng = new BigDecimal(object.getString("che_next_licheng"));
                        che_rjlc = new BigDecimal(object.getString("che_rjlc"));
                        che_baoyanglicheng = new BigDecimal(object.getString("che_baoyanglicheng"));
                        et_cardno.setText(cardNo);
                        tv_cardleftje.setText(card_leftje.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                        et_xmyhje.setText(xche_wxxm_yhje.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                        et_clyhje.setText(xche_peij_yhje.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                        tv_xche_hjje.setText(xche_hjje.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                        xche_ysje = xche_hjje.subtract(xche_wxxm_yhje).subtract(xche_peij_yhje);
                        xche_ssje = xche_ysje;
                        tv_xche_ysje.setText(xche_ysje.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                        et_xche_ssje.setText(xche_ssje.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString()); // 实收金额默认显示为应收金额
                        et_xche_ssje.setSelection(xche_ssje.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString().length());
                        buXianJin = new BigDecimal(0);
                        isChangeSsje = true;
                        if (sys_baoyang_che_fs == 0) {
                            ll_nexttx.setVisibility(View.GONE);
                        } else {
                            ll_nexttx.setVisibility(View.VISIBLE);
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
     * 会员卡查询接口
     */
    public void getCardInfo() {
        AbRequestParams params = new AbRequestParams();
        params.put("card_no", et_cardno.getText().toString());
        params.put("che_no", cheNo);
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
                        if (checkBox.isChecked()) {
                            isChangeSsje = false;
                            // 如果应收金额大于等于会员卡剩余金额
                            if (xche_ysje.subtract(card_leftje).compareTo(BigDecimal.ZERO) >= 0) {
                                buXianJin = xche_ysje.subtract(card_leftje);
                                xche_ssje = card_leftje;
                            } else { // 如果应收金额小于会员卡剩余金额
                                buXianJin = new BigDecimal(0);
                                xche_ssje = xche_ysje;
                            }
                            et_xche_ssje.setText(xche_ssje.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                            tv_buxianjin.setText(buXianJin.setScale(Conts.NORMAL_DIGIT, RoundingMode.HALF_UP).toString());
                            isChangeSsje = true;
                        }
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
                Toast.makeText(getContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void dissm() {
        this.dismiss();
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
            case R.id.ll_jsfs:
                getJSFSData();
                break;
            case R.id.ll_zhanghao:
                getZHData();
                break;
            case R.id.ll_next_byrq:
                timeShow.timePickerAlertDialog(tv_next_byrq);
                break;
        }
    }

    private void getZHData() {
        listZH.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("type", "ITBank");
        params.put("GongSiNo", MyApplication.shared.getString("GongSiNo", ""));
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_HYGL_ADD_TYPE, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    if (jsonObject.get("status").toString().equals("1")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("yh_name", String.valueOf(jsonarray.get(i)));
                            listZH.add(map);
                        }
                    }
                    updateZHData();
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
                Toast.makeText(getContext(), "获取账号失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateZHData() {
        nameList2.clear();
        for (int i = 0; i < listZH.size(); i++) {
            CustemObject object = new CustemObject();
            object.data = listZH.get(i).get("yh_name");
            nameList2.add(object);
        }
        mAdapter2 = new CustemSpinerAdapter(getContext());
        mAdapter2.refreshData(nameList2, 0);
        mSpinerPopWindow2 = new SpinerPopWindow(getContext());
//        mSpinerPopWindow2.setAdatper(mAdapter2, 800);
        mSpinerPopWindow2.setAdatper(mAdapter2);
        mSpinerPopWindow2.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
            @Override
            public void onItemClick(int pos) {
                String clickValue = nameList2.get(pos).toString();
                if (!tv_zhanghao.getText().toString().equals(clickValue)) {
                    tv_zhanghao.setText(clickValue);
                    currentJSFSName = listZH.get(pos).get("yh_name");
                }
            }
        });
        mSpinerPopWindow2.setWidth(ll_zhanghao.getWidth());
        mSpinerPopWindow2.showAsDropDown(ll_zhanghao);
    }

    private void getJSFSData() {
        listJsfs.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("type", "ITJiesuan");
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_HYGL_ADD_TYPE, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    if (jsonObject.get("status").toString().equals("1")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject item = jsonarray.getJSONObject(i);
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("id", item.getString("reco_no"));
                            map.put("jiesuan_mc", item.getString("jiesuan_mc"));
                            listJsfs.add(map);
                        }
                    }
                    updateJSFSData();
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
                Toast.makeText(getContext(), "获取结算方式失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateJSFSData() {
        nameList1.clear();
        for (int i = 0; i < listJsfs.size(); i++) {
            CustemObject object = new CustemObject();
            object.data = listJsfs.get(i).get("jiesuan_mc");
            nameList1.add(object);
        }
        mAdapter1 = new CustemSpinerAdapter(getContext());
        mAdapter1.refreshData(nameList1, 0);
        mSpinerPopWindow1 = new SpinerPopWindow(getContext());
        mSpinerPopWindow1.setAdatper(mAdapter1);
        mSpinerPopWindow1.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
            @Override
            public void onItemClick(int pos) {
                String clickValue = nameList1.get(pos).toString();
                if (!tv_jsfs.getText().toString().equals(clickValue)) {
                    tv_jsfs.setText(clickValue);
                    currentJSFSId = listJsfs.get(pos).get("id");
                    currentJSFSName = listJsfs.get(pos).get("jiesuan_mc");
                }
            }
        });
        mSpinerPopWindow1.setWidth(ll_jsfs.getWidth());
        mSpinerPopWindow1.showAsDropDown(ll_jsfs);
    }

    /**
     * 检测操作员的优惠率
     */
    private void checkBillYhlv() {
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", workNo);
        params.put("xm_yhje", xche_wxxm_yhje.toString());
        params.put("cl_yhje", xche_peij_yhje.toString());
        params.put("caozuoyuanID", MyApplication.shared.getString("userid", ""));
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_CKECKYHLV, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aa, String data) {
                if (data.equals("success")) {
                    // 优惠率正确
                    // 如果勾选了储值卡结算，则检测会员卡密码是否正确
                    if (checkBox.isChecked()) {
                        checkCardPass();
                    } else { // 如果没有勾选储值卡结算，则真正结算
                        realJieSuan();
                    }
                } else {
                    queRen = new QueRen(getContext(), "手工优惠金额超出了本操作员的“整单折扣率”范围！");
                    queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                        @Override
                        public void onYesClick() {
                            et_xmyhje.requestFocus();
                            et_xmyhje.setSelection(et_xmyhje.getText().length());
                            queRen.dismiss();
                        }
                    });
                    queRen.show();
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
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void jiesuan() {
        // 先检测一些不用访问接口的逻辑
        if (xche_ysje.compareTo(BigDecimal.ZERO) < 0) {
            Toast.makeText(getContext(), "优惠金额不能大于合计金额", Toast.LENGTH_SHORT).show();
            et_xmyhje.requestFocus();
            et_xmyhje.setSelection(et_xmyhje.getText().length());
            return;
        }
        if (xche_ysje.subtract(xche_ssje).subtract(buXianJin).compareTo(BigDecimal.ZERO) < 0) {
            Toast.makeText(getContext(), "收款金额不能大于应收金额", Toast.LENGTH_SHORT).show();
            et_xche_ssje.requestFocus();
            et_xche_ssje.setSelection(et_xche_ssje.getText().length());
            return;
        }
        if (checkBox.isChecked()) {
            if (TextUtils.isEmpty(cardNo)) {
                if (TextUtils.isEmpty(et_cardno.getText().toString())) {
                    queRen = new QueRen(getContext(), "当前没有读取到会员卡\n如果不想用会员卡支付\n请将储值卡结算的选项去掉！");
                    queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                        @Override
                        public void onYesClick() {
                            queRen.dismiss();
                        }
                    });
                    queRen.show();
                    return;
                } else {
                    queRen = new QueRen(getContext(), "输入框中的会员卡还未读取\n请读取之后再进行结算！");
                    queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                        @Override
                        public void onYesClick() {
                            queRen.dismiss();
                        }
                    });
                    queRen.show();
                    return;
                }
            } else {
                if (TextUtils.isEmpty(et_cardno.getText().toString())) {
                    queRen = new QueRen(getContext(), "当前已读取会员卡【" + cardNo + "】\n如果不想用会员卡支付\n请将储值卡结算的选项去掉！");
                    queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                        @Override
                        public void onYesClick() {
                            queRen.dismiss();
                        }
                    });
                    queRen.show();
                    return;
                } else {
                    if (!et_cardno.getText().toString().equals(cardNo)) {
                        queRen = new QueRen(getContext(), "当前已读取的会员卡【" + cardNo + "】\n与输入框内的会员卡不一致\n请重新读取会员卡！");
                        queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                            @Override
                            public void onYesClick() {
                                queRen.dismiss();
                            }
                        });
                        queRen.show();
                        return;
                    }
                }
            }
        }
        // 检测下次保养时间和里程
        if (cb_nexttx.isChecked()) {
            if (TextUtils.isEmpty(et_next_bylc.getText().toString())) {
                queRen = new QueRen(getContext(), "下次保养里程不能为空！");
                queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick() {
                        queRen.dismiss();
                    }
                });
                queRen.show();
                return;
            }
            if (TextUtils.isEmpty(tv_next_byrq.getText().toString())) {
                queRen = new QueRen(getContext(), "下次保养保养时间不能为空！");
                queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick() {
                        queRen.dismiss();
                    }
                });
                queRen.show();
                return;
            }
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date nextRq = sdf.parse(tv_next_byrq.getText().toString());
                Date nowRq = new Date();
                if (nextRq.before(nowRq)) {
                    queRen = new QueRen(getContext(), "下次保养保养时间不能小于今天！");
                    queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                        @Override
                        public void onYesClick() {
                            queRen.dismiss();
                        }
                    });
                    queRen.show();
                    return;
                }
            } catch (ParseException e) {
                e.printStackTrace();
                return;
            }

        }
        // 检测需要访问接口的逻辑
        checkBillYhlv();
    }

    /**
     * 检测会员卡密码是否正确
     */
    private void checkCardPass() {
        AbRequestParams params = new AbRequestParams();
        params.put("card_no", cardNo);
        params.put("card_pass", et_cardpass.getText().toString());
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_CKECKCARDPASS, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aa, String data) {
                if (data.equals("success")) {
                    // 正确之后，开始结算
                    realJieSuan();
                } else {
                    queRen = new QueRen(getContext(), "会员卡密码不正确！");
                    queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                        @Override
                        public void onYesClick() {
                            et_cardpass.requestFocus();
                            et_cardpass.setSelection(et_cardpass.getText().length());
                            queRen.dismiss();
                        }
                    });
                    queRen.show();
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
                Toast.makeText(getContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 真正的结算
     */
    private void realJieSuan() {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getContext(), "结算中...");
        AbRequestParams params = new AbRequestParams();
        params.put("caozuoyuanid", MyApplication.shared.getString("name", ""));
        params.put("work_no", workNo);
        params.put("che_no", cheNo);
        params.put("xche_hjje", xche_hjje.toString());
        params.put("xche_ysje", xche_ysje.toString());
        params.put("card_no", cardNo);
        if (checkBox.isChecked()) {
            params.put("iscard", 1);
            params.put("xche_ssje", xche_ysje.toString()); // 用会员卡结算，款肯定是全收了
            params.put("zhifu_card_je", xche_ssje.toString());//实收
            params.put("zhifu_card_xj", buXianJin.toString());//补现金
        } else {
            params.put("iscard", 0);
            params.put("xche_ssje", xche_ssje.toString());
            params.put("zhifu_card_je", "0");
            params.put("zhifu_card_xj", "0");
        }
        params.put("xche_jsfs", tv_jsfs.getText().toString());
        params.put("yh_zhanghao", tv_zhanghao.getText().toString());
        params.put("caozuoyuan_xm", MyApplication.shared.getString("name", ""));
        if (sys_baoyang_che_fs == 0) {
            params.put("sys_baoyang_che_fs", 0);
            if (cb_nexttx.isChecked()) {
                params.put("isNextTx", 1);
                params.put("next_bylc", et_next_bylc.getText().toString());
                params.put("next_byrq", tv_next_byrq.getText().toString());
            } else {
                params.put("isNextTx", 0);
                params.put("next_bylc", "");
                params.put("next_byrq", "");
            }
        } else {
            params.put("sys_baoyang_che_fs", 1);
            params.put("isNextTx", 0);
            params.put("next_bylc", "");
            params.put("next_byrq", "");
        }
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_REALJIESUAN, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aa, String data) {
                if (data.equals("success")) {
                    weiXin();
                    queRen = new QueRen(getContext(), "结算成功！");
                    queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                        @Override
                        public void onYesClick() {
                            queRen.dismiss();
                            dissm();
                            if (onJieSuanListener != null) {
                                onJieSuanListener.onSuccess();
                            }
                        }
                    });
                    queRen.show();
                }
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                queRen = new QueRen(getContext(), s);
                queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick() {
                        queRen.dismiss();
                    }
                });
                queRen.show();
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });
    }

    /**
     * 发送微信
     * 不管是否发送成功了
     */
    private void weiXin() {
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", workNo);
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_mrkx_weixin, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
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

    private void readCardInfo() {
        DownJianPan.hide(getContext(), et_cardno);
        if (TextUtils.isEmpty(et_cardno.getText().toString())) {
            Toast.makeText(getContext(), "会员卡卡号不能为空", Toast.LENGTH_SHORT).show();
        } else {
            getCardInfo();
        }
    }

    public interface OnJieSuanListener {
        void onSuccess();
    }

    public void setOnJieSuanListener(OnJieSuanListener onJieSuanListener) {
        this.onJieSuanListener = onJieSuanListener;
    }

    public interface Guanbi {
        void guanbi();


    }
}
