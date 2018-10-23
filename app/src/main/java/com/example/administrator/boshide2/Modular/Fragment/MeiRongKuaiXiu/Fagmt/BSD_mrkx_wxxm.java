package com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.Fagmt;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
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
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.Fagmt.fagmt_adp.BSD_mrbx_wxxm_adp;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_XM_Entity;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.deleg.BSD_WeiXiuYeWuDiaoDuDian_delg;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.dialog.PaiGongDialog;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.fagmt.BSD_ZCDUXQ_XM_POP;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.BSD_XiuGaiGongShi;
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
import java.util.HashMap;
import java.util.List;

/**
 * 博士德车辆信息维修历史
 * Created by Administrator on 2017-4-24.
 */

public class BSD_mrkx_wxxm extends Fragment {
    XM_ZJ xm_zj;
    private TextView tv_recordNum;
    private int currentPosition = 0;
    private OnRefreashPaiGongListener onRefreashPaiGongListener;

    private int last_item = -1;
    private RelativeLayout beijing;
    private ListView bsd_lsbj_lv;
    private TooPromptdiaog promptdiaog;
    private List<HashMap<String, String>> data = new ArrayList<>();
    private BSD_mrbx_wxxm_adp adapter;
    private List<BSD_WeiXiuJieDan_XM_Entity> list_XM = new ArrayList<>();
    RelativeLayout bsd_zcdd_rl_ztpg;
    BSD_ZCDUXQ_XM_POP bsd_zcduxq_xm_pop;
    BSD_WeiXiuYeWuDiaoDuDian_delg delg;
    ChaKanPaiGongREN chaKanPaiGongREN;
    RelativeLayout bsd_wxxm;
    URLS url;
    private Dialog mWeiboDialog;
    LinearLayout oldView;
    int choufutianjia = 0;
    BSD_XiuGaiGongShi bsd_xiuGaiGongShi;
    private UpdateItemInfoDialog updateItemInfoDialog;
    TextView tv_wxxm_money;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bsd_mrkx_wxxm, null);
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        url = new URLS();
        Conts.MRKX = 1;
        init(view);
        dataxm();
        for (int a = 0; a < list_XM.size(); a++) {
            list_XM.get(a).getReco_no();
            Log.i("cjn", "循环查看这个id" + list_XM.get(a).getReco_no());
        }

        return view;
    }

    public void setChaKanPaiGongREN(ChaKanPaiGongREN chaKanPaiGongREN) {
        this.chaKanPaiGongREN = chaKanPaiGongREN;
    }

    String wxxm_nos;

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

    public void init(View view) {
        tv_wxxm_money = (TextView) view.findViewById(R.id.tv_wxxm_money);
        bsd_lsbj_lv = (ListView) view.findViewById(R.id.bsd_lsbj_lv);
        adapter = new BSD_mrbx_wxxm_adp(getActivity(), list_XM);
        bsd_lsbj_lv.setAdapter(adapter);
        //单项派工
        adapter.setOnOperateItemListener(new BSD_mrbx_wxxm_adp.OnOperateItemListener() {
            @Override
            public void onPaiGong(final String wxxmNo) {
                PaiGongDialog paiGongDialog = new PaiGongDialog(getContext(), PaiGongDialog.PAIGONG_SINGLE);
                paiGongDialog.setWorkNo(Conts.work_no);
                paiGongDialog.setWxxmNo(wxxmNo);
                paiGongDialog.setOnPaiGongListener(new PaiGongDialog.OnPaiGongListener() {
                    @Override
                    public void onSuccess() {
                        if (onRefreashPaiGongListener != null) {
                            onRefreashPaiGongListener.onRefreash(Conts.work_no, wxxmNo);
                        }
                    }
                });
                paiGongDialog.show();
            }

            @Override
            public void onDelete(final String wxxmNo, final int position) {
                promptdiaog = new TooPromptdiaog(getContext(), "是否删除");
                promptdiaog.setToopromtOnClickListener(new TooPromptdiaog.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick() {
                        deleteWxxm(wxxmNo, position);
                    }
                });
                promptdiaog.show();
            }

            @Override
            public void onUpdateYgsf(final String wxxmNo, final double wxxmGs, final double wxxmYje, final String wxxmMc, final int position) {
                updateItemInfoDialog = new UpdateItemInfoDialog(getActivity(), UpdateItemInfoDialog.CHANGE_WXXMGS, wxxmYje, wxxmMc);
                updateItemInfoDialog.show();
                updateItemInfoDialog.setToopromtOnClickListener(new UpdateItemInfoDialog.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick(double gongshif) {
                        Conts.wxxm_je = gongshif;
                        updateWxxmGsf(wxxmNo, wxxmGs, gongshif, wxxmMc, position);
                    }
                });
            }

            @Override
            public void onUpdateWxxmMc(final String wxxmNo, final String wxxmMc, final int position) {
                updateItemInfoDialog = new UpdateItemInfoDialog(getActivity(), UpdateItemInfoDialog.CHANGE_WXXMNAME, 0, wxxmMc);
                updateItemInfoDialog.show();
                updateItemInfoDialog.setToopromtXmmc(new UpdateItemInfoDialog.ToopromtXmmc() {
                    @Override
                    public void onYesClick(String newWxxmMc) {
                        updateWxxmMC(list_XM.get(position).getWork_no(), wxxmNo, newWxxmMc, position);
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

    private void updateWxxmMC(String workNo, String wxxmNo, final String wxxmMc, final int position) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "更新中...");
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", workNo);
        params.put("wxxm_no", wxxmNo);
        params.put("wxxm_mc", wxxmMc);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_Update_WxxmMc, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                list_XM.get(position).setWxxm_mc(wxxmMc);
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

    //修改工时费
    public void updateWxxmGsf(String wxxm_no, double gs, final double dj, String wxxmMc, final int position) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "更新中...");
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", Conts.work_no);
        params.put("wxxm_no", wxxm_no);
        params.put("jg", dj + "");
        params.put("gs", gs + "");
        params.put("hyzk", Conts.MRKX_XM_ZK + "");
        params.put("wxxm_mc", wxxmMc);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_mrkx_upxm, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                list_XM.get(position).setWxxm_yje(dj);
                list_XM.get(position).setWxxm_je(dj * Conts.MRKX_XM_ZK);
                int firstVisiblePosition = bsd_lsbj_lv.getFirstVisiblePosition();
                adapter.notifyDataSetChanged();
                bsd_lsbj_lv.setSelection(firstVisiblePosition);
                wxxmPrice();
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
     * 全部删除操作
     */
    public void deltAll() {
        if (list_XM.size() > 0) {
            for (int i = 0; i < list_XM.size(); i++) {
                deletxmall(list_XM.get(i).getReco_no());



            }
        } else {
        }


    }

    /**
     * 删除操作
     *
     * @param i
     */
    public void deletxmall(final int i) {

        AbRequestParams params = new AbRequestParams();
        params.put("id", i);
        Log.i("cjn", "集合个主" + list_XM.size() + "查看这个id" + i);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_deletXM, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aa, String s) {
                Log.i("cjn", "成功操作==" + s);
                dataxm();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                Log.i("cjn", "失败==" + s);
                adapter.notifyDataSetChanged();
            }
        });


    }

    /**
     * 按照reco_no删除维修项目
     * @param wxxmNo
     * @param position
     */
    public void deleteWxxm(String wxxmNo, final int position) {
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", Conts.work_no);
        params.put("wxxm_no", wxxmNo);
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

    /**
     * 维修项目数据查询
     */
    public void dataxm() {
        Log.i("gscxaaa", "onClick:555555到了mrkx_wxxm里的查询所有项目方法了 ");
        list_XM.clear();
        AbRequestParams params = new AbRequestParams();
        Log.i("cjn", "看看单号" + Conts.work_no);
        params.put("work_no", Conts.work_no);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxjd_xmlb, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int a, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    Log.i("cjn", "这个是请求成功xM" + s);
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
                    } else {
                        Log.i("cjn", "这个是请求失败" + s);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
                wxxmPrice();
                WeiboDialogUtils.closeDialog(mWeiboDialog);
                Conts.BSD_xmshuliang = list_XM.size();
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

    public interface ChaKanPaiGongREN {
        public void onYesClick(String work_no, String wxxm_no, double wxxm_gs, double wxxm_je);
    }


    /**
     * 添加项目
     */
    String json;

    public void XMinData(final String DH) {
        Log.i("gscxaaa", "onClick:4444444到了mrkx_wxxm里的添加方法了 ");

        json = "{" + '"' + "data" + '"' + ":" + "[";
        for (int i = 0; i < list_XM.size() - 1; i++) {
            json = json + "{" + '"' + "work_no" + '"' + ":" + '"' + DH + '"' + "," + '"' + "wxxm_mc" + '"' + ":" + '"' + list_XM.get(i).getWxxm_mc() + '"' + "," + '"' +
                    "wxxm_gs" + '"' + ":" + '"' + list_XM.get(i).getWxxm_gs() + '"' + "," + '"' +
                    "wxxm_dj" + '"' + ":" + '"' + list_XM.get(i).getWxxm_dj() + '"' + "," + '"' +
                    "wxxm_no" + '"' + ":" + '"' + list_XM.get(i).getWxxm_no() + '"' + "," + '"' +
                    "wxxm_je" + '"' + ":" + '"' + list_XM.get(i).getWxxm_je() + '"' + "," + '"' +
                    "wxxm_cb" + '"' + ":" + '"' + list_XM.get(i).getWxxm_cb() + '"' + "," + '"' +
                    "wxxm_zk" + '"' + ":" + '"' + list_XM.get(i).getWxxm_zk() + '"' + "," + '"' +
                    "wxxm_yje" + '"' + ":" + '"' + list_XM.get(i).getWxxm_yje() + '"' + "," + '"' +
                    "wxxm_zt" + '"' + ":" + '"' + "正常" + '"' + "," + '"' +
                    "wxxm_tpye" + '"' + ":" + '"' + "正常" + '"' + "}" + ",";
        }


        json = json + "{" + '"' + "work_no" + '"' + ":" + '"' + DH + '"' + "," + '"' + "wxxm_mc" + '"' + ":" + '"' + list_XM.get(list_XM.size() - 1).getWxxm_mc() + '"' + "," + '"' +
                "wxxm_gs" + '"' + ":" + '"' + list_XM.get(list_XM.size() - 1).getWxxm_gs() + '"' + "," + '"' +
                "wxxm_dj" + '"' + ":" + '"' + list_XM.get(list_XM.size() - 1).getWxxm_dj() + '"' + "," + '"' +
                "wxxm_no" + '"' + ":" + '"' + list_XM.get(list_XM.size() - 1).getWxxm_no() + '"' + "," + '"' +
                "wxxm_je" + '"' + ":" + '"' + list_XM.get(list_XM.size() - 1).getWxxm_je() + '"' + "," + '"' +
                "wxxm_cb" + '"' + ":" + '"' + list_XM.get(list_XM.size() - 1).getWxxm_cb() + '"' + "," + '"' +
                "wxxm_zk" + '"' + ":" + '"' + list_XM.get(list_XM.size() - 1).getWxxm_zk() + '"' + "," + '"' +
                "wxxm_yje" + '"' + ":" + '"' + list_XM.get(list_XM.size() - 1).getWxxm_yje() + '"' + "," + '"' +
                "wxxm_zt" + '"' + ":" + '"' + "正常" + '"' + "," + '"' +
                "wxxm_tpye" + '"' + ":" + '"' + "正常"
                + '"' + "}" + "]" + "}";
//        + "]" + "}"

        Log.i("gscxaaa", "最后一次查看json,工时=" + json);
        AbRequestParams params = new AbRequestParams();
        params.put("json", json);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxjd_addxm, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("cjn", "XM是否成功===" + s.toString());
                dataxm();
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
                Log.i("cjn", "请求失败" + s.toString());
            }
        });
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

    public void setList_XM(List<BSD_WeiXiuJieDan_XM_Entity> list_XM) {
        this.list_XM = list_XM;
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
}
