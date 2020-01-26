package com.laynester.lux.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.CommandHandler;
import com.laynester.lux.commands.core.lux;
import com.laynester.lux.commands.rooms.CloseDiceCommand;
import com.laynester.lux.commands.rooms.RollDiceCommand;
import com.laynester.lux.commands.rooms.buildheight;
import com.laynester.lux.commands.rooms.disableeffects;

public class CommandManager  {

    public CommandManager() { }

    public void load() throws Exception  {
        long startTime = System.currentTimeMillis();

        CommandHandler.addCommand(new BubbleAlertCommand("cmd_bubblealert", Emulator.getTexts().getValue("lux.cmd_bubblealert.keys").split(";")));
        CommandHandler.addCommand(new RollDiceCommand("cmd_rolldice", Emulator.getTexts().getValue("lux.cmd_rolldice.keys").split(";")));
        CommandHandler.addCommand(new CloseDiceCommand("cmd_closedice", Emulator.getTexts().getValue("lux.cmd_closedice.keys").split(";")));
        CommandHandler.addCommand(new CasinoCommand("cmd_makecasino", Emulator.getTexts().getValue("lux.cmd_makecasino.keys").split(";")));
        CommandHandler.addCommand(new SuperPushCommand("cmd_yeet",Emulator.getTexts().getValue("lux.cmd_yeet.keys").split(";")));
        CommandHandler.addCommand(new StatsCommand("cmd_stats",Emulator.getTexts().getValue("lux.cmd_stats.keys").split(";")));
        CommandHandler.addCommand(new DanceCommand("cmd_dance",Emulator.getTexts().getValue("lux.cmd_dance.keys").split(";")));
        CommandHandler.addCommand(new UsersOnlineCommand("cmd_online",Emulator.getTexts().getValue("lux.cmd_online.keys").split(";")));
        CommandHandler.addCommand(new brb("cmd_brb",Emulator.getTexts().getValue("lux.cmd_brb.keys").split(";")));
        CommandHandler.addCommand(new disableeffects("cmd_disableeffects",Emulator.getTexts().getValue("lux.cmd_disableeffects.keys").split(";")));
        CommandHandler.addCommand(new buildheight("cmd_buildheight",Emulator.getTexts().getValue("lux.cmd_buildheight.keys").split(";")));
        CommandHandler.addCommand(new welcome("cmd_welcome",Emulator.getTexts().getValue("lux.cmd_welcome.keys").split(";")));
        CommandHandler.addCommand(new pay("cmd_pay",Emulator.getTexts().getValue("lux.cmd_pay.keys").split(";")));
        CommandHandler.addCommand(new PullCommand());
        CommandHandler.addCommand(new lux());
        CommandHandler.addCommand(new SuperPullCommand());
        CommandHandler.addCommand(new PushCommand());
    }
}
