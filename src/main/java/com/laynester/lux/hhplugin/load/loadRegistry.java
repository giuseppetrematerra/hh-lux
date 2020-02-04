package com.laynester.lux.hhplugin.load;

import com.eu.habbo.plugin.EventListener;
import com.laynester.lux.hhcore.log.error;
import com.laynester.lux.hhcore.log.generic;
import com.laynester.lux.hhplugin.installer.Registry;

public class loadRegistry implements EventListener {
    public void loadRegistry() {
        long startTimeModuleCommandManager = System.currentTimeMillis();
        try {
            Registry.load();
            generic.logMessage("Registry Manager -> " + (System.currentTimeMillis () - startTimeModuleCommandManager) + "ms -> OK");
        } catch (Exception ex) {
            generic.logMessage("Registry Manager -> ERROR");
            error.logError("registryManager","Lux > Emulator Load > Load Registry","", 2, false, ex);
        }
    }
}
