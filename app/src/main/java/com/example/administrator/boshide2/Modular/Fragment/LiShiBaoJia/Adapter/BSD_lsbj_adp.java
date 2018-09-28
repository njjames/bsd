package com.example.administrator.boshide2.Modular.Fragment.LiShiBaoJia.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.Entity.BSD_KuaiSuBaoJia_ety;
import com.example.administrator.boshide2.R;

import java.util.List;

/**
 * Created by Administrator on 2017-4-20.
 */

public class BSD_lsbj_adp extends BaseAdapter {
    LayoutInflater layoutInflater;
    Context context;
    List<BSD_KuaiSuBaoJia_ety> list;
    Photo photo;

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public  BSD_lsbj_adp(Context context, List< BSD_KuaiSuBaoJia_ety> list) {
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
        TextView bsd_xsbj_name,bsd_kxbj_bzsj,bsd_kxbj_cheno,bsd_kxbj_je,bsd_kxbj_qian,bsd_kxbj_cz;
        RelativeLayout bsd_xm6;

   }

    @Override
    public View getView(final int i, View contetview, ViewGroup viewGroup) {
        Holder holder=null;
        if (contetview == null) {
            holder = new Holder();

            contetview=layoutInflater.inflate(R.layout.bsd_lsbj_item,null);

            holder.bsd_xsbj_name= (TextView) contetview.findViewById(R.id.bsd_xsbj_name);
            holder.bsd_kxbj_bzsj= (TextView) contetview.findViewById(R.id.bsd_kxbj_bzsj);
            holder.bsd_kxbj_cheno= (TextView) contetview.findViewById(R.id.bsd_kxbj_cheno);
            holder.bsd_kxbj_je= (TextView) contetview.findViewById(R.id.bsd_kxbj_je);
            holder.bsd_kxbj_cz= (TextView) contetview.findViewById(R.id.bsd_kxbj_cz);
            holder.bsd_kxbj_qian= (TextView) contetview.findViewById(R.id.bsd_kxbj_qian);
            holder.bsd_xm6= (RelativeLayout) contetview.findViewById(R.id.bsd_xm6);
            contetview.setTag(holder);
        } else {
            holder = (Holder) contetview.getTag();
        }
        holder.bsd_xsbj_name.setText(list.get(i).getList_jlrq());
        holder.bsd_kxbj_bzsj.setText(list.get(i).getKehu_mc());
        holder.bsd_kxbj_cheno.setText(list.get(i).getChe_no());
        holder.bsd_kxbj_je.setText(list.get(i).getList_czy());
        holder.bsd_kxbj_cz.setText(""+list.get(i).getList_hjje());
        holder.bsd_kxbj_qian.setText(list.get(i).getKehu_dh());
        holder.bsd_xm6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photo.Ddh(list.get(i).getKehu_dh());
            }
        });

        return contetview;
    }

    public interface Photo {
        public void Ddh(String dianhua);
    }
}