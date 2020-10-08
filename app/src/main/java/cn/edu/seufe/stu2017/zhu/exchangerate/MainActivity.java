package cn.edu.seufe.stu2017.zhu.exchangerate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,Runnable{


    private static final String TAG = "Activity";
    double rmb;
    float R2D, R2E, R2W;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = findViewById(R.id.editText);
//        Intent intent = getIntent();
//        float[] a = new float[3];
//        a = intent.getFloatArrayExtra("rate");
//        if(a == null){
//            R2D = (float) 0.1466;
//            R2E = (float) 0.126;
//            R2W = (float) 172.1467;
//            rmb = 0;
//        }else {
//            this.R2D = a[0];
//            this.R2E = a[1];
//            this.R2W = a[2];
//        }

        SharedPreferences sp = getSharedPreferences("rate", Activity.MODE_PRIVATE);
        PreferenceManager.getDefaultSharedPreferences(this);
        this.R2D = sp.getFloat("R2D",(float) 0.1466);
        this.R2E = sp.getFloat("R2E",(float) 0.126);
        this.R2W = sp.getFloat("R2W",(float) 127.1467);


        //多线程尝试
        Thread t = new Thread(this);
        t.start();

        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what == 5){
                    String str = (String)msg.obj;
                    Log.i(TAG, "handleMessage:getMessage msg = "+ str);
                    Button b = findViewById(R.id.button3);
                    b.setText(str);
                }
                super.handleMessage(msg);
            }
        };





    }

    @Override
    public void onClick(View v) {
        TextView tv1 = findViewById(R.id.editText);
        this.rmb = Double.valueOf(tv1.getText().toString());
    }

    public void change1(View v) {
        onClick(v);
        double ans = this.rmb * R2D;
        TextView tv2 = findViewById(R.id.textView3);
        tv2.setText("转化为:" + String.valueOf(ans));
    }

    public void change2(View v) {
        onClick(v);
        double ans = this.rmb * R2E;
        TextView tv2 = findViewById(R.id.textView3);
        tv2.setText("转化为:" + String.valueOf(ans));
    }

    public void change3(View v) {
        onClick(v);
        double ans = this.rmb * R2W;
        TextView tv2 = findViewById(R.id.textView3);
        tv2.setText("转化为:" + String.valueOf(ans));
    }

    public void goNext(View v){
        Intent intent = new Intent();
        intent.setClass(this,changeRateAct.class);
        double rate[] = new double[3];
        rate[0] = R2D;
        rate[1] = R2E;
        rate[2] = R2W;
        intent.putExtra("rate",rate);
        startActivity(intent);

    }

    private String inputStream2String(InputStream inputStream)
            throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "gb2312");
        while (true) {
            int rsz;
            rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }


    @Override
    //多线程
    public void run() {
        Log.i(TAG, "run:zhu1466()...................");
        Message msg = handler.obtainMessage(5);
        msg.obj = "hello zhu";
        handler.sendMessage(msg);


        //在子线程中获得网络数据
        URL url = null;
        try{
            url = new URL("http://www.usd-cny.com/bankofchina.htm");
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            InputStream in = http.getInputStream();

            String html = inputStream2String(in);
            Log.i(TAG , "run:html=" + html);
            http.disconnect();
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }


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
        Element table6 = tables.get(5);
        //获取TD中的数据
        Elements tds = table6.getElementsByTag("td");
        for(int i=0;i<tds.size();i+=8){
            Element td1 = tds.get(i);
            Element td2 = tds.get(i+5);
            String str1 = td1.text();
            String val = td2.text();
            Log.i(TAG,
                    "run: " + str1 + "==>" + val);
            float v = 100f / Float.parseFloat(val);
            //获取数据并返回……
            System.out.println("!!!!"+v);
        }



    }
}