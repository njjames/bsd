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
    Context context;
    List<WXLS_YL_Bean> list;
    LayoutInflater inflater;
    public static HashMap<Integer, Boolean> isChecked;

    public WXLS_WXYL_Adapter(Context context, List<WXLS_YL_Bean> list, HashMap<Integer, Boolean> isChecked) {
        this.context = context;
        this.list = list;
        this.isChecked = isChecked;
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
            holder.peijian = (TextView) convertView.findViewById(R.id.tv_peijian);
            holder.guige = (TextView) convertView.findViewById(R.id.tv_guige);
            holder.shuliang = (TextView) convertView.findViewById(R.id.tv_shuliang);
            holder.danjia = (TextView) convertView.findViewById(R.id.tv_danjia);
            holder.jine = (TextView) convertView.findViewById(R.id.tv_jine);
//            holder.checkBox = (CheckBox) convertView.findViewById(R.id.cb_xuanzhong_yl);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        WXLS_YL_Bean bean = list.get(position);
        holder.peijian.setText(bean.getPeijian());
        holder.guige.setText(bean.getGuige());
        holder.shuliang.setText(bean.getShuliang());
        holder.danjia.setText(bean.getDanjia());
        holder.jine.setText(bean.getJine());
//        holder.checkBox.setChecked(isChecked.get(position));
//        holder.checkBox.setTag(position);
//        holder.checkBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Integer tag = (Integer) v.getTag();
//                if (isChecked.get(tag)) {// 先判断isSelected中是否已经选中
//                    // 选中就置为false，即不选中
//                    isChecked.put(tag, false);
//                } else {
//                    // 选中
//                    isChecked.put(tag, true);
//                }
//                notifyDataSetChanged();
//            }
//        });
        return convertView;
    }

    class ViewHolder {
        public TextView peijian;
        public TextView guige;
        public TextView shuliang;
        public TextView danjia;
        public TextView jine;
//        public CheckBox checkBox;
    }
}
