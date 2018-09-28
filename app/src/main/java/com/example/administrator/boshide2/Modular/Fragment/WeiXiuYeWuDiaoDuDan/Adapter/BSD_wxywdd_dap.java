package com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.boshide2.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-4-27.
 */

public class BSD_wxywdd_dap extends BaseAdapter {
    LayoutInflater layoutInflater;
    Context context;
    private List<Map<String, String>> list;
    UP_jiaqian up_jiaqian;
    Remo remove;
    UP_gongshi up_gongshi;
    UP_xm_mc  up_xm_mc;



    public  void  setUp_xm_mc(UP_xm_mc up_xm_mc){
        this.up_xm_mc=up_xm_mc;
    }
    public void setUp_jiaqian(UP_jiaqian up_jiaqian) {
        this.up_jiaqian = up_jiaqian;
    }
    public void setUp_gongshi(UP_gongshi up_gongshi) {
        this.up_gongshi = up_gongshi;
    }

    public void setRemove(Remo remove) {
        this.remove = remove;
    }


    public void setList(List<Map<String, String>> list) {
        this.list = list;
    }

    public BSD_wxywdd_dap(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public BSD_wxywdd_dap(Context context, List<Map<String, String>> list) {
        this.context = context;
        this.list = list;
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
        TextView bsd_xsbj_name, bsd_xsbj_caozuo, bsd_xsbj_gongshi, bsd_xsbj_jiner;
    }

    @Override
    public View getView(final int i, View contetview, ViewGroup viewGroup) {
        Log.i("hehehehe", "getView: " + list.size());
        Holder holder = null;
        if (contetview == null) {
            holder = new Holder();
            contetview = LayoutInflater.from(context).inflate(R.layout.bsd_wxywdd_adp_item, null);
            holder.bsd_xsbj_name = (TextView) contetview.findViewById(R.id.bsd_xsbj_name);
            holder.bsd_xsbj_caozuo = (TextView) contetview.findViewById(R.id.bsd_xsbj_caozuo);
            holder.bsd_xsbj_gongshi = (TextView) contetview.findViewById(R.id.bsd_xsbj_gongshi);
            holder.bsd_xsbj_jiner = (TextView) contetview.findViewById(R.id.bsd_xsbj_jiner);
            contetview.setTag(holder);
        } else {
            holder = (Holder) contetview.getTag();
        }
        holder.bsd_xsbj_name.setText(list.get(i).get("reny_mc"));
        holder.bsd_xsbj_gongshi.setText(list.get(i).get("paig_khgs"));
        holder.bsd_xsbj_jiner.setText(list.get(i).get("paig_khje"));
        holder.bsd_xsbj_jiner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                up_jiaqian.onYesClick(list.get(i).get("paig_khje"), i);
            }
        });

        holder.bsd_xsbj_gongshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                up_gongshi.onYesClick(list.get(i).get("paig_khgs"), i);
            }
        });

        holder.bsd_xsbj_caozuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove.onYesClick(list.get(i).get("reco_no"));
                list.remove(i);
                notifyDataSetChanged();
            }
        });
        return contetview;
    }

    public interface Remo {
        public void onYesClick(String reco_no);
    }

    public interface UP_gongshi {
        public void onYesClick(String gongshi, int i);
    }

    public interface UP_jiaqian {
        public void onYesClick(String gongshi, int i);
    }

    public interface UP_xm_mc {
        public void onYesClick(String xm_mc, int i);
    }

}
