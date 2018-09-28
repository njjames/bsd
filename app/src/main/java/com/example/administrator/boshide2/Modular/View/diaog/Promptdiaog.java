package com.example.administrator.boshide2.Modular.View.diaog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.boshide2.R;

/**
 * Created by Administrator on 2017-5-10.
 */

public class Promptdiaog extends Dialog implements android.view.View.OnClickListener {
    private View view;
    TextView tv_promptdiaog_neirong;
    Button bt_promptdiaog_quedding;
    private Display display;
    private LinearLayout ll_promptdiaog;
    private promtOnClickListener promtOnClickListener;
    public Promptdiaog(Context context, String st) {
        super(context,R.style.mydialog);
        // TODO Auto-generated constructor stub
        view = getLayoutInflater().inflate(R.layout.promptdiaog, null, false);
        tv_promptdiaog_neirong=(TextView)view.findViewById(R.id.tv_promptdiaog_neirong);
        tv_promptdiaog_neirong.setText(st);
        bt_promptdiaog_quedding=(Button)view.findViewById(R.id.bt_promptdiaog_quedding);
        bt_promptdiaog_quedding.setOnClickListener(this);
        setContentView(view);
        setCanceledOnTouchOutside(false);
    }



    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.bt_promptdiaog_quedding:
                if(promtOnClickListener!=null)
                {
                    promtOnClickListener.onYesClick();
                }
                dismiss();
                break;
        }
    }
    public void setpromtOnClickListener(promtOnClickListener promtOnClickListener) {
        this.promtOnClickListener = promtOnClickListener;
    }
    public interface promtOnClickListener {
        public void onYesClick();


    }
}
