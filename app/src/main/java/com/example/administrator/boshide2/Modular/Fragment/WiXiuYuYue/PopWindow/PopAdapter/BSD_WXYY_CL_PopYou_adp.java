package com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.PopAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.Pop_Entity.BSD_wxyy_cl_pop_entity;
import com.example.administrator.boshide2.Modular.View.MarqueeView;
import com.example.administrator.boshide2.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-4-19.
 */

public class BSD_WXYY_CL_PopYou_adp extends BaseAdapter {
    private Context mContext;
    private List<BSD_wxyy_cl_pop_entity> list;
    private OnAddListener onAddListeber;

    private Map<String, Double> chooseMaps = new HashMap<>(); // 使用一个map用来存储当前已经选择的Item和添加的数量


    public void setList(List<BSD_wxyy_cl_pop_entity> list) {
        this.list = list;
    }

    public BSD_WXYY_CL_PopYou_adp(Context context) {
        this.mContext = context;
    }

    public BSD_WXYY_CL_PopYou_adp(Context context, List<BSD_wxyy_cl_pop_entity> list) {
        this.mContext = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return (list==null)?0:list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class ViewHolder {
        TextView bad_wxyy_cl_you_tv6;
        TextView bsd_xsbj_name;
        TextView bsd_kxbj_bzsj;
        TextView bsd_kxbj_gsdj;
        TextView bsd_kxbj_je;
        MarqueeView  bsd_mv_cl_cx;
        ImageView iv_add;
        ImageView iv_subtract;
        TextView tv_count;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.bsd_wxyy_cl_popyou_item, null);
            holder.bsd_xsbj_name = (TextView) view.findViewById(R.id.bad_wxyy_cl_you_tv1);
            holder.bsd_kxbj_bzsj = (TextView) view.findViewById(R.id.bad_wxyy_cl_you_tv2);
            holder.bsd_kxbj_gsdj = (TextView) view.findViewById(R.id.bad_wxyy_cl_you_tv3);
            holder.bsd_kxbj_je = (TextView) view.findViewById(R.id.bad_wxyy_cl_you_tv4);
            holder.bad_wxyy_cl_you_tv6= (TextView) view.findViewById(R.id.bad_wxyy_cl_you_tv6);
            holder.bsd_mv_cl_cx= (MarqueeView) view.findViewById(R.id.bsd_mv_cl_cx);
            holder.iv_add = (ImageView) view.findViewById(R.id.iv_add);
            holder.iv_subtract = (ImageView) view.findViewById(R.id.iv_subtract);
            holder.tv_count = (TextView) view.findViewById(R.id.tv_count);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.bsd_xsbj_name.setText(list.get(i).getPeij_mc());
        holder.bsd_kxbj_bzsj.setText(list.get(i).getQxjp());
        holder.bsd_mv_cl_cx.setText(list.get(i).getPeij_cx());
        holder.bsd_kxbj_gsdj.setText(list.get(i).getPeij_dw());
        holder.bsd_kxbj_je.setText(list.get(i).getPeij_th());
        holder.bad_wxyy_cl_you_tv6.setText(list.get(i).getPeij_kc());
        holder.iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onAddListeber != null) {
                    onAddListeber.onAdd(list.get(i));
                }
            }
        });
        holder.iv_subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onAddListeber != null) {
                    onAddListeber.onSubtract(list.get(i));
                }
            }
        });
        if (chooseMaps.containsKey(list.get(i).getPeij_no()) && chooseMaps.get(list.get(i).getPeij_no()) != 0) {
            holder.tv_count.setText(chooseMaps.get(list.get(i).getPeij_no()) + "");
            holder.iv_subtract.setVisibility(View.VISIBLE);
            holder.tv_count.setVisibility(View.VISIBLE);
        } else {
            holder.iv_subtract.setVisibility(View.GONE);
            holder.tv_count.setVisibility(View.GONE);
            holder.tv_count.setText("0");
        }
        return view;
    }

    public interface OnAddListener {
        public void onAdd(BSD_wxyy_cl_pop_entity entity);

        public void onSubtract(BSD_wxyy_cl_pop_entity entity);
    }

    public void setOnAddListener(OnAddListener onAddListener) {
        this.onAddListeber = onAddListener;
    }

    /**
     * 默认加一个
     * @param itemNo
     */
    public void addChooedItem(String itemNo) {
        if (chooseMaps.containsKey(itemNo)) {
            chooseMaps.put(itemNo, chooseMaps.get(itemNo) + 1);
        } else {
            chooseMaps.put(itemNo, (double) 1);
        }
    }

    public void subtractChooedItem(String itemNo) {
        if (chooseMaps.containsKey(itemNo)) {
            chooseMaps.put(itemNo, chooseMaps.get(itemNo) - 1);
        }
    }

    public void addTempMapItem(String itemNo, double count) {
        chooseMaps.put(itemNo, count);
    }
}
