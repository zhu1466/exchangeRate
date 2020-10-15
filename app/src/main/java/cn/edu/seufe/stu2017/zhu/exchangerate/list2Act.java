package cn.edu.seufe.stu2017.zhu.exchangerate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class list2Act extends ListActivity implements AdapterView.OnItemClickListener{
    private static final String TAG = "list2TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list2);
        List<HashMap<String, String>> listItems;
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



//        SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItems,R.layout.list_item,
//                new String[]{"ItemTitle","ItemDetail"},new int[]{R.id.textView6,R.id.textView8});
//        setListAdapter(listItemAdapter);

        MyAdapter ma = new MyAdapter(this,R.layout.list_item, (ArrayList<HashMap<String, String>>) listItems);
//        this.setListAdapter(ma);
//        lv = (ListView)findViewById(R.id.listview2);
//        //lv.setAdapter(ma);
//        lv.setOnItemClickListener(this);

        ListView lv = this.getListView();
        lv.setAdapter(ma);
        lv.setOnItemClickListener(this);



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
}