package com.teamti.timc.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;

public interface Utils {
    Minecraft mc = Minecraft.getMinecraft();
    FontRenderer fr = mc.fontRendererObj;
    ScaledResolution scaledResolution =  new ScaledResolution(mc);
    public static boolean isHovering(float x, float y, float width, float height, int mouseX, int mouseY) {
        return mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    }
}
