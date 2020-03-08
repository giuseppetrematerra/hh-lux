package com.laynester.lux.hhplugin.load;

import com.eu.habbo.plugin.EventListener;
import com.laynester.lux.commands.BRBCommand;
import com.laynester.lux.hhcore.util.LogHelper;

public class loadThreads implements EventListener {
    public static void loadThreads() {
        long startTimeModuleCommandManager = System.currentTimeMillis();
        try {
            BRBCommand.startBackgroundThread();
            LogHelper.logMessage("Thread Manager -> " + (System.currentTimeMillis () - startTimeModuleCommandManager) + "ms -> OK");
        } catch (Exception ex) {
            LogHelper.logMessage("Thread Manager -> ERROR");
            LogHelper.logError("threadManager","Lux > Emulator Load > Load Thread","", 2, false, ex);
        }
    }
}
