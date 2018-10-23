package com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.Entity.WXLS_YL_Bean;
import com.example.administrator.boshide2.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017-09-15.
 */

public class WXLS_WXYL_Adapter extends BaseAdapter {
    private List<WXLS_YL_Bean> list;
    private LayoutInflater inflater;

    public WXLS_WXYL_Adapter(Context context, List<WXLS_YL_Bean> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_item_fr_wxls_yl, null);
            holder.tv_wxcl_name = (TextView) convertView.findViewById(R.id.tv_wxcl_name);
            holder.tv_wxcl_th = (TextView) convertView.findViewById(R.id.tv_wxcl_th);
            holder.tv_wxcl_sl = (TextView) convertView.findViewById(R.id.tv_wxcl_sl);
            holder.tv_wxcl_dj = (TextView) convertView.findViewById(R.id.tv_wxcl_dj);
            holder.tv_wxcl_je = (TextView) convertView.findViewById(R.id.tv_wxcl_je);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        WXLS_YL_Bean bean = list.get(position);
        holder.tv_wxcl_name.setText(bean.getPeijian());
        holder.tv_wxcl_th.setText(bean.getGuige());
        holder.tv_wxcl_sl.setText(bean.getShuliang());
        holder.tv_wxcl_dj.setText(bean.getDanjia());
        holder.tv_wxcl_je.setText(bean.getJine());
        return convertView;
    }

    public final class ViewHolder {
        TextView tv_wxcl_name;
        TextView tv_wxcl_th;
        TextView tv_wxcl_sl;
        TextView tv_wxcl_dj;
        TextView tv_wxcl_je;
    }
}
