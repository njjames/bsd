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

import java.util.List;


/**
 * Created by Administrator on 2017-4-20.
 */

public class BSD_mrkx_wxcl_adp extends BaseAdapter {
    private  LayoutInflater layoutInflater;
    private  Context context;
    private  List<BSD_WeiXiuJieDan_CL_Entity> list;
    private  DeletCL deletCL;
    private  UPsl uPsl;
    private  UPdj uPdj;
    private  KuCun   kuCun;
    private OnOperateItemListener onOperateItemListener;

    public void setuPsl(UPsl uPsl) {
        this.uPsl = uPsl;
    }

    public void setuPdj(UPdj uPdj) {
        this.uPdj = uPdj;
    }

    public void setDeletCL(DeletCL deletCL) {
        this.deletCL = deletCL;
    }

    public  void  setKuCun(KuCun   kuCun){
        this.kuCun=kuCun;
    }

    public void setList(List<BSD_WeiXiuJieDan_CL_Entity> list) {
        this.list = list;
    }

    public BSD_mrkx_wxcl_adp(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public BSD_mrkx_wxcl_adp(Context context, List<BSD_WeiXiuJieDan_CL_Entity> list) {
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
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    class Holder {
        TextView bsd_kxbj_dj,bsd_mrkx_hyzk,bsd_xsbj_name,bsd_kxbj_bzsj,bsd_kxbj_gsdj,bsd_kxbj_je,bsd_kxbj_qian,bsd_kxbj_cz;
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
            holder.bsd_kxbj_je= (TextView) contetview.findViewById(R.id.tv_ygsf);
            holder.bsd_kxbj_cz= (TextView) contetview.findViewById(R.id.bsd_kxbj_cz);
            holder.iv_delete= (ImageView) contetview.findViewById(R.id.iv_delete);
            holder.bsd_mrkx_hyzk= (TextView) contetview.findViewById(R.id.bsd_mrkx_hyzk);
            holder.bsd_kxbj_dj= (TextView) contetview.findViewById(R.id.bsd_kxbj_dj);
            holder.iv_stock = (ImageView) contetview.findViewById(R.id.iv_stock);
            contetview.setTag(holder);
        } else {
            holder = (Holder) contetview.getTag();
        }
        holder.bsd_xsbj_name.setText(list.get(i).getPeij_mc());
        holder.bsd_kxbj_bzsj.setText(""+list.get(i).getPeij_sl());
        holder.bsd_kxbj_bzsj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uPsl.onYesClick(
                        list.get(i).getReco_no(),list.get(i).getPeij_sl(),
                        list.get(i).getPeij_ydj()
                );
            }
        });
        double v = (Math.round(list.get(i).getPeij_zk()* list.get(i).getPeij_ydj() * 100) / 100.0);
        holder.bsd_kxbj_gsdj.setText(""+list.get(i).getPeij_ydj());
        holder.bsd_kxbj_gsdj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uPdj.onYesClick(list.get(i).getReco_no(),list.get(i).getPeij_sl(),
                        list.get(i).getPeij_ydj());
            }
        });
        holder.bsd_kxbj_je.setText(list.get(i).getPeij_dw());
        holder.bsd_kxbj_cz.setText(""+list.get(i).getPeij_sl()*v);
        // 删除
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onOperateItemListener != null) {
                    onOperateItemListener.onDelete(list.get(i).getPeij_no(), i);
                }
            }
        });

        holder.bsd_kxbj_dj.setText(""+v);
        holder.bsd_mrkx_hyzk.setText(""+list.get(i).getPeij_zk());
        holder.iv_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kuCun.query_kc(list.get(i).getPeij_no());
            }
        });
        return contetview;
    }

   public  interface   KuCun{
       public  void  query_kc(String  peij_no );
   }

    public interface  DeletCL{
        public void onYesClick(int i);
    }
    public interface UPsl {
        public void onYesClick(int i, double sl, double dj);
    }
    public interface UPdj {
        public void onYesClick(int i, double sl, double dj);
    }

    public interface OnOperateItemListener {
        void onDelete(String peij_no, int position);
    }

    public void setOnOperateItemListener(OnOperateItemListener onOperateItemListener) {
        this.onOperateItemListener = onOperateItemListener;
    }
}