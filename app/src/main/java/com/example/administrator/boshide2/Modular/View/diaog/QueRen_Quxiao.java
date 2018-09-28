package com.example.administrator.boshide2.Modular.View.diaog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.administrator.boshide2.R;


public class QueRen_Quxiao extends Dialog implements View.OnClickListener {
	private View view;
	TextView bsd_xiugaimiam_xia;
	TextView bt_toopromptdiaog_quedding;
	TextView bt_toopromptdiaog_quxiao;
	private ToopromtOnClickListener toopromtOnClickListener;
	public QueRen_Quxiao(Context context) {
		super(context, R.style.mydialog);
		getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		// TODO Auto-generated constructor stub
		view = getLayoutInflater().inflate(R.layout.queren_quxiao, null, false);
		bt_toopromptdiaog_quedding=(TextView) view.findViewById(R.id.tv_confirm);
		bt_toopromptdiaog_quedding.setOnClickListener(this);
		bt_toopromptdiaog_quxiao=(TextView) view.findViewById(R.id.tv_cancel);
		bt_toopromptdiaog_quxiao.setOnClickListener(this);
		bsd_xiugaimiam_xia= (TextView) view.findViewById(R.id.bsd_xiugaimiam_xia);
		setContentView(view);
		setCanceledOnTouchOutside(false);

        WindowManager.LayoutParams params =
                this.getWindow().getAttributes();
        params.width = 500;
        params.height = 320 ;
        this.getWindow().setAttributes(params);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_confirm:
			if(toopromtOnClickListener!=null)
			{
				toopromtOnClickListener.onYesClick();
			}
			break;
		case R.id.tv_cancel:
			Log.i("cjn","点击小时");
			this.dismiss();
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
