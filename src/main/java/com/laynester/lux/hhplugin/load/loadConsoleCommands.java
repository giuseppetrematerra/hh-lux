package com.laynester.lux.hhplugin.load;

import com.laynester.lux.hhcore.consoleCommands.pluginConsoleCommand;
import com.laynester.lux.hhcore.log.error;
import com.laynester.lux.hhcore.log.generic;

import static com.eu.habbo.core.consolecommands.ConsoleCommand.addCommand;

public class loadConsoleCommands {
    public static void loadConsoleCommands() {
        long startTimeModuleCommandManager = System.currentTimeMillis();
        try {
            addCommand(new pluginConsoleCommand());

            generic.logMessage("Console Manager -> " + (System.currentTimeMillis () - startTimeModuleCommandManager) + "ms -> OK");
        } catch (Exception ex) {
            generic.logMessage("Console Manager -> ERROR");
            error.logError("ConsoleManager","Lux > Emulator Load > Load Console Commands","", 2, false, ex);
        }
    }
}
