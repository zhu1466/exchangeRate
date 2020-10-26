package cn.edu.seufe.stu2017.zhu.exchangerate.frame;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.edu.seufe.stu2017.zhu.exchangerate.R;

public class HomeFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container , Bundle saveInstanceState){
        return inflater.inflate(R.layout.frame_home , container);

    }
    @Override
    public void onActivityCreated(Bundle saveInstanceState) {

        super.onActivityCreated(saveInstanceState);
        TextView tv = (TextView)getView().findViewById(R.id.homeTextView1);
        tv.setText("这是主界面");
    }
}
