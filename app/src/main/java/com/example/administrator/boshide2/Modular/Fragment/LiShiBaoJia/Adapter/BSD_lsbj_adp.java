package com.example.administrator.boshide2.Modular.Fragment.LiShiBaoJia.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.Entity.BSD_KuaiSuBaoJia_ety;
import com.example.administrator.boshide2.R;

import java.util.List;

/**
 * Created by Administrator on 2017-4-20.
 */

public class BSD_lsbj_adp extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<BSD_KuaiSuBaoJia_ety> list;
    private Photo photo;


    public BSD_lsbj_adp(Context context, List<BSD_KuaiSuBaoJia_ety> list) {
        this.list = list;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public final class Holder {
        TextView bsd_xsbj_rq;
        TextView bsd_kxbj_bzsj;
        TextView bsd_kxbj_cheno;
        TextView bsd_kxbj_je;
        TextView bsd_kxbj_phone;
        TextView bsd_kxbj_fwgw;
        LinearLayout bsd_xm6;
        ImageView iv_phone;
    }

    @Override
    public View getView(final int i, View contetview, ViewGroup viewGroup) {
        Holder holder = null;
        if (contetview == null) {
            holder = new Holder();
            contetview = layoutInflater.inflate(R.layout.bsd_lsbj_item, null);
            holder.bsd_xsbj_rq = (TextView) contetview.findViewById(R.id.bsd_xsbj_rq);
            holder.bsd_kxbj_bzsj = (TextView) contetview.findViewById(R.id.bsd_kxbj_bzsj);
            holder.bsd_kxbj_cheno = (TextView) contetview.findViewById(R.id.bsd_kxbj_cheno);
            holder.bsd_kxbj_fwgw = (TextView) contetview.findViewById(R.id.bsd_kxbj_fwgw);
            holder.bsd_kxbj_je = (TextView) contetview.findViewById(R.id.bsd_kxbj_je);
            holder.bsd_kxbj_phone = (TextView) contetview.findViewById(R.id.bsd_kxbj_phone);
            holder.bsd_xm6 = (LinearLayout) contetview.findViewById(R.id.bsd_xm6);
            holder.iv_phone = (ImageView) contetview.findViewById(R.id.iv_phone);
            contetview.setTag(holder);
        } else {
            holder = (Holder) contetview.getTag();
        }
        holder.bsd_xsbj_rq.setText(list.get(i).getList_jlrq());
        holder.bsd_kxbj_bzsj.setText(list.get(i).getKehu_mc());
        holder.bsd_kxbj_cheno.setText(list.get(i).getChe_no());
        holder.bsd_kxbj_fwgw.setText(list.get(i).getList_czy());
        holder.bsd_kxbj_je.setText("" + list.get(i).getList_hjje());
        holder.bsd_kxbj_phone.setText(list.get(i).getKehu_dh());
        holder.iv_phone.setOnClickListener(new View.OnClickListener() {
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

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }
}