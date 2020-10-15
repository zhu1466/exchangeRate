package cn.edu.seufe.stu2017.zhu.exchangerate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class calByRate extends AppCompatActivity {
    private static final String TAG = "calActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal_by_rate);
        String rateString = this.getIntent().getStringExtra("rate");
        final float rate = Float.valueOf(rateString);
        final String kind = String.valueOf(this.getIntent().getStringExtra("kind"));
        Log.i("before cal:  ",String.valueOf(rate));
        final EditText ed = (EditText)findViewById(R.id.editTextTextPersonName4);

        ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                float userInput = Float.valueOf(String.valueOf(ed.getText()));
//                if(userInput>0){
//                    Log.e(TAG, String.valueOf(ed.getText()));
//                    TextView tv = (TextView)findViewById(R.id.textView7);
//                    tv.setText(kind+"兑换为"+String.valueOf(rate*userInput));
//                }

                if(ed.getText()!= null){
                    try {
                        float userInput = Float.valueOf(String.valueOf(ed.getText()));
                        Log.e(TAG, String.valueOf(ed.getText()));
                        TextView tv = (TextView)findViewById(R.id.textView7);
                        tv.setText(kind+"兑换为"+String.valueOf(rate*userInput));
                    } catch (NumberFormatException e) {
                        Log.i(TAG, "type Error");
                        e.printStackTrace();
                    }
                }


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}