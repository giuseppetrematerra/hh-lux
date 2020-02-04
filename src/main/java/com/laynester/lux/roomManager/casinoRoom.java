package com.laynester.lux.roomManager;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;

public class casinoRoom {
    public static void enterCasinoRoom(Habbo habbo) {
        habbo.alert(Emulator.getTexts().getValue("lux.casino.alert"));
        habbo.getHabboStats().cache.put("isRoomCasino", "yes");
    }
    public static void exitCasinoRoom(Habbo habbo) {
        habbo.getHabboStats().cache.remove("isRoomCasino");
    }
}

