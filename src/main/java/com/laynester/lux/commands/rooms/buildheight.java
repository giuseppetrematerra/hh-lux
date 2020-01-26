package com.laynester.lux.commands.rooms;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.events.furniture.FurnitureMovedEvent;
import com.eu.habbo.plugin.events.furniture.FurniturePlacedEvent;
import com.eu.habbo.plugin.events.users.UserExitRoomEvent;
import com.laynester.lux.Lux;

import java.util.EventListener;

public class buildheight extends Command implements EventListener
{
    public static String BUILD_HEIGHT_KEY = "lux.build_height";
    public buildheight(String permission, String[] keys)
    {
        super(permission, keys);

        // Emulator.getPluginManager().registerEvents(Lux.INSTANCE, (com.eu.habbo.plugin.EventListener) this);
    }

    @Override
    public boolean handle(GameClient gameClient, String[] strings) throws Exception
    {
        if (strings.length == 2)
        {
            Double height = -1.0;

            try
            {
                height = Double.valueOf(strings[1]);
            }
            catch (Exception e)
            {
            }

            if(height > 40 || height < 0) {
                gameClient.getHabbo().whisper(Emulator.getTexts().getValue("lux.cmd_buildheight.invalid_height"));
                return true;
            }

            gameClient.getHabbo().getHabboStats().cache.put(BUILD_HEIGHT_KEY, height);
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("lux.cmd_buildheight.changed").replace("%height%", height + ""));
        }
        else
        {
            if(gameClient.getHabbo().getHabboStats().cache.containsKey(BUILD_HEIGHT_KEY))
            {
                gameClient.getHabbo().getHabboStats().cache.remove(BUILD_HEIGHT_KEY);
                gameClient.getHabbo().whisper(Emulator.getTexts().getValue("lux.cmd_buildheight.disabled"));
                return true;
            }
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("lux.cmd_buildheight.not_specified"));
        }
        return true;
    }

}
