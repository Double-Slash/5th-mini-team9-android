package com.coronacircle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ArrayList<String> tabNames = new ArrayList<>();

//    ViewPager viewpagerContent;
//    TabLayout tabContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        viewpagerContent = findViewById(R.id.viewPager);
//        tabContent = findViewById(R.id.tab);
//        tabContent.setupWithViewPager(viewpagerContent);

        loadTabName();
        setTabLayout();
        setViewPager();
//        setupTabIcons();
    }

    @TargetApi(Build.VERSION_CODES.N)
    private void setTabLayout(){
        tabLayout = findViewById(R.id.tab);
        tabNames.stream().forEach(name ->tabLayout.addTab(tabLayout.newTab().setText(name)));
    }

    private void loadTabName(){
        tabNames.add("홈");
        tabNames.add("알림");
        tabNames.add("설정");
    }

    private void setViewPager() {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        SwipeViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setPagingEnabled(false);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

//    private void setupTabIcons() {
//        //아이콘설정
//        View viewFirst = getLayoutInflater().inflate(R.layout.custom_tab, null);
//        ImageView imgFirst = viewFirst.findViewById(R.id.img_tab);
//        imgFirst.setImageResource(R.drawable.btn_home_click);
//
//        View view2 = getLayoutInflater().inflate(R.layout.custom_tab, null);
//        ImageView img2 = view2.findViewById(R.id.img_tab);
//        img2.setImageResource(R.drawable.btn_search);
//
//        View view3 = getLayoutInflater().inflate(R.layout.custom_tab, null);
//        ImageView img3 = view3.findViewById(R.id.img_tab);
//        img3.setImageResource(R.drawable.btn_history);
//
//
//        tabContent.getTabAt(0).setCustomView(viewFirst);
//        tabContent.getTabAt(1).setCustomView(view2);
//        tabContent.getTabAt(2).setCustomView(view3);
//    }
}