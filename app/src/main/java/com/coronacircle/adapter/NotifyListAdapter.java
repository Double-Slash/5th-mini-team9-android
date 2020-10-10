package com.coronacircle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coronacircle.R;
import com.coronacircle.model.data.Notification;

import java.util.ArrayList;

public class NotifyListAdapter extends ArrayAdapter<Notification> {

    private ArrayList<Notification> items;

    public NotifyListAdapter(Context context, int textViewResourceId, ArrayList<Notification> notifications) {
        super(context, textViewResourceId, notifications);
        this.items = notifications;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.custom_notify_item, null);
        }

        LinearLayout noticeTypeMessage = v.findViewById(R.id.notice_type_message);
        LinearLayout noticeTypeTime = v.findViewById(R.id.notice_type_time);
        TextView  txtMessageTime = v.findViewById(R.id.message_time);
        TextView txtMessage = v.findViewById(R.id.notice_message);
        TextView txtTimeDate =  v.findViewById(R.id.time_date);

        //message내용에 date만 있다면 날짜 출력
        if( items.get(position).getMessage().equals("date") ) {
            noticeTypeMessage.setVisibility(View.GONE);
            noticeTypeTime.setVisibility(View.VISIBLE);
            String date = items.get(position).getDate();
            String fomatDate = date.substring(0,4)+"."+date.substring(5,7)+"."+date.substring(8,10) + " "+ getDayOfWeek(date)+"요일";
            txtTimeDate.setText(fomatDate);

        }else{
            noticeTypeMessage.setVisibility(View.VISIBLE);
            noticeTypeTime.setVisibility(View.GONE);
            String time = items.get(position).getTime();
            txtMessageTime.setText(fomatTime(time));
            txtMessage.setText(items.get(position).getMessage());
        }
        return v;
    }

    //요일 구하기
    String getDayOfWeek(String date) {
        int[] months ={31, 0, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        String[] weekNames = {"일","월","화","수","목","금","토"};
        int nalsu, i, w;
        int year = Integer.parseInt(date.substring(0,4));
        int month = Integer.parseInt(date.substring(5,7));
        int day = Integer.parseInt(date.substring(8,10));

        if(year%4==0&&year%100!=0||year%400==0)// 입력 년도가 윤년일 경우
        { // 2월의 마지막 날짜를 29일로 설정
             months[1] = 29; } else { months[1] = 28;
        }

        nalsu = (year-1)*365 + (year-1)/4 - (year-1)/100 + (year-1)/400;

        for(i=0;i<(month-1);i++) nalsu += months[i];
        nalsu += day;
        w = nalsu % 7;

        return weekNames[w];
    }

    //오후 오전 나누기
    String fomatTime(String time){
        int minit = Integer.parseInt(time.substring(0,2));
        String whatTime = minit <12 ? "오전 " : "오후 ";
        minit = minit<12? minit:minit-12;
        return whatTime + minit + ":" +time.substring(3,5);
    }
}