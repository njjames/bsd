package com.example.administrator.boshide2.Modular.View.diaog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.administrator.boshide2.R;


public class BSD_Byaoyang_diaog extends Dialog implements View.OnClickListener {
    private View view;
    TextView qeuren;
    TextView bsd_bycx_tv_chexing, bsd_bycx_tv_pinpai, bsd_bycx_tv_chexi,
            bsd_bycx_tv_youchengfen, bsd_bycx_tv_youpindengji, bsd_bycx_tv_niandujibie;


    //	private ToopromtOnClickListener toopromtOnClickListener;
    public BSD_Byaoyang_diaog(Context context, String text1, String text2, String text3, String text4, String text5, String text6) {
        super(context, R.style.mydialog);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        // TODO Auto-generated constructor stub
        view = getLayoutInflater().inflate(R.layout.bsd_baoyangxinxin, null, false);
        qeuren = (TextView) view.findViewById(R.id.bsd_queren_queren);
        bsd_bycx_tv_chexing = (TextView) view.findViewById(R.id.bsd_bycx_tv_chexing);
        bsd_bycx_tv_pinpai = (TextView) view.findViewById(R.id.bsd_bycx_tv_pinpai);
        bsd_bycx_tv_chexi = (TextView) view.findViewById(R.id.bsd_bycx_tv_chexi);
        bsd_bycx_tv_youchengfen = (TextView) view.findViewById(R.id.bsd_bycx_tv_youchengfen);
        bsd_bycx_tv_youpindengji = (TextView) view.findViewById(R.id.bsd_bycx_tv_youpindengji);
        bsd_bycx_tv_niandujibie = (TextView) view.findViewById(R.id.bsd_bycx_tv_niandujibie);
        bsd_bycx_tv_chexing.setText(text1);
        bsd_bycx_tv_pinpai.setText(text2);
        bsd_bycx_tv_chexi.setText(text3);
        bsd_bycx_tv_youchengfen.setText(text4);
        bsd_bycx_tv_youpindengji.setText(text5);
        bsd_bycx_tv_niandujibie.setText(text6);
        qeuren.setOnClickListener(this);
        setContentView(view);
        setCanceledOnTouchOutside(false);

        WindowManager.LayoutParams params =
                this.getWindow().getAttributes();
        params.width = (int) getContext().getResources().getDimension(R.dimen.x280);
        params.height = (int) getContext().getResources().getDimension(R.dimen.y550);
        this.getWindow().setAttributes(params);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.bsd_queren_queren:

                this.dismiss();
                break;
        }
    }
//	public void setToopromtOnClickListener(ToopromtOnClickListener toopromtOnClickListener) {
//		this.toopromtOnClickListener = toopromtOnClickListener;
//	}
//	public interface ToopromtOnClickListener {
//		public void onAdd();
//}

}
