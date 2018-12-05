package com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.Fagmt;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Fragment.BaseFragment;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.Fagmt.fagmt_adp.BSD_mrkx_wxcl_adp;
import com.example.administrator.boshide2.Modular.Fragment.KuCunDialogFragment;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_CL_Entity;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.UpdateItemInfoDialog;
import com.example.administrator.boshide2.Modular.View.diaog.TooPromptdiaog;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.QuanQuan.WeiboDialogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 博士德车辆信息维修历史
 * Created by Administrator on 2017-4-24.
 */
public class BSD_mrkx_wxcl extends BaseFragment {
    private static final String PARAM_KEY = "param_key";
    private CL_ZJ cl_zj;
    private TextView tv_recordNum;
    private UpdateItemInfoDialog updateItemInfoDialog;
    private ListView bsd_lsbj_lv;
    private BSD_mrkx_wxcl_adp adapter;
    private List<BSD_WeiXiuJieDan_CL_Entity> list_CL = new ArrayList<>();
    private URLS url;
    private Dialog mWeiboDialog;
    private TooPromptdiaog promptdiaog;
    private TextView tv_wxcl_money;
    private String param;
    private double wxclZK;
    private OnUpdateZKListener onUpdateZKListener;

    public static BSD_mrkx_wxcl newInstance(String params) {
        BSD_mrkx_wxcl fragment = new BSD_mrkx_wxcl();
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
        return R.layout.bsd_mrkx_wxcl;
    }

    @Override
    public void initView() {
        tv_wxcl_money = (TextView) view.findViewById(R.id.tv_wxcl_money);
        bsd_lsbj_lv = (ListView) view.findViewById(R.id.bsd_lsbj_lv);
        adapter = new BSD_mrkx_wxcl_adp(getActivity(), list_CL);
        bsd_lsbj_lv.setAdapter(adapter);
        adapter.setOnOperateItemListener(new BSD_mrkx_wxcl_adp.OnOperateItemListener() {
            @Override
            public void onDelete(final String peij_no, final int position) {
                promptdiaog = new TooPromptdiaog(getContext(), "确定删除此配件吗？");
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
                KuCunDialogFragment kcDialog = KuCunDialogFragment.newInstance(peij_no);
                kcDialog.show(getFragmentManager(),"kcDialog");
            }

            @Override
            public void onUpdateSl(final int position) {
                updateItemInfoDialog = new UpdateItemInfoDialog(getActivity(), UpdateItemInfoDialog.CHANGE_PEIJSL, list_CL.get(position).getPeij_sl(), list_CL.get(position).getPeij_mc());
                updateItemInfoDialog.show();
                updateItemInfoDialog.setToopromtOnClickListener(new UpdateItemInfoDialog.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick(double newPeijSl) {
                        updatePeijSl(newPeijSl, position);
                    }
                });
            }

            @Override
            public void onUpdateYDj(final int position) {
                updateItemInfoDialog = new UpdateItemInfoDialog(getActivity(), UpdateItemInfoDialog.CHANGE_PEIJDJ, list_CL.get(position).getPeij_ydj(), list_CL.get(position).getPeij_mc());
                updateItemInfoDialog.show();
                updateItemInfoDialog.setToopromtOnClickListener(new UpdateItemInfoDialog.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick(double newPeijYdj) {
                        updatePeijYdj(newPeijYdj, position);
                    }
                });
            }
        });
        bsd_lsbj_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        tv_recordNum = (TextView) view.findViewById(R.id.tv_record_num);
    }

    @Override
    public void initData() {
        url = new URLS();
        cldata();
    }

    private void updatePeijYdj(final double newPeijYdj, final int position) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "更新中...");
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", param);
        params.put("peij_no", list_CL.get(position).getPeij_no());
        params.put("ydj", newPeijYdj + "");
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_update_peijydj, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String data) {
                if (data.equals("success")) {
                    list_CL.get(position).setPeij_ydj(newPeijYdj);
                    list_CL.get(position).setPeij_yje(newPeijYdj * list_CL.get(position).getPeij_sl());
                    list_CL.get(position).setPeij_dj(newPeijYdj * list_CL.get(position).getPeij_zk());
                    list_CL.get(position).setPeij_je(newPeijYdj * list_CL.get(position).getPeij_zk() * list_CL.get(position).getPeij_sl());
                    int firstVisiblePosition = bsd_lsbj_lv.getFirstVisiblePosition();
                    adapter.notifyDataSetChanged();
                    bsd_lsbj_lv.setSelection(firstVisiblePosition);
                    wxclPrice();
                }
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

    public void updatePeijSl(final double peijSl, final int position) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "更新中...");
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", param);
        params.put("peij_no", list_CL.get(position).getPeij_no());
        params.put("sl", peijSl + "");
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_meirongxiugai, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String data) {
                if (data.equals("success")) {
                    list_CL.get(position).setPeij_sl(peijSl);
                    list_CL.get(position).setPeij_yje(peijSl * list_CL.get(position).getPeij_ydj());
                    list_CL.get(position).setPeij_je(peijSl * list_CL.get(position).getPeij_dj());
                    int firstVisiblePosition = bsd_lsbj_lv.getFirstVisiblePosition();
                    adapter.notifyDataSetChanged();
                    bsd_lsbj_lv.setSelection(firstVisiblePosition);
                    wxclPrice();
                }
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

    /**
     * 删除维修用料
     * @param peij_no
     * @param position
     */
    public void deletWxcl(String peij_no, final int position) {
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", param);
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
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_wxjd_cllb, params, new AbStringHttpResponseListener() {
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
                            entity.setPeij_zk(item.getDouble("peij_zk"));
                            entity.setPeij_ydj(item.getDouble("peij_ydj"));
                            entity.setPeij_yje(item.getDouble("peij_yje"));
                            list_CL.add(entity);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                    wxclPrice();
                } else {
                    wxclPriceGone();
                }
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }

            private void wxclPriceGone() {
                double wxclZje = 0;
                for (int i = 0; i < list_CL.size(); i++) {
                    wxclZje = wxclZje + (list_CL.get(i).getPeij_je());
                }
                if (list_CL.size() > 0) {
                    cl_zj.onYesClick(wxclZje);
                } else {
                    cl_zj.onYesClick(0);
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

    public void updateWxclZK(double wxclZK) {
        this.wxclZK = wxclZK;
        updateWxclZKToDB(param, wxclZK);
    }

    private void updateWxclZKToDB(String workNo, final double wxclZK) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "更新中...");
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", workNo);
        params.put("wxcl_zk", String.valueOf(wxclZK));
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_UPDATA_CLZK, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int a, String data) {
                if (data.equals("success")) { // 库里面更新成功后，才更新UI
                    for (BSD_WeiXiuJieDan_CL_Entity entity : list_CL) {
                        entity.setPeij_zk(wxclZK);
                        entity.setPeij_je(entity.getPeij_yje() * wxclZK);
                        entity.setPeij_dj(entity.getPeij_je() / entity.getPeij_sl());
                    }
                    if (adapter != null) { // 增加null判断，防止维修项目没有显示过，造成adapter不没有被初始化
                        adapter.notifyDataSetChanged();
                        wxclPrice();
                    }
                    if (onUpdateZKListener != null) {
                        onUpdateZKListener.onSuccess(wxclZK);
                    }
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
                Toast.makeText(getHostActicity(), "网络请求失败", Toast.LENGTH_SHORT).show();
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });
    }

    public void refreashData() {
        adapter.notifyDataSetChanged();
        wxclPrice();
    }

    public void reLoadData() {
        cldata();
    }

    public interface CL_ZJ {
        void onYesClick(double clzj);
    }

    public void setCl_zj(CL_ZJ cl_zj) {
        this.cl_zj = cl_zj;
    }

    public List<BSD_WeiXiuJieDan_CL_Entity> getList_CL() {
        return list_CL;
    }


    /**
     * 计算维修材料的总价
     */
    public void wxclPrice() {
        double wxclZje = 0;
        for (int i = 0; i < list_CL.size(); i++) {
            wxclZje = wxclZje + (list_CL.get(i).getPeij_je());
        }
        double v = (Math.round(wxclZje * 100) / 100.0);
        tv_wxcl_money.setText( v  + "元");
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

    public interface OnUpdateZKListener {
        void onSuccess(double wxclZK);
        void onFail();
    }

    public void setOnUpdateZKListener(OnUpdateZKListener onUpdateZKListener) {
        this.onUpdateZKListener = onUpdateZKListener;
    }

}
