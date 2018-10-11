package com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.Entity.BSD_KuaiSuBaoJia_XM_entity;
import com.example.administrator.boshide2.Modular.View.diaog.TooPromptdiaog;
import com.example.administrator.boshide2.R;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Administrator on 2017-4-18.
 */

/**
 * 博士德维修项目适配器
 */
public class BSD_wxxm_adp extends BaseAdapter {
    LayoutInflater layoutInflater;
    Context context;
    private OnOperateItemListener onOperateItemListener;

    public void setList(List<BSD_KuaiSuBaoJia_XM_entity> list) {
        this.list = list;
    }

    List<BSD_KuaiSuBaoJia_XM_entity> list;
    TooPromptdiaog promptdiaog;
    Up_ksbj_gs up_ksbj_gs;
    Up_ksbj_gsdj up_ksbj_gsdj;
    Up_ksbj_gsdj_shanchu up_ksbj_sc;

    public void setUp_ksbj_gs(Up_ksbj_gs up_ksbj_gs) {
        this.up_ksbj_gs = up_ksbj_gs;
    }

    public void setUp_ksbj_gsdj(Up_ksbj_gsdj up_ksbj_gsdj) {
        this.up_ksbj_gsdj = up_ksbj_gsdj;
    }
    public void setUp_ksbj_gsdj_shanchu(Up_ksbj_gsdj_shanchu up_ksbj_sc) {
        this.up_ksbj_sc = up_ksbj_sc;
    }

    public BSD_wxxm_adp(Context context, List<BSD_KuaiSuBaoJia_XM_entity> list) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        return (list==null)?0:list.size();
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
        TextView bsd_xsbj_name;
        TextView bsd_kxbj_bzsj;
        TextView bsd_kxbj_gsdj;
        TextView bsd_kxbj_je;
        TextView bsd_ksbj_tv_zt;
        ImageView iv_delete;
        TextView tv_wxxmNo;
    }

    @Override
    public View getView( final int i, View contetview, ViewGroup viewGroup) {
        Holder holder = null;
        if (contetview == null) {
            holder = new Holder();
            contetview = layoutInflater.inflate(R.layout.bsd_wxxm_item,null);
            holder.tv_wxxmNo = (TextView) contetview.findViewById(R.id.bsd_xsbj_no);
            holder.bsd_xsbj_name= (TextView) contetview.findViewById(R.id.bsd_xsbj_name);
            holder.bsd_kxbj_bzsj= (TextView) contetview.findViewById(R.id.bsd_kxbj_bzsj);
            holder.bsd_kxbj_gsdj= (TextView) contetview.findViewById(R.id.bsd_kxbj_gsdj);
            holder.bsd_kxbj_je= (TextView) contetview.findViewById(R.id.bsd_kxbj_je);
            holder.iv_delete= (ImageView) contetview.findViewById(R.id.iv_delete);
            holder.bsd_ksbj_tv_zt= (TextView) contetview.findViewById(R.id.bsd_ksbj_tv_zt);
            contetview.setTag(holder);
        } else {
            holder = (Holder) contetview.getTag();
        }
        holder.tv_wxxmNo.setText(list.get(i).getWxxm_no());
        holder.bsd_xsbj_name.setText(list.get(i).getWxxm_mc());
        //工时
        holder.bsd_kxbj_bzsj.setText("" + list.get(i).getWxxm_gs());
        holder.bsd_kxbj_bzsj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onOperateItemListener != null) {
                    onOperateItemListener.onUpdateGS(list.get(i).getWxxm_no(), list.get(i).getWxxm_gs(), list.get(i).getWxxm_mc(), i);
                }
            }
        });
        if (list.get(i).getWxxm_gs() == 0) {
            holder.bsd_kxbj_gsdj.setText("" + list.get(i).getWxxm_je());
        } else {
            DecimalFormat df=new DecimalFormat("#.##");
            String    gs=df.format(list.get(i).getWxxm_je() / list.get(i).getWxxm_gs());
            holder.bsd_kxbj_gsdj.setText(gs);
        }
        holder.bsd_kxbj_je.setText("" + list.get(i).getWxxm_je());
        holder.bsd_kxbj_je.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onOperateItemListener != null) {
                    onOperateItemListener.onUpdateJe(list.get(i).getWxxm_no(), list.get(i).getWxxm_je(), list.get(i).getWxxm_mc(), i);
                }
            }
        });
        holder.bsd_ksbj_tv_zt.setText(list.get(i).getWxxm_zt());
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onOperateItemListener != null) {
                    onOperateItemListener.onDelete(list.get(i).getWxxm_no(), i);
                }
            }
        });
        return contetview;
    }

    public interface Up_ksbj_gs {
        public void onYesClick(int i,String name, double gongshi);
    }

    public interface Up_ksbj_gsdj {
        public void onYesClick(int i,String name, double gongshidanjia);
    }
    public interface  Up_ksbj_gsdj_shanchu{
        public void onYesClick();
    }

    public interface OnOperateItemListener {
        void onDelete(String wxxmNo, int position);

        void onUpdateGS(String wxxmNo, double wxxmGs, String wxxmMc, int position);

        void onUpdateJe(String wxxmNo,  double wxxmje, String wxxmMc, int position);
    }

    public void setOnOperateItemListener(OnOperateItemListener onOperateItemListener) {
        this.onOperateItemListener = onOperateItemListener;
    }
}