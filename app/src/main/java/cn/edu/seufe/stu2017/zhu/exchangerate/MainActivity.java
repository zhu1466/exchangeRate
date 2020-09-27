package cn.edu.seufe.stu2017.zhu.exchangerate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    double rmb;
    double R2D, R2E, R2W;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = findViewById(R.id.editText);
        Intent intent = getIntent();
        System.out.println(intent == null);
        double[] a = new double[3];
        a = intent.getDoubleArrayExtra("rate");
        if(a == null){
            R2D = 0.1466;
            R2E = 0.126;
            R2W = 172.1467;
            rmb = 0;
        }else {
            this.R2D = a[0];
            this.R2E = a[1];
            this.R2W = a[2];
        }



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


}