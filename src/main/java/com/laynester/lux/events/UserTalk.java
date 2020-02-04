package com.laynester.lux.events;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.outgoing.generic.alerts.BubbleAlertComposer;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.events.users.UserTalkEvent;
import com.laynester.lux.recharge.rechargeMain;
import gnu.trove.map.hash.THashMap;

import java.io.IOException;

import static com.laynester.lux.recharge.rechargeMain.setRechargeNow;

public class UserTalk implements EventListener {

    @EventHandler
    public static void onUserTalkEvent(UserTalkEvent event) throws IOException {
        if (Emulator.getConfig().getInt("lux.mentions.enabled") == 1 && event.chatMessage.getMessage().startsWith("@"))
        {
            Habbo habbo = Emulator.getGameEnvironment().getHabboManager().getHabbo(event.chatMessage.getMessage().substring(1));
            if (habbo != null && !habbo.getHabboInfo().getUsername().equalsIgnoreCase(event.habbo.getHabboInfo().getUsername()))

                if (!rechargeMain.canUseNow(event.habbo, "mention")) {
                    event.habbo.whisper(Emulator.getTexts().getValue("lux.error.wait"), RoomChatMessageBubbles.ALERT);
                    return;
                }
            setRechargeNow(event.habbo, "mention", 5000);

            {
                THashMap<String, String> notify_keys = new THashMap<String, String>();
                notify_keys.put("display", "BUBBLE");
                notify_keys.put("image", Emulator.getConfig().getValue("lux.mentions.image"));
                notify_keys.put("linkUrl", "event:navigator/goto/" + event.habbo.getHabboInfo().getCurrentRoom().getId() + "");
                notify_keys.put("message",
                        Emulator.getTexts().getValue("lux.mentions")
                                .replace("%username%", event.habbo.getHabboInfo().getUsername()));
                habbo.getClient().sendResponse(new BubbleAlertComposer("mentioned", notify_keys));
            }
        }
    }
}
