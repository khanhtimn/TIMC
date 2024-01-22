package com.teamti.timc.modules;

import com.teamti.timc.event.EventManager;
import com.teamti.timc.event.EventTarget;
import com.teamti.timc.event.events.EventModelUpdate;
import com.teamti.timc.event.events.EventRender3D;
import com.teamti.timc.main.TIMC;
import com.teamti.timc.main.Module;
import com.teamti.timc.util.BooleanValue;
import com.teamti.timc.util.GLUtil;
import com.teamti.timc.util.RenderingUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class ESP extends Module {

    public static boolean isEnabled;

    private int	amount = 0;
    private float spin;
    private float cumSize;
    public int selection = 0;

    private final Map playerRotationMap = new WeakHashMap();
    public static BooleanValue smoothLines = new BooleanValue("Smooth Lines", true);

    public static final BooleanValue player = new BooleanValue("Player", true);
    public static final BooleanValue mob = new BooleanValue("Mob", false);
    //public static final BooleanValue chams = new BooleanValue("Filled", true);
    public static final BooleanValue outline = new BooleanValue("Outline", true);
    public static final BooleanValue skeleton = new BooleanValue("Skeleton", false);
    public static final BooleanValue box = new BooleanValue("Box", true);
    public static final BooleanValue wallhack = new BooleanValue("Wallhack", false);

    RendererLivingEntity renderlivingbase;

	public ESP() {
        super("ESP", Keyboard.KEY_NONE);
        this.jelloCat = TIMC.tabgui.cats.get(4);
        //player = new BooleanValue("Player", true);
        //addValue(player);
        //mob = new BooleanValue("Mob", false);
        //addValue(mob);
        //chams = new BooleanValue("Filled", true);
        //addValue(chams);
        //outline = new BooleanValue("Outline", false);
        addValue(outline);
        //box = new BooleanValue("Outline", true);
        //addValue(box);
        //wallhack = new BooleanValue("Wallhack", false);
        addValue(wallhack);
        addValue(skeleton);
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
    public final void onModelUpdate(EventModelUpdate event) {
        ModelPlayer model = event.getModel();
        this.playerRotationMap.put(event.getPlayer(), new float[][]{{model.bipedHead.rotateAngleX, model.bipedHead.rotateAngleY, model.bipedHead.rotateAngleZ}, {model.bipedRightArm.rotateAngleX, model.bipedRightArm.rotateAngleY, model.bipedRightArm.rotateAngleZ}, {model.bipedLeftArm.rotateAngleX, model.bipedLeftArm.rotateAngleY, model.bipedLeftArm.rotateAngleZ}, {model.bipedRightLeg.rotateAngleX, model.bipedRightLeg.rotateAngleY, model.bipedRightLeg.rotateAngleZ}, {model.bipedLeftLeg.rotateAngleX, model.bipedLeftLeg.rotateAngleY, model.bipedLeftLeg.rotateAngleZ}});
    }
    
    @EventTarget
    public void onRenderEvent(EventRender3D event) {
        setDisplayName("ESP");
        //if (box.getValue()) {
        //    setDisplayName(getDisplayName() + " [Box]");
        //}

        if (outline.getValue()) {
            setDisplayName(getDisplayName() + " [Outline]");

            for (Object theObject : mc.theWorld.loadedEntityList) {
                if (!(theObject instanceof EntityLivingBase))
                    continue;
                EntityLivingBase entity = (EntityLivingBase) theObject;
                if (isCorrect(entity)) {
                    if (isCorrect(entity)) {
                        if (box.getValue()) {
                            player(entity);
                        }

                    }
                }
            }


        }

        if (skeleton.getValue()) {
            setDisplayName(getDisplayName() + " [Skeleton]");
            this.setupRender(true);
            GL11.glEnable(2903);
            GL11.glDisable(2848);
            this.playerRotationMap.keySet().removeIf(this::contain);
            Map playerRotationMap = this.playerRotationMap;
            List worldPlayers = Skeleton.mc.theWorld.playerEntities;
            Object[] players = playerRotationMap.keySet().toArray();
            Minecraft mc = Module.mc;
            int i = 0;

            for(int playersLength = players.length; i < playersLength; ++i) {
                EntityPlayer player = (EntityPlayer)players[i];
                float[][] entPos = (float[][])playerRotationMap.get(player);
                if (entPos != null && player.getEntityId() != -1488 && player.isEntityAlive() && RenderingUtil.isInViewFrustrum((Entity)player) && !player.isDead && player != mc.thePlayer && !player.isPlayerSleeping() && !player.isInvisible()) {
                    GL11.glPushMatrix();
                    float[][] modelRotations = (float[][])playerRotationMap.get(player);
                    GL11.glLineWidth(1.0F);
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    double x = GLUtil.interpolate(player.posX, player.lastTickPosX, (double)event.getPartialTicks()) - mc.getRenderManager().getRenderPosX();
                    double y = GLUtil.interpolate(player.posY, player.lastTickPosY, (double)event.getPartialTicks()) - mc.getRenderManager().getRenderPosY();
                    double z = GLUtil.interpolate(player.posZ, player.lastTickPosZ, (double)event.getPartialTicks()) - mc.getRenderManager().getRenderPosZ();
                    GL11.glTranslated(x, y, z);
                    float bodyYawOffset = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * mc.timer.renderPartialTicks;
                    GL11.glRotatef(-bodyYawOffset, 0.0F, 1.0F, 0.0F);
                    GL11.glTranslated(0.0D, 0.0D, player.isSneaking() ? -0.235D : 0.0D);
                    float legHeight = player.isSneaking() ? 0.6F : 0.75F;
                    float rad = 57.29578F;
                    GL11.glPushMatrix();
                    GL11.glTranslated(-0.125D, (double)legHeight, 0.0D);
                    if (modelRotations[3][0] != 0.0F) {
                        GL11.glRotatef(modelRotations[3][0] * 57.29578F, 1.0F, 0.0F, 0.0F);
                    }

                    if (modelRotations[3][1] != 0.0F) {
                        GL11.glRotatef(modelRotations[3][1] * 57.29578F, 0.0F, 1.0F, 0.0F);
                    }

                    if (modelRotations[3][2] != 0.0F) {
                        GL11.glRotatef(modelRotations[3][2] * 57.29578F, 0.0F, 0.0F, 1.0F);
                    }

                    GL11.glBegin(3);
                    GL11.glVertex3d(0.0D, 0.0D, 0.0D);
                    GL11.glVertex3d(0.0D, (double)(-legHeight), 0.0D);
                    GL11.glEnd();
                    GL11.glPopMatrix();
                    GL11.glPushMatrix();
                    GL11.glTranslated(0.125D, (double)legHeight, 0.0D);
                    if (modelRotations[4][0] != 0.0F) {
                        GL11.glRotatef(modelRotations[4][0] * 57.29578F, 1.0F, 0.0F, 0.0F);
                    }

                    if (modelRotations[4][1] != 0.0F) {
                        GL11.glRotatef(modelRotations[4][1] * 57.29578F, 0.0F, 1.0F, 0.0F);
                    }

                    if (modelRotations[4][2] != 0.0F) {
                        GL11.glRotatef(modelRotations[4][2] * 57.29578F, 0.0F, 0.0F, 1.0F);
                    }

                    GL11.glBegin(3);
                    GL11.glVertex3d(0.0D, 0.0D, 0.0D);
                    GL11.glVertex3d(0.0D, (double)(-legHeight), 0.0D);
                    GL11.glEnd();
                    GL11.glPopMatrix();
                    GL11.glTranslated(0.0D, 0.0D, player.isSneaking() ? 0.25D : 0.0D);
                    GL11.glPushMatrix();
                    GL11.glTranslated(0.0D, player.isSneaking() ? -0.05D : 0.0D, player.isSneaking() ? -0.01725D : 0.0D);
                    GL11.glPushMatrix();
                    GL11.glTranslated(-0.375D, (double)legHeight + 0.55D, 0.0D);
                    if (modelRotations[1][0] != 0.0F) {
                        GL11.glRotatef(modelRotations[1][0] * 57.29578F, 1.0F, 0.0F, 0.0F);
                    }

                    if (modelRotations[1][1] != 0.0F) {
                        GL11.glRotatef(modelRotations[1][1] * 57.29578F, 0.0F, 1.0F, 0.0F);
                    }

                    if (modelRotations[1][2] != 0.0F) {
                        GL11.glRotatef(-modelRotations[1][2] * 57.29578F, 0.0F, 0.0F, 1.0F);
                    }

                    GL11.glBegin(3);
                    GL11.glVertex3d(0.0D, 0.0D, 0.0D);
                    GL11.glVertex3d(0.0D, -0.5D, 0.0D);
                    GL11.glEnd();
                    GL11.glPopMatrix();
                    GL11.glPushMatrix();
                    GL11.glTranslated(0.375D, (double)legHeight + 0.55D, 0.0D);
                    if (modelRotations[2][0] != 0.0F) {
                        GL11.glRotatef(modelRotations[2][0] * 57.29578F, 1.0F, 0.0F, 0.0F);
                    }

                    if (modelRotations[2][1] != 0.0F) {
                        GL11.glRotatef(modelRotations[2][1] * 57.29578F, 0.0F, 1.0F, 0.0F);
                    }

                    if (modelRotations[2][2] != 0.0F) {
                        GL11.glRotatef(-modelRotations[2][2] * 57.29578F, 0.0F, 0.0F, 1.0F);
                    }

                    GL11.glBegin(3);
                    GL11.glVertex3d(0.0D, 0.0D, 0.0D);
                    GL11.glVertex3d(0.0D, -0.5D, 0.0D);
                    GL11.glEnd();
                    GL11.glPopMatrix();
                    GL11.glRotatef(bodyYawOffset - player.rotationYawHead, 0.0F, 1.0F, 0.0F);
                    GL11.glPushMatrix();
                    GL11.glTranslated(0.0D, (double)legHeight + 0.55D, 0.0D);
                    if (modelRotations[0][0] != 0.0F) {
                        GL11.glRotatef(modelRotations[0][0] * 57.29578F, 1.0F, 0.0F, 0.0F);
                    }

                    GL11.glBegin(3);
                    GL11.glVertex3d(0.0D, 0.0D, 0.0D);
                    GL11.glVertex3d(0.0D, 0.3D, 0.0D);
                    GL11.glEnd();
                    GL11.glPopMatrix();
                    GL11.glPopMatrix();
                    GL11.glRotatef(player.isSneaking() ? 25.0F : 0.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glTranslated(0.0D, player.isSneaking() ? -0.16175D : 0.0D, player.isSneaking() ? -0.48025D : 0.0D);
                    GL11.glPushMatrix();
                    GL11.glTranslated(0.0D, (double)legHeight, 0.0D);
                    GL11.glBegin(3);
                    GL11.glVertex3d(-0.125D, 0.0D, 0.0D);
                    GL11.glVertex3d(0.125D, 0.0D, 0.0D);
                    GL11.glEnd();
                    GL11.glPopMatrix();
                    GL11.glPushMatrix();
                    GL11.glTranslated(0.0D, (double)legHeight, 0.0D);
                    GL11.glBegin(3);
                    GL11.glVertex3d(0.0D, 0.0D, 0.0D);
                    GL11.glVertex3d(0.0D, 0.55D, 0.0D);
                    GL11.glEnd();
                    GL11.glPopMatrix();
                    GL11.glPushMatrix();
                    GL11.glTranslated(0.0D, (double)legHeight + 0.55D, 0.0D);
                    GL11.glBegin(3);
                    GL11.glVertex3d(-0.375D, 0.0D, 0.0D);
                    GL11.glVertex3d(0.375D, 0.0D, 0.0D);
                    GL11.glEnd();
                    GL11.glPopMatrix();
                    GL11.glPopMatrix();
                }
            }

            this.setupRender(false);
        }

    }

    public void player(EntityLivingBase entity) {
        float red = 1;
        float green = 1f;
        float blue = 1F;
        float red2 = 0F;
        float blue2 = 1F;
        float green2 = 1F;
        float ired = 1F;
        float igreen = 0.5f;
        float iblue = 0f;

        double xPos = (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks) - mc.getRenderManager().renderPosX;
        double yPos = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks) - mc.getRenderManager().renderPosY;
        double zPos = (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks) - mc.getRenderManager().renderPosZ;

        if (isCorrect(entity)) {
            if (entity.isInvisible() /*&& !Protocol.getFriendManager().isFriend(entity.getName()*/) {
                render(ired, igreen, iblue, xPos, yPos, zPos, entity.width, entity.height, entity);
                return;
            }

            /*
            if (Protocol.getFriendManager().isFriend(entity.getName())) {
                render(red2, green2, blue2, xPos, yPos, zPos, entity.width, entity.height, entity);
                return;         
             */
            } else {
                if (!entity.isPlayerSleeping() && !entity.isSneaking() && !entity.isInvisible()) {
                    render(red, green, blue, xPos, yPos, zPos, entity.width, entity.height, entity);
                    return;
                }
                if (entity.isSneaking() && !entity.isInvisible()) {
                    render(1F, 0.4F, 0.4F, xPos, yPos, zPos, entity.width, entity.height, entity);
                    return;
                }
            }
	    }

    public void render(float red, float green, float blue, double x, double y, double z, float width, float height, EntityLivingBase entity) {
        this.drawEntityESP(x, y, z, width, height, red, green, blue, 0.25F, red, green, blue, 1F, 0.45F, entity);
    }

    public static boolean isCorrect(EntityLivingBase entity) {
        if (player.getValue()) {
            if (entity instanceof EntityPlayer && entity != Minecraft.getMinecraft().thePlayer) {
                return true;
            }
        }

        if (mob.getValue()) {
            if (entity instanceof EntityLivingBase && !(entity instanceof EntityPlayer)) {
                return true;
            }
        }

        return false;
    }

    public static void drawEntityESP(double x, double y, double z, double width, double height, float red, float green, float blue, float alpha, float lineRed, float lineGreen, float lineBlue, float lineAlpha, float lineWdith, EntityLivingBase entity) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GlStateManager.translate(x, y, z);
        GlStateManager.rotate(-entity.rotationYaw, 0.0F, entity.height, 0.0F);
        GlStateManager.translate(-x, -y, -z);
        GL11.glColor4f(red, green, blue, 0.3001f);
        double eheight = (entity.getHealth() / 10);
        double mheight = (entity.getMaxHealth() / 10);
        if(entity instanceof EntityPlayer){
            FilledESP(new AxisAlignedBB(x - width + 0.20, y, z - width + 0.20, x + width - 0.20, y + eheight, z + width - 0.20));
        }else{
            FilledESP(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
        }
        GL11.glLineWidth(0.8F);
        GL11.glColor4f(red, green, blue, 1F);
        if(entity instanceof EntityPlayer){
            ProtocolEntityBox(new AxisAlignedBB(x - width + 0.20, y, z - width + 0.20, x + width - 0.20, y + mheight, z + width - 0.20));
        }else{
            ProtocolEntityBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
        }
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


    private void setupRender(boolean start) {
        boolean smooth = this.smoothLines.getValue();
        if (start) {
            if (smooth) {
                GLUtil.startSmooth();
            } else {
                GL11.glDisable(2848);
            }

            GL11.glDisable(2929);
            GL11.glDisable(3553);
        } else {
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            if (smooth) {
                GLUtil.endSmooth();
            }
        }

        GL11.glDepthMask(!start);
    }

    private boolean contain(Object o) {
        return !mc.theWorld.playerEntities.contains((EntityPlayer) o);
    }
}
