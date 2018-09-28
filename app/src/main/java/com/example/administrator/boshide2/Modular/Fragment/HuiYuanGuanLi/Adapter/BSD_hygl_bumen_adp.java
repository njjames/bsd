package com.example.administrator.boshide2.Modular.Fragment.HuiYuanGuanLi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Fragment.ZaiChangDiaoDU.PopWindow.Adapter.BSD_ZCDD_CL_adp;
import com.example.administrator.boshide2.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-5-9.
 * 会员管理---添加---部门下拉适配器
 */

public class BSD_hygl_bumen_adp extends BaseAdapter{
    LayoutInflater inflater;
    Context context;
    List<Map<String, String>> list;

    public BSD_hygl_bumen_adp(Context context, List<Map<String, String>> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
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
        return i;
    }
    class Holder {
        TextView tv_hygl_bumen_name;}
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Holder holder = null;
        if (view == null) {
            holder = new Holder();
            view = inflater.inflate(R.layout.bsd_hygl_bumen_item, null);
            holder.tv_hygl_bumen_name = (TextView) view.findViewById(R.id.tv_hygl_bumen_name);

            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }


        holder.tv_hygl_bumen_name.setText(list.get(i).get("name"));



        return view;

    }
}
