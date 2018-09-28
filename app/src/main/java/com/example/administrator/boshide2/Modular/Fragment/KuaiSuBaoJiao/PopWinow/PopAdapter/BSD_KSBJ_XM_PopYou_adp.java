package com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.PopWinow.PopAdapter;


/**
 * Created by Administrator on 2017-4-19.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.Pop_Entity.BSD_wxyy_xm_pop_entiy;
import com.example.administrator.boshide2.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 博士德popwind个全部项目选项
 */
public class BSD_KSBJ_XM_PopYou_adp extends BaseAdapter {
    private Context mContext;
    List<BSD_wxyy_xm_pop_entiy> mLists;
    private static HashMap<Integer, Boolean> isSelected;
    private OnAddListener onAddListener;

    private List<String> chooseLists = new ArrayList<>(); // 使用一个集合用来存储当前已经选择的Item

    public static HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        BSD_KSBJ_XM_PopYou_adp.isSelected = isSelected;
    }


    public void setmLists(List<BSD_wxyy_xm_pop_entiy> list) {
        this.mLists = list;
    }

    public BSD_KSBJ_XM_PopYou_adp(Context context, List<BSD_wxyy_xm_pop_entiy> list) {
        this.mContext = context;
        this.mLists = list;
        isSelected = new HashMap<Integer, Boolean>();
    }

    public BSD_KSBJ_XM_PopYou_adp(Context context) {
        this.mContext = context;
        isSelected = new HashMap<Integer, Boolean>();
    }

    @Override
    public int getCount() {
        return mLists.size();
    }

    @Override
    public Object getItem(int i) {
        return mLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class ViewHolder {
        TextView bsd_xsbj_name;
        TextView bsd_kxbj_bzsj;
        TextView bsd_kxbj_gsdj;
        TextView bsd_kxbj_je;
        ImageView bsd_wxyy_xm_cbcb;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.bsd_wxyy_xm_popyou_item, null);
            holder.bsd_xsbj_name = (TextView) view.findViewById(R.id.bsd_xsbj_name1);
            holder.bsd_kxbj_bzsj = (TextView) view.findViewById(R.id.bsd_kxbj_bzsj1);
            holder.bsd_kxbj_gsdj = (TextView) view.findViewById(R.id.bsd_kxbj_gsdj1);
            holder.bsd_kxbj_je = (TextView) view.findViewById(R.id.bsd_kxbj_je1);
            holder.bsd_wxyy_xm_cbcb = (ImageView) view.findViewById(R.id.iv_add);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.bsd_xsbj_name.setText(mLists.get(i).getWxxm_mc());
        holder.bsd_kxbj_bzsj.setText("" + mLists.get(i).getWxxm_gs());
        holder.bsd_kxbj_gsdj.setText("" + mLists.get(i).getWxxm_dj());
        double v = (Math.round(mLists.get(i).getWxxm_gs() * mLists.get(i).getWxxm_dj() * 100) / 100.0);
        holder.bsd_kxbj_je.setText("" + v);
        holder.bsd_wxyy_xm_cbcb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onAddListener != null) {
                    onAddListener.onAdd(mLists.get(i));
                }
            }
        });
        if (chooseLists.contains(mLists.get(i).getWxxm_no())) {
            holder.bsd_wxyy_xm_cbcb.setImageResource(R.drawable.ic_add_success);
        } else {
            holder.bsd_wxyy_xm_cbcb.setImageResource(R.drawable.ic_add);
        }
        return view;
    }

    public interface OnAddListener {
        public void onAdd(BSD_wxyy_xm_pop_entiy entity);
    }

    public void setOnAddListener(OnAddListener onAddListener) {
        this.onAddListener = onAddListener;
    }

    public void addChooedWxxm(String wxxmNo) {
        chooseLists.add(wxxmNo);
    }
}
