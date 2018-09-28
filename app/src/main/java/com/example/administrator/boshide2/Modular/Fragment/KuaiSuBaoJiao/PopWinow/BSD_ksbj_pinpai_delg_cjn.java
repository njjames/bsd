package com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.PopWinow;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Fragment.HuiYuanGuanLi.Adapter.BSD_hygl_bumen_adp;
import com.example.administrator.boshide2.Modular.Fragment.HuiYuanGuanLi.Adapter.BSD_hygl_jiesuan_adp;
import com.example.administrator.boshide2.Modular.Fragment.HuiYuanGuanLi.Adapter.BSD_hygl_jingban_adp;
import com.example.administrator.boshide2.Modular.Fragment.HuiYuanGuanLi.Adapter.BSD_hygl_katype_adp;
import com.example.administrator.boshide2.Modular.View.diaog.Promptdiaog;
import com.example.administrator.boshide2.Modular.View.timepicker.TimePickerShow;
import com.example.administrator.boshide2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 添加会员
 */
public class BSD_ksbj_pinpai_delg_cjn extends Dialog implements View.OnClickListener {
    private View view;

    EditText et_hy_chepai, et_hy_lianxi, et_hy_address, et_hy_phone, et_hy_shouphone, et_hy_kahao,  et_hy_kaimoney, et_hy_shishou, et_hy_youhui, et_hy_keyong, et_hy_shouxi, et_hy_jingxi,
            et_hy_jifen, et_hy_zhanghao,  et_hy_beizhu;//申明一些空间
    TextView tv_hy_banli;//确认办理
    TextView et_hy_birthday, et_hy_youdate,et_hy_bumen,et_hy_payselect,et_hy_jingban,et_hy_kaname;

    TimePickerShow timePickerShow;
    RelativeLayout relativeday, rela_youxiao, rela_bumen,relat_jiesuan,relat_jingban,rela_ka;
    Promptdiaog promptdiaog;

    //PopupWindow对象声明
    PopupWindow pw;
    //当前选中的列表项位置
    int clickPsition = -1;
    private BSD_hygl_bumen_adp hygl_bumen_adp;//会员管理--部门适配器
    List<Map<String, String>> list_bumen = new ArrayList<Map<String, String>>();//会员管理--list集合
    private BSD_hygl_jiesuan_adp hygl_jiesuan_adp;//结算适配器
    List<Map<String, String>> list_jiesuan = new ArrayList<Map<String, String>>();//结算方式--集合
    private BSD_hygl_jingban_adp jingban_adp;//经办人适配器
    List<Map<String, String>> list_jingban = new ArrayList<Map<String, String>>();//经办人--集合
    private BSD_hygl_katype_adp katype_adp;//卡类型
    List<Map<String, String>> list_katype = new ArrayList<Map<String, String>>();//卡类型--集合
    String bumen,jiesuan,jingban,katype,chepai;//吧list里面数据拿出来，赋给bumen,然后显示
   URLS url;
    public BSD_ksbj_pinpai_delg_cjn(Context context) {
        super(context, R.style.mydialog);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        // TODO Auto-generated constructor stub
        view = getLayoutInflater().inflate(R.layout.bad_ksbj_pinpai_pop, null, false);
        url=new URLS();
        timePickerShow = new TimePickerShow(getContext());
        initview();
        katype();//卡类型下拉
        jiesuantype();//结算下拉
        jingbanype();//经办人
        //chepai();
        kaype();

        setContentView(view);

        setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams params =
                this.getWindow().getAttributes();
        params.width = 1630;
        params.x = 140;
        params.y = 0;
        params.height = 600;
        this.getWindow().setAttributes(params);
    }

    private void initview() {
        rela_ka= (RelativeLayout) view.findViewById(R.id.rela_ka);
        relat_jingban= (RelativeLayout) view.findViewById(R.id.relat_jingban);
        relat_jiesuan= (RelativeLayout) view.findViewById(R.id.relat_jiesuan);
        rela_bumen = (RelativeLayout) view.findViewById(R.id.rela_bumen);
        relativeday = (RelativeLayout) view.findViewById(R.id.relativeday);
        rela_youxiao = (RelativeLayout) view.findViewById(R.id.rela_youxiao);
        et_hy_chepai = (EditText) view.findViewById(R.id.et_hy_chepai);
        et_hy_lianxi = (EditText) view.findViewById(R.id.et_hy_lianxi);
        et_hy_address = (EditText) view.findViewById(R.id.et_hy_address);
        et_hy_phone = (EditText) view.findViewById(R.id.et_hy_phone);
        et_hy_shouphone = (EditText) view.findViewById(R.id.et_hy_shouphone);
        et_hy_birthday = (TextView) view.findViewById(R.id.et_hy_birthday);
        et_hy_kahao = (EditText) view.findViewById(R.id.et_hy_kahao);
        et_hy_youdate = (TextView) view.findViewById(R.id.et_hy_youdate);
        et_hy_kaname = (TextView) view.findViewById(R.id.et_hy_kaname);

        et_hy_kaimoney = (EditText) view.findViewById(R.id.et_hy_kaimoney);
        et_hy_youhui = (EditText) view.findViewById(R.id.et_hy_youhui);
        et_hy_shouxi = (EditText) view.findViewById(R.id.et_hy_shouxi);
        et_hy_shishou = (EditText) view.findViewById(R.id.et_hy_shishou);
        et_hy_keyong = (EditText) view.findViewById(R.id.et_hy_keyong);
        et_hy_jingxi = (EditText) view.findViewById(R.id.et_hy_jingxi);
        et_hy_jifen = (EditText) view.findViewById(R.id.et_hy_jifen);
        et_hy_zhanghao = (EditText) view.findViewById(R.id.et_hy_zhanghao);
        et_hy_bumen = (TextView) view.findViewById(R.id.et_hy_bumen);
        et_hy_jingban = (TextView) view.findViewById(R.id.et_hy_jingban);
        et_hy_payselect = (TextView) view.findViewById(R.id.et_hy_payselect);
        et_hy_beizhu = (EditText) view.findViewById(R.id.et_hy_beizhu);
        tv_hy_banli = (TextView) view.findViewById(R.id.tv_hy_banli);
        relativeday.setOnClickListener(this);
        rela_youxiao.setOnClickListener(this);
        tv_hy_banli.setOnClickListener(this);
        rela_bumen.setOnClickListener(this);
        relat_jiesuan.setOnClickListener(this);
        relat_jingban.setOnClickListener(this);
        rela_ka.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //生日
            case R.id.relativeday:
                timePickerShow.timePickerAlertDialog(et_hy_birthday);
                break;
            //有效日期
            case R.id.rela_youxiao:
                timePickerShow.timePickerAlertDialog(et_hy_youdate);
                break;
            //确认办理
            case R.id.tv_hy_banli:
                chepai();
               // addCustom();
                break;
            //部门下拉
            case R.id.rela_bumen:
                popwin();
                break;
            //结算下拉
            case R.id.relat_jiesuan:
                popwin_jiesuan();
                break;
            //经办人下
            case R.id.relat_jingban:
                popwin_jingban();
                break;
            //卡类型
            case R.id.rela_ka:
            popwin_katype();
                break;
        }
    }

    /**
     * 部门弹出框显示
     */
    private void popwin() {
        //通过布局注入器，注入布局给View对象
        View myView = getLayoutInflater().inflate(R.layout.bsd_hygl_bumen, null);
        //通过view 和宽·高，构造PopopWindow
        pw = new PopupWindow(myView, 155, 300, true);
        pw.setBackgroundDrawable(getContext().getResources().getDrawable(
                //此处为popwindow 设置背景，同事做到点击外部区域，popwindow消失
                R.drawable.banbai));
        //设置焦点为可点击
        pw.setFocusable(true);//可以试试设为false的结果
        //将window视图显示在myButton下面
        pw.showAsDropDown(rela_bumen);
        ListView lv = (ListView) myView.findViewById(R.id.lv_hygl_bumen);
        hygl_bumen_adp = new BSD_hygl_bumen_adp(getContext(), list_bumen);
        lv.setAdapter(hygl_bumen_adp);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                bumen = list_bumen.get(i).get("name").toString();
                et_hy_bumen .setText(bumen);
               pw.dismiss();
            }
        });
    }
    /**
     * 结算方式
     */
    private void popwin_jiesuan() {
        //通过布局注入器，注入布局给View对象
        View myView = getLayoutInflater().inflate(R.layout.bsd_hygl_bumen, null);
        //通过view 和宽·高，构造PopopWindow
        pw = new PopupWindow(myView, 155, 300, true);
        pw.setBackgroundDrawable(getContext().getResources().getDrawable(
                //此处为popwindow 设置背景，同事做到点击外部区域，popwindow消失
                R.drawable.banbai));
        //设置焦点为可点击
        pw.setFocusable(true);//可以试试设为false的结果
        //将window视图显示在myButton下面
        pw.showAsDropDown(relat_jiesuan);
        ListView lv = (ListView) myView.findViewById(R.id.lv_hygl_bumen);
        hygl_jiesuan_adp = new BSD_hygl_jiesuan_adp(getContext(), list_jiesuan);
        lv.setAdapter(hygl_jiesuan_adp);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                jiesuan = list_jiesuan.get(i).get("name").toString();
                et_hy_payselect.setText(jiesuan);
                pw.dismiss();
            }
        });
    }
    /**
     * 卡类型
     */
    private void popwin_katype() {
        //通过布局注入器，注入布局给View对象
        View myView = getLayoutInflater().inflate(R.layout.bsd_hygl_bumen, null);
        //通过view 和宽·高，构造PopopWindow
        pw = new PopupWindow(myView, 155, 300, true);
        pw.setBackgroundDrawable(getContext().getResources().getDrawable(
                //此处为popwindow 设置背景，同事做到点击外部区域，popwindow消失
                R.drawable.banbai));
        //设置焦点为可点击
        pw.setFocusable(true);//可以试试设为false的结果
        //将window视图显示在myButton下面
        pw.showAsDropDown(rela_ka);
        ListView lv = (ListView) myView.findViewById(R.id.lv_hygl_bumen);
        katype_adp = new BSD_hygl_katype_adp(getContext(), list_katype);
        lv.setAdapter(katype_adp);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                katype = list_katype.get(i).get("name").toString();
                et_hy_kaname.setText(katype);
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
        pw = new PopupWindow(myView, 155, 300, true);
        pw.setBackgroundDrawable(getContext().getResources().getDrawable(
                //此处为popwindow 设置背景，同事做到点击外部区域，popwindow消失
                R.drawable.banbai));
        //设置焦点为可点击
        pw.setFocusable(true);//可以试试设为false的结果
        //将window视图显示在myButton下面
        pw.showAsDropDown(relat_jingban);
        ListView lv = (ListView) myView.findViewById(R.id.lv_hygl_bumen);
        jingban_adp = new BSD_hygl_jingban_adp(getContext(), list_jingban);
        lv.setAdapter(jingban_adp);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                jingban = list_jingban.get(i).get("name").toString();
                et_hy_jingban.setText(jingban);
                pw.dismiss();
            }
        });
    }
    /**
     * 部门
     */
    private void katype() {
        AbRequestParams params = new AbRequestParams();
            params.put("type", "ITdept");
        Request.Post(MyApplication.shared.getString("ip", "")+url.BSD_HYGL_ADD_TYPE, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int sssss, String s) {
                try {
                    JSONObject object = new JSONObject(s);

                    if (object.get("status").toString().equals("1")) {
                        Log.i("传走没有", "..." + s);
                        JSONArray jsonarray = object.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {

                            JSONObject json = jsonarray.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<>();

                            map.put("name",
                                    json.get("dept_mc").toString());
                            map.put("bianhao", json.get("reco_no").toString());
                            list_bumen.add(map);
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
     * 结算方式
     */
    private void jiesuantype() {
        AbRequestParams params = new AbRequestParams();
        params.put("type", "ITJiesuan");
        Request.Post(MyApplication.shared.getString("ip", "")+url.BSD_HYGL_ADD_TYPE, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int sssss, String s) {
                try {
                    JSONObject object = new JSONObject(s);

                    if (object.get("status").toString().equals("1")) {
                        Log.i("传走没有", "..." + s);
                        JSONArray jsonarray = object.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {

                            JSONObject json = jsonarray.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<>();

                            map.put("name",
                                    json.get("jiesuan_mc").toString());
                            map.put("jiesuanhao", json.get("reco_no").toString());
                            list_jiesuan.add(map);
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
     * 经办人下拉
     */
    private void jingbanype() {
        AbRequestParams params = new AbRequestParams();
        params.put("type", "ITJcr");
        Request.Post(MyApplication.shared.getString("ip", "")+url.BSD_HYGL_ADD_TYPE, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int sssss, String s) {
                try {
                    JSONObject object = new JSONObject(s);

                    if (object.get("status").toString().equals("1")) {
                        Log.i("传走没有", "..." + s);
                        JSONArray jsonarray = object.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {

                            JSONObject json = jsonarray.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<>();

                            map.put("name",
                                    json.get("dept_mc").toString());
                            map.put("reny_dm", json.get("reny_dm").toString());
                           map.put("reny_xm",json.get("reny_xm").toString());

                            list_jingban.add(map);
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
        });}
    /**
     * 卡类型下拉
     */
    private void kaype() {
        AbRequestParams params = new AbRequestParams();

        Request.Post(MyApplication.shared.getString("ip", "")+url.BSD_HYGL_ADD_KA_TYPE, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int sssss, String s) {
                try {
                    JSONObject object = new JSONObject(s);

                    if (object.get("status").toString().equals("1")) {
                        Log.i("传走没有", "..." + s);
                        JSONArray jsonarray = object.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {

                            JSONObject json = jsonarray.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<>();
                            map.put("id",json.get("id").toString());
                            map.put("name",
                                    json.get("CardKind").toString());

                            list_katype.add(map);
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
        });}

    private void addCustom() {
        AbRequestParams params = new AbRequestParams();
        params.put("che_no",et_hy_chepai.getText().toString().trim());
        params.put("kehu_no", chepai);
        Log.i("cjn",et_hy_chepai.getText().toString().trim()+"aaaaaaaaaaaaaa"+chepai);
        params.put("che_mc", et_hy_lianxi.getText().toString());
        params.put("kehu_dz", et_hy_address.getText().toString());
        params.put("kehu_dh", et_hy_phone.getText().toString());
        params.put("kehu_sj", et_hy_shouphone.getText().toString());
        params.put("kehu_Birthday", et_hy_birthday.getText().toString());
        params.put("card_no", et_hy_kahao.getText().toString());
        params.put("card_enddate", et_hy_youdate.getText().toString());
        params.put("card_kind", katype);
        Log.i("cjn","卡的类别"+katype);
        params.put("card_ysje", et_hy_kaimoney.getText().toString());
        params.put("card_ssje", et_hy_shishou.getText().toString());
        params.put("card_addje", et_hy_keyong.getText().toString());
        params.put("card_leftcs_px", et_hy_shouxi.getText().toString());
        params.put("card_cs_jx", et_hy_jingxi.getText().toString());
        params.put("card_jifen", et_hy_jifen.getText().toString());
         params.put("dept_mc", bumen);//部门
           params.put("card_jb", jingban);//经办人
          params.put("card_jsfs", jiesuan);//结算方式
        Request.Post(MyApplication.shared.getString("ip", "")+url.BSD_HYGL_ADD, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int sss, String s) {
                    Log.i("7777777777777777777", "..." + s);
                        promptdiaog = new Promptdiaog(getContext(),
                                "上传成功");
                        promptdiaog.setpromtOnClickListener(new Promptdiaog.promtOnClickListener() {

                            @Override
                            public void onYesClick() {
                                // TODO Auto-generated method stub

                               promptdiaog.dismiss();
                                dimss();



                            }
                        });
                        promptdiaog.show();

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
     *车牌
     */
    private void chepai() {
        AbRequestParams params = new AbRequestParams();
        params.put("che_no",et_hy_chepai.getText().toString());
        Request.Post(MyApplication.shared.getString("ip", "")+url.BSD_HYGL_ADD_che, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int sssss, String s) {
                try {
                    JSONObject object = new JSONObject(s);

                    if (object.get("status").toString().equals("1")) {
                        Log.i("是多少", "..." + s);
                        JSONObject json = object.getJSONObject("data");
                        chepai=json.get("kehu_no").toString();

                      }
                    addCustom();
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
        });}

    public void dimss(){
        this.dismiss();
    }
}
