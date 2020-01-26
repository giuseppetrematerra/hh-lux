package com.laynester.lux.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.outgoing.generic.alerts.MessagesForYouComposer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UsersOnlineCommand extends Command {

    public UsersOnlineCommand(String permission, String[] keys)
    {
        super(permission, keys);
    }

    @Override
    public boolean handle(GameClient gameClient, String[] strings) throws Exception {
        StringBuilder messages = new StringBuilder(Emulator.getTexts().getValue("lux.cmd_online.header"));
        messages.append(" ( "+Emulator.getGameEnvironment().getHabboManager().getOnlineCount()+" )");
        messages.append("\r------------------------------------------------------------------------------\r");
        for (Map.Entry<Integer, Habbo> map : Emulator.getGameEnvironment().getHabboManager().getOnlineHabbos().entrySet()) {
            Habbo habbo = map.getValue();
            messages.append(habbo.getHabboInfo().getUsername() + "\r");
        }
        gameClient.getHabbo().alert(new String[]{messages.toString()});
        return true;
    }
}
