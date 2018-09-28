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
    Context context;
    List<WXLS_XM_Bean> list;
    LayoutInflater inflater;
    public static HashMap<Integer, Boolean> isSelected;//选中状态的集合 防止复用 view造成选择混乱问题

    public WXLS_WXXM_Adapter(Context context, List<WXLS_XM_Bean> list, HashMap<Integer, Boolean> isSelected) {
        this.context = context;
        this.list = list;
        this.isSelected = isSelected;
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
            holder.wxxiangmu = (TextView) convertView.findViewById(R.id.tv_wxmingcheng);
            holder.jine = (TextView) convertView.findViewById(R.id.tv_jine);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        WXLS_XM_Bean bean = list.get(position);
        holder.wxxiangmu.setText(bean.getWxxiangmu());
        holder.jine.setText(bean.getJine());
//        holder.xuanzhong.setChecked(isSelected.get(position));
//        holder.xuanzhong.setTag(position);
//        holder.xuanzhong.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Integer tag = (Integer) v.getTag();
//                if (isSelected.get(tag)) {// 先判断isSelected中是否已经选中
//                    // 选中就置为false，即不选中
//                    isSelected.put(tag, false);
//                } else {
//                    // 选中
//                    isSelected.put(tag, true);
//                }
//                notifyDataSetChanged();
//            }
//        });
        return convertView;
    }

    class ViewHolder {
        public TextView wxxiangmu;
        public TextView jine;
    }
}
