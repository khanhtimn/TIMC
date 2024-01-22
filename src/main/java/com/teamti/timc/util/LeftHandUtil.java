package com.teamti.timc.util;

import com.teamti.timc.modules.LeftBow;
import com.teamti.timc.modules.LeftHand;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;

public class LeftHandUtil
{
    public static boolean use(Entity entity)
    {
        boolean flag = LeftHand.isEnabled;

        if (entity == null)
        {
            return flag;
        }
        else if (!LeftBow.isEnabled)
        {
            return flag;
        }
        else if (!(entity instanceof AbstractClientPlayer))
        {
            return flag;
        }
        else
        {
            AbstractClientPlayer abstractclientplayer = (AbstractClientPlayer)entity;
            return abstractclientplayer.inventory == null ? flag : (abstractclientplayer.inventory.getCurrentItem() == null ? flag : (abstractclientplayer.inventory.getCurrentItem().getItem() == null ? flag : (abstractclientplayer.inventory.getCurrentItem().getItem() instanceof ItemBow ? !flag : flag)));
        }
    }

    public static boolean use(ItemStack itemToRender)
    {
        boolean flag = LeftHand.isEnabled;
        return itemToRender != null && itemToRender.getItem() != null && itemToRender.getItem() instanceof ItemMap ? false : (!LeftBow.isEnabled ? flag : (itemToRender == null ? flag : (itemToRender.getItem() == null ? flag : (itemToRender.getItem() instanceof ItemBow ? !flag : flag))));
    }
}
