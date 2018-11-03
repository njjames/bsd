package com.example.administrator.boshide2.Modular.Fragment.My;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.example.administrator.boshide2.Https.Request;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;
import com.example.administrator.boshide2.Modular.Activity.MainActivity;
import com.example.administrator.boshide2.Modular.Adapter.AbstractSpinerAdapter;
import com.example.administrator.boshide2.Modular.Adapter.CustemSpinerAdapter;
import com.example.administrator.boshide2.Modular.Entity.CustemObject;
import com.example.administrator.boshide2.Modular.Fragment.BaseFragment;
import com.example.administrator.boshide2.Modular.View.SpinerPopWindow;
import com.example.administrator.boshide2.Modular.View.diaog.ChangepswDialog;
import com.example.administrator.boshide2.Modular.View.diaog.CustomDialog;
import com.example.administrator.boshide2.Modular.View.diaog.GengXinWanCheng;
import com.example.administrator.boshide2.Modular.View.diaog.JianChaGengXin;
import com.example.administrator.boshide2.R;
import com.example.administrator.boshide2.Tools.QuanQuan.WeiboDialogUtils;
import com.example.administrator.boshide2.Tools.Show;
import com.example.administrator.boshide2.UpdataManager.UpdateManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @我的管理碎片页 Created by Administrator on 2017-4-13.
 */

public class BSD_my_Fragment extends BaseFragment {
    //修改密码
    LinearLayout bsd_xgmm;
    //联系我们
    LinearLayout bsd_lxwm;
    //检查更新
    LinearLayout bsd_jcgx;
    //检查更新弹框
    JianChaGengXin jianChaGengXin;
    //确认更新弹出框
    GengXinWanCheng gengXinWanCheng;
    //确认价钱
    private LinearLayout bsd_jiage;
    private List<Map<String ,String >>list =new ArrayList<>();
    private List<CustemObject> nameList2 = new ArrayList<CustemObject>();
    private AbstractSpinerAdapter mAdapter2;
    private SpinerPopWindow mSpinerPopWindow2;
    private TextView bsd_my_tv_jiaqian;
    private UpdateManager updateMan;
    private ProgressDialog updateProgressDialog;
    private CheckBox bsd_my_shifoudayin;
    private URLS url;
    private Dialog mWeiboDialog;
    //保存车牌号前缀
    private  String   cpPrefix;
    private EditText  bsd_my_et_qianzhui;
    private TextView  bsd_my_rl_save;
    private   SharedPreferences    mSharedPreferences;
    private TextView title;
    private TextView footerText;
    private EditText oldPsw;
    private EditText newPsw;
    private EditText rePsw;
    private ChangepswDialog changepswDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.bsd_my_fragment;
    }

    @Override
    public void initView() {
        //车牌号前缀
        bsd_my_et_qianzhui= (EditText) view.findViewById(R.id.bsd_my_et_qianzhui);
        bsd_my_et_qianzhui.setText(MyApplication.shared.getString("cpPrefix",""));
        bsd_my_rl_save= (TextView) view.findViewById(R.id.tv_save);
        bsd_my_rl_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cpPrefix=bsd_my_et_qianzhui.getText().toString();
                MyApplication.editor.putString("cpPrefix", cpPrefix);
                MyApplication.editor.commit();
                Log.i("cpPrefix", "onClick:车牌前缀是---"+MyApplication.shared.getString("cpPrefix","没有前缀"));
                Toast.makeText(getActivity(), "保存成功", Toast.LENGTH_SHORT).show();
            }
        });

        bsd_my_shifoudayin= (CheckBox) view.findViewById(R.id.bsd_my_shifoudayin);
        if ( MyApplication.shared.getString("shifoudayin", "").equals("1")) {
            bsd_my_shifoudayin.setChecked(true);
        }else {
            bsd_my_shifoudayin.setChecked(false);
        }

        bsd_my_shifoudayin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    //选中的时候
                    Log.i("cjn","选中状态");
                    MyApplication.editor.putString("shifoudayin", "1");
                    MyApplication.editor.commit();
                }else {
                    //没选中的时候
                    Log.i("cjn","未选中状态");
                    MyApplication.editor.putString("shifoudayin", "0");
                    MyApplication.editor.commit();
                }

            }
        });

        bsd_my_tv_jiaqian= (TextView) view.findViewById(R.id.bsd_my_tv_jiaqian);
        bsd_my_tv_jiaqian.setText(MyApplication.shared.getString("jiagedengji", ""));

        //修改密码
        bsd_xgmm = (LinearLayout) view.findViewById(R.id.bsd_xgmm);
        bsd_xgmm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangepswDialog();
            }
        });
        //联系我们
        bsd_lxwm = (LinearLayout) view.findViewById(R.id.bsd_lxwm);
        bsd_lxwm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).uplxwm();
            }
        });
        //检查更新
        bsd_jcgx = (LinearLayout) view.findViewById(R.id.bsd_jcgx);
        bsd_jcgx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jianChaGengXin=new JianChaGengXin(getActivity());
                jianChaGengXin.setToopromtOnClickListener(new JianChaGengXin.ToopromtOnClickListener() {
                    @Override
                    public void onYesClick() {
                       gengXinWanCheng=new GengXinWanCheng(getActivity());
                       // updateMan = new UpdateManager(getContext(), appUpdateCb);
                       // updateMan.checkUpdate();
                       gengXinWanCheng.show();
                    }
                });

                jianChaGengXin.show();
            }
        });
        bsd_jiage= (LinearLayout) view.findViewById(R.id.bsd_my_jiaqian);
        bsd_jiage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGongSi2();
            }
        });
        title = (TextView) view.findViewById(R.id.tv_title);
        footerText = (TextView) view.findViewById(R.id.tv_footertext);
    }

    /**
     * 修改密码的对话框
     */
    private void showChangepswDialog() {
        changepswDialog = new ChangepswDialog(getHostActicity());
        changepswDialog.show();
    }

    @Override
    public void initData() {
        url=new URLS();
        title.setText("我的管理");
        footerText.setText("公司名称 :   " + MyApplication.shared.getString("GongSiMc", "") +
                "                  公司电话 :   " + MyApplication.shared.getString("danw_dh", ""));
        jiaqiandata();
    }

    //请求价格
    public void jiaqiandata(){
        list.clear();
        Request.Post(MyApplication.shared.getString("ip", "")+url.BSD_JiaQian, null, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                try {
                    JSONObject json=new JSONObject(s);
                    if (json.get("status").toString().equals("1")) {
                        JSONObject item=json.getJSONObject("data");
                        Iterator<String>keys=item.keys();
                        String key = null;
                        String value = null;
                        while(keys.hasNext()){
                            Map<String,String >map=new HashMap<String,String>();
                            key = keys.next();
                            value =  item.getString(key);
                            map.put("name",key);
                            map.put("id",value);
                            list.add(map);
                        }
                    }
                    bumen2();
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
     * 基本信息车型
     */
    public void bumen2() {
        nameList2.clear();
        for (int i = 0; i < list.size(); i++) {
            CustemObject object = new CustemObject();
            object.data = list.get(i).get("name");
            nameList2.add(object);
        }
        mAdapter2 = new CustemSpinerAdapter(getActivity());
        mAdapter2.refreshData(nameList2, 0);
        mSpinerPopWindow2 = new SpinerPopWindow(getActivity());
        mSpinerPopWindow2.setAdatper(mAdapter2, 310);
        mSpinerPopWindow2.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
            @Override
            public void onItemClick(int pos) {
                String value = nameList2.get(pos).toString();
                if (!bsd_my_tv_jiaqian.getText().toString().equals(value)) {
                    bsd_my_tv_jiaqian.setText(value);
                    MyApplication.editor.putString("jiagedengji",value);
                    jiaqianbaocundata(list.get(pos).get("id"),list.get(pos).get("name"));
                     Show.showTime(getActivity(), "您选择了," + value);
                }
            }
        });
    }


    private void showGongSi2() {
        mSpinerPopWindow2.setWidth(bsd_jiage.getWidth());
        mSpinerPopWindow2.showAsDropDown(bsd_jiage);
    }


    public  void jiaqianbaocundata(final String id, final String name){
        AbRequestParams params = new AbRequestParams();
        params.put("price_id",id);
        params.put("price_name",name);
        Request.Post(MyApplication.shared.getString("ip", "")+url.BSD_JiaQian_BaoCun, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int a, String s) {
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    if (jsonObject.get("status").toString().equals("1")){
                        Log.i("cjn","查看手否存档成功"+jsonObject.getString("data"));
                        if (jsonObject.getString("data").toString().equals("保存价格成功")){
                            MyApplication.editor.putString("BSD_jiaqian_id", id);
                            MyApplication.editor.putString("BSD_jiaqian_name", name);
                            MyApplication.editor.commit();
                        }
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

}
