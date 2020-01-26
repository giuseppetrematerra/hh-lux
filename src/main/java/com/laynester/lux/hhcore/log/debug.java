package com.laynester.lux.hhcore.log;

import com.eu.habbo.Emulator;

import static com.laynester.lux.Lux.pluginName;

public class debug {
    public static void logDebug(String module, String message) {
        if (Emulator.getConfig().getValue("hh.debug").equalsIgnoreCase("true")) {
            System.out.println("[@] " + pluginName + " [" + module + "] " + message);
        }
    }
}
