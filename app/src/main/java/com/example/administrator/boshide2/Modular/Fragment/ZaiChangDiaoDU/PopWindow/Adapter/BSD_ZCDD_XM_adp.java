package com.example.administrator.boshide2.Modular.Fragment.ZaiChangDiaoDU.PopWindow.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Fragment.ZaiChangDiaoDU.Entity.BSD_ZaiChangDiaoDu_Entity;
import com.example.administrator.boshide2.R;

import java.util.List;

/**
 * Created by Administrator on 2017-4-21.
 */

public class BSD_ZCDD_XM_adp extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<BSD_ZaiChangDiaoDu_Entity> list;

    public void setList(List<BSD_ZaiChangDiaoDu_Entity> list) {
        this.list = list;
    }

    public BSD_ZCDD_XM_adp(Context context, List<BSD_ZaiChangDiaoDu_Entity> list) {
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    public final class Holder {
        TextView bsd_xzcl_djrq;
        TextView bsd_xzcl_wxjd;
        TextView bsd_xzcl_name;
        TextView bsd_xzcl_shuliang;
        TextView bsd_xzcl_danjia;
        TextView bsd_xzcl_tuhao;
        TextView bsd_xzcl_pinpai;
        TextView bsd_xzcl_caozuo;
    }

    @Override
    public View getView(int i, View contetview, ViewGroup viewGroup) {
        Holder holder = null;
        if (contetview == null) {
            holder = new Holder();
            contetview = layoutInflater.inflate(R.layout.bsd_acddd_item, null);
            holder.bsd_xzcl_name = (TextView) contetview.findViewById(R.id.bsd_xzcl_name);
            holder.bsd_xzcl_shuliang = (TextView) contetview.findViewById(R.id.bsd_xzcl_shuliang);
            holder.bsd_xzcl_danjia = (TextView) contetview.findViewById(R.id.bsd_xzcl_danjia);
            holder.bsd_xzcl_tuhao = (TextView) contetview.findViewById(R.id.bsd_xzcl_dw);
            holder.bsd_xzcl_pinpai = (TextView) contetview.findViewById(R.id.bsd_xzcl_je);
            holder.bsd_xzcl_caozuo = (TextView) contetview.findViewById(R.id.bsd_xzcl_caozuo);
            holder.bsd_xzcl_wxjd= (TextView) contetview.findViewById(R.id.bsd_xzcl_wxjd);
            holder.bsd_xzcl_djrq= (TextView) contetview.findViewById(R.id.bsd_xzcl_djrq);
            contetview.setTag(holder);
        } else {
            holder = (Holder) contetview.getTag();
        }

        holder.bsd_xzcl_name.setText(list.get(i).getWork_no());
        holder.bsd_xzcl_shuliang.setText(list.get(i).getChe_no());
        holder.bsd_xzcl_danjia.setText(list.get(i).getKehu_mc());
        holder.bsd_xzcl_tuhao.setText(list.get(i).getXche_cz());
        holder.bsd_xzcl_pinpai.setText("" + list.get(i).getXche_hjje());
        holder.bsd_xzcl_caozuo.setText(list.get(i).getKehu_dh());
        holder.bsd_xzcl_wxjd.setText(list.get(i).getXche_ywlx());
        holder.bsd_xzcl_djrq.setText(list.get(i).getXche_jdrq());
        return contetview;

    }
}
