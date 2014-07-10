package com.savageboy74.savagecore.common;

import com.savageboy74.savagecore.common.config.ConfigHandler;
import com.savageboy74.savagecore.common.proxy.CommonProxy;
import com.savageboy74.savagecore.common.util.LogHelper;
import com.savageboy74.savagecore.common.util.Reference;
import com.savageboy74.savagecore.common.util.UpdateChecker;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;

import java.io.IOException;

@Mod(modid = Reference.MOD_ID, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY_CLASS)
public class SavageCore
{

    @Mod.Instance(Reference.MOD_ID)
    public static SavageCore instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ConfigHandler.init(event.getSuggestedConfigurationFile());
        FMLCommonHandler.instance().bus().register(new ConfigHandler());

        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);

        LogHelper.info("Pre-Initialization Complete.");

        if (ConfigHandler.checkForUpdates == true)
        {
            try
            {
                LogHelper.info("Checking For Updates...");
                UpdateChecker.checkForUpdates();
            }

            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        LogHelper.info("Initialization Complete.");
    }

    @SubscribeEvent
    public void checkUpdate(PlayerEvent.PlayerLoggedInEvent event) {
        if (Reference.OUTDATED) {
            event.player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.AQUA + "SavageCore " +  EnumChatFormatting.WHITE + "is " + EnumChatFormatting.DARK_RED + "outdated!"));
            event.player.addChatComponentMessage(new ChatComponentText("Current Version: " + EnumChatFormatting.DARK_RED + Reference.CURRENTVERSION + EnumChatFormatting.WHITE +  " Newest Version: " + EnumChatFormatting.DARK_GREEN + Reference.NEWVERSION));
        }
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        LogHelper.info("Post-Initialization Complete.");
    }
}
