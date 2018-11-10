package com.example.administrator.boshide2.Modular.View.diaog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.boshide2.R;


public class QueRen extends Dialog implements View.OnClickListener {
	private View view;
	private TextView qeuren;
	private TextView neirong;
	private ToopromtOnClickListener toopromtOnClickListener;

	public QueRen(Context context,String nr) {
		super(context, R.style.mydialog);
		getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		view = getLayoutInflater().inflate(R.layout.queren, null, false);
		qeuren=(TextView) view.findViewById(R.id.bsd_queren_queren);
		neirong = (TextView) view.findViewById(R.id.bsd_queren_neirong);
		neirong.setText(nr);
		qeuren.setOnClickListener(this);
		setContentView(view);
		setCanceledOnTouchOutside(false);

        WindowManager.LayoutParams params =
                this.getWindow().getAttributes();
		params.width = getContext().getResources().getDimensionPixelSize(R.dimen.qb_px_300);
		params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(params);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bsd_queren_queren:
			toopromtOnClickListener.onYesClick();
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
