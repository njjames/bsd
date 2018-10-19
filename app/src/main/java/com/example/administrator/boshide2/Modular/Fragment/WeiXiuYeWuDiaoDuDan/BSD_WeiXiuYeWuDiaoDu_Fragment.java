package com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.example.administrator.boshide2.Conts;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Activity.MainActivity;
import com.example.administrator.boshide2.Modular.Fragment.BaseFragment;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.Fagmt.BSD_mrkx_wxxm;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.dialogFragment.BSD_LiShiWeiXiuJianYi_DialogFragment;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.dialogFragment.BSD_LishiWeiXiu_DialogFragment;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.dialogFragment.BSD_MeiRongKuaiXiu_cheliangxinxi_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_Entity;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.Adapter.BSD_wxywdd_dap;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.fagmt.BSD_wxywdd_wxcl;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.fagmt.BSD_wxywdd_wxxm;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.BSD_XiuGaiGongShi;
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
import java.util.List;
import java.util.Map;

/**
 * @维修业务调度 Created by Administrator on 2017-4-13.
 */
public class BSD_WeiXiuYeWuDiaoDu_Fragment extends BaseFragment implements View.OnClickListener {
    private static final String PARAM_KEY = "param_key";
    LinearLayout bsd_lsbj_fanhui;
    private TextView tv_wxxmAdd;
    private TextView tv_wxllAdd;
    private Fragment[] fragments;
    private TextView[] arr_tv;// 图标的数组
    private int[] arr_id_box = {R.id.tv_wxxm_add, R.id.tv_wxll_add};
    public Fragment BSD_wxcl;
    public ListView bsd_wxywdd_you_lv;
    BSD_wxywdd_dap adapter;
    List<HashMap<String, String>> data = new ArrayList<>();
    TextView bsd_ywwwdd_dh;
    TextView bsd_ywwwdd_cp;
    TextView bsd_ywwwdd_pinpai;
    TextView bsdywwwdd_chexi;
    TextView bsd_ywwwdd_chezu;
    TextView bsd_ywwwdd_chexing;
    TextView bsd_ywwwdd_vin;
    TextView bsd_ywwwdd_user;
    TextView bsd_ywwwdd_fuwuguwen;
    TextView bsd_ywwwdd_dengjishijian;
    TextView bsd_ywwwdd_dianhua;
    private List<BSD_WeiXiuJieDan_Entity> list = new ArrayList<BSD_WeiXiuJieDan_Entity>();
    private Dialog mWeiboDialog;
    private BSD_wxywdd_wxxm BSD_wxxm;
    Queding_Quxiao queding_quxiao;
    //修改工时弹框
    BSD_XiuGaiGongShi bsd_xiuGaiGongShi;
    List<Map<String, String>> listPGrenyuan = new ArrayList<Map<String, String>>();
    URLS url;
    TextView bsd_zadd_wg;
    QueRen queRen;
    double renyuangongshi;
    TextView tv_stopwx;
    TiaoZhuan tiaoZhuan;
    //车辆信息、历史维修、历史维修建议
    private  TextView  bsd_wxywdd_clxx,bsd_wxywdd_lswxjy,bsd_wxywdd_lswx;

    private MainActivity mainActivity;
    private TextView title;
    private TextView footerText;
    private String param;
    private BSD_WeiXiuJieDan_Entity billEntiy;
    private TextView tv_paigongAll;
    private int currentType = 0;
    private ImageView iv_wxllAdd;
    private ImageView iv_wxxmAdd;

    public void setTiaoZhuan(TiaoZhuan tiaoZhuan) {
        this.tiaoZhuan = tiaoZhuan;
    }

    public interface TiaoZhuan {
        void onYesClick();
    }

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
                ((MainActivity) getActivity()).upzcdd(view);
            }
        });
        tv_stopwx = (TextView) view.findViewById(R.id.tv_stopwx);
        tv_stopwx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zz();
            }
        });

        bsd_zadd_wg = (TextView) view.findViewById(R.id.bsd_zadd_wg);
        bsd_zadd_wg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                queding_quxiao = new Queding_Quxiao(getActivity(), "确认是否完工");
                queding_quxiao.show();
                queding_quxiao.setOnResultClickListener(new Queding_Quxiao.OnResultClickListener() {
                    @Override
                    public void onConfirm() {
                        queding_quxiao.dismiss();
                        WG(Conts.work_no);
                        //发送微信
                        weixin();
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
                Toast.makeText(getActivity(),"车辆信息",Toast.LENGTH_LONG).show();
                //跳转到编辑车辆、客户信息界面
                Conts.danju_type="wxywdd";
                //跳转到编辑车辆、客户信息对话框
                new BSD_MeiRongKuaiXiu_cheliangxinxi_Fragment()
                        .show(getFragmentManager(), "dialog_fragment");

            }
        });
        bsd_wxywdd_lswx= (TextView) view.findViewById(R.id.bsd_wxywdd_lswx);
        bsd_wxywdd_lswx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Conts.danju_type="wxywdd";
                new BSD_LishiWeiXiu_DialogFragment().
                        show(getFragmentManager(),"mrkx_lswx");

            }
        });
        bsd_wxywdd_lswxjy= (TextView) view.findViewById(R.id.bsd_wxywdd_lswxjy);
        bsd_wxywdd_lswxjy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BSD_LiShiWeiXiuJianYi_DialogFragment().
                        show(getFragmentManager(),"mrkx_lswxjy");
            }
        });

        bsd_ywwwdd_dh = (TextView) view.findViewById(R.id.bsd_ywwwdd_dh);
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
        adapter.setRemove(new BSD_wxywdd_dap.Remo() {
            @Override
            public void onYesClick(String reco_no) {
                remodata(reco_no);

            }
        });
        adapter.setUp_gongshi(new BSD_wxywdd_dap.UP_gongshi() {
            @Override
            public void onYesClick(String gongshi, final int i) {
                double gs = Double.parseDouble(gongshi);
                bsd_xiuGaiGongShi = new BSD_XiuGaiGongShi(getActivity(), "修改工时", 0,gs, "","修改工时");
                bsd_xiuGaiGongShi.show();
                bsd_xiuGaiGongShi.setToopromtOnClickListener(new BSD_XiuGaiGongShi.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick(double gongshi) {

                        int a;
                        Double je;
                        Double gs;

                        a = Integer.parseInt(listPGrenyuan.get(i).get("reco_no"));
                        double aa = Double.parseDouble(listPGrenyuan.get(i).get("paig_khje"));
                        je =  aa;
                        gs =  gongshi;
                        renyuangongshi = 0.0;
                        BigDecimal  gsbg=new BigDecimal(renyuangongshi);
                        for (int j = 0; j < listPGrenyuan.size(); j++) {
                            Log.i("pggs", "onAdd: Other  ==="+listPGrenyuan.get(j).get("paig_khgs"));
                            //                            renyuangongshi = renyuangongshi + Double.parseDouble(listPGrenyuan.get(j).get("paig_khgs"));
                            Double  gg= Double.parseDouble(listPGrenyuan.get(j).get("paig_khgs"));
                            BigDecimal gsbd2 = new BigDecimal(Double.toString(gg));
                            gsbg=gsbg.add(gsbd2);
                            Log.i("pggs", "onAdd: Other  gsbd2==="+gsbd2);
                            Log.i("pggs", "onAdd: Other   gsbg  ==="+gsbg);
                            if (j == i) {
                                //                                renyuangongshi = renyuangongshi - Double.parseDouble(listPGrenyuan.get(i).get("paig_khgs"));
                                Double  ggg= Double.parseDouble(listPGrenyuan.get(i).get("paig_khgs"));
                                BigDecimal gsbd3 = new BigDecimal(Double.toString(ggg));
                                gsbg= gsbg.subtract(gsbd3);
                                Log.i("pggs", "onAdd: This  gsbd3==="+gsbd3);
                                Log.i("pggs", "onAdd: This   gsbg  ==="+gsbg);
                            }
                        }
                        renyuangongshi=gsbg.doubleValue();
                        Log.i("gsssss", "可以写入" + "查看总工时===" + Conts.wxxm_gs + "查看人员所有工时===" + renyuangongshi + "查看输入完工时" + gongshi);

                        BigDecimal   gsA = BigDecimal.valueOf(Conts.wxxm_gs);
                        BigDecimal   gsB= BigDecimal.valueOf(renyuangongshi);

                        Double  gsC= gsA.subtract(gsB).doubleValue();
                        Log.i("gsssss", "成功工时gsA="+gsA+"-----gsB="+gsB+"----gsC="+gsC);
                        if (gsC>= gongshi) {
                            xiugaigongshi(a, je, gs);
                            listPGrenyuan.get(i).put("paig_khgs", String.valueOf(gongshi));
                            adapter.notifyDataSetChanged();
                            bsd_xiuGaiGongShi.dismiss();
                            renyuangongshi = renyuangongshi + gongshi;
                        } else {
                            Log.i("gsssss", "不成功"+gsC );
                            Log.i("cjn", "总工时-集合所有工时小于输入工时不可以输入");
                            Toast.makeText(getActivity(), "派工工时不能大于总工时", Toast.LENGTH_SHORT).show();
                            bsd_xiuGaiGongShi.dismiss();
                        }

                    }
                });

            }
        });
        adapter.setUp_jiaqian(new BSD_wxywdd_dap.UP_jiaqian() {
            @Override
            public void onYesClick(String gongshi, final int i) {
                double gs = Double.parseDouble(gongshi);
                bsd_xiuGaiGongShi = new BSD_XiuGaiGongShi(getActivity(), "修改价钱",0, gs,"", "修改价钱");
                bsd_xiuGaiGongShi.show();
                bsd_xiuGaiGongShi.setToopromtOnClickListener(new BSD_XiuGaiGongShi.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick(double gongshi) {
                        int a;
                        Double je;
                        Double gs;
                        a = Integer.parseInt(listPGrenyuan.get(i).get("reco_no"));
                        double aa = Double.parseDouble(listPGrenyuan.get(i).get("paig_khgs"));
                        je = gongshi;
                        gs = aa;
                        renyuangongshi = 0.0;
                        for (int j = 0; j < listPGrenyuan.size(); j++) {
                            renyuangongshi = renyuangongshi + Double.parseDouble(listPGrenyuan.get(j).get("paig_khje"));
                            if (j == i) {
                                renyuangongshi = renyuangongshi - Double.parseDouble(listPGrenyuan.get(i).get("paig_khje"));
                            }
                        }

                        Log.i("cjn", "可以写入" + "查看总金额===" + Conts.wxxm_je + "查看人员所有工时===" + renyuangongshi + "查看输入完工时" + gongshi);
                        BigDecimal   gsA = BigDecimal.valueOf(Conts.wxxm_je);
                        BigDecimal   gsB= BigDecimal.valueOf(renyuangongshi);
                        Double  gsC= gsA.subtract(gsB).doubleValue();
                        if (gsC >= gongshi) {
                            Log.i("cjn", "成功工时"+gsC);
                            xiugaigongshi(a, je, gs);
                            listPGrenyuan.get(i).put("paig_khje", String.valueOf(gongshi));
                            adapter.notifyDataSetChanged();
                            bsd_xiuGaiGongShi.dismiss();
                            renyuangongshi = renyuangongshi + gongshi;
                        } else {
                            Log.i("cjn", "总工时-集合所有工时小于输入工时不可以输入");
                            Toast.makeText(getActivity(), "派工金额不能大于总金额", Toast.LENGTH_SHORT).show();
                            bsd_xiuGaiGongShi.dismiss();
                        }
                    }
                });
            }
        });
        bsd_wxywdd_you_lv.setAdapter(adapter);

        tv_wxxmAdd = (TextView) view.findViewById(R.id.tv_wxxm_add);
        tv_wxllAdd = (TextView) view.findViewById(R.id.tv_wxll_add);
        iv_wxxmAdd = (ImageView) view.findViewById(R.id.iv_wxxm_add);
        iv_wxllAdd = (ImageView) view.findViewById(R.id.iv_wxll_add);
        arr_tv = new TextView[2];
        arr_tv[0] = tv_wxxmAdd;
        arr_tv[1] = tv_wxllAdd;
        tv_wxxmAdd.setOnClickListener(this);
        tv_wxllAdd.setOnClickListener(this);
        title = (TextView) view.findViewById(R.id.tv_title);
        footerText = (TextView) view.findViewById(R.id.tv_footertext);
        tv_paigongAll = (TextView) view.findViewById(R.id.tv_paigong_all);
        tv_paigongAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                list_XM = BSD_wxxm.getList_XM();
//                if (list_XM.size() > 0) {
//                    PaiGongDialog paiGongDialog = new PaiGongDialog(getActivity(), PaiGongDialog.PAIGONG_ALL);
//                    paiGongDialog.setWorkNo(billEntiy.getWork_no());
//                    paiGongDialog.setOnPaiGongListener(new PaiGongDialog.OnPaiGongListener() {
//                        @Override
//                        public void onSuccess() {
//                            int currentPosition = BSD_wxxm.getCurrentPosition();
//                            getPaiGongInfo(billEntiy.getWork_no(), list_XM.get(currentPosition).getWxxm_no());
//                        }
//                    });
//                    paiGongDialog.show();
//                } else {
//                    Show.showTime(getActivity(), "没有维修项目，无法进行派工");
//                }
            }
        });
    }

    @Override
    public void initData() {
        url = new URLS();
        title.setText("维修调度");
        footerText.setText("公司名称 :   " + MyApplication.shared.getString("GongSiMc", "") +
                "                  公司电话 :   " + MyApplication.shared.getString("danw_dh", ""));
        getBillInfo();
//        tiaoZhuan.onYesClick();
//        if (Conts.wxjdtiaozhuan == 1) {
//            Log.i("cjn", "aaaaa");
//            tiaozhuanjiedan();
//            Conts.wxjdtiaozhuan = 0;
//        } else {
//            getBillInfo();
//            Log.i("cjn", "bbbbb");
//            Conts.wxjdtiaozhuan = 0;
//        }
        initFragment();
        checkHighLight(0);
    }

    private void updateBillInfoUI() {
        bsd_ywwwdd_dh.setText(billEntiy.getWork_no());
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
    }

    public void DY() {
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", Conts.work_no);
        params.put("caozuoyuan", MyApplication.shared.getString("bsd_user_name", ""));
        Log.i("cjn", "查看打印的参数+" + Conts.work_no + "===caozuoyuan====" + MyApplication.shared.getString("bsd_user_name", ""));
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_DY, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("cjn", "打印成功显示==" + s);
                queRen = new QueRen(getActivity(), "打印成功");
                queRen.show();
                queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick() {
                        queRen.dismiss();
                        ((MainActivity) getActivity()).upzcdd();
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
                Log.i("cjn", "打印失败显示==" + s);
            }
        });

    }

    public void WG(String wxid) {
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", wxid);
        Log.i("cjn", "查看完工的参数" + wxid);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_WG, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("cjn", "完工成功==" + s);
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
                        queding_quxiao = new Queding_Quxiao(getActivity(), "成功！是否打印");
                        queding_quxiao.show();
                        queding_quxiao.setOnResultClickListener(new Queding_Quxiao.OnResultClickListener() {
                            @Override
                            public void onConfirm() {
                                DY();
                                queding_quxiao.dismiss();
                            }

                            @Override
                            public void onCancel() {
                                queding_quxiao.dismiss();
                                ((MainActivity) getActivity()).upzcdd();
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
    public void zz() {
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", Conts.work_no);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_ZZ, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("cjn", s);
                queRen = new QueRen(getActivity(), "终止成功");
                queRen.show();
                queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick() {
                        queRen.dismiss();
                        ((MainActivity) getActivity()).upzcdd();
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
     * 修改工时
     *
     * @param id
     * @param je
     * @param gs
     */
    public void xiugaigongshi(int id, Double je, Double gs) {
        AbRequestParams params = new AbRequestParams();
        params.put("id", id);
        params.put("je", je+"");
        params.put("gs", gs+"");
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_GsDj_xiugai, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("cjn", "成功的显示" + s);
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                Log.i("cjn", "失败的显示" + s);
            }
        });


    }

    /**
     * 派工人员详细
     *
     * @param work_no
     * @param wxxm_no
     */
    public void PGRenYuan(String work_no, String wxxm_no, double wxxm_gs, double wxxm_je) {
        listPGrenyuan.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", Conts.work_no);
        params.put("wxxm_no", wxxm_no);
        Log.i("cjn", "查看两个数据" + work_no + "======" + wxxm_no);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_PaiGong_XiangXi, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int a, String s) {
                Log.i("cjn", "派工明细" + s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("status").toString().equals("1")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            //这块拿到的是维系接单的详细表
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
                        adapter.setList(listPGrenyuan);
                        adapter.notifyDataSetChanged();
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
     * 初始化碎片
     */
    public void initFragment() {
        BSD_wxxm = new BSD_wxywdd_wxxm();

        //查看
        BSD_wxxm.setChaKanPaiGongREN(new BSD_wxywdd_wxxm.ChaKanPaiGongREN() {
            @Override
            public void onYesClick(String work_no, String wxxm_no, double wxxm_gs, double wxxm_je) {
                PGRenYuan(work_no, wxxm_no, wxxm_gs, wxxm_je);
                Log.i("cjn", "点击事件更近数据");
            }
        });

        BSD_wxxm.setClearPaiGongRenYuan(new BSD_wxywdd_wxxm.ClearPaiGongRenYuan() {
            @Override
            public void clearPaiGong() {
                listPGrenyuan.clear();
                adapter.notifyDataSetChanged();
            }
        });

        BSD_wxcl = new BSD_wxywdd_wxcl();

        fragments = new Fragment[2];
        fragments[0] = BSD_wxxm;
        fragments[1] = BSD_wxcl;

    }


    //切换碎片事务的方法
    private void change(Fragment f) {
        getActivity().getSupportFragmentManager()//碎片管理者
                //开启事务
                .beginTransaction()
                //替换方法
                .replace(R.id.bsd_clxq_lswx, f)
                //提交事务
                .commit();
    }


    //选中项的变色
    private void checkHighLight(int index) {
        for (int i = 0; i < arr_tv.length; i++) {
            arr_tv[i].setTextColor(this.getResources().getColor(R.color.transparent_background));
        }
        arr_tv[index].setTextColor(this.getResources().getColor(R.color.bsd_xz_yes));

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
//                    change_CL();
                    currentType = 1;
                }
                break;
            case R.id.iv_wxxm_add: // 添加维修项目
//                showWxxm();
                break;
            case R.id.iv_wxll_add: // 添加维修用料
//                showWxll();
                break;
        }

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
            BSD_wxxm = new BSD_wxywdd_wxxm();
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
            BSD_wxxm.setChaKanPaiGongREN(new BSD_wxywdd_wxxm.ChaKanPaiGongREN() {
                @Override
                public void onYesClick(String work_no, String wxxm_no, double wxxm_gs, double wxxm_je) {
                    PGRenYuan(work_no, wxxm_no, wxxm_gs, wxxm_je);
                    Log.i("cjn", "点击事件更近数据");
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

    }

    /**
     * 维修接单详情
     */
    public void getBillInfo() {
        list.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("work_no",  param);
        params.put("gongsino", MyApplication.shared.getString("bsd_gs_id", ""));
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_zcdu_listinfo, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aa, String s) {
                WeiboDialogUtils.closeDialog(mWeiboDialog);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("total").toString().equals("1")) {
                        if (jsonObject.get("status").toString().equals("1")) {
                            JSONObject json = jsonObject.getJSONObject("data");
                            billEntiy = new BSD_WeiXiuJieDan_Entity();
                            billEntiy.setWork_no(json.getString("work_no"));
                            billEntiy.setKehu_no(json.getString("kehu_no"));
                            billEntiy.setKehu_mc(json.getString("kehu_mc"));
                            billEntiy.setKehu_xm(json.getString("kehu_xm"));
                            billEntiy.setKehu_dz(json.getString("kehu_dz"));
                            billEntiy.setKehu_yb(json.getString("kehu_yb"));
                            billEntiy.setKehu_dh(json.getString("kehu_dh"));
                            billEntiy.setChe_no(json.getString("che_no"));
                            billEntiy.setChe_cx(json.getString("che_cx"));
                            billEntiy.setChe_vin(json.getString("che_vin"));
                            billEntiy.setXche_lc(json.getInt("xche_lc"));
                            billEntiy.setXche_cz(json.getString("xche_cz"));
                            billEntiy.setXche_yjwgrq(json.getString("xche_yjwgrq"));
                            billEntiy.setXche_ywlx(json.getString("substate"));
                            updateBillInfoUI();
                        } else {
                            String mainstate = jsonObject.getString("data");
                            if (mainstate.equals("-1")) {
                                Show.showTime(getActivity(), "此维修单已经返回到接待登记处");
                            } else if(mainstate.equals("4")) {
                                Show.showTime(getActivity(), "此维修单已完成调度，在结算处");
                            }
                        }
                    } else {
                        Show.showTime(getActivity(), "此维修单已不存在");
                    }
                    change(BSD_wxxm);
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
                change(BSD_wxxm);
                Show.showTime(getActivity(), "网络连接超时");
                Log.i("cjn", "基本信息请求失败");
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });


    }

    /**
     * 跳转之后的详情页面
     */
    public void tiaozhuanjiedan() {
        list.clear();
//        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        AbRequestParams params = new AbRequestParams();
        params.put("pai", Conts.cp);
        params.put("gongsiNo", MyApplication.shared.getString("GongSiNo", ""));
        params.put("caozuoyuan_xm", MyApplication.shared.getString("name", ""));
        Log.i("cjnn", "查看车牌" + Conts.cp + "---查看gongsiNo" + MyApplication.shared.getString("GongSiNo", "")
                + "-----caozuoyuan_xm" + MyApplication.shared.getString("name", ""));
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxjd_jbxxs, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aa, String s) {
                Log.i("cjn", "信息++++++++++++" + s);
                try {
                    JSONObject jsonObject = new JSONObject(s);

                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            //这块拿到的是维系接单的详细表
                            JSONObject item = jsonarray.getJSONObject(i);
                            BSD_WeiXiuJieDan_Entity entiy = new BSD_WeiXiuJieDan_Entity();
                            entiy.setWork_no(item.getString("work_no"));
                            entiy.setKehu_no(item.getString("kehu_no"));
                            entiy.setKehu_mc(item.getString("kehu_mc"));
                            entiy.setKehu_xm(item.getString("kehu_xm"));
                            entiy.setKehu_dz(item.getString("kehu_dz"));
                            entiy.setKehu_yb(item.getString("kehu_yb"));
                            entiy.setKehu_dh(item.getString("kehu_dh"));
                            entiy.setChe_no(item.getString("che_no"));
                            entiy.setChe_cx(item.getString("che_cx"));
                            entiy.setChe_vin(item.getString("che_vin"));
                            entiy.setXche_lc(item.getInt("xche_lc"));
                            entiy.setXche_cz(item.getString("xche_cz"));
                            entiy.setXche_yjwgrq(item.getString("xche_jdrq"));
                            entiy.setXche_ywlx(item.getString("substate"));
                            list.add(entiy);
                        }
                        WeiboDialogUtils.closeDialog(mWeiboDialog);

                    } else {
                        WeiboDialogUtils.closeDialog(mWeiboDialog);
                        Show.showTime(getActivity(), jsonObject.get("message").toString());
                    }

                    change(BSD_wxxm);
                    Conts.work_no = list.get(0).getWork_no();

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
                change(BSD_wxxm);
                Show.showTime(getActivity(), "网络连接超时");
                Log.i("cjn", "基本信息请求失败");
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });


    }


    public void remodata(String reco_no) {
        AbRequestParams params = new AbRequestParams();
        params.put("xxNo", reco_no);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_paigongdelPgxx, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("cjn", "查看删除成功" + s);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                Log.i("cjn", "查看删除错误信息" + s);
            }
        });


    }

}
