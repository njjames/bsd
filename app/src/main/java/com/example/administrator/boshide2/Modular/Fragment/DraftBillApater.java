package com.example.administrator.boshide2.Modular.Fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.Entity.BSD_WXCL_CK_Entity;
import com.example.administrator.boshide2.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 草稿单据适配器
 */
public class DraftBillApater extends BaseAdapter {
    private Context context;
    private List<HashMap<String, Object>> list;
    private OnOperateItemListener onOperateItemListener;

    public DraftBillApater(Context context, List<HashMap<String, Object>> list) {
        this.context = context;
        this.list = list;
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(context).inflate(R.layout.dialog_draftbill_item, null);
            holder.tv_billNo = (TextView) convertView.findViewById(R.id.tv_billNo);
            holder.tv_billrq = (TextView) convertView.findViewById(R.id.tv_billrq);
            holder.iv_open = (ImageView) convertView.findViewById(R.id.iv_open);
            holder.iv_delete = (ImageView) convertView.findViewById(R.id.iv_delete);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        final HashMap<String, Object> item = list.get(position);
        holder.tv_billNo.setText(item.get("billNo").toString());
        holder.tv_billrq.setText(item.get("billRq").toString());
        holder.iv_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onOperateItemListener != null) {
                    onOperateItemListener.onOpen(item.get("billNo").toString());
                }
            }
        });
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onOperateItemListener != null) {
                    onOperateItemListener.onDelete(item.get("billNo").toString());
                }
            }
        });
        return convertView;
    }

    public final class Holder {
        TextView tv_billNo;
        TextView tv_billrq;
        ImageView iv_open;
        ImageView iv_delete;
    }

    public interface OnOperateItemListener {
        void onOpen(String billNo);

        void onDelete(String billNo);
    }

    public void setOnOperateItemListener(OnOperateItemListener onOperateItemListener) {
        this.onOperateItemListener = onOperateItemListener;
    }
}
