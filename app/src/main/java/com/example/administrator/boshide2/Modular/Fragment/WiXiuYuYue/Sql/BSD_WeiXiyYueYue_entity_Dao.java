package com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.Sql;

import android.content.Context;

import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.Entity.BSD_WeiXiuYueYue_entiy;
import com.example.administrator.boshide2.SQL.AbDBDaoImpl;
import com.example.administrator.boshide2.SQL.DBInsideHelper;


/**
 * Created by Administrator on 2017-4-28.
 */

public class BSD_WeiXiyYueYue_entity_Dao extends AbDBDaoImpl<BSD_WeiXiuYueYue_entiy> {
    public BSD_WeiXiyYueYue_entity_Dao(Context context) {
        super(new DBInsideHelper(context),BSD_WeiXiuYueYue_entiy.class);
        // TODO Auto-generated constructor stub
    }
}
