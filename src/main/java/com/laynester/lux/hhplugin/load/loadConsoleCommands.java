package com.laynester.lux.hhplugin.load;

import com.laynester.lux.hhcore.consoleCommands.PluginConsoleCommand;
import com.laynester.lux.hhcore.util.LogHelper;

import static com.eu.habbo.core.consolecommands.ConsoleCommand.addCommand;

public class loadConsoleCommands {
    public static void loadConsoleCommands() {
        long startTimeModuleCommandManager = System.currentTimeMillis();
        try {
            addCommand(new PluginConsoleCommand());

            LogHelper.logMessage("Console Manager -> " + (System.currentTimeMillis () - startTimeModuleCommandManager) + "ms -> OK");
        } catch (Exception ex) {
            LogHelper.logMessage("Console Manager -> ERROR");
            LogHelper.logError("ConsoleManager","Lux > Emulator Load > Load Console Commands","", 2, false, ex);
        }
    }
}
