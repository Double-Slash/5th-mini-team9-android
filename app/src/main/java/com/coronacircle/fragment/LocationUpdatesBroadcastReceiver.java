package com.coronacircle.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import com.coronacircle.dbhelper.UserLocationDbHelper;
import com.coronacircle.model.data.UserLocation;
import com.google.android.gms.location.LocationResult;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LocationUpdatesBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "LUBroadcastReceiver";

    UserLocationDbHelper db;

    static final String ACTION_PROCESS_UPDATES =
            "com.google.android.gms.location.sample.locationupdatespendingintent.action" +
                    ".PROCESS_UPDATES";

    @Override
    public void onReceive(Context context, Intent intent) {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatDate = sdfNow.format(date);
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
                    if(db.insertUserLocation(userLocation)) System.out.println("경로 추가 성공");


                    Utils.setLocationUpdatesResult(context, locations);
                    Utils.sendNotification(context, Utils.getLocationResultTitle(context, locations));
//                    Log.i(TAG, Utils.getLocationUpdatesResult(context));
                }
            }
        }
    }
}

