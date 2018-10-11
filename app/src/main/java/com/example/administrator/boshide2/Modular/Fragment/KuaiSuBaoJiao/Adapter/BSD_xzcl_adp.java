package com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.Adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.Entity.BSD_KuaiSuBaoJia_CL_entity;
import com.example.administrator.boshide2.Modular.View.diaog.TooPromptdiaog;
import com.example.administrator.boshide2.R;

import java.util.List;

/**
 * Created by Administrator on 2017-4-18.
 */

/**
 * 博士德维修cailiao适配器
 */
public class BSD_xzcl_adp extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<BSD_KuaiSuBaoJia_CL_entity> list;
    private TooPromptdiaog promptdiaog;

    int shuliangs[];
    public int shuliang = 1;
    private Updanjia updanjia;//加
    private Delete delete;//删除
    private KuCun kuCun;
    private OnOperateItemListener onOperateItemListener;

    public void setDelete(Delete delete) {
        this.delete = delete;
    }

    public void setUpdanjia(Updanjia updanjia) {
        this.updanjia = updanjia;
    }

    public void setKuCun(KuCun kuCun) {
        this.kuCun = kuCun;
    }

    public void setList(List<BSD_KuaiSuBaoJia_CL_entity> list) {
        this.list = list;
//        shuliangs=new int[list.size()];

        if (list.size() > 0) {
            shuliangs = new int[list.size()];
            for (int i = 0; i < list.size(); i++) {
                shuliangs[i] = (int) list.get(i).getPeij_sl();

            }
        }
    }

    public shuliangzongjia getShuliangzongjia() {
        return shuliangzongjia;
    }

    public void setShuliangzongjia(shuliangzongjia shuliangzongjia) {
        this.shuliangzongjia = shuliangzongjia;
    }

    shuliangzongjia shuliangzongjia;

    public BSD_xzcl_adp(Context context, List<BSD_KuaiSuBaoJia_CL_entity> list) {
        this.context = context;
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
        TextView bsd_xzcl_name, bsd_xzcl_shuliang, bsd_xzcl_danjia, bsd_xzcl_dw, bsd_xzcl_je, bsd_xzcl_caozuo;
        ImageView iv_delete;
        ImageView iv_stock;
        TextView tv_peijNo;
    }

    @Override
    public View getView(final int i, View contetview, ViewGroup viewGroup) {
        Holder holder = null;
        if (contetview == null) {
            holder = new Holder();
            contetview = layoutInflater.inflate(R.layout.bsd_wxyy_xzcl_item, null);
            holder.tv_peijNo = (TextView) contetview.findViewById(R.id.bsd_xzcl_no);
            holder.bsd_xzcl_name = (TextView) contetview.findViewById(R.id.bsd_xzcl_name);
            holder.bsd_xzcl_shuliang = (TextView) contetview.findViewById(R.id.bsd_xzcl_shuliang);
            holder.bsd_xzcl_danjia = (TextView) contetview.findViewById(R.id.bsd_xzcl_danjia);
            holder.bsd_xzcl_dw = (TextView) contetview.findViewById(R.id.bsd_xzcl_dw);
            holder.bsd_xzcl_je = (TextView) contetview.findViewById(R.id.bsd_xzcl_je);
            holder.iv_delete = (ImageView) contetview.findViewById(R.id.iv_delete);
            holder.iv_stock = (ImageView) contetview.findViewById(R.id.iv_stock);
            contetview.setTag(holder);
        } else {
            holder = (Holder) contetview.getTag();
        }
        holder.tv_peijNo.setText(list.get(i).getPeij_no());
        holder.bsd_xzcl_name.setText(list.get(i).getPeij_mc());
        holder.bsd_xzcl_shuliang.setText("" + (int) list.get(i).getPeij_sl());
        holder.bsd_xzcl_danjia.setText("" + list.get(i).getPeij_dj());
        holder.bsd_xzcl_dw.setText(list.get(i).getPeij_dw());
        holder.bsd_xzcl_je.setText("" + list.get(i).getPeij_sl() * list.get(i).getPeij_dj());
        holder.bsd_xzcl_shuliang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onOperateItemListener != null) {
                    onOperateItemListener.onUpdateSL(list.get(i).getPeij_no(), list.get(i).getPeij_sl(), list.get(i).getPeij_mc(), i);
                }
            }
        });
        holder.bsd_xzcl_danjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onOperateItemListener != null) {
                    onOperateItemListener.onUpdateDj(list.get(i).getPeij_no(), list.get(i).getPeij_dj(), list.get(i).getPeij_mc(), i);
                }
            }
        });
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onOperateItemListener != null) {
                    onOperateItemListener.onDelete(list.get(i).getPeij_no(), i);
                }
            }
        });

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

    public interface KuCun {
        public void query_kc(String peij_no);
    }


    public interface shuliangzongjia {
        public void onYesClick(int shuliang, double zongjia);
    }

    public interface Updanjia {
        public void onYesClick(int i, String name, double danjia);
    }

    //删除
    public interface Delete {
        public void onYesClick();
    }

    public interface OnOperateItemListener {
        void onDelete(String peijNo, int position);

        void onUpdateSL(String peijNo, double peijSL, String peijMc, int position);

        void onUpdateDj(String peijNo,  double peijDj, String peijMc, int position);

        void onSearchStock(String peij_no);
    }

    public void setOnOperateItemListener(OnOperateItemListener onOperateItemListener) {
        this.onOperateItemListener = onOperateItemListener;
    }
}
