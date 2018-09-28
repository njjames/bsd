package com.example.administrator.boshide2.Modular.View.diaog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.administrator.boshide2.R;


public class GengXinWanCheng extends Dialog implements View.OnClickListener {
	private View view;
	TextView bt_toopromptdiaog_quxiao;
	private ToopromtOnClickListener toopromtOnClickListener;
	private Dialog mWeiboDialog;
	public GengXinWanCheng(Context context) {
		super(context, R.style.mydialog);
		getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		// TODO Auto-generated constructor stub
		view = getLayoutInflater().inflate(R.layout.gengxinwancheng, null, false);
		bt_toopromptdiaog_quxiao=(TextView) view.findViewById(R.id.tv_confirm);
		bt_toopromptdiaog_quxiao.setOnClickListener(this);
		setContentView(view);
		setCanceledOnTouchOutside(false);

        WindowManager.LayoutParams params =
                this.getWindow().getAttributes();
        params.width = 500;
        params.height = 260 ;
        this.getWindow().setAttributes(params);

	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
//		case R.id.bsd_xgmm_queren:
//			if(toopromtOnClickListener!=null)
//			{
//				toopromtOnClickListener.onAdd();
//			}
//			break;
		case R.id.tv_confirm:
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
