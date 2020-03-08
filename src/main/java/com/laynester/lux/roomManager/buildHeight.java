package com.laynester.lux.roomManager;

import com.eu.habbo.Emulator;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.events.furniture.FurnitureMovedEvent;
import com.eu.habbo.plugin.events.furniture.FurniturePlacedEvent;
import com.eu.habbo.plugin.events.users.UserExitRoomEvent;

import static com.laynester.lux.commands.rooms.BuildHeightCommand.BUILD_HEIGHT_KEY;

public class buildHeight implements EventListener {
    @EventHandler
    public static void onUserExitRoomEvent(UserExitRoomEvent event)
    {
        event.habbo.getHabboStats().cache.remove(BUILD_HEIGHT_KEY);
    }


    @EventHandler
    public static void onFurniturePlaced(final FurniturePlacedEvent event)
    {
        if (event.location != null)
        {
            if (event.habbo.getHabboStats().cache.containsKey(BUILD_HEIGHT_KEY))
            {
                Emulator.getThreading().run(new Runnable() {
                    public void run() {
                        event.furniture.setZ((Double) event.habbo.getHabboStats().cache.get(BUILD_HEIGHT_KEY));
                        event.furniture.needsUpdate(true);
                        event.habbo.getHabboInfo().getCurrentRoom().updateItem(event.furniture);
                    }
                }, 25);
            }
        }
    }

    @EventHandler
    public static void onFurnitureMoved(final FurnitureMovedEvent event)
    {
        if (event.newPosition != null)
        {
            if (event.habbo.getHabboStats().cache.containsKey(BUILD_HEIGHT_KEY))
            {
                Emulator.getThreading().run(new Runnable() {
                    public void run() {
                        event.furniture.setZ((Double) event.habbo.getHabboStats().cache.get(BUILD_HEIGHT_KEY));
                        event.furniture.needsUpdate(true);
                        event.habbo.getHabboInfo().getCurrentRoom().updateItem(event.furniture);
                    }
                }, 25);
            }
        }
    }
}
