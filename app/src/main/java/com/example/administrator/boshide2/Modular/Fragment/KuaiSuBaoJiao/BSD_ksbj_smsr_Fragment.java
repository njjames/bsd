package com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.boshide2.R;

/**
 * @我的管理碎片页 Created by Administrator on 2017-4-13.
 */

public class BSD_ksbj_smsr_Fragment extends Fragment {
    ImageView bsd_ksbj_sm, bsd_ksbj_sr;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bsd_ksbj_smsr_fragment, null);
        init(view);
        return view;
    }

    public void init(View view) {
        //扫描
        bsd_ksbj_sm= (ImageView) view.findViewById(R.id.bsd_ksbj_sm);
        bsd_ksbj_sm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        //输入
        bsd_ksbj_sr= (ImageView) view.findViewById(R.id.bsd_ksbj_sr);
        bsd_ksbj_sr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

    }
}