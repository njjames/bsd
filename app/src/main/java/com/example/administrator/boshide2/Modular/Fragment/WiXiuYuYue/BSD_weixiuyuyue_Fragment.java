package com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue;

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
import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.PopWinow.BSD_KSBJ_CL_POP;
import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.PopWinow.BSD_KSBJ_PinPai_delo;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.dialogFragment.BSD_LiShiWeiXiuJianYi_DialogFragment;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.dialogFragment.BSD_LishiWeiXiu_DialogFragment;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.dialogFragment.BSD_MeiRongKuaiXiu_KuCun_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.dialogFragment.BSD_MeiRongKuaiXiu_cheliangxinxi_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.Adapter.BSD_WXYY_CL_adp;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.Adapter.BSD_WXYY_XM_adp;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.Entity.BSD_WeiXiuYuYue_Cl_entity;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.Entity.BSD_WeiXiuYueYue_entiy;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.Entity.BSD_WeiXiyYuYue_XM_entity;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.BSD_WXYY_XM_POP;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.BSD_XiuGaiGongShi;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.Pop_Entity.BSD_wxyy_cl_pop_entity;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.Pop_Entity.BSD_wxyy_xm_pop_entiy;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.Sql.BSD_WeiXiyYueYue_XM_entity_Dao;
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
 * @维修预约碎片页 Created by Administrator on 2017-4-13.
 */

public class BSD_weixiuyuyue_Fragment extends Fragment implements View.OnClickListener {
    ListView listxm;//维修项目
    ListView listcl;//维修材料
    ScrollView scrollview;
    RelativeLayout beijing;

    //弹出popwin
    TextView tv_add_wxcl;
    TextView tv_add_wxxm;

    //维修项目
    BSD_WeiXiyYueYue_XM_entity_Dao XM_Dao;
    BSD_WXYY_XM_adp adp_xm;
    List<HashMap<String, String>> data = new ArrayList<>();
    //选择材料
    BSD_WXYY_CL_adp adp_cl;
    List<HashMap<String, String>> data1 = new ArrayList<>();

    //项目维修
    BSD_WXYY_XM_POP bsd_wxyy_xm_pop;
    //材料选择
    BSD_KSBJ_CL_POP bsd_wxyy_cl_pop;
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
    EditText bsd_wxyy_top_vin;
    //到场时间
    TextView bsd_wxyy_top_daochangshijian;
    //进厂里程
    EditText bsd_wxyy_top_jinchanglicheng;
    //车主司机
    EditText bsd_wxyy_top_chezhusiji;
    //电话
    EditText bsd_wxyy_top_dianhua;


    List<BSD_WeiXiyYuYue_XM_entity> list_XM = new ArrayList<>();
    List<BSD_WeiXiuYuYue_Cl_entity> list_CL = new ArrayList<>();
    //存档
    TextView bsd_wxyy_cundang;

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
    BSD_KSBJ_PinPai_delo bsd_ksbj_pinPai_delo;
    LinearLayout bsd_wxyy_pp, bsd_wxyy_rl_chexi, bsd_wxyy_rl_chezu, bsd_wxyy_cl_chexing;
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
    LinearLayout bsd_wxyy_rl_gsfl;
    TextView bsd_wxyy_tv_gsfl;
    List<Map<String, String>> listgslv = new ArrayList<Map<String, String>>();

    private List<CustemObject> nameList3 = new ArrayList<CustemObject>();
    private AbstractSpinerAdapter mAdapter3;
    private SpinerPopWindow mSpinerPopWindow3;
    String gongshifeili_name;
    String gongshifeili_id;
    LinearLayout bsd_wxyy_rl_gcsj;
    TextView bsd_wxyy_rl_bycx;
    TextView bsd_wxyy_tv_gcsj;

    TimePickerShow timePickerShow;

    LinearLayout bsd_wxyy_rl_daochangshijian;
    TextView bsd_ksbj_jc;//进厂

    URLS url;
    TimeDialog timeShow;
    public int cd_or_jc;
    Queding_Quxiao queRen_quxiao;

    //根据VIN返回车型信息
    List<Map<String, String>> listvincx = new ArrayList<>();
    String cxnm;      //车型内码
    TextView rl_duqu;

    //车辆信息、历史维修、历史维修建议
    private TextView bsd_wxyy_clxx, bsd_wxyy_lswxjy, bsd_wxyy_lswx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bsd_wxyy_fragment, null);
        url = new URLS();
        entiy = new BSD_WeiXiuYueYue_entiy();
        entiy = ((MainActivity) getActivity()).getEntiy();
        timePickerShow = new TimePickerShow(getActivity());
        timeShow = new TimeDialog(getActivity());
        gsfldata();
        bsdtext(view);
        inits(view);
        init(view);
        popup();
        return view;


    }

    @Override
    public void onStart() {
        super.onStart();
        if (Conts.QX_baoyangxinxii == 1) {
            bsd_wxyy_rl_bycx.setVisibility(View.VISIBLE);
        } else {
            bsd_wxyy_rl_bycx.setVisibility(View.INVISIBLE);
        }

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
            adp_xm.setList(list_XM);
            listxm.setAdapter(adp_xm);
            adp_xm.notifyDataSetChanged();
            adp_cl.setList(list_CL);
            listcl.setAdapter(adp_cl);
            adp_cl.notifyDataSetChanged();
            initdata2();
        } else {
            initdata();
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
        list_XM.clear();
        AbRequestParams params = new AbRequestParams();
        Log.i("cjn", "看看单号" + entiy.getYuyue_no());
        params.put("yuyue_no", entiy.getYuyue_no());

        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxyy_xm, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int a, String s) {
                Log.i("cjn", "看看单号ssss" + s.toString());
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
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
    public void initdata2() {
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


            String che = Conts.bycx_cheliangmingcheng;
            Conts.chexing = Conts.bycx_cheliangmingcheng;
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

            bsd_wxyy_tv_gsfl.setText(entiy.getYuyue_sfbz());
            bsd_wxyy_tv_dh.setText("单号:" + entiy.getYuyue_no());
            bsd_ksbj_tv_sj.setText("时间:" + entiy.getYuyue_jlrq());

            gongshifeili_name = entiy.getYuyue_sfbz();
            Conts.feilv_name = gongshifeili_name;
            gongshifeili_id = entiy.getYuyue_gdfl();

            hejiXM();
            hejiCL();
        }

    }

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
            Conts.bycx_cheliangmingcheng = entiy.getChe_cx();
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
            if (entiy.getChe_vin().toString().trim().length() < 2) {
                bsd_wxyy_top_vin.setText(Conts.VIN);
            } else {
                bsd_wxyy_top_vin.setText(entiy.getChe_vin());
            }

            bsd_wxyy_top_daochangshijian.setText(entiy.getYuyue_yjjcrq());
            bsd_wxyy_top_jinchanglicheng.setText("" + entiy.getYuyue_yjjclc());
            bsd_wxyy_top_chezhusiji.setText(entiy.getKehu_mc());
            bsd_wxyy_top_dianhua.setText(entiy.getKehu_dh());
            bsd_wxyy_tv_gsfl.setText(entiy.getYuyue_sfbz());

            bsd_wxyy_tv_dh.setText("单号:" + entiy.getYuyue_no());
            bsd_ksbj_tv_sj.setText("时间:" + entiy.getYuyue_jlrq());

            gongshifeili_name = entiy.getYuyue_sfbz();
            Conts.feilv_name = gongshifeili_name;
            gongshifeili_id = entiy.getYuyue_gdfl();
            bsd_wxyy_tv_gcsj.setText(entiy.getGcsj());
            //维修项目查询
            dataxm();
            //维修单号查询
            cldata();

        } else if (Conts.zt == 2) {


        }


    }


    /*
 *根据vin码获取车辆名称、代码、内部名称；
 */
    public void duVin() {
        listvincx.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("vinCode", bsd_wxyy_top_vin.getText().toString());
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


                        bsd_wxyy_top_pinpai.setText(s1[0]);
                        bsd_wxyy_top_chexi.setText(s1[1]);
                        bsd_wxyy_top_chezu.setText(s1[2]);
                        bsd_wxyy_top_chexing.setText(s1[3]);


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
     * 进厂成功
     */
    public void yuyuejinchang() {
        AbRequestParams params = new AbRequestParams();
        params.put("no", entiy.getYuyue_no());
        params.put("gongsiNo", MyApplication.shared.getString("GongSiNo", ""));
        params.put("caozuoyuan_xm", MyApplication.shared.getString("name", ""));
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSDyuYue_jinChang, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("cjn", "进厂成功" + s);

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
                                WeiboDialogUtils.closeDialog(mWeiboDialog);
                                Conts.cp = Car;
                                ((MainActivity) getActivity()).upwxywdd();
                                queRen_quxiao.dismiss();
                            }

                            @Override
                            public void onCancel() {
                                WeiboDialogUtils.closeDialog(mWeiboDialog);
                                // ((MainActivity) getActivity()).upBSD_WXyy_log();
                                ((MainActivity) getActivity()).upBSD_WXyy_log();
                                queRen_quxiao.dismiss();
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
            }
        });
    }


    public void inits(View view) {
        bsd_ksbj_jc = (TextView) view.findViewById(R.id.bsd_ksbj_jc);
        bsd_ksbj_jc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cd_or_jc = 1;
                updata();

            }
        });

        bsd_wxyy_rl_gsfl = (LinearLayout) view.findViewById(R.id.bsd_wxyy_rl_gsfl);
        bsd_wxyy_tv_gsfl = (TextView) view.findViewById(R.id.bsd_wxyy_tv_gsfl);
        bsd_wxyy_rl_gsfl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGongSi3();
            }
        });


        bsd_ksbj_tv_sj = (TextView) view.findViewById(R.id.bsd_ksbj_tv_sj);
        bsd_wxyy_tv_dh = (TextView) view.findViewById(R.id.bsd_wxyy_tv_dh);
        bsd_wxyy_cl_zj = (TextView) view.findViewById(R.id.bsd_wxyy_cl_zj);
        bsd_wxyy_xm_zj = (TextView) view.findViewById(R.id.bsd_wxyy_xm_zj);
        bsd_wxyy_cl_zj = (TextView) view.findViewById(R.id.bsd_wxyy_cl_zj);
        bsd_wxyy_xm_zj = (TextView) view.findViewById(R.id.bsd_wxyy_xm_zj);

        listcl = (ListView) view.findViewById(R.id.bsd_wxyy_cl_lv);
        adp_cl = new BSD_WXYY_CL_adp(getActivity(), list_CL);
        adp_cl.setKuCun(new BSD_WXYY_CL_adp.KuCun() {
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
        adp_cl.setShuliangzongjia(new BSD_WXYY_CL_adp.shuliangzongjia() {
            @Override
            public void onYesClick(int shuliang, double zongjia) {
                hejiCL();
            }
        });
        adp_cl.setUpdanjia(new BSD_WXYY_CL_adp.Updanjia() {
            @Override
            public void onYesClick(final int i, String name, double danjia) {
                bsd_xiuGaiGongShi = new BSD_XiuGaiGongShi(getActivity(), list_CL.get(i).getPeij_mc(), 0, list_CL.get(i).getPeij_dj(), "", "修改单价");
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
        adp_cl.setDeleteCL(new BSD_WXYY_CL_adp.DeleteCL() {
            @Override
            public void onYesClick() {
                hejiCL();
            }
        });
        listcl.setAdapter(adp_cl);
        listxm = (ListView) view.findViewById(R.id.bsd_wxyy_xm_lv);
        adp_xm = new BSD_WXYY_XM_adp(getActivity(), list_XM);
        //修改工时
        adp_xm.setUpgongshi(new BSD_WXYY_XM_adp.upgongshi() {
            @Override
            public void onYesClick(final int i, String name, double gongshi) {
                bsd_xiuGaiGongShi = new BSD_XiuGaiGongShi(getActivity(), name, 0, gongshi, "", "修改工时");
                bsd_xiuGaiGongShi.show();
                bsd_xiuGaiGongShi.setToopromtOnClickListener(new BSD_XiuGaiGongShi.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick(double gongshi) {
                        list_XM.get(i).setWxxm_gs(gongshi);
                        hejiXM();
                        bsd_xiuGaiGongShi.dismiss();
                        adp_xm.notifyDataSetChanged();
                    }
                });
            }
        });
        //修改工时单价
        adp_xm.setUpgongshidanjia(new BSD_WXYY_XM_adp.upgongshidanjia() {
            @Override
            public void onYesClick(final int i, String name, double gongshi) {
                bsd_xiuGaiGongShi = new BSD_XiuGaiGongShi(getActivity(), name, 0, gongshi, "", "修改金额");
                bsd_xiuGaiGongShi.show();
                bsd_xiuGaiGongShi.setToopromtOnClickListener(new BSD_XiuGaiGongShi.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick(double gongshi) {
                        list_XM.get(i).setWxxm_je(gongshi);
                        zj();
                        hejiXM();
                        bsd_xiuGaiGongShi.dismiss();
                        adp_xm.notifyDataSetChanged();
                    }
                });

            }
        });
        //维修预约--项目-删除
        adp_xm.setShanchuXM(new BSD_WXYY_XM_adp.ShanchuXM() {
            @Override
            public void onYesClick() {
                hejiXM();
            }
        });
        listxm.setAdapter(adp_xm);
        //存档
        bsd_wxyy_cundang = (TextView) view.findViewById(R.id.bsd_wxyy_cundang);
        bsd_wxyy_cundang.setOnClickListener(this);
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
        bsd_wxyy_top_vin = (EditText) view.findViewById(R.id.bsd_wxyy_top_vin);

        //读取vin码
        rl_duqu = (TextView) view.findViewById(R.id.tv_readvin);
        rl_duqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vin = bsd_wxyy_top_vin.getText().toString();
                if ("".equals(vin)) {
                    Toast.makeText(getActivity(), "请输入vin码", Toast.LENGTH_LONG).show();
                } else {
                    duVin();
                }
            }
        });

        //到场时间
        bsd_wxyy_top_daochangshijian = (TextView) view.findViewById(R.id.bsd_wxyy_top_daochangshijian);
        //进厂里程
        bsd_wxyy_top_jinchanglicheng = (EditText) view.findViewById(R.id.bsd_wxyy_top_jinchanglicheng);
        //车主司机
        bsd_wxyy_top_chezhusiji = (EditText) view.findViewById(R.id.bsd_wxyy_top_chezhusiji);
        //电话
        bsd_wxyy_top_dianhua = (EditText) view.findViewById(R.id.bsd_wxyy_top_dianhua);

        bsd_wxyy_rl_chexi = (LinearLayout) view.findViewById(R.id.bsd_wxyy_rl_chexi);
        bsd_wxyy_rl_chezu = (LinearLayout) view.findViewById(R.id.bsd_wxyy_rl_chezu);
        bsd_wxyy_cl_chexing = (LinearLayout) view.findViewById(R.id.bsd_wxyy_cl_chexing);

        //品牌弹框
        bsd_ksbj_pinPai_delo = new BSD_KSBJ_PinPai_delo(getActivity());
        bsd_wxyy_pp = (LinearLayout) view.findViewById(R.id.bsd_wxyy_pp);
        bsd_wxyy_pp.setOnClickListener(new View.OnClickListener() {
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
                bsd_wxyy_top_pinpai.setText(pinpaiming);
                bsd_wxyy_top_chexi.setText("");
                bsd_wxyy_top_chezu.setText("");
                bsd_wxyy_top_chexing.setText("");
                bsd_ksbj_pinPai_delo.dismiss();
                bsdcx(cxbianhao);
            }
        });

        bsd_wxyy_rl_chexi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bsd_wxyy_top_pinpai.getText().toString().equals("")) {
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
        bsd_wxyy_rl_chezu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bsd_wxyy_top_chexi.getText().toString().equals("")) {
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
        bsd_wxyy_cl_chexing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bsd_wxyy_top_chezu.getText().toString().equals("")) {
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
    public void updaGSFLData() {
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
                if (!bsd_wxyy_tv_gsfl.getText().toString().equals(value)) {
                    bsd_wxyy_tv_gsfl.setText(value);
                    gongshifeili_name = listgslv.get(pos).get("feil_mc");
                    gongshifeili_id = listgslv.get(pos).get("feil_fl");
                    Conts.feilv_name = gongshifeili_name;
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
                    updaGSFLData();
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


    private void showGongSi2() {
        mSpinerPopWindow2.setWidth(bsd_wxyy_cl_chexing.getWidth());
        mSpinerPopWindow2.showAsDropDown(bsd_wxyy_cl_chexing);
    }


    //车组=======================================================================

    private void showGongSi1() {
        mSpinerPopWindow1.setWidth(bsd_wxyy_rl_chezu.getWidth());
        mSpinerPopWindow1.showAsDropDown(bsd_wxyy_rl_chezu);
    }

    /**
     * 基本信息车组接口
     */
    public void bsdcz(String chexiid) {
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

    private void showGongSi() {
        mSpinerPopWindow.setWidth(bsd_wxyy_rl_chexi.getWidth());
        mSpinerPopWindow.showAsDropDown(bsd_wxyy_rl_chexi);
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


    //品牌    车系   车组    车行

    //项目合计
    double jg = 0;

    public void hejiXM() {
        double j = 0;
        for (int i = 0; i < list_XM.size(); i++) {
            j = j + (list_XM.get(i).getWxxm_je());
        }
        bsd_wxyy_xm_zj.setText(j + "元");
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
        bsd_wxyy_cl_zj.setText(xl + "元");
        jgxl = xl;
        wxyy_zongjia();
    }

    public void wxyy_zongjia() {
        tv_wxyy_zongji.setText(jg + jgxl + "");
    }

    public void init(View view) {
        bsd_wxyy_rl_daochangshijian = (LinearLayout) view.findViewById(R.id.bsd_wxyy_rl_daochangshijian);
        bsd_wxyy_rl_daochangshijian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeShow.timePickerAlertDialog(bsd_wxyy_top_daochangshijian);
            }
        });

        bsd_wxyy_rl_gcsj = (LinearLayout) view.findViewById(R.id.bsd_wxyy_rl_gcsj);
        bsd_wxyy_rl_bycx = (TextView) view.findViewById(R.id.bsd_wxyy_rl_bycx);
        bsd_wxyy_tv_gcsj = (TextView) view.findViewById(R.id.bsd_wxyy_tv_gcsj);
        bsd_wxyy_rl_gcsj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeShow.timePickerAlertDialog(bsd_wxyy_tv_gcsj);
            }
        });


        //车辆信息、历史维修、历史维修建议
        bsd_wxyy_clxx = (TextView) view.findViewById(R.id.bsd_wxyy_clxx);
        bsd_wxyy_clxx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "车辆信息", Toast.LENGTH_LONG).show();
                //跳转到编辑车辆、客户信息界面
                Conts.danju_type = "wxyy";

                //跳转到编辑车辆、客户信息对话框
                new BSD_MeiRongKuaiXiu_cheliangxinxi_Fragment()
                        .show(getFragmentManager(), "dialog_fragment");
            }
        });
        bsd_wxyy_lswx = (TextView) view.findViewById(R.id.bsd_wxyy_lswx);
        bsd_wxyy_lswx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //                Toast.makeText(getActivity(),"历史维修",Toast.LENGTH_LONG).show();

                Conts.danju_type = "wxyy";
                new BSD_LishiWeiXiu_DialogFragment().
                        show(getFragmentManager(), "mrkx_lswx");
            }
        });
        bsd_wxyy_lswxjy = (TextView) view.findViewById(R.id.bsd_wxyy_lswxjy);
        bsd_wxyy_lswxjy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //                Toast.makeText(getActivity(),"历史维修建议",Toast.LENGTH_LONG).show();
                new BSD_LiShiWeiXiuJianYi_DialogFragment().
                        show(getFragmentManager(), "mrkx_lswxjy");
            }
        });

        tv_wxyy_zongji = (TextView) view.findViewById(R.id.tv_wxyy_zongji);
        //维修材料

        //历史报价
        bsd_wxyy_cl_pop = new BSD_KSBJ_CL_POP(getActivity());
        bsd_wxyy_xm_pop = new BSD_WXYY_XM_POP(getActivity());
        /**
         * 从pop传过来的材料实体；
         */
        bsd_wxyy_cl_pop.setChuanlistcl(new BSD_KSBJ_CL_POP.chuanlistcl() {
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
                        item.setPeij_je(jiaqian * item.getPeij_sl());
                        Log.i("dj", "单价=" + jiaqian + ",金额=" + item.getPeij_je());
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
                    item.setPeij_je(jiaqian * item.getPeij_sl());
                    Log.i("dj", "单价=" + jiaqian + ",金额=" + item.getPeij_je());
                    //单位
                    item.setPeij_th(entity.getPeij_th());
                    //状态
                    item.setPeij_zt("正常");
                    item.setPeij_dw(entity.getPeij_dw());
                    list_CL.add(item);
                    Show.showTime(getActivity(), "成功");
                }

                adp_cl.setList(list_CL);
                listcl.setAdapter(adp_cl);
                hejiCL();
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
                    item.setWxxm_cb(entity.getWxxm_cb());
                    item.setWxxm_zt("正常");
                    list_XM.add(item);
                    Show.showTime(getActivity(), "成功");
                }

                adp_xm.setList(list_XM);
                listxm.setAdapter(adp_xm);
                hejiXM();
                adp_xm.notifyDataSetChanged();
            }


            //
        });

        //弹出框布局
        tv_add_wxxm = (TextView) view.findViewById(R.id.tv_add_wxxm);
        tv_add_wxcl = (TextView) view.findViewById(R.id.tv_add_wxcl);
        //数据atap
        beijing = (RelativeLayout) getActivity().findViewById(R.id.beijing);
        //维修项目


        //滑动
        scrollview = (ScrollView) view.findViewById(R.id.scrollview);
        //解决滑动问题
        huadong();


        bsd_wxyy_rl_bycx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bsd_wxyy_top_chexing.getText().toString().equals("") ||
                        bsd_wxyy_top_jinchanglicheng.getText().toString().equals("") ||
                        bsd_wxyy_tv_gcsj.getText().toString().equals("")) {
                    Show.showTime(getActivity(), "请输入完整信息");
                } else {
                    bsd_cexifansuan();

                }


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

    /**
     * 选择材料数据
     */
    public void cldata() {
        list_CL.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("yuyue_no", entiy.getYuyue_no());
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxyy_cl, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int a, String s) {

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
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

    }

    public void popup() {
        //维修项目
        tv_add_wxxm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pinpai = bsd_wxyy_top_pinpai.getText().toString();
                chexi = bsd_wxyy_top_chexi.getText().toString();
                chezu = bsd_wxyy_top_chezu.getText().toString();
                chexing = bsd_wxyy_top_chexing.getText().toString();
                che_cx = pinpai + "|" + chexi + "|" + chezu + "|" + chexing;
                Conts.chexing = che_cx;
                bsd_wxyy_xm_pop.showPopupWindow(beijing, 0);
                bsd_wxyy_xm_pop.reightdata();
                bsd_wxyy_xm_pop.gb(new BSD_WXYY_XM_POP.Guanbi() {
                    @Override
                    public void guanbi() {
                    }
                });

            }
        });

        //选择材料
        tv_add_wxcl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bsd_wxyy_cl_pop.showPopupWindow(beijing, 0);
                bsd_wxyy_cl_pop.gb(new BSD_KSBJ_CL_POP.Guanbi() {
                    @Override
                    public void guanbi() {
                    }
                });
            }
        });

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


    String clcdjson;

    /**
     * 传入单号点击存档时候时使用
     * BSD_wxyy_clcd
     */
    public void datacl(final String DH) {


        if (list_CL.size() > 0) {
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
        } else {
            clcdjson = "{" + '"' + "data" + '"' + ":" + '[' + ']' + "}";
        }
        Log.i("cjn", "clcdjson========================" + clcdjson);
        AbRequestParams params = new AbRequestParams();
        params.put("json", clcdjson);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxyy_clcd, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("cjn", "返回个啥CL" + s.toString());
                //                if (list_XM.size() > 0) {
                dataCD(DH);
                //                } else {
                //
                //                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                //                    Show.showTime(getActivity(), "请选择项目");
                //
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
        if (list_XM.size() > 0) {
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
        } else {
            json = "{" + '"' + "data" + '"' + ":" + '[' + ']' + "}";
        }
        Log.i("cjn", "最后一次查看json" + json);
        AbRequestParams params = new AbRequestParams();
        params.put("json", json);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxyy_CD, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int a, String s) {
                Log.i("cjn", "返回个啥XM" + s.toString());
                if (cd_or_jc == 0) {
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                    queRen = new QueRen(getActivity(), "预约成功");
                    queRen.show();
                    queRen.setToopromtOnClickListener(new QueRen.ToopromtOnClickListener() {
                        @Override
                        public void onYesClick() {
                            queRen.dismiss();
                            ((MainActivity) getActivity()).upBSD_WXyy_log();
                        }
                    });
                }
                if (cd_or_jc == 1) {
                    yuyuejinchang();
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
        params.put("che_no", entiy.getChe_no());//预约单号
        params.put("yuyue_no", danhao);//预约单号
        params.put("che_cx", che_cx);//品牌，车系，车组，车行，che_cx
        params.put("che_vin", VIN);//VIN码
        params.put("yuyue_yjjcrq", daochangshijian);//到场时间
        params.put("yuyue_yjjclc", jinchanglicheng);//进厂里程
        params.put("kehu_mc", chezhu);//客户司机
        params.put("kehu_dh", dinahua);//手机号
        params.put("yuyue_sfbz", gongshifeili_name);
        params.put("yuyue_sffl", gongshifeili_id);
        params.put("kehu_no", Conts.kehu_no);
        params.put("yuyue_hjje", tv_wxyy_zongji.getText().toString());
        params.put("gcsj", bsd_wxyy_tv_gcsj.getText().toString());

        Log.i("cjn", "查看客户no" + Conts.kehu_no);
        Log.i("cjn", "预约单号=" + danhao + "----品牌，车系，车组，车行，che_cx" +
                che_cx + "-----VIN码" + VIN + "-----//到场时间=" + daochangshijian +
                "----进厂里程=" + jinchanglicheng + "----用户" + chezhu + "----手机号" + dinahua);

        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxyy_up, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("cjn", "这个是有单号之后返回的东西" + s.toString());
                //                if (list_CL.size() > 0) {
                datacl(s.toString().trim());
                //                } else {
                //                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                //                    Show.showTime(getActivity(), "请选择材料");
                //
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
                Log.i("cjn", "存档失败 " + s);
            }
        });


    }


    //存档操作
    @Override
    public void onClick(View view) {
        //如果没有单号进行这个查询
        //        Log.i("cjn","单号是啥"+danhao.toString());
        if (danhao == null || danhao.equals("")) {
        } else {
            cd_or_jc = 0;
            //如果有单号进行如下操作
            updata();
            Log.i("cjn", "存档操作");
            //            BSD_wxyy_up


        }


    }

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

    String getCar;

    public void bsd_cexifansuan() {
        String id;
        AbRequestParams params = new AbRequestParams();
        params.put("quanMing", bsd_wxyy_top_chexing.getText().toString());
        Log.i("cjn", "bsd_ksbj_cxing.getText().toString()" + bsd_wxyy_top_chexing.getText().toString());
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_chexing_fansuan, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("cjn", "查看" + s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    //                if (jsonObject.getString("message").toString().equals("查询成功")){
                    getCar = jsonObject.getString("data");
                    //                }
                    Conts.bycx_type = 1;//维修预约
                    if (getCar.length() > 0) {
                        chexingid = getCar;
                    }
                    Conts.bycx_chexing = chexingid;//车型
                    int a;
                    double b;
                    b = Double.parseDouble(bsd_wxyy_top_jinchanglicheng.getText().toString().trim());
                    a = (int) b;
                    Conts.bycx_licheng = "" + a;//里程
                    //                    Conts.bycx_licheng = bsd_wxyy_top_jinchanglicheng.getText().toString().trim();//里程
                    Conts.bycx_time = bsd_wxyy_tv_gcsj.getText().toString().trim();//时间
                    Conts.bycx_pinpai = bsd_wxyy_top_pinpai.getText().toString().trim();
                    Conts.bycx_chexi = bsd_wxyy_top_chexi.getText().toString().trim();
                    Conts.bycx_chezu = bsd_wxyy_top_chezu.getText().toString().trim();
                    Conts.bycx_cxing = bsd_wxyy_top_chexing.getText().toString().trim();
                    Conts.bycx_VIN = bsd_wxyy_top_vin.getText().toString().trim();
                    Conts.bycx_CarName = bsd_wxyy_top_chezhusiji.getText().toString().trim();
                    Conts.bycx_Shouji = bsd_wxyy_top_dianhua.getText().toString().trim();
                    Conts.bycx_daochangshijian = bsd_wxyy_top_daochangshijian.getText().toString().trim();

                    Conts.bycx_cheliangmingcheng = bsd_wxyy_top_pinpai.getText().toString().trim() + "|" + bsd_wxyy_top_chexi.getText().toString().trim() + "|" + bsd_wxyy_top_chezu.getText().toString().trim() + "|" + bsd_wxyy_top_chexing.getText().toString().trim();
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
