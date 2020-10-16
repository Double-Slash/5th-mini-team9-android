package com.coronacircle.fragment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.coronacircle.R;
import com.coronacircle.activity.MainActivity;
import com.coronacircle.adapter.NotifyListAdapter;
import com.coronacircle.dbhelper.NotificationDbHelper;
import com.coronacircle.dbhelper.UserLocationDbHelper;
import com.coronacircle.model.data.CoronaLocation;
import com.coronacircle.model.data.Notification;
import com.coronacircle.model.data.UserLocation;
import com.coronacircle.utils.LocationDistance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotifyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotifyFragment extends Fragment {

    View view;
    NotificationDbHelper notificationDbHelper;
    UserLocationDbHelper userLocationDbHelper;

    String lo;



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


    public NotifyFragment() { }

    public static NotifyFragment newInstance() {
        NotifyFragment fragment = new NotifyFragment();
        return fragment;
    }

    NotificationManager manager; NotificationCompat.Builder builder;
    private static String CHANNEL_ID = "channel1";
    private static String CHANEL_NAME = "Channel1";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notify, container, false);
        ListView notificationListView = view.findViewById(R.id.notification_list);



        notificationDbHelper = new NotificationDbHelper(getContext());
        userLocationDbHelper = new UserLocationDbHelper(getContext());
        //알림 샘플
//        notificationDbHelper.insertNotification(new Notification("2020-09-20","01:43:00","date"));
//        notificationDbHelper.insertNotification(new Notification("2020-09-20","03:43:00","확진자 방문 장소 반경 5km 접근 기록이 있습니다."));
//        notificationDbHelper.insertNotification(new Notification("2020-09-20","04:33:00","확진자 방문 장소 반경 5km 접근 기록이 있습니다."));
//        notificationDbHelper.insertNotification(new Notification("2020-09-20","17:03:00","확진자 방문 장소 반경 5km 접근 기록이 있습니다."));
//        notificationDbHelper.insertNotification(new Notification("2020-09-20","13:42:00","확진자 방문 장소 반경 5km 접근 기록이 있습니다."));
//        notificationDbHelper.insertNotification(new Notification("2020-09-20","18:42:00","확진자 방문 장소가 업데이트 되었습니다."));
//        notificationDbHelper.insertNotification(new Notification("2020-09-20","13:04:00","확진자 방문 장소 반경 5km 접근 기록이 있습니다."));
//        notificationDbHelper.insertNotification(new Notification("2020-09-20","13:43:00","확진자 방문 장소 반경 5km 접근 기록이 있습니다."));
//        notificationDbHelper.insertNotification(new Notification("2020-10-02","01:43:00","date"));
//        notificationDbHelper.insertNotification(new Notification("2020-10-02","03:22:00","확진자 방문 장소 반경 5km 접근 기록이 있습니다."));
//        notificationDbHelper.insertNotification(new Notification("2020-10-02","23:51:00","확진자 방문 장소 반경 5km 접근 기록이 있습니다."));
//        notificationDbHelper.insertNotification(new Notification("2020-10-02","18:42:00","확진자 방문 장소가 업데이트 되었습니다."));
//        notificationDbHelper.insertNotification(new Notification("2020-10-02","23:12:00","확진자 방문 장소 반경 5km 접근 기록이 있습니다."));
//        notificationDbHelper.insertNotification(new Notification("2020-10-13","00:01:00","date"));
//        notificationDbHelper.insertNotification(new Notification("2020-10-16","00:01:00","date"));
//        notificationDbHelper.insertNotification(new Notification("2020-10-11","04:49:00","확진자 방문 장소가 업데이트 되었습니다."));
//        notificationDbHelper.insertNotification(new Notification("2020-10-11","11:23:00","확진자 방문 장소 반경 5km 접근 기록이 있습니다."));
//        notificationDbHelper.insertNotification(new Notification("2020-10-11","22:11:00","확진자 방문 장소 반경 5km 접근 기록이 있습니다."));
//        notificationDbHelper.insertNotification(new Notification("2020-10-11","05:10:00","확진자 방문 장소 반경 5km 접근 기록이 있습니다."));

//        for( CoronaLocation c : sampleCoronaLocationList) {
//            List<UserLocation> userOverlapLocationList = userLocationDbHelper.selectUserLocationByDateBetweenTime(c.getDate(), c.getArrivedTime(), c.getExitTime());
//
//            for( UserLocation u : userOverlapLocationList){
//                if(LocationDistance.distance(u.getLatitude(),u.getLongitude(),Double.parseDouble(c.getLatitude()), Double.parseDouble(c.getLongitude()), "kilometer")<3) {
//                    List<Notification> n = notificationDbHelper.selectCheckSameUserLocation(c.getName(), c.getDate());
//                    if (n.size()==0) {
//                        String whereWhen = c.getName() + " : " +c.getArrivedTime() + " ~ "+ c.getExitTime();
//                        lo = c.getName();
//                        System.out.println(whereWhen);
//                        notificationDbHelper.insertNotification(new Notification(c.getName(), u.getDate(),u.getTime(),whereWhen+"\n확진자 방문 장소 반경 5km이내 접근 기록이 있습니다."));
//                        boolean settingNotification = getContext().getSharedPreferences("setNotification", Context.MODE_PRIVATE).getBoolean("setNotification",true);
//                        if(settingNotification) showNoti();
//                    }
//                }
//            }
//        }
//        List<CoronaLocation> sample = sampleCoronaLocationList.stream().filter(u->u.getDate().equals(userLocation.getDate())).collect(Collectors.toList());

        ArrayList<Notification> notificationList = notificationDbHelper.selectUserAllLocation();
        NotifyListAdapter notifyListAdapter = new NotifyListAdapter(view.getContext(), 0, notificationList);
        notificationListView.setAdapter(notifyListAdapter);



        return view;
    }

    public void showNoti(){
        builder = null;
        manager = (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
        //버전 오레오 이상일 경우
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            manager.createNotificationChannel( new NotificationChannel(CHANNEL_ID, CHANEL_NAME, NotificationManager.IMPORTANCE_DEFAULT) );

            builder = new NotificationCompat.Builder(getContext(),CHANNEL_ID);
        }
        //하위 버전일 경우
         else{
            builder = new NotificationCompat.Builder(getContext());
        } //알림창 제목
        builder.setSmallIcon(R.mipmap.ic_coronacircle_round)
                // In a real app, you may want to use a library like Volley
                // to decode the Bitmap.
                .setLargeIcon(BitmapFactory.decodeResource(getContext().getResources(),
                        R.mipmap.ic_coronacircle_round))
                .setColor(Color.RED)
                .setContentTitle("코로나 써클 알림")
                .setContentText(lo+"- 확진자 방문 장소 반경 3km 접근 기록이 있습니다.");
        android.app.Notification notification = builder.build();
        //알림창 실행
        manager.notify(1,notification);


    }


}