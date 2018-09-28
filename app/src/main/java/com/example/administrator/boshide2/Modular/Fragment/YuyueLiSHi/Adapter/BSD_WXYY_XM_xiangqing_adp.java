package com.example.administrator.boshide2.Modular.Fragment.YuyueLiSHi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.Entity.BSD_WeiXiyYuYue_XM_entity;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.Sql.BSD_WeiXiyYueYue_XM_entity_Dao;
import com.example.administrator.boshide2.Modular.View.diaog.TooPromptdiaog;
import com.example.administrator.boshide2.R;

import java.util.List;

/**
 * Created by Administrator on 2017-4-21.
 */

public class BSD_WXYY_XM_xiangqing_adp extends BaseAdapter {
    LayoutInflater layoutInflater;
    Context context;
    List<BSD_WeiXiyYuYue_XM_entity>list;
    TooPromptdiaog promptdiaog;
    public BSD_WeiXiyYueYue_XM_entity_Dao bsdwxyy_xm_dao;//维修项目
    EditText edtext;
    upgongshi upgongshi;
    upgongshidanjia upgongshidanjia;
  ShanchuXM shanchuXM;
    public void setShanchuXM(ShanchuXM shanchuXM) {
        this.shanchuXM = shanchuXM;
    }


    public void setUpgongshidanjia(BSD_WXYY_XM_xiangqing_adp.upgongshidanjia upgongshidanjia) {
        this.upgongshidanjia = upgongshidanjia;
    }

    public void setUpgongshi(BSD_WXYY_XM_xiangqing_adp.upgongshi upgongshi) {
        this.upgongshi = upgongshi;
    }

    public void setList(List<BSD_WeiXiyYuYue_XM_entity> list) {
        this.list = list;
    }

    public BSD_WXYY_XM_xiangqing_adp(Context context) {
        bsdwxyy_xm_dao=new BSD_WeiXiyYueYue_XM_entity_Dao(context);
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
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
        RelativeLayout bsd_wxyy_xm_shanchu,bsd_wxyy_top;
        TextView bsd_xzcl_shuliang;
        TextView bsd_xzcl_name,  bsd_xzcl_danjia, bsd_xzcl_tuhao, bsd_xzcl_pinpai, bsd_xzcl_caozuo;
    }

    @Override
    public View getView(final int i, View contetview, ViewGroup viewGroup) {
        Holder holder = null;
        if (contetview == null) {
            holder = new Holder();
            contetview = layoutInflater.inflate(R.layout.bsd_wxyy_xzxm_xiangqing_item, null);
            holder.bsd_xzcl_name = (TextView) contetview.findViewById(R.id.bsd_xzcl_name);
            holder.bsd_xzcl_shuliang = (TextView) contetview.findViewById(R.id.bsd_xzcl_shuliang);
            holder.bsd_xzcl_danjia = (TextView) contetview.findViewById(R.id.bsd_xzcl_danjia);
            holder.bsd_xzcl_tuhao = (TextView) contetview.findViewById(R.id.bsd_xzcl_tuhao);
            holder.bsd_xzcl_pinpai = (TextView) contetview.findViewById(R.id.bsd_xzcl_pinpai);
            holder.bsd_wxyy_xm_shanchu= (RelativeLayout) contetview.findViewById(R.id.bsd_wxyy_xm_shanchu);
            holder.bsd_wxyy_top= (RelativeLayout) contetview.findViewById(R.id.bsd_wxyy_top);
//  holder.bsd_xzcl_caozuo = (TextView) contetview.findViewById(R.id.bsd_xzcl_caozuo);
            contetview.setTag(holder);
        } else {
            holder = (Holder) contetview.getTag();
        }





        holder.bsd_xzcl_name.setText(list.get(i).getWxxm_mc());
        //工时
        holder.bsd_xzcl_shuliang.setText(""+list.get(i).getWxxm_gs());

        //工时单价
        holder.bsd_xzcl_danjia.setText(""+list.get(i).getWxxm_dj());



        holder.bsd_xzcl_tuhao.setText( ""+list.get(i).getWxxm_gs()*list.get(i).getWxxm_dj());
        holder.bsd_xzcl_pinpai.setText(list.get(i).getWxxm_zt());















//         TextWatcher textWatcher = new TextWatcher() {
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                // TODO Auto-generated method stub
//                Log.i("cjn","ccccc"+edtext.getText().toString());
//
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//                // TODO Auto-generated method stub
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before,
//                                      int count) {
//                Log.d("TAG","onTextChanged--------------->");
////        Log.i("cjn","aaaaa"+edtext.getText().toString());
//                try {
//                    //if ((heighText.getText().toString())!=null)
////                Integer.parseInt(str);
//
//                } catch (Exception e) {
//                    // TODO: handle exception
////                showDialog();
//                }
//
//            }
//        };


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

}
