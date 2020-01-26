package com.laynester.lux.hhcore;

import com.eu.habbo.Emulator;
import com.laynester.lux.Lux;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.laynester.lux.Lux.pluginName;
import static com.laynester.lux.Lux.productId;

public class checkIntegrity {
    public static boolean checkIntegrity() throws IOException {
       if (Emulator.getConfig().getValue("hh.username") == null || Emulator.getConfig().getValue("hh.email") == null || Emulator.getConfig().getValue("hh.check_update") == null || Emulator.getConfig().getValue("hh.hotel_url") == null) {
           System.out.println ( "\n[!] [HH] Incorrect installation. Please refer to the installation document for more information." );
           return false;
       }

        String url = "https://api.hackerman.tech/public/integrity.php?username=" + Emulator.getConfig().getValue("hh.username") + "&email=" + Emulator.getConfig().getValue("hh.email") + "&plugin=" + productId + "&version=" + Lux.version + "&url=" + Emulator.getConfig().getValue("hh.hotel_url");
        try {
            URL obj = new URL ( url );
            HttpURLConnection con = (HttpURLConnection) obj.openConnection ( );
            con.setRequestMethod ( "GET" );
            con.setRequestProperty ( "User-Agent" , "Arcturus Morningstar" );

            int responseCode = con.getResponseCode ( );
            BufferedReader in = new BufferedReader (
                    new InputStreamReader( con.getInputStream ( ) ) );
            String inputLine;
            StringBuffer response = new StringBuffer ( );

            while ((inputLine = in.readLine ( )) != null) {
                response.append ( inputLine );
            }
            in.close ( );

            // Parse the response as JSON
            JSONObject jsonObject = new JSONObject(response.toString());
            // Get status
            String status = jsonObject.getString("status");

            switch (status) {
                case "ERROR":
                    System.out.println("\n[!] Failed to enable `" + pluginName + "`: " + jsonObject.getString("message"));
                    if (jsonObject.getInt("version") == 1) {
                        return true;
                    } else {
                        return false;
                    }
                case "BLOCK":
                    String type = jsonObject.getString("type");
                    if (type.equalsIgnoreCase("SEC")) { // If type is sec it means a security block
                        System.out.println("\n[!] Plugin `" + pluginName + "` is disabled due to a possible vulnerability!");
                        System.out.println("[!] Optional note from developer: " + jsonObject.getString("message"));
                        System.out.println("[!] Update this plugin as quickly as possible @ Hackerman.tech\n");
                        return false;
                    }
                    if (type.equalsIgnoreCase("OUTDATED")) { // If the plugin is way too outdated we block it too
                        System.out.println("\n[!] Plugin `" + pluginName + "` is disabled because it's outdated.");
                        System.out.println("[!] Update this plugin as quickly as possible @ Hackerman.tech\n");
                        return false;
                    }
                    if (type.equalsIgnoreCase("BAN")) { // Used in extreme cases if the user breached the TOS / Morningstar License - This will NOT be used without REASON!
                        System.out.println("\n[!] Plugin `" + pluginName + "` is disabled due to a breachment of the terms of service.");
                        System.out.println("[!] Optional note: " + jsonObject.getString("message"));
                        System.out.println("[!] Please remove this plugin from your hotel or contact a developer\n");
                        return false;
                    }
                    return false;

                case "OK": // Previous checks are OK! Checking for update
                    String update = jsonObject.getString("update");
                    if (update.equalsIgnoreCase("NEW_VERSION_AVAILABLE")) { // If type is sec it means a security block

                        String changelog = jsonObject.getString("changelog");
                        String strRegEx = "<[^>]*>";
                        changelog = changelog.replaceAll(strRegEx, "");

                        System.out.println("\n[!] Plugin `" + pluginName + "` version " + jsonObject.getString("new_version") + " is now available @ Hackerman.tech");
                        System.out.println("[!] Changelog: " + changelog + "\n");
                    }
                    return true;
                default:
                    System.out.println ( "[~] Failed to check the integrity");
            }
        } catch (Exception e) {
            System.out.println ( "[~] Failed to check integrity: " + e.getMessage());
           return false;
        }
       return false;
    }
}
