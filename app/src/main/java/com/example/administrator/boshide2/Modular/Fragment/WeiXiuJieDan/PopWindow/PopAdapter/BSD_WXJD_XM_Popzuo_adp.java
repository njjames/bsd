package com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.PopWindow.PopAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.boshide2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-4-19.
 */

/**
 * 博士德popwind个全部项目选项
 */
public class BSD_WXJD_XM_Popzuo_adp extends BaseAdapter {
    private Context mContext;
    private LayoutInflater layoutInflater;
    private List<String> list = new ArrayList<>();

    public BSD_WXJD_XM_Popzuo_adp(Context context, List<String> list) {
        this.mContext = context;
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

    class ViewHolder {
        public TextView mTextView;

    }

    @Override
    public View getView(int pos, View contetview, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (contetview == null) {
            holder = new ViewHolder();
            contetview = layoutInflater.inflate(R.layout.bsd_wxjd_xm_popzuo_item, null);
            holder.mTextView = (TextView) contetview.findViewById(R.id.bsd_pop_item_tv);
            contetview.setTag(holder);
        } else {
            holder = (ViewHolder) contetview.getTag();
        }
        holder.mTextView.setText(list.get(pos));
        return contetview;
    }
}
