package com.laynester.lux.commands.rooms;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.items.interactions.InteractionDice;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.rooms.RoomTile;
import com.eu.habbo.habbohotel.users.HabboItem;
import com.eu.habbo.threading.runnables.RandomDiceNumber;

import java.util.concurrent.TimeUnit;

public class RollDiceCommand extends Command {
    public RollDiceCommand(String permission, String[] keys)
    {
        super(permission, keys);
    }

    @Override
    public boolean handle(GameClient gameClient, String[] strings) throws Exception
    {
        Room room = gameClient.getHabbo().getHabboInfo().getCurrentRoom();

        if (room != null)
        {
            int count = 0;
            if (strings.length == 1)
            {
                for (RoomTile tile : gameClient.getHabbo().getHabboInfo().getCurrentRoom().getLayout().getTilesAround(gameClient.getHabbo().getRoomUnit().getCurrentLocation()))
                {
                    for (HabboItem item : gameClient.getHabbo().getHabboInfo().getCurrentRoom().getItemsAt(tile))
                    {
                        if (item instanceof InteractionDice && !item.getExtradata().equals("-1"))
                        {
                            rollDice(item, room);
                            ++count;
                        }
                    }
                }

                if (count > 0)
                {
                    gameClient.getHabbo().shout(Emulator.getTexts().getValue("lux.rolldice.rolling").replace("%count%", count + ""), RoomChatMessageBubbles.ALERT);
                }
            }
        }

        return true;
    }

    private void rollDice(HabboItem item, Room room)
    {
        item.setExtradata("-1");
        room.updateItemState(item);
        Emulator.getThreading().run(item);
        if (Integer.parseInt(item.getExtradata()) >= 0) {
            Emulator.getThreading().run(new RandomDiceNumber(room, item, Integer.parseInt(item.getExtradata())), 1500L);
        } else {
            Emulator.getThreading().run(new RandomDiceNumber(item, room, item.getBaseItem().getStateCount()), 1500L);
        }
        item.needsUpdate(true);
        room.updateItem(item);
    }
}
