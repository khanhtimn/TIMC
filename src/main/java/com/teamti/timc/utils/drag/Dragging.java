package com.teamti.timc.utils.drag;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.teamti.timc.module.Module;
import com.teamti.timc.utils.Utils;
import com.teamti.timc.utils.animations.Animation;
import com.teamti.timc.utils.animations.Direction;
import com.teamti.timc.utils.animations.impl.DecelerateAnimation;
import com.teamti.timc.utils.render.ColorUtil;
import com.teamti.timc.utils.render.RoundedUtil;


import java.awt.*;

public class Dragging implements Utils {
    @Expose
    @SerializedName("x")
    private float xPos;
    @Expose
    @SerializedName("y")
    private float yPos;

    public float initialXVal;
    public float initialYVal;

    private float startX, startY;
    private boolean dragging;

    private float width, height;

    @Expose
    @SerializedName("name")
    private String name;

    private final Module module;

    public Animation hoverAnimation = new DecelerateAnimation(250, 1, Direction.BACKWARDS);

    public Dragging(Module module, String name, float initialXVal, float initialYVal) {
        this.module = module;
        this.name = name;
        this.xPos = initialXVal;
        this.yPos = initialYVal;
        this.initialXVal = initialXVal;
        this.initialYVal = initialYVal;
    }

    public Module getModule() {
        return module;
    }
    public String getName() {
        return name;
    }
    public float getWidth() {
        return width;
    }
    public void setWidth(float width) {
        this.width = width;
    }
    public float getHeight() {
        return height;
    }
    public void setHeight(float height) {
        this.height = height;
    }
    public float getX() {
        return xPos;
    }
    public void setX(float x) {
        this.xPos = x;
    }
    public float getY() {
        return yPos;
    }

    public void setY(float y) {
        this.yPos = y;
    }


    private String longestModule;

    public final void onDraw(int mouseX, int mouseY) {
        boolean hovering = Utils.isHovering(xPos, yPos, width, height, mouseX, mouseY);
        if (dragging) {
            xPos = mouseX - startX;
            yPos = mouseY - startY;
        }
        hoverAnimation.setDirection(hovering ? Direction.FORWARDS : Direction.BACKWARDS);
        if (!hoverAnimation.isDone() || hoverAnimation.finished(Direction.FORWARDS)) {
            RoundedUtil.drawRoundOutline(xPos - 4, yPos - 4, width + 8, height + 8, 10, 2,
                    ColorUtil.applyOpacity(Color.WHITE, 0), ColorUtil.applyOpacity(Color.WHITE, (float) hoverAnimation.getOutput().floatValue()));
        }
    }

//    public final void onDrawArraylist(ArrayListMod arraylistMod, int mouseX, int mouseY) {
//        ScaledResolution sr = new ScaledResolution(mc);
//
//        List<Module> modules = Tenacity.INSTANCE.getModuleCollection().getArraylistModules(arraylistMod, arraylistMod.modules);
//
//        String longest = getLongestModule(arraylistMod);
//
//        width = (float) MathUtils.roundToHalf(arraylistMod.font.getStringWidth(longest) + 5);
//        height = (float) MathUtils.roundToHalf((arraylistMod.height.getValue() + 1) * modules.size());
//
//        float textVal = (float) arraylistMod.font.getStringWidth(longest);
//        float xVal = sr.getScaledWidth() - (textVal + 8 + xPos);
//
//        if (sr.getScaledWidth() - xPos <= sr.getScaledWidth() / 2f) {
//            xVal += textVal - 2;
//        }
//
//        boolean hovering = HoveringUtil.isHovering(xVal, yPos - 8, width + 20, height + 16, mouseX, mouseY);
//
//        if (dragging) {
//            xPos = -(mouseX - startX);
//            yPos = mouseY - startY;
//        }
//        hoverAnimation.setDirection(hovering ? Direction.FORWARDS : Direction.BACKWARDS);
//
//        if (!hoverAnimation.isDone() || hoverAnimation.finished(Direction.FORWARDS)) {
//            RoundedUtil.drawRoundOutline(xVal, yPos - 8, width + 20, height + 16, 10, 2,
//                    ColorUtil.applyOpacity(Color.BLACK, (float) (0f * hoverAnimation.getOutput().floatValue())), ColorUtil.applyOpacity(Color.WHITE, (float) hoverAnimation.getOutput().floatValue()));
//        }
//
//    }

    public final void onClick(int mouseX, int mouseY, int button) {
        boolean canDrag = Utils.isHovering(xPos, yPos, width, height, mouseX, mouseY);
        if (button == 0 && canDrag) {
            dragging = true;
            startX = (int) (mouseX - xPos);
            startY = (int) (mouseY - yPos);
        }
    }

//    public final void onClickArraylist(ArrayListMod arraylistMod, int mouseX, int mouseY, int button) {
//        ScaledResolution sr = new ScaledResolution(mc);
//
//        String longest = getLongestModule(arraylistMod);
//
//        float textVal = (float) arraylistMod.font.getStringWidth(longest);
//        float xVal = sr.getScaledWidth() - (textVal + 8 + xPos);
//
//        if (sr.getScaledWidth() - xPos <= sr.getScaledWidth() / 2f) {
//            xVal += textVal - 2;
//        }
//
//        boolean canDrag = HoveringUtil.isHovering(xVal, yPos - 8, width + 20, height + 16, mouseX, mouseY);
//
//        if (button == 0 && canDrag) {
//            dragging = true;
//            startX = (int) (mouseX + xPos);
//            startY = (int) (mouseY - yPos);
//        }
//    }

    public final void onRelease(int button) {
        if (button == 0) dragging = false;
    }


//    private String getLongestModule(ArrayListMod arraylistMod) {
//        return arraylistMod.longest;
//    }


}
