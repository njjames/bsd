package com.example.administrator.boshide2.Modular.Fragment.HuiYuanGuanLi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Fragment.HuiYuanGuanLi.Entity.BSD_HYGL_ety;
import com.example.administrator.boshide2.R;

import java.util.List;

/**
 * Created by Administrator on 2017-4-18.
 */

/**
 * 博士德维修项目适配器
 */
public class BSD_hygl_adp extends BaseAdapter {
    LayoutInflater layoutInflater;
    Context context;
    List<BSD_HYGL_ety> list;

    public BSD_hygl_adp(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setList(List<BSD_HYGL_ety> list) {
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

    class Holder {
        TextView bsd_xzcl_shengyujiner, bsd_xzcl_name, bsd_xzcl_shuliang, bsd_xzcl_danjia, bsd_xzcl_tuhao, bsd_xzcl_pinpai, bsd_xzcl_caozuo, bsd_xzcl_danxi, bsd_xzcl_qingxi, bsd_xzcl_peijian, bsd_xzcl_keyong, bsd_xzcl_shengyu, bsd_xzcl_jinyong;

    }

    @Override
    public View getView(int i, View contetview, ViewGroup viewGroup) {
        Holder holder = null;
        if (contetview == null) {
            holder = new Holder();
            contetview = layoutInflater.inflate(R.layout.bsd_hygl_item, null);
            holder.bsd_xzcl_name = (TextView) contetview.findViewById(R.id.bsd_xzcl_name);
            holder.bsd_xzcl_shuliang = (TextView) contetview.findViewById(R.id.bsd_xzcl_shuliang);
            holder.bsd_xzcl_danjia = (TextView) contetview.findViewById(R.id.bsd_xzcl_danjia);
            holder.bsd_xzcl_tuhao = (TextView) contetview.findViewById(R.id.bsd_xzcl_tuhao);
            holder.bsd_xzcl_pinpai = (TextView) contetview.findViewById(R.id.bsd_xzcl_pinpai);
//            holder.bsd_xzcl_danxi = (TextView) contetview.findViewById(R.id.bsd_xzcl_danxi);
            holder.bsd_xzcl_qingxi = (TextView) contetview.findViewById(R.id.bsd_xzcl_qingxi);
            holder.bsd_xzcl_peijian = (TextView) contetview.findViewById(R.id.bsd_xzcl_peijian);
            holder.bsd_xzcl_shengyujiner = (TextView) contetview.findViewById(R.id.bsd_xzcl_shengyujiner);

            contetview.setTag(holder);
        } else {
            holder = (Holder) contetview.getTag();
        }

        final BSD_HYGL_ety item = list.get(i);

        holder.bsd_xzcl_name.setSelected(true);
        holder.bsd_xzcl_name.setText(item.getKehu_mc());//卡名称
        holder.bsd_xzcl_shuliang.setText(item.getKehu_dh());//折扣
        holder.bsd_xzcl_danjia.setText(item.getCard_no());//配件折扣
        holder.bsd_xzcl_tuhao.setText(item.getCard_kind());//开户金额
        holder.bsd_xzcl_pinpai.setText(item.getCard_jlrq());//可以金额
//        holder.bsd_xzcl_danxi.setText(item.getCard_lx());//单次普希金额
        holder.bsd_xzcl_qingxi.setText(item.getCard_jb());//单次精洗金额
        holder.bsd_xzcl_shengyujiner.setText(item.getCard_leftje());
        holder.bsd_xzcl_peijian.setText(item.getCard_jifen());//清洗次数
//        holder. bsd_xzcl_keyong.setText(item.getCard_usecs_px());//普洗次数
//        if(item.getFlag_use().equals("flase")){
//            holder.bsd_xzcl_jinyong.setText("否");
//
//        }else {
//            holder.bsd_xzcl_jinyong.setText("是");
//        }
        // holder.bsd_xzcl_shengyu.setText(item.getCard_usecs_px());
        //  holder.bsd_xzcl_jinyong.setText(item.getFlag_use());//禁用
        return contetview;
    }


}