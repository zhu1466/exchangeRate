package cn.edu.seufe.stu2017.zhu.exchangerate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
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
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,Runnable{


    private static final String TAG = "Activity";
    double rmb;
    float R2D, R2E, R2W;
    Handler handler;
    int ONE_DAY_MSECOND = 1000*60*60*24;

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

        AlarmManager aManager=(AlarmManager)getSystemService(Service.ALARM_SERVICE);
        Intent intent=new Intent();
        // 启动一个名为DialogActivity的Activity
        intent.setClass(this, getRate.class);
        // 获取PendingIntent对象
        // requestCode 参数用来区分不同的PendingIntent对象
        // flag 参数常用的有4个值：
        //  FLAG_CANCEL_CURRENT 当需要获取的PendingIntent对象已经存在时，先取消当前的对象，再获取新的；
        // 	FLAG_ONE_SHOT 获取的PendingIntent对象只能使用一次，再次使用需要重新获取
        // 	FLAG_NO_CREATE 如果获取的PendingIntent对象不存在，则返回null
        //	FLAG_UPDATE_CURRENT 如果希望获取的PendingIntent对象与已经存在的PendingIntent对象相比，如果只是Intent附加的数据不同，那么当前存在的PendingIntent对象不会被取消，而是重新加载新的Intent附加的数据

        // 设置定时任务，这里使用绝对时间，即使休眠也提醒，程序启动后过一天会启动新的Activity，在这里配置一年的任务
        int cou = 0;
        for (cou=0; cou<365;cou++){
            PendingIntent pi=PendingIntent.getActivity(this, cou, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            aManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+10000*cou, pi);
        }


        //多线程尝试
        Thread t = new Thread(this);
        t.start();

        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what == 5){
                    List<String> list2 = (List<String>)msg.obj;

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

    public void goList(View v){
        Intent intent = new Intent();
        intent.setClass(this, showRate.class);
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


        List<String> list2 =  new ArrayList<String>();


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