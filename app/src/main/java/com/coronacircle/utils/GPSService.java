//package com.coronacircle.utils;
//
//import android.Manifest;
//import android.app.Notification;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.content.SharedPreferences;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.IBinder;
//import android.preference.PreferenceManager;
//import android.widget.Toast;
//
//import com.coronacircle.R;
//import com.coronacircle.activity.MainActivity;
//
//import java.util.logging.Handler;
//import java.util.logging.LogRecord;
//
//import androidx.annotation.Nullable;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//public class GPSService extends Service implements LocationListener {
//
//    private Context context;
//    private LocationManager mLocationManager; // LocationManager 객체.
//    private SharedPreferences sharedPreferences; //Setting 결과를 반영할 SharedPreferences 객체.
////    private Data data; // Model 클래스.
//    private Location lastlocation = new Location("last"); //위치 비교를 위한 Location 객체.
//    private double lastLon = 0; // 위도를 나타낼 변수.
//    private double lastLat = 0; // 경도를 나타낼 변수.
//    public PendingIntent contentIntent; // 서비스 실행을 표시하기 위한 PendingIntent 객체.
//    private Handler handler; // 실시간으로 위치 설정 권한 확인을 위한 Handler.
//
//    public void onCreate() {
//        // 설정 값의 변화를 반영하기 위한 객체.
//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        // 서비스 실행시 실행되는 인텐트
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        // 노티피케이션바가 최상위 스택에 항상 있을수 있도록 선언한다.
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
//
//        updateNotification(false);
//
//        // GPS 수신기를 통해 위치를 받아올 LocationManager 객체.
//        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//        // 설정 선택에 따른 GPS 수신 감도에 따른 변화를 위해 선언된 임시 변수.
//        String gpsValue = sharedPreferences.getString("gps_level", "default");
//        // 위치 측정 기능을 사용하기 위해선 GPS_PROVIDER가 사용 가능 상태인지 확인하고, 권한 설정 확인 한 번 더 필요하다.
//        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) &&
//                ActivityCompat.checkSelfPermission(getApplicationContext(),
//                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_DENIED) {
//            // 설정 값에 따른 requestLocationUpdates 메서드의 인자를 다르게 넘겨준다.
//            // requestLocationUpdates 메서드의 인자 (String provider, long minTime, float minDistance, LocationListener listener)
//            switch (gpsValue) {
//                case "default":
//                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
//                    break;
//                case "high":
//                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, this);
//                    break;
//                case "low":
//                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 7000, 0, this);
//                    break;
//                case "middle":
//                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 0, this);
//                    break;
//            }
//            //...중략
//        }
//    }
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//
//            double currentLat = location.getLatitude();
//            double currentLon = location.getLongitude();
//
//        System.out.println("위도와 경도" + currentLat +", " + currentLon);
////
////            // 이동한 위치를 마지막에 계산된 위치와 비교하여 거리를 계산한다.
////            double distance = lastlocation.distanceTo(location);
//
////            // 위치 계산 정확도와 거리를 비교해서 정확도가 더 좋으면, 거리를 추가하고 현재 위치를 변화시켜준다.
////            if (location.getAccuracy() < distance) {
////                data.addDistance(distance);
////                lastLat = currentLat;
////                lastLon = currentLon;
////            }
//    }
//
//    @Override
//    public void onStatusChanged(String s, int i, Bundle bundle) {
//
//    }
//
//    @Override
//    public void onProviderEnabled(String s) {
//
//    }
//
//    @Override
//    public void onProviderDisabled(String s) {
//
//    }
//
//    /*
//    아래 코드는 서비스 클래스를 상속받고 필수적으로 오버 라이딩해서 구현해야 하는 메서드이다.
//    여기선, 위치 권한이 동의되어 있는지 확인하고, 동의되어 있지 않을 경우, 권한 동의가 필요한 토스트를 띄우게 된다.
//    */
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        context = getApplicationContext();
//        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            new Handler() {
//                @Override
//                public void publish(LogRecord logRecord) {
//
//                }
//
//                @Override
//                public void flush() {
//
//                }
//
//                @Override
//                public void close() throws SecurityException {
//
//                }
//            }.post(new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(GPSService.this.getApplicationContext(), "퍼미션오류", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//        return START_STICKY;
//    }
//
//    @Override
//    public boolean onUnbind(Intent intent) {
//        return super.onUnbind(intent);
//    }
//
//    // 서비스가 종료될 때, removeUpdates 메서드를 호출해서 LocationManager의 자원을 해제해야 한다.
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mLocationManager.removeUpdates(this);
//    }
//}
