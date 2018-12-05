package com.example.administrator.boshide2.Modular.Fragment;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.Entity.BSD_WXCL_CK_Entity;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.adapter.BSD_wxcl_kc_apater;
import com.example.administrator.boshide2.Modular.View.MarqueeView;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.DownJianPan;
import com.example.administrator.boshide2.Tools.QuanQuan.WeiboDialogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017-11-13.
 */
public class KuCunDialogFragment extends DialogFragment {
    private static final String PARAM_KEY = "param_key";
    private TextView bsd_wxcl_kc_bianma;
    private TextView bsd_wxcl_kc_tuhao;
    private TextView bsd_wxcl_kc_mingcheng;
    private TextView bsd_wxcl_kc_danwei;
    private TextView bsd_wxcl_kc_pinpai;
    private MarqueeView bsd_wxcl_kc_chexing;
    private TextView tv_xxSum;
    private TextView tv_sxSum;
    private TextView tv_kcSum;
    private TextView tv_djSum;
    private TextView tv_jeSum;
    private ListView bsd_wxcl_kc_lv;
    private TextView bsd_wxcl_kc_gb;
    private Dialog mWeiboDialog;
    private Context context;
    private BSD_wxcl_kc_apater mAdapter;
    private ArrayList<BSD_WXCL_CK_Entity> mList = new ArrayList<>();
    private URLS url;
    private BSD_WXCL_CK_Entity ck_entity;
    private double currentKc;
    private double pjdj;
    private double je;
    private double kcsx;
    private double kcxx;
    private double currentKcSum = 0, pjdjSum = 0, jeSum = 0, kcsxSum = 0, kcxxSum = 0;
    private String param;

    public static KuCunDialogFragment newInstance(String param) {
        KuCunDialogFragment fragment = new KuCunDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_KEY, param);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        param = getArguments().getString(PARAM_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        url = new URLS();
        View view = inflater.from(context).inflate(R.layout.dialog_stockinfo_layout, null);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        queryKucun();
    }

    @Override
    public void onStart() {
        super.onStart();
        //隐藏输入法
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = (int) getActivity().getResources().getDimension(R.dimen.qb_px_700);
        params.height = LinearLayout.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes(params);
    }

    public void initView(View view) {
        bsd_wxcl_kc_bianma = (TextView) view.findViewById(R.id.bsd_wxcl_kc_bianma);
        bsd_wxcl_kc_tuhao = (TextView) view.findViewById(R.id.bsd_wxcl_kc_tuhao);
        bsd_wxcl_kc_chexing = (MarqueeView) view.findViewById(R.id.bsd_wxcl_kc_chexing);
        bsd_wxcl_kc_mingcheng = (TextView) view.findViewById(R.id.bsd_wxcl_kc_mingcheng);
        bsd_wxcl_kc_danwei = (TextView) view.findViewById(R.id.bsd_wxcl_kc_danwei);
        bsd_wxcl_kc_pinpai = (TextView) view.findViewById(R.id.bsd_wxcl_kc_pinpai);
        tv_sxSum = (TextView) view.findViewById(R.id.tv_sxSum);
        tv_xxSum = (TextView) view.findViewById(R.id.tv_xxSum);
        tv_kcSum = (TextView) view.findViewById(R.id.tv_kcSum);
        tv_djSum = (TextView) view.findViewById(R.id.tv_djSum);
        tv_jeSum = (TextView) view.findViewById(R.id.tv_jeSum);
        bsd_wxcl_kc_lv = (ListView) view.findViewById(R.id.bsd_wxcl_kc_lv);
        mAdapter = new BSD_wxcl_kc_apater(context, mList);
        bsd_wxcl_kc_lv.setAdapter(mAdapter);
        bsd_wxcl_kc_gb = (TextView) view.findViewById(R.id.tv_close);
        bsd_wxcl_kc_gb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        //隐藏软键盘
        DownJianPan.DJP(getActivity());
    }

    /**
     * 查看配件库存
     */
    public void queryKucun() {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this.getActivity(), "加载中......");
        AbRequestParams params = new AbRequestParams();
        params.put("peij_no", param);
        params.put("caozuoyuanid", MyApplication.shared.getString("userid", ""));
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxcl_kc, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int a, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getString("message").equals("查询成功")) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        //主表
                        JSONObject peijZhu = data.getJSONObject("peij_info");
                        bsd_wxcl_kc_bianma.setText(peijZhu.getString("peij_no"));
                        bsd_wxcl_kc_tuhao.setText(peijZhu.getString("peij_th"));
                        bsd_wxcl_kc_chexing.setText(peijZhu.getString("peij_cx"));
                        bsd_wxcl_kc_mingcheng.setText(peijZhu.getString("peij_mc"));
                        bsd_wxcl_kc_danwei.setText(peijZhu.getString("peij_dw"));
                        bsd_wxcl_kc_pinpai.setText(peijZhu.getString("peij_pp"));
                        //明细仓库表
                        JSONArray peijCk = data.getJSONArray("cangk_info");
                        for (int i = 0; i < peijCk.length(); i++) {
                            JSONObject objectCk = peijCk.getJSONObject(i);
                            ck_entity = new BSD_WXCL_CK_Entity();
                            ck_entity.setCkMc(objectCk.getString("cangk_mc"));
                            ck_entity.setCurrentKc(objectCk.getString("peij_kc"));
                            ck_entity.setPjDj(objectCk.getString("jiag_jp"));
                            ck_entity.setJe(objectCk.getString("peij_je"));
                            ck_entity.setKcSum(objectCk.getString("kuc_sx"));
                            ck_entity.setKcMin(objectCk.getString("kuc_xx"));
                            ck_entity.setKw(objectCk.getString("kuc_hw"));
                            //计算各列合计
                            if (!objectCk.getString("peij_kc").equals("")) {
                                currentKc = Double.parseDouble(objectCk.getString("peij_kc"));
                                currentKcSum += currentKc;
                            }
                            if (!objectCk.getString("jiag_jp").equals("")) {
                                pjdj = Double.parseDouble(objectCk.getString("jiag_jp"));
                                pjdjSum += pjdj;
                            }
                            if (!objectCk.getString("peij_je").equals("")) {
                                je = Double.parseDouble(objectCk.getString("peij_je"));
                                jeSum += je;
                            }
                            if (!objectCk.getString("kuc_sx").equals("")) {
                                kcsx = Double.parseDouble(objectCk.getString("kuc_sx"));
                                kcsxSum += kcsx;
                            }
                            if (!objectCk.getString("kuc_xx").equals("")) {
                                kcxx = Double.parseDouble(objectCk.getString("kuc_xx"));
                                kcxxSum += kcxx;
                            }
                            mList.add(ck_entity);
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                    tv_xxSum.setText(kcxxSum + "");
                    tv_sxSum.setText(kcsxSum + "");
                    tv_kcSum.setText(currentKcSum + "");
                    tv_jeSum.setText(jeSum + "");
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
                Toast.makeText(getContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });

    }


}
