package cn.edu.seufe.stu2017.zhu.exchangerate;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.seufe.stu2017.zhu.exchangerate.frame.db.RateManager;
//用来实现监听listView，以及用户输入监听

public class list2Act extends ListActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{
    private static final String TAG = "list2TAG";
    List<HashMap<String, String>> listItems;
    MyAdapter ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list2);
        listItems = new ArrayList<HashMap<String, String>>();
//        for(int i = 0; i <10 ; i++){
//            HashMap<String, String> map = new HashMap<String, String>();
//            map.put("ItemTitle","Rate:"+i);
//            map.put("ItemDetail","detail"+i);
//            listItems.add(map);
//        }

        SharedPreferences sp = getSharedPreferences("rateFromNet", Activity.MODE_PRIVATE);
        PreferenceManager.getDefaultSharedPreferences(this);
        Map<String,String> map = new HashMap<String,String>();
        map = (Map<String, String>) sp.getAll();

        for(Map.Entry<String, String> entry : map.entrySet()){
            HashMap<String, String> map2 = new HashMap<String, String>();
            String mapKey = entry.getKey();
            String mapValue = String.valueOf(entry.getValue());
            map2.put("ItemTitle",mapKey);
            map2.put("ItemDetail",mapValue);
            listItems.add(map2);
        }

        //查询数据库版本号
        String query = "select sqlite_version() AS sqlite_version";
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(":memory:",null);
        Cursor cursor = db.rawQuery(query, null);
        String sqliteVersion="";
        if(cursor.moveToNext()){
            sqliteVersion = cursor.getString(0);
            Log.i(TAG,"databaseVersion"+sqliteVersion);
        }
        RateManager rm = new RateManager(this);
        RateItem rateItem = new RateItem();
        rateItem.setCurname("test1");
        rateItem.setCurrate("9.99");
        rm.add(rateItem);
        RateItem rateItem1 = rm.findById(0);
        RateItem rateItem2 = rm.findById(1);




//        SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItems,R.layout.list_item,
//                new String[]{"ItemTitle","ItemDetail"},new int[]{R.id.textView6,R.id.textView8});
//        setListAdapter(listItemAdapter);
//        this.setListAdapter(ma);
//        lv = (ListView)findViewById(R.id.listview2);
//        lv.setAdapter(ma);
//        lv.setOnItemClickListener(this);

        ma = new MyAdapter(this,R.layout.list_item, (ArrayList<HashMap<String, String>>) listItems);


        ListView lv = this.getListView();
        lv.setAdapter(ma);
        lv.setOnItemClickListener(this);
        lv.setOnItemLongClickListener(this);

        


    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object itemAtPositon = this.getListView().getItemAtPosition(position);
        HashMap<String,String> map = (HashMap<String,String>) itemAtPositon;
        String s1 = map.get("ItemTitle");
        String s2 = (String)map.get("ItemDetail");
        Log.i( TAG,"clickRate:"+s1);
        Intent intent = new Intent();
        intent.setClass(this, calByRate.class);
        intent.putExtra("rate", s2);
        intent.putExtra("kind", s1);
        startActivity(intent);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder bulider = new AlertDialog.Builder(this);
        bulider.setTitle("提示").setMessage("请确认是否删除当前数据").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("对话框","onItemLongClick:");
                listItems.remove(position);
                ma.notifyDataSetChanged();


            }
        }).setNegativeButton("NO", null);
        bulider.create().show();
        return true;
    }
}