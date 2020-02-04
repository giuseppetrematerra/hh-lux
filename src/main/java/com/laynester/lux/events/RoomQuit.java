package com.laynester.lux.events;

import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.events.users.UserExitRoomEvent;
import com.laynester.lux.roomManager.casinoRoom;
import com.laynester.lux.roomManager.frozenRoom;

import java.io.IOException;

public class RoomQuit implements EventListener {

    @EventHandler
    public static void onRoomQuit(UserExitRoomEvent event) throws IOException {
        frozenRoom.exitFrozenRoom(event.habbo);
        casinoRoom.exitCasinoRoom(event.habbo);
    }
}
