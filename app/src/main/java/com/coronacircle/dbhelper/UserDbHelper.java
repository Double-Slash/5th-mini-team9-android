package com.coronacircle.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.coronacircle.model.data.Location;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class UserDbHelper  extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "user.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "user_location";
    public static final String COL_TIME = "time";
    public static final String COL_CITY = "city";
    public static final String COL_DISTRICT = "district";
    public static final String COL_ROAD = "road";
    public static final String COL_DETAILED = "detailed";
    public static final String COL_LATITUDE = "latitude";
    public static final String COL_lONGITUDE= "longitude";

    SQLiteDatabase db;

    public UserDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getWritableDatabase();
    }

    //원래 latitude, longitude의 BigDecimal타입인데 sqilite에는 들어가지 않아서 text로 넣어야합니다.
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+ TABLE_NAME + "("
                + "_id integer primary key autoincrement, "
                + COL_TIME + " datetime not null,"
                + COL_CITY + " text not null,"
                + COL_DISTRICT + " text not null,"
                + COL_ROAD + " text,"
                + COL_DETAILED + " text,"
                + COL_LATITUDE + " text not null,"
                + COL_lONGITUDE + " text not null);"
        );
    }

    public boolean insertUserLocation(Location location){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_TIME, location.getTime());
        contentValues.put(COL_CITY, location.getCity());
        contentValues.put(COL_DISTRICT, location.getDistrict());
        contentValues.put(COL_ROAD, location.getRoad());
        contentValues.put(COL_DETAILED, location.getDetailed());
        contentValues.put(COL_LATITUDE, location.getLatitude().toString());
        contentValues.put(COL_lONGITUDE, location.getLongitude().toString());

        long result = db.insert(TABLE_NAME, null, contentValues);

        if( result == -1)return false;
        else return true;
    }

    public List<Location> selectAllUserLocation(){
        List<Location> resultList = new ArrayList<Location>();
        String sql = "select * from " + TABLE_NAME + ";";
        Cursor results = db.rawQuery(sql, null);

        if(results.moveToFirst()){
            do{
                Location lottoData = new Location();
                lottoData.setTime(results.getString(1));
                lottoData.setCity(results.getString(2));
                lottoData.setDistrict(results.getString(3));
                lottoData.setRoad(results.getString(4));
                lottoData.setDetailed(results.getString(5));
                lottoData.setLatitude(new BigDecimal(results.getString(6)));
                lottoData.setLongitude(new BigDecimal(results.getString(7)));
                resultList.add(lottoData);
            }while(results.moveToNext());
        }
        return resultList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
