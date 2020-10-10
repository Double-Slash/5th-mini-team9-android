package com.coronacircle.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.coronacircle.R;
import com.coronacircle.model.data.CoronaLocation;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;

import java.util.List;

    /*
     * 맵 마커 위 말풍선 커스텀
     * */
public class CustomCalloutBalloonAdapter implements CalloutBalloonAdapter {
    private final View mCalloutBalloon;
    private List maps;

    private String locationName;
    private String locationAddress;
    private String locationTime;

    private Context context;

    public CustomCalloutBalloonAdapter(Activity activity) {
        this.maps = maps;
        mCalloutBalloon = activity.getLayoutInflater().inflate(R.layout.custom_callout_balloon, null);
    }

    @Override
    public View getCalloutBalloon(MapPOIItem poiItem) {
        ((TextView) mCalloutBalloon.findViewById(R.id.location_name)).setText("태릉 우성 아파트2");
        ((TextView) mCalloutBalloon.findViewById(R.id.location_name)).setText(poiItem.getItemName());
        CoronaLocation c = (CoronaLocation)poiItem.getUserObject();
        ((TextView) mCalloutBalloon.findViewById(R.id.location_address)).setText(c.getCity()+" "+c.getDetailed());
//        ((TextView) mCalloutBalloon.findViewById(R.id.location_time)).setText("2020년9월21일 17:30-18:30\n2020년9월23일 17:30-18:30\n2020년9월24일 17:30-18:30\n2020년9월29일 17:30-18:30");
        ((TextView) mCalloutBalloon.findViewById(R.id.location_time)).setText(c.getAllDateTime());
        return mCalloutBalloon;
    }

    public void setLocationInfo(String locationName, String locationAddress, String locationTime){

    }

    @Override
    public View getPressedCalloutBalloon(MapPOIItem poiItem) {
        return mCalloutBalloon;
    }
}