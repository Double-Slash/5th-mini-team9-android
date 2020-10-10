package com.coronacircle.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.coronacircle.adapter.FragmentAdapter;
import com.coronacircle.adapter.SwipeViewPager;
import com.coronacircle.R;
import com.coronacircle.fragment.HomeFragment;
import com.coronacircle.utils.GpsTracker;
import com.coronacircle.utils.UploadWorker;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ArrayList<String> tabNames = new ArrayList<>();

    ViewPager viewpagerContent;
    TabLayout tabContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doworkWithPeriodic();

        viewpagerContent = findViewById(R.id.viewPager);
        tabContent = findViewById(R.id.tab);
        tabContent.setupWithViewPager(viewpagerContent);

        loadTabName();
        setTabLayout();
        setViewPager();
        setupTabIcons();
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

    private void setupTabIcons() {
        //아이콘설정
        View viewFirst = getLayoutInflater().inflate(R.layout.custom_tab, null);
        ImageView imgFirst = viewFirst.findViewById(R.id.img_tab);
        TextView txtFist = viewFirst.findViewById(R.id.txt_tab);
        imgFirst.setImageResource(R.drawable.homeblue);
        txtFist.setText("홈");

        View view2 = getLayoutInflater().inflate(R.layout.custom_tab, null);
        ImageView img2 = view2.findViewById(R.id.img_tab);
        TextView txt2 = view2.findViewById(R.id.txt_tab);
        img2.setImageResource(R.drawable.notificationgray);
        txt2.setText("알림");

        View view3 = getLayoutInflater().inflate(R.layout.custom_tab, null);
        ImageView img3 = view3.findViewById(R.id.img_tab);
        TextView txt3 = view3.findViewById(R.id.txt_tab);
        img3.setImageResource(R.drawable.setgray);
        txt3.setText("설정");


        tabContent.getTabAt(0).setCustomView(viewFirst);
        tabContent.getTabAt(1).setCustomView(view2);
        tabContent.getTabAt(2).setCustomView(view3);
    }

    private void doworkWithPeriodic(){
        PeriodicWorkRequest saveRequest = new PeriodicWorkRequest.Builder(UploadWorker.class, 15, TimeUnit.HOURS).build();

        WorkManager.getInstance(getBaseContext()).enqueue(saveRequest);

        GpsTracker gpsTracker = new GpsTracker(this);
        double latitude = gpsTracker.getLatitude(); // 위도
        double longitude = gpsTracker.getLongitude(); //경도

    }
}