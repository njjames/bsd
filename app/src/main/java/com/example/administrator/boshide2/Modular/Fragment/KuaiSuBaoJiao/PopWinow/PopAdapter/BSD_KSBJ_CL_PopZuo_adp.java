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

public class BSD_KSBJ_CL_PopZuo_adp extends BaseAdapter {
    private Context mContext;
    private LayoutInflater layoutInflater;
    List<HashMap<String, String>> list;


    public BSD_KSBJ_CL_PopZuo_adp(Context context, List<HashMap<String, String>> list) {
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
        TextView bsd_xsbj_name,bsd_kxbj_bzsj,bsd_kxbj_gsdj,bsd_kxbj_je,bsd_kxbj_cz;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if (view == null) {
            holder=new ViewHolder();
            view=layoutInflater.inflate(R.layout.bsd_ksbj_clpop_zuo_item,null);
            holder.bsd_xsbj_name= (TextView) view.findViewById(R.id.bsd_xsbj_name1);
            holder.bsd_kxbj_bzsj= (TextView) view.findViewById(R.id.bsd_kxbj_bzsj1);
            holder.bsd_kxbj_gsdj= (TextView) view.findViewById(R.id.bsd_kxbj_gsdj1);
            holder.bsd_kxbj_je= (TextView) view.findViewById(R.id.bsd_kxbj_je1);
            holder.bsd_kxbj_cz= (TextView) view.findViewById(R.id.bsd_kxbj_cz1);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.bsd_xsbj_name.setText(list.get(i).get("name"));
        holder.bsd_kxbj_bzsj.setText(list.get(i).get("time"));
        holder.bsd_kxbj_gsdj.setText(list.get(i).get("timemany"));
        holder.bsd_kxbj_je.setText(list.get(i).get("jinqian"));
//        holder.bsd_kxbj_cz.setText(mLists.get(i).get("caozuo"));


        return view;
    }
}
