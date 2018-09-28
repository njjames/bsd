package com.example.administrator.boshide2.Modular.Fragment.LiShiBaoJia;

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
import com.example.administrator.boshide2.Conts;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Activity.MainActivity;
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

public class BSD_lishibaojia_Fragment extends Fragment {
    RelativeLayout bsd_lsbj_fanhui;
    ListView bsd_lsbj_lv;
    List<BSD_KuaiSuBaoJia_ety> data = new ArrayList<>();
    BSD_lsbj_adp adapter;
    EditText bsd_lsbj_cp, bsd_lsbj_cz;
    RelativeLayout chaxun;
    int num;
    URLS url;
    @Override
    public void onResume() {
        super.onResume();
        bsd_lsbj_cp.setText("");
        bsd_lsbj_cz.setText("");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bsd_lsbj_fragment, null);
        bsd_lsbj_cp = (EditText) view.findViewById(R.id.bsd_lsbj_cp);
        bsd_lsbj_cz = (EditText) view.findViewById(R.id.bsd_lsbj_cz);
        url = new URLS();  //  data();
        bsdtext(view);
        init(view);
        LSBJ(bsd_lsbj_cp.getText().toString(), bsd_lsbj_cz.getText().toString());

        return view;
    }

    public void init(View view) {

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
        bsd_lsbj_fanhui = (RelativeLayout) view.findViewById(R.id.bsd_lsbj_fanhui);
        bsd_lsbj_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).upksbjlog();


            }
        });

        bsd_lsbj_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BSD_KuaiSuBaoJia_ety entiy = new BSD_KuaiSuBaoJia_ety();
                entiy = data.get(i);
                ((MainActivity) getActivity()).setKsbjenity(entiy);//传了个实体
                Conts.zt = 1;
                Conts.cp = entiy.getChe_no();
                ((MainActivity) getActivity()).upksbjxiangqing();
            }
        });
        chaxun = (RelativeLayout) view.findViewById(R.id.relativeLayout13);
        chaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("cjn", "点击事件");
                LSBJ(bsd_lsbj_cp.getText().toString(), bsd_lsbj_cz.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });
    }

    /***
     * 历史报价
     * @param
     */

    public void LSBJ(String cp, String cz) {
        data.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("che_no", bsd_lsbj_cp.getText().toString());
        params.put("kehu_mc", bsd_lsbj_cz.getText().toString());
        Log.i("cjn", "cp" + cp + "====cz" + cz);
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_CL_WX2, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int statusCode, String s) {
                Log.i("cjn", "查看是否请求成功里" + s);
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
                        adapter.notifyDataSetChanged();
                    } else {
                        adapter.notifyDataSetChanged();
//                        Show.showTime(getActivity(), jsonObject.get("message").toString());
                    }
                    adapter.notifyDataSetChanged();
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
                Toast.makeText(getContext(), "网络连接超时", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            }
        });


    }


    TextView bsd_01_text;

    public void bsdtext(View view) {
        bsd_01_text = (TextView) view.findViewById(R.id.bsd_07_text);
        bsd_01_text.setText("公司名称 :   " + MyApplication.shared.getString("GongSiMc", "") +
                "                  公司电话 :   " + MyApplication.shared.getString("danw_dh", ""));
    }


}
