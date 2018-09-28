package com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.PopAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow.Pop_Entity.BSD_wxyy_xm_pop_entiy;
import com.example.administrator.boshide2.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017-4-19.
 */

public class BSD_WXYY_XM_PopYou_adp extends BaseAdapter {
    private Context mContext;
    private LayoutInflater layoutInflater;
    List<BSD_wxyy_xm_pop_entiy> list;
    List<BSD_wxyy_xm_pop_entiy> list1;
    private static HashMap<Integer, Boolean> isSelected;
     chuanwxyy_xm_pop_entity  infoentity;

    public chuanwxyy_xm_pop_entity getInfoentity() {
        return infoentity;
    }

    public void setInfoentity(chuanwxyy_xm_pop_entity infoentity) {
        this.infoentity = infoentity;
    }

    public static HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        BSD_WXYY_XM_PopYou_adp.isSelected = isSelected;
    }


    public void setList(List<BSD_wxyy_xm_pop_entiy> list) {
        this.list = list;

    }

    public BSD_WXYY_XM_PopYou_adp(Context context) {
        this.mContext = context;
        this.layoutInflater = LayoutInflater.from(context);
        isSelected = new HashMap<Integer, Boolean>();


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

    class ViewHolder {
        TextView bsd_xsbj_name,bsd_kxbj_bzsj,bsd_kxbj_gsdj,bsd_kxbj_je,bsd_kxbj_cz;
        TextView bsd_wxyy_xm_cbrl;
    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
         ViewHolder holder=null;
        if (view == null) {
            holder=new ViewHolder();
            view=layoutInflater.inflate(R.layout.bsd_wxyy_xm_popyou_item,null);
            holder.bsd_xsbj_name= (TextView) view.findViewById(R.id.bsd_xsbj_name1);
            holder.bsd_kxbj_bzsj= (TextView) view.findViewById(R.id.bsd_kxbj_bzsj1);
            holder.bsd_kxbj_gsdj= (TextView) view.findViewById(R.id.bsd_kxbj_gsdj1);
            holder.bsd_kxbj_je= (TextView) view.findViewById(R.id.bsd_kxbj_je1);
            holder.bsd_wxyy_xm_cbrl= (TextView) view.findViewById(R.id.iv_add);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.bsd_xsbj_name.setText(list.get(i).getWxxm_mc());
        holder.bsd_kxbj_bzsj.setText(""+list.get(i).getWxxm_gs());
        holder.bsd_kxbj_gsdj.setText(""+list.get(i).getWxxm_dj());
        Log.i("gscxbbb", "适配器里的工时是= "+list.get(i).getWxxm_gs());
        holder.bsd_kxbj_je.setText(""+list.get(i).getWxxm_gs()*list.get(i).getWxxm_dj());
        holder.bsd_wxyy_xm_cbrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoentity.onYesClick(list.get(i),i);
            }
        });






//            holder.bsd_wxyy_xm_cbcb.setChecked(getIsSelected().get(i));
//        if (list.get(i).getItem()==0){
//            holder.bsd_wxyy_xm_cbcb.setChecked(false);
//        }else {
//            holder.bsd_wxyy_xm_cbcb.setChecked(true);
//        }
//
//
//        holder.bsd_wxyy_xm_cbcb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (list.get(i).getItem()==0) {
//                    list.get(i).setItem(1);
////                    getIsSelected().put(i, false);
//                } else {
////                    getIsSelected().put(i, true);
//                    list.get(i).setItem(0);
//                }
//            }
//        });
//        holder.bsd_wxyy_xm_cbcb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (list.get(i).getItem()==0) {
//                    list.get(i).setItem(0);
//                } else {
//                    list.get(i).setItem(1);
//                }
//            }
//        });

        return view;
    }


    public interface chuanwxyy_xm_pop_entity {
        public void onYesClick( BSD_wxyy_xm_pop_entiy entity,int i);
    }
}
