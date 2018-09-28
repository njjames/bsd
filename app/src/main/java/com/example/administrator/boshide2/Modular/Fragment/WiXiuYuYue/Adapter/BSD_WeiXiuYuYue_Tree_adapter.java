package com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.Adapter;

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

import java.util.List;


public class BSD_WeiXiuYuYue_Tree_adapter<T> extends TreeListViewAdapter<T> {

    public BSD_WeiXiuYuYue_Tree_adapter(ListView mTree, Context context, List<T> datas,
                                        int defaultExpandLevel) throws IllegalArgumentException,
            IllegalAccessException {

        super(mTree, context, datas, defaultExpandLevel);

    }

    @Override
    public View getConvertView(Node node, int position, View convertView, ViewGroup parent, int currentPosition) {
        Log.i("cjn", "这是适配器里面");
        ViewHolder viewHolder = null;
        if (convertView == null) {
            Log.i("cjn", "这是适配器里面0");
            convertView = mInflater.inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.icon = (ImageView) convertView
                    .findViewById(R.id.id_treenode_icon);
            viewHolder.label = (TextView) convertView
                    .findViewById(R.id.id_treenode_label);
            Log.i("cjn", "这是适配器里面1");
            convertView.setTag(viewHolder);

        } else {
            Log.i("cjn", "这是适配器里面2");
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (node.getIcon() == -1) {
            viewHolder.icon.setVisibility(View.INVISIBLE);
            Log.i("cjn", "这是适配器里面3");
        } else {
            viewHolder.icon.setVisibility(View.VISIBLE);
            Log.i("cjn", "这是适配器里面4");
            viewHolder.icon.setImageResource(node.getIcon());
        }

        Log.i("cjn", "这是适配器里面5");
        viewHolder.label.setText(node.getName());


        return convertView;
    }

    private final class ViewHolder {
        ImageView icon;
        TextView label;
    }

}
