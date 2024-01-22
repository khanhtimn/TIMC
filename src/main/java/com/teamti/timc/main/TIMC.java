package com.teamti.timc.main;

import com.teamti.timc.alts.AltFile;
import com.teamti.timc.alts.AltManager;
import com.teamti.timc.alts.GuiAltManager;
import com.teamti.timc.clickgui.TIMCGui;
import com.teamti.timc.discord.DiscordMain;
import com.teamti.timc.hud.TIMCHud;
import com.teamti.timc.modules.*;
import com.teamti.timc.modules.crasher.*;
import com.teamti.timc.modules.crasher.ac.AnimationCrasher2;
import com.teamti.timc.modules.crasher.acc.AutoCompleteCrasher3;
import com.teamti.timc.modules.crasher.cpc.CustomPayloadBypass1;
import com.teamti.timc.modules.crasher.lbc.LabySpammerCrasher;
import com.teamti.timc.modules.crasher.netty.NettyCrasher1;
import com.teamti.timc.modules.crasher.wec.WorldEditCrasher1;
import com.teamti.timc.tabgui.TabGUI;
import com.teamti.timc.util.ChestUtil;
import com.teamti.timc.util.FileManager;
import com.teamti.timc.util.InventoryUtil;
import com.teamti.timc.util.SettingsFile;
import com.teamti.timc.util.chunkanimator.ChunkAnimator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ChatComponentText;
import viamcp.ViaMCP;

import java.io.File;
import java.util.ArrayList;


public class TIMC {

	//f9e8984f2c8d1d18360df902525c93180b9b7726
	public static ArrayList<Module> mods = new ArrayList<Module>();
	private static ScaledResolution s = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
	public static TabGUI tabgui = new TabGUI();
	public static double fontScaleOffset = 1;//round((double)1600/1080, 1) * s.getScaleFactor();//2.75;
	private static TIMCHud hud;
	public static Object theClient;
	public static TCore core;
    private static AltManager altManager;
	private static File directory;
    private static FileManager fileManager;
    public static GuiAltManager altmanagergui;
    public static ArrayList<String> clickguiarray = new ArrayList<String>();
    public static SettingsFile settingsFile = new SettingsFile();
    public static TIMCGui jgui;
   // public static Menu menu;
    public static ChestUtil chestUtil = new ChestUtil();
	public static InventoryUtil inventoryUtil = new InventoryUtil();
	public static double round (double value, int precision) {
	    int scale = (int) Math.pow(10, precision);
	    return (double) Math.round(value * scale) / scale;
	}

	public static void register() {

		DiscordMain.getInstance().init();

		try
		{
			ViaMCP.getInstance().start();

			// Only use one of the following
			ViaMCP.getInstance().initAsyncSlider(); // For top left aligned slider

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		new ChunkAnimator();
		ChunkAnimator.INSTANCE.onStart();

		register(new Notifications());
		register(new Compass());
		register(new com.teamti.timc.modules.TabGUI());
		register(new KeyStrokes());
		register(new MiniMap());
		register(new ActiveMods());
		register(new MenuGUI());
		register(new ItemPhysics());
		register(new ChunkAnimation());
		register(new SmoothHotbar());
		register(new BrigdeAssist());
		register(new AutoTool());
		register(new TimeChanger());
		register(new Crosshair());

		register(new ChestStealer());
		register(new InvCleaner());
		register(new AutoClicker());
		register(new InvMove());
		register(new ESP());
		register(new ChestESP());
		register(new Xray());
		register(new ThirdPerson());
		register(new ArmorHUD());
		register(new Projectiles());
		register(new BowAimbot());
		register(new NoRotate());
		register(new OldBlockHit());
		register(new LeftHand());
		register(new LeftBow());
		register(new OldSneak());
		register(new AntiBlind());
		register(new AutoArmor());


		register(new FlyCrasher());
		register(new AnimationCrasher2());
		register(new CustomPayloadBypass1());
		register(new NettyCrasher1());
		register(new WorldEditCrasher1());
		register(new ExploitFixerCrasher());
		register(new BungeeCrasher());
		register(new MassChunkLoadCrasher());
		register(new NcpCrasher());
		register(new AutoCompleteCrasher3());
		register(new LabySpammerCrasher());


		TIMC.fileManager = new FileManager();
		jgui = new TIMCGui();
		altManager = new AltManager();
		altmanagergui = new GuiAltManager();
		hud = new TIMCHud();
		core = new TCore();
		
		settingsFile.loadFiles();
		
		TIMC.altManager.setupAlts();
		
		
		AltFile.load();
	}
	
	public static void register(Module module) {
		mods.add(module);
	}

	public static ArrayList<Module> getModules() {
		return mods;
	}
	
	public static TIMCHud getInGameGUI() {
		return hud;
	}
	
	public static void onKeyPressed(int keyCode) {
		for(Module module : mods) {
			if(module.getKeyCode() == keyCode) {
				module.toggle();
			}
		}
		if(keyCode == 200){
			tabgui.keyUp();
		}
		if(keyCode == 208){
			tabgui.keyDown();
		}
		if(keyCode == 203){
			tabgui.keyLeft();
		}
		if(keyCode == 205){
			tabgui.keyRight();
		}
	}
	
	public static void onUpdate() {
		for(Module module : mods) {
			module.onUpdate();
		}
	}
	
	public static void onRender() {
		for(Module module : mods) {
			module.onRender();
		}
	}
	
	public static AltManager getAltManager() {
        return TIMC.altManager;
    }
	public static FileManager getFileManager() {
        return TIMC.fileManager;
    }

	public static void addChatMessage(String s) {
		core.player().addChatMessage(new ChatComponentText("[TIMC] \247r" + s));
	}
	public static void addSilentChatMessage(String s) {
		core.player().addChatMessage(new ChatComponentText(s));
	}

	public static void sendChatMessage(String s) {
		core.player().sendChatMessage(s);
	}
	public static File getDirectory() {
        return TIMC.directory;
    }
	public static boolean onSendChatMessage(String s) {// EntityPlayerSP
		
		return true;
	}
	
	public static Module getModule(final String modName) {
        for (final Module module : getModules()) {
            if (module.getName().equalsIgnoreCase(modName) || module.getName().equalsIgnoreCase(modName)) {
                return module;
            }
        }
        return null;
    }
	
	public static ArrayList<Module> getModulesInCategory(TabGUI.Cat cat)
    {
      ArrayList<Module> modsInCat = new ArrayList();
      for (Module mod : getModules()) {
    	  if(mod.jelloCat != null){
    		  if(mod.jelloCat.equals(cat)){
    			  modsInCat.add(mod);
    		  }
    	  }
      }
      return modsInCat;
    }
	
}
