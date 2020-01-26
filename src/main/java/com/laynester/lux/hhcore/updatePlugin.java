package com.laynester.lux.hhcore;

import com.laynester.lux.Lux;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class updatePlugin {
    private static Path download(String sourceURL, String targetDirectory) throws IOException
    {
        System.out.println(sourceURL + " -- " + targetDirectory);
        URL url = new URL(sourceURL);
        String fileName = sourceURL.substring(sourceURL.lastIndexOf('/') + 1, sourceURL.length());
        Path targetPath = new File(targetDirectory + File.separator + fileName).toPath();
        Files.copy(url.openStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        return targetPath;
    }
    public static void updateDirectoryPlugin(String url) throws IOException {
        boolean success = true;

        String current = new File(".").getCanonicalPath();
        Path path = Paths.get(current + "/update");
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                success = false;
                System.out.println("[!] ERROR: Failed to create /update directory (" + Lux.pluginName + ").");
                System.out.println("[!] STACKTRACE: " + e.getMessage());
            }
        }

        if (success) {
            download(url,current + "/update");
        }



    }
}
