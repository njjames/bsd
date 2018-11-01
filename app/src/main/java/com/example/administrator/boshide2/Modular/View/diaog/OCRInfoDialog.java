package com.example.administrator.boshide2.Modular.View.diaog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.boshide2.R;


public class OCRInfoDialog extends Dialog implements View.OnClickListener {
    private TextView tv_confirm;
    private TextView tv_change;
    private EditText et_carno;
    private OnBackListener onBackListener;

    public OCRInfoDialog(Context context, String carNo) {
        super(context, R.style.mydialog);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        View view = getLayoutInflater().inflate(R.layout.car_shi_bie, null, false);
        tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        tv_confirm.setOnClickListener(this);
        tv_change = (TextView) view.findViewById(R.id.tv_change);
        tv_change.setOnClickListener(this);
        et_carno = (EditText) view.findViewById(R.id.et_carno);
        et_carno.setText(carNo);
        et_carno.setEnabled(false);
        setContentView(view);
        setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = (int) getContext().getResources().getDimension(R.dimen.qb_px_300);
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(params);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_confirm:
                String carNo = et_carno.getText().toString();
                if (onBackListener != null) {
                    onBackListener.onConfirm(carNo);
                }
                break;
            case R.id.tv_change:
                et_carno.setEnabled(true);
                et_carno.setSelection(et_carno.getText().toString().length());
                break;
        }
    }

    public interface OnBackListener {
        void onConfirm(String carNo);
    }

    public void setOnBackListener(OnBackListener onBackListener) {
        this.onBackListener = onBackListener;
    }
}
