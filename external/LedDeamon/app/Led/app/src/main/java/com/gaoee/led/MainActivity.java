package com.gaoee.led;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Intent newIntent;
    private final static String TAG="com.gaoee.led.Main";
    //FIXME:LED硬件服务
    private LedService ledHalService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        newIntent = new Intent(this, BootCompleteService.class);
        startService(newIntent);

        Toast toast=Toast.makeText(this, "service started", Toast.LENGTH_SHORT);
        toast.show();

        finish();

        //FIXME:初始化LED硬件服务对象，打开硬件设备
        ledHalService = new LedService();
        if (ledHalService.open() != 0) {
            Log.e(TAG, "service open led device failed");
        } else {
            Log.d(TAG, "service open led device succeed");
        }
    }

    public void buttonStartServiceClicked(View view){
        startService(newIntent);
    }

    public void buttonStopServiceClicked(View view){
        stopService(newIntent);
    }

    public void checkboxAlarmClicked(View view){
        CheckBox box = (CheckBox)view;
        ledHalService.set(LedService.LED_ID_ALARM,box.isChecked()?1:0);
    }

    public void checkboxUsbClicked(View view){
        CheckBox box = (CheckBox)view;
        ledHalService.set(LedService.LED_ID_USB,box.isChecked()?1:0);
    }

    public void checkboxPlayClicked(View view){
        CheckBox box = (CheckBox)view;
        ledHalService.set(LedService.LED_ID_PLAY,box.isChecked()?1:0);
    }

    public void checkboxPauseClicked(View view){
        CheckBox box = (CheckBox)view;
        ledHalService.set(LedService.LED_ID_PAUSE,box.isChecked()?1:0);
    }

    public void checkboxSecClicked(View view){
        CheckBox box = (CheckBox)view;
        ledHalService.set(LedService.LED_ID_SEC,box.isChecked()?1:0);
    }

    public void checkboxEthClicked(View view){
        CheckBox box = (CheckBox)view;
        ledHalService.set(LedService.LED_ID_ETH,box.isChecked()?1:0);
    }

    public void checkboxWifiClicked(View view){
        CheckBox box = (CheckBox)view;
        ledHalService.set(LedService.LED_ID_WIFI,box.isChecked()?1:0);
    }
}
