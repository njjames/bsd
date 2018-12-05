package com.example.administrator.boshide2.Modular.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
import com.example.administrator.boshide2.Modular.Fragment.MeiRongKuaiXiu.Entity.BSD_WXCL_CK_Entity;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.QuanQuan.WeiboDialogUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-11-13.
 */
public class DraftBillDialogFragment extends DialogFragment {
    private static final String PARAM_CHENO_KEY = "paramcheno_key";
    private static final String PARAM_BILLTYPE_KEY = "parambilltype_key";
    private ListView lv_draftbill;
    private TextView tv_close;
    private Dialog mWeiboDialog;
    private Context context;
    private DraftBillApater mAdapter;
    private ArrayList<BSD_WXCL_CK_Entity> mList = new ArrayList<>();
    private URLS url;
    private BSD_WXCL_CK_Entity ck_entity;
    private String paramCheNo;
    private String paramBillType;
    private TextView tv_title;
    private List<HashMap<String, Object>> list = new ArrayList<>();
    private OnOpterateDraftBillListener onOpterateDraftBillListener;

    public static DraftBillDialogFragment newInstance(String paramCheNo, String paramBillType) {
        DraftBillDialogFragment fragment = new DraftBillDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_CHENO_KEY, paramCheNo);
        bundle.putString(PARAM_BILLTYPE_KEY, paramBillType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        paramCheNo = getArguments().getString(PARAM_CHENO_KEY);
        paramBillType = getArguments().getString(PARAM_BILLTYPE_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        url = new URLS();
        View view = inflater.from(context).inflate(R.layout.dialog_draftbill_layout, null);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        queryDraftBill();
    }

    @Override
    public void onStart() {
        super.onStart();
        //隐藏输入法
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = (int) getActivity().getResources().getDimension(R.dimen.qb_px_500);
        params.height = LinearLayout.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes(params);
    }

    public void initView(View view) {
        lv_draftbill = (ListView) view.findViewById(R.id.lv_draftbill);
        mAdapter = new DraftBillApater(context, list);
        mAdapter.setOnOperateItemListener(new DraftBillApater.OnOperateItemListener() {
            @Override
            public void onOpen(String billNo) {
                if (onOpterateDraftBillListener != null) {
                    onOpterateDraftBillListener.onOpen(billNo);
                }
            }

            @Override
            public void onDelete(String billNo) {
                if (onOpterateDraftBillListener != null) {
                    onOpterateDraftBillListener.onDelete(billNo);
                }
            }
        });
        lv_draftbill.setAdapter(mAdapter);
        tv_close = (TextView) view.findViewById(R.id.tv_close);
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        switch (paramBillType) {
            case Conts.BILLTYPE_MRKX:
                tv_title.setText("美容快修草稿单");
                break;
            case Conts.BILLTYPE_KSBJ:
                tv_title.setText("快读报价草稿单");
                break;
            case Conts.BILLTYPE_WXJD:
                tv_title.setText("接待登记草稿单");
                break;
            case Conts.BILLTYPE_WXYY:
                tv_title.setText("维修预约草稿单");
                break;
        }
    }

    /**
     * 查看草稿单
     */
    public void queryDraftBill() {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this.getActivity(), "加载中......");
        AbRequestParams params = new AbRequestParams();
        params.put("che_no", paramCheNo);
        params.put("billtype", paramBillType);
        params.put("gongsino", MyApplication.shared.getString("GongSiNo", ""));
        Request.Post(MyApplication.shared.getString("ip", "") + URLS.BSD_GETDRAFTBILLINFO, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int a, String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    if (jsonObject.getString("message").equals("查询成功")) {
                        JSONArray array = jsonObject.getJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = new JSONObject(array.get(i).toString());
                            HashMap<String, Object> item = new HashMap<>();
                            item.put("billNo", object.getString("billNo"));
                            item.put("billRq", object.getString("billRq"));
                            list.add(item);
                        }
                        mAdapter.notifyDataSetChanged();
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
                Toast.makeText(getContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
                WeiboDialogUtils.closeDialog(mWeiboDialog);
            }
        });

    }

    public interface OnOpterateDraftBillListener {
        void onOpen(String billNo);

        void onDelete(String billNo);
    }

    public void setOnOpterateDraftBillListener(OnOpterateDraftBillListener onOpterateDraftBillListener) {
        this.onOpterateDraftBillListener = onOpterateDraftBillListener;
    }

}
