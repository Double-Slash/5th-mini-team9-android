package com.coronacircle.utils;

import android.content.Context;

import com.coronacircle.activity.MainActivity;
import com.coronacircle.dbhelper.UserLocationDbHelper;
import com.coronacircle.fragment.HomeFragment;
import com.coronacircle.model.data.UserLocation;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class UploadWorker extends Worker {

    Context context2;

    public UploadWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
        context2 = context;
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

        GpsTracker gpsTracker = new GpsTracker(context2);
        double latitude = gpsTracker.getLatitude(); // 위도
        double longitude = gpsTracker.getLongitude(); //경도

        System.out.println( "context2 위도와 경도" + latitude + ", " + longitude);
        // Indicate whether the work finished successfully with the Result
        return Result.success();
    }
}