package com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.Adapter.BSD_wxxm_adp;
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.Entity.BSD_WXCL_CK_Entity;
import com.example.administrator.boshide2.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017-11-13.
 */

public class BSD_wxcl_kc_apater extends BaseAdapter {
   private  Context  context;
   private   ArrayList<BSD_WXCL_CK_Entity>  mList;

  public  BSD_wxcl_kc_apater(Context  context, ArrayList  mList){
      this.context=context;
      this.mList=mList;
  }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=null;
        if (convertView == null) {
            holder = new BSD_wxcl_kc_apater.Holder();
            convertView = LayoutInflater.from(context).inflate(R.layout.bsd_wxcl_kc_item,null);
            holder.bsd_tv_kc_ckmc= (TextView) convertView.findViewById(R.id.bsd_tv_kc_ckmc);
            holder.bsd_tv_kc_dqkc= (TextView) convertView.findViewById(R.id.bsd_tv_kc_dqkc);
            holder.bsd_tv_kc_pjdj= (TextView) convertView.findViewById(R.id.bsd_tv_kc_pjdj);
            holder.bsd_tv_kc_je= (TextView) convertView.findViewById(R.id.bsd_tv_kc_je);
            holder.bsd_tv_kc_kcsx= (TextView) convertView.findViewById(R.id.bsd_tv_kc_kcsx);
            holder.bsd_tv_kc_kcxx= (TextView) convertView.findViewById(R.id.bsd_tv_kc_kcxx);
            holder.bsd_tv_kc_kw= (TextView) convertView.findViewById(R.id.bsd_tv_kc_kw);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.bsd_tv_kc_ckmc.setText(mList.get(position).getCkMc());
        holder.bsd_tv_kc_dqkc.setText(mList.get(position).getCurrentKc());
        holder.bsd_tv_kc_pjdj.setText(mList.get(position).getPjDj());
        holder.bsd_tv_kc_je.setText(mList.get(position).getJe());
        holder.bsd_tv_kc_kcsx.setText(mList.get(position).getKcSum());
        holder.bsd_tv_kc_kcxx.setText(mList.get(position).getKcMin());
        holder.bsd_tv_kc_kw.setText(mList.get(position).getKw());

        return convertView;
    }

    class Holder {
        TextView bsd_tv_kc_ckmc,bsd_tv_kc_dqkc,bsd_tv_kc_pjdj,bsd_tv_kc_je,
                bsd_tv_kc_kcsx,bsd_tv_kc_kcxx,bsd_tv_kc_kw;

    }


}
