package com.example.administrator.boshide2.Modular.Fragment.LiShiJieDan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_Entity;
import com.example.administrator.boshide2.R;

import java.util.List;

/**
 * Created by Administrator on 2017-4-20.
 */

public class BSD_lsjd_adp extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<BSD_WeiXiuJieDan_Entity> list;
    private Photo photo;

    public BSD_lsjd_adp(Context context, List<BSD_WeiXiuJieDan_Entity> list) {
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
        TextView bsd_lsjd_workno;
        TextView bsd_lsjd_jdrq;
        TextView bsd_lsjd_kehumc;
        TextView bsd_lsjd_cheno;
        TextView bsd_lsjd_fwgw;
        TextView bsd_lsjd_phone;
        TextView bsd_lsjd_wxjd;
        ImageView iv_phone;
    }

    @Override
    public View getView(int i, View contetview, ViewGroup viewGroup) {
        Holder holder = null;
        if (contetview == null) {
            holder = new Holder();
            contetview = layoutInflater.inflate(R.layout.bsd_lsjd_item, null);
            holder.bsd_lsjd_workno = (TextView) contetview.findViewById(R.id.bsd_lsjd_workno);
            holder.bsd_lsjd_jdrq = (TextView) contetview.findViewById(R.id.bsd_lsjd_jdrq);
            holder.bsd_lsjd_kehumc = (TextView) contetview.findViewById(R.id.bsd_lsjd_kehumc);
            holder.bsd_lsjd_cheno = (TextView) contetview.findViewById(R.id.bsd_lsjd_cheno);
            holder.bsd_lsjd_fwgw = (TextView) contetview.findViewById(R.id.bsd_lsjd_fwgw);
            holder.bsd_lsjd_wxjd = (TextView) contetview.findViewById(R.id.bsd_lsjd_wxjd);
            holder.bsd_lsjd_phone = (TextView) contetview.findViewById(R.id.bsd_lsjd_phone);
            holder.iv_phone = (ImageView) contetview.findViewById(R.id.iv_phone);
            contetview.setTag(holder);
        } else {
            holder = (Holder) contetview.getTag();
        }
        final BSD_WeiXiuJieDan_Entity item = list.get(i);
        holder.bsd_lsjd_workno.setText(item.getWork_no());
        holder.bsd_lsjd_jdrq.setText(item.getXche_jdrq());
        holder.bsd_lsjd_kehumc.setText(item.getKehu_mc());
        holder.bsd_lsjd_cheno.setText(item.getChe_no());
        holder.bsd_lsjd_fwgw.setText(item.getXche_pgcz());
        holder.bsd_lsjd_wxjd.setText(item.getXche_wxjd());
        holder.bsd_lsjd_phone.setText(item.getKehu_dh());
        holder.iv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photo.Ddh(item.getKehu_dh());

            }
        });
        return contetview;
    }

    public interface Photo {
        void Ddh(String dianhua);
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }
}