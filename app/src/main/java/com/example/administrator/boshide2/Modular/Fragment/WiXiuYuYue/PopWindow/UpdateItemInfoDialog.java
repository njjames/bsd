package com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.PopWindow;

import android.app.Dialog;
import android.content.Context;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.DownJianPan;


public class UpdateItemInfoDialog extends Dialog implements View.OnClickListener {
    public static final int CHANGE_WXXMGS = 0;
    public static final int CHANGE_WXXMNAME = 1;
    public static final int CHANGE_PEIJSL = 2;
    public static final int CHANGE_PEIJYDJ = 3;
    public static final int CHANGE_PGGS = 4;
    public static final int CHANGE_PGJE = 5;
    private View view;
    private TextView tv_confirm;
    private TextView tv_cancel;
    private TextView textView1;
    private TextView tv_changetype;
    private EditText et_changcontent;
    private ToopromtOnClickListener toopromtOnClickListener;
    private ToopromtXmmc  toopromtXmmc;
    private int changeType;
    private final TextView tv_title;

    public UpdateItemInfoDialog(Context context, int changeType, double slOrJe, String wxxmMc) {
        super(context, R.style.mydialog);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        this.changeType = changeType;
        view = getLayoutInflater().inflate(R.layout.bsd_xiugiaigongshi, null, false);
        tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        tv_confirm.setOnClickListener(this);
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(this);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText(wxxmMc);
        tv_changetype = (TextView) view.findViewById(R.id.tv_changetype);
        et_changcontent = (EditText) view.findViewById(R.id.et_changcontent);
        if (changeType == CHANGE_WXXMGS) {
            tv_changetype.setText("修改项目金额:");
            et_changcontent.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            et_changcontent.setText("" + slOrJe);
            et_changcontent.setSelection(et_changcontent.getText().length());
        } else if (changeType == CHANGE_WXXMNAME) {
            tv_changetype.setText("修改项目名称:");
            et_changcontent.setInputType(InputType.TYPE_CLASS_TEXT);
            et_changcontent.setText(wxxmMc);
            et_changcontent.setSelection(et_changcontent.getText().length());
        } else if (changeType == CHANGE_PEIJSL) {
            tv_changetype.setText("修改配件数量:");
            et_changcontent.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            et_changcontent.setText("" + slOrJe);
            et_changcontent.setSelection(et_changcontent.getText().length());
        } else if (changeType == CHANGE_PEIJYDJ) {
            tv_changetype.setText("修改配件原单价:");
            et_changcontent.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            et_changcontent.setText("" + slOrJe);
            et_changcontent.setSelection(et_changcontent.getText().length());
        } else if (changeType == CHANGE_PGGS) {
            tv_changetype.setText("修改派工工时:");
            et_changcontent.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            et_changcontent.setText("" + slOrJe);
            et_changcontent.setSelection(et_changcontent.getText().length());
        } else if (changeType == CHANGE_PGJE) {
            tv_changetype.setText("修改派工金额:");
            et_changcontent.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            et_changcontent.setText("" + slOrJe);
            et_changcontent.setSelection(et_changcontent.getText().length());
        }
        setContentView(view);
        setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = (int) getContext().getResources().getDimension(R.dimen.qb_px_300);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(params);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_confirm:
                if(changeType == CHANGE_WXXMNAME){
                    if (toopromtXmmc != null) {
                        DownJianPan.hide(getContext(), et_changcontent);
                        toopromtXmmc.onYesClick(et_changcontent.getText().toString().trim());
                    }

                }else  if(changeType == CHANGE_WXXMGS || changeType == CHANGE_PEIJSL || changeType == CHANGE_PEIJYDJ || changeType == CHANGE_PGGS || changeType == CHANGE_PGJE){
                    if (toopromtOnClickListener != null) {
                        DownJianPan.hide(getContext(), et_changcontent);
                        if (!TextUtils.isEmpty(et_changcontent.getText().toString().trim())) {
                            toopromtOnClickListener.onYesClick(Double.parseDouble(et_changcontent.getText().toString().trim()));
                        } else if (et_changcontent.getText().toString().length() == 0) {
                            toopromtOnClickListener.onYesClick(0);
                        }
                    }
                }
                break;
            case R.id.tv_cancel:
                DownJianPan.hide(getContext(), et_changcontent);
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
