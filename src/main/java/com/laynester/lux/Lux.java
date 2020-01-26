package com.laynester.lux;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.HabboPlugin;
import com.eu.habbo.plugin.events.emulator.EmulatorLoadedEvent;
import com.laynester.lux.commands.CommandManager;
import com.laynester.lux.commands.brb;
import com.laynester.lux.events.*;
import com.laynester.lux.hhcore.log.error;
import com.laynester.lux.hhcore.log.generic;
import com.laynester.lux.installer.Registry;

import java.io.IOException;

import static com.laynester.lux.hhcore.checkIntegrity.checkIntegrity;

public class Lux extends HabboPlugin implements EventListener {
    public static String pluginShort = "lux";
    public static String pluginPrefix = "LUX";
    public static String pluginName = "Lux";
    public static String pluginAuthor = "Layne & Hackerman";
    public static String version = "2.3.0";
    public static int productId = 4;
    public static Lux INSTANCE = null;

    public void onEnable () throws Exception {
        Emulator.getPluginManager().registerEvents(this, this);
        System.out.println ( "[~] Detected plugin `" + pluginName + "` by " + pluginAuthor + " version " + version);
    }

    // Layné west is copyrighted by Hackerman (c) 2020-2070
    // lol hackerman.tech will soon be as rich as Apple
    // Wanted to fit a John's forehead joke in here but then I ran out of space

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
    public void onEmulatorLoadedEVERYTHING ( EmulatorLoadedEvent e ) throws IOException {
        INSTANCE = this;
        if (checkIntegrity()) {
            Emulator.getPluginManager().registerEvents(this, this);
            long startTime = System.currentTimeMillis ();

            CommandManager commands = new CommandManager();
            try {
                commands.load();
                generic.logMessage("Command Manager -> OK");
            } catch (Exception ex) {
                generic.logMessage("Command Manager -> ERROR");
                error.logError("loadCommands","Lux > Emulator Load > Load Commands","", 2, false, ex);
            }

            Registry register = new Registry();
            try {
                register.load();
                generic.logMessage("Registery -> OK");
            } catch (Exception ex) {
                generic.logMessage("Registery -> ERROR -> " + ex.getMessage());
                error.logError("loadRegistery","Lux > Emulator Load > Load Register","", 2, false, ex);
            }

            // Load Events
            Emulator.getPluginManager().registerEvents(this, new LoginEvents());
            Emulator.getPluginManager().registerEvents(this, new UserTalk());
            Emulator.getPluginManager().registerEvents(this, new UserCommand());
            Emulator.getPluginManager().registerEvents(this, new CreditsReceived());
            Emulator.getPluginManager().registerEvents(this, new PointsReceived());
            Emulator.getPluginManager().registerEvents(this, new RoomLoaded());
            Emulator.getPluginManager().registerEvents(this, new Report());
            Emulator.getPluginManager().registerEvents(this, new Banned());


            brb.startBackgroundThread();

            System.out.println ( "[~] Loaded plugin " + pluginName + " " + version + " in " + (System.currentTimeMillis () - startTime) + "ms -> OK!" );
        }
    }



}
