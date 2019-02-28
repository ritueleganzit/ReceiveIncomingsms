package com.receiveincomingsms;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<User>arrayList=new ArrayList<>();
    ArrayList<User>arrayList2=new ArrayList<>();
    Button btn_silent,btn_norml;
    int data;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.RECEIVE_SMS, android.Manifest.permission.ACCESS_NOTIFICATION_POLICY, Manifest.permission.READ_CONTACTS, Manifest.permission.SEND_SMS}, 1);
        setContentView(R.layout.activity_main);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        btn_norml = findViewById(R.id.btn_norml);
        btn_silent = findViewById(R.id.btn_silent);
        // Check if the notification policy access has been granted for the app.

        try {
            data = Settings.Global.getInt(getContentResolver(), "zen_mode");

            if (data == 0) {
                if (!mNotificationManager.isNotificationPolicyAccessGranted()) {
                    Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                    startActivity(intent);
                }
            } else {

            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        btn_norml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioManager audiomanage = (AudioManager)

                        getSystemService(Context.AUDIO_SERVICE);
                audiomanage.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            }
        });

        btn_silent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                Log.d("kkk", "ok");
            }
        });


       

    }



}
