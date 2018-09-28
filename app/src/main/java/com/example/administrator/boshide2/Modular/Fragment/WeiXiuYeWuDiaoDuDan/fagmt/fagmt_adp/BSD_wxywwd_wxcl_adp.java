package com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.fagmt.fagmt_adp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_CL_Entity;
import com.example.administrator.boshide2.R;

import java.util.List;


/**
 * Created by Administrator on 2017-4-20.
 */

public class BSD_wxywwd_wxcl_adp extends BaseAdapter {
    LayoutInflater layoutInflater;
    Context context;
    List<BSD_WeiXiuJieDan_CL_Entity> list;
    DeletCL deletCL;
    UPsl uPsl;
    UPdj uPdj;
    private  KuCun   kuCun;

    public  void  setKuCun(KuCun   kuCun){
        this.kuCun=kuCun;
    }


    public void setuPsl(UPsl uPsl) {
        this.uPsl = uPsl;
    }

    public void setuPdj(UPdj uPdj) {
        this.uPdj = uPdj;
    }

    public void setDeletCL(DeletCL deletCL) {
        this.deletCL = deletCL;
    }

    public void setList(List<BSD_WeiXiuJieDan_CL_Entity> list) {
        this.list = list;
    }

    public BSD_wxywwd_wxcl_adp(Context context) {
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
    class Holder {
        TextView bsd_kxbj_qian1,bsd_xsbj_name,bsd_kxbj_bzsj,bsd_kxbj_gsdj,bsd_kxbj_je,bsd_kxbj_qian,bsd_kxbj_cz;
        LinearLayout bsd_wxcl_kc;
    }

    @Override
    public View getView(final int i, View contetview, ViewGroup viewGroup) {
        Holder holder=null;
        if (contetview == null) {
            holder = new Holder();
            contetview=layoutInflater.inflate(R.layout.bsd_wxywdd_wxcl_item,null);

            holder.bsd_xsbj_name= (TextView) contetview.findViewById(R.id.bsd_xsbj_name);
            holder.bsd_kxbj_bzsj= (TextView) contetview.findViewById(R.id.bsd_kxbj_bzsj);


            holder.bsd_kxbj_gsdj= (TextView) contetview.findViewById(R.id.bsd_kxbj_gsdj);
            holder.bsd_kxbj_je= (TextView) contetview.findViewById(R.id.bsd_kxbj_je);
            holder.bsd_kxbj_cz= (TextView) contetview.findViewById(R.id.bsd_kxbj_cz);
//            holder.bsd_kxbj_qian= (TextView) contetview.findViewById(R.id.bsd_kxbj_qian);
                holder.bsd_kxbj_qian1= (TextView) contetview.findViewById(R.id.bsd_kxbj_qian1);
            holder.bsd_wxcl_kc= (LinearLayout) contetview.findViewById(R.id.bsd_wxcl_kc);

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
                        list.get(i).getPeij_dj()
                );
            }
        });
        holder.bsd_kxbj_gsdj.setText(""+list.get(i).getPeij_dj());
        holder.bsd_kxbj_gsdj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uPdj.onYesClick(list.get(i).getReco_no(),list.get(i).getPeij_sl(),
                        list.get(i).getPeij_dj());
            }
        });
        holder.bsd_kxbj_je.setText(list.get(i).getPeij_dw());
        holder.bsd_kxbj_cz.setText(""+list.get(i).getPeij_sl()*list.get(i).getPeij_dj());
        holder.bsd_kxbj_qian1.setText("删除");
        holder.bsd_kxbj_qian1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletCL.onYesClick(list.get(i).getReco_no());
            }
        });
        holder.bsd_wxcl_kc.setOnClickListener(new View.OnClickListener() {
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
        public void onYesClick(int i,double sl,double dj);
    }
    public interface UPdj {
        public void onYesClick(int i,double sl,double dj);
    }
}