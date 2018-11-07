package com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
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
import com.example.administrator.boshide2.Modular.Adapter.AbstractSpinerAdapter;
import com.example.administrator.boshide2.Modular.Adapter.CustemSpinerAdapter;
import com.example.administrator.boshide2.Modular.Entity.CustemObject;
import com.example.administrator.boshide2.Modular.Fragment.BaseFragment;
import com.example.administrator.boshide2.Modular.Fragment.PinpaiInfoDialog;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.Fagmt.BSD_mrkx_wxcl;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.Fagmt.BSD_mrkx_wxxm;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.deleg.BSD_mrkx_jiesuan;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.deleg.BSD_mrkx_jiesuan11;
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
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.Pop_Entity.BSD_wxyy_cl_pop_entity;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.Pop_Entity.BSD_wxyy_xm_pop_entiy;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.UpdateItemInfoDialog;
import com.example.administrator.boshide2.Modular.View.SpinerPopWindow;
import com.example.administrator.boshide2.Modular.View.Time.TimeDialog;
import com.example.administrator.boshide2.Modular.View.diaog.CustomDialog;
import com.example.administrator.boshide2.Modular.View.diaog.QueRen;
import com.example.administrator.boshide2.Modular.View.diaog.Queding_Quxiao;
import com.example.administrator.boshide2.Modular.View.diaog.WanGongQueDing_QuXiao;
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
 * @美容快修 Created by Administrator on 2017-4-13.
 */
public class BSD_MeiRongKuaiXiu_Fragment extends BaseFragment implements View.OnClickListener {
    private static final String PARAM_KEY = "param_key";
    private LinearLayout bsd_lsbj_fanhui;
    private TextView tv_wxxmAdd, tv_wxllAdd;
    private ListView bsd_wxywdd_you_lv;
    private BSD_wxywdd_dap paiGongInfoAdapter;
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
    private BSD_mrkx_wxcl BSD_wxcl;
    private BSD_mrkx_wxxm BSD_wxxm;
    private Queding_Quxiao queding_quxiao;
    private WanGongQueDing_QuXiao wanGongQueDing_quXiao;
    //修改工时弹框
    private List<Map<String, String>> listPGrenyuan = new ArrayList<Map<String, String>>();
    private URLS url;
    private TextView billNo;
    private TextView bsd_zadd_wg;
    //车辆信息、历史维修、历史维修建议
    private TextView tv_carInfo, bsd_mrkx_lswxjy, tv_repairHistory;
    private EditText bsd_mrkx_et_miaoshu;  //故障描述
    private EditText et_huiyuankahao;//会员卡号
    private TextView tv_readCard;//查询
    private LinearLayout ll_pinpai;//品牌
    private TextView bsd_mrkx_tv_pp;//品牌
    private LinearLayout ll_chexi;//车系
    private TextView bsd_mrkx_tv_chexi;//车系
    private LinearLayout ll_chezu;//车组
    private TextView bsd_mrkx_tv_chezu;//车组
    private LinearLayout ll_chexing;//车型
    private TextView bsd_mrkx_tv_chexing;//车型
    private EditText bsd_mrkx_et_vin;//VIN码
    private EditText bsd_mrkx_ed_lianxiren;//联系人w
    private LinearLayout bsd_mrxk_rl_djtime;//登记时间
    private TextView bsd_mrxk_tv_djtime;//登记时间
    private EditText bsd_mrkx_lianxifangshi;//联系                                                                           方式
    private PinpaiInfoDialog pinpaiInfoDialog;//品牌弹框
    private String cxbianhao;
    private String pinpaiming;
    //车系
    private List<Map<String, String>> listjbcx = new ArrayList<Map<String, String>>();
    private List<CustemObject> nameList = new ArrayList<CustemObject>();
    private AbstractSpinerAdapter mAdapter;
    private SpinerPopWindow mSpinerPopWindow;
    private String chexiid;
    //车组
    private List<Map<String, String>> listjbcz = new ArrayList<Map<String, String>>();
    private String chezuid;
    private List<CustemObject> nameList1 = new ArrayList<CustemObject>();
    private AbstractSpinerAdapter mAdapter1;
    private SpinerPopWindow mSpinerPopWindow1;
    //车型
    private List<Map<String, String>> listjbchexing = new ArrayList<Map<String, String>>();
    private String chexingid;
    private List<CustemObject> nameList2 = new ArrayList<CustemObject>();
    private AbstractSpinerAdapter mAdapter2;
    private SpinerPopWindow mSpinerPopWindow2;
    private String chezx;
    //购车时间
    private TimeDialog timeShow;
    //保养信息查询
    private TextView bsd_mrxk_bycx;
    //里程
    private EditText bsd_mrkx_ed_lc;
    private TextView tv_save;//存档
    //维修接单基本信息实体类
    private BSD_WeiXiuJieDan_Entity entity;
    //美容快修结算
    private BSD_mrkx_jiesuan bsd_mrkx_jiesuan;
    private BSD_mrkx_jiesuan11 bsd_mrkx_jiesuan11;
    //工时费率
    private LinearLayout ll_gsfl;
    private TextView bsd_mrkx_tv_gsfl;
    private List<Map<String, String>> listgslv = new ArrayList<Map<String, String>>();
    private List<CustemObject> nameList3 = new ArrayList<CustemObject>();
    private AbstractSpinerAdapter mAdapter3;
    private SpinerPopWindow mSpinerPopWindow3;
    private String gongshifeili_name;
    private String gongshifeili_id;
    private double xm_zj;
    private double cl_zj;
    private double zong_zj;
    private TextView bsd_mrkx_zongjia;
    private LinearLayout bsd_mrkx_rl_hukx;
    private String Car;
    private TextView bsd_mrkx_tv_ck;
    private LinearLayout ll_stock;
    private List<Map<String, String>> listStock = new ArrayList<Map<String, String>>();
    private List<CustemObject> nameList4 = new ArrayList<CustemObject>();
    private AbstractSpinerAdapter mAdapter4;
    private SpinerPopWindow mSpinerPopWindow4;
    private String ckname;
    private String ckid;
    private QueRen queRen;
    private boolean isSaveBill = false;
    private TextView tv_removecard;
    private double bsd_xche_hjje;
    private double bsd_xche_ssje;
    private double bsd_xche_wxxm_yhje;
    private double bsd_xche_peij_yhje;
    private double bsd_xche_ysje;
    private String bsd_card_no;
    private String bsd_mima;
    private int bsd_iscard;
    private double bsd_bxj;
    private double bsd_zhifu_card_je;
    private double bsd_zhifu_card_xj;
    private Dialog mWeiboDialog;
    //根据VIN返回车型信息
    private List<Map<String, String>> listvincx = new ArrayList<>();
    private String cxnm;      //车型内码
    private TextView iv_readVin;
    private String params;
    private TextView title;
    private TextView footerText;
    private BSD_WeiXiuJieDan_Entity billEntiy;
    private ImageView iv_wxxmAdd;
    private ImageView iv_wxllAdd;
    private int currentType = 0; // 标示当前是显示的维修项目还是维修用料，0是维修项目，1是维修用来
    private BSD_ZCDUXQ_XM_POP bsd_zcduxq_xm_pop;
    private BSD_ZCDUXQ_CL_POP bsd_zcduxq_cl_pop;
    private RelativeLayout beijing;
    private List<BSD_WeiXiuJieDan_XM_Entity> list_XM;
    private List<BSD_WeiXiuJieDan_CL_Entity> list_CL;
    private TextView tv_paigongAll;
    private UpdateItemInfoDialog updateItemInfoDialog;
    private Queding_Quxiao quedingQuxiao;
    private double currentWxxmZk = 1;
    private double currentWxclZk = 1;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private boolean isShowWxcl = false;

    public static BSD_MeiRongKuaiXiu_Fragment newInstance(String params) {
        BSD_MeiRongKuaiXiu_Fragment fragment = new BSD_MeiRongKuaiXiu_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_KEY, params);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        params = getArguments().getString(PARAM_KEY);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.bsd_meirongkuaixiu;
    }

    @Override
    public void initView() {
        //车辆信息、历史维修、历史维修建议
        tv_carInfo = (TextView) view.findViewById(R.id.tv_carinfo);
        tv_carInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                BSD_MeiRongKuaiXiu_cheliangxinxi_Fragment.newInstance(billEntiy.getChe_no(), Conts.BILLTYPE_MRKX, billEntiy.getWork_no())
                        .show(getFragmentManager(), "dialog_fragment");
            }
        });
        tv_repairHistory = (TextView) view.findViewById(R.id.tv_repairhistory);
        tv_repairHistory.setOnClickListener(this);
        bsd_mrkx_lswxjy = (TextView) view.findViewById(R.id.bsd_mrkx_lswxjy);
        bsd_mrkx_lswxjy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BSD_LiShiWeiXiuJianYi_DialogFragment().
                        show(getFragmentManager(), "mrkx_lswxjy");
            }
        });
        tv_removecard = (TextView) view.findViewById(R.id.tv_removecard);
        tv_removecard.setOnClickListener(this);
        ll_stock = (LinearLayout) view.findViewById(R.id.ll_stock);
        bsd_mrkx_tv_ck = (TextView) view.findViewById(R.id.bsd_mrkx_tv_ck);
        ll_stock.setOnClickListener(this);
        bsd_mrkx_zongjia = (TextView) view.findViewById(R.id.bsd_mrkx_zongjia);
        ll_gsfl = (LinearLayout) view.findViewById(R.id.ll_gsfl);
        bsd_mrkx_tv_gsfl = (TextView) view.findViewById(R.id.bsd_mrkx_tv_gsfl);
        ll_gsfl.setOnClickListener(this);
        // 读取会员卡信息
        tv_readCard = (TextView) view.findViewById(R.id.tv_readcard);
        tv_readCard.setOnClickListener(this);
        bsd_mrkx_ed_lc = (EditText) view.findViewById(R.id.bsd_mrkx_ed_lc);
        //保养查询
        bsd_mrxk_bycx = (TextView) view.findViewById(R.id.bsd_mrxk_bycx);
        bsd_mrxk_bycx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bsd_mrkx_tv_chexing.getText().toString().equals("") ||
                        bsd_mrkx_ed_lc.getText().toString().equals("") ||
                        bsd_mrxk_tv_djtime.getText().toString().equals("")) {
                    Show.showTime(getActivity(), "请输入完整信息");
                } else {
                    bsd_cexifansuan();
                }
            }
        });

        et_huiyuankahao = (EditText) view.findViewById(R.id.bsd_mrkx_et_huiyuankahao);
        bsd_mrkx_et_vin = (EditText) view.findViewById(R.id.bsd_mrkx_et_vin);
        bsd_mrkx_ed_lianxiren = (EditText) view.findViewById(R.id.bsd_mrkx_ed_lianxiren);
        bsd_mrxk_rl_djtime = (LinearLayout) view.findViewById(R.id.bsd_mrxk_rl_djtime);
        bsd_mrxk_tv_djtime = (TextView) view.findViewById(R.id.bsd_mrxk_tv_djtime);
        bsd_mrkx_lianxifangshi = (EditText) view.findViewById(R.id.bsd_mrkx_lianxifangshi);
        //品牌
        ll_pinpai = (LinearLayout) view.findViewById(R.id.ll_pinpai);
        bsd_mrkx_tv_pp = (TextView) view.findViewById(R.id.bsd_mrkx_tv_pp);
        ll_pinpai.setOnClickListener(this);
        //车系
        ll_chexi = (LinearLayout) view.findViewById(R.id.ll_chexi);
        bsd_mrkx_tv_chexi = (TextView) view.findViewById(R.id.bsd_mrkx_tv_chexi);
        ll_chexi.setOnClickListener(this);
        //车组
        ll_chezu = (LinearLayout) view.findViewById(R.id.ll_chezu);
        bsd_mrkx_tv_chezu = (TextView) view.findViewById(R.id.bsd_mrkx_tv_chezu);
        ll_chezu.setOnClickListener(this);
        //车型
        ll_chexing = (LinearLayout) view.findViewById(R.id.ll_chexing);
        bsd_mrkx_tv_chexing = (TextView) view.findViewById(R.id.bsd_mrkx_tv_chexing);
        ll_chexing.setOnClickListener(this);
        //购车时间
        bsd_mrxk_rl_djtime = (LinearLayout) view.findViewById(R.id.bsd_mrxk_rl_djtime);
        bsd_mrxk_tv_djtime = (TextView) view.findViewById(R.id.bsd_mrxk_tv_djtime);
        bsd_mrxk_rl_djtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeShow.timePickerAlertDialog(bsd_mrxk_tv_djtime);
            }
        });

        billNo = (TextView) view.findViewById(R.id.tv_billNo);
        //结算
        bsd_zadd_wg = (TextView) view.findViewById(R.id.bsd_zadd_wg);
        bsd_zadd_wg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bsd_zadd_wg.setEnabled(false);
                //结算
                //判断车牌号和客户编码是否对应
                checkCarIsForThisKehu();
            }
        });

        bsd_ywwwdd_cp = (TextView) view.findViewById(R.id.bsd_ywwwdd_cp);
        bsd_ywwwdd_pinpai = (TextView) view.findViewById(R.id.bsd_ywwwdd_pinpai);
        bsdywwwdd_chexi = (TextView) view.findViewById(R.id.bsdywwwdd_chexi);
        bsd_ywwwdd_chezu = (TextView) view.findViewById(R.id.bsd_ywwwdd_chezu);
        bsd_ywwwdd_chexing = (TextView) view.findViewById(R.id.bsd_ywwwdd_chexing);
        bsd_ywwwdd_vin = (TextView) view.findViewById(R.id.bsd_ywwwdd_vin);
        //读取vin码
        iv_readVin = (TextView) view.findViewById(R.id.tv_readvin);
        iv_readVin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vin = bsd_mrkx_et_vin.getText().toString();
                if ("".equals(vin)) {
                    Show.showTime(getHostActicity(), "请输入vin码");
                } else {
                    duVin();
                }
            }
        });

        bsd_mrkx_et_miaoshu = (EditText) view.findViewById(R.id.bsd_mrkx_et_miaoshu);
        bsd_ywwwdd_user = (TextView) view.findViewById(R.id.bsd_ywwwdd_user);
        bsd_ywwwdd_dengjishijian = (TextView) view.findViewById(R.id.bsd_ywwwdd_dengjishijian);
        bsd_ywwwdd_dianhua = (TextView) view.findViewById(R.id.bsd_ywwwdd_dianhua);
        //获取listView
        bsd_wxywdd_you_lv = (ListView) view.findViewById(R.id.bsd_wxywdd_you_lv);
        paiGongInfoAdapter = new BSD_wxywdd_dap(getActivity(), listPGrenyuan);
        paiGongInfoAdapter.setOnOperateItemListener(new BSD_wxywdd_dap.OnOperateItemListener() {
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
        bsd_wxywdd_you_lv.setAdapter(paiGongInfoAdapter);
        tv_wxxmAdd = (TextView) view.findViewById(R.id.tv_wxxm_add);
        tv_wxllAdd = (TextView) view.findViewById(R.id.tv_wxll_add);
        tv_wxxmAdd.setOnClickListener(this);
        tv_wxllAdd.setOnClickListener(this);
        //存档操作
        tv_save = (TextView) view.findViewById(R.id.bsd_mrkx_cundang);
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSaveBill = true;
                saveBillInfo();
                if (et_huiyuankahao.getText().toString().equals("")) {
//                    Log.i("wxcl", "会员卡空");
                } else {
//                    Log.i("wxcl", "会员卡不为空=="+Conts.card_no);
                    //判断此会员卡、单号下是否有特殊材料，并获取特殊材料的特殊折扣或单价；
                    teShuCL();
                    teShuXM();
                }
            }
        });
        bsd_mrkx_rl_hukx = (LinearLayout) view.findViewById(R.id.bsd_mrkx_rl_hukx);
        if (Conts.BSD_zhongxiaoweixiu == 1) {
            bsd_mrkx_rl_hukx.setVisibility(View.VISIBLE);
            tv_readCard.setVisibility(View.VISIBLE);
        } else {
            bsd_mrkx_rl_hukx.setVisibility(View.INVISIBLE);
            tv_readCard.setVisibility(View.INVISIBLE);
        }
        // 返回
        bsd_lsbj_fanhui = (LinearLayout) view.findViewById(R.id.bsd_lsbj_fanhui);
        bsd_lsbj_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveBillInfoBeforeBack();
            }
        });
        title = (TextView) view.findViewById(R.id.tv_title);
        footerText = (TextView) view.findViewById(R.id.tv_footertext);
        iv_wxxmAdd = (ImageView) view.findViewById(R.id.iv_wxxm_add);
        iv_wxllAdd = (ImageView) view.findViewById(R.id.iv_wxll_add);
        iv_wxxmAdd.setOnClickListener(this);
        iv_wxllAdd.setOnClickListener(this);
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
        // 让会员卡号获取输入焦点
        et_huiyuankahao.setFocusable(true);
        et_huiyuankahao.setFocusableInTouchMode(true);
        et_huiyuankahao.requestFocus();
    }

    /**
     * 修改派工金额
     *
     * @param position
     */
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

    /**
     * 更新派工工时
     *
     * @param position
     */
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

    @Override
    public void initData() {
        url = new URLS();
        title.setText("美容快修");
        footerText.setText("公司名称 :   " + MyApplication.shared.getString("GongSiMc", "") +
                "                  公司电话 :   " + MyApplication.shared.getString("danw_dh", ""));
        timeShow = new TimeDialog(getActivity());
        getBillInfoFromParam();
        updateBillInfo();
        if (!TextUtils.isEmpty(billEntiy.getCard_no())) {
            getCardInfo(billEntiy.getCard_no(), true);
        }
        initFragment();
        change_XM();
    }

    private void initFragment() {
        // 初始化维修项目项目的碎片
        BSD_wxxm = BSD_mrkx_wxxm.newInstance(billEntiy.getWork_no());
        BSD_wxxm.setOnRefreashPaiGongListener(new BSD_mrkx_wxxm.OnRefreashPaiGongListener() {
            @Override
            public void onRefreash(String workNo, String wxxmNo) {
                getPaiGongInfo(workNo, wxxmNo);
            }

            @Override
            public void onWxxmRequestSuccess(String workNo, String wxxmNo) {
                getPaiGongInfo(workNo, wxxmNo);
            }

        });
        BSD_wxxm.setChaKanPaiGongREN(new BSD_mrkx_wxxm.ChaKanPaiGongREN() {
            @Override
            public void onYesClick(String work_no, String wxxm_no, double wxxm_gs, double wxxm_je) {
                PGRenYuan(work_no, wxxm_no, wxxm_gs, wxxm_je);
            }
        });
        BSD_wxxm.setXm_zj(new BSD_mrkx_wxxm.XM_ZJ() {
            @Override
            public void onYesClick(double xmzj) {
                xm_zj = xmzj;
                zong_zj = xm_zj + cl_zj;
                double v = (Math.round(zong_zj * 100) / 100.0);
                bsd_mrkx_zongjia.setText("" + v);
                billEntiy.setXche_hjje(zong_zj);
            }
        });
        BSD_wxxm.setOnUpdateZKListener(new BSD_mrkx_wxxm.OnUpdateZKListener() {
            @Override
            public void onSuccess(double wxxmZK) {
                currentWxxmZk = wxxmZK;
            }

            @Override
            public void onFail() {

            }
        });
        // 初始化维修材料的碎片
        BSD_wxcl = BSD_mrkx_wxcl.newInstance(billEntiy.getWork_no());
        BSD_wxcl.setCl_zj(new BSD_mrkx_wxcl.CL_ZJ() {
            @Override
            public void onYesClick(double clzj) {
                cl_zj = clzj;
                zong_zj = xm_zj + cl_zj;
                double v = (Math.round(zong_zj * 100) / 100.0);
                bsd_mrkx_zongjia.setText("" + v);
                billEntiy.setXche_hjje(zong_zj);
            }
        });
        BSD_wxcl.setOnUpdateZKListener(new BSD_mrkx_wxcl.OnUpdateZKListener() {
            @Override
            public void onSuccess(double wxclZK) {
                if (!isShowWxcl) {
                    double _clZj = cl_zj / currentWxclZk;
                    cl_zj = _clZj * wxclZK;
                    zong_zj = xm_zj + cl_zj;
                    bsd_mrkx_zongjia.setText("" + zong_zj);
                }
                currentWxclZk = wxclZK;
            }

            @Override
            public void onFail() {

            }
        });
        fragmentManager = getActivity().getSupportFragmentManager();
    }

    /**
     * 根据传入的参数，获取到单据中的信息
     */
    private void getBillInfoFromParam() {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(params);
            JSONObject item = jsonObject.getJSONObject("data");
            billEntiy = new BSD_WeiXiuJieDan_Entity();
            billEntiy.setWork_no(item.getString("work_no"));
            billEntiy.setKehu_no(item.getString("kehu_no"));
            billEntiy.setKehu_mc(item.getString("kehu_mc"));
            billEntiy.setKehu_xm(item.getString("kehu_xm"));
            billEntiy.setKehu_dz(item.getString("kehu_dz"));
            billEntiy.setKehu_yb(item.getString("kehu_yb"));
            billEntiy.setKehu_dh(item.getString("kehu_dh"));
            billEntiy.setChe_no(item.getString("che_no"));
            billEntiy.setChe_cx(item.getString("che_cx"));
            billEntiy.setChe_vin(item.getString("che_vin"));
            billEntiy.setChe_gzms(item.getString("xche_bz"));   //故障描述
            billEntiy.setXche_lc(item.getInt("xche_lc"));
            billEntiy.setXche_jdrq(item.getString("xche_jdrq"));
            billEntiy.setXche_sfbz(item.getString("xche_sfbz"));   //费率名称
            billEntiy.setXche_sffl(item.getDouble("xche_sffl"));
            billEntiy.setCangk_dm(item.getString("cangk_dm"));
            billEntiy.setCangk_mc(item.getString("cangk_mc"));
            billEntiy.setGcsj(item.getString("gcsj"));
            billEntiy.setCard_no(item.getString("card_no"));
            billEntiy.setXche_hjje(item.getDouble("xche_hjje"));
            xm_zj = item.getDouble("xche_rgf");
            cl_zj = item.getDouble("xche_clf");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void updateBillInfo() {
        bsd_ywwwdd_cp.setText(billEntiy.getChe_no());
        String cheCx = billEntiy.getChe_cx();
        String[] cheCxs = cheCx.split("\\|");
        if (cheCxs.length >= 4) {
            bsd_mrkx_tv_pp.setText(cheCxs[0]);
            bsd_mrkx_tv_chexi.setText(cheCxs[1]);
            bsd_mrkx_tv_chezu.setText(cheCxs[2]);
            bsd_mrkx_tv_chexing.setText(cheCxs[3]);
        }
        if (TextUtils.isEmpty(billEntiy.getChe_vin()) || billEntiy.getChe_vin().length() < 2) {
            bsd_mrkx_et_vin.setText(Conts.VIN);
        } else {
            bsd_mrkx_et_vin.setText(billEntiy.getChe_vin());
        }
        //会员卡号
        et_huiyuankahao.setText(billEntiy.getCard_no());
        bsd_mrkx_ed_lc.setText("" + billEntiy.getXche_lc());
        bsd_mrkx_ed_lianxiren.setText(billEntiy.getKehu_mc());
        bsd_mrkx_et_miaoshu.setText(billEntiy.getChe_gzms());
        bsd_mrkx_lianxifangshi.setText(billEntiy.getKehu_dh());
        bsd_mrkx_tv_gsfl.setText(billEntiy.getXche_sfbz());    //费率名称
        Conts.feilv_name = billEntiy.getXche_sfbz();
        bsd_mrkx_tv_ck.setText(billEntiy.getCangk_mc());
        bsd_mrxk_tv_djtime.setText(billEntiy.getGcsj());
        billNo.setText(billEntiy.getWork_no());
        ckid = billEntiy.getCangk_dm();
        bsd_mrkx_zongjia.setText("");
    }

    /**
     * 获取仓库信息
     */
    public void getStockInfo() {
        listStock.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("caozuoyuanxm", MyApplication.shared.getString("name", ""));
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_MRKX_CK, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    if (jsonObject.get("status").toString().equals("1")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject item = jsonarray.getJSONObject(i);
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("cangk_dm", item.getString("cangk_dm"));
                            map.put("cangk_mc", item.getString("cangk_mc"));
                            listStock.add(map);
                        }
                    }
                    updateStock();
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
     * 更新仓库信息，下拉内容
     */
    public void updateStock() {
        nameList4.clear();
        for (int i = 0; i < listStock.size(); i++) {
            CustemObject object = new CustemObject();
            object.data = listStock.get(i).get("cangk_mc");
            nameList4.add(object);
        }
        mAdapter4 = new CustemSpinerAdapter(getActivity());
        mAdapter4.refreshData(nameList4, 0);
        mSpinerPopWindow4 = new SpinerPopWindow(getActivity());
        mSpinerPopWindow4.setAdatper(mAdapter4, 310);
        mSpinerPopWindow4.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
            @Override
            public void onItemClick(int pos) {
                String clickValue = nameList4.get(pos).toString();
                if (!bsd_mrkx_tv_ck.getText().toString().equals(clickValue)) {
                    bsd_mrkx_tv_ck.setText(clickValue);
                    ckname = listStock.get(pos).get("cangk_mc");
                    ckid = listStock.get(pos).get("cangk_dm");
                }
            }
        });
        mSpinerPopWindow4.setWidth(ll_stock.getWidth());
        mSpinerPopWindow4.showAsDropDown(ll_stock);
    }

    /**
     * 更新工时费率，下拉内容
     */
    public void updateGSFLData() {
        nameList3.clear();
        for (int i = 0; i < listgslv.size(); i++) {
            CustemObject object = new CustemObject();
            object.data = listgslv.get(i).get("feil_mc");
            nameList3.add(object);
        }
        mAdapter3 = new CustemSpinerAdapter(getActivity());
        mAdapter3.refreshData(nameList3, 0);
        mSpinerPopWindow3 = new SpinerPopWindow(getActivity());
        mSpinerPopWindow3.setAdatper(mAdapter3, 310);
        mSpinerPopWindow3.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
            @Override
            public void onItemClick(int pos) {
                String clickValue = nameList3.get(pos).toString();
                if (!bsd_mrkx_tv_gsfl.getText().toString().equals(clickValue)) {
                    bsd_mrkx_tv_gsfl.setText(clickValue);
                    gongshifeili_name = listgslv.get(pos).get("feil_mc");
                    gongshifeili_id = listgslv.get(pos).get("feil_fl");
                    Conts.feilv_name = gongshifeili_name;
                }
            }
        });
        mSpinerPopWindow3.setWidth(ll_gsfl.getWidth());
        mSpinerPopWindow3.showAsDropDown(ll_gsfl);
    }


    /**
     * 获取工时费率
     */
    public void getGSFLData() {
        listgslv.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("type", "ITGongShi");
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_HYGL_ADD_TYPE, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    if (jsonObject.get("status").toString().equals("1")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject item = jsonarray.getJSONObject(i);
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("id", item.getString("id"));
                            map.put("feil_mc", item.getString("feil_mc"));
                            map.put("feil_fl", item.getString("feil_fl"));
                            map.put("feil_sy", item.getString("feil_sy"));
                            listgslv.add(map);
                        }
                    }
                    updateGSFLData();
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

    private void showGongSi3() {
        mSpinerPopWindow3.setWidth(ll_gsfl.getWidth());
        mSpinerPopWindow3.showAsDropDown(ll_gsfl);
    }


    /**
     * 判断车辆的客户编码和车辆编码
     */
    public void checkCarIsForThisKehu() {
        AbRequestParams params = new AbRequestParams();
        params.put("che_no", Conts.cp);
        params.put("kehu_no", Conts.kehu_no);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_kuaixiu_check, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    if (jsonObject.getString("message").equals("查询成功")) {
                        //判断是大维系还是小维修
                        isSaveBill = false;
                        saveBillInfo();
                    } else {
                        Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                Log.i("check", "check查询shibai=" + s);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }


        });

    }


    /**
     * 判断是否是特殊材料，并获取特殊折扣
     */
    public void teShuCL() {
        AbRequestParams params = new AbRequestParams();
        params.put("card_no", et_huiyuankahao.getText().toString());
        params.put("work_no", billEntiy.getWork_no());
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_getcardpj_price, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                //刷新维修材料列表内容；
                if (BSD_wxcl == null) {
                    BSD_wxcl = new BSD_mrkx_wxcl();
                }
                BSD_wxcl.cldata();
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                Log.i("wxcl", "特殊材料查询失败" + s);
            }
        });


    }

    /**
     * 判断是否是特殊项目，并获取特殊折扣
     */
    public void teShuXM() {
        AbRequestParams params = new AbRequestParams();
        params.put("card_no", et_huiyuankahao.getText().toString());
        params.put("work_no", Conts.work_no);
//        Log.i("wxcl", "看看单号:" + Conts.work_no+",==卡号:"+Conts.card_no);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_card_wxjd_xm, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("wxxm", "特殊材料查询成功==" + s);
                //刷新维修项目列表内容；
                if (BSD_wxxm == null) {
                    BSD_wxxm = new BSD_mrkx_wxxm();
                }
                BSD_wxxm.dataxm();

            }

            @Override
            public void onStart() {
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                Log.i("wxxm", "特殊项目查询失败" + s);
            }
        });
    }


    /*
     *根据vin码获取车辆名称、代码、内部名称；
     */
    public void duVin() {
        listvincx.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("vinCode", bsd_mrkx_et_vin.getText().toString());
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_getcxnm_byvin, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aa, String s) {

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    Log.e("vin", "onSuccess读取的：" + s);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        Map<String, String> map;
                        for (int i = 0; i < jsonarray.length(); i++) {
                            Log.e("vin", "2222");
                            JSONObject item = jsonarray.getJSONObject(i);
                            map = new HashMap<>();
                            map.put("cxMcStd", item.getString("chex_mc_std"));   //车系
                            map.put("cxDm", item.getString("chex_dm"));     //车系代码
                            map.put("cxMc", item.getString("chex_mc"));   //车系名称
                            Log.e("vins", "dm:" + item.getString("chex_dm"));
                            listvincx.add(map);
                        }
                        if (listvincx.size() == 1) {
                            Log.e("vin", "1条记录");
                            //如果只查到一条记录，通过chex_dm查询相应的品牌、车系、车组、车型信息；
                            cxnm = listvincx.get(0).get("cxDm");      //车型内码
//                            cxmc=listvincx.get(0).get("cxMc");      //车型名称
//                            cxmcstd=listvincx.get(0).get("cxMcStd");   //车型内部名称；
                            getcx_by_cxdm();
                        } else if (listvincx.size() > 1) {
                            //如果查到多条记录，弹出对话框，显示chex_mc和chex_mc_std；
                            Log.e("vin", "listvincx的长度：" + listvincx.size());
                            showDialogSelectCx();
                        }
                    }
                    if (jsonObject.get("message").toString().equals("查询失败")) {
                        Toast.makeText(getActivity(), jsonObject.getString("data").toString(), Toast.LENGTH_LONG).show();
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
                Log.e("vin", "onfailure失败了");
            }
        });
    }


    /*
     *根据vin码、车辆代码获取车牌车系车组车型信息
     */
    public void getcx_by_cxdm() {
        AbRequestParams params = new AbRequestParams();
        Log.e("sss", "cxnm是：" + cxnm);
        params.put("chex_dm", cxnm);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_getcx_byvindm, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aa, String s) {

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    Log.e("vin", "onSuccess获取车牌车系等信息：" + s);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        String data = jsonObject.getString("data");
                        Log.e("vin", "车牌车系json串：" + data);
                        //给车牌、车系、车组、车型赋值；
                        ArrayList arr = new ArrayList();
                        String[] s1 = data.split("\\|");
                        Log.e("vin", "品牌：" + s1[0] + ",车系" + s1[1] + ",车组" + s1[2] + ",车系" + s1[3]);
                        for (int j = 0; j < s1.length; j++) {
                            arr.add(j, s1[j]);
                        }


                        bsd_mrkx_tv_pp.setText(s1[0]);
                        bsd_mrkx_tv_chexi.setText(s1[1]);
                        bsd_mrkx_tv_chezu.setText(s1[2]);
                        bsd_mrkx_tv_chexing.setText(s1[3]);


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

    /*
     *vin对应多条车辆信息时，弹出选择对话框；
     */
    public void showDialogSelectCx() {
        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.bsd_clxx_dialog_for_select_cx, null);
        final Dialog dialog = new Dialog(getActivity());
        dialog.setTitle("请选择");
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params =
                dialog.getWindow().getAttributes();
        params.width = 900;
        params.height = 500;
        dialog.getWindow().setAttributes(params);


        ListView lv = (ListView) window.findViewById(R.id.bsd_clxx_lv_for_select_cx);
        lv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return listvincx.size();
            }

            @Override
            public Object getItem(int position) {
                return listvincx.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View layout = LayoutInflater.from(getActivity()).inflate(R.layout.select_cx_dialog_item, null);
                TextView tv_mc = (TextView) layout.findViewById(R.id.tv_cxmc);
                TextView tv_nbmc = (TextView) layout.findViewById(R.id.tv_cx_nbmc);
                tv_mc.setText(listvincx.get(position).get("cxMc"));     //车辆名称
                tv_nbmc.setText(listvincx.get(position).get("cxMcStd"));  //车辆内部名称
                Log.e("vin", "名称： " + listvincx.get(position).get("cxMc"));
                Log.e("vin", "内部名称： " + listvincx.get(position).get("cxMcStd"));
                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        cxnm = listvincx.get(position).get("cxDm");    //车辆代码
                        Log.e("vins", "车辆代码。。。" + cxnm);
                        getcx_by_cxdm();
                    }
                });

                return layout;
            }
        });


        dialog.show();


    }


    /**
     * 删除全部派工人员
     */
    public void delAll() {
        listPGrenyuan.clear();
        paiGongInfoAdapter.setList(listPGrenyuan);
        paiGongInfoAdapter.notifyDataSetChanged();
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", Conts.work_no);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_paigong_delPGAllRx, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("cjn", "查看json" + s);
                paiGongInfoAdapter.notifyDataSetChanged();
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
     * 会员卡查询接口，刚进界面时，如果改单有会员卡，根据会员卡查询材料折扣--李赛
     */
    public void huiyuankachauxn1() {
        AbRequestParams params = new AbRequestParams();
        params.put("card_no", et_huiyuankahao.getText().toString());
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_MRKX_HYK, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aa, String s) {

                Log.i("cjn", "会员卡号查询成功" + s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getString("message").toString().equals("查询成功")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject item = jsonArray.getJSONObject(i);
                                Conts.MRKX_XM_ZK = Double.parseDouble(item.getString("itemrate"));
                                Conts.MRKX_CL_ZK = Double.parseDouble(item.getString("peijrate"));
                                Conts.MRKX_shengYu_jinQian = Double.parseDouble(item.getString("card_leftje"));
                                Log.i("cjn", "查看每个的折扣率" + item.getString("card_leftje"));
                                Conts.MRKX_kahao = et_huiyuankahao.getText().toString();
                                queRen = new QueRen(getActivity(), "读取成功，卡内余额" + item.getString("card_leftje"));
                                queRen.show();
                                queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                                    @Override
                                    public void onYesClick() {
                                        queRen.dismiss();
                                    }
                                });

                            }
                        }
                    } else {
                        Toast.makeText(getActivity(), jsonObject.getString("data"), Toast.LENGTH_SHORT).show();

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
                Log.i("cjn", "会员卡号" + s);
            }
        });

    }

    /**
     * 根据会员卡号，获取会员卡信息
     * @param cardNo
     * @param isfirst 是否是第一个打开单据
     */
    public void getCardInfo(final String cardNo, final boolean isfirst) {
        AbRequestParams params = new AbRequestParams();
        params.put("card_no", cardNo);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_MRKX_HYK, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aa, String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    if (jsonObject.getString("message").equals("查询成功")) {
                        final JSONObject object = jsonObject.getJSONObject("data");
                        final double wxxmZK = Double.parseDouble(object.getString("itemrate"));
                        final double wxclZK = Double.parseDouble(object.getString("peijrate"));
                        double leftje = Double.parseDouble(object.getString("card_leftje"));
                        // 如果是第一次，则只读取会员卡的折扣
                        if (isfirst) {
                            currentWxxmZk = wxxmZK;
                            currentWxclZk = wxclZK;
                        } else {
                            billEntiy.setCard_no(cardNo);
                            quedingQuxiao = new Queding_Quxiao(getHostActicity(), "读取成功" +
                                    "\n卡内余额为：" + object.getString("card_leftje") +
                                    "\n是否对单据中的维修项目和配件应用此卡的折扣？");
                            quedingQuxiao.setOnResultClickListener(new Queding_Quxiao.OnResultClickListener() {
                                @Override
                                public void onConfirm() {
                                    // 如果获取到的折扣和当前折扣不等，则更新折扣，否则不变
//                                    if (wxxmZK != currentWxxmZk) {
//                                        BSD_wxxm.updateWxxmZK(wxxmZK);
//                                    }
//                                    if (wxclZK != currentWxclZk) {
//                                        BSD_wxcl.updateWxclZK(wxclZK);
//                                    }

                                    BSD_wxxm.updateWxxmZK(wxxmZK);
                                    BSD_wxcl.updateWxclZK(wxclZK);
                                    quedingQuxiao.dismiss();
                                }

                                @Override
                                public void onCancel() {
                                    quedingQuxiao.dismiss();
                                }
                            });
                            quedingQuxiao.show();
                        }
                    } else {
                        Toast.makeText(getActivity(), jsonObject.getString("data"), Toast.LENGTH_SHORT).show();
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
                Log.i("cjn", "会员卡号" + s);
            }
        });

    }

    public void jieSuan1() {
        AbRequestParams params = new AbRequestParams();
        params.put("caozuoyuanid", MyApplication.shared.getString("name", ""));
        params.put("work_no", Conts.work_no);
        params.put("che_no", billEntiy.getChe_no());
        params.put("xche_hjje", bsd_xche_hjje + "");
        params.put("xche_wxxm_yhje", bsd_xche_wxxm_yhje + "");
        params.put("xche_peij_yhje", bsd_xche_peij_yhje + "");
        params.put("xche_ysje", bsd_xche_ysje + "");
        params.put("card_no", bsd_card_no);
        params.put("pass", bsd_mima);
        params.put("iscard", bsd_iscard);
        params.put("Zhifu_card_xj", bsd_bxj + "");
        if (bsd_iscard == 1) {
            //卡结算
            params.put("xche_ssje", bsd_xche_ysje + "");//应收
            params.put("zhifu_card_je", bsd_xche_ssje + "");//实收
            params.put("zhifu_card_xj", bsd_zhifu_card_xj + "");//补现金
        } else if (bsd_iscard == 0) {
            //不是储值卡结算
            params.put("xche_ssje", bsd_xche_ssje + "");
            params.put("zhifu_card_je", "0");
            params.put("zhifu_card_xj", "0");
        }
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_MRKX_JieSuan1, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    if (jsonObject.get("status").toString().equals("2")) {
                        String tipStr = jsonObject.get("data").toString();
                        wanGongQueDing_quXiao = new WanGongQueDing_QuXiao(getActivity(), tipStr + "请问是否继续结算？");
                        wanGongQueDing_quXiao.show();
                        wanGongQueDing_quXiao.setQuxiao(new WanGongQueDing_QuXiao.Quxiao() {
                            @Override
                            public void onYesClick() {
                                wanGongQueDing_quXiao.dismiss();
                            }
                        });
                        wanGongQueDing_quXiao.setToopromtOnClickListener(new WanGongQueDing_QuXiao.ToopromtOnClickListener() {
                            @Override
                            public void onYesClick() {
                                jieSuan3();
                                wanGongQueDing_quXiao.dismiss();
                            }
                        });

                    }
                    if (jsonObject.get("status").toString().equals("3")) {

                        jieSuan2();

                    }
                    if (jsonObject.get("status").toString().equals("1")) {
                        Toast.makeText(getActivity(), jsonObject.getString("data").toString()
                                , Toast.LENGTH_SHORT).show();
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
                Log.i("cjn", "结算1失败" + s);
            }
        });


    }

    public void jieSuan11() {
        AbRequestParams params = new AbRequestParams();
        params.put("caozuoyuanid", MyApplication.shared.getString("name", ""));
        params.put("work_no", Conts.work_no);
        params.put("che_no", entity.getChe_no());

        params.put("xche_hjje", bsd_xche_hjje + "");
        params.put("xche_ssje", bsd_xche_ssje + "");
        params.put("xche_wxxm_yhje", bsd_xche_wxxm_yhje + "");
        params.put("xche_peij_yhje", bsd_xche_peij_yhje + "");
        params.put("xche_ysje", bsd_xche_ysje + "");
        params.put("card_no", "");
        params.put("pass", "");
        params.put("iscard", 0);
        params.put("Zhifu_card_xj", 0 + "");
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_MRKX_JieSuan1, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("cjn", "结算1成功" + s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("status").toString().equals("2")) {
                        queding_quxiao = new Queding_Quxiao(getActivity(), jsonObject.getString("data"));
                        queding_quxiao.show();
                        queding_quxiao.setOnResultClickListener(new Queding_Quxiao.OnResultClickListener() {
                            @Override
                            public void onConfirm() {
                                jieSuan2();
                                queding_quxiao.dismiss();
                            }

                            @Override
                            public void onCancel() {
                                queding_quxiao.dismiss();
                            }
                        });
                    }
                    if (jsonObject.get("status").toString().equals("3")) {

                        jieSuan2();

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
                Log.i("cjn", "结算1失败" + s);
            }
        });


    }

    public void jieSuan2() {
        AbRequestParams params = new AbRequestParams();
        params.put("caozuoyuanid", MyApplication.shared.getString("name", ""));
        params.put("work_no", Conts.work_no);
        params.put("che_no", billEntiy.getChe_no());
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_MRKX_JieSuan2, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("cjn", "结算2成功" + s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("status").toString().equals("3")) {
                        //直接调用三
                        jieSuan3();
                    }
                    if (jsonObject.get("status").toString().equals("4")) {
                        //是否继续
                        queding_quxiao = new Queding_Quxiao(getActivity(), jsonObject.getString("data"));
                        queding_quxiao.show();
                        queding_quxiao.setOnResultClickListener(new Queding_Quxiao.OnResultClickListener() {
                            @Override
                            public void onConfirm() {
                                jieSuan3();
                                queding_quxiao.dismiss();
                            }

                            @Override
                            public void onCancel() {
                                queding_quxiao.dismiss();
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
                queRen = new QueRen(getActivity(), "结算失败");
                queRen.show();
                queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick() {
                        queRen.dismiss();
                    }
                });
                Log.i("cjn", "结算2失败" + s);
            }
        });


    }

    public void jieSuan3() {
        AbRequestParams params = new AbRequestParams();
        params.put("caozuoyuanid", MyApplication.shared.getString("name", ""));
        params.put("work_no", billEntiy.getWork_no());
        params.put("che_no", billEntiy.getChe_no());
        params.put("isPrint", MyApplication.shared.getString("shifoudayin", "0"));
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_MRKX_JieSuan3, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                if (bsd_mrkx_jiesuan.isShowing()) {
                    bsd_mrkx_jiesuan.dissm();
                }
                queRen = new QueRen(getActivity(), "结算成功");
                queRen.show();
                queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick() {
                        ((MainActivity) getActivity()).upBSD_MRKX_log();
                        queRen.dismiss();
                        //调用微信接口
                        weixin();
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

                queRen = new QueRen(getActivity(), "结算失败");
                queRen.show();
                queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick() {
                        ((MainActivity) getActivity()).upBSD_MRKX_log();
                        queRen.dismiss();
                    }
                });
                Log.i("cjn", "结算3" +
                        "失败" + s);
            }
        });


    }

    /**
     * 结算成功后发微信
     */
    private void weixin() {
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", Conts.work_no);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_mrkx_weixin, params, new AbStringHttpResponseListener() {
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
     * 存档操作
     */
    String chepai;
    String pinpai;
    String chexi;
    String chezu;
    String chexing;
    String che_cx;
    String VIN;
    String huiyuankahao;
    String jinchanglicheng;
    String chezhu;
    String dinahua;
    String cardNo;
    //单号
    String danhao;

    public void datacundang1() {

        danhao = entity.getWork_no();
        chepai = bsd_ywwwdd_cp.getText().toString();


        pinpai = bsd_mrkx_tv_pp.getText().toString();
        chexi = bsd_mrkx_tv_chexi.getText().toString();
        chezu = bsd_mrkx_tv_chezu.getText().toString();
        chexing = bsd_mrkx_tv_chexing.getText().toString();
        if (bsd_mrkx_tv_pp.getText().toString().equals("") ||
                bsd_mrkx_tv_chexi.getText().toString().equals("") ||
                bsd_mrkx_tv_chezu.getText().toString().equals("") ||
                bsd_mrkx_tv_chexing.getText().toString().equals("")

                ) {
            che_cx = "";
        } else {
            che_cx = pinpai + "|" + chexi + "|" + chezu + "|" + chexing;
        }
        VIN = bsd_mrkx_et_vin.getText().toString().trim();
        jinchanglicheng = bsd_mrkx_ed_lc.getText().toString().trim();
        chezhu = bsd_mrkx_ed_lianxiren.getText().toString().trim();
        dinahua = bsd_mrkx_lianxifangshi.getText().toString().trim();
        cardNo = et_huiyuankahao.getText().toString().trim();

        AbRequestParams params = new AbRequestParams();
        params.put("gongsiNo", MyApplication.shared.getString("GongSiNo", ""));
        params.put("caozuoyuan_xm", MyApplication.shared.getString("name", ""));
        params.put("che_no", chepai);
        params.put("work_no", Conts.work_no);
        params.put("che_cx", che_cx);
        params.put("che_vin", VIN);
        params.put("xche_lc", jinchanglicheng);
        params.put("kehu_mc", chezhu);
        params.put("kehu_dh", dinahua);
        params.put("card_no", cardNo);
        params.put("kehu_no", Conts.kehu_no);
        params.put("cangk_dm", ckid);
        params.put("xche_sfbz", gongshifeili_id);
        params.put("gcsj", bsd_mrxk_tv_djtime.getText().toString());
        Log.i("cjn", "查看车牌号" + chepai);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_MRKX_UP, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                boolean status = s.contains("保存成功");
                if (status) {
                    queRen = new QueRen(getActivity(), "存档成功");
                    queRen.show();
                    queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                        @Override
                        public void onYesClick() {
                            queRen.dismiss();
                        }
                    });

                } else {
                    System.out.println("不包含");
                }
                Log.i("cjn", "成功" + s);
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
     * 返回时的保存，防止返回丢失数据
     */
    public void saveBillInfoBeforeBack() {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "保存中...");
        danhao = billEntiy.getWork_no();
        chepai = bsd_ywwwdd_cp.getText().toString();
        pinpai = bsd_mrkx_tv_pp.getText().toString();
        chexi = bsd_mrkx_tv_chexi.getText().toString();
        chezu = bsd_mrkx_tv_chezu.getText().toString();
        chexing = bsd_mrkx_tv_chexing.getText().toString();
        if (bsd_mrkx_tv_pp.getText().toString().equals("") ||
                bsd_mrkx_tv_chexi.getText().toString().equals("") ||
                bsd_mrkx_tv_chezu.getText().toString().equals("") ||
                bsd_mrkx_tv_chexing.getText().toString().equals("")) {
            che_cx = "";
        } else {
            che_cx = pinpai + "|" + chexi + "|" + chezu + "|" + chexing;
        }
        VIN = bsd_mrkx_et_vin.getText().toString().trim();
        jinchanglicheng = bsd_mrkx_ed_lc.getText().toString().trim();
        chezhu = bsd_mrkx_ed_lianxiren.getText().toString().trim();
        dinahua = bsd_mrkx_lianxifangshi.getText().toString().trim();
        cardNo = et_huiyuankahao.getText().toString().trim();
        AbRequestParams params = new AbRequestParams();
        params.put("gongsiNo", MyApplication.shared.getString("GongSiNo", ""));
        params.put("caozuoyuan_xm", MyApplication.shared.getString("name", ""));
        params.put("che_no", chepai);
        params.put("work_no", Conts.work_no);
        params.put("che_cx", che_cx);
        params.put("che_vin", VIN);
        params.put("xche_lc", jinchanglicheng);
        params.put("kehu_mc", chezhu);
        params.put("kehu_dh", dinahua);
        params.put("card_no", cardNo);
        params.put("kehu_no", Conts.kehu_no);
        params.put("cangk_dm", ckid);
        params.put("xche_sfbz", gongshifeili_id);
        params.put("gcsj", bsd_mrxk_tv_djtime.getText().toString());
        params.put("xche_bz", bsd_mrkx_et_miaoshu.getText().toString().trim());
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_MRKX_UP, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                WeiboDialogUtils.closeDialog(mWeiboDialog);
                boolean status = data.contains("保存成功");
                // 保存成功则跳转fragment，应该没有失败的情况，有再添加
                if (status) {
                    BSD_wxcl = null;
                    BSD_wxxm = null;
                    ((MainActivity) getActivity()).upBSD_MRKX_log();
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
                Toast.makeText(getHostActicity(), "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveBillInfo() {
        if (isSaveBill) {
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "保存中...");
        } else {
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "结算中...");
        }
        danhao = billEntiy.getWork_no();
        chepai = bsd_ywwwdd_cp.getText().toString();
        pinpai = bsd_mrkx_tv_pp.getText().toString();
        chexi = bsd_mrkx_tv_chexi.getText().toString();
        chezu = bsd_mrkx_tv_chezu.getText().toString();
        chexing = bsd_mrkx_tv_chexing.getText().toString();
        if (bsd_mrkx_tv_pp.getText().toString().equals("") ||
                bsd_mrkx_tv_chexi.getText().toString().equals("") ||
                bsd_mrkx_tv_chezu.getText().toString().equals("") ||
                bsd_mrkx_tv_chexing.getText().toString().equals("")) {
            che_cx = "";
        } else {
            che_cx = pinpai + "|" + chexi + "|" + chezu + "|" + chexing;
        }
        VIN = bsd_mrkx_et_vin.getText().toString().trim();
        jinchanglicheng = bsd_mrkx_ed_lc.getText().toString().trim();
        chezhu = bsd_mrkx_ed_lianxiren.getText().toString().trim();
        dinahua = bsd_mrkx_lianxifangshi.getText().toString().trim();
        cardNo = et_huiyuankahao.getText().toString().trim();
        AbRequestParams params = new AbRequestParams();
        params.put("gongsiNo", MyApplication.shared.getString("GongSiNo", ""));
        params.put("xche_jsr", MyApplication.shared.getString("name", ""));
        params.put("che_no", chepai);
        params.put("work_no", billEntiy.getWork_no());
        params.put("che_cx", che_cx);
        params.put("che_vin", VIN);
        params.put("xche_lc", jinchanglicheng);
        params.put("kehu_mc", chezhu);
        params.put("kehu_dh", dinahua);
        params.put("card_no", cardNo);
        params.put("kehu_no", billEntiy.getKehu_no());
        params.put("cangk_dm", ckid);
        params.put("xche_sfbz", gongshifeili_id);
        params.put("gcsj", bsd_mrxk_tv_djtime.getText().toString());
        params.put("xche_bz", bsd_mrkx_et_miaoshu.getText().toString().trim());
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_MRKX_UP, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                WeiboDialogUtils.closeDialog(mWeiboDialog);
                boolean status = data.contains("保存成功");
                if (status) {
                    //点击存档操作的时候走的步骤
                    if (isSaveBill) {
                        queRen = new QueRen(getContext(), "存档成功");
                        queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                            @Override
                            public void onYesClick() {
                                queRen.dismiss();
                            }
                        });
                        queRen.show();
                    } else { //点击结算的时候
                        if (Conts.BSD_zhongxiaoweixiu == 1) {
                            bsd_mrkx_jiesuan = new BSD_mrkx_jiesuan(getActivity(), Conts.MRKX_kahao, Conts.MRKX_shengYu_jinQian, zong_zj);
                            bsd_mrkx_jiesuan.show();
                            bsd_mrkx_jiesuan.setJieSuan(new BSD_mrkx_jiesuan.JieSuan() {
                                @Override
                                public void onyes(double xche_hjje, double xche_ssje, double xche_wxxm_yhje, double xche_peij_yhje, double xche_ysje, String card_no, String mima, int iscard, double bxj, double zhifu_card_je, double zhifu_card_xj) {
                                    bsd_xche_hjje = xche_hjje;
                                    bsd_xche_ssje = xche_ssje;
                                    bsd_xche_wxxm_yhje = xche_wxxm_yhje;
                                    bsd_xche_peij_yhje = xche_peij_yhje;
                                    bsd_xche_ysje = xche_ysje;
                                    bsd_card_no = card_no;
                                    bsd_mima = mima;
                                    bsd_iscard = iscard;
                                    bsd_bxj = bxj;
                                    bsd_zhifu_card_je = zhifu_card_je;
                                    bsd_zhifu_card_xj = zhifu_card_xj;
                                    jieSuan1();
                                }
                            });
                            bsd_mrkx_jiesuan.setGb(new BSD_mrkx_jiesuan.Guanbi() {
                                @Override
                                public void guanbi() {
                                    bsd_zadd_wg.setEnabled(true);
                                }
                            });
                        } else if (Conts.BSD_zhongxiaoweixiu == 0) {
                            Log.i("cjn", "@22222");
                            bsd_mrkx_jiesuan11 = new BSD_mrkx_jiesuan11(getActivity(), Conts.MRKX_kahao, Conts.MRKX_shengYu_jinQian, zong_zj);
                            bsd_mrkx_jiesuan11.show();
                            bsd_mrkx_jiesuan11.setJieSuan(new BSD_mrkx_jiesuan11.JieSuan() {
                                @Override
                                public void onyes(double xche_hjje, double xche_ssje, double xche_wxxm_yhje, double xche_peij_yhje, double xche_ysje) {
                                    bsd_xche_hjje = xche_hjje;
                                    bsd_xche_ssje = xche_ssje;
                                    bsd_xche_wxxm_yhje = xche_wxxm_yhje;
                                    bsd_xche_peij_yhje = xche_peij_yhje;
                                    bsd_xche_ysje = xche_ysje;
                                    jieSuan11();

                                }
                            });
                        }
                    }
                } else {
                    System.out.println("不包含");
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

    /**
     * 基本信息车系接口
     */
    public void getCheXiData(String cxbianhao) {
        listjbcx.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("dm", cxbianhao);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_CX, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aa, String s) {

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(s);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject item = jsonarray.getJSONObject(i);
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("chex_dm", item.getString("chex_dm"));
                            map.put("chex_mc", item.getString("chex_mc"));
                            listjbcx.add(map);
                        }
                    }
                    updateCheXiData();
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
     * 基本信息车系接口
     */
    public void updateCheXiData() {
        nameList.clear();
        for (int i = 0; i < listjbcx.size(); i++) {
            CustemObject object = new CustemObject();
            object.data = listjbcx.get(i).get("chex_mc");
            nameList.add(object);
        }
        mAdapter = new CustemSpinerAdapter(getActivity());
        mAdapter.refreshData(nameList, 0);
        mSpinerPopWindow = new SpinerPopWindow(getActivity());
        mSpinerPopWindow.setAdatper(mAdapter, 310);
        mSpinerPopWindow.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
            @Override
            public void onItemClick(int pos) {
                String clickValue = nameList.get(pos).toString();
                if (!bsd_mrkx_tv_chexi.getText().toString().equals(clickValue)) {
                    bsd_mrkx_tv_chexi.setText(clickValue);
                    chexiid = listjbcx.get(pos).get("chex_dm");
                    bsd_mrkx_tv_chezu.setText("");
                    bsd_mrkx_tv_chexing.setText("");
                }
            }
        });
        mSpinerPopWindow.setWidth(ll_chexi.getWidth());
        mSpinerPopWindow.showAsDropDown(ll_chexi);
    }


    /**
     * 根据车系，获取车组信息
     * @param chexiid 车系ID
     */
    public void getCheZuData(String chexiid) {
        AbRequestParams params = new AbRequestParams();
        params.put("dm", chexiid);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_CZ, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aa, String s) {
                listjbcz.clear();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject item = jsonarray.getJSONObject(i);
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("chex_dm", item.getString("chex_dm"));
                            map.put("chex_mc", item.getString("chex_mc"));
                            listjbcz.add(map);
                        }
                    }
                    updateCheZuData();
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
                Show.showTime(getHostActicity(), "网络请求超时");
            }
        });


    }

    /**
     * 基本信息车组接口
     */
    public void updateCheZuData() {
        nameList1.clear();
        for (int i = 0; i < listjbcz.size(); i++) {
            CustemObject object = new CustemObject();
            object.data = listjbcz.get(i).get("chex_mc");
            nameList1.add(object);
        }
        mAdapter1 = new CustemSpinerAdapter(getActivity());
        mAdapter1.refreshData(nameList1, 0);
        mSpinerPopWindow1 = new SpinerPopWindow(getActivity());
        mSpinerPopWindow1.setAdatper(mAdapter1, 310);
        mSpinerPopWindow1.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
            @Override
            public void onItemClick(int pos) {
                String clickValue = nameList1.get(pos).toString();
                if (!bsd_mrkx_tv_chezu.getText().toString().equals(clickValue)) {
                    bsd_mrkx_tv_chezu.setText(clickValue);
                    bsd_mrkx_tv_chexing.setText("");
                    chezuid = listjbcz.get(pos).get("chex_dm");
                }
            }
        });
        mSpinerPopWindow1.setWidth(ll_chezu.getWidth());
        mSpinerPopWindow1.showAsDropDown(ll_chezu);
    }


    /**
     * 根据车组，获取车型数据
     * @param chezuid
     */
    public void getCheXingData(String chezuid) {
        AbRequestParams params = new AbRequestParams();
        params.put("dm", chezuid);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_Chexing, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aa, String s) {
                listjbchexing.clear();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject item = jsonarray.getJSONObject(i);
                            Map<String, String> map = new HashMap<String, String>();
                            if (item.getString("chex_mc_std").length() > 1) {
                                String a = item.getString("chex_mc") + "【" + item.getString("chex_mc_std") + "】";
                                map.put("chex_dm", a);
                                map.put("chex_mc", a);
                                map.put("chex_bz", item.getString("chex_bz"));
                                map.put("chex_mc_std", item.getString("chex_mc_std"));
                            } else {
                                map.put("chex_dm", item.getString("chex_dm"));
                                map.put("chex_mc", item.getString("chex_mc"));
                                map.put("chex_bz", item.getString("chex_bz"));
                                map.put("chex_mc_std", item.getString("chex_mc_std"));
                            }
                            listjbchexing.add(map);
                        }
                    }
                    updateCheXingData();
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
                Show.showTime(getActivity(), "网络请求超时");
            }
        });


    }

    /**
     * 更新车型的信息，下拉内容
     */
    public void updateCheXingData() {
        nameList2.clear();
        for (int i = 0; i < listjbchexing.size(); i++) {
            CustemObject object = new CustemObject();
            object.data = listjbchexing.get(i).get("chex_mc");
            nameList2.add(object);
        }
        mAdapter2 = new CustemSpinerAdapter(getActivity());
        mAdapter2.refreshData(nameList2, 0);
        mSpinerPopWindow2 = new SpinerPopWindow(getActivity());
        mSpinerPopWindow2.setAdatper(mAdapter2, 310);
        mSpinerPopWindow2.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
            @Override
            public void onItemClick(int pos) {
                String clickValue = nameList2.get(pos).toString();
                if (!bsd_mrkx_tv_chexing.getText().toString().equals(clickValue)) {
                    bsd_mrkx_tv_chexing.setText(clickValue);
                    pinpai = bsd_mrkx_tv_pp.getText().toString();
                    chexi = bsd_mrkx_tv_chexi.getText().toString();
                    chezx = bsd_mrkx_tv_chezu.getText().toString();
                    chexing = bsd_mrkx_tv_chexing.getText().toString();
                    if (bsd_mrkx_tv_pp.getText().toString().equals("") ||
                            bsd_mrkx_tv_chexi.getText().toString().equals("") ||
                            bsd_mrkx_tv_chezu.getText().toString().equals("") ||
                            bsd_mrkx_tv_chexing.getText().toString().equals("")) {
                        che_cx = "";
                    } else {
                        che_cx = pinpai + "|" + chexi + "|" + chezx + "|" + chexing;
                        Conts.chexing = che_cx;
                    }
                    chexingid = listjbchexing.get(pos).get("chex_bz");
                }

            }
        });
        mSpinerPopWindow2.setWidth(ll_chexing.getWidth());
        mSpinerPopWindow2.showAsDropDown(ll_chexing);
    }

    /**
     * 修改工时
     *
     * @param reco_no
     * @param je
     * @param gs
     * @param position
     */
    public void updatePaigongInfoToDB(int reco_no, final double je, final double gs, final int position) {
        AbRequestParams params = new AbRequestParams();
        params.put("id", reco_no);
        params.put("je", je + "");
        params.put("gs", gs + "");
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_GsDj_xiugai, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                listPGrenyuan.get(position).put("paig_khgs", gs + "");
                listPGrenyuan.get(position).put("paig_khje", je + "");
                paiGongInfoAdapter.notifyDataSetChanged();
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

    /**
     * 派工人员详细
     *
     * @param work_no
     * @param wxxm_no
     */
    public void PGRenYuan(String work_no, String wxxm_no, double wxxm_gs, double wxxm_je) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
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
                            WeiboDialogUtils.closeDialog(mWeiboDialog);
                        }
                        paiGongInfoAdapter.setList(listPGrenyuan);
                        paiGongInfoAdapter.notifyDataSetChanged();
                    }

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
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });
    }


    //切换碎片事务的方法
    private void change_XM() {
        tv_wxxmAdd.setTextColor(getResources().getColor(R.color.bsd_xz_yes));
        tv_wxllAdd.setTextColor(getResources().getColor(R.color.transparent_background));
        iv_wxxmAdd.setVisibility(View.VISIBLE);
        iv_wxllAdd.setVisibility(View.INVISIBLE);
        tv_paigongAll.setVisibility(View.VISIBLE);
        transaction = fragmentManager.beginTransaction();
        if (!BSD_wxxm.isAdded()) {
            transaction.add(R.id.bsd_clxq_lswx, BSD_wxxm);
        }
        transaction.hide(BSD_wxcl);
        transaction.show(BSD_wxxm);
        transaction.commit();
    }

    /**
     * 获取派工人员的信息
     *
     * @param workNo
     * @param wxxmNo
     */
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
                paiGongInfoAdapter.notifyDataSetChanged();
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


    //切换碎片事务的方法
    private void change_CL() {
        tv_wxllAdd.setTextColor(getResources().getColor(R.color.bsd_xz_yes));
        tv_wxxmAdd.setTextColor(getResources().getColor(R.color.transparent_background));
        iv_wxllAdd.setVisibility(View.VISIBLE);
        iv_wxxmAdd.setVisibility(View.INVISIBLE);
        tv_paigongAll.setVisibility(View.INVISIBLE);
        transaction = fragmentManager.beginTransaction();
        if (!BSD_wxcl.isAdded()) {
            transaction.add(R.id.bsd_clxq_lswx, BSD_wxcl);
        }
        transaction.hide(BSD_wxxm);
        transaction.show(BSD_wxcl);
        transaction.commit();
        isShowWxcl = true;
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
            case R.id.ll_pinpai:
                showPinPaiDialog();
                break;
            case R.id.ll_chexi:
                showCheXiData();
                break;
            case R.id.ll_chezu:
                showCheZuData();
                break;
            case R.id.ll_chexing:
                showCHeXingData();
                break;
            case R.id.tv_readcard:
                readCardInfo();
                break;
            case R.id.ll_stock:
                getStockInfo();
                break;
            case R.id.ll_gsfl:
                getGSFLData();
                break;
            case R.id.tv_removecard:
                removeCardInfo();
                break;
            case R.id.tv_repairhistory:
                BSD_LishiWeiXiu_DialogFragment.newInstance(billEntiy.getChe_no()).show(getFragmentManager(), "mrkx_lswx");
                break;
        }
    }

    private void removeCardInfo() {
        if (TextUtils.isEmpty(billEntiy.getCard_no()) && TextUtils.isEmpty(et_huiyuankahao.getText().toString())) {
            Toast.makeText(getActivity(), "此单据未使用会员卡", Toast.LENGTH_SHORT).show();
        } else {
            quedingQuxiao = new Queding_Quxiao(getActivity(), "确认取消会员卡优惠吗？");
            quedingQuxiao.setOnResultClickListener(new Queding_Quxiao.OnResultClickListener() {
                @Override
                public void onConfirm() {
                    et_huiyuankahao.setText("");
                    if (!TextUtils.isEmpty(billEntiy.getCard_no())) {
                        BSD_wxxm.updateWxxmZK(1);
                        BSD_wxcl.updateWxclZK(1);
                    }
                    quedingQuxiao.dismiss();
                }

                @Override
                public void onCancel() {
                    quedingQuxiao.dismiss();
                }
            });
            quedingQuxiao.show();
        }
    }

    private void readCardInfo() {
        if (TextUtils.isEmpty(et_huiyuankahao.getText().toString())) {
            Show.showTime(getHostActicity(), "请输入会员卡卡号");
        } else {
            getCardInfo(et_huiyuankahao.getText().toString(), false);
        }
    }

    private void showCHeXingData() {
        if (TextUtils.isEmpty(bsd_mrkx_tv_chezu.getText().toString())) {
            Show.showTime(getActivity(), "请选择车组");
        } else {
            getCheXingData(bsd_mrkx_tv_chezu.getText().toString());
        }
    }

    private void showCheZuData() {
        if (TextUtils.isEmpty(bsd_mrkx_tv_chexi.getText().toString())) {
            Show.showTime(getActivity(), "请选择车系");
        } else {
            getCheZuData(bsd_mrkx_tv_chexi.getText().toString());
        }
    }

    private void showCheXiData() {
        if (TextUtils.isEmpty(bsd_mrkx_tv_pp.getText().toString())) {
            Show.showTime(getActivity(), "请选择品牌");
        } else {
            getCheXiData(bsd_mrkx_tv_pp.getText().toString());
        }
    }

    private void showPinPaiDialog() {
        pinpaiInfoDialog = new PinpaiInfoDialog(getActivity());
        pinpaiInfoDialog.setToopromtOnClickListener(new PinpaiInfoDialog.ToopromtOnClickListener() {
            @Override
            public void onYesClick(String aa, String bianhao) {
                cxbianhao = bianhao;//车牌编号
                pinpaiming = aa;//车牌名称
                bsd_mrkx_tv_pp.setText(pinpaiming);
                bsd_mrkx_tv_chexi.setText("");
                bsd_mrkx_tv_chezu.setText("");
                bsd_mrkx_tv_chexing.setText("");
                pinpaiInfoDialog.dismiss();
            }
        });
        pinpaiInfoDialog.show();
    }

    /**
     * 显示维修领料窗口
     */
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
                        wxclItem.setPeij_dj(item.getPeij_ydj() * currentWxclZk);
                        wxclItem.setPeij_zk(currentWxclZk);
                        wxclItem.setPeij_je(addPeijSl * item.getPeij_ydj() * currentWxclZk);
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
                    //在上面refreashData已经计算了总金额了
//                    // 2.计算维修单的总金额
//                    double addPrice = 0;
//                    for (BSD_WeiXiuJieDan_CL_Entity item : needAddList) {
//                        addPrice = addPrice + item.getPeij_je();
//                    }
//                    // 如果不等于0，则更新到总金额
//                    if (addPrice != 0) {
//                        billEntiy.setXche_hjje(billEntiy.getXche_hjje() + addPrice);
//                        bsd_mrkx_zongjia.setText(billEntiy.getXche_hjje() + "元");
//                    }
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

    /**
     * 显示维修项目窗口
     */
    private void showWxxm() {
        bsd_zcduxq_xm_pop = new BSD_ZCDUXQ_XM_POP(getActivity());
        // 添加这就代码，解决PopWindow被软键盘顶上去的问题，
//        bsd_zcduxq_xm_pop.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
//        bsd_zcduxq_xm_pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
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
                        wxxmItem.setWxxm_cb(item.getWxxm_cb());
                        wxxmItem.setWxxm_zk(currentWxxmZk);
                        wxxmItem.setWxxm_je(currentWxxmZk * item.getWxxm_zddj());
                        wxxmItem.setWxxm_dj(currentWxxmZk * item.getWxxm_zddj() / item.getWxxm_gs());
                        wxxmItem.setWxxm_zt("正常");
                        wxxmItem.setWxxm_Tpye("正常");
                        needAddList.add(wxxmItem);
                    }
                }
                // 请求接口，把数据添加到数据库中
                if (needAddList.size() > 0) {
                    saveNewWxxmToDB(needAddList);
                }
            }
        });
//        bsd_zcduxq_xm_pop.showPopupWindow(beijing, 0);
        // 用下面这个显示方式，上面的显示方式会出现：键盘关闭时popwindow显示后面的activity，而不能恢复到原来的样子
        bsd_zcduxq_xm_pop.showAtLocation(beijing, Gravity.TOP, 0, 0);
    }

    /**
     * 保存新的维修项目到数据库中
     * @param needAddList 需要保存的维修项目
     */
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
                    // 1.将新维修项目添加到work_ll_gz的集合中;
                    list_XM.addAll(needAddList);
                    BSD_wxxm.refreashData();
                    // 2.计算维修单的总金额
//                    double addPrice = 0;
//                    for (BSD_WeiXiuJieDan_XM_Entity item : needAddList) {
//                        addPrice = addPrice + item.getWxxm_je();
//                    }
//                    // 如果不等于0，则更新到总金额
//                    if (addPrice != 0) {
//                        billEntiy.setXche_hjje(billEntiy.getXche_hjje() + addPrice);
//                        bsd_mrkx_zongjia.setText(billEntiy.getXche_hjje() + "元");
//                    }
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

    /**
     * 删除派工人员
     *
     * @param reco_no
     * @param position
     */
    public void deletePaigongInfo(int reco_no, final int position) {
        AbRequestParams params = new AbRequestParams();
        params.put("xxNo", reco_no);//id
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_paigongdelPgxx, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                listPGrenyuan.remove(position);
                paiGongInfoAdapter.notifyDataSetChanged();
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

    String getCar;

    public void bsd_cexifansuan() {
        AbRequestParams params = new AbRequestParams();
        params.put("quanMing", bsd_mrkx_tv_chexing.getText().toString());
        Log.i("cjn", "bsd_ksbj_cxing.getText().toString()" + bsd_mrkx_tv_chexing.getText().toString());
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_chexing_fansuan, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("cjn", "查看" + s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
//                if (jsonObject.getString("message").toString().equals("查询成功")){
                    getCar = jsonObject.getString("data");
                    if (null != getCar) {
                        if (getCar.length() > 0) {
                            chexingid = getCar;
                        }
                        Conts.bycx_chexing = chexingid;
                        Conts.bycx_type = 3;//快速报价
                        Log.i("lym", "-------" + Conts.bycx_chexing);
                        int a;
                        double b;
                        b = Double.parseDouble(bsd_mrkx_ed_lc.getText().toString().trim());
                        a = (int) b;
                        Conts.bycx_licheng = "" + a;//里程
//                    Conts.bycx_licheng = bsd_mrkx_ed_lc.getText().toString().trim();//里程
                        Conts.bycx_time = bsd_mrxk_tv_djtime.getText().toString().trim();//时间
                        Conts.bycx_pinpai = bsd_mrkx_tv_pp.getText().toString().trim();
                        Conts.bycx_chexi = bsd_mrkx_tv_chexi.getText().toString().trim();
                        Conts.bycx_chezu = bsd_mrkx_tv_chezu.getText().toString().trim();
                        Conts.bycx_cxing = bsd_mrkx_tv_chexing.getText().toString().trim();
                        Conts.bycx_VIN = bsd_mrkx_et_vin.getText().toString().trim();
                        Conts.bycx_CarName = bsd_mrkx_ed_lianxiren.getText().toString().trim();
                        Conts.bycx_Shouji = bsd_mrkx_lianxifangshi.getText().toString().trim();
                        Conts.bycx_huiyuan = et_huiyuankahao.getText().toString();
                        Conts.bycx_gongshifeili = bsd_mrkx_tv_gsfl.getText().toString();
//                    Conts.bycx_cangku = bsd_mrkx_tv_ck.getText().toString();

                        ((MainActivity) getActivity()).upBSD_bycx();


                    }
//                }


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


    @Override
    public void onDestroy() {
        BSD_wxxm = null;
        BSD_wxcl = null;
        super.onDestroy();
    }
}
