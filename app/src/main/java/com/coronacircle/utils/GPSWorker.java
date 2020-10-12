package com.coronacircle.utils;

import android.content.Context;

import com.coronacircle.Service.GpsTracker;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class GPSWorker extends Worker {


    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;

    public GPSWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork() {
//
//        // Do the work here--in this case, upload the images.
//        WorkRequest uploadWorkRequest = new OneTimeWorkRequest.Builder(UploadWorker.class).build();
//        WorkManager.getInstance(getApplicationContext()).enqueue(uploadWorkRequest);
//
//        PeriodicWorkRequest saveRequest =
//                new PeriodicWorkRequest.Builder(UploadWorker.class, 15, TimeUnit.HOURS).build();
//

//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
//        if (!checkLocationServicesStatus()) showDialogForLocationServiceSetting();
//        else checkRunTimePermission();

        GpsTracker gpsTracker = new GpsTracker(getApplicationContext());
        double latitude = gpsTracker.getLatitude(); // 위도
        double longitude = gpsTracker.getLongitude(); //경도

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatDate = sdfNow.format(date);


//        UserLocation userLocation = new UserLocation();
//        userLocation.setDateByDateTime(formatDate);
//        userLocation.setTimeByDateTime(formatDate);
//        userLocation.setLongitude(latitude+0.0008);
//        userLocation.setLatitude(longitude);

//        db.insertUserLocation(userLocation);

        System.out.println( "doWork 백그라운드에서의 위도와 경도" + latitude + ", " + longitude);
        // Indicate whether the work finished successfully with the Result
        System.out.println("백그라운드 도는중");
        return Result.success();
    }
}