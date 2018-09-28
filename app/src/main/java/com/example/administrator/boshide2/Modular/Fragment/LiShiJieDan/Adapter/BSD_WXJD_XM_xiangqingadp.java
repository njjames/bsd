package com.example.administrator.boshide2.Modular.Fragment.LiShiJieDan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_XM_Entity;
import com.example.administrator.boshide2.Modular.View.diaog.TooPromptdiaog;
import com.example.administrator.boshide2.R;

import java.util.List;

/**
 * Created by Administrator on 2017-4-21.
 */

public class BSD_WXJD_XM_xiangqingadp extends BaseAdapter {
    LayoutInflater layoutInflater;
    Context context;
    List<BSD_WeiXiuJieDan_XM_Entity> list;


    TooPromptdiaog promptdiaog;
    UpGongShi upGongShi;
    UpGongShiDanJia upGongShiDanJia;

    public void setXmDelete(XmDelete xmDelete) {
        this.xmDelete = xmDelete;
    }

    XmDelete xmDelete;

    public void setList(List<BSD_WeiXiuJieDan_XM_Entity> list) {
        this.list = list;
    }

    public BSD_WXJD_XM_xiangqingadp(Context context) {
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

    public void setPromptdiaog(TooPromptdiaog promptdiaog) {
        this.promptdiaog = promptdiaog;
    }

    public void setUpGongShi(UpGongShi upGongShi) {
        this.upGongShi = upGongShi;
    }

    public void setUpGongShiDanJia(UpGongShiDanJia upGongShiDanJia) {
        this.upGongShiDanJia = upGongShiDanJia;
    }

    @Override
    public View getView(final int i, View contetview, ViewGroup viewGroup) {
        Holder holder = null;
        if (contetview == null) {
            holder = new Holder();
            contetview = layoutInflater.inflate(R.layout.bsd_wxjd_xzxm_xiangqing_item, null);
            holder.bsd_xzcl_name = (TextView) contetview.findViewById(R.id.bsd_xzcl_name);
            holder.bsd_xzcl_shuliang = (TextView) contetview.findViewById(R.id.bsd_xzcl_shuliang);
            holder.bsd_xzcl_danjia = (TextView) contetview.findViewById(R.id.bsd_xzcl_danjia);
            holder.bsd_xzcl_tuhao = (TextView) contetview.findViewById(R.id.bsd_xzcl_tuhao);
            holder.bsd_xzcl_pinpai = (TextView) contetview.findViewById(R.id.bsd_xzcl_pinpai);
            holder.bsd_xzcl_caozuo = (ImageView) contetview.findViewById(R.id.bsd_xzcl_caozuo);
            contetview.setTag(holder);
        } else {
            holder = (Holder) contetview.getTag();
        }


        holder.bsd_xzcl_name.setText(list.get(i).getWxxm_mc());

        //工时
        holder.bsd_xzcl_shuliang.setText(""+list.get(i).getWxxm_gs());


        //工时单价
        holder.bsd_xzcl_danjia.setText(""+list.get(i).getWxxm_dj());

        holder.bsd_xzcl_tuhao.setText(""+list.get(i).getWxxm_gs()*list.get(i).getWxxm_dj());
        holder.bsd_xzcl_pinpai.setText(list.get(i).getWxxm_zt());
        return contetview;

    }


    public interface UpGongShi {
        public void onYesClick(int i, String name, double gongshi);
    }

    public interface UpGongShiDanJia {
        public void onYesClick(int i, String name, double gongshidanjia);
    }

    public interface  XmDelete{
        public void onYesClick();
    }
}
