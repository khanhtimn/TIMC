package com.teamti.timc.util;

import com.teamti.timc.main.TIMC;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.*;

public class InventoryUtil {
    public boolean inventoryIsFull() {
        int i = 9;
        while (i < 45) {
            ItemStack stack = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getStack();
            if (stack == null) {
                return false;
            }
            ++i;
        }
        return true;
    }
    public boolean hotbarHasSpace() {
        int i = 0;
        while (i < 9) {
            if (TIMC.core.mc.thePlayer.inventory.getStackInSlot(i) == null) {
                return true;
            }
            ++i;
        }
        return false;
    }

    public boolean inventoryHasPotion() {
        int i = 9;
        while (i <= 35) {
            ItemStack stack = TIMC.core.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (stack != null && stack.getItem() instanceof ItemPotion) {
                return true;
            }
            ++i;
        }
        return false;
    }
    public boolean inventoryHasRod() {
        int i = 9;
        while (i <= 35) {
            ItemStack stack = TIMC.core.player().inventoryContainer.getSlot(i).getStack();
            if (stack != null && stack.getItem() instanceof ItemFishingRod) {
                return true;
            }
            ++i;
        }
        return false;
    }

    public boolean hotbarHasFood() {
        int i = 0;
        while (i <= 9) {
            ItemStack stack = TIMC.core.player().inventory.getStackInSlot(i);
            if (stack != null && stack.getItem() instanceof ItemFood) {
                return true;
            }
            ++i;
        }
        return false;
    }
    
    public boolean hotbarHasWater() {
        int i = 0;
        while (i <= 9) {
            ItemStack stack = TIMC.core.player().inventory.getStackInSlot(i);
            if (stack != null && stack.getItem() instanceof ItemBucket) {
                return true;
            }
            ++i;
        }
        return false;
    }

    public int potionInventorySlot() {
        int i = 9;
        while (i <= 35) {
            ItemStack stack = TIMC.core.player().inventoryContainer.getSlot(i).getStack();
            if (stack != null && stack.getItem() instanceof ItemPotion) {
                return i;
            }
            ++i;
        }
        return -999;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int logHotbarSlot() {
        int i = 36;
        while (i <= 44) {
            ItemStack stack = TIMC.core.player().inventoryContainer.getSlot(i).getStack();
            if (stack != null) {
                stack.getItem();
                if (Item.getIdFromItem(stack.getItem()) == 17) return i;
                stack.getItem();
                if (Item.getIdFromItem(stack.getItem()) == 162) {
                    return i;
                }
            }
            ++i;
        }
        return -999;
    }

    public int foodHotbarSlot() {
        int i = 0;
        while (i <= 9) {
            ItemStack stack = TIMC.core.player().inventory.getStackInSlot(i);
            if (stack != null && stack.getItem() instanceof ItemFood) {
                return i;
            }
            ++i;
        }
        return -999;
    }
    public int waterHotbarSlot() {
        int i = 0;
        while (i <= 9) {
            ItemStack stack = TIMC.core.player().inventory.getStackInSlot(i);
            if (stack != null && stack.getItem() instanceof ItemBucket) {
                return i;
            }
            ++i;
        }
        return -999;
    }
    
    public int pearlHotbarSlot() {
        int i = 0;
        while (i <= 9) {
            ItemStack stack = TIMC.core.player().inventory.getStackInSlot(i);
            if (stack != null && stack.getItem() instanceof ItemEnderPearl) {
                return i;
            }
            ++i;
        }
        return -999;
    }

    public void switchToSlot(int desiredSlot) {
        TIMC.core.player().inventory.currentItem = desiredSlot;
    }
    public static int getBestHotbarSword() {
        float best = -1.0F;

        int index = -1;



        for(int i = 0; i < 9; ++i) {

           ItemStack itemStack = Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i);

           if(itemStack != null && itemStack.getItem() instanceof ItemSword) {

              ItemSword sword = (ItemSword)itemStack.getItem();

              float damage = sword.getMaxDamage() + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180314_l.effectId, itemStack) * 1.25F;

              if(damage > best) {

                 best = damage;

                 index = i;

              }

           }

        }



        return index;

     }

    public static float getDamageLevel(ItemStack stack) {
        if (stack.getItem() instanceof ItemSword) {
            ItemSword sword = (ItemSword)stack.getItem();
            float sharpness = (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180314_l.effectId, stack) * 1.25F;
            float fireAspect = (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack) * 1.5F;
            return sword.func_150931_i() + sharpness + fireAspect;
        } else {
            return 0.0F;
        }
    }

    public static int getBestTotalSword() {
        float best = -1.0F;

        int index = -1;

        for(int i = 0; i < 45; ++i) {

           ItemStack itemStack = Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i);

           if(itemStack != null && itemStack.getItem() instanceof ItemSword) {

              ItemSword sword = (ItemSword)itemStack.getItem();

              float sharpness = sword.getMaxDamage() + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180314_l.effectId, itemStack) * 1.25F;
              float fireAspect = (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, itemStack) * 1.5F;

              float total = sharpness + fireAspect;

              if(total > best) {

                 best = total;

                 index = i;

              }

           }

        }

        return index;

     }

     public static int getBestTool(Block block) {
        float best = -1.0F;

        int index = -1;



        for(int i = 0; i < 9; ++i) {

           ItemStack itemStack = Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i);

           if(itemStack != null) {

              float str = itemStack.getItem().getStrVsBlock(itemStack, block);

              if(str > best) {

                 best = str;

                 index = i;

              }

           }

        }



        return index;

     }

    public static void click(int slot, int mouseButton, boolean shiftClick) {
        Minecraft.getMinecraft().playerController.windowClick(Minecraft.getMinecraft().thePlayer.inventoryContainer.windowId, slot, mouseButton, shiftClick ? 1 : 0, Minecraft.getMinecraft().thePlayer);
    }

    public static void drop(int slot) {
        Minecraft.getMinecraft().playerController.windowClick(0, slot, 1, 4, Minecraft.getMinecraft().thePlayer);
    }

    public static void swap(int slot, int hSlot) {
        Minecraft.getMinecraft().playerController.windowClick(Minecraft.getMinecraft().thePlayer.inventoryContainer.windowId, slot, hSlot, 2, Minecraft.getMinecraft().thePlayer);
    }
}
