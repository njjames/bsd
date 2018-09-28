package com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.deleg;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.example.administrator.boshide2.Conts;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.deleg.delg_adapter.Bsd_WeiXiuYeWuDiaoDuDian_adp;
import com.example.administrator.boshide2.Modular.View.diaog.QueRen;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.QuanQuan.WeiboDialogUtils;

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
public class BSD_WeiXiuYeWuDiaoDuDian_delg extends Dialog {
    private View view;
    List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    Bsd_WeiXiuYeWuDiaoDuDian_adp adp;
    ListView bsd_zcdd_pg_lv;
    QueRen queRen;
    YuanGong yuanGong;
    TextView bsd_zcdd_top;
    URLS url;
    private EditText  et_pg_query;
    private  Button  but_pg_query;

    private Dialog mWeiboDialog;
    public void setYuanGong(YuanGong yuanGong) {
        this.yuanGong = yuanGong;
    }

    public BSD_WeiXiuYeWuDiaoDuDian_delg(final Context context, final String wxxm_nos) {
        super(context, R.style.mydialog);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        url=new URLS();
        view = getLayoutInflater().inflate(R.layout.bsdweixiuyewudiaodu_delg, null, false);
        bsd_zcdd_pg_lv = (ListView) view.findViewById(R.id.bsd_zcdd_pg_lv);
        bsd_zcdd_top= (TextView) view.findViewById(R.id.bsd_zcdd_top);

        et_pg_query= (EditText) view.findViewById(R.id.et_pg_query);
        but_pg_query= (Button) view.findViewById(R.id.but_pg_query);
        but_pg_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data();
            }
        });


        Log.i("cjn","查看这个数据是多少"+Conts.zhengti_or_danxiang);
        if (Conts.zhengti_or_danxiang==0){
            //整体派工
            bsd_zcdd_top.setText("整体派工");
        }else if (Conts.zhengti_or_danxiang==1){
            //单项派工
            bsd_zcdd_top.setText("单项派工");

        }


        adp = new Bsd_WeiXiuYeWuDiaoDuDian_adp(context);
        adp.setPaiGong(new Bsd_WeiXiuYeWuDiaoDuDian_adp.PaiGong() {
            @Override
            public void onYesClick(int i) {
                //派工操作
                Log.i("cjn","派工操作");
                if (Conts.zhengti_or_danxiang==0){
                    //整体派工
                    Log.i("cjn","整体派工操作");
                    ztpaigongdata(i,context,wxxm_nos);
                }else if (Conts.zhengti_or_danxiang==1){
                    //单项派工
                    Log.i("cjn","单个派工");
                    dxpaigongdata(i,context);
                }
            }
        });

        bsd_zcdd_pg_lv.setAdapter(adp);
        data();
        setContentView(view);
        setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams params =
                this.getWindow().getAttributes();
        params.width = (int) getContext().getResources().getDimension(R.dimen.x250);
        params.height = (int) getContext().getResources().getDimension(R.dimen.y500);
        this.getWindow().setAttributes(params);
    }


    public void data() {
        list.clear();
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getContext(), "加载中...");
        AbRequestParams params = new AbRequestParams();
        params.put("type", "ITall");
        params.put("reny_xm",et_pg_query.getText().toString());
        Request.Post(MyApplication.shared.getString("ip", "")+url.BSD_HYGL_ADD_TYPE, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int a, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    Log.i("rexm", "onSuccess: ==="+s);
                    if (jsonObject.get("status").toString().equals("1")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject item = jsonarray.getJSONObject(i);
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("reny_dm", item.getString("reny_dm"));
                            map.put("danw_dm", item.getString("danw_dm"));
                            map.put("dept_mc", item.getString("dept_mc"));
                            map.put("reny_xm", item.getString("reny_xm"));
                            map.put("reny_zw", item.getString("reny_zw"));
                            map.put("reny_dh", item.getString("reny_dh"));
                            map.put("reny_hb", item.getString("reny_hb"));
                            list.add(map);
                        }
                    }
                    adp.setList(list);
                    adp.notifyDataSetChanged();
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
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
     * 单项派工
     * @param i
     * @param context
     */
    public void dxpaigongdata(int i, final Context context) {
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", Conts.work_no);//单号
        params.put("wxxm_no", Conts.wxxm_no);	//维修项目编号
        params.put("reny_no", list.get(i).get("reny_dm"));//人员编号
        params.put("reny_mc", list.get(i).get("reny_xm"));//人员名称
        params.put("wxry_bm", list.get(i).get("dept_mc"));//人员部门
        params.put("wxry_cj", ""); //车间
        params.put("wxry_bz", "");//班组
        params.put("paig_khgs", ""); //考核工时
        params.put("paig_khje", "");//考核金额

        Log.i("cjn","work_no=="+Conts.work_no+"-----reny_no=="+list.get(i).get("reny_dm")
                +"---------reny_mc=="+list.get(i).get("reny_xm")+"--------------wxry_bm=="+list.get(i).get("dept_mc")
        +"-----------wxxm_no=="+Conts.wxxm_no);

        Request.Post(MyApplication.shared.getString("ip", "")+url.BSD_danxiangPaiGong, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("cjn","派工之后看看返回什么="+s);

                queRen = new QueRen(context, "派工成功");
                queRen.show();
                queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick() {
                        queRen.dismiss();
                        dissmis();
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
                Log.i("cjn","派工之后看看返回什么="+s);
            }
        });


    }

    /**
     * 整体派工
     * @param i
     * @param context
     */
    public void ztpaigongdata(int i, final Context context,String wxxm_nos) {
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", Conts.work_no);//单号
//        params.put("wxxm_no", "");	//维修项目编号
        params.put("reny_no",    list.get(i).get("reny_dm"));//人员编号
        params.put("reny_mc", list.get(i).get("reny_xm"));//人员名称
        params.put("wxry_bm", list.get(i).get("dept_mc"));//人员部门
        params.put("wxry_cj", ""); //车间
        params.put("wxry_bz", "");//班组
        params.put("paig_khgs", ""); //考核工时
        params.put("paig_khje", "");//考核金额

        Log.i("cjn","work_no=="+Conts.work_no+"-----reny_no=="+list.get(i).get("reny_dm")
        +"---------reny_mc=="+list.get(i).get("reny_xm")+"--------------wxry_bm=="+list.get(i).get("dept_mc")
        +"--------wxxm_no=="+wxxm_nos);

        Request.Post(MyApplication.shared.getString("ip", "")+url.BSD_zhengTiPaiGong, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
            Log.i("cjn","派工之后看看返回什么="+s);

                queRen = new QueRen(context, "派工成功");
                queRen.show();
                queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick() {
                        queRen.dismiss();
                        dissmis();
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
                Log.i("cjn","派工之后看看返回什么="+s);
            }
        });


    }

    public void dissmis(){
        this.dismiss();

    }
    public interface YuanGong{
        public void onYesClick(List<Map<String, String>> list);
    }
}