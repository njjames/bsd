package com.example.administrator.boshide2.Modular.View.diaog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.administrator.boshide2.R;


public class Queding_Quxiao extends Dialog implements View.OnClickListener {
	private View view;
	private TextView tv_confirm;
	private TextView tv_cancel;
	private TextView tv_tip;
	private OnResultClickListener resultClickListener;

	public Queding_Quxiao(Context context, String msg) {
		super(context, R.style.mydialog);
		getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		view = getLayoutInflater().inflate(R.layout.queding_quxiao, null, false);
		tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
		tv_confirm.setOnClickListener(this);
		tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
		tv_cancel.setOnClickListener(this);
		tv_tip = (TextView) view.findViewById(R.id.tv_tips);
		tv_tip.setText(msg);
		setContentView(view);
		setCanceledOnTouchOutside(false);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_confirm:
			if (resultClickListener != null) {
				resultClickListener.onConfirm();;
			}
			break;
		case R.id.tv_cancel:
			if (resultClickListener != null) {
				resultClickListener.onCancel();
			};
			break;
		}
	}

	public interface OnResultClickListener {
		public void onConfirm();

		public void onCancel();
	}

	public void setOnResultClickListener(OnResultClickListener onResultClickListener) {
		resultClickListener = onResultClickListener;
	}
}
