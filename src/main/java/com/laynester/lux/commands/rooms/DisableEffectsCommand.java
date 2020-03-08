package com.laynester.lux.commands.rooms;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.users.Habbo;
import gnu.trove.procedure.TObjectProcedure;

public class DisableEffectsCommand extends Command
{
    public DisableEffectsCommand(String permission, String[] keys)
    {
        super(permission, keys);
    }

    @Override
    public boolean handle(GameClient gameClient, String[] strings) throws Exception
    {
        final Room room = gameClient.getHabbo().getHabboInfo().getCurrentRoom();
        if (room != null && room.hasRights(gameClient.getHabbo()))
        {
            room.setAllowEffects(!room.isAllowEffects());

            final String message = Emulator.getTexts().getValue(room.isAllowEffects() ? "lux.cmd_disable_effects.effects_enabled" : "lux.cmd_disable_effects.effects_disabled");
            gameClient.getHabbo().whisper(message);
            room.getUsersWithRights().forEachValue(new TObjectProcedure<String>()
            {
                @Override
                public boolean execute(String object)
                {
                    Habbo habbo = room.getHabbo(object);

                    if (habbo != null)
                    {
                        habbo.whisper(message);
                    }
                    return true;
                }
            });

            return true;
        }

        return false;
    }
}
