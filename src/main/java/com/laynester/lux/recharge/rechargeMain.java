package com.laynester.lux.recharge;

import com.eu.habbo.habbohotel.users.Habbo;

public class rechargeMain {

    public static void setRechargeNow(Habbo habbo, String command, int length) {
        habbo.getHabboStats().cache.put("lux_cooldown_" + command, System.currentTimeMillis() + length);
    }

    public static boolean canUseNow(Habbo habbo, String command) {
        if (habbo.getHabboStats().cache.get("lux_cooldown_" + command) == null) {
            return true;
        }

        long cooldownTime = (long) habbo.getHabboStats().cache.get("lux_cooldown_" + command);
        if (cooldownTime - System.currentTimeMillis() >= 0) {
            return false;
        }
        return true;

    }
}
