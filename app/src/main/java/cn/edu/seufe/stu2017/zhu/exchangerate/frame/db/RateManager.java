package cn.edu.seufe.stu2017.zhu.exchangerate.frame.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.edu.seufe.stu2017.zhu.exchangerate.RateItem;
import cn.edu.seufe.stu2017.zhu.exchangerate.frame.db.DBHepler;

public class RateManager {
    private static final String TAG="DBOperate";

    private DBHepler dbHelper;
    private String TBName;

    public RateManager(Context context) {
        dbHelper = new DBHepler(context);
        TBName = dbHelper.TB_NAME;
    }
    //判断是否存在表格
    public boolean HaveData(String tablename){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor;
        boolean flag=false;
        cursor = db.rawQuery("select name from sqlite_master where type='table' ", null);
        while(cursor.moveToNext()){
            //遍历出表名
            String name = cursor.getString(0);
            if(name.equals(tablename))
            {
                flag = true;
            }
            Log.i(TAG, name);
        }
        if(flag)
        {
            cursor=db.query(tablename,null,null,null,null,null,null);
            //检查是不是空表
            if(cursor.getCount()>0)
                return true;
            else
                return false;
        }
        else
            return false;

    }

    public void add(RateItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("CURNAME", item.getCurname());
        values.put("CURRATE", item.getCurrate());
        db.insert(TBName, null, values);
        db.close();
    }

    public void deleteByName(String CurName){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBName,"CURNAME=?",new String[]{CurName});
        Log.i(TAG,CurName+"已删除");
    }
    public RateItem findByName(String CurName){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(TBName, null,
                "CURNAME=?", new String[]{CurName},
                null,null, null);
        RateItem rateItem = null;
        if(cursor != null && cursor.moveToFirst()){
            rateItem = new RateItem();
            rateItem.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            rateItem.setCurname(cursor.getString(cursor.getColumnIndex("CURNAME")));
            rateItem.setCurrate(cursor.getString(cursor.getColumnIndex("CURRATE")));
            cursor.close();
        }
        db.close();
        return rateItem;
    }
    public List<RateItem> getAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(TBName, null,
                null, null,
                null,null, null);
        List<RateItem> list = new ArrayList<>();
        //取出所有数据
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            RateItem rateItem = new RateItem();
            rateItem.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            rateItem.setCurname(cursor.getString(cursor.getColumnIndex("CURNAME")));
            rateItem.setCurrate(cursor.getString(cursor.getColumnIndex("CURRATE")));
            list.add(rateItem);
        }
        cursor.close();

        db.close();
        return list;
    }
    public void updateByName(String CurName,String CurRate){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("CURRATE",CurRate);
        db.update(TBName,values,"CURNAME=?",new String[]{CurName});
    }
}
