package com.gaoee.led;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

public class BootCompleteService extends Service {
    private final static String TAG="com.gaoee.led.service";
    // LED硬件服务
    private LedService ledHalService = null;
    // LED状态表
    private SparseArray<LedStatus> map;
    // 网络变更监听器
    private BroadcastReceiver connectivityReceiver;
    // USB变更监听器
    private BroadcastReceiver usbReceiver;


    public BootCompleteService() {
        // 初始化LED硬件服务对象，打开硬件设备
        ledHalService = new LedService();
        if (ledHalService.open() != 0) {
            Log.e(TAG, "service open led device failed");
        } else {
            Log.d(TAG, "service open led device succeed");
        }
        // 初始化LED状态表
        map = new SparseArray<>();
        map.put(LedService.LED_ID_ALARM,new LedStatus(false));
        map.put(LedService.LED_ID_USB,new LedStatus(false));
        map.put(LedService.LED_ID_PLAY,new LedStatus(false));
        map.put(LedService.LED_ID_PAUSE,new LedStatus(false));
        map.put(LedService.LED_ID_SEC,new LedStatus(false));
        map.put(LedService.LED_ID_ETH,new LedStatus(false));
        map.put(LedService.LED_ID_WIFI,new LedStatus(false));
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // 添加实例
        connectivityReceiver = new NetworkStateReceiver();
        usbReceiver = new USBBroadcastReceiver();

        // action过滤器
        IntentFilter usbFilter = new IntentFilter();
        IntentFilter connectivityFilter = new IntentFilter();

        connectivityFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        // sd card
        usbFilter.addAction(Intent.ACTION_MEDIA_MOUNTED);
        usbFilter.addAction(Intent.ACTION_MEDIA_EJECT);
        usbFilter.addAction(Intent.ACTION_MEDIA_REMOVED);
        usbFilter.addDataScheme("file");

        this.registerReceiver(connectivityReceiver,connectivityFilter);
        this.registerReceiver(usbReceiver,usbFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /**
     * USB监测对象
     */
    public class USBBroadcastReceiver extends BroadcastReceiver {
        public USBBroadcastReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            final int id = LedService.LED_ID_USB;
            LedStatus oldStatus = map.get(id);
            LedStatus newStatus = new LedStatus(false);
            if (intent.getAction().equals(Intent.ACTION_MEDIA_MOUNTED)) {
                // TODO:USB设备挂载
                newStatus.on = true;
                Toast toast=Toast.makeText(context, "Usb Device Mounted", Toast.LENGTH_SHORT);
                toast.show();
            }else if(intent.getAction().equals(Intent.ACTION_MEDIA_EJECT)) {
                // TODO:USB设备卸载
                newStatus.on = false;
                Toast toast=Toast.makeText(context, "Usb Device Eject", Toast.LENGTH_SHORT);
                toast.show();
            }else if(intent.getAction().equals(Intent.ACTION_MEDIA_REMOVED)){
                // TODO:USB设备卸载
                newStatus.on = false;
                Toast toast=Toast.makeText(context, "Usb Device Removed", Toast.LENGTH_SHORT);
                toast.show();
            }else{
                return ;
            }

            if(newStatus.on != oldStatus.on){
                ledHalService.set(id, newStatus.on ? 1 : 0);
                map.put(id,newStatus);
            }
        }
    }

    /**
     * 网络监测对象
     */
    public class NetworkStateReceiver extends BroadcastReceiver {
        public NetworkStateReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                int type = getConnectedType(context);
                LedStatus oldEthStatus = map.get(LedService.LED_ID_ETH);
                LedStatus oldWifiStatus = map.get(LedService.LED_ID_WIFI);
                LedStatus newEthStatus = new LedStatus(oldEthStatus);
                LedStatus newWifiStatus = new LedStatus(oldWifiStatus);

                switch (type){
                    case ConnectivityManager.TYPE_WIFI: {
                        if(!oldWifiStatus.on){
                            newWifiStatus.on = true;
                        }
                        break;
                    }
                    case ConnectivityManager.TYPE_ETHERNET: {
                        if(!oldEthStatus.on){
                            newEthStatus.on = true;
                        }
                        break;
                    }
                    default: {
                        if(oldEthStatus.on){
                            newEthStatus.on = false;
                        }
                        if(oldWifiStatus.on){
                            newWifiStatus.on = false;
                        }
                        break;
                    }
                }

                if(newWifiStatus.on != oldWifiStatus.on){
                    ledHalService.set(LedService.LED_ID_WIFI, newWifiStatus.on ? 1 : 0);
                    map.put(LedService.LED_ID_WIFI,newWifiStatus);
                }
                if(newEthStatus.on != oldEthStatus.on){
                    ledHalService.set(LedService.LED_ID_ETH, newEthStatus.on ? 1 : 0);
                    map.put(LedService.LED_ID_ETH,newEthStatus);
                }
            }
        }

        public int getConnectedType(Context context) {
            if (context != null) {
                ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
                if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                    return mNetworkInfo.getType();
                }
            }
            return -1;
        }
    }

}
