package com.teamti.timc.util;

import java.awt.Font;
import java.io.InputStream;

import com.teamti.timc.font.TIMCFontRenderer;
import com.teamti.timc.clickgui.TIMCGui;
import com.teamti.timc.main.TIMC;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

public class FontUtil {
	
	public static TIMCFontRenderer getTextFieldFont(boolean password){
		return password ? jelloFontGui : mc.currentScreen instanceof TIMCGui ? jelloFontGui : jelloFontAddAlt;
	}

	private static Font getJelloFont(float size, boolean bold) {
		Font font = null;
		try {
			InputStream is = Minecraft.getMinecraft().getResourceManager()
					.getResource(new ResourceLocation(bold ? "TIMC/jellomedium.ttf": "TIMC/jellolight.ttf")).getInputStream();
			font = Font.createFont(0, is);
			font = font.deriveFont(0, size);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error loading font");
			font = new Font("default", 0, +10);
		}
		return font;
	}
	
	private static Font getJelloFontRegular(int size) {
		Font font = null;
		try {
			InputStream is = Minecraft.getMinecraft().getResourceManager()
					.getResource(new ResourceLocation("TIMC/jelloregular.ttf")).getInputStream();
			font = Font.createFont(0, is);
			font = font.deriveFont(0, size);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error loading font");
			font = new Font("default", 0, +10);
		}
		return font;
	}
	
	private static Font getJelloFontUltralight(int size) {
		Font font = null;
		try {
			InputStream is = Minecraft.getMinecraft().getResourceManager()
					.getResource(new ResourceLocation("TIMC/jelloultralight.ttf")).getInputStream();
			font = Font.createFont(0, is);
			font = font.deriveFont(0, size);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error loading font");
			font = new Font("default", 0, +10);
		}
		return font;
	}
	
	public static Minecraft mc = TIMC.core.mc;
	public static FontRenderer fr = mc.fontRendererObj;
	public static TIMCFontRenderer jelloFont = TIMCFontRenderer.createFontRenderer(getJelloFont((int)(20), false));
	public static TIMCFontRenderer jelloFontScale = TIMCFontRenderer.createFontRenderer(getJelloFont((int)((20*1.2)), false));
	public static TIMCFontRenderer jelloFontAddAlt = TIMCFontRenderer.createFontRenderer(getJelloFont((int)(24), false));
	public static TIMCFontRenderer jelloFontGui = TIMCFontRenderer.createFontRenderer(getJelloFont((int)(25), false));
	public static TIMCFontRenderer jelloFontDuration = TIMCFontRenderer.createFontRenderer(getJelloFont((int)(13), false));
	public static TIMCFontRenderer jelloFontMusic = TIMCFontRenderer.createFontRenderer(getJelloFont((float) (12f), false));
	public static TIMCFontRenderer jelloFontAddAlt2 = TIMCFontRenderer.createFontRenderer(getJelloFont((int)(35), false));
	public static TIMCFontRenderer jelloFontAddAlt3 = TIMCFontRenderer.createFontRenderer(getJelloFont((int)(19), false));
	public static TIMCFontRenderer jelloFontRegular = TIMCFontRenderer.createFontRenderer(getJelloFontRegular((int)(20)));
	public static TIMCFontRenderer jelloFontRegularBig = TIMCFontRenderer.createFontRenderer(getJelloFontRegular((int)(24)));
	public static TIMCFontRenderer jelloFontBoldSmall = TIMCFontRenderer.createFontRenderer(getJelloFont((int)(19), true));
	public static TIMCFontRenderer jelloFontMarker = TIMCFontRenderer.createFontRenderer(getJelloFont((int)(19), false));
	public static TIMCFontRenderer jelloFontSmall = TIMCFontRenderer.createFontRenderer(getJelloFont((int)(14), false));
	public static TIMCFontRenderer jelloFontSmallPassword = TIMCFontRenderer.createFontRenderer(getJelloFont((int)(16), false));
	public static TIMCFontRenderer jelloFontBig = TIMCFontRenderer.createFontRenderer(getJelloFont((int)(41), true));
	public static TIMCFontRenderer jelloFontMedium = TIMCFontRenderer.createFontRenderer(getJelloFont((int)(25), false));
	public static TIMCFontRenderer font = TIMCFontRenderer.createFontRenderer(getJelloFontRegular((int)(18)));
	public static TIMCFontRenderer fontBig = TIMCFontRenderer.createFontRenderer(getJelloFontRegular((int)(33)));
	public static TIMCFontRenderer fontSmall = TIMCFontRenderer.createFontRenderer(getJelloFontRegular((int)(14)));
	
}
