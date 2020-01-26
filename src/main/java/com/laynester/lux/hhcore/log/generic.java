package com.laynester.lux.hhcore.log;

import static com.laynester.lux.Lux.pluginPrefix;

public class generic {
    public static void logMessage(String message) {
        System.out.println("[~] [" + pluginPrefix + "] " + message);
    }
    public static void logInstall(String message) {
        System.out.println("[~] [" + pluginPrefix + "] [INSTALL] " + message);
    }
    public static void logCore(String message) {
        System.out.println("[~] [" + pluginPrefix + "] [HH] " + message);
    }
}
