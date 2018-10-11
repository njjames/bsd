package com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.fagmt.fagmt_adp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

public class BSD_wxywwd_wxxm_adp extends BaseAdapter {
    LayoutInflater layoutInflater;
    Context context;
    DanXiangPaiGong danXiangPaiGong;
    RelativeLayout textView = null;
    int last_item;

    Delite delite;
    UPgs  uPgs;
    UPdj uPdj;
    UPmc  uPmc;

    public void setuPgs(UPgs uPgs) {
        this.uPgs = uPgs;
    }

    public void setuPdj(UPdj uPdj) {
        this.uPdj = uPdj;
    }

    public  void  setuPmc(UPmc uPmc){
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

    public BSD_wxywwd_wxxm_adp(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
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
        TextView bsd_shanchuxiangmu, bsd_xsbj_name, bsd_kxbj_bzsj, bsd_kxbj_gsdj, bsd_kxbj_je, bsd_kxbj_qian, bsd_kxbj_cz;
    }

    @Override
    public View getView(final int i, View contetview, ViewGroup viewGroup) {
        Holder holder = null;
        if (contetview == null) {
            textView = new RelativeLayout(context);
            holder = new Holder();
            contetview = layoutInflater.inflate(R.layout.bsd_wxywdd_wxxm_item, null);

            holder.bsd_xsbj_name = (TextView) contetview.findViewById(R.id.bsd_xsbj_name);
            holder.bsd_kxbj_bzsj = (TextView) contetview.findViewById(R.id.bsd_kxbj_bzsj);
            holder.bsd_kxbj_gsdj = (TextView) contetview.findViewById(R.id.bsd_kxbj_gsdj);
            holder.bsd_kxbj_je = (TextView) contetview.findViewById(R.id.bsd_kxbj_je);
            holder.bsd_kxbj_cz = (TextView) contetview.findViewById(R.id.bsd_kxbj_je);
            holder.bsd_shanchuxiangmu= (TextView) contetview.findViewById(R.id.bsd_kxbj_cz1);
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
                uPmc.onYesClick(list.get(i).getReco_no(),list.get(i).getWxxm_gs(),list.get(i).getWxxm_je(),list.get(i).getWxxm_mc());
            }
        });


        holder.bsd_kxbj_bzsj.setText("" + list.get(i).getWxxm_gs());
        holder.bsd_kxbj_bzsj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uPgs.onYesClick(list.get(i).getReco_no(),list.get(i).getWxxm_gs(),list.get(i).getWxxm_je(),list.get(i).getWxxm_mc());
            }
        });

//        String a  = String.valueOf((list.get(i).getWxxm_je()/list.get
//                (i).getWxxm_gs()));
//        if (a.equals("NaN")){
//            holder.bsd_kxbj_gsdj.setText(list.get(i).getWxxm_je()+"");
//        }else {
//            holder.bsd_kxbj_gsdj.setText(""+list.get(i).getWxxm_je
//                    ()/list.get(i).getWxxm_gs());
//        }

        if(list.get(i).getWxxm_gs()==0){
            list.get(i).setWxxm_gs(1.0);
        }
        DecimalFormat  df=new DecimalFormat("#.##");
        String    gs=df.format(list.get(i).getWxxm_je() / list.get(i).getWxxm_gs());
        holder.bsd_kxbj_gsdj.setText(gs);

//        holder.bsd_kxbj_gsdj.setText(list.get(i).getWxxm_dj()+"");

        holder.bsd_kxbj_je.setText("" + list.get(i).getWxxm_je());
        holder.bsd_kxbj_je.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uPdj.onYesClick(list.get(i).getReco_no(),list.get(i).getWxxm_gs(),list.get(i).getWxxm_je(),list.get(i).getWxxm_mc());
            }
        });

        holder.bsd_kxbj_cz.setText("单项派工");
        holder.bsd_kxbj_cz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                danXiangPaiGong.onYesClick(i);
            }
        });
        holder.bsd_shanchuxiangmu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delite.onYesClick(list.get(i).getReco_no(),list.get(i).getWork_no());

                notifyDataSetChanged();
            }
        });
//        holder.bsd_kxbj_qian.setText(list.get(i).get("qian"));
//        holder.bsd_kxbj_qian1.setText(list.get(i).get("qian1"));
//        if (last_item == i) {//解决滑动listview的时候,选中的条目选中效果消失问题
//            // textView.setBackgroundColor(Color.BLUE);
//            textView.setBackgroundResource(R.drawable.bg_article_listview_item_pressed);
//        } else {
//            // textView.setBackgroundColor(Color.WHITE);
//            textView.setBackgroundResource(R.drawable.article_listview_item_bg);
//        }
        return contetview;
    }

    public interface DanXiangPaiGong {
        public void onYesClick(int i);
    }
    public interface Delite {
        public void onYesClick(int i,String workno);
    }
    public interface UPgs {
        public void onYesClick(int i,double gs,double dj,String  xmmc);
    }
    public interface UPdj {
        public void onYesClick(int i,double gs,double dj,String  xmmc);
    }

    public  interface  UPmc{
        public  void  onYesClick(int i,double gs,double dj,String  xmmc);
    }

}