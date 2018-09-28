package com.example.administrator.boshide2.Modular.View;

import android.content.Context;
import android.util.AttributeSet;

/**
 */
public class MarqueeView extends android.support.v7.widget.AppCompatTextView {

	public MarqueeView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MarqueeView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MarqueeView(Context context, AttributeSet attrs,
								 int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean isFocused() {
		return true;
	}

}