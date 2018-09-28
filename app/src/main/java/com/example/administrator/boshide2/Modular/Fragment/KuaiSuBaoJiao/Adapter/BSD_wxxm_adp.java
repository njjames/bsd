package com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    public BSD_wxxm_adp(Context context) {
        this.context = context;

        this.layoutInflater = LayoutInflater.from(context);
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

    class Holder {
        TextView bsd_xsbj_name,bsd_kxbj_bzsj,bsd_kxbj_gsdj,bsd_kxbj_je,bsd_ksbj_tv_zt;
        ImageView bsd_kxbj_xmsc;

    }

    @Override
    public View getView( final int i, View contetview, ViewGroup viewGroup) {
        Holder holder=null;
        if (contetview == null) {
            holder = new Holder();
            contetview = layoutInflater.inflate(R.layout.bsd_wxxm_item,null);
            holder.bsd_xsbj_name= (TextView) contetview.findViewById(R.id.bsd_xsbj_name);
            holder.bsd_kxbj_bzsj= (TextView) contetview.findViewById(R.id.bsd_kxbj_bzsj);
            holder.bsd_kxbj_gsdj= (TextView) contetview.findViewById(R.id.bsd_kxbj_gsdj);
            holder.bsd_kxbj_je= (TextView) contetview.findViewById(R.id.bsd_kxbj_je);
            holder.bsd_kxbj_xmsc= (ImageView) contetview.findViewById(R.id.bsd_kxbj_xmsc);
            holder.bsd_ksbj_tv_zt= (TextView) contetview.findViewById(R.id.bsd_ksbj_tv_zt);
            contetview.setTag(holder);
        } else {
            holder = (Holder) contetview.getTag();
        }
        holder.bsd_xsbj_name.setText(list.get(i).getWxxm_mc());
        //工时
        holder.bsd_kxbj_bzsj.setText(""+list.get(i).getWxxm_gs());
        holder.bsd_kxbj_bzsj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                up_ksbj_gs.onYesClick(i,list.get(i).getWxxm_mc(),list.get(i).getWxxm_gs());
            }
        });

//        String a  = String.valueOf((list.get(i).getWxxm_je()/list.get(i).getWxxm_gs()));
//        if (a.equals("NaN")){
//            holder.bsd_kxbj_gsdj.setText(list.get(i).getWxxm_je()+"");
//        }else {
//            holder.bsd_kxbj_gsdj.setText(""+list.get(i).getWxxm_je()/list.get(i).getWxxm_gs());
//        }
//        holder.bsd_kxbj_gsdj.setText(""+list.get(i).getWxxm_dj());
        if(list.get(i).getWxxm_gs()==0){
            list.get(i).setWxxm_gs(1.0);
        }

        DecimalFormat df=new DecimalFormat("#.##");
        String    gs=df.format(list.get(i).getWxxm_je() / list.get(i).getWxxm_gs());
        holder.bsd_kxbj_gsdj.setText(gs);
//        Toast.makeText(context,"adapter的单价是"+list.get(i).getWxxm_dj()+"",Toast.LENGTH_SHORT).show();


        Log.i("cjn","这个是适配器的单价"+list.get(i).getWxxm_je());
        holder.bsd_kxbj_je.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                up_ksbj_gsdj.onYesClick(i,list.get(i).getWxxm_mc(),list.get(i).getWxxm_je());
            }
        });
        holder.bsd_ksbj_tv_zt.setText(list.get(i).getWxxm_zt());
        holder.bsd_kxbj_je.setText(""+list.get(i).getWxxm_je());
        holder.bsd_kxbj_xmsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptdiaog=new TooPromptdiaog(context,"是否删除");
                promptdiaog.setToopromtOnClickListener(new TooPromptdiaog.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick() {

                        list.remove(i);
                        notifyDataSetChanged();
                        promptdiaog.dismiss();
                        up_ksbj_sc.onYesClick();

                    }
                });

                promptdiaog.show();
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

}