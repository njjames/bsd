package com.example.administrator.boshide2.Modular.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
 * Created by Administrator on 2017-4-18.
 */

public class ChooseWxclDialogFragment extends DialogFragment implements AbPullToRefreshView.OnHeaderRefreshListener, AbPullToRefreshView.OnFooterLoadListener {
    private static final String PARAM_KEY_CHENO = "param_key_cheno";
    private static final String PARAM_KEY_KEHUNO = "param_key_kehuno";
    private Dialog mWeiboDialog;
    private Context context;
    private OnGuanBiListener onGuanBiListener;
    List<FileBean> categoryLists = new ArrayList<>();
    private TreeListViewAdapter mclAdapter;
    ListView bsd_wxyy_pop_you_lv, bsd_clpop_lv;
    private TextView iv_back;
    private AbPullToRefreshView mAbPullToRefreshView = null;// 下拉刷新
    private int pageIndex = 1;
    private String lbdm;
    private List<BSD_wxyy_cl_pop_entity> detailsLists = new ArrayList<>();
    private BSD_WXYY_CL_PopYou_adp detailsAdapter;
    private EditText et_clname;
    private TextView tv_search;
    private ImageView iv_allCategory;
    private RelativeLayout rl_allCategory;
    private URLS url;
    private List<BSD_wxyy_cl_pop_entity> tempLists = new ArrayList<>();
    private String cheNo;
    private String kehuNo;
    private View view;

    public static ChooseWxclDialogFragment newInstance(String cheNo, String kehuNo, List<BSD_wxyy_cl_pop_entity> tempLists) {
        ChooseWxclDialogFragment dialogFragment = new ChooseWxclDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_KEY_CHENO, cheNo);
        bundle.putString(PARAM_KEY_KEHUNO, kehuNo);
        dialogFragment.setArguments(bundle);
        dialogFragment.tempLists = tempLists;
        return dialogFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        cheNo = getArguments().getString(PARAM_KEY_CHENO);
        kehuNo = getArguments().getString(PARAM_KEY_KEHUNO);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bsd_wxyy_cl_popwin, null);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        initView();
        initData();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //隐藏输入法
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        params.height = LinearLayout.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes(params);
    }

    public void initView() {
        et_clname = (EditText) view.findViewById(R.id.et_clname);
        tv_search = (TextView) view.findViewById(R.id.tv_search);
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailsLists.clear();
                getDetialsInfo();
            }
        });
        mAbPullToRefreshView = (AbPullToRefreshView) view.findViewById(R.id.bsd_wxyy_cl_mPullRefreshView);
        // 设置监听器
        mAbPullToRefreshView.setOnHeaderRefreshListener(this);
        mAbPullToRefreshView.setOnFooterLoadListener(this);
        // 设置进度条的样式
        mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
                context.getResources().getDrawable(R.drawable.progress_circular));
        mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(
                context.getResources().getDrawable(R.drawable.progress_circular));
        iv_allCategory = (ImageView) view.findViewById(R.id.iv_category_all);
        rl_allCategory = (RelativeLayout) view.findViewById(R.id.rl_category_all);
        rl_allCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_allCategory.setVisibility(View.VISIBLE);
                // 不仅要请求右边的数据，还要刷新左边的类别
                getCategoryInfo();
                // 刷新右边的数据， 并且请求第一页的数据
                pageIndex = 1;
                lbdm ="";
                getDetialsInfo();
            }
        });
        bsd_wxyy_pop_you_lv = (ListView) view.findViewById(R.id.bsd_wxyy_pop_you_lv);
        detailsAdapter = new BSD_WXYY_CL_PopYou_adp(context, detailsLists);
        for (BSD_wxyy_cl_pop_entity tempList : tempLists) {
            detailsAdapter.addTempMapItem(tempList.getPeij_no(), tempList.getPeij_sl());
        }
        detailsAdapter.setOnAddListener(new BSD_WXYY_CL_PopYou_adp.OnAddListener() {
            @Override
            public void onAdd(BSD_wxyy_cl_pop_entity entity) {
                // 说明添加过这个配件，那么不用在获取价格了，直接数量加1
                if (tempLists.contains(entity)) {
                    // 循环找到这个配件
                    for (BSD_wxyy_cl_pop_entity tempList : tempLists) {
                        if (tempList.getPeij_no().equals(entity.getPeij_no())) {
                            tempList.setPeij_sl(tempList.getPeij_sl() + 1);
                            break;
                        }
                    }
                    detailsAdapter.addChooedItem(entity.getPeij_no());
                    detailsAdapter.notifyDataSetChanged();
                } else {
                    getPeijDJ(entity);
                }
            }

            @Override
            public void onSubtract(BSD_wxyy_cl_pop_entity entity) {
                if (tempLists.contains(entity)) {
                    // 循环找到这个配件
                    for (BSD_wxyy_cl_pop_entity tempList : tempLists) {
                        if (tempList.getPeij_no().equals(entity.getPeij_no()) && tempList.getPeij_sl() > 0) {
                            tempList.setPeij_sl(tempList.getPeij_sl() - 1);
                            detailsAdapter.subtractChooedItem(entity.getPeij_no());
                            detailsAdapter.notifyDataSetChanged();
                            break;
                        }
                    }
                }
            }
        });
        bsd_wxyy_pop_you_lv.setAdapter(detailsAdapter);
        iv_back = (TextView) view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGuanBiListener.onGuanBi(tempLists);
                close();
            }
        });
    }

    private void initData() {
        url = new URLS();
        //初始化查询相关条件
        lbdm = "";
        pageIndex = 1;
        getCategoryInfo();
        //右边内容
        getDetialsInfo();
    }


    public void getPeijDJ(final BSD_wxyy_cl_pop_entity entity) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(context, "加载中...");
        AbRequestParams params = new AbRequestParams();
        params.put("id", MyApplication.shared.getString("BSD_jiaqian_id", ""));//接口读取默认价格
        params.put("keh_no", kehuNo);//客户编号
        params.put("peij_bm", entity.getPeij_no());//	配件编码
        params.put("che_no", cheNo);//车牌号
        params.put("gongsiNo", MyApplication.shared.getString("GongSiNo", ""));
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxyy_xljq, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String data) {
                double jiaqian = Double.parseDouble(data.toString());
                BSD_wxyy_cl_pop_entity tempItem = new BSD_wxyy_cl_pop_entity();
                tempItem.setReco_no1(entity.getReco_no1());
                tempItem.setPeij_no(entity.getPeij_no());
                tempItem.setPeij_mc(entity.getPeij_mc());
                tempItem.setPeij_sl((double) 1);
                tempItem.setPeij_ydj(jiaqian);
                tempItem.setPeij_dw(entity.getPeij_dw());
                tempItem.setPeij_th(entity.getPeij_th());
                tempLists.add(tempItem);
                detailsAdapter.addChooedItem(entity.getPeij_no());
                int firstVisiblePosition = bsd_wxyy_pop_you_lv.getFirstVisiblePosition();
                detailsAdapter.notifyDataSetChanged();
                bsd_wxyy_pop_you_lv.setSelection(firstVisiblePosition);
                Show.showTime(getActivity(), "添加成功");
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

    /**
     * 获取类别信息，配件的
     */
    public void getCategoryInfo() {
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxyy_cllb, null, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int a, String s) {
                try {
                    JSONObject json = new JSONObject(s);
                    if (json.get("status").toString().equals("1")) {
                        categoryLists.clear();
                        JSONArray jsonarray = json.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject item = jsonarray.getJSONObject(i);
                            BSD_WeiXiuYuYue_Cl_FileBEan filebean = new BSD_WeiXiuYuYue_Cl_FileBEan();
                            filebean.setPeijlb_dm(item.getString("peijlb_dm"));
                            filebean.setPeijlb_top(item.getString("peijlb_top"));
                            filebean.setPeijlb_mc(item.getString("peijlb_mc"));
                            filebean.setPeijlb_qz(item.getString("peijlb_qz"));
                            filebean.setPeijlb_len(item.getInt("peijlb_len"));
                            categoryLists.add(new FileBean(filebean.getPeijlb_dm(), filebean.getPeijlb_top(), filebean.getPeijlb_mc()));
                        }
                        updateCategrory();
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
        bsd_clpop_lv = (ListView) view.findViewById(R.id.bsd_pop_zuo_lv);
        try {
            mclAdapter = new SimpleTreeAdapter<FileBean>(bsd_clpop_lv, context, categoryLists, 10);
            mclAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
                @Override
                public void onClick(Node node, int position) {
                    if (node.isLeaf()) {
                        //这里写的是点击item刷新数据的操作
                        //右面数据请求
                        detailsLists.clear();
                        lbdm = node.getId();
                        getDetialsInfo();
                    }
                }

            });
            bsd_clpop_lv.setAdapter(mclAdapter);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void getDetialsInfo() {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(context, "加载中...");
        AbRequestParams params = new AbRequestParams();
        params.put("id", MyApplication.shared.getString("id", ""));
        params.put("pageNumber", pageIndex);
        params.put("fen", lbdm);
        params.put("clmc", et_clname.getText().toString());
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_wxyy_cainr, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int a, String s) {
                try {
                    JSONObject json = new JSONObject(s);
                    if (json.get("status").toString().equals("1")) {
                        JSONArray jsonarray = json.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject item = jsonarray.getJSONObject(i);
                            BSD_wxyy_cl_pop_entity entity = new BSD_wxyy_cl_pop_entity();
                            entity.setReco_no1(item.getInt("reco_no1"));
                            entity.setPeij_no(item.getString("peij_no"));
                            entity.setPeij_mc(item.getString("peij_mc"));
                            entity.setPeij_th(item.getString("peij_th"));
                            entity.setPeij_dw(item.getString("peij_dw"));
                            entity.setPeij_kc(item.getString("peij_kc"));
                            entity.setQxjp(item.getString("qxjp"));
                            entity.setPeij_cx(item.getString("peij_cx"));
                            detailsLists.add(entity);
                        }
                        detailsAdapter.notifyDataSetChanged();
                    } else {
                        Show.showTime(getActivity(), json.get("message").toString());
                        subPage();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    subPage();
                }
                WeiboDialogUtils.closeDialog(mWeiboDialog);
                mAbPullToRefreshView.onFooterLoadFinish();
                mAbPullToRefreshView.onHeaderRefreshFinish();
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
                subPage();
            }
        });
    }

    private void subPage() {
        if (pageIndex > 1) {
            pageIndex--;
        }
    }

    @Override
    public void onFooterLoad(AbPullToRefreshView abPullToRefreshView) {
        pageIndex++;
        getDetialsInfo();

    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView abPullToRefreshView) {
        pageIndex = 1;
        getDetialsInfo();

    }

    public interface OnGuanBiListener {
        void onGuanBi(List<BSD_wxyy_cl_pop_entity> tempList);
    }

    public void setOnGuanBiListener(OnGuanBiListener onGuanBiListener) {
        this.onGuanBiListener = onGuanBiListener;

    }

    public List<BSD_wxyy_cl_pop_entity> getTempLists() {
        return tempLists;
    }

    public void setTempLists(List<BSD_wxyy_cl_pop_entity> tempLists) {
        this.tempLists = tempLists;
        for (BSD_wxyy_cl_pop_entity tempList : tempLists) {
            detailsAdapter.addTempMapItem(tempList.getPeij_no(), tempList.getPeij_sl());
        }
        detailsAdapter.notifyDataSetChanged();
    }

    private void close() {
        this.dismiss();
    }
}
