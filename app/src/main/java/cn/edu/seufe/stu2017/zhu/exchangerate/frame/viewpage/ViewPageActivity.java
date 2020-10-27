package cn.edu.seufe.stu2017.zhu.exchangerate.frame.viewpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import cn.edu.seufe.stu2017.zhu.exchangerate.R;

public class ViewPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_page);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        MyPageAdapter pageAdapter = new MyPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pageAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);


    }

    public CharSequence getPageTitle(int position) {
        return "Title" + position;
    }

}