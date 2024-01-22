package com.teamti.timc.hud;

import com.teamti.timc.event.EventTarget;
import com.teamti.timc.event.events.EventJoinServer;
import com.teamti.timc.music.image.TextureImage;

import net.minecraft.client.Minecraft;

public class NotificationManager {

	
	public static void notify(String text, String secondText, int time){
		
		if(TIMCHud.notification == null){
			TIMCHud.notification = new Notification(text, secondText, time);
		}else{
			if(TIMCHud.notification.isDone()){
			TIMCHud.notification = new Notification(text, secondText, time);
		}
			
	}
	}
	
	public static void notify(String text, String secondText, int time, TextureImage t){
		
		if(TIMCHud.notification == null){
			TIMCHud.notification = new Notification(text, secondText, time, t);
		}else{
			if(TIMCHud.notification.isDone()){
			TIMCHud.notification = new Notification(text, secondText, time, t);
		}
			
	}
	}
	
	@EventTarget
	public void onJoin(EventJoinServer e){
		String server = Minecraft.getMinecraft().isSingleplayer() == true ? "":Minecraft.getMinecraft().getCurrentServerData().serverIP;


	}
	
}
