package com.coronacircle.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.coronacircle.model.data.Location;
import com.coronacircle.model.data.UserLocation;

import java.math.BigDecimal;
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

    //원래 latitude, longitude는 BigDecimal타입인데 sqilite에는 들어가지 않아서 text로 넣어야합니다.
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+ TABLE_NAME + "("
                + "_id integer primary key autoincrement, "
                + COL_DATE + " date not null,"
                + COL_TIME + " time not null,"
//                + COL_DATETIME + " datetime not null,"
                + COL_LATITUDE + " text not null,"
                + COL_lONGITUDE + " text not null);"
        );
    }

    public boolean insertUserLocation(UserLocation userLocation){
        ContentValues contentValues = new ContentValues();
//        contentValues.put(COL_DATETIME, location.getDate_time());
        contentValues.put(COL_DATE, userLocation.getDate());
        contentValues.put(COL_TIME, userLocation.getTime());
        contentValues.put(COL_LATITUDE, userLocation.getLatitude().toString());
        contentValues.put(COL_lONGITUDE, userLocation.getLongitude().toString());

        long result = db.insert(TABLE_NAME, null, contentValues);

        if( result == -1)return false;
        else return true;
    }

    public List<UserLocation> selectUserAllLocation(){
        List<UserLocation> resultList = new ArrayList<UserLocation>();
        String sql = "select * from " + TABLE_NAME + ";";
        Cursor results = db.rawQuery(sql, null);

        if(results.moveToFirst()){
            do{
                UserLocation userLocation = new UserLocation();
//                userLocation.setDate_time(results.getString(1));
                userLocation.setDate(results.getString(1));
                userLocation.setTime(results.getString(2));
                userLocation.setLatitude(new BigDecimal(results.getString(3)));
                userLocation.setLongitude(new BigDecimal(results.getString(4)));
                resultList.add(userLocation);
            }while(results.moveToNext());
        }
        return resultList;
    }

    public List<UserLocation> selectUserLocationByDate(String date){
        List<UserLocation> resultList = new ArrayList<UserLocation>();
        String sql = "select * from " + TABLE_NAME + " where date = " + date + ";";
        Cursor results = db.rawQuery(sql, null);

        if(results.moveToFirst()){
            do{
                UserLocation userLocation = new UserLocation();
                userLocation.setDate(results.getString(1));
                userLocation.setTime(results.getString(2));
                userLocation.setLatitude(new BigDecimal(results.getString(3)));
                userLocation.setLongitude(new BigDecimal(results.getString(4)));
                resultList.add(userLocation);
            }while(results.moveToNext());
        }
        return resultList;
    }

    //사용자 동선 2주뒤에 지울필요 있을까?

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
