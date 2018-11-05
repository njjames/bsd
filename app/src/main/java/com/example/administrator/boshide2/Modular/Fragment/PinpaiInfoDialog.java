package com.example.administrator.boshide2.Modular.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.Adapter.BSD_KSBJ_PP_adp;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.DownJianPan;
import com.example.administrator.boshide2.Tools.QuanQuan.WeiboDialogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 快速报价----选择品牌 ----diaglog
 */
public class PinpaiInfoDialog extends Dialog {
    private TextView tv_search;
    private EditText et_pinpai;
    private GridView gridView;
    private BSD_KSBJ_PP_adp ksbjPpAdp;
    private List<Map<String, String>> data = new ArrayList<>();
    private ToopromtOnClickListener toopromtOnClickListener;
    private Context context;
    private Dialog mWeiboDialog;
    private TextView tv_close;

    public PinpaiInfoDialog(final Context context) {
        super(context, R.style.mydialog);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        View view = getLayoutInflater().inflate(R.layout.dialog_pinpaiinfo_layout, null, false);
        setContentView(view);
        setCanceledOnTouchOutside(true);
        this.context = context;
        et_pinpai = (EditText) view.findViewById(R.id.et_pinpai);
        tv_search = (TextView) view.findViewById(R.id.tv_search);
        tv_close = (TextView) view.findViewById(R.id.tv_close);
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close();
            }
        });
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryPinpaiByName();
            }
        });
        gridView = (GridView) view.findViewById(R.id.grid_pinpai);
        ksbjPpAdp = new BSD_KSBJ_PP_adp(context, data);
        gridView.setAdapter(ksbjPpAdp);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                toopromtOnClickListener.onYesClick(data.get(i).get("chepainame"), data.get(i).get("bianhao"));
            }
        });
        getAllPinPai();
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = (int) getContext().getResources().getDimension(R.dimen.qb_px_500);
        params.height = LinearLayout.LayoutParams.MATCH_PARENT;
        this.getWindow().setAttributes(params);
    }

    private void close() {
        this.dismiss();
    }

    public void setToopromtOnClickListener(ToopromtOnClickListener toopromtOnClickListener) {
        this.toopromtOnClickListener = toopromtOnClickListener;
    }

    public interface ToopromtOnClickListener {
        void onYesClick(String chepainame, String bianhao);
    }

    /**
     * 获取全部的品牌，初始化时用
     */
    public void getAllPinPai() {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(context, "加载中...");
        AbRequestParams params = new AbRequestParams();
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_PINPAI, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int statusCode, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("status").toString().equals("1")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            HashMap<String, String> map = new HashMap<>();
                            JSONObject json = jsonarray.getJSONObject(i);
                            map.put("bianhao", json.getString("chex_dm"));
                            map.put("chepainame", json.getString("chex_mc"));
                            data.add(map);
                        }
                        ksbjPpAdp.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });
    }

    /**
     * 根据名称进行模糊查询品牌
     */
    public void queryPinpaiByName() {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(context, "加载中...");
        data.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("pinpai", et_pinpai.getText().toString().trim());
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_PINPAI_1, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int statusCode, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            HashMap<String, String> map = new HashMap<>();
                            JSONObject json = jsonarray.getJSONObject(i);
                            map.put("bianhao", json.getString("chex_dm"));
                            map.put("chepainame", json.getString("chex_mc"));
                            data.add(map);
                        }
                    }
                    ksbjPpAdp.notifyDataSetChanged();
                    DownJianPan.hide(context, et_pinpai);
                    if (data.size() == 0) {
                        Toast.makeText(context, "没有搜索到相关的品牌", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                Toast.makeText(context, "网络不佳，请稍后重试", Toast.LENGTH_SHORT).show();
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });
    }


}
