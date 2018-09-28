package com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.Fagmt.fagmt_adp;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_XM_Entity;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.dialog.PaiGongDialog;
import com.example.administrator.boshide2.Modular.View.diaog.TooPromptdiaog;
import com.example.administrator.boshide2.R;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Administrator on 2017-4-20.
 */

public class BSD_mrbx_wxxm_adp extends BaseAdapter {
    LayoutInflater layoutInflater;
    Context context;
    DanXiangPaiGong danXiangPaiGong;
    RelativeLayout textView = null;
    int last_item;

    Delite delite;
    UPgs uPgs;
    UPdj uPdj;
    UPmc  uPmc;
    private OnOperateItemListener onOperateItemListener;
    private int currentPosition = 0;

    public void setuPgs(UPgs uPgs) {
        this.uPgs = uPgs;
    }

    public void setuPdj(UPdj uPdj) {
        this.uPdj = uPdj;
    }

    public  void  setuPmc(UPmc  uPmc){
        this.uPmc=uPmc;
    }

    TooPromptdiaog promptdiaog;

    public void setDelite(Delite delite) {
        this.delite = delite;
    }

    public void setLast_item(int last_item) {
        this.last_item = last_item;
    }

    public void setDanXiangPaiGong(DanXiangPaiGong danXiangPaiGong) {
        this.danXiangPaiGong = danXiangPaiGong;
    }

    public void setList(List<BSD_WeiXiuJieDan_XM_Entity> list) {
        this.list = list;
    }

    List<BSD_WeiXiuJieDan_XM_Entity> list;

    public BSD_mrbx_wxxm_adp(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public BSD_mrbx_wxxm_adp(Context context, List<BSD_WeiXiuJieDan_XM_Entity> list) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        return (list == null) ? 0 : list.size();
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
        TextView bsd_kxbj_hygsf, bsd_kxbj_hyzk, bsd_shanchuxiangmu, bsd_xsbj_name, bsd_kxbj_bzsj, bsd_kxbj_gsdj, bsd_kxbj_je, bsd_kxbj_qian;
        ImageView bsd_kxbj_cz;
        ImageView iv_selected;
    }

    @Override
    public View getView(final int i, View contetview, ViewGroup viewGroup) {
        Log.i("wxxmlb", "getView:项目的长度是 "+list.size());
        Log.i("gscxaaa", "onClick:66666到了mrkx_wxxm里查询项目列表的适配器里了，工时是=== "+list.get(i).getWxxm_gs());
        Holder holder = null;
        if (contetview == null) {
            textView = new RelativeLayout(context);
            holder = new Holder();
            contetview = layoutInflater.inflate(R.layout.bsd_mrkx_wxxm_item, null);
            holder.bsd_xsbj_name = (TextView) contetview.findViewById(R.id.bsd_xsbj_name);
            holder.bsd_kxbj_bzsj = (TextView) contetview.findViewById(R.id.bsd_kxbj_bzsj);
            holder.bsd_kxbj_gsdj = (TextView) contetview.findViewById(R.id.bsd_kxbj_gsdj);
            holder.bsd_kxbj_je = (TextView) contetview.findViewById(R.id.bsd_kxbj_je);
            holder.bsd_kxbj_cz = (ImageView) contetview.findViewById(R.id.bsd_kxbj_cz);
            holder.bsd_shanchuxiangmu = (TextView) contetview.findViewById(R.id.bsd_kxbj_cz1);
            holder.bsd_kxbj_hyzk = (TextView) contetview.findViewById(R.id.bsd_kxbj_hyzk);
            holder.bsd_kxbj_hygsf = (TextView) contetview.findViewById(R.id.bsd_kxbj_hygsf);
            holder.iv_selected =(ImageView) contetview.findViewById(R.id.iv_selected);

//    holder.bsd_kxbj_qian= (TextView) contetview.findViewById(R.id.bsd_kxbj_qian);
//                holder.bsd_kxbj_qian1= (TextView) contetview.findViewById(R.id.bsd_kxbj_qian1);
            contetview.setTag(holder);
        } else {
            holder = (Holder) contetview.getTag();
        }
        holder.bsd_xsbj_name.setText(list.get(i).getWxxm_mc());
        holder.bsd_xsbj_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uPmc.onYesClick(list.get(i).getReco_no(), list.get(i).getWxxm_gs(), list.get(i).getWxxm_yje(),list.get(i).getWxxm_mc());
            }
        });

        holder.bsd_kxbj_bzsj.setText("" + list.get(i).getWxxm_gs());
        Log.i("gscxaaa", "getView:标准工时是多少== "+list.get(i).getWxxm_gs());
//        holder.bsd_kxbj_bzsj.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                uPgs.onAdd(list.get(i).getReco_no(),list.get(i).getWxxm_gs(),list.get(i).getWxxm_dj());
//            }
//        });

//        String a = String.valueOf((list.get(i).getWxxm_je() / list.get
//                (i).getWxxm_gs()));
//
//        if (a.equals("NaN")) {
//            holder.bsd_kxbj_gsdj.setText(list.get(i).getWxxm_je() + "");    //工时单价
//        } else {
//            holder.bsd_kxbj_gsdj.setText("" + list.get(i).getWxxm_je
//                    () / list.get(i).getWxxm_gs());
//        }


        if(list.get(i).getWxxm_gs()==0){
            holder.bsd_kxbj_gsdj.setText(""+list.get(i).getWxxm_je() );
        }else {
            DecimalFormat  df=new DecimalFormat("#.##");
            String    gs=df.format(list.get(i).getWxxm_je() / list.get(i).getWxxm_gs());

            holder.bsd_kxbj_gsdj.setText(gs);
        }

        Log.i("gscxaaa", "getView22222:标准工时是多少== "+list.get(i).getWxxm_gs());

        holder.bsd_kxbj_je.setText("" + list.get(i).getWxxm_yje());
        holder.bsd_kxbj_je.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uPdj.onYesClick(list.get(i).getReco_no(), list.get(i).getWxxm_gs(), list.get(i).getWxxm_yje(),list.get(i).getWxxm_mc());
            }
        });
        // 单项派工
        holder.bsd_kxbj_cz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onOperateItemListener != null) {
                    onOperateItemListener.onPaiGong(list.get(i).getWxxm_no());
                }
            }
        });

        holder.bsd_shanchuxiangmu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delite.onYesClick(list.get(i).getReco_no());

                notifyDataSetChanged();
            }
        });

        double v = (Math.round(list.get(i).getWxxm_zk() * list.get(i).getWxxm_yje() * 100) / 100.0);
        //折扣
        holder.bsd_kxbj_hyzk.setText("" + list.get(i).getWxxm_zk());
        holder.bsd_kxbj_hygsf.setText("" + v);

        if (currentPosition == i) {
            holder.iv_selected.setVisibility(View.VISIBLE);
        } else {
            holder.iv_selected.setVisibility(View.INVISIBLE);
        }
        return contetview;
    }

    public interface DanXiangPaiGong {
        public void onYesClick(String no, double gs, double dj);
    }

    public interface Delite {
        public void onYesClick(int i);
    }

    public interface UPgs {
        public void onYesClick(int i, double gs, double dj,String   xmmc);
    }

    public interface UPdj {
        public void onYesClick(int i, double gs, double dj,String   xmmc);
    }

    public interface UPmc {
        public void onYesClick(int i, double gs, double dj,String   xmmc);
    }

    public interface OnOperateItemListener {
        public void onPaiGong(String wxxmNo);
    }

    public void setOnOperateItemListener(OnOperateItemListener onOperateItemListener) {
        this.onOperateItemListener = onOperateItemListener;
    }

    public void setCurrentPosition(int position) {
        this.currentPosition = position;
    }
}