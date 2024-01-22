package com.teamti.timc.modules;


import com.teamti.timc.event.EventManager;
import com.teamti.timc.event.EventTarget;
import com.teamti.timc.event.events.EventMotion;
import com.teamti.timc.main.Module;
import com.teamti.timc.main.TIMC;
import com.teamti.timc.util.BooleanValue;
import com.teamti.timc.util.InventoryUtil;
import com.teamti.timc.util.NumberValue;
import com.teamti.timc.util.TimerUtil;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;

public class AutoArmor extends Module {


    private final TimerUtil timer = new TimerUtil();
    public static boolean isEnabled;

    public NumberValue delay;
    public BooleanValue invOnly;
    public BooleanValue onlyWhileNotMoving;


    public AutoArmor() {
        super("AutoArmor", Keyboard.KEY_NONE);
        this.jelloCat = TIMC.tabgui.cats.get(0);
        delay = new NumberValue("Delay", 120, 0, 300, 10);
        addValue(delay);
        invOnly = new BooleanValue("Inventory Only", true);
        addValue(invOnly);
        onlyWhileNotMoving = new BooleanValue("Only while not moving", true);
        addValue(onlyWhileNotMoving);
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
        if ((invOnly.isEnabled() && !(mc.currentScreen instanceof GuiInventory)) || (onlyWhileNotMoving.isEnabled() && mc.thePlayer.isMoving())) {
            return;
        }
        if (mc.thePlayer.openContainer instanceof ContainerChest) {
            // so it doesn't put on armor immediately after closing a chest
            timer.reset();
        }
        if (timer.hasTimeElapsed((long) delay.getValue(), false)) {
            for (int armorSlot = 5; armorSlot < 9; armorSlot++) {
                if (equipBest(armorSlot)) {
                    timer.reset();
                    break;
                }
            }
        }
    };

    private boolean equipBest(int armorSlot) {
        int equipSlot = -1, currProt = -1;
        ItemArmor currItem = null;
        ItemStack slotStack = mc.thePlayer.inventoryContainer.getSlot(armorSlot).getStack();
        if (slotStack != null && slotStack.getItem() instanceof ItemArmor) {
            currItem = (ItemArmor) slotStack.getItem();
            currProt = currItem.damageReduceAmount
                    + EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180310_c.effectId, mc.thePlayer.inventoryContainer.getSlot(armorSlot).getStack());
        }
        // find best piece
        for (int i = 9; i < 45; i++) {
            ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (is != null && is.getItem() instanceof ItemArmor) {
                int prot = ((ItemArmor) is.getItem()).damageReduceAmount + EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180310_c.effectId, is);
                if ((currItem == null || currProt < prot) && isValidPiece(armorSlot, (ItemArmor) is.getItem())) {
                    currItem = (ItemArmor) is.getItem();
                    equipSlot = i;
                    currProt = prot;
                }
            }
        }
        // equip best piece (if there is a better one)
        if (equipSlot != -1) {
            if (slotStack != null) {
                InventoryUtil.drop(armorSlot);
            } else {
                InventoryUtil.click(equipSlot, 0, true);
            }
            return true;
        }
        return false;
    }

    private boolean isValidPiece(int armorSlot, ItemArmor item) {
        String unlocalizedName = item.getUnlocalizedName();
        return armorSlot == 5 && unlocalizedName.startsWith("item.helmet")
                || armorSlot == 6 && unlocalizedName.startsWith("item.chestplate")
                || armorSlot == 7 && unlocalizedName.startsWith("item.leggings")
                || armorSlot == 8 && unlocalizedName.startsWith("item.boots");
    }

}
