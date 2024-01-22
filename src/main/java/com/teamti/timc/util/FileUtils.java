package com.teamti.timc.util;

import net.minecraft.client.Minecraft;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class FileUtils {
    private FileUtils() {
    }

    public static List<String> read(File inputFile) {
        ArrayList<String> readContent;
        readContent = new ArrayList<String>();
        BufferedReader reader = null;
        try {
            try {
                String currentReadLine2;
                reader = new BufferedReader(new FileReader(inputFile));
                while ((currentReadLine2 = reader.readLine()) != null) {
                    readContent.add(currentReadLine2);
                }
            }
            catch (FileNotFoundException currentReadLine2) {
            }
            catch (IOException currentReadLine2) {
            }
        }
        finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            }
            catch (IOException var6_10) {}
        }
        return readContent;
    }

    public static void write(File outputFile, List<String> writeContent, boolean overrideContent) {
        BufferedWriter writer = null;
        try {
            try {
                writer = new BufferedWriter(new FileWriter(outputFile, !overrideContent));
                for (String outputLine : writeContent) {
                    writer.write(outputLine);
                    writer.flush();
                    writer.newLine();
                }
            }
            catch (IOException outputLine) {
                try {
                    if (writer != null) {
                        writer.close();
                    }
                }
                catch (IOException var5_5) {}
            }
        }
        finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            }
            catch (IOException var7_10) {}
        }
    }

    public static File getConfigDir() {
        File file = new File(Minecraft.getMinecraft().mcDataDir, "TIMC");
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }
    public static File getProfileDir() {
        File file = new File(Minecraft.getMinecraft().mcDataDir, "TIMC/Profiles");
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }
    public static File getConfigFileExists(String name) {
        File file = new File(FileUtils.getConfigDir(), String.format("%s.txt", name));
        
        return file;
    }
    public static File getConfigFile(String name) {
        File file = new File(FileUtils.getConfigDir(), String.format("%s.txt", name));
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException var2_2) {
                // empty catch block
            }
        }
        return file;
    }
}
