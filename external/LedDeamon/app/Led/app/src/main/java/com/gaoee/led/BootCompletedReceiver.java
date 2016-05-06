package com.gaoee.led;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BootCompletedReceiver extends BroadcastReceiver {
    public BootCompletedReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)){
            Toast.makeText(context, "Led service Started", Toast.LENGTH_SHORT).show();

            // android3.1以及之后版本广播接收器不能在启动应用前注册。可以通过设置intent的flag为
            // Intent.FLAG_INCLUDE_STOPPED_PACKAGES将广播发送给未启动应用的广播接收器。
//            Intent newIntent = new Intent(context, BootCompleteService.class);
//            newIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
//            context.startService(newIntent);
            Intent newIntent = new Intent(context, MainActivity.class);
            newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(newIntent);
        }
    }
}
