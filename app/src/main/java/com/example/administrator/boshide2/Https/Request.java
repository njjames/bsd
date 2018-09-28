package com.example.administrator.boshide2.Https;

import com.ab.http.AbHttpResponseListener;
import com.ab.http.AbHttpUtil;
import com.ab.http.AbJsonParams;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;

import android.content.Context;
import android.util.Log;

public class Request {
	private static AbHttpUtil mAbHttpUtil = null;

	/**
	 * 使用前初始化�?次，整个程序初始化一�?
	 * 
	 * @param context
	 * @return
	 */
	public static void intoRequest(Context context) {
		mAbHttpUtil = AbHttpUtil.getInstance(context);
		mAbHttpUtil.setTimeout(10000);
	}

	/**
	 * 含参Post请求
	 * 
	 * @param URL
	 * @param params
	 *            没有参数可为�?
	 * @param abHttpResponseListner
	 */
	public static void Post(String URL, AbRequestParams params,
			AbHttpResponseListener abHttpResponseListner) {
		if (null == params) {
			params = new AbRequestParams();
		}
		Log.i("Post", params.getParamString());
		mAbHttpUtil.post(URL, params, abHttpResponseListner);
	}

	/**
	 * PostJson请求
	 * 
	 * @param URL
	 * @param JsonString
	 * @param abHttpResonseListner
	 */
	public static void PostJson(String URL, final String JsonString,
			AbStringHttpResponseListener abStringHttpResonseListner) {
		AbJsonParams jsonParams = new AbJsonParams() {
			@Override
			public String getJson() {
				// TODO Auto-generated method stub
				return JsonString;
			}
		};
		mAbHttpUtil.postJson(URL, jsonParams, abStringHttpResonseListner);
	};
}
