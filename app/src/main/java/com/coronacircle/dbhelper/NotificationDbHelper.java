package com.coronacircle.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.coronacircle.model.data.Notification;
import com.coronacircle.model.data.UserLocation;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

// 알림 내부디비

public class NotificationDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "notification.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "notification";
    public static final String COL_DATE = "date";
    public static final String COL_TIME = "time";
    public static final String COL_MESSAGE = "message";

    SQLiteDatabase db;

    public NotificationDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+ TABLE_NAME + "("
                + "_id integer primary key autoincrement, "
                + COL_DATE + " date not null,"
                + COL_TIME + " time not null,"
                + COL_MESSAGE + " text not null);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertNotification(Notification notification){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_DATE, notification.getDate());
        contentValues.put(COL_TIME, notification.getTime());
        contentValues.put(COL_MESSAGE, notification.getMessage());

        long result = db.insert(TABLE_NAME, null, contentValues);

        if( result == -1) return false;
        else return true;
    }

    //모든데이터
    public ArrayList<Notification> selectUserAllLocation(){
        ArrayList<Notification> resultList = new ArrayList<>();
        String sql = "select * from " + TABLE_NAME + " order by " + COL_DATE + ", " + COL_TIME + ";";
        Cursor results = db.rawQuery(sql, null);

        if(results.moveToFirst()){
            do{
                Notification notification = new Notification();
                notification.setId(results.getInt(0));
                notification.setDate(results.getString(1));
                notification.setTime(results.getString(2));
                notification.setMessage(results.getString(3));
                resultList.add(notification);
            }while(results.moveToNext());
        }
        results.close();

        for( Notification n : resultList){
            System.out.println(n.getDate() + " " + n.getTime() + " : " + n.getMessage());
        }
        return resultList;
    }

    public void removeTable(){
        db.delete(TABLE_NAME, null, null );
    }
}
