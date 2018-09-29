package com.example.administrator.boshide2.Modular.Fragment.YuyueLiSHi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.Entity.BSD_WeiXiuYueYue_entiy;
import com.example.administrator.boshide2.R;

import java.util.List;

/**
 * Created by Administrator on 2017-4-20.
 */

public class BSD_yyls_adp extends BaseAdapter {
    LayoutInflater layoutInflater;
    Context context;
    List<BSD_WeiXiuYueYue_entiy> list;

    public void setList(List<BSD_WeiXiuYueYue_entiy> list) {
        this.list = list;
    }

    public BSD_yyls_adp(Context context) {
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
        TextView bsd_xsbj_name,bsd_kxbj_bzsj,bsd_kxbj_gsdj,bsd_kxbj_je,bsd_kxbj_qian,bsd_kxbj_cz,bsd_kxbj_qian1;
    }

    @Override
    public View getView(int i, View contetview, ViewGroup viewGroup) {
        Holder holder=null;
        if (contetview == null) {
            holder = new Holder();

            contetview=layoutInflater.inflate(R.layout.bsd_lsyy_item,null);

            holder.bsd_xsbj_name= (TextView) contetview.findViewById(R.id.bsd_xsbj_name);
            holder.bsd_kxbj_bzsj= (TextView) contetview.findViewById(R.id.bsd_kxbj_bzsj);
            holder.bsd_kxbj_gsdj= (TextView) contetview.findViewById(R.id.bsd_kxbj_gsdj);
            holder.bsd_kxbj_je= (TextView) contetview.findViewById(R.id.tv_ygsf);
            holder.bsd_kxbj_cz= (TextView) contetview.findViewById(R.id.bsd_kxbj_cz);
            holder.bsd_kxbj_qian= (TextView) contetview.findViewById(R.id.bsd_kxbj_qian);
            holder.bsd_kxbj_qian1=(TextView)contetview.findViewById(R.id.bsd_kxbj_qian1);
            contetview.setTag(holder);
        } else {
            holder = (Holder) contetview.getTag();
        }
        holder.bsd_xsbj_name.setText(list.get(i).getYuyue_jlrq());
        holder.bsd_kxbj_bzsj.setText(list.get(i).getYuyue_scjcrq());
        holder.bsd_kxbj_gsdj.setText(list.get(i).getKehu_mc());
        holder.bsd_kxbj_je.setText(list.get(i).getKehu_dh());
        holder.bsd_kxbj_cz.setText(list.get(i).getChe_no());
        holder.bsd_kxbj_qian.setText(list.get(i).getYuyue_czy());
        holder.bsd_kxbj_qian1.setText(list.get(i).getYuyue_progress());

        return contetview;
    }
}