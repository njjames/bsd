package com.example.administrator.boshide2.Modular.View.diaog;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
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


public class ChangepswDialog extends Dialog implements View.OnClickListener {
    private View view;
    TextView bt_toopromptdiaog_quedding;
    TextView bt_toopromptdiaog_quxiao;
    EditText bsd_xiugaimima1, bsd_xiugaimima2;
    String password1, password2;
    EditText et_xiugaimima_yuan;//原密码
    EditText et_xiugaimima_xin;//新密码
    EditText et_xiugaimima_xintoo;//确认密码
    private Dialog mWeiboDialog;
    Activity mycontext;
    private ToopromtOnClickListener toopromtOnClickListener;

    URLS url;

    public ChangepswDialog(Activity context) {
        super(context, R.style.mydialog);
        mycontext = context;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        view = getLayoutInflater().inflate(R.layout.dialog_changepsw_layout, null, false);
        url = new URLS();
        bt_toopromptdiaog_quedding = (TextView) view.findViewById(R.id.tv_confirm);
        bt_toopromptdiaog_quedding.setOnClickListener(this);
        bt_toopromptdiaog_quxiao = (TextView) view.findViewById(R.id.tv_cancel);
        et_xiugaimima_yuan = (EditText) view.findViewById(R.id.et_xiugaimima_yuan);
        et_xiugaimima_xin = (EditText) view.findViewById(R.id.et_xiugaimima_xin);
        et_xiugaimima_xintoo = (EditText) view.findViewById(R.id.et_xiugaimima_xintoo);
        bt_toopromptdiaog_quxiao.setOnClickListener(this);
        setContentView(view);
        setCanceledOnTouchOutside(false);
//
//        WindowManager.LayoutParams params =
//                this.getWindow().getAttributes();
//        params.width = getContext().getResources().getDimensionPixelSize(R.dimen.x200);
//        params.height = getContext().getResources().getDimensionPixelSize(R.dimen.y450);
//        this.getWindow().setAttributes(params);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.tv_confirm:
                if (toopromtOnClickListener != null) {
                    toopromtOnClickListener.onYesClick(password1, password2);
                    if (!et_xiugaimima_yuan.getText().toString().equals(MyApplication.shared.getString("psd", ""))) {
                        Show.showTime(mycontext, "原密码不正确");
                    } else if (!et_xiugaimima_xintoo.getText().toString().equals(et_xiugaimima_xin.getText().toString())) {
                        Show.showTime(mycontext, "两次新密码输入的不一样");
                    } else {
                        pswdEdit();
                    }
                }
                break;
            case R.id.tv_cancel:
                this.dismiss();
                break;
        }
    }

    /**
     * 修改密码请求接口
     */
    private void pswdEdit() {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(mycontext, "加载中...");
        AbRequestParams params = new AbRequestParams();
        params.put("id", MyApplication.shared.getString("userid", ""));
        params.put("newPsd", et_xiugaimima_xin.getText().toString());
        Request.Post(MyApplication.shared.getString("ip", "") + url.PSWEDIT, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int statusCode, String s) {
                Log.i("cjn","成功"+s);
                if(s.toString().equals("true")){
                    delt();
                    Toast.makeText(getContext(),"修改密码成功",Toast.LENGTH_SHORT).show();

                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                }else {
                    delt();
                    Toast.makeText(getContext(),"修改密码失败",Toast.LENGTH_SHORT).show();

                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onFailure(int statusCode, String s, Throwable throwable) {
                Show.showTime(mycontext, "网络连接超时");
                Log.i("cjn","失败"+s);
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });
    }

    public void setToopromtOnClickListener(ToopromtOnClickListener toopromtOnClickListener) {
        this.toopromtOnClickListener = toopromtOnClickListener;
    }

    public interface ToopromtOnClickListener {
        public void onYesClick(String password1, String password2);


    }

    public void delt(){
        this.dismiss();
    }
}
