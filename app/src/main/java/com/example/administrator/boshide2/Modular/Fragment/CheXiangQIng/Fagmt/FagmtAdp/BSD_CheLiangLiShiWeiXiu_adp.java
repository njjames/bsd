package com.example.administrator.boshide2.Modular.Fragment.CheXiangQIng.Fagmt.FagmtAdp;

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
 * 车辆信息-详情页-维修历史
 */
public class BSD_CheLiangLiShiWeiXiu_adp extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<BSD_LSWX_ety> list;

    public BSD_CheLiangLiShiWeiXiu_adp(Context context, List<BSD_LSWX_ety> list) {
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
        return i;
    }

    @Override
    public View getView(int i, View contetview, ViewGroup viewGroup) {
        Holder holder;
        if (contetview == null) {
            holder = new Holder();
            contetview=layoutInflater.inflate(R.layout.bsd_clxq_item,null);
            holder.bsd_xsbj_no= (TextView) contetview.findViewById(R.id.bsd_xsbj_no);
            holder.bsd_xsbj_name= (TextView) contetview.findViewById(R.id.bsd_xsbj_name);
            holder.bsd_kxbj_phone= (TextView) contetview.findViewById(R.id.bsd_kxbj_phone);
            holder.bsd_kxbj_je= (TextView) contetview.findViewById(R.id.bsd_kxbj_je);
            holder.bsd_kxbj_jdrq= (TextView) contetview.findViewById(R.id.bsd_kxbj_jdrq);
            holder.bsd_kxbj_jbr= (TextView) contetview.findViewById(R.id.bsd_kxbj_jbr);
            holder.chezhu = (TextView) contetview.findViewById(R.id.tv_chezhu);
            contetview.setTag(holder);
        } else {
            holder = (Holder) contetview.getTag();
        }

        BSD_LSWX_ety item=list.get(i);
        holder.bsd_xsbj_no.setText(item.getWork_no());
        holder.bsd_xsbj_name.setText(item.getKehu_mc());
        holder.bsd_kxbj_phone.setText(item.getKehu_dh());
        holder.chezhu.setText(item.getChe_zjno());
        holder.bsd_kxbj_jdrq.setText(item.getXche_jdrq());
        holder.bsd_kxbj_je.setText(item.getXche_hjje());
        holder.bsd_kxbj_jbr.setText(item.getXche_jcr());
        return contetview;
    }

    public final class Holder {
        TextView bsd_xsbj_no;
        TextView bsd_kxbj_jbr;
        TextView bsd_xsbj_name;
        TextView bsd_kxbj_phone;
        TextView bsd_kxbj_je;
        TextView bsd_kxbj_jdrq;
        TextView chezhu;
    }
}