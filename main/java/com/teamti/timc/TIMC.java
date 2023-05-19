package com.teamti.timc;

import com.teamti.event.Event;
import com.teamti.event.EventProtocol;
import com.teamti.utils.Utils;
import de.florianmichael.viamcp.ViaMCP;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public enum TIMC implements Utils {
    INSTANCE;
    public static final String NAME = "TIMC";
    public static final String VERSION = "1.0";
    public static final Logger LOGGER = LogManager.getLogger(NAME);
    public static final File DIRECTORY = new File(Minecraft.getMinecraft().mcDataDir, "TIMC");


    private final EventProtocol<Event> eventProtocol = new EventProtocol<>();
//    private final NotificationManager notificationManager = new NotificationManager();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
//    private final SideGui sideGui = new SideGui();
//    private ModuleCollection moduleCollection;
//    private ConfigManager configManager;
//    private GuiAltManager altManager;
    private CommandHandler commandHandler;
//    private DiscordRP discordRP;
//    public final AltPanels altPanels = new AltPanels();
//    public KingGenApi kingGenApi;
//

    public void init() {
//        this.moduleCollection = new ModuleCollection();
//        this.configManager = new ConfigManager();
//        this.altManager = new GuiAltManager();
        try {
            ViaMCP.create();
            ViaMCP.INSTANCE.initAsyncSlider(); // For top left aligned slider
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getVersion() {
        return VERSION;
    }

    public final Color getClientColor() {
        return new Color(236, 133, 209);
    }

    public final Color getAlternateClientColor() {
        return new Color(28, 167, 222);
    }

//    public SideGui getSideGui() {
//        return sideGui;
//    }
//
//    public NotificationManager getNotificationManager() {
//        return notificationManager;
//    }

    public CommandHandler getCommandHandler() { return commandHandler; }

//    public GuiAltManager getAltManager() { return altManager; }
//
//    public ModuleCollection getModuleCollection() {
//        return moduleCollection;
//    }
//
//    public ConfigManager getConfigManager() {
//        return configManager;
//    }

    public ExecutorService getExecutorService() { return executorService; }

//    public void setModuleCollection(ModuleCollection moduleCollection) { this.moduleCollection = moduleCollection; }
//
//    public void setConfigManager(ConfigManager configManager) { this.configManager = configManager; }
//
//    public void setAltManager(GuiAltManager altManager) { this.altManager = altManager; }
//
//    public void setCommandHandler(CommandHandler commandHandler) { this.commandHandler = commandHandler; }
//
//    public void setDiscordRP(DiscordRP discordRP) { this.discordRP = discordRP; }
//
//
//    public boolean isToggled(Class<? extends Module> c) {
//        Module m = TIMC.moduleCollection.get(c);
//        return m != null && m.isToggled();
//    }
//
//    public Dragging createDrag(Module module, String name, float x, float y) {
//        DragManager.draggables.put(name, new Dragging(module, name, x, y));
//        return DragManager.draggables.get(name);
//    }
    public EventProtocol<Event> getEventProtocol() {
        return eventProtocol;
    }

    public static void dispatchEvent(Event event) {
        TIMC.INSTANCE.getEventProtocol().dispatch(event);
    }

}
