package com.example.administrator.boshide2.Modular.View.diaog;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.QuanQuan.WeiboDialogUtils;
import com.example.administrator.boshide2.Tools.Show;


public class ChangepswDialog extends Dialog {
    private View view;
    private TextView tv_confirm;
    private TextView tv_cancel;
    private EditText et_old;//原密码
    private EditText et_new;//新密码
    private EditText et_newtoo;//确认密码
    private Dialog mWeiboDialog;
    private URLS url;

    public ChangepswDialog(Activity context) {
        super(context, R.style.mydialog);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        view = getLayoutInflater().inflate(R.layout.dialog_changepsw_layout, null, false);
        url = new URLS();
        tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changepsw();
            }
        });
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog();
            }
        });
        et_old = (EditText) view.findViewById(R.id.et_xiugaimima_yuan);
        et_new = (EditText) view.findViewById(R.id.et_xiugaimima_xin);
        et_newtoo = (EditText) view.findViewById(R.id.et_xiugaimima_xintoo);
        setContentView(view);
        setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams params =
                this.getWindow().getAttributes();
        params.width = getContext().getResources().getDimensionPixelSize(R.dimen.qb_px_300);
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(params);
    }

    private void changepsw() {
        String oldPass = et_old.getText().toString();
        final String newPass = et_new.getText().toString();
        String rePass = et_newtoo.getText().toString();
        if (!oldPass.equals(MyApplication.shared.getString("psd", ""))) {
            Toast.makeText(getContext(), "原密码不正确", Toast.LENGTH_SHORT).show();
        } else if (!rePass.equals(newPass)) {
            Toast.makeText(getContext(), "两次新密码输入的不一样", Toast.LENGTH_SHORT).show();
        } else {
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getContext(), "修改中...");
            AbRequestParams params = new AbRequestParams();
            params.put("id", MyApplication.shared.getString("userid", ""));
            params.put("newPsd", newPass);
            Request.Post(MyApplication.shared.getString("ip", "") + url.PSWEDIT, params, new AbStringHttpResponseListener() {
                @Override
                public void onSuccess(int code, String data) {
                    if(data.equals("true")){
                        // 修改成功之后，把最新的密码修改到sp中
                        MyApplication.editor.putString("psd", newPass);
                        MyApplication.editor.commit();
                        Toast.makeText(getContext(),"修改密码成功",Toast.LENGTH_SHORT).show();
                        closeDialog();
                    }else {
                        Toast.makeText(getContext(),"修改密码失败",Toast.LENGTH_SHORT).show();
                    }
                    CustomDialog.dismissDialog();
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                }

                @Override
                public void onStart() {
                }

                @Override
                public void onFinish() {
                }

                @Override
                public void onFailure(int statusCode, String s, Throwable throwable) {
                    Toast.makeText(getContext(), "网络连接超时", Toast.LENGTH_SHORT).show();
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                }
            });
        }
    }

    public void closeDialog(){
        this.dismiss();
    }
}
