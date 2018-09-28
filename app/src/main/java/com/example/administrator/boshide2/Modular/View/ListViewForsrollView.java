package com.example.administrator.boshide2.Modular.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.GridView;
import android.widget.ListView;

public class ListViewForsrollView extends ListView {

	private boolean haveScrollbar=true;

	public ListViewForsrollView(Context context) {
		super(context);

	}

	public ListViewForsrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ListViewForsrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setHaveScrollbar(boolean haveScrollbar) {
		this.haveScrollbar = haveScrollbar;
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}

