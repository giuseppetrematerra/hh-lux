package com.laynester.lux.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.outgoing.generic.alerts.BubbleAlertComposer;
import gnu.trove.map.hash.THashMap;

import java.util.Collection;
import java.util.Map;

public class BubbleAlertCommand extends Command {

    public BubbleAlertCommand(String permission, String[] keys)
    {
        super(permission, keys);
    }

    @Override
    public boolean handle(GameClient gameClient, String[] strings) throws Exception
    {

        if (strings.length < 2)
        {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("lux.cmd_bubblealert.incorrect.usage"));
            return true;
        }

        String targetUsername = strings[1];
        StringBuilder message = new StringBuilder();

        for (int i = 2; i < strings.length; i++) {
            message.append(strings[i]).append(" ");
        }

        String option = strings[1];
        if(option == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("lux.cmd_bubblealert.incorrect.usage"));
            return true;
        } else {
            switch(option) {
                case "event":
                    if(strings.length < 3) {
                        gameClient.getHabbo().whisper(Emulator.getTexts().getValue("lux.cmd_bubblealert.incorrect.usage"));
                        return true;
                    } else {
                        THashMap<String, String> events = new THashMap<String, String>();
                        events.put("display", "BUBBLE");
                        events.put("image", Emulator.getConfig().getValue("lux.bubblealert.event.image"));
                        events.put("message",message + "\r\n-"+ gameClient.getHabbo().getHabboInfo().getUsername());
                        events.put("linkUrl", "event:navigator/goto/" + gameClient.getHabbo().getHabboInfo().getCurrentRoom().getId() + "");

                        for (Map.Entry<Integer, Habbo> map : Emulator.getGameEnvironment().getHabboManager().getOnlineHabbos().entrySet()) {
                            Habbo habbo = map.getValue();
                            habbo.getClient().sendResponse(new BubbleAlertComposer("event",events));
                        }
                    }
                    break;
                case "thing":
                    break;
                default:
                    gameClient.getHabbo().whisper(Emulator.getTexts().getValue("lux.cmd_bubblealert.unknown"));
                    break;
            }
            return true;
        }
    }
}
