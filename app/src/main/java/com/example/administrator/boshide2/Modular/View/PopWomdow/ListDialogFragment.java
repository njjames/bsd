package com.example.administrator.boshide2.Modular.View.PopWomdow;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.PopWinow.PopAdapter.BSD_KSBJ_XM_PopYou_adp;
import com.example.administrator.boshide2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-4-19.
 */

public class ListDialogFragment extends BaseDialogFragment {
    public  int last_item = -1;
    public RelativeLayout oldView;
    ListView bsd_pop_lv;
    BSD_KSBJ_XM_PopYou_adp adapter;
    String[] names1 = {"发动机项目", "变速器项目", "美容套餐", "发动机项目", "变速器项目", "美容套餐"};
    List<String> list = new ArrayList<>();
    public static ListDialogFragment getInstance(String ...items){
        ListDialogFragment listDialogFragment = new ListDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArray("items", items);
        listDialogFragment.setArguments(bundle);
        return listDialogFragment;
    }



    @Override
    protected int getContentLayout() {
        return R.layout.bsd_popwin;
    }

    @Override
    protected void init() {

//        lv_list.setBackgroundResource(R.drawable.shape_load);
//        ListDialogAdapter mApdater = new ListDialogAdapter(getActivity());
//        mApdater.setDataToAdapter(getArguments().getStringArray("items"));
//        lv_list.setAdapter(mApdater);

//        adapter = new BSD_KSBJ_XM_PopYou_adp(getActivity(), list);
//        bsd_pop_lv = (ListView)findViewById(R.id.bsd_pop_lv);
//        bsd_pop_lv.setAdapter(adapter);
//        bsd_pop_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                RelativeLayout item = (RelativeLayout) view;
//                item.setBackgroundResource(R.drawable.bsd_jiu);//把当前选中的条目加上选中效果
//                if (last_item != -1 && last_item != position) {//如果已经单击过条目并且上次保存的item位置和当前位置不同
//                    // oldView.setBackgroundColor(Color.WHITE);
//                    oldView.setBackgroundResource(R.drawable.article_listview_item_bg);//把上次选中的样式去掉
//                }
//                oldView = item;//把当前的条目保存下来
//                last_item = position;//把当前的位置保存下来
//
//
//            }
//        });
//
//

    }

    @Override
    protected int getWidth() {
        return 800;
    }

    @Override
    protected int getHeight() {
        return 800;
    }

    @Override
    protected int getGrivaty() {

        return Gravity.CENTER;

    }

    @Override
    protected int getWindowAnimations() {
        return 0;
    }

    @Override
    protected int getDialogBackground() {
        return R.color.bsd_xz_yes;
    }
}
