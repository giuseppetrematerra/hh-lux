package com.laynester.lux.hhplugin.installer.util;

import com.eu.habbo.Emulator;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Cleaner {
    public static void removeOldVersion() {
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection(); Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM `emulator_settings` WHERE `key`='lux.version'");
            statement.getConnection().close();
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + " ~ " + e.getMessage());
        }
    }
}
