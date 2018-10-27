package com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

    //////////////////////////////////////////////////////////////////
    TextView bsd_wxjd_tv_chepai;//车牌
    EditText bsd_wxjd_et_jinchanglicheng;//进厂里程
    TextView bsd_wxjd_et_name;//车主
    TextView bsd_wxjd_et_shouji;//手机
    EditText bsd_wxjd_et_vin;//VIN码
    TextView bsd_wxjd_tv_pinpai;//品牌
    TextView bsd_wxjd_tv_chexi;//车系
    TextView bsd_wxjd_tv_chezu;//车组
    TextView bsd_wxjd_tv_chexing;//车型
    EditText bsd_wxjd_et_qiankuan;//欠款
    EditText bsd_wxjd_et_huiyuan;//会员卡号
    TextView tv_wxjd_clhj, tv_wxjd_xmhj, tv_wxjd_hj;//项目金额。材料金额，总金额
    //车牌号
    String Car;
    //维修接单基本信息实体类
    BSD_WeiXiuJieDan_Entity entity;
    //单号
    String danhao;
    //项目维修
    BSD_WXYY_XM_POP bsd_wxjd_xm_pop;
    //材料选择
    BSD_KSBJ_CL_POP bsd_wxjd_cl_pop;
    private ListView listViewXM;//维修项目
    private ListView listViewCL;//维修材料
    private BSD_WXJD_XM_adp adp_xm;
    private BSD_WXJD_CL_adp adp_cl;
    private List<BSD_WeiXiuJieDan_XM_Entity> list_XM = new ArrayList<>();
    private List<BSD_WeiXiuJieDan_CL_Entity> list_CL = new ArrayList<>();
    int choufutianjia = 0;
    TextView bsd_wxjd_cd;
    public int jc_or_cd;
    TextView bsd_wxjd_cj;

    //修改工时弹框
    BSD_XiuGaiGongShi bsd_xiuGaiGongShi;

    private Dialog mWeiboDialog, mWeiboDialog2;
    QueRen queRen;
    //品牌   车系   车组   车型
    LinearLayout bsd_wxjd_pinpai, bsd_ksbj_RLchexi, bsd_ksbj_chezu, bsd_ksbj_rl_chexing;
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
    LinearLayout bsd_wxjd_rl_gsfl;
    TextView bsd_wxjd_tv_gsfl;

    List<Map<String, String>> listgslv = new ArrayList<Map<String, String>>();

    private List<CustemObject> nameList3 = new ArrayList<CustemObject>();
    private AbstractSpinerAdapter mAdapter3;
    private SpinerPopWindow mSpinerPopWindow3;
    String gongshifeili_name;
    String gongshifeili_id;

    LinearLayout bsd_wxjd_rl_gcsj;
    TextView bsd_wxjd_rl_bycx;
    TextView bsd_wxjd_tv_gcsj;
    EditText bsd_wxjd_et_niankuan, bsd_wxjd_et_cunyou, bsd_wxjd_et_color, bsd_wxjd_et_miaoshu;
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
        bsd_wxjd_tv_gcsj = (TextView) view.findViewById(R.id.bsd_wxjd_tv_gcsj);
        bsd_wxjd_rl_gcsj = (LinearLayout) view.findViewById(R.id.bsd_wxjd_rl_gcsj);
        bsd_wxjd_rl_gcsj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerShow.timePickerAlertDialog(bsd_wxjd_tv_gcsj);
            }
        });

        //读取vin码
        rl_duqu = (TextView) view.findViewById(R.id.tv_readvin);
        rl_duqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vin = bsd_wxjd_et_vin.getText().toString();
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

        bsd_wxjd_et_niankuan = (EditText) view.findViewById(R.id.bsd_wxjd_et_niankuan);
        bsd_wxjd_et_cunyou = (EditText) view.findViewById(R.id.bsd_wxjd_et_cunyou);
        bsd_wxjd_et_color = (EditText) view.findViewById(R.id.bsd_wxjd_et_color);
        bsd_wxjd_et_miaoshu = (EditText) view.findViewById(R.id.bsd_wxjd_et_miaoshu);
        bsd_wxjd_rl_gsfl = (LinearLayout) view.findViewById(R.id.bsd_wxjd_rl_gsfl);
        bsd_wxjd_tv_gsfl = (TextView) view.findViewById(R.id.bsd_wxjd_tv_gsfl);
        bsd_wxjd_rl_gsfl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGongSi3();

            }
        });
        bsd_wxjd_cd = (TextView) view.findViewById(R.id.bsd_wxjd_cd);
        bsd_wxjd_cd.setOnClickListener(this);
        //进厂
        bsd_wxjd_cj = (TextView) view.findViewById(R.id.bsd_wxjd_cj);
        bsd_wxjd_cj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //发送微信
                weixin();

                if (danhao == null || danhao.equals("")) {
                } else {

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

            }
        });

        bsd_wxjd_tv_chepai = (TextView) view.findViewById(R.id.bsd_wxjd_tv_chepai);
        bsd_wxjd_et_jinchanglicheng = (EditText) view.findViewById(R.id.bsd_wxjd_et_jinchanglicheng);
        bsd_wxjd_et_name = (TextView) view.findViewById(R.id.bsd_wxjd_et_name);
        bsd_wxjd_et_shouji = (TextView) view.findViewById(R.id.bsd_wxjd_et_shouji);
        bsd_wxjd_et_vin = (EditText) view.findViewById(R.id.bsd_wxjd_et_vin);
        bsd_wxjd_tv_pinpai = (TextView) view.findViewById(R.id.bsd_wxjd_tv_pinpai);
        bsd_wxjd_tv_chexi = (TextView) view.findViewById(R.id.bsd_wxjd_tv_chexi);
        bsd_wxjd_tv_chezu = (TextView) view.findViewById(R.id.bsd_wxjd_tv_chezu);
        bsd_wxjd_tv_chexing = (TextView) view.findViewById(R.id.bsd_wxjd_tv_chexing);
        bsd_wxjd_tv_chexing = (TextView) view.findViewById(R.id.bsd_wxjd_tv_chexing);
        bsd_wxjd_et_huiyuan = (EditText) view.findViewById(R.id.bsd_wxjd_et_huiyuan);
        tv_wxjd_clhj = (TextView) view.findViewById(R.id.tv_wxjd_clhj);
        tv_wxjd_hj = (TextView) view.findViewById(R.id.tv_wxjd_hj);
        tv_wxjd_xmhj = (TextView) view.findViewById(R.id.tv_wxjd_xmhj);
        bsd_wxjd_pinpai = (LinearLayout) view.findViewById(R.id.bsd_wxjd_pinpai);
        bsd_ksbj_RLchexi = (LinearLayout) view.findViewById(R.id.bsd_ksbj_RLchexi);
        bsd_ksbj_chezu = (LinearLayout) view.findViewById(R.id.bsd_ksbj_chezu);
        bsd_ksbj_rl_chexing = (LinearLayout) view.findViewById(R.id.bsd_ksbj_rl_chexing);

        bsd_wxjd_xm_pop = new BSD_WXYY_XM_POP(getActivity());
        //添加项目回调
        bsd_wxjd_xm_pop.setClist(new BSD_WXYY_XM_POP.chuanlist() {
            @Override
            public void onYesClick(BSD_wxyy_xm_pop_entiy entity, double wxxmdj) {
                Log.i("cjn", "查看集合的长度" + list_XM.size());
                if (list_XM.size() > 0) {
                    for (int i = 0; i < list_XM.size(); i++) {
                        if (list_XM.get(i).getWxxm_no().equals(entity.getWxxm_no())) {

                            choufutianjia = 1;
                            break;
                        }
                    }
                    if (choufutianjia == 1) {
                        Log.i("cjn", "重复添加问题" + list_XM.size());
                        Show.showTime(getActivity(), "添加重复");
                        choufutianjia = 0;
                    } else {
                        BSD_WeiXiuJieDan_XM_Entity item = new BSD_WeiXiuJieDan_XM_Entity();
                        item.setWxxm_mc(entity.getWxxm_mc());
//                        item.setWxxm_mc("自定义名称可以吗");
                        item.setWxxm_gs(entity.getWxxm_gs());
                        item.setWxxm_je(wxxmdj);
//                        String a = String.valueOf(wxxmdj / entity.getWxxm_gs());
//                        if (a.equals("NaN")){
//                            item.setWxxm_dj(wxxmdj);
//                        }else {
//                            item.setWxxm_dj(wxxmdj / entity.getWxxm_gs());
//                        }
                        if (entity.getWxxm_gs() == 0) {
                            entity.setWxxm_gs(1.0);
                        }
                        item.setWxxm_dj(wxxmdj / entity.getWxxm_gs());
                        item.setWxxm_cb(entity.getWxxm_cb());
                        item.setWxxm_no(entity.getWxxm_no());
                        item.setWxxm_zt("正常");
                        list_XM.add(item);
                        Show.showTime(getActivity(), "成功");
                        adp_xm.notifyDataSetChanged();
                        Log.i("cjn", "多条数据过来");
                    }

                } else {
                    BSD_WeiXiuJieDan_XM_Entity item = new BSD_WeiXiuJieDan_XM_Entity();
                    item.setWxxm_mc(entity.getWxxm_mc());
//                    item.setWxxm_mc("自定义第一个名称");
                    item.setWxxm_gs(entity.getWxxm_gs());
                    item.setWxxm_je(wxxmdj);
//                    String a = String.valueOf(wxxmdj / entity.getWxxm_gs());
//                    if (a.equals("NaN")){
//                        item.setWxxm_dj(wxxmdj);
//                    }else {
//                        item.setWxxm_dj(wxxmdj / entity.getWxxm_gs());
//                    }


                    if (entity.getWxxm_gs() == 0) {
                        entity.setWxxm_gs(1.0);
                    }
                    item.setWxxm_dj(wxxmdj / entity.getWxxm_gs());
                    item.setWxxm_no(entity.getWxxm_no());
                    item.setWxxm_zt("正常");
                    list_XM.add(item);
                    Log.i("cjn", "查看这个集合的长度" + wxxmdj);
                    Show.showTime(getActivity(), "成功");
                    Log.i("cjn", "新来的一条数据" + item.getWxxm_dj());

                    adp_xm.notifyDataSetChanged();
                }
                wxjd_xm_money();
                adp_xm.notifyDataSetChanged();
            }
        });


        bsd_wxjd_cl_pop = new BSD_KSBJ_CL_POP(getActivity());
        bsd_wxjd_cl_pop.setChuanlistcl(new BSD_KSBJ_CL_POP.chuanlistcl() {
            @Override
            public void onYesClick(BSD_wxyy_cl_pop_entity entity, double jiaqian) {
                if (list_CL.size() > 0) {
                    for (int i = 0; i < list_CL.size(); i++) {
                        if (list_CL.get(i).getPeij_no().equals(entity.getPeij_no())) {

                            choufutianjia = 1;
                            break;
                        }
                    }
                    if (choufutianjia == 1) {
                        Show.showTime(getActivity(), "添加重复");
                        choufutianjia = 0;
                    } else {
                        BSD_WeiXiuJieDan_CL_Entity item = new BSD_WeiXiuJieDan_CL_Entity();
                        item.setReco_no(entity.getReco_no1());
                        item.setPeij_no(entity.getPeij_no());
                        //名字
                        item.setPeij_mc(entity.getPeij_mc());
                        //数量
                        item.setPeij_sl(1);
                        //单价
                        item.setPeij_dj(jiaqian);
                        item.setPeij_je(jiaqian * item.getPeij_sl());
                        //单位
                        item.setPeij_dw(entity.getPeij_dw());
                        item.setPeij_th(entity.getPeij_th());
                        //状态
                        item.setPeij_zt("正常");
//                            item.setPeij_dw(entity.getWaib_dw());
                        list_CL.add(item);
                        Show.showTime(getActivity(), "成功");


                    }


                } else {
                    BSD_WeiXiuJieDan_CL_Entity item = new BSD_WeiXiuJieDan_CL_Entity();
                    item.setReco_no(entity.getReco_no1());
                    item.setPeij_no(entity.getPeij_no());
                    //名字
                    item.setPeij_mc(entity.getPeij_mc());
                    //数量
                    item.setPeij_sl(1);
                    //单价
                    item.setPeij_je(jiaqian * item.getPeij_sl());
                    item.setPeij_dj(jiaqian);
                    //单位
                    item.setPeij_th(entity.getPeij_th());
                    //状态
                    item.setPeij_zt("正常");
                    item.setPeij_dw(entity.getPeij_dw());
                    list_CL.add(item);
                    Show.showTime(getActivity(), "成功");
                }

                adp_cl.setList(list_CL);
                listViewCL.setAdapter(adp_cl);
                wxjd_cl_money();
                adp_cl.notifyDataSetChanged();

            }
        });

        //数据atap
        listViewXM = (ListView) view.findViewById(R.id.lv_wxxm);
        //维修材料
        listViewCL = (ListView) view.findViewById(R.id.lv_wxcl);
        //维修材料
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

        //维修项目
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

        //品牌弹框
        bsd_ksbj_pinPai_delo = new BSD_KSBJ_PinPai_delo(getActivity());
        bsd_wxjd_pinpai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bsd_ksbj_pinPai_delo.show();
            }
        });

        bsd_ksbj_pinPai_delo.setToopromtOnClickListener(new BSD_KSBJ_PinPai_delo.ToopromtOnClickListener()

        {
            @Override
            public void onYesClick(String aa, String bianhao) {
                cxbianhao = bianhao;//车牌编号
                pinpaiming = aa;//车牌名称
                bsd_wxjd_tv_pinpai.setText(pinpaiming);
                bsd_wxjd_tv_chexi.setText("");
                bsd_wxjd_tv_chezu.setText("");
                bsd_wxjd_tv_chexing.setText("");
                bsd_ksbj_pinPai_delo.dismiss();
                bsdcx(cxbianhao);
            }
        });
        //车系
        bsd_ksbj_RLchexi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bsd_wxjd_tv_pinpai.getText().toString().equals("")) {
                    Show.showTime(getActivity(), "请选择品牌");
                } else {
                    if (listjbcx.size() > 0) {
                        showGongSi();
                    } else {
                        Show.showTime(getActivity(), "数据加载中请稍后");
                    }
                }
            }
        });
        //车组
        bsd_ksbj_chezu.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                if (bsd_wxjd_tv_chexi.getText().toString().equals("")) {
                    Show.showTime(getActivity(), "请选择车系");
                } else {
                    if (listjbcz.size() > 0) {
                        showGongSi1();
                    } else {
                        Show.showTime(getActivity(), "数据加载中请稍后");
                    }
                }
            }
        });
        //车行
        bsd_ksbj_rl_chexing.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                if (bsd_wxjd_tv_chezu.getText().toString().equals("")) {
                    Show.showTime(getActivity(), "请选择车组");
                } else {
                    if (listjbchexing.size() > 0) {
                        showGongSi2();
                    } else {
                        Show.showTime(getActivity(), "数据加载中请稍后");
                    }
                }
            }
        });
        bsd_wxjd_rl_bycx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bsd_wxjd_tv_chexing.getText().toString().equals("") ||
                        bsd_wxjd_et_jinchanglicheng.getText().toString().equals("") ||
                        bsd_wxjd_tv_gcsj.getText().toString().equals("")) {
                    Show.showTime(getActivity(), "请输入完整信息");
                } else {
                    bsd_cexifansuan();
                }
            }
        });
    }


    @Override
    public void initData() {
        url = new URLS();
        entity = new BSD_WeiXiuJieDan_Entity();
        entity = ((MainActivity) getActivity()).getWxjdentity();
        timePickerShow = new TimeDialog(getActivity());
        getBillInfoFromParam();
        updateBillInfoUI();
        getGSFLData();
        xmdata();
        cldata();
    }

    private void updateBillInfoUI() {
        bsd_wxjd_tv_chepai.setText(billEntiy.getChe_no());
        String cheCx = billEntiy.getChe_cx();
        String[] cheCxs = cheCx.split("\\|");
        if (cheCxs.length >= 4) {
            bsd_wxjd_tv_pinpai.setText(cheCxs[0]);
            bsd_wxjd_tv_chexi.setText(cheCxs[1]);
            bsd_wxjd_tv_chezu.setText(cheCxs[2]);
            bsd_wxjd_tv_chexing.setText(cheCxs[3]);
            //车系
            bsdcx(cheCxs[0]);
            //车组
            bsdcz(cheCxs[1]);
            //车行
            bsd_chexingdata(cheCxs[3]);
        }
        if (TextUtils.isEmpty(billEntiy.getChe_vin()) || billEntiy.getChe_vin().length() < 2) {
            bsd_wxjd_et_vin.setText(Conts.VIN);
        } else {
            bsd_wxjd_et_vin.setText(billEntiy.getChe_vin());
        }
        bsd_wxjd_et_jinchanglicheng.setText("" + billEntiy.getXche_lc());
        bsd_wxjd_et_name.setText(billEntiy.getKehu_mc());
        bsd_wxjd_et_shouji.setText(billEntiy.getKehu_dh());
        bsd_wxjd_et_huiyuan.setText(billEntiy.getCard_no());
        gongshifeili_name = billEntiy.getXche_sfbz();
        gongshifeili_id = String.valueOf(billEntiy.getXche_sffl());
        bsd_wxjd_tv_gcsj.setText(billEntiy.getGcsj());
        bsd_wxjd_et_niankuan.setText(billEntiy.getChe_nf());
        bsd_wxjd_et_color.setText(billEntiy.getChe_wxys());
        bsd_wxjd_et_cunyou.setText(billEntiy.getXche_cy());
        bsd_wxjd_et_miaoshu.setText(billEntiy.getXche_bz());
        bsd_wxjd_tv_gsfl.setText(billEntiy.getXche_sfbz());
        gongshifeili_name = billEntiy.getXche_sfbz();
        gongshifeili_id = String.valueOf(billEntiy.getXche_sffl());
    }

    /**
     * 根据传入的参数，获取到单据中的信息
     */
    private void getBillInfoFromParam() {
        try {
            JSONObject item = new JSONObject(params);
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
        params.put("vinCode", bsd_wxjd_et_vin.getText().toString());
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


                        bsd_wxjd_tv_pinpai.setText(s1[0]);
                        bsd_wxjd_tv_chexi.setText(s1[1]);
                        bsd_wxjd_tv_chezu.setText(s1[2]);
                        bsd_wxjd_tv_chexing.setText(s1[3]);


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


    private void showGongSi3() {
        mSpinerPopWindow3.setWidth(bsd_wxjd_rl_gsfl.getWidth());
        mSpinerPopWindow3.showAsDropDown(bsd_wxjd_rl_gsfl);
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
                if (!bsd_wxjd_tv_gsfl.getText().toString().equals(value)) {
                    bsd_wxjd_tv_gsfl.setText(value);
                    gongshifeili_name = listgslv.get(pos).get("feil_mc");
                    gongshifeili_id = listgslv.get(pos).get("feil_fl");
                }
            }
        });
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


    //品牌    车系   车组     车型

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

//品牌   车系   车组   车型

    //品牌    车系   车组    车行
//车型===================================================================
    public void bsd_chexingdata(String chezuid) {
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
                        bumen2();
                    } else {
                        bumen2();
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
     * 基本信息车型
     */
    public void bumen2() {
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
                if (!bsd_wxjd_tv_chexing.getText().toString().equals(value)) {
                    bsd_wxjd_tv_chexing.setText(value);
                    Conts.chexing = bsd_wxjd_tv_chexing.getText().toString();
                    chexingid = listjbchexing.get(pos).get("chex_bz");

                }

            }
        });


    }


    private void showGongSi2() {
        mSpinerPopWindow2.setWidth(bsd_ksbj_rl_chexing.getWidth());
        mSpinerPopWindow2.showAsDropDown(bsd_ksbj_rl_chexing);
    }


//车组=======================================================================

    private void showGongSi1() {
        mSpinerPopWindow1.setWidth(bsd_ksbj_chezu.getWidth());
        mSpinerPopWindow1.showAsDropDown(bsd_ksbj_chezu);
    }

    /**
     * 基本信息车组接口
     */
    public void bsdcz(String chexiid) {
        listjbcz.clear();
//        BSD_CZ
        Log.i("cjn", "车系的id是" + chexiid);
        AbRequestParams params = new AbRequestParams();
        params.put("dm", chexiid);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_CZ, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aa, String s) {

                Log.i("cjn", "车组的基本信息" + s);
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
                        bumen1();
                    } else {
                        bumen1();
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
     * 基本信息车组接口
     */
    public void bumen1() {
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
                if (!bsd_wxjd_tv_chezu.getText().toString().equals(value)) {
                    bsd_wxjd_tv_chezu.setText(value);
                    bsd_wxjd_tv_chexing.setText("");
                    chezuid = listjbcz.get(pos).get("chex_dm");

                    bsd_chexingdata(chezuid);

                }

            }
        });


    }


//    车系=================================================

    /**
     * 基本信息车系接口
     */

    private void showGongSi() {
        mSpinerPopWindow.setWidth(bsd_ksbj_RLchexi.getWidth());
        mSpinerPopWindow.showAsDropDown(bsd_ksbj_RLchexi);
    }

    /**
     * 基本信息车系接口
     */
    public void bumen() {
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
                if (!bsd_wxjd_tv_chexi.getText().toString().equals(value)) {
                    bsd_wxjd_tv_chexi.setText(value);
                    chexiid = listjbcx.get(pos).get("chex_dm");
                    bsd_wxjd_tv_chezu.setText("");
                    bsd_wxjd_tv_chexing.setText("");
                    bsdcz(chexiid);
                }
            }
        });


    }

    /**
     * 基本信息车系接口
     */

    public void bsdcx(String cxbianhao) {
        listjbcx.clear();
        AbRequestParams params = new AbRequestParams();
        Log.i("编111111111111111", "11111111111111" + cxbianhao);
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
                    bumen();
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


//   ========================================


//品牌    车系   车组    车行


    //品牌   车系   车组   车型

    public void initdata2() {
        if (Conts.zt == 0) {
            //没有数据的时候跳转过来的
            if (Conts.cp == null || Conts.cp.equals("")) {
                Car = "没有获取车牌";
                bsd_wxjd_tv_chepai.setText(Car);
            } else {
                Car = Conts.cp;
                bsd_wxjd_tv_chepai.setText(Car);
            }

        } else if (Conts.zt == 1) {
//            有一个条数据跳转过来的

            ArrayList arr = new ArrayList();

            Conts.work_no = entity.getWork_no();
            Car = entity.getChe_no();
            Log.i("cjn", "维修接单详情车牌是" + Car);
            bsd_wxjd_tv_chepai.setText(Car);
            String che = Conts.bycx_cheliangmingcheng;
            danhao = entity.getWork_no();
            Log.i("cjn", "查看数据" + che);
            String[] s1 = che.split("\\|");
            for (int j = 0; j < s1.length; j++) {
                arr.add(j, s1[j]);
            }
            if (arr.size() < 4) {
            } else {
                bsd_wxjd_tv_pinpai.setText(arr.get(0).toString());
                bsd_wxjd_tv_chexi.setText(arr.get(1).toString());
                bsd_wxjd_tv_chezu.setText(arr.get(2).toString());
                bsd_wxjd_tv_chexing.setText(arr.get(3).toString());
                Conts.chexing = bsd_wxjd_tv_chexing.getText().toString();
                //车系
                bsdcx(arr.get(0).toString());
                //车组
                bsdcz(arr.get(1).toString());
                //车行
                bsd_chexingdata(arr.get(2).toString());
            }
        }
        bsd_wxjd_tv_gsfl.setText(entity.getXche_sfbz());

        gongshifeili_name = entity.getXche_sfbz();
        Conts.feilv_name = gongshifeili_name;

        gongshifeili_id = String.valueOf(entity.getXche_sffl());
        wxjd_xm_money();
        wxjd_cl_money();
    }

    public void initdata() {

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

    /**
     * 解决滑动问题
     */
    public void huadong() {
        //解决滑动问题
        listViewCL.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                try {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            scrollview.requestDisallowInterceptTouchEvent(true); //禁止scrollview拦截事件，让listview可滑动
                            break;
                        case MotionEvent.ACTION_UP:
                            scrollview.requestDisallowInterceptTouchEvent(false);
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });


        //解决滑动问题。
        listViewXM.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                try {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            scrollview.requestDisallowInterceptTouchEvent(true); //禁止scrollview拦截事件，让listview可滑动
                            break;
                        case MotionEvent.ACTION_UP:
                            scrollview.requestDisallowInterceptTouchEvent(false);
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

    }


    //进场事件点击
    @Override
    public void onClick(View view) {
        jc_or_cd = 0;
        if (danhao == null || danhao.equals("")) {
        } else {

            updata();


        }


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
        danhao = entity.getWork_no();

//        TextView bsd_wxjd_tv_chepai;//车牌
//        EditText bsd_wxjd_et_jinchanglicheng;//进厂里程
//        TextView bsd_wxjd_et_name;//车主
//        TextView bsd_wxjd_et_shouji;//手机
//        EditText bsd_wxjd_et_vin;//VIN码
//        TextView bsd_wxjd_tv_pinpai;//品牌
//        TextView bsd_wxjd_tv_chexi;//车系
//        TextView bsd_wxjd_tv_chezu;//车组
//        TextView bsd_wxjd_tv_chexing;//车型
//        EditText bsd_wxjd_et_qiankuan;//欠款
//        EditText bsd_wxjd_et_huiyuan;//会员卡号


        chepai = bsd_wxjd_tv_chepai.getText().toString();

        pinpai = bsd_wxjd_tv_pinpai.getText().toString();
        chexi = bsd_wxjd_tv_chexi.getText().toString();
        chezu = bsd_wxjd_tv_chezu.getText().toString();
        chexing = bsd_wxjd_tv_chexing.getText().toString();
        if (bsd_wxjd_tv_pinpai.getText().toString().equals("") ||
                bsd_wxjd_tv_chexi.getText().toString().equals("") ||
                bsd_wxjd_tv_chezu.getText().toString().equals("") ||
                bsd_wxjd_tv_chexing.getText().toString().equals("")

                ) {
            che_cx = "";
        } else {
            che_cx = pinpai + "|" + chexi + "|" + chezu + "|" + chexing;
        }
        VIN = bsd_wxjd_et_vin.getText().toString();
        jinchanglicheng = bsd_wxjd_et_jinchanglicheng.getText().toString();
        dinahua = bsd_wxjd_et_shouji.getText().toString();
        chezhu = bsd_wxjd_et_name.getText().toString();
        huiyuankahao = bsd_wxjd_et_huiyuan.getText().toString();

//        VIN = bsd_wxjd_et_vin.getText().toString();
//        jinchanglicheng = bsd_wxjd_et_jinchanglicheng.getText().toString();
//        dinahua = bsd_wxjd_et_shouji.getText().toString();
//        chezhu = bsd_wxjd_et_name.getText().toString();
//        huiyuankahao = bsd_wxjd_et_huiyuan.getText().toString();

        AbRequestParams params = new AbRequestParams();
        params.put("che_no", chepai);
        params.put("work_no", danhao);
        params.put("che_cx", che_cx);
        params.put("che_vin", VIN);
        params.put("xche_lc", jinchanglicheng);
        params.put("kehu_mc", chezhu);
        params.put("kehu_dh", dinahua);

        params.put("xche_sfbz", gongshifeili_name);
        params.put("xche_sffl", gongshifeili_id);
        params.put("kehu_no", Conts.kehu_no);
        params.put("xche_hjje", tv_wxjd_hj.getText().toString());
        params.put("gcsj", bsd_wxjd_tv_gcsj.getText().toString());
        params.put("card_no", bsd_wxjd_et_huiyuan.getText().toString());


//        接待登记主表work_pz_gz备注字段xche_bz存油xche_cy，

        //by  李赛     增加年款，颜色，存油，故障描述字段
        Log.e("jdd", "年份aaa: " + bsd_wxjd_et_niankuan.getText().toString());
        Log.e("jdd", "颜色aaa: " + bsd_wxjd_et_color.getText().toString());
        Log.e("jdd", "存油aaa: " + bsd_wxjd_et_cunyou.getText().toString());
        Log.e("jdd", "备注aaa: " + bsd_wxjd_et_miaoshu.getText().toString());
        params.put("che_nf", bsd_wxjd_et_niankuan.getText().toString());
        params.put("che_wxys", bsd_wxjd_et_color.getText().toString());
        params.put("xche_cy", bsd_wxjd_et_cunyou.getText().toString());
        params.put("xche_bz", bsd_wxjd_et_miaoshu.getText().toString());


        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxjd_jbxxtj, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("cjn", "查看返回的单号" + s.toString());
//                if (list_XM.size() > 0) {
                XMinData(s.toString().trim());
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
        danhao = entity.getWork_no();
        AbRequestParams params = new AbRequestParams();
        params.put("work_no", danhao);
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


    /**
     * 添加材料
     */
//    String clcdjson;
//
//    public void CLinData(final String DH) {
//        clcdjson = "{" + '"' + "data" + '"' + ":" + "[";
//        for (int i = 0; i < list_CL.size() - 1; i++) {
//            clcdjson = clcdjson + "{" + '"' + "work_no" + '"' + ":" + '"' + DH + '"' + "," + '"' +
//                    "peij_no" + '"' + ":" + '"' + list_CL.get(i).getPeij_no() + '"' + "," + '"' +
//                    "peij_mc" + '"' + ":" + '"' + list_CL.get(i).getPeij_mc() + '"' + "," + '"' +
//                    "peij_sl" + '"' + ":" + '"' + list_CL.get(i).getPeij_sl() + '"' + "," + '"' +
//                    "peij_dj" + '"' + ":" + '"' + list_CL.get(i).getPeij_dj() + '"' + "," + '"' +
//                    "peij_je" + '"' + ":" + '"' + list_CL.get(i).getPeij_je() + '"' + "," + '"' +
//                    "peij_th" + '"' + ":" + '"' + list_CL.get(i).getPeij_th() + '"' + "," + '"' +
//                    "peij_dw" + '"' + ":" + '"' + list_CL.get(i).getPeij_dw() + '"' + "," + '"' +
//                    "peij_zt" + '"' + ":" + '"' + "正常" + '"' + "}" + ",";
//        }
//        clcdjson = clcdjson + "{" + '"' + "work_no" + '"' + ":" + '"' + DH + '"' + "," + '"' +
//                "peij_no" + '"' + ":" + '"' + list_CL.get(list_CL.size() - 1).getPeij_no() + '"' + "," + '"' +
//                "peij_mc" + '"' + ":" + '"' + list_CL.get(list_CL.size() - 1).getPeij_mc() + '"' + "," + '"' +
//                "peij_sl" + '"' + ":" + '"' + list_CL.get(list_CL.size() - 1).getPeij_sl() + '"' + "," + '"' +
//                "peij_dj" + '"' + ":" + '"' + list_CL.get(list_CL.size() - 1).getPeij_dj() + '"' + "," + '"' +
//                "peij_je" + '"' + ":" + '"' + list_CL.get(list_CL.size() - 1).getPeij_je() + '"' + "," + '"' +
//                "peij_th" + '"' + ":" + '"' + list_CL.get(list_CL.size() - 1).getPeij_th() + '"' + "," + '"' +
//                "peij_dw" + '"' + ":" + '"' + list_CL.get(list_CL.size() - 1).getPeij_dw() + '"' + "," + '"' +
//                "peij_zt" + '"' + ":" + '"' + "正常" + '"' + "}" + "]" + "}";
//        Log.i("cjn", "clcdjson========================" + clcdjson);
//        AbRequestParams params = new AbRequestParams();
//        params.put("json", clcdjson);
//        params.put("work_no", danhao);
//        params.put("che_cx", che_cx);
//        params.put("che_vin", VIN);
//        params.put("xche_lc", jinchanglicheng);
//        params.put("kehu_mc", chezhu);
//        params.put("kehu_dh", dinahua);
//        Request.Post(URL.BSD_wxjd_addcl, params, new AbStringHttpResponseListener() {
//
//
//                        @Override
//                        public void onSuccess ( int i, String s){
//                        Log.i("cjn", "CL是否成功" + s.toString());
//
//                        if (jc_or_cd == 0) {
//                            if (s.toString().equals("存档成功")) {
//                                WeiboDialogUtils.closeDialog(mWeiboDialog);
//                                queRen = new QueRen(getActivity(), "存档成功");
//                                queRen.show();
//                                queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
//                                    @Override
//                                    public void onYesClick() {
//                                        queRen.dismiss();
//                                        ((MainActivity) getActivity()).upBSD_WXJD_log();
//                                    }
//                                });
//
//                            }
//                        }
//                        if (jc_or_cd == 1) {
//                            jcdata(DH);
//                        }
//                    }
//
//                        @Override
//                        public void onStart () {
//
//                    }
//
//                        @Override
//                        public void onFinish () {
//
//                    }
//
//                        @Override
//                        public void onFailure ( int i, String s, Throwable throwable){
//                        Log.i("cjn", "请求失败" + s.toString());
//                        WeiboDialogUtils.closeDialog(mWeiboDialog);
//                    }
//                    });
//                }


    /**
     * 添加材料
     */
    String clcdjson;

    public void CLinData(final String DH) {
        if (list_CL.size() > 0) {

            clcdjson = "{" + '"' + "data" + '"' + ":" + "[";
            for (int i = 0; i < list_CL.size() - 1; i++) {
                clcdjson = clcdjson + "{" + '"' + "work_no" + '"' + ":" + '"' + DH + '"' + "," + '"' +
                        "peij_no" + '"' + ":" + '"' + list_CL.get(i).getPeij_no() + '"' + "," + '"' +
                        "peij_mc" + '"' + ":" + '"' + list_CL.get(i).getPeij_mc() + '"' + "," + '"' +
                        "peij_sl" + '"' + ":" + '"' + list_CL.get(i).getPeij_sl() + '"' + "," + '"' +
                        "peij_dj" + '"' + ":" + '"' + list_CL.get(i).getPeij_dj() + '"' + "," + '"' +
                        "peij_je" + '"' + ":" + '"' + list_CL.get(i).getPeij_je() + '"' + "," + '"' +
                        "peij_th" + '"' + ":" + '"' + list_CL.get(i).getPeij_th() + '"' + "," + '"' +
                        "peij_dw" + '"' + ":" + '"' + list_CL.get(i).getPeij_dw() + '"' + "," + '"' +
                        "peij_zt" + '"' + ":" + '"' + "正常" + '"' + "}" + ",";
            }
            clcdjson = clcdjson + "{" + '"' + "work_no" + '"' + ":" + '"' + DH + '"' + "," + '"' +
                    "peij_no" + '"' + ":" + '"' + list_CL.get(list_CL.size() - 1).getPeij_no() + '"' + "," + '"' +
                    "peij_mc" + '"' + ":" + '"' + list_CL.get(list_CL.size() - 1).getPeij_mc() + '"' + "," + '"' +
                    "peij_sl" + '"' + ":" + '"' + list_CL.get(list_CL.size() - 1).getPeij_sl() + '"' + "," + '"' +
                    "peij_dj" + '"' + ":" + '"' + list_CL.get(list_CL.size() - 1).getPeij_dj() + '"' + "," + '"' +
                    "peij_je" + '"' + ":" + '"' + list_CL.get(list_CL.size() - 1).getPeij_je() + '"' + "," + '"' +
                    "peij_th" + '"' + ":" + '"' + list_CL.get(list_CL.size() - 1).getPeij_th() + '"' + "," + '"' +
                    "peij_dw" + '"' + ":" + '"' + list_CL.get(list_CL.size() - 1).getPeij_dw() + '"' + "," + '"' +
                    "peij_zt" + '"' + ":" + '"' + "正常" + '"' + "}" + "]" + "}";
        } else {
            clcdjson = "{" + '"' + "data" + '"' + ":" + '[' + ']' + "}";

        }
        Log.i("cjn", "clcdjson========================" + clcdjson);
        AbRequestParams params = new AbRequestParams();
        params.put("json", clcdjson);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxjd_addcl, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("cjn", "CL是否成功" + s.toString());

                if (jc_or_cd == 0) {
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                    queRen = new QueRen(getActivity(), "存档成功");
                    queRen.show();
                    queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                        @Override
                        public void onYesClick() {
                            queRen.dismiss();
                            ((MainActivity) getActivity()).upBSD_WXJD_log();
                        }
                    });
                }

                if (jc_or_cd == 1) {
                    Log.i("cjn", "走没走进厂方法");
                    jcdata(DH);
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
                Log.i("cjn", "请求失败" + s.toString());
            }
        });
    }


    /**
     * 添加项目
     */
    String json;

    public void XMinData(final String DH) {
        if (list_XM.size() > 0) {
            json = "{" + '"' + "data" + '"' + ":" + "[";
            for (int i = 0; i < list_XM.size() - 1; i++) {
                json = json + "{" + '"' + "work_no" + '"' + ":" + '"' + DH + '"' + "," + '"' + "wxxm_mc" + '"' + ":" + '"' + list_XM.get(i).getWxxm_mc() + '"' + "," + '"' +
                        "wxxm_gs" + '"' + ":" + '"' + list_XM.get(i).getWxxm_gs() + '"' + "," + '"' +
                        "wxxm_dj" + '"' + ":" + '"' + list_XM.get(i).getWxxm_dj() + '"' + "," + '"' +
                        "wxxm_no" + '"' + ":" + '"' + list_XM.get(i).getWxxm_no() + '"' + "," + '"' +
                        "wxxm_je" + '"' + ":" + '"' + list_XM.get(i).getWxxm_je() + '"' + "," + '"' +
                        "wxxm_cb" + '"' + ":" + '"' + list_XM.get(i).getWxxm_cb() + '"' + "," + '"' +
                        "wxxm_zt" + '"' + ":" + '"' + "正常" + '"' + "," + '"' +
                        "wxxm_tpye" + '"' + ":" + '"' + "正常" + '"' + "}" + ",";
            }


            json = json + "{" + '"' + "work_no" + '"' + ":" + '"' + DH + '"' + "," + '"' + "wxxm_mc" + '"' + ":" + '"' + list_XM.get(list_XM.size() - 1).getWxxm_mc() + '"' + "," + '"' +
                    "wxxm_gs" + '"' + ":" + '"' + list_XM.get(list_XM.size() - 1).getWxxm_gs() + '"' + "," + '"' +
                    "wxxm_dj" + '"' + ":" + '"' + list_XM.get(list_XM.size() - 1).getWxxm_dj() + '"' + "," + '"' +
                    "wxxm_no" + '"' + ":" + '"' + list_XM.get(list_XM.size() - 1).getWxxm_no() + '"' + "," + '"' +
                    "wxxm_je" + '"' + ":" + '"' + list_XM.get(list_XM.size() - 1).getWxxm_je() + '"' + "," + '"' +
                    "wxxm_cb" + '"' + ":" + '"' + list_XM.get(list_XM.size() - 1).getWxxm_cb() + '"' + "," + '"' +
                    "wxxm_zt" + '"' + ":" + '"' + "正常" + '"' + "," + '"' +
                    "wxxm_tpye" + '"' + ":" + '"' + "正常"
                    + '"' + "}" + "]" + "}";
//        + "]" + "}"
        } else {
            json = "{" + '"' + "data" + '"' + ":" + '[' + ']' + "}";
        }
        Log.i("cjn", "最后一次查看json" + json);
        AbRequestParams params = new AbRequestParams();
        params.put("json", json);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxjd_addxm, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("cjn", "XM是否成功" + s.toString());
//                if (list_CL.size() > 0) {
                CLinData(DH);
//                } else {
//                    WeiboDialogUtils.closeDialog(mWeiboDialog);
//                    Show.showTime(getActivity(), "请添加项目");
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
                Log.i("cjn", "请求失败" + s.toString());
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
        params.put("quanMing", bsd_wxjd_tv_chexing.getText().toString());
        Log.i("cjn", "bsd_ksbj_cxing.getText().toString()" + bsd_wxjd_tv_chexing.getText().toString());
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
                    b = Double.parseDouble(bsd_wxjd_et_jinchanglicheng.getText().toString().trim());
                    a = (int) b;
                    Conts.bycx_licheng = "" + a;//里程
//                    Conts.bycx_licheng = bsd_wxjd_et_jinchanglicheng.getText().toString().trim();//里程
                    Conts.bycx_time = bsd_wxjd_tv_gcsj.getText().toString().trim();//时间
                    Conts.bycx_pinpai = bsd_wxjd_tv_pinpai.getText().toString().trim();
                    Conts.bycx_chexi = bsd_wxjd_tv_chexi.getText().toString().trim();
                    Conts.bycx_chezu = bsd_wxjd_tv_chezu.getText().toString().trim();
                    Conts.bycx_cxing = bsd_wxjd_tv_chexing.getText().toString().trim();
                    Conts.bycx_VIN = bsd_wxjd_et_vin.getText().toString().trim();
                    Conts.bycx_CarName = bsd_wxjd_et_name.getText().toString().trim();
                    Conts.bycx_Shouji = bsd_wxjd_et_shouji.getText().toString().trim();
                    Conts.bycx_huiyuan = bsd_wxjd_et_huiyuan.getText().toString().trim();
                    Conts.bycx_cheliangmingcheng = bsd_wxjd_tv_pinpai.getText().toString().trim() + "|" + bsd_wxjd_tv_chexi.getText().toString().trim() + "|" + bsd_wxjd_tv_chezu.getText().toString().trim() + "|" + bsd_wxjd_tv_chexing.getText().toString().trim();

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