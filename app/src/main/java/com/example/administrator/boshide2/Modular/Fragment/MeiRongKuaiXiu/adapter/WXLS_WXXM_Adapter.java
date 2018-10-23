package com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.Entity.WXLS_XM_Bean;
import com.example.administrator.boshide2.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator n 2017-09-15.
 */

public class WXLS_WXXM_Adapter extends BaseAdapter {
    private List<WXLS_XM_Bean> list;
    private LayoutInflater inflater;

    public WXLS_WXXM_Adapter(Context context, List<WXLS_XM_Bean> list) {
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
            convertView = inflater.inflate(R.layout.layout_item_fr_wxls_xm, null);
            holder.tv_wxxm_name = (TextView) convertView.findViewById(R.id.tv_wxxm_name);
            holder.tv_wxxm_je = (TextView) convertView.findViewById(R.id.tv_wxxm_je);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        WXLS_XM_Bean bean = list.get(position);
        holder.tv_wxxm_name.setText(bean.getWxxiangmu());
        holder.tv_wxxm_je.setText(bean.getJine());
        return convertView;
    }

    public final class ViewHolder {
        TextView tv_wxxm_name;
        TextView tv_wxxm_je;
    }
}
