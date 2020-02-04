package com.laynester.lux.roomManager;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.incoming.rooms.RoomPlacePaintEvent;
import com.eu.habbo.messages.outgoing.generic.alerts.BubbleAlertComposer;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.events.furniture.*;
import gnu.trove.map.hash.THashMap;

public class frozenRoom implements EventListener {
    public static void enterFrozenRoom(Habbo habbo) {
        habbo.getHabboStats().cache.put("isRoomFrozen", "yes");
        THashMap<String, String> keys = new THashMap<>();
        keys.put("display", "BUBBLE");
        keys.put("image", "${image.library.url}notifications/furni_placement_error.png");
        keys.put("message", Emulator.getTexts().getValue("lux.cmd_freezeroom.entered_frozen_room"));
        habbo.getClient().sendResponse(new BubbleAlertComposer("roomfrozenenter", keys));
    }
    public static void exitFrozenRoom(Habbo habbo) {
        habbo.getHabboStats().cache.remove("isRoomFrozen");
    }

    public static void frozenAlert(Habbo habbo) {
        THashMap<String, String> keys = new THashMap<>();
        keys.put("display", "BUBBLE");
        keys.put("image", "${image.library.url}notifications/furni_placement_error.png");
        keys.put("message", Emulator.getTexts().getValue("lux.cmd_freezeroom.error_frozen"));
        habbo.getClient().sendResponse(new BubbleAlertComposer("roomfrozen", keys));
    }

    @EventHandler
    public static void frozenRoomFurniMove(final FurnitureMovedEvent event)
    {
        if (event.habbo.getHabboStats().cache.get("isRoomFrozen") != null) {
            event.setCancelled(true);
            frozenAlert(event.habbo);
        }
    }

    @EventHandler
    public static void frozenRoomFurniPickup(final FurniturePickedUpEvent event)
    {
        if (event.habbo.getHabboStats().cache.get("isRoomFrozen") != null) {
            event.setCancelled(true);
            frozenAlert(event.habbo);
        }
    }

    @EventHandler
    public static void frozenRoomFurniPlace(final FurniturePlacedEvent event)
    {
        if (event.habbo.getHabboStats().cache.get("isRoomFrozen") != null) {
            event.setCancelled(true);
            frozenAlert(event.habbo);
        }
    }
    @EventHandler
    public static void frozenRoomFurniRedeem(final FurnitureRedeemedEvent event)
    {
        if (event.habbo.getHabboStats().cache.get("isRoomFrozen") != null) {
            event.setCancelled(true);
            frozenAlert(event.habbo);
        }
    }
    @EventHandler
    public static void frozenRoomFurniRotateEvent (final FurnitureRotatedEvent event)
    {
        if (event.habbo.getHabboStats().cache.get("isRoomFrozen") != null) {
            event.setCancelled(true);
            frozenAlert(event.habbo);
        }
    }

    @EventHandler
    public static void frozenRoomFurniRollEvent (final FurnitureUserEvent event)
    {
        if (event.habbo.getHabboStats().cache.get("isRoomFrozen") != null) {
            event.setCancelled(true);
            frozenAlert(event.habbo);
        }
    }

    @EventHandler
    public static void frozenRoomDeleteRoom (final RoomPlacePaintEvent event)
    {
        if (event.client.getHabbo().getHabboStats().cache.get("isRoomFrozen") != null) {
            event.isCancelled = true;
            frozenAlert(event.client.getHabbo());
        }
    }
    @EventHandler
    public static void frozenRoomDeleteRoom (final FurnitureRoomTonerEvent event)
    {
        if (event.habbo.getHabboStats().cache.get("isRoomFrozen") != null) {
            event.setCancelled(true);
            frozenAlert(event.habbo);
        }
    }
}
