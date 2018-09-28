package com.example.administrator.boshide2.Modular.Fragment.WeiXiuXiangQing.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Fragment.WeiXiuXiangQing.Entity.BSD_LSWX_WXMU_Entity;
import com.example.administrator.boshide2.R;

import java.util.List;

/**
 * Created by Administrator on 2017-4-21.
 */

public class BSD_WXXQ_XM_adp extends BaseAdapter {
    LayoutInflater layoutInflater;
    Context context;
    List<BSD_LSWX_WXMU_Entity> list;

    public void setXq_gsdj(Up_xq_gsdj xq_gsdj) {
        this.xq_gsdj = xq_gsdj;
    }

    Up_xq_gsdj xq_gsdj;
    public BSD_WXXQ_XM_adp(Context context, List<BSD_LSWX_WXMU_Entity> list) {
        this.context = context;
        this.list = list;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    class Holder {
        TextView bsd_xzcl_huiyuanzhekou,bsd_xzcl_yuangongshifei,bsd_xzcl_name, bsd_xzcl_shuliang, bsd_xzcl_danjia, bsd_xzcl_tuhao, bsd_xzcl_pinpai, bsd_xzcl_caozuo;
    }

    @Override
    public View getView( final int i, View contetview, ViewGroup viewGroup) {
        Holder holder = null;
        if (contetview == null) {
            holder = new Holder();
            contetview = layoutInflater.inflate(R.layout.bsd_wxxq_xzxm_item, null);
            holder.bsd_xzcl_name = (TextView) contetview.findViewById(R.id.bsd_xzcl_name);
            holder.bsd_xzcl_shuliang = (TextView) contetview.findViewById(R.id.bsd_xzcl_shuliang);
            holder.bsd_xzcl_danjia = (TextView) contetview.findViewById(R.id.bsd_xzcl_danjia);
            holder.bsd_xzcl_tuhao = (TextView) contetview.findViewById(R.id.bsd_xzcl_tuhao);
           holder.bsd_xzcl_yuangongshifei= (TextView) contetview.findViewById(R.id.bsd_xzcl_yuangongshifei);
           holder.bsd_xzcl_huiyuanzhekou= (TextView) contetview.findViewById(R.id.bsd_xzcl_huiyuanzhekou);
            contetview.setTag(holder);
        } else {
            holder = (Holder) contetview.getTag();
        }

        final  BSD_LSWX_WXMU_Entity lswx_wxmu_entity=list.get(i);
        holder.bsd_xzcl_name.setText(lswx_wxmu_entity.getWxxm_mc());
        holder.bsd_xzcl_shuliang.setText(lswx_wxmu_entity.getWxxm_gs()+"");
        holder.bsd_xzcl_danjia.setText(lswx_wxmu_entity.getWxxm_dj()+"");
        holder.bsd_xzcl_tuhao.setText(lswx_wxmu_entity.getWxxm_je());
        holder.bsd_xzcl_yuangongshifei.setText(lswx_wxmu_entity.getWxxm_yje());
        holder.bsd_xzcl_huiyuanzhekou.setText(lswx_wxmu_entity.getWxxm_zk());
        return contetview;

    }
    public interface Up_xq_gsdj {
        public void onYesClick(int i,String name, double gongshidanjia);
    }
}
