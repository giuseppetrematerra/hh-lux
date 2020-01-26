package com.laynester.lux.events;

import com.eu.habbo.Emulator;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.events.users.UserCommandEvent;
import com.laynester.lux.utils.Discord;

import java.io.IOException;

public class UserCommand implements EventListener {
    @EventHandler
    public static void onUserCommand(UserCommandEvent event) throws IOException {
        if(Emulator.getConfig().getInt("lux.discord.enabled") == 1 && !Emulator.getConfig().getValue("lux.discord.commands").equals("")) {
            Discord webhook = new Discord(Emulator.getConfig().getValue("lux.discord.commands"));
            String command = ":";
            for (String s : event.args) {
                command += s + " ";
            }
            String message = Emulator.getTexts().getValue("lux.discord.command_usage")
                    .replace("%username%", event.habbo.getHabboInfo().getUsername())
                    .replace("%message%", command);
            webhook.addEmbed(new Discord.EmbedObject().setDescription(message));
            webhook.setUsername("Lux");
            webhook.execute();
        }
    }
}
