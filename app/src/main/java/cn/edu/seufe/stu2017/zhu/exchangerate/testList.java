package cn.edu.seufe.stu2017.zhu.exchangerate;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import androidx.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class testList extends ListActivity implements Runnable{
    Handler handler;
    protected void onCreate(Bundle savedI){
        super.onCreate(savedI);
        List<String> list1 = new ArrayList<String>();
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what == 14){
                    List<String> list2 = (List<String>)msg.obj;
                    ListAdapter adapter = new ArrayAdapter<String>(testList.this,android.R.layout.simple_list_item_1,list2);
                    setListAdapter(adapter);
                 }
                super.handleMessage(msg);
            }
        };

    }

    @Override
    public void run() {
        String TAG = "testList";
        Log.i(TAG, "run:zhu1466()...................");
        Message msg = handler.obtainMessage(14);


        List<String> list2 =  new ArrayList<String>();

        //处理数据实验
        String url2 = "http://www.usd-cny.com/bankofchina.htm";

        Document doc = null;
        try {
            doc = Jsoup.connect(url2).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i(TAG,
                "run: " + doc.title());
        Elements tables = doc.getElementsByTag("table");
        Element table6 = tables.get(0);
        //获取TD中的数据
        Elements tds = table6.getElementsByTag("td");
        for(int i=0;i<tds.size();i+=6){
            Element td1 = tds.get(i);
            Element td2 = tds.get(i+5);
            String str1 = td1.text();
            String val = td2.text();
            Log.i(TAG,
                    "run: " + str1 + "==>" + val);
            float v = 100f / Float.parseFloat(val);
            list2.add(str1 + "==>" + val);
            //获取数据并返回……
        }
        msg.obj = list2;
        handler.sendMessage(msg);

    }
}
