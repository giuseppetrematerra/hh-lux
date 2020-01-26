package com.laynester.lux.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.games.Game;
import com.eu.habbo.habbohotel.users.Habbo;

public class StatsCommand extends Command {

    public StatsCommand(String permission, String[] keys)
    {
        super(permission, keys);
    }

    @Override
    public boolean handle(GameClient gameClient, String[] strings) throws Exception
    {
        if (strings.length < 2)
        {
            Habbo habbo = gameClient.getHabbo();
            String username = habbo.getHabboInfo().getUsername();
            int coins = habbo.getHabboInfo().getCredits();
            int duckets = habbo.getHabboInfo().getPixels();
            int diamonds = habbo.getHabboInfo().getCurrencyAmount(5);
            int seasonal = habbo.getHabboInfo().getCurrencyAmount(Emulator.getConfig().getInt("lux.daily.seasonal.type"));
            String rank = habbo.getHabboInfo().getRank().getName();

            String message = Emulator.getTexts().getValue("lux.cmd_stats.of").replace("%username%",username) + "\n\n";
            message = message + Emulator.getTexts().getValue("lux.cmd_stats.currency_info") + "\n";
            message = message + Emulator.getTexts().getValue("generic.credits") + ": " + coins + "\n";
            message = message + Emulator.getTexts().getValue("generic.pixels") + ": " + duckets + "\n";
            message = message + Emulator.getTexts().getValue("seasonal.name.105") + ": " + diamonds + "\n";
            message = message + Emulator.getTexts().getValue("seasonal.name." + Emulator.getConfig().getInt("lux.daily.seasonal.type")) + ": " + seasonal + "\n";
            message = message + Emulator.getTexts().getValue("lux.rank_name") + ": " + rank;
            habbo.alert(message);
            return true;
        }
        final Habbo habbo = Emulator.getGameEnvironment().getHabboManager().getHabbo(strings[1]);
        if (habbo != null)
        {
            String username = habbo.getHabboInfo().getUsername();
            int coins = habbo.getHabboInfo().getCredits();
            int duckets = habbo.getHabboInfo().getPixels();
            int diamonds = habbo.getHabboInfo().getCurrencyAmount(5);
            int seasonal = habbo.getHabboInfo().getCurrencyAmount(Emulator.getConfig().getInt("lux.daily.seasonal.type"));
            String rank = habbo.getHabboInfo().getRank().getName();

            String message = Emulator.getTexts().getValue("lux.cmd_stats.of").replace("%username%",username) + "\n";
            message = message + Emulator.getTexts().getValue("lux.cmd_stats.currency_info") + "\n";
            message = message + Emulator.getTexts().getValue("generic.credits") + ": " + coins + "\n";
            message = message + Emulator.getTexts().getValue("generic.pixels") + ": " + duckets + "\n";
            message = message + Emulator.getTexts().getValue("seasonal.name.105") + ": " + diamonds + "\n";
            message = message + Emulator.getTexts().getValue("seasonal.name." + Emulator.getConfig().getInt("lux.daily.seasonal.type")) + ": " + seasonal + "\n";
            message = message + Emulator.getTexts().getValue("lux.rank_name") + ": " + rank;
            gameClient.getHabbo().alert(message);
        } else  {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("habbo.not.found"));
        }
        return true;
    }
}
