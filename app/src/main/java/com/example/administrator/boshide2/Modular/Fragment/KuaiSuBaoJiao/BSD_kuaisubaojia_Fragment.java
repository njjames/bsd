package com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
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
import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.Adapter.BSD_wxxm_adp;
import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.Adapter.BSD_xzcl_adp;
import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.Entity.BSD_KuaiSuBaoJia_CL_entity;
import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.Entity.BSD_KuaiSuBaoJia_XM_entity;
import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.Entity.BSD_KuaiSuBaoJia_ety;
import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.PopWinow.BSD_KSBJ_CL_POP;
import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.PopWinow.BSD_KSBJ_PinPai_delo;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.dialogFragment.BSD_LiShiWeiXiuJianYi_DialogFragment;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.dialogFragment.BSD_LishiWeiXiu_DialogFragment;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.dialogFragment.BSD_MeiRongKuaiXiu_KuCun_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.dialogFragment.BSD_MeiRongKuaiXiu_cheliangxinxi_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.BSD_WXYY_XM_POP;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.BSD_XiuGaiGongShi;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.Pop_Entity.BSD_wxyy_cl_pop_entity;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.Pop_Entity.BSD_wxyy_xm_pop_entiy;
import com.example.administrator.boshide2.Modular.View.MarqueeView;
import com.example.administrator.boshide2.Modular.View.SpinerPopWindow;
import com.example.administrator.boshide2.Modular.View.Time.TimeDialog;
import com.example.administrator.boshide2.Modular.View.diaog.QueRen;
import com.example.administrator.boshide2.Modular.View.diaog.Queding_Quxiao;
import com.example.administrator.boshide2.Modular.View.timepicker.TimePickerShow;
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
 * @快速报价碎片页 Created by Administrator on 2017-4-13.
 */

public class BSD_kuaisubaojia_Fragment extends Fragment implements View.OnClickListener {
    ScrollView scrollview;
    //弹出popwin
    RelativeLayout bsd_wxxm, bsd_wxcl;
    RelativeLayout beijing;
    // 切换碎片页
    LinearLayout bsd_lsbj;
    TextView tv_shibie, tv_chepai, tv_wxxm_money, tv_wxcl_money;//自動識別,维修项目金额，维修材料金额
    RelativeLayout rea_pinpai;
    //当前选中的列表项位置
    int clickPsition = -1;
    private Dialog mWeiboDialog;
    private MarqueeView marqueeView;

    /////////////////

    //车牌号
    String Car;
    TextView bsd_ksbj_cp;
    BSD_KuaiSuBaoJia_ety entity;//快速报价实体类
    String danhao;//单号
    TextView bsd_ksbj_lc, bsd_ksbj_cezhu, bsd_ksbj_dh, bsd_ksbj_vin, bsd_ksbj_pp, bsd_ksbj_cx,
            bsd_ksbj_cz, bsd_ksbj_cxing, tv_zong_money, tv_zong_money1, tv_zong_money3;
    List<BSD_KuaiSuBaoJia_XM_entity> listXM = new ArrayList<BSD_KuaiSuBaoJia_XM_entity>();
    BSD_wxxm_adp adpxm;
    ListView listxm;//维修项目
    //修改工时弹框
    BSD_XiuGaiGongShi bsd_xiuGaiGongShi;
    int choufutianjia = 0;
    List<BSD_KuaiSuBaoJia_CL_entity> listCL = new ArrayList<BSD_KuaiSuBaoJia_CL_entity>();
    private BSD_xzcl_adp adpcl;//车系适配器
    ListView listcl;//维修材料
    //popwin
    BSD_WXYY_XM_POP bsd_ksbj_xm_pop;
    BSD_KSBJ_CL_POP bsd_ksbj_cl_pop;

    RelativeLayout bsd_ksbj_cd;
    RelativeLayout bsd_ksbj_jc;
    public int cd_or_jc;
    QueRen queRen;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 10) { // 更改选中商品的总价格


            }
        }
    };
    int sl;
    double zj;

    //四个弹框那块
    //品牌返回的车名和编号

    //品牌diagle
    BSD_KSBJ_PinPai_delo bsd_ksbj_pinPai_delo;
    String cxbianhao;
    String pinpaiming;
    //这是车系
    RelativeLayout bsd_ksbj_rl_cx;
    List<Map<String, String>> listjbcx = new ArrayList<Map<String, String>>();
    private List<CustemObject> nameList = new ArrayList<CustemObject>();
    private AbstractSpinerAdapter mAdapter;
    private SpinerPopWindow mSpinerPopWindow;

    String chexiid;
    //车组
    RelativeLayout bsd_ksbj_rl_cz;
    List<Map<String, String>> listjbcz = new ArrayList<Map<String, String>>();
    String chezuid;
    private List<CustemObject> nameList1 = new ArrayList<CustemObject>();
    private AbstractSpinerAdapter mAdapter1;
    private SpinerPopWindow mSpinerPopWindow1;
    //车型
    RelativeLayout bsd_ksbj_chexing;
    List<Map<String, String>> listjbchexing = new ArrayList<Map<String, String>>();
    String chexingid;
    private List<CustemObject> nameList2 = new ArrayList<CustemObject>();
    private AbstractSpinerAdapter mAdapter2;
    private SpinerPopWindow mSpinerPopWindow2;
    //单号
    TextView bsd_ksbj_tv_dh;
    TextView bsd_ksbj_tv_sj;


    //车辆信息、历史维修、历史维修建议
    private  RelativeLayout  bsd_ksbj_clxx,bsd_ksbj_lswxjy,bsd_ksbj_lswx;



    //工时费率
    RelativeLayout bsd_ksbj_rl_gsfl;
    TextView bsd_ksbj_tv_gsfl;
    List<Map<String, String>> listgslv = new ArrayList<Map<String, String>>();

    private List<CustemObject> nameList3 = new ArrayList<CustemObject>();
    private AbstractSpinerAdapter mAdapter3;
    private SpinerPopWindow mSpinerPopWindow3;
    String gongshifeili_name;
    String gongshifeili_id;

    //保养数据
    RelativeLayout bsd_ksbj_rl_gcsj;//日期选择
    TimePickerShow timePickerShow;
    TextView bsd_ksbj_tv_gcsj;
    RelativeLayout bsd_ksbj_rl_bycx;//保养查询
    RelativeLayout bsd_rl_money;//点击

    URLS url;
    Queding_Quxiao queRen_quxiao;
    TimeDialog timeShow;


    //根据VIN返回车型信息
    List<Map<String, String>> listvincx = new ArrayList<>();
    String   cxnm;      //车型内码
    RelativeLayout  rl_duqu;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bsd_ksbj_fragment, null);

        url = new URLS();
        beijing = (RelativeLayout) getActivity().findViewById(R.id.beijing);
        entity = new BSD_KuaiSuBaoJia_ety();
        entity = ((MainActivity) getActivity()).getKsbjenity();
        timePickerShow = new TimePickerShow(getActivity());
        timeShow = new TimeDialog(getActivity());
        gsfldata();
        inin(view);

        bsdtext(view);
        POP(view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.i("jslm", "onStart:00000000000 ");


        if (Conts.QX_baoyangxinxii ==1){
            bsd_ksbj_rl_bycx.setVisibility(View.VISIBLE);
        }else {
            bsd_ksbj_rl_bycx.setVisibility(View.INVISIBLE);
        }
        a = 0;
        b = 0;
        tv_zong_money.setText("");
        tv_zong_money1.setText("");
        if (Conts.bycx_fanhuitype == 1) {
            Conts.bycx_fanhuitype = 0;
            bsd_ksbj_pp.setText(Conts.bycx_pinpai);
            bsd_ksbj_cx.setText(Conts.bycx_chexi);
            bsd_ksbj_cz.setText(Conts.bycx_chezu);
            bsd_ksbj_cxing.setText(Conts.bycx_cxing);
            bsd_ksbj_lc.setText(Conts.bycx_licheng);
            bsd_ksbj_tv_gcsj.setText(Conts.bycx_time);
            bsd_ksbj_vin.setText(Conts.bycx_VIN);
            bsd_ksbj_cezhu.setText(Conts.bycx_CarName);
            bsd_ksbj_dh.setText(Conts.bycx_Shouji);
            initdata2();
            adpcl.setList(listCL);
            adpcl.notifyDataSetChanged();
            adpxm.setList(listXM);
            adpxm.notifyDataSetChanged();

            wxclPrice();
            jisuanzongjia();

        }else {
            initdata();
        }
    }

    /**
     * 材料拉数据
     */
    public void cldata() {
        listCL.clear();
        AbRequestParams params = new AbRequestParams();
        Log.i("cjn", "看看单号" + entity.getList_no());
        params.put("list_no", entity.getList_no());
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_ksbj_wxcl, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aa, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject item = jsonarray.getJSONObject(i);
                            BSD_KuaiSuBaoJia_CL_entity entity = new BSD_KuaiSuBaoJia_CL_entity();
                            entity.setReco_no(item.getDouble("reco_no"));
                            entity.setList_no(item.getString("list_no"));
                            entity.setPeij_no(item.getString("peij_no"));
                            entity.setPeij_th(item.getString("peij_th"));
                            entity.setPeij_mc(item.getString("peij_mc"));
                            entity.setPeij_dw(item.getString("peij_dw"));
                            entity.setPeij_sl(item.getDouble("peij_sl"));
                            entity.setPeij_dj(item.getDouble("peij_dj"));
                            entity.setPeij_je(item.getDouble("peij_je"));
                            entity.setPeij_jk(item.getString("peij_jk"));
                            entity.setPeij_zt(item.getString("peij_zt"));
                            listCL.add(entity);
                        }
                        adpcl.setList(listCL);
                        adpcl.notifyDataSetChanged();
                    }else {
//                        Show.show(getContext(),"无材料");
                    }

                    wxclPrice();
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
        mSpinerPopWindow3.setWidth(bsd_ksbj_rl_gsfl.getWidth());
        mSpinerPopWindow3.showAsDropDown(bsd_ksbj_rl_gsfl);
    }

//
    /**
     * 工时费率数据
     */
    /**
     * //     * 基本信息车组接口
     * //
     */
    public void bumen3() {

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

                Log.i("cjn", "看看是不是这里的问题" + value);
                if (!bsd_ksbj_tv_gsfl.getText().toString().equals(value)) {
                    bsd_ksbj_tv_gsfl.setText(value);
                    gongshifeili_name = listgslv.get(pos).get("feil_mc");
                    gongshifeili_id = listgslv.get(pos).get("feil_fl");
                    Conts.feilv_name=gongshifeili_name;
                }
            }
        });


    }

    public void gsfldata() {
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
                    bumen3();
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
     * 项目拉数据
     */
    public void xmdata() {
        listXM.clear();
        AbRequestParams params = new AbRequestParams();
        Log.i("cjn", "看看单号" + entity.getList_no());
        params.put("list_no", entity.getList_no());
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_ksbj_wxxm, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aa, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    Log.i("cjn", "查看快速报价进来的数据" + jsonObject.toString());
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject item = jsonarray.getJSONObject(i);
                            BSD_KuaiSuBaoJia_XM_entity entity = new BSD_KuaiSuBaoJia_XM_entity();
                            entity.setReco_no(item.getDouble("reco_no"));
                            entity.setList_no(item.getString("list_no"));
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
                            listXM.add(entity);
                        }
                        Log.i("cjn", "listXM.size" + listXM.size());
                        adpxm.setList(listXM);
                        adpxm.notifyDataSetChanged();
                    } else {
//                        Show.showTime(getActivity(), jsonObject.get("message").toString());
//                        Show.show(getContext(),"无项目");
                    }

                    jisuanzongjia();
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
                Show.showTime(getActivity(), "网络连接超时");
            }
        });


    }

    /*
     *判断是怎me进来的
     */
    public void initdata2() {
        if (Conts.zt == 0) {
            //没有数据的时候跳转过来的
            if (Conts.cp == null || Conts.cp.equals("")) {
                Car = "没有获取车牌";
                bsd_ksbj_cp.setText(Car);
            } else {
                Car = Conts.cp;
                bsd_ksbj_cp.setText(Car);
            }

        } else if (Conts.zt == 1) {
//            有一个条数据跳转过来的
            ArrayList arr = new ArrayList();
            Car = entity.getChe_no();
            bsd_ksbj_cp.setText(Car);
            String che = Conts.bycx_cheliangmingcheng;
            Conts.list_no = entity.getList_no();
            Conts.chexing = Conts.bycx_cheliangmingcheng;
            danhao = entity.getList_no();

            String[] s1 = che.split("\\|");
            for (int j = 0; j < s1.length; j++) {
                arr.add(j, s1[j]);
            }
            if (arr.size() < 4) {
            } else {
                bsd_ksbj_pp.setText(arr.get(0).toString());
                bsd_ksbj_cx.setText(arr.get(1).toString());
                bsd_ksbj_cz.setText(arr.get(2).toString());
                bsd_ksbj_cxing.setText(arr.get(3).toString());
                //车系
                bsdcx(arr.get(0).toString());
                //车组
                bsdcz(arr.get(1).toString());
                //车行
                bsd_chexingdata(arr.get(2).toString());

            }
            bsd_ksbj_tv_dh.setText("单号:" + entity.getList_no());
            bsd_ksbj_tv_sj.setText("日期:" + entity.getList_jlrq());
            bsd_ksbj_tv_gsfl.setText(entity.getList_sfbz());
            gongshifeili_name=entity.getList_sfbz();
            Conts.feilv_name=gongshifeili_name;

            tv_zong_money.setText(a + b + "");
            tv_zong_money1.setText(a + b + "");
            gongshifeili_name = entity.getList_sfbz();
            gongshifeili_id = entity.getList_gdfl();

        }    }

    public void initdata() {
        if (Conts.zt == 0) {
            //没有数据的时候跳转过来的
            if (Conts.cp == null || Conts.cp.equals("")) {
                Car = "没有获取车牌";
                bsd_ksbj_cp.setText(Car);
            } else {
                Car = Conts.cp;
                bsd_ksbj_cp.setText(Car);
            }

        } else if (Conts.zt == 1) {
//            有一个条数据跳转过来的
            ArrayList arr = new ArrayList();
            Car = entity.getChe_no();
            bsd_ksbj_cp.setText(Car);
            String che = entity.getChe_cx();

            Conts.bycx_cheliangmingcheng=entity.getChe_cx();
            Conts.list_no = entity.getList_no();
            Conts.chexing = entity.getChe_cx();
            danhao = entity.getList_no();

            String[] s1 = che.split("\\|");
            for (int j = 0; j < s1.length; j++) {
                arr.add(j, s1[j]);
            }
            if (arr.size() < 4) {
            } else {
                bsd_ksbj_pp.setText(arr.get(0).toString());
                bsd_ksbj_cx.setText(arr.get(1).toString());
                bsd_ksbj_cz.setText(arr.get(2).toString());
                bsd_ksbj_cxing.setText(arr.get(3).toString());
                //车系
                bsdcx(arr.get(0).toString());
                //车组
                bsdcz(arr.get(1).toString());
                //车行
                bsd_chexingdata(arr.get(2).toString());

            }
            if (entity.getChe_vin().toString().trim().length() < 2) {
                bsd_ksbj_vin.setText(Conts.VIN);
            } else {
                bsd_ksbj_vin.setText(entity.getChe_vin());
            }

////            bsd_wxyy_top_daochangshijian.setText(entiy.etYuyue_yjjcrq());

            bsd_ksbj_lc.setText("" + entity.getList_lc());
            bsd_ksbj_cezhu.setText(entity.getKehu_mc());
            bsd_ksbj_dh.setText(entity.getKehu_dh());
            bsd_ksbj_tv_dh.setText("单号:" + entity.getList_no());
            bsd_ksbj_tv_sj.setText("日期:" + entity.getList_jlrq());
            bsd_ksbj_tv_gsfl.setText(entity.getList_sfbz());
            tv_zong_money.setText(a + b + "");
            tv_zong_money1.setText(a + b + "");
            bsd_ksbj_tv_gcsj.setText(entity.getGcsj());
            gongshifeili_name = entity.getList_sfbz();
            gongshifeili_id = entity.getList_gdfl();
            //维修项目查询
            xmdata();
            //维修单号查询a
            cldata();


        } else if (Conts.zt == 2) {


        }


    }


    public void inin(View view) {
        bsd_ksbj_rl_bycx = (RelativeLayout) view.findViewById(R.id.bsd_ksbj_rl_bycx);

        bsd_ksbj_tv_gcsj = (TextView) view.findViewById(R.id.bsd_ksbj_tv_gcsj);
        bsd_ksbj_rl_gcsj = (RelativeLayout) view.findViewById(R.id.bsd_ksbj_rl_gcsj);
        bsd_ksbj_rl_gcsj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeShow.timePickerAlertDialog(bsd_ksbj_tv_gcsj);
            }
        });


        //工时费率
        bsd_ksbj_rl_gsfl = (RelativeLayout) view.findViewById(R.id.bsd_ksbj_rl_gsfl);
        bsd_ksbj_rl_gsfl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGongSi3();
            }
        });
        bsd_ksbj_tv_gsfl = (TextView) view.findViewById(R.id.bsd_ksbj_tv_gsfl);

        bsd_ksbj_tv_dh = (TextView) view.findViewById(R.id.bsd_ksbj_tv_dh);
        bsd_ksbj_tv_sj = (TextView) view.findViewById(R.id.bsd_ksbj_tv_sj);
        tv_zong_money = (TextView) view.findViewById(R.id.tv_zong_money);
        tv_zong_money3 = (TextView) view.findViewById(R.id.tv_zong_money3);
        tv_zong_money1 = (TextView) view.findViewById(R.id.tv_zong_money1);
        bsd_rl_money = (RelativeLayout) view.findViewById(R.id.bsd_rl_money);
        bsd_rl_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bsd_xiuGaiGongShi = new BSD_XiuGaiGongShi(getActivity(), "优惠金额",0, c,"", "修改价钱");
                bsd_xiuGaiGongShi.show();
                bsd_xiuGaiGongShi.setToopromtOnClickListener(new BSD_XiuGaiGongShi.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick(double gongshi) {
                        tv_zong_money1.setText("" + gongshi);
//                        zj();
                        bsd_xiuGaiGongShi.dismiss();
                    }
                });

            }
        });


        //存档操作
        bsd_ksbj_cd = (RelativeLayout) view.findViewById(R.id.bsd_ksbj_cd);
        bsd_ksbj_cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (danhao == null || danhao.equals("")) {
                    cundangdata();
                } else {
                    Log.i("cjn", "这是点击事件");
                    //如果有单号进行如下操作
                    updata();
                    //没有接口
                    cd_or_jc = 0;
                }
            }
        });
        //进厂操作
        bsd_ksbj_jc = (RelativeLayout) view.findViewById(R.id.bsd_ksbj_jc);
        bsd_ksbj_jc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cd_or_jc = 1;
                if (danhao == null || danhao.equals("")) {
//                    cundangdata();
                } else {
                    Log.i("cjn", "这是点击事件");
                    //如果有单号进行如下操作
                    updata();
                    //没有接口


                }
            }
        });

        //维修项目
        listxm = (ListView) view.findViewById(R.id.bsd_lv);
        //维修材料
        listcl = (ListView) view.findViewById(R.id.bsd_lv1);
        scrollview = (ScrollView) view.findViewById(R.id.scrollview);

        listcl.setOnTouchListener(new View.OnTouchListener() {
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
        listxm.setOnTouchListener(new View.OnTouchListener() {
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
        adpxm = new BSD_wxxm_adp(getActivity());
        adpxm.setUp_ksbj_gs(new BSD_wxxm_adp.Up_ksbj_gs() {
            @Override
            public void onYesClick(final int i, String name, double gongshi) {
                bsd_xiuGaiGongShi = new BSD_XiuGaiGongShi(getActivity(), name,0, gongshi, "","修改工时");
                bsd_xiuGaiGongShi.show();
                bsd_xiuGaiGongShi.setToopromtOnClickListener(new BSD_XiuGaiGongShi.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick(double gongshi) {
                        listXM.get(i).setWxxm_gs(gongshi);
//                        zj();
                        bsd_xiuGaiGongShi.dismiss();
                        jisuanzongjia();
                        adpxm.notifyDataSetChanged();
                    }
                });

            }
        });
        adpxm.setUp_ksbj_gsdj(new BSD_wxxm_adp.Up_ksbj_gsdj() {
            @Override
            public void onYesClick(final int i, String name, double gongshidanjia) {
                bsd_xiuGaiGongShi = new BSD_XiuGaiGongShi(getActivity(), name, 0,gongshidanjia,"", "修改金额");
                bsd_xiuGaiGongShi.show();
                bsd_xiuGaiGongShi.setToopromtOnClickListener(new BSD_XiuGaiGongShi.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick(double gongshi) {
                        listXM.get(i).setWxxm_je(gongshi);
//                        zj();
                        bsd_xiuGaiGongShi.dismiss();
                        jisuanzongjia();
                        adpxm.notifyDataSetChanged();
                    }
                });
            }
        });
        adpxm.setUp_ksbj_gsdj_shanchu(new BSD_wxxm_adp.Up_ksbj_gsdj_shanchu() {

            @Override
            public void onYesClick() {
                jisuanzongjia();
                adpxm.notifyDataSetChanged();
            }
        });
        listxm.setAdapter(adpxm);
        adpcl = new BSD_xzcl_adp(getActivity(), handler);
        adpcl.setKuCun(new  BSD_xzcl_adp.KuCun(){

            @Override
            public void query_kc(String peij_no) {
                //弹出配件库存明细界面；
                Bundle   bundle=new Bundle();
                bundle.putString("peij_no",peij_no);

                BSD_MeiRongKuaiXiu_KuCun_Fragment kcDialog = new BSD_MeiRongKuaiXiu_KuCun_Fragment();
                kcDialog.setArguments(bundle);
                kcDialog.show(getFragmentManager(),"kcDialog");
            }
        });
        adpcl.setShuliangzongjia(new BSD_xzcl_adp.shuliangzongjia() {
            @Override
            public void onYesClick(int shuliang, double zongjia) {
                sl = shuliang;
                zj = zongjia;
                wxclPrice();
            }
        });
        adpcl.setUpdanjia(new BSD_xzcl_adp.Updanjia() {
            @Override
            public void onYesClick(final int i, String name, double danjia) {
                bsd_xiuGaiGongShi = new BSD_XiuGaiGongShi(getActivity(), listCL.get(i).getPeij_mc(),0, listCL.get(i).getPeij_dj(),"", "修改单价");
                bsd_xiuGaiGongShi.show();
                bsd_xiuGaiGongShi.setToopromtOnClickListener(new BSD_XiuGaiGongShi.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick(double gongshi) {
                        listCL.get(i).setPeij_dj(gongshi);
                        listCL.get(i).setPeij_je(gongshi * listCL.get(i).getPeij_sl());
                        wxclPrice();
                        adpcl.notifyDataSetChanged();
                        bsd_xiuGaiGongShi.dismiss();
                    }
                });

            }
        });
        adpcl.setDelete(new BSD_xzcl_adp.Delete() {
            @Override
            public void onYesClick() {
                wxclPrice();
            }
        });
        listcl.setAdapter(adpcl);
        bsd_ksbj_cp = (TextView) view.findViewById(R.id.bsd_ksbj_cp);

        bsd_ksbj_lc = (TextView) view.findViewById(R.id.bsd_ksbj_lc);
        bsd_ksbj_cezhu = (TextView) view.findViewById(R.id.bsd_ksbj_cezhu);
        bsd_ksbj_dh = (TextView) view.findViewById(R.id.bsd_ksbj_dh);
        bsd_ksbj_vin = (TextView) view.findViewById(R.id.bsd_ksbj_vin);
        //读取vin码
        rl_duqu = (RelativeLayout) view.findViewById(R.id.tv_readvin);
        rl_duqu .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vin = bsd_ksbj_vin.getText().toString();
                if ("".equals(vin)) {
                    Toast.makeText(getActivity(), "请输入vin码", Toast.LENGTH_LONG).show();
                } else {
                    duVin();
                }
            }
        });






        bsd_ksbj_pp = (TextView) view.findViewById(R.id.bsd_ksbj_pp);
        bsd_ksbj_cx = (TextView) view.findViewById(R.id.bsd_ksbj_cx);
        bsd_ksbj_cz = (TextView) view.findViewById(R.id.bsd_ksbj_cz);
        bsd_ksbj_cxing = (TextView) view.findViewById(R.id.bsd_ksbj_cxing);


        tv_shibie = (TextView) view.findViewById(R.id.tv_shibie);
        tv_wxxm_money = (TextView) view.findViewById(R.id.tv_wxxm_money);
        tv_wxcl_money = (TextView) view.findViewById(R.id.tv_wxcl_money);


//        relat_cx = (RelativeLayout) view.findViewById(R.id.relat_cx);
        //品牌
        bsd_ksbj_pinPai_delo = new BSD_KSBJ_PinPai_delo(getActivity());
        rea_pinpai = (RelativeLayout) view.findViewById(R.id.rea_pinpai);
        bsd_ksbj_rl_cx = (RelativeLayout) view.findViewById(R.id.bsd_ksbj_rl_cx);
        bsd_ksbj_rl_cz = (RelativeLayout) view.findViewById(R.id.bsd_ksbj_rl_cz);
        bsd_ksbj_chexing = (RelativeLayout) view.findViewById(R.id.bsd_ksbj_chexing);
        //品牌监听，弹出框
        rea_pinpai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bsd_ksbj_pinPai_delo.show();
            }
        });
        bsd_ksbj_pinPai_delo.setToopromtOnClickListener(new BSD_KSBJ_PinPai_delo.ToopromtOnClickListener() {
            @Override
            public void onYesClick(String aa, String bianhao) {
                cxbianhao = bianhao;//车牌编号
                pinpaiming = aa;//车牌名称
                bsd_ksbj_pp.setText(pinpaiming);
                bsd_ksbj_cx.setText("");
                bsd_ksbj_cz.setText("");
                bsd_ksbj_cxing.setText("");
                bsd_ksbj_pinPai_delo.dismiss();
                bsdcx(cxbianhao);
            }
        });
        //车系


        bsd_ksbj_rl_cx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("cjn", "车系点击方法");

                if (bsd_ksbj_pp.getText().toString().equals("")) {
                    Show.showTime(getActivity(), "请选择品牌");
                } else {
                    if (listjbcx.size() > 0) {
                        showGongSi();
                    } else {
                        Show.showTime(getActivity(), "数据加载中请稍后");
                    }

                }

                //车组信息1


            }
        });
        //车组

        bsd_ksbj_rl_cz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bsd_ksbj_cx.getText().toString().equals("")) {
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

        bsd_ksbj_chexing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bsd_ksbj_cz.getText().toString().equals("")) {
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
        bsd_ksbj_rl_bycx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bsd_ksbj_cxing.getText().toString().equals("") ||
                        bsd_ksbj_lc.getText().toString().equals("") ||
                        bsd_ksbj_tv_gcsj.getText().toString().equals("")) {
                    Show.showTime(getActivity(), "请输入完整信息");
                } else {
                    bsd_cexifansuan();
                }
            }
        });

        //车辆信息、历史维修、历史维修建议
        bsd_ksbj_clxx = (RelativeLayout) view.findViewById(R.id.bsd_ksbj_clxx);
        bsd_ksbj_clxx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"车辆信息",Toast.LENGTH_LONG).show();
                Conts.danju_type="ksbj";
                //跳转到编辑车辆、客户信息对话框
                new BSD_MeiRongKuaiXiu_cheliangxinxi_Fragment()
                        .show(getFragmentManager(), "dialog_fragment");

            }
        });
        bsd_ksbj_lswx= (RelativeLayout) view.findViewById(R.id.bsd_ksbj_lswx);
        bsd_ksbj_lswx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Conts.danju_type="ksbj";
                new BSD_LishiWeiXiu_DialogFragment().
                        show(getFragmentManager(),"mrkx_lswx");
            }
        });
        bsd_ksbj_lswxjy= (RelativeLayout) view.findViewById(R.id.bsd_ksbj_lswxjy);
        bsd_ksbj_lswxjy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BSD_LiShiWeiXiuJianYi_DialogFragment().
                        show(getFragmentManager(),"mrkx_lswxjy");
            }
        });





    }






    /*
  *根据vin码获取车辆名称、代码、内部名称；
  */
    public void duVin() {
        listvincx.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("vinCode", bsd_ksbj_vin.getText().toString());
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
                            Log.e("vins", "dm:"+ item.getString("chex_dm"));
                            listvincx.add(map);
                        }
                        if (listvincx.size() == 1) {
                            Log.e("vin", "1条记录" );
                            //如果只查到一条记录，通过chex_dm查询相应的品牌、车系、车组、车型信息；
                            cxnm=listvincx.get(0).get("cxDm");      //车型内码
//                            cxmc=listvincx.get(0).get("cxMc");      //车型名称
//                            cxmcstd=listvincx.get(0).get("cxMcStd");   //车型内部名称；
                            getcx_by_cxdm();
                        } else if (listvincx.size() > 1) {
                            //如果查到多条记录，弹出对话框，显示chex_mc和chex_mc_std；
                            Log.e("vin", "listvincx的长度："+listvincx.size() );
                            showDialogSelectCx();
                        }
                    }
                    if (jsonObject.get("message").toString().equals("查询失败")) {
                        Toast.makeText(getActivity(),jsonObject.getString("data").toString(),Toast.LENGTH_LONG).show();
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
        Log.e("sss", "cxnm是："+cxnm );
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
                        Log.e("vin", "品牌："+s1[0]+",车系"+s1[1]+",车组"+s1[2]+",车系"+s1[3]);
                        for (int j = 0; j < s1.length; j++) {
                            arr.add(j, s1[j]);
                        }

                        bsd_ksbj_pp.setText(s1[0]);
                        bsd_ksbj_cx.setText(s1[1]);
                        bsd_ksbj_cz.setText(s1[2]);
                        bsd_ksbj_cxing.setText(s1[3]);


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
    public void  showDialogSelectCx(){
        final View  view=LayoutInflater.from(getActivity()).inflate(R.layout.bsd_clxx_dialog_for_select_cx,null);
        final Dialog dialog = new Dialog(getActivity());
        dialog.setTitle("请选择");
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params =
                dialog.getWindow().getAttributes();
        params.width = 900;
        params.height = 500 ;
        dialog.getWindow().setAttributes(params);


        ListView  lv= (ListView) window.findViewById(R.id.bsd_clxx_lv_for_select_cx);
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
                View   layout= LayoutInflater.from(getActivity()).inflate(R.layout.select_cx_dialog_item,null);
                TextView   tv_mc= (TextView) layout.findViewById(R.id.tv_cxmc);
                TextView  tv_nbmc= (TextView) layout.findViewById(R.id.tv_cx_nbmc);
                tv_mc.setText(listvincx.get(position).get("cxMc"));     //车辆名称
                tv_nbmc.setText(listvincx.get(position).get("cxMcStd"));  //车辆内部名称
                Log.e("vin", "名称： "+listvincx.get(position).get("cxMc") );
                Log.e("vin", "内部名称： "+listvincx.get(position).get("cxMcStd") );
                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        cxnm=listvincx.get(position).get("cxDm");    //车辆代码
                        Log.e("vins", "车辆代码。。。"+cxnm );
                        getcx_by_cxdm();
                    }
                });

                return layout;
            }
        });


        dialog.show();


    }











    //车型===================================================================
    public void bsd_chexingdata(String chezuid) {
        listjbchexing.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("dm", chezuid);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_Chexing, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aa, String s) {
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

                            } else {
                                map.put("chex_dm", item.getString("chex_dm"));
                                map.put("chex_mc", item.getString("chex_mc"));
                                map.put("chex_bz", item.getString("chex_bz"));
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
                if (!bsd_ksbj_cxing.getText().toString().equals(value)) {
                    bsd_ksbj_cxing.setText(value);

                    pinpai = bsd_ksbj_pp.getText().toString();
                    chexi = bsd_ksbj_cx.getText().toString();
                    chezx = bsd_ksbj_cz.getText().toString();
                    chexing = bsd_ksbj_cxing.getText().toString();


                    if (bsd_ksbj_pp.getText().toString().equals("") ||
                            bsd_ksbj_cx.getText().toString().equals("") ||
                            bsd_ksbj_cz.getText().toString().equals("") ||
                            bsd_ksbj_cxing.getText().toString().equals("")
                            ) {
                        che_cx = "";
                    } else {

                        che_cx = pinpai + "|" + chexi + "|" + chezx + "|" + chexing;
                        Conts.chexing = che_cx;

                    }

                    chexingid = listjbchexing.get(pos).get("chex_bz");
                }

            }
        });


    }


    private void showGongSi2() {
        mSpinerPopWindow2.setWidth(bsd_ksbj_chexing.getWidth());
        mSpinerPopWindow2.showAsDropDown(bsd_ksbj_chexing);
    }


    //车组=======================================================================

    private void showGongSi1() {
        mSpinerPopWindow1.setWidth(bsd_ksbj_rl_cz.getWidth());
        mSpinerPopWindow1.showAsDropDown(bsd_ksbj_rl_cz);
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
                if (!bsd_ksbj_cz.getText().toString().equals(value)) {
                    bsd_ksbj_cz.setText(value);
                    bsd_ksbj_cxing.setText("");
                    chezuid = listjbcz.get(pos).get("chex_dm");

                    bsd_chexingdata(chezuid);

                }

//                bsd_gs_name = bst_text_gs.getText().toString().trim();
//                sbd_user(GongSiNo);
            }
        });


    }


//    车系=================================================

    /**
     * 基本信息车系接口
     */

    private void showGongSi() {
        mSpinerPopWindow.setWidth(bsd_ksbj_rl_cx.getWidth());
        mSpinerPopWindow.showAsDropDown(bsd_ksbj_rl_cx);
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
                String value = nameList.get(pos).toString();
                if (!bsd_ksbj_cx.getText().toString().equals(value)) {
                    bsd_ksbj_cx.setText(value);
                    chexiid = listjbcx.get(pos).get("chex_dm");
                    bsd_ksbj_cz.setText("");
                    bsd_ksbj_cxing.setText("");
                    bsdcz(chexiid);
                }

//                bsd_gs_name = bst_text_gs.getText().toString().trim();
//                sbd_user(GongSiNo);
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
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject item = jsonarray.getJSONObject(i);
                            Map<String, String> map = new HashMap<String, String>();

                            map.put("chex_dm", item.getString("chex_dm"));
                            map.put("chex_mc", item.getString("chex_mc"));
                            listjbcx.add(map);
                        }
                        bumen();
                    } else {
                        bumen();
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


//   ========================================

    @Override
    public void onResume() {
//        XinXiSelet(0);
        super.onResume();


        //zongjia();

        //wxclzongjia();
    }

    /**
     * 计算维修项目的总价格
     */
    double b = 0;

    public void jisuanzongjia() {
        double bb = 0;
        for (int i = 0; i < listXM.size(); i++) {
            bb = bb + (listXM.get(i).getWxxm_je());
        }
        tv_wxxm_money.setText(bb + "");
        Log.i("cjn", "XM的总价：" + bb);
        b = bb;
        zoongjia();
    }

    /**
     * 计算维修材料的总价
     */
    double a = 0;
    double c = 0;

    public void wxclPrice() {
        double jg = 0;
        for (int i = 0; i < listCL.size(); i++) {
            jg = jg + (listCL.get(i).getPeij_dj() * listCL.get(i).getPeij_sl());
        }
        a = jg;

        tv_wxcl_money.setText(jg + "");
        Log.i("cjn", "CL的总价：" + jg);
        zoongjia();
    }

    public void zoongjia() {

        Log.i("jslm", "zoongjia: 1231231313");

        c = a + b;
        tv_zong_money.setText(a + b + "");
        tv_zong_money1.setText(a + b + "");
    }

    public void POP(View view) {
        //pop传来的项目
        bsd_ksbj_xm_pop = new BSD_WXYY_XM_POP(getActivity());
        bsd_ksbj_xm_pop.setClist(new BSD_WXYY_XM_POP.chuanlist() {
            @Override
            public void onYesClick(BSD_wxyy_xm_pop_entiy entity, double wxxmdj) {
                //这里是传过来的数据


                //数据库非空判断
                if (listXM.size() > 0) {
                    //不是空循环对比，如果成立记录下来
                    for (int i = 0; i < listXM.size(); i++) {
                        if (listXM.get(i).getWxxm_no().equals(entity.getWxxm_no())) {
                            choufutianjia = 1;
                            break;
                        }
                    }
                    //循环外判断，如果有，这一步做商品数量+法运算
                    if (choufutianjia == 1) {
                        Show.showTime(getActivity(), "添加重复");
                        choufutianjia = 0;
                    } else {
                        //如果没有存过存储
                        BSD_KuaiSuBaoJia_XM_entity item = new BSD_KuaiSuBaoJia_XM_entity();
                        item.setWxxm_mc(entity.getWxxm_mc());
                        item.setWxxm_gs(entity.getWxxm_gs());
//                        String a = String.valueOf(wxxmdj / entity.getWxxm_gs());
//                        if (a.equals("NaN")){
//                            item.setWxxm_dj(wxxmdj);
//                        }else {
//                            item.setWxxm_dj(wxxmdj / entity.getWxxm_gs());
//                        }
                        if(entity.getWxxm_gs()==0){
                            entity.setWxxm_gs(1.0);
                        }
                        item.setWxxm_dj(wxxmdj / entity.getWxxm_gs());
                        Toast.makeText(getActivity(),"添加后返回的单价是"+item.getWxxm_dj()+"",Toast.LENGTH_SHORT).show();
                        item.setWxxm_je(wxxmdj);
                        item.setWxxm_no(entity.getWxxm_no());
                        item.setWxxm_cb(entity.getWxxm_cb());
                        item.setWxxm_zt("正常");
                        listXM.add(item);
                        Show.showTime(getActivity(), "成功");
                        adpxm.notifyDataSetChanged();
                    }

                } else {
                    BSD_KuaiSuBaoJia_XM_entity item = new BSD_KuaiSuBaoJia_XM_entity();
                    item.setWxxm_mc(entity.getWxxm_mc());
                    item.setWxxm_gs(entity.getWxxm_gs());
//
//                    String a = String.valueOf(wxxmdj / entity.getWxxm_gs());
//                    if (a.equals("NaN")){
//                        item.setWxxm_dj(wxxmdj);
//                    }else {
//                        item.setWxxm_dj(wxxmdj / entity.getWxxm_gs());
//                    }
                    if(entity.getWxxm_gs()==0){
                        Log.i("gsdj", "是0 ");
                        entity.setWxxm_gs(1.0);
                    }
                    Log.i("gsdj", "不是0 ");
                    item.setWxxm_dj(wxxmdj / entity.getWxxm_gs());
                    Log.i("gsdj", "不是0 000000");
                    item.setWxxm_je(wxxmdj);
                    item.setWxxm_no(entity.getWxxm_no());
                    item.setWxxm_cb(entity.getWxxm_cb());
                    item.setWxxm_zt("正常");
                    listXM.add(item);
                    Show.showTime(getActivity(), "成功");
                    Log.i("gsdj", "不是011111111");
                    Log.i("123", "这是快速报价的FRagment的单价" + wxxmdj);
                    adpxm.notifyDataSetChanged();
                }

                adpxm.setList(listXM);
                listxm.setAdapter(adpxm);
                Log.i("123", "===========================" + listXM.size());
                jisuanzongjia();
                adpxm.notifyDataSetChanged();


            }
        });
        //pop传来的材料
        bsd_ksbj_cl_pop = new BSD_KSBJ_CL_POP(getActivity());
        bsd_ksbj_cl_pop.setChuanlistcl(new BSD_KSBJ_CL_POP.chuanlistcl() {
            @Override
            public void onYesClick(BSD_wxyy_cl_pop_entity entity, double jiaqian) {

                if (listCL.size() > 0) {
                    for (int i = 0; i < listCL.size(); i++) {
                        if (listCL.get(i).getPeij_no().equals(entity.getPeij_no())) {

                            choufutianjia = 1;
                            break;
                        }
                    }
                    if (choufutianjia == 1) {
                        Show.showTime(getActivity(), "添加重复");
                        choufutianjia = 0;
                    } else {
                        BSD_KuaiSuBaoJia_CL_entity item = new BSD_KuaiSuBaoJia_CL_entity();
                        item.setReco_no(entity.getReco_no1());
                        item.setPeij_no(entity.getPeij_no());
                        //名字
                        item.setPeij_mc(entity.getPeij_mc());
                        //数量
                        item.setPeij_sl(1);
                        item.setPeij_je(item.getPeij_sl() * jiaqian);
                        //单价
                        item.setPeij_dj(jiaqian);
//                        item.setPeij_dj(1110);
                        //单位
                        item.setPeij_dw(entity.getPeij_dw());
                        item.setPeij_th(entity.getPeij_th());
                        //状态
                        item.setPeij_zt("正常");
//                            item.setPeij_dw(entity.getWaib_dw());
                        listCL.add(item);
                        Show.showTime(getActivity(), "成功");


                    }


                } else {
                    BSD_KuaiSuBaoJia_CL_entity item = new BSD_KuaiSuBaoJia_CL_entity();
                    item.setReco_no(entity.getReco_no1());
                    item.setPeij_no(entity.getPeij_no());
                    //名字
                    item.setPeij_mc(entity.getPeij_mc());
                    //数量
                    item.setPeij_sl(1);
                    //单价
                    item.setPeij_dj(jiaqian);

//                    item.setPeij_dj(1230);



                    //单位
                    item.setPeij_th(entity.getPeij_th());
                    //状态
                    item.setPeij_zt("正常");
                    item.setPeij_dw(entity.getPeij_dw());
                    listCL.add(item);
                    Show.showTime(getActivity(), "成功");
                }

                adpcl.setList(listCL);
                listcl.setAdapter(adpcl);
                adpcl.notifyDataSetChanged();
            }
        });

        //历史报价

        bsd_lsbj = (LinearLayout) view.findViewById(R.id.bsd_lsbj);
        bsd_lsbj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).upqhf(view);

            }
        });
        //维修材料
        bsd_wxcl = (RelativeLayout) view.findViewById(R.id.bsd_wxxm1);
        bsd_wxcl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                bsd_wxcl.setEnabled(false);
                bsd_ksbj_cl_pop.showPopupWindow(beijing, 0);
                bsd_ksbj_cl_pop.gb(new BSD_KSBJ_CL_POP.Guanbi() {
                    @Override
                    public void guanbi() {
                        bsd_wxcl.setEnabled(true);
                    }
                });
            }
        });


        //维修项目
        bsd_wxxm = (RelativeLayout) view.findViewById(R.id.bsd_wxxm);
        bsd_wxxm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pinpai = bsd_ksbj_pp.getText().toString();
                chexi = bsd_ksbj_cx.getText().toString();
                chezx = bsd_ksbj_cz.getText().toString();
                chexing = bsd_ksbj_cxing.getText().toString();

                if (bsd_ksbj_pp.getText().toString().equals("") ||
                        bsd_ksbj_cx.getText().toString().equals("") ||
                        bsd_ksbj_cz.getText().toString().equals("") ||
                        bsd_ksbj_cxing.getText().toString().equals("")
                        ) {
                    che_cx = "";
                } else {
                    che_cx = pinpai + "|" + chexi + "|" + chezx + "|" + chexing;
                    Conts.chexing = che_cx;
                }
//                bsd_wxxm.setEnabled(false);
                bsd_ksbj_xm_pop.showPopupWindow(beijing, 0);
                bsd_ksbj_xm_pop.reightdata();
                bsd_ksbj_xm_pop.gb(new BSD_WXYY_XM_POP.Guanbi() {
                    @Override
                    public void guanbi() {
                        bsd_wxxm.setEnabled(true);
                    }
                });
            }

        });


    }

    String chepai;
    String pinpai;
    String chexi;
    String chexing;
    String chezx;
    String che_cx;

    String VIN;
    String jinchanglicheng;
    String chezhu;
    String dinahua;

    /**
     * 存档操作没有单号的时候
     */
    public void cundangdata() {
        chepai = bsd_ksbj_cp.getText().toString();

        pinpai = bsd_ksbj_pp.getText().toString();
        chexi = bsd_ksbj_cx.getText().toString();
        chezx = bsd_ksbj_cz.getText().toString();
        chexing = bsd_ksbj_cxing.getText().toString();
        if (bsd_ksbj_pp.getText().toString().equals("") ||
                bsd_ksbj_cx.getText().toString().equals("") ||
                bsd_ksbj_cz.getText().toString().equals("") ||
                bsd_ksbj_cxing.getText().toString().equals("")
                ) {
            che_cx = "";
        } else {
            che_cx = pinpai + "|" + chexi + "|" + chezx + "|" + chexing;
        }
        VIN = bsd_ksbj_vin.getText().toString();
        jinchanglicheng = bsd_ksbj_lc.getText().toString();
        dinahua = bsd_ksbj_dh.getText().toString();
        chezhu = bsd_ksbj_cezhu.getText().toString();
        AbRequestParams params = new AbRequestParams();
        params.put("gongsiNo", MyApplication.shared.getString("GongSiNo", ""));
        params.put("caozuoyuan_xm", MyApplication.shared.getString("name", ""));
        params.put("che_no", chepai);//车牌
        params.put("che_cx", che_cx);//品牌车型车组
        params.put("che_vin", VIN);//Vin码
        params.put("yuyue_yjjclc", jinchanglicheng);//进厂里程
        params.put("kehu_mc", chezhu);//车主用户
        params.put("kehu_dh", dinahua);//电话
        params.put("List_sfbz", gongshifeili_name);
        params.put("List_sffl", gongshifeili_id);
        params.put("kehu_no", Conts.kehu_no);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_ksbj_jbxxtj, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                if (s.toString().equals("null")) {
                    Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
                } else {
//  这里是添加材料和项目的接口。没有


// datacl(s.toString().trim());

// datacl(s.toString().trim());

//                    dataCD(s.toString().trim());
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


//    /**
//     * 如果有有单号，则进行以下操作
//     * 没有接口
//     */
//    public void updata() {
//        danhao=entity.getList_no();
//        chepai = bsd_ksbj_cp.getText().toString();
//
//        pinpai = bsd_ksbj_pp.getText().toString();
//        chexi = bsd_ksbj_cx.getText().toString();
//        chezhu = bsd_ksbj_cz.getText().toString();
//        chexing = bsd_ksbj_cxing.getText().toString();
//        che_cx = pinpai + "|" + chexi + "|" + chezu + "|" + chexing;
//
//        VIN = bsd_ksbj_vin.getText().toString();
//        jinchanglicheng = bsd_ksbj_lc.getText().toString();
//        dinahua = bsd_ksbj_dh.getText().toString();
//
//        AbRequestParams params = new AbRequestParams();
//        params.put("", danhao);//快速报价单号
//        params.put("che_cx", che_cx);//品牌，车系，车组，车行，che_cx
//        params.put("che_vin", VIN);//VIN码
//        params.put("yuyue_yjjclc", jinchanglicheng);//进厂里程
//        params.put("kehu_mc", chezhu);//客户司机
//        params.put("kehu_dh", dinahua);//手机号
//
//
//    }

//    /**
//     * 传入单号点击存档时候时使用
//     * cl的东西
//     * BSD_wxyy_clcd
//     */
//    String clcdjson;
//
//
//
//
//
//    }


    /**
     * 有订单是修改，e
     */
    private void updata() {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        Log.i("cjn", "存档开始");
        danhao = entity.getList_no();
        chepai = bsd_ksbj_cp.getText().toString();

        pinpai = bsd_ksbj_pp.getText().toString();
        chexi = bsd_ksbj_cx.getText().toString();
        chezx = bsd_ksbj_cz.getText().toString();
        chexing = bsd_ksbj_cxing.getText().toString();


        if (bsd_ksbj_pp.getText().toString().equals("") ||
                bsd_ksbj_cx.getText().toString().equals("") ||
                bsd_ksbj_cz.getText().toString().equals("") ||
                bsd_ksbj_cxing.getText().toString().equals("")
                ) {
            che_cx = "";
        } else {
            che_cx = pinpai + "|" + chexi + "|" + chezx + "|" + chexing;
        }
        VIN = bsd_ksbj_vin.getText().toString();
        jinchanglicheng = bsd_ksbj_lc.getText().toString();
        dinahua = bsd_ksbj_dh.getText().toString();
        chezhu = bsd_ksbj_cezhu.getText().toString();

        Log.i("cjn", "存档开始" + "单号==" + danhao);
        AbRequestParams params = new AbRequestParams();
        params.put("che_no", Car);
        params.put("list_no", danhao);//预约单号
        params.put("che_cx", che_cx);//品牌，车系，车组，车行，che_cx
        params.put("che_vin", VIN);//VIN码
        params.put("List_lc", jinchanglicheng);//进厂里程
        params.put("List_yjjclc", jinchanglicheng);//进厂里程
        params.put("kehu_mc", chezhu);//客户司机
        params.put("kehu_dh", dinahua);//手机号
        params.put("List_sfbz", gongshifeili_name);
        params.put("List_sffl", gongshifeili_id);
        params.put("kehu_no", Conts.kehu_no);//客户名称
        params.put("gcsj", bsd_ksbj_tv_gcsj.getText().toString().trim());
        params.put("list_hjje", tv_zong_money.getText().toString());
        params.put("list_hjje_yh", tv_zong_money1.getText().toString());
        params.put("gcsj", bsd_ksbj_tv_gcsj.getText().toString());

        Log.i("cjn", "查看这些参数"
                + "--che_no--" + Car
                + "--list_no--" + danhao
                + "--che_cx--" + che_cx
                + "--che_vin--" + VIN
                + "--List_lc--" + jinchanglicheng
                + "--kehu_mc--" + chezhu
                + "--kehu_dh--" + dinahua
                + "--List_sfbz--" + gongshifeili_name
                + "--List_sffl--" + gongshifeili_id
                + "--kehu_no--" + Conts.kehu_no + "--gcsj--" + bsd_ksbj_tv_gcsj.getText().toString().trim());


        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_ksbj_bcjbxx, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("cjn", "存档成功");
                Log.i("cjn", "快速报价存档操作完成返回什么？" + s.toString().trim());

//                if (listXM.size() > 0) {
                dataCD(s.toString().trim());
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
                Show.showTime(getActivity(), "网络请求超时");
                Log.i("cjn", "存档失败返回" + s);
                WeiboDialogUtils.closeDialog(mWeiboDialog);

            }
        });


    }


    /**
     * 快速报价添加材料
     */
    String json;

    public void dataCD(final String DH) {

        if (listXM.size() > 0) {
//        json = "{" + '"' + "yuyue_no" + '"' + ":" + '"' + DH + '"' + "," + '"' + "data" + '"' + ":" + "[";
            json = "{" + '"' + "data" + '"' + ":" + "[";
            for (int i = 0; i < listXM.size() - 1; i++) {
                json = json + "{" + '"' + "list_no" + '"' + ":" + '"' + DH + '"' + "," + '"' +
                        "wxxm_mc" + '"' + ":" + '"' + listXM.get(i).getWxxm_mc() + '"' + "," + '"' +
                        "wxxm_gs" + '"' + ":" + '"' + listXM.get(i).getWxxm_gs() + '"' + "," + '"' +
                        "wxxm_dj" + '"' + ":" + '"' + listXM.get(i).getWxxm_dj() + '"' + "," + '"' +
                        "wxxm_je" + '"' + ":" + '"' + listXM.get(i).getWxxm_je() + '"' + "," + '"' +
                        "wxxm_cb" + '"' + ":" + '"' + listXM.get(i).getWxxm_cb() + '"' + "," + '"' +
                        "wxxm_no" + '"' + ":" + '"' + listXM.get(i).getWxxm_no() + '"' + "," + '"' +
                        "wxxm_zt" + '"' + ":" + '"' + "正常" + '"' + "}" + ",";
            }
            json = json + "{" + '"' + "list_no" + '"' + ":" + '"' + DH + '"' + "," + '"' +
                    "wxxm_mc" + '"' + ":" + '"' + listXM.get(listXM.size() - 1).getWxxm_mc() + '"' + "," + '"' +
                    "wxxm_gs" + '"' + ":" + '"' + listXM.get(listXM.size() - 1).getWxxm_gs() + '"' + "," + '"' +
                    "wxxm_dj" + '"' + ":" + '"' + listXM.get(listXM.size() - 1).getWxxm_dj() + '"' + "," + '"' +
                    "wxxm_je" + '"' + ":" + '"' + listXM.get(listXM.size() - 1).getWxxm_je() + '"' + "," + '"' +
                    "wxxm_cb" + '"' + ":" + '"' + listXM.get(listXM.size() - 1).getWxxm_cb() + '"' + "," + '"' +
                    "wxxm_no" + '"' + ":" + '"' + listXM.get(listXM.size() - 1).getWxxm_no() + '"' + "," + '"' +
                    "wxxm_zt" + '"' + ":" + '"' + "正常" + '"' + "}" + "]" + "}";
//        + "]" + "}"
        } else {
            json = "{" + '"' + "data" + '"' + ":" + '[' + ']' + "}";
        }
        Log.i("cjn", "最后一次查看json" + json);
        AbRequestParams params = new AbRequestParams();
        params.put("json", json);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_ksbj_tjxm, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int a, String s) {
                Log.i("cjn", "返回个啥XM" + s.toString());
//                if (listCL.size() > 0) {
                datacl(DH);
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
                Log.i("CJN", "CD存档失败");
            }
        });

    }


    /**
     * 添加材料
     */
    String clcdjson;

    public void datacl(final String DH) {
        if (listCL.size() > 0) {
            clcdjson = "{" + '"' + "data" + '"' + ":" + "[";
            for (int i = 0; i < listCL.size() - 1; i++) {
                clcdjson = clcdjson + "{" + '"' + "list_no" + '"' + ":" + '"' + DH + '"' + "," + '"' +
                        "peij_no" + '"' + ":" + '"' + listCL.get(i).getPeij_no() + '"' + "," + '"' +
                        "peij_mc" + '"' + ":" + '"' + listCL.get(i).getPeij_mc() + '"' + "," + '"' +
                        "peij_sl" + '"' + ":" + '"' + listCL.get(i).getPeij_sl() + '"' + "," + '"' +
                        "peij_dj" + '"' + ":" + '"' + listCL.get(i).getPeij_dj() + '"' + "," + '"' +
                        "peij_je" + '"' + ":" + '"' + listCL.get(i).getPeij_je() + '"' + "," + '"' +
                        "peij_th" + '"' + ":" + '"' + listCL.get(i).getPeij_th() + '"' + "," + '"' +
                        "peij_dw" + '"' + ":" + '"' + listCL.get(i).getPeij_dw() + '"' + "," + '"' +
                        "peij_zt" + '"' + ":" + '"' + "正常" + '"' + "}" + ",";
            }
            clcdjson = clcdjson + "{" + '"' + "list_no" + '"' + ":" + '"' + DH + '"' + "," + '"' +
                    "peij_no" + '"' + ":" + '"' + listCL.get(listCL.size() - 1).getPeij_no() + '"' + "," + '"' +
                    "peij_mc" + '"' + ":" + '"' + listCL.get(listCL.size() - 1).getPeij_mc() + '"' + "," + '"' +
                    "peij_sl" + '"' + ":" + '"' + listCL.get(listCL.size() - 1).getPeij_sl() + '"' + "," + '"' +
                    "peij_dj" + '"' + ":" + '"' + listCL.get(listCL.size() - 1).getPeij_dj() + '"' + "," + '"' +
                    "peij_je" + '"' + ":" + '"' + listCL.get(listCL.size() - 1).getPeij_je() + '"' + "," + '"' +
                    "peij_th" + '"' + ":" + '"' + listCL.get(listCL.size() - 1).getPeij_th() + '"' + "," + '"' +
                    "peij_dw" + '"' + ":" + '"' + listCL.get(listCL.size() - 1).getPeij_dw() + '"' + "," + '"' +
                    "peij_zt" + '"' + ":" + '"' + "正常" + '"' + "}" + "]" + "}";
        } else {
            clcdjson = "{" + '"' + "data" + '"' + ":" + '[' + ']' + "}";
        }
        Log.i("cjn", "clcdjson========================" + clcdjson);
        AbRequestParams params = new AbRequestParams();
        params.put("json", clcdjson);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_ksbj_tjcl, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("cjn", "返回个啥CL" + s.toString());
                if (cd_or_jc == 0) {
                    if (s.toString().equals("存档成功")) {
                        WeiboDialogUtils.closeDialog(mWeiboDialog);
                        queRen = new QueRen(getActivity(), "存档成功");
                        queRen.show();
                        queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                            @Override
                            public void onYesClick() {
                                queRen.dismiss();
                                ((MainActivity) getActivity()).upBSD_KSBJ_Log();
                            }
                        });

                    }
                }
                if (cd_or_jc == 1) {
                    jinchangdata(DH);
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
                Log.i("cjn", "材料存档失败");
            }
        });


    }

    //进厂操作
    public void jinchangdata(String no) {

        Log.i("cjn", "进厂操作");
        AbRequestParams params = new AbRequestParams();
        params.put("no", no);
        params.put("gongsiNo", MyApplication.shared.getString("bsd_gs_id", ""));
        params.put("caozuoyuan_xm", MyApplication.shared.getString("bsd_user_name", ""));
        Log.i("cjn", "查看进厂数据" +
                "no===" + no +
                "gongsiNo===" + MyApplication.shared.getString("bsd_gs_id", "") +
                "caozuoyuan_xm===" + MyApplication.shared.getString("bsd_user_name", "")
        );

        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_ksbj_jc, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("cjn", "查看返回什么数据" + s.toString());
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
                                WeiboDialogUtils.closeDialog(mWeiboDialog);
                                Conts.wxjdtiaozhuan = 1;
                                Conts.cp = Car;
                                ((MainActivity) getActivity()).upwxywdd();
                                queRen_quxiao.dismiss();
                            }

                            @Override
                            public void onCancel() {
                                WeiboDialogUtils.closeDialog(mWeiboDialog);
                                queRen_quxiao.dismiss();
                                ((MainActivity) getActivity()).upBSD_KSBJ_Log();
                            }
                        });
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
                Log.i("cjn", "进厂失败" + s);
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });


    }

    @Override
    public void onClick(View view) {

    }


    TextView bsd_02_text;

    public void bsdtext(View view) {
        bsd_02_text = (TextView) view.findViewById(R.id.bsd_02_text);
        bsd_02_text.setText("公司名称 :   " + MyApplication.shared.getString("GongSiMc", "") +
                "                  公司电话 :   " + MyApplication.shared.getString("danw_dh", ""));
    }

    String getCar;

    public void bsd_cexifansuan() {
        String id;
        AbRequestParams params = new AbRequestParams();
        params.put("quanMing", bsd_ksbj_cxing.getText().toString());

        Log.i("cjn", "查看这个车型" + bsd_ksbj_cxing.getText().toString());
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_chexing_fansuan, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("cjn", "查看" + s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
//                if (jsonObject.getString("message").toString().equals("查询成功")){
                    getCar = jsonObject.getString("data");
//                }
                    Conts.bycx_type = 0;//快速报价
                    if (getCar.length() > 0) {
                        chexingid = getCar;
                    }
                    Conts.bycx_chexing = chexingid;//车型
                    Log.i("cjn", "asdasdasdasdasda" + chexingid + "11111111111" + getCar);
                    int a;
                    double b;
                    b = Double.parseDouble(bsd_ksbj_lc.getText().toString().trim());
                    a = (int) b;
                    Conts.bycx_licheng = "" + a;//里程
                    Conts.bycx_time = bsd_ksbj_tv_gcsj.getText().toString().trim();//时间
                    Conts.bycx_pinpai = bsd_ksbj_pp.getText().toString().trim();
                    Conts.bycx_chexi = bsd_ksbj_cx.getText().toString().trim();
                    Conts.bycx_chezu = bsd_ksbj_cz.getText().toString().trim();
                    Conts.bycx_cxing = bsd_ksbj_cxing.getText().toString().trim();
                    Conts.bycx_VIN = bsd_ksbj_vin.getText().toString().trim();
                    Conts.bycx_CarName = bsd_ksbj_cezhu.getText().toString().trim();
                    Conts.bycx_Shouji = bsd_ksbj_dh.getText().toString().trim();

                    Conts.bycx_cheliangmingcheng=bsd_ksbj_pp.getText().toString().trim() + "|" + bsd_ksbj_cx.getText().toString().trim() + "|" + bsd_ksbj_cz.getText().toString().trim() + "|" + bsd_ksbj_cxing.getText().toString().trim();
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
