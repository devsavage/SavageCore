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

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import tv.savageboy74.savagecore.common.config.ConfigHandler;
import tv.savageboy74.savagecore.common.core.command.CommandHandler;
import tv.savageboy74.savagecore.common.proxy.CommonProxy;
import tv.savageboy74.savagecore.common.util.LogHelper;
import tv.savageboy74.savagecore.common.util.SavageCoreProps;
import tv.savageboy74.savagecore.common.util.UpdateChecker;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;

import java.io.IOException;

@Mod(modid = SavageCoreProps.MOD_ID, version = SavageCoreProps.VERSION, guiFactory = SavageCoreProps.GUI_FACTORY_CLASS, dependencies = SavageCoreProps.DEPENDENCIES)
public class SavageCore
{

    @Mod.Instance(SavageCoreProps.MOD_ID)
    public static SavageCore instance;

    @SidedProxy(clientSide = SavageCoreProps.CLIENT_PROXY, serverSide = SavageCoreProps.SERVER_PROXY)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ConfigHandler.init(event.getSuggestedConfigurationFile());
        MinecraftForge.EVENT_BUS.register(new ConfigHandler());
        MinecraftForge.EVENT_BUS.register(this);

        if (ConfigHandler.checkForUpdates) {
            try {
                LogHelper.info(SavageCoreProps.MOD_NAME, "Checking For Updates...");
                UpdateChecker.checkForUpdates();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        LogHelper.info(SavageCoreProps.MOD_NAME, "Pre-Initialization Complete.");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        LogHelper.info(SavageCoreProps.MOD_NAME, "Initialization Complete.");
    }


    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        LogHelper.info(SavageCoreProps.MOD_NAME, "Post-Initialization Complete.");
    }


    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        CommandHandler.initCommands(event);
    }

    @SubscribeEvent
    public void checkUpdate(PlayerEvent.PlayerLoggedInEvent event) {
        if(ConfigHandler.checkForUpdates) {
            if (SavageCoreProps.OUTDATED) {
                EnumChatFormatting darkRed = EnumChatFormatting.DARK_RED;
                EnumChatFormatting aqua = EnumChatFormatting.AQUA;
                EnumChatFormatting darkGreen = EnumChatFormatting.DARK_GREEN;
                EnumChatFormatting reset = EnumChatFormatting.RESET;
                EnumChatFormatting green = EnumChatFormatting.GREEN;

                String name = SavageCoreProps.MOD_NAME;
                String outdatedText = aqua + "[" + name + "] " + reset + "This version of " + green + name + reset + " is" + darkRed + " outdated!";
                String versionText =  "Current Version: " + darkRed + SavageCoreProps.VERSION + reset + " Newest Version: " + darkGreen +  SavageCoreProps.NEWVERSION;

                event.player.addChatComponentMessage(new ChatComponentText(outdatedText));
                event.player.addChatComponentMessage(new ChatComponentText(versionText));
            }
        }
    }
}
