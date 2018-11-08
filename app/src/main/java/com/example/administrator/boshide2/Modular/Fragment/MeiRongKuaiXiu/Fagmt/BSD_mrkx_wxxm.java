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
import com.example.administrator.boshide2.Conts;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Fragment.BaseFragment;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.Fagmt.fagmt_adp.BSD_mrbx_wxxm_adp;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_XM_Entity;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.dialog.PaiGongDialog;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.UpdateItemInfoDialog;
import com.example.administrator.boshide2.Modular.View.diaog.TooPromptdiaog;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.DownJianPan;
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

public class BSD_mrkx_wxxm extends BaseFragment {
    private static final String PARAM_KEY = "param_key";
    XM_ZJ xm_zj;
    private TextView tv_recordNum;
    private int currentPosition = 0;
    private OnRefreashPaiGongListener onRefreashPaiGongListener;
    private ListView bsd_lsbj_lv;
    private TooPromptdiaog promptdiaog;
    private BSD_mrbx_wxxm_adp adapter;
    private List<BSD_WeiXiuJieDan_XM_Entity> list_XM = new ArrayList<>();
    ChaKanPaiGongREN chaKanPaiGongREN;
    URLS url;
    private Dialog mWeiboDialog;
    private UpdateItemInfoDialog updateItemInfoDialog;
    TextView tv_wxxm_money;
    private String param;
    double wxxmZK = 1;
    private OnUpdateZKListener onUpdateZKListener;

    public static BSD_mrkx_wxxm newInstance(String params) {
        BSD_mrkx_wxxm fragment = new BSD_mrkx_wxxm();
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
        return R.layout.bsd_mrkx_wxxm;
    }

    public void setChaKanPaiGongREN(ChaKanPaiGongREN chaKanPaiGongREN) {
        this.chaKanPaiGongREN = chaKanPaiGongREN;
    }

    /**
     * 计算维修项目的总价格
     */
    public void wxxmPrice() {
        double wxxmZje = 0;
        for (int i = 0; i < list_XM.size(); i++) {
            wxxmZje = wxxmZje + (list_XM.get(i).getWxxm_yje() * list_XM.get(i).getWxxm_zk());
        }
        double v = (Math.round(wxxmZje * 100) / 100.0);
        tv_wxxm_money.setText(v + "元");
        if (list_XM.size() > 0) {
            tv_recordNum.setText("(共" + list_XM.size() + "条记录)");
        } else {
            tv_recordNum.setText("");
        }
        if (list_XM.size() > 0) {
            xm_zj.onYesClick(wxxmZje);
        } else {
            xm_zj.onYesClick(0);
        }
    }

    @Override
    public void initView() {
        tv_wxxm_money = (TextView) view.findViewById(R.id.tv_wxxm_money);
        bsd_lsbj_lv = (ListView) view.findViewById(R.id.bsd_lsbj_lv);
        adapter = new BSD_mrbx_wxxm_adp(getActivity(), list_XM);
        bsd_lsbj_lv.setAdapter(adapter);
        //单项派工
        adapter.setOnOperateItemListener(new BSD_mrbx_wxxm_adp.OnOperateItemListener() {
            @Override
            public void onPaiGong(final int position) {
                // 如果当前显示的位置不是派工的位置，则切换到派工的位置
                if (currentPosition != position) {
                    adapter.setCurrentPosition(position);
                    currentPosition = position;
                    int firstVisiblePosition = bsd_lsbj_lv.getFirstVisiblePosition();
                    adapter.notifyDataSetChanged();
                    bsd_lsbj_lv.setSelection(firstVisiblePosition);
                    if (onRefreashPaiGongListener != null) {
                        onRefreashPaiGongListener.onRefreash(list_XM.get(position).getWork_no(), list_XM.get(position).getWxxm_no());
                    }
                }
                PaiGongDialog paiGongDialog = new PaiGongDialog(getContext(), PaiGongDialog.PAIGONG_SINGLE);
                paiGongDialog.setWorkNo(param);
                paiGongDialog.setWxxmNo(list_XM.get(position).getWxxm_no());
                paiGongDialog.setOnPaiGongListener(new PaiGongDialog.OnPaiGongListener() {
                    @Override
                    public void onSuccess() {
                        if (onRefreashPaiGongListener != null) {
                            onRefreashPaiGongListener.onRefreash(param, list_XM.get(position).getWxxm_no());
                        }
                    }
                });
                paiGongDialog.show();
            }

            @Override
            public void onDelete(final int position) {
                promptdiaog = new TooPromptdiaog(getContext(), "确定删除此维修项目吗？");
                promptdiaog.setToopromtOnClickListener(new TooPromptdiaog.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick() {
                        deleteWxxm(position);
                    }
                });
                promptdiaog.show();
            }

            @Override
            public void onUpdateYgsf(final int position) {
                updateItemInfoDialog = new UpdateItemInfoDialog(getActivity(), UpdateItemInfoDialog.CHANGE_WXXMGS, list_XM.get(position).getWxxm_yje(), list_XM.get(position).getWxxm_mc());
                updateItemInfoDialog.show();
                updateItemInfoDialog.setToopromtOnClickListener(new UpdateItemInfoDialog.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick(double gongshif) {
                        updateWxxmGsf(gongshif, position);
                    }
                });
            }

            @Override
            public void onUpdateWxxmMc(final int position) {
                updateItemInfoDialog = new UpdateItemInfoDialog(getActivity(), UpdateItemInfoDialog.CHANGE_WXXMNAME, 0, list_XM.get(position).getWxxm_mc());
                updateItemInfoDialog.show();
                updateItemInfoDialog.setToopromtXmmc(new UpdateItemInfoDialog.ToopromtXmmc() {
                    @Override
                    public void onYesClick(String newWxxmMc) {
                        updateWxxmMC(newWxxmMc, position);
                    }
                });
            }
        });
        bsd_lsbj_lv.setAdapter(adapter);
        bsd_lsbj_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (currentPosition != i) {
                    adapter.setCurrentPosition(i);
                    currentPosition = i;
                    int firstVisiblePosition = bsd_lsbj_lv.getFirstVisiblePosition();
                    adapter.notifyDataSetChanged();
                    bsd_lsbj_lv.setSelection(firstVisiblePosition);
                    if (onRefreashPaiGongListener != null) {
                        onRefreashPaiGongListener.onRefreash(list_XM.get(i).getWork_no(), list_XM.get(i).getWxxm_no());
                    }
                }
            }
        });
        tv_recordNum = (TextView) view.findViewById(R.id.tv_record_num);
    }

    @Override
    public void initData() {
        url = new URLS();
        dataxm();
    }

    private void updateWxxmMC(final String wxxmMc, final int position) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "更新中...");
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", param);
        params.put("wxxm_no", list_XM.get(position).getWxxm_no());
        params.put("wxxm_mc", wxxmMc);
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_Update_WxxmMc, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                if (data.equals("success")) {
                    list_XM.get(position).setWxxm_mc(wxxmMc);
                    int firstVisiblePosition = bsd_lsbj_lv.getFirstVisiblePosition();
                    adapter.notifyDataSetChanged();
                    bsd_lsbj_lv.setSelection(firstVisiblePosition);
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

    //修改工时费
    public void updateWxxmGsf(final double ygsf, final int position) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "更新中...");
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", param);
        params.put("wxxm_no", list_XM.get(position).getWxxm_no());
        params.put("ygsf", ygsf + "");
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_mrkx_upxm, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                if (data.equals("success")) {
                    list_XM.get(position).setWxxm_yje(ygsf);
                    list_XM.get(position).setWxxm_je(ygsf * list_XM.get(position).getWxxm_zk());
                    if (list_XM.get(position).getWxxm_gs() == 0) {
                        list_XM.get(position).setWxxm_dj(list_XM.get(position).getWxxm_je());
                    } else {
                        list_XM.get(position).setWxxm_dj(list_XM.get(position).getWxxm_je() / list_XM.get(position).getWxxm_gs());
                    }
                    int firstVisiblePosition = bsd_lsbj_lv.getFirstVisiblePosition();
                    adapter.notifyDataSetChanged();
                    bsd_lsbj_lv.setSelection(firstVisiblePosition);
                    wxxmPrice();
                }
                updateItemInfoDialog.dismiss();
                DownJianPan.DJP(getContext());
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
     * 删除维修项目
     * @param position
     */
    public void deleteWxxm(final int position) {
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", param);
        params.put("wxxm_no", list_XM.get(position).getWxxm_no());
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_deletXM, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                list_XM.remove(position);
                adapter.notifyDataSetChanged();
                wxxmPrice();
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
                Toast.makeText(getContext(), "删除失败", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 维修项目数据查询
     */
    public void dataxm() {
        list_XM.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", param);
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_wxjd_xmlb, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int a, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject item = jsonarray.getJSONObject(i);
                            BSD_WeiXiuJieDan_XM_Entity entity = new BSD_WeiXiuJieDan_XM_Entity();
                            entity.setReco_no(item.getInt("reco_no"));
                            entity.setWork_no(item.getString("work_no"));
                            entity.setWxxm_no(item.getString("wxxm_no"));
                            entity.setWxxm_mc(item.getString("wxxm_mc"));
                            entity.setWxxm_gs(item.getDouble("wxxm_gs"));
                            entity.setWxxm_khgs(item.getDouble("wxxm_khgs"));
                            entity.setWxxm_cb(item.getDouble("wxxm_cb"));
                            entity.setWxxm_je(item.getDouble("wxxm_je"));
                            entity.setWxxm_yje(item.getDouble("wxxm_yje"));
                            entity.setWxxm_mxcx(item.getString("wxxm_mxcx"));
                            entity.setWxxm_zt(item.getString("wxxm_zt"));
                            entity.setWxxm_bz(item.getString("wxxm_bz"));
                            entity.setWxxm_dj(item.getDouble("wxxm_dj"));
                            entity.setWxxm_zk(item.getDouble("wxxm_zk"));
                            entity.setWxxm_yje(item.getDouble("wxxm_yje"));
                            list_XM.add(entity);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
                wxxmPrice();
                if (list_XM.size() > 0) {
                    if (onRefreashPaiGongListener != null) {
                        onRefreashPaiGongListener.onWxxmRequestSuccess(list_XM.get(0).getWork_no(), list_XM.get(0).getWxxm_no());
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
                WeiboDialogUtils.closeDialog(mWeiboDialog);
                Show.showTime(getActivity(), "网络连接超时");
            }
        });

    }

    public void updateWxxmZK(double wxxmZK) {
        this.wxxmZK = wxxmZK;
        updateWxxmZKToDB(param, wxxmZK);
    }

    /**
     * 更新折扣到数据库中
     * @param workNo
     * @param wxxmZK
     */
    private void updateWxxmZKToDB(String workNo, final double wxxmZK) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "更新中...");
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", workNo);
        params.put("wxxm_zk", String.valueOf(wxxmZK));
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_UPDATA_XMZK, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int a, String data) {
                if (data.equals("success")) { // 库里面更新成功后，才更新UI
                    for (BSD_WeiXiuJieDan_XM_Entity entity : list_XM) {
                        entity.setWxxm_zk(wxxmZK);
                        entity.setWxxm_je(entity.getWxxm_yje() * wxxmZK);
                        if (entity.getWxxm_gs() == 0) {
                            entity.setWxxm_dj(entity.getWxxm_je());
                        } else {
                            entity.setWxxm_dj(entity.getWxxm_je() / entity.getWxxm_gs());
                        }
                    }
                    adapter.notifyDataSetChanged();
                    if (onUpdateZKListener != null) {
                        onUpdateZKListener.onSuccess(wxxmZK);
                    }
                    wxxmPrice();
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
                Show.showTime(getActivity(), "网络连接超时");
            }
        });
    }

    public void reLoadData() {
        dataxm();
    }

    public interface ChaKanPaiGongREN {
        void onYesClick(String work_no, String wxxm_no, double wxxm_gs, double wxxm_je);
    }

    public interface XM_ZJ {
        /**
         * 项目修改之后，计算单据总金额的回调方法
         * @param xmzj
         */
        void onYesClick(double xmzj);
    }

    public void setXm_zj(XM_ZJ xm_zj) {
        this.xm_zj = xm_zj;
    }

    public List<BSD_WeiXiuJieDan_XM_Entity> getList_XM() {
        return list_XM;
    }

    public void refreashData() {
        adapter.notifyDataSetChanged();
        wxxmPrice();
    }

    public interface OnRefreashPaiGongListener {
        void onRefreash(String workNo, String wxxmNo);

        void onWxxmRequestSuccess(String workNo, String wxxmNo);
    }

    public void setOnRefreashPaiGongListener(OnRefreashPaiGongListener onRefreashPaiGongListener) {
        this.onRefreashPaiGongListener = onRefreashPaiGongListener;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public interface OnUpdateZKListener {
        void onSuccess(double wxxmZK);
        void onFail();
    }

    public void setOnUpdateZKListener(OnUpdateZKListener onUpdateZKListener) {
        this.onUpdateZKListener = onUpdateZKListener;
    }
}
