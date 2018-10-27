package com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.PopAdapter.BSD_WXYY_XM_PopYou_adp;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.PopAdapter.BSD_WXYY_XM_Popzuo_adp;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.Pop_Entity.BSD_WeiXiuYuYue_XM_FileBean;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.Pop_Entity.BSD_wxyy_xm_pop_entiy;
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
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017-4-18.
 */

public class BSD_WXYY_XM_POP extends PopupWindow implements AbPullToRefreshView.OnHeaderRefreshListener, AbPullToRefreshView.OnFooterLoadListener, View.OnClickListener {
    //行布局选中效果
    URLS url;
    private View contentView;
    public Activity context;
    int w;
    int h;
    ListView bsd_pop_lv, bsd_lv;
    BSD_WXYY_XM_Popzuo_adp adapter;
    List<String> list = new ArrayList<>();
    RelativeLayout bsd_di, bsd_tou;
    List<HashMap<String, String>> data = new ArrayList<>();
    BSD_WXYY_XM_PopYou_adp adapter1;


    private Guanbi gb;
    private Dialog mWeiboDialog;
    //左边数据
    private TreeListViewAdapter mAdapter;
    private List<FileBean> mDatas = new ArrayList<FileBean>();
    private List<BSD_WeiXiuYuYue_XM_FileBean> listxm_tree = new ArrayList<BSD_WeiXiuYuYue_XM_FileBean>();


    //获取维修项目单价
    double wxxmdj;

    String lbdm;
    int lx;
    //右边数据
    //关闭监听事件
    private AbPullToRefreshView mAbPullToRefreshView = null;// 下拉刷新
    int page = 1;
    List<BSD_wxyy_xm_pop_entiy> list_pop_xm = new ArrayList<BSD_wxyy_xm_pop_entiy>();


    public void setClist(chuanlist clist) {
        this.clist = clist;
    }

    private chuanlist clist;
    String neirong;

    //图片选择切换
    TextView  bsd_wxyy_pop_rl_chaxun;
    TextView bsd_pop_shang;
    TextView bsd_wxyy_pop_rl_fanhui;
    private LinearLayout bsd_wxyy_pop_rl_xiangmu_quanbu, bsd_wxyy_pop_rl_xiangmu_1, bsd_wxyy_pop_rl_xiangmu_2, bsd_wxyy_pop_rl_xiangmu_3;
    ImageView bsd_wxyy_pop_im_xiangmu_quanbu, bsd_wxyy_pop_im_xiangmu_1, bsd_wxyy_pop_im_xiangmu_2, bsd_wxyy_pop_im_xiangmu_3;
    EditText bsd_wxyy_pop_et_neirong;

    public BSD_WXYY_XM_POP(final Activity context) {
        url=new URLS();
        this.context = context;
        w = context.getWindowManager().getDefaultDisplay().getWidth();
        h = context.getWindowManager().getDefaultDisplay().getHeight();
        init(w, h);
    }

    public void init(int w, int heitht) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.bsd_wxyy_xm_popwin, null);
        //获得 LayoutInflater 的实例
        this.setContentView(contentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(heitht);


        ColorDrawable dw = new ColorDrawable(0xb0ffffff);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

//  设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();
// 设置SelectPicPopupWindow弹出窗体动画效果

//        bsd_wexyy_tjxiangmu = (RelativeLayout) contentView.findViewById(R.id.bsd_wexyy_tjxiangmu);
//        bsd_wexyy_tjxiangmu.setOnClickListener(this);
        bsd_lv = (ListView) contentView.findViewById(R.id.bsd_lv);
        adapter1 = new BSD_WXYY_XM_PopYou_adp(context);
        adapter1.setInfoentity(new BSD_WXYY_XM_PopYou_adp.chuanwxyy_xm_pop_entity() {
            @Override
            public void onYesClick(BSD_wxyy_xm_pop_entiy entity,int i) {
                ///
                Log.i("cjn", "查==========" + entity.getWxxm_py());
                xmdj(entity, entity.getWxxm_no());


            }
        });
        bsd_wxyy_pop_rl_fanhui = (TextView) contentView.findViewById(R.id.iv_back);
        bsd_wxyy_pop_rl_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BSD_WXYY_XM_POP.this.dismiss();
                gb.guanbi();
            }
        });
        bsd_wxyy_pop_et_neirong = (EditText) contentView.findViewById(R.id.et_wxxm_name);
        bsd_wxyy_pop_et_neirong.getText().toString();
        //查询按钮
        bsd_wxyy_pop_rl_chaxun = (TextView) contentView.findViewById(R.id.tv_search);
        bsd_wxyy_pop_rl_chaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                neirong = null;
                if (bsd_wxyy_pop_et_neirong.getText().toString().length() > 0) {
                    neirong = bsd_wxyy_pop_et_neirong.getText().toString();
                }
                list_pop_xm.clear();
                reightdata();
            }
        });
        bsd_pop_shang = (TextView) contentView.findViewById(R.id.bsd_pop_shang);
        bsd_pop_shang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                neirong = null;
                if (bsd_wxyy_pop_et_neirong.getText().toString().length() > 0) {
                    neirong = bsd_wxyy_pop_et_neirong.getText().toString();
                }
                page = 1;
                lbdm = null;
                lx = 0;
                reightdata();
            }
        });

        bsd_wxyy_pop_rl_xiangmu_quanbu = (LinearLayout) contentView.findViewById(R.id.ll_all_wxxm);
        bsd_wxyy_pop_rl_xiangmu_1 = (LinearLayout) contentView.findViewById(R.id.ll_by_wxxm);
        bsd_wxyy_pop_rl_xiangmu_2 = (LinearLayout) contentView.findViewById(R.id.ll_wx_wxxm);
        bsd_wxyy_pop_rl_xiangmu_3 = (LinearLayout) contentView.findViewById(R.id.ll_wjg_wxxm);

        bsd_wxyy_pop_im_xiangmu_quanbu = (ImageView) contentView.findViewById(R.id.bsd_wxyy_pop_im_xiangmu_quanbu);
        bsd_wxyy_pop_im_xiangmu_1 = (ImageView) contentView.findViewById(R.id.bsd_wxyy_pop_im_xiangmu_1);
        bsd_wxyy_pop_im_xiangmu_2 = (ImageView) contentView.findViewById(R.id.bsd_wxyy_pop_im_xiangmu_2);
        bsd_wxyy_pop_im_xiangmu_3 = (ImageView) contentView.findViewById(R.id.bsd_wxyy_pop_im_xiangmu_3);

        bsd_wxyy_pop_rl_xiangmu_quanbu.setOnClickListener(this);
        bsd_wxyy_pop_rl_xiangmu_1.setOnClickListener(this);
        bsd_wxyy_pop_rl_xiangmu_2.setOnClickListener(this);
        bsd_wxyy_pop_rl_xiangmu_3.setOnClickListener(this);


        bsd_lv.setAdapter(adapter1);
        mAbPullToRefreshView = (AbPullToRefreshView) contentView.findViewById(R.id.bsd_wxyy_xm_mPullRefreshView);
// 设置监听器
        mAbPullToRefreshView.setOnHeaderRefreshListener(this);
        mAbPullToRefreshView.setOnFooterLoadListener(this);

        // 设置进度条的样式
        mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
                context.getResources().getDrawable(R.drawable.progress_circular));
        mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(
                context.getResources().getDrawable(R.drawable.progress_circular));

//项目选择
        initDatas();
        //右边
        reightdata();

    }

//项目单价
    public void xmdj(final BSD_wxyy_xm_pop_entiy entity, String wxxm_no) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog
                (context, "加载中...");
        AbRequestParams params = new AbRequestParams();
//        Log.i("cjn", Conts.cp);
        params.put("wxxm_no",wxxm_no );
        params.put("che_cx", Conts.chexing);
        params.put("che_no", Conts.cp);
        params.put("fenlei", 2);
        params.put("no", Conts.yuyue_no);
        params.put("feil_mc", Conts.feilv_name);   //费率名称
        Log.i("wxdj000", "项目单价的费率 ---"+Conts.feilv_name);
        Log.i("cjn","wxxm_no=="+Conts.yuyue_no
        +"-------che_cx=="+Conts.chexing+"---------che_no=="+Conts.cp+"----------" +
                "-----fenlei=="+2+"----------no=="+wxxm_no);


        Request.Post(MyApplication.shared.getString("ip", "")+url.BSD_xm_dj, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                wxxmdj = Double.parseDouble(s.toString());
                Log.i("cjn", "s的值是" + s.toString());
                clist.onYesClick(entity, wxxmdj);
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
                Log.i("cjn","请求失败");
            }
        });


    }

    /**
     * 项目数据选择
     */
    public void xiangmu() {

        bsd_pop_lv = (ListView) contentView.findViewById(R.id.bsd_wxyy_zuo_pop_lv);
        try {
            mAdapter = new SimpleTreeAdapter<FileBean>(bsd_pop_lv, context, mDatas, 10);

            mAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
                @Override
                public void onClick(Node node, int position) {
                    if (node.isLeaf()) {
                        // 这里写的是点击item刷新数据的操作
                        lbdm = node.getId();
                        neirong = null;
                        if (bsd_wxyy_pop_et_neirong.getText().toString().length() > 0) {
                            neirong = bsd_wxyy_pop_et_neirong.getText().toString();
                        }
                        list_pop_xm.clear();
                        mWeiboDialog = WeiboDialogUtils.createLoadingDialog
                                (context, "加载中...");
                        reightdata();
                    }
                }

            });

            bsd_pop_lv.setAdapter(mAdapter);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private void dismis() {
        this.dismiss();

    }


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

    public void gb(Guanbi dismiss) {
        this.gb = dismiss;


    }

    //下拉刷新
    @Override
    public void onHeaderRefresh(AbPullToRefreshView abPullToRefreshView) {
        list_pop_xm.clear();
        page = 1;
        reightdata();
//        Log.i("cjn", "走这步操作了吗" + page);
        adapter1.notifyDataSetChanged();
    }

    //上拉加载更多
    @Override
    public void onFooterLoad(AbPullToRefreshView abPullToRefreshView) {
        page++;
        reightdata();
//        adapter1.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_all_wxxm:
                bsd_wxyy_pop_im_xiangmu_quanbu.setImageResource(R.mipmap.bsd_dx_yes);
                bsd_wxyy_pop_im_xiangmu_1.setImageResource(R.mipmap.bsd_dx_no);
                bsd_wxyy_pop_im_xiangmu_2.setImageResource(R.mipmap.bsd_dx_no);
                bsd_wxyy_pop_im_xiangmu_3.setImageResource(R.mipmap.bsd_dx_no);

                break;
            case R.id.ll_by_wxxm:
                lx = 1;
                bsd_wxyy_pop_im_xiangmu_quanbu.setImageResource(R.mipmap.bsd_dx_no);
                bsd_wxyy_pop_im_xiangmu_1.setImageResource(R.mipmap.bsd_dx_yes);
                bsd_wxyy_pop_im_xiangmu_2.setImageResource(R.mipmap.bsd_dx_no);
                bsd_wxyy_pop_im_xiangmu_3.setImageResource(R.mipmap.bsd_dx_no);
                break;
            case R.id.ll_wx_wxxm:
                lx = 2;
                bsd_wxyy_pop_im_xiangmu_quanbu.setImageResource(R.mipmap.bsd_dx_no);
                bsd_wxyy_pop_im_xiangmu_1.setImageResource(R.mipmap.bsd_dx_no);
                bsd_wxyy_pop_im_xiangmu_2.setImageResource(R.mipmap.bsd_dx_yes);
                bsd_wxyy_pop_im_xiangmu_3.setImageResource(R.mipmap.bsd_dx_no);
                break;
            case R.id.ll_wjg_wxxm:
                lx = 3;
                bsd_wxyy_pop_im_xiangmu_quanbu.setImageResource(R.mipmap.bsd_dx_no);
                bsd_wxyy_pop_im_xiangmu_1.setImageResource(R.mipmap.bsd_dx_no);
                bsd_wxyy_pop_im_xiangmu_2.setImageResource(R.mipmap.bsd_dx_no);
                bsd_wxyy_pop_im_xiangmu_3.setImageResource(R.mipmap.bsd_dx_yes);
                break;
        }
    }


    /**
     * 确认添加项目
     */
//    @Override
//    public void onClick(View view) {
//        list_pop_xm1.clear();
//        for (int i = 0; i < list_pop_xm.size(); i++) {
//            if (list_pop_xm.get(i).getItem() == 1) {
//                BSD_wxyy_xm_pop_entiy entity =list_pop_xm.get(i);
//                list_pop_xm1.add(entity);
//            }
//
//        }
//
//        clist.onAdd();
//
//
////        Log.i("cjn","查看拿到的选中项"+list_pop_xm1.get(0).getWxxm_mc());
//
//    }

    public interface Guanbi {
        public void guanbi();


    }

    /**
     * 请求左边列表数据
     */
    private void initDatas() {
        AbRequestParams params = new AbRequestParams();
        Request.Post(MyApplication.shared.getString("ip", "")+url.BSD_XMXZ, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int a, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("status").toString().equals("1")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject item = jsonarray.getJSONObject(i);
                            BSD_WeiXiuYuYue_XM_FileBean fileBean = new BSD_WeiXiuYuYue_XM_FileBean();
                            fileBean.setWxxm_lbdm(item.getString("wxxm_lbdm"));
                            fileBean.setWxxm_lbmc(item.getString("wxxm_lbmc"));
                            fileBean.setWxxm_lbtop(item.getString("wxxm_lbtop"));
                            fileBean.setWxxm_lbqz(item.getString("wxxm_lbqz"));
                            fileBean.setWxxm_lblen(item.getInt("wxxm_lblen"));
                            listxm_tree.add(fileBean);
                        }
                        indata();


                    } else {
                        Show.showTime(context, jsonObject.get("message").toString());
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

        for (int i = 0; i < listxm_tree.size(); i++) {
            mDatas.add(new FileBean(listxm_tree.get(i).getWxxm_lbdm(), listxm_tree.get(i).getWxxm_lbtop(), listxm_tree.get(i).getWxxm_lbmc()));

        }
        xiangmu();

    }

    /**
     * 请求右边数据
     */
    public void reightdata() {
        list_pop_xm.clear();
//        mWeiboDialog = WeiboDialogUtils.createLoadingDialog
//                (context, "加载中...");
        AbRequestParams params = new AbRequestParams();
        params.put("pageNumber", page);
        params.put("lbdm", lbdm);
        params.put("lx", lx);
        params.put("name", neirong);
        Request.Post(MyApplication.shared.getString("ip", "")+url.BSD_xmxz_reight, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int a, String s) {
                Log.i("gscxbbb","右边数据1111111111111"+s);
                try {
                    JSONObject json = new JSONObject(s);
                    if (json.get("message").toString().equals("查询成功")) {
                        JSONArray data = json.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);
                            BSD_wxyy_xm_pop_entiy entiy = new BSD_wxyy_xm_pop_entiy();
                            entiy.setId(item.getDouble("id"));
                            entiy.setWxxm_no(item.getString("wxxm_no"));
                            entiy.setWxxm_mc(item.getString("wxxm_mc"));
                            entiy.setWxxm_gs(item.getDouble("wxxm_gs"));
                            Log.i("gsdj", "onSuccess: 右边数据里的工时单价=="+item.getDouble("wxxm_gs"));
                            entiy.setWxxm_dj(item.getDouble("wxxm_dj"));
                            entiy.setWxxm_cb(item.getDouble("wxxm_cb"));
                            entiy.setItem(0);
                            list_pop_xm.add(entiy);
                        }

                        adapter1.setList(list_pop_xm);
                        adapter1.notifyDataSetChanged();

                    } else {
                        adapter1.notifyDataSetChanged();
//                        Show.showTime(context, json.get("message").toString());

                    }
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

    public interface chuanlist {
        public void onYesClick(BSD_wxyy_xm_pop_entiy entity, double wxxmdj);
    }
}
