package com.coronacircle.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.coronacircle.R;
import com.coronacircle.utils.PermissionSupport;

public class PermissionCheckActivity extends AppCompatActivity {

    private PermissionSupport permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissioncheck);

        Button btnConfirm = findViewById(R.id.confirm_button);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PermissionCheckActivity.this, MainActivity.class);
                startActivity(intent);
//                finish();
            }
        });

        permissionCheck();
    }

    private void permissionCheck(){
        permission = new PermissionSupport(this, this);

        if(!permission.checkPermission()){
            permission.requestPermission();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for(int i=0; i<grantResults.length; i++){
            if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"승인이 허가되었습니다.",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this,"앱을 이용하기 위해선 승인이 필요합니다.",Toast.LENGTH_LONG).show();
            }
        }
    }
}