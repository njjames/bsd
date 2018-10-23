package com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.fagmt.fagmt_adp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_CL_Entity;
import com.example.administrator.boshide2.R;

import java.util.List;


/**
 * Created by Administrator on 2017-4-20.
 */

public class BSD_wxywwd_wxcl_adp extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<BSD_WeiXiuJieDan_CL_Entity> list;
    private OnOperateItemListener onOperateItemListener;

    public BSD_wxywwd_wxcl_adp(Context context, List<BSD_WeiXiuJieDan_CL_Entity> list) {
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
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
        return 0;
    }

    public final class Holder {
        TextView bsd_xsbj_name;
        TextView bsd_kxbj_sl;
        TextView bsd_kxbj_dj;
        TextView bsd_kxbj_dw;
        TextView bsd_kxbj_je;
        ImageView iv_stock;
        ImageView iv_delete;

    }

    @Override
    public View getView(final int i, View contetview, ViewGroup viewGroup) {
        Holder holder=null;
        if (contetview == null) {
            holder = new Holder();
            contetview=layoutInflater.inflate(R.layout.bsd_wxywdd_wxcl_item,null);
            holder.bsd_xsbj_name= (TextView) contetview.findViewById(R.id.bsd_xsbj_name);
            holder.bsd_kxbj_sl= (TextView) contetview.findViewById(R.id.bsd_kxbj_sl);
            holder.bsd_kxbj_dj= (TextView) contetview.findViewById(R.id.bsd_kxbj_dj);
            holder.bsd_kxbj_dw= (TextView) contetview.findViewById(R.id.bsd_kxbj_dw);
            holder.bsd_kxbj_je= (TextView) contetview.findViewById(R.id.bsd_kxbj_je);
            holder.iv_delete= (ImageView) contetview.findViewById(R.id.iv_delete);
            holder.iv_stock= (ImageView) contetview.findViewById(R.id.iv_stock);
            contetview.setTag(holder);
        } else {
            holder = (Holder) contetview.getTag();
        }
        holder.bsd_xsbj_name.setText(list.get(i).getPeij_mc());
        holder.bsd_kxbj_sl.setText("" + list.get(i).getPeij_sl());
        holder.bsd_kxbj_dj.setText("" + list.get(i).getPeij_dj());
        holder.bsd_kxbj_dw.setText(list.get(i).getPeij_dw());
        holder.bsd_kxbj_je.setText("" + list.get(i).getPeij_sl() * list.get(i).getPeij_dj());

        holder.bsd_kxbj_sl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onOperateItemListener != null) {
                    onOperateItemListener.onUpdateSl(list.get(i).getPeij_no(), list.get(i).getPeij_mc(), list.get(i).getPeij_sl(), i);
                }
            }
        });
        holder.bsd_kxbj_dj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onOperateItemListener != null) {
                    onOperateItemListener.onUpdateYDj(list.get(i).getPeij_no(), list.get(i).getPeij_mc(), list.get(i).getPeij_ydj(), i);
                }
            }
        });
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onOperateItemListener != null) {
                    onOperateItemListener.onDelete(list.get(i).getPeij_no(), i);
                }
            }
        });
        holder.iv_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOperateItemListener != null) {
                    onOperateItemListener.onSearchStock(list.get(i).getPeij_no());
                }
            }
        });
        return contetview;
    }

    public interface OnOperateItemListener {
        void onDelete(String peij_no, int position);

        void onSearchStock(String peij_no);

        void onUpdateSl(String peij_no, String peij_mc, double peij_sl, int position);

        void onUpdateYDj(String peij_no, String peij_mc, double peij_ydj, int position);
    }

    public void setOnOperateItemListener(OnOperateItemListener onOperateItemListener) {
        this.onOperateItemListener = onOperateItemListener;
    }
}