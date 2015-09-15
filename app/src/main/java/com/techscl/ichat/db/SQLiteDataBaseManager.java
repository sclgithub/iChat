package com.techscl.ichat.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dllo on 15/7/17.
 */
public class SQLiteDataBaseManager extends SQLiteOpenHelper {
    public static final String CITY_LIST = "城市列表", CITY_NAME = "城市名", CITY_CODE = "城市代码";// 定义表名
    public static final String DATA_BASE_NAME = "/iChat.db";// 定义数据库名称常量

    public SQLiteDataBaseManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);// Activity对象, 数据库名称, 工厂类对象null,当前版本号
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {// 第一次创建实例时调用的方法,sqLiteDatabase指的是在构造方法中传递的参数创建的数据库对象
        String city_code = "create table "
                + CITY_LIST + " (_id integer primary key autoincrement, "
                + CITY_CODE + " varchar(255),"
                + CITY_NAME + " varchar(255))";// 创建sql语句
        sqLiteDatabase.execSQL(city_code);// 执行创建
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {// 当数据库版本发生变化时,系统自动回调的升级方法; sqLiteDatabase对应的数据库对象, 之前的版本号, 当前最新的版本号


    }

}
