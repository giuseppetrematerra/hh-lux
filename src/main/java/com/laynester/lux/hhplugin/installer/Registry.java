package com.laynester.lux.hhplugin.installer;

import com.eu.habbo.Emulator;
import com.laynester.lux.hhcore.log.generic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import static com.laynester.lux.Lux.pluginName;

public class Registry {

    public static void install(String version) throws Exception {
        boolean reloadPermissions = false;
        boolean installSuccess = true;
        int errors = 0;
        long startTime = System.currentTimeMillis ();
        generic.logInstall("Starting to install " + pluginName + " version " + version + ":");

        // Installer V1.0.0
        if (version.equalsIgnoreCase("1.0.0")) {
            // Create table lux_logins
            try (Connection connection = Emulator.getDatabase().getDataSource().getConnection(); Statement statement = connection.createStatement()) {
                statement.execute("CREATE TABLE IF NOT EXISTS `lux_logins` ( `id` int(11) unsigned NOT NULL AUTO_INCREMENT, `user_id` int(11) NOT NULL, `timestamp` int(30) NOT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;");
                System.out.println("              -> Creating table `lux_logins` -> OK");
            } catch (SQLException e) {
                System.out.println("              -> Creating table `lux_logins` -> ERROR");
                System.out.println("              EXCEPTION STACKTRACE -> " + e.getMessage());
                installSuccess = false;
                errors++;
            }

            // Altering table rooms
            try (Connection connection = Emulator.getDatabase().getDataSource().getConnection(); Statement statement = connection.createStatement()) {
                statement.execute("ALTER TABLE  `rooms` ADD  `is_casino` ENUM('0','1') NOT NULL DEFAULT  '0'");
                System.out.println("              -> Altering table `rooms` -> OK");
            } catch (SQLException e) {
                System.out.println("              -> Altering table `rooms` -> ERROR");
                System.out.println("              EXCEPTION STACKTRACE -> " + e.getMessage());
                installSuccess = false;
                errors++;
            }

            Emulator.getTexts().register("habbo.not.found","User not found...");
            Emulator.getTexts().register("lux.mentions", "%username% has mentioned you.\r\nClick to follow");
            Emulator.getConfig().register("lux.mentions.enabled", "1");
            Emulator.getConfig().register("lux.mentions.image", "${image.library.url}/notifications/mention.gif");
            System.out.println("              -> Settings for `Mentions` -> OK");

            // Welcome Alert registry
            Emulator.getConfig().register("lux.welcomeAlert.enabled", "1");
            Emulator.getConfig().register("lux.welcomeAlert.link", "habbopages/welcome.txt");
            System.out.println("              -> Settings for `Welcome Alert` -> OK");

            // daily gifts
            Emulator.getConfig().register("lux.daily.gifts", "2;3;4;5;6;7;8;9;10");
            Emulator.getConfig().register("lux.daily.credits", "1000");
            Emulator.getConfig().register("lux.daily.duckets", "1000");
            Emulator.getConfig().register("lux.daily.diamonds", "25");
            Emulator.getConfig().register("lux.daily.seasonal.type", "103");
            Emulator.getConfig().register("lux.daily.seasonal", "30");
            Emulator.getConfig().register("lux.daily.enabled", "1");
            Emulator.getTexts().register("lux.daily.gift", "Heres your gift!!");
            System.out.println("              -> Settings for `Daily Gifts` -> OK");

            // Bubble Alert Command
            Emulator.getTexts().register("commands.description.cmd_bubblealert", ":bubblealert <type>");
            Emulator.getTexts().register("lux.cmd_bubblealert.keys", "bubblealert;balert;ba");
            Emulator.getTexts().register("lux.cmd_bubblealert.incorrect.usage", "Missing arguments for bubble alert command!");
            Emulator.getTexts().register("lux.cmd_bubblealert.unknown", "Unknown argument..");
            Emulator.getConfig().register("lux.bubblealert.event.image", "${image.library.url}/notifications/event.gif");
            reloadPermissions = registerPermission("cmd_bubblealert", "'0', '1', '2'", "1", reloadPermissions);
            System.out.println("              -> Settings for `Bubble Alerts` -> OK");

            // Roll Dice Command
            Emulator.getTexts().register("commands.description.cmd_rolldice", ":rolldice");
            Emulator.getTexts().register("lux.cmd_rolldice.keys", "rd;rolldice;roll");
            Emulator.getTexts().register("lux.rolldice.rolling", "* Rolling %count% Dice *");
            reloadPermissions = registerPermission("cmd_rolldice", "'0', '1', '2'", "1", reloadPermissions);
            System.out.println("              -> Settings for `Roll Dice` -> OK");

            // Close Dice Command
            Emulator.getTexts().register("commands.description.cmd_closedice", ":closedice <all>");
            Emulator.getTexts().register("lux.cmd_closedice.keys", "cd;closedice");
            Emulator.getTexts().register("lux.closedice.closed", "* Closed %count% Dice *");
            Emulator.getTexts().register("lux.closedice.all", "All");
            reloadPermissions = registerPermission("cmd_closedice", "'0', '1', '2'", "1", reloadPermissions);
            reloadPermissions = registerPermission("cmd_closedice_room", "'0', '1', '2'", "1", reloadPermissions);
            System.out.println("              -> Settings for `Close Dice` -> OK");

            // Casino Command
            Emulator.getTexts().register("commands.description.cmd_makecasino", ":casino");
            Emulator.getTexts().register("lux.cmd_makecasino.keys", "casino;makecasino");
            Emulator.getTexts().register("lux.cmd_makecasino.arguments", "Missing arguments");
            Emulator.getTexts().register("lux.casino.alert", "<b>An Official Casino</b>\r\nThis Room is an Official Casino, Here, you can bet, trade and gamble all kinds of currency, play safe and don't scam!\r\n- Hotel Management");
            Emulator.getTexts().register("lux.casino.enabled", "This room is now a casino.");
            Emulator.getTexts().register("lux.casino.disabled", "This room is no longer a casino");
            reloadPermissions = registerPermission("cmd_makecasino", "'0', '1', '2'", "1", reloadPermissions);
            System.out.println("              -> Settings for `Casino Management System` -> OK");

            // Discord
            Emulator.getConfig().register("lux.discord.enabled","0");
            Emulator.getConfig().register("lux.discord.report","");
            Emulator.getConfig().register("lux.discord.chat","");
            Emulator.getConfig().register("lux.discord.commands","");
            Emulator.getConfig().register("lux.discord.login","");
            Emulator.getConfig().register("lux.discord.roomEntry","");
            Emulator.getConfig().register("lux.discord.bans","");
            Emulator.getTexts().register("lux.discord.login","%username% (%ip%) has entered the hotel");
            Emulator.getTexts().register("lux.discord.reporter","Reporter");
            Emulator.getTexts().register("lux.discord.reported","Reported");
            Emulator.getTexts().register("lux.discord.reason","Reason");
            Emulator.getTexts().register("lux.discord.banInfo","Ban Info");
            Emulator.getTexts().register("lux.discord.banInfo.user","Banned User");
            Emulator.getTexts().register("lux.discord.banInfo.type","Ban Type");
            Emulator.getTexts().register("lux.discord.banInfo.staff","Banned By");
            Emulator.getTexts().register("lux.discord.banInfo.duration","Duration");
            Emulator.getTexts().register("lux.discord.banInfo.reason","Reason");
            Emulator.getTexts().register("lux.discord.room_entry","%username% (%ip%) has entered the room %room_name%");
            Emulator.getTexts().register("lux.discord.command_usage","%username% has used the command :%message%");
            Emulator.getTexts().register("lux.discord.cfh","A user has made a call for help!");
            Emulator.getTexts().register("lux.discord.banned","%username% has been banned!");
            System.out.println("              -> Settings for `Discord Logging` -> OK");

            // Yeet Command
            Emulator.getTexts().register("commands.description.cmd_yeet", ":superpush");
            Emulator.getTexts().register("lux.cmd_yeet.keys","superpush;yeet;spush");
            reloadPermissions = registerPermission("cmd_yeet", "'0', '1', '2'", "1", reloadPermissions);
            System.out.println("              -> Settings for `Super Push` -> OK");

            // Stats Command
            Emulator.getTexts().register("lux.cmd_stats.keys","stats;userstats");
            Emulator.getTexts().register("commands.description.cmd_stats", ":stats");
            Emulator.getTexts().register("lux.rank_name","Rank");
            Emulator.getTexts().register("lux.cmd_stats.of","<b>Stats Of: %username%</b>");
            Emulator.getTexts().register("lux.cmd_stats.currency_info","<b>Currency Info:</b>");
            reloadPermissions = registerPermission("cmd_stats", "'0', '1', '2'", "1", reloadPermissions);
            System.out.println("              -> Settings for `Stats` -> OK");

            // Dance Command
            Emulator.getTexts().register("lux.cmd_dance.keys","dance");
            Emulator.getTexts().register("commands.description.cmd_dance", ":dance");
            Emulator.getTexts().register("lux.cmd_dance.arguments", "Missing arguments");
            reloadPermissions = registerPermission("cmd_dance", "'0', '1', '2'", "1", reloadPermissions);
            System.out.println("              -> Settings for `Dance` -> OK");

            // Users Online Command
            Emulator.getTexts().register("commands.description.cmd_online", ":whosonline");
            Emulator.getTexts().register("lux.cmd_online.keys","whosonline;usersonline;users");
            Emulator.getTexts().register("lux.cmd_online.header","Habbo's Online");
            reloadPermissions = registerPermission("cmd_online", "'0', '1', '2'", "1", reloadPermissions);
            System.out.println("              -> Settings for `Users Online` -> OK");
        }

        if (version.equalsIgnoreCase("2.3.0")) {
            Emulator.getTexts().register("commands.description.cmd_buildheight", ":bh");
            Emulator.getTexts().register("lux.cmd_buildheight.keys","bh");
            Emulator.getTexts().register("lux.cmd_buildheight.changed", "Changed build height to %height%");
            Emulator.getTexts().register("lux.cmd_buildheight.disabled", "Build height removed.");
            Emulator.getTexts().register("lux.cmd_buildheight.not_specified", "No buildheight set. Height must be between 0 - 40.");
            reloadPermissions = registerPermission("cmd_buildheight", "'0', '1', '2'", "1", reloadPermissions);
            System.out.println("              -> Settings for `Build Height` -> OK");

            Emulator.getTexts().register("commands.description.cmd_welcome", ":welcome <username>");
            Emulator.getTexts().register("lux.cmd_welcome.keys", "welcome;willkommen;welkom;bienvenue;bienvenida;bem-vindo");
            Emulator.getTexts().register("lux.cmd_welcome.text", "Welcome %username% to %hotelname%;Enjoy your stay in %hotelname% %username%!");
            reloadPermissions = registerPermission("cmd_welcome", "'0', '1', '2'", "0", reloadPermissions);
            System.out.println("              -> Settings for `Welcome` -> OK");

            Emulator.getTexts().register("commands.description.cmd_disable_effects", ":disableffects");
            Emulator.getTexts().register("lux.cmd_disable_effects.keys", "antieffects;disableffects");
            Emulator.getTexts().register("lux.cmd_disable_effects.effects_enabled", "Effects in this room have been enabled!");
            Emulator.getTexts().register("lux.cmd_disable_effects.effects_disabled", "Effects in this room have been disabled!");
            System.out.println("              -> Settings for `Disable Effects` -> OK");

            Emulator.getTexts().register("commands.description.cmd_brb", ":brb");
            Emulator.getTexts().register("lux.cmd_brb.keys", "afk;brb;");
            Emulator.getTexts().register("lux.cmd_brb.brb", "* %username% is now AFK! *");
            Emulator.getTexts().register("lux.cmd_brb.back", "* %username% is now back! *");
            Emulator.getTexts().register("lux.cmd_brb.time", "* %username% has now been away for %time% minutes *");
            reloadPermissions = registerPermission("cmd_brb", "'0', '1', '2'", "1", reloadPermissions);
            System.out.println("              -> Settings for `Brb` -> OK");

            Emulator.getTexts().register("lux.cmd_pay.keys", "pay;transfer");
            Emulator.getTexts().register("lux.cmd_pay.incorrect.usage", "Missing arguments for pay command!");
            Emulator.getTexts().register("commands.description.cmd_pay", ":pay <username> <amount> <currency>");
            Emulator.getTexts().register("generic.habbo.notfound", "Habbo not found");
            Emulator.getTexts().register("lux.cmd_pay.self", "You cannot pay yourself, silly!");
            Emulator.getTexts().register("lux.cmd_pay.invalid_amount", "Invalid amount! Please use a positive value.");
            Emulator.getTexts().register("lux.cmd_pay.not_enough", "Cannot pay %username%. You don't have %amount% %type%!");
            Emulator.getTexts().register("lux.cmd_pay.invalid_type", "The currency %type% does not exist!");
            Emulator.getTexts().register("lux.cmd_pay.received", "%username% has paid you %amount% %type%!");
            Emulator.getTexts().register("lux.cmd_pay.transferred", "You paid %username% %amount% %type%!");
            reloadPermissions = registerPermission("cmd_pay", "'0', '1'", "1", reloadPermissions);
            System.out.println("              -> Settings for `Pay` -> OK");
        }

        if (version.equalsIgnoreCase("2.4.0")) {
            // Altering table rooms
            try (Connection connection = Emulator.getDatabase().getDataSource().getConnection(); Statement statement = connection.createStatement()) {
                statement.execute("ALTER TABLE  `rooms` ADD  `is_frozen` ENUM('0','1') NOT NULL DEFAULT  '0'");
                System.out.println("              -> Altering table `rooms` -> OK");
            } catch (SQLException e) {
                System.out.println("              -> Altering table `rooms` -> ERROR");
                System.out.println("              EXCEPTION STACKTRACE -> " + e.getMessage());
                installSuccess = false;
                errors++;
            }
            Emulator.getTexts().register("commands.description.cmd_freezeroom", ":freezeroom");
            Emulator.getTexts().register("lux.cmd_freezeroom.keys", "freezeroom");
            Emulator.getTexts().register("lux.cmd_freezeroom.error_frozen", "This room is currently frozen and cannot be modified");
            Emulator.getTexts().register("lux.cmd_freezeroom.entered_frozen_room", "This room was frozen by the Hotel Management and cannot be modified");
            Emulator.getTexts().register("lux.cmd_freezeroom.enabled", "This room is now frozen");
            Emulator.getTexts().register("lux.cmd_freezeroom.disabled", "This room is no longer frozen");
            reloadPermissions = registerPermission("cmd_freezeroom", "'0', '1'", "0", reloadPermissions);
            System.out.println("              -> Settings for `Freeze Room` -> OK");


            Emulator.getTexts().register("lux.error.wait", "Please wait before using this again");
            System.out.println("              -> Updating various variables -> OK");
        }

        if (installSuccess) {
            generic.logInstall("Installed " + pluginName + " version " + version + " in " + (System.currentTimeMillis () - startTime) + "ms\n\n");
        } else {
            generic.logInstall("ERROR IN INSTALLATION: " + errors + " error(s) when trying to install " + pluginName + " version " + version + "!");
            generic.logInstall("Installation will continue as no critical errors were detected\n\n");
        }
        if (reloadPermissions) {
            Emulator.getGameEnvironment().getPermissionsManager().reload();
        }
        Emulator.getConfig().register("lux.version", version);
        Emulator.getConfig().update("lux.version", version);
        load();

    }

    public static void load() throws Exception  {
        if(Emulator.getConfig().getValue("lux.version").equals("") || Emulator.getConfig().getValue("lux.version") == null || Emulator.getConfig().getValue("lux.version").equalsIgnoreCase("1.2.0")) {
            Registry.install("1.0.0");
        }

        if(Emulator.getConfig().getValue("lux.version").equals("1.0.0")) {
            Registry.install("2.3.0");
        }

        if(Emulator.getConfig().getValue("lux.version").equals("2.3.0")) {
            Registry.install("2.4.0");
        }
    }

    private static boolean registerPermission(String name, String options, String defaultValue, boolean defaultReturn)
    {
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection())
        {
            try (PreparedStatement statement = connection.prepareStatement("ALTER TABLE  `permissions` ADD  `" + name +"` ENUM(  " + options + " ) NOT NULL DEFAULT  '" + defaultValue + "'"))
            {
                statement.execute();
                return true;
            }
        } catch (SQLException ignored){}

        return defaultReturn;
    }
    private void updateField(String table, String field1, String value, String field2, String value2) {
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection(); Statement statement = connection.createStatement()) {
            statement.execute("UPDATE `"+table+"` SET `"+field1+"` = '"+value+"' WHERE `"+field2+"` = '"+value2+"'");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
