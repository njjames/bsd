package com.example.administrator.boshide2.Modular.Fragment.ZaiChangDiaoDU.PopWindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.example.administrator.boshide2.Modular.Activity.MainActivity;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.PopWindow.PopAdapter.BSD_WXJD_CL_PopYou_adp;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.PopWindow.PopAdapter.BSD_WXJD_CL_Popzuo_adp;
import com.example.administrator.boshide2.Modular.Fragment.ZaiChangDiaoDU.PopWindow.PopAdapter.BSD_ZCDD_CL_PopYou_adp;
import com.example.administrator.boshide2.Modular.Fragment.ZaiChangDiaoDU.PopWindow.PopAdapter.BSD_ZCDD_CL_Popzuo_adp;
import com.example.administrator.boshide2.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017-4-18.
 */

public class BSD_ZCDD_CL_POP extends PopupWindow {
    //行布局选中效果
    public int last_item = -1;
    public RelativeLayout oldView;

    private View contentView;
    public Activity context;
    int w;
    int h;
    ListView bsd_pop_lv, bsd_lv;
    BSD_ZCDD_CL_Popzuo_adp adapter;
    List<String> list = new ArrayList<>();
    RelativeLayout bsd_di, bsd_tou;
    List<HashMap<String, String>> data = new ArrayList<>();
    BSD_ZCDD_CL_PopYou_adp adapter1;
    //关闭监听事件
    private Guanbi gb;

    public BSD_ZCDD_CL_POP(final Activity context) {

        this.context = context;
        list.add("全部材料");
        list.add("奔驰");
        list.add("原子灰");
        list.add("五菱");
        list.add("奔驰");
        list.add("原子灰");
        list.add("五菱");
        w = context.getWindowManager().getDefaultDisplay().getWidth();
        h = context.getWindowManager().getDefaultDisplay().getHeight();
        init(w, h);
    }

    public void init(int w, int heitht) {


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.bsd_zcdd_cl_popwin, null);
        //获得 LayoutInflater 的实例
        this.setContentView(contentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(heitht);


        ColorDrawable dw = new ColorDrawable(0xb0ffffff);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

//         设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();
// 设置SelectPicPopupWindow弹出窗体动画效果
        bsd_tou = (RelativeLayout) contentView.findViewById(R.id.bsd_tou);
        bsd_tou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BSD_ZCDD_CL_POP.this != null)
                    BSD_ZCDD_CL_POP.this.dismiss();
                gb.guanbi();
            }
        });
        bsd_di = (RelativeLayout) contentView.findViewById(R.id.bsd_di);
        bsd_di.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BSD_ZCDD_CL_POP.this != null)
                    BSD_ZCDD_CL_POP.this.dismiss();
                gb.guanbi();
            }
        });

//项目选择
        xiangmu();
        liebiao();

    }

    /**
     * 项目数据选择
     */
    public void xiangmu() {
        adapter = new BSD_ZCDD_CL_Popzuo_adp(context, list);
        bsd_pop_lv = (ListView) contentView.findViewById(R.id.bsd_pop_lv);
        bsd_pop_lv.setAdapter(adapter);
        bsd_pop_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                RelativeLayout item = (RelativeLayout) view;
                item.setBackgroundResource(R.drawable.bsd_jiu);//把当前选中的条目加上选中效果
                if (last_item != -1 && last_item != position) {//如果已经单击过条目并且上次保存的item位置和当前位置不同
                    // oldView.setBackgroundColor(Color.WHITE);
                    oldView.setBackgroundResource(R.drawable.article_listview_item_bg);//把上次选中的样式去掉
                }
                oldView = item;//把当前的条目保存下来
                last_item = position;//把当前的位置保存下来


            }
        });

    }

    public void data() {

        for (int i = 0; i < 20; i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put("name", "项目名称" + i + "个");
            map.put("time", "工时" + i + "小时");
            map.put("timemany", "工时价钱" + i + "元");
            map.put("jinqian", "金钱" + i + "元");
            map.put("caozuo", "操作" + i + "+");
            data.add(map);
        }
    }

    /**
     * 列表数据
     */
    public void liebiao() {
        data();
        bsd_lv = (ListView) contentView.findViewById(R.id.bsd_lv);
        adapter1 = new BSD_ZCDD_CL_PopYou_adp(context, data);
        bsd_lv.setAdapter(adapter1);
    }


    public void showPopupWindow(View parent, int hight) {
        if (!this.isShowing() || this != null) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent, 0, hight);
//            WindowManager.LayoutParams lp = context.getWindow().getAttributes();
//            lp.alpha = 0.4f;
//            context.getWindow().setAttributes(lp);

        } else {
            this.dismiss();
        }
    }


    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 屏幕透明度0.0-1.0 1表示完全不透明
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((MainActivity) context).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((MainActivity) context).getWindow().setAttributes(lp);
    }

    public void gb(Guanbi dismiss) {
        this.gb = dismiss;

    }

    public interface Guanbi {
        public void guanbi();


    }

}
