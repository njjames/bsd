package com.example.administrator.boshide2.Modular.Fragment.LiShiKuaiXiu;

import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.view.pullview.AbPullToRefreshView;
import com.alibaba.fastjson.JSON;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Activity.MainActivity;
import com.example.administrator.boshide2.Modular.Fragment.BaseFragment;
import com.example.administrator.boshide2.Modular.Fragment.LiShiWeiXiu.Adapter.BSD_lswx_adp;
import com.example.administrator.boshide2.Modular.Fragment.LiShiWeiXiu.Adapter.BSD_lswx_guwen_adp;
import com.example.administrator.boshide2.Modular.Fragment.LiShiWeiXiu.Entity.BSD_LSWX_ety;
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
 * @历史维修碎片页 Created by Administrator on 2017-4-13.
 */

public class BSD_LiShiKuaiXiu_Fragment extends BaseFragment implements AbPullToRefreshView.OnHeaderRefreshListener, AbPullToRefreshView.OnFooterLoadListener {
    private ListView bsd_lsbj_lv;
    private List<BSD_LSWX_ety> data = new ArrayList<BSD_LSWX_ety>();
    private BSD_lswx_adp adapter;
    private AbPullToRefreshView wx_rfview;//刷新
    private int pageIndex = 1;//分页
    private EditText et_wx_chepai;
    private TextView tv_wx_chaxun;
    private LinearLayout relat_guwen;
    //转圈
    private Dialog mWeiboDialog;
    //PopupWindow对象声明
    PopupWindow pw;
    //当前选中的列表项位置
    int clickPsition = -1;
    private BSD_lswx_guwen_adp lswx_guwen_adp;//服务顾问适配器
    private List<Map<String, String>> list_gu = new ArrayList<Map<String, String>>();
    private TextView tv_lswx_gw;
    private String guwen;
    private URLS url;
    private LinearLayout bsd_lsbj_fanhui;
    private TextView title;
    private TextView footerText;
    private String cheNo = ""; // 设置为成员变量，用来保存查询条件，否则查询条件会丢失

    @Override
    protected int getLayoutId() {
        return R.layout.bsd_lishikuaixiu_fragment;
    }

    @Override
    public void initView() {
        bsd_lsbj_fanhui = (LinearLayout) view.findViewById(R.id.bsd_lsbj_fanhui);
        bsd_lsbj_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).upBSD_MRKX_log();
            }
        });

        tv_lswx_gw = (TextView) view.findViewById(R.id.tv_lswx_gw);
        //服务顾问
        relat_guwen = (LinearLayout) view.findViewById(R.id.relat_guwen);
        relat_guwen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popwin();
            }
        });

        wx_rfview = (AbPullToRefreshView) view.findViewById(R.id.wx_rfview);
        // 查询
        tv_wx_chaxun = (TextView) view.findViewById(R.id.tv_wx_chaxun);
        tv_wx_chaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageIndex = 1;
                cheNo = et_wx_chepai.getText().toString();
                searchWXLS();
            }
        });
        et_wx_chepai = (EditText) view.findViewById(R.id.et_wx_chepai);

        bsd_lsbj_lv = (ListView) view.findViewById(R.id.bsd_lsbj_lv);
        adapter = new BSD_lswx_adp(getActivity(), data);
        bsd_lsbj_lv.setAdapter(adapter);
        bsd_lsbj_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ((MainActivity) getActivity()).showMrkxXqFragment(JSON.toJSON(data.get(i)).toString());
            }
        });

        wx_rfview.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(AbPullToRefreshView view) {
                pageIndex = 1;
                searchWXLS();
            }
        });

        wx_rfview.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {
            @Override
            public void onFooterLoad(AbPullToRefreshView view) {
                pageIndex++;
                searchWXLS();
            }
        });
        title = (TextView) view.findViewById(R.id.tv_title);
        footerText = (TextView) view.findViewById(R.id.tv_footertext);
    }

    @Override
    public void initData() {
        url = new URLS();
        title.setText("历史快修");
        footerText.setText("公司名称 :   " + MyApplication.shared.getString("GongSiMc", "") +
                "                  公司电话 :   " + MyApplication.shared.getString("danw_dh", ""));
        searchWXLS();
//        list_gu.clear();
//        guwen();
    }

    public void searchWXLS() {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        AbRequestParams params = new AbRequestParams();
        params.put("pageNumber", pageIndex);
        params.put("che_no", cheNo);
        params.put("type", 1);
        params.put("caozuoyuanid", Integer.parseInt(MyApplication.shared.getString("id", "")));
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_LSWX, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int sss, String s) {
                data.clear();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject json = jsonarray.getJSONObject(i);
                            BSD_LSWX_ety lswx_ety = new BSD_LSWX_ety();
                            lswx_ety.setWork_no(json.getString("work_no"));
                            lswx_ety.setKehu_mc(json.getString("kehu_mc"));
                            lswx_ety.setChe_no(json.getString("che_no"));
                            lswx_ety.setXche_jsrq(json.getString("xche_jsrq"));
                            lswx_ety.setXche_jcr(json.getString("xche_jcr"));
                            lswx_ety.setXche_hjje(json.getString("xche_hjje"));
                            lswx_ety.setXche_jdrq(json.getString("xche_jdrq"));
                            lswx_ety.setKehu_mc(json.getString("kehu_mc"));
                            lswx_ety.setKehu_dh(json.getString("kehu_dh"));
                            lswx_ety.setXche_jsrq(json.getString("xche_jsrq"));
                            lswx_ety.setCard_no(json.getString("card_no"));
                            lswx_ety.setZhifu_card_je(json.getString("zhifu_card_je"));//会员卡支付金额
                            lswx_ety.setZhifu_card_no(json.getString("zhifu_card_no"));//储值卡号
                            lswx_ety.setFlag_cardjs(json.getBoolean("flag_cardjs"));//储值卡结算标志
                            lswx_ety.setZhifu_card_xj(json.getString("zhifu_card_xj"));//补现金
                            data.add(lswx_ety);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
                WeiboDialogUtils.closeDialog(mWeiboDialog);
                wx_rfview.onFooterLoadFinish();
                wx_rfview.onHeaderRefreshFinish();
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
                wx_rfview.onFooterLoadFinish();
                wx_rfview.onHeaderRefreshFinish();
                Show.showTime(getActivity(), "网络连接超时");
            }
        });
    }

    private void popwin() {
        //通过布局注入器，注入布局给View对象
        View myView = getActivity().getLayoutInflater().inflate(R.layout.bsd_hygl_bumen, null);
        //通过view 和宽·高，构造PopopWindow
        pw = new PopupWindow(myView, 160, 300, true);
        pw.setBackgroundDrawable(getContext().getResources().getDrawable(
                //此处为popwindow 设置背景，同事做到点击外部区域，popwindow消失
                R.drawable.banbai));
        //设置焦点为可点击
        pw.setFocusable(true);//可以试试设为false的结果
        //将window视图显示在myButton下面
        pw.showAsDropDown(relat_guwen);
        ListView lv = (ListView) myView.findViewById(R.id.lv_hygl_bumen);
        lswx_guwen_adp = new BSD_lswx_guwen_adp(getContext(), list_gu);
        lv.setAdapter(lswx_guwen_adp);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                guwen = list_gu.get(i).get("name").toString();
                tv_lswx_gw.setText(guwen);
                pw.dismiss();
            }
        });
    }

    /**
     * 服务顾问
     *
     * @param
     */
    private void guwen() {
        AbRequestParams params = new AbRequestParams();
        params.put("type", "ITJcr");
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_HYGL_ADD_TYPE, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int sssss, String s) {
                try {
                    JSONObject object = new JSONObject(s);

                    if (object.get("status").toString().equals("1")) {
                        Log.i("传走没有", "..." + s);
                        JSONArray jsonarray = object.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {

                            JSONObject json = jsonarray.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<>();

                            map.put("name",
                                    json.get("reny_xm").toString());
                            map.put("bianhao", json.get("reny_dm").toString());
                            list_gu.add(map);
                        }
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


    @Override
    public void onFooterLoad(AbPullToRefreshView abPullToRefreshView) {

    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView abPullToRefreshView) {

    }

}
