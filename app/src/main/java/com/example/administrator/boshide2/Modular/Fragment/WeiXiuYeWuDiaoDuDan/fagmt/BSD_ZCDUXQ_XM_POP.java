package com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.fagmt;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
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
import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.Adapter.SimpleTreeAdapter;
import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.PopWinow.PopAdapter.BSD_KSBJ_XM_PopYou_adp;
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

public class BSD_ZCDUXQ_XM_POP extends PopupWindow implements AbPullToRefreshView.OnHeaderRefreshListener, AbPullToRefreshView.OnFooterLoadListener, View.OnClickListener {
    private Dialog mWeiboDialog;
    private View view;
    public Activity context;
    private ListView bsd_pop_lv, bsd_lv;
    private BSD_KSBJ_XM_PopYou_adp detailsAdapter;
    private OnGuanbiListener onGuanBiListener;
    //左边数据
    private TreeListViewAdapter mAdapter;
    private List<FileBean> categoryLists = new ArrayList<FileBean>();
    private List<BSD_WeiXiuYuYue_XM_FileBean> listxm_tree = new ArrayList<BSD_WeiXiuYuYue_XM_FileBean>();
    private String currentLbdm;
    private int currentLx = 0;
    //右边数据
    private AbPullToRefreshView mAbPullToRefreshView = null;// 下拉刷新
    private int pageIndex = 1;
    private List<BSD_wxyy_xm_pop_entiy> detailsLists = new ArrayList<BSD_wxyy_xm_pop_entiy>();
    private LinearLayout ll_wjgWxxm;
    private LinearLayout ll_wxWxxm;
    private LinearLayout ll_byWxxm;
    private LinearLayout ll_allWxxm;
    private ImageView iv_allCategory;
    //图片选择切换
    private LinearLayout bsd_wxyy_pop_rl_xiangmu_quanbu;
    private LinearLayout bsd_wxyy_pop_rl_xiangmu_1;
    private LinearLayout bsd_wxyy_pop_rl_xiangmu_2;
    private LinearLayout bsd_wxyy_pop_rl_xiangmu_3;
    private ImageView bsd_wxyy_pop_im_xiangmu_quanbu;
    private ImageView bsd_wxyy_pop_im_xiangmu_1;
    private ImageView bsd_wxyy_pop_im_xiangmu_2;
    private ImageView bsd_wxyy_pop_im_xiangmu_3;
    private EditText et_wxxmName;
    private RelativeLayout rl_allCategory;
    private TextView tv_search;
    private TextView iv_back;
    private URLS url;
    private List<BSD_wxyy_xm_pop_entiy> tempLists = new ArrayList<>(); // 用来临时存储选择的维修项目
    private String cheCx;
    private String cheNo;
    private String cheFl;
    private String workNo;

    public BSD_ZCDUXQ_XM_POP(final Activity context) {
        this.context = context;
        initView();
        initData();
    }

    public void initView() {
        view = LayoutInflater.from(context).inflate(R.layout.bsd_wxyy_xm_popwin, null);
        //获得 LayoutInflater 的实例
        this.setContentView(view);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(new ColorDrawable(0xb0ffffff));
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();
        et_wxxmName = (EditText) view.findViewById(R.id.et_wxxm_name);
        //查询按钮
        tv_search = (TextView) view.findViewById(R.id.tv_search);
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 点击查询时，显示第一页的数据
                detailsLists.clear();
                pageIndex = 1;
                getDetialsInfo();
            }
        });
        bsd_wxyy_pop_rl_xiangmu_quanbu = (LinearLayout) view.findViewById(R.id.ll_all_wxxm);
        bsd_wxyy_pop_rl_xiangmu_1 = (LinearLayout) view.findViewById(R.id.ll_by_wxxm);
        bsd_wxyy_pop_rl_xiangmu_2 = (LinearLayout) view.findViewById(R.id.ll_wx_wxxm);
        bsd_wxyy_pop_rl_xiangmu_3 = (LinearLayout) view.findViewById(R.id.ll_wjg_wxxm);

        bsd_wxyy_pop_im_xiangmu_quanbu = (ImageView) view.findViewById(R.id.bsd_wxyy_pop_im_xiangmu_quanbu);
        bsd_wxyy_pop_im_xiangmu_1 = (ImageView) view.findViewById(R.id.bsd_wxyy_pop_im_xiangmu_1);
        bsd_wxyy_pop_im_xiangmu_2 = (ImageView) view.findViewById(R.id.bsd_wxyy_pop_im_xiangmu_2);
        bsd_wxyy_pop_im_xiangmu_3 = (ImageView) view.findViewById(R.id.bsd_wxyy_pop_im_xiangmu_3);

        bsd_wxyy_pop_rl_xiangmu_quanbu.setOnClickListener(this);
        bsd_wxyy_pop_rl_xiangmu_1.setOnClickListener(this);
        bsd_wxyy_pop_rl_xiangmu_2.setOnClickListener(this);
        bsd_wxyy_pop_rl_xiangmu_3.setOnClickListener(this);

        mAbPullToRefreshView = (AbPullToRefreshView) view.findViewById(R.id.bsd_wxyy_xm_mPullRefreshView);
        // 设置监听器
        mAbPullToRefreshView.setOnHeaderRefreshListener(this);
        mAbPullToRefreshView.setOnFooterLoadListener(this);

        // 设置进度条的样式
        mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
                context.getResources().getDrawable(R.drawable.progress_circular));
        mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(
                context.getResources().getDrawable(R.drawable.progress_circular));

        ll_allWxxm = (LinearLayout) view.findViewById(R.id.ll_all_wxxm);
        ll_byWxxm = (LinearLayout) view.findViewById(R.id.ll_by_wxxm);
        ll_wxWxxm = (LinearLayout) view.findViewById(R.id.ll_wx_wxxm);
        ll_wjgWxxm = (LinearLayout) view.findViewById(R.id.ll_wjg_wxxm);
        ll_allWxxm.setOnClickListener(this);
        ll_byWxxm.setOnClickListener(this);
        ll_wxWxxm.setOnClickListener(this);
        ll_wjgWxxm.setOnClickListener(this);

        iv_allCategory = (ImageView) view.findViewById(R.id.iv_category_all);
        rl_allCategory = (RelativeLayout) view.findViewById(R.id.rl_category_all);
        rl_allCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_allCategory.setVisibility(View.VISIBLE);
                // 不仅要请求右边的数据，还要刷新左边的类别
                getCategoryInfo();
                // 刷新右边的数据， 并且请求第一页的数据
                detailsLists.clear();
                pageIndex = 1;
                currentLbdm ="";
                getDetialsInfo();
            }
        });
        bsd_lv = (ListView) view.findViewById(R.id.bsd_lv);
        detailsAdapter = new BSD_KSBJ_XM_PopYou_adp(context, detailsLists);
        detailsAdapter.setOnAddListener(new BSD_KSBJ_XM_PopYou_adp.OnAddListener() {
            @Override
            public void onAdd(BSD_wxyy_xm_pop_entiy entity) {
                // 首先判断维修项目是否添加
                if (tempLists.contains(entity)) {
                    Show.showTime(context, "此项目已经添加，不能重复添加");
                } else {  // 如果没有添加，那么就去获取这个维修项目的单价
                    getWXXMDj(entity);
                    //点击添加走的事件
                }
            }

        });
        bsd_lv.setAdapter(detailsAdapter);
        // 返回
        iv_back = (TextView) view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onGuanBiListener != null) {
                    onGuanBiListener.onGuanBi(tempLists);
                    BSD_ZCDUXQ_XM_POP.this.dismiss();
                }
            }
        });
    }

    private void initData() {
        url=new URLS();
        //初始化查询相关条件
        currentLbdm = "";
        currentLx = 0;
        pageIndex = 1;
        // 左边的类别
        getCategoryInfo();
        // 右边的详细内容
        getDetialsInfo();
    }

    /**
     * 获取维修项目单价
     * @param entiy
     */
    public void getWXXMDj(final BSD_wxyy_xm_pop_entiy entiy) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(context, "加载中...");
        AbRequestParams params = new AbRequestParams();
        params.put("wxxm_no",entiy.getWxxm_no());
        params.put("che_cx", cheCx);
        params.put("che_no", cheNo);
        params.put("fenlei", 3);  //分类
        params.put("feil_mc", cheFl);   //费率名称
        params.put("no", workNo);
        Request.Post(MyApplication.shared.getString("ip", "")+url.BSD_xm_dj, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                double wxxmdj = Double.parseDouble(data);
                // 不能在原来的entiy上修改，不然数据源也就修改了，需要新建一个对放到临时表中
                BSD_wxyy_xm_pop_entiy tempItem = new BSD_wxyy_xm_pop_entiy();
                // 设置维修单上需要的字段
                tempItem.setWxxm_no(entiy.getWxxm_no());
                tempItem.setWxxm_mc(entiy.getWxxm_mc());
                tempItem.setWxxm_zddj(wxxmdj); // 暂时用这个变量表示金额，这里的金额是总金额
                if (entiy.getWxxm_gs() == 0) {
                    tempItem.setWxxm_gs(1.0);
                    tempItem.setWxxm_dj(wxxmdj);
                } else {
                    tempItem.setWxxm_gs(entiy.getWxxm_gs());
                    tempItem.setWxxm_dj(wxxmdj / entiy.getWxxm_gs());
                }
                tempItem.setWxxm_cb(entiy.getWxxm_cb());
//                entiy.setWxxm_dj(wxxmdj);
                tempLists.add(tempItem);
//                detailsLists.get(position).setHasSelected(true);
                detailsAdapter.addChooedWxxm(entiy.getWxxm_no());
                int firstVisiblePosition = bsd_lv.getFirstVisiblePosition();
                detailsAdapter.notifyDataSetChanged();
                bsd_lv.setSelection(firstVisiblePosition);
                Show.showTime(context, "添加成功");
//                clist.onAdd(entiy, wxxmdj);
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
                Show.showTime(context, "添加失败");
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });
    }

    //下拉刷新
    @Override
    public void onHeaderRefresh(AbPullToRefreshView abPullToRefreshView) {
        detailsLists.clear();
        pageIndex = 1;
        getDetialsInfo();
    }

    //上拉加载更多
    @Override
    public void onFooterLoad(AbPullToRefreshView abPullToRefreshView) {
        pageIndex++;
        getDetialsInfo();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_all_wxxm:
                currentLx = 0;
                bsd_wxyy_pop_im_xiangmu_quanbu.setImageResource(R.drawable.ic_checked);
                bsd_wxyy_pop_im_xiangmu_1.setImageResource(R.drawable.ic_unchecked);
                bsd_wxyy_pop_im_xiangmu_2.setImageResource(R.drawable.ic_unchecked);
                bsd_wxyy_pop_im_xiangmu_3.setImageResource(R.drawable.ic_unchecked);
                break;
            case R.id.ll_by_wxxm:
                currentLx = 1;
                bsd_wxyy_pop_im_xiangmu_quanbu.setImageResource(R.drawable.ic_unchecked);
                bsd_wxyy_pop_im_xiangmu_1.setImageResource(R.drawable.ic_checked);
                bsd_wxyy_pop_im_xiangmu_2.setImageResource(R.drawable.ic_unchecked);
                bsd_wxyy_pop_im_xiangmu_3.setImageResource(R.drawable.ic_unchecked);
                break;
            case R.id.ll_wx_wxxm:
                currentLx = 2;
                bsd_wxyy_pop_im_xiangmu_quanbu.setImageResource(R.drawable.ic_unchecked);
                bsd_wxyy_pop_im_xiangmu_1.setImageResource(R.drawable.ic_unchecked);
                bsd_wxyy_pop_im_xiangmu_2.setImageResource(R.drawable.ic_checked);
                bsd_wxyy_pop_im_xiangmu_3.setImageResource(R.drawable.ic_unchecked);
                break;
            case R.id.ll_wjg_wxxm:
                currentLx = 3;
                bsd_wxyy_pop_im_xiangmu_quanbu.setImageResource(R.drawable.ic_unchecked);
                bsd_wxyy_pop_im_xiangmu_1.setImageResource(R.drawable.ic_unchecked);
                bsd_wxyy_pop_im_xiangmu_2.setImageResource(R.drawable.ic_unchecked);
                bsd_wxyy_pop_im_xiangmu_3.setImageResource(R.drawable.ic_checked);
                break;
        }
    }

    /**
     * 请求类别信息，也就是左边列表数据
     */
    private void getCategoryInfo() {
        AbRequestParams params = new AbRequestParams();
        Request.Post(MyApplication.shared.getString("ip", "")+url.BSD_XMXZ, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    if (jsonObject.get("status").toString().equals("1")) {
                        categoryLists.clear();
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject item = jsonarray.getJSONObject(i);
                            BSD_WeiXiuYuYue_XM_FileBean fileBean = new BSD_WeiXiuYuYue_XM_FileBean();
                            fileBean.setWxxm_lbdm(item.getString("wxxm_lbdm"));
                            fileBean.setWxxm_lbmc(item.getString("wxxm_lbmc"));
                            fileBean.setWxxm_lbtop(item.getString("wxxm_lbtop"));
                            fileBean.setWxxm_lbqz(item.getString("wxxm_lbqz"));
                            fileBean.setWxxm_lblen(item.getInt("wxxm_lblen"));
                            categoryLists.add(new FileBean(fileBean.getWxxm_lbdm(), fileBean.getWxxm_lbtop(), fileBean.getWxxm_lbmc()));
                        }
                        updateCategrory();
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
     * 更新类别数据
     */
    public void updateCategrory() {
        // 还想是因为这个Adapter不能notify，所以这样写了（也可能不是）
        bsd_pop_lv = (ListView) view.findViewById(R.id.bsd_wxyy_zuo_pop_lv);
        try {
            mAdapter = new SimpleTreeAdapter<FileBean>(bsd_pop_lv, context, categoryLists, 10);
            mAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
                @Override
                public void onClick(Node node, int position) {
                    // 隐藏全部项目的选择图标
                    iv_allCategory.setVisibility(View.INVISIBLE);
                    detailsLists.clear();
                    currentLbdm = node.getId();
                    pageIndex = 1;
                    getDetialsInfo();
                }
            });
            bsd_pop_lv.setAdapter(mAdapter);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 请求右边数据
     */
    public void getDetialsInfo() {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(context, "加载中...");
        AbRequestParams params = new AbRequestParams();
        params.put("pageNumber", pageIndex);
        params.put("lbdm", currentLbdm);
        params.put("lx", currentLx);
        params.put("name", et_wxxmName.getText().toString());
        Request.Post(MyApplication.shared.getString("ip", "")+url.BSD_xmxz_reight, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject item = jsonArray.getJSONObject(i);
                            BSD_wxyy_xm_pop_entiy entiy = new BSD_wxyy_xm_pop_entiy();
                            entiy.setId(item.getDouble("id"));
                            entiy.setWxxm_no(item.getString("wxxm_no"));
                            entiy.setWxxm_mc(item.getString("wxxm_mc"));
                            entiy.setWxxm_gs(item.getDouble("wxxm_gs"));
                            entiy.setWxxm_dj(item.getDouble("wxxm_dj"));
                            entiy.setWxxm_cb(item.getDouble("wxxm_cb"));
                            entiy.setItem(0);
                            detailsLists.add(entiy);
                        }
                        detailsAdapter.notifyDataSetChanged();
                    } else {
                        Show.showTime(context, jsonObject.get("message").toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mAbPullToRefreshView.onFooterLoadFinish();
                mAbPullToRefreshView.onHeaderRefreshFinish();
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
                mAbPullToRefreshView.onFooterLoadFinish();
                mAbPullToRefreshView.onHeaderRefreshFinish();
            }
        });
    }

    public interface OnGuanbiListener {
        void onGuanBi(List<BSD_wxyy_xm_pop_entiy> tempList);
    }

    public void setOnGuanbiListener(OnGuanbiListener onGuanbiListener) {
        this.onGuanBiListener = onGuanbiListener;
    }

    public List<BSD_wxyy_xm_pop_entiy> getTempLists() {
        return tempLists;
    }

    public void setTempLists(List<BSD_wxyy_xm_pop_entiy> tempLists) {
        this.tempLists = tempLists;
        for (BSD_wxyy_xm_pop_entiy tempList : tempLists) {
            detailsAdapter.addChooedWxxm(tempList.getWxxm_no());
        }
        detailsAdapter.notifyDataSetChanged();
    }

    public void setCheCx(String cheCx) {
        this.cheCx = cheCx;
    }

    public void setCheNo(String cheNo) {
        this.cheNo = cheNo;
    }

    public void setCheFl(String cheFl) {
        this.cheFl = cheFl;
    }

    public void setWorkNo(String workNo) {
        this.workNo = workNo;
    }
}