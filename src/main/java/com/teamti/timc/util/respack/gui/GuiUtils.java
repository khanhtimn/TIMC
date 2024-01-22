/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.Sys
 */
package com.teamti.timc.util.respack.gui;

import com.teamti.timc.util.respack.packs.ResourcePackListEntryFolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.Util;
import org.lwjgl.Sys;

import java.io.File;
import java.net.URI;
import java.util.List;

public final class GuiUtils {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void openFolder(File file) {
        String s = file.getAbsolutePath();
        if (Util.getOSType() == Util.EnumOS.OSX) {
            try {
                Runtime.getRuntime().exec(new String[]{"/usr/bin/open", s});
                return;
            }
            catch (Exception exception) {}
        } else if (Util.getOSType() == Util.EnumOS.WINDOWS) {
            String command = String.format("cmd.exe /C start \"Open file\" \"%s\"", s);
            try {
                Runtime.getRuntime().exec(command);
                return;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        try {
            Class<?> cls = Class.forName("java.awt.Desktop");
            Object desktop = cls.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
            cls.getMethod("browse", URI.class).invoke(desktop, file.toURI());
        }
        catch (Throwable t) {
            Sys.openURL((String)("file://" + s));
        }
    }

    public static void renderFolderEntry(ResourcePackListEntryFolder entry, int x, int y, boolean isSelected) {
        String s;
        int i2;
        entry.func_148313_c();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, 32, 32, 32.0f, 32.0f);
        GlStateManager.disableBlend();
        if ((GuiUtils.mc.gameSettings.touchscreen || isSelected) && entry.func_148310_d()) {
            Gui.drawRect(x, y, x + 32, y + 32, -1601138544);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        }
        if ((i2 = GuiUtils.mc.fontRendererObj.getStringWidth(s = entry.func_148312_b())) > 157) {
            s = GuiUtils.mc.fontRendererObj.trimStringToWidth(s, 157 - GuiUtils.mc.fontRendererObj.getStringWidth("...")) + "...";
        }
        GuiUtils.mc.fontRendererObj.drawStringWithShadow(s, x + 32 + 2, y + 1, 0xFFFFFF);
        List<String> list = GuiUtils.mc.fontRendererObj.listFormattedStringToWidth(entry.func_148311_a(), 157);
        for (int j2 = 0; j2 < 2 && j2 < list.size(); ++j2) {
            GuiUtils.mc.fontRendererObj.drawStringWithShadow(list.get(j2), x + 32 + 2, y + 12 + 10 * j2, 0x808080);
        }
    }
}

