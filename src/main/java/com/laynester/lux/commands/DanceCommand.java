package com.laynester.lux.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.users.DanceType;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.outgoing.rooms.users.RoomUserDanceComposer;

public class DanceCommand extends Command {

    public DanceCommand(String permission, String[] keys)
    {
        super(permission, keys);
    }

    @Override
    public boolean handle(GameClient gameClient, String[] strings) throws Exception {

        Habbo habbo = gameClient.getHabbo();

        if (strings.length == 2) {

            int danceId;

            try {
                danceId = Integer.valueOf(strings[1]);
            } catch (Exception e) {
                gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.error.cmd_danceall.invalid_dance"), RoomChatMessageBubbles.ALERT);
                return true;
            }

            if (danceId < 0 || danceId > 4) {
                gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.error.cmd_danceall.outside_bounds"), RoomChatMessageBubbles.ALERT);
                return true;
            }

            habbo.getRoomUnit().setDanceType(DanceType.values()[danceId]);
            habbo.getHabboInfo().getCurrentRoom().sendComposer(new RoomUserDanceComposer(habbo.getRoomUnit()).compose());

        } else {
            int danceId = 1;
            habbo.getRoomUnit().setDanceType(DanceType.values()[danceId]);
            habbo.getHabboInfo().getCurrentRoom().sendComposer(new RoomUserDanceComposer(habbo.getRoomUnit()).compose());
        }
        return true;
    }
}
