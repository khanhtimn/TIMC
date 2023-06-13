package com.teamti.timc.main;

import com.teamti.timc.utils.viamcp.ViaMCP;

public class Init {

//    private static final HashMap<Object, Module> modules = new HashMap<>();

    public static void start() {

//        TIMC.INSTANCE.getEventProtocol().register(new BackgroundProcess());
//
//        Tenacity.INSTANCE.setConfigManager(new ConfigManager());
//        ConfigManager.defaultConfig = new File(Minecraft.getMinecraft().mcDataDir + "/Tenacity/Config.json");
//        Tenacity.INSTANCE.getConfigManager().collectConfigs();
//        if (ConfigManager.defaultConfig.exists()) {
//            Tenacity.INSTANCE.getConfigManager().loadConfig(Tenacity.INSTANCE.getConfigManager().readConfigData(ConfigManager.defaultConfig.toPath()), true);
//        }

//        DragManager.loadDragData();
//
//        Tenacity.INSTANCE.setAltManager(new GuiAltManager());


        //String apiKey = Tenacity.INSTANCE.getIntentAccount().api_key;
        //Tenacity.INSTANCE.getIrcUtil().connect(apiKey);

        //Cloud.setApiKey(apiKey);
        //Tenacity.INSTANCE.getCloudDataManager().refreshData();


//        Tenacity.INSTANCE.kingGenApi = new KingGenApi();

        try {
            TIMC.LOGGER.info("Starting ViaMCP...");
            ViaMCP.create();
            ViaMCP.INSTANCE.initAsyncSlider(); // For top left aligned slider
        } catch (Exception e) {
            e.printStackTrace();
        }



    }



//    private static void addModules(Class<? extends Module>... classes) {
//        for (Class<? extends Module> moduleClass : classes) {
//            try {
//                modules.put(moduleClass, moduleClass.newInstance());
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

}
