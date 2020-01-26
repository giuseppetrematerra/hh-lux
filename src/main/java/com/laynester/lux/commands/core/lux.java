package com.laynester.lux.commands.core;

import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.laynester.lux.Lux;

public class lux extends Command
{
    public lux()
    {
        super(null, new String[] { "lux" });
    }

    @Override
    public boolean handle(GameClient gameClient, String[] strings) throws Exception
    {
        String message = "<b>Lux</b> version " + Lux.version + "\n";
        message = message + "Lux is an Arcturus Morningstar plugin created by Laynester\r\n";
        message = message + "<b>Features:</b>\n";
        message = message + "- Welcome Alert\n";
        message = message + "- Daily Gifts\n";
        message = message + "- Mentions\n";
        message = message + "- Bubble Alerts (currency)\n";
        message = message + "- Bubble Alert Command\n";
        message = message + "- Close Dice Command\n";
        message = message + "- Roll Dice Command\n";
        message = message + "- Casino Command\n";
        message = message + "- Discord Logging\n";
        message = message + "\r\nMore plugins can be found on <b>Hackerman.tech</b>";


        gameClient.getHabbo().alert(message);
        return true;

    }
}