package com.coronacircle.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.coronacircle.R;

public class PermissionCheck extends AppCompatActivity {

    private PermissionSupport permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_permit);

        permissionCheck();
    }

    private  void permissionCheck(){
        permission = new PermissionSupport(this, this);

        if(!permission.checkPermission()){
            permission.requestPermission();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if(!permission.permissionResult(requestCode, permissions,grantResults)){
            permission.requestPermission();
        }
    }
}
