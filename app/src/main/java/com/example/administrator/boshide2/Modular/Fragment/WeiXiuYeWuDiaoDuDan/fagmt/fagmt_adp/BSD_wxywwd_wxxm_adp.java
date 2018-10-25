package com.example.administrator.boshide2.Modular.Fragment.WeiXiuYeWuDiaoDuDan.fagmt.fagmt_adp;

import android.content.Context;
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

public class BSD_wxywwd_wxxm_adp extends BaseAdapter {
    LayoutInflater layoutInflater;
    Context context;
    DanXiangPaiGong danXiangPaiGong;
    RelativeLayout textView = null;

    UPgs  uPgs;

    private List<BSD_WeiXiuJieDan_XM_Entity> list;
    private int currentPosition = 0;
    private OnOperateItemListener onOperateItemListener;

    public void setList(List<BSD_WeiXiuJieDan_XM_Entity> list) {
        this.list = list;
    }

    public BSD_wxywwd_wxxm_adp(Context context, List<BSD_WeiXiuJieDan_XM_Entity> list) {
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
        TextView bsd_xsbj_name;
        TextView bsd_kxbj_bzsj;
        TextView bsd_kxbj_gsdj;
        TextView bsd_kxbj_je;
        ImageView iv_delete;
        ImageView bsd_kxbj_pg;
        ImageView iv_selected;
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
            holder.iv_delete = (ImageView) contetview.findViewById(R.id.iv_delete);
            holder.bsd_kxbj_pg = (ImageView) contetview.findViewById(R.id.bsd_kxbj_pg);
            holder.iv_selected =(ImageView) contetview.findViewById(R.id.iv_selected);
            contetview.setTag(holder);
        } else {
            holder = (Holder) contetview.getTag();
        }
        holder.bsd_xsbj_name.setText(list.get(i).getWxxm_mc());
        holder.bsd_xsbj_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOperateItemListener != null) {
                    onOperateItemListener.onUpdateWxxmMc(i);
                }
            }
        });

        holder.bsd_kxbj_bzsj.setText("" + list.get(i).getWxxm_gs());
        holder.bsd_kxbj_bzsj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        if(list.get(i).getWxxm_gs()==0){
            list.get(i).setWxxm_gs(1.0);
        }
        DecimalFormat  df=new DecimalFormat("#.##");
        String gs=df.format(list.get(i).getWxxm_je() / list.get(i).getWxxm_gs());
        holder.bsd_kxbj_gsdj.setText(gs);

        holder.bsd_kxbj_je.setText("" + list.get(i).getWxxm_je());
        holder.bsd_kxbj_je.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onOperateItemListener != null) {
                    onOperateItemListener.onUpdateYgsf(i);
                }
            }
        });

        holder.bsd_kxbj_pg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onOperateItemListener != null) {
                    onOperateItemListener.onPaiGong(list.get(i).getWxxm_no());
                }
            }
        });
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onOperateItemListener != null) {
                    onOperateItemListener.onDelete(i);
                }
            }
        });
        if (currentPosition == i) {
            holder.iv_selected.setVisibility(View.VISIBLE);
        } else {
            holder.iv_selected.setVisibility(View.INVISIBLE);
        }
        return contetview;
    }

    public interface DanXiangPaiGong {
        public void onYesClick(int i);
    }
    public interface UPgs {
        public void onYesClick(int i,double gs,double dj,String  xmmc);
    }


    public interface OnOperateItemListener {
        void onPaiGong(String wxxmNo);

        /**
         * 删除维修项目
         * @param position  这个维修项目在集合中的位置
         */
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