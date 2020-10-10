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

        ArrayList<Notification> notificationList = notificationDbHelper.selectUserAllLocation();

        NotifyListAdapter notifyListAdapter = new NotifyListAdapter(view.getContext(), 0, notificationList);
        notificationListView.setAdapter(notifyListAdapter);



        return view;
    }
}