package com.teamti.timc.modules;

import com.google.common.collect.Lists;
import com.teamti.timc.event.EventManager;
import com.teamti.timc.event.EventTarget;
import com.teamti.timc.event.events.EventMotion;
import com.teamti.timc.main.Module;
import com.teamti.timc.main.TIMC;
import com.teamti.timc.util.BooleanValue;
import com.teamti.timc.util.NumberValue;
import com.teamti.timc.util.TimerUtil;
import net.minecraft.inventory.ContainerChest;
import org.lwjgl.input.Keyboard;

import java.util.Collections;
import java.util.List;

public class ChestStealer extends Module {

	private final TimerUtil timer = new TimerUtil();
	public static boolean isEnabled;

	public NumberValue delay;
	public BooleanValue titleCheck;
	public BooleanValue reverse;



	public ChestStealer() {
		super("ChestStealer", Keyboard.KEY_V);
		this.jelloCat = TIMC.tabgui.cats.get(0);
		delay = new NumberValue("Delay", 80, 0, 300, 10);
		addValue(delay);
		titleCheck = new BooleanValue("Title Check", true);
		addValue(titleCheck);
		reverse = new BooleanValue("Reverse", false);
		addValue(reverse);
	}

	public void onEnable() {
		isEnabled = true;
		EventManager.register(this);
	}

	public void onDisable() {
		EventManager.unregister(this);
		isEnabled = false;
	}

	@EventTarget
	public void onEvent(EventMotion e) {
		if (mc.thePlayer.openContainer instanceof ContainerChest) {
			ContainerChest chest = (ContainerChest) mc.thePlayer.openContainer;
			String chestName = chest.getLowerChestInventory().getName();
			if (titleCheck.isEnabled() && !((chestName.contains("Chest") && !chestName.equals("Ender Chest")) || chestName.equals("LOW")))
				return;

			List<Integer> slots = Lists.newArrayList();
			for (int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); i++) {
				if (chest.getLowerChestInventory().getStackInSlot(i) != null) {
					slots.add(i);
				}
			}

			if (reverse.isEnabled()) Collections.reverse(slots);

			for (int slot : slots) {
				if (delay.getValue() == 0 || timer.hasTimeElapsed( (long) delay.getValue(), true)) {
					mc.playerController.windowClick(chest.windowId, slot, 0, 1, mc.thePlayer);
				}
			}

			if (slots.isEmpty() || this.isInventoryFull()) {
				mc.thePlayer.closeScreen();
			}
		}
	}

	private boolean isInventoryFull() {
		for (int i = 9; i < 45; i++) {
			if (mc.thePlayer.inventoryContainer.getSlot(i).getStack() == null) {
				return false;
			}
		}
		return true;
	}


}
