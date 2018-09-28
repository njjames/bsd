package com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.PopWinow.PopAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.boshide2.R;

import java.util.HashMap;
import java.util.List;

/**
 * 快速报价--车品牌-弹出框-适配器
 * Created by Administrator on 2017-4-27.
 */

public class BSD_KSBJ_PinPai_adp extends BaseAdapter {
    private Context mContext;
    private LayoutInflater layoutInflater;
    List<HashMap<String, String>> list;


    public BSD_KSBJ_PinPai_adp(Context context, List<HashMap<String, String>> list) {
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
        return i;
    }

    class Holder {
        TextView tv_chepai_name;
        ImageView img_chepai1;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        BSD_KSBJ_PinPai_adp.Holder holder = null;
        if (view == null) {
            holder = new BSD_KSBJ_PinPai_adp.Holder();
            view = layoutInflater.inflate(R.layout.bsd_ksbj_pinpai_item, null);
            holder.tv_chepai_name = (TextView) view.findViewById(R.id.tv_chepai_name);
            holder.img_chepai1 = (ImageView) view.findViewById(R.id.img_chepai1);
            view.setTag(holder);
        } else {
            holder = (BSD_KSBJ_PinPai_adp.Holder) view.getTag();
            holder.tv_chepai_name.setText(list.get(i).get(""));

        }
        return view;
    }
}