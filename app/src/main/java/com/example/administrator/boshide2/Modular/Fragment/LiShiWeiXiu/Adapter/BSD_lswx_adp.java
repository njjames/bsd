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
    LayoutInflater layoutInflater;
    Context context;
    List<BSD_LSWX_ety> list;

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
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    class Holder {
        TextView bsd_kxbj_qian1,bsd_xsbj_name,bsd_kxbj_bzsj,bsd_kxbj_gsdj,bsd_kxbj_je,bsd_kxbj_qian,bsd_kxbj_cz;
    }

    @Override
    public View getView(int i, View contetview, ViewGroup viewGroup) {
        Holder holder=null;
        if (contetview == null) {
            holder = new Holder();
            contetview=layoutInflater.inflate(R.layout.bsd_lswx_item,null);

            holder.bsd_xsbj_name= (TextView) contetview.findViewById(R.id.bsd_xsbj_name);
            holder.bsd_kxbj_bzsj= (TextView) contetview.findViewById(R.id.bsd_kxbj_bzsj);
            holder.bsd_kxbj_gsdj= (TextView) contetview.findViewById(R.id.bsd_kxbj_gsdj);
            holder.bsd_kxbj_je= (TextView) contetview.findViewById(R.id.tv_ygsf);
            holder.bsd_kxbj_cz= (TextView) contetview.findViewById(R.id.bsd_kxbj_cz);
            holder.bsd_kxbj_qian= (TextView) contetview.findViewById(R.id.bsd_kxbj_qian);
                holder.bsd_kxbj_qian1= (TextView) contetview.findViewById(R.id.bsd_kxbj_qian1);
            contetview.setTag(holder);
        } else {
            holder = (Holder) contetview.getTag();
        }
        final BSD_LSWX_ety item=list.get(i);

        holder.bsd_xsbj_name.setText(item.getWork_no());//单据号
       holder.bsd_kxbj_bzsj.setText(item.getKehu_mc());//客户名称
        holder.bsd_kxbj_gsdj.setText(item.getChe_no());//车牌
        holder.bsd_kxbj_je.setText(item.getXche_jdrq());//接待日期
        holder.bsd_kxbj_cz.setText(item.getXche_hjje());//合计金额
        holder.bsd_kxbj_qian.setText(item.getKehu_bxno());//联系方式
        holder.bsd_kxbj_qian1.setText(item.getXche_hjje());//合计金额

        return contetview;
    }
}