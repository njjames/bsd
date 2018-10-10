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
    LayoutInflater layoutInflater;
    Context context;

    public void setList(List<BSD_KuaiSuBaoJia_XM_entity> list) {
        this.list = list;
    }

    List<BSD_KuaiSuBaoJia_XM_entity> list;
    TooPromptdiaog promptdiaog;


    Up_ksbj_gs up_ksbj_gs;
    Up_ksbj_gsdj up_ksbj_gsdj;
    Up_ksbj_gsdj_shanchu up_ksbj_sc;

    public void setUp_ksbj_gs(Up_ksbj_gs up_ksbj_gs) {
        this.up_ksbj_gs = up_ksbj_gs;
    }

    public void setUp_ksbj_gsdj(Up_ksbj_gsdj up_ksbj_gsdj) {
        this.up_ksbj_gsdj = up_ksbj_gsdj;
    }
    public void setUp_ksbj_gsdj_shanchu(Up_ksbj_gsdj_shanchu up_ksbj_sc) {
        this.up_ksbj_sc = up_ksbj_sc;
    }

    public BSD_wxxm_xiangqiang_adp(Context context) {
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
TextView bsd_xsbj_name,bsd_kxbj_bzsj,bsd_kxbj_gsdj,bsd_kxbj_je,bsd_ksbj_tv_zt;
        ImageView bsd_kxbj_xmsc;

    }

    @Override
    public View getView( final int i, View contetview, ViewGroup viewGroup) {
        Holder holder=null;
        if (contetview == null) {
            holder = new Holder();
            contetview = layoutInflater.inflate(R.layout.bsd_wxxm_xiangxiang_item,null);
            holder.bsd_xsbj_name= (TextView) contetview.findViewById(R.id.bsd_xsbj_name);
            holder.bsd_kxbj_bzsj= (TextView) contetview.findViewById(R.id.bsd_kxbj_bzsj);
            holder.bsd_kxbj_gsdj= (TextView) contetview.findViewById(R.id.bsd_kxbj_gsdj);
            holder.bsd_kxbj_je= (TextView) contetview.findViewById(R.id.tv_ygsf);
            holder.bsd_kxbj_xmsc= (ImageView) contetview.findViewById(R.id.iv_delete);
            holder.bsd_ksbj_tv_zt= (TextView) contetview.findViewById(R.id.bsd_ksbj_tv_zt);
            contetview.setTag(holder);
        } else {
            holder = (Holder) contetview.getTag();
        }
        holder.bsd_xsbj_name.setText(list.get(i).getWxxm_mc());
        //工时
        holder.bsd_kxbj_bzsj.setText(""+list.get(i).getWxxm_gs());
        //工时单价
        holder.bsd_kxbj_gsdj.setText(""+list.get(i).getWxxm_dj());
        holder.bsd_ksbj_tv_zt.setText(list.get(i).getWxxm_zt());
        holder.bsd_kxbj_je.setText(""+list.get(i).getWxxm_gs()*list.get(i).getWxxm_dj());


        return contetview;
    }

    public interface Up_ksbj_gs {
        public void onYesClick(int i, String name, double gongshi);
    }

    public interface Up_ksbj_gsdj {
        public void onYesClick(int i, String name, double gongshidanjia);
    }
    public interface  Up_ksbj_gsdj_shanchu{
        public void onYesClick();
    }

}