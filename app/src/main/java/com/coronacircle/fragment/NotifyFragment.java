package com.coronacircle.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.coronacircle.R;
import com.coronacircle.adapter.NotifyListAdapter;
import com.coronacircle.dbhelper.NotificationDbHelper;
import com.coronacircle.model.data.Notification;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotifyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotifyFragment extends Fragment {

    View view;
    NotificationDbHelper notificationDbHelper;


    public NotifyFragment() { }

    public static NotifyFragment newInstance() {
        NotifyFragment fragment = new NotifyFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notify, container, false);
        ListView notificationListView = view.findViewById(R.id.notification_list);

        notificationDbHelper = new NotificationDbHelper(getContext());
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
//        notificationDbHelper.insertNotification(new Notification("2020-10-11","01:43:00","date"));
//        notificationDbHelper.insertNotification(new Notification("2020-10-11","04:49:00","확진자 방문 장소가 업데이트 되었습니다."));
//        notificationDbHelper.insertNotification(new Notification("2020-10-11","11:23:00","확진자 방문 장소 반경 5km 접근 기록이 있습니다."));
//        notificationDbHelper.insertNotification(new Notification("2020-10-11","22:11:00","확진자 방문 장소 반경 5km 접근 기록이 있습니다."));
//        notificationDbHelper.insertNotification(new Notification("2020-10-11","05:10:00","확진자 방문 장소 반경 5km 접근 기록이 있습니다."));

        ArrayList<Notification> notificationList = notificationDbHelper.selectUserAllLocation();

        NotifyListAdapter notifyListAdapter = new NotifyListAdapter(view.getContext(), 0, notificationList);
        notificationListView.setAdapter(notifyListAdapter);



        return view;
    }
}