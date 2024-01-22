package com.minimap;

import com.minimap.events.ControlsHandler;
import com.minimap.events.Events;
import com.minimap.events.FMLEvents;
import com.minimap.interfaces.InterfaceHandler;
import com.minimap.settings.ModSettings;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;

public class XaeroMinimap
{
    public static XaeroMinimap instance;
    public static final String versionID = "1.8_1.9.1.1";
    public static int newestUpdateID;
    public static boolean isOutdated;
    public static ModSettings settings;
    public static String message;
    public static ControlsHandler ch;
    public static Events events;
    public static FMLEvents fmlEvents;
    public static File old_optionsFile;
    public static File old_waypointsFile;
    public static File optionsFile;
    public static File waypointsFile;
    public static Minecraft mc;
    
    public static ModSettings getSettings() {
        return XaeroMinimap.settings;
    }
    
    
    public void load() throws IOException {
        InterfaceHandler.loadPresets();
        InterfaceHandler.load();
        XaeroMinimap.settings = new ModSettings();
        if (XaeroMinimap.old_optionsFile.exists() && !XaeroMinimap.optionsFile.exists()) {
            new File("./config").mkdirs();
            Files.move(XaeroMinimap.old_optionsFile.toPath(), XaeroMinimap.optionsFile.toPath(), new CopyOption[0]);
        }
        if (XaeroMinimap.old_waypointsFile.exists() && !XaeroMinimap.waypointsFile.exists()) {
            new File("./config").mkdirs();
            Files.move(XaeroMinimap.old_waypointsFile.toPath(), XaeroMinimap.waypointsFile.toPath(), new CopyOption[0]);
        }
       // XaeroMinimap.settings.loadSettings();
        XaeroMinimap.events = new Events();
        //XaeroMinimap.fmlEvents = new FMLEvents();
        //this.checkModVersion();
        //MinecraftForge.EVENT_BUS.register((Object)XaeroMinimap.events);
        //FMLCommonHandler.instance().bus().register((Object)XaeroMinimap.fmlEvents);
       // SupportMods.load();
    }


    
    static {
        XaeroMinimap.isOutdated = true;
        XaeroMinimap.message = "";
        XaeroMinimap.old_optionsFile = new File("./xaerominimap.txt");
        XaeroMinimap.old_waypointsFile = new File("./xaerowaypoints.txt");
        XaeroMinimap.optionsFile = new File("./config/xaerominimap.txt");
        XaeroMinimap.waypointsFile = new File("./config/xaerowaypoints.txt");
        XaeroMinimap.mc = Minecraft.getMinecraft();
    }
}
