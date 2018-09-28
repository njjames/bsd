package com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.Entity.WXLS_Bean;
import com.example.administrator.boshide2.R;

import java.util.List;

/**
 * Created by Administrator on 2017-09-15.
 */

public class WXLS_Adapter extends BaseAdapter {
    Context context;
    List<WXLS_Bean> list;
    LayoutInflater inflater;
    private int currentPosition = 0;

    public WXLS_Adapter(Context context, List<WXLS_Bean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_item_fr_wxls, null);
            holder.workno= (TextView) convertView.findViewById(R.id.tv_work_no);
            holder.riqi = (TextView) convertView.findViewById(R.id.tv_date);
            holder.chepai = (TextView) convertView.findViewById(R.id.tv_chepai);
            holder.chezhu = (TextView) convertView.findViewById(R.id.tv_chezhu);
            holder.huiyuan = (TextView) convertView.findViewById(R.id.tv_huiyuan);
            holder.dianhua = (TextView) convertView.findViewById(R.id.tv_dianhua);
            holder.licheng = (TextView) convertView.findViewById(R.id.tv_licheng);
            holder.jine = (TextView) convertView.findViewById(R.id.tv_jine);
            holder.iv_selected = (ImageView) convertView.findViewById(R.id.iv_selected);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        WXLS_Bean bean = list.get(position);
        if (position == currentPosition) {
            holder.iv_selected.setVisibility(View.VISIBLE);
        } else {
            holder.iv_selected.setVisibility(View.INVISIBLE);
        }
        holder.workno.setText(bean.getDanhao());
        holder.riqi.setText(bean.getRiqi());
        holder.chepai.setText(bean.getChepai());
        holder.chezhu.setText(bean.getChezhu());
        holder.huiyuan.setText(bean.getHuiyuan());
        holder.dianhua.setText(bean.getDianhua());
        holder.licheng.setText(bean.getLicheng());
        holder.jine.setText(bean.getJine());
        return convertView;
    }

    public void setCurrentPositon(int position) {
        this.currentPosition = position;
    }

    class ViewHolder {
        public TextView workno;
        public TextView riqi;
        public TextView chepai;
        public TextView chezhu;
        public TextView huiyuan;
        public TextView dianhua;
        public TextView licheng;
        public TextView jine;
        public ImageView iv_selected;
    }
}
