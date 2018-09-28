package com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.boshide2.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-5-2.
 * 快速报价品牌适配器
 */

public class BSD_KSBJ_PP_adp extends BaseAdapter {
    LayoutInflater layoutInflater;
    Context context;
    List<Map<String, String>> list;
    public BSD_KSBJ_PP_adp(Context context, List<Map<String, String>> list) {
        this.context = context;
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
    class Holder {
        TextView tv_chepai_name;
        ImageView img_chepai1;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       Holder holder=null;
        if (view == null) {
            holder = new Holder();
            view = layoutInflater.inflate(R.layout.bsd_ksbj_pinpai_item,null);
            holder.tv_chepai_name= (TextView) view.findViewById(R.id.tv_chepai_name);
            holder.img_chepai1= (ImageView) view.findViewById(R.id.img_chepai1);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        holder.tv_chepai_name.setText(list.get(i).get("chepainame"));

        return view;
    }
}
