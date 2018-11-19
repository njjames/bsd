package com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.fagmt;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
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
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_XM_Entity;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.dialog.PaiGongDialog;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.fagmt.fagmt_adp.BSD_wxywwd_wxxm_adp;
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

public class BSD_wxywdd_wxxm extends BaseFragment {
    private static final String PARAM_KEY = "param_key";
    private OnRefreashPaiGongListener onRefreashPaiGongListener;
    private String param;
    private TextView tv_wxxm_money;
    private TextView tv_record_num;
    private UpdateItemInfoDialog updateItemInfoDialog;
    private int currentPosition = 0;
    private XM_ZJ xm_zj;
    private ListView bsd_lsbj_lv;
    private TooPromptdiaog promptdiaog;
    private BSD_wxywwd_wxxm_adp adapter;
    private List<BSD_WeiXiuJieDan_XM_Entity> list_XM = new ArrayList<>();
    private URLS url;
    private Dialog mWeiboDialog;

    public static BSD_wxywdd_wxxm newInstance(String params) {
        BSD_wxywdd_wxxm fragment = new BSD_wxywdd_wxxm();
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
        return R.layout.bsd_wxywdd_wxxm;
    }

    @Override
    public void initView() {
        bsd_lsbj_lv = (ListView) view.findViewById(R.id.bsd_lsbj_lv);
        adapter = new BSD_wxywwd_wxxm_adp(getActivity(), list_XM);
        adapter.setOnOperateItemListener(new BSD_wxywwd_wxxm_adp.OnOperateItemListener() {
            @Override
            public void onPaiGong(final int position) {
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
                promptdiaog = new TooPromptdiaog(getContext(), "是否删除");
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
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
            }
        });
        tv_wxxm_money = (TextView) view.findViewById(R.id.tv_wxxm_money);
        tv_record_num = (TextView) view.findViewById(R.id.tv_record_num);
    }

    private void updateWxxmGsf(final double gongshif, final int position) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "更新中...");
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", list_XM.get(position).getWork_no());
        params.put("wxxm_no", list_XM.get(position).getWxxm_no());
        params.put("jg", gongshif + "");
        params.put("hyzk", "1"); // 这个参数目前也没有用
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_mrkx_upxm, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                list_XM.get(position).setWxxm_yje(gongshif);
                list_XM.get(position).setWxxm_je(gongshif);
                if (list_XM.get(position).getWxxm_gs() == 0) {
                    list_XM.get(position).setWxxm_dj(gongshif);
                } else {
                    list_XM.get(position).setWxxm_dj(gongshif / list_XM.get(position).getWxxm_gs());
                }
                int firstVisiblePosition = bsd_lsbj_lv.getFirstVisiblePosition();
                adapter.notifyDataSetChanged();
                bsd_lsbj_lv.setSelection(firstVisiblePosition);
                wxxmPrice();
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

    private void updateWxxmMC(final String newWxxmMc, final int position) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "更新中...");
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", list_XM.get(position).getWork_no());
        params.put("wxxm_no", list_XM.get(position).getWxxm_no());
        params.put("wxxm_mc", newWxxmMc);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_Update_WxxmMc, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                list_XM.get(position).setWxxm_mc(newWxxmMc);
                int firstVisiblePosition = bsd_lsbj_lv.getFirstVisiblePosition();
                adapter.notifyDataSetChanged();
                bsd_lsbj_lv.setSelection(firstVisiblePosition);
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

    private void deleteWxxm(final int position) {
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", list_XM.get(position).getWork_no());
        params.put("wxxm_no", list_XM.get(position).getWxxm_no());
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_deletXM, params, new AbStringHttpResponseListener() {
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

    private void wxxmPrice() {
        double wxxmZje = 0;
        for (int i = 0; i < list_XM.size(); i++) {
            wxxmZje = wxxmZje + (list_XM.get(i).getWxxm_je());
        }
        double v = (Math.round(wxxmZje * 100) / 100.0);
        tv_wxxm_money.setText(v + "元");
        if (list_XM.size() > 0) {
            tv_record_num.setText("(共" + list_XM.size() + "条记录)");
        } else {
            tv_record_num.setText("");
        }
        if (list_XM.size() > 0) {
            xm_zj.onYesClick(wxxmZje);
        } else {
            xm_zj.onYesClick(0);
        }
    }

    @Override
    public void initData() {
        url = new URLS();
        dataxm();
    }

    /**
     * 维修项目数据查询
     */
    public void dataxm() {
        list_XM.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", param);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxjd_xmlb, params, new AbStringHttpResponseListener() {
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
                            list_XM.add(entity);
                        }
                    } else {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
                wxxmPrice();
                WeiboDialogUtils.closeDialog(mWeiboDialog);
                if (list_XM.size() > 0) {
                    if (onRefreashPaiGongListener != null) {
                        onRefreashPaiGongListener.onWxxmRequestSuccess(list_XM.get(0).getWork_no(), list_XM.get(0).getWxxm_no());
                    }
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
                Show.showTime(getActivity(), "网络连接超时");
            }
        });
    }

    public void refreashData() {
        adapter.notifyDataSetChanged();
        wxxmPrice();
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public interface OnRefreashPaiGongListener {
        void onRefreash(String workNo, String wxxmNo);

        void onWxxmRequestSuccess(String workNo, String wxxmNo);
    }

    public void setOnRefreashPaiGongListener(OnRefreashPaiGongListener onRefreashPaiGongListener) {
        this.onRefreashPaiGongListener = onRefreashPaiGongListener;
    }

    public List<BSD_WeiXiuJieDan_XM_Entity> getList_XM() {
        return list_XM;
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
}
