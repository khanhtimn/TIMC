package com.teamti.timc.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.teamti.timc.main.TIMC;
import com.teamti.timc.main.Module;
public class Settings
extends SettingsFile.CustomSettingsFile {
    public Settings(String name, boolean Module2, boolean loadOnStart) {
        super(name, Module2, loadOnStart);
    }

    @Override
    public void loadFile() throws IOException {
        String line;
        BufferedReader variable9 = new BufferedReader(new FileReader(this.getFile()));
        while ((line = variable9.readLine()) != null) {
            String[] stuff = line.split(":");
            Module module = TIMC.getModule(stuff[0]);
            
            Value val = module.getValue(stuff[1]);
            if (val instanceof BooleanValue) {
                BooleanValue value = (BooleanValue)val;
                value.set(Boolean.valueOf(stuff[2]));
                continue;
            }
            if (!(val instanceof NumberValue)) continue;
            NumberValue value = (NumberValue)val;
            value.setVal(Double.valueOf(stuff[2]));
        }
        variable9.close();
    }

    @Override
    public void saveFile() throws IOException {
        PrintWriter variable9 = new PrintWriter(new FileWriter(this.getFile()));
        for (Module m2 : TIMC.getModules()) {
        	
            for (Value val : m2.getValues()) {
                
                if (val instanceof BooleanValue) {
                	BooleanValue value = (BooleanValue)val;
                    value = (BooleanValue)val;
                    variable9.println(String.valueOf(m2.getName()) + ":" + value.getName() + ":" + value.isEnabled());
                    continue;
                }
                if (!(val instanceof NumberValue)) continue;
                NumberValue value = (NumberValue)val;
                variable9.println(String.valueOf(m2.getName()) + ":" + value.getName() + ":" + value.getValue());
                //System.out.println(String.valueOf(m2.getName()) + ":" + value.getName() + ":" + value.getValue());
                
                
            }
            
        }
        variable9.close();
    }
}

