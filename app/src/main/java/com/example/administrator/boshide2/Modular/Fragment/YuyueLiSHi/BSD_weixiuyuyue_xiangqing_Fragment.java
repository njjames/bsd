package com.example.administrator.boshide2.Modular.Fragment.YuyueLiSHi;

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
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.Entity.BSD_WeiXiuYuYue_Cl_entity;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.Entity.BSD_WeiXiuYueYue_entiy;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.Entity.BSD_WeiXiyYuYue_XM_entity;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.BSD_WXYY_CL_POP;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.BSD_WXYY_XM_POP;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.BSD_XiuGaiGongShi;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.Pop_Entity.BSD_wxyy_cl_pop_entity;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.Pop_Entity.BSD_wxyy_xm_pop_entiy;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.Sql.BSD_WeiXiyYueYue_XM_entity_Dao;
import com.example.administrator.boshide2.Modular.Fragment.YuyueLiSHi.Adapter.BSD_WXYY_CL_xiangqing_adp;
import com.example.administrator.boshide2.Modular.Fragment.YuyueLiSHi.Adapter.BSD_WXYY_XM_xiangqing_adp;
import com.example.administrator.boshide2.Modular.View.SpinerPopWindow;
import com.example.administrator.boshide2.Modular.View.Time.TimeDialog1;
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
 * @维修预约碎片页 Created by Administrator on 2017-4-13.
 */

public class BSD_weixiuyuyue_xiangqing_Fragment extends Fragment  {
    ListView listxm;//维修项目
    ListView listcl;//维修材料
    ScrollView scrollview;
    RelativeLayout beijing;
    // 切换碎片页
    RelativeLayout bsd_lsbj;
    //弹出popwin
    RelativeLayout bsd_wxxm, bsd_wxxm1;

    //维修项目
    BSD_WeiXiyYueYue_XM_entity_Dao XM_Dao;
    BSD_WXYY_XM_xiangqing_adp adp_xm;
    List<HashMap<String, String>> data = new ArrayList<>();
    //选择材料
    BSD_WXYY_CL_xiangqing_adp adp_cl;
    List<HashMap<String, String>> data1 = new ArrayList<>();

    //项目维修
    BSD_WXYY_XM_POP bsd_wxyy_xm_pop;
    //材料选择
    BSD_WXYY_CL_POP bsd_wxyy_cl_pop;
    //车牌号
    public String Car;
    //车牌
    TextView bsd_wxyy_cp;

    BSD_WeiXiuYueYue_entiy entiy;
    //品牌
    TextView bsd_wxyy_top_pinpai;
    //车系
    TextView bsd_wxyy_top_chexi;
    //车组
    TextView bsd_wxyy_top_chezu;
    //车行
    TextView bsd_wxyy_top_chexing;
    //VIN码
    TextView bsd_wxyy_top_vin;
    //到场时间
    TextView bsd_wxyy_top_daochangshijian;
    //进厂里程
    TextView bsd_wxyy_top_jinchanglicheng;
    //车主司机
    TextView bsd_wxyy_top_chezhusiji;
    //电话
    TextView bsd_wxyy_top_dianhua;


    List<BSD_WeiXiyYuYue_XM_entity> list_XM = new ArrayList<>();
    List<BSD_WeiXiuYuYue_Cl_entity> list_CL = new ArrayList<>();
    //存档

    String json;
    String danhao;
    int choufutianjia = 0;

    int sl;
    double zj;
    QueRen queRen;
    //修改工时弹框
    BSD_XiuGaiGongShi bsd_xiuGaiGongShi;
    //项目和材料总计金额
    TextView bsd_wxyy_cl_zj, bsd_wxyy_xm_zj, tv_wxyy_zongji;
    //项目和金河
    double clzj, xmzj;
    private Dialog mWeiboDialog;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 10) { // 更改选中商品的总价格


            }
        }
    };
    //品牌 车系车组 车行
//    BSD_KSBJ_PinPai_delo bsd_ksbj_pinPai_delo;
//    RelativeLayout bsd_wxyy_pp, bsd_wxyy_rl_chexi, bsd_wxyy_rl_chezu, bsd_wxyy_cl_chexing;
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

    TextView bsd_wxyy_tv_dh, bsd_ksbj_tv_sj;


    //工时费率
    RelativeLayout bsd_wxyy_rl_gsfl;
    TextView bsd_wxyy_tv_gsfl;
    List<Map<String, String>> listgslv = new ArrayList<Map<String, String>>();

    private List<CustemObject> nameList3 = new ArrayList<CustemObject>();
    private AbstractSpinerAdapter mAdapter3;
    private SpinerPopWindow mSpinerPopWindow3;
    String gongshifeili_name;
    String gongshifeili_id;

    //    RelativeLayout  bsd_wxyy_rl_bycx;
    TextView bsd_wxyy_tv_gcsj;

    TimePickerShow timePickerShow;

    RelativeLayout bsd_wxyy_rl_daochangshijian;

    URLS url;

    RelativeLayout bsd_weixiuyuyeu_fh, bsd_yuls_xiugairiqi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bsd_wxyy_xiangqiang_fragment, null);
        url = new URLS();
        bsd_yuls_xiugairiqi = (RelativeLayout) view.findViewById(R.id.bsd_yuls_xiugairiqi);
        entiy = new BSD_WeiXiuYueYue_entiy();
        entiy = ((MainActivity) getActivity()).getEntiy();
        timePickerShow = new TimePickerShow(getActivity());
        gsfldata();
        bsdtext(view);
        inits(view);


        init(view);

//        popup();
        return view;


    }

    @Override
    public void onStart() {
        super.onStart();
        initdata();
        list_XM.clear();
        list_CL.clear();
        if (Conts.bycx_fanhuitype == 1) {
            Conts.bycx_fanhuitype = 0;
            bsd_wxyy_top_pinpai.setText(Conts.bycx_pinpai);
            bsd_wxyy_top_chexi.setText(Conts.bycx_chexi);
            bsd_wxyy_top_chezu.setText(Conts.bycx_chezu);
            bsd_wxyy_top_chexing.setText(Conts.bycx_cxing);
            bsd_wxyy_top_jinchanglicheng.setText(Conts.bycx_licheng);
            bsd_wxyy_tv_gcsj.setText(Conts.bycx_time);
            bsd_wxyy_top_vin.setText(Conts.bycx_VIN);
            bsd_wxyy_top_chezhusiji.setText(Conts.bycx_CarName);
            bsd_wxyy_top_dianhua.setText(Conts.bycx_Shouji);
            bsd_wxyy_top_daochangshijian.setText(Conts.bycx_daochangshijian);

        }


    }


    //    public void shuaxin(){
//        XM_Dao.startReadableDatabase();
//        list_XM_ZS  = XM_Dao.queryList();
//        XM_Dao.closeDatabase();
//        adp_xm = new BSD_WXYY_XM_adp(getActivity(), list_XM_ZS);
//        listxm.setAdapter(adp_xm);
//        adp_xm.notifyDataSetChanged();
//    }

    /**
     * 维修项目数据查询
     */
    public void dataxm() {
        AbRequestParams params = new AbRequestParams();
        Log.i("cjn", "看看单号" + entiy.getYuyue_no());
        params.put("yuyue_no", entiy.getYuyue_no());

        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxyy_xm, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int a, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    Log.i("yyxm", "onSuccess: =="+s);
                    if (jsonObject.get("status").toString().equals("1")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject item = jsonarray.getJSONObject(i);
                            BSD_WeiXiyYuYue_XM_entity entity = new BSD_WeiXiyYuYue_XM_entity();
                            entity.setReco_no(item.getDouble("reco_no"));
                            entity.setYuyue_no(item.getString("yuyue_no"));
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

                        adp_xm.setList(list_XM);
                        listxm.setAdapter(adp_xm);
                        adp_xm.notifyDataSetChanged();
//                        Log.i("cjn", "这个只是傻" + list_XM.get(0).getWxxm_mc());
//                        addWxxm(list_XM);


                    } else {
                        Show.showTime(getActivity(), jsonObject.get("message").toString());

                    }
                    hejiXM();

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
    public void initdata() {
        if (Conts.zt == 0) {
            //没有数据的时候跳转过来的
            if (Conts.cp == null || Conts.cp.equals("")) {
                Car = "没有获取车牌";
                bsd_wxyy_cp.setText(Car);
            } else {
                Car = Conts.cp;
                bsd_wxyy_cp.setText(Car);
            }

        } else if (Conts.zt == 1) {
//            有一个条数据跳转过来的

            ArrayList arr = new ArrayList();
            Conts.yuyue_no = entiy.getYuyue_no();
            Car = entiy.getChe_no();
            bsd_wxyy_cp.setText(Car);
            String che = entiy.getChe_cx();
            Conts.chexing = entiy.getChe_cx();
            danhao = entiy.getYuyue_no();
            Log.i("cjn", "查看数据" + che);
            String[] s1 = che.split("\\|");
            for (int j = 0; j < s1.length; j++) {
                arr.add(j, s1[j]);
            }
            if (arr.size() < 4) {
            } else {
                bsd_wxyy_top_pinpai.setText(arr.get(0).toString());
                bsd_wxyy_top_chexi.setText(arr.get(1).toString());
                bsd_wxyy_top_chezu.setText(arr.get(2).toString());
                bsd_wxyy_top_chexing.setText(arr.get(3).toString());

                //车系
                bsdcx(arr.get(0).toString());
                //车组
                bsdcz(arr.get(1).toString());
                //车行
                bsd_chexingdata(arr.get(2).toString());
            }
            bsd_wxyy_top_vin.setText(entiy.getChe_vin());
            bsd_wxyy_top_daochangshijian.setText(entiy.getYuyue_yjjcrq());
            bsd_wxyy_top_jinchanglicheng.setText("" + entiy.getYuyue_yjjclc());
            bsd_wxyy_top_chezhusiji.setText(entiy.getKehu_mc());
            bsd_wxyy_top_dianhua.setText(entiy.getKehu_dh());
            bsd_wxyy_tv_gsfl.setText(entiy.getYuyue_sfbz());
            bsd_wxyy_tv_dh.setText("单号:" + entiy.getYuyue_no());
            bsd_ksbj_tv_sj.setText("时间:" + entiy.getYuyue_jlrq());

            //维修项目查询
            dataxm();
            //维修单号查询
            cldata();

        } else if (Conts.zt == 2) {


        }


    }

    public void inits(View view) {

        bsd_weixiuyuyeu_fh = (RelativeLayout) view.findViewById(R.id.bsd_weixiuyuyeu_fh);
        bsd_weixiuyuyeu_fh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).upLSYY();
            }
        });

        bsd_wxyy_tv_gsfl = (TextView) view.findViewById(R.id.bsd_wxyy_tv_gsfl);
        bsd_ksbj_tv_sj = (TextView) view.findViewById(R.id.bsd_ksbj_tv_sj);
        bsd_wxyy_tv_dh = (TextView) view.findViewById(R.id.bsd_wxyy_tv_dh);
        bsd_wxyy_cl_zj = (TextView) view.findViewById(R.id.bsd_wxyy_cl_zj);
        bsd_wxyy_xm_zj = (TextView) view.findViewById(R.id.bsd_wxyy_xm_zj);
        bsd_wxyy_cl_zj = (TextView) view.findViewById(R.id.bsd_wxyy_cl_zj);
        bsd_wxyy_xm_zj = (TextView) view.findViewById(R.id.bsd_wxyy_xm_zj);
        adp_cl = new BSD_WXYY_CL_xiangqing_adp(getActivity());

        adp_cl.setShuliangzongjia(new BSD_WXYY_CL_xiangqing_adp.shuliangzongjia() {
            @Override
            public void onYesClick(int shuliang, double zongjia) {
                hejiCL();
            }
        });
        adp_cl.setUpdanjia(new BSD_WXYY_CL_xiangqing_adp.Updanjia() {
            @Override
            public void onYesClick(final int i, String name, double danjia) {
                bsd_xiuGaiGongShi = new BSD_XiuGaiGongShi(getActivity(), list_CL.get(i).getPeij_mc(), 0, list_CL.get(i).getPeij_dj(),"" ,"修改单价");
                bsd_xiuGaiGongShi.show();
                bsd_xiuGaiGongShi.setToopromtOnClickListener(new BSD_XiuGaiGongShi.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick(double gongshi) {
                        list_CL.get(i).setPeij_dj(gongshi);
                        list_CL.get(i).setPeij_je(gongshi * list_CL.get(i).getPeij_sl());
                        hejiCL();
                        adp_cl.notifyDataSetChanged();
                        bsd_xiuGaiGongShi.dismiss();

                    }
                });
            }
        });
        adp_cl.setDeleteCL(new BSD_WXYY_CL_xiangqing_adp.DeleteCL() {
            @Override
            public void onYesClick() {
                hejiCL();
            }
        });
        adp_xm = new BSD_WXYY_XM_xiangqing_adp(getActivity());
//        //修改工时
//        adp_xm.setUpgongshi(new BSD_WXYY_XM_adp.upgongshi() {
//            @Override
//            public void onAdd(final int i, String name, double gongshi) {
//                bsd_xiuGaiGongShi = new BSD_XiuGaiGongShi(getActivity(), name, gongshi, "修改工时");
//                bsd_xiuGaiGongShi.show();
//                bsd_xiuGaiGongShi.setToopromtOnClickListener(new BSD_XiuGaiGongShi.ToopromtOnClickListener() {
//                    @Override
//                    public void onAdd(double gongshi) {
//                        list_XM.get(i).setWxxm_gs(gongshi);
//                        hejiXM();
//                        bsd_xiuGaiGongShi.dismiss();
//                        adp_xm.notifyDataSetChanged();
//                    }
//                });
//            }
//        });
//        //修改工时单价
//        adp_xm.setUpgongshidanjia(new BSD_WXYY_XM_adp.upgongshidanjia() {
//            @Override
//            public void onAdd(final int i, String name, double gongshi) {
//                bsd_xiuGaiGongShi = new BSD_XiuGaiGongShi(getActivity(), name, gongshi, "修改工时单价");
//                bsd_xiuGaiGongShi.show();
//                bsd_xiuGaiGongShi.setToopromtOnClickListener(new BSD_XiuGaiGongShi.ToopromtOnClickListener() {
//                    @Override
//                    public void onAdd(double gongshi) {
//                        list_XM.get(i).setWxxm_dj(gongshi);
//                        zj();
//                        hejiXM();
//                        bsd_xiuGaiGongShi.dismiss();
//                        adp_xm.notifyDataSetChanged();
//                    }
//                });
//
//            }
//        });
//        //维修预约--项目-删除
//        adp_xm.setShanchuXM(new BSD_WXYY_XM_adp.ShanchuXM() {
//            @Override
//            public void onAdd() {
//                hejiXM();
//            }
//        });
        //存档
        XM_Dao = new BSD_WeiXiyYueYue_XM_entity_Dao(getActivity());

        bsd_wxyy_cp = (TextView) view.findViewById(R.id.bsd_wxyy_cp);

        //品牌
        bsd_wxyy_top_pinpai = (TextView) view.findViewById(R.id.bsd_wxyy_top_pinpai);

        //车系
        bsd_wxyy_top_chexi = (TextView) view.findViewById(R.id.bsd_wxyy_top_chexi);
        //车组
        bsd_wxyy_top_chezu = (TextView) view.findViewById(R.id.bsd_wxyy_top_chezu);
        //车行
        bsd_wxyy_top_chexing = (TextView) view.findViewById(R.id.bsd_wxyy_top_chexing);
        //VIN码
        bsd_wxyy_top_vin = (TextView) view.findViewById(R.id.bsd_wxyy_top_vin);
        //到场时间
        bsd_wxyy_top_daochangshijian = (TextView) view.findViewById(R.id.bsd_wxyy_top_daochangshijian);
        //进厂里程
        bsd_wxyy_top_jinchanglicheng = (TextView) view.findViewById(R.id.bsd_wxyy_top_jinchanglicheng);
        //车主司机
        bsd_wxyy_top_chezhusiji = (TextView) view.findViewById(R.id.bsd_wxyy_top_chezhusiji);
        //电话
        bsd_wxyy_top_dianhua = (TextView) view.findViewById(R.id.bsd_wxyy_top_dianhua);

    }


    private void showGongSi3() {
        mSpinerPopWindow3.setWidth(bsd_wxyy_rl_gsfl.getWidth());
        mSpinerPopWindow3.showAsDropDown(bsd_wxyy_rl_gsfl);
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
                if (!bsd_wxyy_tv_gsfl.getText().toString().equals(value)) {
                    bsd_wxyy_tv_gsfl.setText(value);
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


    //品牌    车系   车组    车行
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
                if (!bsd_wxyy_top_chexing.getText().toString().equals(value)) {
                    bsd_wxyy_top_chexing.setText(value);

                    if (bsd_wxyy_top_pinpai.getText().toString().equals("") ||
                            bsd_wxyy_top_chexi.getText().toString().equals("") ||
                            bsd_wxyy_top_chezu.getText().toString().equals("") ||
                            bsd_wxyy_top_chexing.getText().toString().equals("")
                            ) {
                        che_cx = "";
                    } else {

                        che_cx = pinpai + "|" + chexi + "|" + chezhu + "|" + chexing;
                        Conts.chexing = che_cx;
                    }
                    chexingid = listjbchexing.get(pos).get("chex_bz");


                }

            }
        });


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
                if (!bsd_wxyy_top_chezu.getText().toString().equals(value)) {
                    bsd_wxyy_top_chezu.setText(value);
                    bsd_wxyy_top_chexing.setText("");
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
                if (!bsd_wxyy_top_chexi.getText().toString().equals(value)) {
                    bsd_wxyy_top_chexi.setText(value);
                    chexiid = listjbcx.get(pos).get("chex_dm");
                    bsd_wxyy_top_chezu.setText("");
                    bsd_wxyy_top_chexing.setText("");
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

    //项目合计
    double jg = 0;

    public void hejiXM() {
        double j = 0;
        for (int i = 0; i < list_XM.size(); i++) {
            j = j + (list_XM.get(i).getWxxm_dj() * list_XM.get(i).getWxxm_gs());
        }
        bsd_wxyy_xm_zj.setText(j + "");
        Log.i("cjn", "wxyuXM的总价：" + j);
        jg = j;
        wxyy_zongjia();
    }

    //材料合计
    double jgxl = 0;

    public void hejiCL() {
        double xl = 0;
        for (int i = 0; i < list_CL.size(); i++) {
            xl = xl + (list_CL.get(i).getPeij_dj() * list_CL.get(i).getPeij_sl());
        }
        bsd_wxyy_cl_zj.setText(xl + "");
        Log.i("cjn", "wxyuCL的总价：" + xl);
        jgxl = xl;
        wxyy_zongjia();
    }

    public void wxyy_zongjia() {
        tv_wxyy_zongji.setText(jg + jgxl + "");

    }


    /**
     * 修改日期
     */
    public void genggaidata() {
        AbRequestParams params = new AbRequestParams();
        String time = bsd_wxyy_top_daochangshijian.getText().toString().trim() + ":00";
        params.put("yuyue_no", Conts.yuyue_no);
        params.put("pz", time);
        Log.i("cjn", "查看单号+" + Conts.yuyue_no + "查看日期" + time);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_xiugaishijian, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("cjn", "查看返回记过" + s);
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

    public void init(View view) {
        bsd_yuls_xiugairiqi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeDialog1 timeShow;
                timeShow = new TimeDialog1(getActivity());
                timeShow.timePickerAlertDialog(bsd_wxyy_top_daochangshijian);
                timeShow.setQueRenXiugai(new TimeDialog1.QueRenXiugai() {
                    @Override
                    public void onYesClick() {
                        genggaidata();
                    }
                });


            }
        });
//        bsd_wxyy_rl_gcsj = (RelativeLayout) view.findViewById(R.id.bsd_wxyy_rl_gcsj);
//        bsd_wxyy_rl_bycx = (RelativeLayout) view.findViewById(R.id.bsd_wxyy_rl_bycx);
        bsd_wxyy_tv_gcsj = (TextView) view.findViewById(R.id.bsd_wxyy_tv_gcsj);
//        bsd_wxyy_rl_gcsj.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                timePickerShow.timePickerAlertDialog(bsd_wxyy_tv_gcsj);
//
//            }
//        });

        tv_wxyy_zongji = (TextView) view.findViewById(R.id.tv_wxyy_zongji);
        //维修材料
        listcl = (ListView) view.findViewById(R.id.bsd_wxyy_cl_lv);
        listxm = (ListView) view.findViewById(R.id.bsd_wxyy_xm_lv);
        //历史报价
//        bsd_lsbj = (RelativeLayout) view.findViewById(R.id.bsd_lsbj);
//        bsd_lsbj.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ((MainActivity) getActivity()).upLSYY(view);
//            }
//        });
        bsd_wxyy_cl_pop = new BSD_WXYY_CL_POP(getActivity());
        bsd_wxyy_xm_pop = new BSD_WXYY_XM_POP(getActivity());
        /**
         * 从pop传过来的材料实体；
         */
        bsd_wxyy_cl_pop.setChuanlistcl(new BSD_WXYY_CL_POP.chuanlistcl() {
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
                        BSD_WeiXiuYuYue_Cl_entity item = new BSD_WeiXiuYuYue_Cl_entity();
                        item.setReco_no("" + entity.getReco_no1());
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
                    BSD_WeiXiuYuYue_Cl_entity item = new BSD_WeiXiuYuYue_Cl_entity();
                    item.setReco_no("" + entity.getReco_no1());
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


        /**
         * 从pop选中传过来的项目实体类
         */
        bsd_wxyy_xm_pop.setClist(new BSD_WXYY_XM_POP.chuanlist() {
            @Override
            public void onYesClick(BSD_wxyy_xm_pop_entiy entity, double wxxmdj) {
                if (list_XM.size() > 0) {
                    for (int i = 0; i < list_XM.size(); i++) {
                        if (list_XM.get(i).getWxxm_no().equals(entity.getWxxm_no())) {

                            choufutianjia = 1;
                            break;
                        }
                    }
                    if (choufutianjia == 1) {
                        Show.showTime(getActivity(), "添加重复");
                        choufutianjia = 0;
                    } else {
                        BSD_WeiXiyYuYue_XM_entity item = new BSD_WeiXiyYuYue_XM_entity();
                        item.setWxxm_mc(entity.getWxxm_mc());
                        item.setWxxm_gs(entity.getWxxm_gs());
                        item.setWxxm_dj(wxxmdj);
                        item.setWxxm_no(entity.getWxxm_no());
                        item.setWxxm_cb(entity.getWxxm_cb());
                        item.setWxxm_zt("正常");
                        list_XM.add(item);
                        Show.showTime(getActivity(), "成功");
                    }

                } else {
                    BSD_WeiXiyYuYue_XM_entity item = new BSD_WeiXiyYuYue_XM_entity();
                    item.setWxxm_mc(entity.getWxxm_mc());
                    item.setWxxm_gs(entity.getWxxm_gs());
                    item.setWxxm_dj(wxxmdj);
                    item.setWxxm_no(entity.getWxxm_no());
                    item.setWxxm_cb(entity.getWxxm_cb());
                    item.setWxxm_zt("正常");
                    list_XM.add(item);
                    Show.showTime(getActivity(), "11111成功");
                }

                adp_xm.setList(list_XM);
                listxm.setAdapter(adp_xm);
                adp_xm.notifyDataSetChanged();
            }


//
        });
//        bsd_wxyy_xm_pop.setClist(new BSD_WXYY_XM_POP.chuanlist() {
//            @Override
//            public void onAdd(List<BSD_wxyy_xm_pop_entiy> list) {
//                for (int i = 0; i < list.size(); i++) {
//
//                    for (int j=0;j<list_XM.size();j++){
//                        if (list_XM.get(j).getWxxm_no().equals(list.get(i).getWxxm_no())){
//                            choufutianjia=1;
//                        }
//                    }
//        if (choufutianjia==1){
//            Show.show(getContext(),"添加重复");
//        }else {
//            BSD_WeiXiyYuYue_XM_entity entity = new BSD_WeiXiyYuYue_XM_entity();
//            entity.setWxxm_mc(list.get(i).getWxxm_mc());
//            entity.setWxxm_gs(list.get(i).getWxxm_gs());
//            entity.setWxxm_dj(list.get(i).getWxxm_dj());
//            entity.setWxxm_no(list.get(i).getWxxm_no());
//            entity.setWxxm_zt("正常");
//            list_XM.add(entity);
//        }
//
//
//
//                }
//                adp_xm = new BSD_WXYY_XM_adp(getActivity(), list_XM);
//                listxm.setAdapter(adp_xm);
//                adp_xm.notifyDataSetChanged();
//
//            }
//        });


        //弹出框布局
//        bsd_wxxm1 = (RelativeLayout) view.findViewById(R.id.bsd_wxxm1);
//        bsd_wxxm = (RelativeLayout) view.findViewById(R.id.bsd_wxyy_rl_wxxm);
        //数据atap
        beijing = (RelativeLayout) getActivity().findViewById(R.id.beijing);
        //维修项目


        //滑动
        scrollview = (ScrollView) view.findViewById(R.id.scrollview);
        //解决滑动问题
        huadong();


//        bsd_wxyy_rl_bycx.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (bsd_wxyy_top_chexing.getText().toString().equals("")||
//                        bsd_wxyy_top_jinchanglicheng.getText().toString().equals("")||
//                        bsd_wxyy_tv_gcsj.getText().toString().equals("")){
//                    Show.showTime(getActivity(),"请输入完整信息");
//                }else {
//                    Conts.bycx_type = 1;//维修预约
//                    Conts.bycx_chexing = chexingid;//车型
//                    Conts.bycx_licheng = bsd_wxyy_top_jinchanglicheng.getText().toString().trim();//里程
//                    Conts.bycx_time = bsd_wxyy_tv_gcsj.getText().toString().trim();//时间
//                    Conts.bycx_pinpai = bsd_wxyy_top_pinpai.getText().toString().trim();
//                    Conts.bycx_chexi = bsd_wxyy_top_chexi.getText().toString().trim();
//                    Conts.bycx_chezu = bsd_wxyy_top_chezu.getText().toString().trim();
//                    Conts.bycx_cxing = bsd_wxyy_top_chexing.getText().toString().trim();
//                    Conts.bycx_VIN = bsd_wxyy_top_vin.getText().toString().trim();
//                    Conts.bycx_CarName = bsd_wxyy_top_chezhusiji.getText().toString().trim();
//                    Conts.bycx_Shouji = bsd_wxyy_top_dianhua.getText().toString().trim();
//                    Conts.bycx_daochangshijian=bsd_wxyy_top_daochangshijian.getText().toString().trim();
//                    ((MainActivity) getActivity()).upBSD_bycx();
//
//
//                }
//
//
//
//            }
//        });


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

    /**
     * 选择材料数据
     */
    public void cldata() {
        AbRequestParams params = new AbRequestParams();
        params.put("yuyue_no", entiy.getYuyue_no());
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxyy_cl, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int a, String s) {

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("status").toString().equals("1")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject item = jsonarray.getJSONObject(i);
                            BSD_WeiXiuYuYue_Cl_entity entity = new BSD_WeiXiuYuYue_Cl_entity();
                            entity.setReco_no(item.getString("reco_no"));
                            entity.setYuyue_no(item.getString("yuyue_no"));
                            entity.setPeij_no(item.getString("peij_no"));
                            //名字
                            entity.setPeij_mc(item.getString("peij_mc"));
                            //数量
                            entity.setPeij_sl(item.getDouble("peij_sl"));
                            //单价
                            entity.setPeij_dj(item.getDouble("peij_dj"));
                            //图号
                            entity.setPeij_th(item.getString("peij_th"));
                            //状态
                            entity.setPeij_zt(item.getString("peij_zt"));
                            entity.setPeij_dw(item.getString("peij_dw"));
                            list_CL.add(entity);
                        }
//                                BSD_WXYY_XM_adp(getActivity(), list_XM);
                        adp_cl.setList(list_CL);
                        listcl.setAdapter(adp_cl);
                        adp_cl.notifyDataSetChanged();


                    }
                    hejiCL();

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


//        for (int i = 0; i < 20; i++) {
//            HashMap<String, String> map = new HashMap<>();
//            map.put("name", "洗车" + i + "个");
//            map.put("shuliang", "" + i);
//            map.put("danjia", "" + i + 100);
//            map.put("tuhao", "50");
//            map.put("pinpai", "150");
//            map.put("caozuo", "操作");
//            data1.add(map);
//        }

    }

    public void popup() {


//        //维修项目
//        bsd_wxxm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (bsd_wxyy_top_chexing.getText().toString().equals("")) {
//                    Show.showTime(getActivity(), "请选择车型");
//                } else {
//                    pinpai = bsd_wxyy_top_pinpai.getText().toString();
//                    chexi = bsd_wxyy_top_chexi.getText().toString();
//                    chezu = bsd_wxyy_top_chezu.getText().toString();
//                    chexing = bsd_wxyy_top_chexing.getText().toString();
//                    che_cx = pinpai + "|" + chexi + "|" + chezu + "|" + chexing;
//                    Conts.chexing = che_cx;
//
//
//                    bsd_wxxm.setEnabled(false);
//                    bsd_wxyy_xm_pop.showPopupWindow(beijing, 0);
//                    bsd_wxyy_xm_pop.setOnGuanbiListener(new BSD_WXYY_XM_POP.OnGuanbiListener() {
//                        @Override
//                        public void guanbi() {
//                            bsd_wxxm.setEnabled(true);
//                        }
//                    });
//                }
//            }
//        });

        //选择材料
//        bsd_wxxm1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                bsd_wxxm1.setEnabled(false);
//                bsd_wxyy_cl_pop.showPopupWindow(beijing, 0);
//                bsd_wxyy_cl_pop.setOnGuanbiListener(new BSD_WXYY_CL_POP.OnGuanbiListener() {
//                    @Override
//                    public void guanbi() {
//                        bsd_wxxm1.setEnabled(true);
//                    }
//                });
//            }
//        });

    }


    /**
     * 生成单号，有单号就那单号，没有单号就生成单号这个方法就不走了
     * 没有单号的情况下先生成这个。。。。
     */

    String chepai;

    String pinpai;
    String chexi;
    String chezu;
    String chexing;
    String che_cx;

    String VIN;
    String daochangshijian;
    String jinchanglicheng;
    String chezhu;
    String dinahua;

    /**
     * 存档操作没有单号的时候
     */
    public void cundangdata() {

        chepai = bsd_wxyy_cp.getText().toString();

        pinpai = bsd_wxyy_top_pinpai.getText().toString();
        chexi = bsd_wxyy_top_chexi.getText().toString();
        chezu = bsd_wxyy_top_chezu.getText().toString();
        chexing = bsd_wxyy_top_chexing.getText().toString();
        if (bsd_wxyy_top_pinpai.getText().toString().equals("") ||
                bsd_wxyy_top_chexi.getText().toString().equals("") ||
                bsd_wxyy_top_chezu.getText().toString().equals("") ||
                bsd_wxyy_top_chexing.getText().toString().equals("")
                ) {
            che_cx = "";
        } else {
            che_cx = pinpai + "|" + chexi + "|" + chezu + "|" + chexing;
        }
        VIN = bsd_wxyy_top_vin.getText().toString();
        daochangshijian = bsd_wxyy_top_daochangshijian.getText().toString();
        jinchanglicheng = bsd_wxyy_top_jinchanglicheng.getText().toString();
        dinahua = bsd_wxyy_top_dianhua.getText().toString();
        chezhu = bsd_wxyy_top_chezhusiji.getText().toString();

        AbRequestParams params = new AbRequestParams();
        /*
         *公司用户
         */
        params.put("gongsiNo", "01");
        params.put("caozuoyuan_xm", "李祥鹏");


        params.put("che_no", chepai);//车牌
        params.put("che_cx", che_cx);//品牌车型车组
        params.put("che_vin", VIN);//Vin码
        params.put("yuyue_yjjcrq", daochangshijian);//到场时间
        params.put("yuyue_yjjclc", jinchanglicheng);//进厂里程
        params.put("kehu_mc", chezhu);//车主用户
        params.put("kehu_dh", dinahua);//电话

        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxyy_DH, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                if (s.toString().equals("null")) {
                    Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("cjn", "看看这是啥==-=-=-=-=" + s.toString().trim())
                    ;
                    dataCD(s.toString().trim());
                }


                //拿到单号
//

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


    String clcdjson;

    /**
     * 传入单号点击存档时候时使用
     * BSD_wxyy_clcd
     */
    public void datacl(final String DH) {
        clcdjson = "{" + '"' + "data" + '"' + ":" + "[";
        for (int i = 0; i < list_CL.size() - 1; i++) {
            clcdjson = clcdjson + "{" + '"' + "yuyue_no" + '"' + ":" + '"' + DH + '"' + "," + '"' +
                    "peij_no" + '"' + ":" + '"' + list_CL.get(i).getPeij_no() + '"' + "," + '"' +
                    "peij_mc" + '"' + ":" + '"' + list_CL.get(i).getPeij_mc() + '"' + "," + '"' +
                    "peij_sl" + '"' + ":" + '"' + list_CL.get(i).getPeij_sl() + '"' + "," + '"' +
                    "peij_dj" + '"' + ":" + '"' + list_CL.get(i).getPeij_dj() + '"' + "," + '"' +
                    "peij_je" + '"' + ":" + '"' + list_CL.get(i).getPeij_je() + '"' + "," + '"' +
                    "peij_th" + '"' + ":" + '"' + list_CL.get(i).getPeij_th() + '"' + "," + '"' +
                    "peij_dw" + '"' + ":" + '"' + list_CL.get(i).getPeij_dw() + '"' + "," + '"' +
                    "peij_zt" + '"' + ":" + '"' + "正常" + '"' + "}" + ",";
        }
        clcdjson = clcdjson + "{" + '"' + "yuyue_no" + '"' + ":" + '"' + DH + '"' + "," + '"' +
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
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxyy_clcd, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("cjn", "返回个啥CL" + s.toString());
                if (list_XM.size() > 0) {
                    dataCD(DH);
                } else {

                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                    Show.showTime(getActivity(), "请选择项目");

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
                Log.i("cjn", "跟新材料返回失败");
            }
        });

    }


    /**
     * 有单号存档
     * BSD_wxyy_CD
     * 项目的部分
     * 穿进去单号
     */
    public void dataCD(String DH) {

//        json = "{" + '"' + "yuyue_no" + '"' + ":" + '"' + DH + '"' + "," + '"' + "data" + '"' + ":" + "[";
        json = "{" + '"' + "data" + '"' + ":" + "[";
        for (int i = 0; i < list_XM.size() - 1; i++) {
            json = json + "{" + '"' + "yuyue_no" + '"' + ":" + '"' + DH + '"' + "," + '"' + "wxxm_mc" + '"' + ":" + '"' + list_XM.get(i).getWxxm_mc() + '"' + "," + '"' +
                    "wxxm_gs" + '"' + ":" + '"' + list_XM.get(i).getWxxm_gs() + '"' + "," + '"' +
                    "wxxm_dj" + '"' + ":" + '"' + list_XM.get(i).getWxxm_dj() + '"' + "," + '"' +
                    "wxxm_no" + '"' + ":" + '"' + list_XM.get(i).getWxxm_no() + '"' + "," + '"' +
                    "wxxm_je" + '"' + ":" + '"' + list_XM.get(i).getWxxm_je() + '"' + "," + '"' +
                    "wxxm_cb" + '"' + ":" + '"' + list_XM.get(i).getWxxm_cb() + '"' + "," + '"' +
                    "wxxm_zt" + '"' + ":" + '"' + "正常" + '"' + "}" + ",";
        }
        json = json + "{" + '"' + "yuyue_no" + '"' + ":" + '"' + DH + '"' + "," + '"' + "wxxm_mc" + '"' + ":" + '"' + list_XM.get(list_XM.size() - 1).getWxxm_mc() + '"' + "," + '"' +
                "wxxm_gs" + '"' + ":" + '"' + list_XM.get(list_XM.size() - 1).getWxxm_gs() + '"' + "," + '"' +
                "wxxm_dj" + '"' + ":" + '"' + list_XM.get(list_XM.size() - 1).getWxxm_dj() + '"' + "," + '"' +
                "wxxm_no" + '"' + ":" + '"' + list_XM.get(list_XM.size() - 1).getWxxm_no() + '"' + "," + '"' +
                "wxxm_je" + '"' + ":" + '"' + list_XM.get(list_XM.size() - 1).getWxxm_je() + '"' + "," + '"' +
                "wxxm_cb" + '"' + ":" + '"' + list_XM.get(list_XM.size() - 1).getWxxm_cb() + '"' + "," + '"' +
                "wxxm_zt" + '"' + ":" + '"' + "正常" + '"' + "}" + "]" + "}";
//        + "]" + "}"

        Log.i("cjn", "最后一次查看json" + json);
        AbRequestParams params = new AbRequestParams();
        params.put("json", json);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxyy_CD, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int a, String s) {
                Log.i("cjn", "返回个啥XM" + s.toString());
                WeiboDialogUtils.closeDialog(mWeiboDialog);
                queRen = new QueRen(getActivity(), "存档成功");
                queRen.show();
                queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick() {
                        queRen.dismiss();
                        ((MainActivity) getActivity()).upBSD_WXYY_log();
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
                WeiboDialogUtils.closeDialog(mWeiboDialog);
                Log.i("cjn", "跟新项目失败");

                Log.i("cjn", "跟新项目失败");
            }
        });

    }

    /**
     * 如果有有单号，则进行以下操作
     */
    public void updata() {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        danhao = entiy.getYuyue_no();
        chepai = bsd_wxyy_cp.getText().toString();

        pinpai = bsd_wxyy_top_pinpai.getText().toString();
        chexi = bsd_wxyy_top_chexi.getText().toString();
        chezu = bsd_wxyy_top_chezu.getText().toString();
        chexing = bsd_wxyy_top_chexing.getText().toString();


        if (bsd_wxyy_top_pinpai.getText().toString().equals("") ||
                bsd_wxyy_top_chexi.getText().toString().equals("") ||
                bsd_wxyy_top_chezu.getText().toString().equals("") ||
                bsd_wxyy_top_chexing.getText().toString().equals("")
                ) {
            che_cx = "";
        } else {
            che_cx = pinpai + "|" + chexi + "|" + chezu + "|" + chexing;
        }
        VIN = bsd_wxyy_top_vin.getText().toString();
        daochangshijian = bsd_wxyy_top_daochangshijian.getText().toString();
        jinchanglicheng = bsd_wxyy_top_jinchanglicheng.getText().toString();
        dinahua = bsd_wxyy_top_dianhua.getText().toString();
        chezhu = bsd_wxyy_top_chezhusiji.getText().toString();


        AbRequestParams params = new AbRequestParams();
        params.put("yuyue_no", danhao);//预约单号
        params.put("che_cx", che_cx);//品牌，车系，车组，车行，che_cx
        params.put("che_vin", VIN);//VIN码
        params.put("yuyue_yjjcrq", daochangshijian);//到场时间
        params.put("yuyue_yjjclc", jinchanglicheng);//进厂里程
        params.put("kehu_mc", chezhu);//客户司机
        params.put("kehu_dh", dinahua);//手机号
        params.put("yuyue_sfbz", gongshifeili_name);
        params.put("yuyue_sffl", gongshifeili_id);
        Log.i("cjn", "预约单号=" + danhao + "----品牌，车系，车组，车行，che_cx" +
                che_cx + "-----VIN码" + VIN + "-----//到场时间=" + daochangshijian +
                "----进厂里程=" + jinchanglicheng + "----用户" + chezhu + "----手机号" + dinahua);

        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxyy_up, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("cjn", "这个是有单号之后返回的东西" + s.toString());
                if (list_CL.size() > 0) {
                    datacl(s.toString().trim());
                } else {
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                    Show.showTime(getActivity(), "请选择材料");

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
                Log.i("cjn", "存档失败 ");
            }
        });


    }


//    //存档操作
//    @Override
//    public void onClick(View view) {
//        //如果没有单号进行这个查询
////        Log.i("cjn","单号是啥"+danhao.toString());
//        if (danhao == null || danhao.equals("")) {
//            cundangdata();
//        } else {
//            //如果有单号进行如下操作
//            updata();
//            Log.i("cjn", "存档操作");
////            BSD_wxyy_up
//
//
//        }
//
//
//    }

    public void zj() {
        for (int i = 0; i < list_XM.size(); i++) {
            xmzj = xmzj + list_XM.get(i).getWxxm_je();

        }
        Log.i("cjn", "项目总金额" + xmzj);

    }


    TextView bsd_01_text;

    public void bsdtext(View view) {
        bsd_01_text = (TextView) view.findViewById(R.id.bsd_16_text);
        bsd_01_text.setText("公司名称 :   " + MyApplication.shared.getString("GongSiMc", "") +
                "                  公司电话 :   " + MyApplication.shared.getString("danw_dh", ""));
    }
}
