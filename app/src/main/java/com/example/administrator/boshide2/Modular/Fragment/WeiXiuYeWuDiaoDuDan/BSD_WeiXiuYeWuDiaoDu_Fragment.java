package com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.alibaba.fastjson.JSON;
import com.example.administrator.boshide2.Conts;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Activity.MainActivity;
import com.example.administrator.boshide2.Modular.Fragment.BaseFragment;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.dialogFragment.BSD_LiShiWeiXiuJianYi_DialogFragment;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.dialogFragment.BSD_LishiWeiXiu_DialogFragment;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.dialogFragment.BSD_MeiRongKuaiXiu_cheliangxinxi_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_CL_Entity;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_Entity;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_XM_Entity;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.Adapter.BSD_wxywdd_dap;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.dialog.PaiGongDialog;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.fagmt.BSD_ZCDUXQ_CL_POP;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.fagmt.BSD_ZCDUXQ_XM_POP;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.fagmt.BSD_wxywdd_wxcl;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.fagmt.BSD_wxywdd_wxxm;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.Pop_Entity.BSD_wxyy_cl_pop_entity;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.Pop_Entity.BSD_wxyy_xm_pop_entiy;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.UpdateItemInfoDialog;
import com.example.administrator.boshide2.Modular.View.diaog.QueRen;
import com.example.administrator.boshide2.Modular.View.diaog.Queding_Quxiao;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.QuanQuan.WeiboDialogUtils;
import com.example.administrator.boshide2.Tools.Show;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @维修业务调度 Created by Administrator on 2017-4-13.
 */
public class BSD_WeiXiuYeWuDiaoDu_Fragment extends BaseFragment implements View.OnClickListener {
    private static final String PARAM_KEY = "param_key";
    private LinearLayout bsd_lsbj_fanhui;
    private TextView tv_wxxmAdd;
    private TextView tv_wxllAdd;
    private ListView bsd_wxywdd_you_lv;
    private BSD_wxywdd_dap adapter;
    private List<HashMap<String, String>> data = new ArrayList<>();
    private TextView bsd_ywwwdd_cp;
    private TextView bsd_ywwwdd_pinpai;
    private TextView bsdywwwdd_chexi;
    private TextView bsd_ywwwdd_chezu;
    private TextView bsd_ywwwdd_chexing;
    private TextView bsd_ywwwdd_vin;
    private TextView bsd_ywwwdd_user;
    private TextView bsd_ywwwdd_fuwuguwen;
    private TextView bsd_ywwwdd_dengjishijian;
    private TextView bsd_ywwwdd_dianhua;
    private List<BSD_WeiXiuJieDan_Entity> list = new ArrayList<BSD_WeiXiuJieDan_Entity>();
    private Dialog mWeiboDialog;
    private BSD_wxywdd_wxcl BSD_wxcl;
    private BSD_wxywdd_wxxm BSD_wxxm;
    private Queding_Quxiao queding_quxiao;
    private List<Map<String, String>> listPGrenyuan = new ArrayList<Map<String, String>>();
    private URLS url;
    private TextView bsd_zadd_wg;
    private QueRen queRen;
    private TextView tv_stopwx;
    private  TextView  bsd_wxywdd_clxx;
    private TextView bsd_wxywdd_lswxjy;
    private TextView bsd_wxywdd_lswx;
    private TextView title;
    private TextView footerText;
    private String param;
    private BSD_WeiXiuJieDan_Entity billEntiy;
    private TextView tv_paigongAll;
    private int currentType = 0;
    private ImageView iv_wxllAdd;
    private ImageView iv_wxxmAdd;
    private BSD_ZCDUXQ_XM_POP bsd_zcduxq_xm_pop;
    private RelativeLayout beijing;
    private List<BSD_WeiXiuJieDan_XM_Entity> list_XM;
    private List<BSD_WeiXiuJieDan_CL_Entity> list_CL;
    private BSD_ZCDUXQ_CL_POP bsd_zcduxq_cl_pop;
    private double cl_zj;
    private double xm_zj;
    private double zong_zj;
    private UpdateItemInfoDialog updateItemInfoDialog;
    private TextView billNo;
    private TextView tv_gsfl;
    private TextView tv_totalJe;

    public static BSD_WeiXiuYeWuDiaoDu_Fragment newInstance(String params) {
        BSD_WeiXiuYeWuDiaoDu_Fragment fragment = new BSD_WeiXiuYeWuDiaoDu_Fragment();
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
        return R.layout.bsd_wxywdd;
    }

    @Override
    public void initView() {
        // 返回
        bsd_lsbj_fanhui = (LinearLayout) view.findViewById(R.id.bsd_lsbj_fanhui);
        bsd_lsbj_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).upBSD_ZCDD_page();
            }
        });
        // 终止维修
        tv_stopwx = (TextView) view.findViewById(R.id.tv_stopwx);
        tv_stopwx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queding_quxiao = new Queding_Quxiao(getActivity(), "确认要终止维修吗？");
                queding_quxiao.show();
                queding_quxiao.setOnResultClickListener(new Queding_Quxiao.OnResultClickListener() {
                    @Override
                    public void onConfirm() {
                        queding_quxiao.dismiss();
                        stopWX();
                    }

                    @Override
                    public void onCancel() {
                        queding_quxiao.dismiss();
                    }
                });
            }
        });
        // 完工
        bsd_zadd_wg = (TextView) view.findViewById(R.id.bsd_zadd_wg);
        bsd_zadd_wg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queding_quxiao = new Queding_Quxiao(getActivity(), "确认完工吗？");
                queding_quxiao.show();
                queding_quxiao.setOnResultClickListener(new Queding_Quxiao.OnResultClickListener() {
                    @Override
                    public void onConfirm() {
                        queding_quxiao.dismiss();
                        completeWX();
                    }

                    @Override
                    public void onCancel() {
                        queding_quxiao.dismiss();
                    }
                });
            }
        });
        //车辆信息、历史维修、历史维修建议
        bsd_wxywdd_clxx = (TextView) view.findViewById(R.id.bsd_wxywdd_clxx);
        bsd_wxywdd_clxx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BSD_MeiRongKuaiXiu_cheliangxinxi_Fragment.newInstance(billEntiy.getChe_no(), Conts.BILLTYPE_WXDD, billEntiy.getWork_no())
                        .show(getFragmentManager(), "dialog_fragment");

            }
        });
        bsd_wxywdd_lswx= (TextView) view.findViewById(R.id.bsd_wxywdd_lswx);
        bsd_wxywdd_lswx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BSD_LishiWeiXiu_DialogFragment.newInstance(billEntiy.getChe_no()).show(getFragmentManager(),"wxywdd_lswx");
            }
        });
        bsd_wxywdd_lswxjy= (TextView) view.findViewById(R.id.bsd_wxywdd_lswxjy);
        bsd_wxywdd_lswxjy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BSD_LiShiWeiXiuJianYi_DialogFragment().show(getFragmentManager(),"mrkx_lswxjy");
            }
        });
        bsd_ywwwdd_cp = (TextView) view.findViewById(R.id.bsd_ywwwdd_cp);
        bsd_ywwwdd_pinpai = (TextView) view.findViewById(R.id.bsd_ywwwdd_pinpai);
        bsdywwwdd_chexi = (TextView) view.findViewById(R.id.bsdywwwdd_chexi);
        bsd_ywwwdd_chezu = (TextView) view.findViewById(R.id.bsd_ywwwdd_chezu);
        bsd_ywwwdd_chexing = (TextView) view.findViewById(R.id.bsd_ywwwdd_chexing);
        bsd_ywwwdd_vin = (TextView) view.findViewById(R.id.bsd_ywwwdd_vin);
        bsd_ywwwdd_user = (TextView) view.findViewById(R.id.bsd_ywwwdd_user);
        bsd_ywwwdd_fuwuguwen = (TextView) view.findViewById(R.id.bsd_ywwwdd_fuwuguwen);
        bsd_ywwwdd_dengjishijian = (TextView) view.findViewById(R.id.bsd_ywwwdd_dengjishijian);
        bsd_ywwwdd_dianhua = (TextView) view.findViewById(R.id.bsd_ywwwdd_dianhua);
        //获取listView
        bsd_wxywdd_you_lv = (ListView) view.findViewById(R.id.bsd_wxywdd_you_lv);
        adapter = new BSD_wxywdd_dap(getActivity(), listPGrenyuan);
        adapter.setOnOperateItemListener(new BSD_wxywdd_dap.OnOperateItemListener() {
            @Override
            public void onDelete(int reco_no, int position) {
                deletePaigongInfo(reco_no, position);
            }

            @Override
            public void onUpdateGS(int position) {
                updatePaigongGS(position);
            }

            @Override
            public void onUpdateJE(int position) {
                updatePaigongJE(position);
            }
        });
        bsd_wxywdd_you_lv.setAdapter(adapter);
        tv_wxxmAdd = (TextView) view.findViewById(R.id.tv_wxxm_add);
        tv_wxllAdd = (TextView) view.findViewById(R.id.tv_wxll_add);
        tv_wxxmAdd.setOnClickListener(this);
        tv_wxllAdd.setOnClickListener(this);
        iv_wxxmAdd = (ImageView) view.findViewById(R.id.iv_wxxm_add);
        iv_wxllAdd = (ImageView) view.findViewById(R.id.iv_wxll_add);
        iv_wxxmAdd.setOnClickListener(this);
        iv_wxllAdd.setOnClickListener(this);
        title = (TextView) view.findViewById(R.id.tv_title);
        billNo = (TextView) view.findViewById(R.id.tv_billNo);
        footerText = (TextView) view.findViewById(R.id.tv_footertext);
        tv_paigongAll = (TextView) view.findViewById(R.id.tv_paigong_all);
        tv_paigongAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list_XM = BSD_wxxm.getList_XM();
                if (list_XM.size() > 0) {
                    PaiGongDialog paiGongDialog = new PaiGongDialog(getActivity(), PaiGongDialog.PAIGONG_ALL);
                    paiGongDialog.setWorkNo(billEntiy.getWork_no());
                    paiGongDialog.setOnPaiGongListener(new PaiGongDialog.OnPaiGongListener() {
                        @Override
                        public void onSuccess() {
                            int currentPosition = BSD_wxxm.getCurrentPosition();
                            getPaiGongInfo(billEntiy.getWork_no(), list_XM.get(currentPosition).getWxxm_no());
                        }
                    });
                    paiGongDialog.show();
                } else {
                    Show.showTime(getActivity(), "没有维修项目，无法进行派工");
                }
            }
        });
        tv_gsfl = (TextView) view.findViewById(R.id.bsd_ksbj_tv_gsfl);
        tv_totalJe = (TextView) view.findViewById(R.id.tv_zong_money);
        bsd_ywwwdd_cp.setFocusable(true);
        bsd_ywwwdd_cp.setFocusableInTouchMode(true);
        bsd_ywwwdd_cp.requestFocus();
    }

    private void updatePaigongJE(final int position) {
        final int reco_no = Integer.parseInt(listPGrenyuan.get(position).get("reco_no"));
        final double paig_khgs = Double.parseDouble(listPGrenyuan.get(position).get("paig_khgs"));
        final double paig_khje = Double.parseDouble(listPGrenyuan.get(position).get("paig_khje"));
        String reny_mc = listPGrenyuan.get(position).get("reny_mc");
        updateItemInfoDialog = new UpdateItemInfoDialog(getActivity(), UpdateItemInfoDialog.CHANGE_PGJE, paig_khje, reny_mc);
        updateItemInfoDialog.show();
        updateItemInfoDialog.setToopromtOnClickListener(new UpdateItemInfoDialog.ToopromtOnClickListener() {
            @Override
            public void onYesClick(double gongshif) {
                double jeNow = 0.0;
                for (int i = 0; i < listPGrenyuan.size(); i++) {
                    if (i != position) {
                        jeNow = jeNow + Double.parseDouble(listPGrenyuan.get(i).get("paig_khje"));
                    } else {
                        jeNow = jeNow + gongshif;
                    }
                }
                double jeAll = 0.0;
                list_XM = BSD_wxxm.getList_XM();
                for (BSD_WeiXiuJieDan_XM_Entity entity : list_XM) {
                    jeAll = jeAll + entity.getWxxm_je();
                }
                BigDecimal gsAll = BigDecimal.valueOf(jeAll);
                BigDecimal gsNow = BigDecimal.valueOf(jeNow);
                Double gsRemain = gsAll.subtract(gsNow).doubleValue();
                if (gsRemain >= 0) {
                    updatePaigongInfoToDB(reco_no, gongshif, paig_khgs, position);
                } else {
                    Toast.makeText(getActivity(), "派工金额不能大于总金额", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updatePaigongGS(final int position) {
        final int reco_no = Integer.parseInt(listPGrenyuan.get(position).get("reco_no"));
        final double paig_khgs = Double.parseDouble(listPGrenyuan.get(position).get("paig_khgs"));
        final double paig_khje = Double.parseDouble(listPGrenyuan.get(position).get("paig_khje"));
        String reny_mc = listPGrenyuan.get(position).get("reny_mc");
        updateItemInfoDialog = new UpdateItemInfoDialog(getActivity(), UpdateItemInfoDialog.CHANGE_PGGS, paig_khgs, reny_mc);
        updateItemInfoDialog.show();
        updateItemInfoDialog.setToopromtOnClickListener(new UpdateItemInfoDialog.ToopromtOnClickListener() {
            @Override
            public void onYesClick(double gongshif) {
                double gongshiNow = 0.0;
                for (int i = 0; i < listPGrenyuan.size(); i++) {
                    if (i != position) {
                        gongshiNow = gongshiNow + Double.parseDouble(listPGrenyuan.get(i).get("paig_khgs"));
                    } else {
                        gongshiNow = gongshiNow + gongshif;
                    }
                }
                double gongshiAll = 0.0;
                list_XM = BSD_wxxm.getList_XM();
                for (BSD_WeiXiuJieDan_XM_Entity entity : list_XM) {
                    gongshiAll = gongshiAll + entity.getWxxm_khgs();
                }
                BigDecimal gsAll = BigDecimal.valueOf(gongshiAll);
                BigDecimal gsNow = BigDecimal.valueOf(gongshiNow);
                Double gsRemain = gsAll.subtract(gsNow).doubleValue();
                if (gsRemain >= 0) {
                    updatePaigongInfoToDB(reco_no, paig_khje, gongshif, position);
                } else {
                    Toast.makeText(getActivity(), "派工工时不能大于总工时", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updatePaigongInfoToDB(int reco_no, final double je, final double gs, final int position) {
        AbRequestParams params = new AbRequestParams();
        params.put("id", reco_no);
        params.put("je", je + "");
        params.put("gs", gs + "");
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_GsDj_xiugai, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                listPGrenyuan.get(position).put("paig_khgs", gs + "");
                listPGrenyuan.get(position).put("paig_khje", je + "");
                adapter.notifyDataSetChanged();
                updateItemInfoDialog.dismiss();
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                Toast.makeText(getActivity(), "修改失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deletePaigongInfo(int reco_no, final int position) {
        AbRequestParams params = new AbRequestParams();
        params.put("xxNo", reco_no);//id
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_paigongdelPgxx, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                listPGrenyuan.remove(position);
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
                Toast.makeText(getContext(), "派工人员删除失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void initData() {
        url = new URLS();
        title.setText("维修调度");
        footerText.setText("公司名称 :   " + MyApplication.shared.getString("GongSiMc", "") +
                "                  公司电话 :   " + MyApplication.shared.getString("danw_dh", ""));
        getBillInfoFromParam();
        updateBillInfoUI();
        change_XM();
    }

    private void getBillInfoFromParam() {
        billEntiy = JSON.parseObject(param, BSD_WeiXiuJieDan_Entity.class);
        xm_zj = billEntiy.getXche_rgf();
        cl_zj = billEntiy.getXche_clf();
        zong_zj = billEntiy.getXche_hjje();
    }


    private void updateBillInfoUI() {
        billNo.setText(billEntiy.getWork_no());
        Conts.wxxm_no = billEntiy.getWork_no();
        bsd_ywwwdd_cp.setText(billEntiy.getChe_no());
        String cheCx = billEntiy.getChe_cx();
        String[] cheCxs = cheCx.split("\\|");
        if (cheCxs.length >= 4) {
            bsd_ywwwdd_pinpai.setText(cheCxs[0]);
            bsdywwwdd_chexi.setText(cheCxs[1]);
            bsd_ywwwdd_chezu.setText(cheCxs[2]);
            bsd_ywwwdd_chexing.setText(cheCxs[3]);
        }
        bsd_ywwwdd_vin.setText(billEntiy.getChe_vin());
        bsd_ywwwdd_user.setText(billEntiy.getKehu_mc());
        bsd_ywwwdd_fuwuguwen.setText(billEntiy.getXche_cz());
        bsd_ywwwdd_dengjishijian.setText(billEntiy.getXche_yjwgrq());
        bsd_ywwwdd_dianhua.setText(billEntiy.getKehu_dh());
        tv_gsfl.setText(billEntiy.getXche_sfbz());
        tv_totalJe.setText(billEntiy.getXche_hjje() + "");
    }

    /**
     * 打印
     */
    public void print() {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "打印中...");
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", billEntiy.getWork_no());
        params.put("caozuoyuan", MyApplication.shared.getString("bsd_user_name", ""));
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_DY, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String data) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(data);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        queRen = new QueRen(getActivity(), "打印成功");
                        queRen.show();
                        queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                            @Override
                            public void onYesClick() {
                                queRen.dismiss();
                                ((MainActivity) getActivity()).upBSD_ZCDD_page();
                            }
                        });
                    }
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
                Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });

    }

    public void completeWX() {
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", billEntiy.getWork_no());
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_WG, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("message").toString().equals("查询失败")) {
                        queRen = new QueRen(getActivity(), jsonObject.getString("data"));
                        queRen.show();
                        queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                            @Override
                            public void onYesClick() {
                                queRen.dismiss();
                            }
                        });
                    } else {
                        //发送微信
                        weixin();
                        queding_quxiao = new Queding_Quxiao(getActivity(), "已完工！是否打印");
                        queding_quxiao.show();
                        queding_quxiao.setOnResultClickListener(new Queding_Quxiao.OnResultClickListener() {
                            @Override
                            public void onConfirm() {
                                print();
                                queding_quxiao.dismiss();
                            }

                            @Override
                            public void onCancel() {
                                queding_quxiao.dismiss();
                                ((MainActivity) getActivity()).upBSD_ZCDD_page();
                            }
                        });
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
                Log.i("cjn", "完工失败==" + s);
            }
        });

    }

    /**
     * 终止派工
     */
    public void stopWX() {
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", billEntiy.getWork_no());
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_ZZ, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                queRen = new QueRen(getActivity(), "终止成功");
                queRen.show();
                queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick() {
                        queRen.dismiss();
                        ((MainActivity) getActivity()).upBSD_ZCDD_page();
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
                Toast.makeText(getContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 完工时发微信
     */
    private void weixin() {
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", Conts.work_no);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wg_weixin, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {

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
     * 切换碎片的点击方法
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_wxxm_add:   //维修项目
                if (currentType == 1) {
                    change_XM();
                    currentType = 0;
                }
                break;
            case R.id.tv_wxll_add:    //维修材料
                if (currentType == 0) {
                    change_CL();
                    currentType = 1;
                }
                break;
            case R.id.iv_wxxm_add: // 添加维修项目
                showWxxm();
                break;
            case R.id.iv_wxll_add: // 添加维修用料
                showWxcl();
                break;
        }

    }

    private void showWxcl() {
        bsd_zcduxq_cl_pop = new BSD_ZCDUXQ_CL_POP(getActivity());
        bsd_zcduxq_cl_pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        beijing = (RelativeLayout) getActivity().findViewById(R.id.beijing);
        list_CL = BSD_wxcl.getList_CL();
        List<BSD_wxyy_cl_pop_entity> tempLists = new ArrayList<>();
        if (list_CL != null) {
            for (BSD_WeiXiuJieDan_CL_Entity item : list_CL) {
                BSD_wxyy_cl_pop_entity tempItem = new BSD_wxyy_cl_pop_entity();
                tempItem.setPeij_no(item.getPeij_no());
                tempItem.setPeij_sl(item.getPeij_sl());
                tempItem.setPeij_ydj(item.getPeij_ydj());
                tempLists.add(tempItem);
            }
        }
        bsd_zcduxq_cl_pop.setTempLists(tempLists);
        bsd_zcduxq_cl_pop.setCheNo(billEntiy.getChe_no());
        bsd_zcduxq_cl_pop.setKehuNo(billEntiy.getKehu_no());
        bsd_zcduxq_cl_pop.gb(new BSD_ZCDUXQ_CL_POP.Guanbi() {
            @Override
            public void onGuanBi(List<BSD_wxyy_cl_pop_entity> tempList) {
                list_CL = BSD_wxcl.getList_CL();
                List<BSD_WeiXiuJieDan_CL_Entity> needAddList = new ArrayList<>();
                // 循环返回的数据，找不需要新添加的
                for (BSD_wxyy_cl_pop_entity item : tempList) {
                    boolean hasAdd = false;
                    double addPeijSl = item.getPeij_sl();
                    for (BSD_WeiXiuJieDan_CL_Entity listCLItem : list_CL) {
                        if (listCLItem.getPeij_no().equals(item.getPeij_no())) {
                            if (listCLItem.getPeij_sl() == item.getPeij_sl()) {
                                // 如果相等，则表示存在，直接退出，否则继续循环
                                hasAdd = true;
                            } else {
                                addPeijSl = item.getPeij_sl() - listCLItem.getPeij_sl();
                            }
                            break;
                        }
                    }
                    // 如果没有找到，则是需要添加的集合
                    if (!hasAdd) {
                        BSD_WeiXiuJieDan_CL_Entity wxclItem = new BSD_WeiXiuJieDan_CL_Entity();
                        wxclItem.setWork_no(billEntiy.getWork_no());
                        wxclItem.setPeij_no(item.getPeij_no());
                        wxclItem.setPeij_mc(item.getPeij_mc());
                        wxclItem.setPeij_sl(addPeijSl);
                        wxclItem.setPeij_ydj(item.getPeij_ydj());
                        wxclItem.setPeij_dj(item.getPeij_ydj() * Conts.MRKX_CL_ZK);
                        wxclItem.setPeij_zk(Conts.MRKX_CL_ZK);
                        wxclItem.setPeij_je(addPeijSl * item.getPeij_ydj() * Conts.MRKX_CL_ZK);
                        wxclItem.setPeij_dw(item.getPeij_dw());
                        wxclItem.setPeij_th(item.getPeij_th());
                        wxclItem.setPeij_zt("正常");
                        needAddList.add(wxclItem);
                    }
                }
                // 请求接口，把数据添加到数据库中
                if (needAddList.size() > 0) {
                    saveNewWxclToDB(needAddList);
                }
            }
        });
        bsd_zcduxq_cl_pop.showAtLocation(beijing, Gravity.TOP, 0, 0);
    }

    private void saveNewWxclToDB(final List<BSD_WeiXiuJieDan_CL_Entity> needAddList) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "保存中...");
        AbRequestParams params = new AbRequestParams();
        Object json = JSON.toJSON(needAddList);
        params.put("addLists", json.toString());
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxjd_addnewcl, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                // 保存成功
                if (data.equals("success")) {
                    boolean isAdd = true;
                    for (BSD_WeiXiuJieDan_CL_Entity item : needAddList) {
                        for (BSD_WeiXiuJieDan_CL_Entity listClItem : list_CL) {
                            if (listClItem.getPeij_no().equals(item.getPeij_no())) {
                                isAdd = false;
                                listClItem.setPeij_sl(listClItem.getPeij_sl() + item.getPeij_sl());
                                listClItem.setPeij_je(listClItem.getPeij_je() + item.getPeij_je());
                                break;
                            }
                        }
                        if (isAdd) {
                            list_CL.add(item);
                        }
                    }
                    // 循环遍历一下集合，删除数量为0的
                    Iterator<BSD_WeiXiuJieDan_CL_Entity> iterator = list_CL.iterator();
                    while (iterator.hasNext()) {
                        BSD_WeiXiuJieDan_CL_Entity next = iterator.next();
                        if (next.getPeij_sl() == 0) {
                            iterator.remove();
                        }
                    }
                    BSD_wxcl.refreashData();
                }
                bsd_zcduxq_cl_pop.dismiss();
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

    private void change_CL() {
        tv_wxllAdd.setTextColor(getResources().getColor(R.color.bsd_xz_yes));
        tv_wxxmAdd.setTextColor(getResources().getColor(R.color.transparent_background));
        iv_wxllAdd.setVisibility(View.VISIBLE);
        iv_wxxmAdd.setVisibility(View.INVISIBLE);
        tv_paigongAll.setVisibility(View.INVISIBLE);
        FragmentTransaction transaction = getActivity().
                getSupportFragmentManager().
                beginTransaction();
        if (BSD_wxcl == null) {
            BSD_wxcl = BSD_wxywdd_wxcl.newInstance(billEntiy.getWork_no());
            BSD_wxcl.setCl_zj(new BSD_wxywdd_wxcl.CL_ZJ() {
                @Override
                public void onYesClick(double clzj) {
                    cl_zj = clzj;
                    zong_zj = xm_zj + cl_zj;
                    double v = (Math.round(zong_zj * 100) / 100.0);
                    tv_totalJe.setText("" + v);
                    billEntiy.setXche_hjje(zong_zj);
                }
            });
            transaction.hide(BSD_wxxm);
            transaction.add(R.id.bsd_clxq_lswx, BSD_wxcl).commit();
        } else {
            if (BSD_wxxm != null) {
                transaction.hide(BSD_wxxm);
            }
            transaction.show(BSD_wxcl);
            transaction.commit();
        }
    }

    private void showWxxm() {
        bsd_zcduxq_xm_pop = new BSD_ZCDUXQ_XM_POP(getActivity());
        bsd_zcduxq_xm_pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        beijing = (RelativeLayout) getActivity().findViewById(R.id.beijing);
        // 将当前已经添加的维修项目，放到popwindow的临时表中，表示这些项目已经添加
        list_XM = BSD_wxxm.getList_XM();
        List<BSD_wxyy_xm_pop_entiy> tempLists = new ArrayList<>();
        if (list_XM != null) {
            for (BSD_WeiXiuJieDan_XM_Entity item : list_XM) {
                BSD_wxyy_xm_pop_entiy tempItem = new BSD_wxyy_xm_pop_entiy();
                tempItem.setWxxm_no(item.getWxxm_no());
                tempLists.add(tempItem);
            }
        }
        bsd_zcduxq_xm_pop.setTempLists(tempLists);
        bsd_zcduxq_xm_pop.setCheCx(billEntiy.getChe_cx());
        bsd_zcduxq_xm_pop.setCheNo(billEntiy.getChe_no());
        bsd_zcduxq_xm_pop.setCheFl(billEntiy.getXche_sfbz());
        bsd_zcduxq_xm_pop.setWorkNo(billEntiy.getWork_no());
        // 设置点击返回的回调
        bsd_zcduxq_xm_pop.setOnGuanbiListener(new BSD_ZCDUXQ_XM_POP.OnGuanbiListener() {
            @Override
            public void onGuanBi(List<BSD_wxyy_xm_pop_entiy> tempList) {
                // 获取到当前work_ll_gz中显示的维修项目
                list_XM = BSD_wxxm.getList_XM();
                List<BSD_WeiXiuJieDan_XM_Entity> needAddList = new ArrayList<>();
                // 循环返回的数据，找不需要新添加的
                for (BSD_wxyy_xm_pop_entiy item : tempList) {
                    boolean hasAdd = false;
                    for (BSD_WeiXiuJieDan_XM_Entity listXMItem : list_XM) {
                        if (listXMItem.getWxxm_no().equals(item.getWxxm_no())) {
                            // 如果相等，则表示存在，直接退出，否则继续循环
                            hasAdd = true;
                            break;
                        }
                    }
                    // 如果没有找到，则是需要添加的集合
                    if (!hasAdd) {
                        BSD_WeiXiuJieDan_XM_Entity wxxmItem = new BSD_WeiXiuJieDan_XM_Entity();
                        wxxmItem.setWork_no(billEntiy.getWork_no());
                        wxxmItem.setWxxm_no(item.getWxxm_no());
                        wxxmItem.setWxxm_mc(item.getWxxm_mc());
                        wxxmItem.setWxxm_yje(item.getWxxm_zddj());
                        wxxmItem.setWxxm_gs(item.getWxxm_gs());
                        wxxmItem.setWxxm_dj(item.getWxxm_dj());
                        wxxmItem.setWxxm_cb(item.getWxxm_cb());
                        wxxmItem.setWxxm_zk(Conts.MRKX_XM_ZK);
                        wxxmItem.setWxxm_je(Conts.MRKX_XM_ZK * item.getWxxm_zddj());
                        wxxmItem.setWxxm_zt("正常");
                        wxxmItem.setWxxm_tpye("正常");
                        needAddList.add(wxxmItem);
                    }
                }
                // 请求接口，把数据添加到数据库中
                if (needAddList.size() > 0) {
                    saveNewWxxmToDB(needAddList);
                }
            }
        });
        // 用下面这个显示方式，上面的显示方式会出现：键盘关闭时popwindow显示后面的activity，而不能恢复到原来的样子
        bsd_zcduxq_xm_pop.showAtLocation(beijing, Gravity.TOP, 0, 0);
    }

    private void saveNewWxxmToDB(final List<BSD_WeiXiuJieDan_XM_Entity> needAddList) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "保存中...");
        AbRequestParams params = new AbRequestParams();
        Object json = JSON.toJSON(needAddList);
        params.put("addLists", json.toString());
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxjd_addnewxm, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                // 保存成功
                if (data.equals("success")) {
                    list_XM.addAll(needAddList);
                    BSD_wxxm.refreashData();
                }
                bsd_zcduxq_xm_pop.dismiss();
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

    private void change_XM() {
        tv_wxxmAdd.setTextColor(getResources().getColor(R.color.bsd_xz_yes));
        tv_wxllAdd.setTextColor(getResources().getColor(R.color.transparent_background));
        iv_wxxmAdd.setVisibility(View.VISIBLE);
        iv_wxllAdd.setVisibility(View.INVISIBLE);
        tv_paigongAll.setVisibility(View.VISIBLE);
        FragmentTransaction transaction = getActivity().
                getSupportFragmentManager().
                beginTransaction();
        if (BSD_wxxm == null) {
            BSD_wxxm = BSD_wxywdd_wxxm.newInstance(billEntiy.getWork_no());
            BSD_wxxm.setOnRefreashPaiGongListener(new BSD_wxywdd_wxxm.OnRefreashPaiGongListener() {
                @Override
                public void onRefreash(String workNo, String wxxmNo) {
                    getPaiGongInfo(workNo, wxxmNo);
                }

                @Override
                public void onWxxmRequestSuccess(String workNo, String wxxmNo) {
                    getPaiGongInfo(workNo, wxxmNo);
                }

            });
            BSD_wxxm.setXm_zj(new BSD_wxywdd_wxxm.XM_ZJ() {
                @Override
                public void onYesClick(double xmzj) {
                    xm_zj = xmzj;
                    zong_zj = xm_zj + cl_zj;
                    double v = (Math.round(zong_zj * 100) / 100.0);
                    tv_totalJe.setText("" + v);
                    billEntiy.setXche_hjje(zong_zj);
                }
            });
            transaction.add(R.id.bsd_clxq_lswx, BSD_wxxm);
            transaction.show(BSD_wxxm);
            transaction.commit();
        } else {
            if (BSD_wxcl != null) {
                transaction.hide(BSD_wxcl);
            }
            transaction.show(BSD_wxxm);
            transaction.commit();
        }
    }

    private void getPaiGongInfo(String workNo, String wxxmNo) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        listPGrenyuan.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", workNo);
        params.put("wxxm_no", wxxmNo);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_PaiGong_XiangXi, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int a, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("status").toString().equals("1")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject item = jsonarray.getJSONObject(i);
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("reco_no", item.getString("reco_no"));
                            map.put("work_no", item.getString("work_no"));
                            map.put("wxxm_no", item.getString("wxxm_no"));
                            map.put("reny_no", item.getString("reny_no"));
                            map.put("reny_mc", item.getString("reny_mc"));
                            map.put("wxry_bm", item.getString("wxry_bm"));
                            map.put("paig_khgs", item.getString("paig_khgs"));
                            map.put("paig_khje", item.getString("paig_khje"));
                            map.put("paig_pgsj", item.getString("paig_pgsj"));
                            listPGrenyuan.add(map);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
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
                Show.showTime(getActivity(), "获取派工信息失败");
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });
    }

}
