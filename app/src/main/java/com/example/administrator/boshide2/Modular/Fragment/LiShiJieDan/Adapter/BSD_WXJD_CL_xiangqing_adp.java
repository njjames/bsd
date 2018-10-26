package com.example.administrator.boshide2.Modular.Fragment.LiShiJieDan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_CL_Entity;
import com.example.administrator.boshide2.Modular.View.diaog.TooPromptdiaog;
import com.example.administrator.boshide2.R;

import java.util.List;

/**
 * Created by Administrator on 2017-4-21.
 */

public class BSD_WXJD_CL_xiangqing_adp extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<BSD_WeiXiuJieDan_CL_Entity> list;

    public BSD_WXJD_CL_xiangqing_adp(Context context, List<BSD_WeiXiuJieDan_CL_Entity> list) {
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
        return i;
    }


    public final class Holder {
        TextView bsd_xzcl_name;
        TextView bsd_xzcl_sl;
        TextView bsd_xzcl_dj;
        TextView bsd_xzcl_dw;
        TextView bsd_xzcl_je;
    }

    @Override
    public View getView(final int i, View contetview, ViewGroup viewGroup) {
        Holder holder = null;
        if (contetview == null) {
            holder = new Holder();
            contetview = layoutInflater.inflate(R.layout.bsd_wxjd_xzcl_xiangqing_item, null);
            holder.bsd_xzcl_name = (TextView) contetview.findViewById(R.id.bsd_xzcl_name);
            holder.bsd_xzcl_sl = (TextView) contetview.findViewById(R.id.bsd_xzcl_sl);
            holder.bsd_xzcl_dj = (TextView) contetview.findViewById(R.id.bsd_xzcl_dj);
            holder.bsd_xzcl_dw = (TextView) contetview.findViewById(R.id.bsd_xzcl_dw);
            holder.bsd_xzcl_je = (TextView) contetview.findViewById(R.id.bsd_xzcl_je);
            contetview.setTag(holder);
        } else {
            holder = (Holder) contetview.getTag();
        }
        holder.bsd_xzcl_name.setText(list.get(i).getPeij_mc());
        holder.bsd_xzcl_sl.setText("" + list.get(i).getPeij_sl());
        holder.bsd_xzcl_dj.setText("" + list.get(i).getPeij_dj());
        holder.bsd_xzcl_dw.setText(list.get(i).getPeij_dw());
        holder.bsd_xzcl_je.setText("" + list.get(i).getPeij_sl() * list.get(i).getPeij_dj());
        return contetview;
    }
}
