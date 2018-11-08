package com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.Fagmt.fagmt_adp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_CL_Entity;
import com.example.administrator.boshide2.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


/**
 * Created by Administrator on 2017-4-20.
 */
public class BSD_mrkx_wxcl_adp extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<BSD_WeiXiuJieDan_CL_Entity> list;
    private OnOperateItemListener onOperateItemListener;

    public BSD_mrkx_wxcl_adp(Context context, List<BSD_WeiXiuJieDan_CL_Entity> list) {
        this.layoutInflater = LayoutInflater.from(context);
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

    public final class Holder {
        TextView bsd_kxbj_dj;
        TextView bsd_mrkx_hyzk;
        TextView bsd_xsbj_name;
        TextView bsd_kxbj_bzsj;
        TextView bsd_kxbj_gsdj;
        TextView bsd_kxbj_dw;
        TextView bsd_kxbj_je;
        ImageView iv_stock;
        ImageView iv_delete;
    }

    @Override
    public View getView(final int i, View contetview, ViewGroup viewGroup) {
        Holder holder=null;
        if (contetview == null) {
            holder = new Holder();
            contetview=layoutInflater.inflate(R.layout.bsd_mrkx_wxcl_item,null);
            holder.bsd_xsbj_name= (TextView) contetview.findViewById(R.id.bsd_xsbj_name);
            holder.bsd_kxbj_bzsj= (TextView) contetview.findViewById(R.id.bsd_kxbj_bzsj);
            holder.bsd_kxbj_gsdj= (TextView) contetview.findViewById(R.id.bsd_kxbj_gsdj);
            holder.bsd_kxbj_dw= (TextView) contetview.findViewById(R.id.bsd_kxbj_dw);
            holder.bsd_kxbj_je= (TextView) contetview.findViewById(R.id.bsd_kxbj_je);
            holder.iv_delete= (ImageView) contetview.findViewById(R.id.iv_delete);
            holder.bsd_mrkx_hyzk= (TextView) contetview.findViewById(R.id.bsd_mrkx_hyzk);
            holder.bsd_kxbj_dj= (TextView) contetview.findViewById(R.id.bsd_kxbj_dj);
            holder.iv_stock = (ImageView) contetview.findViewById(R.id.iv_stock);
            contetview.setTag(holder);
        } else {
            holder = (Holder) contetview.getTag();
        }
        holder.bsd_xsbj_name.setText(list.get(i).getPeij_mc());
        holder.bsd_kxbj_bzsj.setText("" + list.get(i).getPeij_sl());
        // 修改数量
        holder.bsd_kxbj_bzsj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onOperateItemListener != null) {
                    onOperateItemListener.onUpdateSl(i);
                }
            }
        });
        double v = (Math.round(list.get(i).getPeij_zk()* list.get(i).getPeij_ydj() * 100) / 100.0);
        holder.bsd_kxbj_gsdj.setText("" + list.get(i).getPeij_ydj());
        // 修改原单价
        holder.bsd_kxbj_gsdj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onOperateItemListener != null) {
                    onOperateItemListener.onUpdateYDj(i);
                }
            }
        });
        holder.bsd_kxbj_dw.setText(list.get(i).getPeij_dw());
        holder.bsd_kxbj_je.setText("" + list.get(i).getPeij_je());
        // 删除
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onOperateItemListener != null) {
                    onOperateItemListener.onDelete(list.get(i).getPeij_no(), i);
                }
            }
        });

        holder.bsd_kxbj_dj.setText("" + list.get(i).getPeij_dj());
        BigDecimal zk = new BigDecimal(list.get(i).getPeij_zk()).setScale(2, RoundingMode.HALF_UP);
        holder.bsd_mrkx_hyzk.setText(zk.toString());
        // 查看库存
        holder.iv_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOperateItemListener != null) {
                    onOperateItemListener.onSearchStock(list.get(i).getPeij_no());
                }
            }
        });
        return contetview;
    }

    public interface OnOperateItemListener {
        void onDelete(String peij_no, int position);

        void onSearchStock(String peij_no);

        void onUpdateSl(int position);

        void onUpdateYDj(int position);
    }

    public void setOnOperateItemListener(OnOperateItemListener onOperateItemListener) {
        this.onOperateItemListener = onOperateItemListener;
    }
}