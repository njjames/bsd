package com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.example.administrator.boshide2.Conts;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Activity.MainActivity;
import com.example.administrator.boshide2.Modular.Adapter.AbstractSpinerAdapter;
import com.example.administrator.boshide2.Modular.Adapter.CustemSpinerAdapter;
import com.example.administrator.boshide2.Modular.Entity.CustemObject;
import com.example.administrator.boshide2.Modular.Fragment.BaseFragment;
import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.PopWinow.BSD_KSBJ_CL_POP;
import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.PopWinow.BSD_KSBJ_PinPai_delo;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.dialogFragment.BSD_LiShiWeiXiuJianYi_DialogFragment;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.dialogFragment.BSD_LishiWeiXiu_DialogFragment;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.dialogFragment.BSD_MeiRongKuaiXiu_KuCun_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.dialogFragment.BSD_MeiRongKuaiXiu_cheliangxinxi_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Adapter.BSD_WXJD_CL_adp;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Adapter.BSD_WXJD_XM_adp;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_CL_Entity;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_Entity;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_XM_Entity;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.BSD_WXYY_XM_POP;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.BSD_XiuGaiGongShi;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.Pop_Entity.BSD_wxyy_cl_pop_entity;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.Pop_Entity.BSD_wxyy_xm_pop_entiy;
import com.example.administrator.boshide2.Modular.View.SpinerPopWindow;
import com.example.administrator.boshide2.Modular.View.Time.TimeDialog;
import com.example.administrator.boshide2.Modular.View.diaog.QueRen;
import com.example.administrator.boshide2.Modular.View.diaog.Queding_Quxiao;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.QuanQuan.WeiboDialogUtils;
import com.example.administrator.boshide2.Tools.Show;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @维修接单碎片页 Created by Administrator on 2017-4-13.
 */

public class BSD_WeiXiuJieDan_Fragment extends BaseFragment implements View.OnClickListener {
    private static final String PARAM_KEY = "param_key";
    ScrollView scrollview;
    RelativeLayout beijing;
    private TextView bsd_wxjd_tv_chepai;//车牌
    private EditText et_jinchanglicheng;//进厂里程
    private EditText et_kehumc;//车主
    private EditText et_shouji;//手机
    private EditText et_vin;//VIN码
    private TextView tv_pinpai;//品牌
    private TextView tv_chexi;//车系
    private TextView tv_chezu;//车组
    private TextView tv_chexing;//车型
    private EditText bsd_wxjd_et_qiankuan;//欠款
    private EditText et_cardno;//会员卡号
    TextView tv_wxjd_clhj, tv_wxjd_xmhj, tv_wxjd_hj;//项目金额。材料金额，总金额
    //车牌号
    String Car;
    //维修接单基本信息实体类
    BSD_WeiXiuJieDan_Entity entity;
    private ListView listViewXM;//维修项目
    private ListView listViewCL;//维修材料
    private BSD_WXJD_XM_adp adp_xm;
    private BSD_WXJD_CL_adp adp_cl;
    private List<BSD_WeiXiuJieDan_XM_Entity> list_XM = new ArrayList<>();
    private List<BSD_WeiXiuJieDan_CL_Entity> list_CL = new ArrayList<>();
    TextView bsd_wxjd_save;
    public int jc_or_cd;
    TextView bsd_wxjd_cj;
    //修改工时弹框
    BSD_XiuGaiGongShi bsd_xiuGaiGongShi;
    private Dialog mWeiboDialog, mWeiboDialog2;
    private QueRen queRen;
    private LinearLayout ll_pinpai;
    private LinearLayout ll_chexi;
    private LinearLayout ll_chezu;
    private LinearLayout ll_chexing;
    //品牌 车系车组 车行
    BSD_KSBJ_PinPai_delo bsd_ksbj_pinPai_delo;
    String cxbianhao;
    String pinpaiming;
    //这是车系
    List<Map<String, String>> listjbcx = new ArrayList<Map<String, String>>();
    private List<CustemObject> nameList = new ArrayList<CustemObject>();
    private AbstractSpinerAdapter mAdapter;
    private SpinerPopWindow mSpinerPopWindow;
    String chexiid;
    //车组
    List<Map<String, String>> listjbcz = new ArrayList<Map<String, String>>();
    String chezuid;
    private List<CustemObject> nameList1 = new ArrayList<CustemObject>();
    private AbstractSpinerAdapter mAdapter1;
    private SpinerPopWindow mSpinerPopWindow1;
    //车型
    List<Map<String, String>> listjbchexing = new ArrayList<Map<String, String>>();
    String chexingid;
    private List<CustemObject> nameList2 = new ArrayList<CustemObject>();
    private AbstractSpinerAdapter mAdapter2;
    private SpinerPopWindow mSpinerPopWindow2;
    //工时费率
    LinearLayout ll_gsfl;
    TextView tv_gsfl;

    List<Map<String, String>> listgslv = new ArrayList<Map<String, String>>();

    private List<CustemObject> nameList3 = new ArrayList<CustemObject>();
    private AbstractSpinerAdapter mAdapter3;
    private SpinerPopWindow mSpinerPopWindow3;
    String gongshifeili_name;
    String gongshifeili_id;

    LinearLayout ll_gcsj;
    TextView bsd_wxjd_rl_bycx;
    TextView tv_gcsj;
    EditText et_niankuan;
    EditText et_cunyou;
    EditText et_color;
    EditText et_miaoshu;
    TimeDialog timePickerShow;
    Queding_Quxiao queRen_quxiao;
    Queding_Quxiao queding_quxiao;
    private String isPrint;
    URLS url;

    //根据VIN返回车型信息
    List<Map<String, String>> listvincx = new ArrayList<>();
    String cxnm;      //车型内码
    TextView rl_duqu;

    //车辆信息、历史维修、历史维修建议
    private TextView bsd_wxjd_clxx, bsd_wxjd_lswxjy, bsd_wxjd_lswx;
    private String params;
    private BSD_WeiXiuJieDan_Entity billEntiy;
    private TextView title;
    private TextView footerText;
    private TextView billNo;

    public static BSD_WeiXiuJieDan_Fragment newInstance(String params) {
        BSD_WeiXiuJieDan_Fragment fragment = new BSD_WeiXiuJieDan_Fragment();
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
        return R.layout.bsd_wxjd_fragment;
    }

    @Override
    public void initView() {
        bsd_wxjd_rl_bycx = (TextView) view.findViewById(R.id.bsd_wxjd_rl_bycx);
        tv_gcsj = (TextView) view.findViewById(R.id.tv_gcsj);
        ll_gcsj = (LinearLayout) view.findViewById(R.id.ll_gcsj);
        ll_gcsj.setOnClickListener(this);
        //读取vin码
        rl_duqu = (TextView) view.findViewById(R.id.tv_readvin);
        rl_duqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vin = et_vin.getText().toString();
                if ("".equals(vin)) {
                    Toast.makeText(getActivity(), "请输入vin码", Toast.LENGTH_LONG).show();
                } else {
                    duVin();
                }
            }
        });

        //车辆信息、历史维修、历史维修建议
        bsd_wxjd_clxx = (TextView) view.findViewById(R.id.bsd_wxjd_clxx);
        bsd_wxjd_clxx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "车辆信息", Toast.LENGTH_LONG).show();
                //跳转到编辑车辆、客户信息界面
                Conts.danju_type = "wxjd";

                //跳转到编辑车辆、客户信息对话框
                //隐藏输入法
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

                new BSD_MeiRongKuaiXiu_cheliangxinxi_Fragment()
                        .show(getFragmentManager(), "dialog_fragment");

            }
        });
        bsd_wxjd_lswx = (TextView) view.findViewById(R.id.bsd_wxjd_lswx);
        bsd_wxjd_lswx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Conts.danju_type = "wxjd";
                new BSD_LishiWeiXiu_DialogFragment().
                        show(getFragmentManager(), "mrkx_lswx");
            }
        });
        bsd_wxjd_lswxjy = (TextView) view.findViewById(R.id.bsd_wxjd_lswxjy);
        bsd_wxjd_lswxjy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BSD_LiShiWeiXiuJianYi_DialogFragment().
                        show(getFragmentManager(), "mrkx_lswxjy");
            }
        });

        //进厂
        bsd_wxjd_cj = (TextView) view.findViewById(R.id.bsd_wxjd_cj);
        bsd_wxjd_cj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //发送微信
                weixin();

                    //by  李赛
                    queding_quxiao = new Queding_Quxiao(getActivity(), "需要打印单据吗？");
                    queding_quxiao.show();
                    queding_quxiao.setOnResultClickListener(new Queding_Quxiao.OnResultClickListener() {
                        @Override
                        public void onConfirm() {
                            queding_quxiao.dismiss();
                            //调用打印接口
                            print();
                            jc_or_cd = 1;
                            updata();
                        }

                        @Override
                        public void onCancel() {
                            queding_quxiao.dismiss();
                            jc_or_cd = 1;
                            updata();
                        }
                    });

                }
        });

        et_niankuan = (EditText) view.findViewById(R.id.et_niankuan);
        et_cunyou = (EditText) view.findViewById(R.id.et_cunyou);
        et_color = (EditText) view.findViewById(R.id.et_color);
        et_miaoshu = (EditText) view.findViewById(R.id.et_miaoshu);
        ll_gsfl = (LinearLayout) view.findViewById(R.id.ll_gsfl);
        tv_gsfl = (TextView) view.findViewById(R.id.tv_gsfl);
        ll_gsfl.setOnClickListener(this);
        bsd_wxjd_save = (TextView) view.findViewById(R.id.bsd_wxjd_save);
        bsd_wxjd_save.setOnClickListener(this);
        bsd_wxjd_tv_chepai = (TextView) view.findViewById(R.id.bsd_wxjd_tv_chepai);
        et_jinchanglicheng = (EditText) view.findViewById(R.id.et_jinchanglicheng);
        et_kehumc = (EditText) view.findViewById(R.id.et_kehumc);
        et_shouji = (EditText) view.findViewById(R.id.et_shouji);
        et_vin = (EditText) view.findViewById(R.id.et_vin);
        tv_pinpai = (TextView) view.findViewById(R.id.tv_pinpai);
        tv_chexi = (TextView) view.findViewById(R.id.tv_chexi);
        tv_chezu = (TextView) view.findViewById(R.id.tv_chezu);
        tv_chexing = (TextView) view.findViewById(R.id.tv_chexing);
        et_cardno = (EditText) view.findViewById(R.id.et_cardno);
        tv_wxjd_clhj = (TextView) view.findViewById(R.id.tv_wxjd_clhj);
        tv_wxjd_hj = (TextView) view.findViewById(R.id.tv_wxjd_hj);
        tv_wxjd_xmhj = (TextView) view.findViewById(R.id.tv_wxjd_xmhj);
        ll_pinpai = (LinearLayout) view.findViewById(R.id.ll_pinpai);
        ll_chexi = (LinearLayout) view.findViewById(R.id.ll_chexi);
        ll_chezu = (LinearLayout) view.findViewById(R.id.ll_chezu);
        ll_chexing = (LinearLayout) view.findViewById(R.id.ll_chexing);

        listViewXM = (ListView) view.findViewById(R.id.lv_wxxm);
        listViewCL = (ListView) view.findViewById(R.id.lv_wxcl);
        adp_cl = new BSD_WXJD_CL_adp(getContext(), list_CL);
        adp_cl.setJia_jian(new BSD_WXJD_CL_adp.Jia_Jian() {
            @Override
            public void onYesClick() {
                wxjd_cl_money();
            }
        });

        adp_cl.setUpdanjia(new BSD_WXJD_CL_adp.Updanjia() {
            @Override
            public void onYesClick(final int i, String name, double danjia) {
                bsd_xiuGaiGongShi = new BSD_XiuGaiGongShi(getActivity(), list_CL.get(i).getPeij_mc(), 0, list_CL.get(i).getPeij_dj(), "", "修改单价");
                bsd_xiuGaiGongShi.show();
                bsd_xiuGaiGongShi.setToopromtOnClickListener(new BSD_XiuGaiGongShi.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick(double gongshi) {
                        list_CL.get(i).setPeij_dj(gongshi);
                        list_CL.get(i).setPeij_je(gongshi * list_CL.get(i).getPeij_sl());
                        wxjd_cl_money();
                        adp_cl.notifyDataSetChanged();
                        bsd_xiuGaiGongShi.dismiss();
                    }
                });
            }
        });
        adp_cl.setClDelete(new BSD_WXJD_CL_adp.CLDelete() {
            @Override
            public void onYesClick() {
                wxjd_cl_money();
            }
        });

        adp_cl.setKuCun(new BSD_WXJD_CL_adp.KuCun() {
            @Override
            public void query_kc(String peij_no) {
                //弹出配件库存明细界面；
                Bundle bundle = new Bundle();
                bundle.putString("peij_no", peij_no);

                BSD_MeiRongKuaiXiu_KuCun_Fragment kcDialog = new BSD_MeiRongKuaiXiu_KuCun_Fragment();
                kcDialog.setArguments(bundle);
                kcDialog.show(getFragmentManager(), "kcDialog");
            }
        });
        listViewCL.setAdapter(adp_cl);

        adp_xm = new BSD_WXJD_XM_adp(getActivity(), list_XM);
        adp_xm.setUpGongShi(new BSD_WXJD_XM_adp.UpGongShi() {
            @Override
            public void onYesClick(final int i, String name, double gongshi) {
                bsd_xiuGaiGongShi = new BSD_XiuGaiGongShi(getActivity(), name, 0, gongshi, "", "修改工时");
                bsd_xiuGaiGongShi.show();
                bsd_xiuGaiGongShi.setToopromtOnClickListener(new BSD_XiuGaiGongShi.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick(double gongshi) {
                        list_XM.get(i).setWxxm_gs(gongshi);
                        bsd_xiuGaiGongShi.dismiss();
                        wxjd_xm_money();
                        adp_xm.notifyDataSetChanged();
                    }
                });
            }
        });
        //修改工时单价  金额
        adp_xm.setUpGongShiDanJia(new BSD_WXJD_XM_adp.UpGongShiDanJia() {
            @Override
            public void onYesClick(final int i, String name, double gongshi) {
                bsd_xiuGaiGongShi = new BSD_XiuGaiGongShi(getActivity(), name, 0, gongshi, "", "修改金额");
                bsd_xiuGaiGongShi.show();
                bsd_xiuGaiGongShi.setToopromtOnClickListener(new BSD_XiuGaiGongShi.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick(double gongshi) {
                        list_XM.get(i).setWxxm_je(gongshi);
                        bsd_xiuGaiGongShi.dismiss();
                        wxjd_xm_money();
                        adp_xm.notifyDataSetChanged();
                    }
                });
            }
        });
        adp_xm.setUpXmmc(new BSD_WXJD_XM_adp.UpXmmc() {
            @Override
            public void onYesClick(final int i, String name) {
                bsd_xiuGaiGongShi = new BSD_XiuGaiGongShi(getActivity(), name, 2, 0, "", "修改金额");
                bsd_xiuGaiGongShi.show();
                bsd_xiuGaiGongShi.setToopromtXmmc(new BSD_XiuGaiGongShi.ToopromtXmmc() {
                    @Override
                    public void onYesClick(String xmmc) {
                        list_XM.get(i).setWxxm_mc(xmmc);
                        bsd_xiuGaiGongShi.dismiss();
                        adp_xm.notifyDataSetChanged();
                    }
                });
            }
        });
        adp_xm.setXmDelete(new BSD_WXJD_XM_adp.XmDelete() {
            @Override
            public void onYesClick() {
                wxjd_xm_money();
            }
        });
        listViewXM.setAdapter(adp_xm);
        beijing = (RelativeLayout) getActivity().findViewById(R.id.beijing);
        //维修项目
        //滑动
        scrollview = (ScrollView) view.findViewById(R.id.scrollview);
        ll_pinpai.setOnClickListener(this);
        ll_chexi.setOnClickListener(this);
        ll_chezu.setOnClickListener(this);
        ll_chexing.setOnClickListener(this);
        bsd_wxjd_rl_bycx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_chexing.getText().toString().equals("") ||
                        et_jinchanglicheng.getText().toString().equals("") ||
                        tv_gcsj.getText().toString().equals("")) {
                    Show.showTime(getActivity(), "请输入完整信息");
                } else {
                    bsd_cexifansuan();
                }
            }
        });
        title = (TextView) view.findViewById(R.id.tv_title);
        footerText = (TextView) view.findViewById(R.id.tv_footertext);
        billNo = (TextView) view.findViewById(R.id.tv_billNo);
    }


    @Override
    public void initData() {
        url = new URLS();
        title.setText("美容快修");
        footerText.setText("公司名称 :   " + MyApplication.shared.getString("GongSiMc", "") +
                "                  公司电话 :   " + MyApplication.shared.getString("danw_dh", ""));
        entity = new BSD_WeiXiuJieDan_Entity();
        entity = ((MainActivity) getActivity()).getWxjdentity();
        timePickerShow = new TimeDialog(getActivity());
        getBillInfoFromParam();
        updateBillInfoUI();
        xmdata();
        cldata();
    }

    private void updateBillInfoUI() {
        billNo.setText(billEntiy.getWork_no());
        bsd_wxjd_tv_chepai.setText(billEntiy.getChe_no());
        String cheCx = billEntiy.getChe_cx();
        String[] cheCxs = cheCx.split("\\|");
        if (cheCxs.length >= 4) {
            tv_pinpai.setText(cheCxs[0]);
            tv_chexi.setText(cheCxs[1]);
            tv_chezu.setText(cheCxs[2]);
            tv_chexing.setText(cheCxs[3]);
        }
        if (TextUtils.isEmpty(billEntiy.getChe_vin()) || billEntiy.getChe_vin().length() < 2) {
            et_vin.setText(Conts.VIN);
        } else {
            et_vin.setText(billEntiy.getChe_vin());
        }
        et_jinchanglicheng.setText("" + billEntiy.getXche_lc());
        et_kehumc.setText(billEntiy.getKehu_mc());
        et_shouji.setText(billEntiy.getKehu_dh());
        et_cardno.setText(billEntiy.getCard_no());
        gongshifeili_name = billEntiy.getXche_sfbz();
        gongshifeili_id = String.valueOf(billEntiy.getXche_sffl());
        tv_gcsj.setText(billEntiy.getGcsj());
        et_niankuan.setText(billEntiy.getChe_nf());
        et_color.setText(billEntiy.getChe_wxys());
        et_cunyou.setText(billEntiy.getXche_cy());
        et_miaoshu.setText(billEntiy.getXche_bz());
        tv_gsfl.setText(billEntiy.getXche_sfbz());
        gongshifeili_name = billEntiy.getXche_sfbz();
        gongshifeili_id = String.valueOf(billEntiy.getXche_sffl());
    }

    /**
     * 根据传入的参数，获取到单据中的信息
     */
    private void getBillInfoFromParam() {
        try {
            JSONObject item = new JSONObject(params);
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
//            xm_zj = item.getDouble("xche_rgf");
//            cl_zj = item.getDouble("xche_clf");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*
     *根据vin码获取车辆名称、代码、内部名称；
     */
    public void duVin() {
        listvincx.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("vinCode", et_vin.getText().toString());
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


                        tv_pinpai.setText(s1[0]);
                        tv_chexi.setText(s1[1]);
                        tv_chezu.setText(s1[2]);
                        tv_chexing.setText(s1[3]);


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
     * 工时费率数据
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
                String value = nameList3.get(pos).toString();
                if (!tv_gsfl.getText().toString().equals(value)) {
                    tv_gsfl.setText(value);
                    gongshifeili_name = listgslv.get(pos).get("feil_mc");
                    gongshifeili_id = listgslv.get(pos).get("feil_fl");
                }
            }
        });
        mSpinerPopWindow3.setWidth(ll_gsfl.getWidth());
        mSpinerPopWindow3.showAsDropDown(ll_gsfl);
    }

    /**
     * 工时费率
     */
    public void getGSFLData() {
        listgslv.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("type", "ITGongShi");
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_HYGL_ADD_TYPE, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aa, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
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


    /**
     * 进厂时发微信
     */
    private void weixin() {
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", Conts.work_no);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxjd_weixin, params, new AbStringHttpResponseListener() {
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


    //项目金额
    double j = 0;

    public void wxjd_xm_money() {
        double jj = 0;
        for (int i = 0; i < list_XM.size(); i++) {
            jj = jj + (list_XM.get(i).getWxxm_je());
        }
        tv_wxjd_xmhj.setText(jj + "");
        Log.i("cjn", "wxjdXM的总价：" + jj);
        j = jj;
        wxjd_heji();
    }


    //材料金额
    double g = 0;

    public void wxjd_cl_money() {
        double gg = 0;
        for (int i = 0; i < list_CL.size(); i++) {
            gg = gg + (list_CL.get(i).getPeij_dj() * list_CL.get(i).getPeij_sl());
        }
        tv_wxjd_clhj.setText(gg + "");
        Log.i("cjn", "wxjdcl的总价：" + gg);
        g = gg;
        wxjd_heji();
    }

    //总金额
    public void wxjd_heji() {
        tv_wxjd_hj.setText(j + g + "");
        Log.i("cjn", "tv_wxjd_hj" + tv_wxjd_hj.getText().toString());
    }

    /**
     * 维修材料列表
     */
    public void cldata() {
        list_CL.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", billEntiy.getWork_no());
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
                    adp_cl.notifyDataSetChanged();
                    wxjd_cl_money();
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
     * 维修项目列表
     */
    public void xmdata() {
        list_XM.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", billEntiy.getWork_no());
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxjd_xmlb, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aaa, String s) {
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
                            entity.setWxxm_je(item.getDouble("wxxm_je"));
                            entity.setWxxm_zt(item.getString("wxxm_zt"));
                            entity.setWxxm_dj(item.getDouble("wxxm_dj"));
                            list_XM.add(entity);
                        }
                    }
                    adp_xm.notifyDataSetChanged();
                    wxjd_xm_money();
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
    public void onClick(View view) {
        switch (view.getId()) {
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
            case R.id.ll_gsfl:
                getGSFLData();
                break;
            case R.id.ll_gcsj:
                timePickerShow.timePickerAlertDialog(tv_gcsj);
                break;
        }
    }

    private void showCHeXingData() {
        if (tv_chezu.getText().toString().equals("")) {
            Show.showTime(getActivity(), "请选择车组");
        } else {
            getCheXingData(tv_chezu.getText().toString());
        }
    }

    private void showCheZuData() {
        if (tv_chexi.getText().toString().equals("")) {
            Show.showTime(getActivity(), "请选择车系");
        } else {
            getCheZuData(tv_chexi.getText().toString());
        }
    }

    private void showCheXiData() {
        if (tv_pinpai.getText().toString().equals("")) {
            Show.showTime(getActivity(), "请选择品牌");
        } else {
            getCheXiData(tv_pinpai.getText().toString());
        }

    }

    private void showPinPaiDialog() {
        bsd_ksbj_pinPai_delo = new BSD_KSBJ_PinPai_delo(getActivity());
        bsd_ksbj_pinPai_delo.setToopromtOnClickListener(new BSD_KSBJ_PinPai_delo.ToopromtOnClickListener() {
            @Override
            public void onYesClick(String aa, String bianhao) {
                cxbianhao = bianhao;//车牌编号
                pinpaiming = aa;//车牌名称
                tv_pinpai.setText(pinpaiming);
                tv_chexi.setText("");
                tv_chezu.setText("");
                tv_chexing.setText("");
                bsd_ksbj_pinPai_delo.dismiss();
            }
        });
        bsd_ksbj_pinPai_delo.show();
    }
    /**
     * 根据车组获取车型信息
     * @param chezuid
     */
    public void getCheXingData(String chezuid) {
        listjbchexing.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("dm", chezuid);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_Chexing, params, new AbStringHttpResponseListener() {
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

            }
        });
    }

    /**
     * 基本信息车型
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
                String value = nameList2.get(pos).toString();
                if (!tv_chexing.getText().toString().equals(value)) {
                    tv_chexing.setText(value);
                    Conts.chexing = tv_chexing.getText().toString();
                    chexingid = listjbchexing.get(pos).get("chex_bz");
                }
            }
        });
        mSpinerPopWindow2.setWidth(ll_chexing.getWidth());
        mSpinerPopWindow2.showAsDropDown(ll_chexing);
    }

    /**
     * 根据车系获取车组信息
     * @param chexiid
     */
    public void getCheZuData(String chexiid) {
        listjbcz.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("dm", chexiid);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_CZ, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aa, String s) {
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
                    updateChexZuData();
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
     * 基本信息车组接口
     */
    public void updateChexZuData() {
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
                String value = nameList1.get(pos).toString();
                if (!tv_chezu.getText().toString().equals(value)) {
                    chezuid = listjbcz.get(pos).get("chex_dm");
                    tv_chezu.setText(value);
                    tv_chexing.setText("");
                }
            }
        });
        mSpinerPopWindow1.setWidth(ll_chezu.getWidth());
        mSpinerPopWindow1.showAsDropDown(ll_chezu);
    }

    /**
     * 更新车系的数据并显示
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
                chexiid = "";
                String value = nameList.get(pos).toString();
                if (!tv_chexi.getText().toString().equals(value)) {
                    tv_chexi.setText(value);
                    chexiid = listjbcx.get(pos).get("chex_dm");
                    tv_chezu.setText("");
                    tv_chexing.setText("");
                }
            }
        });
        // 显示出来
        mSpinerPopWindow.setWidth(ll_chexi.getWidth());
        mSpinerPopWindow.showAsDropDown(ll_chexi);
    }

    /**
     * 根据车牌号获取车系信息
     * @param cxbianhao
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
                    if (jsonObject.get("status").toString().equals("1")) {
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

    //存档操作
    private void updata() {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        chepai = bsd_wxjd_tv_chepai.getText().toString();
        pinpai = tv_pinpai.getText().toString();
        chexi = tv_chexi.getText().toString();
        chezu = tv_chezu.getText().toString();
        chexing = tv_chexing.getText().toString();
        if (tv_pinpai.getText().toString().equals("") ||
                tv_chexi.getText().toString().equals("") ||
                tv_chezu.getText().toString().equals("") ||
                tv_chexing.getText().toString().equals("")) {
            che_cx = "";
        } else {
            che_cx = pinpai + "|" + chexi + "|" + chezu + "|" + chexing;
        }
        VIN = et_vin.getText().toString();
        jinchanglicheng = et_jinchanglicheng.getText().toString();
        dinahua = et_shouji.getText().toString();
        chezhu = et_kehumc.getText().toString();
        huiyuankahao = et_cardno.getText().toString();
        AbRequestParams params = new AbRequestParams();
        params.put("che_no", chepai);
//        params.put("work_no", danhao);
        params.put("che_cx", che_cx);
        params.put("che_vin", VIN);
        params.put("xche_lc", jinchanglicheng);
        params.put("kehu_mc", chezhu);
        params.put("kehu_dh", dinahua);

        params.put("xche_sfbz", gongshifeili_name);
        params.put("xche_sffl", gongshifeili_id);
        params.put("kehu_no", Conts.kehu_no);
        params.put("xche_hjje", tv_wxjd_hj.getText().toString());
        params.put("gcsj", tv_gcsj.getText().toString());
        params.put("card_no", et_cardno.getText().toString());


//        接待登记主表work_pz_gz备注字段xche_bz存油xche_cy，

        //by  李赛     增加年款，颜色，存油，故障描述字段
        Log.e("jdd", "年份aaa: " + et_niankuan.getText().toString());
        Log.e("jdd", "颜色aaa: " + et_color.getText().toString());
        Log.e("jdd", "存油aaa: " + et_cunyou.getText().toString());
        Log.e("jdd", "备注aaa: " + et_miaoshu.getText().toString());
        params.put("che_nf", et_niankuan.getText().toString());
        params.put("che_wxys", et_color.getText().toString());
        params.put("xche_cy", et_cunyou.getText().toString());
        params.put("xche_bz", et_miaoshu.getText().toString());


        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxjd_jbxxtj, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("cjn", "查看返回的单号" + s.toString());
//                if (list_XM.size() > 0) {
//                } else {
//                    WeiboDialogUtils.closeDialog(mWeiboDialog);
//                    Show.showTime(getActivity(), "请添加材料");
//                }

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
                Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
                Log.i("cjn", "失败信息：" + s.toString());
            }
        });
    }


    /**
     * 打印维修接待单
     */
    private void print() {
        mWeiboDialog2 = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", entity.getWork_no());
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxjd_print, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("print", "查看返回的" + s.toString());
                WeiboDialogUtils.closeDialog(mWeiboDialog2);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                WeiboDialogUtils.closeDialog(mWeiboDialog2);
                Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
                Log.i("cjn", "失败信息：" + s.toString());
            }
        });
    }

    //进厂操作
    public void jcdata(String no) {
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", no);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_WXJD_jc, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("cjn", "是否成功" + s.toString());
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    Log.i("cjn", "进厂返回啥===" + s);
                    if (jsonObject.get("data").toString().trim().equals("进厂成功")) {
                        WeiboDialogUtils.closeDialog(mWeiboDialog);
                        queRen = new QueRen(getActivity(), "进厂成功");
                        queRen.show();
                        queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                            @Override
                            public void onYesClick() {
                                queRen.dismiss();
                                queRen_quxiao = new Queding_Quxiao(getActivity(), "是否跳转详情");
                                queRen_quxiao.show();
                                queRen_quxiao.setOnResultClickListener(new Queding_Quxiao.OnResultClickListener() {
                                    @Override
                                    public void onConfirm() {
                                        Conts.wxjdtiaozhuan = 1;
                                        Conts.cp = Car;
                                        ((MainActivity) getActivity()).upwxywdd();
                                        queRen_quxiao.dismiss();
                                    }

                                    @Override
                                    public void onCancel() {
                                        queRen_quxiao.dismiss();
                                        ((MainActivity) getActivity()).upBSD_WXJD_log();
                                    }
                                });

                            }
                        });
                        WeiboDialogUtils.closeDialog(mWeiboDialog);
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
                Log.i("cjn", "进厂失败");
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });


    }

    String getCar;

    public void bsd_cexifansuan() {
        String id;
        AbRequestParams params = new AbRequestParams();
        params.put("quanMing", tv_chexing.getText().toString());
        Log.i("cjn", "bsd_ksbj_cxing.getText().toString()" + tv_chexing.getText().toString());
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_chexing_fansuan, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("cjn", "查看" + s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
//                if (jsonObject.getString("message").toString().equals("查询成功")){
                    getCar = jsonObject.getString("data");
//                }
                    Conts.bycx_type = 2;//维修接单
                    if (getCar.length() > 0) {
                        chexingid = getCar;
                    }
                    Conts.bycx_chexing = chexingid;//车型

                    int a;
                    double b;
                    b = Double.parseDouble(et_jinchanglicheng.getText().toString().trim());
                    a = (int) b;
                    Conts.bycx_licheng = "" + a;//里程
//                    Conts.bycx_licheng = bsd_wxjd_et_jinchanglicheng.getText().toString().trim();//里程
                    Conts.bycx_time = tv_gcsj.getText().toString().trim();//时间
                    Conts.bycx_pinpai = tv_pinpai.getText().toString().trim();
                    Conts.bycx_chexi = tv_chexi.getText().toString().trim();
                    Conts.bycx_chezu = tv_chezu.getText().toString().trim();
                    Conts.bycx_cxing = tv_chexing.getText().toString().trim();
                    Conts.bycx_VIN = et_vin.getText().toString().trim();
                    Conts.bycx_CarName = et_kehumc.getText().toString().trim();
                    Conts.bycx_Shouji = et_shouji.getText().toString().trim();
                    Conts.bycx_huiyuan = et_cardno.getText().toString().trim();
                    Conts.bycx_cheliangmingcheng = tv_pinpai.getText().toString().trim() + "|" + tv_chexi.getText().toString().trim() + "|" + tv_chezu.getText().toString().trim() + "|" + tv_chexing.getText().toString().trim();

                    ((MainActivity) getActivity()).upBSD_bycx();

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
}