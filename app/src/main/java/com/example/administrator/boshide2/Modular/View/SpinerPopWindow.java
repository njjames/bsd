package com.example.administrator.boshide2.Modular.View;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.administrator.boshide2.Modular.Adapter.AbstractSpinerAdapter;
import com.example.administrator.boshide2.R;

import java.util.List;

public class SpinerPopWindow extends PopupWindow implements OnItemClickListener {

	private Context mContext;
	private ListView mListView;
	private AbstractSpinerAdapter mAdapter;
	private AbstractSpinerAdapter.IOnItemSelectListener mItemSelectListener;

	public SpinerPopWindow(Context context) {
		super(context);

		mContext = context;
		init();
	}

	public void setItemListener(AbstractSpinerAdapter.IOnItemSelectListener listener) {
		mItemSelectListener = listener;
	}

	public void setAdatper(AbstractSpinerAdapter adapter,int aa) {
		mAdapter = adapter;
		mListView.setAdapter(mAdapter);
		LayoutParams params= (LayoutParams) mListView.getLayoutParams();

			params.height=aa;//设置当前控件布局的高度
			mListView.setLayoutParams(params);//将设置好的布局参数应用到控件中



	}public void setAdatper(AbstractSpinerAdapter adapter) {
		mAdapter = adapter;
		mListView.setAdapter(mAdapter);

	}

	private void init() {
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.spiner_window_layout, null);
		setContentView(view);
		setWidth(LayoutParams.WRAP_CONTENT);
		setHeight(LayoutParams.WRAP_CONTENT);

		setFocusable(true);
		ColorDrawable dw = new ColorDrawable(0x00);
		setBackgroundDrawable(dw);

		mListView = (ListView) view.findViewById(R.id.listview);


		mListView.setOnItemClickListener(this);
	}

	public <T> void refreshData(List<T> list, int selIndex) {
		if (list != null && selIndex != -1) {
			if (mAdapter != null) {
				mAdapter.refreshData(list, selIndex);
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
		dismiss();
		if (mItemSelectListener != null) {
			mItemSelectListener.onItemClick(pos);
		}
	}

	@Override
	public void dismiss() {
		// TODO Auto-generated method stub
		super.dismiss();
	}

}
