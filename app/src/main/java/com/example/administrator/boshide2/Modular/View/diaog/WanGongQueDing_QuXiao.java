package com.example.administrator.boshide2.Modular.View.diaog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.administrator.boshide2.R;


public class WanGongQueDing_QuXiao extends Dialog implements View.OnClickListener {
	private View view;
	private TextView tv_confirm;
	private TextView tv_cancel;
	private TextView tv_tips;
	Quxiao quxiao;

	public void setQuxiao(Quxiao quxiao) {
		this.quxiao = quxiao;
	}

	private ToopromtOnClickListener toopromtOnClickListener;
	public WanGongQueDing_QuXiao(Context context, String a) {
		super(context, R.style.mydialog);
		getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		view = getLayoutInflater().inflate(R.layout.queding_quxiao, null, false);
		tv_confirm =(TextView) view.findViewById(R.id.tv_confirm);
		tv_confirm.setOnClickListener(this);
		tv_cancel =(TextView) view.findViewById(R.id.tv_cancel);
		tv_cancel.setOnClickListener(this);
		tv_tips = (TextView) view.findViewById(R.id.tv_tips);
		tv_tips.setText(a);
		setContentView(view);
		setCanceledOnTouchOutside(false);

        WindowManager.LayoutParams params =
                this.getWindow().getAttributes();
        params.width = getContext().getResources().getDimensionPixelSize(R.dimen.qb_px_320);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(params);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_confirm:
			if(toopromtOnClickListener!=null) {
				toopromtOnClickListener.onYesClick();
			}
			break;
		case R.id.tv_cancel:
			quxiao.onYesClick();
			break;
		}
	}
	public void setToopromtOnClickListener(ToopromtOnClickListener toopromtOnClickListener) {
		this.toopromtOnClickListener = toopromtOnClickListener;
	}

	public interface ToopromtOnClickListener {
		public void onYesClick();

	}

	public interface Quxiao {
		public void onYesClick();
	}
}
