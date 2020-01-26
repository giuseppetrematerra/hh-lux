package com.laynester.lux.events;

import com.eu.habbo.Emulator;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.events.support.SupportTicketEvent;
import com.laynester.lux.utils.Discord;

import java.awt.*;
import java.io.IOException;

public class Report implements EventListener {
    @EventHandler
    public static void reported(SupportTicketEvent event) throws IOException {
        if(Emulator.getConfig().getInt("lux.discord.enabled") == 1 && !Emulator.getConfig().getValue("lux.discord.report").equals("")) {
            Discord webhook = new Discord(Emulator.getConfig().getValue("lux.discord.report"));
            webhook.setContent("@here");
            webhook.addEmbed(new Discord.EmbedObject()
                    .setTitle("Lux")
                    .setDescription(Emulator.getTexts().getValue("lux.discord.cfh"))
                    .setColor(Color.RED)
                    .addField(Emulator.getTexts().getValue("lux.discord.reporter"), event.ticket.senderUsername, true)
                    .addField(Emulator.getTexts().getValue("lux.discord.reported"), event.ticket.reportedUsername, true)
                    .addField(Emulator.getTexts().getValue("lux.discord.reason"), event.ticket.message, false));
            webhook.setUsername("Lux");
            webhook.execute();
        }
    }
}
