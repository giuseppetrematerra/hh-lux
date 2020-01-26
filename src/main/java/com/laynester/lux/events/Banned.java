package com.laynester.lux.events;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.habbohotel.users.HabboInfo;
import com.eu.habbo.habbohotel.users.HabboManager;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.events.support.SupportUserBannedEvent;
import com.laynester.lux.utils.Discord;

import java.awt.*;
import java.io.IOException;

public class Banned implements EventListener {
    @EventHandler
    public static void reported(SupportUserBannedEvent event) throws IOException {
        if(Emulator.getConfig().getInt("lux.discord.enabled") == 1 && !Emulator.getConfig().getValue("lux.discord.bans").equals("")) {

            Discord webhook = new Discord(Emulator.getConfig().getValue("lux.discord.bans"));

            Habbo staff = Emulator.getGameEnvironment().getHabboManager().getHabbo(event.ban.staffId);
            HabboInfo target = HabboManager.getOfflineHabboInfo(event.ban.userId);
            webhook.addEmbed(new Discord.EmbedObject()
                    .setTitle("Lux")
                    .setDescription(Emulator.getTexts().getValue("lux.discord.banned").replace("%username%", target.getUsername()))
                    .setColor(Color.RED)
                    .addField(Emulator.getTexts().getValue("lux.discord.banInfo.user"), target.getUsername(), false)
                    .addField(Emulator.getTexts().getValue("lux.discord.banInfo.type"), event.ban.type.getType(), false)
                    .addField(Emulator.getTexts().getValue("lux.discord.banInfo.staff"), (staff != null ? staff.getHabboInfo().getUsername() : " UNKNOWN"), false)
                    .addField(Emulator.getTexts().getValue("lux.discord.banInfo.duration"), String.valueOf(((event.ban.expireDate - Emulator.getIntUnixTimestamp()) / 86400)) + "Days", false)
                    .addField(Emulator.getTexts().getValue("lux.discord.banInfo.reason"), event.ban.reason, false)
            );
            webhook.setUsername("Lux");
            webhook.execute();
        }
    }
}
