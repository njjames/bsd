package com.example.administrator.boshide2.Modular.View.diaog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.boshide2.R;


public class TooPromptdiaog extends Dialog implements View.OnClickListener {
	private View view;
	TextView tv_toopromptdiaog_neirong;
	TextView bt_toopromptdiaog_quedding;
	TextView bt_toopromptdiaog_quxiao;
	private Display display;
	private ToopromtOnClickListener toopromtOnClickListener;
	private LinearLayout ll_promptdiaog;
	public TooPromptdiaog(Context context,String st) {
		super(context, R.style.mydialog);
		view = getLayoutInflater().inflate(R.layout.toopromptdiaog, null, false);
		tv_toopromptdiaog_neirong = (TextView) view.findViewById(R.id.tv_toopromptdiaog_neirong);
		tv_toopromptdiaog_neirong.setText(st);
		bt_toopromptdiaog_quedding = (TextView) view.findViewById(R.id.bt_toopromptdiaog_quedding);
		bt_toopromptdiaog_quedding.setOnClickListener(this);
		bt_toopromptdiaog_quxiao = (TextView) view.findViewById(R.id.bt_toopromptdiaog_quxiao);
		bt_toopromptdiaog_quxiao.setOnClickListener(this);
		setContentView(view);
		setCanceledOnTouchOutside(false);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_toopromptdiaog_quedding:
			if(toopromtOnClickListener!=null)
			{
				toopromtOnClickListener.onYesClick();
			}
			break;
		case R.id.bt_toopromptdiaog_quxiao:
			dismiss();
			break;
		}
	}
	public void setToopromtOnClickListener(ToopromtOnClickListener toopromtOnClickListener) {
		this.toopromtOnClickListener = toopromtOnClickListener;
	}
	public interface ToopromtOnClickListener {
		public void onYesClick();
	

	}
}
