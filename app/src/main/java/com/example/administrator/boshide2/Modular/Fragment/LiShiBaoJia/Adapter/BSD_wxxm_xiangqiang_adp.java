package com.example.administrator.boshide2.Modular.Fragment.LiShiBaoJia.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.Entity.BSD_KuaiSuBaoJia_XM_entity;
import com.example.administrator.boshide2.Modular.View.diaog.TooPromptdiaog;
import com.example.administrator.boshide2.R;

import java.util.List;

/**
 * Created by Administrator on 2017-4-18.
 */

/**
 * 博士德维修项目适配器
 */
public class BSD_wxxm_xiangqiang_adp extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<BSD_KuaiSuBaoJia_XM_entity> list;

    public BSD_wxxm_xiangqiang_adp(Context context, List<BSD_KuaiSuBaoJia_XM_entity> list) {
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
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

    public final class Holder {
        TextView bsd_xsbj_no;
        TextView bsd_xsbj_name;
        TextView bsd_kxbj_bzsj;
        TextView bsd_kxbj_gsdj;
        TextView bsd_kxbj_je;
        TextView bsd_ksbj_tv_zt;
    }

    @Override
    public View getView( final int i, View contetview, ViewGroup viewGroup) {
        Holder holder=null;
        if (contetview == null) {
            holder = new Holder();
            contetview = layoutInflater.inflate(R.layout.bsd_wxxm_xiangxiang_item,null);
            holder.bsd_xsbj_no= (TextView) contetview.findViewById(R.id.bsd_xsbj_no);
            holder.bsd_xsbj_name= (TextView) contetview.findViewById(R.id.bsd_xsbj_name);
            holder.bsd_kxbj_bzsj= (TextView) contetview.findViewById(R.id.bsd_kxbj_bzsj);
            holder.bsd_kxbj_gsdj= (TextView) contetview.findViewById(R.id.bsd_kxbj_gsdj);
            holder.bsd_kxbj_je= (TextView) contetview.findViewById(R.id.bsd_kxbj_je);
            holder.bsd_ksbj_tv_zt= (TextView) contetview.findViewById(R.id.bsd_ksbj_tv_zt);
            contetview.setTag(holder);
        } else {
            holder = (Holder) contetview.getTag();
        }
        holder.bsd_xsbj_no.setText(list.get(i).getWxxm_no());
        holder.bsd_xsbj_name.setText(list.get(i).getWxxm_mc());
        holder.bsd_kxbj_bzsj.setText("" + list.get(i).getWxxm_gs());
        holder.bsd_kxbj_gsdj.setText("" + list.get(i).getWxxm_dj());
        holder.bsd_ksbj_tv_zt.setText(list.get(i).getWxxm_zt());
        holder.bsd_kxbj_je.setText("" + list.get(i).getWxxm_gs() * list.get(i).getWxxm_dj());
        return contetview;
    }

}