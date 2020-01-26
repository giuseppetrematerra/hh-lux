package com.laynester.lux.installer;

import com.eu.habbo.Emulator;
import com.laynester.lux.Lux;
import com.laynester.lux.hhcore.log.generic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Registry {

    public Registry() { }

    public void load() throws Exception  {
        boolean reloadPermissions = false;
        String version = "1.2.0";
        if(!Emulator.getConfig().getValue("lux.version").equals(version)) {
            if (Emulator.getConfig().getValue("lux.version").equals("")) {
                generic.logMessage("This is your first time running Lux with Morningstar..");
                Emulator.getConfig().register("lux.version", version);
                try (Connection connection = Emulator.getDatabase().getDataSource().getConnection(); Statement statement = connection.createStatement()) {
                    statement.execute("CREATE TABLE IF NOT EXISTS `lux_logins` ( `id` int(11) unsigned NOT NULL AUTO_INCREMENT, `user_id` int(11) NOT NULL, `timestamp` int(30) NOT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;");
                    generic.logMessage("Creating lux_logins Table..");
                } catch (SQLException ignored) {

                }
                try (Connection connection = Emulator.getDatabase().getDataSource().getConnection(); Statement statement = connection.createStatement()) {
                    statement.execute("ALTER TABLE  `rooms` ADD  `is_casino` ENUM('0','1') NOT NULL DEFAULT  '0'");
                    generic.logMessage("Updating Rooms table...");
                } catch (SQLException ignored) {

                }

            } else {
                generic.logMessage("You're updating Lux, no data will be lost.");
                Emulator.getConfig().update("lux.version", version);
                Emulator.getTexts().update("lux.discord.login","%username% (%ip%) has entered the hotel");
            }

            generic.logMessage("Generating texts/config for Lux features..");
            Emulator.getTexts().register("habbo.not.found","User not found...");
            // Mentions
            Emulator.getTexts().register("lux.mentions", "%username% has mentioned you.\r\nClick to follow");
            Emulator.getConfig().register("lux.mentions.enabled", "1");
            Emulator.getConfig().register("lux.mentions.image", "${image.library.url}/notifications/mention.gif");
            generic.logMessage("Generating Mention texts/config..");

            // Welcome Alert registry
            Emulator.getConfig().register("lux.welcomeAlert.enabled", "1");
            Emulator.getConfig().register("lux.welcomeAlert.link", "habbopages/welcome.txt");
            generic.logMessage("Generating Welcome Alert config....");

            // daily gifts
            Emulator.getConfig().register("lux.daily.gifts", "2;3;4;5;6;7;8;9;10");
            Emulator.getConfig().register("lux.daily.credits", "1000");
            Emulator.getConfig().register("lux.daily.duckets", "1000");
            Emulator.getConfig().register("lux.daily.diamonds", "25");
            Emulator.getConfig().register("lux.daily.seasonal.type", "103");
            Emulator.getConfig().register("lux.daily.seasonal", "30");
            Emulator.getConfig().register("lux.daily.enabled", "1");
            Emulator.getTexts().register("lux.daily.gift", "Heres your gift!!");
            generic.logMessage("Generating Daily Gift options..");

            // Bubble Alert Command
            Emulator.getTexts().register("commands.description.cmd_bubblealert", ":bubblealert <type>");
            Emulator.getTexts().register("lux.cmd_bubblealert.keys", "bubblealert;balert;ba");
            Emulator.getTexts().register("lux.cmd_bubblealert.incorrect.usage", "Missing arguments for bubble alert command!");
            Emulator.getTexts().register("lux.cmd_bubblealert.unknown", "Unknown argument..");
            Emulator.getConfig().register("lux.bubblealert.event.image", "${image.library.url}/notifications/event.gif");
            reloadPermissions = registerPermission("cmd_bubblealert", "'0', '1', '2'", "1", reloadPermissions);
            generic.logMessage("Generating Bubble Alert command texts/config/permissions..");

            // Exit Command
            Emulator.getTexts().register("commands.description.cmd_exit", ":exit");
            Emulator.getTexts().register("lux.cmd_exit.keys", "exit;leave");
            reloadPermissions = registerPermission("cmd_exit", "'0', '1', '2'", "1", reloadPermissions);
            generic.logMessage("Generating Exit command texts/permissions...");

            // Tea Command
            Emulator.getTexts().register("commands.description.cmd_tea", ":tea");
            Emulator.getTexts().register("lux.tea", "Throws shade, sips tea...");
            Emulator.getTexts().register("lux.tea.keys", "sips;tea;sipstea");
            reloadPermissions = registerPermission("cmd_tea", "'0', '1', '2'", "1", reloadPermissions);
            generic.logMessage("Generating Tea Command texts/permissions...");

            // Eggify Command
            Emulator.getTexts().register("commands.description.cmd_eggify", ":eggify <user>");
            Emulator.getTexts().register("lux.cmd_eggify.keys", "eggify;egg");
            Emulator.getTexts().register("lux.eggify.self", "* You cannot eggify yourself.. *");
            Emulator.getTexts().register("lux.eggify.action", "* Eggifies %username% *");
            Emulator.getTexts().register("lux.eggify.received", ":(");
            reloadPermissions = registerPermission("cmd_eggify", "'0', '1', '2'", "1", reloadPermissions);
            generic.logMessage("Generating Eggify Command Texts/permissions..");

            // Roll Dice Command
            Emulator.getTexts().register("commands.description.cmd_rolldice", ":rolldice");
            Emulator.getTexts().register("lux.cmd_rolldice.keys", "rd;rolldice;roll");
            Emulator.getTexts().register("lux.rolldice.rolling", "* Rolling %count% Dice *");
            reloadPermissions = registerPermission("cmd_rolldice", "'0', '1', '2'", "1", reloadPermissions);
            generic.logMessage("Generating RollDice Command texts/permissions..");

            // Close Dice Command
            Emulator.getTexts().register("commands.description.cmd_closedice", ":closedice <all>");
            Emulator.getTexts().register("lux.cmd_closedice.keys", "cd;closedice");
            Emulator.getTexts().register("lux.closedice.closed", "* Closed %count% Dice *");
            Emulator.getTexts().register("lux.closedice.all", "All");
            reloadPermissions = registerPermission("cmd_closedice", "'0', '1', '2'", "1", reloadPermissions);
            reloadPermissions = registerPermission("cmd_closedice_room", "'0', '1', '2'", "1", reloadPermissions);
            generic.logMessage("Generating CloseDice Command texts/permissions..");

            // Casino Command
            Emulator.getTexts().register("commands.description.cmd_makecasino", ":casino");
            Emulator.getTexts().register("lux.cmd_makecasino.keys", "casino;makecasino");
            Emulator.getTexts().register("lux.cmd_makecasino.arguments", "Missing arguments");
            Emulator.getTexts().register("lux.casino.alert", "<b>An Official Casino</b>\r\nThis Room is an Official Casino, Here, you can bet, trade and gamble all kinds of currency, play safe and don't scam!\r\n- Hotel Management");
            Emulator.getTexts().register("lux.casino.enabled", "This room is now a casino.");
            Emulator.getTexts().register("lux.casino.disabled", "This room is no longer a casino");
            reloadPermissions = registerPermission("cmd_makecasino", "'0', '1', '2'", "1", reloadPermissions);
            generic.logMessage("Generating Casino Command texts/permissions..");

            // Poof Command
            Emulator.getTexts().register("commands.description.cmd_poof", ":poof");
            Emulator.getTexts().register("lux.cmd_poof.keys","poof;flash");
            Emulator.getTexts().register("lux.poof","* Poofs *");
            reloadPermissions = registerPermission("cmd_poof", "'0', '1', '2'", "1", reloadPermissions);
            generic.logMessage("Generating Poof Command texts/permissions...");

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
            generic.logMessage("Generating Discord logging texts/config..");

            // Yeet Command
            Emulator.getTexts().register("commands.description.cmd_yeet", ":superpush");
            Emulator.getTexts().register("lux.cmd_yeet.keys","superpush;yeet;spush");
            reloadPermissions = registerPermission("cmd_yeet", "'0', '1', '2'", "1", reloadPermissions);
            generic.logMessage("Generating Yeet Command texts/permissions...");

            // Stats Command
            Emulator.getTexts().register("lux.cmd_stats.keys","stats;userstats");
            Emulator.getTexts().register("commands.description.cmd_stats", ":stats");
            Emulator.getTexts().register("lux.rank_name","Rank");
            Emulator.getTexts().register("lux.cmd_stats.of","<b>Stats Of: %username%</b>");
            Emulator.getTexts().register("lux.cmd_stats.currency_info","<b>Currency Info:</b>");
            reloadPermissions = registerPermission("cmd_stats", "'0', '1', '2'", "1", reloadPermissions);
            generic.logMessage("Generating Stats Command texts/permissions..");

            // Dance Command
            Emulator.getTexts().register("lux.cmd_dance.keys","dance");
            Emulator.getTexts().register("commands.description.cmd_dance", ":dance");
            Emulator.getTexts().register("lux.cmd_dance.arguments", "Missing arguments");
            reloadPermissions = registerPermission("cmd_dance", "'0', '1', '2'", "1", reloadPermissions);
            generic.logMessage("Generating Dance Command texts/permissions...");

            // Users Online Command
            Emulator.getTexts().register("commands.description.cmd_online", ":whosonline");
            Emulator.getTexts().register("lux.cmd_online.keys","whosonline;usersonline;users");
            Emulator.getTexts().register("lux.cmd_online.header","Habbo's Online");
            reloadPermissions = registerPermission("cmd_online", "'0', '1', '2'", "1", reloadPermissions);
            generic.logMessage("Generating Users Online Command texts/permissions...");

            //////////////////////////////////////////////////////////////////

            if (reloadPermissions) {
                Emulator.getGameEnvironment().getPermissionsManager().reload();
            }
            generic.logMessage("\u001b[33m -> WARNING -> EMULATOR MAY NEED TO BE RESTARTED AFTER UPDATING/RUNNING LUX FOR THE FIRST TIME.");
        }

        Emulator.getTexts().register("commands.description.cmd_buildheight", ":bh");
        Emulator.getTexts().register("lux.cmd_buildheight.keys","bh");
        Emulator.getTexts().register("lux.cmd_buildheight.changed", "Changed build height to %height%");
        Emulator.getTexts().register("lux.cmd_buildheight.disabled", "Build height removed.");
        Emulator.getTexts().register("lux.cmd_buildheight.not_specified", "No buildheight set. Height must be between 0 - 40.");
        reloadPermissions = registerPermission("cmd_buildheight", "'0', '1', '2'", "1", reloadPermissions);

        Emulator.getTexts().register("commands.description.cmd_welcome", ":welcome <username>");
        Emulator.getTexts().register("lux.cmd_welcome.keys", "welcome;willkommen;welkom;bienvenue;bienvenida;bem-vindo");
        Emulator.getTexts().register("lux.cmd_welcome.text", "Welcome %username% to %hotelname%;Enjoy your stay in %hotelname% %username%!");
        reloadPermissions = registerPermission("cmd_welcome", "'0', '1', '2'", "0", reloadPermissions);

        Emulator.getTexts().register("commands.description.cmd_disable_effects", ":disableffects");
        Emulator.getTexts().register("lux.cmd_disable_effects.keys", "antieffects;disableffects");
        Emulator.getTexts().register("lux.cmd_disable_effects.effects_enabled", "Effects in this room have been enabled!");
        Emulator.getTexts().register("lux.cmd_disable_effects.effects_disabled", "Effects in this room have been disabled!");

        Emulator.getTexts().register("commands.description.cmd_brb", ":brb");
        Emulator.getTexts().register("lux.cmd_brb.keys", "afk;brb;");
        Emulator.getTexts().register("lux.cmd_brb.brb", "* %username% is now AFK! *");
        Emulator.getTexts().register("lux.cmd_brb.back", "* %username% is now back! *");
        Emulator.getTexts().register("lux.cmd_brb.time", "* %username% has now been away for %time% minutes *");
        reloadPermissions = registerPermission("cmd_brb", "'0', '1', '2'", "1", reloadPermissions);

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
