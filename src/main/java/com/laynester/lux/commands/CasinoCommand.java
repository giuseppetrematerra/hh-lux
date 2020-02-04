package com.laynester.lux.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.users.Habbo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CasinoCommand extends Command {

    public CasinoCommand(String permission, String[] keys)
    {
        super(permission, keys);
    }

    @Override
    public boolean handle(GameClient gameClient, String[] strings) throws Exception
    {
        Habbo habbo = gameClient.getHabbo();
        Room room = gameClient.getHabbo().getHabboInfo().getCurrentRoom();
        int roomId = room.getId();
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) as rowcount FROM rooms WHERE id=? AND is_casino = '1'")) {
                statement.setInt(1, roomId);
                ResultSet rs = statement.executeQuery();
                rs.next();
                int count = rs.getInt("rowcount");
                rs.close();
                if(count == 1) {
                    gameClient.getHabbo().whisper(Emulator.getTexts().getValue("lux.casino.disabled"), RoomChatMessageBubbles.ALERT);
                    try (PreparedStatement statementt = connection.prepareStatement("UPDATE rooms SET is_casino = '0' WHERE id = '"+roomId+"'")) {
                        statementt.execute();
                    }
                } else {
                    gameClient.getHabbo().whisper(Emulator.getTexts().getValue("lux.casino.enabled"), RoomChatMessageBubbles.ALERT);
                    try (PreparedStatement statementt = connection.prepareStatement("UPDATE rooms SET is_casino = '1' WHERE id = '"+roomId+"'")) {
                        statementt.execute();
                    }
                }
            }

        } catch(SQLException e) {}
        return true;
    }
}
