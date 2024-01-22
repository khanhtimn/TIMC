package com.teamti.timc.modules;

import com.teamti.timc.event.EventManager;
import com.teamti.timc.event.EventTarget;
import com.teamti.timc.event.events.EventRender3D;
import com.teamti.timc.main.TIMC;
import com.teamti.timc.main.Module;
import com.teamti.timc.util.BooleanValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class ChestESP extends Module {

    public static boolean isEnabled;
    public final BooleanValue box;

    RendererLivingEntity renderlivingbase;

	public ChestESP() {
        super("ChestESP", Keyboard.KEY_NONE);
        this.jelloCat = TIMC.tabgui.cats.get(4);
        box = new BooleanValue("Box", true);
        addValue(box);
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
    public void onRender3D(EventRender3D event){
        for(Object t : Minecraft.getMinecraft().theWorld.loadedTileEntityList){
            if(t instanceof TileEntityChest){
                TileEntityChest chest = (TileEntityChest)t;
                if(box.getValue()){
                    this.drawChestESP(chest.getPos(), false);
                }
            }
            if(t instanceof TileEntityEnderChest){
                TileEntityEnderChest chest = (TileEntityEnderChest)t;
                if(box.getValue()){
                    this.drawChestESP(chest.getPos(), true);
                }
            }
        }
    }

    public static void drawChestESP(BlockPos blockPos, boolean echest)
    {
        double d = blockPos.getX() - Minecraft.getMinecraft().getRenderManager().renderPosX;
        double d1 = blockPos.getY() - Minecraft.getMinecraft().getRenderManager().renderPosY;
        double d2 = blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().renderPosZ;
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        if(echest){
            GL11.glColor4d(0.5f, 0f, 1f, 0.1001f);
        }else{
            GL11.glColor4d(1, 0.5f, 0, 0.1001F);
        }
        FilledESP(new AxisAlignedBB(d + 0.06, d1, d2  + 0.06, d + 0.94, d1 + 0.88, d2 + 0.94));
        if(echest){
            GL11.glColor4d(0.5f, 0f, 1f, 1f);
        }else{
            GL11.glColor4d(1f, 0.5f, 0f, 1f);
        }
        GL11.glLineWidth(1f);
        ProtocolEntityBox(new AxisAlignedBB(d + 0.06, d1, d2  + 0.06, d + 0.94, d1 + 0.88, d2 + 0.94));
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    public static void FilledESP(AxisAlignedBB aa)
    {
        Tessellator ts = Tessellator.getInstance();
        WorldRenderer wr = ts.getWorldRenderer();

        wr.startDrawingQuads();
        wr.addVertex(aa.minX, aa.minY, aa.minZ);
        wr.addVertex(aa.minX, aa.maxY, aa.minZ);
        wr.addVertex(aa.maxX, aa.minY, aa.minZ);
        wr.addVertex(aa.maxX, aa.maxY, aa.minZ);
        wr.addVertex(aa.maxX, aa.minY, aa.maxZ);
        wr.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        wr.addVertex(aa.minX, aa.minY, aa.maxZ);
        wr.addVertex(aa.minX, aa.maxY, aa.maxZ);
        ts.draw();
        wr.startDrawingQuads();
        wr.addVertex(aa.maxX, aa.maxY, aa.minZ);
        wr.addVertex(aa.maxX, aa.minY, aa.minZ);
        wr.addVertex(aa.minX, aa.maxY, aa.minZ);
        wr.addVertex(aa.minX, aa.minY, aa.minZ);
        wr.addVertex(aa.minX, aa.maxY, aa.maxZ);
        wr.addVertex(aa.minX, aa.minY, aa.maxZ);
        wr.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        wr.addVertex(aa.maxX, aa.minY, aa.maxZ);
        ts.draw();
        wr.startDrawingQuads();
        wr.addVertex(aa.minX, aa.maxY, aa.minZ);
        wr.addVertex(aa.maxX, aa.maxY, aa.minZ);
        wr.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        wr.addVertex(aa.minX, aa.maxY, aa.maxZ);
        wr.addVertex(aa.minX, aa.maxY, aa.minZ);
        wr.addVertex(aa.minX, aa.maxY, aa.maxZ);
        wr.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        wr.addVertex(aa.maxX, aa.maxY, aa.minZ);
        ts.draw();
        wr.startDrawingQuads();
        wr.addVertex(aa.minX, aa.minY, aa.minZ);
        wr.addVertex(aa.maxX, aa.minY, aa.minZ);
        wr.addVertex(aa.maxX, aa.minY, aa.maxZ);
        wr.addVertex(aa.minX, aa.minY, aa.maxZ);
        wr.addVertex(aa.minX, aa.minY, aa.minZ);
        wr.addVertex(aa.minX, aa.minY, aa.maxZ);
        wr.addVertex(aa.maxX, aa.minY, aa.maxZ);
        wr.addVertex(aa.maxX, aa.minY, aa.minZ);
        ts.draw();
        wr.startDrawingQuads();
        wr.addVertex(aa.minX, aa.minY, aa.minZ);
        wr.addVertex(aa.minX, aa.maxY, aa.minZ);
        wr.addVertex(aa.minX, aa.minY, aa.maxZ);
        wr.addVertex(aa.minX, aa.maxY, aa.maxZ);
        wr.addVertex(aa.maxX, aa.minY, aa.maxZ);
        wr.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        wr.addVertex(aa.maxX, aa.minY, aa.minZ);
        wr.addVertex(aa.maxX, aa.maxY, aa.minZ);
        ts.draw();
        wr.startDrawingQuads();
        wr.addVertex(aa.minX, aa.maxY, aa.maxZ);
        wr.addVertex(aa.minX, aa.minY, aa.maxZ);
        wr.addVertex(aa.minX, aa.maxY, aa.minZ);
        wr.addVertex(aa.minX, aa.minY, aa.minZ);
        wr.addVertex(aa.maxX, aa.maxY, aa.minZ);
        wr.addVertex(aa.maxX, aa.minY, aa.minZ);
        wr.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        wr.addVertex(aa.maxX, aa.minY, aa.maxZ);
        ts.draw();
    }

    public static void ProtocolEntityBox(AxisAlignedBB aa)
    {
        Tessellator ts = Tessellator.getInstance();
        WorldRenderer wr = ts.getWorldRenderer();
        wr.startDrawing(3);
        wr.addVertex(aa.maxX, aa.maxY, aa.minZ);
        wr.addVertex(aa.minX, aa.maxY, aa.minZ);
        wr.addVertex(aa.minX, aa.minY, aa.minZ);
        wr.addVertex(aa.maxX, aa.minY, aa.minZ);
        wr.addVertex(aa.maxX, aa.maxY, aa.minZ);
        ts.draw();
        wr.startDrawing(3);
        wr.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        wr.addVertex(aa.minX, aa.maxY, aa.maxZ);
        wr.addVertex(aa.minX, aa.minY, aa.maxZ);
        wr.addVertex(aa.maxX, aa.minY, aa.maxZ);
        wr.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        ts.draw();
        wr.startDrawing(3);
        wr.addVertex(aa.maxX, aa.maxY, aa.minZ);
        wr.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        ts.draw();
        wr.startDrawing(3);
        wr.addVertex(aa.maxX, aa.minY, aa.minZ);
        wr.addVertex(aa.maxX, aa.minY, aa.maxZ);
        ts.draw();
        wr.startDrawing(3);
        wr.addVertex(aa.minX, aa.minY, aa.minZ);
        wr.addVertex(aa.minX, aa.minY, aa.maxZ);
        ts.draw();
        wr.startDrawing(3);
        wr.addVertex(aa.minX, aa.maxY, aa.minZ);
        wr.addVertex(aa.minX, aa.maxY, aa.maxZ);
        ts.draw();
//			wr.startDrawing(3);
//			wr.addVertex(aa.minX, aa.maxY, aa.maxZ);
//			wr.addVertex(aa.minX, aa.minY, aa.minZ);
//			ts.draw();
//			wr.startDrawing(3);
//			wr.addVertex(aa.maxX, aa.maxY, aa.maxZ);
//			wr.addVertex(aa.maxX, aa.minY, aa.minZ);
//			ts.draw();
//			wr.startDrawing(3);
//			wr.addVertex(aa.maxX, aa.maxY, aa.minZ);
//			wr.addVertex(aa.minX, aa.maxY, aa.maxZ);
//			ts.draw();
//			wr.startDrawing(3);
//			wr.addVertex(aa.maxX, aa.minY, aa.minZ);
//			wr.addVertex(aa.minX, aa.minY, aa.maxZ);
//			ts.draw();
    }
}
