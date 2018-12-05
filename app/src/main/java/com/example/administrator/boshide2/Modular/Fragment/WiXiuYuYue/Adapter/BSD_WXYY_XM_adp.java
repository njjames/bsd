package com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.Entity.BSD_WeiXiyYuYue_XM_entity;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.Sql.BSD_WeiXiyYueYue_XM_entity_Dao;
import com.example.administrator.boshide2.Modular.View.diaog.TooPromptdiaog;
import com.example.administrator.boshide2.R;
import com.lidong.photopicker.Image;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Administrator on 2017-4-21.
 */
public class BSD_WXYY_XM_adp extends BaseAdapter {
    LayoutInflater layoutInflater;
    Context context;
    List<BSD_WeiXiyYuYue_XM_entity> list;
    TooPromptdiaog promptdiaog;
    private BSD_WeiXiyYueYue_XM_entity_Dao bsdwxyy_xm_dao;//维修项目
    upgongshi upgongshi;
    upgongshidanjia upgongshidanjia;
    ShanchuXM shanchuXM;
    private OnOperateItemListener onOperateItemListener;

    public void setShanchuXM(ShanchuXM shanchuXM) {
        this.shanchuXM = shanchuXM;
    }


    public void setUpgongshidanjia(BSD_WXYY_XM_adp.upgongshidanjia upgongshidanjia) {
        this.upgongshidanjia = upgongshidanjia;
    }

    public void setUpgongshi(BSD_WXYY_XM_adp.upgongshi upgongshi) {
        this.upgongshi = upgongshi;
    }

    public void setList(List<BSD_WeiXiyYuYue_XM_entity> list) {
        this.list = list;
    }

    public BSD_WXYY_XM_adp(Context context, List<BSD_WeiXiyYuYue_XM_entity> list) {
        bsdwxyy_xm_dao = new BSD_WeiXiyYueYue_XM_entity_Dao(context);
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
        TextView bsd_xzcl_gs;
        TextView bsd_xzcl_name;
        TextView bsd_xzcl_danjia;
        TextView bsd_xzcl_je;
        TextView bsd_xzcl_zt;
        ImageView iv_delete;
    }

    @Override
    public View getView(final int i, View contetview, ViewGroup viewGroup) {
        Holder holder = null;
        if (contetview == null) {
            holder = new Holder();
            contetview = layoutInflater.inflate(R.layout.bsd_wxyy_xzxm_item, null);
            holder.bsd_xzcl_name = (TextView) contetview.findViewById(R.id.bsd_xzcl_name);
            holder.bsd_xzcl_gs = (TextView) contetview.findViewById(R.id.bsd_xzcl_gs);
            holder.bsd_xzcl_danjia = (TextView) contetview.findViewById(R.id.bsd_xzcl_danjia);
            holder.bsd_xzcl_je = (TextView) contetview.findViewById(R.id.bsd_xzcl_je);
            holder.bsd_xzcl_zt = (TextView) contetview.findViewById(R.id.bsd_xzcl_zt);
            holder.iv_delete = (ImageView) contetview.findViewById(R.id.iv_delete);
            contetview.setTag(holder);
        } else {
            holder = (Holder) contetview.getTag();
        }
        holder.bsd_xzcl_name.setText(list.get(i).getWxxm_mc());
        if (list.get(i).getWxxm_gs() == 0) {
            list.get(i).setWxxm_gs(1.0);
        }
        holder.bsd_xzcl_gs.setText("" + list.get(i).getWxxm_gs());
        holder.bsd_xzcl_gs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upgongshi.onYesClick(i, list.get(i).getWxxm_mc(), list.get(i).getWxxm_gs());
            }
        });
        holder.bsd_xzcl_danjia.setText("" + list.get(i).getWxxm_dj());
        holder.bsd_xzcl_je.setText("" + list.get(i).getWxxm_je());
        holder.bsd_xzcl_je.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onOperateItemListener != null) {
                    onOperateItemListener.onUpdateYgsf(i);
                }
            }
        });
        holder.bsd_xzcl_zt.setText(list.get(i).getWxxm_zt());
        //删除
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onOperateItemListener != null) {
                    onOperateItemListener.onDelete(i);
                }
            }
        });
        return contetview;
    }

    public interface upgongshi {
        public void onYesClick(int i, String name, double gongshi);
    }

    public interface upgongshidanjia {
        public void onYesClick(int i, String name, double gongshidanjia);
    }

    //删除项目
    public interface ShanchuXM {
        public void onYesClick();
    }

    public interface OnOperateItemListener {
        void onDelete(int position);

        void onUpdateYgsf(int position);
    }

    public void setOnOperateItemListener(OnOperateItemListener onOperateItemListener) {
        this.onOperateItemListener = onOperateItemListener;
    }

}
