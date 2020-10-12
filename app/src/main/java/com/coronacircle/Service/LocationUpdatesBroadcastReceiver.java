package com.coronacircle.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;
import android.util.Log;

import com.coronacircle.dbhelper.UserLocationDbHelper;
import com.coronacircle.fragment.SettingFragment;
import com.coronacircle.model.data.UserLocation;
import com.coronacircle.utils.BackgroundUtils;
import com.google.android.gms.location.LocationResult;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;

import androidx.fragment.app.Fragment;

public class LocationUpdatesBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "LUBroadcastReceiver";

    UserLocationDbHelper db;

    public static final String ACTION_PROCESS_UPDATES =
            "com.google.android.gms.location.sample.locationupdatespendingintent.action" +
                    ".PROCESS_UPDATES";


    @Override
    public void onReceive(Context context, Intent intent) {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatDate = sdfNow.format(date);

        boolean settingLocation = context.getSharedPreferences("setLocation", Context.MODE_PRIVATE).getBoolean("setLocation",true);
        boolean settingNotification = context.getSharedPreferences("setNotification", Context.MODE_PRIVATE).getBoolean("setNotification",true);

        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_PROCESS_UPDATES.equals(action)) {
                LocationResult result = LocationResult.extractResult(intent);

                if (result != null) {
                    db = new UserLocationDbHelper(context);
                    List<Location> locations = result.getLocations();
                    UserLocation userLocation = new UserLocation();
                    userLocation.setDateByDateTime(formatDate);
                    userLocation.setTimeByDateTime(formatDate);
                    userLocation.setLatitude(locations.get(0).getLatitude());
                    userLocation.setLongitude(locations.get(0).getLongitude());
                    if(settingLocation) {
                        BackgroundUtils.setLocationUpdatesResult(context, locations);
                        if (db.insertUserLocation(userLocation)) {
                            System.out.println("경로 추가");
                            //알림 설정 동의시에만 전송
                            if(settingNotification){
                                BackgroundUtils.sendNotification(context, BackgroundUtils.getLocationResultTitle(context, locations));
                            }
                        }
                        else
                            System.out.println("경로 추가 안함");
                    }

//                    if(settingNotification && settingLocation) BackgroundUtils.sendNotification(context, BackgroundUtils.getLocationResultTitle(context, locations));
//                    Log.i(TAG, Utils.getLocationUpdatesResult(context));
                }
            }
        }
    }
}

