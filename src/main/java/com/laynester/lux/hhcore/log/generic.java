package com.laynester.lux.hhcore.log;

import static com.laynester.lux.Lux.pluginPrefix;

public class generic {
    public static void logMessage(String message) {
        System.out.println("[~] [" + pluginPrefix + "] " + message);
    }
}
