package com.example.administrator.boshide2.Modular.Fragment.LiShiJieDan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_Entity;
import com.example.administrator.boshide2.R;

import java.util.List;

/**
 * Created by Administrator on 2017-4-20.
 */

public class BSD_lsjd_adp extends BaseAdapter {
    LayoutInflater layoutInflater;
    Context context;
    List<BSD_WeiXiuJieDan_Entity> list;
    Photo photo;

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public BSD_lsjd_adp(Context context, List<BSD_WeiXiuJieDan_Entity> list) {
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
        TextView bsd_xsbj_name,bsd_kxbj_bzsj,bsd_kxbj_gsdj,bsd_kxbj_je,bsd_kxbj_qian,bsd_kxbj_cz;
    RelativeLayout bsd_xm6;
    }

    @Override
    public View getView(int i, View contetview, ViewGroup viewGroup) {
        Holder holder=null;
        if (contetview == null) {
            holder = new Holder();

            contetview=layoutInflater.inflate(R.layout.bsd_lsjd_item,null);

            holder.bsd_xsbj_name= (TextView) contetview.findViewById(R.id.bsd_xsbj_rq);
            holder.bsd_kxbj_bzsj= (TextView) contetview.findViewById(R.id.bsd_kxbj_bzsj);
            holder.bsd_kxbj_gsdj= (TextView) contetview.findViewById(R.id.bsd_kxbj_gsdj);
            holder.bsd_kxbj_je= (TextView) contetview.findViewById(R.id.bsd_kxbj_fwgw);
            holder.bsd_kxbj_cz= (TextView) contetview.findViewById(R.id.bsd_kxbj_je);
            holder.bsd_kxbj_qian= (TextView) contetview.findViewById(R.id.bsd_kxbj_phone);
            holder.bsd_xm6= (RelativeLayout) contetview.findViewById(R.id.bsd_xm6);
            contetview.setTag(holder);
        } else {
            holder = (Holder) contetview.getTag();
        }
        final BSD_WeiXiuJieDan_Entity item=list.get(i);
        holder.bsd_xsbj_name.setText(item.getXche_jdrq());
        holder.bsd_kxbj_bzsj.setText(item.getKehu_mc());
        holder.bsd_kxbj_gsdj.setText(item.getChe_no());
        holder.bsd_kxbj_je.setText(item.getXche_pgcz());
        holder.bsd_kxbj_cz.setText(item.getXche_wxjd());
        holder.bsd_kxbj_qian.setText(item.getKehu_dh());
        holder.bsd_xm6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photo.Ddh(item.getKehu_dh());

            }
        });
        return contetview;
    }

    public interface Photo {
        public void Ddh(String dianhua);
    }
}