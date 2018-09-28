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
    LayoutInflater layoutInflater;
    Context context;

    public void setList(List<BSD_ZaiChangDiaoDu_Entity> list) {
        this.list = list;
    }

    List<BSD_ZaiChangDiaoDu_Entity> list;

    public BSD_ZCDD_XM_adp(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (list==null)?0:list.size();
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
        TextView bsd_xzcl_djrq,bsd_xzcl_wxjd,bsd_xzcl_name, bsd_xzcl_shuliang, bsd_xzcl_danjia, bsd_xzcl_tuhao, bsd_xzcl_pinpai, bsd_xzcl_caozuo;
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
            holder.bsd_xzcl_tuhao = (TextView) contetview.findViewById(R.id.bsd_xzcl_tuhao);
            holder.bsd_xzcl_pinpai = (TextView) contetview.findViewById(R.id.bsd_xzcl_pinpai);
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
        holder.bsd_xzcl_pinpai.setText(""+list.get(i).getXche_hjje());
        holder.bsd_xzcl_caozuo.setText(list.get(i).getKehu_dh());
        holder.bsd_xzcl_wxjd.setText(list.get(i).getXche_ywlx());
        holder.bsd_xzcl_djrq.setText(list.get(i).getXche_jdrq());
        return contetview;

    }
}
