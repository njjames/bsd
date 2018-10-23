package com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.alibaba.fastjson.JSON;
import com.example.administrator.boshide2.Conts;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.deleg.delg_adapter.Bsd_WeiXiuYeWuDiaoDuDian_adp;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.dialog.adapter.PaiGongDialogAdapter;
import com.example.administrator.boshide2.Modular.View.diaog.CustomDialog;
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
 * 派工的对话框
 */
public class PaiGongDialog extends Dialog {
    public static final int PAIGONG_ALL = 0;
    public static final int PAIGONG_SINGLE = 1;
    private View view;
    List<Map<String, String>> staffLists = new ArrayList<Map<String, String>>();
    private PaiGongDialogAdapter paiGongDialogAdapter;
    ListView bsd_zcdd_pg_lv;
    QueRen queRen;
    YuanGong yuanGong;
    TextView bsd_zcdd_top;
    URLS url;
    private EditText  et_pg_query;
    private  Button  but_pg_query;

    private Dialog mWeiboDialog;
    private final Button btnConfirm;
    private final Button btnCancel;
    private String workNo;
    private String wxxmNo;
    private OnPaiGongListener onPaiGongListener;

    public void setWorkNo(String workNo) {
        this.workNo = workNo;
    }

    public void setWxxmNo(String wxxmNo) {
        this.wxxmNo = wxxmNo;
    }

    public void setYuanGong(YuanGong yuanGong) {
        this.yuanGong = yuanGong;
    }

    public PaiGongDialog(final Context context, final int type) {
        super(context, R.style.mydialog);
        view = getLayoutInflater().inflate(R.layout.bsdweixiuyewudiaodu_delg, null, false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(view);
        setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = (int) getContext().getResources().getDimension(R.dimen.qb_px_500);
//        params.height = (int) getContext().getResources().getDimension(R.dimen.qb_px_340);
        params.height = LinearLayout.LayoutParams.MATCH_PARENT;
        this.getWindow().setAttributes(params);

        bsd_zcdd_top= (TextView) view.findViewById(R.id.bsd_zcdd_top);
        if (type == PAIGONG_ALL) {
            bsd_zcdd_top.setText("整体派工");
        } else if (type == PAIGONG_SINGLE) {
            bsd_zcdd_top.setText("单项派工");
        }
        et_pg_query= (EditText) view.findViewById(R.id.et_pg_query);
        bsd_zcdd_pg_lv = (ListView) view.findViewById(R.id.bsd_zcdd_pg_lv);
        paiGongDialogAdapter = new PaiGongDialogAdapter(context, staffLists);
        bsd_zcdd_pg_lv.setAdapter(paiGongDialogAdapter);

        // 查询
        but_pg_query= (Button) view.findViewById(R.id.but_pg_query);
        but_pg_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStaffInfo();
            }
        });

        // 确认派工
        btnConfirm = (Button) view.findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> chooseLists = paiGongDialogAdapter.getChooseLists();
                if (chooseLists.size() > 0) {
                    if (type == PAIGONG_ALL) {
                        paiGongAll(chooseLists);
                    } else if (type == PAIGONG_SINGLE) {
                        paiGongSingle(chooseLists);
                    }
                } else {
                    Toast.makeText(getContext(), "您没有选择派工人员，无法派工", Toast.LENGTH_LONG).show();
                }
            }
        });
        // 取消
        btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dissmis();
            }
        });
        url=new URLS();
        getStaffInfo();
    }

    private void paiGongSingle(List<String> chooseLists) {
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", workNo);//单号
        params.put("wxxm_no", wxxmNo);//维修项目编码
        params.put("reny_nos", JSON.toJSONString(chooseLists));
        Request.Post(MyApplication.shared.getString("ip", "")+url.PaiGongSingle, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String data) {
                if (data.equals("success")) {
                    if (onPaiGongListener != null) {
                        onPaiGongListener.onSuccess();
                    }
                    CustomDialog.Builder builder = new CustomDialog.Builder(getContext());
                    CustomDialog dialog = builder.style(R.style.mydialog)
                            .view(R.layout.queren)
                            .widthDimenRes(R.dimen.qb_px_300)
                            .heightDimenRes(R.dimen.qb_px_174)
                            .addViewOnclick(R.id.bsd_queren_queren, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    CustomDialog.dismissDialog();
                                    dissmis();
                                }
                            })
                            .build();
                    TextView tips = (TextView) dialog.getView().findViewById(R.id.bsd_queren_neirong);
                    tips.setText("派工成功");
                    dialog.show();
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

    private void paiGongAll(List<String> chooseLists) {
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", workNo);//单号
        params.put("reny_nos", JSON.toJSONString(chooseLists));
        Request.Post(MyApplication.shared.getString("ip", "")+url.PaiGongAll, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String data) {
                if (data.equals("success")) {
                    if (onPaiGongListener != null) {
                        onPaiGongListener.onSuccess();
                    }
                    CustomDialog.Builder builder = new CustomDialog.Builder(getContext());
                    CustomDialog dialog = builder.style(R.style.mydialog)
                            .view(R.layout.queren)
                            .widthDimenRes(R.dimen.qb_px_300)
                            .heightDimenRes(R.dimen.qb_px_174)
                            .addViewOnclick(R.id.bsd_queren_queren, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    CustomDialog.dismissDialog();
                                    dissmis();
                                }
                            })
                            .build();
                    TextView tips = (TextView) dialog.getView().findViewById(R.id.bsd_queren_neirong);
                    tips.setText("派工成功");
                    dialog.show();
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
     * 获取员工信息
     */
    public void getStaffInfo() {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getContext(), "加载中...");
        AbRequestParams params = new AbRequestParams();
        params.put("type", "ITall");
        params.put("reny_xm",et_pg_query.getText().toString());
        Request.Post(MyApplication.shared.getString("ip", "")+url.BSD_HYGL_ADD_TYPE, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int a, String s) {
                try {
                    staffLists.clear();
                    JSONObject jsonObject = new JSONObject(s);
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
                            staffLists.add(map);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                paiGongDialogAdapter.notifyDataSetChanged();
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


    /**
     * 单项派工
     * @param i
     * @param context
     */
    public void dxpaigongdata(int i, final Context context) {
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", Conts.work_no);//单号
        params.put("wxxm_no", Conts.wxxm_no);	//维修项目编号
        params.put("reny_no", staffLists.get(i).get("reny_dm"));//人员编号
        params.put("reny_mc", staffLists.get(i).get("reny_xm"));//人员名称
        params.put("wxry_bm", staffLists.get(i).get("dept_mc"));//人员部门
        params.put("wxry_cj", ""); //车间
        params.put("wxry_bz", "");//班组
        params.put("paig_khgs", ""); //考核工时
        params.put("paig_khje", "");//考核金额

        Log.i("cjn","work_no=="+Conts.work_no+"-----reny_no=="+ staffLists.get(i).get("reny_dm")
                +"---------reny_mc=="+ staffLists.get(i).get("reny_xm")+"--------------wxry_bm=="+ staffLists.get(i).get("dept_mc")
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
        params.put("reny_no",    staffLists.get(i).get("reny_dm"));//人员编号
        params.put("reny_mc", staffLists.get(i).get("reny_xm"));//人员名称
        params.put("wxry_bm", staffLists.get(i).get("dept_mc"));//人员部门
        params.put("wxry_cj", ""); //车间
        params.put("wxry_bz", "");//班组
        params.put("paig_khgs", ""); //考核工时
        params.put("paig_khje", "");//考核金额

        Log.i("cjn","work_no=="+Conts.work_no+"-----reny_no=="+ staffLists.get(i).get("reny_dm")
        +"---------reny_mc=="+ staffLists.get(i).get("reny_xm")+"--------------wxry_bm=="+ staffLists.get(i).get("dept_mc")
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

    public interface OnPaiGongListener {
        void onSuccess();
    }

    public void setOnPaiGongListener(OnPaiGongListener onPaiGongListener) {
        this.onPaiGongListener = onPaiGongListener;
    }
}