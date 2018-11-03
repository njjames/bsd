package com.example.administrator.boshide2.Modular.Fragment.LiShiBaoJia;

import android.content.Intent;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.alibaba.fastjson.JSON;
import com.example.administrator.boshide2.Conts;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Activity.MainActivity;
import com.example.administrator.boshide2.Modular.Fragment.BaseFragment;
import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.Entity.BSD_KuaiSuBaoJia_ety;
import com.example.administrator.boshide2.Modular.Fragment.LiShiBaoJia.Adapter.BSD_lsbj_adp;
import com.example.administrator.boshide2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * @历史报价碎片页 Created by Administrator on 2017-4-13.
 */

public class BSD_lishibaojia_Fragment extends BaseFragment {
    private LinearLayout bsd_lsbj_fanhui;
    private ListView bsd_lsbj_lv;
    private List<BSD_KuaiSuBaoJia_ety> data = new ArrayList<>();
    private BSD_lsbj_adp adapter;
    private EditText bsd_lsbj_cp, bsd_lsbj_cz;
    private TextView tv_search;
    private int num;
    private URLS url;
    private TextView title;
    private TextView footerText;
    private String cardNo = "";
    private String kehuMc = "";

    @Override
    protected int getLayoutId() {
        return R.layout.bsd_lsbj_fragment;
    }

    @Override
    public void initView() {
        bsd_lsbj_cp = (EditText) view.findViewById(R.id.bsd_lsbj_cp);
        bsd_lsbj_cz = (EditText) view.findViewById(R.id.bsd_lsbj_cz);
        bsd_lsbj_lv = (ListView) view.findViewById(R.id.bsd_lsbj_lv);
        adapter = new BSD_lsbj_adp(getActivity(), data);
        bsd_lsbj_lv.setAdapter(adapter);
        adapter.setPhoto(new BSD_lsbj_adp.Photo() {
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

        bsd_lsbj_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ((MainActivity) getActivity()).showKsbjXqFragment(JSON.toJSON(data.get(i)).toString());
            }
        });
        // 返回
        bsd_lsbj_fanhui = (LinearLayout) view.findViewById(R.id.bsd_lsbj_fanhui);
        bsd_lsbj_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).upBSD_KSBJ_Log();
            }
        });
        // 查询
        tv_search = (TextView) view.findViewById(R.id.tv_search);
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardNo = bsd_lsbj_cp.getText().toString();
                kehuMc = bsd_lsbj_cz.getText().toString();
                searchLSBJ();
            }
        });
        title = (TextView) view.findViewById(R.id.tv_title);
        footerText = (TextView) view.findViewById(R.id.tv_footertext);
    }

    @Override
    public void initData() {
        url = new URLS();
        title.setText("历史报价");
        footerText.setText("公司名称 :   " + MyApplication.shared.getString("GongSiMc", "") +
                "                  公司电话 :   " + MyApplication.shared.getString("danw_dh", ""));
        searchLSBJ();
    }

    /**
     * 历史报价
     */
    public void searchLSBJ() {
        AbRequestParams params = new AbRequestParams();
        params.put("che_no", cardNo);
        params.put("kehu_mc", kehuMc);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_CL_WX2, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int code, String s) {
                data.clear();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.get("message").toString().equals("查询成功")) {
                        JSONArray jsonarray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            BSD_KuaiSuBaoJia_ety entity = new BSD_KuaiSuBaoJia_ety();
                            JSONObject item = jsonarray.getJSONObject(i);
                            entity.setReco_no(item.getDouble("reco_no"));
                            entity.setList_no(item.getString("list_no"));
                            entity.setList_sfbz(item.getString("List_sfbz"));
                            entity.setList_sffl(item.getDouble("List_sffl"));
                            entity.setKehu_no(item.getString("kehu_no"));
                            entity.setKehu_mc(item.getString("kehu_mc"));
                            entity.setKehu_xm(item.getString("kehu_xm"));
                            entity.setKehu_dh(item.getString("kehu_dh"));
                            entity.setChe_no(item.getString("che_no"));
                            entity.setChe_vin(item.getString("che_vin"));
                            entity.setChe_cx(item.getString("che_cx"));
                            entity.setList_czy(item.getString("List_czy"));
                            entity.setGongSiNo(item.getString("GongSiNo"));
                            entity.setGongSiMc(item.getString("GongSiMc"));
                            entity.setWork_no(item.getString("work_no"));
                            entity.setList_jlrq(item.getString("List_jlrq"));
                            entity.setList_yjjclc(item.getInt("List_yjjclc"));
                            entity.setList_hjje(item.getDouble("List_hjje"));
                            data.add(entity);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                Toast.makeText(getContext(), "网络连接超时", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
