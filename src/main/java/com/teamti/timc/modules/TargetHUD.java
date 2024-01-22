package com.teamti.timc.modules;

import com.teamti.timc.event.EventManager;
import com.teamti.timc.event.EventTarget;
import com.teamti.timc.event.events.EventRender2D;
import com.teamti.timc.main.TIMC;
import com.teamti.timc.main.Module;
import com.teamti.timc.util.ColorUtil;
import com.teamti.timc.util.RenderingUtil;
import com.teamti.timc.util.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TargetHUD extends Module {

    //GetTargetUtil targetUtil = new GetTargetUtil();

    public TargetHUD() {
        super("TargetHUD", Keyboard.KEY_NONE);
        this.jelloCat = TIMC.tabgui.cats.get(4);
    }

    private int targetIndex;
    public EntityLivingBase target;
    public static boolean isEnabled;
    private double healthBarWidth;
    private double hudHeight;
    private static final Color COLOR = new Color(0, 0, 0, 180);
    private final TimerUtil animationStopwatch = new TimerUtil();
    private List<EntityLivingBase> targetList = new ArrayList<>();
    Minecraft mc = Minecraft.getMinecraft();

    public void onEnable() {
        isEnabled = true;
        EventManager.register(this);
        targetList.clear();
        target = null;
    }

    public void onDisable() {
        targetList.clear();
        target = null;
        EventManager.unregister(this);
        isEnabled = false;
    }
    @EventTarget
    public final void onRender(EventRender2D event) {
        AuraMod aura = new AuraMod();
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        float scaledWidth = (float) sr.getScaledWidth();
        float scaledHeight = (float)sr.getScaledHeight();
        if (aura.getTarget() != null) {
            if (aura.getTarget() instanceof EntityOtherPlayerMP) {
                this.target = (EntityOtherPlayerMP)aura.getTarget();
                float width = 140.0F;
                float height = 40.0F;
                float xOffset = 40.0F;
                float x = scaledWidth / 2.0F - 70.0F;
                float y = scaledHeight / 2.0F + 80.0F;
                float health = this.target.getHealth();
                double hpPercentage = (double)(health / this.target.getMaxHealth());
                hpPercentage = MathHelper.clamp_double(hpPercentage, 0.0D, 1.0D);
                double hpWidth = 92.0D * hpPercentage;
                int healthColor = ColorUtil.getHealthColor(this.target.getHealth(), this.target.getMaxHealth()).getRGB();
                String healthStr = String.valueOf((float)((int)this.target.getHealth()) / 2.0F);
                if (this.animationStopwatch.hasTimeElapsed(15L,false)) {
                    this.healthBarWidth = RenderingUtil.animate(hpWidth, this.healthBarWidth, 0.3529999852180481D);
                    this.hudHeight = RenderingUtil.animate(40.0D, this.hudHeight, 0.10000000149011612D);
                    this.animationStopwatch.reset();
                }

                GL11.glEnable(3089);
                RenderingUtil.prepareScissorBox(x, y, x + 140.0F, (float)((double)y + this.hudHeight));
                Gui.drawRect((double)x, (double)y, (double)(x + 140.0F), (double)(y + 40.0F), COLOR.getRGB());
                Gui.drawRect((double)(x + 40.0F), (double)(y + 15.0F), (double)(x + 40.0F) + this.healthBarWidth, (double)(y + 25.0F), healthColor);
                mc.fontRendererObj.drawStringWithShadow(healthStr, x + 40.0F + 46.0F - (float)mc.fontRendererObj.getStringWidth(healthStr) / 2.0F, y + 16.0F, -1);
                mc.fontRendererObj.drawStringWithShadow(this.target.getName(), x + 40.0F, y + 2.0F, -1);
                GuiInventory.drawEntityOnScreen((int)(x + 13.333333F), (int)(y + 40.0F), 20, this.target.rotationYaw, this.target.rotationPitch, this.target);
                GL11.glDisable(3089);
            }
        } else {
            this.healthBarWidth = 92.0D;
            this.hudHeight = 0.0D;
            this.target = null;
        }

    }

}
