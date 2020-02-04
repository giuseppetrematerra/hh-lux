package com.laynester.lux.events;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.habbohotel.users.HabboItem;
import com.eu.habbo.messages.outgoing.habboway.nux.NuxAlertComposer;
import com.eu.habbo.messages.outgoing.inventory.InventoryRefreshComposer;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.events.users.UserLoginEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class LoginEvents implements EventListener {

    @EventHandler
    public static void onUserLoginEvent(UserLoginEvent event) throws IOException {

        if(Emulator.getConfig().getInt("lux.welcomeAlert.enabled") == 1) {
            event.habbo.getClient().sendResponse(new NuxAlertComposer(Emulator.getConfig().getValue("lux.welcomeAlert.link")));
        }

        long time = Long.parseLong(String.valueOf(System.currentTimeMillis()/1000));
        int user = event.habbo.getHabboInfo().getId();
        int timestamp = (int) time;

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection())
        {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO `lux_logins` (`user_id`,`timestamp`) VALUES(?,?)"))
            {
                statement.setInt(1, user);
                statement.setInt(2, timestamp);
                statement.execute();
            }
            if(Emulator.getConfig().getInt("lux.daily.enabled") == 1) {
                try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) as rowcount FROM lux_logins WHERE FROM_UNIXTIME(timestamp,'%Y-%m-%d') = CURDATE() AND user_id=? ORDER BY timestamp DESC"))
                {
                    statement.setInt(1,user);
                    ResultSet rs = statement.executeQuery();
                    rs.next();
                    int count = rs.getInt("rowcount");
                    rs.close();
                    if(count == 1) {
                        if(Emulator.getConfig().getInt("lux.daily.credits") > 0) {
                            event.habbo.giveCredits(Emulator.getConfig().getInt("lux.daily.credits"));
                        }
                        if(Emulator.getConfig().getInt("lux.daily.duckets") > 0) {
                            event.habbo.givePixels(Emulator.getConfig().getInt("lux.daily.duckets"));
                        }
                        if(Emulator.getConfig().getInt("lux.daily.diamonds") > 0) {
                            event.habbo.givePoints(5,Emulator.getConfig().getInt("lux.daily.diamonds"));
                        }
                        if(Emulator.getConfig().getInt("lux.daily.seasonal") > 0) {
                            event.habbo.givePoints(Emulator.getConfig().getInt("lux.daily.seasonal.type"),Emulator.getConfig().getInt("lux.daily.seasonal"));
                        }
                        if(Emulator.getConfig().getValue("lux.daily.gifts") != "") {

                            Habbo habbo = event.habbo;

                            String[] giftID = Emulator.getConfig().getValue("lux.daily.gifts").split(";");

                            String username = event.habbo.getHabboInfo().getUsername();
                            Random r= new Random();
                            int randomNumber = r.nextInt(giftID.length);
                            Item baseItem = Emulator.getGameEnvironment().getItemManager().getItem(randomNumber);

                            HabboItem item = Emulator.getGameEnvironment().getItemManager().createItem(0, baseItem, 0, 0, "");

                            Item giftItem = Emulator.getGameEnvironment().getItemManager().getItem((Integer)Emulator.getGameEnvironment().getCatalogManager().giftFurnis.values().toArray()[Emulator.getRandom().nextInt(Emulator.getGameEnvironment().getCatalogManager().giftFurnis.size())]);

                            String extraData = "1\t" + item.getId();
                            extraData = extraData + "\t0\t0\t0\t"+Emulator.getTexts().getValue("lux.daily.gift")+"\t0\t0";
                            Emulator.getGameEnvironment().getItemManager().createGift(username, giftItem, extraData, 0, 0);
                            habbo.getClient().sendResponse(new InventoryRefreshComposer());
                        }
                    }
                }
            }

        } catch (SQLException ignored)
        {}
    }

}
