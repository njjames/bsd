package com.example.administrator.boshide2.Modular.Fragment.LiShiJieDan;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

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
import com.example.administrator.boshide2.Modular.Fragment.LiShiJieDan.Adapter.BSD_WXJD_CL_xiangqing_adp;
import com.example.administrator.boshide2.Modular.Fragment.LiShiJieDan.Adapter.BSD_WXJD_XM_xiangqingadp;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_CL_Entity;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_Entity;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_XM_Entity;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.PopWindow.BSD_WXJD_CL_POP;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.PopWindow.BSD_WXJD_XM_POP;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.BSD_WXYY_XM_POP;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.BSD_XiuGaiGongShi;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.Pop_Entity.BSD_wxyy_cl_pop_entity;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.Pop_Entity.BSD_wxyy_xm_pop_entiy;
import com.example.administrator.boshide2.Modular.View.SpinerPopWindow;
import com.example.administrator.boshide2.Modular.View.diaog.QueRen;
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
 * @维修接单碎片页 Created by Administrator on 2017-4-13.
 */

public class BSD_WeiXiuJieDan_xiangqing_Fragment extends Fragment implements View.OnClickListener {

    ScrollView scrollview;
    RelativeLayout beijing;

    // 切换碎片页
    RelativeLayout bsd_lsbj;
    //////////////////////////////////////////////////////////////////
    TextView bsd_wxjd_tv_chepai;//车牌
    TextView bsd_wxjd_et_jinchanglicheng;//进厂里程
    TextView bsd_wxjd_et_name;//车主
    TextView bsd_wxjd_et_shouji;//手机
    TextView bsd_wxjd_et_vin;//VIN码
    TextView bsd_wxjd_tv_pinpai;//品牌
    TextView bsd_wxjd_tv_chexi;//车系
    TextView bsd_wxjd_tv_chezu;//车组
    TextView bsd_wxjd_tv_chexing;//车型
    TextView bsd_wxjd_et_qiankuan;//欠款
    TextView bsd_wxjd_et_huiyuan;//会员卡号
    TextView tv_wxjd_clhj, tv_wxjd_xmhj, tv_wxjd_hj;//项目金额。材料金额，总金额
    //车牌号
    String Car;
    //维修接单基本信息实体类
    BSD_WeiXiuJieDan_Entity entity;
    //单号
    String danhao;
    //项目维修
    BSD_WXJD_XM_POP bsd_wxjd_xm_pop;
    //材料选择
    BSD_WXJD_CL_POP bsd_wxjd_cl_pop;
    ListView listxm;//维修项目
    ListView listcl;//维修材料
    //维修项目
    BSD_WXJD_XM_xiangqingadp adp_xm;
    List<BSD_WeiXiuJieDan_XM_Entity> list_XM = new ArrayList<>();
    //选择材料
    BSD_WXJD_CL_xiangqing_adp adp_cl;
    List<BSD_WeiXiuJieDan_CL_Entity> list_CL = new ArrayList<>();
    int choufutianjia = 0;

    public int jc_or_cd;

    //修改工时弹框
    BSD_XiuGaiGongShi bsd_xiuGaiGongShi;


    private Dialog mWeiboDialog;
    QueRen queRen;
    //品牌   车系   车组   车型
//    RelativeLayout bsd_wxjd_pinpai, bsd_ksbj_RLchexi, bsd_ksbj_chezu, bsd_ksbj_rl_chexing;
//    //品牌 车系车组 车行
//    BSD_KSBJ_PinPai_delo bsd_ksbj_pinPai_delo;
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

    TextView bsd_wxjd_tv_dh, bsd_wxjd_tv_sj;
    //工时费率
//    RelativeLayout bsd_wxjd_rl_gsfl;
    TextView bsd_wxjd_tv_gsfl;


    List<Map<String, String>> listgslv = new ArrayList<Map<String, String>>();

    private List<CustemObject> nameList3 = new ArrayList<CustemObject>();
    private AbstractSpinerAdapter mAdapter3;
    private SpinerPopWindow mSpinerPopWindow3;
    String gongshifeili_name;
    String gongshifeili_id;


    TimePickerShow timePickerShow;

    RelativeLayout bsd_weixiujiedan_fh;
    String che = "";
    URLS url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bsd_wxjd_xiangqing_fragment, null);
        url = new URLS();
        entity = new BSD_WeiXiuJieDan_Entity();
        entity = ((MainActivity) getActivity()).getWxjdentity();
        Log.i("cjn", "entity=========================" + entity.getWork_no());
        Log.i("cjn", "Conts.cp=========================" + Conts.cp);
        timePickerShow = new TimePickerShow(getActivity());
        gsfldata();
        init(view);

        bsdtext(view);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        initdata();
        list_CL.clear();
        list_XM.clear();


        if (Conts.bycx_fanhuitype == 1) {
            Conts.bycx_fanhuitype = 0;
            bsd_wxjd_tv_pinpai.setText(Conts.bycx_pinpai);
            bsd_wxjd_tv_chexi.setText(Conts.bycx_chexi);
            bsd_wxjd_tv_chezu.setText(Conts.bycx_chezu);
            bsd_wxjd_tv_chexing.setText(Conts.bycx_cxing);
            bsd_wxjd_et_jinchanglicheng.setText(Conts.bycx_licheng);
            bsd_wxjd_et_vin.setText(Conts.bycx_VIN);
            bsd_wxjd_et_name.setText(Conts.bycx_CarName);
            bsd_wxjd_et_shouji.setText(Conts.bycx_Shouji);
            bsd_wxjd_et_huiyuan.setText(Conts.bycx_huiyuan);

        }


    }

    private void init(View view) {
        bsd_weixiujiedan_fh = (RelativeLayout) view.findViewById(R.id.bsd_weixiujiedan_fh);
        bsd_weixiujiedan_fh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).uplishijiedan(view);
            }
        });

        bsd_wxjd_tv_gsfl = (TextView) view.findViewById(R.id.bsd_wxjd_tv_gsfl);

        bsd_wxjd_tv_sj = (TextView) view.findViewById(R.id.bsd_wxjd_tv_sj);
        bsd_wxjd_tv_dh = (TextView) view.findViewById(R.id.bsd_wxjd_tv_dh);


        //获取首页东西
        bsd_wxjd_tv_chepai = (TextView) view.findViewById(R.id.bsd_wxjd_tv_chepai);
        bsd_wxjd_et_jinchanglicheng = (TextView) view.findViewById(R.id.bsd_wxjd_et_jinchanglicheng);
        bsd_wxjd_et_name = (TextView) view.findViewById(R.id.bsd_wxjd_et_name);
        bsd_wxjd_et_shouji = (TextView) view.findViewById(R.id.bsd_wxjd_et_shouji);
        bsd_wxjd_et_vin = (TextView) view.findViewById(R.id.bsd_wxjd_et_vin);
        bsd_wxjd_tv_pinpai = (TextView) view.findViewById(R.id.bsd_wxjd_tv_pinpai);
        bsd_wxjd_tv_chexi = (TextView) view.findViewById(R.id.bsd_wxjd_tv_chexi);
        bsd_wxjd_tv_chezu = (TextView) view.findViewById(R.id.bsd_wxjd_tv_chezu);
        bsd_wxjd_tv_chexing = (TextView) view.findViewById(R.id.bsd_wxjd_tv_chexing);
        bsd_wxjd_tv_chexing = (TextView) view.findViewById(R.id.bsd_wxjd_tv_chexing);
        bsd_wxjd_et_huiyuan = (TextView) view.findViewById(R.id.bsd_wxjd_et_huiyuan);
        tv_wxjd_clhj = (TextView) view.findViewById(R.id.tv_wxjd_clhj);
        tv_wxjd_hj = (TextView) view.findViewById(R.id.tv_wxjd_hj);
        tv_wxjd_xmhj = (TextView) view.findViewById(R.id.tv_wxjd_xmhj);
        bsd_lsbj = (RelativeLayout) view.findViewById(R.id.bsd_lsbj);

        bsd_wxjd_xm_pop = new BSD_WXJD_XM_POP(getActivity());
        bsd_wxjd_xm_pop.setClist(new BSD_WXYY_XM_POP.chuanlist() {
            @Override
            public void onYesClick(BSD_wxyy_xm_pop_entiy entity, double wxxmdj) {
                Log.i("cjn", "这个集合长度是几" + list_XM.size());
                if (list_XM.size() > 0) {
                    Log.i("cjn", "这里是多条数据走的方法" + list_XM.size());
                    for (int i = 0; i < list_XM.size(); i++) {
                        if (list_XM.get(i).getWxxm_no().equals(entity.getWxxm_no())) {

                            choufutianjia = 1;
                            break;
                        }
                    }
                    if (choufutianjia == 1) {
                        Log.i("cjn", "这个不该走啊" + list_XM.size());
                        Show.showTime(getActivity(), "添加重复");
                        choufutianjia = 0;
                    } else {

                        BSD_WeiXiuJieDan_XM_Entity item = new BSD_WeiXiuJieDan_XM_Entity();
                        item.setWxxm_mc(entity.getWxxm_mc());
                        item.setWxxm_gs(entity.getWxxm_gs());
                        item.setWxxm_dj(wxxmdj);
                        item.setWxxm_cb(entity.getWxxm_cb());
                        item.setWxxm_no(entity.getWxxm_no());
                        item.setWxxm_zt("正常");
                        list_XM.add(item);
                        Show.showTime(getActivity(), "成功");
                    }

                } else {
                    Log.i("cjn", "这里是111条数据走的方法" + list_XM.size());
                    BSD_WeiXiuJieDan_XM_Entity item = new BSD_WeiXiuJieDan_XM_Entity();
                    item.setWxxm_mc(entity.getWxxm_mc());
                    item.setWxxm_gs(entity.getWxxm_gs());
                    item.setWxxm_dj(wxxmdj);
                    item.setWxxm_no(entity.getWxxm_no());
                    item.setWxxm_zt("正常");
                    list_XM.add(item);
                    Show.showTime(getActivity(), "11111成功");
                }

                adp_xm.setList(list_XM);
                listxm.setAdapter(adp_xm);
                adp_xm.notifyDataSetChanged();
            }
        });


        bsd_wxjd_cl_pop = new BSD_WXJD_CL_POP(getActivity());
        bsd_wxjd_cl_pop.setChuanlistcl(new BSD_WXJD_CL_POP.chuanlistcl() {
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
                    item.setPeij_dj(jiaqian);
                    //单位
                    item.setPeij_th(entity.getPeij_th());
                    //状态
                    item.setPeij_zt("正常");
                    item.setPeij_dw(entity.getPeij_dw());
                    list_CL.add(item);
                    Show.showTime(getActivity(), "11111成功");
                }

                adp_cl.setList(list_CL);
                listcl.setAdapter(adp_cl);
                adp_cl.notifyDataSetChanged();

            }
        });

        //弹出框布局

        //数据atap
        listxm = (ListView) view.findViewById(R.id.bsd_lv);
        //维修材料
        listcl = (ListView) view.findViewById(R.id.bsd_lv1);
        //维修材料
        adp_cl = new BSD_WXJD_CL_xiangqing_adp(getContext());
        //维修项目
        adp_xm = new BSD_WXJD_XM_xiangqingadp(getActivity());
        listxm.setAdapter(adp_xm);
        listcl.setAdapter(adp_cl);
        beijing = (RelativeLayout) getActivity().findViewById(R.id.beijing);
        //维修项目

        //滑动
        scrollview = (ScrollView) view.findViewById(R.id.scrollview);
        //解决滑动问题
        huadong();

//        //品牌弹框
//        bsd_ksbj_pinPai_delo = new BSD_KSBJ_PinPai_delo(getActivity());
////        bsd_wxyy_pp = (RelativeLayout) view.findViewById(R.id.bsd_wxyy_pp);
//        bsd_wxjd_pinpai.setOnClickListener(new View.OnClickListener()
//
//        {
//            @Override
//            public void onClick(View view) {
//                bsd_ksbj_pinPai_delo.show();
//            }
//        });
//
//
//        bsd_ksbj_pinPai_delo.setToopromtOnClickListener(new BSD_KSBJ_PinPai_delo.ToopromtOnClickListener()
//
//        {
//            @Override
//            public void onAdd(String aa, String bianhao) {
//                cxbianhao = bianhao;//车牌编号
//                pinpaiming = aa;//车牌名称
//                bsd_wxjd_tv_pinpai.setText(pinpaiming);
//                bsd_wxjd_tv_chexi.setText("");
//                bsd_wxjd_tv_chezu.setText("");
//                bsd_wxjd_tv_chexing.setText("");
//                bsd_ksbj_pinPai_delo.dismiss();
//                bsdcx(cxbianhao);
//            }
//        });
//
//        //车系
//
//        bsd_ksbj_RLchexi.setOnClickListener(new View.OnClickListener()
//
//        {
//            @Override
//            public void onClick(View view) {
//                Log.i("cjn", "车系点击方法");
//
//                if (bsd_wxjd_tv_pinpai.getText().toString().equals("")) {
//                    Show.showTime(getActivity(), "请选择品牌");
//                } else {
//                    showGongSi();
//                }
//
//                //车组信息1
//
//
//            }
//        });
//        //车组
//
//        bsd_ksbj_chezu.setOnClickListener(new View.OnClickListener()
//
//        {
//            @Override
//            public void onClick(View view) {
//                if (bsd_wxjd_tv_chexi.getText().toString().equals("")) {
//                    Show.showTime(getActivity(), "请选择车系");
//
//                } else {
//                    showGongSi1();
//                }
//            }
//        });
//
//        //车行
//
//        bsd_ksbj_rl_chexing.setOnClickListener(new View.OnClickListener()
//
//        {
//            @Override
//            public void onClick(View view) {
//                if (bsd_wxjd_tv_chezu.getText().toString().equals("")) {
//                    Show.showTime(getActivity(), "请选择车组");
//
//                } else {
//                    showGongSi2();
//                }
//
//
//            }
//        });


    }


//    private void showGongSi3() {
//        mSpinerPopWindow3.setWidth(bsd_wxjd_rl_gsfl.getWidth());
//        mSpinerPopWindow3.showAsDropDown(bsd_wxjd_rl_gsfl);
//    }

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
                if (!bsd_wxjd_tv_gsfl.getText().toString().equals(value)) {
                    bsd_wxjd_tv_gsfl.setText(value);
                    gongshifeili_name = listgslv.get(pos).get("feil_mc");
                    gongshifeili_id = listgslv.get(pos).get("feil_fl");
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


    //品牌    车系   车组     车型

    //项目金额
    double j = 0;

    public void wxjd_xm_money() {
        double jj = 0;
        for (int i = 0; i < list_XM.size(); i++) {
            jj = jj + (list_XM.get(i).getWxxm_dj() * list_XM.get(i).getWxxm_gs());
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
                    if (jsonObject.get("status").toString().equals("1")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject item = jsonarray.getJSONObject(i);
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("chex_dm", item.getString("chex_dm"));
                            map.put("chex_mc", item.getString("chex_mc"));
                            map.put("chex_bz", item.getString("chex_bz"));
                            listjbchexing.add(map);
                        }

                    }
                    bumen2();
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
                    if (jsonObject.get("status").toString().equals("1")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject item = jsonarray.getJSONObject(i);
                            Map<String, String> map = new HashMap<String, String>();

                            map.put("chex_dm", item.getString("chex_dm"));
                            map.put("chex_mc", item.getString("chex_mc"));
                            listjbcz.add(map);
                        }
                    }

                    bumen1();
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
    public void initdata() {
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

            che = entity.getChe_cx();
            Log.i("lsjd", "che=======111111111111" + che);
            danhao = entity.getWork_no();
            Log.i("cjn", "查看数据cx---" + che);

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
                //车系
                bsdcx(arr.get(0).toString());
                //车组
                bsdcz(arr.get(1).toString());
                //车行
                bsd_chexingdata(arr.get(2).toString());

            }

//                if (che.length() > 4) {
//                    String[] s1 = che.split("\\|");
//                    for (int j = 0; j < s1.length; j++) {
//                        arr.add(j, s1[j]);
//                    }
//                    if (arr.size() < 4) {
//                    } else {
//                        bsd_wxjd_tv_pinpai.setText(arr.get(0).toString());
//                        bsd_wxjd_tv_chexi.setText(arr.get(1).toString());
//                        bsd_wxjd_tv_chezu.setText(arr.get(2).toString());
//                        bsd_wxjd_tv_chexing.setText(arr.get(3).toString());
//                        Conts.chexing = bsd_wxjd_tv_chexing.getText().toString();
//                        //车系
//                        bsdcx(arr.get(0).toString());
//                        //车组
//                        getCheZuInfo(arr.get(1).toString());
//                        //车行
//                        getCheXingInfo(arr.get(2).toString());
//                    }
//                } else {
//                    bsd_wxjd_tv_pinpai.setText("");
//                    bsd_wxjd_tv_chexi.setText("");
//                    bsd_wxjd_tv_chezu.setText("");
//                    bsd_wxjd_tv_chexing.setText("");
//                }

        }
        bsd_wxjd_et_vin.setText(entity.getChe_vin());
////            bsd_wxyy_top_daochangshijian.setText(entiy.getYuyue_yjjcrq());
        bsd_wxjd_et_jinchanglicheng.setText("" + entity.getXche_lc());
        bsd_wxjd_et_name.setText(entity.getKehu_mc());
        bsd_wxjd_et_shouji.setText(entity.getKehu_dh());
        bsd_wxjd_et_huiyuan.setText(entity.getCard_no());
        bsd_wxjd_tv_gsfl.setText(entity.getXche_sfbz());
        bsd_wxjd_tv_dh.setText("单号:" + entity.getWork_no());
        bsd_wxjd_tv_sj.setText("时间:" + entity.getXche_jdrq());
        //维修项目查询
        xmdata();
//            维修单号查询
        cldata();

//            dataxm();

//            cldata();

    }


    /**
     * 维修材料列表
     */

    public void cldata() {
        list_CL.clear();
//        BSD_wxjd_cllb
        AbRequestParams params = new AbRequestParams();
        Log.i("cjn", "看看单号" + entity.getWork_no());
        params.put("work_no", entity.getWork_no());
//        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxjd_cllb, params, new AbStringHttpResponseListener() {
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxjd_lscl, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int a, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    Log.e("返回值", "查询材料成功"+s);
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
                            Log.e("返回值", "012300000");
                            list_CL.add(entity);
                        }
                        adp_cl.setList(list_CL);
                        listcl.setAdapter(adp_cl);
                        adp_cl.notifyDataSetChanged();
                    }
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
                Log.e("返回值", "查询材料00000失败了"+s);
            }
        });


    }


    /**
     * 维修项目列表
     */
    public void xmdata() {
        list_XM.clear();
//        BSD_wxjd_xmlb
        AbRequestParams params = new AbRequestParams();
        Log.i("cjn", "看看单号" + entity.getWork_no());
        params.put("work_no", entity.getWork_no());
//        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxjd_xmlb, params, new AbStringHttpResponseListener() {
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxjd_lsxm, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aaa, String s) {
                try {
                    Log.e("返回值", "查询项目成功"+s);
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
                        adp_xm.setList(list_XM);
                        adp_xm.notifyDataSetChanged();

                    }
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
                Log.e("返回值", "查询项目失败了"+s);
            }
        });

    }



    /**
     * 解决滑动问题
     */
    public void huadong() {
        //解决滑动问题
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
        params.put("work_no", danhao);
        params.put("che_cx", che_cx);
        params.put("che_vin", VIN);
        params.put("xche_lc", jinchanglicheng);
        params.put("kehu_mc", chezhu);
        params.put("kehu_dh", dinahua);
        params.put("xche_sfbz", gongshifeili_name);
        params.put("xche_sffl", gongshifeili_id);

        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxjd_jbxxtj, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("cjn", "查看返回的单号" + s.toString());
                if (list_XM.size() > 0) {
                    XMinData(s.toString().trim());
                } else {
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                    Show.showTime(getActivity(), "请添加材料");
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
//                                    public void onAdd() {
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
        Log.i("cjn", "clcdjson========================" + clcdjson);
        AbRequestParams params = new AbRequestParams();
        params.put("json", clcdjson);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxjd_addcl, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("cjn", "CL是否成功" + s.toString());

                if (s.toString().equals("存档成功")) {
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

        json = "{" + '"' + "data" + '"' + ":" + "[";
        for (int i = 0; i < list_XM.size() - 1; i++) {
            json = json + "{" + '"' + "work_no" + '"' + ":" + '"' + DH + '"' + "," + '"' + "wxxm_mc" + '"' + ":" + '"' + list_XM.get(i).getWxxm_mc() + '"' + "," + '"' +
                    "wxxm_gs" + '"' + ":" + '"' + list_XM.get(i).getWxxm_gs() + '"' + "," + '"' +
                    "wxxm_dj" + '"' + ":" + '"' + list_XM.get(i).getWxxm_dj() + '"' + "," + '"' +
                    "wxxm_no" + '"' + ":" + '"' + list_XM.get(i).getWxxm_no() + '"' + "," + '"' +
                    "wxxm_je" + '"' + ":" + '"' + list_XM.get(i).getWxxm_je() + '"' + "," + '"' +
                    "wxxm_cb" + '"' + ":" + '"' + list_XM.get(i).getWxxm_cb() + '"' + "," + '"' +
                    "wxxm_zt" + '"' + ":" + '"' + "正常" + '"' +
                    "wxxm_tpye" + '"' + ":" + '"' + "正常" + '"' + "}" + ",";
        }


        json = json + "{" + '"' + "work_no" + '"' + ":" + '"' + DH + '"' + "," + '"' + "wxxm_mc" + '"' + ":" + '"' + list_XM.get(list_XM.size() - 1).getWxxm_mc() + '"' + "," + '"' +
                "wxxm_gs" + '"' + ":" + '"' + list_XM.get(list_XM.size() - 1).getWxxm_gs() + '"' + "," + '"' +
                "wxxm_dj" + '"' + ":" + '"' + list_XM.get(list_XM.size() - 1).getWxxm_dj() + '"' + "," + '"' +
                "wxxm_no" + '"' + ":" + '"' + list_XM.get(list_XM.size() - 1).getWxxm_no() + '"' + "," + '"' +
                "wxxm_je" + '"' + ":" + '"' + list_XM.get(list_XM.size() - 1).getWxxm_je() + '"' + "," + '"' +
                "wxxm_cb" + '"' + ":" + '"' + list_XM.get(list_XM.size() - 1).getWxxm_cb() + '"' + "," + '"' +
                "wxxm_zt" + '"' + ":" + '"' + "正常" +
                "wxxm_tpye" + '"' + ":" + '"' + "正常"
                + '"' + "}" + "]" + "}";
//        + "]" + "}"

        Log.i("cjn", "最后一次查看json" + json);
        AbRequestParams params = new AbRequestParams();
        params.put("json", json);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxjd_addxm, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("cjn", "XM是否成功" + s.toString());
                if (list_CL.size() > 0) {
                    CLinData(DH);
                } else {
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                    Show.showTime(getActivity(), "请添加材料");
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
                if (s.toString().equals("进厂成功")) {
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                    queRen = new QueRen(getActivity(), "进厂成功");
                    queRen.show();
                    queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                        @Override
                        public void onYesClick() {
                            queRen.dismiss();
                            ((MainActivity) getActivity()).upBSD_WXJD_log();
                        }
                    });
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

    TextView bsd_01_text;

    public void bsdtext(View view) {
        bsd_01_text = (TextView) view.findViewById(R.id.bsd_12_text);
        bsd_01_text.setText("公司名称 :   " + MyApplication.shared.getString("GongSiMc", "") +
                "                  公司电话 :   " + MyApplication.shared.getString("danw_dh", ""));
    }
}