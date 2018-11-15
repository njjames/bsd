package com.example.administrator.boshide2.Modular.Fragment.HuiYuanGuanLi.PopWindow;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Adapter.AbstractSpinerAdapter;
import com.example.administrator.boshide2.Modular.Adapter.CustemSpinerAdapter;
import com.example.administrator.boshide2.Modular.Entity.CustemObject;
import com.example.administrator.boshide2.Modular.Fragment.HuiYuanGuanLi.Adapter.BSD_hygl_bumen_adp;
import com.example.administrator.boshide2.Modular.Fragment.HuiYuanGuanLi.Adapter.BSD_hygl_jiesuan_adp;
import com.example.administrator.boshide2.Modular.Fragment.HuiYuanGuanLi.Adapter.BSD_hygl_jingban_adp;
import com.example.administrator.boshide2.Modular.Fragment.HuiYuanGuanLi.Adapter.BSD_hygl_katype_adp;
import com.example.administrator.boshide2.Modular.View.SpinerPopWindow;
import com.example.administrator.boshide2.Modular.View.Time.TimeDialog;
import com.example.administrator.boshide2.Modular.View.diaog.QueRen;
import com.example.administrator.boshide2.Modular.View.diaog.TooPromptdiaog;
import com.example.administrator.boshide2.Modular.View.timepicker.TimePickerShow;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.QuanQuan.WeiboDialogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * 添加会员
 */
public class BSD_HYGL_TianJia_delo extends Dialog implements View.OnClickListener {
    private View view;
    EditText et_hy_chepai, et_hy_lianxi, et_hy_address, et_hy_phone, et_hy_shouphone, et_hy_kahao, et_hy_kaimoney, et_hy_shishou, et_hy_youhui, et_hy_keyong, et_hy_shouxi, et_hy_jingxi,
            et_hy_jifen, et_hy_zhanghao, et_hy_beizhu;//申明一些空间
    TextView tv_hy_banli;//确认办理
    TextView et_hy_birthday, et_hy_youdate, tv_dept, tv_jsfs, tv_jbr, tv_cardkind;
    TextView bsd_chepaichaun, et_hy_keyongjie;
    TimePickerShow timePickerShow;
    LinearLayout ll_birthday, rela_youxiao, ll_dept, ll_jsfs, ll_jbr, ll_cardkind;
    TooPromptdiaog promptdiaog;
    //PopupWindow对象声明
    PopupWindow pw;
    //当前选中的列表项位置
    private BSD_hygl_bumen_adp hygl_bumen_adp;//会员管理--部门适配器
    List<Map<String, String>> list_bumen = new ArrayList<Map<String, String>>();//会员管理--list集合
    private BSD_hygl_jiesuan_adp hygl_jiesuan_adp;//结算适配器
    List<Map<String, String>> list_jiesuan = new ArrayList<Map<String, String>>();//结算方式--集合
    private BSD_hygl_jingban_adp jingban_adp;//经办人适配器
    List<Map<String, String>> list_jingban = new ArrayList<Map<String, String>>();//经办人--集合
    private BSD_hygl_katype_adp katype_adp;//卡类型
    List<Map<String, String>> list_katype = new ArrayList<Map<String, String>>();//卡类型--集合
    String bumen, jiesuan, jingban, katype, chepai, yxq, px, jx, khje;//吧list里面数据拿出来，赋给bumen,然后显示

    List<Map<String, String>> list_cp = new ArrayList<Map<String, String>>();//卡类型--集合

    URLS url;
    private static String mYear; // 当前年
    private static String mMonth; // 月
    private static String mDay;
    private static String mWay;

    QueRen queRen;
    TimeDialog timeShow;
    //转圈
    private Dialog mWeiboDialog;
    private TextView tv_cancel;

    private List<CustemObject> nameList = new ArrayList<>();
    private List<CustemObject> nameList1 = new ArrayList<>();
    private List<CustemObject> nameList2 = new ArrayList<>();
    private List<CustemObject> nameList3 = new ArrayList<>();
    private AbstractSpinerAdapter mAdapter;
    private AbstractSpinerAdapter mAdapter1;
    private AbstractSpinerAdapter mAdapter2;
    private AbstractSpinerAdapter mAdapter3;
    private SpinerPopWindow mSpinerPopWindow;
    private SpinerPopWindow mSpinerPopWindow1;
    private SpinerPopWindow mSpinerPopWindow2;
    private SpinerPopWindow mSpinerPopWindow3;

    public BSD_HYGL_TianJia_delo(Context context) {
        super(context, R.style.mydialog);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        view = getLayoutInflater().inflate(R.layout.bsd_hygl_tianjia, null, false);
        url = new URLS();
        timeShow = new TimeDialog(getContext());
        timePickerShow = new TimePickerShow(getContext());
        initview();
        getDeptData();//部门
        getJSFSData();//结算下拉
        getJBRData();//经办人
        getCardKindData();
        setContentView(view);
        setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = (int) getContext().getResources().getDimension(R.dimen.qb_px_680);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(params);
    }

    private void initview() {
        bsd_chepaichaun = (TextView) view.findViewById(R.id.bsd_chepaichaun);
        ll_cardkind = (LinearLayout) view.findViewById(R.id.ll_cardkind);
        ll_jbr = (LinearLayout) view.findViewById(R.id.ll_jbr);
        ll_jsfs = (LinearLayout) view.findViewById(R.id.ll_jsfs);
        ll_dept = (LinearLayout) view.findViewById(R.id.ll_dept);
        ll_birthday = (LinearLayout) view.findViewById(R.id.ll_birthday);
        rela_youxiao = (LinearLayout) view.findViewById(R.id.ll_youxiaoq);
        et_hy_chepai = (EditText) view.findViewById(R.id.et_hy_chepai);
        et_hy_lianxi = (EditText) view.findViewById(R.id.et_hy_lianxi);
        et_hy_address = (EditText) view.findViewById(R.id.et_hy_address);
        et_hy_phone = (EditText) view.findViewById(R.id.et_hy_phone);
        et_hy_shouphone = (EditText) view.findViewById(R.id.et_hy_shouphone);
        et_hy_birthday = (TextView) view.findViewById(R.id.et_hy_birthday);
        // et_hy_kahao = (EditText) view.findViewById(R.id.et_hy_kahao);
        et_hy_youdate = (TextView) view.findViewById(R.id.et_hy_youdate);
        tv_cardkind = (TextView) view.findViewById(R.id.tv_cardkind);
        et_hy_keyongjie = (TextView) view.findViewById(R.id.et_hy_keyongjie);
        et_hy_kaimoney = (EditText) view.findViewById(R.id.et_hy_kaimoney);
        et_hy_youhui = (EditText) view.findViewById(R.id.et_hy_youhui);
        et_hy_shouxi = (EditText) view.findViewById(R.id.et_hy_shouxi);
        et_hy_shishou = (EditText) view.findViewById(R.id.et_hy_shishou);
        et_hy_keyong = (EditText) view.findViewById(R.id.et_hy_keyong);
        et_hy_jingxi = (EditText) view.findViewById(R.id.et_hy_jingxi);
        et_hy_jifen = (EditText) view.findViewById(R.id.et_hy_jifen);
        et_hy_zhanghao = (EditText) view.findViewById(R.id.et_hy_zhanghao);
        tv_dept = (TextView) view.findViewById(R.id.tv_dept);
        tv_jbr = (TextView) view.findViewById(R.id.tv_jbr);
        tv_jsfs = (TextView) view.findViewById(R.id.tv_jsfs);
        et_hy_beizhu = (EditText) view.findViewById(R.id.et_hy_beizhu);
        tv_hy_banli = (TextView) view.findViewById(R.id.tv_hy_banli);
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(this);
        ll_birthday.setOnClickListener(this);
        rela_youxiao.setOnClickListener(this);
        tv_hy_banli.setOnClickListener(this);
        ll_dept.setOnClickListener(this);
        ll_jsfs.setOnClickListener(this);
        ll_jbr.setOnClickListener(this);
        ll_cardkind.setOnClickListener(this);
        et_hy_chepai.setText("");
        et_hy_lianxi.setText("");
        et_hy_address.setText("");
        et_hy_phone.setText("");
        et_hy_shouphone.setText("");
        et_hy_birthday.setText("");
//        et_hy_kahao.setText("");
        tv_cardkind.setText("");
        et_hy_youdate.setText("");
        et_hy_kaimoney.setText("");
        et_hy_keyongjie.setText("");
        et_hy_shishou.setText("");
        et_hy_youhui.setText("");
        et_hy_keyong.setText("");
        et_hy_shouxi.setText("");
        et_hy_jingxi.setText("");
        et_hy_jifen.setText("");
        et_hy_zhanghao.setText("");
        tv_dept.setText("");
        tv_jbr.setText("");
        tv_jsfs.setText("");

        bsd_chepaichaun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(et_hy_chepai.getText().toString())) {
                    Toast.makeText(getContext(), "请输入车牌号", Toast.LENGTH_SHORT).show();
                } else {
                    getCarInfo(et_hy_chepai.getText().toString());
                }
            }
        });
    }

    public void getCarInfo(String cardNo) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getContext(), "加载中...");
        AbRequestParams params = new AbRequestParams();
        params.put("che_no", cardNo);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_ChePaiXinXi, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        JSONObject json = jsonObject.getJSONObject("data");
                        et_hy_lianxi.setText(json.getString("kehu_mc"));
                        et_hy_address.setText(json.getString("kehu_dz"));
                        et_hy_phone.setText(json.getString("kehu_dh"));
                        et_hy_birthday.setText(json.getString("kehu_birthday"));
                        et_hy_shouphone.setText(json.getString("kehu_sj"));
                    }else {
                        Toast.makeText(getContext(),"不存在此车牌的相关信息",Toast.LENGTH_SHORT).show();
                        et_hy_lianxi.setText("");
                        et_hy_address.setText("");
                        et_hy_phone.setText("");
                        et_hy_birthday.setText("");
                        et_hy_shouphone.setText("");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //生日
            case R.id.ll_birthday:
                timeShow.timePickerAlertDialog(et_hy_birthday);
                break;
            //有效日期
//            case R.id.rela_youxiao:
//                timePickerShow.timePickerAlertDialog(et_hy_youdate);
//                break;
            //确认办理
            case R.id.tv_hy_banli:
                if (TextUtils.isEmpty(et_hy_chepai.getText().toString())) {
                    queRen = new QueRen(getContext(), "车牌号不能为空");
                    queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                        @Override
                        public void onYesClick() {
                            queRen.dismiss();
                        }
                    });
                    queRen.show();
                } else if (TextUtils.isEmpty(et_hy_lianxi.getText().toString())) {
                    queRen = new QueRen(getContext(), "客户不能为空");
                    queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                        @Override
                        public void onYesClick() {
                            queRen.dismiss();
                        }
                    });
                    queRen.show();
                } else if (TextUtils.isEmpty(et_hy_shouphone.getText().toString())) {
                    queRen = new QueRen(getContext(), "手机号不能为空");
                    queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                        @Override
                        public void onYesClick() {
                            queRen.dismiss();
                        }
                    });
                    queRen.show();
                } else if (TextUtils.isEmpty(tv_cardkind.getText().toString())) {
                    queRen = new QueRen(getContext(), "卡名称不能为空");
                    queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                        @Override
                        public void onYesClick() {
                            queRen.dismiss();
                        }
                    });
                    queRen.show();
                } else {
                    checkCarNo(et_hy_chepai.getText().toString());
                }
                break;
            //部门下拉
            case R.id.ll_dept:
                showDeptData();
                break;
            //结算下拉
            case R.id.ll_jsfs:
                showJSFSData();
                break;
            //经办人下
            case R.id.ll_jbr:
                showJBRData();
                break;
            //卡类型
            case R.id.ll_cardkind:
                showCradKindData();
                break;
            case R.id.tv_cancel:
                this.dismiss();
                break;
        }
    }

    /**
     * 部门弹出框显示
     */
    private void popwin_dept() {
        //通过布局注入器，注入布局给View对象
        View myView = getLayoutInflater().inflate(R.layout.bsd_hygl_bumen, null);
        ListView lv = (ListView) myView.findViewById(R.id.lv_hygl_bumen);
        hygl_bumen_adp = new BSD_hygl_bumen_adp(getContext(), list_bumen);
        lv.setAdapter(hygl_bumen_adp);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                bumen = list_bumen.get(i).get("name");
                tv_dept.setText(bumen);
                pw.dismiss();
            }
        });
        //通过view 和宽·高，构造PopopWindow
        pw = new PopupWindow(myView, ll_dept.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //此处为popwindow 设置背景，同事做到点击外部区域，popwindow消失
        pw.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.yuan));
        //设置焦点为可点击
        pw.setFocusable(true);//可以试试设为false的结果
        //将window视图显示在myButton下面
        pw.showAsDropDown(ll_dept);
//        int[] location = new int[2];
////        ll_dept.getLocationOnScreen(location);
//        ll_dept.getLocationInWindow(location);
////        listView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//        pw.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//        int popHeight = pw.getContentView().getMeasuredHeight();
//        ll_dept.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//        int popHeight1 = ll_dept.getMeasuredHeight();
//        pw.showAtLocation(ll_dept, Gravity.NO_GRAVITY, location[0], location[1] + popHeight1);
    }

    /**
     * 结算方式
     */
    private void popwin_jiesuan() {
        //通过布局注入器，注入布局给View对象
        View myView = getLayoutInflater().inflate(R.layout.bsd_hygl_bumen, null);
        ListView lv = (ListView) myView.findViewById(R.id.lv_hygl_bumen);
        hygl_jiesuan_adp = new BSD_hygl_jiesuan_adp(getContext(), list_jiesuan);
        lv.setAdapter(hygl_jiesuan_adp);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                jiesuan = list_jiesuan.get(i).get("name").toString();
                tv_jsfs.setText(jiesuan);
                pw.dismiss();
            }
        });
        //通过view 和宽·高，构造PopopWindow
        pw = new PopupWindow(myView, ll_jsfs.getWidth(), (int) getContext().getResources().getDimension(R.dimen.y200), true);
        pw.setBackgroundDrawable(getContext().getResources().getDrawable(
                //此处为popwindow 设置背景，同事做到点击外部区域，popwindow消失
                R.drawable.yuan));
        //设置焦点为可点击
        pw.setFocusable(true);//可以试试设为false的结果
        //将window视图显示在myButton下面
        pw.showAsDropDown(ll_jsfs);

    }

    /**
     * 获取当前年月日
     *
     * @return
     */
    public static String StringData(int time) {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mYear = String.valueOf(c.get(Calendar.YEAR));// 获取当前年份
        mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        int a;
        a = Integer.parseInt(mYear);
        return (a + time) + "-" + mMonth + "-" + mDay;
    }

    /**
     * 卡类型
     */
    private void popwin_katype() {
        //通过布局注入器，注入布局给View对象
        View myView = getLayoutInflater().inflate(R.layout.bsd_hygl_bumen, null);
        //通过view 和宽·高，构造PopopWindow
        pw = new PopupWindow(myView, ll_cardkind.getWidth(), (int) getContext().getResources().getDimension(R.dimen.y300), true);
        pw.setBackgroundDrawable(getContext().getResources().getDrawable(
                //此处为popwindow 设置背景，同事做到点击外部区域，popwindow消失
                R.drawable.yuan));
        //设置焦点为可点击
        pw.setFocusable(true);//可以试试设为false的结果
        //将window视图显示在myButton下面
        pw.showAsDropDown(ll_cardkind);
        ListView lv = (ListView) myView.findViewById(R.id.lv_hygl_bumen);
        katype_adp = new BSD_hygl_katype_adp(getContext(), list_katype);
        lv.setAdapter(katype_adp);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                katype = list_katype.get(i).get("name").toString();
                tv_cardkind.setText(katype);
                yxq = list_katype.get(i).get("khje").toString();
                et_hy_kaimoney.setText(yxq);
                px = list_katype.get(i).get("px").toString();
                et_hy_shouxi.setText(px);
                jx = list_katype.get(i).get("jx").toString();
                et_hy_jingxi.setText(jx);
                khje = list_katype.get(i).get("yxq").toString();
                et_hy_youdate.setText(khje);
                et_hy_keyongjie.setText(list_katype.get(i).get("card_addje").toString());

                pw.dismiss();
            }
        });
    }

    /**
     * 经办人
     */
    private void popwin_jingban() {
        //通过布局注入器，注入布局给View对象
        View myView = getLayoutInflater().inflate(R.layout.bsd_hygl_bumen, null);
        //通过view 和宽·高，构造PopopWindow
        pw = new PopupWindow(myView, ll_jbr.getWidth(), (int) getContext().getResources().getDimension(R.dimen.y300), true);
        pw.setBackgroundDrawable(getContext().getResources().getDrawable(
                //此处为popwindow 设置背景，同事做到点击外部区域，popwindow消失
                R.drawable.yuan));
        //设置焦点为可点击
        pw.setFocusable(true);//可以试试设为false的结果
        //将window视图显示在myButton下面
        pw.showAsDropDown(ll_jbr);
        ListView lv = (ListView) myView.findViewById(R.id.lv_hygl_bumen);
        jingban_adp = new BSD_hygl_jingban_adp(getContext(), list_jingban);
        lv.setAdapter(jingban_adp);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                jingban = list_jingban.get(i).get("name").toString();
                tv_jbr.setText(jingban);
                pw.dismiss();
            }
        });
    }

    /**
     * 部门
     */
    private void getDeptData() {
        AbRequestParams params = new AbRequestParams();
        params.put("type", "ITdept");
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_HYGL_ADD_TYPE, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.get("status").toString().equals("1")) {
                        JSONArray jsonarray = object.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject json = jsonarray.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<>();
                            map.put("name", json.get("dept_mc").toString());
                            map.put("bianhao", json.get("reco_no").toString());
                            list_bumen.add(map);
                        }
                    }
                    updateDeptData();
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

    private void updateDeptData() {
        nameList2.clear();
        for (int i = 0; i < list_bumen.size(); i++) {
            CustemObject object = new CustemObject();
            object.data = list_bumen.get(i).get("name");
            nameList2.add(object);
        }
        mAdapter2 = new CustemSpinerAdapter(getContext());
        mAdapter2.refreshData(nameList2, 0);
        mSpinerPopWindow2 = new SpinerPopWindow(getContext());
        mSpinerPopWindow2.setAdatper(mAdapter2);
        mSpinerPopWindow2.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
            @Override
            public void onItemClick(int pos) {
                String value = nameList2.get(pos).toString();
                if (!tv_dept.getText().toString().equals(value)) {
                    tv_dept.setText(value);
                }
            }
        });
    }

    private void showDeptData() {
        mSpinerPopWindow2.setWidth(ll_dept.getWidth());
        mSpinerPopWindow2.showAsDropDown(ll_dept);
    }

    /**
     * 结算方式
     */
    private void getJSFSData() {
        AbRequestParams params = new AbRequestParams();
        params.put("type", "ITJiesuan");
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_HYGL_ADD_TYPE, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.get("status").toString().equals("1")) {
                        JSONArray jsonarray = object.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject json = jsonarray.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<>();
                            map.put("name", json.get("jiesuan_mc").toString());
                            map.put("jiesuanhao", json.get("reco_no").toString());
                            list_jiesuan.add(map);
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

            }
        });
    }

    private void updateJSFSData() {
        nameList1.clear();
        for (int i = 0; i < list_jiesuan.size(); i++) {
            CustemObject object = new CustemObject();
            object.data = list_jiesuan.get(i).get("name");
            nameList1.add(object);
        }
        mAdapter1 = new CustemSpinerAdapter(getContext());
        mAdapter1.refreshData(nameList1, 0);
        mSpinerPopWindow1 = new SpinerPopWindow(getContext());
        mSpinerPopWindow1.setAdatper(mAdapter1);
        mSpinerPopWindow1.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
            @Override
            public void onItemClick(int pos) {
                String value = nameList1.get(pos).toString();
                if (!tv_jsfs.getText().toString().equals(value)) {
                    tv_jsfs.setText(value);
                }
            }
        });
    }

    private void showJSFSData() {
        mSpinerPopWindow1.setWidth(ll_jsfs.getWidth());
        mSpinerPopWindow1.showAsDropDown(ll_jsfs);
    }

    /**
     * 经办人下拉
     */
    private void getJBRData() {
        AbRequestParams params = new AbRequestParams();
        params.put("type", "ITjb");
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_HYGL_ADD_TYPE, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.get("status").toString().equals("1")) {
                        JSONArray jsonarray = object.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject json = jsonarray.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<>();
                            map.put("name", json.get("reny_xm").toString());
                            map.put("reny_dm", json.get("reny_dm").toString());
                            map.put("reny_xm", json.get("dept_mc").toString());
                            list_jingban.add(map);
                        }
                    }
                    updateJBRData();
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

    private void updateJBRData() {
        nameList3.clear();
        for (int i = 0; i < list_jingban.size(); i++) {
            CustemObject object = new CustemObject();
            object.data = list_jingban.get(i).get("name");
            nameList3.add(object);
        }
        mAdapter3 = new CustemSpinerAdapter(getContext());
        mAdapter3.refreshData(nameList3, 0);
        mSpinerPopWindow3 = new SpinerPopWindow(getContext());
        mSpinerPopWindow3.setAdatper(mAdapter3);
        mSpinerPopWindow3.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
            @Override
            public void onItemClick(int pos) {
                String value = nameList3.get(pos).toString();
                if (!tv_jbr.getText().toString().equals(value)) {
                    tv_jbr.setText(value);
                }
            }
        });
    }

    private void showJBRData() {
        mSpinerPopWindow3.setWidth(ll_jbr.getWidth());
        mSpinerPopWindow3.showAsDropDown(ll_jbr);
    }

    /**
     * 卡类型下拉
     */
    private void getCardKindData() {
        AbRequestParams params = new AbRequestParams();
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_HYGL_ADD_KA_TYPE, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.get("status").toString().equals("1")) {
                        JSONArray jsonarray = object.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject json = jsonarray.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<>();
                            map.put("id", json.get("id").toString());
                            map.put("name", json.get("CardKind").toString());
                            int a = Integer.parseInt(json.get("card_yxq").toString());
                            map.put("yxq", StringData(a));//有效期
                            map.put("khje", json.get("card_khje").toString());//开户金额
                            map.put("px", json.get("card_cs_px").toString());//普洗
                            map.put("jx", json.get("card_cs_jx").toString());//精细
                            map.put("card_addje", json.get("card_addje").toString());
                            list_katype.add(map);
                        }

                    }
                    updateCardKindData();
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

    private void updateCardKindData() {
        nameList.clear();
        for (int i = 0; i < list_katype.size(); i++) {
            CustemObject object = new CustemObject();
            object.data = list_katype.get(i).get("name");
            nameList.add(object);
        }
        mAdapter = new CustemSpinerAdapter(getContext());
        mAdapter.refreshData(nameList, 0);
        mSpinerPopWindow = new SpinerPopWindow(getContext());
        mSpinerPopWindow.setAdatper(mAdapter);
        mSpinerPopWindow.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
            @Override
            public void onItemClick(int pos) {
                String value = nameList.get(pos).toString();
                if (!tv_cardkind.getText().toString().equals(value)) {
                    tv_cardkind.setText(value);
                }
            }
        });
    }

    private void showCradKindData() {
        mSpinerPopWindow.setWidth(ll_cardkind.getWidth());
        mSpinerPopWindow.showAsDropDown(ll_cardkind);
    }

    private void addCustom(int isNewCar) {
        AbRequestParams params = new AbRequestParams();
        params.put("che_no", et_hy_chepai.getText().toString().trim());
        params.put("kehu_no", chepai);
        params.put("kehu_mc", et_hy_lianxi.getText().toString());
        params.put("kehu_dz", et_hy_address.getText().toString());
        params.put("kehu_dh", et_hy_phone.getText().toString());
        params.put("kehu_sj", et_hy_shouphone.getText().toString());
        params.put("kehu_Birthday", et_hy_birthday.getText().toString());
        // params.put("card_no", et_hy_kahao.getText().toString());
        params.put("card_enddate", et_hy_youdate.getText().toString());
        params.put("card_kind", katype);
        params.put("card_ysje", et_hy_kaimoney.getText().toString());//开户金额
        params.put("card_ssje", et_hy_kaimoney.getText().toString());
        params.put("card_addje", et_hy_keyongjie.getText().toString());
        params.put("card_cs", et_hy_shouxi.getText().toString());
//        params.put("card_leftcs_px", px);
        params.put("card_cs_jx", et_hy_jingxi.getText().toString());
        // params.put("card_jifen", et_hy_jifen.getText().toString());
        params.put("dept_mc", bumen);//部门
        params.put("card_jb", jingban);//经办人
        params.put("card_jsfs", jiesuan);//结算方式
        params.put("isNewCar", isNewCar);
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_HYGL_ADD, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int sss, String s) {
                WeiboDialogUtils.closeDialog(mWeiboDialog);
                queRen = new QueRen(getContext(), "办理成功");
                queRen.show();
                queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick() {
                        queRen.dismiss();
                        dimss();
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
                Log.i("cjn", "存档请求失败" + s);
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });
    }

    /**
     * 判断车牌号是否存在
     */
    private void checkCarNo(String carNo) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getContext(), "加载中...");
        AbRequestParams params = new AbRequestParams();
        params.put("che_no", carNo);
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_HYGL_check_che, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String s) {
                WeiboDialogUtils.closeDialog(mWeiboDialog);
                if (s.equals("false")) {
                    promptdiaog = new TooPromptdiaog(getContext(), "此车为新车，确认办理将会自动增加车辆和客户信息，是否继续");
                    promptdiaog.setToopromtOnClickListener(new TooPromptdiaog.ToopromtOnClickListener() {
                        @Override
                        public void onYesClick() {
                            promptdiaog.dismiss();
                            addCustom(1);
                        }
                    });
                    promptdiaog.show();
                } else {
                    addCustom(0);
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

    public void dimss() {
        this.dismiss();
    }
}
