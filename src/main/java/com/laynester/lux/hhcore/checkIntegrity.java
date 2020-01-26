package com.laynester.lux.hhcore;

import com.eu.habbo.Emulator;
import com.laynester.lux.Lux;
import com.laynester.lux.hhcore.log.generic;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.laynester.lux.Lux.*;

// This class checks for plugin information directly on the Hackerman servers
// The only data we store is the IP, your forum ID and the timestamp the request was made

public class checkIntegrity {
    public static boolean checkIntegrity() throws IOException {
        String latestVersion = "Not Checked";

        // Do not check for updates
        if (Emulator.getConfig().getValue(" ").equalsIgnoreCase("false")) {
            generic.logMessage("IMPORTANT: You have checking for updates turned OFF! We are not responsible if you do not update " + pluginName);
            generic.logMessage("Check daily the Hackerman.tech website and check for updates to ensure that you are up-to-date!");
            latestVersion = "Disabled by user";
            return true;
        }

        // Check if the values are setup correctly
       if (Emulator.getConfig().getValue("hh.username") == null || Emulator.getConfig().getValue("hh.email") == null || Emulator.getConfig().getValue("hh.check_update") == null || Emulator.getConfig().getValue("hh.hotel_url") == null) {
           generic.logMessage("\nIncorrect installation. Please refer to the installation document for more information." );
           return false;
       }

       generic.logMessage("Checking for an update for " + pluginName + "...");
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
                    return jsonObject.getInt("version") == 1;
                case "BLOCK":
                    String type = jsonObject.getString("type");
                    // This is only used when a plugin contains a vulnerability that can really fuck over hotels.
                    if (type.equalsIgnoreCase("SEC")) {
                        System.out.println("\n[!] Plugin `" + pluginName + "` is disabled due to a possible vulnerability!");
                        System.out.println("[!] Optional note from developer: " + jsonObject.getString("message"));
                        System.out.println("[!] Update this plugin as quickly as possible @ Hackerman.tech\n");
                        return false;
                    }
                    // If the plugin used is way too outdated we block it if we believe it may contain vulnerabilities
                    if (type.equalsIgnoreCase("OUTDATED")) {
                        System.out.println("\n[!] Plugin `" + pluginName + "` is disabled because it's outdated.");
                        System.out.println("[!] Update this plugin as quickly as possible @ Hackerman.tech\n");
                        return false;
                    }
                    return false;

                case "OK": // Previous checks are OK! Checking for update
                    String update = jsonObject.getString("update");
                    if (update.equalsIgnoreCase("NEW_VERSION_AVAILABLE")) {

                        String changelog = jsonObject.getString("changelog");
                        String strRegEx = "<[^>]*>";
                        changelog = changelog.replaceAll(strRegEx, "");

                        latestVersion = jsonObject.getString("new_version");

                        System.out.println("\n[!] Plugin `" + pluginName + "` version " + jsonObject.getString("new_version") + " is now available @ Hackerman.tech");
                        System.out.println("[!] Changelog: " + changelog + "\n");
                    }
                    latestVersion = version;
                    return true;
                default:
                    generic.logMessage("Failed to check for an update. Update API returned unknown status.");
                    return true;
            }
        } catch (Exception e) {
            generic.logMessage("Failed to check for an update. Error: " + e.getMessage());
           return true;
        }
    }
}
