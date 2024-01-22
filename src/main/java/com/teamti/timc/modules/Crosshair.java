package com.teamti.timc.modules;

import com.teamti.timc.event.EventManager;
import com.teamti.timc.event.EventTarget;
import com.teamti.timc.event.events.EventRenderCrosshair;
import com.teamti.timc.main.TIMC;
import com.teamti.timc.main.Module;
import com.teamti.timc.util.BooleanValue;
import com.teamti.timc.util.NumberValue;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Crosshair extends Module {

    public static boolean isEnabled;
    public static BooleanValue dot;
    //public static BooleanValue outline;
    public static NumberValue thickness ;
    public static NumberValue length;
    public static NumberValue gap;

    public static NumberValue outlineThickness;
    Color color = new Color(0, 255, 0);

	public Crosshair() {
        super("Crosshair", Keyboard.KEY_NONE);
        this.jelloCat = TIMC.tabgui.cats.get(4);
        dot = new BooleanValue("Dot", true);
        addValue(dot);
        thickness = new NumberValue("Thickness", 1.0D, 0.5D, 4.0D, 0.5D);
        addValue(thickness);
        length = new NumberValue("Length", 3.0D, 0.5D, 10.0D, 0.5D);
        addValue(length);
        gap = new NumberValue("Gap", 3.0D, 0.5D, 4.0D, 0.5D);
        addValue(gap);
        outlineThickness = new NumberValue("Outline Thickness", 0.5D, 0D, 4.0D, 0.5D);
        addValue(outlineThickness);
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
    public final void RenderCrosshair(EventRenderCrosshair event) {
        GL11.glPushMatrix();
        event.setCancelled(true);
        ScaledResolution sr = event.getScaledRes();
        double thickness = (Double)Crosshair.thickness.getValue() / 2.0D;
        int width = sr.getScaledWidth();
        int height = sr.getScaledHeight();
        float middleX = (float)width / 2.0F;
        float middleY = (float)height / 2.0F;
        if (dot.getValue()) {
            Gui.drawRect((double)middleX - thickness - (Double)outlineThickness.getValue(), (double)middleY - thickness - (Double)outlineThickness.getValue(), (double)middleX + thickness + (Double)outlineThickness.getValue(), (double)middleY + thickness + (Double)outlineThickness.getValue(), Color.BLACK.getRGB());
            Gui.drawRect((double)middleX - thickness, (double)middleY - thickness, (double)middleX + thickness, (double)middleY + thickness, (color.getRGB()));
        }

        Gui.drawRect((double)middleX - thickness - (Double)outlineThickness.getValue(), (double)middleY - (Double)gap.getValue() - (Double)length.getValue() - (Double)outlineThickness.getValue(), (double)middleX + thickness + (Double)outlineThickness.getValue(), (double)middleY - (Double)gap.getValue() + (Double)outlineThickness.getValue(), Color.BLACK.getRGB());
        Gui.drawRect((double)middleX - thickness, (double)middleY - (Double)gap.getValue() - (Double)length.getValue(), (double)middleX + thickness, (double)middleY - (Double)gap.getValue(), (color.getRGB()));
        Gui.drawRect((double)middleX - (Double)gap.getValue() - (Double)length.getValue() - (Double)outlineThickness.getValue(), (double)middleY - thickness - (Double)outlineThickness.getValue(), (double)middleX - (Double)gap.getValue() + (Double)outlineThickness.getValue(), (double)middleY + thickness + (Double)outlineThickness.getValue(), Color.BLACK.getRGB());
        Gui.drawRect((double)middleX - (Double)gap.getValue() - (Double)length.getValue(), (double)middleY - thickness, (double)middleX - (Double)gap.getValue(), (double)middleY + thickness, (color.getRGB()));
        Gui.drawRect((double)middleX - thickness - (Double)outlineThickness.getValue(), (double)middleY + (Double)gap.getValue() - (Double)outlineThickness.getValue(), (double)middleX + thickness + (Double)outlineThickness.getValue(), (double)middleY + (Double)gap.getValue() + (Double)length.getValue() + (Double)outlineThickness.getValue(), Color.BLACK.getRGB());
        Gui.drawRect((double)middleX - thickness, (double)middleY + (Double)gap.getValue(), (double)middleX + thickness, (double)middleY + (Double)gap.getValue() + (Double)length.getValue(), (color.getRGB()));
        Gui.drawRect((double)middleX + (Double)gap.getValue() - (Double)outlineThickness.getValue(), (double)middleY - thickness - (Double)outlineThickness.getValue(), (double)middleX + (Double)gap.getValue() + (Double)length.getValue() + (Double)outlineThickness.getValue(), (double)middleY + thickness + (Double)outlineThickness.getValue(), Color.BLACK.getRGB());
        Gui.drawRect((double)middleX + (Double)gap.getValue(), (double)middleY - thickness, (double)middleX + (Double)gap.getValue() + (Double)length.getValue(), (double)middleY + thickness, (color.getRGB()));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableBlend();
        GL11.glPopMatrix();
    }
}
