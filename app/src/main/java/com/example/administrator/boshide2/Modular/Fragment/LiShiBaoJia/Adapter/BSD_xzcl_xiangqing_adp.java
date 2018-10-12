package com.example.administrator.boshide2.Modular.Fragment.LiShiBaoJia.Adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.Entity.BSD_KuaiSuBaoJia_CL_entity;
import com.example.administrator.boshide2.Modular.View.diaog.TooPromptdiaog;
import com.example.administrator.boshide2.R;

import java.util.List;

/**
 * Created by Administrator on 2017-4-18.
 */

/**
 * 博士德维修cailiao适配器
 */
public class BSD_xzcl_xiangqing_adp extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<BSD_KuaiSuBaoJia_CL_entity> list;

    public BSD_xzcl_xiangqing_adp(Context context, List<BSD_KuaiSuBaoJia_CL_entity> list) {
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
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


    public final class Holder {
        TextView bsd_xzcl_no;
        TextView bsd_xzcl_name;
        TextView bsd_xzcl_shuliang;
        TextView bsd_xzcl_danjia;
        TextView bsd_xzcl_tuhao;
        TextView bsd_xzcl_pinpai;
        TextView bsd_xzcl_dw;
        TextView bsd_xzcl_je;
    }
    @Override
    public View getView(final int i, View contetview, ViewGroup viewGroup) {
        Holder holder = null;
        if (contetview == null) {
            holder = new Holder();
            contetview = layoutInflater.inflate(R.layout.bsd_ksbj_wxcl_xiangqing_item, null);
            holder.bsd_xzcl_no = (TextView) contetview.findViewById(R.id.bsd_xzcl_no);
            holder.bsd_xzcl_name = (TextView) contetview.findViewById(R.id.bsd_xzcl_name);
            holder.bsd_xzcl_shuliang = (TextView) contetview.findViewById(R.id.bsd_xzcl_shuliang);
            holder.bsd_xzcl_danjia = (TextView) contetview.findViewById(R.id.bsd_xzcl_danjia);
            holder.bsd_xzcl_je = (TextView) contetview.findViewById(R.id.bsd_xzcl_je);
            holder.bsd_xzcl_tuhao = (TextView) contetview.findViewById(R.id.bsd_xzcl_th);
            holder.bsd_xzcl_dw = (TextView) contetview.findViewById(R.id.bsd_xzcl_dw);
            holder.bsd_xzcl_pinpai = (TextView) contetview.findViewById(R.id.bsd_xzcl_pp);
            contetview.setTag(holder);
        } else {
            holder = (Holder) contetview.getTag();
        }
        holder.bsd_xzcl_no.setText(list.get(i).getPeij_no());
        holder.bsd_xzcl_name.setText(list.get(i).getPeij_mc());
        holder.bsd_xzcl_shuliang.setText("" + (int) list.get(i).getPeij_sl());
        holder.bsd_xzcl_danjia.setText("" + list.get(i).getPeij_dj());
        holder.bsd_xzcl_tuhao.setText(list.get(i).getPeij_th());
        holder.bsd_xzcl_dw.setText(list.get(i).getPeij_dw());
        holder.bsd_xzcl_pinpai.setText(list.get(i).getPeij_pp());
        holder.bsd_xzcl_je.setText("" + list.get(i).getPeij_sl() * list.get(i).getPeij_dj());
        return contetview;
    }
}
