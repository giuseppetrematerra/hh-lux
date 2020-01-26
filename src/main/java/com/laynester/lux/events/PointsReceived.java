package com.laynester.lux.events;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.outgoing.generic.alerts.BubbleAlertComposer;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.events.users.UserPointsEvent;
import gnu.trove.map.hash.THashMap;

public class PointsReceived implements EventListener {

    @EventHandler
    public static void onUserPointsReceived(UserPointsEvent event)
    {
        if (event.points > 0 && event.type != 5)
        {
            THashMap<String, String> points_keys = new THashMap<String, String>();
            points_keys.put("display", "BUBBLE");
            points_keys.put("image", "${image.library.url}notifications/points_" + event.type + ".png");
            points_keys.put("message", Emulator.getTexts().getValue("commands.generic.cmd_points.received").replace("%amount%", event.points + "").replace("%type%", Emulator.getTexts().getValue("seasonal.name." + event.type)));
            points_keys.put("count", event.points + "");
            points_keys.put("type", event.type + "");
            event.habbo.getClient().sendResponse(new BubbleAlertComposer("receivedpoints", points_keys));
        }
    }
}
