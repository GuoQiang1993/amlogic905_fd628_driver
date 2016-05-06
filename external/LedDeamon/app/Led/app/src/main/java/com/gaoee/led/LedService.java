package com.gaoee.led;

import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by yixiaoyang on 16-4-25.
 */
public class LedService extends ILedService.Stub {
    private static final String TAG = "LedService";
    private static final String libStr="led-jni";
    private boolean opened;

    public static final int LED_ID_ALARM=0;
    public static final int LED_ID_USB=1;
    public static final int LED_ID_PLAY=2;
    public static final int LED_ID_PAUSE=3;
    public static final int LED_ID_SEC=4;
    public static final int LED_ID_ETH=5;
    public static final int LED_ID_WIFI=6;

    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

    }

    public LedService() {
        opened = false;
    }



    static{
        System.loadLibrary(libStr);
        ledInit();
    }

    public int open(){
        int ret = ledOpen();
        opened = ret == 0;
        return ret;
    }

    public int close(){
        return ledClose();
    }

    public int set(int id, int value){
        return ledSet(id,value);
    }

    private static native int ledInit();
    private static native int ledOpen();
    private static native int ledClose();
    private static native int ledSet(int id, int value);

    /*
    public int open(){
        Log.d(TAG, "led opened");
        return 0;
    }

    public int close(){
        Log.d(TAG, "led closed");
        return 0;
    }

    public int set(int id, int value) {
        String str = new String("set ");
        str += Integer.toString(id);
        str += ",value ";
        str += Integer.toString(value);
        Log.d(TAG, str);
        return 0;
    }*/
}
