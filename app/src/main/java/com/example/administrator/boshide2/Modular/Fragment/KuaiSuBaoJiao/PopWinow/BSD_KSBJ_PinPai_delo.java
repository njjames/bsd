package com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.PopWinow;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Fragment.KuaiSuBaoJiao.Adapter.BSD_KSBJ_PP_adp;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.DensityUtil;
import com.example.administrator.boshide2.Tools.DownJianPan;

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
public class BSD_KSBJ_PinPai_delo extends Dialog  {
	private View view;

	RelativeLayout  but_pp_query;
	EditText  et_pp_query;
	GridView gridView;
	BSD_KSBJ_PP_adp ksbjPpAdp;
	List<HashMap<String, String>> list = new ArrayList<>();
	List<Map<String, String>> data = new ArrayList<>();
	private ToopromtOnClickListener toopromtOnClickListener;
    Context   context;

	URLS url;
    private final ImageView iv_empty;

    public BSD_KSBJ_PinPai_delo(final Context context) {
		super(context, R.style.mydialog);
		getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		view = getLayoutInflater().inflate(R.layout.bad_ksbj_pinpai_pop, null, false);
		url=new URLS();//data();
		setContentView(view);
		setCanceledOnTouchOutside(true);
		this.context=context;
		but_pp_query= (RelativeLayout) view.findViewById(R.id.but_pp_query);
		et_pp_query= (EditText) view.findViewById(R.id.et_pp_query);
        iv_empty = (ImageView) view.findViewById(R.id.iv_empty);
        but_pp_query.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			    queryPinpai();
			}
		});

		gridView= (GridView) view.findViewById(R.id.grid_pinpai);
		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                toopromtOnClickListener.onYesClick(data.get(i).get("chepainame").toString(),
						data.get(i).get("bianhao").toString());

            }
        });
		ksbjPpAdp = new BSD_KSBJ_PP_adp(context, data);
		gridView.setAdapter(ksbjPpAdp);
        WindowManager.LayoutParams params =
                this.getWindow().getAttributes();
        params.width = (int) getContext().getResources().getDimension(R.dimen.qb_px_500);
        params.height = DensityUtil.getScreenHeight((Activity) context);
        this.getWindow().setAttributes(params);
		pinpai();
	}

	public void setToopromtOnClickListener(ToopromtOnClickListener toopromtOnClickListener) {
		this.toopromtOnClickListener = toopromtOnClickListener;
	}
	public interface ToopromtOnClickListener {
		public void onYesClick(String aa,String bianhao);
	}

	public void data() {
		for (int i = 0; i < 10; i++) {
			HashMap<String, String> map = new HashMap<>();
			//map.put("tupian", R.drawable.cundang);
			map.put("chepainame", "女举gifv欧式的仓库年参加搜 ");
			data.add(map);
		}
	}
	/**
	 * 品牌
	 */
   public void pinpai(){
	AbRequestParams params = new AbRequestParams();
	Request.Post(MyApplication.shared.getString("ip", "")+url.BSD_PINPAI, params, new AbStringHttpResponseListener() {
		@Override
		public void onSuccess(int statusCode, String s) {
			try {
				JSONObject jsonObject = new JSONObject(s);
				if(jsonObject.get("status").toString().equals("1")){
					JSONArray jsonarray = jsonObject.getJSONArray("data");
                    for(int i=0;i<jsonarray.length();i++){
                        HashMap<String, String> map = new HashMap<>();
                        JSONObject json = jsonarray.getJSONObject(i);
						map.put("bianhao",json.getString("chex_dm"));
                        map.put("chepainame",json.getString("chex_mc"));
                        data.add(map);
                    }
					ksbjPpAdp.notifyDataSetChanged();


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

		}
	});
}

    /**
     * 根据名称进行模糊查询品牌
     */
    public void queryPinpai() {
        data.clear();
        AbRequestParams params = new AbRequestParams();
        params.put("pinpai", et_pp_query.getText().toString().trim());
        Request.Post(MyApplication.shared.getString("ip", "") + url.BSD_PINPAI_1, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int statusCode, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
//					if(jsonObject.get("status").toString().equals("1")){
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
                    if (data.size() > 0) {
                        iv_empty.setVisibility(View.GONE);
                    } else {
                        iv_empty.setVisibility(View.VISIBLE);
                    }
                    DownJianPan.hide(context, et_pp_query);
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
                Toast.makeText(context, "网络不佳，请稍后重试", Toast.LENGTH_SHORT).show();
            }
        });


    }


}
