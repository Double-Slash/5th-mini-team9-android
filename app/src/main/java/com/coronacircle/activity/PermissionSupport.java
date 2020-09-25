package com.coronacircle.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissionSupport {

    private Context context;
    private Activity activity;

    private String[] permissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE
    };

    private List permissionList;

    private final int MULTIPLE_PERMISSIONS =1023;

    public PermissionSupport(Activity _activity, Context _context){
        this.activity = _activity;
        this.context =_context;
    }

    public boolean checkPermission(){
        int result;
        permissionList = new ArrayList<>();

        for(String pm : permissions){
            result = ContextCompat.checkSelfPermission(context, pm);
            if(result != PackageManager.PERMISSION_GRANTED){
                permissionList.add(pm);
            }
        }

        if(!permissionList.isEmpty()){
            return false;
        }
        return true;
    }

    public void requestPermission(){
        ActivityCompat.requestPermissions(activity, (String[]) permissionList.toArray(new String[permissionList.size()]), MULTIPLE_PERMISSIONS);
    }

    public boolean permissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        if(requestCode == MULTIPLE_PERMISSIONS && (grantResults.length >0)){
            for(int i=0; i<grantResults.length; i++){
                if(grantResults[i] == -1){
                    return false;
                }
            }
        }
        return true;
    }
}
