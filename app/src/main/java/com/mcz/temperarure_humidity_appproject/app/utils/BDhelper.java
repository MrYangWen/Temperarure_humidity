package com.mcz.temperarure_humidity_appproject.app.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/*
 * 生成ximeitable表的数据库类
 * 数据库名和传递的表名相同
 * 
 */


public class BDhelper extends SQLiteOpenHelper{
	private static final int VERSION = 1;

	

	public BDhelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	
	public BDhelper(Context context,String name){
		this(context,name,VERSION);
	}
	public BDhelper(Context context,String name,int version){
		this(context, name,null,version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		db.execSQL("create table homaytable(qbbh varchar(20) UNIQUE)");
		
	}
	
	

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}