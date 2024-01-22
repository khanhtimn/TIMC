package com.teamti.timc.alts;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.teamti.timc.main.TIMC;
import com.teamti.timc.util.FileUtils;

public class AltFile {
	public static final File ALTS_DIR = FileUtils.getConfigFile("Alts");
	public static void load() {
		try {
        	List<String> fileContent = FileUtils.read(ALTS_DIR);
             for (String line : fileContent) {
                try {
                	String s;
                        if (line.contains("\t")) {
                        	line = line.replace("\t", "");
                        }
                        if(line.isEmpty())
                        	continue;
                        
                        if(!line.contains(":"))
                        	continue;
                        
                        if(line.split(":").length == 4){
                        	TIMC.getAltManager().getAlts().add(new Alt(line.split(":")[0], line.split(":")[1], line.split(":")[2], line.split(":")[3]));
                        }
                        if(line.split(":").length == 2){
                        	TIMC.getAltManager().getAlts().add(new Alt(line.split(":")[0], line.split(":")[1]));
                        }
                
                } catch (Exception split) {
    				// empty catch block
                }
            }
    	}catch (Exception split) {
    		
    	}
    }

    public static void save() {
    	new Thread(){
    		
    		@Override
    		public void run(){
    	ArrayList<String> fileContent = new ArrayList<String>();
		for (final Alt alt : TIMC.getAltManager().getAlts()) {
			if(alt.username.isEmpty() && alt.uuid.isEmpty()){
				fileContent.add(alt.email+":"+alt.password);
			}else{
				fileContent.add(alt.email+":"+alt.password+":"+alt.username+":"+alt.uuid);
			}
			
		}
		FileUtils.write(ALTS_DIR, fileContent, true);
    		}
    		
    	}.start();
    }
}
