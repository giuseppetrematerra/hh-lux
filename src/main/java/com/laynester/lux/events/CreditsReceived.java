package com.laynester.lux.events;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.outgoing.generic.alerts.BubbleAlertComposer;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.events.users.UserCreditsEvent;
import gnu.trove.map.hash.THashMap;

public class CreditsReceived implements EventListener {

    @EventHandler
    public static void onUserCreditsReceived(UserCreditsEvent event)
    {
        if (event.credits > 0)
        {
            THashMap<String, String> credits_keys = new THashMap<String, String>();
            credits_keys.put("display", "BUBBLE");
            credits_keys.put("image", "${image.library.url}notifications/credits_image.png");
            credits_keys.put("message", Emulator.getTexts().getValue("commands.generic.cmd_credits.received").replace("%amount%", event.credits + ""));
            credits_keys.put("count", event.credits + "");
            event.habbo.getClient().sendResponse(new BubbleAlertComposer("receivedcredits", credits_keys));
        }
    }
}
