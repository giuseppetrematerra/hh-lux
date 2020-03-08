package com.laynester.lux.hhplugin.installer;

import com.laynester.lux.Lux;

public class versionManager {
    public static String parseVersion(int version) {
        switch(version) {
            case 1:
                return "1.0.0";
            case 2:
                return "2.3.0";
            default:
                return null;
        }
    }
    public static String getLatestVersion() {
        return Lux.version;
    }
}
