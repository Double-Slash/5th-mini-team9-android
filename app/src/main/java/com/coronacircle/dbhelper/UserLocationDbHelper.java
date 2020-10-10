package com.coronacircle.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.coronacircle.model.data.UserLocation;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class UserLocationDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "user.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "user_location";
    public static final String COL_DATE = "ul_date";
    public static final String COL_TIME = "ul_time";
    public static final String COL_LATITUDE = "latitude";
    public static final String COL_lONGITUDE= "longitude";

    SQLiteDatabase db;

    public UserLocationDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+ TABLE_NAME + "("
                + "_id integer primary key autoincrement, "
                + COL_DATE + " date not null,"
                + COL_TIME + " time not null,"
//                + COL_DATETIME + " datetime not null,"
                + COL_LATITUDE + " double not null,"
                + COL_lONGITUDE + " double not null);"
        );
    }

    public boolean insertUserLocation(UserLocation userLocation){
        ContentValues contentValues = new ContentValues();
//        contentValues.put(COL_DATETIME, location.getDate_time());
        contentValues.put(COL_DATE, userLocation.getDate());
        contentValues.put(COL_TIME, userLocation.getTime());
        contentValues.put(COL_LATITUDE, userLocation.getLatitude());
        contentValues.put(COL_lONGITUDE, userLocation.getLongitude());

        long result = db.insert(TABLE_NAME, null, contentValues);

        if( result == -1) return false;
        else return true;
    }

    //모든데이터
    public List<UserLocation> selectUserAllLocation(){
        List<UserLocation> resultList = new ArrayList<UserLocation>();
        String sql = "select * from " + TABLE_NAME + ";";
        Cursor results = db.rawQuery(sql, null);

        if(results.moveToFirst()){
            do{
                UserLocation userLocation = new UserLocation();
                userLocation.setId(results.getInt(0));
                userLocation.setDate(results.getString(1));
                userLocation.setTime(results.getString(2));
                userLocation.setLatitude(results.getDouble(3));
                userLocation.setLongitude(results.getDouble(4));
                resultList.add(userLocation);
            }while(results.moveToNext());
        }
        results.close();
        return resultList;
    }

    //사용자의 일일 동선 데이터
    public List<UserLocation> selectUserLocationByDate(String date){
        List<UserLocation> resultList = new ArrayList<UserLocation>();
        String sql = "select * from " + TABLE_NAME + " where " + COL_DATE + " = '" + date + "';";
//        String sql = "select * from " + TABLE_NAME + " where " + COL_DATE + " ='2020/10/09' AND " + COL_TIME + " between '01:27:00' AND '01:35:00';";
        Cursor results = db.rawQuery(sql, null);

        if(results.moveToFirst()){
            do{
                UserLocation userLocation = new UserLocation();
                userLocation.setId(results.getInt(0));
                userLocation.setDate(results.getString(1));
                userLocation.setTime(results.getString(2));
                userLocation.setLatitude(results.getDouble(3));
                userLocation.setLongitude(results.getDouble(4));
                resultList.add(userLocation);
            }while(results.moveToNext());
        }
        results.close();
        return resultList;
    }

    //확진자와 겹치는 데이터
    public List<UserLocation> selectUserLocationByDateBetweenTime(String date, String arrivedTime, String exitTime){
        List<UserLocation> resultList = new ArrayList<UserLocation>();

//        if(exitTime.equals("null")){
//            int exitTimePlus = Integer.parseInt(arrivedTime.substring(3,5))+5;
//            exitTime = arrivedTime.substring(0,3) + exitTimePlus + ":00";
//        }

        String sql = "select * from " + TABLE_NAME + " where " + COL_DATE + " = '" + date + "' AND " + COL_TIME + " between '"+ arrivedTime +"' AND '" + exitTime + "';";
        Cursor results = db.rawQuery(sql, null);

        if(results.moveToFirst()){
            do{
                UserLocation userLocation = new UserLocation();
                userLocation.setId(results.getInt(0));
                userLocation.setDate(results.getString(1));
                userLocation.setTime(results.getString(2));
                userLocation.setLatitude(results.getDouble(3));
                userLocation.setLongitude(results.getDouble(4));
                resultList.add(userLocation);
            }while(results.moveToNext());
        }
        results.close();
        return resultList;
    }

    //사용자 동선 2주뒤에 지울필요 있을까? ->지운다

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
