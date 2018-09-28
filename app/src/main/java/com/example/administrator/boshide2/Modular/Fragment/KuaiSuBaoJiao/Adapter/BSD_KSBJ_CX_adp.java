package com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.Entity.BSD_KSBJ_CX_ety;
import com.example.administrator.boshide2.R;

import java.util.List;

/**
 * Created by Administrator on 2017-5-3.
 * 车系适配器
 */

public class BSD_KSBJ_CX_adp extends BaseAdapter {


    LayoutInflater layoutInflater;
    Context context;
    List<BSD_KSBJ_CX_ety> list;

    public BSD_KSBJ_CX_adp(Context context, List<BSD_KSBJ_CX_ety> list) {
        this.context = context;
        this.list = list;
        this.layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {

        return (list == null)? 0:list.size();
    }

    @Override
    public Object getItem(int pos) {
        return pos;
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup arg2) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.bsd_ksbj_cx_item, null);
            viewHolder = new ViewHolder();
            viewHolder.mTextView = (TextView) convertView.findViewById(R.id.tv_cx_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder = (ViewHolder) convertView.getTag();
        final BSD_KSBJ_CX_ety cx_ety=list.get(pos);
        viewHolder.mTextView.setText(list.get(pos).getChex_mc());

        return convertView;
    }

    public static class ViewHolder {
        public TextView mTextView;
    }
}
