package com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.Adapter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.Entity.BSD_WeiXiuYuYue_Cl_entity;
import com.example.administrator.boshide2.Modular.View.diaog.TooPromptdiaog;
import com.example.administrator.boshide2.R;

import java.util.List;

/**
 * Created by Administrator on 2017-4-21.
 */

public class BSD_WXYY_CL_adp extends BaseAdapter {
    LayoutInflater layoutInflater;
    Context context;
    List<BSD_WeiXiuYuYue_Cl_entity> list;
    TooPromptdiaog promptdiaog;
    double zongjia;

    int shuliangs[];
    public int shuliang = 1;
    Updanjia updanjia;
    private  KuCun   kuCun;
    private  DeleteCL deleteCL;

    public  void  setKuCun(KuCun   kuCun){
        this.kuCun=kuCun;
    }

    public void setDeleteCL(DeleteCL deleteCL) {
        this.deleteCL = deleteCL;
    }


    public void setShuliangzong(BSD_WXYY_CL_adp.shuliangzongjia shuliangzong) {
        this.shuliangzong = shuliangzong;
    }

    shuliangzongjia shuliangzong;

    public void setUpdanjia(Updanjia updanjia) {
        this.updanjia = updanjia;
    }

    public void setList(List<BSD_WeiXiuYuYue_Cl_entity> list) {
        this.list = list;
//        shuliangs=new int[list.size()];

        if (list.size() > 0) {
            shuliangs = new int[list.size()];
            for (int i = 0; i < list.size(); i++) {
                shuliangs[i] = (int) list.get(i).getPeij_sl();

            }
        }


    }


    private Handler mHandler;

    public BSD_WXYY_CL_adp.shuliangzongjia getShuliangzongjia() {
        return shuliangzongjia;
    }

    public void setShuliangzongjia(BSD_WXYY_CL_adp.shuliangzongjia shuliangzongjia) {
        this.shuliangzongjia = shuliangzongjia;
    }

    shuliangzongjia shuliangzongjia;

    public BSD_WXYY_CL_adp(Context context ) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (list == null)? 0:list.size();
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
        TextView bsd_xzcl_name, bsd_xzcl_shuliang, bsd_xzcl_danjia, bsd_xzcl_tuhao, bsd_xzcl_pinpai, bsd_xzcl_caozuo;
        RelativeLayout bsd_wxyy_cl_delet;
        ImageView bsd_scl_iv_jian, bsd_scl_iv_jia;
        LinearLayout bsd_wxcl_kc;
    }

    @Override
    public View getView(final int i, View contetview, ViewGroup viewGroup) {
        Holder holder = null;
        if (contetview == null) {
            holder = new Holder();
            contetview = layoutInflater.inflate(R.layout.bsd_wxyy_xzcl_item, null);
            holder.bsd_xzcl_name = (TextView) contetview.findViewById(R.id.bsd_xzcl_name);
            holder.bsd_xzcl_shuliang = (TextView) contetview.findViewById(R.id.bsd_xzcl_shuliang);
            holder.bsd_xzcl_danjia = (TextView) contetview.findViewById(R.id.bsd_xzcl_danjia);
            holder.bsd_xzcl_tuhao = (TextView) contetview.findViewById(R.id.bsd_xzcl_tuhao);
            holder.bsd_xzcl_pinpai = (TextView) contetview.findViewById(R.id.bsd_xzcl_pinpai);
            holder.bsd_wxyy_cl_delet = (RelativeLayout) contetview.findViewById(R.id.bsd_wxyy_cl_delet);
            holder.bsd_scl_iv_jia = (ImageView) contetview.findViewById(R.id.bsd_scl_iv_jia);
            holder.bsd_scl_iv_jian = (ImageView) contetview.findViewById(R.id.bsd_scl_iv_jian);
            holder.bsd_wxcl_kc= (LinearLayout) contetview.findViewById(R.id.bsd_wxcl_kc);
// holder.bsd_xzcl_caozuo = (TextView) contetview.findViewById(R.id.bsd_xzcl_caozuo);
            contetview.setTag(holder);
        } else {
            holder = (Holder) contetview.getTag();
        }


        holder.bsd_xzcl_name.setText(list.get(i).getPeij_mc());


        holder.bsd_xzcl_shuliang.setText("" + (int) list.get(i).getPeij_sl());


        final Holder finalHolder = holder;



        holder.bsd_xzcl_danjia.setText("" + list.get(i).getPeij_dj());
        holder.bsd_xzcl_danjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updanjia.onYesClick(i,list.get(i).getPeij_mc(),list.get(i).getPeij_dj());
            }
        });


        holder.bsd_xzcl_tuhao.setText(list.get(i).getPeij_dw());

        zongjia = shuliangs[i] * list.get(i).getPeij_dj();
        holder.bsd_xzcl_pinpai.setText("" + "" +list.get(i).getPeij_sl()*list.get(i).getPeij_dj());
//        holder.bsd_xzcl_caozuo.setText(list.get(i).get("caozuo"));
        holder.bsd_scl_iv_jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shuliangs[i]++;
//                shuliang++;
                finalHolder.bsd_xzcl_shuliang.setText("" + shuliangs[i]);
                zongjia = shuliangs[i] * list.get(i).getPeij_dj();
                Log.i("cjn","查看这个单价"+list.get(i).getPeij_dj()+"总价"+zongjia+"数量"+shuliangs[i]);
                finalHolder.bsd_xzcl_pinpai.setText("" + zongjia);

                list.get(i).setPeij_sl(shuliangs[i]);
                list.get(i).setPeij_je(zongjia);
                shuliangzongjia.onYesClick(shuliangs[i], zongjia);
            }
        });
        holder.bsd_scl_iv_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shuliangs[i] > 1) {
                    shuliangs[i]--;
                    finalHolder.bsd_xzcl_shuliang.setText("" + shuliangs[i]);
                    zongjia = shuliangs[i] * list.get(i).getPeij_dj();
                    finalHolder.bsd_xzcl_pinpai.setText("" + zongjia);
                    list.get(i).setPeij_sl(shuliangs[i]);
                    list.get(i).setPeij_je(zongjia);
                    shuliangzongjia.onYesClick(shuliangs[i], zongjia);
                }
            }
        });
        holder.bsd_wxyy_cl_delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptdiaog = new TooPromptdiaog(context, "是否删除");
                promptdiaog.setToopromtOnClickListener(new TooPromptdiaog.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick() {
                        list.remove(i);
                        notifyDataSetChanged();
                        promptdiaog.dismiss();
                        deleteCL.onYesClick();
                    }
                });
                promptdiaog.show();
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
    public interface shuliangzongjia {
        public void onYesClick(int shuliang, double zongjia);
    }
    public interface Updanjia {
        public void onYesClick(int i,String name, double danjia);
    }

    //删除
    //删除
    public interface DeleteCL {
        public void onYesClick();
    }
}
