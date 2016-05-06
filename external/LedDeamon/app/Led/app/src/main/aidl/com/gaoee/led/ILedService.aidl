// ILedService.aidl
package com.gaoee.led;

// Declare any non-default types here with import statements

interface ILedService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);


	int open();
	int close();
	int set(int id, int value);
}
