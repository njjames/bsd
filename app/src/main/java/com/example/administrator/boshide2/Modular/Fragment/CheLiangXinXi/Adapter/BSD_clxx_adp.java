package com.example.administrator.boshide2.Modular.Fragment.CheLiangXinXi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Fragment.CheLiangXinXi.Entity.BSD_CLXX_ety;
import com.example.administrator.boshide2.R;

import java.util.List;

/**
 * Created by Administrator on 2017-4-20.
 */

public class BSD_clxx_adp extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<BSD_CLXX_ety> list;

    public BSD_clxx_adp(Context context, List<BSD_CLXX_ety> list) {
        this.list = list;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View contetview, ViewGroup viewGroup) {
        Holder holder;
        if (contetview == null) {
            holder = new Holder();
            contetview=layoutInflater.inflate(R.layout.bsd_clxx_item,null);
            holder.tv_reco_no= (TextView) contetview.findViewById(R.id.tv_reco_no);
            holder.bsd_xsbj_cheno= (TextView) contetview.findViewById(R.id.bsd_xsbj_cheno);
            holder.bsd_xsbj_kehumc= (TextView) contetview.findViewById(R.id.bsd_xsbj_kehumc);
            holder.bsd_kxbj_kehuxm= (TextView) contetview.findViewById(R.id.bsd_kxbj_kehuxm);
            holder.bsd_kxbj_rq= (TextView) contetview.findViewById(R.id.bsd_kxbj_rq);
            holder.bsd_kxbj_cx= (TextView) contetview.findViewById(R.id.bsd_kxbj_cx);
            holder.bsd_kxbj_xz= (TextView) contetview.findViewById(R.id.bsd_kxbj_xz);
            contetview.setTag(holder);
        } else {
            holder = (Holder) contetview.getTag();
        }
        BSD_CLXX_ety item=list.get(i);
        holder.tv_reco_no.setText("" + (i + 1));
        holder.bsd_xsbj_cheno.setText(item.getChe_no());
        holder.bsd_xsbj_kehumc.setText(item.getKehu_mc());
        holder.bsd_kxbj_kehuxm.setText(item.getKehu_xm());
        holder.bsd_kxbj_rq.setText(item.getChe_djrq());
        holder.bsd_kxbj_cx.setText(item.getChe_cx());
        holder.bsd_kxbj_xz.setText(item.getChe_xingzhi());
        return contetview;
    }

    class Holder {
        TextView bsd_xsbj_cheno;
        TextView bsd_xsbj_kehumc;
        TextView bsd_kxbj_kehuxm;
        TextView bsd_kxbj_rq;
        TextView bsd_kxbj_cx;
        TextView bsd_kxbj_xz;
        TextView tv_reco_no;
    }
}