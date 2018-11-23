package com.example.administrator.boshide2.Modular.Fragment.YuyueLiSHi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.Entity.BSD_WeiXiuYueYue_entiy;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.BsdUtil;

import java.util.List;

/**
 * Created by Administrator on 2017-4-20.
 */

public class BSD_yyls_adp extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<BSD_WeiXiuYueYue_entiy> list;

    public BSD_yyls_adp(Context context, List<BSD_WeiXiuYueYue_entiy> list) {
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
        TextView bsd_xsbj_jlrq;
        TextView bsd_kxbj_jcrq;
        TextView bsd_kxbj_kehumc;
        TextView bsd_kxbj_phone;
        TextView bsd_kxbj_cheno;
        TextView bsd_kxbj_fwgw;
        TextView bsd_kxbj_zt;
    }

    @Override
    public View getView(int i, View contetview, ViewGroup viewGroup) {
        Holder holder=null;
        if (contetview == null) {
            holder = new Holder();
            contetview=layoutInflater.inflate(R.layout.bsd_lsyy_item,null);
            holder.bsd_xsbj_jlrq= (TextView) contetview.findViewById(R.id.bsd_xsbj_jlrq);
            holder.bsd_kxbj_jcrq= (TextView) contetview.findViewById(R.id.bsd_kxbj_jcrq);
            holder.bsd_kxbj_kehumc= (TextView) contetview.findViewById(R.id.bsd_kxbj_kehumc);
            holder.bsd_kxbj_phone= (TextView) contetview.findViewById(R.id.bsd_kxbj_phone);
            holder.bsd_kxbj_cheno= (TextView) contetview.findViewById(R.id.bsd_kxbj_cheno);
            holder.bsd_kxbj_fwgw= (TextView) contetview.findViewById(R.id.bsd_kxbj_fwgw);
            holder.bsd_kxbj_zt=(TextView)contetview.findViewById(R.id.bsd_kxbj_zt);
            contetview.setTag(holder);
        } else {
            holder = (Holder) contetview.getTag();
        }
        holder.bsd_xsbj_jlrq.setText(BsdUtil.dateToStr(list.get(i).getYuyue_jlrq()));
        holder.bsd_kxbj_jcrq.setText(BsdUtil.dateToStr(list.get(i).getYuyue_scjcrq()));
        holder.bsd_kxbj_kehumc.setText(list.get(i).getKehu_mc());
        holder.bsd_kxbj_phone.setText(list.get(i).getKehu_dh());
        holder.bsd_kxbj_cheno.setText(list.get(i).getChe_no());
        holder.bsd_kxbj_fwgw.setText(list.get(i).getYuyue_czy());
        holder.bsd_kxbj_zt.setText(list.get(i).getYuyue_progress());
        return contetview;
    }
}