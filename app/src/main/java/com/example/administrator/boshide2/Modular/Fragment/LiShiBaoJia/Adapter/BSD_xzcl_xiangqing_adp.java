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
    LayoutInflater layoutInflater;
    Context context;
    List<BSD_KuaiSuBaoJia_CL_entity> list;
    TooPromptdiaog promptdiaog;
    double zongjia;

    int shuliangs[];
    public int shuliang = 1;
    Updanjia updanjia;//加
    Delete delete;//删除
    public void setDelete(Delete delete) {
        this.delete = delete;
    }

    public void setUpdanjia(Updanjia updanjia) {
        this.updanjia = updanjia;
    }

    public void setList(List<BSD_KuaiSuBaoJia_CL_entity> list) {
        this.list = list;
//        shuliangs=new int[list.size()];

        if (list.size() > 0) {
            shuliangs = new int[list.size()];
            for (int i = 0; i < list.size(); i++) {
                shuliangs[i] = (int) list.get(i).getPeij_sl();

            }
        }


    }
    private Handler mHandler;

    public shuliangzongjia getShuliangzongjia() {
        return shuliangzongjia;
    }

    public void setShuliangzongjia(shuliangzongjia shuliangzongjia) {
        this.shuliangzongjia = shuliangzongjia;
    }

    shuliangzongjia shuliangzongjia;

    public BSD_xzcl_xiangqing_adp(Context context, Handler mHandler) {
        this.mHandler = mHandler;
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
        TextView bsd_xzcl_name, bsd_xzcl_shuliang, bsd_xzcl_danjia, bsd_xzcl_tuhao, bsd_xzcl_pinpai, bsd_xzcl_caozuo;
        RelativeLayout bsd_wxyy_cl_delet;
        ImageView bsd_scl_iv_jian, bsd_scl_iv_jia;
    }
    @Override
    public View getView(final int i, View contetview, ViewGroup viewGroup) {
        Holder holder = null;
        if (contetview == null) {
            holder = new Holder();
            contetview = layoutInflater.inflate(R.layout.bsd_ksbj_wxcl_xiangqing_item, null);
            holder.bsd_xzcl_name = (TextView) contetview.findViewById(R.id.bsd_xzcl_name);
            holder.bsd_xzcl_shuliang = (TextView) contetview.findViewById(R.id.bsd_xzcl_shuliang);
            holder.bsd_xzcl_danjia = (TextView) contetview.findViewById(R.id.bsd_xzcl_danjia);
            holder.bsd_xzcl_tuhao = (TextView) contetview.findViewById(R.id.bsd_xzcl_dw);
            holder.bsd_xzcl_pinpai = (TextView) contetview.findViewById(R.id.bsd_xzcl_je);
            holder.bsd_wxyy_cl_delet = (RelativeLayout) contetview.findViewById(R.id.bsd_wxyy_cl_delet);
            holder.bsd_scl_iv_jia = (ImageView) contetview.findViewById(R.id.bsd_scl_iv_jia);
            holder.bsd_scl_iv_jian = (ImageView) contetview.findViewById(R.id.bsd_scl_iv_jian);


            contetview.setTag(holder);
        } else {
            holder = (Holder) contetview.getTag();
        }


        holder.bsd_xzcl_name.setText(list.get(i).getPeij_mc());


        holder.bsd_xzcl_shuliang.setText("" + (int) list.get(i).getPeij_sl());
        final Holder finalHolder = holder;



        holder.bsd_xzcl_danjia.setText("" + list.get(i).getPeij_dj());

        holder.bsd_xzcl_tuhao.setText(list.get(i).getPeij_dw());

        zongjia = shuliangs[i] * list.get(i).getPeij_dj();
        holder.bsd_xzcl_pinpai.setText("" +list.get(i).getPeij_sl()*list.get(i).getPeij_dj());
//        holder.bsd_xzcl_caozuo.setText(list.get(i).get("caozuo"));


        return contetview;

    }

    public interface shuliangzongjia {
        public void onYesClick(int shuliang, double zongjia);
    }
    public interface Updanjia {
        public void onYesClick(int i, String name, double danjia);
    }
    //删除
    public interface Delete {
        public void onYesClick();
    }
}
