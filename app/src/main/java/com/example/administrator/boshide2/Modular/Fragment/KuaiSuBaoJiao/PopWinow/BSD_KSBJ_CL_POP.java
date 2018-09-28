package com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.PopWinow;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.view.pullview.AbPullToRefreshView;
import com.example.administrator.boshide2.Conts;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Activity.MainActivity;
import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.Adapter.SimpleTreeAdapter;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.PopAdapter.BSD_WXYY_CL_PopYou_adp;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.Pop_Entity.BSD_WeiXiuYuYue_Cl_FileBEan;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.Pop_Entity.BSD_wxyy_cl_pop_entity;
import com.example.administrator.boshide2.Modular.View.bean.FileBean;
import com.example.administrator.boshide2.Modular.View.tree.bean.Node;
import com.example.administrator.boshide2.Modular.View.tree.bean.TreeListViewAdapter;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.QuanQuan.WeiboDialogUtils;
import com.example.administrator.boshide2.Tools.Show;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 快速报价材料选择popwin
 * Created by Administrator on 2017-4-18.
 */

public class BSD_KSBJ_CL_POP extends PopupWindow implements AbPullToRefreshView.OnHeaderRefreshListener, AbPullToRefreshView.OnFooterLoadListener {
    //行布局选中效果
    private Dialog mWeiboDialog;
    private View contentView;
    public Activity context;
    int w;
    int h;
    RelativeLayout bsd_di, bsd_tou;
    //关闭监听事件
    private Guanbi gb;
    String  price_id;
    //___________
    //左面东西
    List<BSD_WeiXiuYuYue_Cl_FileBEan> cllblist = new ArrayList<BSD_WeiXiuYuYue_Cl_FileBEan>();
    private List<FileBean> mclDatas = new ArrayList<FileBean>();
    private TreeListViewAdapter mclAdapter;
    //ListView
    ListView bsd_wxyy_pop_you_lv, bsd_clpop_lv;


    //返回
    RelativeLayout bsd_wxyy_pop_rl_fanhui;
    //刷新
    private AbPullToRefreshView mAbPullToRefreshView = null;// 下拉刷新
    int page = 1;
    String fen;
    List<BSD_wxyy_cl_pop_entity> listclneirong = new ArrayList<>();
    BSD_WXYY_CL_PopYou_adp adpyou;
    chuanlistcl chuanlistcl;
    double jiaqian;

EditText bsd_cailiao_chaxun;
    TextView bsd_cailiaoanniucha;
    RelativeLayout bsd_pop_shang;

    public BSD_KSBJ_CL_POP.chuanlistcl getChuanlistcl() {
        return chuanlistcl;
    }

    public void setChuanlistcl(BSD_KSBJ_CL_POP.chuanlistcl chuanlistcl) {
        this.chuanlistcl = chuanlistcl;
    }
    //___________

URLS url;
    public BSD_KSBJ_CL_POP(final Activity context) {
        url=new URLS();
        this.context = context;
        w = context.getWindowManager().getDefaultDisplay().getWidth();
        h = context.getWindowManager().getDefaultDisplay().getHeight();
        init(w, h);


    }

    public void init(int w, int heitht) {


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.bsd_wxyy_cl_popwin, null);

        //获得 LayoutInflater 的实例
        this.setContentView(contentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(heitht);


        ColorDrawable dw = new ColorDrawable(0xb0ffffff);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

//         设置SelectPicPopupWindow弹出窗体可点击
        this.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();
// 设置SelectPicPopupWindow弹出窗体动画效果

        bsd_pop_shang = (RelativeLayout) contentView.findViewById(R.id.bsd_pop_shang);
        bsd_pop_shang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("jjj","11111111111111111111111");
                fen="";
                datayou();
            }
        });
        bsd_tou = (RelativeLayout) contentView.findViewById(R.id.bsd_tou);
        bsd_tou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BSD_KSBJ_CL_POP.this != null)
                    BSD_KSBJ_CL_POP.this.dismiss();
                gb.guanbi();
            }
        });

        bsd_cailiao_chaxun= (EditText) contentView.findViewById(R.id.et_clname);

        bsd_cailiaoanniucha= (TextView) contentView.findViewById(R.id.tv_search);
        bsd_cailiaoanniucha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listclneirong.clear();
                datayou();
            }
        });

        bsd_di = (RelativeLayout) contentView.findViewById(R.id.bsd_di);
        bsd_di.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BSD_KSBJ_CL_POP.this != null)
                    BSD_KSBJ_CL_POP.this.dismiss();
                gb.guanbi();
            }
        });

        //________________________________________________________
        bsd_wxyy_pop_rl_fanhui = (RelativeLayout) contentView.findViewById(R.id.iv_back);
        bsd_wxyy_pop_rl_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BSD_KSBJ_CL_POP.this.dismiss();
                gb.guanbi();
            }
        });


        bsd_wxyy_pop_you_lv = (ListView) contentView.findViewById(R.id.bsd_wxyy_pop_you_lv);

        mAbPullToRefreshView = (AbPullToRefreshView) contentView.findViewById(R.id.bsd_wxyy_cl_mPullRefreshView);
// 设置监听器
        mAbPullToRefreshView.setOnHeaderRefreshListener(this);
        mAbPullToRefreshView.setOnFooterLoadListener(this);

        // 设置进度条的样式
        mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
                context.getResources().getDrawable(R.drawable.progress_circular));
        mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(
                context.getResources().getDrawable(R.drawable.progress_circular));
        adpyou=new BSD_WXYY_CL_PopYou_adp(context);
        adpyou.setOnAddListener(new BSD_WXYY_CL_PopYou_adp.OnAddListener() {
            @Override
            public void onAdd(BSD_wxyy_cl_pop_entity entity) {



                cljaqian(entity, entity.getPeij_no());


            }

            @Override
            public void onSubtract(BSD_wxyy_cl_pop_entity entity) {

            }
        });
//左边选择
        cllb();
        //右边内容
        datayou();
        //_________________________________________________________
    }



    public  void cljaqian(final BSD_wxyy_cl_pop_entity entity, String bianhao){
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog
                (context, "加载中...");
        AbRequestParams params = new AbRequestParams();
//        params.put("id",price_id);//接口读取默认价格
        params.put("id",MyApplication.shared.getString("BSD_jiaqian_id",""));//接口读取默认价格
        params.put("keh_no", Conts.kehu_no);//客户编号
        params.put("peij_bm",bianhao);//	配件编码
        params.put("che_no",Conts.cp);//车牌号
        params.put("gongsi_no", MyApplication.shared.getString("GongSiNo", ""));
        Log.i("cjn","entity是："+entity);
        Log.i("cjn","客户编号==="+Conts.kehu_no+"配件编号==="+bianhao+"车牌号==="+Conts.cp+"默认价格id=="+MyApplication.shared.getString("BSD_jiaqian_id",""));

        Request.Post(MyApplication.shared.getString("ip", "")+url.BSD_wxyy_xljq, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                jiaqian= Double.parseDouble(s.toString());
                Log.i("cjn","s的值是"+s.toString());
                chuanlistcl.onYesClick(entity,jiaqian);
//                chuanlistcl.onAdd(entity,123);

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


    //左面开始=======
    /*
     *维修材料选择列表
     */
    public void cllb() {
        AbRequestParams params = new AbRequestParams();


        Request.Post(MyApplication.shared.getString("ip", "")+url.BSD_wxyy_cllb, null, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int a, String s) {
                try {
                    JSONObject json = new JSONObject(s);
                    if (json.get("status").toString().equals("1")) {
                        JSONArray jsonarray = json.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject item = jsonarray.getJSONObject(i);
                            BSD_WeiXiuYuYue_Cl_FileBEan entity = new BSD_WeiXiuYuYue_Cl_FileBEan();
                            entity.setPeijlb_dm(item.getString("peijlb_dm"));
                            entity.setPeijlb_top(item.getString("peijlb_top"));
                            entity.setPeijlb_mc(item.getString("peijlb_mc"));
                            entity.setPeijlb_qz(item.getString("peijlb_qz"));
                            entity.setPeijlb_len(item.getInt("peijlb_len"));
                            cllblist.add(entity);
                        }
                        indata();
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
     * 加载左边列表数据
     */

    public void indata() {
        for (int i = 0; i < cllblist.size(); i++) {
            mclDatas.add(new FileBean(cllblist.get(i).getPeijlb_dm(), cllblist.get(i).getPeijlb_top(), cllblist.get(i).getPeijlb_mc()));

        }

        xiangmu();

    }

    /**
     * 项目数据选择
     */
    public void xiangmu() {
        mclAdapter = null;

        bsd_clpop_lv = (ListView) contentView.findViewById(R.id.bsd_pop_zuo_lv);
        try {
            mclAdapter = new SimpleTreeAdapter<FileBean>(bsd_clpop_lv, context, mclDatas, 10);
            mclAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
                @Override
                public void onClick(Node node, int position) {
                    if (node.isLeaf()) {
                        //这里写的是点击item刷新数据的操作

//右面数据请求
                        listclneirong.clear();
                        fen=node.getId();
                        datayou();

                    }
                }

            });
            bsd_clpop_lv.setAdapter(mclAdapter);
            mclAdapter.notifyDataSetChanged();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    //左面结束=======
//================================================
    //右面开始

    public void datayou() {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog
                (context, "加载中...");
        AbRequestParams params = new AbRequestParams();
        params.put("id", MyApplication.shared.getString("id", ""));//用户id
        params.put("pageNumber", page);
        params.put("fen", fen);
        params.put("clmc",bsd_cailiao_chaxun.getText().toString());
        Log.i("cjn","查看这个id=======---------======----="+MyApplication.shared.getString("id", ""));
        Request.Post(MyApplication.shared.getString("ip", "")+url.BSD_wxyy_cainr, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int a, String s) {
                try {
                    JSONObject json = new JSONObject(s);

                    if (json.get("message").toString().equals("查询成功")) {

                        JSONArray jsonarray = json.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {

                            JSONObject item = jsonarray.getJSONObject(i);
                            BSD_wxyy_cl_pop_entity entity = new BSD_wxyy_cl_pop_entity();
                            entity.setReco_no1(item.getInt("reco_no1"));
                            entity.setPeij_no(item.getString("peij_no"));
                            entity.setPeij_mc(item.getString("peij_mc"));
                            entity.setPeij_th(item.getString("peij_th"));
                            entity.setPeij_dw(item.getString("peij_dw"));
                            entity.setQxjp(item.getString("qxjp"));
                            entity.setPeij_kc(item.getString("peij_kc"));
                            entity.setPeij_cx(item.getString("peij_cx"));
                            listclneirong.add(entity);
                        }
                        adpyou.setList(listclneirong);
                        bsd_wxyy_pop_you_lv.setAdapter(adpyou);
                        adpyou.notifyDataSetChanged();

                    }else {
                        Show.showTime(context, json.get("message").toString());
                    }


                    adpyou.notifyDataSetChanged();
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                    mAbPullToRefreshView.onFooterLoadFinish();
                    mAbPullToRefreshView.onHeaderRefreshFinish();
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
                mAbPullToRefreshView.onFooterLoadFinish();
                mAbPullToRefreshView.onHeaderRefreshFinish();
            }
        });

    }


    //右面结束
    public void showPopupWindow(View parent, int hight) {
        if (!this.isShowing() || this != null) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent, 0, hight);
//            WindowManager.LayoutParams lp = context.getWindow().getAttributes();
//            lp.alpha = 0.4f;
//            context.getWindow().setAttributes(lp);

        } else {
            this.dismiss();
        }
    }


    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 屏幕透明度0.0-1.0 1表示完全不透明
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((MainActivity) context).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((MainActivity) context).getWindow().setAttributes(lp);
    }

    public void gb(BSD_KSBJ_CL_POP.Guanbi dismiss) {
        this.gb = dismiss;

    }

    @Override
    public void onFooterLoad(AbPullToRefreshView abPullToRefreshView) {
        page++;
        datayou();

    }

    //
    @Override
    public void onHeaderRefresh(AbPullToRefreshView abPullToRefreshView) {
        listclneirong.clear();
        page = 1;
        datayou();

    }

    public interface Guanbi {
        public void guanbi();


    }
    public interface chuanlistcl {
        public void onYesClick(BSD_wxyy_cl_pop_entity entity,double jiaqian);
    }
}
