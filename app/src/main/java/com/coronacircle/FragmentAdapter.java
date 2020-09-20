package com.coronacircle;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return HomeFragment.newInstance();
            case 1:
                return NotifyFragment.newInstance();
            case 2:
                return SettingFragment.newInstance();
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return 3;
    } // tap의 갯수
}