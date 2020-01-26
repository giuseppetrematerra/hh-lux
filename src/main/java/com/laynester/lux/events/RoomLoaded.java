package com.laynester.lux.events;

import com.eu.habbo.Emulator;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.events.users.UserEnterRoomEvent;
import com.laynester.lux.utils.Discord;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomLoaded implements EventListener {

    @EventHandler
    public static void onRoomLoaded(UserEnterRoomEvent event) throws IOException {
        if(Emulator.getConfig().getInt("lux.discord.enabled") == 1 && !Emulator.getConfig().getValue("lux.discord.roomEntry").equals("")) {
            Discord webhook = new Discord(Emulator.getConfig().getValue("lux.discord.roomEntry"));
            webhook.addEmbed(new Discord.EmbedObject()
                    .setDescription(Emulator.getTexts().getValue("lux.discord.room_entry")
                            .replace("%username%", event.habbo.getHabboInfo().getUsername())
                            .replace("%room_name%", event.room.getName())));
            webhook.setUsername("Lux");
            webhook.execute();
        }
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) as rowcount FROM rooms WHERE id=? AND is_casino = '1'")) {
                statement.setInt(1, event.room.getId());
                ResultSet rs = statement.executeQuery();
                rs.next();
                int count = rs.getInt("rowcount");
                rs.close();
                if(count > 0) {
                    event.habbo.alert(Emulator.getTexts().getValue("lux.casino.alert"));
                }
            }
        } catch(SQLException e) {}
    }
}
