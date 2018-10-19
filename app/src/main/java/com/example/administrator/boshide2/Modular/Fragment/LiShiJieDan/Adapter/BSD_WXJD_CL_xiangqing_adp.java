package com.example.administrator.boshide2.Modular.Fragment.LiShiJieDan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_CL_Entity;
import com.example.administrator.boshide2.Modular.View.diaog.TooPromptdiaog;
import com.example.administrator.boshide2.R;

import java.util.List;

/**
 * Created by Administrator on 2017-4-21.
 */

public class BSD_WXJD_CL_xiangqing_adp extends BaseAdapter {
    LayoutInflater layoutInflater;
    Context context;
    List<BSD_WeiXiuJieDan_CL_Entity> list;
    TooPromptdiaog promptdiaog;
    Updanjia updanjia;
    int shuliangs[];
    double zongjia;
    CLDelete clDelete;

    public void setJia_jian(Jia_Jian jia_jian) {
        this.jia_jian = jia_jian;
    }

    Jia_Jian jia_jian;
    public void setClDelete(CLDelete clDelete) {
        this.clDelete = clDelete;
    }

    public void setUpdanjia(Updanjia updanjia) {
        this.updanjia = updanjia;
    }

    public void setList(List<BSD_WeiXiuJieDan_CL_Entity> list) {
        this.list = list;


        if (list.size() > 0) {
            shuliangs = new int[list.size()];
            for (int i = 0; i < list.size(); i++) {
                shuliangs[i] = (int) list.get(i).getPeij_sl();

            }
        }
    }

    public BSD_WXJD_CL_xiangqing_adp(Context context ) {

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
        TextView bsd_xzcl_name, bsd_xzcl_shuliang, bsd_xzcl_danjia, bsd_xzcl_tuhao, bsd_xzcl_pinpai;
        ImageView bsd_xzcl_caozuo;
    }

    @Override
    public View getView(final int i, View contetview, ViewGroup viewGroup) {
        Holder holder = null;
        if (contetview == null) {
            holder = new Holder();
            contetview = layoutInflater.inflate(R.layout.bsd_wxjd_xzcl_xiangqing_item, null);
            holder.bsd_xzcl_name = (TextView) contetview.findViewById(R.id.bsd_xzcl_name);
            holder.bsd_xzcl_shuliang = (TextView) contetview.findViewById(R.id.bsd_xzcl_shuliang);
            holder.bsd_xzcl_danjia = (TextView) contetview.findViewById(R.id.bsd_xzcl_danjia);
            holder.bsd_xzcl_tuhao = (TextView) contetview.findViewById(R.id.bsd_xzcl_dw);
            holder.bsd_xzcl_pinpai = (TextView) contetview.findViewById(R.id.bsd_xzcl_je);
            holder.bsd_xzcl_caozuo = (ImageView) contetview.findViewById(R.id.bsd_xzcl_caozuo);
            contetview.setTag(holder);
        } else {
            holder = (Holder) contetview.getTag();
        }


        holder.bsd_xzcl_name.setText(list.get(i).getPeij_mc());
        holder.bsd_xzcl_shuliang.setText(""+list.get(i).getPeij_sl());
        //单价
        holder.bsd_xzcl_danjia.setText(""+list.get(i).getPeij_dj());

        holder.bsd_xzcl_tuhao.setText(list.get(i).getPeij_dw());
        holder.bsd_xzcl_pinpai.setText(""+list.get(i).getPeij_sl()*list.get(i).getPeij_dj());
        final Holder finalHolder = holder;
        return contetview;
    }
    public interface Updanjia {
        public void onYesClick(int i, String name, double danjia);
    }

    public interface CLDelete {
        public void onYesClick();
    }
    public interface Jia_Jian{
        public void onYesClick();

    }
}
