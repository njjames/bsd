package com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.Adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.boshide2.Modular.View.tree.bean.Node;

import com.example.administrator.boshide2.Modular.View.tree.bean.TreeListViewAdapter;
import com.example.administrator.boshide2.R;
import com.lidong.photopicker.Image;


public class SimpleTreeAdapter<T> extends TreeListViewAdapter<T> {

    public SimpleTreeAdapter(ListView mTree, Context context, List<T> datas, int defaultExpandLevel)
            throws IllegalArgumentException, IllegalAccessException {
        super(mTree, context, datas, defaultExpandLevel);
    }

    @Override
    public View getConvertView(Node node, int position, View convertView, ViewGroup parent, int currentPosition) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.id_treenode_icon);
            viewHolder.label = (TextView) convertView.findViewById(R.id.id_treenode_label);
            viewHolder.iv_selected = (ImageView) convertView.findViewById(R.id.id_select_icon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (node.getIcon() == -1) {
            viewHolder.icon.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.icon.setVisibility(View.VISIBLE);
            viewHolder.icon.setImageResource(node.getIcon());
        }
        if (currentPosition != -1 && currentPosition == position) { // 说明点击的是这
            viewHolder.iv_selected.setVisibility(View.VISIBLE);
        } else {
            viewHolder.iv_selected.setVisibility(View.INVISIBLE);
        }
        viewHolder.label.setText(node.getName());
        return convertView;
    }

    private final class ViewHolder {
        ImageView icon;
        TextView label;
        ImageView iv_selected;
    }

}
