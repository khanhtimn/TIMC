package com.teamti.timc.modules;

import com.teamti.timc.main.TIMC;
import com.teamti.timc.main.Module;
import com.teamti.timc.util.ColorUtil;
import com.teamti.timc.util.RenderingUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import org.lwjgl.input.Keyboard;

public class SmoothHotbar extends Module {


    static double currentSlot = 0.0D;
    public static double y;
    private static RenderingUtil r2DUtils = new RenderingUtil();


    public static RenderingUtil get2DUtils(){
        return r2DUtils;
    }

    public SmoothHotbar() {
        super("SmoothHotbar", Keyboard.KEY_NONE);
        this.jelloCat = TIMC.tabgui.cats.get(4);
    }


    public static void renderHotbar() {
        if (Minecraft.getMinecraft().currentScreen != null && Minecraft.getMinecraft().currentScreen instanceof GuiChat) {
            if (y > 0.0D) {
                y -= 0.3D;
            }
        } else if (y < 22.0D) {
            y += 0.3D;
        }

        double targetSlot = (double) Minecraft.getMinecraft().thePlayer.inventory.currentItem;
        if (Math.abs(currentSlot - targetSlot) < 0.01D) {
            currentSlot = targetSlot;
        } else {
            currentSlot += (targetSlot - currentSlot) / 10.0D;
        }

        GuiIngame var10000;

        var10000 = Minecraft.getMinecraft().ingameGUI;
        GuiIngame.drawRect(get2DUtils().scaledRes().getScaledWidth() - 5,
                (int) ((double) get2DUtils().scaledRes().getScaledHeight() - y), get2DUtils().scaledRes().getScaledWidth(),
                get2DUtils().scaledRes().getScaledHeight(), ColorUtil.getColor(255, 255, 255, 255));
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        int var6 = get2DUtils().scaledRes().getScaledWidth() / 2;
        var10000 = Minecraft.getMinecraft().ingameGUI;
        GuiIngame.drawRect(var6 - 91, (int) ((double) get2DUtils().scaledRes().getScaledHeight() - y), var6 - 91 + 180,
                get2DUtils().scaledRes().getScaledHeight(), ColorUtil.getColor(0, 0, 0, 100));
        var10000 = Minecraft.getMinecraft().ingameGUI;
        GuiIngame.drawRect((int) ((double) (var6 - 91) + currentSlot * 20.0D),
                (int) ((double) get2DUtils().scaledRes().getScaledHeight() - y),
                (int) ((double) (var6 - 91) + currentSlot * 20.0D + 20.0D), get2DUtils().scaledRes().getScaledHeight(),
                ColorUtil.getColor(255, 255, 255, 180));
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        RenderHelper.enableGUIStandardItemLighting();

        for (int j = 0; j < 9; ++j) {
            int k = get2DUtils().scaledRes().getScaledWidth() / 2 - 90 + j * 20 + 1;
            int l = (int) ((double) get2DUtils().scaledRes().getScaledHeight() - (y - 6.0D) - 3.0D);
            Minecraft.getMinecraft().ingameGUI.func_175184_a(j, k, l, Minecraft.getMinecraft().timer.renderPartialTicks, Minecraft.getMinecraft().thePlayer);
        }

        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
    }

}
