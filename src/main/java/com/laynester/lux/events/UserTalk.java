package com.laynester.lux.events;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.outgoing.generic.alerts.BubbleAlertComposer;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.events.users.UserTalkEvent;
import com.laynester.lux.utils.Discord;
import gnu.trove.map.hash.THashMap;

import java.io.IOException;

public class UserTalk implements EventListener {

    @EventHandler
    public static void onUserTalkEvent(UserTalkEvent event) throws IOException {
        if(Emulator.getConfig().getInt("lux.discord.enabled") == 1 && !Emulator.getConfig().getValue("lux.discord.chat").equals("")) {
            Discord webhook = new Discord(Emulator.getConfig().getValue("lux.discord.chat"));
            String message = event.chatMessage.getMessage().replace("\n","").replace("\r","\n");
            webhook.setContent(message);
            webhook.setAvatarUrl("https://habbo.com.br/habbo-imaging/avatarimage?figure=" + event.habbo.getHabboInfo().getLook() + "&headonly=1&head_direction=3");
            webhook.setUsername(event.habbo.getHabboInfo().getUsername() + " Room: "+event.habbo.getHabboInfo().getCurrentRoom().getName());
            webhook.execute();
        }
        if (Emulator.getConfig().getInt("lux.mentions.enabled") == 1 && event.chatMessage.getMessage().startsWith("@"))
        {
            Habbo habbo = Emulator.getGameEnvironment().getHabboManager().getHabbo(event.chatMessage.getMessage().substring(1));
            if (habbo != null && habbo.getHabboInfo().getUsername() != event.habbo.getHabboInfo().getUsername())
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
