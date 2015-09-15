package com.techscl.ichat.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.techscl.ichat.base.CityCode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 15/7/17.
 */
public class SQLiteDataBaseTools {

    private Context context;
    private SQLiteDatabase database;// 定义一个数据库对象

    public SQLiteDataBaseTools(Context context) {
        this.context = context;
        init();// 调用初始化方法
    }

    private void init() {// 初始化方法

        File file = context.getFilesDir();// 获得文件路径
        String path = file.getAbsolutePath() + SQLiteDataBaseManager.DATA_BASE_NAME;// 定义数据库名称
        SQLiteDataBaseManager manager = new SQLiteDataBaseManager(context, path, null, 1);//实例化SQLiteOpenHelper对象

        database = manager.getWritableDatabase();// 从SQLiteOpenHelper对象中获得一个可读写的SQLiteDataBase对象
    }

    /**
     * 插入数据方法
     *
     * @param tableName
     * @return
     */
    public long insert(String city_name, String city_code, String tableName) {// 向数据库中插入数据,return行号

        ContentValues values = new ContentValues();
        values.put(SQLiteDataBaseManager.CITY_NAME, city_name);
        values.put(SQLiteDataBaseManager.CITY_CODE, city_code);

        long Id = database.insert(tableName, null, values);// 插入数据

        return Id;
    }

    /**
     * 查询某个表中的所有数据
     *
     * @param tableName
     * @return
     */
    public List<CityCode> query(String tableName, String name) {// 查询所有信息
        String sql = "select * from " + tableName + " where 城市名=" + name;
        Cursor cursor = database.rawQuery(sql, null);
        List<CityCode> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            CityCode city = new CityCode();

            int city_name = cursor.getColumnIndex(SQLiteDataBaseManager.CITY_NAME);
            int city_code = cursor.getColumnIndex(SQLiteDataBaseManager.CITY_CODE);

            int newsId_type = cursor.getType(city_name);
            if (newsId_type == Cursor.FIELD_TYPE_STRING) {
                String newsId = cursor.getString(city_name);
                city.setCityName(newsId);
            }
            int title_type = cursor.getType(city_code);
            if (title_type == Cursor.FIELD_TYPE_STRING) {
                String title = cursor.getString(city_code);
                city.setCityCode(title);
            }

            list.add(city);
        }
        return list;
    }

    /**
     * 删除某个表中所有数据
     *
     * @param tableName
     */
    public void delete(String tableName) {
        String sql = "delete from " + tableName;
        database.execSQL(sql);
    }

    /**
     * 通过newsId删除
     *
     * @param tableName
     * @param Id
     */
    public void deleteById(String tableName, String Id) {
        String sql = "delete from " + tableName + " where 新闻id=" + Id;
        database.execSQL(sql);
    }

}
