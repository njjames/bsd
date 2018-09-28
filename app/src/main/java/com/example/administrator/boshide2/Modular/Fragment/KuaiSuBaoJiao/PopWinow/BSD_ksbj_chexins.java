package com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.PopWinow;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;

import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.PopWinow.PopAdapter.BSD_KSBJ_che_adp;
import com.example.administrator.boshide2.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/***
 * 快速报价----选择品牌 ----diaglog
 */
public class BSD_ksbj_chexins extends Dialog {
    ListView bsd_ksbj_chexi_s;
    private View view;
    URLS url;
    XuanZECheXing xuanZECheXing;

    public void setXuanZECheXing(XuanZECheXing xuanZECheXing) {
        this.xuanZECheXing = xuanZECheXing;
    }

    BSD_KSBJ_che_adp adapter;

    List<HashMap<String, String>> list = new ArrayList<>();


    public BSD_ksbj_chexins(final Context context, final List<HashMap<String, String>> list) {
        super(context, R.style.mydialog);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        // TODO Auto-generated constructor stub
        url = new URLS();
        view = getLayoutInflater().inflate(R.layout.bsd_ksbj_chexins, null, false);
        this.list = list;
        bsd_ksbj_chexi_s = (ListView) view.findViewById(R.id.bsd_ksbj_chexi_s);
        adapter = new BSD_KSBJ_che_adp(getContext());
        adapter.setList(list);
        bsd_ksbj_chexi_s.setAdapter(adapter);

        adapter.setXuanZe(new BSD_KSBJ_che_adp.XuanZe() {
            @Override
            public void onYesClick(String i) {
                xuanZECheXing.onyes(i);
            }
        });
        setContentView(view);
        setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams params =
                this.getWindow().getAttributes();
        params.width = 800;
        params.x = 0;
        params.y = 0;
        params.height = 600;
        this.getWindow().setAttributes(params);

    }


    public interface XuanZECheXing {
        public void onyes(String id);
    }
}
