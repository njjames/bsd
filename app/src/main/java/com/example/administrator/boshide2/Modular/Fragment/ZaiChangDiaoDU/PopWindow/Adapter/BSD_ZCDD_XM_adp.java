package com.example.administrator.boshide2.Modular.Fragment.ZaiChangDiaoDU.PopWindow.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Entity.WorkPzGz_Entity;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.BsdUtil;

import java.util.List;

/**
 * Created by Administrator on 2017-4-21.
 */
public class BSD_ZCDD_XM_adp extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<WorkPzGz_Entity> list;

    public BSD_ZCDD_XM_adp(Context context, List<WorkPzGz_Entity> list) {
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
        TextView bsd_xzcl_ywlx;
        TextView bsd_xzcl_workno;
        TextView bsd_xzcl_cheno;
        TextView bsd_xzcl_kehumc;
        TextView bsd_xzcl_fwgw;
        TextView bsd_xzcl_je;
        TextView bsd_xzcl_kehudh;
    }

    @Override
    public View getView(int i, View contetview, ViewGroup viewGroup) {
        Holder holder = null;
        if (contetview == null) {
            holder = new Holder();
            contetview = layoutInflater.inflate(R.layout.bsd_acddd_item, null);
            holder.bsd_xzcl_workno = (TextView) contetview.findViewById(R.id.bsd_xzcl_workno);
            holder.bsd_xzcl_cheno = (TextView) contetview.findViewById(R.id.bsd_xzcl_cheno);
            holder.bsd_xzcl_kehumc = (TextView) contetview.findViewById(R.id.bsd_xzcl_kehumc);
            holder.bsd_xzcl_fwgw = (TextView) contetview.findViewById(R.id.bsd_xzcl_fwgw);
            holder.bsd_xzcl_je = (TextView) contetview.findViewById(R.id.bsd_xzcl_je);
            holder.bsd_xzcl_kehudh = (TextView) contetview.findViewById(R.id.bsd_xzcl_kehudh);
            holder.bsd_xzcl_ywlx= (TextView) contetview.findViewById(R.id.bsd_xzcl_ywlx);
            holder.bsd_xzcl_djrq= (TextView) contetview.findViewById(R.id.bsd_xzcl_djrq);
            contetview.setTag(holder);
        } else {
            holder = (Holder) contetview.getTag();
        }

        holder.bsd_xzcl_workno.setText(list.get(i).getWork_no());
        holder.bsd_xzcl_cheno.setText(list.get(i).getChe_no());
        holder.bsd_xzcl_kehumc.setText(list.get(i).getKehu_mc());
        holder.bsd_xzcl_fwgw.setText(list.get(i).getXche_cz());
        holder.bsd_xzcl_je.setText("" + list.get(i).getXche_hjje());
        holder.bsd_xzcl_ywlx.setText(list.get(i).getXche_ywlx());
        holder.bsd_xzcl_djrq.setText(BsdUtil.dateToStr(list.get(i).getXche_jdrq()));
        holder.bsd_xzcl_kehudh.setText(list.get(i).getKehu_dh());
        return contetview;

    }
}
