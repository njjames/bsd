package com.example.administrator.boshide2.Modular.Fragment.LiShiBaoJia;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
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
import com.example.administrator.boshide2.Modular.Entity.CustemObject;
import com.example.administrator.boshide2.Modular.Fragment.BaseFragment;
import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.Entity.BSD_KuaiSuBaoJia_CL_entity;
import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.Entity.BSD_KuaiSuBaoJia_XM_entity;
import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.Entity.BSD_KuaiSuBaoJia_ety;
import com.example.administrator.boshide2.Modular.Fragment.LiShiBaoJia.Adapter.BSD_wxxm_xiangqiang_adp;
import com.example.administrator.boshide2.Modular.Fragment.LiShiBaoJia.Adapter.BSD_xzcl_xiangqing_adp;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.BSD_XiuGaiGongShi;
import com.example.administrator.boshide2.Modular.View.SpinerPopWindow;
import com.example.administrator.boshide2.Modular.View.diaog.QueRen;
import com.example.administrator.boshide2.Modular.View.diaog.Queding_Quxiao;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.QuanQuan.WeiboDialogUtils;
import com.example.administrator.boshide2.Tools.Show;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @快速报价碎片页 Created by Administrator on 2017-4-13.
 */

public class BSD_kuaisubaojia_xiangqing_Fragment extends BaseFragment implements View.OnClickListener {
    ScrollView scrollview;
    //弹出popwin
    RelativeLayout beijing;
    // 切换碎片页
    TextView tv_shibie, tv_wxxm_money, tv_wxcl_money;//自動識別,维修项目金额，维修材料金额
    RelativeLayout rea_pinpai;
    //当前选中的列表项位置
    int clickPsition = -1;
    private Dialog mWeiboDialog;

    //车牌号
    String Car;
    TextView bsd_ksbj_cp;
    BSD_KuaiSuBaoJia_ety entity;//快速报价实体类
    String danhao;//单号
    TextView bsd_ksbj_lc, bsd_ksbj_cezhu, bsd_ksbj_dh, bsd_ksbj_vin, bsd_ksbj_pp, bsd_ksbj_cx,
            bsd_ksbj_cz, bsd_ksbj_cxing, tv_zong_money, tv_zong_money3;
    List<BSD_KuaiSuBaoJia_XM_entity> listXM = new ArrayList<BSD_KuaiSuBaoJia_XM_entity>();
    BSD_wxxm_xiangqiang_adp adpxm;
    ListView listxm;//维修项目
    //修改工时弹框
    BSD_XiuGaiGongShi bsd_xiuGaiGongShi;
    int choufutianjia = 0;

    List<BSD_KuaiSuBaoJia_CL_entity> listCL = new ArrayList<BSD_KuaiSuBaoJia_CL_entity>();
    private BSD_xzcl_xiangqing_adp adpcl;//车系适配器
    ListView listcl;//维修材料
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

    //工时费率
    //    RelativeLayout bsd_ksbj_rl_gsfl;
    private TextView bsd_ksbj_tv_gsfl;
    private String gongshifeili_name;
    private String gongshifeili_id;
    private URLS url;
    private Queding_Quxiao queRen_quxiao;
    private LinearLayout bsd_lishibaojiaxiangqing_fh;
    private TextView billNo;
    private TextView title;
    private TextView footerText;

    @Override
    protected int getLayoutId() {
        return R.layout.bsd_ksbj_xiangqing_fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        initdata();
        listCL.clear();
        listXM.clear();
        if (Conts.bycx_fanhuitype == 1) {
            Conts.bycx_fanhuitype = 0;
            bsd_ksbj_pp.setText(Conts.bycx_pinpai);
            bsd_ksbj_cx.setText(Conts.bycx_chexi);
            bsd_ksbj_cz.setText(Conts.bycx_chezu);
            bsd_ksbj_cxing.setText(Conts.bycx_cxing);
            bsd_ksbj_lc.setText(Conts.bycx_licheng);
            //            bsd_ksbj_tv_gcsj.setText(Conts.bycx_time);
            bsd_ksbj_vin.setText(Conts.bycx_VIN);
            bsd_ksbj_cezhu.setText(Conts.bycx_CarName);
            bsd_ksbj_dh.setText(Conts.bycx_Shouji);
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
                    if (jsonObject.get("status").toString().equals("1")) {
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
                Log.i("cjn", "快速报价的项目");
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    Log.i("cjn", "查看快速报价进来的数据" + jsonObject.toString());
                    if (jsonObject.get("status").toString().equals("1")) {
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
                        Show.showTime(getActivity(), jsonObject.get("message").toString());
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
                Log.i("cjn", "快速报价失败");
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
            }
            bsd_ksbj_vin.setText(entity.getChe_vin());

            bsd_ksbj_lc.setText("" + entity.getList_sffl());
            bsd_ksbj_cezhu.setText(entity.getKehu_mc());
            bsd_ksbj_dh.setText(entity.getKehu_dh());
            bsd_ksbj_tv_gsfl.setText(entity.getList_sfbz());
            //维修项目查询
            xmdata();
            //维修单号查询a
            cldata();

        } else if (Conts.zt == 2) {

        }

    }

    @Override
    public void initView() {
        beijing = (RelativeLayout) getActivity().findViewById(R.id.beijing);
        entity = new BSD_KuaiSuBaoJia_ety();
        entity = ((MainActivity) getActivity()).getKsbjenity();
        bsd_lishibaojiaxiangqing_fh = (LinearLayout) view.findViewById(R.id.bsd_lsbj_fanhui);
        bsd_lishibaojiaxiangqing_fh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).upqhf(view);
            }
        });

        bsd_ksbj_tv_gsfl = (TextView) view.findViewById(R.id.bsd_ksbj_tv_gsfl);
        tv_zong_money = (TextView) view.findViewById(R.id.tv_total_je);

        //维修项目
        listxm = (ListView) view.findViewById(R.id.bsd_lv);
        //维修材料
        listcl = (ListView) view.findViewById(R.id.bsd_lv1);

        adpxm = new BSD_wxxm_xiangqiang_adp(getActivity());
        listxm.setAdapter(adpxm);
        adpcl = new BSD_xzcl_xiangqing_adp(getActivity(), handler);
        adpcl.setShuliangzongjia(new BSD_xzcl_xiangqing_adp.shuliangzongjia() {
            @Override
            public void onYesClick(int shuliang, double zongjia) {
                sl = shuliang;
                zj = zongjia;
                wxclPrice();
            }
        });
        listcl.setAdapter(adpcl);

        bsd_ksbj_cp = (TextView) view.findViewById(R.id.bsd_ksbj_cp);
        bsd_ksbj_lc = (TextView) view.findViewById(R.id.bsd_ksbj_lc);
        bsd_ksbj_cezhu = (TextView) view.findViewById(R.id.bsd_ksbj_cezhu);
        bsd_ksbj_dh = (TextView) view.findViewById(R.id.bsd_ksbj_dh);
        bsd_ksbj_vin = (TextView) view.findViewById(R.id.bsd_ksbj_vin);
        bsd_ksbj_pp = (TextView) view.findViewById(R.id.bsd_ksbj_pp);
        bsd_ksbj_cx = (TextView) view.findViewById(R.id.bsd_ksbj_cx);
        bsd_ksbj_cz = (TextView) view.findViewById(R.id.bsd_ksbj_cz);
        bsd_ksbj_cxing = (TextView) view.findViewById(R.id.bsd_ksbj_cxing);
        tv_wxxm_money = (TextView) view.findViewById(R.id.tv_wxxm_money);
        tv_wxcl_money = (TextView) view.findViewById(R.id.tv_wxcl_money);
        title = (TextView) view.findViewById(R.id.tv_title);
        footerText = (TextView) view.findViewById(R.id.tv_footertext);
        billNo = (TextView) view.findViewById(R.id.tv_billNo);

    }

    @Override
    public void initData() {
        url = new URLS();
        title.setText("历史报价");
        footerText.setText("公司名称 :   " + MyApplication.shared.getString("GongSiMc", "") +
                "                  公司电话 :   " + MyApplication.shared.getString("danw_dh", ""));
        billNo.setText(entity.getList_no());
    }

    /**
     * 计算维修项目的总价格
     */
    double b = 0;

    public void jisuanzongjia() {
        double bb = 0;
        for (int i = 0; i < listXM.size(); i++) {
            bb = bb + (listXM.get(i).getWxxm_dj() * listXM.get(i).getWxxm_gs());
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
        c = a + b;
        tv_zong_money.setText(a + b + "");
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
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_ksbj_jbxxtj, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                if (s.toString().equals("null")) {
                    Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("cjn", "看看这是啥==-=-=-=-=" + s.toString().trim());
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
        params.put("list_no", danhao);//预约单号
        params.put("che_cx", che_cx);//品牌，车系，车组，车行，che_cx
        params.put("che_vin", VIN);//VIN码
        params.put("List_lc", jinchanglicheng);//进厂里程
        params.put("kehu_mc", chezhu);//客户司机
        params.put("kehu_dh", dinahua);//手机号
        params.put("List_sfbz", gongshifeili_name);
        params.put("List_sffl", gongshifeili_id);
        Log.i("cjn", "查看这些参数"
                + "--list_no--" + danhao
                + "--che_cx--" + che_cx
                + "--che_vin--" + VIN
                + "--List_lc--" + jinchanglicheng
                + "--kehu_mc--" + chezhu
                + "--kehu_dh--" + dinahua
                + "--List_sfbz--" + gongshifeili_name
                + "--List_sffl--" + gongshifeili_id);


        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_ksbj_bcjbxx, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("cjn", "存档成功");
                Log.i("cjn", "快速报价存档操作完成返回什么？" + s.toString().trim());

                if (listXM.size() > 0) {
                    dataCD(s.toString().trim());
                } else {
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                    Show.showTime(getActivity(), "请添加项目");
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
                WeiboDialogUtils.closeDialog(mWeiboDialog);

            }
        });


    }


    /**
     * 快速报价添加材料
     */
    String json;

    public void dataCD(final String DH) {

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

        Log.i("cjn", "最后一次查看json" + json);
        AbRequestParams params = new AbRequestParams();
        params.put("json", json);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_ksbj_tjxm, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int a, String s) {
                Log.i("cjn", "返回个啥XM" + s.toString());
                if (listCL.size() > 0) {
                    datacl(DH);
                } else {
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                    Show.showTime(getActivity(), "请添加项目");
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
                Log.i("CJN", "CD存档失败");
            }
        });

    }


    /**
     * 添加材料
     */
    String clcdjson;

    public void datacl(final String DH) {
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
        params.put("gongsiNo", "01");
        params.put("caozuoyuan_xm", "李祥鹏");
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_ksbj_jc, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("cjn", "查看返回什么数据" + s.toString());
                WeiboDialogUtils.closeDialog(mWeiboDialog);
                queRen_quxiao = new Queding_Quxiao(getActivity(), "是否跳转详情");

                queRen_quxiao.show();
                queRen_quxiao.setOnResultClickListener(new Queding_Quxiao.OnResultClickListener() {
                    @Override
                    public void onConfirm() {
                        Conts.cp = Car;
                        ((MainActivity) getActivity()).upwxywdd();
                        queRen_quxiao.dismiss();
                    }

                    @Override
                    public void onCancel() {
                        ((MainActivity) getActivity()).upBSD_KSBJ_Log();
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
                Log.i("cjn", "进厂失败");
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });


    }

    @Override
    public void onClick(View view) {

    }

}
