package tv.savageboy74.savagecore.common;

/*
 * SavageCore.java
 * Copyright (C) 2014 - 2015 Savage - github.com/savageboy74
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import tv.savageboy74.savagecore.common.config.ConfigHandler;
import tv.savageboy74.savagecore.common.core.command.CommandHandler;
import tv.savageboy74.savagecore.common.proxy.CommonProxy;
import tv.savageboy74.savagecore.common.util.LogHelper;
import tv.savageboy74.savagecore.common.util.Reference;
import tv.savageboy74.savagecore.common.util.UpdateChecker;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
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

        if (ConfigHandler.checkForUpdates)
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
    public void serverStarting(FMLServerStartingEvent event)
    {
        CommandHandler.initCommands(event);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        LogHelper.info("Initialization Complete.");
    }

    @SubscribeEvent
    public void checkUpdate(PlayerEvent.PlayerLoggedInEvent event)
    {
        if (Reference.OUTDATED)
        {
            EnumChatFormatting darkRed = EnumChatFormatting.DARK_RED;
            EnumChatFormatting aqua = EnumChatFormatting.AQUA;
            EnumChatFormatting darkGreen = EnumChatFormatting.DARK_GREEN;
            EnumChatFormatting reset = EnumChatFormatting.RESET;
            EnumChatFormatting green = EnumChatFormatting.GREEN;

            String name = Reference.MOD_NAME;
            String outdatedText = aqua + "[" + name + "] " + reset + "This version of " + green + name + reset + " is" + darkRed + " outdated!";
            String versionText =  "Current Version: " + darkRed + Reference.VERSION + reset + "Newest Version: " + darkGreen +  Reference.NEWVERSION;

            event.player.addChatComponentMessage(new ChatComponentText(outdatedText));
            event.player.addChatComponentMessage(new ChatComponentText(versionText));
        }
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        LogHelper.info("Post-Initialization Complete.");
    }
}
