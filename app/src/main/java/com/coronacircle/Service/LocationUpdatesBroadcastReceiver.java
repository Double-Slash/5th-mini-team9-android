package com.coronacircle.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import com.coronacircle.dbhelper.NotificationDbHelper;
import com.coronacircle.dbhelper.UserLocationDbHelper;
import com.coronacircle.model.data.CoronaLocation;
import com.coronacircle.model.data.Notification;
import com.coronacircle.model.data.UserLocation;
import com.coronacircle.utils.BackgroundUtils;
import com.coronacircle.utils.LocationDistance;
import com.google.android.gms.location.LocationResult;


import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class LocationUpdatesBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "LUBroadcastReceiver";

    //확진자 데이터 샘플
    List<CoronaLocation> sampleCoronaLocationList = Arrays.asList(
            new CoronaLocation("사무실", "2020-10-06", "17:05:00", "02:10:00", "서울특별시", "노원구", "서초대로길54길 29-18", "37.7590039", "127.037487"),
            new CoronaLocation("사무실", "2020-10-14", "00:05:00", "02:10:00", "서울특별시", "노원구", "서초대로길54길 29-18", "37.7590039", "127.037487"),
            new CoronaLocation("서초구청", "2020-10-06", "01:05:00", "02:10:00", "서울특별시", "서초구", "서초대로길11번지", "37.7593039", "127.036987"),
            new CoronaLocation("서초구청", "2020-10-08", "02:05:00", "02:10:00", "서울특별시", "서초구", "서초대로길11번지", "37.7593039", "127.036987"),
            new CoronaLocation("서초구청", "2020-10-09", "02:06:00", "03:10:00", "서울특별시", "서초구", "서초대로길11번지", "37.7593039", "127.036987"),
            new CoronaLocation("서초구청", "2020-10-09", "14:16:00", "null", "서울특별시", "서초구", "서초대로길11번지", "37.7593039", "127.036987"),
            new CoronaLocation("서초구청", "2020-10-09", "23:16:00", "null", "서울특별시", "서초구", "서초대로길11번지", "37.7593039", "127.036987"),
            new CoronaLocation("서초구청", "2020-10-10", "21:31:00", "null", "서울특별시", "서초구", "서초대로길11번지", "37.7593039", "127.036987"),
            new CoronaLocation("강남구청", "2020-10-13", "11:31:00", "21:01:00", "서울특별시", "서초구", "서초대로길11번지", "37.7593039", "127.036987"),
            new CoronaLocation("챔프당구클럽\n", "2020-10-03", "15:02:00", "15:32:00", "서울특별시", "서초구", "남부순환로347길 23", "37.4854200334459", "127.004320329245"),
            new CoronaLocation("스시앤벤토 바이하즈벤\n", "2020-10-04", "16:15:00", "16:41:00", "서울특별시", "서초구", "신반포로176", "37.5053057320819", "127.00432"),
            new CoronaLocation("야미또치킨 고속터미널점\n", "2020-10-06", "11:31:00", "20:26:00", "서울특별시", "서초구", "신반포로177", "37.5062373926847", "127.003696076291"),
            new CoronaLocation("버거킹 양재점\n", "2020-10-06", "13:13:00", "13:35:00", "서울특별시", "서초구", "강남대로221", "37.4833000029317", "127.034439429663"),
            new CoronaLocation("도봉산역", "2020-10-14", "07:13:00", "13:35:00", "서울특별시", "도봉구", "도봉구 도봉2동 17-7", "37.6896849", "127.0440296"),
            new CoronaLocation("녹양현대아파트", "2020-10-02", "18:05:00", "18:05:00", "서울특별시", "노원구", "서초대로길 -18", "37.7597039", "127.035487"),
            new CoronaLocation("녹양현대아파트", "2020-10-05", "11:35:00", "null", "서울특별시", "노원구", "서초대로길 -18", "37.7597039", "127.035487"),
            new CoronaLocation("녹양현대아파트", "2020-10-05", "11:35:00", "null", "서울특별시", "노원구", "서초대로길 -18", "37.7597039", "127.035487"),
            new CoronaLocation("녹양현대아파트", "2020-10-16", "14:30:00", "15:02:00", "서울특별시", "노원구", "서초대로길 -18", "37.7597039", "127.035487"));




    UserLocationDbHelper userLoDb;
    NotificationDbHelper notifiDb;

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
                    userLoDb = new UserLocationDbHelper(context);
                    notifiDb = new NotificationDbHelper(context);
                    List<Location> locations = result.getLocations();
                    UserLocation userLocation = new UserLocation();
                    userLocation.setDateByDateTime(formatDate);
                    userLocation.setTimeByDateTime(formatDate);
                    userLocation.setLatitude(locations.get(0).getLatitude());
                    userLocation.setLongitude(locations.get(0).getLongitude());

                    for( CoronaLocation c : sampleCoronaLocationList) {
                        List<UserLocation> userOverlapLocationList = userLoDb.selectUserLocationByDateBetweenTime(c.getDate(), c.getArrivedTime(), c.getExitTime());

                        for( UserLocation u : userOverlapLocationList){
                            if(LocationDistance.distance(u.getLatitude(),u.getLongitude(),Double.parseDouble(c.getLatitude()), Double.parseDouble(c.getLongitude()), "kilometer")<3) {
                                List<Notification> n = notifiDb.selectCheckSameUserLocation(c.getName(), c.getDate());
                                if (n.size()==0) {
                                    String whereWhen = c.getName() + " : " +c.getArrivedTime() + " ~ "+ c.getExitTime();
                                    System.out.println(whereWhen);
                                    notifiDb.insertNotification(new Notification(c.getName(), u.getDate(),u.getTime(),whereWhen+"\n확진자 방문 장소 반경 5km이내 접근 기록이 있습니다."));
                                    if(settingNotification) BackgroundUtils.sendNotification(context, BackgroundUtils.getLocationResultTitle(context, locations));
                                }
                            }
                        }
                    }

                    if(settingLocation) {
                        BackgroundUtils.setLocationUpdatesResult(context, locations);
                        if (userLoDb.insertUserLocation(userLocation)) {
                            System.out.println("경로 추가");
                            //알림 설정 동의시에만 전송
//                            if(settingNotification){
//                                BackgroundUtils.sendNotification(context, BackgroundUtils.getLocationResultTitle(context, locations));
//                            }
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

