package cn.edu.seufe.stu2017.zhu.exchangerate.frame.viewpage;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import cn.edu.seufe.stu2017.zhu.exchangerate.frame.FuncFragment;
import cn.edu.seufe.stu2017.zhu.exchangerate.frame.HomeFragment;
import cn.edu.seufe.stu2017.zhu.exchangerate.frame.SettingFragment;

public class MyPageAdapter extends FragmentPagerAdapter {
    public MyPageAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new HomeFragment();
        }else if(position == 1){
            return new FuncFragment();
        }else {
            return new SettingFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
