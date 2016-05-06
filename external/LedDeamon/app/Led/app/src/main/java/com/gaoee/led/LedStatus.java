package com.gaoee.led;

/**
 * Created by yixiaoyang on 16-4-26.
 */
public class LedStatus {
    public boolean on;

    public LedStatus(boolean isOn){
        on = isOn;
    }

    public  LedStatus(LedStatus obj){
        this.on = obj.on;
    }
}
