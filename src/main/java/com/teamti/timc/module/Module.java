package com.teamti.timc.module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.teamti.timc.config.ConfigSetting;
import com.teamti.timc.event.ListenerAdapter;
import com.teamti.timc.main.TIMC;
import com.teamti.timc.module.settings.Setting;
import com.teamti.timc.module.settings.impl.*;
import com.teamti.timc.utils.Utils;
import com.teamti.timc.utils.animations.Animation;
import com.teamti.timc.utils.animations.Direction;
import com.teamti.timc.utils.animations.impl.DecelerateAnimation;
import lombok.Getter;
import lombok.Setter;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
public class Module extends ListenerAdapter implements Utils {
    @Expose
    @SerializedName("name")
    private final String name;
    private final String description;
    private final Category category;
    private final CopyOnWriteArrayList<Setting> settingsList = new CopyOnWriteArrayList<>();
    private String suffix;
    private String author = "";

    @Expose
    @SerializedName("toggled")
    protected boolean enabled;
    @Expose
    @SerializedName("settings")
    public ConfigSetting[] cfgSettings;


    private boolean expanded;
    private final Animation animation = new DecelerateAnimation(250, 1).setDirection(Direction.BACKWARDS);

    public static int categoryCount;
    public static float allowedClickGuiHeight = 300;

    private final KeybindSetting keybind = new KeybindSetting(Keyboard.KEY_NONE);

    public Module(String name, Category category, String description) {
        this.name = name;
        this.category = category;
        this.description = description;
        addSettings(keybind);
    }

    public boolean isInGame() {
        return mc.theWorld != null && mc.thePlayer != null;
    }

    public void addSettings(Setting... settings) {
        settingsList.addAll(Arrays.asList(settings));
    }

    public void setToggled(boolean toggled) {
        this.enabled = toggled;
        if (toggled) {
            this.onEnable();
        } else {
            this.onDisable();
        }
    }

    public void toggleSilent() {
        this.enabled = !this.enabled;
        if (this.enabled) {
            this.onEnable();
        } else {
            this.onDisable();
        }
    }

    public void toggleSilent(boolean toggled) {
        this.enabled = toggled;
        if (this.enabled) {
            this.onEnable();
        } else {
            this.onDisable();
        }
    }

    //TODO: wtf is this code.
    public void toggle() {
        toggleSilent();
//        if (NotificationsMod.toggleNotifications.isEnabled()) {
//            String titleToggle = "Module toggled";
//            String descriptionToggleOn = this.getName() + " was " + "§aenabled\r";
//            String descriptionToggleOff = this.getName() + " was " + "§cdisabled\r";
//
//            switch (NotificationsMod.mode.getMode()) {
//                case "Default":
//                    if (NotificationsMod.onlyTitle.isEnabled()) titleToggle = this.getName() + " toggled";
//                    break;
//                case "SuicideX":
//                    if (this.isEnabled()) {
//                        titleToggle = "Enabled Module " + this.getName() + ". PogO";
//                    } else {
//                        titleToggle = "Disabled Module " + this.getName() + ". :/";
//                    }
//                    descriptionToggleOff = "";
//                    descriptionToggleOn = "";
//                    break;
//            }
//            if (enabled) {
//                NotificationManager.post(NotificationType.SUCCESS, titleToggle, descriptionToggleOn);
//            } else {
//                NotificationManager.post(NotificationType.DISABLE, titleToggle, descriptionToggleOff);
//            }
//        }
    }

    public boolean hasMode() {
        return suffix != null;
    }


    public void onEnable() {
        TIMC.INSTANCE.getEventProtocol().register(this);
    }

    public void onDisable() {
//        if (this instanceof GlowESP) {
//            GlowESP.fadeIn.setDirection(Direction.BACKWARDS);
//            Multithreading.schedule(() -> TIMC.INSTANCE.getEventProtocol().unregister(this), 250, TimeUnit.MILLISECONDS);
//        } else {
//            TIMC.INSTANCE.getEventProtocol().unregister(this);
//        }
          TIMC.INSTANCE.getEventProtocol().unregister(this);

    }

    public void setKey(int code) {
        this.keybind.setCode(code);
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getKeybindCode() {
        return keybind.getCode();
    }


    public NumberSetting getNumberSetting(String name) {
        for (Setting setting : settingsList) {
            if (setting instanceof NumberSetting && setting.getName().equalsIgnoreCase(name)) {
                return (NumberSetting) setting;
            }
        }
        return null;
    }

    public BooleanSetting getBooleanSetting(String name) {
        for (Setting setting : settingsList) {
            if (setting instanceof BooleanSetting && setting.getName().equalsIgnoreCase(name)) {
                return (BooleanSetting) setting;
            }
        }
        return null;
    }

    public ModeSetting getModeSetting(String name) {
        for (Setting setting : settingsList) {
            if (setting instanceof ModeSetting && setting.getName().equalsIgnoreCase(name)) {
                return (ModeSetting) setting;
            }
        }
        return null;
    }

    public StringSetting getStringSetting(String name) {
        for (Setting setting : settingsList) {
            if (setting instanceof StringSetting && setting.getName().equalsIgnoreCase(name)) {
                return (StringSetting) setting;
            }
        }
        return null;
    }

    public MultipleBoolSetting getMultiBoolSetting(String name) {
        for (Setting setting : settingsList) {
            if (setting instanceof MultipleBoolSetting && setting.getName().equalsIgnoreCase(name)) {
                return (MultipleBoolSetting) setting;
            }
        }
        return null;
    }


    public ColorSetting getColorSetting(String name) {
        for (Setting setting : settingsList) {
            if (setting instanceof ColorSetting && setting.getName().equalsIgnoreCase(name)) {
                return (ColorSetting) setting;
            }
        }
        return null;
    }

}
