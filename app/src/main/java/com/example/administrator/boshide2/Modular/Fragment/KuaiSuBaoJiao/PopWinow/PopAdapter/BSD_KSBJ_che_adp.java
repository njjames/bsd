package com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.PopWinow.PopAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.boshide2.R;

import java.util.HashMap;
import java.util.List;

/**
 * 快速报价-项目选择-右边adp
 * Created by Administrator on 2017-4-19.
 */

public class BSD_KSBJ_che_adp extends BaseAdapter {
    private Context mContext;
    private LayoutInflater layoutInflater;
    List<HashMap<String, String>> list;

    XuanZe xuanZe;

    public void setXuanZe(XuanZe xuanZe) {
        this.xuanZe = xuanZe;
    }

    public void setList(List<HashMap<String, String>> list) {
        this.list = list;
    }

    public BSD_KSBJ_che_adp(Context context) {
        this.mContext = context;
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
        TextView bsd_ksbj_che_name, bsd_ksbj_che_xuanze;

    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.bsd_ksbj_che_item, null);
            holder.bsd_ksbj_che_name = (TextView) view.findViewById(R.id.bsd_ksbj_che_name);
            holder.bsd_ksbj_che_xuanze = (TextView) view.findViewById(R.id.bsd_ksbj_che_xuanze);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.bsd_ksbj_che_name.setText(list.get(i).get("name"));
        holder.bsd_ksbj_che_xuanze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuanZe.onYesClick(list.get(i).get("chex_bz"));
            }
        });

        return view;
    }


    public interface  XuanZe{
        public void onYesClick(String i);
    }

}
