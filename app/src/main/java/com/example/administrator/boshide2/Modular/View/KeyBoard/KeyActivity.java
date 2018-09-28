package com.example.administrator.boshide2.Modular.View.KeyBoard;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.Show;


public class KeyActivity extends Activity  {

	public static final String INPUT_LICENSE_COMPLETE = "me.kevingo.licensekeyboard.input.comp";
	public static final String INPUT_LICENSE_KEY = "LICENSE";

	private EditText inputbox1,inputbox2,
			inputbox3,inputbox4,
			inputbox5,inputbox6,inputbox7;
	private LinearLayout boxesContainer;
//	private KeyboardUtil keyboardUtil;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.key);
//		inputbox1 = (EditText) this.findViewById(R.id.et_car_license_inputbox1);
//		inputbox2 = (EditText) this.findViewById(R.id.et_car_license_inputbox2);
//		inputbox3 = (EditText) this.findViewById(R.id.et_car_license_inputbox3);
//		inputbox4 = (EditText) this.findViewById(R.id.et_car_license_inputbox4);
//		inputbox5 = (EditText) this.findViewById(R.id.et_car_license_inputbox5);
//		inputbox6 = (EditText) this.findViewById(R.id.et_car_license_inputbox6);
//		inputbox7 = (EditText) this.findViewById(R.id.et_car_license_inputbox7);
//		boxesContainer = (LinearLayout) this.findViewById(R.id.ll_car_license_inputbox_content);
//
//		//输入车牌完成后的intent过滤器
//		IntentFilter finishFilter = new IntentFilter(INPUT_LICENSE_COMPLETE);
//
//		final BroadcastReceiver receiver =  new  BroadcastReceiver() {
//			@Override
//			public   void  onReceive(Context context, Intent intent) {
//				String license = intent.getStringExtra(INPUT_LICENSE_KEY);
//				if(license != null && license.length() > 0){
//					boxesContainer.setVisibility(View.GONE);
////					if(keyboardUtil != null){
////						keyboardUtil.hideKeyboard();
////					}
//
//					Show.showTime(KeyActivity.this,"车牌号为"+license);
//				}
//				KeyActivity.this.unregisterReceiver(this);
//			}
//		};
//		this.registerReceiver(receiver, finishFilter);
//
//		keyboardUtil = new KeyboardUtil(this,new EditText[]{inputbox1,inputbox2,inputbox3,
//				inputbox4,inputbox5,inputbox6,inputbox7});
//		keyboardUtil.showKeyboard();
//	}
	}
}
