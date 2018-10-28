package com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.fagmt;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.example.administrator.boshide2.Conts;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Fragment.BaseFragment;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.dialogFragment.BSD_MeiRongKuaiXiu_KuCun_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_CL_Entity;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.fagmt.fagmt_adp.BSD_wxywwd_wxcl_adp;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.BSD_XiuGaiGongShi;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.Pop_Entity.BSD_wxyy_cl_pop_entity;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.UpdateItemInfoDialog;
import com.example.administrator.boshide2.Modular.View.diaog.TooPromptdiaog;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.QuanQuan.WeiboDialogUtils;
import com.example.administrator.boshide2.Tools.Show;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 博士德车辆信息维修历史
 * Created by Administrator on 2017-4-24.
 */

public class BSD_wxywdd_wxcl extends BaseFragment {
    private static final String PARAM_KEY = "param_key";
    private ListView bsd_lsbj_lv;
    private BSD_wxywwd_wxcl_adp adapter;
    private List<BSD_WeiXiuJieDan_CL_Entity> list_CL = new ArrayList<>();
    private URLS url;
    private Dialog mWeiboDialog;
    private TooPromptdiaog promptdiaog;
    private TextView tv_wxxm_money;
    private TextView tv_recordNum;
    private String param;
    private UpdateItemInfoDialog updateItemInfoDialog;
    private CL_ZJ cl_zj;

    public static BSD_wxywdd_wxcl newInstance(String params) {
        BSD_wxywdd_wxcl fragment = new BSD_wxywdd_wxcl();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_KEY, params);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        param = getArguments().getString(PARAM_KEY);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.bsd_wxywdd_wxcl;
    }

    @Override
    public void initView() {
        bsd_lsbj_lv = (ListView) view.findViewById(R.id.bsd_lsbj_lv);
        adapter = new BSD_wxywwd_wxcl_adp(getActivity(), list_CL);
        adapter.setOnOperateItemListener(new BSD_wxywwd_wxcl_adp.OnOperateItemListener() {
            @Override
            public void onDelete(final String peij_no, final int position) {
                promptdiaog = new TooPromptdiaog(getContext(), "确定删除吗？");
                promptdiaog.setToopromtOnClickListener(new TooPromptdiaog.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick() {
                        deletWxcl(peij_no, position);
                    }
                });
                promptdiaog.show();
            }

            @Override
            public void onSearchStock(String peij_no) {
                BSD_MeiRongKuaiXiu_KuCun_Fragment.newInstance(peij_no).show(getFragmentManager(),"kcDialog");
            }

            @Override
            public void onUpdateSl(final String peij_no, String peij_mc, double peij_sl, final int position) {
                updateItemInfoDialog = new UpdateItemInfoDialog(getActivity(), UpdateItemInfoDialog.CHANGE_PEIJSL, peij_sl, peij_mc);
                updateItemInfoDialog.show();
                updateItemInfoDialog.setToopromtOnClickListener(new UpdateItemInfoDialog.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick(double newPeijSl) {
                        updatePeijSl(peij_no, newPeijSl, position);
                    }
                });
            }

            @Override
            public void onUpdateYDj(final String peij_no, String peij_mc, double peij_ydj, final int position) {
                updateItemInfoDialog = new UpdateItemInfoDialog(getActivity(), UpdateItemInfoDialog.CHANGE_PEIJDJ, peij_ydj, peij_mc);
                updateItemInfoDialog.show();
                updateItemInfoDialog.setToopromtOnClickListener(new UpdateItemInfoDialog.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick(double newPeijYdj) {
                        updatePeijYdj(peij_no, newPeijYdj, position);
                    }
                });
            }
        });
        bsd_lsbj_lv.setAdapter(adapter);
        bsd_lsbj_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        tv_wxxm_money = (TextView) view.findViewById(R.id.tv_wxcl_money);
        tv_recordNum = (TextView) view.findViewById(R.id.tv_record_num);
    }

    @Override
    public void initData() {
        url = new URLS();
        cldata();
    }

    private void updatePeijYdj(String peij_no, final double newPeijYdj, final int position) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "更新中...");
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", Conts.work_no);
        params.put("peij_no", peij_no);
        params.put("ydj", newPeijYdj + "");
        params.put("zk", Conts.MRKX_CL_ZK + "");
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_update_peijydj, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                list_CL.get(position).setPeij_ydj(newPeijYdj);
                list_CL.get(position).setPeij_yje(newPeijYdj * list_CL.get(position).getPeij_sl());
                list_CL.get(position).setPeij_dj(newPeijYdj * Conts.MRKX_CL_ZK);
                list_CL.get(position).setPeij_je(newPeijYdj * Conts.MRKX_CL_ZK * list_CL.get(position).getPeij_sl());
                int firstVisiblePosition = bsd_lsbj_lv.getFirstVisiblePosition();
                adapter.notifyDataSetChanged();
                bsd_lsbj_lv.setSelection(firstVisiblePosition);
                wxclPrice();
                updateItemInfoDialog.dismiss();
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
                Toast.makeText(getContext(), "修改失败", Toast.LENGTH_SHORT).show();
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });
    }

    private void updatePeijSl(String peij_no, final double peijSl, final int position) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "更新中...");
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", Conts.work_no);
        params.put("peij_no", peij_no);
        params.put("sl", peijSl + "");
        params.put("zk", Conts.MRKX_CL_ZK + "");
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_meirongxiugai, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                list_CL.get(position).setPeij_sl(peijSl);
                list_CL.get(position).setPeij_yje(peijSl * list_CL.get(position).getPeij_ydj());
                list_CL.get(position).setPeij_dj(Conts.MRKX_CL_ZK * list_CL.get(position).getPeij_ydj());
                list_CL.get(position).setPeij_je(peijSl * Conts.MRKX_CL_ZK * list_CL.get(position).getPeij_ydj());
                int firstVisiblePosition = bsd_lsbj_lv.getFirstVisiblePosition();
                adapter.notifyDataSetChanged();
                bsd_lsbj_lv.setSelection(firstVisiblePosition);
                wxclPrice();
                updateItemInfoDialog.dismiss();
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
                Toast.makeText(getContext(), "修改失败", Toast.LENGTH_SHORT).show();
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });
    }

    private void deletWxcl(String peij_no, final int position) {
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", Conts.work_no);
        params.put("peij_no", peij_no);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_deletCL, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int a, String s) {
                list_CL.remove(position);
                adapter.notifyDataSetChanged();
                wxclPrice();
                promptdiaog.dismiss();
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
            }
        });
    }


    /**
     * 维修材料列表
     */
    public void cldata() {
        list_CL.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", param);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxjd_cllb, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int a, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject item = jsonarray.getJSONObject(i);
                            BSD_WeiXiuJieDan_CL_Entity entity = new BSD_WeiXiuJieDan_CL_Entity();
                            entity.setReco_no(item.getInt("reco_no"));
                            entity.setWork_no(item.getString("work_no"));
                            entity.setPeij_no(item.getString("peij_no"));
                            entity.setPeij_th(item.getString("peij_th"));
                            entity.setPeij_mc(item.getString("peij_mc"));
                            entity.setPeij_dw(item.getString("peij_dw"));
                            entity.setPeij_sl(item.getDouble("peij_sl"));
                            entity.setPeij_dj(item.getDouble("peij_dj"));
                            entity.setPeij_je(item.getDouble("peij_je"));
                            entity.setPeij_zt(item.getString("peij_zt"));
                            list_CL.add(entity);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
                wxclPrice();
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


    public List<BSD_WeiXiuJieDan_CL_Entity> getList_CL() {
        return list_CL;
    }

    public void refreashData() {
        adapter.notifyDataSetChanged();
        wxclPrice();
    }

    private void wxclPrice() {
        double wxclZje = 0;
        for (int i = 0; i < list_CL.size(); i++) {
            wxclZje = wxclZje + (list_CL.get(i).getPeij_je());
        }
        double v = (Math.round(wxclZje* 100) / 100.0);
        tv_wxxm_money.setText( v  + "元");
        if (list_CL.size() > 0) {
            tv_recordNum.setText("(共" + list_CL.size() + "条记录)");
        } else {
            tv_recordNum.setText("");
        }
        if (list_CL.size() > 0) {
            cl_zj.onYesClick(wxclZje);
        } else {
            cl_zj.onYesClick(0);
        }
    }

    public interface CL_ZJ {
        void onYesClick(double clzj);
    }

    public void setCl_zj(CL_ZJ cl_zj) {
        this.cl_zj = cl_zj;
    }
}
