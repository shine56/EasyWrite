package com.shine56.easywrite.ui.longessay;

public class LacUtil {

    static {
        System.loadLibrary("native_lib");
    }

    public native void initLac(String model_path);

    public native void releaseLac();

    public native String stringFromJNI();

    public native String stringCutFromJNI(String source_text);
}
