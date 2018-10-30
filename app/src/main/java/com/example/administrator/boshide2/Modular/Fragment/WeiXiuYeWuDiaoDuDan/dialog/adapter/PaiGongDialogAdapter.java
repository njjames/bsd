package com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.dialog.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.boshide2.R;
import com.lidong.photopicker.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-5-21.
 */

public class PaiGongDialogAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String, String>> list;
    private List<String> chooseLists = new ArrayList<>();

    public PaiGongDialogAdapter(Context context, List<Map<String, String>> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        Holder holder=null;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(context).inflate(R.layout.dialog_paigong_item, null);
            holder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_dept= (TextView) convertView.findViewById(R.id.tv_dept);
            holder.iv_paigong= (ImageView) convertView.findViewById(R.id.iv_paigong);
            convertView.setTag(holder);
        }else {
            holder = (Holder) convertView.getTag();
        }
        holder.tv_name.setText(list.get(i).get("reny_xm"));
        holder.tv_dept.setText(list.get(i).get("dept_mc"));
        final Holder finalHolder = holder;
        holder.iv_paigong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chooseLists.contains(list.get(i).get("reny_dm"))) {
                    chooseLists.remove(list.get(i).get("reny_dm"));
                    finalHolder.iv_paigong.setImageResource(R.drawable.ic_paigong_fail);
                } else {
                    chooseLists.add(list.get(i).get("reny_dm"));
                    finalHolder.iv_paigong.setImageResource(R.drawable.ic_paigong_sucesss);
                }
            }
        });
        return convertView;
    }

    public final class Holder {
        TextView tv_name;
        TextView tv_dept;
        ImageView iv_paigong;
    }

    public interface  PaiGong{
        void onYesClick(String renyDm);
    }

    public List<String> getChooseLists() {
        return chooseLists;
    }
}
