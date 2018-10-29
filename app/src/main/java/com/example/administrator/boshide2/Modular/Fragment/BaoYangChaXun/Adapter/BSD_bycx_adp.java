package com.example.administrator.boshide2.Modular.Fragment.BaoYangChaXun.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.boshide2.Conts;
import com.example.administrator.boshide2.Modular.View.diaog.TooPromptdiaog;
import com.example.administrator.boshide2.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-4-18.
 */

/**
 * 博士德维修项目适配器
 */
public class BSD_bycx_adp extends BaseAdapter {
    LayoutInflater layoutInflater;
    Context context;

    public void setList(List<Map<String, String>> list) {
        this.list = list;
    }

    List<Map<String,String >>list;
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

    public BSD_bycx_adp(Context context, List<Map<String,String >>list) {
        this.context = context;
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

    class Holder {
TextView bsd_bycx_tv_item_licheng,
        bsd_bycx_tv_item_hejijine,bsd_bycx_tv_item_gongshfei,
        bsd_bycx_tv_item_zhidaojia,bsd_bycx_tv_item_byyongliang,bsd_bycx_tv_item_danwei,
        bsd_bycx_tv_item_lingjianhao,bsd_bycx_tv_item_xmname;

    }

    @Override
    public View getView( final int i, View contetview, ViewGroup viewGroup) {
        Holder holder=null;
        if (contetview == null) {
            holder = new Holder();
            contetview = layoutInflater.inflate(R.layout.bsd_bycx_item,null);
            holder.bsd_bycx_tv_item_licheng= (TextView) contetview.findViewById(R.id.bsd_bycx_tv_item_licheng);
            holder.bsd_bycx_tv_item_hejijine= (TextView) contetview.findViewById(R.id.bsd_bycx_tv_item_hejijine);
            holder.bsd_bycx_tv_item_gongshfei= (TextView) contetview.findViewById(R.id.bsd_bycx_tv_item_gongshfei);
            holder.bsd_bycx_tv_item_zhidaojia= (TextView) contetview.findViewById(R.id.bsd_bycx_tv_item_zhidaojia);
            holder.bsd_bycx_tv_item_byyongliang= (TextView) contetview.findViewById(R.id.bsd_bycx_tv_item_byyongliang);
            holder.bsd_bycx_tv_item_danwei= (TextView) contetview.findViewById(R.id.bsd_bycx_tv_item_danwei);
            holder.bsd_bycx_tv_item_lingjianhao= (TextView) contetview.findViewById(R.id.bsd_bycx_tv_item_lingjianhao);
            holder.bsd_bycx_tv_item_xmname= (TextView) contetview.findViewById(R.id.bsd_bycx_tv_item_xmname);
            contetview.setTag(holder);
        } else {
            holder = (Holder) contetview.getTag();
        }

        if (Conts.QX_baoyangxinxi_JQ == 1){
            holder.bsd_bycx_tv_item_xmname.setText(list.get(i).get("bylc"));
            holder.bsd_bycx_tv_item_lingjianhao.setText(list.get(i).get("byxmmc"));
            holder.bsd_bycx_tv_item_danwei.setText(list.get(i).get("ycljh"));
            holder.bsd_bycx_tv_item_byyongliang.setText(""+list.get(i).get("dw"));
            holder.bsd_bycx_tv_item_zhidaojia.setText(""+list.get(i).get("yl"));
            holder.bsd_bycx_tv_item_gongshfei.setText(""+list.get(i).get("cfzdj"));
            holder.bsd_bycx_tv_item_hejijine.setText(""+list.get(i).get("gsf"));
            holder.bsd_bycx_tv_item_licheng.setText(""+list.get(i).get("subtotal"));
        }else {
            holder.bsd_bycx_tv_item_xmname.setText(list.get(i).get("bylc"));
            holder.bsd_bycx_tv_item_lingjianhao.setText(list.get(i).get("byxmmc"));
            holder.bsd_bycx_tv_item_danwei.setText(list.get(i).get("ycljh"));
            holder.bsd_bycx_tv_item_byyongliang.setText(""+list.get(i).get("dw"));
            holder.bsd_bycx_tv_item_zhidaojia.setText(""+list.get(i).get("yl"));
            holder.bsd_bycx_tv_item_gongshfei.setText("***");
            holder.bsd_bycx_tv_item_hejijine.setText("***");
            holder.bsd_bycx_tv_item_licheng.setText("***");
        }

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