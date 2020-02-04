package com.laynester.lux;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.HabboPlugin;
import com.eu.habbo.plugin.events.emulator.EmulatorLoadedEvent;
import com.laynester.lux.hhcore.log.generic;
import com.laynester.lux.hhplugin.load.loadEvents;

import static com.laynester.lux.hhcore.checkIntegrity.checkIntegrity;
import static com.laynester.lux.hhplugin.loadAll.loadAll;

// This plugin is originally created by Layne but maintained by Hackerman
// Thank you Layne <3.

public class Lux extends HabboPlugin implements EventListener {
    public static String pluginShort = "lux";
    public static String pluginPrefix = "LUX";
    public static String pluginName = "Lux";
    public static String pluginAuthor = "Layne & Hackerman";
    public static String version = "2.4.1";
    public static int productId = 4;
    public static Lux INSTANCE = null;

    // Layné west is copyrighted by Hackerman (c) 2020-2070
    // lol hackerman.tech will soon be as rich as Apple
    // Wanted to fit a John's forehead joke in here but then I ran out of space

    public void onEnable () throws Exception {
        Emulator.getPluginManager().registerEvents(this, this);
        System.out.println ( "[~] Detected plugin `" + pluginName + "` by " + pluginAuthor + " version " + version + "! Support @ Hackerman.tech");
    }


    public static void main(String[] args)
    {
        generic.logMessage("Please move this file in your plugins folder!");
    }

    @Override
    public void onDisable() {
        System.out.println ( "[~] Disabled plugin `" + pluginName + "` by " + pluginAuthor + " version " + version + " -> OK!");
    }

    @Override
    public boolean hasPermission(Habbo habbo, String s) {return false; }

    @EventHandler
    public void onEmulatorLoadedEVERYTHING ( EmulatorLoadedEvent e ) throws Exception {
        long startTime = System.currentTimeMillis ();
        INSTANCE = this;
        if (checkIntegrity()) {

            Emulator.getPluginManager().registerEvents(this, this);
            Emulator.getPluginManager().registerEvents(this, (EventListener) new loadEvents());

            // Loader
            loadAll();

            System.out.println ( "[~] Loaded plugin " + pluginName + " " + version + " in " + (System.currentTimeMillis () - startTime) + "ms -> OK" );
        }
    }
}
