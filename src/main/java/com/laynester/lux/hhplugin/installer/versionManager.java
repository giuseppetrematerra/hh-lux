package com.laynester.lux.hhplugin.installer;

import com.laynester.lux.Lux;

public class versionManager {
    public static String getVersions(int version) {

        if (version == 1) {
            return "1.0.0";
        }
        if (version == 2) {
            return "2.3.0";
        }

        return null;
    }
    public static String getLatestVersion() {
        return Lux.version;
    }
}
