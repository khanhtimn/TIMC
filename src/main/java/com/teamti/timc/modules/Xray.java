package com.teamti.timc.modules;

import com.teamti.timc.event.EventManager;
import com.teamti.timc.main.TIMC;
import com.teamti.timc.main.Module;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class Xray extends Module {

    public static ArrayList<Block> blocks	= new ArrayList<Block>();
    public static boolean isEnabled;

	public Xray() {
        super("Xray", Keyboard.KEY_NONE);
        this.jelloCat = TIMC.tabgui.cats.get(0);
        blocks.add(Block.getBlockById(15));
        blocks.add(Block.getBlockById(16));
        blocks.add(Block.getBlockById(2));
        blocks.add(Block.getBlockById(10));
        blocks.add(Block.getBlockById(11));
        blocks.add(Block.getBlockById(10));
        blocks.add(Block.getBlockById(48));
        blocks.add(Block.getBlockById(52));
        blocks.add(Block.getBlockById(56));
        blocks.add(Block.getBlockById(15));
        blocks.add(Block.getBlockById(14));
        blocks.add(Block.getBlockById(21));
        blocks.add(Block.getBlockById(89));
        blocks.add(Block.getBlockById(15));
        blocks.add(Block.getBlockById(124));
        blocks.add(Block.getBlockById(129));
        blocks.add(Block.getBlockById(41));
        blocks.add(Block.getBlockById(19));
        blocks.add(Block.getBlockById(42));
        blocks.add(Block.getBlockById(73));
        blocks.add(Block.getBlockById(74));
    }
    public void onEnable() {
        isEnabled = true;
        // Visgraph.java to make all sides visible
        Minecraft.getMinecraft().gameSettings.gammaSetting += 1000;
        Minecraft.getMinecraft().renderGlobal.loadRenderers();
        EventManager.register(this);
    }

    public void onDisable() {
        Minecraft.getMinecraft().gameSettings.gammaSetting -= 1000;
        Minecraft.getMinecraft().renderGlobal.loadRenderers();
        EventManager.unregister(this);
        isEnabled = false;
    }

    public static boolean shouldXray(Block block) {
        return blocks.contains(block);
    }
   
	
	
	
}
