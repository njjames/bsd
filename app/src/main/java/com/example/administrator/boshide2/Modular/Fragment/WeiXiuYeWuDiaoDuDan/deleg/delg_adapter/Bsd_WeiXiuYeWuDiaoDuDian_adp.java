package com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.deleg.delg_adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.boshide2.Conts;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_CL_Entity;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.fagmt.fagmt_adp.BSD_wxywwd_wxcl_adp;
import com.example.administrator.boshide2.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-5-21.
 */

public class Bsd_WeiXiuYeWuDiaoDuDian_adp extends BaseAdapter {
    LayoutInflater layoutInflater;
    Context context;
    PaiGong paiGong;

    public void setPaiGong(PaiGong paiGong) {
        this.paiGong = paiGong;
    }

    public void setList(List<Map<String, String>> list) {
        this.list = list;
    }

    List<Map<String, String>> list = new ArrayList<Map<String, String>>();

    public Bsd_WeiXiuYeWuDiaoDuDian_adp(Context context) {
        this.context = context;
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

    @Override
    public View getView(final int i, View contetview, ViewGroup viewGroup) {
        Holder holder=null;
        if (contetview == null) {
            holder = new Holder();
            contetview = layoutInflater.inflate(R.layout.bsd_zcdd_pg_item, null);
            holder.bsd_zcdd_pg_name= (TextView) contetview.findViewById(R.id.bsd_zcdd_pg_name);
            Log.i("heidddd", "getView: 123456"+  holder.bsd_zcdd_pg_name.getHeight());
            holder.bsd_zcdd_pg_bumen= (TextView) contetview.findViewById(R.id.bsd_zcdd_pg_bumen);
            holder.bsd_zcdd_pg_pg= (TextView) contetview.findViewById(R.id.bsd_zcdd_pg_pg);
            contetview.setTag(holder);
        }else {
            holder = (Holder) contetview.getTag();
        }
        holder.bsd_zcdd_pg_name.setText(list.get(i).get("reny_xm"));
        holder.bsd_zcdd_pg_bumen.setText(list.get(i).get("dept_mc"));
        holder.bsd_zcdd_pg_pg.setText("派工");
        holder.bsd_zcdd_pg_pg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paiGong.onYesClick(i);
            }
        });
        return contetview;
    }



    class Holder {
        TextView bsd_zcdd_pg_name,bsd_zcdd_pg_bumen,bsd_zcdd_pg_pg;
    }
    public interface  PaiGong{
        public void onYesClick(int i);
    }
}
