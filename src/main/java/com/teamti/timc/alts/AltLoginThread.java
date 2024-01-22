package com.teamti.timc.alts;

import com.teamti.timc.main.TIMC;
import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class AltLoginThread extends Thread
{
    private final Minecraft mc;
    private final String password;
    private String status;
    private final String username;
    private File altsFile;
    public static String player;
    public Alt alt;
    
    public AltLoginThread(Alt alt) {
        super("Alt Login Thread");
        this.mc = Minecraft.getMinecraft();
        this.alt = alt;
        this.username = alt.email;
        this.password = alt.password;
        alt.start();
       
        this.status = "\2477Waiting...";
    }
    
//    private final Session createSession(final String username, final String password) {
//        final YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
//        final YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
//        auth.setUsername(username);
//        auth.setPassword(password);
//        try {
//            auth.logIn();
//            alt.callback(auth.getSelectedProfile());
//            return new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
//        }
//        catch (AuthenticationException e) {
//            return null;
//        }
//    }
    
    public String getStatus() {
        return this.status;
    }
    
    @Override
    public void run() {
//        if (this.password.equals("")) {
//            this.mc.session = new Session(this.username, "", "", "mojang");
//            this.status = "\247aLogged in. (" + this.username + " - offline name)";
//            GuiAltManager.pubStatus = "\247aLogged in. (" + this.username + " - offline name)";
//            return;
//        }

//        this.status = "\247eLogging in...";
//        final Session auth = this.createSession(this.username, this.password);
//        if (auth == null) {
//        	alt.fail();
//            this.status = "\247cLogin failed!";
//            GuiAltManager.pubStatus = "\247cLogin failed!";
//        }
//        else {
//        	TIMC.getAltManager().setLastAlt(new Alt(this.username, this.password));
//            this.status = "\247aLogged in. (" + auth.getUsername() + ")";
//            GuiAltManager.pubStatus = "\247aLogged in. (" + auth.getUsername() + ")";
//            player = GuiAltManager.currentEmail+" - "+auth.getUsername()+" \2476(Current)";
//            this.mc.session = auth;
//        }
        if (this.password.equals("")) {
            this.mc.session = new Session(this.username, "", "", "msa");
            this.status = "\247aLogged in. (" + this.username + " - offline name)";
            GuiAltManager.pubStatus = "\247aLogged in. (" + this.username + " - offline name)";
            return;
        }
        this.status = "\247eLogging in...";

        MicrosoftAuthenticator microsoftAuthenticator = new MicrosoftAuthenticator();
        try {
            MicrosoftAuthResult microsoftAuthResult = microsoftAuthenticator.loginWithCredentials(this.username, this.password);
            if (microsoftAuthResult == null) {
                alt.fail();
                this.status = "\247cLogin failed!";
                GuiAltManager.pubStatus = "\247cLogin failed!";
            } else {
                TIMC.getAltManager().setLastAlt(new Alt(this.username, this.password));
                this.status = "\247aLogged in. (" + microsoftAuthResult.getProfile().getName() + ")";
                GuiAltManager.pubStatus = "\247aLogged in. (" + microsoftAuthResult.getProfile().getName() + ")";
                player = GuiAltManager.currentEmail+" - "+ microsoftAuthResult.getProfile().getName() +" \2476(Current)";
                this.mc.session = new Session(microsoftAuthResult.getProfile().getName(), microsoftAuthResult.getProfile().getId(), microsoftAuthResult.getAccessToken(), "msa");
            }

        } catch (MicrosoftAuthenticationException e) {
            e.printStackTrace();
            this.status = "\247cLogin failed!";
            GuiAltManager.pubStatus = "\247cLogin failed!";
        }
    }
    
    public void setStatus(final String status) {
        this.status = status;
        GuiAltManager.pubStatus = status;
    }
    public void saveAccountList() {
        try {
            final StringBuilder data = new StringBuilder();
            for (final Alt s : TIMC.getAltManager().getAlts()) {
            	//System.out.println("sjknk");
                data.append(String.valueOf(s) + "\n");
            }
            final BufferedWriter writer = new BufferedWriter(new FileWriter(this.altsFile));
            writer.write(data.toString());
            writer.close();
        }
        catch (Exception ex) {}
    }
}
