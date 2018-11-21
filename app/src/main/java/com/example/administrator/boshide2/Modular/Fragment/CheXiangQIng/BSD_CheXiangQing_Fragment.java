package com.example.administrator.boshide2.Modular.Fragment.CheXiangQIng;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.administrator.boshide2.Conts;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Activity.MainActivity;
import com.example.administrator.boshide2.Modular.Fragment.BaseFragment;
import com.example.administrator.boshide2.Modular.Fragment.CheLiangXinXi.Entity.BSD_CLXX_ety;
import com.example.administrator.boshide2.Modular.Fragment.CheXiangQIng.Fagmt.BSD_CheLiangTuPian;
import com.example.administrator.boshide2.Modular.Fragment.CheXiangQIng.Fagmt.BSD_CheLiangWeiXiuLiShi;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.BsdUtil;
import com.example.administrator.boshide2.Tools.DownJianPan;

/**
 * @车辆详情碎片页 Created by Administrator on 2017-4-13.
 */
public class BSD_CheXiangQing_Fragment extends BaseFragment implements View.OnClickListener {
    private static final String PARAM_KEY = "param_key";
    LinearLayout bsd_lsbj_fanhui;
    TextView bus_clxq_tv_cltp, bus_clxq_tv_wxls;
    private TextView[] arr_tv;// 图标的数组0
    ScrollView scrollview;
    TextView tv_xq_number,tv_xq_cx,tv_xq_xz,tv_xq_vin,tv_xq_kh,tv_xq_lxr,tv_xq_chezhu,tv_xq_phone,tv_xq_gmrq,tv_xq_bysj,
            tv_xq_bylc,tv_xq_nextrq,tv_xq_next_lc;
    private TextView title;
    private TextView footerText;
    private String params;
    private BSD_CLXX_ety clxxEty;

    public static BSD_CheXiangQing_Fragment newInstance(String params) {
        BSD_CheXiangQing_Fragment fragment = new BSD_CheXiangQing_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_KEY, params);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        params = getArguments().getString(PARAM_KEY);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.bsd_cxq_fragment;
    }

    @Override
    public void initView() {
        scrollview= (ScrollView) view.findViewById(R.id.scrollview);
        bus_clxq_tv_cltp = (TextView) view.findViewById(R.id.bus_clxq_tv_cltp);
        bus_clxq_tv_wxls = (TextView) view.findViewById(R.id.bus_clxq_tv_wxls);
        arr_tv = new TextView[2];
        arr_tv[0] = bus_clxq_tv_cltp;
        arr_tv[1] = bus_clxq_tv_wxls;
        bus_clxq_tv_cltp.setOnClickListener(this);
        bus_clxq_tv_wxls.setOnClickListener(this);
        tv_xq_number= (TextView) view.findViewById(R.id.tv_xq_number);
        tv_xq_cx= (TextView) view.findViewById(R.id.tv_xq_cx);
        tv_xq_xz= (TextView) view.findViewById(R.id.tv_xq_xz);
        tv_xq_vin= (TextView) view.findViewById(R.id.tv_xq_vin);
        tv_xq_kh= (TextView) view.findViewById(R.id.tv_xq_kh);
        tv_xq_lxr= (TextView) view.findViewById(R.id.tv_xq_lxr);
        tv_xq_phone=(TextView) view.findViewById(R.id.tv_xq_phone);
        tv_xq_gmrq=(TextView) view.findViewById(R.id.tv_xq_gmrq);
        tv_xq_bysj=(TextView) view.findViewById(R.id.tv_xq_bysj);
        tv_xq_bylc=(TextView) view.findViewById(R.id.tv_xq_bylc);
        tv_xq_nextrq=(TextView) view.findViewById(R.id.tv_xq_nextrq);
        tv_xq_next_lc=(TextView) view.findViewById(R.id.tv_xq_next_lc);
        title = (TextView) view.findViewById(R.id.tv_title);
        footerText = (TextView) view.findViewById(R.id.tv_footertext);
        bsd_lsbj_fanhui = (LinearLayout) view.findViewById(R.id.bsd_lsbj_fanhui);
        bsd_lsbj_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).upBSD_CLXX_page();
            }
        });
    }

    @Override
    public void initData() {
        title.setText("车辆信息详情");
        footerText.setText("公司名称 :   " + MyApplication.shared.getString("GongSiMc", "") +
                "                  公司电话 :   " + MyApplication.shared.getString("danw_dh", ""));
        getCarInfoFromParams();
        updateCarInfoUI();
        initFragment();
    }

    private void updateCarInfoUI() {
        tv_xq_number.setText(clxxEty.getChe_no());
        tv_xq_cx.setText(clxxEty.getChe_cx());
        tv_xq_xz.setText(clxxEty.getChe_xingzhi());
        tv_xq_vin.setText(clxxEty.getChe_vin());
        tv_xq_kh.setText(clxxEty.getKehu_mc());
        tv_xq_lxr.setText(clxxEty.getKehu_xm());
        tv_xq_phone.setText(clxxEty.getKehu_dh());
        tv_xq_gmrq.setText(BsdUtil.dateToStr(clxxEty.getChe_gcrq()));
        tv_xq_bysj.setText(BsdUtil.dateToStr(clxxEty.getChe_prior_byrq()));
        tv_xq_bylc.setText("" + clxxEty.getChe_prior_licheng());
        tv_xq_nextrq.setText(BsdUtil.dateToStr(clxxEty.getChe_next_byrq()));
        tv_xq_next_lc.setText("" + clxxEty.getChe_next_licheng());
    }

    private void getCarInfoFromParams() {
        clxxEty = JSON.parseObject(params, BSD_CLXX_ety.class);
    }

    /**
     * 初始化碎片
     */
    private void initFragment() {
        DownJianPan.DJP(getActivity());
        change(BSD_CheLiangTuPian.newInstance(clxxEty.getChe_no()));
        checkHighLight(0);
    }


    //切换碎片事务的方法
    private void change(Fragment f) {
        getActivity().getSupportFragmentManager()//碎片管理者
                //开启事务
                .beginTransaction()
                //替换方法
                .replace(R.id.bsd_clxq_lswx, f)
                //提交事务
                .commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bus_clxq_tv_wxls:
                DownJianPan.DJP(getActivity());
                change(BSD_CheLiangWeiXiuLiShi.newInstance(clxxEty.getChe_no()));
                checkHighLight(1);
                break;
            case R.id.bus_clxq_tv_cltp:
                DownJianPan.DJP(getActivity());
                change(BSD_CheLiangTuPian.newInstance(clxxEty.getChe_no()));
                checkHighLight(0);
                break;
        }
    }

    //选中项的变色
    private void checkHighLight(int index) {
        for (int i = 0; i < arr_tv.length; i++) {
            arr_tv[i].setTextColor(this.getResources().getColor(R.color.hong));
            arr_tv[i].setBackground(getResources().getDrawable(R.drawable.bsd_yuankuang));
        }
        arr_tv[index].setTextColor(this.getResources().getColor(R.color.bai));
        arr_tv[index].setBackground(this.getResources().getDrawable(R.drawable.bsd_shixin));
    }

}
