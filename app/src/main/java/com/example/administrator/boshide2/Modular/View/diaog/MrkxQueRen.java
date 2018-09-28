package com.example.administrator.boshide2.Modular.View.diaog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.administrator.boshide2.R;


public class MrkxQueRen extends Dialog implements View.OnClickListener {
	private View view;
	TextView qeuren;
	TextView neirong;

	public Guanbi getGb() {
		return gb;
	}

	public void setGb(Guanbi gb) {
		this.gb = gb;
	}

	Guanbi gb;
	private ToopromtOnClickListener toopromtOnClickListener;
	public MrkxQueRen(Context context, String nr) {
		super(context, R.style.mydialog);
		getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		// TODO Auto-generated constructor stub
		view = getLayoutInflater().inflate(R.layout.queren, null, false);
		qeuren=(TextView) view.findViewById(R.id.bsd_queren_queren);
		neirong = (TextView) view.findViewById(R.id.bsd_queren_neirong);
		neirong.setText(nr);
		qeuren.setOnClickListener(this);
		setContentView(view);
		setCanceledOnTouchOutside(false);

        WindowManager.LayoutParams params =
                this.getWindow().getAttributes();
        params.width = (int) getContext().getResources().getDimension(R.dimen.x150);
        params.height = (int) getContext().getResources().getDimension(R.dimen.y220);
        this.getWindow().setAttributes(params);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bsd_queren_queren:
			gb.guanbi();
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
	public interface Guanbi {
		public void guanbi();
	}

}
