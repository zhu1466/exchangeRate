package cn.edu.seufe.stu2017.zhu.exchangerate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class girdviewactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_girdviewactivity);
        List<HashMap<String, String>> listItems;
        listItems = new ArrayList<HashMap<String, String>>();
        for(int i = 0; i <100 ; i++){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("ItemTitle","Rate:"+i);
            map.put("ItemDetail","detail"+i);
            listItems.add(map);
        }
        MyAdapter ma = new MyAdapter(this,R.layout.list_item, (ArrayList<HashMap<String, String>>) listItems);
        GridView gv = (GridView)findViewById(R.id.mygirdview);
        gv.setAdapter(ma);
    }
}