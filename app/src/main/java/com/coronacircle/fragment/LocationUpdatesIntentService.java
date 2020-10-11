package com.coronacircle.fragment;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;

import java.util.List;

import com.coronacircle.dbhelper.UserLocationDbHelper;
import com.google.android.gms.location.LocationResult;

public class LocationUpdatesIntentService extends IntentService {

    static final String ACTION_PROCESS_UPDATES =
            "com.google.android.gms.location.sample.locationupdatespendingintent.action" +
                    ".PROCESS_UPDATES";
    static final String TAG = LocationUpdatesIntentService.class.getSimpleName();

    UserLocationDbHelper db;

    public LocationUpdatesIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {


//        db = new UserLocationDbHelper(getApplicationContext());

        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_PROCESS_UPDATES.equals(action)) {
                LocationResult result = LocationResult.extractResult(intent);
                if (result != null) {
//                    UserLocation userLocation = new UserLocation();
//                    userLocation.setDateByDateTime(formatDate);
//                    userLocation.setTimeByDateTime(formatDate);
                    List<Location> locations = result.getLocations();
//                    userLocation.setLatitude(locations.get(0).getLatitude());
//                    userLocation.setLongitude(locations.get(0).getLongitude());
//                    System.out.println("유저동선 : " +userLocation.getDate() + " " + userLocation.getTime() + " " + userLocation.getLatitude() + " " + userLocation.getLongitude());
//                    db.insertUserLocation(userLocation);
                    Utils.setLocationUpdatesResult(this, locations);
                    Utils.sendNotification(this, Utils.getLocationResultTitle(this, locations));
//                    Log.i(TAG, Utils.getLocationUpdatesResult(this));

                }
            }
        }
    }
}