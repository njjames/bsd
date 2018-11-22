package com.example.administrator.boshide2.Modular.View.diaog;

import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.boshide2.R;


public class ChangePageDialog extends Dialog implements View.OnClickListener {
	private View view;
	private TextView bt_toopromptdiaog_quedding;
	private TextView bt_toopromptdiaog_quxiao;
	private TextView tips;
	private OnResultClickListener resultClickListener;

	public ChangePageDialog(Context context, String from, String to) {
		super(context, R.style.mydialog);
		getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		view = getLayoutInflater().inflate(R.layout.queding_quxiao, null, false);
		bt_toopromptdiaog_quedding = (TextView) view.findViewById(R.id.tv_confirm);
		bt_toopromptdiaog_quedding.setOnClickListener(this);
		bt_toopromptdiaog_quxiao = (TextView) view.findViewById(R.id.tv_cancel);
		bt_toopromptdiaog_quxiao.setOnClickListener(this);
		Spanned tipsstr =
                Html.fromHtml("您即将离开<font color=#00bbaa>" + from + "</font>页面，并前往<font color=#F7714C>" + to + "</font>页面");
		tips = (TextView) view.findViewById(R.id.tv_tips);
		tips.setText(tipsstr);
		setContentView(view);
		setCanceledOnTouchOutside(false);
		WindowManager.LayoutParams params = this.getWindow().getAttributes();
		params.width = getContext().getResources().getDimensionPixelSize(R.dimen.qb_px_400);
		params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
		this.getWindow().setAttributes(params);
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
		void onConfirm();

		void onCancel();
	}

	public void setOnResultClickListener(OnResultClickListener onResultClickListener) {
		resultClickListener = onResultClickListener;
	}
}
