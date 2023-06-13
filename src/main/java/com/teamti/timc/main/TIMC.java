//public enum TIMC implements Utils {
//    INSTANCE;
//    public static final String NAME = "TIMC";
//    public static final String VERSION = "1.0";
//    public static final Logger LOGGER = LogManager.getLogger(NAME);
//    public static final File DIRECTORY = new File(Minecraft.getMinecraft().mcDataDir, "TIMC");
//
//
//    private final EventProtocol<Event> eventProtocol = new EventProtocol<>();
////    private final NotificationManager notificationManager = new NotificationManager();
//    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
////    private final SideGui sideGui = new SideGui();
////    private ModuleCollection moduleCollection;
////    private ConfigManager configManager;
////    private GuiAltManager altManager;
//    private CommandHandler commandHandler;
////    private DiscordRP discordRP;
////    public final AltPanels altPanels = new AltPanels();
////    public KingGenApi kingGenApi;
////
//
//    public void init() {
////        this.moduleCollection = new ModuleCollection();
////        this.configManager = new ConfigManager();
////        this.altManager = new GuiAltManager();
//        try {
//            ViaMCP.create();
//            ViaMCP.INSTANCE.initAsyncSlider(); // For top left aligned slider
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public String getVersion() {
//        return VERSION;
//    }
//
//    public final Color getClientColor() {
//        return new Color(236, 133, 209);
//    }
//
//    public final Color getAlternateClientColor() {
//        return new Color(28, 167, 222);
//    }
//
////    public SideGui getSideGui() {
////        return sideGui;
////    }
////
////    public NotificationManager getNotificationManager() {
////        return notificationManager;
////    }
//
//    public CommandHandler getCommandHandler() { return commandHandler; }
//
////    public GuiAltManager getAltManager() { return altManager; }
////
////    public ModuleCollection getModuleCollection() {
////        return moduleCollection;
////    }
////
////    public ConfigManager getConfigManager() {
////        return configManager;
////    }
//
//    public ExecutorService getExecutorService() { return executorService; }
//
////    public void setModuleCollection(ModuleCollection moduleCollection) { this.moduleCollection = moduleCollection; }
////
////    public void setConfigManager(ConfigManager configManager) { this.configManager = configManager; }
////
////    public void setAltManager(GuiAltManager altManager) { this.altManager = altManager; }
////
////    public void setCommandHandler(CommandHandler commandHandler) { this.commandHandler = commandHandler; }
////
////    public void setDiscordRP(DiscordRP discordRP) { this.discordRP = discordRP; }
////
////
////    public boolean isToggled(Class<? extends Module> c) {
////        Module m = TIMC.moduleCollection.get(c);
////        return m != null && m.isToggled();
////    }
////
////    public Dragging createDrag(Module module, String name, float x, float y) {
////        DragManager.draggables.put(name, new Dragging(module, name, x, y));
////        return DragManager.draggables.get(name);
////    }
//    public EventProtocol<Event> getEventProtocol() {
//        return eventProtocol;
//    }
//
//    public static void dispatchEvent(Event event) {
//        TIMC.INSTANCE.getEventProtocol().dispatch(event);
//    }
//
//}
package com.teamti.timc.main;

import com.teamti.timc.config.ConfigManager;
import com.teamti.timc.config.DragManager;
import com.teamti.timc.event.EventProtocol;
import com.teamti.timc.module.Module;
import com.teamti.timc.module.ModuleCollection;
import com.teamti.timc.utils.Utils;
import com.teamti.timc.utils.drag.Dragging;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.command.CommandHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
@Setter
public class TIMC implements Utils {

    public static final TIMC INSTANCE = new TIMC();

    public static final String NAME = "TIMC";
    public static final String VERSION = "2.0";
//    public static final ReleaseType RELEASE = ReleaseType.DEV;
    public static final Logger LOGGER = LogManager.getLogger(NAME);
    public static final File DIRECTORY = new File(mc.mcDataDir, NAME);

    private final EventProtocol eventProtocol = new EventProtocol();
//    private final CloudDataManager cloudDataManager = new CloudDataManager();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
//    private final SideGUI sideGui = new SideGUI();
//    private final SearchBar searchBar = new SearchBar();
    private ModuleCollection moduleCollection;
//    private ScriptManager scriptManager;
//    private IntentAccount intentAccount;
    private ConfigManager configManager;
//    private GuiAltManager altManager;
    private CommandHandler commandHandler;
//    private PingerUtils pingerUtils;
//    private DiscordRPC discordRPC;
//    public KingGenApi kingGenApi;
//    private DiscordAccount discordAccount;

    public static boolean updateGuiScale;
    public static int prevGuiScale;

    public String getVersion() {
        return VERSION;
    }

    public final Color getClientColor() {
        return new Color(236, 133, 209);
    }

    public final Color getAlternateClientColor() {
        return new Color(28, 167, 222);
    }

    public boolean isEnabled(Class<? extends Module> c) {
        Module m = INSTANCE.moduleCollection.get(c);
        return m != null && m.isEnabled();
    }

    public Dragging createDrag(Module module, String name, float x, float y) {
        DragManager.draggables.put(name, new Dragging(module, name, x, y));
        return DragManager.draggables.get(name);
    }

}
