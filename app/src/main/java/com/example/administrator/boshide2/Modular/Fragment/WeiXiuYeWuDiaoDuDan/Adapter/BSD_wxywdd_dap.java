package com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Entity.WorkPgGz_Entity;
import com.example.administrator.boshide2.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-4-27.
 */

public class BSD_wxywdd_dap extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<WorkPgGz_Entity> list;
    private OnOperateItemListener onOperateItemListener;

    public BSD_wxywdd_dap(Context context, List<WorkPgGz_Entity> list) {
        layoutInflater = LayoutInflater.from(context);
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
        TextView bsd_xsbj_name;
        TextView bsd_xsbj_gongshi;
        TextView bsd_xsbj_je;
        ImageView iv_delete;
    }

    @Override
    public View getView(final int i, View contetview, ViewGroup viewGroup) {
        Holder holder = null;
        if (contetview == null) {
            holder = new Holder();
            contetview = layoutInflater.inflate(R.layout.bsd_wxywdd_adp_item, null);
            holder.bsd_xsbj_name = (TextView) contetview.findViewById(R.id.bsd_xsbj_name);
            holder.bsd_xsbj_gongshi = (TextView) contetview.findViewById(R.id.bsd_xsbj_gongshi);
            holder.bsd_xsbj_je = (TextView) contetview.findViewById(R.id.bsd_xsbj_je);
            holder.iv_delete = (ImageView) contetview.findViewById(R.id.iv_delete);
            contetview.setTag(holder);
        } else {
            holder = (Holder) contetview.getTag();
        }
        holder.bsd_xsbj_name.setText(list.get(i).getReny_mc());
        holder.bsd_xsbj_gongshi.setText("" + list.get(i).getPaig_khgs());
        holder.bsd_xsbj_je.setText("" + list.get(i).getPaig_khje());
        holder.bsd_xsbj_je.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onOperateItemListener != null) {
                    onOperateItemListener.onUpdateJE(i);
                }
            }
        });

        holder.bsd_xsbj_gongshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onOperateItemListener != null) {
                    onOperateItemListener.onUpdateGS(i);
                }
            }
        });

        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onOperateItemListener != null) {
                    onOperateItemListener.onDelete(list.get(i).getReco_no(), i);
                }
            }
        });
        return contetview;
    }

    public interface OnOperateItemListener {
        void onDelete(int reco_no, int position);

        void onUpdateGS(int position);

        void onUpdateJE(int position);
    }

    public void setOnOperateItemListener(OnOperateItemListener onOperateItemListener) {
        this.onOperateItemListener = onOperateItemListener;
    }
}
