package com.teamti.timc.discord;

public class DiscordMain {

    private static final DiscordMain INSTANCE = new DiscordMain();
     public static final DiscordMain getInstance() {
         return INSTANCE;
     }
     private DiscordRP discordRP = new DiscordRP();


    public void init() {
        discordRP.start();
    }

    public void shutdown() {
        discordRP.shutdown();
    }

    public DiscordRP getDiscordRP() {
        return discordRP;
    }
}
