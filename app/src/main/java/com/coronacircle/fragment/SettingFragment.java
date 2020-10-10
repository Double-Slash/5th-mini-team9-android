package com.coronacircle.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.coronacircle.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {

    View view;
    Switch switchSettingLocation;
    boolean isCheckSettingLocation;


    Switch switchSettingNotification;
    boolean isCheckSettingNotification;

    public SharedPreferences prefsetLocation;
    public SharedPreferences prefsetNotification;

    public SettingFragment() {
        // Required empty public constructor
    }


    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting, container, false);

        prefsetLocation = getContext().getSharedPreferences("setLocation", getContext().MODE_PRIVATE);
        prefsetNotification= getContext().getSharedPreferences("setLotification", getContext().MODE_PRIVATE);

        switchSettingLocation = view.findViewById(R.id.setting_location_switch);
        switchSettingLocation.setChecked(prefsetLocation.getBoolean("setLocation",isCheckSettingLocation));
        switchSettingLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                prefsetLocation.edit().putBoolean("setLocation", b).apply();
                isCheckSettingLocation = b;
            }
        });

        switchSettingNotification = view.findViewById(R.id.setting_notification_switch);
        switchSettingNotification.setChecked(prefsetLocation.getBoolean("setLotification",isCheckSettingNotification));
        switchSettingNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                prefsetLocation.edit().putBoolean("setLotification", b).apply();
                isCheckSettingNotification = b;
            }
        });

        return view;
    }
}