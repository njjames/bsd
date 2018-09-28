package com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.dialogFragment;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

public class BSD_MeiRongKuaiXiu_KuCun_Fragment extends DialogFragment {

  private EditText  bsd_wxcl_kc_bianma,bsd_wxcl_kc_tuhao,
          bsd_wxcl_kc_mingcheng,bsd_wxcl_kc_danwei,bsd_wxcl_kc_pinpai;
  private MarqueeView   bsd_wxcl_kc_chexing;
  private TextView  tv_xxSum,tv_sxSum,tv_kcSum,tv_djSum,tv_jeSum;
  private ListView    bsd_wxcl_kc_lv;
  private RelativeLayout    bsd_wxcl_kc_gb;
  private Dialog  mWeiboDialog;
  private Context  context;
  private BSD_wxcl_kc_apater mAdapter;
  private ArrayList<BSD_WXCL_CK_Entity>  mList;
  private    String  peij_no;
  private URLS url;
  private BSD_WXCL_CK_Entity  ck_entity;
  private double currentKc,pjdj,je,kcsx,kcxx;
  private double currentKcSum=0,pjdjSum=0,jeSum=0,kcsxSum=0,kcxxSum=0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
        context=getActivity();
        url=new URLS();
        View view = inflater.from(context).inflate(R.layout.bsd_mrkx_wxcl_kc, null);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        peij_no=getArguments().getString("peij_no");
        mList=new ArrayList();
        mAdapter=new BSD_wxcl_kc_apater(context,mList);

        initView(view);
        query_kucun(peij_no);
        return   view;
    }


    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(1200,950);
        //隐藏输入法
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public  void  initView(View view){
        bsd_wxcl_kc_bianma= (EditText) view.findViewById(R.id.bsd_wxcl_kc_bianma);
        bsd_wxcl_kc_tuhao= (EditText) view.findViewById(R.id.bsd_wxcl_kc_tuhao);
        bsd_wxcl_kc_chexing= (MarqueeView) view.findViewById(R.id.bsd_wxcl_kc_chexing);
        bsd_wxcl_kc_mingcheng= (EditText) view.findViewById(R.id.bsd_wxcl_kc_mingcheng);
        bsd_wxcl_kc_danwei= (EditText) view.findViewById(R.id.bsd_wxcl_kc_danwei);
        bsd_wxcl_kc_pinpai= (EditText) view.findViewById(R.id.bsd_wxcl_kc_pinpai);

        bsd_wxcl_kc_lv= (ListView) view.findViewById(R.id.bsd_wxcl_kc_lv);
        bsd_wxcl_kc_lv.setAdapter(mAdapter);


        tv_sxSum= (TextView) view.findViewById(R.id.tv_sxSum);
        tv_xxSum= (TextView) view.findViewById(R.id.tv_xxSum);
        tv_kcSum= (TextView) view.findViewById(R.id.tv_kcSum);
        tv_djSum= (TextView) view.findViewById(R.id.tv_djSum);
        tv_jeSum= (TextView) view.findViewById(R.id.tv_jeSum);

        bsd_wxcl_kc_gb= (RelativeLayout) view.findViewById(R.id.bsd_wxcl_kc_gb);
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
    public  void   query_kucun(String peij_no){
        mWeiboDialog=WeiboDialogUtils.createLoadingDialog(this.getActivity(),"加载中......");
        AbRequestParams params = new AbRequestParams();
        params.put("peij_no", peij_no);
        params.put("caozuoyuanid", MyApplication.shared.getString("userid", ""));
        Log.i("cjn", "配件ck的参数，peij_no= "+peij_no+",caozuoyuanid="+MyApplication.shared.getString("userid", ""));
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxcl_kc, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int a, String s) {
                Log.i("cjn", "配件ck成功操作==" + s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if(jsonObject.getString("message").equals("查询成功")){
                        JSONObject  data=jsonObject.getJSONObject("data");
                        //主表
                        JSONArray  peijZhu=data.getJSONArray("peij_info");
                        JSONObject  zhuObject=peijZhu.getJSONObject(0);

//                        bsd_wxcl_kc_bianma,bsd_wxcl_kc_tuhao,bsd_wxcl_kc_chexing,
//                                bsd_wxcl_kc_mingcheng,bsd_wxcl_kc_danwei,bsd_wxcl_kc_pinpai;
                        bsd_wxcl_kc_bianma.setText(zhuObject.getString("peij_no"));
                        bsd_wxcl_kc_tuhao.setText(zhuObject.getString("peij_th"));
                        bsd_wxcl_kc_chexing.setText(zhuObject.getString("peij_cx"));
                        bsd_wxcl_kc_mingcheng.setText(zhuObject.getString("peij_mc"));
                        bsd_wxcl_kc_danwei.setText(zhuObject.getString("peij_dw"));
                        bsd_wxcl_kc_pinpai.setText(zhuObject.getString("peij_pp"));

                        //明细仓库表
                        JSONArray  peijCk=data.getJSONArray("cangk_info");
                        for(int  i=0;i<peijCk.length();i++){
                            JSONObject  objectCk=peijCk.getJSONObject(i);
                            ck_entity=new BSD_WXCL_CK_Entity();
                            ck_entity.setCkMc(objectCk.getString("cangk_mc"));
                            ck_entity.setCurrentKc(objectCk.getString("peij_kc"));
                            ck_entity.setPjDj(objectCk.getString("jiag_jp"));
                            ck_entity.setJe(objectCk.getString("peij_je"));
                            ck_entity.setKcSum(objectCk.getString("kuc_sx"));
                            ck_entity.setKcMin(objectCk.getString("kuc_xx"));
                            ck_entity.setKw(objectCk.getString("kuc_hw"));
                            //计算各列合计


                           if(!objectCk.getString("peij_kc").equals("")){
                               currentKc=Double.parseDouble(objectCk.getString("peij_kc"));
                               currentKcSum+=currentKc;
                           }

                           if(!objectCk.getString("jiag_jp").equals("")){
                               pjdj=Double.parseDouble(objectCk.getString("jiag_jp"));
                               pjdjSum+=pjdj;
                           }

                           if(!objectCk.getString("peij_je").equals("")){
                               je=Double.parseDouble(objectCk.getString("peij_je"));
                               jeSum+=je;
                           }
                            Log.i("sss", "kcsx==="+objectCk.getString("kuc_sx"));
                            if(!objectCk.getString("kuc_sx").equals("")){
                                kcsx=Double.parseDouble(objectCk.getString("kuc_sx"));
                                kcsxSum+=kcsx;
                            }
                            if(!objectCk.getString("kuc_xx").equals("")){
                                kcxx=Double.parseDouble(objectCk.getString("kuc_xx"));
                                kcxxSum+=kcxx;
                            }

                            mList.add(ck_entity);
                        }
                    }

                    mAdapter.notifyDataSetChanged();

//                    tv_xxSum,tv_sxSum,tv_kcSum,tv_djSum,tv_jeSum;
                    tv_xxSum.setText(kcxxSum+"");
                    tv_sxSum.setText(kcsxSum+"");
                    tv_kcSum.setText(currentKcSum+"");
                    tv_jeSum.setText(jeSum+"");


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
                Log.i("cjn", "失败" + s);
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });


    }




}
