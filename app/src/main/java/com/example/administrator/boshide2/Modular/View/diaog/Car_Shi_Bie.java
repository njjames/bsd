package com.example.administrator.boshide2.Modular.View.diaog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.boshide2.R;


public class Car_Shi_Bie extends Dialog implements View.OnClickListener {
	private View view;
	TextView bt_toopromptdiaog_quedding;
	TextView bt_toopromptdiaog_quxiao;
	SetUp setUp;
	EditText bsd_xiugaimiam_xia;
			SetYes setYes;
	String   chepai;

	public void setSetUp(SetUp setUp) {
		this.setUp = setUp;
	}

	public void setSetYes(SetYes setYes) {
		this.setYes = setYes;
	}

	public Car_Shi_Bie(Context context,String cp) {
		super(context, R.style.mydialog);
		getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		// TODO Auto-generated constructor stub
		view = getLayoutInflater().inflate(R.layout.car_shi_bie, null, false);
		bt_toopromptdiaog_quedding=(TextView) view.findViewById(R.id.tv_confirm);
		bt_toopromptdiaog_quedding.setOnClickListener(this);
		bt_toopromptdiaog_quxiao=(TextView) view.findViewById(R.id.tv_cancel);
		bt_toopromptdiaog_quxiao.setOnClickListener(this);
		bsd_xiugaimiam_xia= (EditText) view.findViewById(R.id.bsd_xiugaimiam_xia);
		bsd_xiugaimiam_xia.setText(cp);
		bsd_xiugaimiam_xia.setEnabled(false);
		setContentView(view);
		setCanceledOnTouchOutside(false);

        WindowManager.LayoutParams params =
                this.getWindow().getAttributes();
        params.width = (int) getContext().getResources().getDimension(R.dimen.x160);
        params.height = (int) getContext().getResources().getDimension(R.dimen.y300) ;
        this.getWindow().setAttributes(params);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_confirm:
			chepai=bsd_xiugaimiam_xia.getText().toString();
			if(setYes!=null)
			{
				setYes.onyes(chepai);
			}
			break;
		case R.id.tv_cancel:
			bsd_xiugaimiam_xia.setEnabled(true);
			bsd_xiugaimiam_xia.setSelection(bsd_xiugaimiam_xia.getText().toString().length());
//			if(setUp!=null)
//			{
//				setUp.onup();
//			}
			break;
		}
	}
	public interface SetUp {
		public void onup();
	}
	public interface SetYes {
		public void onyes(String   chepai);
	}
}
