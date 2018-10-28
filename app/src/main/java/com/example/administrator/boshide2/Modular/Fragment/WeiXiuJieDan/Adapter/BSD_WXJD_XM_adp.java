package com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_XM_Entity;
import com.example.administrator.boshide2.Modular.View.diaog.TooPromptdiaog;
import com.example.administrator.boshide2.R;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Administrator on 2017-4-21.
 */

public class BSD_WXJD_XM_adp extends BaseAdapter {
    LayoutInflater layoutInflater;
    Context context;
    List<BSD_WeiXiuJieDan_XM_Entity> list;


    TooPromptdiaog promptdiaog;
    UpGongShi upGongShi;
    UpGongShiDanJia upGongShiDanJia;
    UpXmmc upXmmc;
    private OnOperateItemListener onOperateItemListener;
    private int currentPosition;

    public void setXmDelete(XmDelete xmDelete) {
        this.xmDelete = xmDelete;
    }

    public void  setUpXmmc(UpXmmc  upXmmc){
        this.upXmmc=upXmmc;
    }


    XmDelete xmDelete;

    public void setList(List<BSD_WeiXiuJieDan_XM_Entity> list) {
        this.list = list;
    }

    public BSD_WXJD_XM_adp(Context context, List<BSD_WeiXiuJieDan_XM_Entity> list) {
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
        TextView bsd_xzcl_name, bsd_xzcl_gs, bsd_xzcl_danjia, bsd_xzcl_je, bsd_xzcl_zt;
            ImageView bsd_xzcl_caozuo;
    }

    public void setPromptdiaog(TooPromptdiaog promptdiaog) {
        this.promptdiaog = promptdiaog;
    }

    public void setUpGongShi(UpGongShi upGongShi) {
        this.upGongShi = upGongShi;
    }

    public void setUpGongShiDanJia(UpGongShiDanJia upGongShiDanJia) {
        this.upGongShiDanJia = upGongShiDanJia;
    }

    @Override
    public View getView(final int i, View contetview, ViewGroup viewGroup) {
        Holder holder = null;
        if (contetview == null) {
            holder = new Holder();
            contetview = layoutInflater.inflate(R.layout.bsd_wxjd_xzxm_item, null);
            holder.bsd_xzcl_name = (TextView) contetview.findViewById(R.id.bsd_xzcl_name);
            holder.bsd_xzcl_gs = (TextView) contetview.findViewById(R.id.bsd_xzcl_gs);
            holder.bsd_xzcl_danjia = (TextView) contetview.findViewById(R.id.bsd_xzcl_danjia);
            holder.bsd_xzcl_je = (TextView) contetview.findViewById(R.id.bsd_xzcl_je);
            holder.bsd_xzcl_zt = (TextView) contetview.findViewById(R.id.bsd_xzcl_zt);
            holder.bsd_xzcl_caozuo = (ImageView) contetview.findViewById(R.id.bsd_xzcl_caozuo);
            contetview.setTag(holder);
        } else {
            holder = (Holder) contetview.getTag();
        }

        holder.bsd_xzcl_name.setText(list.get(i).getWxxm_mc());
        holder.bsd_xzcl_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOperateItemListener != null) {
                    onOperateItemListener.onUpdateWxxmMc(i);
                }
            }
        });

        //工时
        holder.bsd_xzcl_gs.setText(""+list.get(i).getWxxm_gs());
        holder.bsd_xzcl_gs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upGongShi.onYesClick(i,list.get(i).getWxxm_mc(),list.get(i).getWxxm_gs());
            }
        });

//        String a  = String.valueOf((list.get(i).getWxxm_je()/list.get
//                (i).getWxxm_gs()));
//        if (a.equals("NaN")){
//            holder.bsd_xzcl_danjia.setText(list.get(i).getWxxm_je()+"");
//        }else {
//            holder.bsd_xzcl_danjia.setText(""+list.get(i).getWxxm_je
//                    ()/list.get(i).getWxxm_gs());
//        }金额   工时  单价

        if (list.get(i).getWxxm_gs() == 0) {
            list.get(i).setWxxm_gs(1.0);
        }
        DecimalFormat df=new DecimalFormat("#.##");
        String gs = df.format(list.get(i).getWxxm_je() / list.get(i).getWxxm_gs());

        holder.bsd_xzcl_danjia.setText(gs);

//        holder.bsd_xzcl_danjia.setText(""+list.get(i).getWxxm_dj());


        //金额
        holder.bsd_xzcl_je.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onOperateItemListener != null) {
                    onOperateItemListener.onUpdateYgsf(i);
                }
            }
        });

        holder.bsd_xzcl_je.setText("" + list.get(i).getWxxm_je());
        holder.bsd_xzcl_zt.setText(list.get(i).getWxxm_zt());
        //删除操作
        holder.bsd_xzcl_caozuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onOperateItemListener != null) {
                    onOperateItemListener.onDelete(i);
                }
            }
        });
        return contetview;
    }


    public interface UpGongShi {
        public void onYesClick(int i,String name, double gongshi);
    }

    public interface UpGongShiDanJia {
        public void onYesClick(int i,String name, double gongshidanjia);
    }

    public interface  XmDelete{
        public void onYesClick();
    }

    public  interface  UpXmmc{
        public  void  onYesClick(int i,String name);
    }

    public interface OnOperateItemListener {
        void onPaiGong(String wxxmNo);

        void onDelete(int position);

        void onUpdateYgsf(int position);

        void onUpdateWxxmMc(int position);
    }

    public void setOnOperateItemListener(OnOperateItemListener onOperateItemListener) {
        this.onOperateItemListener = onOperateItemListener;
    }

    public void setCurrentPosition(int position) {
        this.currentPosition = position;
    }

}
