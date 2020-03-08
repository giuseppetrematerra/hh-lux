package com.laynester.lux.hhplugin.load;

import com.eu.habbo.plugin.EventListener;
import com.laynester.lux.hhcore.util.LogHelper;
import com.laynester.lux.hhplugin.installer.Registry;

public class loadRegistry implements EventListener {
    public void loadRegistry() {
        long startTimeModuleCommandManager = System.currentTimeMillis();
        try {
            Registry.load();
            LogHelper.logMessage("Registry Manager -> " + (System.currentTimeMillis () - startTimeModuleCommandManager) + "ms -> OK");
        } catch (Exception ex) {
            LogHelper.logMessage("Registry Manager -> ERROR");
            LogHelper.logError("registryManager","Lux > Emulator Load > Load Registry","", 2, false, ex);
        }
    }
}
