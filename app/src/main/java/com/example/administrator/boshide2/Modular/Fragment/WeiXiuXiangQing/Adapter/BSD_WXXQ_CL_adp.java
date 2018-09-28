package com.example.administrator.boshide2.Modular.Fragment.WeiXiuXiangQing.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Fragment.WeiXiuXiangQing.Entity.BSD_LSWX_WXCL_Entity;
import com.example.administrator.boshide2.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017-4-21.
 *
 * 传参work_no维修单号。
 * pageNumber 页数
 */

public class BSD_WXXQ_CL_adp extends BaseAdapter {
    LayoutInflater layoutInflater;
    Context context;
    List<BSD_LSWX_WXCL_Entity> list;

    public BSD_WXXQ_CL_adp(Context context, List<BSD_LSWX_WXCL_Entity> list) {
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
        TextView bsd_xzcl_name, bsd_xzcl_shuliang, bsd_xzcl_danjia, bsd_xzcl_tuhao, bsd_xzcl_pinpai, bsd_xzcl_caozuo;
    }

    @Override
    public View getView(int i, View contetview, ViewGroup viewGroup) {
        Holder holder = null;
        if (contetview == null) {
            holder = new Holder();
            contetview = layoutInflater.inflate(R.layout.bsd_zcdd_xzcl_item, null);
            holder.bsd_xzcl_name = (TextView) contetview.findViewById(R.id.bsd_xzcl_name);
            holder.bsd_xzcl_shuliang = (TextView) contetview.findViewById(R.id.bsd_xzcl_shuliang);
            holder.bsd_xzcl_danjia = (TextView) contetview.findViewById(R.id.bsd_xzcl_danjia);
            holder.bsd_xzcl_tuhao = (TextView) contetview.findViewById(R.id.bsd_xzcl_tuhao);
            holder.bsd_xzcl_pinpai = (TextView) contetview.findViewById(R.id.bsd_xzcl_pinpai);
            holder.bsd_xzcl_caozuo = (TextView) contetview.findViewById(R.id.bsd_xzcl_caozuo);
            contetview.setTag(holder);
        } else {
            holder = (Holder) contetview.getTag();
        }

final BSD_LSWX_WXCL_Entity lswx_wxcl_entity=list.get(i);
        holder.bsd_xzcl_name.setText(lswx_wxcl_entity.getPeij_mc());
        holder.bsd_xzcl_shuliang.setText(lswx_wxcl_entity.getPeij_sl()+"");
        holder.bsd_xzcl_danjia.setText(lswx_wxcl_entity.getPeij_dj()+"");
        holder.bsd_xzcl_tuhao.setText(lswx_wxcl_entity.getPeij_th());//图号，现在取得是账号
        holder.bsd_xzcl_pinpai.setText(lswx_wxcl_entity.getPeij_pp());
        holder.bsd_xzcl_caozuo.setText(lswx_wxcl_entity.getPeij_je());
        Log.i("cjn","查看数据itm"+lswx_wxcl_entity.getPeij_mc());


        return contetview;

    }
}
