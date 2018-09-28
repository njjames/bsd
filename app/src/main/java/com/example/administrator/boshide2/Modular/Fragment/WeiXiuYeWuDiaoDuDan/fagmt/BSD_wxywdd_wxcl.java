package com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.fagmt;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.example.administrator.boshide2.Conts;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.dialogFragment.BSD_MeiRongKuaiXiu_KuCun_Fragment;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_CL_Entity;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.fagmt.fagmt_adp.BSD_wxywwd_wxcl_adp;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.BSD_XiuGaiGongShi;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.Pop_Entity.BSD_wxyy_cl_pop_entity;
import com.example.administrator.boshide2.Modular.View.diaog.TooPromptdiaog;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.QuanQuan.WeiboDialogUtils;
import com.example.administrator.boshide2.Tools.Show;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 博士德车辆信息维修历史
 * Created by Administrator on 2017-4-24.
 */

public class BSD_wxywdd_wxcl extends Fragment {
    ListView bsd_lsbj_lv;
    BSD_wxywwd_wxcl_adp adapter;
    List<BSD_WeiXiuJieDan_CL_Entity> list_CL = new ArrayList<>();
    URLS url;
    BSD_ZCDUXQ_CL_POP bsd_zcduxq_cl_pop;
    int choufutianjia = 0;
    private Dialog mWeiboDialog;
    RelativeLayout bsd_wxxm1;
    RelativeLayout beijing;
    TooPromptdiaog promptdiaog;
    BSD_XiuGaiGongShi bsd_xiuGaiGongShi;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bsd_wxywdd_wxcl, null);
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        url = new URLS();
        init(view);
        cldata();

        return view;
    }

    public void init(View view) {
        beijing = (RelativeLayout) getActivity().findViewById(R.id.beijing);
        bsd_zcduxq_cl_pop = new BSD_ZCDUXQ_CL_POP(getActivity());
        bsd_wxxm1 = (RelativeLayout) view.findViewById(R.id.bsd_wxxm1);
        bsd_wxxm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                bsd_wxxm1.setEnabled(false);
                bsd_zcduxq_cl_pop.showPopupWindow(beijing, 0);
                bsd_zcduxq_cl_pop.gb(new BSD_ZCDUXQ_CL_POP.Guanbi() {
                    @Override
                    public void guanbi() {
                        bsd_wxxm1.setEnabled(true);
                    }

                    @Override
                    public void onGuanBi(List<BSD_wxyy_cl_pop_entity> tempList) {

                    }
                });


            }
        });

        bsd_zcduxq_cl_pop.setChuanlistcl(new BSD_ZCDUXQ_CL_POP.chuanlistcl() {
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
                        CLinData(Conts.work_no);
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
                    CLinData(Conts.work_no);
                    Show.showTime(getActivity(), "成功");
                }

                adapter.setList(list_CL);
                bsd_lsbj_lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();


            }
        });

        bsd_lsbj_lv = (ListView) view.findViewById(R.id.bsd_lsbj_lv);
        adapter = new BSD_wxywwd_wxcl_adp(getActivity());
        //查看库存
        adapter.setKuCun(new  BSD_wxywwd_wxcl_adp.KuCun(){
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

//        删除
        adapter.setDeletCL(new BSD_wxywwd_wxcl_adp.DeletCL() {
            @Override
            public void onYesClick(final int i) {
                promptdiaog = new TooPromptdiaog(getContext(), "是否删除");
                promptdiaog.setToopromtOnClickListener(new TooPromptdiaog.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick() {
                        deletcl(i);

                        adapter.notifyDataSetChanged();
                    }
                });

                promptdiaog.show();


            }
        });
        //修改数量
        adapter.setuPsl(new BSD_wxywwd_wxcl_adp.UPsl() {
            @Override
            public void onYesClick(final int i, double sl, final double dj) {
                bsd_xiuGaiGongShi = new BSD_XiuGaiGongShi(getActivity(), "修改数量",0,sl,"", "修改数量");
                bsd_xiuGaiGongShi.show();
                bsd_xiuGaiGongShi.setToopromtOnClickListener(new BSD_XiuGaiGongShi.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick(double gongshi) {
                        upxl(i,gongshi,dj);

                        bsd_xiuGaiGongShi.dismiss();
                    }
                });

            }
        });
        //修改单价
        adapter.setuPdj(new BSD_wxywwd_wxcl_adp.UPdj() {
            @Override
            public void onYesClick(final int i, final double sl, final double dj) {
                bsd_xiuGaiGongShi = new BSD_XiuGaiGongShi(getActivity(), "修改单价",0,dj,"", "修改单价");
                bsd_xiuGaiGongShi.show();
                bsd_xiuGaiGongShi.setToopromtOnClickListener(new BSD_XiuGaiGongShi.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick(double gongshi) {
                        upxl(i,sl,gongshi);

                        bsd_xiuGaiGongShi.dismiss();
                    }
                });
            }
        });
        bsd_lsbj_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


            }
        });
    }

    public void upxl(int i, double sl, double dj) {
        AbRequestParams params = new AbRequestParams();
        params.put("id", i);
        params.put("sl", sl + "");
        params.put("jg", dj + "");
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_upclxin, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("cjn", "成功" + s);
                cldata();
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
     * 删除莋
     *
     * @param i
     */
    public void deletcl(int i) {

        AbRequestParams params = new AbRequestParams();
        params.put("id", i);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_deletCL, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int a, String s) {
                Log.i("cjn", "成功" + s);
                promptdiaog.dismiss();
                Log.i("cjn", "成功操作==" + s);
                cldata();
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
                Log.i("cjn", "失败" + s);
            }
        });

    }

    /**
     * 维修材料列表
     */

    public void cldata() {
        list_CL.clear();
//        BSD_wxjd_cllb
        AbRequestParams params = new AbRequestParams();
        Log.i("cjn", "看看单号" + Conts.work_no);
        params.put("work_no", Conts.work_no);
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
                        adapter.setList(list_CL);
                        bsd_lsbj_lv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
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
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });


    }

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
                cldata();
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
                Log.i("cjn", "请求失败" + s.toString());
            }
        });
    }
}
