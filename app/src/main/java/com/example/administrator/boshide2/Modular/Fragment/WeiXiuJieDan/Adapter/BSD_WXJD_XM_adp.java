package com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_XM_Entity;
import com.example.administrator.boshide2.Modular.View.diaog.TooPromptdiaog;
import com.example.administrator.boshide2.R;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Administrator on 2017-4-21.
 */

public class BSD_WXJD_XM_adp extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<BSD_WeiXiuJieDan_XM_Entity> list;
    private OnOperateItemListener onOperateItemListener;

    public BSD_WXJD_XM_adp(Context context, List<BSD_WeiXiuJieDan_XM_Entity> list) {
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
        return i;
    }

    public final class Holder {
        TextView bsd_xzcl_no;
        TextView bsd_xzcl_name;
        TextView bsd_xzcl_gs;
        TextView bsd_xzcl_dj;
        TextView bsd_xzcl_je;
        TextView bsd_xzcl_zt;
        ImageView iv_delete;
    }

    @Override
    public View getView(final int i, View contetview, ViewGroup viewGroup) {
        Holder holder = null;
        if (contetview == null) {
            holder = new Holder();
            contetview = layoutInflater.inflate(R.layout.bsd_wxjd_xzxm_item, null);
            holder.bsd_xzcl_no = (TextView) contetview.findViewById(R.id.bsd_xzcl_no);
            holder.bsd_xzcl_name = (TextView) contetview.findViewById(R.id.bsd_xzcl_name);
            holder.bsd_xzcl_gs = (TextView) contetview.findViewById(R.id.bsd_xzcl_gs);
            holder.bsd_xzcl_dj = (TextView) contetview.findViewById(R.id.bsd_xzcl_danjia);
            holder.bsd_xzcl_je = (TextView) contetview.findViewById(R.id.bsd_xzcl_je);
            holder.bsd_xzcl_zt = (TextView) contetview.findViewById(R.id.bsd_xzcl_zt);
            holder.iv_delete = (ImageView) contetview.findViewById(R.id.iv_delete);
            contetview.setTag(holder);
        } else {
            holder = (Holder) contetview.getTag();
        }
        holder.bsd_xzcl_no.setText(list.get(i).getWxxm_no());
        holder.bsd_xzcl_name.setText(list.get(i).getWxxm_mc());
        holder.bsd_xzcl_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOperateItemListener != null) {
                    onOperateItemListener.onUpdateWxxmMc(i);
                }
            }
        });
        //工时
        holder.bsd_xzcl_gs.setText(""+list.get(i).getWxxm_gs());
        holder.bsd_xzcl_gs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        if (list.get(i).getWxxm_gs() == 0) {
            list.get(i).setWxxm_gs(1.0);
        }
        DecimalFormat df=new DecimalFormat("#.##");
        String gs = df.format(list.get(i).getWxxm_je() / list.get(i).getWxxm_gs());
        holder.bsd_xzcl_dj.setText(gs);
        //金额
        holder.bsd_xzcl_je.setText("" + list.get(i).getWxxm_je());
        holder.bsd_xzcl_je.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onOperateItemListener != null) {
                    onOperateItemListener.onUpdateYgsf(i);
                }
            }
        });
        holder.bsd_xzcl_zt.setText(list.get(i).getWxxm_zt());
        //删除
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onOperateItemListener != null) {
                    onOperateItemListener.onDelete(i);
                }
            }
        });
        return contetview;
    }

    public interface OnOperateItemListener {
        void onPaiGong(String wxxmNo);

        void onDelete(int position);

        void onUpdateYgsf(int position);

        void onUpdateWxxmMc(int position);
    }

    public void setOnOperateItemListener(OnOperateItemListener onOperateItemListener) {
        this.onOperateItemListener = onOperateItemListener;
    }

}
