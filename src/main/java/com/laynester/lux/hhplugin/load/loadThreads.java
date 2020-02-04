package com.laynester.lux.hhplugin.load;

import com.eu.habbo.plugin.EventListener;
import com.laynester.lux.commands.brb;
import com.laynester.lux.hhcore.log.error;
import com.laynester.lux.hhcore.log.generic;

public class loadThreads implements EventListener {
    public static void loadThreads() {
        long startTimeModuleCommandManager = System.currentTimeMillis();
        try {
            brb.startBackgroundThread();
            generic.logMessage("Thread Manager -> " + (System.currentTimeMillis () - startTimeModuleCommandManager) + "ms -> OK");
        } catch (Exception ex) {
            generic.logMessage("Thread Manager -> ERROR");
            error.logError("threadManager","Lux > Emulator Load > Load Thread","", 2, false, ex);
        }
    }
}
