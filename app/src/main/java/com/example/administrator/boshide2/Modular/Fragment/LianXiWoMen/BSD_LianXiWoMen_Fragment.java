package com.example.administrator.boshide2.Modular.Fragment.LianXiWoMen;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ab.http.AbStringHttpResponseListener;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Activity.MainActivity;
import com.example.administrator.boshide2.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

/**
 * @联系我们 Created by Administrator on 2017-4-13.
 */

public class BSD_LianXiWoMen_Fragment extends Fragment {
    LinearLayout bsd_lsbj_fanhui;
    URLS url;
    String html;
    Html.ImageGetter imgGetter;
    private TextView title;
    private TextView footerText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bsd_lxwm_fragment, null);
        url=new URLS();
//        init();
        title = (TextView) view.findViewById(R.id.tv_title);
        title.setText("联系我们");
        footerText = (TextView) view.findViewById(R.id.tv_footertext);
        footerText.setText("公司名称 :   " + MyApplication.shared.getString("GongSiMc", "") +
                "                  公司电话 :   " + MyApplication.shared.getString("danw_dh", ""));
        bsd_lsbj_fanhui = (LinearLayout) view.findViewById(R.id.bsd_lsbj_fanhui);
        bsd_lsbj_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).upBSD_MY_page();
            }
        });
//        data();
        return view;
    }

    public void init(){
    //这里面的resource就是fromhtml函数的第一个参数里面的含有的url
    imgGetter = new Html.ImageGetter() {
        public Drawable getDrawable(String source) {
            Drawable drawable = null;
            URL url;
            try {
                url = new URL(source);
                drawable = Drawable.createFromStream(url.openStream(), ""); // 获取网路图片
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            return drawable;
        }
    };
}
    public void data(){
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_MY_gywm, null, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    if (jsonObject.getString("data").length()>0){
                        html=jsonObject.getString("data");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                Log.i("cjn","失败"+s);
            }
        });

    }

}
