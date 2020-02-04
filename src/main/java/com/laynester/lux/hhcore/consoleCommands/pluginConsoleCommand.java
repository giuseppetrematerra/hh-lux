package com.laynester.lux.hhcore.consoleCommands;

import com.eu.habbo.core.consolecommands.ConsoleCommand;
import com.laynester.lux.hhcore.checkIntegrity;
import com.laynester.lux.hhcore.log.generic;

import static com.laynester.lux.Lux.*;
import static com.laynester.lux.hhplugin.loadAll.loadAll;

public class pluginConsoleCommand
        extends ConsoleCommand
{
    public pluginConsoleCommand() { super(pluginShort, "Hackerman.tech plugin command for " + pluginName); }
    public void handle(String[] args) throws Exception {
       if (args[1] == null || args[1].equalsIgnoreCase("help")) {
           System.out.println("Listing console commands for " + pluginName  + ":");
           System.out.println("      " + pluginName +  " about");
           System.out.println("      " + pluginName + " reload");
           System.out.println("      " + pluginName + " update");
           System.out.println("      " + pluginName + " report");
       }
       if (args[1].equalsIgnoreCase("about")) {
           System.out.println("\nInformation about `" + pluginName + "` (ID: " + productId  + "):" +
                           "\n      Plugin Author: " + pluginAuthor +
                           "\n      Plugin Version: " + version +
                           "\n      Latest Version: " + checkIntegrity.latestVersion + "\n");
       }
        if (args[1].equalsIgnoreCase("reload")) {
            generic.logMessage("Reloading...");
            loadAll();
            generic.logMessage("Reloaded.");
        }
        if (args[1].equalsIgnoreCase("update")) {
            System.out.println("Coming soon");
        }
        if (args[1].equalsIgnoreCase("report")) {
            System.out.println("Coming soon");
        }
       System.out.println("Unknown Command. Type \"" + pluginShort + " help\" 'for help.");
    }
}

