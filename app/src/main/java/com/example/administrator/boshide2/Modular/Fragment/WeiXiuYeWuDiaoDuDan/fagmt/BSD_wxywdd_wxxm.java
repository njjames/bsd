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
import android.widget.Toast;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.example.administrator.boshide2.Conts;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_XM_Entity;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.deleg.BSD_WeiXiuYeWuDiaoDuDian_delg;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.fagmt.fagmt_adp.BSD_wxywwd_wxxm_adp;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.BSD_XiuGaiGongShi;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.Pop_Entity.BSD_wxyy_xm_pop_entiy;
import com.example.administrator.boshide2.Modular.View.diaog.TooPromptdiaog;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.QuanQuan.WeiboDialogUtils;
import com.example.administrator.boshide2.Tools.Show;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 博士德车辆信息维修历史
 * Created by Administrator on 2017-4-24.
 */

public class BSD_wxywdd_wxxm extends Fragment {

    Delet delet;

    public Delet getDelet() {
        return delet;
    }

    public void setDelet(Delet delet) {
        this.delet = delet;
    }

    private int last_item = -1;
    RelativeLayout beijing;
    ListView bsd_lsbj_lv;
    TooPromptdiaog promptdiaog;
    List<HashMap<String, String>> data = new ArrayList<>();
    BSD_wxywwd_wxxm_adp adapter;
    List<BSD_WeiXiuJieDan_XM_Entity> list_XM = new ArrayList<>();
    RelativeLayout bsd_zcdd_rl_ztpg;
    BSD_ZCDUXQ_XM_POP bsd_zcduxq_xm_pop;
    BSD_WeiXiuYeWuDiaoDuDian_delg delg;
    ChaKanPaiGongREN chaKanPaiGongREN;
    RelativeLayout bsd_wxxm;
    URLS url;
    private Dialog mWeiboDialog;
    RelativeLayout oldView;
    int choufutianjia = 0;
    BSD_XiuGaiGongShi bsd_xiuGaiGongShi;


    private  ClearPaiGongRenYuan   clearPaiGongRenYuan;
    public  interface   ClearPaiGongRenYuan{
         void   clearPaiGong();
    }

    public  void  setClearPaiGongRenYuan(ClearPaiGongRenYuan   clearPaiGongRenYuan){
        this.clearPaiGongRenYuan=clearPaiGongRenYuan;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bsd_wxywdd_wxxm, null);
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        url = new URLS();

        dataxm();
        init(view);
        for (int a = 0; a < list_XM.size(); a++) {
            list_XM.get(a).getReco_no();
            Log.i("cjn", "循环查看这个id" + list_XM.get(a).getReco_no());
        }

        return view;
    }

    public void setChaKanPaiGongREN(ChaKanPaiGongREN chaKanPaiGongREN) {
        this.chaKanPaiGongREN = chaKanPaiGongREN;
    }

    String wxxm_nos;

    public void init(View view) {
        beijing = (RelativeLayout) getActivity().findViewById(R.id.beijing);
        //维修项目添加
        bsd_wxxm = (RelativeLayout) view.findViewById(R.id.bsd_wxxm);


        bsd_zcduxq_xm_pop = new BSD_ZCDUXQ_XM_POP(getActivity());
        bsd_zcduxq_xm_pop.setClist(new BSD_ZCDUXQ_XM_POP.chuanlist() {
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
                        BSD_WeiXiuJieDan_XM_Entity item = new BSD_WeiXiuJieDan_XM_Entity();
                        item.setWxxm_mc(entity.getWxxm_mc());
                        item.setWxxm_gs(entity.getWxxm_gs());
                        item.setWxxm_je(wxxmdj);
//                        String a = String.valueOf(wxxmdj / entity.getWxxm_gs());
//                        if (a.equals("NaN")){
//                            item.setWxxm_dj(wxxmdj);
//                        }else {
//                            item.setWxxm_dj(wxxmdj / entity.getWxxm_gs());
//                        }

                        if(entity.getWxxm_gs()==0){
                            Log.i("gsdj", "是0 ");
                            entity.setWxxm_gs(1.0);
                        }
                        item.setWxxm_dj(wxxmdj / entity.getWxxm_gs());
                        item.setWxxm_cb(entity.getWxxm_cb());
                        item.setWxxm_no(entity.getWxxm_no());
                        item.setWxxm_zt("正常");
                        list_XM.add(item);
                        XMinData(Conts.work_no);
                        Show.showTime(getActivity(), "成功");
                    }

                } else {
                    BSD_WeiXiuJieDan_XM_Entity item = new BSD_WeiXiuJieDan_XM_Entity();
                    item.setWxxm_mc(entity.getWxxm_mc());
                    item.setWxxm_gs(entity.getWxxm_gs());
                    item.setWxxm_je(wxxmdj);
//                    String a = String.valueOf(wxxmdj / entity.getWxxm_gs());
//                    if (a.equals("NaN")){
//                        item.setWxxm_dj(wxxmdj);
//                    }else {
//                        item.setWxxm_dj(wxxmdj / entity.getWxxm_gs());
//                    }
                    if(entity.getWxxm_gs()==0){
                        entity.setWxxm_gs(1.0);
                        Log.i("gsdj", "是0");
                    }
                    Log.i("gsdj", "不是0");
                    item.setWxxm_dj(wxxmdj / entity.getWxxm_gs());
                    item.setWxxm_no(entity.getWxxm_no());
                    item.setWxxm_zt("正常");
                    Log.i("gsdj", "不是0 0000");
                    list_XM.add(item);
                    XMinData(Conts.work_no);
                    Show.showTime(getActivity(), "成功");
                }

                adapter.setList(list_XM);
                bsd_lsbj_lv.setAdapter(adapter);
                Log.i("gsdj", "不是0 11111");
                adapter.notifyDataSetChanged();
            }

        });
        bsd_wxxm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                bsd_wxxm.setEnabled(false);
                bsd_zcduxq_xm_pop.showPopupWindow(beijing, 0);
                bsd_zcduxq_xm_pop.getDetialsInfo();
                bsd_zcduxq_xm_pop.gb(new BSD_ZCDUXQ_XM_POP.Guanbi() {
                    @Override
                    public void guanbi() {
                        bsd_wxxm.setEnabled(true);
                    }

                    @Override
                    public void onGuanBi(List<BSD_wxyy_xm_pop_entiy> tempList) {

                    }
                });


            }
        });

        bsd_zcdd_rl_ztpg = (RelativeLayout) view.findViewById(R.id.bsd_zcdd_rl_ztpg);
        bsd_zcdd_rl_ztpg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //整体派工
                if (list_XM.size() > 0) {
                    wxxm_nos = "[";
                    for (int i = 0; i < list_XM.size() - 1; i++) {
                        wxxm_nos = wxxm_nos + list_XM.get(i).getWxxm_no() + ",";
                    }
                    wxxm_nos = wxxm_nos + list_XM.get(list_XM.size() - 1).getWxxm_no() + "]";
                    Conts.zhengti_or_danxiang = 0;
                    delg = new BSD_WeiXiuYeWuDiaoDuDian_delg(getActivity(), wxxm_nos);
                    delg.show();

                } else {
                    Show.showTime(getActivity(), "没有维修项目");
                }

            }
        });


        bsd_lsbj_lv = (ListView) view.findViewById(R.id.bsd_lsbj_lv);
        adapter = new BSD_wxywwd_wxxm_adp(getActivity());
        //单项派工
        adapter.setDanXiangPaiGong(new BSD_wxywwd_wxxm_adp.DanXiangPaiGong() {
            @Override
            public void onYesClick(int i) {
                //单项派工
                Conts.wxxm_no = list_XM.get(i).getWxxm_no();
                Conts.wxxm_gs = list_XM.get(i).getWxxm_gs();
                Conts.wxxm_je = list_XM.get(i).getWxxm_je();
                Conts.zhengti_or_danxiang = 1;
                delg = new BSD_WeiXiuYeWuDiaoDuDian_delg(getActivity(), wxxm_nos);
                delg.show();


            }
        });
        //删除
        adapter.setDelite(new BSD_wxywwd_wxxm_adp.Delite() {
            @Override
            public void onYesClick(final int i, final String workno) {
                promptdiaog = new TooPromptdiaog(getContext(), "是否删除");
                promptdiaog.setToopromtOnClickListener(new TooPromptdiaog.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick() {
                        deletxm(i,workno);
                        adapter.notifyDataSetChanged();
                    }
                });

                promptdiaog.show();


            }
        });
        //修改工时
        adapter.setuPgs(new BSD_wxywwd_wxxm_adp.UPgs() {
            @Override
            public void onYesClick(final int i, double gs, final double dj, final String xmmc) {
                bsd_xiuGaiGongShi = new BSD_XiuGaiGongShi(getActivity(), "修改工时",0,gs,"", "修改工时");
                bsd_xiuGaiGongShi.show();
                bsd_xiuGaiGongShi.setToopromtOnClickListener(new BSD_XiuGaiGongShi.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick(double gongshi) {
                        Conts.wxxm_gs=gongshi;
                        upxm(i,gongshi,dj,xmmc);
                        bsd_xiuGaiGongShi.dismiss();
                    }
                });
            }
        });
        //修改金额
        adapter.setuPdj(new BSD_wxywwd_wxxm_adp.UPdj() {
            @Override
            public void onYesClick(final int i, final double gs, final double dj, final String xmmc) {
                bsd_xiuGaiGongShi = new BSD_XiuGaiGongShi(getActivity(), "修改金额",0,dj, "","修改金额");
                bsd_xiuGaiGongShi.show();
                bsd_xiuGaiGongShi.setToopromtOnClickListener(new BSD_XiuGaiGongShi.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick(double gongshi) {
                        Conts.wxxm_je=gongshi;
                        upxm(i,gs,gongshi,xmmc);
                        bsd_xiuGaiGongShi.dismiss();
                    }
                });
            }
        });
        adapter.setuPmc(new BSD_wxywwd_wxxm_adp.UPmc() {
            @Override
            public void onYesClick(final int i, final double gs, final double dj, final String xmmc) {
                bsd_xiuGaiGongShi = new BSD_XiuGaiGongShi(getActivity(), "修改项目名称",1,dj, xmmc,"修改项目名称");
                bsd_xiuGaiGongShi.show();
                bsd_xiuGaiGongShi.setToopromtXmmc(new BSD_XiuGaiGongShi.ToopromtXmmc() {
                    @Override
                    public void onYesClick(String xmmc) {
                        upxm(i,gs,dj,xmmc);
                        bsd_xiuGaiGongShi.dismiss();
                    }
                });

            }
        });


        bsd_lsbj_lv.setAdapter(adapter);
        bsd_lsbj_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RelativeLayout item = (RelativeLayout) view;
                item.setBackgroundResource(R.drawable.bsd_jiu);//把当前选中的条目加上选中效果
                if (last_item != -1 && last_item != i) {//如果已经单击过条目并且上次保存的item位置和当前位置不同
                    // oldView.setBackgroundColor(Color.WHITE);
                    oldView.setBackgroundResource(R.drawable.article_listview_item_bg);//把上次选中的样式去掉
                }
                oldView = item;//把当前的条目保存下来
                last_item = i;//把当前的位置保存下来
                adapter.setLast_item(last_item);
                if (chaKanPaiGongREN != null) {
                    chaKanPaiGongREN.onYesClick(list_XM.get(i).getWork_no(), list_XM.get(i).getWxxm_no(), list_XM.get(i).getWxxm_gs(), list_XM.get(i).getWxxm_je());
                    Conts.wxxm_je = list_XM.get(i).getWxxm_gs() * list_XM.get(i).getWxxm_dj();
                    Log.i("cjn", "材料是否传过去了" + Conts.wxxm_je);
                    Conts.wxxm_gs = list_XM.get(i).getWxxm_gs();
                }
            }
        });
    }

    public void upxm(int i,double gs, double dj,String  xmmc){
        AbRequestParams params = new AbRequestParams();
        params.put("id", i);
        params.put("jg", dj+"");
        params.put("gs", gs+"");
        params.put("wxxm_mc",xmmc);

       Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_upxm, params, new AbStringHttpResponseListener() {
           @Override
           public void onSuccess(int i, String s) {
               Log.i("cjn","成功"+s);
               dataxm();
           }

           @Override
           public void onStart() {

           }

           @Override
           public void onFinish() {

           }

           @Override
           public void onFailure(int i, String s, Throwable throwable) {
               Log.i("cjn","失败"+s);
           }
       });




    }




    /**
     * 删除操作
     * @param i
     */
    public void deletxm(final int i, final String workno) {
        AbRequestParams params = new AbRequestParams();
        params.put("id", i);
        Log.i("cjn", "集合个主" + list_XM.size() + "查看这个id" + i);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_deletXM, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int aa, String s) {
                promptdiaog.dismiss();
                Log.i("cjn", "成功操作==" + s);
                try {
                    JSONObject  object=new JSONObject(s);
                    if(object.getString("data").equals("删除成功")){
                        dataxm();
                    }else {
                        Toast.makeText(getContext(),object.getString("message"),Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                Log.i("cjn", "失败==" + s);
                adapter.notifyDataSetChanged();
            }
        });


    }

//    public void data() {
//
//        for (int i = 0; i < 20; i++) {
//            HashMap<String, String> map = new HashMap<>();
//            map.put("name", "洗车");
//            map.put("time", "2");
//            map.put("timemany","500");
//            map.put("jinqian", "150");
//            map.put("caozuo", "添加");
//            data.add(map);
//        }
//    }

    /**
     * 维修项目数据查询
     */
    public void dataxm() {
        list_XM.clear();
        AbRequestParams params = new AbRequestParams();
        Log.i("cjn", "看看单号" + Conts.work_no);
        params.put("work_no", Conts.work_no);

        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxjd_xmlb, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int a, String s) {
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
                            entity.setWxxm_khgs(item.getDouble("wxxm_khgs"));
                            entity.setWxxm_cb(item.getDouble("wxxm_cb"));
                            entity.setWxxm_je(item.getDouble("wxxm_je"));
                            entity.setWxxm_yje(item.getDouble("wxxm_yje"));
                            entity.setWxxm_mxcx(item.getString("wxxm_mxcx"));
                            entity.setWxxm_zt(item.getString("wxxm_zt"));
                            entity.setWxxm_bz(item.getString("wxxm_bz"));
                            entity.setWxxm_dj(item.getDouble("wxxm_dj"));
                            list_XM.add(entity);
                            Log.i("cjn", "查看这个worno" + item.getString("work_no"));
                        }
                        WeiboDialogUtils.closeDialog(mWeiboDialog);
                        adapter.setList(list_XM);
                        adapter.notifyDataSetChanged();

                        //每次查询项目都应该清空派工人员列表
                        clearPaiGongRenYuan.clearPaiGong();
                    } else {
                        WeiboDialogUtils.closeDialog(mWeiboDialog);
//                        Show.showTime(getActivity(), jsonObject.get("message").toString());

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
                Show.showTime(getActivity(), "网络连接超时");
            }
        });


    }

    public interface ChaKanPaiGongREN {
        public void onYesClick(String work_no, String wxxm_no, double wxxm_gs, double wxxm_je);
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

        Log.i("cjn", "最后一次查看json" + json);
        AbRequestParams params = new AbRequestParams();
        params.put("json", json);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxjd_addxm, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                Log.i("cjn", "XM是否成功" + s.toString());
                dataxm();
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

    public interface Delet {
        public void onYesClick(int i, String work_no);
    }
}
