package com.example.administrator.boshide2.Modular.View.diaog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.boshide2.R;


public class Queding_Quxiao extends Dialog implements View.OnClickListener {
    private OnResultClickListener resultClickListener;

    public Queding_Quxiao(Context context, String msg) {
        super(context, R.style.mydialog);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        View view = getLayoutInflater().inflate(R.layout.queding_quxiao, null, false);
        TextView tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        tv_confirm.setOnClickListener(this);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(this);
        TextView tv_tip = (TextView) view.findViewById(R.id.tv_tips);
        tv_tip.setText(msg);
        setContentView(view);
        setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = getContext().getResources().getDimensionPixelSize(R.dimen.qb_px_300);
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(params);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_confirm:
                if (resultClickListener != null) {
                    resultClickListener.onConfirm();
                }
                break;
            case R.id.tv_cancel:
                if (resultClickListener != null) {
                    resultClickListener.onCancel();
                }
                break;
        }
    }

    public interface OnResultClickListener {
        void onConfirm();

        void onCancel();
    }

    public void setOnResultClickListener(OnResultClickListener onResultClickListener) {
        resultClickListener = onResultClickListener;
    }
}
