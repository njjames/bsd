package com.example.administrator.boshide2.Modular.Fragment.LiShiJieDan;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.view.pullview.AbPullToRefreshView;
import com.example.administrator.boshide2.Conts;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Activity.MainActivity;
import com.example.administrator.boshide2.Modular.Fragment.LiShiJieDan.Adapter.BSD_lsjd_adp;
import com.example.administrator.boshide2.Modular.Fragment.WeiXiuJieDan.Entity.BSD_WeiXiuJieDan_Entity;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.Show;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * @历史接单 Created by Administrator on 2017-4-13.
 */

public class BSD_lishijiedan_Fragment extends Fragment implements AbPullToRefreshView.OnFooterLoadListener, AbPullToRefreshView.OnHeaderRefreshListener {
    RelativeLayout bsd_lsbj_fanhui;
    ListView bsd_lsbj_lv;
    List<BSD_WeiXiuJieDan_Entity> data = new ArrayList<BSD_WeiXiuJieDan_Entity>();
    BSD_lsjd_adp adapter;
    AbPullToRefreshView abPullToRefreshView = null;
    int page = 1;
    URLS url;
    EditText bsd_lsjd_cp, bsd_lsjd_cz;
    RelativeLayout relativeLayout13;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bsd_lsjd_fragment, null);
        bsd_lsjd_cp = (EditText) view.findViewById(R.id.bsd_lsjd_cp);
        bsd_lsjd_cz = (EditText) view.findViewById(R.id.bsd_lsjd_cz);
        relativeLayout13 = (RelativeLayout) view.findViewById(R.id.relativeLayout13);
        url = new URLS();
        LSJD();
        //data();
        init(view);
        bsdtext(view);

        return view;
    }

    public void init(View view) {
        abPullToRefreshView = (AbPullToRefreshView) view.findViewById(R.id.lsfreshview);
        bsd_lsbj_lv = (ListView) view.findViewById(R.id.bsd_lsbj_lv);
        adapter = new BSD_lsjd_adp(getActivity(), data);
        adapter.setPhoto(new BSD_lsjd_adp.Photo() {
            @Override
            public void Ddh(String dianhua) {
                TelephonyManager manager = (TelephonyManager) getActivity()
                        .getSystemService(TELEPHONY_SERVICE);// 取得相关系统服务
                String imsi = manager.getSubscriberId(); // 取出IMSI
                if (imsi == null || imsi.length() <= 0) {
                    Toast.makeText(getContext(), "没有检测到SIM卡", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + dianhua));
                    startActivity(intent);
                }
            }
        });
        bsd_lsbj_lv.setAdapter(adapter);
        bsd_lsbj_fanhui = (RelativeLayout) view.findViewById(R.id.bsd_lsbj_fanhui);
        bsd_lsbj_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).uplishijiedanlog(view);
            }
        });

        bsd_lsbj_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BSD_WeiXiuJieDan_Entity entiy = new BSD_WeiXiuJieDan_Entity();
                entiy = data.get(i);
                ((MainActivity) getActivity()).setWxjdentity(entiy);//传了个实体
                Conts.zt = 1;
                Conts.cp = entiy.getChe_no();
                ((MainActivity) getActivity()).uowxjdxiangqing();
            }
        });
// 设置监听器
        abPullToRefreshView.setOnHeaderRefreshListener(this);
        abPullToRefreshView.setOnFooterLoadListener(this);

        // 设置进度条的样式
        // 设置进度条的样式
        abPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
                getResources().getDrawable(R.drawable.progress_circular));
        abPullToRefreshView.getFooterView().setFooterProgressBarDrawable(
                getResources().getDrawable(R.drawable.progress_circular));

        relativeLayout13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LSJD();
            }
        });

    }

    /**
     * lishi接单
     */
    public void LSJD() {
        data.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("pageNumber", page);
        params.put("che_no",bsd_lsjd_cp.getText().toString());
        params.put("kehu_mc",bsd_lsjd_cz.getText().toString());
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_LSJD, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int statusCode, String s) {
                Log.i("cjn", "历史接单====" + s);
                try {

                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject item = jsonarray.getJSONObject(i);
                            BSD_WeiXiuJieDan_Entity entiy = new BSD_WeiXiuJieDan_Entity();
                            entiy.setWork_no(item.getString("work_no"));
                            entiy.setKehu_no(item.getString("kehu_no"));
                            entiy.setKehu_mc(item.getString("kehu_mc"));
//                            entiy.setKehu_mc(item.getString("kehu_mc"));
//                            entiy.setKehu_xm(item.getString("kehu_xm"));
//                            entiy.setKehu_dz(item.getString("kehu_dz"));
//                            entiy.setKehu_yb(item.getString("kehu_yb"));
//                            entiy.setKehu_dh(item.getString("kehu_dh"));
                            entiy.setChe_no(item.getString("che_no"));
                            entiy.setChe_cx(item.getString("che_cx"));
                            Log.i("lsjd", "onSuccess: 车系是---"+item.getString("che_cx"));
                            entiy.setChe_vin(item.getString("che_vin"));
                            entiy.setXche_lc(item.getInt("xche_lc"));
                            entiy.setKehu_dh(item.getString("kehu_dh"));
                            entiy.setCard_no(item.getString("card_no"));
                            entiy.setXche_jdrq(item.getString("xche_jdrq"));
                            entiy.setXche_sfbz(item.getString("xche_sfbz"));
                            entiy.setXche_sffl(item.getDouble("xche_sffl"));
                            entiy.setXche_pgcz(item.getString("xche_pgcz"));
                            entiy.setXche_wxjd(item.getString("xche_wxjd"));
                            data.add(entiy);
                        }
                        Log.i("cjn", "历史接单" + data.toString());
                        adapter.notifyDataSetChanged();

                    } else {
                        Show.showTime(getActivity(), jsonObject.get("message").toString());
                    }
                    abPullToRefreshView.onFooterLoadFinish();
                    abPullToRefreshView.onHeaderRefreshFinish();
//                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                } catch (JSONException e) {
                    e.printStackTrace();
                    adapter.notifyDataSetChanged();
                    abPullToRefreshView.onFooterLoadFinish();
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
                Log.i("cjn", "历史接单" + s);
                Show.showTime(getActivity(), "网络连接超时");
                abPullToRefreshView.onFooterLoadFinish();
                abPullToRefreshView.onHeaderRefreshFinish();
//                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });


    }

    @Override
    public void onFooterLoad(AbPullToRefreshView abPullToRefreshView) {

        page++;
        LSJD();
    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView abPullToRefreshView) {
        data.clear();
        page = 1;
        LSJD();
        adapter.notifyDataSetChanged();
    }


    //    public void data() {
//
//        for (int i = 0; i < 20; i++) {
//            HashMap<String, String> map = new HashMap<>();
//            map.put("name", "2017/4/24 11:12");
//            map.put("time", "张三");
//            map.put("timemany", "冀A·54321");
//            map.put("jinqian", "李晓敏");
//            map.put("caozuo", "500元");
//            map.put("qian", "11011912066");
//            data.add(map);
//        }
//    }
    TextView bsd_01_text;

    public void bsdtext(View view) {
        bsd_01_text = (TextView) view.findViewById(R.id.bsd_08_text);
        bsd_01_text.setText("公司名称 :   " + MyApplication.shared.getString("GongSiMc", "") +
                "                  公司电话 :   " + MyApplication.shared.getString("danw_dh", ""));
    }


}
