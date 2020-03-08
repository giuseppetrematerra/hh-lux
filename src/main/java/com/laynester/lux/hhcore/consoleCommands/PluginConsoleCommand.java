package com.laynester.lux.hhcore.consoleCommands;

import com.eu.habbo.core.consolecommands.ConsoleCommand;
import com.laynester.lux.hhcore.util.LogHelper;
import com.laynester.lux.hhcore.util.CheckIntegrity;

import static com.laynester.lux.Lux.*;
import static com.laynester.lux.hhplugin.loadAll.loadAll;

public class PluginConsoleCommand extends ConsoleCommand {

    public PluginConsoleCommand() {
        super(pluginShort, "Hackerman.tech plugin command for " + pluginName);
    }

    public void handle(String[] args) throws Exception {
        switch (args[1]) {
            case "":
            case "help":
                System.out.println("Listing console commands for " + pluginName  + ":");
                System.out.println("      " + pluginName +  " about");
                System.out.println("      " + pluginName + " reload");
                System.out.println("      " + pluginName + " update");
                System.out.println("      " + pluginName + " report");
                break;
            case "about":
                System.out.println("\nInformation about `" + pluginName + "` (ID: " + productId  + "):" +
                        "\n      Plugin Author: " + pluginAuthor +
                        "\n      Plugin Version: " + version +
                        "\n      Latest Version: " + CheckIntegrity.latestVersion + "\n");
                break;
            case "reload":
                LogHelper.logMessage("Reloading...");
                loadAll();
                LogHelper.logMessage("Reloaded.");
                break;
            case "update":
                System.out.println("Coming soon");
                break;
            case "report":
                System.out.println("Coming soon");
                break;
            default:
                System.out.println("Unknown Command. Type \"" + pluginShort + " help\" 'for help.");
                break;
        }
    }
}

