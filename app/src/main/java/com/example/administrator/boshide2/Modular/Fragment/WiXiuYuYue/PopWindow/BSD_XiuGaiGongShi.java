package com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.boshide2.R;


public class BSD_XiuGaiGongShi extends Dialog implements View.OnClickListener {
    private View view;
    TextView bt_toopromptdiaog_quedding;
    TextView bt_toopromptdiaog_quxiao;
    TextView textView1, bsd_xiugaineirong;
    EditText bsd_wxyy_gsxiugai;
    private ToopromtOnClickListener toopromtOnClickListener;
    private  ToopromtXmmc  toopromtXmmc;
    private  int  type;
    public BSD_XiuGaiGongShi(Context context, String a,int  type, double gongshi,String  xmmc, String xiugaineirong) {
        super(context, R.style.mydialog);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        this.type=type;
        // TODO Auto-generated constructor stub
        view = getLayoutInflater().inflate(R.layout.bsd_xiugiaigongshi, null, false);
        bt_toopromptdiaog_quedding = (TextView) view.findViewById(R.id.tv_confirm);
        bt_toopromptdiaog_quedding.setOnClickListener(this);
        bt_toopromptdiaog_quxiao = (TextView) view.findViewById(R.id.tv_cancel);
        bt_toopromptdiaog_quxiao.setOnClickListener(this);
        textView1 = (TextView) view.findViewById(R.id.textView1);
        textView1.setText(a);
        bsd_wxyy_gsxiugai = (EditText) view.findViewById(R.id.et_changcontent);

        if(type==1){
            bsd_wxyy_gsxiugai.setText(xmmc);
            bsd_wxyy_gsxiugai.setTextSize(16);
            Log.i("cjn", "查看传进来的xmmc" + xmmc);
        }else if(type==0){
            bsd_wxyy_gsxiugai.setText("" + gongshi);
        }else  if(type==2){
            bsd_wxyy_gsxiugai.setText(a);
            bsd_wxyy_gsxiugai.setTextSize(16);
        }

        bsd_xiugaineirong = (TextView) view.findViewById(R.id.tv_changetype);
        bsd_xiugaineirong.setText(xiugaineirong);
        setContentView(view);
        setCanceledOnTouchOutside(false);

        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = (int) getContext().getResources().getDimension(R.dimen.qb_px_300);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(params);
        Log.i("cjn", "查看传进来的工时" + gongshi);
    }



    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.tv_confirm:
                if(type==1||type==2){
                    if (toopromtXmmc != null) {
                            toopromtXmmc.onYesClick(bsd_wxyy_gsxiugai.getText().toString());
                    }

                }else  if(type==0){
                    if (toopromtOnClickListener != null) {
                        if (bsd_wxyy_gsxiugai.getText().toString().length()>0) {
                            toopromtOnClickListener.onYesClick(Double.parseDouble(bsd_wxyy_gsxiugai.getText().toString()));
                        }else if (bsd_wxyy_gsxiugai.getText().toString().length()==0){
                            toopromtOnClickListener.onYesClick(0);
                        }
                    }
                }

                break;
            case R.id.tv_cancel:
                this.dismiss();
                break;
        }
    }

    public void setToopromtOnClickListener(ToopromtOnClickListener toopromtOnClickListener) {
        this.toopromtOnClickListener = toopromtOnClickListener;
    }

    public interface ToopromtOnClickListener {
        public void onYesClick(double gongshi);
    }

    public  void  setToopromtXmmc(ToopromtXmmc  toopromtXmmc){
        this.toopromtXmmc=toopromtXmmc;
    }

    public   interface   ToopromtXmmc{
        public void onYesClick(String xmmc);
    }
}
