package com.example.administrator.boshide2.SQL;

import android.content.Context;

import com.ab.db.orm.AbSDDBHelper;
import com.ab.util.AbFileUtil;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.Entity.BSD_WeiXiuYueYue_entiy;
import com.example.administrator.boshide2.Modular.Fragment.WiXiuYuYue.Entity.BSD_WeiXiyYuYue_XM_entity;


public class DBSDHelper extends AbSDDBHelper {
	// 数据库名
	private static final String DBNAME = "andbase.db";

	// 当前数据库的版本
	private static final int DBVERSION = 1;
	// 要初始化的表


	private static final Class<?>[] clazz = {};



	public DBSDHelper(Context context) {
		super(context, AbFileUtil.getDbDownloadDir(context), DBNAME, null, DBVERSION, clazz);
	}

}
