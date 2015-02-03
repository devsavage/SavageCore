package tv.savageboy74.savagecore.common.config;

import tv.savageboy74.savagecore.common.util.Reference;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigHandler
{
    public static Configuration configuration;
    public static boolean checkForUpdates = true;

    public static void init(File configFile)
    {
        if (configuration == null)
        {
            configuration = new Configuration(new File("config/savageboy74/SavageCore.cfg"));
            loadConfiguration();
        }
    }

    @SubscribeEvent
    public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.modID.equalsIgnoreCase(Reference.MOD_ID))
        {
            loadConfiguration();
        }
    }

    private static void loadConfiguration()
    {
        checkForUpdates = configuration.getBoolean("Check For Updates", Configuration.CATEGORY_GENERAL, true, "Allow SavageCore to check for updates");

        if (configuration.hasChanged())
        {
            configuration.save();
        }
    }
}
