package com.coronacircle.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coronacircle.BuildConfig;
import com.coronacircle.adapter.FragmentAdapter;
import com.coronacircle.utils.SwipeViewPager;
import com.coronacircle.R;
import com.coronacircle.utils.UploadWorker;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ArrayList<String> tabNames = new ArrayList<>();

    ViewPager viewpagerContent;
    TabLayout tabContent;

    private long backKeyPressedTime = 0;
//
//    private static final String TAG = MainActivity.class.getSimpleName();
//    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
//    /**
//     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
//     */
//    private static final long UPDATE_INTERVAL = 10000; // Every 10 seconds.
//
//    /**
//     * The fastest rate for active location updates. Updates will never be more frequent
//     * than this value, but they may be less frequent.
//     */
//    private static final long FASTEST_UPDATE_INTERVAL = 5000; // Every 5 seconds
//
//    /**
//     * The max time before batched results are delivered by location services. Results may be
//     * delivered sooner than this interval.
//     */
//    private static final long MAX_WAIT_TIME = UPDATE_INTERVAL * 3; // Every 1 minutes.
//
//    /**
//     * Stores parameters for requests to the FusedLocationProviderApi.
//     */
//    private LocationRequest mLocationRequest;
//
//    /**
//     * Provides access to the Fused Location Provider API.
//     */
//    private FusedLocationProviderClient mFusedLocationClient;


//    // UI Widgets.
//    private Button mRequestUpdatesButton;
//    private Button mRemoveUpdatesButton;
//    private TextView mLocationUpdatesResultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        doworkWithPeriodic();

        viewpagerContent = findViewById(R.id.viewPager);
        tabContent = findViewById(R.id.tab);
        tabContent.setupWithViewPager(viewpagerContent);

        loadTabName();
        setTabLayout();
        setViewPager();
        setupTabIcons();
//
//        mRequestUpdatesButton = (Button) findViewById(R.id.request_updates_button);
//        mRemoveUpdatesButton = (Button) findViewById(R.id.remove_updates_button);
//        mLocationUpdatesResultView = (TextView) findViewById(R.id.location_updates_result);
//
//        // Check if the user revoked runtime permissions.
//        if (!checkPermissions()) {
//            requestPermissions();
//        }
//
//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//        createLocationRequest();

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
        PeriodicWorkRequest saveRequest = new PeriodicWorkRequest.Builder(UploadWorker.class, 10, TimeUnit.SECONDS).build();
        WorkManager.getInstance(getApplicationContext()).enqueue(saveRequest);
    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        PreferenceManager.getDefaultSharedPreferences(this)
//                .registerOnSharedPreferenceChangeListener(this);
//    }
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        updateButtonsState(Utils.getRequestingLocationUpdates(this));
//        mLocationUpdatesResultView.setText(Utils.getLocationUpdatesResult(this));
//    }
//
//    @Override
//    protected void onStop() {
//        PreferenceManager.getDefaultSharedPreferences(this)
//                .unregisterOnSharedPreferenceChangeListener(this);
//        super.onStop();
//    }
//
//    /**
//     * Sets up the location request. Android has two location request settings:
//     * {@code ACCESS_COARSE_LOCATION} and {@code ACCESS_FINE_LOCATION}. These settings control
//     * the accuracy of the current location. This sample uses ACCESS_FINE_LOCATION, as defined in
//     * the AndroidManifest.xml.
//     * <p/>
//     * When the ACCESS_FINE_LOCATION setting is specified, combined with a fast update
//     * interval (5 seconds), the Fused Location Provider API returns location updates that are
//     * accurate to within a few feet.
//     * <p/>
//     * These settings are appropriate for mapping applications that show real-time location
//     * updates.
//     */
//    private void createLocationRequest() {
//        mLocationRequest = new LocationRequest();
//
//        // Sets the desired interval for active location updates. This interval is
//        // inexact. You may not receive updates at all if no location sources are available, or
//        // you may receive them slower than requested. You may also receive updates faster than
//        // requested if other applications are requesting location at a faster interval.
//        // Note: apps running on "O" devices (regardless of targetSdkVersion) may receive updates
//        // less frequently than this interval when the app is no longer in the foreground.
//        mLocationRequest.setInterval(UPDATE_INTERVAL);
//
//        // Sets the fastest rate for active location updates. This interval is exact, and your
//        // application will never receive updates faster than this value.
//        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL);
//
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//
//        // Sets the maximum time when batched location updates are delivered. Updates may be
//        // delivered sooner than this interval.
//        mLocationRequest.setMaxWaitTime(MAX_WAIT_TIME);
//    }
//
//    private PendingIntent getPendingIntent() {
//        // Note: for apps targeting API level 25 ("Nougat") or lower, either
//        // PendingIntent.getService() or PendingIntent.getBroadcast() may be used when requesting
//        // location updates. For apps targeting API level O, only
//        // PendingIntent.getBroadcast() should be used. This is due to the limits placed on services
//        // started in the background in "O".
//
//        // TODO(developer): uncomment to use PendingIntent.getService().
////        Intent intent = new Intent(this, LocationUpdatesIntentService.class);
////        intent.setAction(LocationUpdatesIntentService.ACTION_PROCESS_UPDATES);
////        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        Intent intent = new Intent(this, LocationUpdatesBroadcastReceiver.class);
//        intent.setAction(LocationUpdatesBroadcastReceiver.ACTION_PROCESS_UPDATES);
//        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//    }
//
//    /**
//     * Return the current state of the permissions needed.
//     */
//    private boolean checkPermissions() {
//        int permissionState = ActivityCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION);
//        return permissionState == PackageManager.PERMISSION_GRANTED;
//    }
//
//    private void requestPermissions() {
//        boolean shouldProvideRationale =
//                ActivityCompat.shouldShowRequestPermissionRationale(this,
//                        Manifest.permission.ACCESS_FINE_LOCATION);
//
//        // Provide an additional rationale to the user. This would happen if the user denied the
//        // request previously, but didn't check the "Don't ask again" checkbox.
//        if (shouldProvideRationale) {
//            Log.i(TAG, "Displaying permission rationale to provide additional context.");
//            Snackbar.make(
//                    findViewById(R.id.activity_main),
//                    R.string.permission_rationale,
//                    Snackbar.LENGTH_INDEFINITE)
//                    .setAction(R.string.ok, new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            // Request permission
//                            ActivityCompat.requestPermissions(MainActivity.this,
//                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                                    REQUEST_PERMISSIONS_REQUEST_CODE);
//                        }
//                    })
//                    .show();
//        } else {
//            Log.i(TAG, "Requesting permission");
//            // Request permission. It's possible this can be auto answered if device policy
//            // sets the permission in a given state or the user denied the permission
//            // previously and checked "Never ask again".
//            ActivityCompat.requestPermissions(MainActivity.this,
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                    REQUEST_PERMISSIONS_REQUEST_CODE);
//        }
//    }
//
//    /**
//     * Callback received when a permissions request has been completed.
//     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        Log.i(TAG, "onRequestPermissionResult");
//        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
//            if (grantResults.length <= 0) {
//                // If user interaction was interrupted, the permission request is cancelled and you
//                // receive empty arrays.
//                Log.i(TAG, "User interaction was cancelled.");
//            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission was granted.
//                requestLocationUpdates(null);
//            } else {
//                // Permission denied.
//
//                // Notify the user via a SnackBar that they have rejected a core permission for the
//                // app, which makes the Activity useless. In a real app, core permissions would
//                // typically be best requested during a welcome-screen flow.
//
//                // Additionally, it is important to remember that a permission might have been
//                // rejected without asking the user for permission (device policy or "Never ask
//                // again" prompts). Therefore, a user interface affordance is typically implemented
//                // when permissions are denied. Otherwise, your app could appear unresponsive to
//                // touches or interactions which have required permissions.
//                Snackbar.make(
//                        findViewById(R.id.activity_main),
//                        R.string.permission_denied_explanation,
//                        Snackbar.LENGTH_INDEFINITE)
//                        .setAction(R.string.settings, new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                // Build intent that displays the App settings screen.
//                                Intent intent = new Intent();
//                                intent.setAction(
//                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                                Uri uri = Uri.fromParts("package",
//                                        BuildConfig.APPLICATION_ID, null);
//                                intent.setData(uri);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                startActivity(intent);
//                            }
//                        })
//                        .show();
//            }
//        }
//    }
//
//    @Override
//    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
//        if (s.equals(Utils.KEY_LOCATION_UPDATES_RESULT)) {
//            System.out.println(Utils.getLocationUpdatesResult(this)+"이거야?");
//            mLocationUpdatesResultView.setText(Utils.getLocationUpdatesResult(this));
//        } else if (s.equals(Utils.KEY_LOCATION_UPDATES_REQUESTED)) {
//            updateButtonsState(Utils.getRequestingLocationUpdates(this));
//        }
//    }
//
//    /**
//     * Handles the Request Updates button and requests start of location updates.
//     */
//    public void requestLocationUpdates(View view) {
//        try {
//            Log.i(TAG, "Starting location updates");
//            Utils.setRequestingLocationUpdates(this, true);
//            System.out.println("위도경도내놔");
//            AlarmManager alarmManager=(AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
//            mFusedLocationClient.requestLocationUpdates(mLocationRequest, getPendingIntent());
//            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),5000,
//                    getPendingIntent());    //3초..
//        } catch (SecurityException e) {
//            Utils.setRequestingLocationUpdates(this, false);
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Handles the Remove Updates button, and requests removal of location updates.
//     */
//    public void removeLocationUpdates(View view) {
//        Log.i(TAG, "Removing location updates");
//        Utils.setRequestingLocationUpdates(this, false);
//        mFusedLocationClient.removeLocationUpdates(getPendingIntent());
//    }
//
//    /**
//     * Ensures that only one button is enabled at any time. The Start Updates button is enabled
//     * if the user is not requesting location updates. The Stop Updates button is enabled if the
//     * user is requesting location updates.
//     */
//    private void updateButtonsState(boolean requestingLocationUpdates) {
//        if (requestingLocationUpdates) {
//            mRequestUpdatesButton.setEnabled(false);
//            mRemoveUpdatesButton.setEnabled(true);
//        } else {
//            mRequestUpdatesButton.setEnabled(true);
//            mRemoveUpdatesButton.setEnabled(false);
//        }
//    }
}
