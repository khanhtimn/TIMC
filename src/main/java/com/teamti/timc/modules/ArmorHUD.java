package com.teamti.timc.modules;

import com.teamti.timc.event.EventManager;
import com.teamti.timc.event.EventTarget;
import com.teamti.timc.event.events.EventRenderGUI;
import com.teamti.timc.main.TIMC;
import com.teamti.timc.main.Module;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class ArmorHUD extends Module {

    private ScaledResolution sr;
    public static boolean isEnabled;


	public ArmorHUD() {
        super("ArmorHUD", Keyboard.KEY_NONE);
        this.jelloCat = TIMC.tabgui.cats.get(4);
    }

    public void onEnable(){
        isEnabled = true;
        EventManager.register(this);
    }

    public void onDisable(){
        EventManager.unregister(this);
        isEnabled = false;
    }


    @EventTarget
    public void onEvent(EventRenderGUI e) {
        boolean currentItem = true;
        GL11.glPushMatrix();
        ArrayList<ItemStack> stuff = new ArrayList<ItemStack>();
        boolean onwater = ArmorHUD.mc.thePlayer.isEntityAlive() && ArmorHUD.mc.thePlayer.isInsideOfMaterial(Material.water);
        int split = -3;
        for (int index = 3; index >= 0; --index) {
            ItemStack armer = ArmorHUD.mc.thePlayer.inventory.armorInventory[index];
            if (armer == null) continue;
            stuff.add(armer);
        }
        if (ArmorHUD.mc.thePlayer.getCurrentEquippedItem() != null) {
            stuff.add(ArmorHUD.mc.thePlayer.getCurrentEquippedItem());
        }
        for (ItemStack errything : stuff) {
            if (ArmorHUD.mc.theWorld != null) {
                RenderHelper.enableGUIStandardItemLighting();
                split += 16;
            }
            GlStateManager.pushMatrix();
            GlStateManager.disableAlpha();
            GlStateManager.clear(256);
            GlStateManager.enableBlend();
            ArmorHUD.mc.getRenderItem().zLevel = -150.0f;
            mc.getRenderItem().func_180450_b(errything, split + e.getScaledRes().getScaledWidth() / 2 - 4, e.getScaledRes().getScaledHeight() - (onwater ? 65 : 55));
            mc.getRenderItem().func_175030_a(ArmorHUD.mc.fontRendererObj, errything, split + e.getScaledRes().getScaledWidth() / 2 - 4, e.getScaledRes().getScaledHeight() - (onwater ? 65 : 55));
            ArmorHUD.mc.getRenderItem().zLevel = 0.0f;
            GlStateManager.disableBlend();
            GlStateManager.scale(0.5, 0.5, 0.5);
            GlStateManager.disableDepth();
            GlStateManager.disableLighting();
            GlStateManager.enableDepth();
            GlStateManager.scale(2.0f, 2.0f, 2.0f);
            GlStateManager.enableAlpha();
            GlStateManager.popMatrix();
            errything.getEnchantmentTagList();
        }
        GL11.glPopMatrix();
    }
}
