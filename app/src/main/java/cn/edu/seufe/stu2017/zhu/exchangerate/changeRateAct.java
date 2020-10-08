package cn.edu.seufe.stu2017.zhu.exchangerate;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class changeRateAct extends AppCompatActivity{

    EditText et1,et2,et3;
    double R2D, R2E, R2W;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkrate);
        et1 = findViewById(R.id.editTextTextPersonName);
        et2 = findViewById(R.id.editTextTextPersonName2);
        et3 = findViewById(R.id.editTextTextPersonName3);
        Intent intent = getIntent();
        double[] rate  = new double[3];
        rate = intent.getDoubleArrayExtra("rate");
        R2D = rate[0];
        R2E = rate[1];
        R2W = rate[2];


        et1.setText(Double.toString(R2D));
        et2.setText(Double.toString(R2E));
        et3.setText(Double.toString(R2W));


    }

    public void saveRate(View v){
        float rate1 = Float.valueOf(et1.getText().toString());
        float rate2 = Float.valueOf(et2.getText().toString());
        float rate3 = Float.valueOf(et3.getText().toString());
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class) ;
        float rate[] = new float[3];
        rate[0] = rate1;
        rate[1] = rate2;
        rate[2] = rate3;
        intent.putExtra("rate",rate);


        SharedPreferences sp = getSharedPreferences("rate", Activity.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putFloat("R2D", rate1);
        ed.putFloat("R2E", rate2);
        ed.putFloat("R2W", rate3);
        ed.commit();

        startActivity(intent);


    }
    public void back(View v){
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class) ;
        startActivity(intent);
    }




}