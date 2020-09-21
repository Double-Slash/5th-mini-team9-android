package com.coronacircle.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.coronacircle.R;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;

import java.util.List;

public class CustomCalloutBalloonAdapter implements CalloutBalloonAdapter {
    private final View mCalloutBalloon;
    private List maps;
    private Context context;

    public CustomCalloutBalloonAdapter(Activity activity) {
        this.maps = maps;
        mCalloutBalloon = activity.getLayoutInflater().inflate(R.layout.custom_callout_balloon, null);
    }

    @Override
    public View getCalloutBalloon(MapPOIItem poiItem) {
//        ((TextView) mCalloutBalloon.findViewById(R.id.title)).setText(poiItem.getItemName());
        ((TextView) mCalloutBalloon.findViewById(R.id.title)).setText("장소이름");
        ((TextView) mCalloutBalloon.findViewById(R.id.time)).setText("확진자 방문 시간 : 15:33 ~ 16 : 12");
        return mCalloutBalloon;
    }

    @Override
    public View getPressedCalloutBalloon(MapPOIItem poiItem) {
        return mCalloutBalloon;
    }
}