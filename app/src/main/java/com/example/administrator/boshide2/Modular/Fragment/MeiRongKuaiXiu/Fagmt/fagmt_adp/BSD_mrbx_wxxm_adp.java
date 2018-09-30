package com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.Fagmt.fagmt_adp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_XM_Entity;
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

    public final class Holder {
        TextView bsd_kxbj_hygsf, bsd_kxbj_hyzk, bsd_xsbj_name, bsd_kxbj_bzsj, bsd_kxbj_gsdj, bsd_kxbj_qian;
        TextView tv_ygsf;
        ImageView tv_delete;
        ImageView bsd_kxbj_cz;
        ImageView iv_selected;
    }

    @Override
    public View getView(final int i, View contetview, ViewGroup viewGroup) {
        Holder holder = null;
        if (contetview == null) {
            textView = new RelativeLayout(context);
            holder = new Holder();
            contetview = layoutInflater.inflate(R.layout.bsd_mrkx_wxxm_item, null);
            holder.bsd_xsbj_name = (TextView) contetview.findViewById(R.id.bsd_xsbj_name);
            holder.bsd_kxbj_bzsj = (TextView) contetview.findViewById(R.id.bsd_kxbj_bzsj);
            holder.bsd_kxbj_gsdj = (TextView) contetview.findViewById(R.id.bsd_kxbj_gsdj);
            holder.tv_ygsf = (TextView) contetview.findViewById(R.id.tv_ygsf);
            holder.bsd_kxbj_cz = (ImageView) contetview.findViewById(R.id.bsd_kxbj_cz);
            holder.tv_delete = (ImageView) contetview.findViewById(R.id.iv_delete);
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
                if (onOperateItemListener != null) {
                    onOperateItemListener.onUpdateWxxmMc(list.get(i).getWxxm_no(), list.get(i).getWxxm_mc(), i);
                }
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

        holder.tv_ygsf.setText("" + list.get(i).getWxxm_yje());
        holder.tv_ygsf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onOperateItemListener != null) {
                    onOperateItemListener.onUpdateYgsf(list.get(i).getWxxm_no(), list.get(i).getWxxm_gs(), list.get(i).getWxxm_yje(),list.get(i).getWxxm_mc(), i);
                }
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

        // 删除维修项目
        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onOperateItemListener != null) {
                    onOperateItemListener.onDelete(list.get(i).getWxxm_no(), i);
                }
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
        void onPaiGong(String wxxmNo);

        /**
         * 删除维修项目
         * @param wxxmNo  维修项目编码
         * @param position  这个维修项目在集合中的位置
         */
        void onDelete(String wxxmNo, int position);

        void onUpdateYgsf(String wxxmNo, double wxxmGs, double wxxmYje, String wxxmMc, int position);

        void onUpdateWxxmMc(String wxxmNo, String wxxmMc, int position);
    }

    public void setOnOperateItemListener(OnOperateItemListener onOperateItemListener) {
        this.onOperateItemListener = onOperateItemListener;
    }

    public void setCurrentPosition(int position) {
        this.currentPosition = position;
    }
}