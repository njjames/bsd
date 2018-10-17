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
    RelativeLayout bsd_lsbj_fanhui;
    URLS url;
    String html;
    TextView bsd_my;
    TextView bsd_my_daiam;
    Html.ImageGetter imgGetter;
    private TextView title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bsd_lxwm_fragment, null);
        bsdtext(view);
        url=new URLS();
        bsd_my= (TextView) view.findViewById(R.id.bsd_my);
        bsd_my_daiam= (TextView) view.findViewById(R.id.bsd_my_daiam);
        bsd_my_daiam.setText("\u3000\u3000"+"石家庄博士德软件科技开发有限公司(以下简称博士德公司),创建于1999年, 是我国最具活力和最具竞争力的商务管理软件科研开发商之一,是中国软件产业的杰出代表。\n" +
                "\u3000\u3000"+"自成立以来，博士德公司时刻关注着行业管理软件市场的需求，以中小企业管理的规范化、科学化、现代化为已任，把开发适合中国国情的行业管理软件当做自己的使命，研发出一系列管理软件，满足了不同企业的需求。\n" +
                "\u3000\u3000"+"经过多年努力，博士德软件客户拥有量和市场占有率迅速扩大，其中博士德汽配汽修管理软件已经成为该行业管理软件的知名品牌。博士德系列商务管理软件赢得了全国各地用户的好评。经过几年的积累与发展，公司的科研队伍不断壮大。公司拥有一支由计算机软件专家和商务管理专家共同组成的一流的科研开发队伍。\n" +
                "\u3000\u3000"+"目前，公司正在全国各省、自治区、直辖市，建立健全各地销售服务中心。以各地销售服务中心以基础，在全国中等以上城市建立各级代理体系。该体系将涵盖全国地区以上的城市312个，在全国任何地级市，都将有博士德公司的销售代理体系。该销售网络体系的建立，将极大地丰富博士德企业文化的内容，同时，为实现博士德公司下一步战略目标打下坚实的基础。\n");
        init();
        title = (TextView) view.findViewById(R.id.tv_title);
        title.setText("联系我们");
        bsd_lsbj_fanhui = (RelativeLayout) view.findViewById(R.id.bsd_lsbj_fanhui);
        bsd_lsbj_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).upmy(view);
            }
        });
        data();
        bsd_my.setMovementMethod(ScrollingMovementMethod.getInstance());// 设置可滚动
        bsd_my.setMovementMethod(LinkMovementMethod.getInstance());//设置超链接可以打开网页
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
                Log.i("cjn","成功"+s);
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    if (jsonObject.getString("data").length()>0){
                        html=jsonObject.getString("data");
                        bsd_my.setText(Html.fromHtml(html, imgGetter, null));
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

    TextView bsd_01_text;

    public void bsdtext(View view) {
        bsd_01_text = (TextView) view.findViewById(R.id.bsd_06_text);
        bsd_01_text.setText("公司名称 :   " + MyApplication.shared.getString("GongSiMc", "") +
                "                  公司电话 :   " + MyApplication.shared.getString("danw_dh", ""));
    }


}
