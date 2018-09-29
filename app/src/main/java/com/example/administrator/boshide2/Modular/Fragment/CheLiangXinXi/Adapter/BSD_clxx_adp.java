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
    LayoutInflater layoutInflater;
    Context context;
    List<BSD_CLXX_ety> list;

    public BSD_clxx_adp(Context context, List<BSD_CLXX_ety> list) {
        this.context = context;
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
        BSD_CLXX_ety item=list.get(i);
        holder.bsd_xsbj_name.setText(item.getKehu_mc());
        holder.bsd_xsbj_name.setSelected(true);
        holder.bsd_kxbj_bzsj.setText(item.getKehu_xm());
        holder.bsd_kxbj_bzsj.setSelected(true);
        holder.bsd_kxbj_gsdj.setText(item.getChe_djrq());
        holder.bsd_kxbj_je.setText(item.getChe_no());
        holder.bsd_kxbj_cz.setText(item.getChe_cx());
        holder.bsd_kxbj_qian.setText("");
        holder.bsd_kxbj_qian1.setText(item.getChe_xingzhi());
        holder.bsd_kxbj_cz.setSelected(true);
        holder.bsd_kxbj_gsdj.setSelected(true);
        return contetview;
    }

    class Holder {
        TextView bsd_kxbj_qian1;
        TextView bsd_xsbj_name;
        TextView bsd_kxbj_bzsj;
        TextView bsd_kxbj_gsdj;
        TextView bsd_kxbj_je;
        TextView bsd_kxbj_qian;
        TextView bsd_kxbj_cz;
    }
}