package com.example.administrator.boshide2.Modular.Fragment.LiShiWeiXiu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Fragment.LiShiWeiXiu.Entity.BSD_LSWX_ety;
import com.example.administrator.boshide2.R;

import java.util.List;

/**
 * Created by Administrator on 2017-4-20.
 */

public class BSD_lswx_adp extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<BSD_LSWX_ety> list;

    public BSD_lswx_adp(Context context, List<BSD_LSWX_ety> list) {
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
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public final class Holder {
        TextView bsd_xsbj_no;
        TextView bsd_kxbj_kehumc;
        TextView bsd_kxbj_cheno;
        TextView bsd_kxbj_jdrq;
        TextView bsd_kxbj_fwgw;
        TextView bsd_kxbj_phone;
        TextView bsd_kxbj_je;
    }

    @Override
    public View getView(int i, View contetview, ViewGroup viewGroup) {
        Holder holder = null;
        if (contetview == null) {
            holder = new Holder();
            contetview = layoutInflater.inflate(R.layout.bsd_lswx_item, null);
            holder.bsd_xsbj_no = (TextView) contetview.findViewById(R.id.bsd_xsbj_no);
            holder.bsd_kxbj_kehumc = (TextView) contetview.findViewById(R.id.bsd_kxbj_kehumc);
            holder.bsd_kxbj_cheno = (TextView) contetview.findViewById(R.id.bsd_kxbj_cheno);
            holder.bsd_kxbj_jdrq = (TextView) contetview.findViewById(R.id.bsd_kxbj_jdrq);
            holder.bsd_kxbj_fwgw = (TextView) contetview.findViewById(R.id.bsd_kxbj_fwgw);
            holder.bsd_kxbj_phone = (TextView) contetview.findViewById(R.id.bsd_kxbj_phone);
            holder.bsd_kxbj_je = (TextView) contetview.findViewById(R.id.bsd_kxbj_je);
            contetview.setTag(holder);
        } else {
            holder = (Holder) contetview.getTag();
        }
        final BSD_LSWX_ety item = list.get(i);
        holder.bsd_xsbj_no.setText(item.getWork_no());//单据号
        holder.bsd_kxbj_kehumc.setText(item.getKehu_mc());//客户名称
        holder.bsd_kxbj_cheno.setText(item.getChe_no());//车牌
        holder.bsd_kxbj_jdrq.setText(item.getXche_jdrq());//接待日期
        holder.bsd_kxbj_fwgw.setText(item.getXche_jcr());//服务顾问
        holder.bsd_kxbj_phone.setText(item.getKehu_bxno());//联系方式
        holder.bsd_kxbj_je.setText(item.getXche_hjje());//合计金额
        return contetview;
    }
}