package com.teamti.timc.modules;


import com.teamti.timc.event.EventManager;
import com.teamti.timc.event.EventTarget;
import com.teamti.timc.event.events.EventMotion;
import com.teamti.timc.event.events.EventPacketSent;
import com.teamti.timc.main.Module;
import com.teamti.timc.main.TIMC;
import com.teamti.timc.util.BooleanValue;
import com.teamti.timc.util.InventoryUtil;
import com.teamti.timc.util.NumberValue;
import com.teamti.timc.util.TimerUtil;
import net.minecraft.block.*;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.*;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;

// TODO: recode shit parts
public class InvCleaner extends Module {

	public NumberValue delay;
	public NumberValue slotWeapon;
	public NumberValue slotPick;
	public NumberValue slotAxe;
	public NumberValue slotShovel;
	public NumberValue slotBow;
	public NumberValue slotBlock;
	public BooleanValue inventoryPackets;
	public BooleanValue onlyWhileNotMoving;
	public BooleanValue inventoryOnly;
	public BooleanValue dropArchery;
	public BooleanValue moveArrows;
	public BooleanValue dropFood;

	public static boolean isEnabled;

	private final String[] shitItems = {"tnt", "stick", "egg", "string", "cake", "mushroom", "flint", "compass", "dyePowder", "feather", "bucket", "chest", "snow", "fish", "enchant", "exp", "shears", "anvil", "torch", "seeds", "leather", "reeds", "skull", "record", "snowball", "piston"};
	private final String[] serverItems = {"selector", "tracking compass", "(right click)", "tienda ", "perfil", "salir", "shop", "collectibles", "game", "profil", "lobby", "show all", "hub", "friends only", "cofre", "(click", "teleport", "play", "exit", "hide all", "jeux", "gadget", " (activ", "emote", "amis", "bountique", "choisir", "choose "};

	private final TimerUtil timer = new TimerUtil();
	private boolean isInvOpen;

	public InvCleaner() {
		super("InvCleaner", Keyboard.KEY_NONE);
		this.jelloCat = TIMC.tabgui.cats.get(0);
		delay = new NumberValue("Delay", 120, 0, 300, 10);
		addValue(delay);
		slotWeapon = new NumberValue("Weapon Slot", 1, 1, 9, 1);
		addValue(slotWeapon);
		slotPick = new NumberValue("Pickaxe Slot", 2, 1, 9, 1);
		addValue(slotPick);
		slotAxe = new NumberValue("Axe Slot", 3, 1, 9, 1);
		addValue(slotAxe);
		slotShovel = new NumberValue("Shovel Slot", 4, 1, 9, 1);
		addValue(slotShovel);
		slotBow = new NumberValue("Bow Slot", 5, 1, 9, 1);
		addValue(slotBow);
		slotBlock = new NumberValue("Block Slot", 6, 1, 9, 1);
		addValue(slotBlock);
		inventoryOnly = new BooleanValue("Inventory Only", true);
		addValue(inventoryOnly);
		onlyWhileNotMoving = new BooleanValue("Only while not moving", true);
		addValue(onlyWhileNotMoving);
		inventoryPackets = new BooleanValue("Send inventory packets", true);
		addValue(inventoryPackets);
		dropArchery = new BooleanValue("Drop archery", false);
		addValue(dropArchery);
		moveArrows = new BooleanValue("Move arrows", true);
		addValue(moveArrows);
		dropFood = new BooleanValue("Drop food", false);
		addValue(dropFood);

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
			if (stop()) return;
			if (!mc.thePlayer.isUsingItem() && (mc.currentScreen == null || mc.currentScreen instanceof GuiChat || mc.currentScreen instanceof GuiInventory || mc.currentScreen instanceof GuiIngameMenu)) {
				long delay = (long) this.delay.getValue();
				if (timer.hasTimeElapsed(delay, false)) {
					Slot slot = mc.thePlayer.inventoryContainer.getSlot(getDesiredSlot(ItemType.WEAPON));
					if (!slot.getHasStack() || !isBestWeapon(slot.getStack())) {
						getBestWeapon();
					}
				}
				if (timer.hasTimeElapsed(delay, false)) getBestPickaxe();
				if (timer.hasTimeElapsed(delay, false)) getBestAxe();
				if (timer.hasTimeElapsed(delay, false)) getBestShovel();
				if (timer.hasTimeElapsed(delay, false)) {
					for (int i = 9; i < 45; i++) {
						if (stop()) return;
						Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);
						ItemStack is = slot.getStack();
						if (is != null && shouldDrop(is, i)) {
							InventoryUtil.drop(i);
							timer.reset();
							break;
						}
					}
				}
				if (timer.hasTimeElapsed(delay, false)) swapBlocks();
				if (timer.hasTimeElapsed(delay, false)) getBestBow();
				if (timer.hasTimeElapsed(delay, false)) moveArrows();
			}
	}
	@EventTarget
	public void onEvent(EventPacketSent e) {
		if (isInvOpen) {
			Packet<?> packet = e.getPacket();
			if ((packet instanceof C16PacketClientStatus && ((C16PacketClientStatus) packet).getStatus() == C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT)
					|| packet instanceof C0DPacketCloseWindow) {
				e.setCancelled(true);
			} else if (packet instanceof C02PacketUseEntity) {
				fakeClose();
			}
		}
	};

	public static float getDamageScore(ItemStack stack) {
		if (stack == null || stack.getItem() == null) return 0;

		float damage = 0;
		Item item = stack.getItem();

		if (item instanceof ItemSword) {
			damage += ((ItemSword) item).func_150931_i();
		} else if (item instanceof ItemTool) {
			damage += item.getMaxDamage();
		}

		damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180314_l.effectId, stack) * 1.25F +
				EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack) * 0.1F;

		return damage;
	}

	public static float getProtScore(ItemStack stack) {
		float prot = 0;
		if (stack.getItem() instanceof ItemArmor) {
			ItemArmor armor = (ItemArmor) stack.getItem();
			prot += armor.damageReduceAmount
					+ ((100 - armor.damageReduceAmount) * EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180310_c.effectId, stack)) * 0.0075F;
			prot += EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, stack) / 100F;
			prot += EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, stack) / 100F;
			prot += EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack) / 100F;
			prot += EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack) / 25.F;
			prot += EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180309_e.effectId, stack) / 100F;
		}
		return prot;
	}

	private int getDesiredSlot(ItemType tool) {
		int slot = 36;
		switch (tool) {
			case WEAPON:
				slot =  ((int) slotWeapon.getValue());
				break;
			case PICKAXE:
				slot =  ((int) slotPick.getValue());
				break;
			case AXE:
				slot =  ((int) slotAxe.getValue());
				break;
			case SHOVEL:
				slot =  ((int) slotShovel.getValue());
				break;
			case BLOCK:
				slot =  ((int) slotBlock.getValue());
				break;
			case BOW:
				slot =  ((int) slotBow.getValue());
				break;
		}
		return slot + 35;
	}

	private boolean isBestWeapon(ItemStack is) {
		float damage = getDamageScore(is);
		for (int i = 9; i < 45; i++) {
			Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);
			if (slot.getHasStack()) {
				ItemStack is2 = slot.getStack();
				if (getDamageScore(is2) > damage && is2.getItem() instanceof ItemSword) {
					return false;
				}
			}
		}
		return is.getItem() instanceof ItemSword;
	}

	private void getBestWeapon() {
		for (int i = 9; i < 45; i++) {
			if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
				ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
				if (isBestWeapon(is) && getDamageScore(is) > 0 && is.getItem() instanceof ItemSword) {
					swap(i, getDesiredSlot(ItemType.WEAPON) - 36);
					break;
				}
			}
		}
	}
	public static boolean isValidBlock(BlockPos pos) {
		return isValidBlock(mc.theWorld.getBlockState(pos).getBlock(), false);
	}

	public static boolean isValidBlock(Block block, boolean placing) {
		if (block instanceof BlockCarpet
				|| block instanceof BlockSnow
				|| block instanceof BlockContainer
				|| block instanceof BlockBasePressurePlate
				|| block.getMaterial().isLiquid()) {
			return false;
		}
		if (placing && (block instanceof BlockSlab
				|| block instanceof BlockStairs
				|| block instanceof BlockLadder
				|| block instanceof BlockStainedGlassPane
				|| block instanceof BlockWall
				|| block instanceof BlockWeb
				|| block instanceof BlockCactus
				|| block instanceof BlockFalling
				|| block == Blocks.glass_pane
				|| block == Blocks.iron_bars)) {
			return false;
		}
		return (block.getMaterial().isSolid() || !block.isTranslucent() || block.isFullBlock());
	}
	private boolean shouldDrop(ItemStack stack, int slot) {
		String stackName = stack.getDisplayName().toLowerCase();
		Item item = stack.getItem();
		String ulName = item.getUnlocalizedName();
		if (Arrays.stream(serverItems).anyMatch(stackName::contains)) return false;

		if (item instanceof ItemBlock) {
			return !isValidBlock(((ItemBlock) item).getBlock(), true);
		}

		int weaponSlot = getDesiredSlot(ItemType.WEAPON);
		int pickaxeSlot = getDesiredSlot(ItemType.PICKAXE);
		int axeSlot = getDesiredSlot(ItemType.AXE);
		int shovelSlot = getDesiredSlot(ItemType.SHOVEL);

		if ((slot != weaponSlot || !isBestWeapon(mc.thePlayer.inventoryContainer.getSlot(weaponSlot).getStack()))
				&& (slot != pickaxeSlot || !isBestPickaxe(mc.thePlayer.inventoryContainer.getSlot(pickaxeSlot).getStack()))
				&& (slot != axeSlot || !isBestAxe(mc.thePlayer.inventoryContainer.getSlot(axeSlot).getStack()))
				&& (slot != shovelSlot || !isBestShovel(mc.thePlayer.inventoryContainer.getSlot(shovelSlot).getStack()))) {
			if (item instanceof ItemArmor) {
				for (int type = 1; type < 5; type++) {
					if (mc.thePlayer.inventoryContainer.getSlot(4 + type).getHasStack()) {
						ItemStack is = mc.thePlayer.inventoryContainer.getSlot(4 + type).getStack();
						if (isBestArmor(is, type)) {
							continue;
						}
					}
					if (isBestArmor(stack, type)) {
						return false;
					}
				}
			}

			if ((item == Items.wheat) || item == Items.spawn_egg
					|| (item instanceof ItemFood && dropFood.isEnabled() && !(item instanceof ItemAppleGold))) {
				return true;
			} else if (!(item instanceof ItemSword) && !(item instanceof ItemTool) && !(item instanceof ItemHoe) && !(item instanceof ItemArmor)) {
				if (dropArchery.isEnabled() && (item instanceof ItemBow || item == Items.arrow)) {
					return true;
				} else {
					return item instanceof ItemGlassBottle || Arrays.stream(shitItems).anyMatch(ulName::contains);
				}
			}
			return true;
		}

		return false;
	}

	private void getBestPickaxe() {
		for (int i = 9; i < 45; ++i) {
			Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);
			if (slot.getHasStack()) {
				ItemStack is = slot.getStack();
				if (isBestPickaxe(is) && !isBestWeapon(is)) {
					int desiredSlot = getDesiredSlot(ItemType.PICKAXE);
					if (i == desiredSlot) return;
					Slot slot2 = mc.thePlayer.inventoryContainer.getSlot(desiredSlot);
					if (!slot2.getHasStack() || !isBestPickaxe(slot2.getStack())) {
						swap(i, desiredSlot - 36);
					}
				}
			}
		}
	}

	private void getBestAxe() {
		for (int i = 9; i < 45; i++) {
			Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);
			if (slot.getHasStack()) {
				ItemStack is = slot.getStack();
				if (isBestAxe(is) && !isBestWeapon(is)) {
					int desiredSlot = getDesiredSlot(ItemType.AXE);
					if (i == desiredSlot) return;
					Slot slot2 = mc.thePlayer.inventoryContainer.getSlot(desiredSlot);
					if (!slot2.getHasStack() || !isBestAxe(slot2.getStack())) {
						swap(i, desiredSlot - 36);
						timer.reset();
					}
				}
			}
		}
	}

	private void getBestShovel() {
		for (int i = 9; i < 45; i++) {
			Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);
			if (slot.getHasStack()) {
				ItemStack is = slot.getStack();
				if (isBestShovel(is) && !isBestWeapon(is)) {
					int desiredSlot = getDesiredSlot(ItemType.SHOVEL);
					if (i == desiredSlot) return;
					Slot slot2 = mc.thePlayer.inventoryContainer.getSlot(desiredSlot);
					if (!slot2.getHasStack() || !isBestShovel(slot2.getStack())) {
						swap(i, desiredSlot - 36);
						timer.reset();
					}
				}
			}
		}
	}

	private void getBestBow() {
		for (int i = 9; i < 45; i++) {
			Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);
			if (slot.getHasStack()) {
				ItemStack is = slot.getStack();
				String stackName = is.getDisplayName().toLowerCase();
				if (Arrays.stream(serverItems).anyMatch(stackName::contains) || !(is.getItem() instanceof ItemBow))
					continue;
				if (isBestBow(is) && !isBestWeapon(is)) {
					int desiredSlot = getDesiredSlot(ItemType.BOW);
					if (i == desiredSlot) return;
					Slot slot2 = mc.thePlayer.inventoryContainer.getSlot(desiredSlot);
					if (!slot2.getHasStack() || !isBestBow(slot2.getStack())) {
						swap(i, desiredSlot - 36);
					}
				}
			}
		}
	}

	private void moveArrows() {
		if (dropArchery.isEnabled() || !moveArrows.isEnabled()) return;
		for (int i = 36; i < 45; i++) {
			ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
			if (is != null && is.getItem() == Items.arrow) {
				for (int j = 0; j < 36; j++) {
					if (mc.thePlayer.inventoryContainer.getSlot(j).getStack() == null) {
						fakeOpen();
						InventoryUtil.click(i, 0, true);
						fakeClose();
						timer.reset();
						break;
					}
				}
			}
		}
	}

	private int getMostBlocks() {
		int stack = 0;
		int biggestSlot = -1;
		for (int i = 9; i < 45; i++) {
			Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);
			ItemStack is = slot.getStack();
			if (is != null && is.getItem() instanceof ItemBlock && is.stackSize > stack && Arrays.stream(serverItems).noneMatch(is.getDisplayName().toLowerCase()::contains)) {
				stack = is.stackSize;
				biggestSlot = i;
			}
		}
		return biggestSlot;
	}

	private void swapBlocks() {
		int mostBlocksSlot = getMostBlocks();
		int desiredSlot = getDesiredSlot(ItemType.BLOCK);
		if (mostBlocksSlot != -1 && mostBlocksSlot != desiredSlot) {
			// only switch if the hotbar slot doesn't already have blocks of the same quantity to prevent an inf loop
			Slot dss = mc.thePlayer.inventoryContainer.getSlot(desiredSlot);
			ItemStack dsis = dss.getStack();
			if (!(dsis != null && dsis.getItem() instanceof ItemBlock && dsis.stackSize >= mc.thePlayer.inventoryContainer.getSlot(mostBlocksSlot).getStack().stackSize && Arrays.stream(serverItems).noneMatch(dsis.getDisplayName().toLowerCase()::contains))) {
				swap(mostBlocksSlot, desiredSlot - 36);
			}
		}
	}

	private boolean isBestPickaxe(ItemStack stack) {
		Item item = stack.getItem();
		if (!(item instanceof ItemPickaxe)) {
			return false;
		} else {
			float value = getToolScore(stack);
			for (int i = 9; i < 45; i++) {
				Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);
				if (slot.getHasStack()) {
					ItemStack is = slot.getStack();
					if (is.getItem() instanceof ItemPickaxe && getToolScore(is) > value) {
						return false;
					}
				}
			}
			return true;
		}
	}

	private boolean isBestShovel(ItemStack stack) {
		if (!(stack.getItem() instanceof ItemSpade)) {
			return false;
		} else {
			float score = getToolScore(stack);
			for (int i = 9; i < 45; i++) {
				Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);
				if (slot.getHasStack()) {
					ItemStack is = slot.getStack();
					if (is.getItem() instanceof ItemSpade && getToolScore(is) > score) {
						return false;
					}
				}
			}
			return true;
		}
	}

	private boolean isBestAxe(ItemStack stack) {
		if (!(stack.getItem() instanceof ItemAxe)) {
			return false;
		} else {
			float value = getToolScore(stack);
			for (int i = 9; i < 45; i++) {
				Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);
				if (slot.getHasStack()) {
					ItemStack is = slot.getStack();
					if (getToolScore(is) > value && is.getItem() instanceof ItemAxe && !isBestWeapon(stack)) {
						return false;
					}
				}
			}
			return true;
		}
	}

	private boolean isBestBow(ItemStack stack) {
		if (!(stack.getItem() instanceof ItemBow)) {
			return false;
		} else {
			float value = getPowerLevel(stack);
			for (int i = 9; i < 45; i++) {
				Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);
				if (slot.getHasStack()) {
					ItemStack is = slot.getStack();
					if (getPowerLevel(is) > value && is.getItem() instanceof ItemBow && !isBestWeapon(stack)) {
						return false;
					}
				}
			}
			return true;
		}
	}

	private float getPowerLevel(ItemStack stack) {
		float score = 0;
		Item item = stack.getItem();
		if (item instanceof ItemBow) {
			score += EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
			score += EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) * 0.5F;
			score += EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack) * 0.1F;
		}
		return score;
	}

	private float getToolScore(ItemStack stack) {
		float score = 0;
		Item item = stack.getItem();
		if (item instanceof ItemTool) {
			ItemTool tool = (ItemTool) item;
			String name = item.getUnlocalizedName().toLowerCase();
			if (item instanceof ItemPickaxe) {
				score = tool.getStrVsBlock(stack, Blocks.stone) - (name.contains("gold") ? 5 : 0);
			} else if (item instanceof ItemSpade) {
				score = tool.getStrVsBlock(stack, Blocks.dirt) - (name.contains("gold") ? 5 : 0);
			} else {
				if (!(item instanceof ItemAxe)) return 1;
				score = tool.getStrVsBlock(stack, Blocks.log) - (name.contains("gold") ? 5 : 0);
			}
			score += EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, stack) * 0.0075F;
			score += EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack) / 100F;
		}
		return score;
	}


	public boolean isBestArmor(ItemStack stack, int type) {
		String typeStr = "";
		switch (type) {
			case 1:
				typeStr = "helmet";
				break;
			case 2:
				typeStr = "chestplate";
				break;
			case 3:
				typeStr = "leggings";
				break;
			case 4:
				typeStr = "boots";
				break;
		}
		if (stack.getUnlocalizedName().contains(typeStr)) {
			float prot = getProtScore(stack);
			for (int i = 5; i < 45; i++) {
				Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);
				if (slot.getHasStack()) {
					ItemStack is = slot.getStack();
					if (is.getUnlocalizedName().contains(typeStr) && getProtScore(is) > prot) {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}

	private void fakeOpen() {
		if (!isInvOpen) {
			timer.reset();
			if (!inventoryOnly.isEnabled() && inventoryPackets.isEnabled())
				mc.getNetHandler().getNetworkManager().sendPacket(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
			isInvOpen = true;
		}
	}

	private void fakeClose() {
		if (isInvOpen) {
			if (!inventoryOnly.isEnabled() && inventoryPackets.isEnabled())
				mc.getNetHandler().getNetworkManager().sendPacket(new C0DPacketCloseWindow(mc.thePlayer.inventoryContainer.windowId));
			isInvOpen = false;
		}
	}

	private void swap(int slot, int hSlot) {
		fakeOpen();
		InventoryUtil.swap(slot, hSlot);
		fakeClose();
		timer.reset();
	}

	private boolean stop() {
		return (inventoryOnly.isEnabled() && !(mc.currentScreen instanceof GuiInventory)) || (onlyWhileNotMoving.isEnabled() && mc.thePlayer.isMoving());
	}

	private enum ItemType {
		WEAPON, PICKAXE, AXE, SHOVEL, BLOCK, BOW
	}

}
