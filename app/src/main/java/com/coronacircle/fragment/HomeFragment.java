package com.coronacircle.fragment;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Toast;

import com.coronacircle.adapter.CustomCalloutBalloonAdapter;
import com.coronacircle.R;
import com.coronacircle.custom.YearMonthDayPickerDialog;
import com.coronacircle.dbhelper.UserLocationDbHelper;
import com.coronacircle.model.data.CoronaLocation;
import com.coronacircle.model.data.UserLocation;
import com.coronacircle.Service.GpsTracker;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

public class HomeFragment extends Fragment implements MapView.CurrentLocationEventListener, MapView.MapViewEventListener, MapView.POIItemEventListener{

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;

    //권한 받아야하는 퍼미션 종류
    String[] REQUIRED_PERMISSIONS  = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
    };

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


    private UserLocationDbHelper userLocationDbHelper;
    YearMonthDayPickerDialog pd;

    ImageButton nowLocBtn;
    ImageButton filterBtn;

    View view;
    MapView mapView;

    List<UserLocation> dateUserLocationLine = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);



        //if (위치 정보 비활성화시) 설정화면으로 안내
        //else (위치 정보 활성화시) 퍼미션 확인
        if (!checkLocationServicesStatus()) showDialogForLocationServiceSetting();
        else checkRunTimePermission();

        ViewGroup mapViewContainer = (ViewGroup)view.findViewById(R.id.map_view);
        mapView = new MapView(view.getContext());
        mapViewContainer.addView(mapView);
        mapView.setMapViewEventListener(this);
        mapView.setPOIItemEventListener(this);
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);




        //말풍선 커스텀 어댑터
        CustomCalloutBalloonAdapter customCalloutBalloonAdapter = new CustomCalloutBalloonAdapter(getActivity());
        mapView.setCalloutBalloonAdapter(customCalloutBalloonAdapter);

        //백그라운드 되면 dateSetListener - if (pd.isCheckMyLocation())로 옮기기
        userLocationDbHelper = new UserLocationDbHelper(getContext());

        nowLocBtn = view.findViewById(R.id.now_location);
        nowLocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserLocation now_UserLocation = getUserLocation();
                userLocationDbHelper.insertUserLocation(getUserLocation());

                //현재 사용자 위치로 이동
                mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
                MapPoint now_Point = MapPoint.mapPointWithGeoCoord(now_UserLocation.getLatitude(), now_UserLocation.getLongitude());
                mapView.moveCamera(CameraUpdateFactory.newMapPoint(now_Point,4));
            }
        });


        //첫 화면에선 전체 확진자 마커 보이기
        allCoronaLocation();

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                Log.d("YearMonthPickerTest", "year = " + year + ", month = " + monthOfYear + ", day = " + dayOfMonth);

                String date = year+"-"+(monthOfYear<10? "0" + monthOfYear : monthOfYear) +"-"+(dayOfMonth<10? "0"+dayOfMonth : dayOfMonth);
                coronaLocationByDate(date);

                if (pd.isCheckMyLocation()) {
                    dateUserLocationLine = userLocationDbHelper.selectUserLocationByDate(date);
                    if (!dateUserLocationLine.isEmpty()) drawUserLocation();
                    else Toast.makeText(getContext(), "저장된 데이터가 없습니다.", Toast.LENGTH_LONG).show();
                }
                else mapView.removeAllPolylines();
            }
        };

        pd = new YearMonthDayPickerDialog();
        pd.setListener(dateSetListener);

        filterBtn = view.findViewById(R.id.filter);
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show(getChildFragmentManager(), "YearMonthPickerTest");
            }
        });

        return view;
    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint currentLocation, float accuracyInMeters) {
        MapPoint.GeoCoordinate mapPointGeo = currentLocation.getMapPointGeoCoord();
        Log.i("HomeFragment", String.format("MapView onCurrentLocationUpdate (%f,%f) accuracy (%f)", mapPointGeo.latitude, mapPointGeo.longitude, accuracyInMeters));

    }
    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {
    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {
    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {

    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면
            boolean check_result = true;

            // 모든 퍼미션을 허용했는지 체크합니다.
            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }

            if ( check_result ) {
                Log.d("@@@", "start");
                //위치 값을 가져올 수 있음

            }
            else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있다
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), REQUIRED_PERMISSIONS[0])) {
                    Toast.makeText(getActivity(), "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    getActivity().finish();
                }else {
                    Toast.makeText(getActivity(), "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    void checkRunTimePermission(){

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(view.getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED ) {
            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)
            // 3.  위치 값을 가져올 수 있음

        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.
            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), REQUIRED_PERMISSIONS[0])) {
                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(getActivity(), "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }
        }
    }

    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                             + "위치 설정을 수정하시겠습니까?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case GPS_ENABLE_REQUEST_CODE:
                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {
                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }
                break;
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }

    public UserLocation getUserLocation(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatDate = sdfNow.format(date);

        GpsTracker gpsTracker = new GpsTracker(getContext());
        double latitude = gpsTracker.getLatitude(); // 위도
        double longitude = gpsTracker.getLongitude(); //경도

        UserLocation userLocation = new UserLocation();
        userLocation.setDateByDateTime(formatDate);
        userLocation.setTimeByDateTime(formatDate);
        userLocation.setLatitude(latitude);
        userLocation.setLongitude(longitude);

        return userLocation;
    }

    public void drawUserLocation(){
        //기존 동선 삭제
        mapView.removeAllPolylines();

        //선그리기
        MapPolyline polyline = new MapPolyline();
        polyline.setTag(1000);
        polyline.setLineColor(Color.argb(128, 255, 51, 0)); // Polyline 컬러 지정.

        for (UserLocation u : dateUserLocationLine) {
            System.out.println("유저동선 : " +u.getDate() + " " + u.getTime() + " " + u.getLatitude() + ", " + u.getLongitude());
            polyline.addPoint(MapPoint.mapPointWithGeoCoord(u.getLatitude(), u.getLongitude()));
        }

        // Polyline 지도에 올리기.
        mapView.addPolyline(polyline);

        // 지도뷰의 중심좌표와 줌레벨을 Polyline이 모두 나오도록 조정.
        MapPointBounds mapPointBounds = new MapPointBounds(polyline.getMapPoints());
        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, 100)); //100px
    }

    public void allCoronaLocation(){

        List<CoronaLocation> allCoronaLocationList = new ArrayList<>();
        for( CoronaLocation sampleCopy : sampleCoronaLocationList) allCoronaLocationList.add(new CoronaLocation(sampleCopy));
        List<CoronaLocation> onlyLocation = oneLocationManyTimeList(allCoronaLocationList);
        markers(onlyLocation);

    }

    public void coronaLocationByDate(String date){

        List<CoronaLocation> onlyLocaiontByDateList = new ArrayList<>();
        for( CoronaLocation cc : sampleCoronaLocationList){
            if(cc.getDate().equals(date)) {
                onlyLocaiontByDateList.add(cc);
                System.out.println(cc.getName() + " 왜 벌써 이지랄 "+cc.getAllDateTime());
            }
        }
        for (CoronaLocation test : onlyLocaiontByDateList){
            System.out.println(test.getName());
            System.out.println(test.getName() + test.getDate() +test.getAllDateTime() +" 들어감");
        }

        markers(oneLocationManyTimeList(onlyLocaiontByDateList));

    }

    public List<CoronaLocation> oneLocationManyTimeList(List<CoronaLocation> LocationData){
        List<CoronaLocation> oneLocationManyTimeList = new ArrayList<>();
        List<String> coronaLaLos = new ArrayList<>();
        for( CoronaLocation c : LocationData) {
            //위도와 경도 기준으로
            //중복되는 장소가 없다면 장소(CoronaLocation추가)
            if (!coronaLaLos.contains((c.getLatitude() + " " + c.getLatitude()))) {
                coronaLaLos.add(c.getLatitude() + " " + c.getLatitude());
                oneLocationManyTimeList.add(c);
            }
            //중복되는 장소가 있다면  AlldateTime만 재 설정
            else {
                for (CoronaLocation s : oneLocationManyTimeList) {
                    if ((s.getLatitude() + " " + c.getLatitude()).equals(c.getLatitude() + " " + c.getLatitude())) {
                        s.setAllDateTimeAdd(c.getDate() + " " + c.getArrivedTime() + " - " + c.getExitTime());
                    }
                }
            }
        }

        return oneLocationManyTimeList;
    }

    public void markers(List<CoronaLocation> LocationList){
        mapView.removeAllPOIItems();

        MapPOIItem[] markers;
        List<MapPOIItem> markerList = new ArrayList<>();

        if(!LocationList.isEmpty()) {
            for(CoronaLocation c : LocationList){
                MapPOIItem marker = new MapPOIItem();
                marker.setItemName(c.getName());
                marker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 마커타입을 커스텀 마커로 지정.
                marker.setCustomImageResourceId(R.drawable.maker); // 마커 이미지.
                marker.setCustomSelectedImageResourceId(R.drawable.clickmaker); // 마커 선택시 이미지.
                marker.setMapPoint(MapPoint.mapPointWithGeoCoord(Double.parseDouble(c.getLatitude()), Double.parseDouble(c.getLongitude())));//마커위치
                marker.setUserObject(c);
                markerList.add(marker);
            }
        }
        else Toast.makeText(getContext(),"확진자 방문 장소 데이터가 없습니다.",Toast.LENGTH_LONG);
        markers = markerList.toArray(new MapPOIItem[markerList.size()]);
        mapView.addPOIItems(markers); //마커 등록
    }

}